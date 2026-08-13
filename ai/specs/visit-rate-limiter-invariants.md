# Spec Invariants: Per-Pet Visit Booking Rate Limit

Spec ID: SI-VRL-001\
Status: accepted for the educational companion scope  
Feature: rate-limit visit-booking POST attempts per pet  
Policy owner: product and engineering owner  
Implementation baseline: Spring PetClinic, Java 17, Spring Boot 4.1

## Behavioral invariants

- **INV-01 Scope:** The limiter applies only to `POST /owners/{ownerId}/pets/{petId}/visits/new`, including the same path with a trailing slash.
- **INV-02 Identity:** The rate-limit key is the numeric `petId` from the route. `ownerId`, client IP, session, and visit date do not alter the key.
- **INV-03 Threshold:** The first three POST attempts for an existing pet in one fixed 60-second window are allowed. The fourth and later attempts in that window are rejected.
- **INV-04 Rejection contract:** A rejected request returns HTTP `429 Too Many Requests`, a whole-second `Retry-After` header of at least `1`, and no visit is persisted.
- **INV-05 Attempt meaning:** Every matching POST attempt counts before form validation. Invalid forms therefore consume capacity. GET requests and unrelated POST routes do not.
- **INV-06 Window boundary:** An attempt at exactly `window_started_at + 60 seconds` starts a new window and is allowed.
- **INV-07 Missing pet:** A nonexistent `petId` is not rate-limit rejected. The existing controller remains responsible for its current not-found behavior, preventing the limiter from becoming a pet-enumeration oracle.

## Time and persistence invariants

- **INV-08 Time basis:** Window timestamps are stored and compared as UTC instants. Server locale, browser timezone, daylight-saving transitions, and the requested visit date do not affect the window.
- **INV-09 Durable state:** Rate-limit state is database-backed so application restarts and requests routed to different application instances do not reset the active window.
- **INV-10 Per-pet serialization:** Concurrent limiter decisions for the same existing pet acquire a pessimistic database lock on that pet row before reading or writing the rate-limit row.
- **INV-11 Isolation:** Locks for one pet do not intentionally serialize attempts for a different pet.
- **INV-12 Atomic capacity:** Under concurrent requests for one pet and one window, no more than three decisions return allowed.
- **INV-13 Referential integrity:** Each rate-limit row references one existing pet and is deleted or migrated safely if pet deletion behavior is later introduced.

## Compatibility and operational invariants

- **INV-14 Existing success path:** An allowed, valid visit booking preserves the existing redirect and flash-message behavior.
- **INV-15 Existing validation:** The limiter does not change visit-date or description validation; it only decides whether the request may proceed.
- **INV-16 Database support:** Equivalent schema behavior is maintained for H2, MySQL, and PostgreSQL.
- **INV-17 Dependency boundary:** No new runtime library or external rate-limit service is introduced.
- **INV-18 Privacy:** Logs and responses must not include owner contact data, visit descriptions, or other personal data.
- **INV-19 Failure policy:** A database failure is not silently converted into “allowed.” Existing transaction and error handling surface the failure for observation.
- **INV-20 Performance target:** Added limiter decision time should remain below 10 ms at p95 under the agreed representative load profile. This is a release target, not proven by unit or in-memory integration tests.

## Explicitly unresolved

- Whether three attempts per 60 seconds is the desired production policy.
- Whether invalid form submissions should consume capacity.
- Whether operators require a manual reset endpoint.
- Whether the fixed-window algorithm should later become a rolling window.
- The representative production load profile and database latency used to accept INV-20.

These items require product or operational authority. An AI contributor must not silently choose different answers while implementing the accepted slice.

## Evidence not yet executed

- Multi-instance enforcement.
- MySQL and PostgreSQL limiter-specific concurrency.
- Representative p95 limiter latency under an accepted load profile.

These gaps remain unverified until corresponding evidence runs. Schema loading or
H2 integration evidence must not be reported as proof of them.
