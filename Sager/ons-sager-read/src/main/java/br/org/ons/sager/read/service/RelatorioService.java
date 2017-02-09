package br.org.ons.sager.read.service;

import static com.querydsl.core.alias.Alias.alias;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bouncycastle.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.querydsl.collections.QueryEngine;

import br.org.ons.geracao.evento.ComentarioSituacao;
import br.org.ons.geracao.evento.ComentarioSituacao.StatusObjeto;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.ResultMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.command.VerificarSituacaoInstalacaoCommand;
import br.org.ons.sager.instalacao.event.EventosApuradosEvent;
import br.org.ons.sager.read.config.IIBusProperties;
import br.org.ons.sager.read.config.mq.CommandBus;
import br.org.ons.sager.read.domain.ArqDispVer;
import br.org.ons.sager.read.domain.ArqIndAcum;
import br.org.ons.sager.read.domain.ArqTipSinc;
import br.org.ons.sager.read.domain.Equipamento;
import br.org.ons.sager.read.domain.Evento;
import br.org.ons.sager.read.domain.InstalacaoTaxas;
import br.org.ons.sager.read.domain.RespIIBGerarRelatorio;
import br.org.ons.sager.read.domain.Taxa;
import br.org.ons.sager.read.domain.Valores;
import br.org.ons.sager.read.repository.CondicaoOperativaRepository;
import br.org.ons.sager.read.repository.EstadoOperativoRepository;
import br.org.ons.sager.read.web.rest.dto.DispInstalacoesDTO;
import br.org.ons.sager.dto.EquipamentoDTO;
import br.org.ons.sager.dto.UsinaDTO;
import br.org.ons.sager.read.web.rest.errors.ErrorDTO;
import br.org.ons.sager.regra.parameters.VerificarSituacaoInstalacaoResponse;

/**
 * Service Implementation for managing Relatorio.
 */
@Service
public class RelatorioService {
    private final Logger log = LoggerFactory.getLogger(RelatorioService.class);

    private EventoService eventoService;
    private EquipamentoEventHandler equipamentoService;
    private CalcularTaxasService taxasAcumuladasCalculadasService;
	private EstadoOperativoRepository estadoOperativoRepository;
	private CondicaoOperativaRepository condicaoOperativaRepository;
    private IIBusProperties iibProperties;
    private CommandBus commandBus; 
	private final PeriodoApuracao qApuracao = alias(PeriodoApuracao.class, "apuracao");
	private final Evento qEvento = alias(Evento.class, "evento");
	/**
	 * Atributo de estado dos períodos de apuração de taxas da instalação
	 */
	private List<PeriodoApuracao> apuracoes = new ArrayList<>();

	@Autowired
	private QueryEngine queryEngine;

	public RelatorioService(EventoService eventoService, EquipamentoEventHandler equipamentoService,
    		CalcularTaxasService taxasAcumuladasCalculadasService, EstadoOperativoRepository estadoOperativoRepository,
    		CondicaoOperativaRepository condicaoOperativaRepository, IIBusProperties iibProperties, CommandBus commandBus) {
		super();
		this.eventoService = eventoService;
		this.equipamentoService = equipamentoService;
		this.taxasAcumuladasCalculadasService = taxasAcumuladasCalculadasService;
		this.estadoOperativoRepository = estadoOperativoRepository;
		this.condicaoOperativaRepository = condicaoOperativaRepository;
		this.iibProperties = iibProperties;		
		this.commandBus = commandBus;
	}
    
