# Correction Contract: Evidence-Accurate Reminder Outcomes

**Role:** Bounded Correction Implementer  
**Accountable human:** Repository maintainer  
**Evidence classification:** Verified Reference Implementation

## Objective

Correct the reminder outcome model so every state, metric, interface, test, diagram, and instruction describes only what the available evidence establishes.

## Authority

- Accepted reminder requirements
- ADR-001
- Independent reminder review
- Existing repository conventions

## Permitted scope

- Reminder package
- Reminder schemas
- Reminder tests and UI wording
- Companion artifacts, diagrams, and journey documentation
- Direct manuscript references to the PetClinic state model

## Required changes

- Rename local success from `DELIVERED` to `ACCEPTED`.
- Define `ACCEPTED` as adapter acceptance, not provider delivery or owner receipt.
- Add `UNKNOWN` for indeterminate adapter outcomes.
- Keep definite exceptions as `FAILED`.
- Permit manual retry only from `FAILED`.
- Update metrics and timestamps to use accepted terminology.
- Preserve destination masking.

## Exclusions

- External provider integration
- Consent management
- Cancellation and rescheduling implementation
- Distributed scheduling
- Production deployment

## Verification

- Focused reminder tests
- Full regression tests
- Database-profile checks
- Docker startup and functional reminder check
- Search for stale PetClinic `DELIVERED` semantics

## Stop conditions

Stop if the correction requires a provider decision, destructive migration strategy, production secret, public API compatibility decision, or behavior outside the approved reminder boundary.

