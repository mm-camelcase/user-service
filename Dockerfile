# Stage 1: Base image with Gradle for dependency caching
FROM gradle:8.3-jdk17 AS base
WORKDIR /app

# Copy Gradle wrapper and project files required for dependency resolution
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle /app/build.gradle
COPY settings.gradle /app/settings.gradle

# Pre-fetch Gradle dependencies to cache them
RUN ./gradlew dependencies --no-daemon

# Stage 2: Build native image using GraalVM
FROM ghcr.io/graalvm/graalvm-ce:ol8-java17 AS builder
WORKDIR /app

# Copy pre-fetched dependencies from the base stage
COPY --from=base /app /app

# Copy the rest of the source code
COPY src /app/src

# Make the Gradle wrapper executable
RUN chmod +x ./gradlew

# Build the native image
RUN ./gradlew nativeCompile --no-daemon

# Stage 3: Minimal runtime image
#FROM gcr.io/distroless/base
#FROM alpine:latest
FROM frolvlad/alpine-glibc:latest
WORKDIR /app

# Copy the native executable from the builder stage
COPY --from=builder /app/build/native/nativeCompile/user /app/user

# Expose the application port
EXPOSE 8080

# Set the entrypoint to run the native binary
ENTRYPOINT ["/app/user"]
