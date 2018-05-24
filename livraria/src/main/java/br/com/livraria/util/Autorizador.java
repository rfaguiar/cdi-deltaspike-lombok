package br.com.livraria.util;

import br.com.livraria.modelo.Usuario;
import br.com.livrarialib.jsf.annotation.After;
import br.com.livrarialib.jsf.annotation.Phase;
import br.com.livrarialib.jsf.annotation.ScopeMap;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.event.Observes;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import java.util.Map;

@Slf4j
public class Autorizador {

	private FacesContext context;
	private Map<String, Object> sessionMap;
	private NavigationHandler handler;

	@Inject
	public Autorizador(FacesContext context, @ScopeMap(ScopeMap.Scope.SESSION) Map<String, Object> sessionMap, NavigationHandler handler) {
		this.context = context;
		this.sessionMap = sessionMap;
		this.handler = handler;
	}

	public void autoriza(@Observes @After @Phase(Phase.Phases.RESTORE_VIEW) PhaseEvent event) {
		String nomePagina = context.getViewRoot().getViewId();

		log.debug(nomePagina);

		if ("/login.xhtml".equals(nomePagina)) {
			return;
		}

		Usuario usuarioLogado = (Usuario) sessionMap.get("usuarioLogado");

		if (usuarioLogado != null) {
			return;
		}

		// redirecionamento para login.xhtml

		handler.handleNavigation(context, null, "/login?faces-redirect=true");
		context.renderResponse();
	}

}
