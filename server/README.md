# HoHoMalls Server

Server applications are built on top of Spring WebFlux and GraphQL to provide a non-blocking and robust running
environment.

## Prerequisites

To build and run the server application, make sure you have at least [JDK 11](http://openjdk.java.net/)
and [Docker](https://www.docker.com/products/docker-desktop) installed.

## Build the server

Git clone or download the source code, and then execute the following commands:

````bash
cd hohomalls/server
./gradlew clean bootBuildImage
````

## Set database confidential

### MongoDB

### Redis

## Start and stop the server

````bash
cd hohomalls/server

# Create and start containers
docker-compose -f docker/compose-local.yml up -d

# Stop and remove resources
docker-compose -f docker/compose-local.yml down
````

## TODO