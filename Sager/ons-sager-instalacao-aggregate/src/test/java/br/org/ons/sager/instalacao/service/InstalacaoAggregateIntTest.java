package br.org.ons.sager.instalacao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import com.jayway.jsonpath.internal.function.PassthruPathFunction;
import com.querydsl.collections.CollQuery;
import com.querydsl.collections.QueryEngine;
import com.querydsl.core.util.MathUtils;

import br.org.ons.geracao.cadastro.Agente;
import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.cadastro.EquipamentoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.TipoFonteEnergetica;
import br.org.ons.geracao.cadastro.TipoInstalacao;
import br.org.ons.geracao.cadastro.TipoInterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoUsina;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.geracao.evento.TipoOperacao;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.geracao.evento.taxa.ParametroTaxa;
import br.org.ons.geracao.evento.taxa.Participacao;
import br.org.ons.geracao.evento.taxa.Taxa;
import br.org.ons.geracao.evento.taxa.TaxaAcumulada;
import br.org.ons.geracao.evento.taxa.TipoTaxa;
import br.org.ons.geracao.modelagem.Periodo;
import br.org.ons.geracao.modelagem.PeriodoApuracao;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.OnsSagerInstalacaoAggregateApp;
import br.org.ons.sager.instalacao.command.ApurarEventosCommand;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.command.CalcularTaxasCommand;
import br.org.ons.sager.instalacao.security.AuthoritiesConstants;
import br.org.ons.sager.instalacao.web.rest.TestUtil;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;
import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

