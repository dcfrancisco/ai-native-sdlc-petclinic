# Stage 3: Architecture

## Situation

The reminder capability needs an outbound notification boundary, but provider selection is unauthorized and would make the exercise depend on credentials and commercial behavior.

## Engineering question

How should notification be represented without hiding the boundary or overstating evidence?

## Reader task

Evaluate at least three alternatives using the same forces. Decide where the outbound port belongs and what each state is permitted to mean.

## AI instruction

```text
Role
Software Architect

Objective
Propose an architecture decision for the reminder notification boundary.

Authority
Requirements, context package, existing PetClinic architecture, and human-owned
constraints.

Required analysis
Decision question, forces, at least three credible alternatives, consequences,
invariants, deferred decisions, conformance evidence, and supersession triggers.

Constraints
Remain vendor neutral. Do not select a provider. Do not equate local acceptance
with owner receipt.

Stop conditions
Stop if an alternative requires consent, credentials, distributed coordination,
or a product decision not present in the authority sources.
```

## Human review

An ADR is not acceptable merely because it contains a preferred option. Alternatives must be credible, consequences must include costs, and invariants must be testable.

## Disposition

- **PASS:** The decision constrains implementation and names evidence for conformance.
- **REVISE:** The proposal lists patterns without evaluating consequences.
- **STOP:** The proposal silently chooses a provider or invents product policy.

## Reference

Compare with [ADR-001](../../artifacts/decisions/ADR-001-reminder-boundary.md), [Context Diagram](../../artifacts/architecture/context-diagram.mmd), and [Component Diagram](../../artifacts/architecture/component-diagram.mmd).

## Transfer

Record one decision in your project that an AI implementer must not rediscover.

