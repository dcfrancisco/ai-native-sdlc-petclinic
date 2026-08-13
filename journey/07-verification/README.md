# Stage 7: Verification

## Situation

AI-generated implementation and AI-generated tests can share the same mistaken assumption. Verification must combine independent oracles and functional evidence.

## Engineering question

What evidence is sufficient to decide whether the contribution satisfies its accepted criteria?

## Reader task

Collect separate evidence for:

- focused tests;
- regression tests;
- database profiles;
- application startup;
- browser behavior;
- Docker execution;
- failed and not-run checks;
- acceptance limitations.

## AI instruction

```text
Role
Verification Engineer

Objective
Evaluate each acceptance criterion using independent evidence.

Authority
Accepted requirements and verification commands. The implementation report is
input, not proof.

Required output
Criterion-to-evidence mapping, exact commands, concise results, failures, not-run
checks, limitations, and PASS, REVISE, or STOP recommendation.

Constraints
Do not silently repair code. Do not convert environment failures into passes.

Stop conditions
Stop acceptance when required evidence is missing, contradictory, or derived only
from the implementation's own assumptions.
```

## Disposition

- **PASS:** Every in-scope criterion has relevant evidence and limitations are explicit.
- **REVISE:** Behavior may be correct, but required evidence is incomplete.
- **STOP:** Evidence contradicts the claim or a blocking criterion fails.

## Reference

Compare with [Verification Report](../../artifacts/evidence/verification-report.md).

## Transfer

Identify one independent oracle in your project that was not generated from the implementation.

