FROM openjdk:8-jdk-alpine
ENV DOCKER_IDP_SERVICE_HOST localhost
ENV DOCKER_IDP_SERVICE_PORT 8080

VOLUME /tmp
ADD target/laa-saml-mock-idp-1.0.0.jar app.jar
COPY docker-idp-application.yml config/idp-application.yml

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","app.jar", "--spring.config.location=config/idp-application.yml", "-DIDP_SERVICE_HOST=${DOCKER_IDP_SERVICE_HOST}", "-DIDP_SERVICE_PORT=${DOCKER_IDP_SERVICE_PORT}"]
