package br.com.livraria.bean;

import br.com.livraria.modelo.Autor;
import br.com.livraria.modelo.Livro;
import br.com.livraria.repository.AutorRepository;
import br.com.livraria.repository.LivroRepository;
import br.com.livrarialib.helper.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import java.util.ArrayList;
import java.util.Calendar;
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
        List<Livro> livros = criarListaLivrosTeste();
        Mockito.when(mockLivroRepo.findAll()).thenReturn(livros);

        List<Livro> result = livroBean.getLivros();

        assertEquals(livros.size(), result.size());
        assertEquals(livros.get(0), result.get(0));
    }

    @Test
    public void testeMetodoGetAutoresDeveRetornarTodosAutoresDoRepositorio() {
        List<Autor> autores = criarListaAutoresTeste();
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
        Livro livro = criarLivroTeste();
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);

        livroBean.carregarLivroPelaId();
        Livro result = livroBean.getLivro();

        assertEquals(livro, result);
    }

    @Test
    public void testeMetodoGravarAutorDeveAdicionarUAutorAoLivro() {
        Autor autor = criarAutorTeste();
        livroBean.setAutorId(autor.getId());
        Mockito.when(mockAutorRepo.findBy(autor.getId())).thenReturn(autor);

        livroBean.gravarAutor();
        List<Autor> result = livroBean.getLivro().getAutores();

        assertFalse(result.isEmpty());
        assertEquals(autor, result.get(0));
    }

    @Test
    public void testeMetodoGravarDeveSalvarLivroNoRepositorioEProcurartodosLivrosOrdenadosPelaDataDeLancamentoELimparLivro() {
        Livro livro = criarLivroTeste();
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
        Livro livro = criarLivroTeste();
        livro.setAutores(new ArrayList<>());
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);
        livroBean.gravar();

        Mockito.verifyZeroInteractions(mockLivroRepo);
    }

    @Test
    public void testeMetodoRemoverDeveRemoverUmLivroDoRepositorioELimparLivroERepopularListaDeLivros() {
        Livro livro = criarLivroTeste();
        List<Livro> livros = criarListaLivrosTeste();
        Mockito.when(mockLivroRepo.findAll()).thenReturn(livros);

        livroBean.remover(livro);

        Mockito.verify(mockLivroRepo).attachAndRemove(livro);
        assertNull(livroBean.getLivro().getId());
        assertEquals(livros, livroBean.getLivros());
    }

    @Test
    public void testeMetodoRemoverAutorDoLivroDeveRemoverUmAutorDaListaDoLivro() {
        Livro livro = criarLivroTeste();
        livroBean.getLivro().setId(livro.getId());
        Mockito.when(mockLivroRepo.findBy(livro.getId())).thenReturn(livro);
        livroBean.gravar();
        Autor autor = livro.getAutores().get(0);

        livroBean.removerAutorDoLivro(autor);

        assertTrue(livroBean.getLivro().getAutores().isEmpty());
    }

    @Test
    public void testeMetodoCarregarDeveCarregarLivroSeusAutores() {
        Livro livro = criarLivroTeste();
        livro.setAutores(null);
        List<Autor> autores = criarListaAutoresTeste();
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

    private List<Livro> criarListaLivrosTeste() {
        List<Livro> livros = new ArrayList<>();
        Livro livro = criarLivroTeste();
        livros.add(livro);
        return livros;
    }

    private Livro criarLivroTeste() {
        return Livro.builder()
                .id(1)
                .titulo("tituloTeste")
                .preco(10.00)
                .isbn("0123465789")
                .dataLancamento(Calendar.getInstance())
                .autores(criarListaAutoresTeste())
                .build();
    }
}