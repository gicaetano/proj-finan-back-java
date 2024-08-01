package br.com.xlcode.controlli.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private PasswordEncoder encoder;

	public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
		super();
		this.repository = repository;
		this.encoder = encoder;
	}

	public Usuario autenticar(String email, String senha) {
		Optional<Usuario> usuario = repository.findByEmail(email);

		if (usuario.isEmpty()) {
			throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado.");
		}

		boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());

		if (!senhasBatem) {
			throw new ErroAutenticacao("Senha inválida");
		}
		return usuario.get();
	}

	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		criptografarSenha(usuario);
		return repository.save(usuario);
	}

	private void criptografarSenha(Usuario usuario) {
		String senha = usuario.getSenha();
		String senhaCripto = encoder.encode(senha);
		usuario.setSenha(senhaCripto);
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
