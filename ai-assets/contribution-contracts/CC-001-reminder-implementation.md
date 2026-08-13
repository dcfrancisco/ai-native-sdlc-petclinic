# CC-001: Reminder Implementation Contribution Contract

**Related chapter:** 8  
**Version:** 1.0  
**Status:** Accepted for the reference implementation  
**Accountable human owner:** Repository maintainer

## Objective

Implement the accepted local appointment-reminder capability while preserving existing PetClinic behavior and producing reviewable evidence.

## Authority

- Reminder requirements;
- ADR-001;
- approved work packages;
- pinned baseline;
- repository build and test conventions.

## Permitted scope

- bounded reminder package;
- H2, PostgreSQL, and MySQL reminder schemas;
- reminder operator page and navigation;
- configuration and Actuator metrics;
- focused and integration tests;
- implementation documentation.

## Exclusions

- commercial providers;
- production credentials;
- new contact fields;
- consent inference;
- distributed scheduling;
- unrelated refactoring;
- release acceptance.

## Invariants

- one reminder record per visit within the bounded local exercise;
- masked persisted and logged destination data;
- adapter acceptance is never called delivery or owner receipt;
- definite failures remain observable and retryable;
- unknown outcomes remain observable and require reconciliation;
- current owner and visit behavior remains compatible;
- environmental failures are reported separately;
- human authority owns acceptance.

## Deliverables

- implementation and schemas;
- tests;
- verification report;
- modified-file inventory;
- residual limitations;
- human functional procedure.

## Stop conditions

Stop before changing code if the implementation requires a decision outside the authority sources, a production secret, a public contract change, a new contact field, provider selection, or a distributed coordination mechanism.
