# Use Zulu OpenJDK 17 as the base image
FROM azul/zulu-openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the build.gradle and settings.gradle files
COPY build.gradle settings.gradle /app/

# Copy the gradle wrapper files
COPY gradlew /app/
COPY gradle /app/gradle

# Copy the source code
COPY src /app/src

# Set permissions to execute the Gradle wrapper
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew build

# Expose the port that the application will run on
EXPOSE 8080

# Run the main jar file (avoiding the plain jar)
CMD ["sh", "-c", "java -jar /app/build/libs/user-*-SNAPSHOT.jar"]
