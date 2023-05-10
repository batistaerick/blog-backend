# Blog

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [How to use](#how-to-use)
    - [With Docker](#with-docker)
    - [With Maven](#with-maven)
4. [Swagger](#swagger)
5. [Credits](#credits)

# Introduction

A monolithic REST API with the ability to create users, image albums, posts and comments on posts.

# Prerequisites

To run locally you need:

- Java 17
- Maven
- PostgreSQL - Need to reset the environments in ***resources/application.properties*** with your settings

# How to use

Before launching the application, you will need to create the .pem files with the following commands:

```bash
cd src/main/resources/certs/
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem
```

Now you can create a new user through the endpoint ending in `/users`,
you can see the json template in swagger documentation.
After that you need to request a token via the `/token` endpoint.
Then you will be able to use the `JWT token` for your requests.

### With Docker

```bash
docker compose up
```

### With Maven

```bash
mvn spring-boot:run
```

# Swagger

After starting the application locally. The Swagger Documentation can be accessed
at [**http://localhost:8080/api-doc**](http://localhost:8080/api-doc) and all accessible routes will be shown.

# Credits

- [Erick Batista Prado](https://github.com/batistaerick)
