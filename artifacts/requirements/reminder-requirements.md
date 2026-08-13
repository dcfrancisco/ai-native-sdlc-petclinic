# Appointment Reminder Requirements

**Chapter:** 5  
**Status:** Accepted for the local reference implementation
**Evidence classification:** Verified Reference Implementation

## Problem

Pet owners may overlook scheduled visits because the application records appointments but does not create or track reminder delivery.

## Outcome

An operator can process visits that enter a configured reminder window and observe one durable local-adapter outcome per visit.

## Functional requirements

- R1: A visit dated exactly the configured lead days from processing is eligible.
- R2: Processing creates at most one reminder record per visit.
- R3: The local adapter records `ACCEPTED` when it accepts a reminder without contacting an external provider.
- R4: Delivery failure remains visible and retryable.
- R5: An indeterminate adapter result remains `UNKNOWN` and is not described as delivery, failure, or user receipt.
- R6: Reminder history displays masked destination data, status, attempts, and sanitized error information.
- R7: Scheduled and manual processing use the same eligibility and adapter service.
- R8: Created, accepted, failed, and unknown outcomes emit metrics.

## Quality requirements

- QR1: Existing owner, pet, visit, and veterinarian behavior remains compatible.
- QR2: Reprocessing is idempotent for a visit.
- QR3: Full telephone numbers do not appear in reminder persistence or delivery logs.
- QR4: H2, PostgreSQL, and MySQL schemas support the reminder record.
- QR5: The capability is testable without production credentials or network delivery.

## Constraints

- Use the existing telephone field only as local adapter input.
- Do not claim that a telephone number establishes consent.
- Do not add email, SMS, or push provider integration.
- Do not introduce distributed locking or production scheduling infrastructure.
- Preserve Java 17 source compatibility and existing build conventions.

## Acceptance criteria

- A visit two days in the future produces one reminder accepted by the local adapter under default configuration.
- A visit outside the window produces none.
- A second run produces no duplicate.
- A delivery exception produces a failed record with a sanitized error.
- An indeterminate adapter result produces an unknown record that requires reconciliation rather than automatic retry.
- A failed record can be retried.
- Reminder metrics are available through Actuator.
- Focused, regression, database, startup, and human functional checks are reported separately.

## Non-goals

- production messaging;
- consent management;
- multi-region scheduling;
- guaranteed delivery;
- evidence of owner receipt;
- reminder cancellation after visit cancellation;
- rescheduling after a reminder intent has been recorded;
- production retention policy.

## Open production decisions

The production channel, consent evidence, schedule ownership, retry backoff, dead-letter handling, data retention, audit requirements, and service-level objectives require accountable owners outside this reference implementation.
