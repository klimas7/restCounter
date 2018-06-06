#!/bin/bash

mkdir -p log

command=$1

printUsage() {
    echo "Usage:"
    echo "restCounter.sh start"
    echo "restCounter.sh stop"
}

start() {
    java -jar restCounter.jar >> log/output.log  &
}

stop() {
    processPID=$(ps aux | grep java | grep restCounter | awk '{print $2}')
    echo "kill restCounter process PID: "$processPID
    kill -9 $processPID 2>&1 > /dev/null
}

case $command in
    "start")
        start
        ;;
    "stop")
        stop
        ;;
    *)
        printUsage
esac