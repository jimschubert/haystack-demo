# haystack demo

## Build

```bash
./gradlew assemble
docker build -t hsd .
docker run -p 8080:8080 hsd
```

## Startup

Make sure you run `git submodules init`.

Next, start up the "official" haystack demo code:

```bash
docker-compose -f haystack-docker/docker-compose.yml \
               -f haystack-docker/traces/docker-compose.yml \
               -f haystack-docker/trends/docker-compose.yml \
               -f haystack-docker/service-graph/docker-compose.yml \
               -f haystack-docker/adaptive-alerting/docker-compose.yml \
               -f haystack-docker/example/traces/docker-compose.yml up
```

A simpler (less memory/cpu intensive) example can be started with trends and alerting:

```bash
docker-compose -f haystack-docker/docker-compose.yml \
               -f haystack-docker/traces/docker-compose.yml \
               -f haystack-docker/service-graph/docker-compose.yml \
               -f haystack-docker/example/traces/docker-compose.yml
```

Then, start this app one of two ways…

Docker:
```bash
docker run -p 8888:8080 --net haystack-docker_default -e "OTHER_SERVICE=http://frontend:9090/hello" -e "HAYSTACK_AGENT_HOST=haystack-agent" -e "SPRING_PROFILES_ACTIVE=remote"  hsd
```

Via IDE after configuring these environment variables:
```bash
OTHER_SERVICE=http://localhost:9090/hello
HAYSTACK_AGENT_HOST=localhost
```

## Queries

* http://localhost:8888/v1/products
  - Default: offset=0,limit=20
* http://localhost:8888/v1/products?offset=0&limit=1
  - "Special" case, queries 2 records at a time, 10 times (serial database calls)
* http://localhost:8888/v1/other
  - Makes a request to the `frontend` API from official Haystack Docker demo
  
Other queries are available in the [Postman collection](./postman/Haystack%20Demo.postman_collection.json).

## Inspecting

This project exposes actuator. You can view specifics about environment, metrics, etc.

Example:
http://localhost:8888/actuator/metrics/com.expedia.www.haystack.client.dispatchers.Dispatcher.dispatch

Want to change log levels at runtime? Try the following:

```bash
curl -i -X POST -H 'Content-Type: application/json' -d '{"configuredLevel": "TRACE"}' http://localhost:8888/actuator/loggers/io.opentracing
```

## Haystack

After starting up the Haystack Infrastructure/Demo and this repository demo, load Haystack's UI at http://localhost:8080/. 

Services will display in the UI only after requests are made.

Services will display in the service graph if they are connected to at least one other service (you must hit `/v1/other` for this repo's service to display in the graph).
