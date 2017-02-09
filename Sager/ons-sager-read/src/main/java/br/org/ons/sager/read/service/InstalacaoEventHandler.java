package br.org.ons.sager.read.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent;
import br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.Interligacoes;
import br.org.ons.sager.read.domain.QInterligacoes;
import br.org.ons.sager.read.domain.QUsinas;
import br.org.ons.sager.read.domain.Usinas;
import br.org.ons.sager.read.repository.InterligacoesRepository;
import br.org.ons.sager.read.repository.UsinasRepository;

/**
 * EventHandler que recebe eventos publicados no barramento
 * e atualiza o repositório de dados de leitura
 */
@Component
public class InstalacaoEventHandler {

	@Inject
	private UsinasRepository usinaRepository;
	
	@Inject
	private InterligacoesRepository interligacoesRepository;
	private final Logger log = LoggerFactory.getLogger(InstalacaoEventHandler.class);
	public InstalacaoEventHandler(UsinasRepository usinaRepository, InterligacoesRepository interligacoesRepository) {
		super();
		this.usinaRepository = usinaRepository;
		this.interligacoesRepository = interligacoesRepository;
	}
	
	
	
	

	/**
	 * Recebe eventos de InstalacaoCadastradaEvent e insere novos registros de
	 * CadastroUsina no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	private List<Equipamento> continuarVerificarUnidadeGeradora(List<Equipamento> equipamentos, Usina usina){
		for (UnidadeGeradora ug : usina.getUnidadesGeradoras()) {
			Equipamento equip = new Equipamento();
			equip.setId(ug.getId());
			equip.setNome(ug.getNome());
			equipamentos.add(equip);
		}
		return equipamentos;
	}
	
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.InstalacaoCadastradaEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleInstalacaoCadastradaEvent(EventMessage<InstalacaoCadastradaEvent> eventMessage) throws JMSException {
		
		try{
			log.debug("handleInstalacaoCadastradaEvent: {}", eventMessage);
			
			InstalacaoCadastradaEvent instalacaoCriada = eventMessage.getEvent();
			
			if ( instalacaoCriada.getInstalacao() instanceof Usina){
				Usina usina = (Usina)instalacaoCriada.getInstalacao();
				if (!usinaRepository.exists(usina.getId())) {
					
					Usinas usinaGrid = new Usinas();
					usinaGrid.setId(usina.getId());
					if (usina.getAgenteProprietario() != null)
						usinaGrid.setAgente(usina.getAgenteProprietario().getNomeCurto());
					usinaGrid.setCodaneel(usina.getCodigoAneel());
					usinaGrid.setCodvisaoapuracaogeracao(usina.getCodigoVisaoApuracaoGeracao());
					usinaGrid.setCosr(usina.getCosr());
					usinaGrid.setDtoutorgaimplantacao(usina.getDataOutorgaImplantacao());
					usinaGrid.setDtprorrogacaorenovacaoconcessao(usina.getDataProrrogacao());
					List<Equipamento> equipamentos = new ArrayList<>();
					if (usina.getUnidadesGeradoras() != null){
						equipamentos = continuarVerificarUnidadeGeradora(equipamentos, usina);
					}
					usinaGrid.setEquipamentos(equipamentos);
					
					usinaGrid.setMinorVersion(""+(eventMessage.getMetadata().getMinorVersion()));
					usinaGrid.setNome(usina.getNomeCurto());
					if (usina.getTipo() != null){
						usinaGrid.setTipo_id(usina.getTipo().getCodigo());
						usinaGrid.setTipo_nome(usina.getTipo().getDescricao());
					}
					usinaRepository.save(usinaGrid);
				} else {
					log.error("Usina já existente: {}", usina.getId());
				}
			} else if ( instalacaoCriada.getInstalacao() instanceof InterligacaoInternacional){
				InterligacaoInternacional interligacao = (InterligacaoInternacional)instalacaoCriada.getInstalacao();
				if (!interligacoesRepository.exists(interligacao.getId())) {
					
					Interligacoes interligacaoGrid = new Interligacoes();
					interligacaoGrid.setId(interligacao.getId());
					interligacaoGrid.setVersao(interligacao.getVersao());
					interligacaoGrid.setIddpp(interligacao.getIdDpp());
					interligacaoGrid.setCintdpp(interligacao.getCintDpp());
					interligacaoGrid.setDtimplantacao(interligacao.getDataOutorgaImplantacao());
					interligacaoGrid.setNome(interligacao.getNomeCurto());
					if (interligacao.getAgenteResponsavel() != null){
						interligacaoGrid.setIdagente(interligacao.getAgenteResponsavel().getId());
						interligacaoGrid.setNomeagente(interligacao.getAgenteResponsavel().getNomeCurto());
						interligacaoGrid.setSiglaagente(interligacao.getAgenteResponsavel().getSigla());
					}
					interligacaoGrid.setMinorVersion(""+(eventMessage.getMetadata().getMinorVersion()));
					interligacaoGrid.setCosr(interligacao.getCosr());
					
					List<Equipamento> equipamentos = new ArrayList<>();
					if (interligacao.getEquipamento() != null){					
							Equipamento equip = new Equipamento();
							equip.setId(interligacao.getEquipamento().getId());
							equip.setNome(interligacao.getEquipamento().getNome());
							equipamentos.add(equip);
					}
					interligacaoGrid.setEquipamentos(equipamentos);
					
					
					interligacoesRepository.save(interligacaoGrid);
				} else {
					log.error("Interligação Internacional já existente: {}", interligacao.getId());
				}
			}
		}catch(Exception e){
			log.error("handleInstalacaoCadastradaEvent: ",e);
		}
	}
	
	/**
	 * Recebe eventos de InstalacaoCadastradaEvent e insere novos registros de
	 * CadastroUsina no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	
	private void continuarVerificandoInterligacao(Interligacoes interligacoes, EquipamentoInterligacaoInternacional equipInterligacaoInter){
		boolean hasEquip = false;
		for (Equipamento equipInter : interligacoes.getEquipamentos()) {
			if (equipInter.getId().equalsIgnoreCase(equipInterligacaoInter.getId())){
				hasEquip = true;
			}
		}
		if (!hasEquip){
			Equipamento equip = new Equipamento();
			equip.setId(equipInterligacaoInter.getId());
			equip.setNome(equipInterligacaoInter.getNome());
			equip.setQuantidadeHorasServico(equipInterligacaoInter.getQuantidadeHorasServico());
			equip.setDataImplantacao(equipInterligacaoInter.getDataImplantacao());
			interligacoes.getEquipamentos().add(equip);
			
			interligacoesRepository.save(interligacoes);
		}else{
			log.error("EquipamentoInterligacaoInternacional já existente: {}", equipInterligacaoInter.getId());
		}
	}
	
	private void continuarVerificacaoUsina(Usinas usinas, UnidadeGeradora ug ){
		boolean hasEquip = false;
		for (Equipamento equipUsina : usinas.getEquipamentos()) {
			if (equipUsina.getId().equalsIgnoreCase(ug.getId())){
				hasEquip = true;
			}
		}
		if (!hasEquip){
			Equipamento equip = new Equipamento();
			equip.setId(ug.getId());
			equip.setNome(ug.getNome());
			equip.setVersao(ug.getVersao());
			equip.setCodid_dpp(ug.getCodigoIdDpp());
			equip.setIddpp(ug.getIdDpp());
			equip.setCodigoCcee(ug.getCodigoCcee());
			equip.setCodigoOns(ug.getCodigoOns());
			equip.setIdInstalacao(ug.getIdInstalacao());
			equip.setTipoInstalacao(ug.getTipoInstalacao());
			equip.setIdVisaoPlanejamentoOpEnergetica(ug.getIdVisaoPlanejamentoOpEnergetica());
			equip.setTipoFonteEnergetica(ug.getTipoFonteEnergetica());
			equip.setDataDesativacao(ug.getDataDesativacao());
			equip.setDataEventoEOC(ug.getDataEventoEOC());
			equip.setDataImplantacao(ug.getDataImplantacao());
			equip.setDataRenovacaoProrrogacaoConcessao(ug.getDataRenovacaoProrrogacaoConcessao());
			equip.setQuantidadeHorasServico(ug.getQuantidadeHorasServico());
			
			usinas.getEquipamentos().add(equip);
			usinaRepository.save(usinas);
		}else{
			log.error("UG já existente: {}", ug.getId());
		}
	}
	
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.EquipamentoCadastradoEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleEquipamentoCadastradoEvent(EventMessage<EquipamentoCadastradoEvent> eventMessage) throws JMSException {
		try{
			log.debug("handleEquipamentoCadastradoEvent: {}", eventMessage);
			
			EquipamentoCadastradoEvent equipCriado = eventMessage.getEvent();
			
			if ( equipCriado.getEquipamento() instanceof UnidadeGeradora){
				UnidadeGeradora ug = (UnidadeGeradora)equipCriado.getEquipamento();
				
				QUsinas pred = new QUsinas("usinas");
				
				//Se existir a usina cadastrada na projeção
				Usinas usinas = usinaRepository.findOne(pred.id.equalsIgnoreCase(eventMessage.getMetadata().getAggregateId()));
				if ( usinas != null ){
					continuarVerificacaoUsina(usinas, ug);
				} else{
					log.error("Usina não cadastrada: {}", ug.getIdInstalacao());
				}
			} else if ( equipCriado.getEquipamento() instanceof EquipamentoInterligacaoInternacional){
				EquipamentoInterligacaoInternacional equipInterligacaoInter = (EquipamentoInterligacaoInternacional)equipCriado.getEquipamento();
				
				QInterligacoes pred = new QInterligacoes("interligacoes");
				
				//Se existir a interligacoes cadastrada na projeção
				Interligacoes interligacoes = interligacoesRepository.findOne(pred.id.equalsIgnoreCase(eventMessage.getMetadata().getAggregateId()));
				if ( interligacoes != null ){
					continuarVerificandoInterligacao(interligacoes, equipInterligacaoInter);
				} else{
					log.error("InterligacaoInternacional não cadastrada: {}", equipInterligacaoInter.getIdInstalacao());
				}
			}
		}catch(Exception e){
			log.error("handleEquipamentoCadastradoEvent: ",e);
		}
		
	}

	
}
