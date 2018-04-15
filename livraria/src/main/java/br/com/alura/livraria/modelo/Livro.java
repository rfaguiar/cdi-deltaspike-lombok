package br.com.alura.livraria.modelo;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String titulo;
	private String isbn;
	private double preco;
	private Calendar dataLancamento;

	private List<Autor> autores;

    @Id
    @GeneratedValue
	public Integer getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public double getPreco() {
		return preco;
	}

    @Temporal(TemporalType.DATE)
	public Calendar getDataLancamento() {
		return dataLancamento;
	}

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "Livro_Autor",
            joinColumns = { @JoinColumn(name = "Livro_id") },
            inverseJoinColumns = { @JoinColumn(name = "autores_id") })
    public List<Autor> getAutores() {
        return autores;
    }

	public void removeAutor(Autor autor) {
        if (this.autores == null) this.autores = new ArrayList<>();
		this.autores.remove(autor);
	}

    public void adicionaAutor(Autor autor) {
        if (this.autores == null) this.autores = new ArrayList<>();
        this.autores.add(autor);
    }

}