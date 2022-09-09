package br.com.xlcode.minhasfinancas.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.xlcode.minhasfinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
