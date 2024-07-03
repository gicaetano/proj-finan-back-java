package br.com.xlcode.controlli.api.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
	
	private String email;
	private String nome;
	private String senha;

}
