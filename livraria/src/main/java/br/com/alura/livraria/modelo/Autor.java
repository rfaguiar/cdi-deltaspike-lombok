package br.com.alura.livraria.modelo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private String email;
	private List<Livro> livros;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "autores")
    public List<Livro> getLivros() { return livros; }
}
