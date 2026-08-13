# PLC-VRL-001 Primary Case Artifacts

This is the authoritative reader index for **PLC-VRL-001 — PetClinic
Lifecycle Case**, the per-pet visit-booking rate limiter. Follow the links in
order. The reminder capability remains a secondary case for later operational,
security, and governance practice.

| Book stage | Primary artifact | Path |
| --- | --- | --- |
| Capability request | Visit-rate-limiter request | [`artifacts/requirements/visit-rate-limiter-request.md`](../artifacts/requirements/visit-rate-limiter-request.md) |
| Requirements challenge | Challenge Matrix | [`artifacts/requirements/visit-rate-limiter-challenge-matrix.md`](../artifacts/requirements/visit-rate-limiter-challenge-matrix.md) |
| Spec Invariants | SI-VRL-001 | [`ai/specs/visit-rate-limiter-invariants.md`](../ai/specs/visit-rate-limiter-invariants.md) |
| Context | CP-VRL-001 | [`ai/context/visit-rate-limiter-context-package.yaml`](../ai/context/visit-rate-limiter-context-package.yaml) |
| Architecture | ADR-004 | [`ai/memory/adr-004-visit-rate-limiting.md`](../ai/memory/adr-004-visit-rate-limiting.md) |
| Work Package | WP-VRL-001 | [`artifacts/work-packages/visit-rate-limiter-work-package.md`](../artifacts/work-packages/visit-rate-limiter-work-package.md) |
| Planning | PR-VRL-001 | [`ai/plans/visit-rate-limiter-plan-receipt.md`](../ai/plans/visit-rate-limiter-plan-receipt.md) |
| Contribution boundary | CC-VRL-001 | [`ai/contracts/visit-rate-limiter-contribution.yaml`](../ai/contracts/visit-rate-limiter-contribution.yaml) |
| AI instruction | AI-002 | [`ai-assets/ai-instructions/AI-002-visit-rate-limiter-implementation.md`](../ai-assets/ai-instructions/AI-002-visit-rate-limiter-implementation.md) |
| Implementation | Limiter source | [`src/main/java/org/springframework/samples/petclinic/owner/VisitRateLimiter.java`](../src/main/java/org/springframework/samples/petclinic/owner/VisitRateLimiter.java) |
| Tests | Limiter integration tests | [`src/test/java/org/springframework/samples/petclinic/owner/VisitRateLimiterIntegrationTests.java`](../src/test/java/org/springframework/samples/petclinic/owner/VisitRateLimiterIntegrationTests.java) |
| Review | Limiter review findings | [`artifacts/reviews/visit-rate-limiter-review.md`](../artifacts/reviews/visit-rate-limiter-review.md) |
| Correction | Correction contract and record | [`journey/08-review-and-correction/visit-rate-limiter-correction-contract.md`](../journey/08-review-and-correction/visit-rate-limiter-correction-contract.md) |
| Verification | EV-VRL-001 and VM-VRL-001 | [`ai/evidence/visit-rate-limiter-evidence-log.md`](../ai/evidence/visit-rate-limiter-evidence-log.md), [`ai/evidence/visit-rate-limiter-verification.json`](../ai/evidence/visit-rate-limiter-verification.json) |
| Functional evidence | Limiter golden path | [`artifacts/evidence/visit-rate-limiter-functional-evidence.md`](../artifacts/evidence/visit-rate-limiter-functional-evidence.md) |
| Acceptance | Human acceptance record | [`artifacts/acceptance/visit-rate-limiter-acceptance-record.md`](../artifacts/acceptance/visit-rate-limiter-acceptance-record.md) |
| Security | Limiter security/control assessment | [`artifacts/security/visit-rate-limiter-security-assessment.md`](../artifacts/security/visit-rate-limiter-security-assessment.md) |
| Release | PLC-VRL-001 delivery evidence | [`docs/delivery-evidence.md`](delivery-evidence.md), [`artifacts/evidence/delivery/README.md`](../artifacts/evidence/delivery/README.md) |
| Operations | Limiter runbook | [`artifacts/operations/visit-rate-limiter-runbook.md`](../artifacts/operations/visit-rate-limiter-runbook.md) |
| Learning | ADR-004 and final lifecycle assessment | [`ai/memory/adr-004-visit-rate-limiting.md`](../ai/memory/adr-004-visit-rate-limiting.md), [`artifacts/lessons/visit-rate-limiter-lifecycle-assessment.md`](../artifacts/lessons/visit-rate-limiter-lifecycle-assessment.md) |

The primary case deliberately retains `NOT RUN`, `PARTIAL`, `REVISE`, and
`PENDING` states. Presence of an artifact is not evidence that its procedure
was executed or that a human accepted the claim.
