FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/coding-platform-backend-0.0.1-SNAPSHOT.jar app.jar
RUN ./mvnw clean package -DskipTests

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=dev"]
