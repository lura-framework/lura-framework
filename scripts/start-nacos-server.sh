#!/usr/bin/env bash

./start-net.sh

workdir=$(dirname "$PWD")

cd $workdir/lura-nacos-server

docker-compose up -d





