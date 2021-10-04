# Kubenetes Commands

## Create a namespace

```bash
cd hohomalls/k8s/local
kubectl create -f namespace.yml
```

## Setting the namespace preference

```bash
# Permanently save the namespace for all subsequent kubectl commands in that context
kubectl config set-context --current --namespace=hohomalls-local

# Validate it
kubectl config view --minify | grep namespace:
```

## Create secrets

```bash
echo -n "STRING" | base64
kubectl create -f secret.yml
```

## TODO

- [Encrypt secrets](https://kubernetes.io/docs/concepts/configuration/secret/)
