# Stage 6: Implementation

## Situation

The AI contributor receives an approved contract. Its responsibility is to implement within the boundary and report evidence honestly, not to declare the contribution accepted.

## Engineering question

Can the approved package be implemented without changing unauthorized behavior or hiding uncertainty?

## Reader task

Execute the structured instruction in an isolated working copy. Preserve:

- files modified;
- commands run;
- failed attempts;
- assumptions;
- stop conditions reached;
- checks not run;
- residual limitations.

## AI instruction

Use [AI-001](../../ai-assets/ai-instructions/AI-001-reminder-implementation.md). Before execution, confirm that its authority sources match the current repository.

## Human intervention points

Stop the contribution if it requests:

- production credentials;
- provider selection;
- a new owner contact model;
- consent inference;
- distributed scheduling;
- changes outside the approved packages.

## Disposition

- **PASS:** The change is ready for independent verification and its report is complete.
- **REVISE:** The implementation is bounded but evidence or reporting is incomplete.
- **STOP:** Scope, authority, or security boundaries were crossed.

Implementation `PASS` means ready for verification. It does not mean accepted or releasable.

## Reference

Inspect the bounded implementation under `src/main/java/org/springframework/samples/petclinic/reminder/`. Do not assume the source is correct because it is the reference implementation. Stage 8 demonstrates a real defect found after technical checks passed.

## Transfer

Execute one bounded contribution in your project and preserve its unedited report.

