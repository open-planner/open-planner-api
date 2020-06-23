#!/bin/bash
./mvnw clean install -DskipTests
docker build -t open-planner-api .

