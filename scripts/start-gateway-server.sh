#!/usr/bin/env bash

./start-net.sh

workdir=$(dirname "$PWD")

cd $workdir/lura-gateway-server

gradle bootJar

nohup java -jar ./build/libs/lura-gateway-server.jar &
