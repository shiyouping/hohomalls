# HoHoMalls: An Online Shopping Platform

This project aims to provide online shopping malls to Hong Kong merchants to sell their services and products, and to integrate the Faster Payment System (FPS) to facilitate online transactions, and offer extraordinary shopping experiences to customers.

This project will cover server, web and mobile applications. Currently, the server application have been being developed. Any interested persons are welcome, especially web and mobile developers.

## Table of Content

- [1. Prerequisites](#1-prerequisites)
- [2. Run on the local environment](#2-run-on-the-local-environment)
  - [2.1. On local host](#21-on-local-host)
  - [2.2. On Docker](#22-on-docker)
  - [2.3. On Kubernetes](#23-on-kubernetes)
- [3. Run on the non-local environments](#3-run-on-the-non-local-environments)
  - [3.1. On Docker](#31-on-docker)
  - [3.2. On Kubenetes](#32-on-kubenetes)
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

### 2.1. On local host

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
  docker-compose up -d

  # Stop and remove resources
  docker-compose down
  ```

### 2.3. On Kubernetes

- Install [Docker](https://www.docker.com/get-started) and start it
- Build the Docker image

  ```bash
  cd hohomalls/server
  ./gradlew clean bootBuildImage
  ```

- Install [Kubernetes](https://kubernetes.io/docs/setup/) and start it
- Assign labels to the worker nodes

  ```bash
  # Replace minikube-m02 and minikube-m03 with actual worker node names
  kubectl label nodes minikube-m02 nodeType=hohomalls-data-worker
  kubectl label nodes minikube-m03 nodeType=hohomalls-app-worker
  ```

- Install [Helm](https://helm.sh/docs/intro/install/)
- Install the applications with Helm

  ```bash
  # Add Bitnami to Helm repo
  helm repo add bitnami https://charts.bitnami.com/bitnami
  helm repo update

  cd hohomalls/container/helm
  # Update the dependent charts
  helm dependency update hohomalls

  # Create the target namespace, e.g. hohomalls-local
  kubectl create namespace hohomalls-local

  # Switch to the namespace just created
  kubectl config set-context --current --namespace=hohomalls-local

  # Install hohomalls with release name hohomalls in the current namespace
  helm install hohomalls hohomalls
  ```

## 3. Run on the non-local environments

The following environment variables have to be set before starting the applications:

```markdown
# Required
SPRING_PROFILES_ACTIVE = actual_value
# Required
SPRING_REDIS_HOST = actual_value
# Optional. Default is 6379
SPRING_REDIS_PORT = actual_value
# Required
SPRING_REDIS_PASSWORD = actual_value
# Required
SPRING_DATA_MONGODB_HOST = actual_value
# Optional. Default is 27017
SPRING_DATA_MONGODB_PORT = actual_value
# Required
SPRING_DATA_MONGODB_USERNAME = actual_value
# Required
SPRING_DATA_MONGODB_PASSWORD = actual_value
# Required
COM_HOHOMALLS_TOKEN_PUBLIC-KEY = actual_value
# Required
COM_HOHOMALLS_TOKEN_PRIVATE-KEY = actual_value
```

### 3.1. On Docker

Note that if you are using `Docker Swarm`, you may want to
use [Docker Secrets](https://docs.docker.com/engine/swarm/secrets/) to manage the sensitive data. In this case,
you have to update the Docker compose files yourself.

- Update configurations in `hohomalls/container/docker/.env`
- Follow [2.2 On Docker](#22-on-docker) to start/stop the applications

### 3.2. On Kubenetes

- Update configurations in `hohomalls/container/helm/hohomalls/values.yaml`
- Follow [2.3 On Kubernetes](#23-on-kubernetes) to start/stop the applications with appropriate namespace and release name, e.g.:

  ```bash
  # Create the target namespace, e.g. hohomalls-prod
  kubectl create namespace hohomalls-prod

  # Switch to the namespace just created
  kubectl config set-context --current --namespace=hohomalls-prod

  # Install hohomalls with release name prod in the current namespace
  helm install prod hohomalls
  ```

## 4. AWS Infrastructure

If the applications will be running on the cloud, e.g. AWS VPC, the following is the recommended infrastructure:

![AWS Infrastructure](/docs/aws-infrastructure.png)

## 5. TODO

- Use MongoDB replication mode
- Use Redis replication mode
- Aggregate logs from different instances
