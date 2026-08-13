# Automated Evidence Log: Visit Rate Limiter

Evidence ID: EV-VRL-001  
Spec baseline: SI-VRL-001 (`ai/specs/visit-rate-limiter-invariants.md`)\
Contract: `ai/contracts/visit-rate-limiter-contribution.yaml`\
Generated: 2026-07-28  
Review state: candidate, human acceptance required

## Evidence provenance

- Evidence class: historical execution record.
- Recorded execution date: 2026-07-28.
- Exact source commit and retained raw command output: not recorded.
- Current publication baseline reproduction: `NOT_RUN`.
- Current reproduction limitation: the available environment has no Java
  runtime; MySQL and PostgreSQL limiter behavior also require explicit
  database-specific execution.

The results below preserve what the engineering record reported on 2026-07-28.
They are not evidence that the current publication baseline reran those
commands.

## Diff boundary

- Six planned Java files were created.
- Three planned schema files were modified.
- No dependency, controller, UI, public route, reminder, or build-definition file was changed.
- Existing unrelated workspace edits were not touched.

## Recorded historical execution

| Command or check | Result | Supports | Limit |
| --- | --- | --- | --- |
| `spring-javaformat:apply` | Pass | contribution formatting | Does not prove behavior |
| Maven validate and compile with Java 17 | Pass | formatting, checkstyle, no-HTTP policy, Java/API compatibility | Dependency cache was temporarily placed outside the scan surface |
| `VisitRateLimiterIntegrationTests` | Pass: 8 test invocations | INV-03, INV-04, INV-06, INV-10, INV-12 | H2; service and MockMvc paths |
| `VisitControllerTests` | Pass: 4 tests | INV-14, INV-15 | MVC slice; does not exercise a 429 response |
| Full PetClinic test inventory | 85 pass; 2 PostgreSQL tests initially blocked by host port 5432 | broad regression | Environment conflict, not a product assertion |
| `PostgresIntegrationTests` rerun on host port 55432 | Historical pass: 2 tests | PostgreSQL schema loaded and application started in the recorded run | Raw output and exact source commit were not retained; limiter behavior did not execute |
| `MySqlIntegrationTests` within full run | Historical pass | MySQL schema loaded and application started in the recorded run | Raw output and exact source commit were not retained; limiter behavior did not execute |
| Manual diff-to-contract inspection | Pass | allowed change surface, INV-17 | Requires human confirmation |

## Invariant coverage

Covered by executable tests:

- Threshold: fourth attempt is rejected.
- HTTP contract: the fourth matching POST returns 429, includes `Retry-After`, and returns the bounded response body.
- Boundary: attempt at exactly 60 seconds resets the window.
- Concurrency: exactly three of eight same-pet attempts are allowed, repeated five times.
- Regression: the existing valid, invalid, and non-future visit flows still pass their MVC tests.

Covered by code and schema inspection:

- Matching POST route and trailing-slash pattern.
- Per-pet key.
- UTC `Instant` storage.
- Durable database state.
- Pessimistic pet-row locking.
- H2, MySQL, and PostgreSQL schema definitions.
- No new dependency.
- Missing pets defer to existing controller behavior.

Not yet proven:

- MySQL and PostgreSQL limiter behavior, including lock timing and timestamp
  precision. The historical record reports successful schema-loading tests,
  but the current publication baseline did not reproduce them.
- Reproduction of the historical MySQL and PostgreSQL startup results from the
  current publication baseline.
- Same-pet lock behavior across multiple application instances.
- Different-pet parallelism.
- p95 overhead below 10 ms in a representative environment.
- Behavior during database lock timeout or database outage.

## Edge-case Challenge Matrix

| Boundary | Status | Evidence | Review consequence |
| --- | --- | --- | --- |
| Fourth same-pet attempt | Covered | H2 service and MockMvc tests | Merge input |
| Exactly 60 seconds | Covered | Fixed-`Instant` service test | Merge input |
| 59.999 seconds, locale, and DST | Partial | UTC design inspection; exact pre-boundary case not executed | Add focused evidence |
| Eight simultaneous same-pet decisions | Covered | Five repeated H2 races; exactly three allowed | Does not establish database parity |
| Different-pet isolation | Not proven | No timed parallel execution | Performance gate |
| First limiter row under contention | Partial | Pet-row lock inspection plus H2 race | Database-specific runs required |
| Missing pet | Partial | Pass-through source inspection | Runtime case absent |
| Lock timeout or database outage | Not proven | No failure injection | Operational gate |

## Performance trade-offs

The design favors correctness across instances over minimum latency. Every matching POST obtains a database lock on the pet row and reads or writes one limiter row. That creates a deliberate same-pet serialization point and adds database round trips. Different pets should not share the lock, but this remains unmeasured. INV-20 must not be accepted from the H2 integration test.

## Security and privacy

- The limiter returns the same 429 text for every limited pet and includes no owner or visit data.
- A nonexistent pet bypasses limiter rejection, so the filter does not reveal existence through a distinct limiter response.
- The request URI contains numeric identifiers only; no personal data is added to logging.
- The feature is abuse resistance, not authentication or authorization.
- Counting invalid forms can let an attacker consume another pet's capacity if routes are unauthenticated. Product and security owners must decide whether the current PetClinic trust model is sufficient.

## Reviewer findings

1. **Production-enablement blocker:** representative performance evidence is missing for INV-20.
2. **Deployment finding:** run database-specific limiter tests against MySQL and PostgreSQL.
3. **Policy confirmation:** the production threshold and invalid-form capacity policy remain unresolved.
4. **Operational confirmation:** define lock-timeout observation and alerting expectations.

## Human acceptance

Not recorded. The AI-generated log organizes evidence; it does not approve the change.
