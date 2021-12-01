#!/usr/bin/env bash

./start-net.sh

workdir=$(dirname "$PWD")

cd $workdir/lura-gateway-server

PID=$(ps -ef | grep lura-gateway-server.jar | grep -v grep | awk '{ print $2 }')
if [ -z "$PID" ]
then
    echo Application is already stopped
else
    echo kill $PID
    kill $PID
fi





