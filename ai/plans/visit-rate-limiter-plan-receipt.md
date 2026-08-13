# Plan Receipt: Per-Pet Visit Booking Rate Limiter

Receipt ID: PR-VRL-001  
Context Package: CP-VRL-001  
Spec: SI-VRL-001 (`ai/specs/visit-rate-limiter-invariants.md`)\
Architecture decision: ADR-004, approved for bounded implementation\
State: approved for bounded implementation

## Repository observations

1. `VisitController` handles the target POST route and saves the owning aggregate through `OwnerRepository`.
2. `visits.visit_date` is a business date, not a request timestamp. It cannot safely represent a 60-second window.
3. The application supports H2, MySQL, and PostgreSQL with separate schema files.
4. There is no dedicated `PetRepository`; a focused repository is needed to lock the existing pet row.
5. Spring Data JPA is already present. No new dependency is required.
6. ADR-004 selects persisted limiter state and the existing pet row as the
   serialization point before this plan decomposes the work.

## Modification sequence

1. Add `visit_rate_limits` to all three schemas with `pet_id` as primary and foreign key, `window_started_at`, and `attempt_count`.
2. Add a small JPA entity and repository for durable limiter state.
3. Add a focused `PetRepository.findByIdForUpdate` query using `PESSIMISTIC_WRITE`.
4. Add a transactional `VisitRateLimiter.acquire(petId, now)` operation:
   - allow a missing pet and defer not-found behavior;
   - create the first window row;
   - reset at the exact 60-second boundary;
   - reject after three attempts with remaining duration;
   - serialize same-pet decisions through the pet-row lock.
5. Add `VisitRateLimiterFilter` for only the matching POST route. On rejection, return 429 and `Retry-After`; otherwise continue the existing chain.
6. Add Spring integration tests for threshold, exact boundary, and eight simultaneous attempts repeated five times.
7. Run formatting, focused limiter tests, and existing visit-controller regression tests.
8. Record unexecuted database-parity and performance checks as gaps rather than claims.

## Expected files

New:

- `VisitRateLimit.java`
- `VisitRateLimitRepository.java`
- `PetRepository.java`
- `VisitRateLimiter.java`
- `VisitRateLimiterFilter.java`
- `VisitRateLimiterIntegrationTests.java`

Modified:

- `src/main/resources/db/h2/schema.sql`
- `src/main/resources/db/mysql/schema.sql`
- `src/main/resources/db/postgres/schema.sql`

## Stop conditions

Stop and return to the owner if implementation requires a new dependency, a public API change beyond 429, a new product rule, a migration strategy for an already deployed schema, or weakening any accepted invariant.

## Decisions withheld

Before approval, the accountable engineer must accept the educational slice's
threshold, window, attempt semantics, fixed-window algorithm, and database-locking
trade-off.

Production threshold policy, invalid-form policy, manual reset, a future rolling
window, the representative load profile, multi-instance evidence, and
database-specific concurrency evidence remain unresolved or unexecuted. Approval
of this receipt does not resolve or prove them. It also does not promote ADR-004
from bounded implementation authority to production acceptance.

## Plan Challenge Matrix disposition

| Concern | Disposition | Gate effect |
| --- | --- | --- |
| `visit_date` used as request time | rejected; dedicated UTC instant required | binding for implementation |
| first limiter row has nothing to lock | lock the existing pet row first | binding architectural input |
| missing pet changes response | preserve controller behavior | binding security and compatibility input |
| database failure silently allows | prohibited by INV-19 | requires failure evidence before production |
| H2 evidence generalized to other databases | prohibited | database-specific behavior remains unexecuted |
| p95 below 10 ms | target has no accepted load profile | cannot support a release claim |

## Engineer approval

Decision: **APPROVED FOR CC-VRL-001 PREPARATION**  
Approved scope: educational companion implementation only  
Accountable role: repository owner or delegated application engineer  
Approval date: 2026-07-28  
Conditions:

- CC-VRL-001 must preserve this receipt's file surface, sequence, and stop conditions.
- Any change to a binding Spec Invariant returns to its accountable owner.
- Unresolved production policy and unexecuted evidence remain outside approval.
- Code generation must not begin from a modified or unsigned copy of this receipt.
