# ADR-004: Persist and Serialize Per-Pet Visit Booking Limits

Status: accepted for the educational companion scope  
Date: 2026-07-28  
Decision owners: product owner, application architect, service owner  
Related artifacts: SI-VRL-001, CP-VRL-001, PR-VRL-001, CC-VRL-001,
EV-VRL-001, VM-VRL-001

Decision history:

- approved for bounded implementation before PR-VRL-001;
- promoted to accepted for the educational companion scope after Review and
  Verify preserved the limitations recorded below.

## Context

Spring PetClinic accepts visit bookings through `POST /owners/{ownerId}/pets/{petId}/visits/new`. Repeated requests can create spam bookings. The application may run more than one instance and supports H2, MySQL, and PostgreSQL. The existing `visits.visit_date` field represents the scheduled business date, not request time.

The accepted product rule for this candidate is three POST attempts per existing pet in a fixed 60-second window. Every matching POST counts before form validation. The fourth attempt returns 429 with `Retry-After`.

## Decision

Persist one rate-limit row per active pet window in `visit_rate_limits`, keyed by `pet_id`, with a UTC `window_started_at` instant and `attempt_count`.

Before reading or changing that row, acquire a pessimistic write lock on the existing pet row. This serializes same-pet decisions, including creation of the first rate-limit row, without an in-memory lock or an external service.

Apply the decision in a `OncePerRequestFilter` limited to the visit-booking POST route. Missing pets bypass the rate-limit decision and continue to the controller's existing error path.

## Rationale

- Database state survives restarts and is shared across application instances.
- Locking the existing pet row avoids a race when two requests both try to create the first limiter row.
- UTC instants avoid browser timezone, locale, and daylight-saving ambiguity.
- A filter can reject abusive request volume before validation and aggregate persistence.
- The design uses dependencies already present in PetClinic.

## Alternatives considered

### In-memory concurrent map

Rejected because each application instance would have independent capacity, restarts would reset windows, and keys would require eviction policy.

### Count recent visits

Rejected because visits have a business date but no booking-request timestamp, invalid submissions would be invisible, and concurrent count-then-insert remains racy.

### Redis or a rate-limit library

Deferred because the candidate contract forbids new dependencies and infrastructure. This may become preferable if database lock latency or scale violates the performance target.

### Rolling window

Deferred. It reduces fixed-window boundary bursts but adds state and query complexity not authorized by the accepted product rule.

## Consequences

Positive:

- No more than three same-pet decisions can be allowed in a window when database locking behaves as specified.
- The design uses shared database state so multi-instance consistency can be
  tested without introducing another service; that behavior remains unverified.
- State and decisions are inspectable.

Negative:

- Each matching POST adds a pet-row lock and limiter-row database access.
- Same-pet requests serialize by design.
- Invalid forms consume capacity.
- Database availability and lock timeouts affect booking availability.
- Schema migration and retention require operational ownership.

## Security and governance

- The rejection response contains no owner contact data, visit description, or pet details.
- Missing pets continue to existing controller behavior so the limiter does not create a new pet-enumeration response.
- The limiter is abuse resistance, not authentication or authorization. In an unauthenticated deployment, a caller can consume another pet's capacity.
- No new runtime dependency, cache, endpoint, or external service is authorized by this decision.
- An AI contributor may identify gaps or propose supersession but cannot approve residual risk or silently broaden the decision.

## Verification

Historical H2 integration evidence recorded on 2026-07-28:

- fourth attempt rejection: pass;
- fourth matching POST returns 429 with `Retry-After`: pass;
- exact 60-second reset: pass;
- eight concurrent attempts, exactly three allowed, repeated five times: pass;
- four existing visit-controller regression tests: pass.
- existing MySQL application integration test with the new schema: recorded
  pass;
- existing PostgreSQL application integration tests with the new schema:
  recorded pass on host port 55432 after the default host port was found
  occupied.

The historical record does not retain the exact source commit or raw command
output. These results were not reproduced from the current publication
baseline and therefore do not establish current-release database evidence.

Not yet verified:

- rejected HTTP requests do not persist a visit, asserted independently;
- 59.999-second, locale, and daylight-saving boundary cases;
- missing-pet behavior through a dedicated runtime case;
- different-pet parallelism;
- database lock-timeout and outage behavior;
- MySQL and PostgreSQL limiter threshold and runtime locking;
- multi-instance behavior;
- p95 limiter overhead below 10 ms.

The historical record reports that MySQL and PostgreSQL application tests
loaded the changed schemas; absent the exact commit and raw output, that record
does not establish current-release schema evidence or limiter behavior.
Production enablement remains blocked until the required evidence is accepted.
Merge and deployment dispositions remain separate human decisions.

## Unresolved decisions

- Whether three attempts per 60 seconds is the desired production policy.
- Whether invalid form submissions should consume capacity in production.
- Whether operators require a manual reset endpoint.
- Whether a rolling window should supersede the fixed window.
- Which representative load profile and database latency define INV-20.

These decisions require product, security, or operational authority. They are
not defaults for future AI sessions.

## Supersession rule

Future AI sessions must treat this ADR as authoritative until a newer accepted ADR explicitly supersedes it. A session may propose a different algorithm or store, but must not implement one within a contribution contract that cites ADR-004.
