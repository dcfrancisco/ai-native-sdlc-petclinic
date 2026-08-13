# Security and Control Assessment: Visit Rate Limiter

**Case:** PLC-VRL-001 — PetClinic Lifecycle Case  
**Status:** Reviewed for bounded educational scope; residual risk open  
**Related decision:** `../../ai/memory/adr-004-visit-rate-limiting.md`

## Assets and boundaries

- visit-booking request and pet identifier;
- pet/owner relationship and visit state;
- database limiter rows and lock behavior;
- application logs, error responses, and AI execution context;
- build commands, schema files, and local database environments.

## Controls and residual findings

| Risk | Control in the bounded design | Evidence status | Residual limitation |
| --- | --- | --- | --- |
| Pet enumeration through limiter responses | Missing pets pass through existing controller behavior | Source inspection; runtime case not run | Dedicated runtime assertion is absent |
| Capacity exhaustion by an unauthenticated caller | Limiter is explicitly not authentication or authorization | Documented in ADR-004 | Production trust and abuse policy are unresolved |
| Personal data in rejection response | Response contains no owner contact, visit description, or pet details | Code/evidence inspection | Full security validation not executed |
| Invalid forms consuming capacity | Accepted educational rule is explicit in INV-05 | H2 behavior included in scope | Production policy is unresolved |
| Database failure silently allowing requests | INV-19 prohibits silent allow | Not run | No lock-timeout or outage injection exists in retained evidence |
| Locking creates availability/latency risk | Pet-row lock and p95 target are explicit | H2 concurrency evidence only | Database-specific and representative performance evidence absent |
| Unauthorized AI scope expansion | CC-VRL-001 allowlists files, imports, commands, and stop conditions | Contract inspection | Execution-environment enforcement is not independently validated |
| Dependency or external-service expansion | No new dependency, cache, endpoint, or service permitted | Diff/evidence inspection | Production supply-chain/security review remains separate |

## Control boundary

This assessment supports review of the educational companion scope. It is not a
penetration test, production security approval, authorization design, or proof
of database failure safety.

## Required human decisions

The product, security, and service owners must decide production threshold,
invalid-form policy, authentication/authorization expectations, lock-failure
handling, performance acceptance, and whether the bounded design may be
promoted beyond the educational claim.
