package br.com.xlcode.controlli.api.resource;

import java.util.List;
import java.util.Optional;

import br.com.xlcode.controlli.api.mapper.LancamentoMapper;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.xlcode.controlli.api.dto.AtualizaStatusDTO;
import br.com.xlcode.controlli.api.dto.LancamentoDTO;
import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Lancamento;
import br.com.xlcode.controlli.model.entity.Usuario;
import br.com.xlcode.controlli.model.enums.StatusLancamento;
import br.com.xlcode.controlli.model.enums.TipoLancamento;
import br.com.xlcode.controlli.service.LancamentoService;
import br.com.xlcode.controlli.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lancamentos")
public class LancamentoResource {

	private final LancamentoService lancamentoService;
	private final UsuarioService usuarioService;
	private final LancamentoMapper lancamentoMapper;
	
	@GetMapping
	public ResponseEntity buscar( 			
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "mes", required = false) Integer mes,
			@RequestParam(value = "ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
			) {
		
		Lancamento lancamentoFiltro = new Lancamento();
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);
		
		Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
		if(!usuario.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado para o Id informado");
		}else {
			lancamentoFiltro.setUsuario(usuario.get());
		}

		List<Lancamento> lancamentos = lancamentoService.buscar(lancamentoFiltro);

		return ResponseEntity.ok(lancamentos);
	}

	@GetMapping("{id}")
	public ResponseEntity obterLancamento(@PathVariable("id") Long id) {
		return lancamentoService.obterPorId(id)
				.map( lancamento -> new ResponseEntity(lancamentoMapper.converterToDto(lancamento), HttpStatus.OK) )
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND));
	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
		try {
			Lancamento entidade = lancamentoMapper.converterToEntity(dto);
			lancamentoService.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch(RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizar( @PathVariable("id") Long id, @RequestBody LancamentoDTO dto ) {
		return lancamentoService.obterPorId(id).map(entity -> {
			try {
				Lancamento lancamento = lancamentoMapper.converterToEntity(dto);
				lancamento.setId(entity.getId());
				lancamentoService.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () -> 
			new ResponseEntity("Lancamento não encontrado na base de daods.", HttpStatus.BAD_REQUEST ));
	}
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus( @PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
		return lancamentoService.obterPorId(id).map(entity -> {
			StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
			
			if (statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do lancamento, envie um status válido.");
			}
			
			try {
				entity.setStatus(statusSelecionado);
				lancamentoService.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
			
		}).orElseGet( () -> 
		new ResponseEntity("Lancamento não encontrado na base de daods.", HttpStatus.BAD_REQUEST ));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar (@PathVariable("id") Long id) {
		return lancamentoService.obterPorId(id).map( entidade -> {
			lancamentoService.deletar(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet( () -> 
		new ResponseEntity("Lancamento não encontrado na base de daods.", HttpStatus.BAD_REQUEST ));
	}

//	private Lancamento converter(LancamentoDTO dto) {
//		Lancamento lancamento = new Lancamento();
//		lancamento.setId(dto.getId());
//		lancamento.setDescricao(dto.getDescricao());
//		lancamento.setAno(dto.getAno());
//		lancamento.setMes(dto.getMes());
//		lancamento.setValor(dto.getValor());
//
//
//		Usuario usuario = usuarioService
//			.obterPorId(dto.getUsuario())
//			.orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id informado"));
//
//		lancamento.setUsuario(usuario);
//
//		if(dto.getTipo() != null) {
//			lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
//		}
//
//		if(dto.getStatus() != null) {
//			lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
//		}
//
//		return lancamento;
//	}

}
