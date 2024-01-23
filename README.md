[![CircleCI](https://dl.circleci.com/status-badge/img/gh/m-remis/user-posts-management-service/tree/main.svg?style=svg&circle-token=be10159a23b14433d8f05f28bd11d770041576dd)](https://dl.circleci.com/status-badge/redirect/gh/m-remis/user-posts-management-service/tree/main)
![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven%20-8A2BE2)
![Spring](https://img.shields.io/badge/Spring_Boot%20-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL%20-blue)
![Docker](https://img.shields.io/badge/Docker%20-aqua)
![Flyway](https://img.shields.io/badge/Flyway%20-red)
![Caffeine Cache](https://img.shields.io/badge/Caffeine%20Cache-brown)

### User Posts Management Service

#### Simple example application

All functional requirements are covered by unit tests.

### API functional requirements

<table>
    <tr>
        <th>Feature</th>
        <th>Description</th>
    </tr>
    <tr>
        <td>add post</td>
        <td> - needs to validate userId using an external API before saving</td>
    </tr>
    <tr>
        <td>search post</td>
        <td> - based on post id or userId 
        <br> - if the post is not found in the system, it needs to be searched using an external API and saved (valid only for searching using the post id)</td>
    </tr>
    <tr>
        <td>remove post</td>
        <td> - remove a post from the system</td>
    </tr>
    <tr>
        <td>edit post</td>
        <td> - ability to change the title and body of a post</td>
    </tr>
</table>

#### All responses from external API need to be cached in system for 60 seconds

#### External API used for integration can be found here

[Json Placeholder APIs](https://jsonplaceholder.typicode.com/)

#### Apis used:

[Posts API](https://jsonplaceholder.typicode.com/posts)

[Users API](https://jsonplaceholder.typicode.com/users)

### Format of the post

<table>
  <tr>
    <th>Name</th>
    <th>Typer</th>
  </tr>
  <tr>
    <td>id</td>
    <td>Integer</td>
  </tr>
  <tr>
    <td>userId</td>
    <td>Integer</td>
  </tr>
  <tr>
    <td>title</td>
    <td>String</td>
  </tr>
  <tr>
    <td>body</td>
    <td>String</td>
  </tr>
</table>

### This project uses

<table>
  <tr>
    <th>Tech stack</th>
  </tr>
  <tr>
    <td>Java 17</td>
  </tr>
  <tr>
    <td>Maven</td>
  </tr>
  <tr>
    <td>Spring Boot 3</td>
  </tr>
  <tr>
    <td>H2 (tests)</td>
  </tr>
  <tr>
    <td>Postgres SQL</td>
  </tr>
  <tr>
    <td>Flyway</td>
  </tr>
  <tr>
    <td>Caffeine Cache</td>
  </tr>
  <tr>
    <td>Spring Cache</td>
  </tr>
  <tr>
    <td>Docker</td>
  </tr>
  <tr>
    <td>Tomcat replaced by JBoss Undertow (no reason)</td>
  </tr>
  <tr>
    <td>SpringDoc OpenAPI UI (Swagger UI)</td>
  </tr>
  <tr>
    <td>Hibernate</td>
  </tr>
    <tr>
    <td>CircleCI</td>
  </tr>
</table>

### Make sure to have installed

* [Git](https://git-scm.com/downloads)

* [JDK 17 or later](https://adoptium.net)

* [Maven 3.8.8 or later](https://maven.apache.org/download.cgi)

* [Docker](https://www.docker.com/)

### DB Structure

Flyway is used for automatic DB migration on application start.

* [Flyway DB Migration scripts](src/main/resources/db/migration)

### Build & Test:

```
mvn clean install
```

Tests run in "test" profile and use in memory H2 db together with flyway

### How to start:

Start dependencies in docker (this will start PostgreSQL container required to run this application, alternatively you
can use your already running instance / container, just make sure to change application-dev.yml, app will create it's
own schema)

```
docker-compose up -d
```

Start application in dev profile

```
java -jar -Dspring.profiles.active=dev user-posts-management-service\target\user-posts-management-service-0.0.1-SNAPSHOT.jar
```

### Swagger UI:

This project uses OpenAPI for documentation

Swagger UI can be found under

```
{server-url}/api/user-posts-service/swagger-ui/index.html
```

#### For local testing, click [here](http://localhost:8080/api/user-posts-service/swagger-ui/index.html) after running the application to redirect to Swagger UI

![swagger](docs/img.png)

### YAML API Specification:

Specification can be downloaded under

```
{server-url}/api/user-posts-service/v3/api-docs.yaml
```

#### For local testing, click [here](http://localhost:8080/api/user-posts-service/v3/api-docs.yaml) after running the application to download the file

### Simplified visual representation

![diagram](docs/diagram.svg)
