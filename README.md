# AI-Native SDLC PetClinic

```text
			  |\      _,,,--,,_
			 /,`.-'`'   ._  \-;;,_
  _______ __|,4-  ) )_   .;.(__`'-'__     ___ __    _ ___ _______
 |       | '---''(_/._)-'(_\_)   |   |   |   |  |  | |   |       |
 |    _  |    ___|_     _|       |   |   |   |   |_| |   |       | __ _ _
 |   |_| |   |___  |   | |       |   |   |   |       |   |       | \ \ \ \
 |    ___|    ___| |   | |      _|   |___|   |  _    |   |      _|  \ \ \ \
 |   |   |   |___  |   | |     |_|       |   | | |   |   |     |_    ) ) ) )
 |___|   |_______| |___| |_______|_______|___|_|  |__|___|_______|  / / / /
 ==================================================================/_/_/_/
```

## Companion Book

This repository is the official companion implementation for:

**AI-Native Software Development Lifecycle: Designing, Building, Testing, and Governing Software with AI**

📘 [Get the book on Amazon](https://a.co/d/0bsAH54g)

This project carries a per-pet visit-booking rate limiter through Challenge, Plan, Build, Review, Verify, and Govern. An appointment-reminder capability remains as a secondary exercise for the later security, release, and operational chapters. This is not a prompt collection, and it does not prescribe Java or Spring Boot as part of AI-Native SDLC. It is one concrete reader laboratory and reference implementation. It demonstrates how a human and AI partnership produces requirements, architecture, decisions, plans, instructions, code, evidence, reviews, controls, release material, operational guidance, and lessons as one engineered delivery.

The primary thread is the **PetClinic Lifecycle Case** (`PLC-VRL-001`): the
per-pet visit-booking rate limiter. The same case identity is retained in the
requirements, architecture, contribution, verification, release, and lifecycle
evidence records. The companion's hosted delivery path is Maven-based and
produces a bounded delivery-evidence bundle; release authority remains human.

The immutable companion baseline for the first published edition is Git tag
`ai-native-sdlc-v1.0.1`.

Readers may follow this implementation, translate the journey to another
language and platform, or apply every stage directly to software of their own.
The engineering functions transfer. The framework-specific mechanics do not.

## What the capability does

The primary six-phase capability limits spam visit bookings per pet while
preserving existing PetClinic behavior outside the approved contribution
contract. The implementation and retained evidence distinguish local H2
results from database-specific, multi-instance, and performance claims that
have not run.

The secondary reminder capability is broader:

PetClinic can identify visits that enter a configurable reminder window, create one persistent reminder record per visit, submit it to a local notification adapter, preserve accepted, failed, or unknown outcomes, expose operational metrics, and retry definite failures.

The reference implementation deliberately excludes commercial messaging providers, production credentials, distributed scheduling, and new owner contact fields. Those require product, architecture, privacy, security, and operational decisions that are outside the bounded implementation.

## Start here

1. Begin with the [PLC-VRL-001 Primary Case Artifacts](docs/primary-case-artifacts.md).
2. Follow the [PetClinic Engineering Journey](journey/README.md).
3. Use [Getting Started](docs/getting-started.md) to establish the environment.
4. Use [Chapter Mapping](docs/chapter-mapping.md) for the manuscript-to-artifact view.
5. Complete each reader decision before comparing it with the [Accepted Reference Artifacts](reference/accepted-artifacts/README.md).
6. Apply the same engineering function to software you own or understand.

## Quick start: establish the primary case baseline

Prerequisites:

- Java 17 or newer;
- Docker for database-backed and packaged-runtime checks;
- a browser;
- an IDE or terminal according to your preferred workflow.

```bash
./mvnw test
./mvnw spring-boot:run
```

Open `http://localhost:8080/` and use the baseline visit-booking workflow
before opening the primary artifacts.

The reminder capability has a separate secondary quick check:

1. Find an owner and book a visit exactly two days in the future.
2. Open **Reminders**.
3. Select **Process due reminders**.
4. Confirm one reminder accepted by the local adapter appears.
5. Process again and confirm no duplicate is created.

See [Running the Labs](docs/running-the-labs.md) for database, Docker, and evidence instructions.

## Cumulative chapter states

This repository maintains one cumulative implementation. [Chapter Mapping](docs/chapter-mapping.md) identifies the artifact state associated with each chapter. Readers may inspect ordinary source history if they already use it, but the educational workflow depends on artifacts, not source-control mechanics.

## Evidence rule

A passing build is not acceptance. Report automated tests, database checks, container startup, application startup, and human functional behavior separately. Record failed and not-run checks. The human remains responsible for acceptance and residual risk.

## Upstream provenance

This project is derived from Spring PetClinic revision `f182358d02e4a68e52bdbabf55ca7800288511e7`, licensed under the Apache License 2.0. See [Baseline](docs/baseline.md) and [LICENSE.txt](LICENSE.txt).

## Documentation

Companion quick reference:

- [Engineering with AI Contributors](../companion/website/resources/ai-contributor-quick-reference.md)
- [Printable two-page PDF](../companion/website/downloads/engineering-with-ai-contributors-quick-reference.pdf)

- [Getting Started](docs/getting-started.md)
- [Baseline](docs/baseline.md)
- [Book Mapping](docs/book-mapping.md)
- [Chapter Mapping](docs/chapter-mapping.md)
- [PLC-VRL-001 Primary Case Artifacts](docs/primary-case-artifacts.md)
- [Repository Structure](docs/repository-structure.md)
- [Artifact Guide](docs/artifact-guide.md)
- [Contribution Contracts](docs/contribution-contracts.md)
- [Running the Labs](docs/running-the-labs.md)
- [Transfer Workbook](labs/reader-project/transfer-workbook.md)
- [Controlled Experiment](docs/controlled-experiment.md)
- [Engineering Evidence](docs/engineering-evidence.md)
- [Executed Verification Report](artifacts/evidence/verification-report.md)
- [Reader Readiness and Publication Gate](docs/reader-readiness.md)
- [Delivery Evidence](docs/delivery-evidence.md)
- [Retirement Decision](artifacts/retirement/retirement-decision.md)
