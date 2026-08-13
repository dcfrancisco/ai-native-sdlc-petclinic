# Stage 4: Planning

## Situation

AI can generate the entire reminder feature in one change. That does not make the change reviewable or safe.

## Engineering question

How should the work be divided so each contribution can be reviewed, verified, and stopped independently?

## Reader task

Create work packages derived from requirements and ADR-001. Include dependencies, risks, verification, and stop conditions.

## AI instruction

```text
Role
Engineering Planner

Objective
Decompose the accepted reminder capability into reviewable work packages.

Authority
Accepted requirements, ADR-001, repository context, and available verification
capacity.

Constraints
Each package must preserve architecture boundaries and produce independently
reviewable evidence. Do not combine unrelated refactoring.

Required output
Package objective, inputs, permitted scope, dependencies, risks, acceptance
evidence, and stop conditions.

Verification
Explain why each package fits within human review capacity.
```

## Human review

Reject a package named “Implement reminder feature” that spans schema, domain behavior, UI, scheduling, metrics, security, and release evidence without intermediate review points.

## Disposition

- **PASS:** One package can be assigned without asking the implementer to redesign the capability.
- **REVISE:** The scope is technically coherent but too large to review confidently.
- **STOP:** Required architecture or product decisions remain unresolved.

## Reference

Compare with [Reminder Work Packages](../../artifacts/work-packages/reminder-work-packages.md).

## Transfer

Choose one package from your project and explain why its size matches review capacity.

