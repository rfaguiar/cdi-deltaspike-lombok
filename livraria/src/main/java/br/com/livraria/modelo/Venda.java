package br.com.livraria.modelo;

import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

	private Livro livro;
	private Integer quantidade;

}
