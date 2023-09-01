FROM eclipse-temurin:17-jdk-focal

WORKDIR /app

COPY target/city-0.0.1-SNAPSHOT.jar city-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","city-0.0.1-SNAPSHOT.jar"]