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

You need a username (email) and password to request end-points with Basic Auth.

A default user will be created on first run of the application with the following login information so you can test it
out:

-

//openssl genrsa -out keypair.pem 2048
//openssl rsa -in keypair.pem -pubout -out public.pem
//openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem

- User: `test@test.com`
- Password: `password`

### With Docker

```bash
docker-compose up
```

### With Maven

```bash
mvn spring-boot:run
```

# Swagger

After starting the application locally. The Swagger Documentation can be accessed
at [**http://localhost:8080/blog**](http://localhost:8080/blog) and all accessible routes will be shown.

# Credits

- [Erick Batista Prado](https://github.com/batistaerick)
