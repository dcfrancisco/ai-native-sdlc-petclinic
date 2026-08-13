# Stage 10: Release and Recovery

## Situation

A technically correct change is not release-ready until operators can detect failure, limit impact, recover safely, and distinguish accepted, failed, and unknown outcomes.

## Engineering question

Does the evidence package justify release of the bounded local reference capability?

## Reader task

Perform the [Failure and Recovery Exercise](failure-recovery-exercise.md). Review the release checklist, rollback plan, runbook, security disposition, and residual limitations.

## AI instruction

```text
Role
Release Evidence Reviewer

Objective
Determine whether the local reminder capability is release-ready within its
explicit educational boundary.

Authority
Requirements, ADR, verification report, independent review, security disposition,
release checklist, rollback plan, and runbook.

Required output
PASS, REVISE, or STOP; evidence for each release criterion; residual limitations;
rollback trigger; monitoring signals; and decisions reserved for the release owner.

Constraints
Do not approve external messaging or production deployment.

Stop conditions
Stop if required evidence is absent, failure cannot be detected, unknown outcomes
cannot be preserved, or rollback and disablement are unverified.
```

## Disposition

- **PASS:** The bounded local capability has complete evidence, recovery, and named limitations.
- **REVISE:** The implementation works but operational evidence or recovery is incomplete.
- **STOP:** The release claim exceeds tested behavior or no accountable release owner exists.

## Reference

Compare with [Release Checklist](../../artifacts/release/release-checklist.md), [Rollback Plan](../../artifacts/release/rollback-plan.md), and [Runbook](../../artifacts/operations/runbook.md).

## Transfer

Name the evidence that would allow an operator in your project to distinguish failure from uncertainty.

