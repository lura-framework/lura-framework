#!/usr/bin/env bash

workDir=$(dirname "$PWD")

# 启动nacos server
.$workDir/start-nacos-server.sh
#启动zipkin server
.$workDir/start-zipkin-server.sh
