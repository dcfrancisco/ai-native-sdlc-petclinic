# Context Package: Appointment Reminders

**Chapter:** 4  
**Status:** Accepted for the reference implementation  
**Accountable owner:** Human maintainer  
**AI contribution:** Repository inspection and synthesis

## Objective

Provide the minimum authoritative context needed to frame reminder work without allowing implementation assumptions to become product decisions.

## Authority sources

| Source | Authority |
|---|---|
| `docs/baseline.md` | Pinned version and environment facts |
| `Owner`, `Pet`, and `Visit` domain classes | Implemented owner, pet, and visit behavior |
| Database schemas | Implemented persistence constraints |
| Existing tests | Current verified behavior |
| `docker-compose.yml` | Local MySQL and PostgreSQL dependencies |
| Book Chapter 4 | Context method and reader exercise |

## Current state

- Owners provide telephone numbers.
- Pets have visits with dates and descriptions.
- Future visits can be booked.
- No reminder record, eligibility policy, notification boundary, delivery state, retry path, or reminder-specific metric exists upstream.
- Actuator, H2, PostgreSQL, and MySQL are available.

## Relevant boundaries

- `owner`: owners, pets, and visits;
- `reminder`: new bounded reminder capability;
- database schemas for H2, PostgreSQL, and MySQL;
- Thymeleaf operator interface;
- Actuator metrics;
- local Docker environment.

## Unknown or human-owned decisions

- reminder window;
- eligible visit states;
- contact consent;
- production delivery channel and provider;
- retry policy;
- retention and deletion policy;
- production scheduler topology;
- service-level objectives.

## Disclosure constraints

Do not expose full telephone numbers in reminder records, logs, or screenshots. Do not introduce credentials. Do not infer consent from the existence of a telephone field.

## Baseline verification

See `artifacts/evidence/verification-report.md`. Environmental failures must be separated from source defects.