    // Retorna a resposta para o serviço Gerar Relatório
    public byte[] gerarRelatorio(List<UsinaDTO> instalacoes,
    		String tipoRelatorio ,
    		String tipoArquivo ,
    		ZonedDateTime dtInicio,
    		ZonedDateTime dtFim,
    		String tipoResposta)
    {
        byte[] respByte = null;
        byte tpRetorno = getTpArquivo(tipoArquivo);
//      dtInicio = dtInicio.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
//    	dtFim = dtFim.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
		dtInicio = dtInicio.withDayOfMonth(1);
		dtFim = dtFim.with(java.time.temporal.TemporalAdjusters.lastDayOfMonth());
    	System.out.println("gerarRelatorio dtInicio: " + dtInicio);   
    	System.out.println("gerarRelatorio dtFim: " + dtFim);   

        for (UsinaDTO instalacao : instalacoes) {
        	DispInstalacoesDTO usina = new DispInstalacoesDTO();
    		usina.setNome(instalacao.getNome());        	
    		usina.setEquipamentos(instalacao.getEquipamentos());
    		// usina.setColunas(this.getColunas(usina.getEquipamentos()));
    		usina.setId(instalacao.getId());
    		usina.setMinorVersion(instalacao.getMinorVersion());
    		usina.setHasDisponibilidades(false);

    		VerificarSituacaoInstalacaoResponse response = this.verificarSituacaoInstalacao(usina, dtInicio, dtFim);
    		if (response != null){
    	    	System.out.println("VerificarSituacaoInstalacaoResponse: " + response.getComentarios().toString());   
    			List<ComentarioSituacao> comentarios = response.getComentarios();
    			
    			if (!comentarios.isEmpty())
    			{
    			
	    			/*
	    			 * Se os comentários retornarem nulos, podemos processeguir com o processamento e calcular as disponibilidades
	    			 * caso contrário, devemos apenas mostrar os comentários na tela.
	    			 */
	    			List<ErrorDTO> listaErros = new ArrayList<ErrorDTO>();
					for (ComentarioSituacao comentario : comentarios) 
					{
						if (comentario.getStatusObjeto() == StatusObjeto.FORA_OPERACAO_COMERCIAL && !response.isProssegue())
						{
							ErrorDTO erro = new ErrorDTO("RS_MENS_006", comentario.getDescricao());	
							listaErros.add(erro);
						}
						if (comentario.getStatusObjeto()==StatusObjeto.DESATIVADO && !response.isProssegue())
						{
							ErrorDTO erro = new ErrorDTO("RS_MENS_008", comentario.getDescricao());	
							listaErros.add(erro);
						}
						if (comentario.getStatusObjeto()==StatusObjeto.SUSPENSO && !response.isProssegue())
						{
							ErrorDTO erro = new ErrorDTO("RS_MENS_009", comentario.getDescricao());	
							listaErros.add(erro);
						}
					}   
					ObjectMapper mapper = new ObjectMapper();
					String JSONStr = "";
					try {
						JSONStr = mapper.writeValueAsString(listaErros);
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!listaErros.isEmpty())
					{
						respByte = Arrays.concatenate(new byte[]{TiposArquivo.ERROS.getValor()}, JSONStr.toString().getBytes());
						return respByte;
					}
    			}
    		}
    		else
    		{
    	    	System.out.println("VerificarSituacaoInstalacaoResponse: No Responde");    			
    		}
        	
        }
    	
		List<String> nomesArquivo = new ArrayList<String>();
		List<byte[]> dadosArquivos = new ArrayList<byte[]>();
		RestTemplate restTemplate = new RestTemplate();
		ZonedDateTime dtAux;
    	// List<Evento> eventos = eventoService.findAll(dtInicio, dtFim, instalacoes, null);
    	switch(tipoRelatorio)
    	{
		case "dispVer":
			List<ArqDispVer> linhasArqDispVer = this.geraDispVer(instalacoes, dtInicio, dtInicio.plusMonths(1));
	    	System.out.println("Linhas Dispver: " + linhasArqDispVer.size() + " " + linhasArqDispVer.toString());
    	    if (linhasArqDispVer.size()>0) 
    		{
		    	// System.out.println("URL Broker: " + iibProperties.getUrl() + "/ArqDispVer/" + tipoResposta + "/" + tipoArquivo);
	    		RespIIBGerarRelatorio resp = restTemplate.postForObject(iibProperties.getUrl() + "/ArqDispVer/" + tipoResposta + "/" + tipoArquivo, linhasArqDispVer, RespIIBGerarRelatorio.class);
		    	// System.out.println("RespIIBGerarRelatorio: " + resp.toString());
	    		nomesArquivo.add(tipoRelatorio + String.format("%02d", dtInicio.getMonth().getValue()) + dtInicio.getYear() + "." + (tipoArquivo.equals("ambos")?"zip":tipoArquivo));
	    		dadosArquivos.add(this.respBroker2ByteArray(resp, tipoArquivo));
		    }
    	    else respByte = Arrays.concatenate(new byte[]{TiposArquivo.VAZIO.getValor()}, ("[]").getBytes());

	    	dtAux = dtInicio.plusMonths(1);
			while (dtAux.isBefore(dtFim))
			{
				linhasArqDispVer = this.geraDispVer(instalacoes, dtAux, dtAux.plusMonths(1));
		    	System.out.println("Linhas Dispver: " + dtAux + " " + linhasArqDispVer.size() + " " + linhasArqDispVer.toString());
	    	    if (linhasArqDispVer.size()>0) 
	    		{
			    	System.out.println("URL Broker: " + iibProperties.getUrl() + "/ArqDispVer/" + tipoResposta + "/" + tipoArquivo);
		    		RespIIBGerarRelatorio resp = restTemplate.postForObject(iibProperties.getUrl() + "/ArqDispVer/" + tipoResposta + "/" + tipoArquivo, linhasArqDispVer, RespIIBGerarRelatorio.class);
			    	System.out.println("RespIIBGerarRelatorio: " + resp.toString());
		    		nomesArquivo.add("dispVer" + String.format("%02d", dtAux.getMonth().getValue()) + dtAux.getYear() + "." + (tipoArquivo.equals("ambos")?"zip":tipoArquivo));
		    		dadosArquivos.add(this.respBroker2ByteArray(resp, tipoArquivo));
			    }
	    	    dtAux = dtAux.plusMonths(1);
			}
			switch(dadosArquivos.size())
			{
			case 0:
				respByte = Arrays.concatenate(new byte[]{TiposArquivo.VAZIO.getValor()}, ("[]").getBytes());
				break;
			case 1:
	    		respByte = Arrays.concatenate(new byte[]{tpRetorno}, dadosArquivos.get(0));
				break;
			default:
				respByte = Arrays.concatenate(new byte[]{TiposArquivo.ZIP.getValor()}, geraZipTotal(nomesArquivo, dadosArquivos));
				break;
			}
			    	    	
	    	break;
		case "tipSinc":
			List<ArqTipSinc> linhasArqTipSinc = this.geraTipSinc(instalacoes, dtInicio, dtInicio.plusMonths(1));
	    	System.out.println("Linhas TipSinc: " + linhasArqTipSinc.size() + " " + linhasArqTipSinc.toString());
	    	if (linhasArqTipSinc.size()>0) 
    		{
		    	// System.out.println("URL Broker: " + iibProperties.getUrl() + "/ArqTipSinc/" + tipoResposta + "/" + tipoArquivo);
	    		RespIIBGerarRelatorio resp = restTemplate.postForObject(iibProperties.getUrl() + "/ArqTipSinc/" + tipoResposta + "/" + tipoArquivo, linhasArqTipSinc, RespIIBGerarRelatorio.class);
		    	System.out.println("RespIIBGerarRelatorio: " + resp.toString());
	    		respByte = Arrays.concatenate(new byte[]{tpRetorno}, this.respBroker2ByteArray(resp, tipoArquivo));
	    		nomesArquivo.add(tipoRelatorio + String.format("%02d", dtInicio.getMonth().getValue()) + dtInicio.getYear() + "." + (tipoArquivo.equals("ambos")?"zip":tipoArquivo));
	    		dadosArquivos.add(this.respBroker2ByteArray(resp, tipoArquivo));
		    }
    	    else respByte = Arrays.concatenate(new byte[]{TiposArquivo.VAZIO.getValor()}, ("[]").getBytes());
	    	
	    	dtAux = dtInicio.plusMonths(1);
			while (dtAux.isBefore(dtFim))
			{
				linhasArqTipSinc = this.geraTipSinc(instalacoes, dtAux, dtAux.plusMonths(1));
		    	System.out.println("Linhas tipSinc: " + dtAux + " " + linhasArqTipSinc.size() + " " + linhasArqTipSinc.toString());
	    	    if (linhasArqTipSinc.size()>0) 
	    		{
			    	// System.out.println("URL Broker: " + iibProperties.getUrl() + "/ArqDispVer/" + tipoResposta + "/" + tipoArquivo);
		    		RespIIBGerarRelatorio resp = restTemplate.postForObject(iibProperties.getUrl() + "/ArqTipSinc/" + tipoResposta + "/" + tipoArquivo, linhasArqTipSinc, RespIIBGerarRelatorio.class);
			    	System.out.println("RespIIBGerarRelatorio: " + resp.toString());
		    		nomesArquivo.add("tipSinc" + String.format("%02d", dtAux.getMonth().getValue()) + dtAux.getYear() + "." + (tipoArquivo.equals("ambos")?"zip":tipoArquivo));
		    		dadosArquivos.add(this.respBroker2ByteArray(resp, tipoArquivo));
			    }
	    	    dtAux = dtAux.plusMonths(1);
			}
			switch(dadosArquivos.size())
			{
			case 0:
				respByte = Arrays.concatenate(new byte[]{TiposArquivo.VAZIO.getValor()}, ("[]").getBytes());
				break;
			case 1:
	    		respByte = Arrays.concatenate(new byte[]{tpRetorno}, dadosArquivos.get(0));
				break;
			default:
				respByte = Arrays.concatenate(new byte[]{TiposArquivo.ZIP.getValor()}, geraZipTotal(nomesArquivo, dadosArquivos));
				break;
			}
	    	
	    	break;
		case "indAcum":
			List<ArqIndAcum> linhasArqIndAcum = this.geraIndAcum(instalacoes);
	    	System.out.println("Linhas IndAcum: " + linhasArqIndAcum.size() + " " + linhasArqIndAcum.toString());
	    	if (linhasArqIndAcum.size()>0) 
    		{
		    	// System.out.println("URL Broker: " + iibProperties.getUrl() + "/ArqIndAcum/" + tipoResposta + "/" + tipoArquivo);
	    		RespIIBGerarRelatorio resp = restTemplate.postForObject(iibProperties.getUrl() + "/ArqIndAcum/" + tipoResposta + "/" + tipoArquivo, linhasArqIndAcum, RespIIBGerarRelatorio.class);
		    	System.out.println("RespIIBGerarRelatorio: " + resp.toString());
	    		respByte = Arrays.concatenate(new byte[]{tpRetorno}, this.respBroker2ByteArray(resp, tipoArquivo));
		    }
    	    else respByte = Arrays.concatenate(new byte[]{TiposArquivo.VAZIO.getValor()}, ("[]").getBytes());
	    	break;			
    	}    	
    	return respByte;
    }
    
