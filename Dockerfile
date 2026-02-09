FROM maven:3.10.1-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy pom first for dependency caching
COPY pom.xml ./

# Copy source and build
COPY src ./src

# Build the application (skip tests for faster image builds)
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
