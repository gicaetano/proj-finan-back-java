package br.com.xlcode.controlli.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.xlcode.controlli.exception.ErroAutenticacao;
import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Usuario;
import br.com.xlcode.controlli.model.repositories.UsuarioRepository;
import br.com.xlcode.controlli.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private UsuarioRepository repository;

	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}

	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuarioOptional = repository.findByEmail(email);

		if (!usuarioOptional.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado.");
		}

		Usuario usuario = usuarioOptional.get();

		// Comparação simples de senha
		if (!usuario.getSenha().equals(senha)) {
			throw new ErroAutenticacao("Senha inválida");
		}

		return usuario;
	}

//	public Usuario autenticar(String email, String senha) {
//		Optional<Usuario> usuario = repository.findByEmail(email);
//
//		if(!usuario.isPresent()) {
//			throw new ErroAutenticacao("Usuário não encontrado, para o e-mail informado.");
//		}
//
//		if(usuario.get().getSenha().equals(senha)) {
//			throw new ErroAutenticacao("Senha inválida");
//		}
//
//		return null;
//	}

	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
	}

	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}

}
