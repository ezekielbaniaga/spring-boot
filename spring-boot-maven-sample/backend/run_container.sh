#!/bin/sh

# This is for running spring-boot-sample only,
# use docker compose to run other components
# such as postgres

docker rm spring-boot-sample:1.0 2>/dev/null || true
docker run --name=spring-boot-sample -p 8080:8080 spring-boot-sample:1.0
