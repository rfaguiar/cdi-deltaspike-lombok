package br.com.livraria.bean;

import br.com.livraria.modelo.Usuario;
import br.com.livraria.repository.UsuarioRepository;
import br.com.livrarialib.helper.MessageHelper;
import br.com.livrarialib.jsf.annotation.ScopeMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.util.Map;
import java.util.Optional;

@Model
@Slf4j
public class LoginBean {

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
        log.debug("fazendo login do usuario" + this.usuario.getEmail());
		Optional<Usuario> usuarioOptional = usuarioRepo
				.findByEmailEqualAndSenhaEqual(this.usuario.getEmail(), this.usuario.getSenha());
		if(usuarioOptional.isPresent()) {
            sessionMap.put("usuarioLogado", usuarioOptional.get());
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
