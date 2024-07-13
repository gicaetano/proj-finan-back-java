//package br.com.xlcode.controlli.service;
//
//import java.util.Optional;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import br.com.xlcode.controlli.exception.ErroAutenticacao;
//import br.com.xlcode.controlli.exception.RegraNegocioException;
//import br.com.xlcode.controlli.model.entity.Usuario;
//import br.com.xlcode.controlli.model.repositories.UsuarioRepository;
//import br.com.xlcode.controlli.service.impl.UsuarioServiceImpl;
//
//@RunWith(SpringRunner.class)
//@ActiveProfiles("test")
//public class UsuarioServiceTest {
//
//	UsuarioService service;
//
//	@MockBean
//	UsuarioRepository repository;
//
//	@Before
//	public void setUp() {
//		service = new UsuarioServiceImpl(repository);
//	}
//
//	@Test(expected = ErroAutenticacao.class)
//	public void deveAutenticarUmUsuarioComSucesso() {
//		//cenario
//		String email = "email@email.com";
//		String senha = "senha";
//
//		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
//		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
//
//		//acao
//		Usuario result = service.autenticar(email, senha);
//
//		//verificaçao
//		Assertions.assertThat(result).isNotNull();
//
//	}
//
//	@Test(expected = ErroAutenticacao.class)
//	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
//		//cenario
//		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
//		//acao
//		service.autenticar("email@email.com", "senha");
//	}
//
//	//@Test
//	public void deveLancarErroQuandoSenhaNaoBater() {
//		//cenario
//		String senha = "senha";
//		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
//		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
//		//acao
//		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123"));
//		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha Inválida");
//	}
//
//	@Test(expected = Test.None.class)
//	public void deveValidarEmail() {
//		//cenario
//		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
//
//		//ação
//		service.validarEmail("email@email.com");
//	}
//
//	@Test(expected = RegraNegocioException.class)
//	public void deveLancarErroAoValidarQaundoExistirEmailCadastrado() {
//		//cenario
//		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
//		//acao
//		service.validarEmail("usuario@email.com");
//	}
//
//}
