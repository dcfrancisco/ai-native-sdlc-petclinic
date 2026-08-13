# Independent Review: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Evidence source:** `../../ai/evidence/visit-rate-limiter-evidence-log.md`  
**Status:** Review findings recorded; production acceptance withheld

## Review scope

Review the limiter diff, SI-VRL-001, ADR-004, PR-VRL-001, CC-VRL-001, tests,
schemas, and recorded evidence for behavior, architecture, security,
maintainability, and evidence accuracy.

## Findings

### Major: production-enablement evidence is incomplete

INV-20 has no accepted representative load profile or p95 execution. Production
enablement remains blocked.

### Major: database-specific limiter behavior is not established

The retained historical record reports MySQL and PostgreSQL application/schema
startup, but it does not retain the exact source commit or raw output and did
not execute limiter threshold or concurrency behavior on those databases.

### Major: independent rejected-persistence assertion is absent

The HTTP evidence records 429 and `Retry-After`, but does not independently
query persistence to prove that the rejected request did not create a visit.

### Moderate: operational failure behavior is unverified

No lock-timeout or database-outage injection was executed. The failure policy
is specified by INV-19 but not established by runtime evidence.

### Moderate: several boundary cases remain partial or unrun

Missing-pet runtime behavior, different-pet parallelism, 59.999-second and
timezone cases, and multi-instance behavior remain unverified as recorded in
the Verification Matrix.

### Policy: production semantics remain human-owned

The threshold, invalid-form capacity policy, manual reset, rolling-window
option, and representative load profile remain unresolved production choices.

## Review disposition

**REVISE for broader claims.** The bounded educational implementation and its
recorded H2 evidence may be inspected, but the evidence does not authorize
production enablement or a broader database/performance claim.

## Correction handoff

See [`visit-rate-limiter-correction-contract.md`](../../journey/08-review-and-correction/visit-rate-limiter-correction-contract.md).
