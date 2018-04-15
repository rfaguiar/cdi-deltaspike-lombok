package br.com.alura.livraria.bean;

import br.com.alura.livraria.modelo.Autor;
import br.com.alura.livraria.modelo.Livro;
import br.com.alura.livraria.repository.AutorRepository;
import br.com.alura.livraria.repository.LivroRepository;
import br.com.livrarialib.helper.MessageHelper;
import br.com.livrarialib.jsf.annotation.ViewModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@ViewModel
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	private Livro livro;
	@Getter @Setter
	private Integer autorId;
	private List<Livro> livros;
    private LivroRepository livroRepo;
    private AutorRepository autorRepo;
    private MessageHelper helper;

    @Inject
    public LivroBean(LivroRepository livroRepo, AutorRepository autorRepo,
                     MessageHelper helper) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
        this.helper = helper;
    }

    @PostConstruct
    public void init() {
        this.livro = this.criarLivro();
    }

    public List<Livro> getLivros() {
		if(this.livros == null) {
			this.livros = livroRepo.findAll();
		}
		return livros;
	}

	public List<Autor> getAutores() {
		return autorRepo.findAll();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public void carregarLivroPelaId() {
		this.livro = livroRepo.findBy(this.livro.getId());
	}

	public void gravarAutor() {
		Autor autor = autorRepo.findBy(this.autorId);
		this.livro.adicionaAutor(autor);
		System.out.println("Escrito por: " + autor.getNome());
	}

	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores() == null || livro.getAutores().isEmpty()) {
            helper.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor."));
			return;
		}

        livroRepo.save(this.livro);
        this.livros = livroRepo.findAll();
		this.livro = this.criarLivro();
	}

	public void remover(Livro livro) {
		System.out.println("Removendo livro");
        livroRepo.attachAndRemove(livro);
		this.livros = livroRepo.findAll();
		this.livro = this.criarLivro();
	}

	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro");
		livro.setAutores(autorRepo.findByLivrosEqual(livro.getId()));
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

    private Livro criarLivro() {
        return Livro.builder()
                .autores(new ArrayList<>())
                .dataLancamento(Calendar.getInstance())
                .build();
    }
}
