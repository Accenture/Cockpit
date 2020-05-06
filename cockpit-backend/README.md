## Project description

There are 3 parts for Cockpit backend application, one library and two spring-boot applications:
    - cockpit-core
        The library that contains the datamodel shared by the various backend applications:  
        - JPA datamodel mapped to the database + corresponding Spring Repository classes  
        - Jira classes (that are serialized/deserialized and exchanged through REST calls)
        __Note__:  
        cockpit-core is a library that is meant to be referenced by the other projects, it can not be run by itself.
    - jira-gateway
        This project calls the JIRA REST API in order to get data regarding the MVPs in development.  
        Those data will then be stored in a MySQL database, and later displayed on Cockpit Front-end page.
        For more information on the JIRA API, see https://developer.atlassian.com/cloud/jira/platform/rest
    - mvp-infos
        This project is the REST API endpoints of Cockpit application.
        
## Prerequisites
### Java 8

To check if it's already installed:
java -version

If Java 8 is not installed yet, see http://www.oracle.com/technetwork/java/javaee/overview/index.html

### MySQL server

A MySQL server need to run locally with a database called "cockpitdb"(CREATE DATABASE cockpitdb)
Once the database is created, update host, username and password in the application-local.yml file.
For instructions, check https://dev.mysql.com/doc/mysql-getting-started/en/#mysql-getting-started-installing

### Maven

To check if it's already installed:
mvn --version

### Spring

https://projects.spring.io/spring-framework

### Lombok

https://projectlombok.org/download

### IDE
Install your IDE of choice.  
An IDE with Spring support would ease the development process (Intellij ultimate or Spring Tool Suite). 

## Project Dependency

In this project, jira-gateway and mvp-infos depends on the cockpit-core library.  
So the cockpit-core project has to be built and installed in the maven .m2 repository.

## Build and run

### From the commandline    

As the cockpit library is an dependency for jira-gateway and mvp-infos, from the cockpit-core directory, run:  
mvn clean install
This installs the generated mvp-core library in the .m2 repository of the machine where the build has been performed.  
Then the other projects can reference this library in their pom.

And then for both jira-gateway and mvp-infos (from their directory), run:
mvn clean package
run with the "local" profile:  
mvn spring-boot:run -Dspring.profiles.active=local

### From the IDE

make sure the launch configuration sets the following VM parameter:  
-Dspring.profiles.active=local

## Run the backend application in Docker containers
### Local config

In order to launch the project you need to apply few changes:
1. Change the following variables `JIRA_TOKEN` with your own values.(In the docker-compose file) 
2. Build the images with the following command `docker-compose build`
3. Launch the containers with `docker-compose up`
4. Optionally to gain time you can one-shot both commands with `docker-compose up --build`