# Demo App

This project is a simple Spring Boot application with a REST API connected to an H2 in-memory database. It can be used for testing CICD pipelines & deployments.

## Requirements

- **Java 17 or above**
- **Gradle** (Optional if using the Gradle wrapper provided in the project)

## Project Structure

This project contains a basic CRUD API with an H2 in-memory database. It can be run locally using the Gradle wrapper (`./gradlew`) provided in the project.

## Common Gradle Commands

Here are some commonly used Gradle commands for managing your Spring Boot project:

``./gradlew bootRun`` - Runs the Spring Boot application. App available at http://localhost:8080   
``./gradlew build`` - Builds the project and packages it into a JAR.  
``./gradlew test`` - Runs the unit tests.  
``./gradlew clean`` - Cleans the project by removing the build directory.  
``./gradlew bootJar`` - Packages the application as an executable JAR.  
``./gradlew dependencies`` - Displays the dependency tree for your project.  



## Accessing the H2 Database Console

The H2 in-memory database can be accessed via the H2 console. Once the application is running, go to:

```bash
http://localhost:8080/h2-console
```

Use the following credentials:

**JDBC URL**: `jdbc:h2:mem:testdb`  
**Username**: `sa`  
**Password**: `password` 



## Troubleshooting
If you encounter issues with the build or dependencies, you can run the following command to refresh the Gradle dependencies:

```bash
./gradlew clean build --refresh-dependencies
```

This will force Gradle to re-download the dependencies and rebuild the project.


## CI/CD Pipeline

This project uses a GitHub Actions pipeline to automate building, packaging, and deployment:

1. **Build**: The project is built using Gradle to generate the JAR file.
2. **Docker**: A Docker image is built from the JAR and pushed to the GitHub Container Registry (GHCR).
3. **Helm**: A Helm chart is versioned, packaged, and published to GitHub Packages.

![pipeline](.artifacts/build_deploy.png)







