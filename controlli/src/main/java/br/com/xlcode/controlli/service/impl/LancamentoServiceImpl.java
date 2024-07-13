package br.com.xlcode.controlli.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import br.com.xlcode.controlli.service.ValidacaoLancamento;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.xlcode.controlli.exception.RegraNegocioException;
import br.com.xlcode.controlli.model.entity.Lancamento;
import br.com.xlcode.controlli.model.enums.StatusLancamento;
import br.com.xlcode.controlli.model.enums.TipoLancamento;
import br.com.xlcode.controlli.model.repositories.LancamentoRepository;
import br.com.xlcode.controlli.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {
	
	private final LancamentoRepository repository;
	private final ValidacaoLancamento validacaoLancamento;

	public LancamentoServiceImpl(LancamentoRepository repository, ValidacaoLancamento validacaoLancamento) {
		this.repository = repository;
		this.validacaoLancamento = validacaoLancamento;
	}

	@Transactional
	public Lancamento salvar(Lancamento lancamento) {
		validacaoLancamento.validarLancamento(lancamento);
		//validar(lancamento);
		lancamento.setStatus(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		validacaoLancamento.validarLancamento(lancamento);
		return repository.save(lancamento);
	}

	@Transactional
	public void deletar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
		
	}

	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING);

		Example<Lancamento> example = Example.of(lancamentoFiltro, matcher);
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
		lancamento.setStatus(status);
		atualizar(lancamento);
	}

	@Override
	public Optional<Lancamento> obterPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public BigDecimal obterSaldoPorUsuario(Long id) {
		BigDecimal receitas = repository.obterSaldoPorTipoLancamentoeUsuarioEStatus(id, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO);
		BigDecimal despesas = repository.obterSaldoPorTipoLancamentoeUsuarioEStatus(id, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO);

		receitas = receitas != null ? receitas : BigDecimal.ZERO;
		despesas = despesas != null ? despesas : BigDecimal.ZERO;
		
		return receitas.subtract(despesas);
	}

}
