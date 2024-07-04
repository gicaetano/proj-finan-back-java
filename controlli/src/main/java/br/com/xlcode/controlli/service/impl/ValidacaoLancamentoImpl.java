package br.com.xlcode.controlli.service.impl;

import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Lancamento;
import br.com.xlcode.controlli.service.ValidacaoLancamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidacaoLancamentoImpl implements ValidacaoLancamento {

    @Override
    public void validarLancamento(Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Informe uma Descrição válida.");
        }
        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocioException("Informe um Mês válido");
        }
        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Informe um Ano válido");
        }
        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
            throw new RegraNegocioException("Informe um Usuário");
        }
        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocioException("Informe um Valor válido");
        }
        if (lancamento.getTipo() == null) {
            throw new RegraNegocioException("Informe um Tipo de Lancamento");
        }
    }


}
