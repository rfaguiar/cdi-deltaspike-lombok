package br.com.livraria.bean;

import br.com.livraria.modelo.Usuario;
import br.com.livraria.repository.UsuarioRepository;
import br.com.livrarialib.helper.MessageHelper;
import br.com.livrarialib.jsf.annotation.ScopeMap;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Map;

@Model
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Getter
	private Usuario usuario;

	private UsuarioRepository usuarioRepo;
	private Map<String, Object> sessionMap;
	private MessageHelper helper;

	@Inject
    public LoginBean(UsuarioRepository usuarioRepo,
					 @ScopeMap(ScopeMap.Scope.SESSION) Map<String,Object> sessionMap,
                     MessageHelper helper) {
        this.usuarioRepo = usuarioRepo;
		this.sessionMap = sessionMap;
		this.helper = helper;
	}

	@PostConstruct
	public void init() { this.usuario = new Usuario(); }

	public String efetuaLogin() {
		System.out.println("fazendo login do usuario " + this.usuario.getEmail());

		boolean existe = usuarioRepo
				.findByEmailEqualAndSenhaEqual(this.usuario.getEmail(), this.usuario.getSenha())
				.isPresent();
		if(existe) {
            sessionMap.put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}

        helper.onFlash().addMessage(new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
        sessionMap.remove("usuarioLogado");
		return "login?faces-redirect=true";
	}
}
