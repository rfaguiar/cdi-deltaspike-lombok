package br.com.alura.livraria.dao;

import br.com.alura.livraria.modelo.Usuario;
import br.com.livrarialib.jpa.annotation.Query;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;

public class UsuarioDao implements Serializable {

	@Inject @Query("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha")
	private TypedQuery<Usuario> query;


	public boolean existe(Usuario usuario) {
		
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}

}
