# Reminder Governance Policy

**Chapter:** 13
**Status:** Accepted for the educational reference

## Permitted without additional approval

- local adapter acceptance;
- masked destination evidence;
- H2, PostgreSQL, and MySQL verification;
- manual operator processing in a local environment;
- test-only failure simulation.

## Requires explicit human approval

- production provider selection;
- credentials and secret store integration;
- contact-channel and consent interpretation;
- schema changes to owner contact data;
- automatic retry policy;
- retention and deletion policy;
- production scheduling topology;
- public API exposure;
- production release.

## Enforceable controls

- unique database constraint for one record per visit;
- masked stored destination;
- bounded package and outbound interface;
- tests for eligibility, accepted, failed, unknown, and idempotent outcomes;
- stop conditions in AI instructions;
- separate security and release reviews.

Policy text alone is not enforcement. Controls that cannot be automated must identify an accountable reviewer and evidence requirement.
