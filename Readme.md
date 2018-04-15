criar pastas:
    banco
    webapps
criar imagens:
    docker-compose build
subir containers e banco mysql:
    docker-compose up -d
deploy da aplicação:
    ./deploy.sh
banco e tabelas:
    import.sql
remote debug port tomcat:
    8000
aplicação tomcat8-jre8:
    localhost:8080/login.xhtml
banco mysql:
    localhost:3307/livrariadb-cdi

