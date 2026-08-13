#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "${WORK_DIR}"' EXIT

printf 'candidate\n' > "${WORK_DIR}/artifact.jar"
printf '{"bomFormat":"CycloneDX","components":[]}\n' > "${WORK_DIR}/bom.json"

python3 - "${WORK_DIR}" <<'PY'
import hashlib
import json
import pathlib
import sys

root = pathlib.Path(sys.argv[1])
def sha(path):
    return hashlib.sha256(path.read_bytes()).hexdigest()

(root / "evidence.json").write_text(json.dumps({
    "schemaVersion": "PLC-CI-001",
    "source": {"revision": "fixture-revision"},
    "artifact": {"path": "artifact.jar", "sha256": sha(root / "artifact.jar")},
    "sbom": {"path": "bom.json", "sha256": sha(root / "bom.json")},
    "checks": {"build": "PASS", "tests": "PASS", "sbom": "PASS"},
    "gate": {"disposition": "PROMOTE", "releaseAuthorized": False},
}, indent=2) + "\n")
PY

"${ROOT_DIR}/scripts/verify-delivery-evidence.sh" "${WORK_DIR}/evidence.json" >/dev/null

printf 'tampered\n' >> "${WORK_DIR}/artifact.jar"
if "${ROOT_DIR}/scripts/verify-delivery-evidence.sh" "${WORK_DIR}/evidence.json" >/dev/null 2>&1; then
    echo "Expected tampered artifact to be rejected" >&2
    exit 1
fi

echo "PASS: delivery gate accepts matching evidence and rejects tampered artifacts"
