package br.com.alura.livraria.repository;

import br.com.alura.livraria.modelo.Livro;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository
public interface LivroRepository extends EntityRepository<Livro, Integer> {
}
