#!/bin/bash
docker rm -f open-planner-db
sudo rm -rf .docker-volumes/open-planner-db
docker-compose up -d open-planner-db
