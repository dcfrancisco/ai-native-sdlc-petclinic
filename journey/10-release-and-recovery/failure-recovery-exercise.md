# Failure and Recovery Exercise

## Objective

Demonstrate that failure remains observable, retry is bounded, unknown outcomes are preserved, and duplicate processing does not silently create another reminder intent.

## Procedure

1. Start from a clean local database.
2. Start PetClinic with reminder processing enabled.
3. Create a visit exactly two days in the future.
4. Configure or substitute a test gateway that throws a deterministic exception.
5. Process due reminders.
6. Observe one `FAILED` record, one attempt, a masked destination, and a sanitized error type.
7. Remove the deterministic failure.
8. Retry the failed reminder once.
9. Observe `ACCEPTED`, two attempts, and no full destination or secret.
10. Process due reminders again.
11. Confirm that no second reminder record is created.
12. Run the unknown-outcome test and confirm that it produces `UNKNOWN`.
13. Confirm that the UI does not offer ordinary retry for `UNKNOWN`.

## Expected evidence

- Exact commands and configuration
- Focused test result
- Functional observation
- Reminder row before and after retry
- Metrics before and after retry
- No duplicate record
- Sanitized error evidence
- Unknown outcome preserved
- Checks not run and environmental limitations

## Disposition

- **PASS:** Failure, retry, unknown state, masking, and duplicate suppression are all observed.
- **REVISE:** Automated evidence exists but the functional recovery path was not exercised.
- **STOP:** Unknown is converted to success or failure, retry can duplicate an uncertain external effect, or sensitive data appears in evidence.

## Cleanup

Stop the application and local database containers. Do not preserve credentials, unrestricted logs, or database volumes as publication evidence.

