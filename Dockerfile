# Use the official OpenJDK image as a parent image
FROM openjdk:8-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged JAR file into the container
COPY target/dispatch-api-0.0.1-SNAPSHOT.jar /app/dispatch-api-0.0.1-SNAPSHOT.jar
EXPOSE 9082
# Run the JAR file
CMD ["java", "-jar", "dispatch-api-0.0.1-SNAPSHOT.jar"]