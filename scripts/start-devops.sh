#!/usr/bin/env bash

workdir=$(dirname "$PWD")

cd $workdir/devops

cd mysql

docker-compose up -d

cd ../redis

docker-compose up -d

