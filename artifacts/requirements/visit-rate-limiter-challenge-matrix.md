# Challenge Matrix: Per-Pet Visit Booking Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Accepted for the educational companion scope  
**Book stage:** Chapter 5

This matrix records the questions raised from the capability request. It does
not claim that unresolved production policy or unexecuted verification has
been resolved.

| Dimension | Challenge question | Accepted answer for this bounded case | Evidence or disposition |
| --- | --- | --- | --- |
| Trigger | Which requests count? | Matching visit-booking POST attempts, including invalid forms before validation | SI-VRL-001 INV-01, INV-05 |
| Identity | What is “per pet”? | Numeric `petId` from the route; not owner, client, session, or visit date | INV-02 |
| Threshold | How much capacity is allowed? | First three attempts in one fixed window; fourth and later attempts are rejected | INV-03 |
| Rejection | What does rejection mean? | HTTP 429, integer `Retry-After`, and no persisted visit for the rejected request | INV-04; rejected persistence is not independently run |
| Time | What clock and boundary apply? | UTC instants; exactly 60 seconds starts a new window | INV-06, INV-08 |
| Persistence | Does restart or another instance reset state? | Limiter state is database-backed | INV-09; multi-instance behavior is not run |
| First row | What is locked when no limiter row exists? | Lock the existing pet row before reading or creating limiter state | INV-10; selected by ADR-004 |
| Isolation | Do different pets share a lock? | They must not intentionally serialize on one global lock | INV-11; not measured |
| Concurrency | Can concurrent requests exceed capacity? | No more than three same-pet decisions are allowed in one window | INV-12; H2 evidence exists |
| Missing pet | Should the limiter reveal pet existence? | Pass through to existing controller behavior | INV-07; dedicated runtime case not run |
| Compatibility | What existing behavior must remain? | Existing redirect, flash, validation, and schema compatibility remain | INV-14–INV-16 |
| Dependencies | May implementation add infrastructure? | No new runtime library, cache, endpoint, or external limiter service | INV-17; CC-VRL-001 |
| Privacy/security | What data may be exposed? | No owner contact data, visit descriptions, or personal data in limiter responses/logs | INV-18; security assessment records residual risk |
| Failure | What happens on database failure? | Do not silently convert failure into allowed | INV-19; failure injection not run |
| Performance | What latency is acceptable? | Target below 10 ms p95 under an agreed representative load profile | INV-20; profile and run are absent |

## Explicitly unresolved

The production threshold, invalid-form policy, manual reset, rolling-window
option, representative load profile, multi-instance behavior, database-specific
locking behavior, and production acceptance remain outside this matrix's
authority. See the complete [Spec Invariants](../../ai/specs/visit-rate-limiter-invariants.md).
