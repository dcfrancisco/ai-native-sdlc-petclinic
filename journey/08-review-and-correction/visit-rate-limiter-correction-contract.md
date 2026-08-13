# Correction Contract: Visit Rate-Limiter Evidence Gaps

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Proposed bounded correction; not executed in the retained evidence  
**Related review:** `../../artifacts/reviews/visit-rate-limiter-review.md`

## Objective

Address only the review findings that can be corrected by bounded evidence and
verification work. Do not silently change product policy, architecture, or the
release claim.

## Authority

- SI-VRL-001
- ADR-004
- PR-VRL-001
- CC-VRL-001
- the independent review and existing Verification Matrix

## Permitted correction work

- add an independent HTTP assertion that a rate-rejected request does not
  persist a visit;
- add focused boundary tests for missing pets, pre-boundary time, and different
  pet isolation where the environment supports them;
- execute and record limiter-specific MySQL/PostgreSQL and failure-injection
  checks only with approved local environments;
- record actual Docker/runtime and functional evidence when executed;
- update the evidence and acceptance records with observed results.

## Exclusions

- changing the threshold, window, attempt semantics, or pet-row serialization;
- adding a reset endpoint, new dependency, external service, or production
  policy;
- claiming performance, multi-instance, database, Docker, security, or release
  evidence without execution;
- approving the correction or accepting residual risk.

## Required reporting

Report each check as passed, failed, blocked, partial, or not run with the
environment, command, raw-output reference where available, limitation, and
gate effect. Preserve the current historical-provenance limitation.

## Stop conditions

Stop when a finding requires a new product or architecture decision, a
deployed-schema migration strategy, unavailable environment authority, or a
security/privacy decision owned by a human.
