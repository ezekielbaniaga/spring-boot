#!/bin/sh
./mvnw clean install && \
docker build -t spring-boot-sample:1.0 .
