#!/usr/bin/env bash

projectDir=$(dirname "$PWD")
cd $projectDir

./gradlew clean :lura-framework-spring-boot-starter:publishToMavenLocal
