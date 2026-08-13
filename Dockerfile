FROM eclipse-temurin:17-jre-jammy@sha256:475d8e96b4b2bfe08999e5e854755c773af1581acdf959a4545d88f0696a2339

RUN groupadd --system petclinic \
    && useradd --system --gid petclinic --home-dir /app --shell /usr/sbin/nologin petclinic

WORKDIR /app
COPY --chown=petclinic:petclinic target/spring-petclinic-4.0.0-SNAPSHOT.jar app.jar

USER petclinic
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
