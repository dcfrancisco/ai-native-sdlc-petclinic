# Reminder Operations Runbook

**Chapter:** 14
**Status:** Reference runbook

## Signals

- `petclinic.reminders.created`;
- `petclinic.reminders.accepted`;
- `petclinic.reminders.failed`;
- `petclinic.reminders.unknown`;
- failed reminder rows;
- scheduler and local-adapter logs;
- database and application health.

## Manual processing

Open `/reminders` and select **Process due reminders**. Record created, accepted, failed, unknown, and existing counts.

## Retry

Retry only a failed reminder after the cause is understood. Confirm attempt count, status, and sanitized error state after retry. Do not automatically retry `UNKNOWN`; reconcile the outcome first because the external effect may have occurred.

## Duplicate concern

Check for more than one reminder row per visit. The database constraint should prevent this. A constraint exception under concurrent processing requires incident analysis and a scheduling decision, not blind retry.

## Disable processing

Set `petclinic.reminders.enabled=false` and restart. This prevents service processing but does not remove the scheduler invocation or historical records.

## Incident record

Capture timeline, environment, configuration, affected visits, commands, logs with private data removed, recovery action, evidence, residual impact, and follow-up owner.

## Escalation

Escalate when failures involve contact consent, potential data exposure, repeated delivery, missing visits, database integrity, unauthorized processing, or a production provider.
