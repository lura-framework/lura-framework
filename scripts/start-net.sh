#!/usr/bin/env bash

echo 'create docker network: lura_net.'
networkNum=$(docker network ls | grep lura_net | grep -v grep |wc -l)

if [ "$networkNum" -gt 0 ]; then
  echo 'network already existed...'
fi
docker network create lura_net
