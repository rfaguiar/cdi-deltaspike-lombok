package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livraria.repository.AutorRepository;
import br.com.livraria.util.ModelsBuilder;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
        Autor autor = ModelsBuilder.criarAutorTeste();
        Mockito.when(mockAutorRepo.findBy(1)).thenReturn(autor);

        autorBean.carregarAutorPelaId();

        assertEquals(autor, autorBean.getAutor());
    }

    @Test
    public void testeMetodoGravarDeveSalvarAutorNoRepositorioELimparAutorERedirecionarParaTelaDeLivro() {
        Autor autor = ModelsBuilder.criarAutorTeste();
        autorBean.setAutor(autor);
        Mockito.when(mockAutorRepo.save(autor)).thenReturn(autor);

        String result = autorBean.gravar();

        assertNotEquals(autor, autorBean.getAutor());
        assertEquals("livro?faces-redirect=true", result);
    }

    @Test
    public void testeMetodoRemoverDeveRemoverUmAutorDoRepositorio() {
        Autor autor = ModelsBuilder.criarAutorTeste();
        autorBean.remover(autor);

        Mockito.verify(mockAutorRepo).remove(autor);
    }

    @Test
    public void testeMetodoGetAutoresDeveRetornarTodosAutoresDoRepositorio() {
        List<Autor> autores = ModelsBuilder.criarListaAutoresTeste();
        Mockito.when(mockAutorRepo.findAll()).thenReturn(autores);

        List<Autor> result = autorBean.getAutores();

        assertEquals(autores, result);
    }

}