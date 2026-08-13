# Functional Evidence: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** NOT RUN / reader-owned procedure  
**Related verification:** `../../ai/evidence/visit-rate-limiter-verification.json`

This record defines the functional evidence required by the manuscript. It does
not claim that the procedure was executed in the retained publication evidence.

## Procedure

1. Start the application with the supported baseline and record the source
   revision and environment.
2. Select one existing pet and record its current visits.
3. Submit the matching visit-booking POST three times inside one 60-second
   window.
4. Confirm those requests continue through the existing controller behavior.
5. Submit a fourth matching POST.
6. Observe HTTP 429 and an integer `Retry-After` header.
7. Confirm the rejected request did not persist a visit by querying the
   resulting owner/pet state independently.
8. At the exact window boundary, submit again and confirm a new window begins.

## Evidence record

| Check | Result | Environment | Raw evidence | Limitation |
| --- | --- | --- | --- | --- |
| Three allowed attempts | NOT RUN in retained functional record | — | — | H2 automated evidence is separate from human functional evidence |
| Fourth attempt returns 429 | NOT RUN in retained functional record | — | — | Automated MockMvc evidence exists in EV-VRL-001 |
| `Retry-After` is integer | NOT RUN in retained functional record | — | — | Automated evidence is separate |
| Rejected request does not persist | NOT RUN | — | — | Explicit independent assertion remains absent |
| Exact boundary begins a new window | NOT RUN in retained functional record | — | — | Automated fixed-`Instant` evidence exists |

## Claim boundary

This procedure does not prove MySQL or PostgreSQL limiter behavior,
multi-instance enforcement, representative latency, or production safety.
