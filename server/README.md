# HoHoMalls Server

The server application is built on top
of [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
and [GraphQL](https://graphql.org/) to provide a non-blocking and robust running environment.

## 1. Prerequisites

To build and run the server application, make sure you have at least [JDK 11](http://openjdk.java.net/) installed.

## 2. How to run on local mode

There are three ways to run the server application.

Notice that the credentials used on local mode are `root`(username) and `P@55w0rd`(password).

### 2.1. On local machine

#### 2.1.1. Update hosts file

Add the following adresses to the hosts file:

````
127.0.0.1 hohomalls 
127.0.0.1 hohomalls-mongo 
127.0.0.1 hohomalls-redis
````

#### 2.1.2. Configure MongoDB

- Install [MongoDB v4.4.x](https://www.mongodb.com/try/download)
- Run MongoDB on port `27017`
- [Enable access control](https://docs.mongodb.com/v4.4/tutorial/enable-authentication/) with:
    ````
    Username: root
    Password: P@55w0rd
    Auth database: admin
    ````

#### 2.1.3. Configure Redis

- Install [Redis v6.2.x](https://redis.io/download).
- Run Redis on port `6379`
- [Enable access control](https://stackink.com/how-to-set-password-for-redis-server/) with:
    ````
    # Update redis.conf
    requirepass P@55w0rd
    ````

#### 2.1.4. Start the app

Make sure port `8080` is available and then execute:

````bash 
cd hohomalls/server 
./gradlew bootRun --args='--spring.profiles.active=local'
````

### 2.2. On Docker

- Install [Docker](https://www.docker.com/get-started) and run it
- Build the Docker image
    ````bash
    cd hohomalls/server
    ./gradlew clean bootBuildImage
    ````
- Start and stop the server
    ````bash
    cd hohomalls/server
    
    # Create and start containers
    docker-compose -f docker/compose-local.yml up -d
    
    # Stop and remove resources
    docker-compose -f docker/compose-local.yml down
    ````

### 2.3. On Kubernetes

- Install [Kubernetes](https://kubernetes.io/docs/setup/) and run it
- Build the Docker image
    ````bash
    cd hohomalls/server
    ./gradlew clean bootBuildImage
    ````

## 3. Non-local configurations

The following environment variables have to be set for production and staging deployment:

````
SPRING_REDIS_PASSWORD=actual_value_1;
SPRING_DATA_MONGODB_USERNAME=actual_value_2;
SPRING_DATA_MONGODB_PASSWORD=actual_value_3;
COM_HOHOMALLS_TOKEN_PUBLIC-KEY=actual_value_4;
COM_HOHOMALLS_TOKEN_PRIVATE-KEY=actual_value_5;
````