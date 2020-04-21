# Project description

This project is the library that contains the datamodel shared by the various backend applications:  
  - JPA datamodel mapped to the database + corresponding Spring Repository classes  
  - Jira and Kantree classes (that are serialized/deserialized and exchanged through REST calls)

__Note__:  
This project is a library that is meant to be referenced by the other projects, it can not be run by itself.

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

### Spring

https://projects.spring.io/spring-framework

### Lombok

https://projectlombok.org/download

### IDE
Install your IDE of choice.  
An IDE with Spring support would ease the development process (Intellij ultimate or Spring Tool Suite). 


## Build

### From the commandline
mvn clean install

This installs the generated mvp-core library in the .m2 repository of the machine where the build has been performed.  
Then the other projects can reference this library in their pom.