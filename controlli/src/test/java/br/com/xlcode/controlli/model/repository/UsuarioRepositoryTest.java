//package br.com.xlcode.controlli.model.repository;
//
//import java.util.Optional;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import br.com.xlcode.controlli.model.entity.Usuario;
//import br.com.xlcode.controlli.model.repositories.UsuarioRepository;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = Replace.NONE)
//public class UsuarioRepositoryTest {
//
//	@Autowired
//	UsuarioRepository repository;
//
//	@Autowired
//	TestEntityManager entityManager;
//
//	@Test
//	public void deveVerificarAExistenciaDeUmEmail() {
//		//cenario
//		//Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
//		Usuario usuario = criarUsuario();
//		entityManager.persist(usuario);
//		//ação/execução
//		boolean result = repository.existsByEmail("usuario@email.com");
//		//verificação
//		Assertions.assertThat(result).isTrue();
//	}
//
//	@Test
//	public void deveRetornarFalsoQuandoNãoHouverUsuarioCadastradoComOEmail() {
//		//cenário
//
//		//acão/execucao
//		boolean result = repository.existsByEmail("usuario@email.com");
//		//verificaçao
//		Assertions.assertThat(result).isFalse();
//	}
//
//	@Test
//	public void devePersistirUmUsuarioNaBaseDeDados() {
//		//cenario
//		Usuario usuario = criarUsuario();
//		//acao
//		Usuario usuarioSalvo = repository.save(usuario);
//		//verificacao
//		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
//	}
//
//	@Test
//	public void deveRetornarVazioBuscarUsuarioPorEmailQuandoNaoExiteNaBase() {
//		//cenario
//
//		//verificacao
//		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
//
//		Assertions.assertThat(result.isPresent()).isFalse();
//
//	}
//
//	@Test
//	public void deveBuscarUmUsuarioPorEmail() {
//		//cenario
//		Usuario usuario = criarUsuario();
//		entityManager.persist(usuario);
//		//verificacao
//		Optional<Usuario> result = repository.findByEmail("usuario@email.com");
//
//		Assertions.assertThat(result.isPresent()).isTrue();
//
//	}
//
//	public static Usuario criarUsuario() {
//		return Usuario
//				.builder()
//				.nome("usuario")
//				.email("usuario@email.com")
//				.senha("senha")
//				.build();
//	}
//}
