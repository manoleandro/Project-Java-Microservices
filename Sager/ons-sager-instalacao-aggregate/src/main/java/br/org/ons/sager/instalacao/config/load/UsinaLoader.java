package br.org.ons.sager.instalacao.config.load;

import static com.querydsl.core.alias.Alias.*;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.collections.CollQuery;
import com.querydsl.collections.QueryEngine;

import br.org.ons.geracao.cadastro.PotenciaCalculo;
import br.org.ons.geracao.cadastro.UnidadeGeradora;
import br.org.ons.geracao.cadastro.Usina;
import br.org.ons.geracao.evento.EventoMudancaEstadoOperativo;
import br.org.ons.geracao.evento.franquia.Franquia;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.command.ApurarEventosCommand;
import br.org.ons.sager.instalacao.command.CadastrarEquipamentoCommand;
import br.org.ons.sager.instalacao.command.CadastrarInstalacaoCommand;
import br.org.ons.sager.instalacao.security.AuthoritiesConstants;
import br.org.ons.sager.instalacao.service.InstalacaoAggregateService;

/**
 * Carrega objetos do tipo usina (com suas unidades geradoras e apurações),
 * enviando comandos para o serviço
 */
@Configuration
@Profile("load")
public class UsinaLoader {
	
	private static final String ADMIN = "admin";
	private static final String PRINCIPAL = "principal";
	private static final String AUTHORITIES = "authorities";