    private byte getTpArquivo(String tipoArquivo)
    {
    	byte resposta = TiposArquivo.VAZIO.getValor();
    	switch(tipoArquivo)
    	{
    	case "xml":
    		resposta = TiposArquivo.XML.getValor();		// XML
    		break;
    	case "dat":
    		resposta = TiposArquivo.DAT.getValor();		// Dat
    		break;
    	case "ambos":
    		resposta = TiposArquivo.ZIP.getValor();		// ZIP
    		break;    		
    	}
    	return resposta;    	
    }
    
	
	public enum TiposArquivo {
		XML((byte)0),DAT((byte)1),ZIP((byte)2),ERROS((byte)3),VAZIO((byte)4);
		
		private final byte valor;
		TiposArquivo(byte valorOpcao){
			valor = valorOpcao;
		}
		public byte getValor(){
			return valor;
		}
	}	    
    // Retorna uma lista de objetos de tipo IndAcum
    private List<ArqIndAcum> geraIndAcum(List<UsinaDTO> instalacoes)
    {
    	List<ArqIndAcum> linhasArqIndAcum = new ArrayList<ArqIndAcum>();
    	System.out.println("Instalacoes: " + instalacoes.size() + " " + instalacoes.toString());
		for (int i=0;i<instalacoes.size();i++)
		{
    		String[] ids = {instalacoes.get(i).getEquipamentos().get(0).getId()};
	    	List<Equipamento> listaUG = equipamentoService.findById(ids, null);
	    	System.out.println("EquipamentoCadastrado / UG: " + listaUG.size() + " " + listaUG.toString() + " " + ids);
    		String[] idsInst = {instalacoes.get(i).getId()};
	    	List<InstalacaoTaxas> listaITM = taxasAcumuladasCalculadasService.findById(idsInst, null);
	    	System.out.println("InstalacaoTaxas: " + listaITM.size() + " " + listaITM.toString() + " " + idsInst);
	    	
	    	for (int j=0;j<listaITM.size();j++)
	    	{
	    		ArqIndAcum regIndAcum = new ArqIndAcum();
	    		regIndAcum.setNomeUnidadeGeradora(instalacoes.get(i).getNome());
	    		regIndAcum.setCodDPP(listaUG.get(0).getCodid_dpp());
	    		boolean encontrouTaxa = false;
	    		List<Taxa> listaTaxas = listaITM.get(j).getTaxas();
	    		for (int k=0;k<listaTaxas.size();k++)
	    		{
	    			Taxa tx = listaTaxas.get(k);
		    		regIndAcum.setMesAno(String.format("%02d", tx.getMes()) + "/" + String.valueOf(tx.getAno()));

		    		List<Valores> listaValores = tx.getValores();
			    	System.out.println("Valores: " + listaValores.size() + " " + listaValores.toString());
		    		for (int l=0;l<listaValores.size();l++)
		    		{
		    			Valores valor = listaValores.get(l);
				    	System.out.println("Valor: " + valor.getNome().toUpperCase());
		    			switch(valor.getNome().toUpperCase())
		    			{
		    			case "TEIFA MENSAL MENSAL":
					    	System.out.println("Encontrou: " + valor.getNome().toUpperCase() + " " + valor.getValor());
					    	if (valor.getValor()!=null) System.out.println("Valor String: " + valor.getValor().toString());
					    	if (valor.getValor()!=null) regIndAcum.setTeifaMes(valor.getValor().toString());
		    				encontrouTaxa = true;
		    				break;
		    			case "TEIFA ACUMULADA":
					    	System.out.println("Encontrou: " + valor.getNome().toUpperCase() + " " + valor.getValor());
					    	if (valor.getValor()!=null) System.out.println("Valor String: " + valor.getValor().toString());
					    	if (valor.getValor()!=null) regIndAcum.setTeifaAcumulado(valor.getValor().toString());
		    				encontrouTaxa = true;
		    				break;
		    			case "TEIP MENSAL MENSAL":
					    	System.out.println("Encontrou: " + valor.getNome().toUpperCase() + " " + valor.getValor());
					    	if (valor.getValor()!=null) System.out.println("Valor String: " + valor.getValor().toString());
					    	if (valor.getValor()!=null) regIndAcum.setTeipMes(valor.getValor().toString());
		    				encontrouTaxa = true;
		    				break;
		    			case "TEIP ACUMULADA":
					    	System.out.println("Encontrou: " + valor.getNome().toUpperCase() + " " + valor.getValor());
					    	if (valor.getValor()!=null) System.out.println("Valor String: " + valor.getValor().toString());
					    	if (valor.getValor()!=null) regIndAcum.setTeipAcumulado(valor.getValor().toString());
		    				encontrouTaxa = true;
		    				break;
		    			}
				    	System.out.println("Encontrou?: " + encontrouTaxa);
		    		}
	    		}
	    		if (encontrouTaxa) linhasArqIndAcum.add(regIndAcum);    		
	    	}
			
		}
		return linhasArqIndAcum;
    	
    }
    
