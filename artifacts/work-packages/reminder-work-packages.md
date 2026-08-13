# Reminder Work Packages

**Chapter:** 7  
**Status:** Accepted

| ID | Objective | Dependencies | Verification |
|---|---|---|---|
| WP-01 | Establish reminder schema and domain record | Requirements, ADR-001 | Schema initialization and repository test |
| WP-02 | Implement eligibility and idempotent creation | WP-01 | Unit and integration tests |
| WP-03 | Add notification port and local adapter | ADR-001, WP-02 | Accepted, failed, unknown, and masking tests |
| WP-04 | Add manual processing, history, and retry UI | WP-02, WP-03 | MVC and browser checks |
| WP-05 | Add scheduled trigger and configuration | WP-02 | Configuration and startup checks |
| WP-06 | Add operational metrics and runbook | WP-03 to WP-05 | Actuator and recovery checks |
| WP-07 | Complete database, security, governance, and release evidence | WP-01 to WP-06 | Evidence envelope review |

## Risks

- duplicate records under concurrent processing;
- contact data leakage;
- scheduler overlap in a multi-instance deployment;
- misleading adapter-outcome semantics;
- database type differences;
- environmental failures misclassified as code failures.

## Readiness rule

Implementation stops if it requires production credentials, a new owner contact field, provider selection, consent inference, public API change, automatic retry of an unknown outcome, or distributed scheduling architecture.
