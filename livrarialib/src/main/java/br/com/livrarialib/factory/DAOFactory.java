package br.com.livrarialib.factory;

import br.com.livrarialib.dao.DAO;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class DAOFactory {

    @Inject
    private EntityManager manager;

    @Produces
    public <T, I> DAO<T, I> factory(InjectionPoint point) {
        ParameterizedType types = (ParameterizedType) point.getType();

        Type type = types.getActualTypeArguments()[0];

        return new DAO<T, I>((Class<T>)type, manager);
    }
}
