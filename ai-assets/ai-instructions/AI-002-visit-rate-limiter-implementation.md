# AI-002: Visit Rate-Limiter Implementation Instruction

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Contract:** CC-VRL-001  
**Work Package:** WP-VRL-001  
**Plan Receipt:** PR-VRL-001  
**Status:** Reference instruction; execution results belong in the evidence artifacts

## Role

Implementation contributor for the bounded per-pet visit-booking rate limiter.

## Objective

Implement PR-VRL-001 exactly within the authority of CP-VRL-001, ADR-004,
SI-VRL-001, WP-VRL-001, and CC-VRL-001. Co-generate the authorized focused
integration tests without changing the accepted product or architecture rules.

## Authority and context

Read the Context Package, Spec Invariants, ADR-004, Work Package, Plan Receipt,
existing visit controller/domain/schema/test files, and the Contribution
Contract before editing. The contract is the execution boundary; this
instruction does not add authority.

## Permitted changes

Create only the six planned Java/test files and modify only the three database
schema files named by PR-VRL-001 and CC-VRL-001. Preserve H2, MySQL, and
PostgreSQL schema parity and existing visit-controller behavior.

## Required behavior

- count every matching visit-booking POST before form validation;
- key capacity by `petId`;
- allow three attempts in a fixed 60-second UTC window;
- return 429 and whole-second `Retry-After` after capacity is exhausted;
- use database state and the existing pet row as the serialization point;
- preserve missing-pet, validation, redirect, and flash behavior;
- do not introduce dependencies, infrastructure, endpoints, caches, or UI.

## Verification and reporting

Run only the commands authorized by CP-VRL-001/CC-VRL-001, including formatting,
focused limiter tests, and visit-controller regression tests. Report changed
files, invariant coverage, exact commands, results, deviations, and residual
risk. Distinguish passed, failed, blocked, and not-run checks.

Do not claim MySQL, PostgreSQL, multi-instance, representative p95, Docker, or
production evidence unless those checks are executed and retained. Do not
claim acceptance or release readiness.

## Stop conditions

Stop and return to the human owner if an authoritative artifact conflicts, a
required edit falls outside the allowlist, a migration decision is required,
verification fails without explanation, or security/privacy/destructive work is
discovered.

## Human decision boundary

The human owner reviews the diff and evidence. AI may not approve the
contribution, accept residual risk, promote ADR-004 for production, or widen
the case.
