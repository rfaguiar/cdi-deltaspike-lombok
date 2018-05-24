package br.com.livraria.bean;

import br.com.livraria.modelo.Usuario;
import br.com.livraria.repository.UsuarioRepository;
import br.com.livrarialib.helper.MessageHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.faces.application.FacesMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class LoginBeanTest {

    private Map<String, Object> mockSessionMap;

    private LoginBean loginBean;

    @Mock
    private MessageHelper mockMessageHelper;
    @Mock
    private UsuarioRepository mockUsuarioRepo;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockSessionMap = new HashMap<>();
        loginBean = new LoginBean(mockUsuarioRepo, mockSessionMap, mockMessageHelper);
        loginBean.init();
        Mockito.when(mockMessageHelper.onFlash()).thenReturn(mockMessageHelper);
    }

    @Test
    public void testMetodoEfetuarLoginQuandUsuarioESenhaCorretosDeveRedirecionarParaTelaDeNovoLivro() {
        Optional<Usuario> usuario = Optional.of(new Usuario(1, "email", "senha"));
        loginBean.getUsuario().setEmail(usuario.get().getEmail());
        loginBean.getUsuario().setSenha(usuario.get().getSenha());
        Mockito.when(mockUsuarioRepo.findByEmailEqualAndSenhaEqual(usuario.get().getEmail(), usuario.get().getSenha()))
                .thenReturn(usuario);

        String pagina = loginBean.efetuaLogin();

        Mockito.verifyZeroInteractions(mockMessageHelper);
        assertEquals(mockSessionMap.get("usuarioLogado"), usuario.get());
        assertEquals("livro?faces-redirect=true", pagina);
    }

    @Test
    public void testMetodoEfetuarLoginQuandoUsuarioESenhaIncorretosDeveRedirecionarParaLoginEMsgErro() {
        Optional<Usuario> usuario = Optional.empty();
        Mockito.when(mockUsuarioRepo.findByEmailEqualAndSenhaEqual(null, null))
                .thenReturn(usuario);

        String pagina = loginBean.efetuaLogin();

        assertEquals(mockSessionMap.get("usuarioLogado"), null);
        assertEquals("login?faces-redirect=true", pagina);
    }

    @Test
    public void testMetodoDeslogar() {
        mockSessionMap.put("usuarioLogado", "usuarioLogado");

        String result = loginBean.deslogar();

        assertNull(mockSessionMap.get("usuarioLogado"));
        assertEquals(result, "login?faces-redirect=true");

    }
}
