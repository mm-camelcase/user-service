# Use minimal OpenJDK 17 as the base image
# FROM openjdk:17-jdk-slim      # 260 MB
# 192 MB
#FROM amazoncorretto:17-alpine   
#FROM eclipse-temurin:17-jdk     # 237 MB
FROM openjdk:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by GitHub Actions into the Docker image
COPY build/libs/user-*-SNAPSHOT.jar /app/user.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the main jar file (avoiding the plain jar)
CMD ["sh", "-c", "java -jar /app/user.jar"]