    // Retorna uma lista de objetos de tipo TipSinc
    private List<ArqTipSinc> geraTipSinc(List<UsinaDTO> instalacoes,
    		ZonedDateTime dtInicio,
    		ZonedDateTime dtFim)
    {
    	List<Evento> eventosComEspelho = new ArrayList<Evento>();
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    	this.insereEventosZeroHora(eventosComEspelho, instalacoes, Date.from(dtInicio.toInstant()));

    	List<Evento> eventos = eventoService.findEventosTipSinc(dtInicio, dtFim, instalacoes);
    	System.out.println("geraDispVer Eventos: " + eventos.size() + " " + eventos.toString());
    	eventosComEspelho.addAll(eventos);
    	List<ArqTipSinc> linhasArqTipSinc = new ArrayList<ArqTipSinc>();
    	for (int i=0;i<eventosComEspelho.size();i++)
    	{
    		String[] ids = {eventosComEspelho.get(i).getIdEquipamento()};
	    	List<Equipamento> listaUG = equipamentoService.findById(ids, null);
	    	// List<UnidadeGeradora> listaUG = TesteApagar();
	    	for (int j=0;j<listaUG.size();j++)
	    	{
	    		Evento evento = eventosComEspelho.get(i);
	    		Equipamento ug = listaUG.get(j);
	    		ArqTipSinc regTipSinc = new ArqTipSinc();
	    		regTipSinc.setDataHora(evento.getDataVerificada());
	    		regTipSinc.setNomeUnidadeGeradora(ug.getNome());
	    		regTipSinc.setIdUGE(ug.getIddpp());
	    		regTipSinc.setCodDPP(ug.getCodid_dpp());
	    		regTipSinc.setCodCCEE(ug.getCodigoCcee());
	    		regTipSinc.setFlgSincrono(evento.getEstadoOperativo().equals("LCS")?"1":"0");
	    		regTipSinc.setComentario("");
	    		
	    		linhasArqTipSinc.add(regTipSinc);    		
	    	}	    		
    	}
    	return linhasArqTipSinc;
    	
    }
    
