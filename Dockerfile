FROM openjdk:8-alpine
MAINTAINER Jim Schubert <james.schubert@gmail.com>

ENV APP_NAME haystack-demo
ENV APP_HOME /app/bin

COPY build/libs/${APP_NAME}.jar ${APP_HOME}/
COPY entrypoint.sh /

WORKDIR ${APP_HOME}

ENTRYPOINT /entrypoint.sh