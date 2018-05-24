package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livraria.modelo.Livro;
import br.com.livraria.repository.AutorRepository;
import br.com.livraria.repository.LivroRepository;
import br.com.livraria.util.ModelsBuilder;
import br.com.livrarialib.helper.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LivroBeanTest {

    private LivroBean livroBean;
    @Mock
    private MessageHelper mockMessageHelper;
    @Mock
    private AutorRepository mockAutorRepo;
    @Mock
    private LivroRepository mockLivroRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.livroBean = new LivroBean(mockLivroRepo, mockAutorRepo, mockMessageHelper);
        this.livroBean.init();
    }

    @Test
    public void testeMetodoGetLivrosDeveRetornarTodosLivrosSalvosNoRepositorio() {
        List<Livro> livros = ModelsBuilder.criarListaLivrosTeste();
        Mockito.when(mockLivroRepo.findAll()).thenReturn(livros);

        List<Livro> result = livroBean.getLivros();

        assertEquals(livros.size(), result.size());
        assertEquals(livros.get(0), result.get(0));
    }

    @Test
    public void testeMetodoGetAutoresDeveRetornarTodosAutoresDoRepositorio() {
        List<Autor> autores = ModelsBuilder.criarListaAutoresTeste();
        Mockito.when(mockAutorRepo.findAll()).thenReturn(autores);

        List<Autor> result = livroBean.getAutores();

        assertEquals(autores.size(), result.size());
        assertEquals(autores.get(0), result.get(0));
    }

    @Test
    public void testeMetodoGetAutoresDoLivroDeveRetornarListaDeAutoresDoLivroSelecionado() {
        List<Autor> result = livroBean.getAutoresDoLivro();

        assertTrue(result.isEmpty());
    }

    @Test
    public void testeMetodoCarregarLivroPelaIdDeveIniciarLivroPeloIdInformado() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);

        livroBean.carregarLivroPelaId();
        Livro result = livroBean.getLivro();

        assertEquals(livro, result);
    }

    @Test
    public void testeMetodoGravarAutorDeveAdicionarUAutorAoLivro() {
        Autor autor = ModelsBuilder.criarAutorTeste();
        livroBean.setAutorId(autor.getId());
        Mockito.when(mockAutorRepo.findBy(autor.getId())).thenReturn(autor);

        livroBean.gravarAutor();
        List<Autor> result = livroBean.getLivro().getAutores();

        assertFalse(result.isEmpty());
        assertEquals(autor, result.get(0));
    }

    @Test
    public void testeMetodoGravarDeveSalvarLivroNoRepositorioEProcurartodosLivrosOrdenadosPelaDataDeLancamentoELimparLivro() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);
        livroBean.carregarLivroPelaId();

        livroBean.gravar();

        Mockito.verify(mockLivroRepo).save(livro);
        Mockito.verify(mockLivroRepo).findAllOrderByDataLancamentoDesc();
        assertNotEquals(livro, livroBean.getLivro());
    }

    @Test
    public void testeMetodoGravarQuandoAutoresVazioDeveMostrarMsgAdequada() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        livro.setAutores(new ArrayList<>());
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);
        livroBean.gravar();

        Mockito.verifyZeroInteractions(mockLivroRepo);
    }

    @Test
    public void testeMetodoRemoverDeveRemoverUmLivroDoRepositorioELimparLivroERepopularListaDeLivros() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        List<Livro> livros = ModelsBuilder.criarListaLivrosTeste();
        Mockito.when(mockLivroRepo.findAll()).thenReturn(livros);

        livroBean.remover(livro);

        Mockito.verify(mockLivroRepo).attachAndRemove(livro);
        assertNull(livroBean.getLivro().getId());
        assertEquals(livros, livroBean.getLivros());
    }

    @Test
    public void testeMetodoRemoverAutorDoLivroDeveRemoverUmAutorDaListaDoLivro() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);
        livroBean.gravar();
        Autor autor = livro.getAutores().get(0);

        livroBean.removerAutorDoLivro(autor);

        assertTrue(livroBean.getLivro().getAutores().isEmpty());
    }

    @Test
    public void testeMetodoCarregarDeveCarregarLivroSeusAutores() {
        Livro livro = ModelsBuilder.criarLivroTeste();
        livro.setAutores(null);
        List<Autor> autores = ModelsBuilder.criarListaAutoresTeste();
        Mockito.when(mockAutorRepo.findByLivrosEqual(livro.getId())).thenReturn(autores);

        livroBean.carregar(livro);

        assertEquals(autores, livroBean.getLivro().getAutores());
    }

    @Test
    public void testeMetodoFormAutorDeveRetornarRedirectAutor() {
        String result = livroBean.formAutor();

        assertEquals("autor?faces-redirect=true", result);
    }

    @Test(expected = ValidatorException.class)
    public void testeMetodoComecaComDigitoUmQuandoValorNaoIniciarComDigitoUmDeveLancarExcecaoComMsgAdequada() {
        try {
            livroBean.comecaComDigitoUm("0123456789");
        } catch (Exception e) {
            assertEquals("ISBN deveria começar com 1", e.getMessage());
            throw e;
        }
        fail();
    }

    @Test
    public void testeMetodoComecaComDigitoUmQuandoValorIniciarComDigitoUm() {
        try {
            livroBean.comecaComDigitoUm("123456789");
        } catch (Exception e) {
            fail();
        }
        assertTrue(true);
    }
}