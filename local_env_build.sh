#!/bin/bash

podman stop -a &> /dev/null || true

MSYS_NO_PATHCONV=1 cd ${0%/*}/poi-creator

# Build Quarkus application 1
echo "Building Quarkus Persons of Interest Creator..."

./mvnw package -DskipTests

podman build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-poi-creator .

# Build Quarkus application 2
echo "Building Google Calendar API..."
MSYS_NO_PATHCONV=1 cd google-calendar-api

./mvnw package -DskipTests

podman build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-google-calendar .