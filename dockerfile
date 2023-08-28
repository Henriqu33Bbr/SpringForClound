# BuildStage

FROM maven:3.8.3-openjdk-17 as build

COPY . .
RUN mvn clear package -DskipTests

# Package Stage
FROM openjdk:17-jdk-slim

COPY --from=build /target/barba-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090
ENTRYPOINT [ "java", "-jar", "app.jar" ]