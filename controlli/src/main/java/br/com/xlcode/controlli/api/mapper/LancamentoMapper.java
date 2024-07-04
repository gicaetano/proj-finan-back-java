package br.com.xlcode.controlli.api.mapper;

import br.com.xlcode.controlli.api.dto.LancamentoDTO;
import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Lancamento;
import br.com.xlcode.controlli.model.entity.Usuario;
import br.com.xlcode.controlli.model.enums.StatusLancamento;
import br.com.xlcode.controlli.model.enums.TipoLancamento;
import br.com.xlcode.controlli.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LancamentoMapper {

    private final UsuarioService usuarioService;

    public LancamentoDTO converterToDto(Lancamento lancamento) {
        return LancamentoDTO.builder()
                .id(lancamento.getId())
                .descricao(lancamento.getDescricao())
                .valor(lancamento.getValor())
                .mes(lancamento.getMes())
                .ano(lancamento.getAno())
                .status(lancamento.getStatus().name())
                .tipo(lancamento.getTipo().name())
                .usuario(lancamento.getUsuario().getId())
                .build();
    }

    public Lancamento converterToEntity(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setValor(dto.getValor());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado para o id informado"));

        lancamento.setUsuario(usuario);

        if(dto.getTipo() != null) {
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }

        if(dto.getStatus() != null) {
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;
    }
}


