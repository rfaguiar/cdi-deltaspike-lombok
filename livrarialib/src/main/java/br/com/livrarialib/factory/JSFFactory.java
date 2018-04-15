package br.com.livrarialib.factory;

import br.com.livrarialib.jsf.annotation.ScopeMap;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.util.Map;

public class JSFFactory {

    @Produces
    @RequestScoped
    public FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

    @Produces
    @RequestScoped
    public Flash getFlash(){
        return getExternalContext().getFlash();
    }

    @Produces
    @ScopeMap(ScopeMap.Scope.REQUEST)
    public Map<String,Object> getRequestMap(){
        return getExternalContext().getRequestMap();
    }

    @Produces
    @ScopeMap(ScopeMap.Scope.SESSION)
    public Map<String, Object> getSessionMap(){
        return getExternalContext().getSessionMap();
    }

    @Produces
    @ScopeMap(ScopeMap.Scope.APPLICATION)
    public Map<String,Object> getApplicationMap(){
        return getExternalContext().getApplicationMap();
    }

    @Produces
    @RequestScoped
    public NavigationHandler getNavigationHandler() {
        return getFacesContext().getApplication().getNavigationHandler();
    }

    private ExternalContext getExternalContext(){
        return getFacesContext().getExternalContext();
    }
}
