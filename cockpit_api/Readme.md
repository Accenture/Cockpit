# Cockpit Backend on Spring Boot

## Project description

Cockpit is an open-source application now developed as a MVP at Total Digital Factory, it offers a direct vision with detailed information and health states for all MVPs in the factory.
It is now on Accenture Github site: github.com/accenture, and could be used for other organizations who use Jira as their project management tool.
        
## Prerequisites
### Java 8

To check if it's already installed:
java -version

If Java 8 is not installed yet, see http://www.oracle.com/technetwork/java/javaee/overview/index.html

### PostgreSQL server

A PostgreSQL server need to run locally with a database called "cockpitdb"(CREATE DATABASE cockpitdb)
Once the database is created, update host, username and password in the application-local.yml file.
For instructions, check https://www.postgresql.org/

### Maven

To check if it's already installed:
mvn --version

### Spring

https://projects.spring.io/spring-framework


### IDE
Install your IDE of choice.  
An IDE with Spring support would ease the development process (Intellij ultimate or Spring Tool Suite). 

## Project Dependency

In this project, jira-gateway and mvp-infos depends on the cockpit-core library.  
So the cockpit-core project has to be built and installed in the maven .m2 repository.

## Build and run

### From the commandline    

If you are not using an IDE, befor the first you run Cockpit backend application, run the following commands:
mvn clean package
run with the "local" profile:  
mvn spring-boot:run -Dspring.profiles.active=local

### From the IDE

make sure the launch configuration sets the following VM parameter:  
-Dspring.profiles.active=local

## Run the backend application in Docker containers
### Local config

In order to launch the project you need to apply few changes:
1. Build the images with the following command `docker-compose build`
2. Launch the containers with `docker-compose up`
3. Optionally to gain time you can one-shot both commands with `docker-compose up --build`
Before your first launch of the docker-compose you need to make the first build locally.

```bash
mvn package && java -jar target/cockpit_api-0.1.0.jar
```

After that you can launch your docker-compose with the following command:

```bash
docker-compose up --build
```

This will build and launch you api on localhost:8085

The Documentation of APIs for Cockpit backend generated with OpenAPI 3.0 on:
http://{HOST}:{PORT}/cockpit-api-doc
You can also see them with Swagger-ui on:
http://{HOST}:{PORT}//swagger-ui/cockpit-api-doc