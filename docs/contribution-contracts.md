# Contribution Contracts

A contribution contract translates approved engineering work into a bounded AI contribution. The AI instruction is one serialization of that contract.

Every consequential instruction should include:

```text
Role
Objective
Authority
Context and current state
Scope
Exclusions
Constraints and invariants
Required deliverables
Acceptance criteria
Verification
Functional checks for the reader
Docker or environment checks
Reporting requirements
Stop conditions
```

Use `ai-assets/contribution-contracts/CC-001-reminder-implementation.md` for the complete reference and `ai-assets/templates/structured-ai-instruction.md` for new work.

The same AI system may perform several roles sequentially. Keep one primary responsibility per instruction so implementation, testing, review, security, and release conclusions remain distinguishable.
