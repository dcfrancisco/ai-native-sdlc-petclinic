# Operations Runbook: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Reference runbook; operational rehearsal NOT RUN  
**Operational claim:** Local educational guidance only; not production approval

## Signals to observe

- HTTP 429 responses on the visit-booking route;
- `Retry-After` values and request timestamps;
- limiter-row `attempt_count` and `window_started_at`;
- database lock wait, timeout, and error signals;
- application health and database connectivity;
- rejected requests versus persisted visits.

Do not log owner contact data, visit descriptions, or other personal data for
limiter diagnosis.

## Triage

1. Identify the candidate revision and database profile.
2. Confirm whether the observed state is an active window, a new-window
   boundary, a 429 response, or a database failure.
3. Compare the request path with INV-01 and the pet key with INV-02.
4. Check whether the rejected request changed visit persistence.
5. Preserve evidence before changing configuration or restarting a process.

## Database failure or lock timeout

Do not convert an unavailable database or lock failure into an allowed booking.
Record the error, affected environment, request outcome, and recovery owner.
Escalate because INV-19 requires an observed and approved failure policy.

## Disablement and recovery

The bounded companion has no limiter reset endpoint and no production disablement
procedure. Do not invent one during an incident. Stop the affected educational
run, preserve the database/evidence state, and return any reset or rollback
choice to the service owner.

## Evidence boundary

No operational rehearsal, multi-instance test, database-outage injection, or
production run is claimed by this file. A reader must record commands, observed
signals, recovery action, and residual impact if the procedure is executed.