	private static final ZonedDateTime DATA_INICIO = ZonedDateTime.of(2001, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	
	private static final int MILISSEGUNDO = 1000000;

	private ObjectMapper objectMapper;

	private QueryEngine queryEngine;

	private InstalacaoAggregateService service;

	private Map<String, Long> usinaVersions = new HashMap<>();

	@Inject
	public UsinaLoader(ObjectMapper objectMapper, QueryEngine queryEngine,
			InstalacaoAggregateService service) {
		this.objectMapper = objectMapper;
		this.queryEngine = queryEngine;
		this.service = service;
	}

	@PostConstruct
	public void load() {
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(new User(ADMIN, "", authorities), "", authorities));
		try {
			// Carregando usinas
			loadUsinas();

			// Carregando unidades geradoras (com suspensões)
			loadUnidadesGeradoras();

			// Carregando eventos
			loadEventosMudancaEstadoOperativo();
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	private void loadUsinas() throws IOException {
		List<Usina> usinas = objectMapper.readValue(getClass().getResourceAsStream("/load/Usina.json"),
				new TypeReference<List<Usina>>() {
				});
		for (Usina usina : usinas) {
			CadastrarInstalacaoCommand command = new CadastrarInstalacaoCommand();
			command.setInstalacao(usina);

			usinaVersions.put(usina.getId(), 0L);

			CommandMessage<CadastrarInstalacaoCommand> message = new CommandMessage<>();
			message.setCommand(command);
			message.getMetadata().setId(IdGenerator.newId());
			message.getMetadata().setAggregateId(usina.getId());
			message.getMetadata().setMinorVersion(usinaVersions.get(usina.getId()));
			message.getMetadata().setTimelineDate(DATA_INICIO);
			message.getMetadata().putProperty(PRINCIPAL, ADMIN);
			message.getMetadata().putProperty(AUTHORITIES, ADMIN);

			service.cadastrarInstalacao(message);

			usinaVersions.put(usina.getId(), usinaVersions.get(usina.getId()) + 1);
		}
	}

	private void loadUnidadesGeradoras() throws IOException {
		List<UnidadeGeradora> unidadesGeradoras = objectMapper.readValue(
				getClass().getResourceAsStream("/load/UnidadeGeradora.json"),
				new TypeReference<List<UnidadeGeradora>>() {
				});
		List<PotenciaCalculo> potencias = objectMapper.readValue(
				getClass().getResourceAsStream("/load/PotenciaCalculo.json"),
				new TypeReference<List<PotenciaCalculo>>() {
				});
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		for (UnidadeGeradora unidadeGeradora : unidadesGeradoras) {
			// Filtra as potências pertencentes à unidade geradora
			PotenciaCalculo qPot = alias(PotenciaCalculo.class, "potcalc");
			List<PotenciaCalculo> potenciasUnidadeGeradora = new CollQuery<PotenciaCalculo>(queryEngine)
					.from($(qPot), potencias)
					.where($(qPot.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qPot.getVigencia().getDataInicio()).asc())
					.fetch();
			// Seta data fim de vigência = data inicio de vigência da próxima (menos 1 milissegundo)
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
			List<Franquia> franquiasUnidadeGeradora = new CollQuery<Franquia>(queryEngine)
					.from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(unidadeGeradora.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc())
					.fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasUnidadeGeradora.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			unidadeGeradora.setFranquias(franquiasUnidadeGeradora);
			
			CadastrarEquipamentoCommand command = new CadastrarEquipamentoCommand();
			command.setEquipamento(unidadeGeradora);

			CommandMessage<CadastrarEquipamentoCommand> message = new CommandMessage<>();
			message.setCommand(command);
			message.getMetadata().setId(IdGenerator.newId());
			message.getMetadata().setAggregateId(unidadeGeradora.getIdInstalacao());
			message.getMetadata().setMinorVersion(usinaVersions.get(unidadeGeradora.getIdInstalacao()));
			message.getMetadata().setTimelineDate(
					DATA_INICIO.plusNanos(MILISSEGUNDO * usinaVersions.get(unidadeGeradora.getIdInstalacao())));
			message.getMetadata().putProperty(PRINCIPAL, ADMIN);
			message.getMetadata().putProperty(AUTHORITIES, ADMIN);

			service.cadastrarEquipamento(message);
			usinaVersions.put(unidadeGeradora.getIdInstalacao(),
					usinaVersions.get(unidadeGeradora.getIdInstalacao()) + 1);
		}
	}

	private void loadEventosMudancaEstadoOperativo() throws IOException {
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoMudancaEstadoOperativo.json"),
				new TypeReference<List<EventoMudancaEstadoOperativo>>() {
				});
		eventos.sort(Comparator.comparing(EventoMudancaEstadoOperativo::getIdInstalacao));
		String idInstalacao = null;
		List<EventoMudancaEstadoOperativo> eventosInstalacao = null;
		for (EventoMudancaEstadoOperativo evento : eventos) {
			if (idInstalacao == null) {
				idInstalacao = evento.getIdInstalacao();
				eventosInstalacao = new ArrayList<>();
				eventosInstalacao.add(evento);
			} else if (evento.getIdInstalacao().equals(idInstalacao)) {
				eventosInstalacao.add(evento);
			} else {
				loadApuracoes(eventosInstalacao, idInstalacao);

				idInstalacao = evento.getIdInstalacao();
				eventosInstalacao = new ArrayList<>();
				eventosInstalacao.add(evento);
			}
		}
		loadApuracoes(eventosInstalacao, idInstalacao);
	}

	private void loadApuracoes(List<EventoMudancaEstadoOperativo> eventosInstalacao, String idInstalacao) {
		eventosInstalacao.sort(Comparator.comparing(EventoMudancaEstadoOperativo::getDataVerificada));
		Integer mes = null;
		Integer ano = null;
		List<EventoMudancaEstadoOperativo> eventosPeriodo = null;
		for (EventoMudancaEstadoOperativo evento : eventosInstalacao) {
			Calendar dataVerificada = Calendar.getInstance(TimeZone.getDefault());
			dataVerificada.setTime(evento.getDataVerificada());
			if (mes == null && ano == null) {
				mes = dataVerificada.get(Calendar.MONTH);
				ano = dataVerificada.get(Calendar.YEAR);
				eventosPeriodo = new ArrayList<>();
				eventosPeriodo.add(evento);
			} else if (dataVerificada.get(Calendar.MONTH) == mes && dataVerificada.get(Calendar.YEAR) == ano) {
				eventosPeriodo.add(evento);
			} else {
				loadApuracao(eventosPeriodo, idInstalacao, mes, ano);

				mes = dataVerificada.get(Calendar.MONTH);
				ano = dataVerificada.get(Calendar.YEAR);
				eventosPeriodo = new ArrayList<>();
				eventosPeriodo.add(evento);
			}
		}
		loadApuracao(eventosPeriodo, idInstalacao, mes, ano);
	}

	private void loadApuracao(List<EventoMudancaEstadoOperativo> eventosPeriodo, String idInstalacao, Integer mes,
			Integer ano) {
		ApurarEventosCommand command = new ApurarEventosCommand();
		command.setDataInicio(Date.from(ZonedDateTime.of(ano, mes + 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).toInstant()));
		command.setDataFim(Date.from(ZonedDateTime.of(ano, mes + 1, 1, 0, 0, 0, 0, ZoneId.systemDefault()).plusMonths(1L)
				.minusNanos(MILISSEGUNDO).toInstant()));
		command.setEventos(eventosPeriodo);

		CommandMessage<ApurarEventosCommand> message = new CommandMessage<>();
		message.setCommand(command);
		message.getMetadata().setId(IdGenerator.newId());
		message.getMetadata().setAggregateId(idInstalacao);
		message.getMetadata().setMinorVersion(usinaVersions.get(idInstalacao));
		message.getMetadata().setTimelineDate(
				ZonedDateTime.ofInstant(Instant.ofEpochMilli(command.getDataFim().getTime()), ZoneId.systemDefault()));
		message.getMetadata().putProperty(PRINCIPAL, ADMIN);
		message.getMetadata().putProperty(AUTHORITIES, ADMIN);
		service.apurarEventos(message);
		usinaVersions.put(idInstalacao, usinaVersions.get(idInstalacao) + 1);
	}
}