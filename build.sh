#!/bin/bash
mvn clean install
docker build -t open-planner-api .
