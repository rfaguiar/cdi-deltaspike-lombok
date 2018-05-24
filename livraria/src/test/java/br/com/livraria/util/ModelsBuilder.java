package br.com.livraria.util;

import br.com.livraria.modelo.Autor;
import br.com.livraria.modelo.Livro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class ModelsBuilder {

    private ModelsBuilder() {}

    public static List<Livro> criarListaLivrosTeste() {
        List<Livro> livros = new ArrayList<>();
        Livro livro = criarLivroTeste();
        livros.add(livro);
        return livros;
    }

    public static Livro criarLivroTeste() {
        return Livro.builder()
                .id(1)
                .titulo("tituloTeste")
                .preco(10.00)
                .isbn("0123465789")
                .dataLancamento(Calendar.getInstance())
                .autores(criarListaAutoresTeste())
                .build();
    }

    public static List<Autor> criarListaAutoresTeste() {
        List<Autor> autores = new ArrayList<>();
        autores.add(criarAutorTeste());
        return autores;
    }

    public static Autor criarAutorTeste() {
        return Autor.builder()
                .id(1)
                .nome("autor teste")
                .email("email@teste.com")
                .livros(new ArrayList<>())
                .build();
    }
}
