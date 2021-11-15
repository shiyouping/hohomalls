# HoHoMalls: An Online Shopping Platform

This project aims to provide online shopping malls to Hong Kong merchants to sell their services and products, and to integrate the Faster Payment System (FPS) to facilitate online transactions, and offer extraordinary shopping experiences to customers.

This project will cover server, web and mobile applications. Currently, the server application have been being developed. Any interested persons are welcome, especially web and mobile developers.

## Table of Content

- [1. Prerequisites](#1-prerequisites)
- [2. Run on the local environment](#2-run-on-the-local-environment)
  - [2.1. On local machine](#21-on-local-machine)
  - [2.2. On Docker](#22-on-docker)
  - [2.3. On Kubernetes](#23-on-kubernetes)
- [3. Run on the non-local environments](#3-run-on-the-non-local-environments)
  - [3.1. On Docker](#31-on-docker)
- [4. Infrastructure on the Cloud](#4-infrastructure-on-the-cloud)
- [5. TODO](#5-todo)

## 1. Prerequisites

To build and run the applications, make sure you have at least [JDK 17](http://openjdk.java.net/) installed.

## 2. Run on the local environment

There are three ways to run the applications.

Note that the account used on local mode is:

```markdown
Username: root
Password: Pa55w0rd
```

### 2.1. On local machine

#### 2.1.1. Update hosts file

Add the following mapping to the hosts file:

```markdown
127.0.0.1 hohomalls
```

#### 2.1.2. Configure MongoDB

- Install [MongoDB v4.4.x](https://www.mongodb.com/try/download)
- [Enable access control](https://docs.mongodb.com/v4.4/tutorial/enable-authentication/) with:

  ```javascript
  use admin
  db.createUser(
    {
      user: "root",
      pwd: "Pa55w0rd",
      roles: [ { role: "userAdminAnyDatabase", db: "admin" }, "readWriteAnyDatabase" ]
    }
  )
  ```

- Start MongoDB on port `27017`

#### 2.1.3. Configure Redis

- Install [Redis v6.2.x](https://redis.io/download)
- Start Redis on port `6379` with:

  ```bash
  redis-server --requirepass Pa55w0rd
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
  cd hohomalls/container/docker

  # Create and start containers
  docker-compose --env-file .env.local up -d

  # Stop and remove resources
  docker-compose --env-file .env.local down
  ```

### 2.3. On Kubernetes

- Install [Kubernetes](https://kubernetes.io/docs/setup/) and start it
- Install [Docker](https://www.docker.com/get-started) and start it
- Build the Docker image

  ```bash
  cd hohomalls/server
  ./gradlew clean bootBuildImage
  ```

## 3. Run on the non-local environments

The following environment variables have to be set before starting the applications:

```markdown
SPRING_PROFILES_ACTIVE = actual_value
SPRING_REDIS_HOST = actual_value
SPRING_REDIS_PASSWORD = actual_value
SPRING_DATA_MONGODB_HOST = actual_value
SPRING_DATA_MONGODB_USERNAME = actual_value
SPRING_DATA_MONGODB_PASSWORD = actual_value
COM_HOHOMALLS_TOKEN_PUBLIC-KEY = actual_value
COM_HOHOMALLS_TOKEN_PRIVATE-KEY = actual_value
```

### 3.1. On Docker

- Configure the variables in a Docker environment file

  ```bash
  cd hohomalls/container/docker
  touch .env
  vi .env
  ```

- Set the correct values in the environment file

  ```markdown
  REDIS_HOST=actual_value
  REDIS_PASSWORD=actual_value
  MONGODB_HOST=actual_value
  MONGODB_USERNAME=actual_value
  MONGODB_PASSWORD=actual_value
  TOKEN_PUBLIC_KEY=actual_value
  TOKEN_PRIVATE_KEY=actual_value
  ```

- Start and stop the containers

  ```bash
  cd hohomalls/container/docker

  # Create and start containers
  docker-compose up -d

  # Stop and remove resources
  docker-compose down
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
