# Useful Commands

Note that files in `k8s` directory are for the purpose of testing on the local machine only. Please follow [this document](../README.md) for formal installation.

## Common

- echo -n "STRING" | base64

## Docker

- docker-compose --env-file .env.local up -d

## Minikube

- minikube ip
- minikube ssh
- minikube start --memory max --cpus max
- minikube image load hohomalls-app:1.0.0-SNAPSHOT

## Helm

- helm install test hohomalls --dry-run --debug
- Scripts

  ```bash
  brew install helm
  helm repo add bitnami https://charts.bitnami.com/bitnami
  helm repo update
  cd hohomalls/container/helm
  helm dependency update hohomalls
  kubectl create namespace hohomalls-local
  kubectl config set-context --current --namespace=hohomalls-local
  helm install test hohomalls
  ```

## Kubenetes

- kubectl config set-context --current --namespace=hohomalls-local
- kubectl apply -f https://openebs.github.io/charts/openebs-operator.yaml
- Scripts

  ```bash
  cd hohomalls/container/k8s
  kubectl create namespace hohomalls-local
  kubectl config set-context --current --namespace=hohomalls-local
  kubectl apply -f 1-secret.yml
  kubectl apply -f 2-config-map.yml
  helm install hohomalls-mongo -f 3-helm-mongo.yml bitnami/mongodb --version 10.29.2
  helm install hohomalls-redis -f 4-helm-redis.yml bitnami/redis --version 15.5.5
  kubectl apply -f 5-application.yml
  ```
