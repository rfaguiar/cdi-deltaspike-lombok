package br.com.livrarialib.dao;

import br.com.livrarialib.tx.annotation.Transacional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class DAO<T, I> implements Serializable {

	private final Class<T> classe;
	private EntityManager em;

	public DAO(Class<T> classe, EntityManager em) {
		this.classe = classe;
		this.em = em;
	}

	@Transacional
	public void adiciona(T t) {
		em.persist(t);
	}

	@Transacional
	public void remove(T t) {
		em.remove(em.merge(t));
	}

	@Transacional
	public void atualiza(T t) {
		em.merge(t);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
        return em.createQuery(query).getResultList();
	}

	public T buscaPorId(I id) {
        return em.find(classe, id);
	}

    public Long contaTodos() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(classe)));
        return em.createQuery(query).getSingleResult();
    }

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));
        return em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

}
