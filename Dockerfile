# Stage 1: Build
FROM gradle:8.3-jdk17 AS builder
WORKDIR /app

# Copy Gradle files for dependency caching
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Pre-fetch dependencies
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon

# Stage 2: Runtime
FROM gcr.io/distroless/java17
WORKDIR /app

# Copy the JAR from the build stage
COPY --from=builder /app/build/libs/user-*-SNAPSHOT.jar /app/user.jar

# Expose the port
EXPOSE 8080

# Set the command to run the application
CMD ["user.jar"]
