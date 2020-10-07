FROM maven:latest AS build_backend
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:resolve
COPY src src
RUN mv src/main/resources/application-docker.properties src/main/resources/application.properties && mvn package -DskipTests

FROM openjdk:12
COPY --from=build_backend /app/target/backend-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.jar"]
