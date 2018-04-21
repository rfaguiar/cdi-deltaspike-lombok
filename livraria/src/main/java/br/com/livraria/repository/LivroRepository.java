package br.com.livraria.repository;

import br.com.livraria.modelo.Livro;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends EntityRepository<Livro, Integer> {
    List<Livro> findAllOrderByDataLancamentoDesc();
}
