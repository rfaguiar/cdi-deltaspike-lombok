package br.com.livrarialib.factory;

import br.com.livrarialib.jpa.annotation.Query;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.ParameterizedType;

public class TypedQueryFactory {

    @Inject
    private EntityManager em;

    @Produces
    @Query("")
    public <X> TypedQuery<X> factory(InjectionPoint point){

        ParameterizedType type = (ParameterizedType) point.getType();

        @SuppressWarnings("unchecked")
        Class<X> classe = (Class<X>) type.getActualTypeArguments()[0];

        String jpql = point.getAnnotated().getAnnotation(Query.class).value();
        return em.createQuery(jpql, classe);
    }
}
