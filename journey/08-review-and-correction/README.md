# Stage 8: Review and Correction

## Situation

The original reminder implementation passed technical checks while using `DELIVERED` to mean that a local adapter accepted a message. It did not contact a provider or establish owner receipt.

This was a real review finding. Technical verification passed, but engineering acceptance had to fail.

## Engineering question

What should happen when implementation evidence is internally valid but the underlying claim is wrong?

## Reader task

Review the original claim:

> A successful local gateway call produces `DELIVERED`.

Decide whether to PASS, REVISE, or STOP before reading the correction record.

## Review disposition

**STOP**

`DELIVERED` exceeds the evidence. The state name would leak into APIs, metrics, UI, tests, and future adapters. Documentation cannot repair an inaccurate domain model.

## Correction

The human owner approved a bounded correction:

- replace `DELIVERED` with `ACCEPTED`;
- define acceptance as adapter acceptance, not owner receipt;
- add `UNKNOWN` for an indeterminate adapter result;
- rename metrics, timestamps, messages, tests, diagrams, and evidence;
- prohibit automatic retry of `UNKNOWN`;
- retain external provider delivery as out of scope.

See [Correction Contract](correction-contract.md) and [Correction Record](correction-record.md).

## Disposition

- **PASS:** The corrected implementation and all evidence use the same defensible semantics.
- **REVISE:** Code is corrected but diagrams, metrics, tests, or documentation retain the stronger claim.
- **STOP:** Any artifact still equates adapter acceptance with delivery or user receipt.

## Reference

Compare with [Independent Review](../../artifacts/reviews/reminder-review.md) and [ADR-001](../../artifacts/decisions/ADR-001-reminder-boundary.md).

## Transfer

Find one status, metric, or API field in your project whose name claims more than its evidence proves.

