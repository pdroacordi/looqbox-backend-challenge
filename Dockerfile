FROM gradle:7.6-jdk17 AS builder

WORKDIR /app

COPY --chown=gradle:gradle looqbox-backend-challenge/ .

RUN gradle bootJar --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/looqbox-backend-challenge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
