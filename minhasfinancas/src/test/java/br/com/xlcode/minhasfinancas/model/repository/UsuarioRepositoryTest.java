package br.com.xlcode.minhasfinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.xlcode.minhasfinancas.model.entity.Usuario;
import br.com.xlcode.minhasfinancas.model.repositories.UsuarioRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	
	@Autowired
	UsuarioRepository repository;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
		repository.save(usuario);
		//ação/execução
		boolean result = repository.existsByEmail("usuario@email.com");
		//verificação
		Assertions.assertThat(result).isTrue();
	}

}
