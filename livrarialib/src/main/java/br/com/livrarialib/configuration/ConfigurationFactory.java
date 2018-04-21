package br.com.livrarialib.configuration;

import br.com.livrarialib.configuration.annotation.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationFactory {

    @Produces
    @Configuration
    @ApplicationScoped
    public Properties getProperties() throws IOException {
        Properties properties = new Properties();
        String configDb = System.getenv("CONFIG-BD");
        if (configDb == null) {
            properties.put("livraria.lib.persistenceUnit", "livraria-dev");
        } else if ("prod".equals(configDb)) {

            properties.put("livraria.lib.persistenceUnit", "livraria-heroku");
            String host = System.getenv("MYSQL_DB_HOST");
            String port = System.getenv("MYSQL_DB_PORT");
            String databaseName = System.getenv("MYSQL_SCHEMA");
            String userName = System.getenv("MYSQL_DB_USERNAME");
            String password = System.getenv("MYSQL_DB_PASSWORD");
            String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s", host, port, databaseName);
            properties.put("javax.persistence.jdbc.url", jdbcUrl);
            properties.put("javax.persistence.jdbc.user", userName);
            properties.put("javax.persistence.jdbc.password", password);
        }
        return properties;
    }
}
