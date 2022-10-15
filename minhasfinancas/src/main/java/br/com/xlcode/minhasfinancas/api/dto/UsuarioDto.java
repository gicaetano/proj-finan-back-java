package br.com.xlcode.minhasfinancas.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {
	
	private String email;
	private String nome;
	private String senha;

}
