#!/bin/bash
podman run -i --rm \
  --name quarkus-google-calendar \
  -p 8080:8080 --network=mynetwork quarkus/quarkus-google-calendar