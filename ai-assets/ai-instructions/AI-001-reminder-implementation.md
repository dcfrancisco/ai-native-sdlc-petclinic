# AI-001: Reminder Implementation Instruction

**Book chapter:** 8, executed in Chapter 9

## Role

Implementation engineer for the bounded Spring PetClinic appointment-reminder work packages.

## Objective

Implement the accepted reminder eligibility, persistence, local delivery, tracking, operator recovery, configuration, and metrics. Produce tests and verification evidence without making production provider or consent decisions.

## Authority

- `artifacts/requirements/reminder-requirements.md`
- `artifacts/decisions/ADR-001-reminder-boundary.md`
- `artifacts/work-packages/reminder-work-packages.md`
- `ai-assets/contribution-contracts/CC-001-reminder-implementation.md`
- `docs/baseline.md`
- existing repository conventions

## Context and current state

PetClinic stores owners, pets, and future visits. Owners have telephone numbers but no email field. The upstream baseline has no reminder capability. The accepted design keeps reminder behavior in a bounded package and uses a local notification adapter.

## Scope

- reminder record and repository;
- eligibility and idempotent processing;
- notification port and local adapter;
- delivery state and retry;
- manual and scheduled triggers;
- operator history page;
- metrics and configuration;
- H2, PostgreSQL, and MySQL schemas;
- unit, MVC, and integration tests;
- required technical documentation.

## Exclusions

- commercial email, SMS, or push integration;
- production credentials;
- owner schema changes;
- consent policy;
- distributed scheduling or locking;
- unrelated refactoring;
- deployment acceptance.

## Constraints and invariants

- Process only visits exactly inside the configured lead-day window.
- Do not create more than one reminder record for a visit.
- Preserve delivery success and failure states.
- Mask destination data in persistence, views, and logs.
- Sanitize recorded errors.
- Preserve existing behavior and Java 17 compatibility.
- Do not claim production readiness.

## Required deliverables

- bounded source and schema changes;
- focused and integration tests;
- documentation updates;
- verification report with limitations;
- human functional procedure.

## Acceptance criteria

Use the criteria in the accepted requirements without weakening or extending them.

## Verification

1. Inspect current source and tests before changing them.
2. Run focused tests during implementation.
3. Run `./mvnw test` before reporting completion.
4. Run database-backed checks when Docker is available.
5. Start the application and inspect `/reminders` and reminder metrics.
6. Report passed, failed, blocked, and not-run checks separately.
7. Report modified files and concise command results.

## Functional checks for the reader

Provide steps to book a visit two days ahead, process reminders, observe delivery, process again, and confirm no duplicate. Include expected results.

## Docker or environment checks

Use the repository scripts and configurable host ports. A container starting does not prove reminder behavior. Record database profile, observed behavior, and cleanup.

## Reporting requirements

Summarize behavior, files changed, commands, results, limitations, and decisions returned to the human owner. Do not label the work accepted.

## Stop conditions

Stop if required authority is missing or conflicting, baseline failures are unrelated to the contribution, credentials are required, or the work crosses an excluded boundary.
