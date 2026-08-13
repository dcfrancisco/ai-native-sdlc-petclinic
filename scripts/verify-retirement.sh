#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
retired="${ROOT_DIR}/.github/workflows/gradle-build.yml"

if [[ -e "${retired}" ]]; then
    echo "REJECT: retired workflow still exists" >&2
    exit 1
fi

if rg -n --glob '!target/**' --glob '!artifacts/retirement/**' \
    'gradle-build\.yml' "${ROOT_DIR}/.github" "${ROOT_DIR}/docs" "${ROOT_DIR}/README.md"; then
    echo "REJECT: active documentation or workflow references retired path" >&2
    exit 1
fi

test -f "${ROOT_DIR}/artifacts/retirement/retirement-decision.md"
test -f "${ROOT_DIR}/artifacts/retirement/consumer-search.md"
echo "PASS: repository-level retirement checks complete; external consumers remain not established"
