# Use the official OpenJDK 17 image from the Docker Hub
FROM openjdk:17-oracle

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file from target folder to the Docker image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Expose the port application uses
EXPOSE 8080
