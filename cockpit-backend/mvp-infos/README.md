# Project description

This project is the REST API endpoints of Cockpit application.

# Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

## Prerequisites

### Java 8

To check if it's already installed:
java -version

If Java 8 is not installed yet, see http://www.oracle.com/technetwork/java/javaee/overview/index.html

### Maven

To check if it's already installed:
mvn --version

### MySQL server

A MySQL server need to run locally with a database called "cockpitdb"(CREATE DATABASE cockpitdb)
Once the database is created, update host, username and password in the application-local.yml file.
For instructions, check https://dev.mysql.com/doc/mysql-getting-started/en/#mysql-getting-started-installing

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

## Run locally

### From the commandline
run with the "local" profile:  
mvn spring-boot:run -Dspring.profiles.active=local

In order to allow remote debugging, add the following parameters:  
-Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"

### From the IDE
make sure the launch configuration sets the following VM parameter:  
-Dspring.profiles.active=local
