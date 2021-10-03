# HoHoMalls Server

The server application is built on top
of [Spring WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html)
and [GraphQL](https://graphql.org/) to provide a non-blocking and robust running environment.

## 1. Prerequisites

To build and run the server application, make sure you have at least [JDK 11](http://openjdk.java.net/) installed.

## 2. Development

It's recommended to install the following IDE plugins to assist development:

- [EditorConfig](https://editorconfig.org/#download) helps maintain consistent coding styles across various editors and
  IDEs
- [Google Java Format](https://github.com/google/google-java-format) reformats Java source code to comply with Google
  Java Style
- [checkstyle](https://checkstyle.sourceforge.io/index.html) helps programmers write Java code that adheres to Google
  Java Style
- [PMD](https://pmd.github.io/#plugins) finds common programming flaws like unused variables, empty catch blocks,
  unnecessary object creation, and so forth.

Notice that Gradle build will fail if the Java source code doesn't pass the check of checkstyle and PMD.

## 3. How to run on local mode

There are three ways to run the server application.

Notice that the credentials used on local mode are `root`(username) and `P@55w0rd`(password).

### 3.1. On local machine

#### 3.1.1. Update hosts file

Add the following adresses to the hosts file:

````
127.0.0.1 hohomalls 
127.0.0.1 hohomalls-mongo 
127.0.0.1 hohomalls-redis
````

#### 3.1.2. Configure MongoDB

- Install [MongoDB v4.4.x](https://www.mongodb.com/try/download)
- Run MongoDB on port `27017`
- [Enable access control](https://docs.mongodb.com/v4.4/tutorial/enable-authentication/) with:
    ````
    Username: root
    Password: P@55w0rd
    Auth database: admin
    ````

#### 3.1.3. Configure Redis

- Install [Redis v6.2.x](https://redis.io/download).
- Run Redis on port `6379`
- [Enable access control](https://stackink.com/how-to-set-password-for-redis-server/) with:
    ````
    # Update redis.conf
    requirepass P@55w0rd
    ````

#### 3.1.4. Start the app

Make sure port `8080` is available and then execute:

````bash 
cd hohomalls/server 
./gradlew bootRun --args='--spring.profiles.active=local'
````

### 3.2. On Docker

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

### 3.3. On Kubernetes

- Install [Kubernetes](https://kubernetes.io/docs/setup/) and run it
- Build the Docker image
    ````bash
    cd hohomalls/server
    ./gradlew clean bootBuildImage
    ````

## 4. Staging and Production environments

The following environment variables have to be set before running:

````
SPRING_REDIS_PASSWORD=actual_value_1;
SPRING_DATA_MONGODB_USERNAME=actual_value_2;
SPRING_DATA_MONGODB_PASSWORD=actual_value_3;
COM_HOHOMALLS_TOKEN_PUBLIC-KEY=actual_value_4;
COM_HOHOMALLS_TOKEN_PRIVATE-KEY=actual_value_5;
````

### 4.1. On Docker

- Configure the variables in a Docker environment file
    ````bash
    cd hohomalls/server/docker
    touch .env.prod
    vi .env.prod
    ````

- Set the correct values in the environment file
    ````
    REDIS_PASSWORD=actual_value_1
    MONGODB_USERNAME=actual_value_2
    MONGODB_PASSWORD=actual_value_3
    TOKEN_PUBLIC_KEY=actual_value_4
    TOKEN_PRIVATE_KEY=actual_value_5
    ````

- Start and stop the server
    ````bash
    cd hohomalls/server
    
    # Create and start containers
    docker-compose --env-file docker/.env.prod -f docker/compose-prod.yml up -d
    
    # Stop and remove resources
    docker-compose --env-file docker/.env.prod -f docker/compose-prod.yml down
    ````

Notice that if you are using `Docker Swarm`, you may want to
use [Docker Secrets](https://docs.docker.com/engine/swarm/secrets/) to manage the above sensitive data. In this case,
you have to update the Docker compose files yourself.
