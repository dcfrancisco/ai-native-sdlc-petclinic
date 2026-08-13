# Repository Structure

```text
src/                       Application and tests
artifacts/context/         Chapter 4 context package
artifacts/requirements/    Primary request, Challenge Matrix, and accepted intent
artifacts/architecture/    Chapter 6 diagrams and design
artifacts/decisions/       Architecture and governance decisions
artifacts/work-packages/   Chapter 7 implementation decomposition
artifacts/acceptance/      Human acceptance and residual-risk decisions
artifacts/evidence/        Test and verification evidence
artifacts/reviews/         Independent findings and corrections
artifacts/security/        Threat model and security assessment
artifacts/governance/      Policy and control decisions
artifacts/release/         Release, rollback, and approval material
artifacts/operations/      Monitoring, recovery, and incident guidance
artifacts/traceability/    Requirement-to-evidence mapping
artifacts/measurements/    Balanced engineering measures
artifacts/operating-model/ Human and AI roles and authority
artifacts/adoption/        Bounded adoption plan
artifacts/lessons/         Final lifecycle assessment
docs/primary-case-artifacts.md  Ordered PLC-VRL-001 reader index
ai-assets/                 Reusable context, contracts, instructions, templates
ai/specs/                  Primary Spec Invariants
ai/context/                Primary Context Package
ai/plans/                  Primary Plan Receipt
ai/contracts/              Primary Contribution Contract
ai/evidence/               Primary evidence log and Verification Matrix
ai/memory/                 Canonical retained-memory ADR-004
labs/                      Reader exercises and controlled experiment
docs/                      Reader and maintainer documentation
scripts/                   Transparent verification helpers
```

Application source is cumulative. Chapter directories hold engineering artifacts, not duplicated source trees.
