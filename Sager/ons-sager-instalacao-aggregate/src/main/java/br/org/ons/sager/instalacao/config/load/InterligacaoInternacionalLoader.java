package br.org.ons.sager.instalacao.config.load;

import static com.querydsl.core.alias.Alias.$;
import static com.querydsl.core.alias.Alias.alias;

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

import br.org.ons.geracao.cadastro.Equipamento;
import br.org.ons.geracao.cadastro.InterligacaoInternacional;
import br.org.ons.geracao.cadastro.TipoInstalacao;
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
 * Carrega objetos do tipo interligação internacional (com suas apurações),
 * enviando comandos para o serviço
 */
@Configuration
@Profile("load")
public class InterligacaoInternacionalLoader {

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
	public InterligacaoInternacionalLoader(ObjectMapper objectMapper, QueryEngine queryEngine,
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
			// Carregando interligações (com equipamento incluso)
			loadInterligacoesInternacionais();

			// Carregando eventos
			loadEventosMudancaEstadoOperativo();
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		} finally {
			SecurityContextHolder.clearContext();
		}
	}

	private void loadInterligacoesInternacionais() throws IOException {
		List<InterligacaoInternacional> interligacoes = objectMapper.readValue(
				getClass().getResourceAsStream("/load/InterligacaoInternacional.json"),
				new TypeReference<List<InterligacaoInternacional>>() {
				});
		List<Franquia> franquias = objectMapper.readValue(getClass().getResourceAsStream("/load/Franquia.json"),
				new TypeReference<List<Franquia>>() {
				});
		for (InterligacaoInternacional interligacao : interligacoes) {
			Equipamento equipamento = interligacao.getEquipamento();
			interligacao.setEquipamento(null);
			
			// Cadastrando instalação
			CadastrarInstalacaoCommand instalacaoCommand = new CadastrarInstalacaoCommand();
			instalacaoCommand.setInstalacao(interligacao);

			usinaVersions.put(interligacao.getId(), 0L);

			CommandMessage<CadastrarInstalacaoCommand> instalacaoMessage = new CommandMessage<>();
			instalacaoMessage.setCommand(instalacaoCommand);
			instalacaoMessage.getMetadata().setId(IdGenerator.newId());
			instalacaoMessage.getMetadata().setAggregateId(interligacao.getId());
			instalacaoMessage.getMetadata().setMinorVersion(usinaVersions.get(interligacao.getId()));
			instalacaoMessage.getMetadata().setTimelineDate(DATA_INICIO);
			instalacaoMessage.getMetadata().putProperty(PRINCIPAL, ADMIN);
			instalacaoMessage.getMetadata().putProperty(AUTHORITIES, ADMIN);

			service.cadastrarInstalacao(instalacaoMessage);

			usinaVersions.put(interligacao.getId(), usinaVersions.get(interligacao.getId()) + 1);

			// Cadastrando equipamento que representa a instalação
			CadastrarEquipamentoCommand equipamentoCommand = new CadastrarEquipamentoCommand();
			equipamento.setIdInstalacao(interligacao.getId());
			equipamento.setNome(interligacao.getNomeCurto());
			equipamento.setVersao(interligacao.getVersao());
			equipamento.setTipoInstalacao(TipoInstalacao.INTERLIGACAO_INTERNACIONAL);

			// Filtra as franquias pertencentes à unidade geradora
			Franquia qFq = alias(Franquia.class, "franquia");
			List<Franquia> franquiasInterligacao = new CollQuery<Franquia>(queryEngine).from($(qFq), franquias)
					.where($(qFq.getIdEquipamento()).eq(interligacao.getId()))
					.orderBy($(qFq.getCodigo()).asc(), $(qFq.getPeriodoVigencia().getDataInicio()).asc())
					.fetch();
			// Ajustando data fim de vigência para último milissegundo do dia
			franquiasInterligacao.forEach(franquia -> {
				if (franquia.getPeriodoVigencia().getDataFim() != null)
					franquia.getPeriodoVigencia().setDataFim(Date.from(ZonedDateTime
							.ofInstant(franquia.getPeriodoVigencia().getDataFim().toInstant(), ZoneId.systemDefault())
							.plusDays(1).minusNanos(MILISSEGUNDO).toInstant()));
			});
			equipamento.setFranquias(franquiasInterligacao);
			
			equipamentoCommand.setEquipamento(equipamento);

			CommandMessage<CadastrarEquipamentoCommand> equipamentoMessage = new CommandMessage<>();
			equipamentoMessage.setCommand(equipamentoCommand);
			equipamentoMessage.getMetadata().setId(IdGenerator.newId());
			equipamentoMessage.getMetadata().setAggregateId(equipamento.getIdInstalacao());
			equipamentoMessage.getMetadata().setMinorVersion(usinaVersions.get(equipamento.getIdInstalacao()));
			equipamentoMessage.getMetadata().setTimelineDate(
					DATA_INICIO.plusNanos(MILISSEGUNDO * usinaVersions.get(equipamento.getIdInstalacao())));
			equipamentoMessage.getMetadata().putProperty(PRINCIPAL, ADMIN);
			equipamentoMessage.getMetadata().putProperty(AUTHORITIES, ADMIN);

			service.cadastrarEquipamento(equipamentoMessage);
			usinaVersions.put(equipamento.getIdInstalacao(),
					usinaVersions.get(equipamento.getIdInstalacao()) + 1);
		}
	}

	private void loadEventosMudancaEstadoOperativo() throws IOException {
		List<EventoMudancaEstadoOperativo> eventos = objectMapper.readValue(
				getClass().getResourceAsStream("/load/EventoInterligacaoInternacional.json"),
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