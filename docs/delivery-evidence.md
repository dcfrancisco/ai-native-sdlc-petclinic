# Delivery Evidence

The Maven workflow and `scripts/collect-delivery-evidence.sh` demonstrate the
bounded delivery chain for `PLC-VRL-001`:

```text
source revision
  -> Maven build and tests
  -> CycloneDX SBOM
  -> candidate artifact
  -> artifact and SBOM digests
  -> deterministic evidence verification
  -> PROMOTE / REJECT / NOT_ESTABLISHED
```

The evidence script records signature and hosted attestation as
`NOT_EXECUTED` in the local companion. A production release may add those
controls, but an SBOM is not itself a security verdict and an eligible
candidate is not an authorized release.

AI may propose code, analyze findings, or assemble evidence. The deterministic
checks establish whether the recorded evidence is internally consistent. A
named release owner remains responsible for promotion and residual risk.

Run the focused gate test with:

```bash
./scripts/test-delivery-gate.sh
```

Run the full bounded evidence path with:

```bash
./scripts/collect-delivery-evidence.sh
```

If Java, Maven, or SBOM generation is unavailable, the bundle preserves
`NOT_ESTABLISHED` rather than converting an unavailable check into success.
