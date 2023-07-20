FROM gradle:8.2.1-jdk17 AS builder
WORKDIR /code
COPY . .
RUN gradle clean bootJar

FROM openjdk:17
COPY --from=builder /code/build/libs/test-platform.jar /app/test-platform.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/app/test-platform.jar"]