# Apache Sling and Keycloak integration on top of Apache Karaf 

This project is a demo project. It's purspose is to present the integration between Apache Sling and 
Keycloak IDM projects at Adapt.to 2018 conference in Potsdam, Germany. 
  

## Running the application

The easiest way to run is via Docker. 
We are going to pull and run a docker image that will start Apache Karaf application server.
We are going to access the Karaf console.  
Once inside Karaf console, we can install the application using feature commands:

```sh
docker run \
    --rm \
    -it -d \
    -e JAVA_OPTS="-Xms1024m -Xmx2048m" \
    -p 1099:1099 \
    -p 8101:8101 \
    -p 8181:8181 \
    -p 44444:44444 \
    --name karaf quay.io/netdava/docker-karaf:4.2.1-1

echo "Let's install sling inside karaf container" 
docker exec -ti karaf /opt/karaf/bin/client

feature:install sling-keycloak-base
feature:install sling-keyclaok-all

```

###  Setup Keycloak realm and client


### Setup Sling keycloak auth 

Copy keycoak configuration to docker container: `docker cp ./keycloak.json karaf:/opt/karaf-base/etc/keycloak.json`


## Building the project

This project uses dependencies not available on Maven Central. 
To build it you need to setup your maven repository to access Netdava Maven Repository https://bintray.com/netdava/maven .
This should be setup allready via maven project specific `.mvn/local-settings.xml` .
Once all components will be available on Maven Central this step will not be necessary. 

### Build karaf docker image 

```sh
cd karaf-docker
docker build -t quay.io/netdava/docker-karaf:4.2.1-1 . 
docker push quay.io/netdava/docker-karaf:4.2.1-1 

```

