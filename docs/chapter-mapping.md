# Chapter Mapping

The chapter map identifies authoritative outputs. The guided route through them begins at [`journey/README.md`](../journey/README.md).

| Chapter | Primary reader output | Primary reference location |
| --- | --- | --- |
| 4 | Context package and baseline | `ai/context/visit-rate-limiter-context-package.yaml`, `docs/baseline.md` |
| 5 | Capability request, Challenge Matrix, and Spec Invariants | `artifacts/requirements/visit-rate-limiter-request.md`, `artifacts/requirements/visit-rate-limiter-challenge-matrix.md`, `ai/specs/visit-rate-limiter-invariants.md` |
| 6 | Architecture and ADR-004 | `ai/memory/adr-004-visit-rate-limiting.md` |
| 7 | Work Package and Plan Receipt | `artifacts/work-packages/visit-rate-limiter-work-package.md`, `ai/plans/visit-rate-limiter-plan-receipt.md` |
| 8 | Contribution contract and AI instruction | `ai/contracts/visit-rate-limiter-contribution.yaml`, `ai-assets/ai-instructions/AI-002-visit-rate-limiter-implementation.md` |
| 9 | Bounded implementation and tests | `src/main/java/.../VisitRateLimiter.java`, `src/test/java/.../VisitRateLimiterIntegrationTests.java` |
| 10 | Review findings and correction | `artifacts/reviews/visit-rate-limiter-review.md`, `journey/08-review-and-correction/visit-rate-limiter-correction-contract.md` |
| 11 | Verification and functional evidence | `ai/evidence/visit-rate-limiter-verification.json`, `artifacts/evidence/visit-rate-limiter-functional-evidence.md` |
| 12 | Traceability, acceptance, and retained memory | `artifacts/acceptance/visit-rate-limiter-acceptance-record.md`, `ai/memory/adr-004-visit-rate-limiting.md` |
| 13 | Limiter security and controls | `artifacts/security/visit-rate-limiter-security-assessment.md` |
| 14 | Limiter release evidence and operations | `docs/delivery-evidence.md`, `artifacts/evidence/delivery/README.md`, `artifacts/operations/visit-rate-limiter-runbook.md` |
| 15 | Measurements and controlled experiment | `artifacts/measurements/`, `labs/controlled-experiment/` |
| 16 | Operating model and adoption | `artifacts/operating-model/`, `artifacts/adoption/` |
| 17 | Engineered delivery and lessons | `artifacts/lessons/final-lifecycle-assessment.md` |

The same primary case appears throughout this map as **PLC-VRL-001 — PetClinic
Lifecycle Case**, the per-pet visit-booking rate limiter. Its complete ordered
artifact index is [PLC-VRL-001 Primary Case Artifacts](primary-case-artifacts.md).
Its delivery evidence is in `docs/delivery-evidence.md`; its bounded retirement example is in
`artifacts/retirement/`. The retirement example closes an obsolete duplicate CI
workflow, not the application capability or a production service.

The appointment-reminder artifacts remain available as a clearly separate
secondary case for later operational, security, governance, and adoption
practice. They are not the primary references for Chapters 4–14.

Readers are not expected to learn source-control mechanics to complete the practice. The repository is cumulative and the chapter map points to engineering artifacts, not duplicated application copies.
