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
import java.io.Serializable;
import java.util.Map;

@Slf4j
public class Autorizador implements Serializable {

	@Inject
	private FacesContext context;

	@Inject
	@ScopeMap(ScopeMap.Scope.SESSION)
	private Map<String, Object> sessionMap;

	@Inject
	private NavigationHandler handler;

	public void autoriza( @Observes @After @Phase(Phase.Phases.RESTORE_VIEW) PhaseEvent event) {
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
