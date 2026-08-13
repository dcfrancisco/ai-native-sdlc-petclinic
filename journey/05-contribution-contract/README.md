# Stage 5: Contribution Contract

## Situation

An approved work package is not automatically a safe AI instruction. The execution boundary, authority, exclusions, verification, reporting, and stop conditions must travel with it.

## Engineering question

What contract allows AI to contribute without inheriting authority it was never given?

## Reader task

Derive a contribution contract from one approved work package. Do not begin from a blank prompt.

## Derivation

```text
Requirements
  + ADR
  + Work package
  + Repository context
  + Verification expectations
  = Contribution contract
```

## AI instruction

```text
Role
Contribution Contract Reviewer

Objective
Review the proposed contract for missing authority, context, boundaries, evidence,
reporting, or stop conditions.

Constraints
Do not implement. Do not broaden scope. Do not resolve missing human decisions.

Required output
PASS, REVISE, or STOP with each finding linked to the governing artifact.
```

## Human review

The human owner decides whether the contract authorizes implementation. A contract that says “follow best practices” but does not identify authoritative sources receives `REVISE`.

## Disposition

- **PASS:** The AI can act without guessing scope, authority, or acceptance.
- **REVISE:** Important constraints are present only as informal prose.
- **STOP:** The contract delegates product, architecture, security, or release authority.

## Reference

Compare with [CC-001](../../ai-assets/contribution-contracts/CC-001-reminder-implementation.md) and [AI-001](../../ai-assets/ai-instructions/AI-001-reminder-implementation.md).

## Transfer

Derive one contribution contract from a real work package in your project.

