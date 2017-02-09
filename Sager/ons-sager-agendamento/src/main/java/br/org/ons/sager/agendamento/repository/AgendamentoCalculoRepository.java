package br.org.ons.sager.agendamento.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import br.org.ons.sager.agendamento.domain.AgendamentoCalculo;

/**
 * Spring Data Keyvalue repository for the AgendamentoCalculo entity.
 */
public interface AgendamentoCalculoRepository extends ECJCompiled<AgendamentoCalculo,String>,QueryDslPredicateExecutor<AgendamentoCalculo>{
	
	 
	public AgendamentoCalculo findTopByAnoCriacaoOrderByProtocoloDesc(int anoCriacao );
	
	public AgendamentoCalculo findOneByJobId(String jobId);
	public AgendamentoCalculo findOneByJobIdAndSituacao(String jobId, String situacao);
	public AgendamentoCalculo findOneByProtocoloStr(String protocoloStr);

}
