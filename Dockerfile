# Use Eclipse Temurin JDK 21 image
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY . .

RUN chmod +x ./mvnw

RUN ./mvnw clean package -DskipTests

ENTRYPOINT ["java", "-jar", "target/coding-platform-backend-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=dev"]

