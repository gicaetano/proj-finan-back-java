package br.com.xlcode.controlli.model.repositories;

import java.math.BigDecimal;

import br.com.xlcode.controlli.model.enums.StatusLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.xlcode.controlli.model.entity.Lancamento;
import br.com.xlcode.controlli.model.enums.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	
	@Query( value = 
			"select sum(l.valor) from Lancamento l join l.usuario u "
			+ "where u.id = :idUsuario and l.tipo =:tipo and l.status = :status group by u")
	BigDecimal obterSaldoPorTipoLancamentoeUsuarioEStatus(
			@Param("idUsuario") Long idUsuario, 
			@Param("tipo")TipoLancamento tipo,
			@Param("status") StatusLancamento status);
}
