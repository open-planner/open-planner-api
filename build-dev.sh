#!/bin/bash
./mvnw clean install -DskipTests
docker build -f Dockerfile.dev -t open-planner-api .
