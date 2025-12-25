# Use JDK 21 slim image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built JAR
COPY target/userservice-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional, good for docs)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
