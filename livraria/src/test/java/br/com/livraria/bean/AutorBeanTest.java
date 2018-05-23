package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livraria.repository.AutorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AutorBeanTest {

    private AutorBean autorBean;
    @Mock
    private AutorRepository mockAutorRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.autorBean = new AutorBean(mockAutorRepo);
        this.autorBean.init();
    }

    @Test
    public void testeMetodoCarregarAutorPelaIdDeveBurcarNoRepositorioAutorPeloId() {
        autorBean.setAutorId(1);
        Autor autor = criarAutorTeste();
        Mockito.when(mockAutorRepo.findBy(1)).thenReturn(autor);

        autorBean.carregarAutorPelaId();

        assertEquals(autor, autorBean.getAutor());
    }

    @Test
    public void testeMetodoGravarDeveSalvarAutorNoRepositorioELimparAutorERedirecionarParaTelaDeLivro() {
        Autor autor = criarAutorTeste();
        autorBean.setAutor(autor);
        Mockito.when(mockAutorRepo.save(autor)).thenReturn(autor);

        String result = autorBean.gravar();

        assertNotEquals(autor, autorBean.getAutor());
        assertEquals("livro?faces-redirect=true", result);
    }

    @Test
    public void testeMetodoRemoverDeveRemoverUmAutorDoRepositorio() {
        Autor autor = criarAutorTeste();
        autorBean.remover(autor);

        Mockito.verify(mockAutorRepo).remove(autor);
    }

    @Test
    public void testeMetodoGetAutoresDeveRetornarTodosAutoresDoRepositorio() {
        List<Autor> autores = criarListaAutoresTeste();
        Mockito.when(mockAutorRepo.findAll()).thenReturn(autores);

        List<Autor> result = autorBean.getAutores();

        assertEquals(autores, result);
    }

    private List<Autor> criarListaAutoresTeste() {
        List<Autor> autores = new ArrayList<>();
        autores.add(criarAutorTeste());
        return autores;
    }

    private Autor criarAutorTeste() {
        return Autor.builder()
                .id(1)
                .nome("autor teste")
                .email("email@teste.com")
                .livros(new ArrayList<>())
                .build();
    }

}