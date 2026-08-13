# Pinned Baseline

| Item | Recorded value |
|---|---|
| Upstream repository | `https://github.com/spring-projects/spring-petclinic` |
| Upstream commit | `f182358d02e4a68e52bdbabf55ca7800288511e7` |
| Commit date | 2026-07-16 |
| Spring Boot | 4.1.0 |
| Project version | 4.0.0-SNAPSHOT |
| Java source level | 17 |
| Maven wrapper | 3.9.11 |
| Container Java runtime | Eclipse Temurin 17.0.19, digest `sha256:475d8e96b4b2bfe08999e5e854755c773af1581acdf959a4545d88f0696a2339` |
| PostgreSQL image | 18.4 |
| MySQL image | 9.7, verified database version 9.7.1 |
| License | Apache License 2.0 |

The exact Maven, Java, Docker client, Docker server, and Compose versions used for published verification are recorded in `artifacts/evidence/verification-report.md` after each release verification.

## Why this baseline

Spring PetClinic is familiar, bounded, tested, and large enough to expose real ecosystem decisions. The reminder capability crosses application logic, data, scheduling, notification boundaries, delivery state, configuration, monitoring, recovery, security, governance, release, and operations without requiring readers to learn an unfamiliar business domain.

The revision is pinned so upstream changes cannot silently change the exercise. The upstream repository remains read-only. This companion repository owns its derived implementation and educational artifacts.

## Baseline behavior

- Owners have telephone numbers but no email addresses.
- Pets can have future visits.
- The upstream application has no reminder capability.
- H2 is the default fast-feedback database.
- PostgreSQL and MySQL provide database-specific evidence paths.
- Actuator is available for operational evidence.

None of these facts authorizes an AI contributor to choose a commercial provider, infer consent, add a contact field, or define production policy.
