#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
OUT_DIR="${DELIVERY_EVIDENCE_DIR:-${ROOT_DIR}/target/delivery-evidence}"
mkdir -p "${OUT_DIR}"

status="PASS"
build_status="NOT_RUN"
tests_status="NOT_RUN"
sbom_status="NOT_RUN"

if [[ -n "${JAVA_HOME:-}" ]]; then
    export PATH="${JAVA_HOME}/bin:${PATH}"
fi

source_revision="$(git -C "${ROOT_DIR}" rev-parse HEAD 2>/dev/null || printf 'UNKNOWN')"
java_version="$(java -version 2>&1 | head -n 1 || printf 'NOT_RUN')"
maven_version="$("${ROOT_DIR}/mvnw" -version 2>&1 | head -n 1 || printf 'NOT_RUN')"

if "${ROOT_DIR}/mvnw" -B verify >"${OUT_DIR}/maven-verify.log" 2>&1; then
    build_status="PASS"
    tests_status="PASS"
else
    build_status="FAIL"
    tests_status="FAIL"
    status="REJECT"
fi

artifact=""
if [[ -f "${ROOT_DIR}/target/spring-petclinic-4.0.0-SNAPSHOT.jar" ]]; then
    artifact="${ROOT_DIR}/target/spring-petclinic-4.0.0-SNAPSHOT.jar"
fi

if "${ROOT_DIR}/mvnw" -B -DskipTests cyclonedx:makeAggregateBom >"${OUT_DIR}/cyclonedx.log" 2>&1 \
    && [[ -f "${ROOT_DIR}/target/bom.json" ]]; then
    sbom_status="PASS"
else
    sbom_status="NOT_ESTABLISHED"
    status="NOT_ESTABLISHED"
fi

if [[ -z "${artifact}" ]]; then
    status="NOT_ESTABLISHED"
    artifact="${OUT_DIR}/missing-artifact"
    : > "${artifact}"
fi

if [[ -f "${ROOT_DIR}/target/bom.json" ]]; then
    cp "${ROOT_DIR}/target/bom.json" "${OUT_DIR}/bom.json"
else
    printf '%s\n' '{"bomFormat":"CycloneDX","status":"NOT_ESTABLISHED"}' > "${OUT_DIR}/bom.json"
fi
cp "${artifact}" "${OUT_DIR}/artifact.jar"

python3 - "${OUT_DIR}" "${source_revision}" "${java_version}" "${maven_version}" "${build_status}" "${tests_status}" "${sbom_status}" "${status}" <<'PY'
import hashlib
import json
import pathlib
import sys

out = pathlib.Path(sys.argv[1])
revision, java, maven, build, tests, sbom, status = sys.argv[2:]
def sha(path):
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()

payload = {
    "schemaVersion": "PLC-CI-001",
    "caseId": "PLC-VRL-001",
    "evidenceClass": "BOUNDED_DEMONSTRATION",
    "source": {"revision": revision},
    "environment": {"java": java, "maven": maven},
    "checks": {"build": build, "tests": tests, "sbom": sbom},
    "artifact": {"path": "artifact.jar", "sha256": sha(out / "artifact.jar")},
    "sbom": {"path": "bom.json", "sha256": sha(out / "bom.json")},
    "provenance": {
        "sourceRevisionRecorded": revision not in {"", "UNKNOWN"},
        "attestation": "NOT_EXECUTED",
        "signature": "NOT_EXECUTED",
        "reason": "Hosted signing and attestation require the release environment."
    },
    "gate": {
        "disposition": status,
        "releaseAuthorized": False,
        "authority": "Named release owner, not AI or the evidence script"
    }
}
(out / "evidence.json").write_text(json.dumps(payload, indent=2) + "\n")
PY

if [[ "${status}" == "PASS" ]]; then
    "${ROOT_DIR}/scripts/verify-delivery-evidence.sh" "${OUT_DIR}/evidence.json" >"${OUT_DIR}/gate-result.txt"
else
    printf '%s\n' "${status}: evidence is not eligible for promotion" > "${OUT_DIR}/gate-result.txt"
fi

cat > "${OUT_DIR}/README.md" <<EOF
# Delivery Evidence Bundle

Case: PLC-VRL-001
Source revision: ${source_revision}
Evidence class: BOUNDED_DEMONSTRATION
Deterministic disposition: ${status}
Release authorization: NOT_GRANTED

The bundle joins build/test status, the CycloneDX SBOM digest, the candidate
artifact digest, and the source revision. It does not establish a production
signature, hosted attestation, deployment success, or business outcome.
AI review may contribute analysis, but it cannot change this gate or authorize
release.
EOF

printf '%s\n' "${status}"
