rm -Rf webapps/livraria.war
mvn clean install
cp livraria/target/livraria.war webapps/