    // Retorna uma lista de objetos de tipo DispVer
    private List<ArqDispVer> geraDispVer(List<UsinaDTO> instalacoes,
    		ZonedDateTime dtInicio,
    		ZonedDateTime dtFim)
    {
    	List<Evento> eventosComEspelho = new ArrayList<Evento>();
    	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    	this.insereEventosZeroHora(eventosComEspelho, instalacoes, Date.from(dtInicio.toInstant()));
    	System.out.println("geraDispVer eventosComEspelho: " + eventosComEspelho.size());

    	List<Evento> eventos = eventoService.findEventosDispVer(dtInicio, dtFim, instalacoes);
    	eventosComEspelho.addAll(eventos);
    	
    	System.out.println("geraDispVer Eventos: " + eventos.size() + " " + eventos.toString());
    	List<ArqDispVer> linhasArqDispVer = new ArrayList<ArqDispVer>();
    	Double disponibilidade = -1.0;
    	
    	System.out.println("geraDispVer eventosComEspelho: " + eventosComEspelho.size());
    	for (int i=0;i<eventosComEspelho.size();i++)
    	{
        	System.out.println("geraDispVer Evento: " + eventosComEspelho.get(i).getDataVerificada() + " " + disponibilidade + " " + eventosComEspelho.get(i).getValorPotenciaDisponivel().doubleValue());
    		if (disponibilidade != eventosComEspelho.get(i).getValorPotenciaDisponivel().doubleValue())
    		{
	    		disponibilidade = eventosComEspelho.get(i).getValorPotenciaDisponivel().doubleValue();
	    		String[] ids = {eventosComEspelho.get(i).getIdEquipamento()};
		    	List<Equipamento> listaUG = equipamentoService.findById(ids, null);
		    	// System.out.println("geraDispVer EquipamentoCadastrado: " + listaUG.size() + " " + listaUG.toString());
		    	for (int j=0;j<listaUG.size();j++)
		    	{
		    		Evento evento = eventosComEspelho.get(i);
		    		Equipamento ug = listaUG.get(j);
		    		ArqDispVer regDispVer = new ArqDispVer();
		    		regDispVer.setDataHora(evento.getDataVerificada());
		    		regDispVer.setNomeUnidadeGeradora(ug.getNome());
		    		regDispVer.setIdUGE(ug.getIddpp());
		    		regDispVer.setValorDisponibilidade(evento.getValorPotenciaDisponivel().toString());
		    		regDispVer.setCodEstadoOperativo(evento.getEstadoOperativo());
		    		if (!evento.getEstadoOperativo().equals("")) regDispVer.setDescEstadoOperativo(estadoOperativoRepository.findOne(evento.getEstadoOperativo()).getDescricao());
		    		if (!evento.getCondicaoOperativa().equals("")) regDispVer.setDescCondicaoOperativa(condicaoOperativaRepository.findOne(evento.getCondicaoOperativa()).getDescricao());
		    		regDispVer.setCodDPP(ug.getCodid_dpp());
		    		linhasArqDispVer.add(regDispVer);    		
		    	}	
    		}
    	}
    	return linhasArqDispVer;    	
    }
    
