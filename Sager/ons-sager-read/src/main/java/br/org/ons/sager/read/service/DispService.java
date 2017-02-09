package br.org.ons.sager.read.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.jms.JMSException;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.keyvalue.core.IterableConverter;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.OrderSpecifier;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.TipoObjeto;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.TipoComentario;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.EventMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.Comentario;
import br.org.ons.sager.read.domain.Disp;
import br.org.ons.sager.read.domain.DispComparator;
import br.org.ons.sager.read.domain.QDisp;
import br.org.ons.sager.read.repository.ComentarioRepository;
import br.org.ons.sager.read.repository.DispRepository;
import br.org.ons.sager.read.web.rest.dto.CheckDispInstalacoesResponse;
import br.org.ons.sager.read.web.rest.dto.DispDTO;
import br.org.ons.sager.read.web.rest.dto.DispInstalacoesDTO;
import br.org.ons.sager.read.web.rest.dto.DisponibilidadesDTO;
import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.exceptions.BadRequestException;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * Service Implementation for managing Disp.
 */
/**
 * @author jose.cardoso
 *
 */
@Service
public class DispService {
    private final Logger log = LoggerFactory.getLogger(DispService.class);
    
    private DispRepository dispRepository;
    
    private ComentarioRepository comentarioRepository;
    
    private CommandBus commandBus; 
    
    @Inject
    public DispService(DispRepository dispRepository, ComentarioRepository comentarioRepository,
			CommandBus commandBus) {
		super();
		this.dispRepository = dispRepository;
		this.comentarioRepository = comentarioRepository;
		this.commandBus = commandBus;
	}

	/**
	 * Recebe eventos de DisponibilidadesCalculadasEvent as disponibilidades calculadas
	 * no repositório de dados de leitura
	 * 
	 * @param eventMessage
	 *            Evento
	 * @throws JMSException
	 */
	@SuppressWarnings("unchecked")
	@JmsListener(destination = "platform/EventTopic/br.org.ons.sager.instalacao.event.DisponibilidadesCalculadasEvent", 
			containerFactory = "JmsTopicListenerContainerFactory", selector = "isReplay = FALSE")
	public void handleDisponibilidadesCalculadasEvent(EventMessage<DisponibilidadesCalculadasEvent> eventMessage) throws JMSException {
		log.debug("handleDisponibilidadesCalculadasEvent: {}", eventMessage);
		try{
			DisponibilidadesCalculadasEvent dispCalculada = eventMessage.getEvent();
			
			List<Disponibilidade> lDisponibilidades = dispCalculada.getDisponibilidades();
			if (lDisponibilidades != null && !lDisponibilidades.isEmpty()){
				List<Disp> lDisp = this.parseDisponibilidadesToDisp(dispCalculada.getDisponibilidades(), eventMessage.getMetadata().getAggregateId(), dispCalculada.getIdEquipamento());
				
				if (lDisp != null){
					for (Disp disp : lDisp) {
						this.saveDisp(disp);
					}
				}
			}
		} catch (Exception e){
			log.error(e.getMessage());
		}
		
		
		/*
		 * OBS - jcardoso - não carregar o comentário via Evento mais.
		 *  Pois a regra de VerificarSituaçãoInstlação será chamada sempre para cada período.
		 */
//		List<ComentarioSituacao> lComentarios = (List<ComentarioSituacao>)(List<?>)dispCalculada.getComentarios();
//		
//		if (lComentarios != null && lComentarios.size() > 0){
//			for (ComentarioSituacao comentarioSituacao : lComentarios) {
//				Comentario comentario = new Comentario(comentarioSituacao, eventMessage.getMetadata().getAggregateId());
//				
//				this.saveComentario(comentario);
//			}
//		}
		
	}
	
//	private Comentario saveComentario(Comentario comentario) {
//		log.debug("Request to save Comentario : {}", comentario);
//		
//		QComentario pred = new QComentario("comentario");
//		
//		Comentario comentarioFromGrid = null;
//		if (comentario.getDataInicio() == null){
//			comentarioFromGrid = comentarioRepository.findOne(
//				pred.idInstalacao.equalsIgnoreCase(comentario.getIdInstalacao())
//				.and(pred.statusObjeto.equalsIgnoreCase(comentario.getStatusObjeto()))
//				.and(pred.tipoObjeto.equalsIgnoreCase(comentario.getTipoObjeto()))
//				.and(pred.nomeObjeto.equalsIgnoreCase(comentario.getNomeObjeto()))				
//				.and(pred.dataInicio.isNull())
//				.and(pred.dataFim.eq(comentario.getDataFim()))
//			);
//			
//		} else if (comentario.getDataFim() == null ){
//			comentarioFromGrid = comentarioRepository.findOne(
//				pred.idInstalacao.equalsIgnoreCase(comentario.getIdInstalacao())
//				.and(pred.statusObjeto.equalsIgnoreCase(comentario.getStatusObjeto()))
//				.and(pred.tipoObjeto.equalsIgnoreCase(comentario.getTipoObjeto()))
//				.and(pred.nomeObjeto.equalsIgnoreCase(comentario.getNomeObjeto()))				
//				.and(pred.dataInicio.eq(comentario.getDataInicio()))
//				.and(pred.dataFim.isNotNull())
//			);
//		}else{
//			comentarioFromGrid = comentarioRepository.findOne(
//				pred.idInstalacao.equalsIgnoreCase(comentario.getIdInstalacao())
//				.and(pred.statusObjeto.equalsIgnoreCase(comentario.getStatusObjeto()))
//				.and(pred.tipoObjeto.equalsIgnoreCase(comentario.getTipoObjeto()))
//				.and(pred.nomeObjeto.equalsIgnoreCase(comentario.getNomeObjeto()))				
//				.and(pred.dataInicio.eq(comentario.getDataInicio()))
//				.and(pred.dataFim.eq(comentario.getDataFim()))
//			);
//		}
//		
//		if (comentarioFromGrid != null){
//			comentario.setId(comentarioFromGrid.getId());
//		}		
//
//		Comentario result = comentarioRepository.save(comentario);
//		return result;
//		
//	}

