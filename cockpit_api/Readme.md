# Cockpit API on Spring Boot

Before your first launch of the docker-compose you need to make the first build locally.

```bash
mvn package && java -jar target/cockpit_api-0.1.0.jar
```

After that you can launch your docker-compose with the following command:

```bash
docker-compose up --build
```

This will build and launch you api on localhost:8080