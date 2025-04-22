#!/bin/bash

# Clean the Gradle cache
echo "Cleaning Gradle cache..."
rm -rf $HOME/.gradle/caches/

# Clean the project
echo "Cleaning project..."
./gradlew clean

# Rebuild the project
echo "Rebuilding project..."
./gradlew build 