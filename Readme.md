[![Build Status](https://travis-ci.org/rfaguiar/cdi-deltaspike-lombok.svg?branch=master)](https://travis-ci.org/rfaguiar/cdi-deltaspike-lombok)

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
      
[link para aplicação no heroku!](https://livraria-app1.herokuapp.com/login.xhtml)

acesso: admin@admin.com
pass: admin

