# Use minimal JDK 17 as the base image
FROM amazoncorretto:17-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by GitHub Actions into the Docker image
COPY build/libs/user-*-SNAPSHOT.jar /app/user.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the main jar file (avoiding the plain jar)
CMD ["sh", "-c", "java -jar /app/user.jar"]
