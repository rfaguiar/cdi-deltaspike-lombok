package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livrarialib.dao.DAO;
import br.com.livrarialib.tx.annotation.Transacional;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Model
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Autor autor = new Autor();
	
	private Integer autorId;

	private DAO<Autor, Integer> autorDAO;

	@Inject
    public AutorBean(DAO<Autor, Integer> autorDAO) {
        this.autorDAO = autorDAO;
    }

    public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPelaId() {
		this.autor = autorDAO.buscaPorId(autorId);
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null) {
            autorDAO.adiciona(this.autor);
		} else {
            autorDAO.atualiza(this.autor);
		}

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}

	@Transacional
	public void remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
        autorDAO.remove(autor);
	}
	
	public List<Autor> getAutores() {
		return autorDAO.listaTodos();
	}
	
	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
}
