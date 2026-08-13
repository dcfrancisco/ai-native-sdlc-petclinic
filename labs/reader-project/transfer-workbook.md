# Transfer Workbook: Apply the Lifecycle to Your Software

**Book chapters:** 4 through 17

Use one bounded capability in software you own or understand. Do not copy the PetClinic design. Reproduce the engineering function of each artifact in the language and form appropriate to your project.

## Choose the capability

Choose work that is valuable enough to require judgment but small enough to complete during the book. The capability should cross at least one meaningful boundary such as persistence, an external service, identity, asynchronous processing, deployment, or operations.

Do not choose a project-wide rewrite. If you cannot describe the intended outcome in one paragraph, reduce the scope.

## Chapter 4: Context

Produce:

- repository and system inventory;
- authoritative architecture and decision sources;
- current build, test, startup, and container commands;
- relevant contracts, dependencies, and operational constraints;
- known gaps and conflicting evidence.

Exit when another engineer can locate the authoritative sources and reproduce the current state without relying on chat history.

## Chapter 5: Requirements and acceptance

Produce:

- problem statement and desired outcome;
- actors and affected stakeholders;
- functional and quality requirements;
- constraints, exclusions, and assumptions;
- observable acceptance criteria.

Exit when implementation can be evaluated without inventing product behavior.

## Chapter 6: Architecture

Produce:

- system context and component views at the minimum useful level;
- affected ecosystem boundaries;
- credible alternatives and consequences;
- one recorded architecture decision;
- deferred decisions that implementation must not make.

Exit when the intended boundaries and prohibited shortcuts are explicit.

## Chapter 7: Planning and readiness

Produce:

- bounded work packages;
- ordering, dependencies, and integration points;
- risks and evidence required for each package;
- stop conditions;
- readiness decision.

Exit when one package can be assigned without asking its implementer to redesign the capability.

## Chapter 8: Contribution contract

Produce one contribution contract derived from the approved work package. Include role, objective, authority, context, scope, exclusions, constraints, deliverables, acceptance criteria, verification, reporting, and stop conditions.

Exit when an AI contributor can act without guessing which sources govern the work or which decisions it may make.

Separate stable instruction structure from project-specific values. Catalog only assets that are likely to be reused. Record purpose, required inputs, outputs, owner, version, status, and retirement conditions.

Exit when reuse reduces repeated discovery without preserving stale assumptions.

## Chapter 9: Implementation

Execute one approved package. Keep changes inside the stated scope. Run focused tests during implementation. Record deviations, failed attempts, and any stop condition reached.

Exit when the contribution is ready for verification, not when the AI says it is complete.

## Chapter 10: Evidence

Produce:

- build and automated test results;
- integration and database results where relevant;
- functional checks performed by a human;
- container or environment checks;
- failed and not-run checks;
- residual limitations.

Exit when every acceptance criterion points to evidence or is explicitly unverified.

## Chapter 11: Independent review

Use roles that did not implement the change. Review architecture conformance, behavior, tests, maintainability, security, and evidence. Correct material findings and preserve the review record.

Exit when blocking findings are resolved or explicitly rejected by an accountable human with rationale.

## Chapter 12: Documentation and traceability

Update the documentation that future humans and AI contributors need. Link requirements to implementation, tests, decisions, and evidence. Remove stale instructions discovered during the work.

Exit when a future contributor can reconstruct why the change exists and how to verify it.

## Chapter 13: Security and governance

Identify assets, trust boundaries, abuse cases, private data, secrets, dependencies, and supply-chain exposure. Separate controls present in code from controls that exist only as policy.

Exit when residual security risk has a named human owner and disposition.

State what AI may do, what requires approval, what is prohibited, and how each important control is enforced. Avoid governance language that cannot be observed or audited.

Exit when authority and escalation paths are explicit.

## Chapter 14: Release and operations

Produce release criteria, deployment checks, rollback steps, monitoring signals, recovery procedures, and incident evidence requirements. Run the application in the environment the release evidence claims to cover.

Exit when an operator can detect failure, limit impact, recover service, and preserve learning.

## Chapter 15: Measurement

Choose balanced measures for quality, review effort, rework, scope adherence, risk, and delivery. Record the baseline and measurement method. Do not infer universal productivity from one project.

Exit when measures can inform a decision rather than merely decorate a report.

## Chapter 16: Operating model and adoption

Assign human accountability and AI contribution across architecture, implementation, testing, review, security, release, and operations. Separate contribution from approval.

Exit when every consequential decision has a human owner with enough context and authority to make it.

Define a bounded pilot, eligible task types, excluded work, training, evidence capacity, review dates, success measures, and stopping conditions.

Exit when adoption can expand, change, or stop based on evidence.

## Chapter 17: Engineered delivery

Assemble the lifecycle package in the order another engineer would need it:

1. intent and requirements;
2. context and architecture;
3. decisions and plan;
4. contribution contract and AI instruction;
5. implementation and evidence;
6. review and corrections;
7. documentation and traceability;
8. security and governance;
9. release and operations;
10. measurements, operating model, adoption, and lessons.

Exit when another engineer can explain what was intended, what changed, why it was accepted, what remains uncertain, and how operation will inform the next decision.

## Final transfer check

Without referring to PetClinic, explain:

- why each artifact exists;
- which artifacts carry authority into the next activity;
- where AI contributed;
- where human judgment changed or rejected a contribution;
- what evidence justified acceptance;
- what would stop the next contribution;
- how the method should be reduced or strengthened for your project risk.

If you can do that and complete one bounded capability in your own software, the practice has transferred.
