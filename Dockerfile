FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/coding-platform-backend-0.0.1-SNAPSHOT.jar app.jar

# Here’s the critical line that tells Spring Boot to use the dev profile
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
