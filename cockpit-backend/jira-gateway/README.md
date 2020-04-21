# Project description

This project calls the JIRA REST API in order to get data regarding the MVPs in development.  
This data is then stored in a MySQL database, and later displayed on Cockpit Front-end page.

This application is always running and makes get request via the JIRA API regularly. 

For more information on the JIRA API, see https://developer.atlassian.com/cloud/jira/platform/rest

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

This project depends on the cockpit-core library.  
So the cockpit-core project has to be built and installed in the maven .m2 repository.  

In order to do this, from the cockpit-core directory, run:  
mvn clean install


## Build

### From the commandline    
mvn clean package

### From the commandline
run with the "local" profile:  
mvn spring-boot:run -Dspring.profiles.active=local

### From the IDE
make sure the launch configuration sets the following VM parameter:  
-Dspring.profiles.active=local