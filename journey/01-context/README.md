# Stage 1: Context

## Situation

PetClinic records future visits but has no reminder capability. Before asking AI to propose changes, determine which repository files, commands, architecture sources, constraints, and unknown decisions govern the work.

## Engineering question

What context is authoritative and sufficient for a bounded reminder contribution?

## Reader task

Inspect the repository and prepare a context package without opening the accepted reference. Identify:

- the build, test, startup, and Docker commands;
- affected domain and UI areas;
- database profiles;
- architecture and license constraints;
- decisions that the repository cannot authorize.

## AI instruction

```text
Role
Repository Context Analyst

Objective
Construct a context package for an appointment-reminder capability.

Authority
Treat repository source, build configuration, existing tests, and accepted
architecture decisions as authoritative. Treat chat history as non-authoritative.

Scope
Inspect only. Do not modify files.

Required output
Current state, authoritative sources, relevant boundaries, build and runtime
commands, contradictions, unknown decisions, and missing evidence.

Verification
Cite every repository path used. Distinguish observed facts from inferences.

Stop conditions
Stop if required source is unavailable or if two authoritative sources conflict.
```

## Human review

Reject any package that invents product policy, treats documentation as current without checking source, or omits the existing verification commands.

## Disposition

- **PASS:** Another engineer can reproduce current state and find every authority source.
- **REVISE:** Sources are listed but precedence, freshness, or boundaries are unclear.
- **STOP:** Required source is missing or authoritative sources conflict.

## Reference

Compare with [Context Package](../../artifacts/context/context-package.md) and [Pinned Baseline](../../docs/baseline.md).

## Transfer

Create the equivalent package for one bounded capability in your software.

