#!/bin/bash
./gradlew clean build
java -jar build/libs/kartvelang-1.0-SNAPSHOT.jar
exit 0
