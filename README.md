# Zyra
## Overview
Kubernetes mutating webhook controller that can alter kubernetes resources before they are deployed.

## How it works
In Kubernetes, you can define a `MutatingWebhookConfiguration` as seen [here](deploy/mutating-config/mutating-webhook-config.yaml)
which will state a particular HTTPS pathway for Kubernetes to hit when a particular condition is true. This application will
then get the corresponding JSONPatch file and apply that to the incoming Kubernetes resource request.

For example, we can have a mutating webhook defined for all pod create events to append on the label `env: test`.

## Use cases
- Add extra metadata to Kubernetes resources
- Add additional containers or init-containers to pods
- Secret management
- Deployment configuration via environment variables or configmaps

## Run locally
1. Startup ZyraApplication
1. Run following curl
```shell
curl -v -X POST localhost:8080/kube/patch/simple-apps -H "Content-Type: application/json" -d "@src/test/resources/sample/sample-kube-pod-request.json"
```

## Run in Kubernetes
```shell
export IMAGE_BASE="pflookyy"
export IMAGE_TAG="latest"
#Create jar and image
gradle clean build
docker build -t "zyra:${IMAGE_TAG}" .
docker tag "zyra:${IMAGE_TAG}" "${IMAGE_BASE}/zyra\:${IMAGE_TAG}"
docker push "${IMAGE_BASE}/zyra:${IMAGE_TAG}"

#Create certificate needed along with deploying mutating webhook configuration
./deploy/cert/create-cert.sh

#Install zyra with certificate
helm install zyra deploy/zyra

#Deploy test pod and see if zyra alters it
kubectl apply -f src/test/resources/sample/pod.yaml
kubectl logs $(kubectl get po|grep zyra|awk -F ' ' '{print $1}')
kubectl describe pod print-greeting
```
