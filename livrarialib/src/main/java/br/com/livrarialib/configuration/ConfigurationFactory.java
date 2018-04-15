package br.com.livrarialib.configuration;

import br.com.livrarialib.configuration.annotation.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationFactory {

    @Produces
    @Configuration
    @ApplicationScoped
    public Properties getProperties() throws IOException {
        InputStream inputStream = ConfigurationFactory.class.getResourceAsStream("/livraria.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }
}
