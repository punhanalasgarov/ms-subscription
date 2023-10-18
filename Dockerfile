FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR open-weather
COPY --from=build target/*.jar exchange-rate-service.jar
ENTRYPOINT ["java", "-jar", "ms-subscription.jar"]