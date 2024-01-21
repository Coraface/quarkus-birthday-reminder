#!/bin/bash

podman container stop -a && podman container rm -a&> /dev/null || true

# Start Kafka and ZooKeeper
MSYS_NO_PATHCONV=1 docker-compose -f docker-compose/kafka-docker-compose.yaml up -d

# Start Jaeger
MSYS_NO_PATHCONV=1 docker-compose -f docker-compose/jaeger-docker-compose.yaml up -d

MSYS_NO_PATHCONV=1 cd ${0%/*}/poi-creator

# Start Postgres
MSYS_NO_PATHCONV=1 podman run -d --rm \
  --name persons-of-interest-db \
  -p 5432:5432 \
  --network=mynetwork \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -v `pwd`/src/main/resources/scripts:/scripts:ro \
  postgres:15.2
  
echo "[Waiting for db startup]"
until podman exec persons-of-interest-db pg_isready -h localhost > /dev/null; do
  sleep 0.5
done;

echo '[Loading data into database]'
MSYS_NO_PATHCONV=1 podman exec persons-of-interest-db \
  psql -U postgres -f scripts/schema-load-data.sql
  
echo "[Running Quarkus Persons of Interest Creator...]"
podman run -d --rm \
  --name quarkus-poi-creator \
  -p 8080:8080 --network=mynetwork \
  quarkus/quarkus-poi-creator

# Wait for a moment to ensure the first application is up
sleep 10

echo "[Running Quarkus Google Calendar API...]"
podman run -d --rm \
  --name quarkus-google-calendar \
  -p 8081:8080 --network=mynetwork \
  quarkus/quarkus-google-calendar &