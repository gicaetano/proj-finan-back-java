package br.com.xlcode.minhasfinancas.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xlcode.minhasfinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	//Optional<Usuario> findByEmailAndNome(String email, String nome);
	
	boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String email);
}
