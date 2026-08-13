# Correction Record: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** NOT EXECUTED  
**Contract:** `visit-rate-limiter-correction-contract.md`

No corrective execution is claimed by this record. It preserves the review
findings and the bounded work required to close them.

## Findings requiring correction or disposition

- rejected HTTP persistence has not been independently asserted;
- missing-pet runtime behavior is only supported by source inspection;
- different-pet isolation is not measured;
- 59.999-second, locale, and daylight-saving cases are not executed;
- MySQL and PostgreSQL limiter threshold/concurrency behavior is not run;
- database lock-timeout/outage behavior is not injected;
- multi-instance enforcement is not run;
- INV-20 has no accepted representative load profile or p95 result;
- Docker/runtime and human functional evidence for the limiter are not retained.

## Disposition

**REVISE.** The existing evidence remains valid only within its recorded H2 and
inspection boundaries. No result is promoted from `NOT_RUN`, `PARTIAL`, or
historical evidence by this record.

## Human action required

An accountable engineer must approve and execute the bounded correction before
the acceptance record can change from pending. A human must separately decide
whether any production policy or residual risk is acceptable.
