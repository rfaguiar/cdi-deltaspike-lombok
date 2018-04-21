package br.com.livraria.repository;

import br.com.livraria.modelo.Autor;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

import java.util.List;

@Repository
public interface AutorRepository extends EntityRepository<Autor, Integer> {

    @Query(value = "SELECT a FROM Autor a JOIN FETCH a.livros l where l.id = ?1")
    List<Autor> findByLivrosEqual(Integer livroId);
}
