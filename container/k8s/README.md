# Kubenetes Commands

- Namespace
- Secret
- Storage Class
- Deployment
- Service

## Create a namespace

```bash
cd hohomalls/k8s/local
kubectl create -f namespace.yml
```

## Setting the namespace preference

```bash
# Permanently save the namespace for all subsequent kubectl commands in that context
kubectl config set-context --current --namespace=hohomalls-local
```

## Create secrets

```bash
echo -n "STRING" | base64
kubectl create -f secret.yml
```





































## Install OpenEBS

```bash
kubectl apply -f https://openebs.github.io/charts/openebs-operator.yaml
```

## [Create StorageClass](https://openebs.io/docs/user-guides/localpv-hostpath)

```bash
mkdir -p /opt/k8s/volumes
kubectl apply -f storage-class.yml
kubectl apply -f persistent-volume-claim.yml
```

## TODO

- [Encrypt secrets](https://kubernetes.io/docs/concepts/configuration/secret/)
