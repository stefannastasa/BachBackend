# Use an official Gradle image to build the application
FROM gradle:7.5.1-jdk17 AS build

# Set the working directory
WORKDIR /home/gradle/src

# Copy the Gradle wrapper and build files
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

# Copy the application source
COPY src ./src

# Build the application
RUN ./gradlew build --no-daemon

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