	public Disp saveDisp(Disp disp){
//		log.debug("Request to save Disp : {}", disp);
		
		QDisp ref = new QDisp("disp");
		Disp dispFromGrid = dispRepository.findOne(
			ref.data.eq(disp.getData())
			.and(ref.instalacao.equalsIgnoreCase(disp.getInstalacao()))
			.and(ref.equipamento.equalsIgnoreCase(disp.getEquipamento()))
		);
		
		if (dispFromGrid != null){
			disp.setId(dispFromGrid.getId());
		}
		
		disp.setDispConsolidada(true);
		
		return dispRepository.save(disp);
	}
    
	
	public byte[] generateExcel(ZonedDateTime dtInicio, ZonedDateTime dtFim, List<DispInstalacoesDTO> instalacoes, String [] tipos) throws BadRequestException{

		// Finds the workbook instance for XLSX file 
		XSSFWorkbook myWorkBook = new XSSFWorkbook();

		
		for (DispInstalacoesDTO instalacao : instalacoes) {
			if (instalacao.isHasDisponibilidades()){
				DisponibilidadesDTO disps = this.calcularDisponibilidades2(instalacao, dtInicio, dtFim, tipos);
								
				// Return first sheet from the XLSX workbook 
				XSSFSheet mySheet = myWorkBook.createSheet(instalacao.getNome());
				int rownum = mySheet.getLastRowNum();
				
				int qtdeTipos = tipos.length;
				//Primeira linha do Excel mesclar Equipamebntos de acordo com a qtde de tipos selecionadas
				XSSFRow row = mySheet.createRow(rownum++);
				int cellnum = 1;
				for (EquipamentoDTO equip : instalacao.getEquipamentos()) {
					XSSFCell cell = row.createCell(cellnum++); 
					cell.setCellValue((String) equip.getCodigoOns());
					switch (qtdeTipos){
						case 2: 
							cellnum = cellnum + 1;
							mySheet.addMergedRegion(new CellRangeAddress(0,0,cellnum-2, cellnum-1));
							break;
						case 3: 
							cellnum = cellnum + 2;
							mySheet.addMergedRegion(new CellRangeAddress(0,0,cellnum-3, cellnum-1));
							break;
					}
				}
				
				//Segunda linha do Excel com os tipos de disponibilidades selecionados.
				row = mySheet.createRow(rownum++);
				cellnum = 0;
				
				Set<String> columns = disps.getColunas().keySet();
				for (String column : columns) { 
					XSSFCell cell = row.createCell(cellnum++); 
					cell.setCellValue((String) disps.getColunas().get(column));					
				}
				
				DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH");
				
				for (DispDTO disp : disps.getDisponibilidades()) {
					row = mySheet.createRow(rownum++);
					cellnum = 0;
					XSSFCell cell = row.createCell(cellnum++); 
					cell.setCellValue((String) format.format( disp.getData() ) );
					Set<String> valores = disp.getValores().keySet();
					for (String key : valores) { 
						cell = row.createCell(cellnum++); 
						cell.setCellValue((Double) Double.parseDouble(disp.getValores().get(key)) );
					}
				}
			}
		}
		
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			myWorkBook.write(bos);
		} catch (IOException e) {
			throw new BadRequestException("Erro ao gerar Excel", "RN_XXXX_XX");
		} finally {
		    try {
				bos.close();
			} catch (IOException e) {
				throw new BadRequestException("Erro ao gerar Excel", "RN_XXXX_XX");
			}
		}
		
//		try {
//			Path path = Paths.get("/tmp/Employee.xlsx");
//			byte[] result = Files.readAllBytes(path);
//			
//			
//			
//			return result;
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		return bos.toByteArray();
		
	}
    
   
    /**
     * @param dtInicio
     * @param dtFim
     * @param instid
     * @param tipos
     * @return
     * @throws BadRequestException
     */
    public CheckDispInstalacoesResponse checkDispInstalacoes(ZonedDateTime dtInicio, ZonedDateTime dtFim, List<UsinaDTO> instid, String [] tipos) throws BadRequestException {
        log.debug("Request para verificar se as Instalacoes estão aptas para o cálculo de Disponibilidade no período solicitado.");
        
        CheckDispInstalacoesResponse response = new CheckDispInstalacoesResponse();
        
        List<DispInstalacoesDTO> instalacoes = new ArrayList<>();
        
        boolean hasAnyDisponibilidade = false;
        
    	//OBS - jcardoso - trazer os dados usina a usina respeitando a qtde de Colunas <= 25. Caso ultrapasse fazer o excel para essa usina.
    	for (UsinaDTO instalacao : instid) {
    		DispInstalacoesDTO usina = new DispInstalacoesDTO();
    		usina.setNome(instalacao.getNome());        	
    		usina.setEquipamentos(instalacao.getEquipamentos());
    		usina.setId(instalacao.getId());
    		usina.setMinorVersion(instalacao.getMinorVersion());
    		usina.setHasDisponibilidades(false);
    		
    		/*
    		 * OBS - jcardoso - Alteração para Verificar Situação da Instlação antes de chamar o cálculo da Disponibilidade.
    		 * Feito aqui e no aggregate na carga das instalações/equipamentos. No aggregate temos os comentários no momento 
    		 * de cada evento de criação de criação de Instlação/Equipamento e aqui temos os comentários da instalação para exatamente o período informado na tela.
    		 */
    		
    		VerificarSituacaoInstalacaoResponse verificarSituacaoResponse = this.verificarSituacaoInstalacao(usina,dtInicio, dtFim);
    		if (verificarSituacaoResponse != null){
    			usina.setHasDisponibilidades(verificarSituacaoResponse.isProssegue());
    			if (verificarSituacaoResponse.isProssegue())
    				hasAnyDisponibilidade = true;
    			
    			List<ComentarioSituacao> comentarios = verificarSituacaoResponse.getComentarios();
    			
    			/*
    			 * Se os comentários retornarem nulos, podemos processeguir com o processamento e calcular as disponibilidades
    			 * caso contrário, devemos apenas mostrar os comentários na tela.
    			 */
    			boolean hasComentarioInstalacao = false;
    			for (ComentarioSituacao comentario : comentarios) {
    				/*
    				 * Se o comentário for da usina adicionar a lista de comentários
    				 * caso contrário, ele será carregado via aggregate.
    				 */
					if(comentario.getTipoObjeto() == TipoObjeto.USINA || comentario.getTipoObjeto() == TipoObjeto.INTERLIGACAO_INTERNACIONAL){
						hasComentarioInstalacao = true;
    					if (comentario.getTipo() == TipoComentario.COMENTARIO){
    						if (usina.getComentarios() == null)
    							usina.setComentarios(new HashSet<Comentario>());
    						usina.getComentarios().add(new Comentario(comentario, instalacao.getId()));
    						usina.setHasComentarios(true);
    					} else if (comentario.getTipo() == TipoComentario.AVISO){
    						if (usina.getComentariosAviso() == null)
    							usina.setComentariosAviso(new HashSet<Comentario>());
    						usina.getComentariosAviso().add(new Comentario(comentario, instalacao.getId()));
    						usina.setHasComentarios(true);
    					} else if (comentario.getTipo() == TipoComentario.ERRO){
    						if (usina.getComentariosErro() == null)
    							usina.setComentariosErro(new HashSet<Comentario>());
    						usina.getComentariosErro().add(new Comentario(comentario, instalacao.getId()));
    						usina.setHasComentarios(true);
    					}
					}
    			}
    			for (ComentarioSituacao comentario : comentarios) {
    				if (!hasComentarioInstalacao){
    					if(comentario.getTipoObjeto() != TipoObjeto.USINA && comentario.getTipoObjeto() != TipoObjeto.INTERLIGACAO_INTERNACIONAL){
    						hasComentarioInstalacao = true;
    						if (comentario.getTipo() == TipoComentario.COMENTARIO){
    							if (usina.getComentarios() == null)
    								usina.setComentarios(new HashSet<Comentario>());
    							usina.getComentarios().add(new Comentario(comentario, instalacao.getId()));
    							usina.setHasComentarios(true);
    						} else if (comentario.getTipo() == TipoComentario.AVISO){
    							if (usina.getComentariosAviso() == null)
    								usina.setComentariosAviso(new HashSet<Comentario>());
    							usina.getComentariosAviso().add(new Comentario(comentario, instalacao.getId()));
    							usina.setHasComentarios(true);
    						} else if (comentario.getTipo() == TipoComentario.ERRO){
    							if (usina.getComentariosErro() == null)
    								usina.setComentariosErro(new HashSet<Comentario>());
    							usina.getComentariosErro().add(new Comentario(comentario, instalacao.getId()));
    							usina.setHasComentarios(true);
    						}
    					}
    				}
    			}
    		}
    		instalacoes.add(usina);
    	}
    	
    	 Period periodo = Period.between(dtInicio.toLocalDate(), dtFim.toLocalDate());
         int mesesEntredatas = periodo.getMonths();
         
         if (mesesEntredatas > 0 && hasAnyDisponibilidade){
         	response.setHasExcelGeral(true);         	
         }
         
         response.setInstalacoes(instalacoes);
        
        return response;
    }    
    
    public DisponibilidadesDTO calcularDisponibilidades(DispInstalacoesDTO instalacao, ZonedDateTime dtInicio, ZonedDateTime dtFim, String[] tipos) {
    	DisponibilidadesDTO result = new DisponibilidadesDTO();
        
        result.setColunas(this.getColunas(instalacao.getEquipamentos(), tipos));
    	
        int qtdeColunas = result.getColunas().keySet().size();
		/*
		 * OBS - jcardoso - Regra de negócio:
		 * Caso a qtde de colunas a ser mostrada na tela seja maior que 25 (colunas disponibilidade - 1 a mais por causa da coluna da data)
		 * fazer o download do excel e não mostrar a tabela.
		 * Caso contrário, mostrar os resultados na tela.
		 */
		if (qtdeColunas > 26){
			result.setHasExcel(true);
			
			return result;
		}else{
			result = this.calcularDisponibilidades2(instalacao, dtInicio, dtFim, tipos);
		}
		
		return result;
    }

	private DisponibilidadesDTO calcularDisponibilidades2(DispInstalacoesDTO instalacao, ZonedDateTime dtInicio, ZonedDateTime dtFim, String[] tipos) {
		List<Disp> dispGridResult = new ArrayList<>();
		
		QDisp ref= new QDisp("disp");         
        
        OrderSpecifier<Date> sortOrder1 = QDisp.disp.data.asc();   
        
        DisponibilidadesDTO result = new DisponibilidadesDTO();
        
        result.setColunas(this.getColunas(instalacao.getEquipamentos(), tipos));
		
			
		//Verificando a quantidade de disponibilidades persistidas na base de dados por Usina >> Equipamento
		for (EquipamentoDTO equip : instalacao.getEquipamentos()) {
			
			//Busar os períodos (mes a mes) entre as datas
			List<Periodo> listaPeriodos = this.getPeriodos(dtInicio, dtFim);    		
			
			for (Periodo periodo : listaPeriodos) {  
				/*
				 * OBS - jcardoso
				 * Computar o período de horas com o próximo dia além do período às 00:00AM
				 * quando temos virada do horário de verão não temos 24 horas no dia
				 * no início do horário de verão temos 23 horas
				 * no término temos 26 horas.
				 */
				
				LocalDateTime toDateTime = LocalDateTime.ofInstant(periodo.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
				toDateTime = toDateTime.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
				Date nextDay = Date.from(toDateTime.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
				
				//Para cada período verificar a quantidade de horas de disponibilidades prevista e persistidas.
				long qtdeHorasPrevistas = ChronoUnit.HOURS.between(
						Instant.ofEpochMilli(periodo.getDataInicio().getTime()),
						Instant.ofEpochMilli(nextDay.getTime()));
				
				long qtdeHorasPersistidas = dispRepository.count(
						ref.data.between(Date.from(periodo.getDataInicio().toInstant()), Date.from(periodo.getDataFim().toInstant()))
						.and(ref.instalacao.equalsIgnoreCase(instalacao.getId()))
						.and(ref.equipamento.equalsIgnoreCase(equip.getId()))
						);
				
				log.info("***************************************");
				log.info("qtdeHorasPrevistas: " + qtdeHorasPrevistas);
				log.info("qtdeHorasPersistidas: " + qtdeHorasPersistidas);
				log.info("***************************************");
				if (qtdeHorasPrevistas == qtdeHorasPersistidas){
					//Se existir exatamente a qtde de horas persisitdas de disponibilidades calculadas buscá-las e adicioná-las ao resultado da tela.
					Iterable<Disp> dispRows = dispRepository.findAll(
							ref.data.between(Date.from(periodo.getDataInicio().toInstant()), Date.from(periodo.getDataFim().toInstant()))
							.and(ref.instalacao.equalsIgnoreCase(instalacao.getId()))
							.and(ref.equipamento.equalsIgnoreCase(equip.getId())),
							sortOrder1
							);
					dispGridResult.addAll( IterableConverter.toList(dispRows) );
				}else{
					log.info("*****************************************");
					log.info("HORAS NOK");
					log.info("usina.getId(): "+ instalacao.getId());
					log.info("usina.getMinorVersion(): "+ instalacao.getMinorVersion());
					log.info("equip.getId(): "+ equip.getId());
					log.info("equip.getNome(): "+ equip.getNome());
					log.info("periodo.getDataInicio(): "+ periodo.getDataInicio());
					log.info("periodo.getDataFim(): "+ periodo.getDataFim());
					log.info("*****************************************");
					//Chamar o cálculo de disponibilidade no ODM.
					List<Disp> lDisp = this.enviarComandoCalculoDisponibilidade(periodo, instalacao, equip.getId());
					if (lDisp != null){
						dispGridResult.addAll(lDisp);
					}
					else{
						//TODO - jcardoso - Fazer tratamento de erro quando o resultado do ODM é nulo
						throw new BadRequestException("Erro ao chamar Regras de Negócio", "RN_XXXX_XX");
					}
				}
			}
		}
		
		
		result.setDisponibilidades(this.getDispDTO(dispGridResult, instalacao.getEquipamentos(), tipos));
		
		
		return result;
	}

	@SuppressWarnings("unchecked")
	private VerificarSituacaoInstalacaoResponse verificarSituacaoInstalacao(DispInstalacoesDTO usina, ZonedDateTime dtInicio, ZonedDateTime dtFim) {
		
		VerificarSituacaoInstalacaoCommand command = new VerificarSituacaoInstalacaoCommand();
		
		command.setDataInicio(Date.from(dtInicio.toInstant()));
    	command.setDataFim(Date.from(dtFim.toInstant()) );
    	
    	CommandMessage<VerificarSituacaoInstalacaoCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
        commandMessage.getMetadata().setAggregateId(usina.getId());
		commandMessage.getMetadata().setMajorVersion(null); // Default timeline
		commandMessage.getMetadata().setMinorVersion(usina.getMinorVersion()); // First Version
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
        commandMessage.getMetadata().setIsReplay(false);
        
        //TODO - jcardoso - Tratar exceções (Timeout, por exemplo)
        try{
        	ResultMessage<VerificarSituacaoInstalacaoResponse> response = (ResultMessage<VerificarSituacaoInstalacaoResponse>)(ResultMessage<?>)commandBus.sendAndWait(commandMessage);
        	log.debug("VerificarSituacaoInstalacaoCommand.ResultMessage : {}", response);
        	
        	 List<VerificarSituacaoInstalacaoResponse> results = response.getResults();
             if ( !results.isEmpty() ){
            	 return results.get(0);
             }        	
        } catch (RuntimeException e){
        	e.printStackTrace();
        }
        
        return null;
	}

	@SuppressWarnings("unchecked")
	private List<Disp> enviarComandoCalculoDisponibilidade(Periodo periodo, DispInstalacoesDTO usina, String equip) {
    	List<Disp> lDisp = new ArrayList<>();
    	
    	CalcularDisponibilidadesCommand command = new CalcularDisponibilidadesCommand();
    	command.setDataInicio(Date.from(periodo.getDataInicio().toInstant()));
    	command.setDataFim(Date.from(periodo.getDataFim().toInstant()) );
    	command.setIdUnidadeGeradora(equip);
    	
    	
    	List<TipoDisponibilidade> tiposDisp = new ArrayList<>();
    	
    	tiposDisp.add(TipoDisponibilidade.COMERCIAL);
    	tiposDisp.add(TipoDisponibilidade.OPERACIONAL);
    	tiposDisp.add(TipoDisponibilidade.ELETROMECANICA);

    	command.setTiposDisponibilidade(tiposDisp);
    	
    	CommandMessage<CalcularDisponibilidadesCommand> commandMessage = new CommandMessage<>();
        commandMessage.setCommand(command);
        commandMessage.getMetadata().setId(IdGenerator.newId());
        commandMessage.getMetadata().setCorrelationId(null); // Root command
        commandMessage.getMetadata().setAggregateId(usina.getId());
		commandMessage.getMetadata().setMajorVersion(null); // Default timeline
		commandMessage.getMetadata().setMinorVersion(usina.getMinorVersion()); // First Version
        commandMessage.getMetadata().setTimelineDate(ZonedDateTime.now());
        commandMessage.getMetadata().setIsReplay(false);
        
        //TODO - jcardoso - Tratar exceções (Timeout, por exemplo)
        try{
        	ResultMessage<CalcularDisponibilidadeResponse> response = (ResultMessage<CalcularDisponibilidadeResponse>)(ResultMessage<?>)commandBus.sendAndWait(commandMessage);
        	log.debug("CalcularDisponibilidadesCommand.ResultMessage : {}", response);
        	lDisp = this.parseResponseFromCommand(response, usina.getId(), equip);
        	
        	return lDisp;
        } catch (RuntimeException e){
        	e.printStackTrace();
        }
        
        return null;
	}

	private List<Disp> parseResponseFromCommand(ResultMessage<CalcularDisponibilidadeResponse> result, String usinaID, String equip) {
		
		List<Disp> lDisp = new ArrayList<>();		
		
        /*
         * OBS - jcardoso - O Result da mensagem está retornando um lista que faz referência as versões da Timeline (mais de uma timeline corrente)
         * Como a disponibilidade não trabalha em cima das n versões de timeline pegar apenas o 1º resultado.
         * TODO - jcardoso - verificar como amarrar a lista de Result ao MajorVersion no Aggregate.
         */
        
        List<CalcularDisponibilidadeResponse> results = result.getResults();
        if ( !results.isEmpty() ){
        	CalcularDisponibilidadeResponse response = results.get(0);
        	List<Disponibilidade> lDisponibilidades = response.getDisponibilidades();
        	
        	lDisp = this.parseDisponibilidadesToDisp(lDisponibilidades, usinaID, equip);
        }
        
        
		return lDisp;
	}
	
	 

	private List<Disp> parseDisponibilidadesToDisp(List<Disponibilidade> lDisponibilidades, String usinaID, String equip) {
		List<Disp> lDisp = new ArrayList<>();
		boolean isFirstRow = true;
        
        for (Disponibilidade dispRow : lDisponibilidades) {        	 
        	
        	ZonedDateTime dataDisp = ZonedDateTime.ofInstant(dispRow.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
       	 
        	//Primeira linha?
        	if (isFirstRow){
        		isFirstRow = false;            		
        		Disp disp = new Disp();         		
        		disp.setInstalacao(usinaID);
        		disp.setEquipamento(equip);
        		disp.setData(Date.from(dataDisp.toInstant()));
        		disp.setId(dispRow.getId());
        		if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.COMERCIAL ){
        			disp.setNum_dc(dispRow.getValor());
        		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.ELETROMECANICA ){
        			disp.setNum_de(dispRow.getValor());
        		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.OPERACIONAL ){
        			disp.setNum_do(dispRow.getValor());
        		}
        		//adicionar disp anterior a disps 	
        		lDisp.add(disp);
        	} 
        	else {
        		
        		Disp disp = this.hasDispData(dataDisp, lDisp) ;
        		//Nova data
        		if ( disp == null ){
        			disp = new Disp();         		
            		disp.setInstalacao(usinaID);
            		disp.setEquipamento(equip);
            		disp.setData(Date.from(dataDisp.toInstant()));
            		disp.setId(dispRow.getId());
            		if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.COMERCIAL ){
            			disp.setNum_dc(dispRow.getValor());
            		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.ELETROMECANICA ){
            			disp.setNum_de(dispRow.getValor());
            		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.OPERACIONAL ){
            			disp.setNum_do(dispRow.getValor());
            		}
            		//adicionar disp anterior a disps 	
            		lDisp.add(disp);             		
            		
            	} //Novo valores
            	else{
            		if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.COMERCIAL ){
            			disp.setNum_dc(dispRow.getValor());
            		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.ELETROMECANICA ){
            			disp.setNum_de(dispRow.getValor());
            		} else if ( dispRow.getTipoDisponibilidade() == TipoDisponibilidade.OPERACIONAL ){
            			disp.setNum_do(dispRow.getValor());
            		}            		
            	}         		
        	}         			
		}
        return lDisp;
	}


	private Disp hasDispData(ZonedDateTime dataDisp, List<Disp> lDisp) {
		for (Disp disp : lDisp) {			
			if (ZonedDateTime.ofInstant(disp.getData().toInstant(), ZoneId.of("America/Sao_Paulo")).isEqual(dataDisp) )
				return disp;
			}
		return null;
	}

	
	 /**
     *  Retornar os períodos entre a data inicial e final quebrando mensalmente.
     *  
     *  @param dtInicio data inicial do período
     *  @param dtFim data final do período
     *  @return lista de Períodos entre a data inicial e a data final.
     */
	private List<Periodo> getPeriodos(ZonedDateTime dtInicio, ZonedDateTime dtFim) {
		List<Periodo> periodos = new ArrayList<>();	
		
		Date dataInicioAux = Date.from(dtInicio.toInstant());
		Date dataFimAux = Date.from(dtFim.toInstant());
		
		long qtdeMeses = ChronoUnit.MONTHS.between(dtInicio, dtFim);
		
//		if ( qtdeMeses > 0 ){
		for (int i=0; i < qtdeMeses; i++){
			//No primeiro mes, setar o período até o final desse mês
			
			ZonedDateTime dateAux = ZonedDateTime.ofInstant(Instant.ofEpochMilli(dataInicioAux.getTime()), ZoneId.of("America/Sao_Paulo")).plusMonths(1);
			dateAux = dateAux.withDayOfMonth(1);
			dateAux = dateAux.minusDays(1);
			dateAux = dateAux.withHour(23).withMinute(59).withSecond(59);
			
			Date dataProximoMes = Date.from(dateAux.toInstant());
			periodos.add( new Periodo(dataInicioAux, dataProximoMes) );
			dataInicioAux = Date
					.from(ZonedDateTime.ofInstant(Instant.ofEpochMilli(dataProximoMes.getTime()), ZoneId.of("America/Sao_Paulo"))
							.plusSeconds(1).toInstant());
		}

		
		periodos.add(new Periodo(dataInicioAux, dataFimAux));
		
		return periodos;
	}

	private List<DispDTO> getDispDTO(List<Disp> dispRows, List<EquipamentoDTO> equipamentos, String[] tipos ) {
    	if (dispRows == null || equipamentos == null || dispRows.isEmpty() || equipamentos.isEmpty())
    		return null;
    	
    	dispRows.sort(new DispComparator());
    	    	    	
    	List<DispDTO> result = new ArrayList<>();
    	
        boolean isFirstRow = true;
         
         for (Disp dispRow : dispRows) {        	 
         	
         	ZonedDateTime dataDisp = ZonedDateTime.ofInstant(dispRow.getData().toInstant(), ZoneId.of("America/Sao_Paulo"));
        	 
         	//Primeira linha?
         	if (isFirstRow){
         		isFirstRow = false;
         		DispDTO disp = new DispDTO(dispRow.getData(), equipamentos, dispRow.isDispConsolidada(), tipos);          		

         		disp.setValor(dispRow);  
         		
         		//adicionar disp anterior a disps 	
         		result.add(disp);
         	} 
         	else {
         		
         		DispDTO disp = this.hasUsinaData(dataDisp, result) ;
         		//Nova data
         		if ( disp == null ){
         			disp = new DispDTO(dispRow.getData(), equipamentos, dispRow.isDispConsolidada(), tipos);         		
             		disp.setValor(dispRow);
             		
             		//adicionar disp anterior a disps 	
             		result.add(disp); 
             	} //Novo valores
             	else{
             		disp.setValor(dispRow);             		
             	}         		
         	}         			
 		}
         
         return result;
	}

	private DispDTO hasUsinaData(ZonedDateTime dataDisp, List<DispDTO> result) {
		for (DispDTO dispDTO : result) {
			if (dispDTO.getData().isEqual(dataDisp) )
				return dispDTO;
		}
		return null;
	}

	private Map<String, String> getColunas(List<EquipamentoDTO> equipamentos, String[] tipos) {
    	if (equipamentos == null)
    		return null;
    	
		Map<String, String> colunas = new LinkedHashMap<>();
		colunas.put("Data", "Data");
		for (EquipamentoDTO equip : equipamentos) {
			
			for (String tipo : tipos) {
    			switch (tipo) {
					case "O":
						colunas.put(equip.getCodigoOns() + "_O", "O");					
						break;
					case "E":
						colunas.put(equip.getCodigoOns() + "_E", "E");
						break;
					case "C":
						colunas.put(equip.getCodigoOns() + "_C", "C");
						break;
					default:
						colunas.put(equip.getCodigoOns() + "_O", "O");	
						colunas.put(equip.getCodigoOns() + "_C", "C");
						colunas.put(equip.getCodigoOns() + "_E", "E");
						break;
				}
			}
		}
		return colunas;
	}
	
	 public List<Comentario> findAllComentarios(){
	    	log.debug("Request to get all Comentarios 2");
	    	
	    	List<Comentario> comentariosResult = new ArrayList<>();
	    	Iterable<Comentario> comentarios = comentarioRepository.findAll();
	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
	    	
	    	return comentariosResult;
	 }
	 
	 private String getOrdenarNomeEquipamento(String nomeEquipamento){
			
			int index = nomeEquipamento.indexOf("UG");
			
			//Interligacoes Internacionais.
			if( index < 1)
				return nomeEquipamento;
			
			//Acrescenta a posicao das 2 letras(UG) ao index
			index = index +2;
			int numeroEquip = Integer.parseInt(nomeEquipamento.substring(index));
			//Formata para minimo de 2 casas.
			String numEquipFormatado = String.format("%02d", numeroEquip);
			String nomeEquipamentoSemNumero = nomeEquipamento.substring(0, index);
			
			
			return nomeEquipamentoSemNumero+numEquipFormatado;
		}
	
