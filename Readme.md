[![Build Status](https://travis-ci.org/rfaguiar/cdi-deltaspike-lombok.svg?branch=master)](https://travis-ci.org/rfaguiar/cdi-deltaspike-lombok) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=br.com.livraria-jsf-deltaSpike%3Alivraria-jsf-deltaSpike&metric=alert_status)](https://sonarcloud.io/api/project_badges/measure?project=br.com.livraria-jsf-deltaSpike%3Alivraria-jsf-deltaSpike&metric=alert_status) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=br.com.livraria-jsf-deltaSpike%3Alivraria-jsf-deltaSpike&metric=coverage)](https://sonarcloud.io/api/project_badges/measure?project=br.com.livraria-jsf-deltaSpike%3Alivraria-jsf-deltaSpike&metric=coverage)  
# Tecnologias: Java 8, Tomcat8, Docker 17ce, DockerCompose 1.15, Maven 3, Hibernate 4, DeltaSpike 1.8, Lombok 1.16, JSF 2.2, MySQL 5.1, Primefaces 5.3, CDI-Weld 2.3, Junit 4, Selenium 3, Powermock 1.7
* Qualidade/Cobertura de codigo com SonnarLint/jacoco
* Testes unitários TDD com Junit e Mocks
* Teste de integração e automáticos com Selenium e BDD
* Build automático com TravisCI e pipeline com deploy automático no heroku cloud
* versionamento GIT

## Pré-requisitos para devs:
* criar pastas na raiz do projeto para o docker compose funcionar:  
    > banco  
    > webapps  
* criar imagens docker:  
    > docker-compose build  
* subir containers e banco mysql com docker compose e logs:  
    > docker-compose up -d  
    > docker-compose logs  
* deploy da aplicação dentro da pasta mapeada pelo docker compose:  
    > ./deploy.sh  
* banco e tabelas:  
    > import.sql  
* remote debug port tomcat:  
    > 8000  
* aplicação tomcat8-jre8:  
    > localhost:8080/login.xhtml  
* banco mysql:  
    > localhost:3307/livrariadb-cdi  

## Ambiente docker:
* Imagen base tomcat:8.0-jre8
    > volume: webapps
    > http Port: 8080
    > http Debug Port: 8000
* Imagen mysql:5.6
    > volume: banco
    > http port: 3307
    > usuario: root
    > senha: root

### Docker compose v3:
* network:
    > tomcat-network
    > bridge
* containers:
    > tomcat8
    > db-mysql

### Variaveis de ambiente produção:
* CONFIG-BD=prod  
    > MYSQL_DB_HOST  
    > MYSQL_DB_PORT  
    > MYSQL_SCHEMA  
    > MYSQL_DB_USERNAME  
    > MYSQL_DB_PASSWORD  

#### Aplicação na cloud do heroku pode estar dormindo, tenha paciência:  
[link para aplicação no heroku!](https://livraria-app1.herokuapp.com/login.xhtml)  

acesso: admin@admin.com  
pass: admin  

