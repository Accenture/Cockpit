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

The Documentation of APIs for Cockpit backend generated with OpenAPI 3.0 on:
http://{HOST}:{PORT}/cockpit-api-doc
You can also see them with Swagger-ui on:
http://{HOST}:{PORT}//swagger-ui/cockpit-api-doc