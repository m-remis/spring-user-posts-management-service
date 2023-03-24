[![CircleCI](https://dl.circleci.com/status-badge/img/gh/m-remis/user-posts-management-service/tree/main.svg?style=svg&circle-token=be10159a23b14433d8f05f28bd11d770041576dd)](https://dl.circleci.com/status-badge/redirect/gh/m-remis/user-posts-management-service/tree/main)

## User Posts Management Service

This is user posts management microservice project.  
This microservice is responsible for:
* creating posts
* serving posts
* helping me to get a job

### Make sure to have installed

[JDK 17 or later](https://adoptium.net)

[Maven 3.8.8 or later](https://maven.apache.org/download.cgi)


### DB Structure
This project uses in memory H2 database with initial SQL script for schema creation.
* [SQL Script](src/main/resources/schema.sql)

### Build & Test
```
mvn clean install
```

### How to start:
```
java -jar user-posts-management-service\target\user-posts-management-service-0.0.1-SNAPSHOT.jar
```

### Swagger UI: 

This project uses OpenAPI 3.0.3 for documentation

Swagger UI can be found under:

```
{server-url}/api/user-posts-service/swagger-ui/index.html
```
#### For local testing, click [here](http://localhost:8080/api/user-posts-service/swagger-ui/index.html) after running the application to redirect to Swagger UI

![swagger](docs/img.png)

### YAML API Specification

specification can be downloaded under: 
```
{server-url}/api/user-posts-service/v3/api-docs.yaml
```
#### For local testing, click [here](http://localhost:8080/api/user-posts-service/v3/api-docs.yaml) after running the application to download the file