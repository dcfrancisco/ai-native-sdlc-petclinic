# Reminder Security and Privacy Assessment

**Chapter:** 13
**Status:** Reviewed for local reference use

## Assets

- owner contact data;
- visit schedule;
- pet and owner identity relationships;
- adapter outcome status and errors;
- application and database credentials in non-local deployments.

## Trust boundaries

- browser to application;
- scheduler to reminder service;
- application to database;
- notification port to future external provider;
- Actuator endpoint to monitoring consumer.

## Principal threats and controls

| Threat | Reference control | Residual limitation |
|---|---|---|
| Contact data in logs or views | Mask destination | Full number still exists in owner domain |
| Sensitive provider error persistence | Persist only the failure type | Provider-specific classification is absent |
| Duplicate delivery | Unique visit constraint and lookup | Multi-instance race handling is not designed |
| Unauthorized manual processing | Local educational UI only | Authentication and authorization are absent upstream |
| Exposed operational endpoints | Development-only Actuator configuration | Production endpoint policy is not defined |
| Secret leakage | No production provider or secret | Future provider requires secret management |
| Dependency risk | Maven dependency and SBOM tooling | Release review must inspect current advisories |

## Production blockers

- authentication and role-based authorization;
- consent and communication policy;
- provider credential management;
- network and egress controls;
- retention and deletion requirements;
- audit requirements;
- dependency and image vulnerability review;
- production Actuator exposure policy.