//	 public Set<Comentario> findAllComentarios(Date dtInicioPesquisa, Date dtFimPesquisa, String idInstalacao, TipoComentario tipo){
//	    	log.debug("Request to get all Comentarios");
//	    	
//	    	Set<Comentario> comentariosResult = new HashSet<Comentario>();
//	    	
//	    	comentariosResult.addAll(this.findComentariosForaOperacao(dtInicioPesquisa, dtFimPesquisa, idInstalacao, tipo.toString()));
//	    	List<String> statusObjeto = new ArrayList<String>();
//	    	statusObjeto.add("DESATIVADO");
//	    	statusObjeto.add("SUSPENSO");
//	    	comentariosResult.addAll(this.findComentariosByStatusObjeto(dtInicioPesquisa, dtFimPesquisa, statusObjeto, idInstalacao, tipo.toString()));
//	    	
//	    	
//	    	return comentariosResult;
//    }
//	    
//	    private List<Comentario> findComentariosByStatusObjeto( Date dtInicioPesquisa, Date dtFimPesquisa, List<String> statusObjeto, String idInstalacao, String tipoStr) {
//	    	List<Comentario> comentariosResult = new ArrayList<Comentario>();
//	    	
//	    	QComentario pred = new QComentario("comentario");   	
//	    	
//	    	/*
//	    	 * OBS - jcardoso - Situação 1) DataFinalPesquisa após a data inical e antes da data final do período SUSPENSAO
//	    	 * e a data final do período de SUSPENSAO não nula
//	    	 */
//	    	Iterable<Comentario> comentarios = comentarioRepository.findAll(
//	    			pred.idInstalacao.equalsIgnoreCase(idInstalacao)
//	    			.and(pred.tipo.equalsIgnoreCase(tipoStr))
//	    			.and(pred.dataFim.isNotNull())
//	    			.and(pred.dataInicio.before(dtFimPesquisa))
//	    			.and(pred.dataFim.after(dtFimPesquisa))    			
//	    			.and(pred.statusObjeto.in(statusObjeto))
//			);    	
//	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
//	    	
//	    	/*
//	    	 * OBS - jcardoso - Situação 2) DataFinalPesquisa após a data inical do período SUSPENSAO
//	    	 * e a data final do período de SUSPENSAO nula
//	    	 */
//	    	comentarios = comentarioRepository.findAll(
//	    			pred.idInstalacao.equalsIgnoreCase(idInstalacao)
//	    			.and(pred.tipo.equalsIgnoreCase(tipoStr))
//	    			.and(pred.dataFim.isNull())
//	    			.and(pred.dataInicio.before(dtFimPesquisa))
//	    			.and(pred.statusObjeto.in(statusObjeto))
//			);    	
//	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
//	    	
//	    	/*
//	    	 * OBS - jcardoso - Situação 3) DataInicialPesquisa entre período SUSPENSAO
//	    	 * e a DataFinalPesquisa após a data final do período de SUSPENSAO
//	    	 */
//	    	comentarios = comentarioRepository.findAll(
//	    			pred.idInstalacao.equalsIgnoreCase(idInstalacao)
//	    			.and(pred.tipo.equalsIgnoreCase(tipoStr))
//	    			.and(pred.dataFim.isNotNull())
//	    			.and(pred.dataInicio.before(dtInicioPesquisa))
//	    			.and(pred.dataFim.after(dtInicioPesquisa))
//	    			.and(pred.dataFim.before(dtFimPesquisa))
//	    			.and(pred.statusObjeto.in(statusObjeto))
//			);    	
//	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
//	    	
//	    	/*
//	    	 * OBS - jcardoso - Situação 4) DataInicialPesquisa antes do período SUSPENSAO
//	    	 * e a DataFinalPesquisa após o período de SUSPENSAO
//	    	 */
//	    	comentarios = comentarioRepository.findAll(
//	    			pred.idInstalacao.equalsIgnoreCase(idInstalacao)
//	    			.and(pred.tipo.equalsIgnoreCase(tipoStr))
//	    			.and(pred.dataFim.isNotNull())
//	    			.and(pred.dataInicio.after(dtInicioPesquisa))
//	    			.and(pred.dataFim.before(dtFimPesquisa))
//	    			.and(pred.statusObjeto.in(statusObjeto))
//			);    	
//	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
//	    	
//	    	
//	    	return comentariosResult;
//		}
//
//		private List<Comentario> findComentariosForaOperacao( Date dtInicioPesquisa, Date dtFimPesquisa, String idInstalacao, String tipoStr){
//	    	List<Comentario> comentariosResult = new ArrayList<Comentario>();
//	    	
//	    	QComentario pred = new QComentario("comentario");   	
//	    	
//	    	//OBS - jcardoso - DataInicialPesquisa antes que a data de fim do período FORA_OPERACAO_COMERCIAL
//	    	Iterable<Comentario> comentarios = comentarioRepository.findAll(
//	    			pred.idInstalacao.equalsIgnoreCase(idInstalacao)
//	    			.and(pred.tipo.equalsIgnoreCase(tipoStr))
//	    			.and(pred.dataFim.after(dtInicioPesquisa))    			
//	    			.and(pred.statusObjeto.equalsIgnoreCase("FORA_OPERACAO_COMERCIAL"))
//			);
//	    	
//	    	comentariosResult.addAll( IterableConverter.toList(comentarios) );
//	    	
//	    	return comentariosResult;
//	    }
		
