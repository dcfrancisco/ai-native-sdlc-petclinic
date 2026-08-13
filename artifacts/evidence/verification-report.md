# Reminder Capability Verification Report

**Book chapters:** 10, 11, 12, 14, 16, and 17
**Execution dates:** 2026-07-19 and 2026-07-26, Asia/Manila
**Evidence status:** Current automated verification completed; current functional recovery replay and human acceptance pending

## Baseline

- Upstream: `https://github.com/spring-projects/spring-petclinic`
- Upstream commit: `f182358d02e4a68e52bdbabf55ca7800288511e7`
- Spring Boot: 4.1.0
- Build Java: 21.0.10, compiling for Java 17
- Container Java: Eclipse Temurin 17.0.19
- Maven: 3.9.11
- Docker client and server: 28.0.4
- Docker Compose: 2.34.0-desktop.1
- PostgreSQL: 18.4
- MySQL: 9.7.1

## Current automated verification

The corrected `ACCEPTED`, `FAILED`, and `UNKNOWN` outcome model was verified on
2026-07-26.

Command:

```bash
POSTGRES_PORT=55432 ./mvnw -Dmaven.repo.local=/tmp/ai-native-sdlc-petclinic-m2 test
```

Result:

- tests run: 78;
- failures: 0;
- errors: 0;
- skipped: 2;
- Java formatting: passed;
- Checkstyle violations: 0;
- H2 tests: passed;
- reminder service, controller, and H2 integration tests: 9 passed;
- localization consistency tests: passed.

Docker was not available to Testcontainers during this execution. The
PostgreSQL integration test did not execute, and the two MySQL integration
tests were skipped. This run therefore does not claim current
PostgreSQL or MySQL verification.

The tests include explicit `UNKNOWN` handling and a null adapter outcome. A
null outcome is treated as failure rather than silently accepted.

## Historical pre-correction functional evidence

On 2026-07-19, the application was started with H2 on port 18080 and the
following workflow was executed:

1. `GET /` returned HTTP 200.
2. `GET /reminders` returned HTTP 200.
3. A visit was created for pet Leo on 2026-07-21, two days after execution.
4. `POST /reminders/process` created one reminder using the local adapter.
5. The operator page displayed the then-current status `DELIVERED` and destination `******1023`.
6. The process operation was executed a second time.
7. `petclinic.reminders.delivered` remained `1.0`, confirming no duplicate delivery.

This evidence confirms that the earlier workflow executed and suppressed a
duplicate record. It does not validate the corrected outcome semantics. The
term `DELIVERED` was the defect that triggered the correction.

## Historical pre-correction container evidence

On 2026-07-19, the runtime was pinned to:

```text
eclipse-temurin@sha256:475d8e96b4b2bfe08999e5e854755c773af1581acdf959a4545d88f0696a2339
```

Commands:

```bash
docker build -t ai-native-sdlc-petclinic:local .
docker run --rm -p 18081:8080 ai-native-sdlc-petclinic:local
```

Result:

- image built successfully;
- application image ID: `sha256:5d88381b433a28bc7e224054695cd4ad5fba8dee5c739ee22ff99d51d5bd110d`;
- container started with Java 17.0.19;
- configured container user: `petclinic`;
- `GET /` returned HTTP 200;
- `GET /reminders` returned HTTP 200;
- graceful shutdown completed.

The Spring Boot buildpack command was also attempted. It failed before image
construction because the local Docker credential helper returned a null
username. This evidence establishes that the pre-correction application image
ran. The container and browser workflow were not rerun after the outcome model
was corrected.

## Evidence boundaries

- `ACCEPTED` means that the local reference adapter accepted the request. It
  does not prove provider delivery or owner receipt.
- No external email or SMS provider was called.
- The database uniqueness constraint prevents duplicate reminder records. Concurrent collision handling remains a production-hardening concern documented in the independent review.
- The controlled comparison between minimal and structured instructions has a complete protocol but has not been independently executed. No comparative result is claimed.
- The current automated run does not establish Docker-backed database
  compatibility or functional recovery behavior.
- These checks do not establish that an independent reader can complete the
  labs without clarification.

## Acceptance disposition

The corrected implementation passes the current automated H2 reference scope.
The disposition remains `REVISE` until the functional recovery exercise is
executed against the corrected model and its evidence is reviewed. Human
acceptance, an independent reader pilot, and execution of the controlled
experiment remain separate gates.
