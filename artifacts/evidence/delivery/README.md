# Delivery Evidence Records

This directory describes the reader-visible evidence shape for `PLC-VRL-001`.
The generated bundle is written to `target/delivery-evidence/` because it
contains a candidate artifact and environment-specific output.

The focused gate test has two executed outcomes:

- matching artifact and SBOM digests: accepted as an eligible candidate by the
  deterministic verifier;
- tampered artifact: rejected because its digest no longer matches the evidence.

The full local run recorded in Chapter 14 generated a CycloneDX 1.6 SBOM with
106 components but rejected the candidate because Maven verification failed in
the available environment. It is therefore **Bounded Demonstration**, not
release acceptance. Hosted signing and attestation remain `NOT_EXECUTED`.

Run:

```bash
./scripts/test-delivery-gate.sh
./scripts/collect-delivery-evidence.sh
```
