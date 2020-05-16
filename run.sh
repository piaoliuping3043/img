#!/bin/sh

export HOME=/root/img



cd ${HOME}
nohup java -Xmx1024m -Xms1024m -XX:+PrintCommandLineFlags -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCApplicationStoppedTime -XX:+PrintHeapAtGC -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=500m -Xloggc:${HOME}/logs/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${HOME}/logs/img.dump -jar img-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &
