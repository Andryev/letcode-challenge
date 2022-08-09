# Let's Code Challenge Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application

You can run your application in dev mode that enables live coding using:

```shell script
mvn clean install
```
```shell script
docker-compose -f docker-compose.yml up --build
```
## Api Documentation

Note, authentication using Quarkus Security with openapi, for login, use the openapi login functionality.


### Openapi and application url:

http://localhost:8080/q/swagger-ui/

### Endpoints example:

#### Start a game:

```shell script
curl -X 'POST' \
  'http://localhost:8080/game' \
  -H 'accept: application/json' \
  -H 'Authorization: Basic aWFtOmlhbQ==' \
  -d ''
```

#### Stop a game:

```shell script
curl -X 'PUT' \
  'http://localhost:8080/game' \
  -H 'accept: application/json' \
  -H 'Authorization: Basic aWFtOmlhbQ=='
```

#### Get player ranking:

```shell script
curl -X 'GET' \
  'http://localhost:8080/game/ranking' \
  -H 'accept: application/json' \
  -H 'Authorization: Basic aWFtOmlhbQ=='
```


#### Get a new round of a game:

```shell script
curl -X 'GET' \
  'http://localhost:8080/round' \
  -H 'accept: */*' \
  -H 'Authorization: Basic aWFtOmlhbQ=='
```

#### Submit a new round of a game:
###### Note. The choice field is only available for options A or B
```shell script
curl -X 'POST' \
  'http://localhost:8080/round/a' \
  -H 'accept: */*' \
  -H 'Authorization: Basic aWFtOmlhbQ==' \
  -d ''
```

#### Insert a new player user:

###### Note. The only available role field is: USER
```shell script
curl -X 'POST' \
  'http://localhost:8080/user' \
  -H 'accept: application/json' \
  -H 'Authorization: Basic aWFtOmlhbQ==' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "lets",
  "password": "letspass",
  "role": "USER"
}'
```

## Related Guides

- Hibernate ORM ([guide](https://quarkus.io/guides/hibernate-orm)): Define your persistent model with Hibernate ORM and JPA
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, JPA)
- Liquibase ([guide](https://quarkus.io/guides/liquibase)): Handle your database schema migrations with Liquibase

## Provided Code

### Hibernate ORM

Create your first JPA entity

[Related guide section...](https://quarkus.io/guides/hibernate-orm)

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
