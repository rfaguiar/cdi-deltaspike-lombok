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
        System.out.println("############################ CONFIG-BD ###################");
        System.out.println(configDb);
        System.out.println();
        System.out.println();

        if (configDb == null) {
            InputStream inputStream = ConfigurationFactory.class.getResourceAsStream("livraria-dev");
            properties.load(inputStream);
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
            properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        }
        System.out.println("############################ properties bd ###################");
        System.out.println(properties);
        System.out.println();
        System.out.println();
        return properties;
    }
}
