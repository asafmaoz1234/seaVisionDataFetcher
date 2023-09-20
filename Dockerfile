FROM maven:3.8.5-openjdk-18 AS build
WORKDIR /seaVisionDataFetcher
# Copy the pom.xml and source code to the image
COPY pom.xml ./
COPY src ./src/

# Build the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK 18 image to run the app
FROM openjdk:18-jdk

# Copy the jar file built in the build stage
COPY --from=build /seaVisionDataFetcher/target/seaVisionDataFetcher-0.0.1-SNAPSHOT.jar /application.jar

# Set the startup command
ENTRYPOINT ["java", "-jar", "application.jar"]