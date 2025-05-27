# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy all source files and pom.xml
COPY . .

# Build the project (skip tests for faster build)
RUN mvn clean install -DskipTests

# Stage 2: Run the application with minimal JDK image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Start the Spring Boot app
CMD ["java", "-Xmx256m", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]

