#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
IMAGE_BASE="pflookyy"
IMAGE_TAG="latest"

function assert_fail () {
  if "$@"; then
    SHORT_COMMAND=$(echo "${*:0:20}")
    echo -e "${GREEN}$SHORT_COMMAND didn't fail"
  else
    echo -e "${RED}$* failed."
  fi
}

echo -e "${GREEN}Checking if you are connected to Kubernetes cluster..."
if kubectl get po ; then
  echo -e "${GREEN}Connected to Kubernetes"
else
  echo -e "${RED}Please setup connection to Kubernetes cluster before running"
  exit 1
fi

echo -e "${GREEN}Creating the certificate for TLS and adding as Kubernetes secret..."
assert_fail bash deploy/cert/create-cert.sh

CA_BUNDLE=$(kubectl config view --raw --minify --flatten -o jsonpath='{.clusters[].cluster.certificate-authority-data}')
echo -e "${GREEN}Including CA_BUNDLE as part of mutating webhook configuration..."
sed -e "s/\${CA_BUNDLE}/${CA_BUNDLE}/g" deploy/mutating-config/mutating-webhook-config-base.yaml > deploy/mutating-config/mutating-webhook-config.yaml

echo -e "${GREEN}Deploying the mutating webhook configuration..."
assert_fail kubectl apply -f deploy/mutating-config/mutating-webhook-config.yaml

echo -e "${GREEN}Building the application jar and image..."
gradle clean build
docker build -t "zyra:${IMAGE_TAG}" .
docker tag "zyra:${IMAGE_TAG}" "${IMAGE_BASE}/zyra:${IMAGE_TAG}"
docker push "${IMAGE_BASE}/zyra:${IMAGE_TAG}"

echo -e "${GREEN}Starting up zyra application into Kubernetes..."
assert_fail helm upgrade zyra deploy/zyra --force --install

sleep 60
zyra_pod=$(kubectl get po | grep zyra | awk -F ' ' '{print $1}')
kubectl delete po print-greeting
sleep 5
kubectl apply -f src/test/resources/sample/pod.yaml
kubectl logs "$zyra_pod"
