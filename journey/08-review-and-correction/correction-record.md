# Correction Record

**Date:** 2026-07-26  
**Provenance:** Actual publication-review finding followed by an AI-assisted bounded correction  
**Status:** Automated verification complete; functional recovery replay pending

## Original condition

The implementation used:

- `ReminderStatus.DELIVERED`;
- `delivered_at`;
- `petclinic.reminders.delivered`;
- UI and evidence language describing a local adapter result as delivered.

Automated tests passed because the tests encoded the same assumption.

## Review finding

The local adapter did not contact a provider or establish owner receipt. `DELIVERED` therefore claimed more than its evidence proved.

## Human decision

Acceptance was withheld. The owner approved the bounded correction contract and retained external messaging as out of scope.

## AI contribution

The AI contributor changed the outcome model to:

- `PENDING`;
- `ACCEPTED`;
- `FAILED`;
- `UNKNOWN`.

It also updated the gateway result, metrics, timestamps, UI messages, database schemas, tests, ADR, requirements, and teaching material.

## Verification status

Command executed:

```bash
POSTGRES_PORT=55432 ./mvnw -Dmaven.repo.local=/tmp/ai-native-sdlc-petclinic-m2 test
```

Results:

- 78 tests run;
- 0 failures;
- 0 errors;
- 2 skipped;
- 9 reminder service, controller, and H2 integration tests passed;
- Java formatting passed;
- Checkstyle reported 0 violations.

Docker was unavailable to Testcontainers. The PostgreSQL integration test did
not execute, and two MySQL integration tests were skipped. The browser,
container, and failure-recovery workflows were not rerun after the correction.

## Disposition

**REVISE**

The automated evidence supports the corrected semantics within the H2 reference
scope. The correction cannot receive `PASS` until the functional recovery
exercise is executed against the corrected model and the resulting evidence is
reviewed. The not-run checks remain visible rather than being inferred from
older evidence.
