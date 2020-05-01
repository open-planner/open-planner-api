#!/bin/bash
docker rm -f open-planner-db-test
docker run --name open-planner-db-test -p 5432:5432 \
  -e POSTGRES_DB=open-planner \
  -e POSTGRES_USER=user.auth \
  -e POSTGRES_PASSWORD=user.pass \
  -d postgres:10-alpine
