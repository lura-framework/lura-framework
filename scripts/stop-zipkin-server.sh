#!/usr/bin/env bash

workdir=$(dirname "$PWD")

cd $workdir/lura-zipkin-server || exit

docker-compose down





