# Build the mock IdP docker image

## Prerequisites
Before building the Docker image, the Spring Boot application artifact must exist in the **target** directory

> e.g. mujina-idp/target/laa-saml-mock-idp-1.0.0.jar

_The best practice is to run a maven install on the pom.xml in the laa-saml-mock __root__ directory, and then build the docker image._
```bash
cd laa-saml-mock
mvn clean install
cd mujina-idp
```

----

```bash
docker build . -t laa-saml-mock-idp
```

# Run the mock IdP docker container
```bash
docker container run -p 8080:8080 laa-saml-mock-idp
```

# Run the mock IdP docker container on a custom host
You can use the DOCKER_IDP_SERVICE_HOST environment variable to run the mock IdP somewhere other than localhost
```bash
docker container run -p 8080:8080 -e DOCKER_IDP_SERVICE_HOST=10.98.221.157 laa-saml-mock-idp
```

# Run the mock IdP docker container on a custom port
You can use the DOCKER_IDP_SERVICE_PORT environment variable to expose the mock IdP from the docker host on a different port than 8080
```bash
docker container run -p 8000:8080 -e DOCKER_IDP_SERVICE_PORT=8000 laa-saml-mock-idp
```
_the docker host port must match the IDP_SERVICE_PORT port_ e.g. 8000

# Run the mock IdP docker container on a custom host and port
Use a combination of the DOCKER_IDP_SERVICE_HOST and DOCKER_IDP_SERVICE_PORT environment variables
```bash
docker container run -p 8000:8080 -e DOCKER_IDP_SERVICE_HOST=10.98.221.157 -e DOCKER_IDP_SERVICE_PORT=8000 laa-saml-mock-idp
```

# Specify an override spring configuration file
It is possible to mount a Docker volume to the /config directory and
provide a file containing a complete override of the spring boot application configuration
- e.g. config/idp-application.yml

The contents of the file must have the __idp.base_url__ property set to the following value in order for the
SAML IdP application to work correctly
```yml
idp:
  base_url: http://${IDP_SERVICE_HOST}:${IDP_SERVICE_PORT}
```
