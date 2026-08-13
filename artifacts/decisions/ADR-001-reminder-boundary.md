# ADR-001: Keep Reminder Delivery Behind a Local Port

**Chapter:** 6  
**Status:** Accepted
**Decision owner:** Repository maintainer
**Decision date:** 2026-07-18
**Evidence classification:** Verified Reference Implementation

## Decision question

How should the reference implementation represent reminder notification while remaining reproducible, vendor neutral, and honest about what its evidence proves?

## Context

The book needs a complete lifecycle capability, but production provider selection would introduce credentials, contracts, consent, external reliability, cost, and vendor-specific behavior. The practice must expose the outbound boundary without implying that a local execution proves delivery to a pet owner.

## Forces

- Readers need an implementation they can run without commercial accounts or secrets.
- The design must preserve a replaceable outbound boundary.
- State names and metrics must not claim stronger evidence than the adapter supplies.
- Provider selection, consent, and production reliability require authorities outside this exercise.
- The example must support deterministic tests, failure handling, review, and operational recovery.

## Decision

Place reminder eligibility and attempt tracking inside a bounded reminder package. Define `NotificationGateway` as an outbound port. Use a local adapter that returns explicit evidence:

- `ACCEPTED`: the configured adapter accepted the reminder; this does not prove user receipt.
- `FAILED`: the adapter reported a definite failure.
- `UNKNOWN`: the adapter cannot determine whether the effect occurred.

Persist only a masked destination hint. Do not call the local outcome `DELIVERED`.

## Alternatives

1. **Integrate a commercial provider.** Rejected because it introduces decisions, secrets, consent, cost, and provider behavior not authorized by the exercise.
2. **Log directly from the service.** Rejected because it hides the outbound boundary, cannot represent indeterminate outcomes, and weakens substitution and testing.
3. **Treat a successful local call as delivery.** Rejected because adapter acceptance is not evidence of owner receipt.
4. **Build a separate reminder service.** Deferred because deployment and distributed consistency would dominate the learning objective.

## Invariants

- A state name must describe only what its evidence establishes.
- No full destination is persisted or written to ordinary logs.
- `UNKNOWN` is preserved for reconciliation and is not automatically retried.
- Production provider selection and release authority remain human decisions.
- The reminder package must not change existing owner and visit behavior.

## Consequences

- The capability demonstrates architecture, test doubles, adapter outcomes, recovery, metrics, security, and governance.
- It does not establish production messaging readiness.
- A future provider must satisfy a separate ADR, consent decision, security review, and operational readiness review.

## Deferred decisions

- Provider and channel selection
- Consent evidence
- Cancellation and rescheduling semantics
- Retry backoff and unknown-outcome reconciliation
- Retention, audit, and service-level objectives

## Conformance evidence

- `NotificationGateway` isolates the outbound dependency.
- `LocalNotificationGateway` returns `ACCEPTED` without external network delivery.
- Reminder tests cover accepted, failed, and unknown outcomes.
- Metrics and operator messages use the same evidence terms.
- The verification report records what the local adapter does not prove.

## Supersession triggers

Supersede this ADR before adding an external provider, inferring consent, automatically retrying unknown outcomes, supporting rescheduling after intent creation, or claiming confirmed delivery.
