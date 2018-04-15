package br.com.livrarialib.factory;

import br.com.livrarialib.configuration.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Properties;

public class JPAFactory {

	private static EntityManagerFactory emf;

	@Inject
	@Configuration
	private Properties properties;

	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(@Disposes  EntityManager em) {
		em.close();
	}

	@PostConstruct
	public void loadEMF(){
		emf = Persistence.createEntityManagerFactory(properties.getProperty("livraria.lib.persistenceUnit"));
	}
}
