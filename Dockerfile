# Build stage
FROM maven:3.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies separately to leverage Docker cache
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Run stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root user
RUN addgroup --system appuser && adduser --system --ingroup appuser appuser

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Security: run as non-root user
USER appuser

# Set environment variables
ENV JAVA_OPTS="-Xms128m -Xmx256m"
ENV API_KEY="your-secret-api-key"

# Health check
HEALTHCHECK --interval=30s --timeout=3s CMD wget -q -O /dev/null http://localhost:8080/actuator/health || exit 1

# Expose the port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]