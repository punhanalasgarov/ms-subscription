FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
COPY src /app/src

RUN ./gradlew build
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar ms-subscription.jar
ENTRYPOINT ["java", "-jar", "ms-subscription.jar"]
