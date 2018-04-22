package br.com.livraria.repository;

import br.com.livraria.modelo.Usuario;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends EntityRepository<Usuario, Integer>, Serializable {

    /*
        Metodo equivalente a query
        select u from Usuario u where u.email = :pEmail and u.senha = :pSenha
     */
    Optional<Usuario> findByEmailEqualAndSenhaEqual(String name, String Senha);
}
