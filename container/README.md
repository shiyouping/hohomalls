# Useful Commands

Note that files in `k8s` directory are for the purpose of testing on the local machine only. Please follow [this document](../README.md) for formal installation.

## Common

- echo -n "STRING" | base64: Encodes binary strings into text representations using the base64 encoding format

## Docker

- docker login: Log in to a Docker registry
- docker logout: Log out from a Docker registry
- docker-compose --env-file .env.local up -d: Start the containers using the given environment file in the background and leave them running

## Helm

- helm install hohomalls hohomalls --dry-run --debug: To check the generated manifests of a release without installing the chart
- Scripts

  ```bash
  brew install helm
  helm repo add bitnami https://charts.bitnami.com/bitnami
  helm repo update
  cd hohomalls/container/helm
  helm dependency update hohomalls
  kubectl create namespace hohomalls-local
  kubectl config set-context --current --namespace=hohomalls-local
  helm install hohomalls hohomalls
  ```

## Minikube

- minikube ip: Retrieve the IP address of the primary control plane
- minikube ssh: Log into the minikube environment (for debugging)
- minikube start --memory max --cpus max --nodes=3: Start minikube
- eval $(minikube docker-env): Configure environment to use minikube’s Docker daemon
- minikube image load hohomalls-app:1.0.0-SNAPSHOT: Load directly to in-cluster container runtime
- minikube service --url hohomalls-mongo -n hohomalls-local &: Return the Kubernetes URL for a service in your local cluster
- minikube service --url hohomalls-redis-master -n hohomalls-local &: Return the Kubernetes URL for a service in your local cluster
- minikube service --url hohomalls-app -n hohomalls-local &: Return the Kubernetes URL for a service in your local cluster

## Kubenetes

- kubectl config get-contexts: Get context entries in kubeconfig
- kubectl config use-context CONTEXT_NAME: Set the current-context in a kubeconfig file
- kubectl config set-context --current --namespace=hohomalls-local: Set a context entry in kubeconfig
- kubectl get nodes --show-labels: Get nodes with labels
- kubectl label nodes minikube-m02 nodeType=hohomalls-data-worker: Set a label to a node
- kubectl label nodes minikube-m03 nodeType=hohomalls-app-worker: Set a label to a node
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

## Amazon Elastic Kubernetes Service

- eksctl get cluster -r ap-east-1: Get clusters in the given region
- eksctl create cluster -f cluster.yml: Create a cluster using the given file
- eksctl delete cluster -n hohomalls -r ap-east-1: Delete a cluster with the specific name and in the given region
- create a cluster and install hohomalls

  ```bash
  cd hohomalls/container/eks
  eksctl create cluster -f cluster.yml

  cd hohomalls/container/helm
  helm install hohomalls hohomalls
  ```