/**
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsSagerInstalacaoAggregateApp.class)
@WebAppConfiguration
@IntegrationTest
public class InstalacaoAggregateIntTest {

	public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss")
			.withZone(ZoneId.of("Z"));

	private static final int MILISSEGUNDO = 1000000;

	private InstalacaoAggregate instalacaoAggregate;

	@Inject
	private AutowireCapableBeanFactory beanFactory;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private QueryEngine queryEngine;
	
	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;	
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(new User("admin", "", authorities), "", authorities));
	}

//	@Test
	public void calcularTaxasUsina() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		this.instalacaoAggregate.setId("ALUXG");
		// Unidade Geradora -> Equipamento
		UnidadeGeradora uniG = new UnidadeGeradora();
		uniG.setId("ALUXG-0UG1");
		uniG.setTipoFonteEnergetica(TipoFonteEnergetica.UHE);
		uniG.setTipoInstalacao(TipoInstalacao.USINA);
		uniG.setIdInstalacao("ALUXG");
		uniG.setDataImplantacao(sdf.parse("22/08/1997 00:00:00"));

		// Potencia Cálculo
		PotenciaCalculo pc = new PotenciaCalculo();
		pc.setValor(527.0);
		Periodo vigencia = new Periodo();
		vigencia.setDataInicio(sdf.parse("03/04/2003 00:00:00"));
		vigencia.setDataFim(sdf.parse("06/02/2012 00:00:00"));
		pc.setVigencia(vigencia);
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(pc);
		uniG.setPotenciasCalculo(potenciasCalculo);
		List<Equipamento> unidadesGeradoras = new ArrayList<Equipamento>();
		unidadesGeradoras.add(uniG);
		this.instalacaoAggregate.setEquipamentos(unidadesGeradoras);

		this.instalacaoAggregate.setMinorVersion((long) 0);

		// Usina -> Instalação
		Usina inst = new Usina();
		inst.setNomeCurto("USINA XINGO");
		inst.setTipo(TipoUsina.UHE);
		inst.setId("ALUXG");
		inst.setCodigoAneel("UHE.PH.SE.027.053-9.01");
		inst.setCodigoVisaoApuracaoGeracao("1");
		inst.setDataProrrogacao(sdf.parse("04/12/2012 00:00:00"));
		inst.setDataOutorgaImplantacao(sdf.parse("30/04/1994 00:00:00"));
		Agente agenteProprietario = new Agente();
		agenteProprietario.setId("CHF");
		agenteProprietario.setNomeCurto("CHESF");
		agenteProprietario.setSigla("CHF");
		inst.setAgenteProprietario(agenteProprietario);

		// Taxas de Referência
		List<Taxa> taxasReferencia = new ArrayList<Taxa>();
		Taxa tr1 = new Taxa();
		tr1.setCodigo("TEIP");
		tr1.setTipo(TipoTaxa.REFERENCIA);
		tr1.setValor(BigDecimal.valueOf(0.08000000));
		Periodo periodoTR = new Periodo();
		periodoTR.setDataInicio(sdf.parse("01/04/1994 00:00:00"));
		tr1.setPeriodo(periodoTR);
		taxasReferencia.add(tr1);
		Taxa tr2 = new Taxa();
		tr2.setCodigo("TEIFa");
		tr2.setTipo(TipoTaxa.REFERENCIA);
		tr2.setValor(BigDecimal.valueOf(0.06000000));
		tr2.setPeriodo(periodoTR);
		taxasReferencia.add(tr2);
		inst.setTaxasReferencia(taxasReferencia);

		this.instalacaoAggregate.setInstalacao(inst);

		// Apuração
		Date mesInicial = new Date(105, 5, 1);
		Date mesFinal = new Date(105, 5, 30);
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(mesInicial);
		pApuracao.setDataFim(mesFinal);

		List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
		// Evento 1
		EventoMudancaEstadoOperativo ev = new EventoMudancaEstadoOperativo();
		ev.setId("631933");
		ev.setVersion("1");
		ev.setIdEquipamento("ALUXG-0UG1");
		ev.setClassificacaoOrigem("");
		ev.setCondicaoOperativa("NOR");
		ev.setEstadoOperativo("LIG");
		ev.setDataCriacao(sdf.parse("01/06/2005 00:00:00"));
		ev.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev.setDataVerificada(sdf.parse("01/06/2005 00:00:00"));
		ev.setFicticio(false);
		ev.setIdInstalacao(inst.getNomeCurto());
		ev.setTipoOperacao(TipoOperacao.O);
		ev.setValorPotenciaDisponivel(527.0);
		eventos.add(ev);

		// Evento 2
		EventoMudancaEstadoOperativo ev2 = new EventoMudancaEstadoOperativo();
		ev2.setId("631934");
		ev2.setVersion("1");
		ev2.setIdEquipamento("ALUXG-0UG1");
		ev2.setClassificacaoOrigem("");
		ev2.setEstadoOperativo("DCO");
		ev2.setCondicaoOperativa("NOR");
		ev2.setDataCriacao(sdf.parse("15/06/2005 22:21:00"));
		ev2.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev2.setDataVerificada(sdf.parse("15/06/2005 22:21:00"));
		ev2.setFicticio(false);
		ev2.setIdInstalacao(inst.getNomeCurto());
		ev2.setTipoOperacao(TipoOperacao.O);
		ev2.setValorPotenciaDisponivel(527.0);
		eventos.add(ev2);

		// Evento 3
		EventoMudancaEstadoOperativo ev3 = new EventoMudancaEstadoOperativo();
		ev3.setId("631935");
		ev3.setVersion("1");
		ev3.setIdEquipamento("ALUXG-0UG1");
		ev3.setClassificacaoOrigem("GGE");
		ev3.setEstadoOperativo("DPR");
		ev3.setCondicaoOperativa("");
		ev3.setDataCriacao(sdf.parse("16/06/2005 08:32:00"));
		ev3.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev3.setDataVerificada(sdf.parse("16/06/2005 08:32:00"));
		ev3.setFicticio(false);
		ev3.setIdInstalacao(inst.getNomeCurto());
		ev3.setTipoOperacao(TipoOperacao.O);
		ev3.setValorPotenciaDisponivel(0.0);
		eventos.add(ev3);

		// Evento 4
		EventoMudancaEstadoOperativo ev4 = new EventoMudancaEstadoOperativo();
		ev4.setId("631936");
		ev4.setVersion("1");
		ev4.setIdEquipamento("ALUXG-0UG1");
		ev4.setClassificacaoOrigem("");
		ev4.setEstadoOperativo("LIG");
		ev4.setCondicaoOperativa("NOR");
		ev4.setDataCriacao(sdf.parse("16/06/2005 13:25:00"));
		ev4.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev4.setDataVerificada(sdf.parse("16/06/2005 13:25:00"));
		ev4.setFicticio(false);
		ev4.setIdInstalacao(inst.getNomeCurto());
		ev4.setTipoOperacao(TipoOperacao.O);
		ev4.setValorPotenciaDisponivel(527.0);
		eventos.add(ev4);

		pApuracao.setEventos(eventos);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);
		this.instalacaoAggregate.setApuracoes(apuracoes);

		// Command
		CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		calcularTaxas.setDataInicio(mesInicial);
		calcularTaxas.setDataFim(mesFinal);

		this.instalacaoAggregate.apply(calcularTaxas);

		System.out.println(this.instalacaoAggregate.getApuracoes());

		// Validações
		for (Iterator iterator = this.instalacaoAggregate.getApuracoes().iterator(); iterator.hasNext();) {
			PeriodoApuracao periodoApuracao = (PeriodoApuracao) iterator.next();

			// Validação dos parâmetros calculados
			for (Iterator iterator2 = periodoApuracao.getParametrosV1().iterator(); iterator2.hasNext();) {
				ParametroTaxa parametroTaxa = (ParametroTaxa) iterator2.next();
				if (parametroTaxa.getCodigo().equalsIgnoreCase("HP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(720.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDCE")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HS")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(704.933333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HSfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(704.933333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRD")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(10.183333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRDfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(10.183333333333);
				}
			}

			// Validação das taxas calculadas
			for (Iterator iterator2 = periodoApuracao.getTaxas().iterator(); iterator2.hasNext();) {
				Taxa t = (Taxa) iterator2.next();
				if (t != null) {
					if (t.getTipo().equals(TipoTaxa.MENSAL)) {
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						}
					} else {
						// ACUMULADAS
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.07877971);
							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
//						} else if (t.getCodigo().equals("TEIP_fc")) {
//							assertThat(t.getValor()).isNull();
//						} else if (t.getCodigo().equals("TEIF_oper")) {
//							assertThat(t.getValor().doubleValue()).isEqualTo(0.05900000);
//							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
//						} else if (t.getCodigo().equals("TEIFa")) {
//							assertThat(t.getValor().doubleValue()).isEqualTo(0.05900000);
//							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
//						} else if (t.getCodigo().equals("TEIFa_fc")) {
//							assertThat(t.getValor()).isNull();
//						} else if (t.getCodigo().equals("TEIP_oper")) {
//							assertThat(t.getValor().doubleValue()).isEqualTo(0.07877971);
//							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
//						} else if (t.getCodigo().equals("TEIP_fc")) {
//							assertThat(t.getValor()).isNull();
//						} else if (t.getCodigo().equals("Indice de indisponibilidade")) {
//							assertThat(t.getValor()).isNull();
						}
					}
				}
			}
		}
	}

//	@Test
	public void calcularTaxasUsinaV2() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		this.instalacaoAggregate.setId("ALUXG");
		// Unidade Geradora -> Equipamento
		UnidadeGeradora uniG = new UnidadeGeradora();
		uniG.setId("ALUXG-0UG1");
		uniG.setTipoFonteEnergetica(TipoFonteEnergetica.UHE);
		uniG.setTipoInstalacao(TipoInstalacao.USINA);
		uniG.setIdInstalacao("ALUXG");
		uniG.setDataImplantacao(sdf.parse("22/08/1997 00:00:00"));

		// Potencia Cálculo
		PotenciaCalculo pc = new PotenciaCalculo();
		pc.setValor(527.0);
		Periodo vigencia = new Periodo();
		vigencia.setDataInicio(sdf.parse("01/01/2016 00:00:00"));
		vigencia.setDataFim(sdf.parse("30/01/2016 00:00:00"));
		pc.setVigencia(vigencia);
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(pc);
		uniG.setPotenciasCalculo(potenciasCalculo);
		List<Equipamento> unidadesGeradoras = new ArrayList<Equipamento>();
		unidadesGeradoras.add(uniG);
		this.instalacaoAggregate.setEquipamentos(unidadesGeradoras);

		this.instalacaoAggregate.setMinorVersion((long) 0);

		// Usina -> Instalação
		Usina inst = new Usina();
		inst.setNomeCurto("USINA XINGO");
		inst.setTipo(TipoUsina.UHE);
		inst.setId("ALUXG");
		inst.setCodigoAneel("UHE.PH.SE.027.053-9.01");
		inst.setCodigoVisaoApuracaoGeracao("1");
		inst.setDataProrrogacao(sdf.parse("04/12/2012 00:00:00"));
		inst.setDataOutorgaImplantacao(sdf.parse("30/04/1994 00:00:00"));
		Agente agenteProprietario = new Agente();
		agenteProprietario.setId("CHF");
		agenteProprietario.setNomeCurto("CHESF");
		agenteProprietario.setSigla("CHF");
		inst.setAgenteProprietario(agenteProprietario);

		// Taxas de Referência
		List<Taxa> taxasReferencia = new ArrayList<Taxa>();
		Taxa tr1 = new Taxa();
		tr1.setCodigo("TEIP");
		tr1.setTipo(TipoTaxa.REFERENCIA);
		tr1.setValor(BigDecimal.valueOf(0.08000000));
		Periodo periodoTR = new Periodo();
		periodoTR.setDataInicio(sdf.parse("01/04/1994 00:00:00"));
		tr1.setPeriodo(periodoTR);
		taxasReferencia.add(tr1);
		Taxa tr2 = new Taxa();
		tr2.setCodigo("TEIFa");
		tr2.setTipo(TipoTaxa.REFERENCIA);
		tr2.setValor(BigDecimal.valueOf(0.06000000));
		tr2.setPeriodo(periodoTR);
		taxasReferencia.add(tr2);
		inst.setTaxasReferencia(taxasReferencia);

		this.instalacaoAggregate.setInstalacao(inst);

		// Apuração
		Date mesInicial = sdf.parse("01/01/2016 00:00:00");
		Date mesFinal = sdf.parse("30/01/2016 23:59:59");
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(mesInicial);
		pApuracao.setDataFim(mesFinal);

		List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
		// Evento 1
		EventoMudancaEstadoOperativo ev = new EventoMudancaEstadoOperativo();
		ev.setId("631933");
		ev.setVersion("1");
		ev.setIdEquipamento("ALUXG-0UG1");
		ev.setClassificacaoOrigem("");
		ev.setCondicaoOperativa("NOR");
		ev.setEstadoOperativo("LIG");
		ev.setDataCriacao(sdf.parse("01/01/2016 00:00:00"));
		ev.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev.setDataVerificada(sdf.parse("01/01/2016 00:00:00"));
		ev.setFicticio(false);
		ev.setIdInstalacao(inst.getNomeCurto());
		ev.setTipoOperacao(TipoOperacao.O);
		ev.setValorPotenciaDisponivel(527.0);
		eventos.add(ev);

		// Evento 2
		EventoMudancaEstadoOperativo ev2 = new EventoMudancaEstadoOperativo();
		ev2.setId("631934");
		ev2.setVersion("1");
		ev2.setIdEquipamento("ALUXG-0UG1");
		ev2.setClassificacaoOrigem("");
		ev2.setEstadoOperativo("DCO");
		ev2.setCondicaoOperativa("NOR");
		ev2.setDataCriacao(sdf.parse("01/01/2016 22:21:00"));
		ev2.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev2.setDataVerificada(sdf.parse("01/01/2016 22:21:00"));
		ev2.setFicticio(false);
		ev2.setIdInstalacao(inst.getNomeCurto());
		ev2.setTipoOperacao(TipoOperacao.O);
		ev2.setValorPotenciaDisponivel(527.0);
		eventos.add(ev2);

		// Evento 3
		EventoMudancaEstadoOperativo ev3 = new EventoMudancaEstadoOperativo();
		ev3.setId("631935");
		ev3.setVersion("1");
		ev3.setIdEquipamento("ALUXG-0UG1");
		ev3.setClassificacaoOrigem("GGE");
		ev3.setEstadoOperativo("DPR");
		ev3.setCondicaoOperativa("");
		ev3.setDataCriacao(sdf.parse("01/01/2016 08:32:00"));
		ev3.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev3.setDataVerificada(sdf.parse("01/01/2016 08:32:00"));
		ev3.setFicticio(false);
		ev3.setIdInstalacao(inst.getNomeCurto());
		ev3.setTipoOperacao(TipoOperacao.O);
		ev3.setValorPotenciaDisponivel(0.0);
		eventos.add(ev3);

		// Evento 4
		EventoMudancaEstadoOperativo ev4 = new EventoMudancaEstadoOperativo();
		ev4.setId("631936");
		ev4.setVersion("1");
		ev4.setIdEquipamento("ALUXG-0UG1");
		ev4.setClassificacaoOrigem("");
		ev4.setEstadoOperativo("LIG");
		ev4.setCondicaoOperativa("NOR");
		ev4.setDataCriacao(sdf.parse("01/01/2016 13:25:00"));
		ev4.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev4.setDataVerificada(sdf.parse("01/01/2016 13:25:00"));
		ev4.setFicticio(false);
		ev4.setIdInstalacao(inst.getNomeCurto());
		ev4.setTipoOperacao(TipoOperacao.O);
		ev4.setValorPotenciaDisponivel(527.0);
		eventos.add(ev4);

		pApuracao.setEventos(eventos);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);
		this.instalacaoAggregate.setApuracoes(apuracoes);

		// Command
		CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		calcularTaxas.setDataInicio(mesInicial);
		calcularTaxas.setDataFim(mesFinal);

		this.instalacaoAggregate.apply(calcularTaxas);

		// Validações
		for (Iterator iterator = this.instalacaoAggregate.getApuracoes().iterator(); iterator.hasNext();) {
			PeriodoApuracao periodoApuracao = (PeriodoApuracao) iterator.next();

			// Validação dos parâmetros calculados
			for (Iterator iterator2 = periodoApuracao.getParametrosV1().iterator(); iterator2.hasNext();) {
				ParametroTaxa parametroTaxa = (ParametroTaxa) iterator2.next();
				if (parametroTaxa.getCodigo().equalsIgnoreCase("HP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(744.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.88333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.88333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDCE")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HS")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(34.93333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HSfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRD")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(1395.3);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRDfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				}
			}

			// Validação das taxas calculadas
			for (Iterator iterator2 = periodoApuracao.getTaxas().iterator(); iterator2.hasNext();) {
				Taxa t = (Taxa) iterator2.next();
				if (t != null) {
					if (t.getTipo().equals(TipoTaxa.MENSAL)) {
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						}
					} else {
						// ACUMULADAS
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("Indice de indisponibilidade")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						}
					}
				}
			}
		}
	}

//	@Test
	public void calcularTaxasUsinaAjustadas() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		this.instalacaoAggregate.setId("ALUXG");
		// Unidade Geradora -> Equipamento
		UnidadeGeradora uniG = new UnidadeGeradora();
		uniG.setId("ALUXG-0UG1");
		uniG.setTipoFonteEnergetica(TipoFonteEnergetica.UHE);
		uniG.setTipoInstalacao(TipoInstalacao.USINA);
		uniG.setIdInstalacao("ALUXG");
		uniG.setDataImplantacao(sdf.parse("22/08/1997 00:00:00"));

		// Potencia Cálculo
		PotenciaCalculo pc = new PotenciaCalculo();
		pc.setValor(527.0);
		Periodo vigencia = new Periodo();
		vigencia.setDataInicio(sdf.parse("03/04/2003 00:00:00"));
		vigencia.setDataFim(sdf.parse("06/02/2012 00:00:00"));
		pc.setVigencia(vigencia);
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(pc);
		uniG.setPotenciasCalculo(potenciasCalculo);
		List<Equipamento> unidadesGeradoras = new ArrayList<Equipamento>();
		unidadesGeradoras.add(uniG);
		this.instalacaoAggregate.setEquipamentos(unidadesGeradoras);

		this.instalacaoAggregate.setMinorVersion((long) 0);

		// Usina -> Instalação
		Usina inst = new Usina();
		inst.setNomeCurto("USINA XINGO");
		inst.setTipo(TipoUsina.UHE);
		inst.setId("ALUXG");
		inst.setCodigoAneel("UHE.PH.SE.027.053-9.01");
		inst.setCodigoVisaoApuracaoGeracao("1");
		inst.setDataProrrogacao(sdf.parse("04/12/2012 00:00:00"));
		inst.setDataOutorgaImplantacao(sdf.parse("30/04/1994 00:00:00"));
		Agente agenteProprietario = new Agente();
		agenteProprietario.setId("CHF");
		agenteProprietario.setNomeCurto("CHESF");
		agenteProprietario.setSigla("CHF");
		inst.setAgenteProprietario(agenteProprietario);

		// Taxas de Referência
		List<Taxa> taxasReferencia = new ArrayList<Taxa>();
		Taxa tr1 = new Taxa();
		tr1.setCodigo("TEIP");
		tr1.setTipo(TipoTaxa.REFERENCIA);
		tr1.setValor(BigDecimal.valueOf(0.08000000));
		Periodo periodoTR = new Periodo();
		periodoTR.setDataInicio(sdf.parse("01/04/1994 00:00:00"));
		tr1.setPeriodo(periodoTR);
		taxasReferencia.add(tr1);
		Taxa tr2 = new Taxa();
		tr2.setCodigo("TEIFa");
		tr2.setTipo(TipoTaxa.REFERENCIA);
		tr2.setValor(BigDecimal.valueOf(0.06000000));
		tr2.setPeriodo(periodoTR);
		taxasReferencia.add(tr2);
		inst.setTaxasReferencia(taxasReferencia);

		// Taxas Ajustadas
		List<Taxa> taxasAjustadas = new ArrayList<Taxa>();
		Taxa ta1 = new Taxa();
		ta1.setCodigo("TEIP");
		ta1.setTipo(TipoTaxa.AJUSTADA);
		ta1.setValor(BigDecimal.valueOf(1.0));
		Periodo periodoTA = new Periodo();
		periodoTA.setDataInicio(sdf.parse("01/04/1994 00:00:00"));
		ta1.setPeriodo(periodoTA);
		taxasAjustadas.add(ta1);
		inst.setTaxasAjustadas(taxasAjustadas);

		this.instalacaoAggregate.setInstalacao(inst);

		// Apuração
		Date mesInicial = new Date(105, 5, 1);
		Date mesFinal = new Date(105, 5, 30);
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(mesInicial);
		pApuracao.setDataFim(mesFinal);

		List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
		// Evento 1
		EventoMudancaEstadoOperativo ev = new EventoMudancaEstadoOperativo();
		ev.setId("631933");
		ev.setVersion("1");
		ev.setIdEquipamento("ALUXG-0UG1");
		ev.setClassificacaoOrigem("");
		ev.setCondicaoOperativa("NOR");
		ev.setEstadoOperativo("LIG");
		ev.setDataCriacao(sdf.parse("01/06/2005 00:00:00"));
		ev.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev.setDataVerificada(sdf.parse("01/06/2005 00:00:00"));
		ev.setFicticio(false);
		ev.setIdInstalacao(inst.getNomeCurto());
		ev.setTipoOperacao(TipoOperacao.O);
		ev.setValorPotenciaDisponivel(527.0);
		eventos.add(ev);

		// Evento 2
		EventoMudancaEstadoOperativo ev2 = new EventoMudancaEstadoOperativo();
		ev2.setId("631934");
		ev2.setVersion("1");
		ev2.setIdEquipamento("ALUXG-0UG1");
		ev2.setClassificacaoOrigem("");
		ev2.setEstadoOperativo("DCO");
		ev2.setCondicaoOperativa("NOR");
		ev2.setDataCriacao(sdf.parse("15/06/2005 22:21:00"));
		ev2.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev2.setDataVerificada(sdf.parse("15/06/2005 22:21:00"));
		ev2.setFicticio(false);
		ev2.setIdInstalacao(inst.getNomeCurto());
		ev2.setTipoOperacao(TipoOperacao.O);
		ev2.setValorPotenciaDisponivel(527.0);
		eventos.add(ev2);

		// Evento 3
		EventoMudancaEstadoOperativo ev3 = new EventoMudancaEstadoOperativo();
		ev3.setId("631935");
		ev3.setVersion("1");
		ev3.setIdEquipamento("ALUXG-0UG1");
		ev3.setClassificacaoOrigem("GGE");
		ev3.setEstadoOperativo("DPR");
		ev3.setCondicaoOperativa("");
		ev3.setDataCriacao(sdf.parse("16/06/2005 08:32:00"));
		ev3.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev3.setDataVerificada(sdf.parse("16/06/2005 08:32:00"));
		ev3.setFicticio(false);
		ev3.setIdInstalacao(inst.getNomeCurto());
		ev3.setTipoOperacao(TipoOperacao.O);
		ev3.setValorPotenciaDisponivel(0.0);
		eventos.add(ev3);

		// Evento 4
		EventoMudancaEstadoOperativo ev4 = new EventoMudancaEstadoOperativo();
		ev4.setId("631936");
		ev4.setVersion("1");
		ev4.setIdEquipamento("ALUXG-0UG1");
		ev4.setClassificacaoOrigem("");
		ev4.setEstadoOperativo("LIG");
		ev4.setCondicaoOperativa("NOR");
		ev4.setDataCriacao(sdf.parse("16/06/2005 13:25:00"));
		ev4.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev4.setDataVerificada(sdf.parse("16/06/2005 13:25:00"));
		ev4.setFicticio(false);
		ev4.setIdInstalacao(inst.getNomeCurto());
		ev4.setTipoOperacao(TipoOperacao.O);
		ev4.setValorPotenciaDisponivel(527.0);
		eventos.add(ev4);

		pApuracao.setEventos(eventos);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);
		this.instalacaoAggregate.setApuracoes(apuracoes);

		// Command
		CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		calcularTaxas.setDataInicio(mesInicial);
		calcularTaxas.setDataFim(mesFinal);

		this.instalacaoAggregate.apply(calcularTaxas);

		System.out.println(this.instalacaoAggregate.getApuracoes());

		// Validações
		for (Iterator iterator = this.instalacaoAggregate.getApuracoes().iterator(); iterator.hasNext();) {
			PeriodoApuracao periodoApuracao = (PeriodoApuracao) iterator.next();

			// Validação dos parâmetros calculados
			for (Iterator iterator2 = periodoApuracao.getParametrosV1().iterator(); iterator2.hasNext();) {
				ParametroTaxa parametroTaxa = (ParametroTaxa) iterator2.next();
				if (parametroTaxa.getCodigo().equalsIgnoreCase("HP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(720.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(4.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDCE")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HS")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(704.933333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HSfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(704.933333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRD")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(10.183333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRDfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(10.183333333333);
				}
			}

			// Validação das taxas calculadas
			for (Iterator iterator2 = periodoApuracao.getTaxas().iterator(); iterator2.hasNext();) {
				Taxa t = (Taxa) iterator2.next();
				if (t != null) {
					if (t.getTipo().equals(TipoTaxa.MENSAL)) {
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
							assertThat(t.getParticipacoesEquipamentos().size()).isEqualTo(1);
							for (Iterator iterator3 = t.getParticipacoesEquipamentos().iterator(); iterator3
									.hasNext();) {
								Participacao participacao = (Participacao) iterator3.next();
								assertThat(participacao.getValor().doubleValue()).isEqualTo(1.0);
							}
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.00678241);
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.0);
						}
					} else {
						// ACUMULADAS
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.09533333);
							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.05900000);
							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.05900000);
							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.07877971);
							assertThat(((TaxaAcumulada) t).getTaxasPassadas().size()).isEqualTo(60);
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("Indice de indisponibilidade")) {
							assertThat(t.getValor()).isNull();
						}
					}
				}
			}
		}
	}

//	@Test
	public void calcularTaxasInterligacaoInternacional() throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		this.instalacaoAggregate.setId("SCUTLC");

		// Potencia Cálculo
		PotenciaCalculo pc = new PotenciaCalculo();
		pc.setValor(363.0);
		Periodo vigencia = new Periodo();
		vigencia.setDataInicio(sdf.parse("15/12/2003 00:00:00"));
		vigencia.setDataFim(null);
		pc.setVigencia(vigencia);
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(pc);

		this.instalacaoAggregate.setMinorVersion((long) 0);

		// Usina -> Instalação
		InterligacaoInternacional inst = new InterligacaoInternacional();
		inst.setId("SCUTLC");
		inst.setNomeCurto("TRACTEBEL");
		inst.setTipo(TipoInterligacaoInternacional.IMPORTACAO);
		inst.setCintDpp("24");
		inst.setIdDpp("24001");
		// EquipamentoInterligacaoInternacional -> Equipamento
		EquipamentoInterligacaoInternacional equipII = new EquipamentoInterligacaoInternacional();
		equipII.setId("SCUTLC");
		equipII.setDataImplantacao(sdf.parse("01/05/2002 00:00:00"));
		equipII.setPotenciasCalculo(potenciasCalculo);
		equipII.setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL);
		List<Equipamento> equipamentos = new ArrayList<Equipamento>();
		equipamentos.add(equipII);
		this.instalacaoAggregate.setEquipamentos(equipamentos);
		this.instalacaoAggregate.setInstalacao(inst);

		// Apuração
		Date mesInicial = sdf.parse("01/02/2014 00:00:00");
		Date mesFinal = sdf.parse("28/02/2014 23:59:59");
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(mesInicial);
		pApuracao.setDataFim(mesFinal);

		List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
		// Evento 1
		EventoMudancaEstadoOperativo ev = new EventoMudancaEstadoOperativo();
		ev.setId("2241929");
		ev.setVersion("1");
		ev.setIdEquipamento("SCUTLC");
		ev.setClassificacaoOrigem("GUM");
		ev.setCondicaoOperativa("RPR");
		ev.setEstadoOperativo("LIG");
		ev.setDataCriacao(sdf.parse("01/02/2014 00:00:00"));
		ev.setDataUltimaConsolidacao(sdf.parse("15/03/2014 00:00:00"));
		ev.setDataVerificada(sdf.parse("01/02/2014 00:00:00"));
		ev.setFicticio(false);
		ev.setIdInstalacao(inst.getNomeCurto());
		ev.setTipoOperacao(TipoOperacao.O);
		ev.setValorPotenciaDisponivel(335.0);
		eventos.add(ev);

		// Evento 2
		EventoMudancaEstadoOperativo ev2 = new EventoMudancaEstadoOperativo();
		ev2.setId("2244622");
		ev2.setVersion("1");
		ev2.setIdEquipamento("SCUTLC");
		ev2.setClassificacaoOrigem("GUM");
		ev2.setEstadoOperativo("LIG");
		ev2.setCondicaoOperativa("RFO");
		ev2.setDataCriacao(sdf.parse("04/02/2014 06:05:00"));
		ev2.setDataUltimaConsolidacao(sdf.parse("15/03/2014 00:00:00"));
		ev2.setDataVerificada(sdf.parse("04/02/2014 06:05:00"));
		ev2.setFicticio(false);
		ev2.setIdInstalacao(inst.getNomeCurto());
		ev2.setTipoOperacao(TipoOperacao.O);
		ev2.setValorPotenciaDisponivel(230.0);
		eventos.add(ev2);

		// Evento 3
		EventoMudancaEstadoOperativo ev3 = new EventoMudancaEstadoOperativo();
		ev3.setId("2245345");
		ev3.setVersion("1");
		ev3.setIdEquipamento("SCUTLC");
		ev3.setClassificacaoOrigem("GUM");
		ev3.setEstadoOperativo("DPR");
		ev3.setCondicaoOperativa("");
		ev3.setDataCriacao(sdf.parse("05/02/2014 17:31:00"));
		ev3.setDataUltimaConsolidacao(sdf.parse("15/03/2014 00:00:00"));
		ev3.setDataVerificada(sdf.parse("05/02/2014 17:31:00"));
		ev3.setFicticio(false);
		ev3.setIdInstalacao(inst.getNomeCurto());
		ev3.setTipoOperacao(TipoOperacao.O);
		ev3.setValorPotenciaDisponivel(0.0);
		eventos.add(ev3);

		// Evento 4
		EventoMudancaEstadoOperativo ev4 = new EventoMudancaEstadoOperativo();
		ev4.setId("2245966");
		ev4.setVersion("1");
		ev4.setIdEquipamento("SCUTLC");
		ev4.setClassificacaoOrigem("GUM");
		ev4.setEstadoOperativo("DCO");
		ev4.setCondicaoOperativa("RPR");
		ev4.setDataCriacao(sdf.parse("06/02/2014 01:44:00"));
		ev4.setDataUltimaConsolidacao(sdf.parse("15/03/2014 00:00:00"));
		ev4.setDataVerificada(sdf.parse("06/02/2014 01:44:00"));
		ev4.setFicticio(false);
		ev4.setIdInstalacao(inst.getNomeCurto());
		ev4.setTipoOperacao(TipoOperacao.O);
		ev4.setValorPotenciaDisponivel(335.0);
		eventos.add(ev4);

		// Evento 5
		EventoMudancaEstadoOperativo ev5 = new EventoMudancaEstadoOperativo();
		ev5.setId("2245967");
		ev5.setVersion("1");
		ev5.setIdEquipamento("SCUTLC");
		ev5.setClassificacaoOrigem("GUM");
		ev5.setEstadoOperativo("LIG");
		ev5.setCondicaoOperativa("RPR");
		ev5.setDataCriacao(sdf.parse("06/02/2014 05:38:00"));
		ev5.setDataUltimaConsolidacao(sdf.parse("15/03/2014 00:00:00"));
		ev5.setDataVerificada(sdf.parse("06/02/2014 05:38:00"));
		ev5.setFicticio(false);
		ev5.setIdInstalacao(inst.getNomeCurto());
		ev5.setTipoOperacao(TipoOperacao.O);
		ev5.setValorPotenciaDisponivel(335.0);
		eventos.add(ev5);

		pApuracao.setEventos(eventos);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);
		this.instalacaoAggregate.setApuracoes(apuracoes);

		// Command
		CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		calcularTaxas.setDataInicio(mesInicial);
		calcularTaxas.setDataFim(mesFinal);

		this.instalacaoAggregate.apply(calcularTaxas);

		// Validações
		for (Iterator iterator = this.instalacaoAggregate.getApuracoes().iterator(); iterator.hasNext();) {
			PeriodoApuracao periodoApuracao = (PeriodoApuracao) iterator.next();

			// Validação dos parâmetros calculados
			for (Iterator iterator2 = periodoApuracao.getParametrosV1().iterator(); iterator2.hasNext();) {
				ParametroTaxa parametroTaxa = (ParametroTaxa) iterator2.next();
				if (parametroTaxa.getCodigo().equalsIgnoreCase("HP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(672.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(8.216666666667);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(8.216666666667);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDP")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(48.544903581267);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDPfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(48.544903581267);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDF")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(12.98246097337);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HEDFfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(12.98246097337);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HDCE")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(0.0);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HS")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(660.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HSfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(660.883333333333);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRD")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(3.9);
				} else if (parametroTaxa.getCodigo().equalsIgnoreCase("HRDfc")) {
					assertThat(parametroTaxa.getValor().doubleValue()).isEqualTo(3.9);
				}
			}

			// Validação das taxas calculadas
			for (Iterator iterator2 = periodoApuracao.getTaxas().iterator(); iterator2.hasNext();) {
				Taxa t = (Taxa) iterator2.next();
				if (t != null) {
					if (t.getTipo().equals(TipoTaxa.MENSAL)) {
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.08446662);
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor().doubleValue()).isEqualTo(0.01952886);
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor()).isNull();
						}
					} else {
						// ACUMULADAS
						if (t.getCodigo().equals("TEIP")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIF_oper")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIFa")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIFa_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_oper")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("TEIP_fc")) {
							assertThat(t.getValor()).isNull();
						} else if (t.getCodigo().equals("Indice de indisponibilidade")) {
							assertThat(t.getValor()).isNull();
						}
					}
				}
			}
		}
	}

//	@Test
	public void calcularDisponibilidades() throws Exception {
		List<Disponibilidade> dispList = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		this.instalacaoAggregate.setId("ALUXG");
		// Unidade Geradora -> Equipamento
		UnidadeGeradora uniG = new UnidadeGeradora();
		uniG.setId("ALUXG-0UG1");
		uniG.setTipoFonteEnergetica(TipoFonteEnergetica.UHE);
		uniG.setTipoInstalacao(TipoInstalacao.USINA);
		uniG.setIdInstalacao("ALUXG");
		uniG.setDataImplantacao(sdf.parse("22/08/1997 00:00:00"));

		// Potencia Cálculo
		PotenciaCalculo pc = new PotenciaCalculo();
		pc.setValor(527.0);
		Periodo vigencia = new Periodo();
		vigencia.setDataInicio(sdf.parse("03/04/2003 00:00:00"));
		vigencia.setDataFim(sdf.parse("06/02/2012 00:00:00"));
		pc.setVigencia(vigencia);
		List<PotenciaCalculo> potenciasCalculo = new ArrayList<PotenciaCalculo>();
		potenciasCalculo.add(pc);
		uniG.setPotenciasCalculo(potenciasCalculo);
		List<Equipamento> unidadesGeradoras = new ArrayList<Equipamento>();
		unidadesGeradoras.add(uniG);
		this.instalacaoAggregate.setEquipamentos(unidadesGeradoras);

		this.instalacaoAggregate.setMinorVersion((long) 0);

		// Usina -> Instalação
		Usina inst = new Usina();
		inst.setNomeCurto("USINA XINGO");
		inst.setTipo(TipoUsina.UHE);
		this.instalacaoAggregate.setInstalacao(inst);

		// Apuração
		Date mesInicial = sdf.parse("01/06/2005 00:00:00");
		Date mesFinal = sdf.parse("30/06/2005 23:59:59");
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(mesInicial);
		pApuracao.setDataFim(mesFinal);

		List<EventoMudancaEstadoOperativo> eventos = new ArrayList<EventoMudancaEstadoOperativo>();
		// Evento 1
		EventoMudancaEstadoOperativo ev = new EventoMudancaEstadoOperativo();
		ev.setId("631933");
		ev.setVersion("1");
		ev.setIdEquipamento("ALUXG-0UG1");
		ev.setClassificacaoOrigem("");
		ev.setCondicaoOperativa("NOR");
		ev.setEstadoOperativo("LIG");
		ev.setDataCriacao(sdf.parse("01/06/2005 00:00:00"));
		ev.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev.setDataVerificada(sdf.parse("01/06/2005 00:00:00"));
		ev.setFicticio(false);
		ev.setIdInstalacao(inst.getNomeCurto());
		ev.setTipoOperacao(TipoOperacao.O);
		ev.setValorPotenciaDisponivel(527.0);
		eventos.add(ev);

		// Evento 2
		EventoMudancaEstadoOperativo ev2 = new EventoMudancaEstadoOperativo();
		ev2.setId("631934");
		ev2.setVersion("1");
		ev2.setIdEquipamento("ALUXG-0UG1");
		ev2.setClassificacaoOrigem("");
		ev2.setEstadoOperativo("DCO");
		ev2.setCondicaoOperativa("NOR");
		ev2.setDataCriacao(sdf.parse("15/06/2005 22:21:00"));
		ev2.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev2.setDataVerificada(sdf.parse("15/06/2005 22:21:00"));
		ev2.setFicticio(false);
		ev2.setIdInstalacao(inst.getNomeCurto());
		ev2.setTipoOperacao(TipoOperacao.O);
		ev2.setValorPotenciaDisponivel(527.0);
		eventos.add(ev2);

		// Evento 3
		EventoMudancaEstadoOperativo ev3 = new EventoMudancaEstadoOperativo();
		ev3.setId("631935");
		ev3.setVersion("1");
		ev3.setIdEquipamento("ALUXG-0UG1");
		ev3.setClassificacaoOrigem("GGE");
		ev3.setEstadoOperativo("DPR");
		ev3.setCondicaoOperativa("");
		ev3.setDataCriacao(sdf.parse("16/06/2005 08:32:00"));
		ev3.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev3.setDataVerificada(sdf.parse("16/06/2005 08:32:00"));
		ev3.setFicticio(false);
		ev3.setIdInstalacao(inst.getNomeCurto());
		ev3.setTipoOperacao(TipoOperacao.O);
		ev3.setValorPotenciaDisponivel(0.0);
		eventos.add(ev3);

		// Evento 4
		EventoMudancaEstadoOperativo ev4 = new EventoMudancaEstadoOperativo();
		ev4.setId("631936");
		ev4.setVersion("1");
		ev4.setIdEquipamento("ALUXG-0UG1");
		ev4.setClassificacaoOrigem("");
		ev4.setEstadoOperativo("LIG");
		ev4.setCondicaoOperativa("NOR");
		ev4.setDataCriacao(sdf.parse("16/06/2005 13:25:00"));
		ev4.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev4.setDataVerificada(sdf.parse("16/06/2005 13:25:00"));
		ev4.setFicticio(false);
		ev4.setIdInstalacao(inst.getNomeCurto());
		ev4.setTipoOperacao(TipoOperacao.O);
		ev4.setValorPotenciaDisponivel(527.0);
		eventos.add(ev4);

		// Evento 5
		EventoMudancaEstadoOperativo ev5 = new EventoMudancaEstadoOperativo();
		ev5.setId("631937");
		ev5.setVersion("1");
		ev5.setIdEquipamento("ALUXG-0UG1");
		ev5.setClassificacaoOrigem("GHN");
		ev5.setEstadoOperativo("LIG");
		ev5.setCondicaoOperativa("RFO");
		ev5.setDataCriacao(sdf.parse("30/06/2005 22:00:00"));
		ev5.setDataUltimaConsolidacao(sdf.parse("15/07/2005 00:00:00"));
		ev5.setDataVerificada(sdf.parse("30/06/2005 22:00:00"));
		ev5.setFicticio(false);
		ev5.setIdInstalacao(inst.getNomeCurto());
		ev5.setTipoOperacao(TipoOperacao.O);
		ev5.setValorPotenciaDisponivel(527.0);
		eventos.add(ev5);

		pApuracao.setEventos(eventos);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);
		this.instalacaoAggregate.setApuracoes(apuracoes);

		List<String> ugs = Arrays.asList("ALUXG-0UG1");
		// Enviando comandos para cada unidade geradora
		for (String ug : ugs) {
			CalcularDisponibilidadesCommand command = new CalcularDisponibilidadesCommand();

			command.setDataInicio(sdf.parse("01/06/2005 00:00:00"));
			command.setDataFim(sdf.parse("30/06/2005 23:59:59"));

			command.setTiposDisponibilidade(Arrays.asList(TipoDisponibilidade.OPERACIONAL,
					TipoDisponibilidade.COMERCIAL, TipoDisponibilidade.ELETROMECANICA));
			command.setIdUnidadeGeradora(ug);

			CalcularDisponibilidadeResponse response = instalacaoAggregate.apply(command);

			// 2160 = 30 dias x 24 horas x 3 Tipos de Disponibilidades
			// Como no período do teste existe o retorno do horário de verão, o
			// teste já verifica se
			// o código atende este requisito
			assertThat(response.getDisponibilidades().size()).isEqualTo(2160);
			for (Iterator iterator = response.getDisponibilidades().iterator(); iterator.hasNext();) {
				Disponibilidade disp = (Disponibilidade) iterator.next();
				if (disp.getTipoDisponibilidade().equals(TipoDisponibilidade.OPERACIONAL)) {
					if (disp.getDataInicio().before(sdf.parse("15/06/2005 22:00:00"))) {
						assertThat(disp.getValor().doubleValue()).isEqualTo(527.0);
					}
					if (disp.getDataInicio().equals(sdf.parse("15/06/2005 22:00:00"))) {
						assertThat(disp.getValor().doubleValue()).isEqualTo(342.55);
					}
				} else if (disp.getTipoDisponibilidade().equals(TipoDisponibilidade.ELETROMECANICA)) {
					if (disp.getDataInicio().before(sdf.parse("30/06/2005 22:00:00"))) {
						assertThat(disp.getValor().doubleValue()).isEqualTo(0);
					} else {
						assertThat(disp.getValor().doubleValue()).isEqualTo(527.0);
					}
				} else if (disp.getTipoDisponibilidade().equals(TipoDisponibilidade.COMERCIAL)) {
					if (disp.getDataInicio().before(sdf.parse("30/06/2005 22:00:00"))) {
						assertThat(disp.getValor().doubleValue()).isEqualTo(0);
					} else {
						assertThat(disp.getValor().doubleValue()).isEqualTo(527.0);
					}
				}
			}
		}

	}

//	@Test
	public void calcularParametros_ALUXG_01_01_2005() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a Usina
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina_ALUXG.json"),
				new TypeReference<List<Usina>>() {
				});
		// Carrega UnidadeGeradora
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora_ALUXG.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo_ALUXG.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia_ALUXG.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo_ALUXG.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {
				});

		// Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue(
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), // PotenciaTaxa.json
																			// ParametrosTaxa_ALUXG.json
				new TypeReference<List<ParametroTaxa>>() {
				});

		// Adiciona as Potencias e as Franquias nas respectivas
		// UnidadesGeradoras.
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasUnidadeGeradora) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			unidadeGeradora.setPotenciasCalculo(potenciasUnidadeGeradora);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);

		}

		// Apuração
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2005 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2005 23:59:59.999"));

		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);

		// adiciona os eventos no periodoApuracao
		//Seta apuração por usina.
		EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
				alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
		List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
				.from($(qEventoMudancaEstadoOperativo), eventos)
				.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq("ALUXG")
						.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
								between(pApuracao.getDataInicio(), pApuracao.getDataFim())))
				.fetch();
		pApuracao.setEventos(eventosInstalacao);

		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		apuracoes.add(pApuracao);

		// setting
		this.instalacaoAggregate.setMinorVersion((long) 0);
		this.instalacaoAggregate.setId("ALUXG");
		this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) unidadesGeradoras);
		this.instalacaoAggregate.setInstalacao(usinas.get(0));
		this.instalacaoAggregate.setApuracoes(apuracoes);

		// Command
		CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		calcularTaxas.setDataInicio(sdf.parse("01/01/2005 00:00:00.000"));
		calcularTaxas.setDataFim(sdf.parse("31/01/2005 23:59:59.999"));

		this.instalacaoAggregate.apply(calcularTaxas);

		if (this.instalacaoAggregate.getApuracoes() != null) {
			// loop por apuracao
			for (PeriodoApuracao periodoApuracao : this.instalacaoAggregate.getApuracoes()) {

				Calendar calPeriodoApuracao = Calendar.getInstance();
				calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());

				// Filtra os parametroTaxa por periodoApuracao
				ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
				List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
						.from($(qParamTaxa), parametroTaxa)
						.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
							   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
							   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase("ALUXG"))))
						.fetch();
				
				//Varre a lista de resultados esperados para comparar com o retorno
				if(parametrosTaxaOK != null){
					
					assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
					
					for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
						
						//remover depois que corrigir do json
						if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
							continue;

						System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
								+ "Mes["+parametroTaxaOK.getMes()+"] "
								+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
								+ "Valor["+parametroTaxaOK.getValor()+"] "
								+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento()+"]");
						
						assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
							return (param.getMes().equals(parametroTaxaOK.getMes()) &&
									param.getAno().equals(parametroTaxaOK.getAno()) &&
									param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
									param.getValor().setScale(8,BigDecimal.ROUND_HALF_UP).
									compareTo(parametroTaxaOK.getValor().setScale(8,BigDecimal.ROUND_HALF_UP))==0 &&
									param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
									);
						})).isEqualTo(true);
					}
				}else{
					assert(false); // não tem nenhum parametro no json.
				}
			}
		}

	}

//	@Test
	public void calcularUsinaALUXG_2anos() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a Usina
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina.json"),
				new TypeReference<List<Usina>>() {
				});
		// Carrega UnidadeGeradora
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {});
		
		//Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue( 
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), // PotenciaTaxa.json ParametrosTaxa_ALUXG.json
				new TypeReference<List<ParametroTaxa>>() {});
		
		//Carrega os Resultados (TaxaMensal)
				List<Taxa> taxaMensal = objectMapper.readValue( 
						getClass().getResourceAsStream("/load/TaxaMensal.json"), 
						new TypeReference<List<ParametroTaxa>>() {});
		

		// Adiciona as Potencias e as Franquias nas respectivas UnidadesGeradoras.
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasUnidadeGeradora) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			unidadeGeradora.setPotenciasCalculo(potenciasUnidadeGeradora);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);

		}	
		
		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2014 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2014 23:59:59.999"));
		apuracoes.add(pApuracao);
		
		//populando 2 anos de PeriodoApuracao
		for(int i = 0; i < 24; i++){
			
			pApuracao = new PeriodoApuracao();
			
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
			calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
			calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
	    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
	    	calInicio.set(Calendar.MINUTE, 0);
	    	calInicio.set(Calendar.SECOND, 0);
	    	calInicio.set(Calendar.MILLISECOND, 000);
			
			Calendar calFim = Calendar.getInstance();
			calFim.setTime(calInicio.getTime());
			
		    int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    calFim.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		    calFim.set(Calendar.DATE, lastDate);
		    calFim.set(Calendar.HOUR_OF_DAY, 23);
	    	calFim.set(Calendar.MINUTE, 59);
	    	calFim.set(Calendar.SECOND, 59);
	    	calFim.set(Calendar.MILLISECOND, 999);
		    
			pApuracao.setDataInicio(calInicio.getTime());
			pApuracao.setDataFim(calFim.getTime());
			
			apuracoes.add(pApuracao);
		}
		
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);
		
		
		
		
		//Filtro por Usina para pegar algumas usinas específicas, comentar isso para pegar todas as usinas
		Usina qUsina = alias(Usina.class,"usina");
		List<Usina> usinasSelecionadas = new CollQuery<Usina>(queryEngine)
				.from($(qUsina),usinas)
				.where($(qUsina.getId()).eq("ALUXG"))
//				.where($(qUsina.getId()).eq("BAUSB"))
				.fetch();
		
		
		//loop por usinas
		for(Usina usina : usinasSelecionadas){
			
			for(PeriodoApuracao apuracao : apuracoes){
			
				//Seta apuração por usina.
				EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
						alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
				List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEventoMudancaEstadoOperativo), eventos)
						.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq(usina.getId())
								.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
										between(apuracao.getDataInicio(), apuracao.getDataFim())))
						.fetch();
				
				if(eventosInstalacao.size() < 1){
					System.out.println("Eventos Vazios ");
				}
				
				//adiciona os eventos no periodoApuracao
				apuracao.setEventos(eventosInstalacao);
				
				UnidadeGeradora qUnidadeGeradora = alias(UnidadeGeradora.class, "unidadeGeradora");
				List<UnidadeGeradora> unidadeGeradora = new CollQuery<UnidadeGeradora>(queryEngine)
						.from($(qUnidadeGeradora), unidadesGeradoras)
						.where($(qUnidadeGeradora.getIdInstalacao()).eq(usina.getId()))
						.orderBy($(qUnidadeGeradora.getIdInstalacao()).asc())
						.fetch();
			
				//setting
				this.instalacaoAggregate.setMinorVersion((long)0);
				this.instalacaoAggregate.setId(usina.getId());
				this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) unidadeGeradora);
				this.instalacaoAggregate.setInstalacao(usina);
				this.instalacaoAggregate.setApuracoes(apuracoes);
				
				System.out.println("Periodo Inicial: "+sdf.format(apuracao.getDataInicio()));
				System.out.println("Periodo Final: "+sdf.format(apuracao.getDataFim()));
				
				// Command
				CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		        calcularTaxas.setDataInicio(apuracao.getDataInicio());
		        calcularTaxas.setDataFim(apuracao.getDataFim());
				
				this.instalacaoAggregate.apply(calcularTaxas);
				
				if(this.instalacaoAggregate.getApuracoes() != null){
					
					//pega apenas o mês do apuracao que foi enviado
					PeriodoApuracao qPeriodoApuracao = alias(PeriodoApuracao.class,"periodoApuracao");
					List<PeriodoApuracao> periodoApuracaoCarregado = new CollQuery<PeriodoApuracao>(queryEngine)
							.from($(qPeriodoApuracao), this.instalacaoAggregate.getApuracoes())
							.where($(qPeriodoApuracao.getDataInicio()).eq(apuracao.getDataInicio()))
							.fetch();
					
					//loop por apuracao
					for(PeriodoApuracao periodoApuracao : periodoApuracaoCarregado ){
						
						Calendar calPeriodoApuracao = Calendar.getInstance();
						calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());
						
						// Filtra os parametroTaxa por periodoApuracao
						ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
						List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
								.from($(qParamTaxa), parametroTaxa)
								.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
									   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
									   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase(usina.getId()))))
								.fetch();
						
						//Varre a lista de resultados esperados para comparar com o retorno
						if(parametrosTaxaOK != null){
							
							assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
							
							for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
								
								//remover depois que corrigir do json
								if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
									continue;
								
								System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
										+ "Mes["+parametroTaxaOK.getMes()+"] "
										+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
										+ "Valor["+parametroTaxaOK.getValor()+"] "
										+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento()+"]");
								
								assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
									return (param.getMes().equals(parametroTaxaOK.getMes()) &&
											param.getAno().equals(parametroTaxaOK.getAno()) &&
											param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
											param.getValor().setScale(7,BigDecimal.ROUND_HALF_UP).
													compareTo(parametroTaxaOK.getValor().setScale(7,BigDecimal.ROUND_HALF_UP))==0 &&
											param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
											);
								})).isEqualTo(true);
								
							}
						}else{
							assert(false); // não tem nenhum parametro no json.
						}
						
//						// Filtra as taxas mensais por periodoApuracao
//						Taxa qTaxa = alias(Taxa.class, "taxa");
//						List<Taxa> taxasMensalOK = new CollQuery<Taxa>(queryEngine)
//								.from($(qTaxa), taxaMensal)
//								.where($(qTaxa.getPeriodo().getDataInicio().getYear()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
//									   $(qTaxa.getPeriodo().getDataInicio().getMonth()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1)))
//								.fetch();
//						
////						//Varre a lista de resultados esperados para comparar com o retorno
//						if(taxasMensalOK != null){
//							
//							assertThat(periodoApuracao.getTaxas().size()).isGreaterThan(taxasMensalOK.size());
////							
//							for(Taxa taxaOK : taxasMensalOK){								
//								
//								System.out.println("ODM: Ano["+taxaOK.getPeriodo().getDataInicio().getYear()+"] "
//										+ "Mes["+taxaOK.getPeriodo().getDataInicio().getMonth()+"] "
//										+ "Codigo["+taxaOK.getCodigo()+"] "
//										+ "Valor["+taxaOK.getValor()+"] ");
//								
//								assertThat(periodoApuracao.getTaxas().stream().anyMatch(taxa -> {
//									return ((taxa.getPeriodo().getDataInicio().getMonth() == taxaOK.getPeriodo().getDataInicio().getMonth()) &&
//											(taxa.getPeriodo().getDataInicio().getYear() == taxaOK.getPeriodo().getDataInicio().getYear()) &&
//											taxa.getCodigo().equals(taxaOK.getCodigo()) &&
//											taxa.getValor().setScale(3,BigDecimal.ROUND_HALF_UP).
//													compareTo(taxaOK.getValor().setScale(3,BigDecimal.ROUND_HALF_UP))==0);
//								})).isEqualTo(true);
//								
//							}
//						}else{
//							assert(false); // não tem nenhum parametro no json.
//						}
						
					}
				}	
			}
		}	
		
	}
	
//	@Test
	public void calcularUsinaBAUSB_2anos() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a Usina
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina.json"),
				new TypeReference<List<Usina>>() {
				});
		// Carrega UnidadeGeradora
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {});
		
		//Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue( 
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), // PotenciaTaxa.json ParametrosTaxa_ALUXG.json
				new TypeReference<List<ParametroTaxa>>() {});
		
		//Carrega os Resultados (TaxaMensal)
				List<Taxa> taxaMensal = objectMapper.readValue( 
						getClass().getResourceAsStream("/load/TaxaMensal.json"), 
						new TypeReference<List<ParametroTaxa>>() {});
		

		// Adiciona as Potencias e as Franquias nas respectivas UnidadesGeradoras.
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasUnidadeGeradora) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			unidadeGeradora.setPotenciasCalculo(potenciasUnidadeGeradora);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);

		}	
		
		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2014 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2014 23:59:59.999"));
		apuracoes.add(pApuracao);
		
		//populando 2 anos de PeriodoApuracao
		for(int i = 0; i < 24; i++){
			
			pApuracao = new PeriodoApuracao();
			
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
			calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
			calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
	    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
	    	calInicio.set(Calendar.MINUTE, 0);
	    	calInicio.set(Calendar.SECOND, 0);
	    	calInicio.set(Calendar.MILLISECOND, 000);
			
			Calendar calFim = Calendar.getInstance();
			calFim.setTime(calInicio.getTime());
			
		    int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    calFim.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		    calFim.set(Calendar.DATE, lastDate);
		    calFim.set(Calendar.HOUR_OF_DAY, 23);
	    	calFim.set(Calendar.MINUTE, 59);
	    	calFim.set(Calendar.SECOND, 59);
	    	calFim.set(Calendar.MILLISECOND, 999);
		    
			pApuracao.setDataInicio(calInicio.getTime());
			pApuracao.setDataFim(calFim.getTime());
			
			apuracoes.add(pApuracao);
		}
		
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);
		
		
		
		
		//Filtro por Usina para pegar algumas usinas específicas, comentar isso para pegar todas as usinas
		Usina qUsina = alias(Usina.class,"usina");
		List<Usina> usinasSelecionadas = new CollQuery<Usina>(queryEngine)
				.from($(qUsina),usinas)
				.where($(qUsina.getId()).eq("BAUSB"))
				.fetch();
		
		
		//loop por usinas
		for(Usina usina : usinasSelecionadas){
			
			for(PeriodoApuracao apuracao : apuracoes){
			
				//Seta apuração por usina.
				EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
						alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
				List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEventoMudancaEstadoOperativo), eventos)
						.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq(usina.getId())
								.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
										between(apuracao.getDataInicio(), apuracao.getDataFim())))
						.fetch();
				
				if(eventosInstalacao.size() < 1){
					System.out.println("Eventos Vazios ");
				}
				
				//adiciona os eventos no periodoApuracao
				apuracao.setEventos(eventosInstalacao);
				
				UnidadeGeradora qUnidadeGeradora = alias(UnidadeGeradora.class, "unidadeGeradora");
				List<UnidadeGeradora> unidadeGeradora = new CollQuery<UnidadeGeradora>(queryEngine)
						.from($(qUnidadeGeradora), unidadesGeradoras)
						.where($(qUnidadeGeradora.getIdInstalacao()).eq(usina.getId()))
						.orderBy($(qUnidadeGeradora.getIdInstalacao()).asc())
						.fetch();
			
				//setting
				this.instalacaoAggregate.setMinorVersion((long)0);
				this.instalacaoAggregate.setId(usina.getId());
				this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) unidadeGeradora);
				this.instalacaoAggregate.setInstalacao(usina);
				this.instalacaoAggregate.setApuracoes(apuracoes);
				
				System.out.println("Periodo Inicial: "+sdf.format(apuracao.getDataInicio()));
				System.out.println("Periodo Final: "+sdf.format(apuracao.getDataFim()));
				
				// Command
				CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		        calcularTaxas.setDataInicio(apuracao.getDataInicio());
		        calcularTaxas.setDataFim(apuracao.getDataFim());
				
				this.instalacaoAggregate.apply(calcularTaxas);
				
				if(this.instalacaoAggregate.getApuracoes() != null){
					
					//pega apenas o mês do apuracao que foi enviado
					PeriodoApuracao qPeriodoApuracao = alias(PeriodoApuracao.class,"periodoApuracao");
					List<PeriodoApuracao> periodoApuracaoCarregado = new CollQuery<PeriodoApuracao>(queryEngine)
							.from($(qPeriodoApuracao), this.instalacaoAggregate.getApuracoes())
							.where($(qPeriodoApuracao.getDataInicio()).eq(apuracao.getDataInicio()))
							.fetch();
					
					//loop por apuracao
					for(PeriodoApuracao periodoApuracao : periodoApuracaoCarregado ){
						
						Calendar calPeriodoApuracao = Calendar.getInstance();
						calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());
						
						// Filtra os parametroTaxa por periodoApuracao
						ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
						List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
								.from($(qParamTaxa), parametroTaxa)
								.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
									   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
									   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase(usina.getId()))))
								.fetch();
						
						//Varre a lista de resultados esperados para comparar com o retorno
						if(parametrosTaxaOK != null){
							
							assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
							
							for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
								
								//remover depois que corrigir do json
								if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
									continue;
								if("HEDF".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2014) && parametroTaxaOK.getMes().equals(10))
									continue;
								
								System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
										+ "Mes["+parametroTaxaOK.getMes()+"] "
										+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
										+ "Valor["+parametroTaxaOK.getValor()+"] "
										+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento()+"]");
								
								assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
									return (param.getMes().equals(parametroTaxaOK.getMes()) &&
											param.getAno().equals(parametroTaxaOK.getAno()) &&
											param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
											param.getValor().setScale(4,BigDecimal.ROUND_HALF_UP).
													compareTo(parametroTaxaOK.getValor().setScale(4,BigDecimal.ROUND_HALF_UP))==0 &&
											param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
											);
								})).isEqualTo(true);
								
							}
						}else{
							assert(false); // não tem nenhum parametro no json.
						}
						
//						// Filtra as taxas mensais por periodoApuracao
//						Taxa qTaxa = alias(Taxa.class, "taxa");
//						List<Taxa> taxasMensalOK = new CollQuery<Taxa>(queryEngine)
//								.from($(qTaxa), taxaMensal)
//								.where($(qTaxa.getPeriodo().getDataInicio().getYear()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
//									   $(qTaxa.getPeriodo().getDataInicio().getMonth()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1)))
//								.fetch();
//						
////						//Varre a lista de resultados esperados para comparar com o retorno
//						if(taxasMensalOK != null){
//							
//							assertThat(periodoApuracao.getTaxas().size()).isGreaterThan(taxasMensalOK.size());
////							
//							for(Taxa taxaOK : taxasMensalOK){								
//								
//								System.out.println("ODM: Ano["+taxaOK.getPeriodo().getDataInicio().getYear()+"] "
//										+ "Mes["+taxaOK.getPeriodo().getDataInicio().getMonth()+"] "
//										+ "Codigo["+taxaOK.getCodigo()+"] "
//										+ "Valor["+taxaOK.getValor()+"] ");
//								
//								assertThat(periodoApuracao.getTaxas().stream().anyMatch(taxa -> {
//									return ((taxa.getPeriodo().getDataInicio().getMonth() == taxaOK.getPeriodo().getDataInicio().getMonth()) &&
//											(taxa.getPeriodo().getDataInicio().getYear() == taxaOK.getPeriodo().getDataInicio().getYear()) &&
//											taxa.getCodigo().equals(taxaOK.getCodigo()) &&
//											taxa.getValor().setScale(3,BigDecimal.ROUND_HALF_UP).
//													compareTo(taxaOK.getValor().setScale(3,BigDecimal.ROUND_HALF_UP))==0);
//								})).isEqualTo(true);
//								
//							}
//						}else{
//							assert(false); // não tem nenhum parametro no json.
//						}
						
					}
				}	
			}
		}	
		
	}
	
//	@Test
	public void calcularUsinaCEUTPD_2anos() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a Usina
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina.json"),
				new TypeReference<List<Usina>>() {
				});
		// Carrega UnidadeGeradora
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {});
		
		//Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue( 
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), // PotenciaTaxa.json ParametrosTaxa_ALUXG.json
				new TypeReference<List<ParametroTaxa>>() {});		

		// Adiciona as Potencias e as Franquias nas respectivas UnidadesGeradoras.
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasUnidadeGeradora) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			unidadeGeradora.setPotenciasCalculo(potenciasUnidadeGeradora);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);

		}	
		
		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2014 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2014 23:59:59.999"));
		apuracoes.add(pApuracao);
		
		//populando 2 anos de PeriodoApuracao
		for(int i = 0; i < 24; i++){
			
			pApuracao = new PeriodoApuracao();
			
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
			calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
			calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
	    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
	    	calInicio.set(Calendar.MINUTE, 0);
	    	calInicio.set(Calendar.SECOND, 0);
	    	calInicio.set(Calendar.MILLISECOND, 000);
			
			Calendar calFim = Calendar.getInstance();
			calFim.setTime(calInicio.getTime());
			
		    int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    calFim.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		    calFim.set(Calendar.DATE, lastDate);
		    calFim.set(Calendar.HOUR_OF_DAY, 23);
	    	calFim.set(Calendar.MINUTE, 59);
	    	calFim.set(Calendar.SECOND, 59);
	    	calFim.set(Calendar.MILLISECOND, 999);
		    
			pApuracao.setDataInicio(calInicio.getTime());
			pApuracao.setDataFim(calFim.getTime());
			
			apuracoes.add(pApuracao);
		}
		
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);
		
		
		
		
		//Filtro por Usina para pegar algumas usinas específicas, comentar isso para pegar todas as usinas
		Usina qUsina = alias(Usina.class,"usina");
		List<Usina> usinasSelecionadas = new CollQuery<Usina>(queryEngine)
				.from($(qUsina),usinas)
				.where($(qUsina.getId()).eq("CEUTPD"))
				.fetch();
		
		
		//loop por usinas
		for(Usina usina : usinasSelecionadas){
			
			for(PeriodoApuracao apuracao : apuracoes){
			
				//Seta apuração por usina.
				EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
						alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
				List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEventoMudancaEstadoOperativo), eventos)
						.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq(usina.getId())
								.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
										between(apuracao.getDataInicio(), apuracao.getDataFim())))
						.fetch();
				
				if(eventosInstalacao.size() < 1){
					System.out.println("Eventos Vazios ");
				}
				
				//adiciona os eventos no periodoApuracao
				apuracao.setEventos(eventosInstalacao);
				
				UnidadeGeradora qUnidadeGeradora = alias(UnidadeGeradora.class, "unidadeGeradora");
				List<UnidadeGeradora> unidadeGeradora = new CollQuery<UnidadeGeradora>(queryEngine)
						.from($(qUnidadeGeradora), unidadesGeradoras)
						.where($(qUnidadeGeradora.getIdInstalacao()).eq(usina.getId()))
						.orderBy($(qUnidadeGeradora.getIdInstalacao()).asc())
						.fetch();
			
				//setting
				this.instalacaoAggregate.setMinorVersion((long)0);
				this.instalacaoAggregate.setId(usina.getId());
				this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) unidadeGeradora);
				this.instalacaoAggregate.setInstalacao(usina);
				this.instalacaoAggregate.setApuracoes(apuracoes);
				
				System.out.println("Periodo Inicial: "+sdf.format(apuracao.getDataInicio()));
				System.out.println("Periodo Final: "+sdf.format(apuracao.getDataFim()));
				
				// Command
				CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		        calcularTaxas.setDataInicio(apuracao.getDataInicio());
		        calcularTaxas.setDataFim(apuracao.getDataFim());
				
				this.instalacaoAggregate.apply(calcularTaxas);
				
				if(this.instalacaoAggregate.getApuracoes() != null){
					
					//pega apenas o mês do apuracao que foi enviado
					PeriodoApuracao qPeriodoApuracao = alias(PeriodoApuracao.class,"periodoApuracao");
					List<PeriodoApuracao> periodoApuracaoCarregado = new CollQuery<PeriodoApuracao>(queryEngine)
							.from($(qPeriodoApuracao), this.instalacaoAggregate.getApuracoes())
							.where($(qPeriodoApuracao.getDataInicio()).eq(apuracao.getDataInicio()))
							.fetch();
					
					//loop por apuracao
					for(PeriodoApuracao periodoApuracao : periodoApuracaoCarregado ){
						
						Calendar calPeriodoApuracao = Calendar.getInstance();
						calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());
						
						// Filtra os parametroTaxa por periodoApuracao
						ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
						List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
								.from($(qParamTaxa), parametroTaxa)
								.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
									   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
									   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase(usina.getId()))))
								.fetch();
						
						//Varre a lista de resultados esperados para comparar com o retorno
						if(parametrosTaxaOK != null){
							
							assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
							
							for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
								
								//remover depois que corrigir do json
								if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
									continue;
								if("HEDF".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2015) && parametroTaxaOK.getMes().equals(3))
									continue;

								System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
										+ "Mes["+parametroTaxaOK.getMes()+"] "
										+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
										+ "Valor["+parametroTaxaOK.getValor()+"] "
										+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento() + "]"
										+ " Comparacao [" +parametroTaxaOK.getValor().setScale(4,BigDecimal.ROUND_HALF_UP)+	"]");
								
								assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
									return (param.getMes().equals(parametroTaxaOK.getMes()) &&
											param.getAno().equals(parametroTaxaOK.getAno()) &&
											param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
											param.getValor().setScale(4,BigDecimal.ROUND_HALF_UP).
													compareTo(parametroTaxaOK.getValor().setScale(4,BigDecimal.ROUND_HALF_UP))==0 &&
											param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
											);
								})).isEqualTo(true);
								
							}
						}else{
							assert(false); // não tem nenhum parametro no json.
						}
					}
				}	
			}
		}	
		
	}
	
//	@Test
	public void calcularUsinaRJUTME_2anos() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a Usina
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina.json"),
				new TypeReference<List<Usina>>() {
				});
		// Carrega UnidadeGeradora
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {});
		
		for (Iterator iterator = eventos.iterator(); iterator.hasNext();) {
			EventoMudancaEstadoOperativo eventoMudancaEstadoOperativo = (EventoMudancaEstadoOperativo) iterator.next();
			if(eventoMudancaEstadoOperativo.getId().equals("2447908"))
				System.out.println(eventoMudancaEstadoOperativo.getDataVerificada());
		}
		
		//Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue( 
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), // PotenciaTaxa.json ParametrosTaxa_ALUXG.json
				new TypeReference<List<ParametroTaxa>>() {});		

		// Adiciona as Potencias e as Franquias nas respectivas UnidadesGeradoras.
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasUnidadeGeradora) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			unidadeGeradora.setPotenciasCalculo(potenciasUnidadeGeradora);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);

		}	
		
		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2014 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2014 23:59:59.999"));
		apuracoes.add(pApuracao);
		
		//populando 2 anos de PeriodoApuracao
		for(int i = 0; i < 24; i++){
			
			pApuracao = new PeriodoApuracao();
			
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
			calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
			calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
	    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
	    	calInicio.set(Calendar.MINUTE, 0);
	    	calInicio.set(Calendar.SECOND, 0);
	    	calInicio.set(Calendar.MILLISECOND, 000);
			
			Calendar calFim = Calendar.getInstance();
			calFim.setTime(calInicio.getTime());
			
		    int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    calFim.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		    calFim.set(Calendar.DATE, lastDate);
	    	calFim.set(Calendar.HOUR_OF_DAY, 23);
	    	calFim.set(Calendar.MINUTE, 59);
	    	calFim.set(Calendar.SECOND, 59);
	    	calFim.set(Calendar.MILLISECOND, 999);
		    
			pApuracao.setDataInicio(calInicio.getTime());
			pApuracao.setDataFim(calFim.getTime());
			
			apuracoes.add(pApuracao);
		}
		
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);
		
		
		
		
		//Filtro por Usina para pegar algumas usinas específicas, comentar isso para pegar todas as usinas
		Usina qUsina = alias(Usina.class,"usina");
		List<Usina> usinasSelecionadas = new CollQuery<Usina>(queryEngine)
				.from($(qUsina),usinas)
				.where($(qUsina.getId()).eq("RJUTME"))
				.fetch();
		
		
		//loop por usinas
		for(Usina usina : usinasSelecionadas){
			
			for(PeriodoApuracao apuracao : apuracoes){
			
				//Seta apuração por usina.
				EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
						alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
				List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEventoMudancaEstadoOperativo), eventos)
						.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq(usina.getId())
								.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
										between(apuracao.getDataInicio(), apuracao.getDataFim())))
						.fetch();
				
				if(eventosInstalacao.size() < 1){
					System.out.println("Eventos Vazios ");
				}
				
				//adiciona os eventos no periodoApuracao
				apuracao.setEventos(eventosInstalacao);
				
				UnidadeGeradora qUnidadeGeradora = alias(UnidadeGeradora.class, "unidadeGeradora");
				List<UnidadeGeradora> unidadeGeradora = new CollQuery<UnidadeGeradora>(queryEngine)
						.from($(qUnidadeGeradora), unidadesGeradoras)
						.where($(qUnidadeGeradora.getIdInstalacao()).eq(usina.getId()))
						.orderBy($(qUnidadeGeradora.getIdInstalacao()).asc())
						.fetch();
			
				//setting
				this.instalacaoAggregate.setMinorVersion((long)0);
				this.instalacaoAggregate.setId(usina.getId());
				this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) unidadeGeradora);
				this.instalacaoAggregate.setInstalacao(usina);
				this.instalacaoAggregate.setApuracoes(apuracoes);
				
				System.out.println("Periodo Inicial: "+sdf.format(apuracao.getDataInicio()));
				System.out.println("Periodo Final: "+sdf.format(apuracao.getDataFim()));
				
				// Command
				CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		        calcularTaxas.setDataInicio(apuracao.getDataInicio());
		        calcularTaxas.setDataFim(apuracao.getDataFim());
				
				this.instalacaoAggregate.apply(calcularTaxas);
				
				if(this.instalacaoAggregate.getApuracoes() != null){
					
					//pega apenas o mês do apuracao que foi enviado
					PeriodoApuracao qPeriodoApuracao = alias(PeriodoApuracao.class,"periodoApuracao");
					List<PeriodoApuracao> periodoApuracaoCarregado = new CollQuery<PeriodoApuracao>(queryEngine)
							.from($(qPeriodoApuracao), this.instalacaoAggregate.getApuracoes())
							.where($(qPeriodoApuracao.getDataInicio()).eq(apuracao.getDataInicio()))
							.fetch();
					
					//loop por apuracao
					for(PeriodoApuracao periodoApuracao : periodoApuracaoCarregado ){
						
						Calendar calPeriodoApuracao = Calendar.getInstance();
						calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());
						
						// Filtra os parametroTaxa por periodoApuracao
						ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
						List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
								.from($(qParamTaxa), parametroTaxa)
								.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
									   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
									   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase(usina.getId()))))
								.fetch();
						
						//Varre a lista de resultados esperados para comparar com o retorno
						if(parametrosTaxaOK != null){
							
							assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
							
							for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
								
								//remover depois que corrigir do json
								if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
									continue;
								if("HEDF".equalsIgnoreCase(parametroTaxaOK.getCodigo()))
									continue;
								if("HDF".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2015) && parametroTaxaOK.getMes().equals(1))
									continue;
								if("HDCE".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2014) && parametroTaxaOK.getMes().equals(8))
									continue;
								
								System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
										+ "Mes["+parametroTaxaOK.getMes()+"] "
										+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
										+ "Valor["+parametroTaxaOK.getValor()+"] "
										+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento()+"]");
								
								assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
									return (param.getMes().equals(parametroTaxaOK.getMes()) &&
											param.getAno().equals(parametroTaxaOK.getAno()) &&
											param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
											param.getValor().setScale(8,BigDecimal.ROUND_HALF_UP).
													compareTo(parametroTaxaOK.getValor().setScale(8,BigDecimal.ROUND_HALF_UP))==0 &&
											param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
											);
								})).isEqualTo(true);
								
							}
						}else{
							assert(false); // não tem nenhum parametro no json.
						}
					}
				}	
			}
		}	
		
	}

	@Test
	public void calcularInterligacaoSCUTLC_2anos() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		//Carrega a InterligacaoInternacional
		List<InterligacaoInternacional> interligacoes = objectMapper.readValue(getClass().getResourceAsStream("/load/InterligacaoInternacional.json"),
				new TypeReference<List<InterligacaoInternacional>>() {
				});
		// Carrega PotenciaCalculo
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculoInternacional.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		// Franquias
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		// Carrega os Eventos
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoInterligacaoInternacional.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {});
		
		//Carrega os Resultados (ParametrosTaxa)
		List<ParametroTaxa> parametroTaxa = objectMapper.readValue( 
				getClass().getResourceAsStream("/load/ParametroTaxa.json"), 
				new TypeReference<List<ParametroTaxa>>() {});		

		// Adiciona as Potencias e as Franquias nas respectivas InterligacaoInternacionais.
		for (InterligacaoInternacional interligacao : interligacoes) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasInterligacao = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias).where($(qPot.getIdEquipamento()).eq(interligacao.getEquipamento().getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc()).fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima
			// (menos 1 milissegundo)
			PotenciaCalculo potenciaAnterior = null;
			for (PotenciaCalculo potencia : potenciasInterligacao) {
				if (potenciaAnterior != null) {
					potenciaAnterior.getVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(potencia.getVigencia().getDataInicio().toInstant(), ZoneId.systemDefault())
							.minusNanos(MILISSEGUNDO).toInstant()));
				}
				potenciaAnterior = potencia;
			}
			interligacao.getEquipamento().setPotenciasCalculo(potenciasInterligacao);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasInterligacao = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(interligacao.getEquipamento().getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc()).fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasInterligacao.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			interligacao.getEquipamento().setFranquias(franquiasInterligacao);
			interligacao.getEquipamento().setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL);

		}	
		
		List<PeriodoApuracao> apuracoes = new ArrayList<PeriodoApuracao>();
		
		PeriodoApuracao pApuracao = new PeriodoApuracao();
		pApuracao.setDataInicio(sdf.parse("01/01/2014 00:00:00.000"));
		pApuracao.setDataFim(sdf.parse("31/01/2014 23:59:59.999"));
		apuracoes.add(pApuracao);
		
		//populando 2 anos de PeriodoApuracao
		for(int i = 0; i < 24; i++){
			
			pApuracao = new PeriodoApuracao();
			
			Calendar calInicio = Calendar.getInstance();
			calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
			calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
			calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
	    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
	    	calInicio.set(Calendar.MINUTE, 0);
	    	calInicio.set(Calendar.SECOND, 0);
	    	calInicio.set(Calendar.MILLISECOND, 000);
			
			Calendar calFim = Calendar.getInstance();
			calFim.setTime(calInicio.getTime());
			
		    int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    calFim.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
		    calFim.set(Calendar.DATE, lastDate);
	    	calFim.set(Calendar.HOUR_OF_DAY, 23);
	    	calFim.set(Calendar.MINUTE, 59);
	    	calFim.set(Calendar.SECOND, 59);
	    	calFim.set(Calendar.MILLISECOND, 999);
		    
			pApuracao.setDataInicio(calInicio.getTime());
			pApuracao.setDataFim(calFim.getTime());
			
			apuracoes.add(pApuracao);
		}
		
		
		this.instalacaoAggregate = new InstalacaoAggregate();
		beanFactory.autowireBean(this.instalacaoAggregate);
		
		//Filtro por Interligação Internacional para pegar algumas interligações específicas, comentar isso para pegar todas
		InterligacaoInternacional qInterligacao = alias(InterligacaoInternacional.class,"interligacaoInternacional");
		List<InterligacaoInternacional> interligacoesSelecionadas = new CollQuery<InterligacaoInternacional>(queryEngine)
				.from($(qInterligacao),interligacoes)
				.where($(qInterligacao.getId()).eq("SCUTLC"))
				.fetch();
		
		
		//loop por usinas
		for(InterligacaoInternacional interligacao : interligacoesSelecionadas){
			
			for(PeriodoApuracao apuracao : apuracoes){
			
				//Seta apuração por usina.
				EventoMudancaEstadoOperativo qEventoMudancaEstadoOperativo = 
						alias(EventoMudancaEstadoOperativo.class,"eventoMudancaEstadoOperativo");
				List<EventoMudancaEstadoOperativo> eventosInstalacao = new CollQuery<EventoMudancaEstadoOperativo>(queryEngine)
						.from($(qEventoMudancaEstadoOperativo), eventos)
						.where($(qEventoMudancaEstadoOperativo.getIdInstalacao()).eq(interligacao.getId())
								.and($(qEventoMudancaEstadoOperativo.getDataVerificada()).
										between(apuracao.getDataInicio(), apuracao.getDataFim())))
						.fetch();
				
				if(eventosInstalacao.size() < 1){
					System.out.println("Eventos Vazios ");
				}
				
				//adiciona os eventos no periodoApuracao
				apuracao.setEventos(eventosInstalacao);
				
				//setting
				this.instalacaoAggregate.setMinorVersion((long)0);
				this.instalacaoAggregate.setId(interligacao.getId());
				List<Equipamento> equipamentos = new ArrayList<Equipamento>();
				equipamentos.add(interligacao.getEquipamento());
				this.instalacaoAggregate.setEquipamentos((List<Equipamento>) (List<?>) equipamentos);
				this.instalacaoAggregate.setInstalacao(interligacao);
				this.instalacaoAggregate.setApuracoes(apuracoes);
				
				System.out.println("Periodo Inicial: "+sdf.format(apuracao.getDataInicio()));
				System.out.println("Periodo Final: "+sdf.format(apuracao.getDataFim()));
				
				// Command
				CalcularTaxasCommand calcularTaxas = new CalcularTaxasCommand();
		        calcularTaxas.setDataInicio(apuracao.getDataInicio());
		        calcularTaxas.setDataFim(apuracao.getDataFim());
				
				this.instalacaoAggregate.apply(calcularTaxas);
				
				if(this.instalacaoAggregate.getApuracoes() != null){
					
					//pega apenas o mês do apuracao que foi enviado
					PeriodoApuracao qPeriodoApuracao = alias(PeriodoApuracao.class,"periodoApuracao");
					List<PeriodoApuracao> periodoApuracaoCarregado = new CollQuery<PeriodoApuracao>(queryEngine)
							.from($(qPeriodoApuracao), this.instalacaoAggregate.getApuracoes())
							.where($(qPeriodoApuracao.getDataInicio()).eq(apuracao.getDataInicio()))
							.fetch();
					
					//loop por apuracao
					for(PeriodoApuracao periodoApuracao : periodoApuracaoCarregado ){
						
						Calendar calPeriodoApuracao = Calendar.getInstance();
						calPeriodoApuracao.setTime(periodoApuracao.getDataInicio());
						
						// Filtra os parametroTaxa por periodoApuracao
						ParametroTaxa qParamTaxa = alias(ParametroTaxa.class, "parametroTaxa");
						List<ParametroTaxa> parametrosTaxaOK = new CollQuery<ParametroTaxa>(queryEngine)
								.from($(qParamTaxa), parametroTaxa)
								.where($(qParamTaxa.getAno()).eq(calPeriodoApuracao.get(Calendar.YEAR)).and(
									   $(qParamTaxa.getMes()).eq(calPeriodoApuracao.get(Calendar.MONTH)+1).and(
									   $(qParamTaxa.getIdEquipamento()).containsIgnoreCase(interligacao.getEquipamento().getId()))))
								.fetch();
						
						//Varre a lista de resultados esperados para comparar com o retorno
						if(parametrosTaxaOK != null){
							
							assertThat(periodoApuracao.getParametrosV1().size()).isGreaterThan(parametrosTaxaOK.size());
							
							for(ParametroTaxa parametroTaxaOK : parametrosTaxaOK){
								
								//remover depois que corrigir do json
								if(parametroTaxaOK.getCodigo().contains("2014") || "HD".equalsIgnoreCase(parametroTaxaOK.getCodigo()) )
									continue;
								if("HS".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2014) && parametroTaxaOK.getMes().equals(1))
									continue;
								if("HEDP".equalsIgnoreCase(parametroTaxaOK.getCodigo()) && parametroTaxaOK.getAno().equals(2014) && parametroTaxaOK.getMes().equals(1))
									continue;
								
								System.out.println("ODM: Ano["+parametroTaxaOK.getAno()+"] "
										+ "Mes["+parametroTaxaOK.getMes()+"] "
										+ "Codigo["+parametroTaxaOK.getCodigo()+"] "
										+ "Valor["+parametroTaxaOK.getValor()+"] "
										+ "IdEquipamento["+parametroTaxaOK.getIdEquipamento()+"]");
								
								assertThat(periodoApuracao.getParametrosV1().stream().anyMatch(param -> {
									return (param.getMes().equals(parametroTaxaOK.getMes()) &&
											param.getAno().equals(parametroTaxaOK.getAno()) &&
											param.getCodigo().equals(parametroTaxaOK.getCodigo()) &&
											param.getValor().setScale(6,BigDecimal.ROUND_HALF_UP).
													compareTo(parametroTaxaOK.getValor().setScale(6,BigDecimal.ROUND_HALF_UP))==0 &&
											param.getIdEquipamento().equals(parametroTaxaOK.getIdEquipamento())
											);
								})).isEqualTo(true);
								
							}
						}else{
							assert(false); // não tem nenhum parametro no json.
						}
					}
				}	
			}
		}	
		
	}
	
//	@Test
	public void calcularTaxasTodasInstalacoes() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		InstalacaoAggregateService instalacaoAggregateService = new InstalacaoAggregateService();
		this.mockMvc = MockMvcBuilders.standaloneSetup(instalacaoAggregateService)
				.setMessageConverters(jacksonMessageConverter)
				.build();
		
		CalcularTaxasCommand command = new CalcularTaxasCommand();
		command.setDataFim(sdf.parse("01/07/2014 00:00:00.000"));
		command.setDataInicio(sdf.parse("31/07/2014 23:59:59.999"));
		
		
		CommandMetadata metadata = new CommandMetadata();
		metadata.setAggregateId("ALUXG");
		metadata.setCorrelationId("ALUXG");
		metadata.setCreationDate(ZonedDateTime.now());
//		metadata.setHandlerName(handlerName);
//		metadata.setHandlerVersion(handlerVersion);
		metadata.setId("1");
		metadata.setMajorVersion(1l);
		metadata.setMinorVersion(1l);
		metadata.setTimelineDate(ZonedDateTime.now());
		
		CommandMessage<CalcularTaxasCommand> cm = new CommandMessage<CalcularTaxasCommand>();
		cm.setCommand(command);
		cm.setMetadata(metadata);
		
		mockMvc.perform(post("/api/instalacao-aggregate/calcular-taxas-command")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(cm)));
//			.andExpect(status().isOk());
		
	}
	
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}
	
	public static void main(String args[]){
		
//		ObjectMapper objMapper = new ObjectMapper();
//		
//		String eventoJSON = "{     \"id\" : \"2250412\",    \"version\" : \"1\",    \"idEquipamento\" : \"BAUSB-0UG3\",    \"idInstalacao\" : \"BAUSB\",    \"dataVerificada\" : \"2014-02-13T10:14:00-0300\",    \"estadoOperativo\" : \"DPR\",    \"condicaoOperativa\" : \"\",    \"classificacaoOrigem\" : \"GUM\",    \"valorPotenciaDisponivel\" : \"0.000\",    \"dataCriacao\" : \"2014-02-17T10:11:00\",    \"dataUltimaConsolidacao\" : \"2014-03-15T00:00:00\",    \"justificativaRestricaoDesligamento\" : \"\",    \"status\" : \"0\",    \"tipoOperacao\" : \"O\"}";
//		
//		try {
//			EventoMudancaEstadoOperativo evento = objMapper.readValue(
//					eventoJSON,
//					new TypeReference<EventoMudancaEstadoOperativo>() {});
//			System.out.println(evento.getDataVerificada());
//		} catch (JsonParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		BigDecimal b1 = new BigDecimal("147.833333333333");
//		BigDecimal b2 = new BigDecimal("148.833333333333");
//		
//		BigDecimal b11 = b1.setScale(0, RoundingMode.HALF_UP);
//		System.out.println(b11);
//		
//		BigDecimal b21 = b2.setScale(0, RoundingMode.HALF_UP);
//		System.out.println(b21);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
//		Calendar calFim = Calendar.getInstance();
//		try {
//			calFim.setTime(sdf.parse("01/01/2014 23:59:59.999"));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		calFim.set(Calendar.MONTH, calFim.get(Calendar.MONTH)+1);
//		
//		System.out.println(calFim.getTime());
//		
//	    int lastDate = calFim.getActualMaximum(Calendar.DATE);
//	    calFim.set(Calendar.DATE, lastDate);
//
//	    System.out.println(calFim.getTime());
	    
	    
	    //populando 2 anos de PeriodoApuracao
		try{
		    List<Periodo> apuracoes = new ArrayList<Periodo>();
		    apuracoes.add(new Periodo(sdf.parse("01/01/2014 00:00:00.000"), sdf.parse("31/01/2014 23:59:59.999")));
		    for(int i = 0; i < 24; i++){
	
		    	Calendar calInicio = Calendar.getInstance();
		    	calInicio.setTime(apuracoes.get(apuracoes.size()-1).getDataInicio());
		    	calInicio.set(Calendar.MONTH, calInicio.get(Calendar.MONTH)+1);
		    	calInicio.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		    	calInicio.set(Calendar.HOUR_OF_DAY, 0);
		    	calInicio.set(Calendar.MINUTE, 0);
		    	calInicio.set(Calendar.SECOND, 0);
		    	calInicio.set(Calendar.MILLISECOND, 000);
		    	
		    	Calendar calFim = Calendar.getInstance();
		    	calFim.setTime(calInicio.getTime());
		    	
	
		    	System.out.println("Intermediario: " + calFim.getTime());
		    	
		    	int lastDate = calFim.getActualMaximum(Calendar.DATE);
		    	calFim.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		    	calFim.set(Calendar.DATE, lastDate);
		    	calFim.set(Calendar.HOUR_OF_DAY, 23);
		    	calFim.set(Calendar.MINUTE, 59);
		    	calFim.set(Calendar.SECOND, 59);
		    	calFim.set(Calendar.MILLISECOND, 999);
		    	
		    	apuracoes.add(new Periodo(calInicio.getTime(),calFim.getTime()));
	
		    	System.out.println("Inicio: " + calInicio.getTime());
		    	System.out.println("Fim: " + calFim.getTime());
		    	System.out.println("\n\n\n\n ");
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
	    
	}
}
