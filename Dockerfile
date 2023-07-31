FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /code
COPY ./ ./
RUN gradle bootJar

FROM openjdk:17
COPY --from=builder /code/build/libs/test-platform.jar /app/test-platform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/test-platform.jar"]
HEALTHCHECK --interval=15s --timeout=3s --retries=3 CMD curl -f -s http://localhost:8080/rest/actuator/health | grep -s -q "UP" || exit 1