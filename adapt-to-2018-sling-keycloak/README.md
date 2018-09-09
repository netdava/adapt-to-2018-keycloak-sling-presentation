# Apache Sling and Keycloak integration on top of Apache Karaf 

This project is a demo project. It's purspose is to present the integration between Apache Sling and 
Keycloak IDM projects at Adapt.to 2018 conference in Potsdam, Germany. 
  

## Building the project

This project uses dependencies not available on Maven Central. 
To build it you need to setup your maven repository to access Netdava Maven Repository https://bintray.com/netdava/maven .
This should be setup allready via maven project specific `.mvn/local-settings.xml` .
Once all components will be available on Maven Central this step will not be necessary. 


```sh
mvn clean package 
echo "Unpack distribution"
export WORK_TMP_DIR=tmp-sling-keycloak
mkdir -p $WORK_TMP_DIR \
   && tar -xf sling-keycloak-karaf-dist/target/sling-keycloak-karaf-dist-1.0-SNAPSHOT.tar.gz -C $WORK_TMP_DIR --strip 1
   
echo "Start the distribution with ./$WORK_TMP_DIR/bin/karaf"
./$WORK_TMP_DIR/bin/karaf

```

Once inside Karaf console, you should install the application using features:

```sh
docker run \
    --rm \
    -it -d \
    -e JAVA_OPTS="-Xms1024m -Xmx2048m" \
    -p 1099:1099 \
    -p 8101:8101 \
    -p 8181:8181 \
    -p 44444:44444 \
    --name karaf karaf-docker

echo "Let's install sling inside karaf container" 
docker exec -ti karaf /opt/karaf/bin/client

feature:install sling-quickstart-oak-tar sling-discovery-oak sling-starter-content

``` 

docker run \
    --rm \
    -it -d \
    -e JAVA_OPTS="-Xms1024m -Xmx2048m" \
    -p 1099:1099 \
    -p 8101:8101 \
    -p 8181:8181 \
    -p 44444:44444 \
    --name karaf karaf-docker

docker run \
    -it -d \
    -e JAVA_OPTS="-Xms1024m -Xmx2048m" \
    -p 1099:1099 \
    -p 8101:8101 \
    -p 8181:8181 \
    -p 44444:44444 \
    --name karaf karaf-docker

 
 
 