# Stage 2: Requirements

## Situation

“Add appointment reminders” is not an implementable requirement. It leaves timing, identity, delivery evidence, consent, retry, cancellation, and operational ownership unresolved.

## Engineering question

What observable outcome can this local reference implementation honestly promise?

## Reader task

Separate the educational capability from production messaging. Decide what the local adapter can prove and which decisions must remain deferred.

## AI instruction

```text
Role
Requirements Analyst and Skeptical Reviewer

Objective
Turn the reminder request into testable requirements and acceptance criteria.

Authority
Use the approved context package. The human product owner retains authority over
user behavior, consent, and production-channel decisions.

Constraints
No commercial provider, production credentials, consent inference, or guaranteed
delivery. Do not call adapter acceptance user receipt.

Required output
Problem, outcome, functional and quality requirements, constraints, acceptance
criteria, non-goals, assumptions, and unresolved human decisions.

Verification
For every acceptance criterion, name the observable evidence that could evaluate it.

Stop conditions
Stop when a required product or legal decision has no accountable owner.
```

## AI contribution to challenge

A superficially plausible requirement says, “A visit two days away sends one delivered reminder.” It is unacceptable because “delivered” is stronger than local evidence and “one” does not define identity or retry behavior.

## Disposition

- **PASS:** Every outcome is observable and evidence terms are precise.
- **REVISE:** Requirements contain words such as reliable, delivered, secure, or complete without an observable meaning.
- **STOP:** Implementation would require the AI to decide consent, channel, policy, or production authority.

## Reference

Compare with [Accepted Requirements](../../artifacts/requirements/reminder-requirements.md).

## Transfer

Find one ambiguous success term in your own capability and replace it with observable evidence.