//		public static void main(String[] args) {
//			
//			Periodo dia16 = new Periodo();
//			dia16.setDataInicio(new Date(new Long("1360980000000"))); //16
//			dia16.setDataFim(new Date(new Long("1361066399999"))); //16
//			LocalDateTime dia16Inicio = LocalDateTime.ofInstant(dia16.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime dia16Fim = LocalDateTime.ofInstant(dia16.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			Periodo dia17 = new Periodo();
//			dia17.setDataInicio(new Date(new Long("1361070000000"))); //17
//			dia17.setDataFim(new Date(new Long("1361156399999"))); //17
//			LocalDateTime dia17Inicio = LocalDateTime.ofInstant(dia17.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime dia17Fim = LocalDateTime.ofInstant(dia17.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			Periodo dia18 = new Periodo();
//			dia18.setDataInicio(new Date(new Long("1361156400000"))); //18
//			dia18.setDataFim(new Date(new Long("1361242799999"))); //18
//			LocalDateTime dia18Inicio = LocalDateTime.ofInstant(dia18.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime dia18Fim = LocalDateTime.ofInstant(dia18.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			Periodo dia19 = new Periodo();
//			dia19.setDataInicio(new Date(new Long("1361242800000"))); //19
//			dia19.setDataFim(new Date(new Long("1361329199999"))); //19
//			LocalDateTime dia19Inicio = LocalDateTime.ofInstant(dia19.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime dia19Fim = LocalDateTime.ofInstant(dia19.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			
//			long qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia16.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia16.getDataFim().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia16: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia16.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia17.getDataInicio().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia16 > 17 00:00: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia17.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia17.getDataFim().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia17: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia17.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia18.getDataInicio().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia17 > 18 00:00: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia18.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia18.getDataFim().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia18: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia18.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia19.getDataInicio().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia18 > 19 00:00: " + qtdeHorasPrevistas);
//			
//			qtdeHorasPrevistas = ChronoUnit.HOURS.between(
//					Instant.ofEpochMilli(dia19.getDataInicio().getTime()),
//					Instant.ofEpochMilli(dia19.getDataFim().getTime())) + 1;
//			
//			System.out.println("qtdeHorasPrevistas - dia19: " + qtdeHorasPrevistas);
//			
//			
//			
//			LocalDateTime fromDateTimedia17 = LocalDateTime.ofInstant(dia17.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime toDateTimedia17 = LocalDateTime.ofInstant(dia17.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			LocalDateTime tempDateTimedia17 = LocalDateTime.from( fromDateTimedia17 );
//			
//			long hoursdia17 = tempDateTimedia17.until( toDateTimedia17, ChronoUnit.HOURS);
//			
//			System.out.println("hoursdia17: " + hoursdia17);
//			
//			tempDateTimedia16 = LocalDateTime.from( fromDateTimedia16 );
//			long hoursdia16dia17 = tempDateTimedia16.until( toDateTimedia17, ChronoUnit.HOURS);
//			
//			System.out.println("hoursdia16dia17: " + hoursdia16dia17);
//			
//			LocalDateTime fromDateTimedia18 = LocalDateTime.ofInstant(dia18.getDataInicio().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			LocalDateTime toDateTimedia18 = LocalDateTime.ofInstant(dia18.getDataFim().toInstant(), ZoneId.of("America/Sao_Paulo"));
//			
//			tempDateTimedia17 = LocalDateTime.from( fromDateTimedia17 );
//			long hoursdia17dia18 = tempDateTimedia17.until( toDateTimedia18, ChronoUnit.HOURS);
//			
//			System.out.println("hoursdia17dia18: " + hoursdia17dia18);
//
//		}
}
