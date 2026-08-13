#!/usr/bin/env bash
set -euo pipefail

EVIDENCE_FILE="${1:?usage: verify-delivery-evidence.sh <evidence.json>}"

python3 - "$EVIDENCE_FILE" <<'PY'
import hashlib
import json
import pathlib
import sys

evidence_path = pathlib.Path(sys.argv[1]).resolve()
evidence = json.loads(evidence_path.read_text())
root = evidence_path.parent

def digest(path):
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            h.update(chunk)
    return h.hexdigest()

artifact = root / evidence["artifact"]["path"]
sbom = root / evidence["sbom"]["path"]

if not artifact.is_file():
    raise SystemExit(f"REJECT: artifact is missing: {artifact}")
if not sbom.is_file():
    raise SystemExit(f"REJECT: SBOM is missing: {sbom}")
if digest(artifact) != evidence["artifact"]["sha256"]:
    raise SystemExit("REJECT: artifact digest does not match evidence")
if digest(sbom) != evidence["sbom"]["sha256"]:
    raise SystemExit("REJECT: SBOM digest does not match evidence")
if evidence["source"]["revision"] in {"", "UNKNOWN", None}:
    raise SystemExit("REJECT: source revision is not established")
if evidence["checks"].get("build") != "PASS":
    raise SystemExit("REJECT: build evidence is not PASS")
if evidence["checks"].get("tests") != "PASS":
    raise SystemExit("REJECT: test evidence is not PASS")
if evidence["checks"].get("sbom") != "PASS":
    raise SystemExit("REJECT: SBOM evidence is not PASS")

print("PROMOTE: deterministic delivery evidence is internally consistent")
PY
