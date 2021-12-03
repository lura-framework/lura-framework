#!/usr/bin/env bash

workdir=$(dirname "$PWD")

cd $workdir/devops

cd mysql

docker-compose down

cd ../redis

docker-compose down

