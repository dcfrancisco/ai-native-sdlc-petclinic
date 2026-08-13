# Human Acceptance Record: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** PENDING HUMAN ACCEPTANCE  
**Evidence:** `../../ai/evidence/visit-rate-limiter-evidence-log.md` and `../../ai/evidence/visit-rate-limiter-verification.json`

## Candidate claim

The candidate demonstrates a bounded educational per-pet visit-booking rate
limiter using database-backed state, the existing pet row as the serialization
point, H2 integration evidence, and existing visit-controller regression tests.

## Evidence supporting the bounded claim

- threshold and 429 behavior are recorded as passing H2 evidence;
- exact 60-second reset is recorded as passing H2 evidence;
- repeated eight-request same-pet concurrency is recorded as passing H2 evidence;
- existing visit-controller regression tests are recorded as passing;
- implementation and schema inspection support the selected design and
  dependency boundary.

## Limitations and blocking gates

Human acceptance must not promote the following to proven claims:

- current-publication-baseline reproduction;
- MySQL or PostgreSQL limiter behavior;
- multi-instance enforcement;
- different-pet isolation;
- lock-timeout or database-outage behavior;
- independent rejected-visit persistence assertion;
- representative p95 performance for INV-20;
- Docker/runtime functional execution for the limiter;
- production threshold, invalid-form policy, or production enablement.

## Decision

**No human acceptance is recorded.** The AI-generated evidence log explicitly
states that acceptance is not recorded, and the Verification Matrix gate is
`BLOCKED` for broader claims. An accountable human may later record a narrow
educational acceptance only after reviewing the evidence and residual risk.

| Role | Decision | Date | Notes |
| --- | --- | --- | --- |
| Accountable acceptance owner | PENDING | — | No approval fabricated |
