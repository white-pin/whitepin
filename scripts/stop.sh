#!/usr/bin/env bash

SCRIPT_PATH=$( cd "$(dirname "$0")" ; pwd -P )
PROJECT=${1}
JAR_NAME=${PROJECT}-0.1.jar
JAR_PATH=${SCRIPT_PATH}/../${PROJECT}/target/${JAR_NAME}
PID=

function check_server_pid() {
    local server_pid="${SCRIPT_PATH}/../${PROJECT}.pid"

    if [[ -f "$server_pid" ]]; then
        if [[ -s "$server_pid" ]]; then
            local server_pid=$(cat ${server_pid})
            if [[ ! -z ${server_pid} ]]; then
              PID=$(ps -p ${server_pid} | tail -1 | grep -v grep | grep -v vi | grep -v PID | awk '{print $1}')
            fi
        fi
    fi

    if [[ -z ${PID} ]]; then
      PID=$(ps -ef | grep ${JAR_NAME} | grep -v grep | grep -v vi | grep -v PID | awk '{print $2}')
    fi
}

check_server_pid

if [[ ! -z ${PID} ]]; then
    $(kill -9 ${PID})
    if [[ -f ${SERVER_PID_FILE} ]]; then
        $(rm ${SERVER_PID_FILE})
    fi
    echo "Success to stop ${PROJECT} server"
    exit 0
else
    echo "Failed to stop ${PROJECT} server because is not running"
    exit 1
fi


