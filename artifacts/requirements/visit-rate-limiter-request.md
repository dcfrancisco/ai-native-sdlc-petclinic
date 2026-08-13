# PetClinic Capability Request: Per-Pet Visit Booking Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Recorded reference input; not a stakeholder-discovery record  
**Book stage:** Chapter 5, before the Challenge Matrix

## Initial request

> Add a visit scheduling rate-limiter per pet to prevent spam bookings in
> Spring PetClinic.

This request is intentionally incomplete. It does not, by itself, decide the
threshold, window, meaning of an attempt, time basis, persistence model,
concurrency behavior, missing-pet behavior, failure policy, security policy, or
performance claim. Those questions are addressed by the Challenge Matrix and
the accepted Spec Invariants.

## Authority boundary

This file preserves the request used by the manuscript. It does not claim that
stakeholder interviews, incident data, prioritization evidence, or a production
policy decision were performed. A reader may perform that discovery separately
before accepting the educational scope.

## Downstream artifacts

- Requirements challenge: `visit-rate-limiter-challenge-matrix.md`
- Accepted rules: `../../ai/specs/visit-rate-limiter-invariants.md`
- Architecture decision: `../../ai/memory/adr-004-visit-rate-limiting.md`
- Lifecycle identity: PLC-VRL-001
