#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Colors for better output visibility
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${GREEN}Starting the application setup...${NC}"

# Step 1: Ensure required tools are available
if ! which java > /dev/null; then
  echo -e "${RED}Error: Java is not installed. Please install OpenJDK 23 or later.${NC}"
  exit 1
fi

if ! which mvn > /dev/null; then
  echo -e "${RED}Error: Maven is not installed. Please install Maven to build the project.${NC}"
  exit 1
fi

# Step 2: Clean and build the project
echo -e "${GREEN}Cleaning and building the Maven project...${NC}"
mvn clean package -DskipTests

# Step 3: Run the application
JAR_FILE="target/hotel-room-allocation-0.0.1-SNAPSHOT.jar"

if [[ ! -f "$JAR_FILE" ]]; then
  echo -e "${RED}Error: $JAR_FILE not found! Maven build might have failed.${NC}"
  exit 1
fi

echo -e "${GREEN}Starting the application...${NC}"
java -jar "$JAR_FILE"

# Exit with success
echo -e "${GREEN}Application started successfully!${NC}"
