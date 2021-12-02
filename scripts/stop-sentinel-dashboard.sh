#!/usr/bin/env bash

workdir=$(dirname "$PWD")

cd $workdir/lura-sentinel-dashboard

docker-compose up -d
