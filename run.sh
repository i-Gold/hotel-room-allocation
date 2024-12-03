#!/bin/bash

# Ensure the script is executable
# chmod +x run.sh

# Build the application (Maven)
echo "Building the application..."
mvn clean install

# Build Docker image
echo "Building Docker image..."
docker build -t hotel-room-allocation .

# Run Docker container
echo "Running the application in Docker..."
docker run -p 8080:8080 hotel-room-allocation

# Ensure the application is running
echo "Application should now be running at http://localhost:8080"
