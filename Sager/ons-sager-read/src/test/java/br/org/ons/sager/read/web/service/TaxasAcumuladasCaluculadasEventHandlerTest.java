//package br.org.ons.sager.read.web.service;
//
//import java.math.BigDecimal;
//import java.time.ZonedDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.inject.Inject;
//import javax.jms.JMSException;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
//import br.org.ons.geracao.evento.taxa.TipoTaxa;
//import br.org.ons.platform.common.EventMessage;
//import br.org.ons.platform.common.EventMetadata;
//import br.org.ons.platform.common.util.IdGenerator;
//import br.org.ons.sager.instalacao.event.TaxasAcumuladasCalculadasV1Event;
//import br.org.ons.sager.read.OnsSagerReadApp;
//import br.org.ons.sager.read.domain.InstalacaoTaxasMensais;
//import br.org.ons.sager.read.repository.InstalacaoTaxasMensaisRepository;
//import br.org.ons.sager.read.repository.MCEquipamentoParametroRepository;
//import br.org.ons.sager.read.repository.ParametrosTaxasCalculadosRepository;
//import br.org.ons.sager.read.service.TaxasAcumuladasCalculadasEventHandler;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OnsSagerReadApp.class)
//public class TaxasAcumuladasCaluculadasEventHandlerTest {
//	
//	@Inject
//	private MCEquipamentoParametroRepository mcEquipParamRepository;
//	@Inject
//	private ParametrosTaxasCalculadosRepository parametrosRepository;
//	@Inject
//	private InstalacaoTaxasMensaisRepository instalacaoTaxasMensaisRepository;	
//	
//	
////	/**
////	 * RF01 - Armazenar Taxas Acumuladas V1 a partir do Event TaxasAcumuladasCalculadasEvent 
////	 * CT010006 - Armazenar 1 TEIP Acumulada para 1 Instalação em 1 data de apuração (1 mês) 
////	 * Resultado Esperado - A TEIP Acumulada persistida com o valor correto, no período correto, para a instalação correta, sem comentários 
////	 */
////	@Test
////	public void ct010006(){
////		EventMetadata metadata = new EventMetadata();
////		metadata.setAggregateId("RJUSCP");
////		metadata.setCorrelationId(IdGenerator.newId());
////		metadata.putProperty("scenarioName", "Cenário Liminar 9.1234");
////		metadata.setCreationDate(ZonedDateTime.now());
////		
////		TaxaAcumulada teipAcumulada = new TaxaAcumulada();
////		teipAcumulada.setCodigo("TEIP");
////		teipAcumulada.setComentarios(null);
////		teipAcumulada.setValor(new BigDecimal("0.12345678"));
////		teipAcumulada.setTipo(TipoTaxa.ACUMULADA);
////		
////		List<TaxaAcumulada> taxas = new ArrayList<TaxaAcumulada>();
////		taxas.add(teipAcumulada);
////		
////		TaxasAcumuladasCalculadasV1Event event = new TaxasAcumuladasCalculadasV1Event();
////		event.setDataInicioApuracao(new Date(113, 0, 1));
////		event.setTaxas(taxas);
////		
////		
////		EventMessage<TaxasAcumuladasCalculadasV1Event> eventMessage = new EventMessage<TaxasAcumuladasCalculadasV1Event>();
////		eventMessage.setEvent(event);
////		eventMessage.setMetadata(metadata);
////		
////		TaxasAcumuladasCalculadasEventHandler handler = new TaxasAcumuladasCalculadasEventHandler(mcEquipParamRepository, parametrosRepository, instalacaoTaxasMensaisRepository);
////		try {
////			
////			handler.handleTaxasAcumuladasCalculadasV1Event(eventMessage);
////			instalacaoTaxasMensaisRepository.findOne("RJUSCP");
////			assertThat(instalacaoTaxasMensaisRepository.count() == 1).isTrue();
//////			assertThat(content.getId() == "RJUSCP").isTrue();
////			
////		} catch (JMSException e) {
////			e.printStackTrace();
////		}
////		
////	}
////	
////	/**
////	 * RF01 - Armazenar Taxas Acumuladas V1 a partir do Event TaxasAcumuladasCalculadasEvent 
////	 * CT010001 - Armazenar Taxa com Event null
////	 * Resultado Esperado - Retornar um erro, impossibilitando a inserção no repositorio
////	 */
////	@Test
////	public void ct010001() throws Exception{
////		EventMetadata metadata = new EventMetadata();
////		metadata.setAggregateId("RJUSCP");
////		metadata.setCorrelationId(IdGenerator.newId());
////		metadata.putProperty("scenarioName", "Cenário Liminar 9.1234");
////		metadata.setCreationDate(ZonedDateTime.now());
////		
////		EventMessage<TaxasAcumuladasCalculadasV1Event> eventMessage = new EventMessage<TaxasAcumuladasCalculadasV1Event>();
////		eventMessage.setEvent(null);
////		eventMessage.setMetadata(metadata);
////		
////		TaxasAcumuladasCalculadasEventHandler handler = new TaxasAcumuladasCalculadasEventHandler(mcEquipParamRepository, parametrosRepository, instalacaoTaxasMensaisRepository);
////			handler.handleTaxasAcumuladasCalculadasV1Event(eventMessage);	
////		
////	}
//}
