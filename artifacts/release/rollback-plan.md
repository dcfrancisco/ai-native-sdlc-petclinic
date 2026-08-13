# Reminder Rollback Plan

**Chapter:** 14

## Local reference rollback

1. Disable scheduled processing with `PETCLINIC_REMINDERS_ENABLED=false` or `petclinic.reminders.enabled=false`.
2. Stop manual processing while preserving reminder records as evidence.
3. Revert application changes through the normal source-control process.
4. Do not drop reminder data until retention and forensic needs are resolved.
5. Verify owner, pet, and visit workflows after rollback.

## Limitations

The reference schema does not include a reversible migration framework because upstream PetClinic initializes educational databases from SQL. A production system requires versioned forward and backward migration decisions.
