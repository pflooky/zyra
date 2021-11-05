# Zyra
## Overview
Kubernetes mutating webhook controller that can alter kubernetes resources before they are deployed.

## Run locally
1. Startup ZyraApplication
1. Run following curl
```shell
curl -v -X POST localhost:8080/kube/patch/simple_apps -H "Content-Type: application/json" -d "@src/test/resources/sample/sample-kube-pod-request.json"
```

## How it works


## Use cases
- Add extra metadata to Kubernetes resources
- Add additional containers or init-containers to pods
- 

