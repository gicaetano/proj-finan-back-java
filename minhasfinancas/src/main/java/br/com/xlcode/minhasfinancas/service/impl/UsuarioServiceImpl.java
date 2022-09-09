package br.com.xlcode.minhasfinancas.service.impl;

import org.springframework.stereotype.Service;

import br.com.xlcode.minhasfinancas.exception.RegraNegocioException;
import br.com.xlcode.minhasfinancas.model.entity.Usuario;
import br.com.xlcode.minhasfinancas.model.repositories.UsuarioRepository;
import br.com.xlcode.minhasfinancas.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	public Usuario autenticar(String email, String senha) {
		return null;
	}

	public Usuario salvarUsuario(Usuario usuario) {
		return null;
	}

	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
	}

}
