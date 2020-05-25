# OpenPlanner API

stable: [![Build Status](https://travis-ci.org/open-planner/open-planner-api.svg?branch=master)](https://travis-ci.org/open-planner/open-planner-api)
develop: [![Build Status](https://travis-ci.org/open-planner/open-planner-api.svg?branch=develop)](https://travis-ci.org/open-planner/open-planner-api)

Projeto da disciplina de Engenharia de Software do Mestrado Profissional de Informática do IFPB.

## Requisitos

* Maven
* JDK11
* PostgreSQL 10

## Funcionalidades

* Autenticação com OAuth2 e JWT
* Controle de Acesso
  * Cadastro com confirmação por e-mail
  * Alteração de Informações Pessoais
  * Alteração de Senha
  * Recuperação de Senha
* Roda da Vida
* Notificações
* Tags
* Viagens
* Planos de Férias
* Eventos
* Projetos
* Metas
* Tarefas

## Utilização

### Opção 1

```
mvn clean install
java -Dserver.port=8080 -Dspring.profiles.active=dev -jar target/open-planner-api-*.jar
```

### Opção 2

```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Profiles

* **dev**: desenvolvimento
* **prod**: produção

## Principais Properties e Environments

```yml
spring:
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/open-planner}
    username: ${DB_USER:user.auth}
    password: ${DB_PASSWORD:user.pass}
  mail:
    host: ${MAIL_HOST:smtp.gmail.com}
    port: ${MAIL_PORT:587}
    username: ${MAIL_USERNAME:username}
    password: ${MAIL_PASSWORD:password}
open-planner-api:
  web-app:
    base-url: ${WEB_APP_BASE_URL:http://localhost:4200}
  access-control-headers:
    allow-origin: ${ACCESS_CONTROL_ALLOW_ORIGIN:*}
    allow-credentials: ${ACCESS_CONTROL_ALLOW_CREDENTIALS:false}
  security:
    oauth2:
      client: ${OAUTH2_CLIENT:auth}
      secret: ${OAUTH2_SECRET:P@55-@uth-1937}
      scopes: ${OAUTH2_SCOPES:read,write}
      authorized-grant-types: ${OAUTH2_AUTHORIZED_GRANT_TYPES:password,refresh_token}
      access-token:
        validity-seconds: ${OAUTH2_ACCESS_TOKEN_VALIDITY_SECONDS:1800}
      refresh-token:
        enabled: ${OAUTH2_REFRESH_TOKEN_ENABLED:true}
        secure-cookie: ${OAUTH2_REFRESH_TOKEN_SECURE_COOKIE:true}
        validity-seconds: ${OAUTH2_REFRESH_TOKEN_VALIDITY_SECONDS:86400}
    jwt:
      signing-key: ${JWT_SIGNING_KEY:0p3n-p1ann3r-ap1}
    maximum-attempts-login: ${MAXIMUM_ATTEMPTS_LOGIN:5}
  mail:
    sender: ${MAIL_SENDER:sender@email.com}
```

## Documentação Swagger

> Habilitado apenas para o profile **dev**

http://localhost:8080/swagger-ui.html