    // Transforma o resposta do broker de formato base 64 para um byte array para transmissão
    private byte[] respBroker2ByteArray(RespIIBGerarRelatorio respBroker, String tipoArquivo)
    {
        byte[] respByte = null;
    	switch(tipoArquivo)
    	{
    	case "xml":
	    	respByte = Base64.getDecoder().decode(respBroker.getDadosXML());
    		break;
    	case "dat":
	    	respByte = Base64.getDecoder().decode(respBroker.getDadosDat());
    		break;
    	case "ambos":
	    	respByte = zipResposta(respBroker.getArquivoDat(), Base64.getDecoder().decode(respBroker.getDadosDat()),
	    			respBroker.getArquivoXML(), Base64.getDecoder().decode(respBroker.getDadosXML()));
    		break;
    	}        
        return respByte;        
    }
    
    // Gera arquivo zip de resposta dos arquivos Dat e XML
    private byte[] zipResposta(String arquivoDat, byte[] dadosDat, String arquivoXML, byte[] dadosXML)
    {
		try {
			// FileOutputStream dest = new FileOutputStream("c:\\temp\\ONS\\relatorios.zip");
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(byteArrayOutputStream));
			//out.setMethod(ZipOutputStream.DEFLATED);
			
			ZipEntry entry = new ZipEntry(arquivoDat);
			out.putNextEntry(entry);
		    out.write(dadosDat, 0, dadosDat.length);				
			
			entry = new ZipEntry(arquivoXML);
			out.putNextEntry(entry);
		    out.write(dadosXML, 0, dadosXML.length);				
			
			out.close();
			
			return byteArrayOutputStream.toByteArray();
		 } catch(Exception e) {
		    e.printStackTrace();
		 }
		return null;
    }
   
    
    // Gera arquivo zip completo de resposta dos arquivos
    private byte[] geraZipTotal(List<String> nomesArquivo, List<byte[]> dadosArquivos)
    {
		try {
			// FileOutputStream dest = new FileOutputStream("c:\\temp\\ONS\\relatorios.zip");
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(byteArrayOutputStream));
			//out.setMethod(ZipOutputStream.DEFLATED);
			for (int i=0;i<nomesArquivo.size();i++)
			{
				ZipEntry entry = new ZipEntry(nomesArquivo.get(i));
				out.putNextEntry(entry);
			    out.write(dadosArquivos.get(i), 0, dadosArquivos.get(i).length);				
			}
			
			out.close();
			
			return byteArrayOutputStream.toByteArray();
		 } catch(Exception e) {
		    e.printStackTrace();
		 }
		return null;
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
             if ( results.size() > 0 ){
            	 return results.get(0);
             }        	
        } catch (RuntimeException e){
        	e.printStackTrace();
        }
        
        return null;
	}

	private Map<String, String> getColunas(List<EquipamentoDTO> equipamentos) {
    	if (equipamentos == null)
    		return null;
    	
		Map<String, String> colunas = new LinkedHashMap<String, String>();
		colunas.put("Data", "Data");
		for (EquipamentoDTO equip : equipamentos) {
			colunas.put(equip.getNome() + "_O", "O");
			colunas.put(equip.getNome() + "_C", "C");
			colunas.put(equip.getNome() + "_E", "E");
		}
		
		return colunas;
	}
 
	
	private Evento buscaEventoEspelho(Date dataEventoEspelho, String idEquipamento ){
    	return eventoService.findEventoEspelhoDispVer(dataEventoEspelho, idEquipamento);		
	}
	
	private void insereEventosZeroHora(List<Evento> eventosComEspelho, List<UsinaDTO> instalacoes, Date dataEventoEspelho)
	{
    	System.out.println("insereEventosZeroHora dataEventoEspelho: " + dataEventoEspelho);
    	for (int i=0;i<instalacoes.size();i++)
    	{
        	for (int j=0;j<instalacoes.get(i).getEquipamentos().size();j++)
        	{
    			Evento eventoEspelho = this.buscaEventoEspelho(dataEventoEspelho, instalacoes.get(i).getEquipamentos().get(j).getId());
    	    	if (eventoEspelho!=null) eventosComEspelho.add(eventoEspelho);
    	    	// System.out.println("eventoEspelho: " + eventoEspelho);        		
        	}    		
    	}		
    	System.out.println("insereEventosZeroHora eventosComEspelho: " + eventosComEspelho.size());
	}

	@SuppressWarnings("unused")
	private void when(EventosApuradosEvent eventosApurados) {
		apuracoes.add(eventosApurados.getApuracao());
	}

}

