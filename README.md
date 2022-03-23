# GitOps Demo

Demo for showcasing OpenShift GitOps

## Prerequisites

* Red Hat OpenShift 4.9 Cluster
* User with **cluster-admin** privileges
* OpenShift Client (oc) installed

## Deploy OpenShift GitOps

To deploy OpenShift GitOps (based on ArgoCD) you need to create a **namespace** and install the OpenShift GitOps **operator**:
```
oc create namespace openshift-gitops
oc create -f config/openshift-gitops-sub.yaml
```

(See also [Installing OpenShift GitOps](https://access.redhat.com/documentation/en-us/openshift_container_platform/4.9/html/cicd/gitops#getting-started-with-openshift-gitops)

## Deploy Gitea

### Deploy Postgres

oc new-app postgresql-ephemeral -p DATABASE_SERVICE_NAME=postgres -p POSTGRESQL_USER=postgres -p POSTGRESQL_PASSWORD=postgres -p POSTGRESQL_DATABASE=gitops --name=postgres


## Prepare project on OpenShift

To prepare the **gitops-demo** namespace, create an **application** called **cluster-configs** like this:
```
oc create -f config/application-cluster-configs.yaml
```
## Deploy Application

The GitOps demo application consiste of two parts:

* A Apache Kafka cluster
* An Camel Quarkus application

### AMQ Streams

To deploy Apache Kafka (Red Hat AMQ Streams) execute the following
```
oc create -f config/application-gitops-demo-amq-streams.yaml
```
This will create an ephemereal Apache Kafka cluster with three broker instance and a highly available Zookeeper control-plane.

### Camel Quarkus Application

To deploy the Camel Quarkus application execute the following
```
oc create -f config/application-gitops-demo-camel-quarkus.yaml
```
This will trigger the creation of various resources that are needed for a Source-2-Image deployment.

To test the application run
```
CAMEL_QUARKUS_HOST=`oc get route camel-quarkus -o json | jq -r '.spec.host'`
curl -v -k -H "Content-Type: application/json" -X POST "https://${CAMEL_QUARKUS_HOST}/event" -d '{"id": "1", "status": "OK"}'
```

In the Camel Quarkus pod you should now see the following log staments

    INFO  [route1] (executor-thread-0) Received event {"id": "1", "status": "OK"}
    INFO  [route2] (Camel (camel-1) thread #1 - KafkaConsumer[events]) Consumed event {"id": "1", "status": "OK"}

The resources that you can find in **app** have been derived from thos that have been created by the following command:
```
oc new-app openshift/ubi8-openjdk-11:1.3~https://github.com/jcordes73/gitops-demo --context-dir=camel-quarkus --name=camel-quarkus
```

## Cleanup

```
oc delete gitopsservice cluster -n openshift-gitops
```
