# GitOps Demo

Demo for showcasing OpenShift GitOps
https://www.youtube.com/watch?v=UvwcVNv61Mo&list=PLbMP1JcGBmSGKO8UreWpOBOhCqilejhtd&index=38

## Prerequisites

* Red Hat OpenShift 4.9 Cluster
* User with **cluster-admin** privileges
* OpenShift Client (oc) installed

## Deploy OpenShift GitOps
https://access.redhat.com/documentation/en-us/openshift_container_platform/4.9/html/cicd/gitops

`` oc create namespace openshift-gitops
oc create -f config/openshift-gitops-sub.yaml
oc create -f config/openshift-gitops.yaml -n openshift-gitops``

(See also [Installing OpenShift GitOps](https://access.redhat.com/documentation/en-us/openshift_container_platform/4.9/html/cicd/gitops#getting-started-with-openshift-gitops)

## Deploy Application

### AMQ Streams

``oc create -f config/application-cluster-configs.yaml
oc create -f config/``


### Camel Quarkus Application
