#!/bin/bash
./mvnw clean package -DskipTests
podman build -f src/main/docker/Dockerfile.jvm -t quarkus/quarkus-poi-creator .