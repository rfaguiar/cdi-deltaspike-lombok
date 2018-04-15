FROM tomcat:8.0-jre8
MAINTAINER Rogerio Aguiar < rfaguiar1@gmail.com>
ADD . /code
WORKDIR /code
COPY tomcat-users.xml  $CATALINA_HOME/conf/

VOLUME $CATALINA_HOME/webapps

#habilita remote debug na porta 8000
ENV JPDA_ADDRESS="8000"
ENV JPDA_TRANSPORT="dt_socket"

EXPOSE 8080 8000
ENTRYPOINT ["catalina.sh", "jpda", "run"]