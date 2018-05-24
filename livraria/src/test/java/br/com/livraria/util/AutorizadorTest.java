package br.com.livraria.util;

import br.com.livraria.modelo.Usuario;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import java.util.HashMap;
import java.util.Map;

public class AutorizadorTest {

    private Autorizador autorizador;
    private Map<String, Object> mockSessionMap;
    @Mock
    private PhaseEvent mockPhaseEvent;
    @Mock
    private FacesContext mockFacesContext;
    @Mock
    private NavigationHandler mockNavigationHandler;
    @Mock
    private UIViewRoot mockUiViewRoot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockSessionMap = new HashMap<>();
        this.autorizador = new Autorizador(mockFacesContext, mockSessionMap, mockNavigationHandler);
    }

    @Test
    public void testeMetodoAutorizaQuandoUsuarioAutorizadoDeveRetornarParaTelaSelecionada() {
        Usuario usuario = ModelsBuilder.criarUsuarioTeste();
        mockSessionMap.put("usuarioLogado", usuario);
        Mockito.when(mockFacesContext.getViewRoot()).thenReturn(mockUiViewRoot);
        Mockito.when(mockUiViewRoot.getViewId()).thenReturn("teste.html");

        autorizador.autoriza(mockPhaseEvent);

        Mockito.verifyZeroInteractions(mockNavigationHandler);
    }

    @Test
    public void testeMetodoAutorizaQuandoUsuarioNaoAutorizadoEPaginaDiferenteDeLoginDeveRetornarParaPaginaDeLogin() {
        Mockito.when(mockFacesContext.getViewRoot()).thenReturn(mockUiViewRoot);
        Mockito.when(mockUiViewRoot.getViewId()).thenReturn("teste.html");

        autorizador.autoriza(mockPhaseEvent);

        Mockito.verify(mockNavigationHandler).handleNavigation(mockFacesContext, null, "/login?faces-redirect=true");
    }

    @Test
    public void testeMetodoAutorizaQuandoPaginaLoginDeveRetornarParaPaginaDeLogin() {
        Mockito.when(mockFacesContext.getViewRoot()).thenReturn(mockUiViewRoot);
        Mockito.when(mockUiViewRoot.getViewId()).thenReturn("/login.xhtml");

        autorizador.autoriza(mockPhaseEvent);

        Mockito.verifyZeroInteractions(mockNavigationHandler);
    }
}