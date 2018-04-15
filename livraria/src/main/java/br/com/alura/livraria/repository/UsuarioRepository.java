package br.com.alura.livraria.repository;

import br.com.alura.livraria.modelo.Usuario;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends EntityRepository<Usuario, Integer> {

    /*
        Metodo equivalente a query
        select u from Usuario u where u.email = :pEmail and u.senha = :pSenha
     */
    Optional<Usuario> findByEmailEqualAndSenhaEqual(String name, String Senha);
}
