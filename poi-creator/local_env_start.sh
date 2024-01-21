#!/bin/bash
podman run -i --rm \
  --name quarkus-poi-creator \
  -p 8080:8080 --network=mynetwork quarkus/quarkus-poi-creator