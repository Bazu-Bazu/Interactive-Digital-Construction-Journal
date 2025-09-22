FROM openjdk:17-jdk-slim
WORKDIR /backend
COPY target/Interactive-Electronic-Journal-0.0.1-SNAPSHOT.jar backend.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "backend.jar"]