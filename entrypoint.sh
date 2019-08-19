#!/usr/bin/env sh

set +x

SPRING_PROFILE=${SPRING_PROFILE:-remote}
JAVA_XMS=${JAVA_XMS:-128m}
JAVA_XMX=${JAVA_XMX:-512m}
APP_NAME=${APP_NAME:-haystack-demo}
APP_HOME=${APP_HOME:-/app}
APP_MODE=${APP_MODE:-remote}

JAVA_OPTS="${JAVA_OPTS}
-XX:+UseG1GC
-Xloggc:/var/log/gc.log
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+UseGCLogFileRotation
-XX:NumberOfGCLogFiles=5
-XX:GCLogFileSize=2M
-Xmx${JAVA_XMX}
-Xms${JAVA_XMS}
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
-Dcom.sun.management.jmxremote.port=1098
-Dspring.profiles.active=${SPRING_PROFILE}"

# shellcheck disable=SC2086
exec java ${JAVA_OPTS} -jar "${APP_HOME}/${APP_NAME}.jar" ${APP_MODE}

