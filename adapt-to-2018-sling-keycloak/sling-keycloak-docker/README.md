# Docker compose setup for Apache Sling + Keycloak

This setup is easiest to do with docker compose. We can start the following components:

- PostgreSql database for Keycloak
- Keyclaok instance to handle authentication, accessible at: http://auth.docker.localhost
- Apache Sling instance on top of Apache Karaf, at http://sling.docker.localhost
- Traefik reverse proxy to have nice hostnames - Dasgboard at http://localhost:8080/


# Running


To start the contaiers run the commands:
```sh
  cd sling-keycloak-docker/
  docker-compose up -d
```

The containers should start in background. You can check the debug with `docker ps -a` and
`docker logs -f <container_name>` .

Once the containers are up we can install Apache Sling inside Apache Karaf.
Apache Karaf has a text (and web) console that we can use to install and manage OSGi apps.
We will run the karaf `client` command inside the container to access the Karaf console.

```sh
  docker exec -ti slingkeycloakdocker_sling_1 /opt/karaf/bin/client
```

Once we can access the console we can install Karaf features for Sling.

```sh
  feature:install sling-keycloak-base
  feature:install sling-keyclaok-all
```

NOTE: This might take some time and you might get disconnected from the console.
Karaf will download and install ~ 70mb of data.

Once the process is done you should be able to see and acess the sling starter page at

