package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livraria.repository.AutorRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Slf4j
@Model
public class AutorBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private Autor autor;

	@Getter @Setter
	private Integer autorId;

    private AutorRepository autorRepo;

	@Inject
    public AutorBean(AutorRepository autorRepo) {
        this.autorRepo = autorRepo;
    }

    @PostConstruct
	public void init() {
		this.autor = new Autor();
	}

	public void carregarAutorPelaId() {
		this.autor = autorRepo.findBy(autorId);
	}

	public String gravar() {
		log.debug("Gravando autor " + this.autor.getNome());

		this.autorRepo.save(autor);

		this.autor = new Autor();

		return "livro?faces-redirect=true";
	}

	public void remover(Autor autor) {
        log.debug("Removendo autor " + autor.getNome());
        autorRepo.remove(autor);
	}
	
	public List<Autor> getAutores() {
		return autorRepo.findAll();
	}

}
