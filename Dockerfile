# Stage 1: Build the jar with Maven
FROM maven:3.8.7-openjdk-17-slim AS build

WORKDIR /app

# Copy source files
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn clean package -DskipTests

# Stage 2: Run the jar
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT:-8080}", "-jar", "app.jar"]
