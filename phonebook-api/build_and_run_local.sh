#!/bin/bash

# Define variables
APP_NAME="phonebook-api"
JAR_FILE="target/phonebook-api-1.0-SNAPSHOT.jar"
SERVER_PORT=8080

# Step 1: Clean and package the application using Maven
echo "Cleaning and packaging the application with Maven..."
mvn clean package

# Check if Maven build was successful
if [ $? -ne 0 ]; then
  echo "Maven build failed. Exiting."
  exit 1
fi

# Step 2: Run the application
echo "Running the application..."
java -jar $JAR_FILE

# Check if the application started successfully
if [ $? -ne 0 ]; then
  echo "Failed to start the application. Exiting."
  exit 1
fi

echo "Application is running at http://localhost:$SERVER_PORT"
