# Use the official OpenJDK image for Java 23
FROM eclipse-temurin:23-jdk-jammy

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies first
COPY pom.xml .

# Download project dependencies with Maven
RUN mvn dependency:go-offline

# Copy the entire source code into the container
COPY . .

# Build the project with Maven and skip the tests
RUN mvn clean package -DskipTests

# Expose the port the application will run on
EXPOSE 8080

# Run the Spring Boot application using the generated jar file
CMD ["java", "-jar", "target/hotel-room-allocation-0.0.1-SNAPSHOT.jar"]
