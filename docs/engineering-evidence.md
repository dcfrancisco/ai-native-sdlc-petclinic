# Engineering Evidence

Evidence supports a bounded claim. It does not convert uncertainty into certainty.

## Evidence layers

- source and tool provenance;
- formatting and compilation;
- focused unit tests;
- integration tests;
- database-specific checks;
- container startup and health;
- application startup;
- human functional behavior;
- independent review;
- security and policy review;
- release and rollback rehearsal;
- operational observation.

## Reporting format

For every check record:

- claim being tested;
- command or procedure;
- environment;
- result;
- passed, failed, blocked, or not-run state;
- relevant excerpt;
- limitations;
- accountable reviewer.

Container startup proves only that the container started. A browser observation proves only the behavior observed. Acceptance requires the evidence set appropriate to the risk.
