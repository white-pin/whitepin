#!/usr/bin/env bash

SCRIPT_PATH=$( cd "$(dirname "$0")" ; pwd -P )
PROJECT=${1}
JAR_NAME=${PROJECT}-0.1.jar
JAR_PATH=${SCRIPT_PATH}/../${PROJECT}/target/${JAR_NAME}

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

if [[ -z ${PROJECT} ]]; then
    echo "start.sh [PROJECT] e.g) start.sh [server|frontend]"
    exit 1
elif [[ "${PROJECT}" != "server" && "${PROJECT}" != "frontend" ]]; then
    echo "project name arg must be server or frontend"
    exit 1
fi

check_server_pid

if [[ -z ${PID} ]]; then
    #java -Dspring.profiles.active=${ACTIVE_PROFILES} -jar ${JAR_PATH} --spring.config.location=${SPRING_CONFIG_PATH}> /dev/null 2>&1 &
    nohup java -Dspring.profiles.active=dev -jar ${JAR_PATH}  1>> ${SCRIPT_PATH}/../logs/${PROJECT}.log 2>&1 & echo $! > ${SCRIPT_PATH}/../${PROJECT}.pid
    PID=$( cat ${SCRIPT_PATH}/../${PROJECT}.pid )
    echo "Success to start ${PROJECT}. pid : ${PID}"
    exit 0
else
  echo "Already ${PROJECT} server is running. pid : ${PID}"
  exit 1
fi
