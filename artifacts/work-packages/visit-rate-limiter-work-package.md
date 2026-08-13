# Work Package: Per-Pet Visit Booking Rate Limiter

**Work Package:** WP-VRL-001  
**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Approved for bounded implementation preparation  
**Related Plan Receipt:** PR-VRL-001  
**Related Contract:** CC-VRL-001

## Role of this artifact

This Work Package is the bounded implementation unit. It is distinct from the
Plan Receipt. The Work Package states the scope, exclusions, dependencies,
deliverables, and evidence boundary. The Plan Receipt records the reviewed
observation and ordered modification plan derived from the Context Package,
Spec Invariants, and ADR-004. The receipt does not replace this boundary.

No separate historical execution event is claimed by adding this indexable
artifact; it makes explicit the work-package role taught by the manuscript and
links it to the existing approved receipt.

## Authority

- `../../ai/specs/visit-rate-limiter-invariants.md` — SI-VRL-001
- `../../ai/context/visit-rate-limiter-context-package.yaml` — CP-VRL-001
- `../../ai/memory/adr-004-visit-rate-limiting.md` — ADR-004
- `../../ai/plans/visit-rate-limiter-plan-receipt.md` — PR-VRL-001

## Bounded outcome

Implement the database-backed per-pet visit-booking limiter for the matching
POST route while preserving existing controller behavior, schema parity, and
the accepted invariants. The implementation may create the six files and
modify the three schema files listed in PR-VRL-001.

## In scope

- limiter entity, repository, pet-row lock repository, service, and filter;
- H2, MySQL, and PostgreSQL schema definitions;
- focused limiter integration tests and existing visit-controller regression;
- formatting, compile, focused tests, and authorized reporting;
- explicit recording of deferred database, multi-instance, performance, and
  operational evidence.

## Out of scope

- changing threshold, window, or attempt semantics;
- editing `VisitController`, owner/pet/visit behavior, UI, reminder code, or
  `pom.xml`;
- new dependencies, endpoint, cache, external service, or credentials;
- migration strategy for an already deployed schema;
- production acceptance, release approval, or unresolved policy decisions.

## Evidence and stop conditions

The contributor must report passed, failed, blocked, and not-run checks. It
must stop on authority conflict, an out-of-scope edit, required migration work,
unexplained verification failure, or a discovered security/privacy boundary.
MySQL, PostgreSQL limiter behavior, representative p95 performance,
multi-instance behavior, and database-failure behavior remain unverified unless
they are actually executed and recorded.

## Handoff

The implementation instruction is derived from this Work Package and the
approved Plan Receipt. The Plan Receipt remains the planning output; this Work
Package remains the contribution boundary.
