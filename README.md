# HoHoMalls: An Online Shopping Platform

This project aims to provide online shopping malls to Hong Kong merchants to sell their services and products, and to integrate the Faster Payment System (FPS) to facilitate online transactions, and offer extraordinary shopping experiences to customers.

This project will cover server, web and mobile applications. Currently, the server application have been being developed. Any interested persons are welcome, especially web and mobile developers.

## 1. Prerequisites

To build and run the applications, make sure you have at least [JDK 11](http://openjdk.java.net/) installed.

## 2. How to run on local mode

There are three ways to run the applications.

Note that the account used on local mode is `root`(username) and `Pa55w0rd`(password).

### 2.1. On local machine

#### 2.1.1. Update hosts file

Add the following adresses to the hosts file:

```markdown
127.0.0.1 hohomalls
127.0.0.1 hohomalls-mongo
127.0.0.1 hohomalls-redis
```

#### 2.1.2. Configure MongoDB

- Install [MongoDB v4.4.x](https://www.mongodb.com/try/download)
- Start MongoDB on port `27017`
- [Enable access control](https://docs.mongodb.com/v4.4/tutorial/enable-authentication/) with:

    ```markdown
    Username: root
    Password: Pa55w0rd
    Auth database: admin
    ```

#### 2.1.3. Configure Redis

- Install [Redis v6.2.x](https://redis.io/download)
- Start Redis on port `6379`
- [Enable access control](https://stackink.com/how-to-set-password-for-redis-server/) with:

    ```markdown
    # Update redis.conf
    requirepass Pa55w0rd
    ```

#### 2.1.4. Start the applications

Make sure port `8080` is available and then start with:

```bash
cd hohomalls/server
./gradlew bootRun --args='--spring.profiles.active=local'
```

### 2.2. On Docker

- Install [Docker](https://www.docker.com/get-started) and start it
- Build the Docker image

    ```bash
    cd hohomalls/server
    ./gradlew clean bootBuildImage
    ```

- Start and stop the containers

    ```bash
    cd hohomalls

    # Create and start containers
    docker-compose -f docker/compose-local.yml up -d

    # Stop and remove resources
    docker-compose -f docker/compose-local.yml down
    ```

### 2.3. On Kubernetes

- Install [Kubernetes](https://kubernetes.io/docs/setup/) and start it
- Install [Docker](https://www.docker.com/get-started) and start it
- Build the Docker image

    ```bash
    cd hohomalls/server
    ./gradlew clean bootBuildImage
    ```

## 3. Staging and Production environments

The following environment variables have to be set before starting the applications:

```markdown
SPRING_REDIS_PASSWORD=actual_value_1;
SPRING_DATA_MONGODB_USERNAME=actual_value_2;
SPRING_DATA_MONGODB_PASSWORD=actual_value_3;
COM_HOHOMALLS_TOKEN_PUBLIC-KEY=actual_value_4;
COM_HOHOMALLS_TOKEN_PRIVATE-KEY=actual_value_5;
```

### 3.1. On Docker

- Configure the variables in a Docker environment file

    ```bash
    cd hohomalls/docker
    touch .env.prod
    vi .env.prod
    ```

- Set the correct values in the environment file

    ```markdown
    REDIS_PASSWORD=actual_value_1
    MONGODB_USERNAME=actual_value_2
    MONGODB_PASSWORD=actual_value_3
    TOKEN_PUBLIC_KEY=actual_value_4
    TOKEN_PRIVATE_KEY=actual_value_5
    ```

- Create required directory and files

    ````bash
    mkdir -p /opt/docker/mongo/data
    cp -r hohomalls/docker/mongo /opt/docker
    
    mkdir -p /opt/docker/redis/data
    cp -r hohomalls/docker/redis /opt
    
    mkdir /opt/docker/hohomalls
    ````

- Start and stop the containers

    ```bash
    cd hohomalls

    # Create and start containers using production configurations
    docker-compose --env-file docker/.env.prod -f docker/compose-prod.yml up -d

    # Stop and remove resources
    docker-compose --env-file docker/.env.prod -f docker/compose-prod.yml down
    ```

Note that if you are using `Docker Swarm`, you may want to
use [Docker Secrets](https://docs.docker.com/engine/swarm/secrets/) to manage the above sensitive data. In this case,
you have to update the Docker compose files yourself.

## 4. Infrastructure on the Cloud

If the applications will be running on the cloud, e.g. AWS VPC, the following is the recommended infrastructure:

![AWS Infrastructure](/docs/aws-infrastructure.png)

## 5. TODO

- Use MongoDB replication mode
- Use Redis replication mode
- Aggregate logs from different instances
