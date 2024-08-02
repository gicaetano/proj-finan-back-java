package br.com.xlcode.controlli.api.resource;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.xlcode.controlli.api.dto.TokenDTO;
import br.com.xlcode.controlli.service.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.xlcode.controlli.api.dto.UsuarioDto;
import br.com.xlcode.controlli.exception.ErroAutenticacao;
import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Usuario;
import br.com.xlcode.controlli.service.LancamentoService;
import br.com.xlcode.controlli.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@Api(value = "Usuários", tags = "Usuários")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource {
	
	private final UsuarioService service;
	private final LancamentoService lancamentoService;
	private final JwtService jwtService;

	@ApiOperation(value = "Autenticar usuário")
	@PostMapping("/autenticar")
	public ResponseEntity<?> autenticar (@RequestBody UsuarioDto dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			String token = jwtService.gerarToken(usuarioAutenticado);
			TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getNome(), token);
			return ResponseEntity.ok(tokenDTO);
		} catch(ErroAutenticacao e ) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@ApiOperation(value = "Salvar usuário")
	@PostMapping
	public ResponseEntity salvar (@RequestBody UsuarioDto dto) {
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e){
			return ResponseEntity.badRequest().body(e.getMessage());			
		}
	}

	@ApiOperation(value = "Obter saldo do usuário")
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo( @PathVariable("id") Long id ) {
		Optional<Usuario> usuario = service.obterPorId(id);
		
		if(!usuario.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);		
			}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}
}
