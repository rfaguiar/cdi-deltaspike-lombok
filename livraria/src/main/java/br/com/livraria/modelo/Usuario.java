package br.com.livraria.modelo;

import lombok.*;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String senha;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
	
	

}
