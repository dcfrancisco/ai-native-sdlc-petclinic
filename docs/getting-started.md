# Getting Started

## Choose your working surface

Use an IDE when navigation, call hierarchies, debugging, and focused test execution help you understand the change. Use a terminal when you need reproducible commands and concise evidence. You may use both. The method does not depend on a particular editor or AI product.

## Establish current state

```bash
java -version
./mvnw -version
docker version
docker compose version
./scripts/verify.sh
```

If Docker is unavailable, continue with H2 and record Docker checks as not run.

## Run locally

```bash
./mvnw spring-boot:run
```

Open `http://localhost:8080/`. Verify owner search and future-visit booking before testing reminders.

## Run with PostgreSQL

The helper script selects a configurable host port and preserves the command boundary:

```bash
POSTGRES_PORT=55432 ./scripts/postgres-up.sh
SPRING_PROFILES_ACTIVE=postgres \
POSTGRES_URL=jdbc:postgresql://localhost:55432/petclinic \
./mvnw spring-boot:run
./scripts/postgres-down.sh
```

## Run the application in Docker

Build the tested application JAR before building the container image:

```bash
./mvnw clean package
docker build -t ai-native-sdlc-petclinic:local .
docker run --rm -p 8080:8080 ai-native-sdlc-petclinic:local
```

In another terminal, verify `http://localhost:8080/` and `http://localhost:8080/reminders`. The Dockerfile pins the Java 17 runtime image by digest and runs the application as a non-root user.

## Perform the reminder check

1. Book a visit two days in the future.
2. Open `/reminders`.
3. Process due reminders.
4. Observe an `ACCEPTED` record with a masked destination. This proves local-adapter acceptance, not owner receipt.
5. Process again.
6. Confirm the existing count increases and no second reminder record appears.
7. Inspect `/actuator/metrics/petclinic.reminders.accepted`.

## What to record

- source revision;
- tool versions;
- commands;
- passed, failed, and not-run checks;
- functional observations;
- modified files;
- limitations;
- decisions requiring human authority.

Do not report the project as accepted merely because the commands complete.
