#!/usr/bin/env bash

SCRIPT_PATH=$(cd "$(dirname $0)" && pwd)

chmod +x ${SCRIPT_PATH}/../mvnw
${SCRIPT_PATH}/../mvnw -Dmaven.test.skip=true clean package
