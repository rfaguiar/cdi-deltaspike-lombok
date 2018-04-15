package br.com.alura.livraria.bean;

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.livrarialib.dao.DAO;
import br.com.livrarialib.helper.MessageHelper;
import br.com.livrarialib.jsf.annotation.ViewModel;
import br.com.livrarialib.tx.annotation.Transacional;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewModel
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Livro livro = new Livro();

	private Integer autorId;

	private List<Livro> livros;

    private DAO<Livro, Integer> livroDAO;
    private DAO<Autor, Integer> autorDAO;
    private MessageHelper helper;

    @Inject
    public LivroBean(DAO<Livro, Integer> livroDAO, DAO<Autor, Integer> autorDAO,
                     MessageHelper helper) {
        this.livroDAO = livroDAO;
        this.autorDAO = autorDAO;
        this.helper = helper;
    }

    public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}

	public Integer getAutorId() {
		return autorId;
	}

	public Livro getLivro() {
		return livro;
	}

	public List<Livro> getLivros() {
		if(this.livros == null) {
			this.livros = livroDAO.listaTodos();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return autorDAO.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void carregarLivroPelaId() {
		this.livro = livroDAO.buscaPorId(this.livro.getId());
	}

	@Transacional
	public void gravarAutor() {
		Autor autor = autorDAO.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	@Transacional
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
            helper.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

		if(this.livro.getId() == null) {
            livroDAO.adiciona(this.livro);
			this.livros = livroDAO.listaTodos();
		} else {
            livroDAO.atualiza(this.livro);
		}

		this.livro = new Livro();
	}

	@Transacional
	public void remover(Livro livro) {
		System.out.println("Removendo livro");
        livroDAO.remove(livro);
		this.livros = livroDAO.listaTodos();
	}

	@Transacional
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		this.livro = livro;
	}
	
	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	public void comecaComDigitoUm(FacesContext fc, UIComponent component,
			Object value) throws ValidatorException {

		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage(
					"ISBN deveria começar com 1"));
		}

	}
}
