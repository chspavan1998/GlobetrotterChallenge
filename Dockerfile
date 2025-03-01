# Use an official OpenJDK runtime as a parent image.
FROM openjdk:11-jre-slim

# Set the working directory in the container.
WORKDIR /app

# Copy the executable jar file from the target folder to the container.
COPY target/globetrotter-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application will run on.
EXPOSE 8080

# Define the command to run your application.
CMD ["java", "-jar", "app.jar"]
