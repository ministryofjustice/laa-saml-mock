# Build the mock IdP docker image

## Prerequisites
Before building the Docker image from the [Dockerfile](Dockerfile), the Spring Boot application artifact must exist in the **target** directory

> e.g. mujina-idp/target/laa-saml-mock-idp-1.0.0.jar

_The best practice is to run a maven install on the pom.xml in the laa-saml-mock __root__ directory, and then build the docker image._
```bash
cd laa-saml-mock
mvn clean install
cd mujina-idp
```

```bash
docker build . -t laa-saml-mock-idp
```

----

# Run the mock IdP docker container

## Run the container on localhost port 8080
```bash
docker container run -p 8080:8080 laa-saml-mock-idp
```

## Run the container on a custom host
You can use the `IDP_SERVICE_HOST` environment variable to run the mock IdP somewhere other than localhost

### Using an IPv4 address:
```bash
docker container run -p 8080:8080 -e IDP_SERVICE_HOST=10.0.1.10 laa-saml-mock-idp
```

### Using a hostname:
```bash
docker container run -p 8080:8080 -e IDP_SERVICE_HOST=dev-saml-mock-idp laa-saml-mock-idp
```

#### Alternatively, reference an OS environment variable
```bash
export IDP_SERVICE_HOST=dev-saml-mock-idp
...
docker container run -p $IDP_SERVICE_HOST:8080 -e IDP_SERVICE_HOST=$IDP_SERVICE_HOST laa-saml-mock-idp
```

## Run the container on a custom port
You can use the `IDP_SERVICE_PORT` environment variable to expose the mock IdP from the docker host on a different port than 8080
```bash
docker container run -p 8000:8080 -e IDP_SERVICE_PORT=8000 laa-saml-mock-idp
```
_the docker container host port must match the IDP_SERVICE_PORT port_ e.g. 8000

#### Alternatively, reference an OS environment variable
```bash
export IDP_SERVICE_PORT=8000
...
docker container run -p $IDP_SERVICE_PORT:8080 -e IDP_SERVICE_PORT=$IDP_SERVICE_PORT laa-saml-mock-idp
```

## Run the container on a custom host and port
Use a combination of the `IDP_SERVICE_HOST` and `IDP_SERVICE_PORT` environment variables
```bash
docker container run -p 8000:8080 -e IDP_SERVICE_HOST=10.0.1.10 -e IDP_SERVICE_PORT=8000 laa-saml-mock-idp
```

# Specify an override Spring Boot configuration file
It is possible to mount a Docker volume to the /config directory and supply
a file containing an override of the spring boot application
configuration, __including user data__:
- e.g. config/idp-application.yml

The contents of the file must have at least a __idp.base_url__ property
using the expected environment variables if you want to run it outside
of localhost:8080
- IDP_SERVICE_HOST
- IDP_SERVICE_PORT

```yml
idp:
  base_url: http://${IDP_SERVICE_HOST}:${IDP_SERVICE_PORT}

samlUserStore:
    samlUsers:
      - username: test-user
        password: test password
        samlAttributes:
          attribute 1: test attribute 1 value
          attribute 2: test attribute 2 value
```

These values can be passed to the docker container as environment variables (_-e flag_).
```bash
docker container run -p 8000:8080 -e IDP_SERVICE_HOST=10.0.1.10 -e IDP_SERVICE_PORT=8000 -v /${PWD}/config:/config laa-saml-mock-idp
```

Or a hardcoded base_url value can be used:
```yml
idp:
  base_url: http://dev-saml-mock-idp:8000
```

If not specified, the containers default environment variable values will be used
- IDP_SERVICE_HOST: _localhost_
- IDP_SERVICE_PORT: _8080_

Resulting in...
```yml
idp:
  base_url: http://localhost:8080
```
