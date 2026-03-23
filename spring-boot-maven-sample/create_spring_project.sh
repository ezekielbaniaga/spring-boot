#!/bin/bash

PACKAGE=ezekiel.baniaga.springboot.maven
DIR=backend

if [ -d "$DIR" ]; then
	echo "$DIR exists."
else
	spring init --artifact-id=$DIR --groupId=$PACKAGE --bootVersion=4.0.3 --dependencies=web --packaging=jar --type=maven-project --javaVersion=17 $DIR
fi



