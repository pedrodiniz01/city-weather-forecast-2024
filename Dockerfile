FROM maven:3.8.4 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=builder /app/target/weatherforecast-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "weatherforecast-0.0.1-SNAPSHOT.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]