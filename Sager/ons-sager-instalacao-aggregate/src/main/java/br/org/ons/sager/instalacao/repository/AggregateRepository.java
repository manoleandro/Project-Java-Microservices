package br.org.ons.sager.instalacao.repository;


import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.sager.instalacao.security.jwt.TokenProvider;
import br.org.ons.sager.instalacao.service.Aggregate;

@Repository
public class AggregateRepository<T extends Aggregate> {

	@Value("${onsplatform.url}")
	private String platformEndpoint;

	@Inject
	private RestTemplate restTemplate;

	@Inject
	private TokenProvider tokenProvider;

	private Class<T> aggregateClass;

	private <B> HttpEntity<B> requestEntity(B body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION,
				"Bearer " + tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
		return new HttpEntity<>(body, headers);
	}

	public T createMain(String aggregateId, ZonedDateTime startDate) {
		Assert.notNull(aggregateId);
		StringBuilder url = new StringBuilder(platformEndpoint);
		Map<String, Object> variables = new HashMap<>();
		url.append("/api/es-repository/main?aggregateId={aggregateId}&aggregateType={aggregateType}");
		variables.put("aggregate" + "Id", aggregateId);
		variables.put("aggregateType", aggregateClass.getName());
		Optional.ofNullable(startDate).ifPresent(result -> {
			url.append("&startDate={startDate}");
			variables.put("startDate", result.format(DateTimeFormatter.ISO_DATE_TIME));
		});
		return restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity(null), aggregateClass, variables)
				.getBody();
	}

	@SuppressWarnings("unchecked")
	public List<T> checkOutLatestSnapshotBeforeDate(String aggregateId, Long majorVersion, ZonedDateTime beforeDate) {
		Assert.notNull(aggregateId);
		StringBuilder url = new StringBuilder(platformEndpoint);
		Map<String, Object> variables = new HashMap<>();
		url.append("/api/es-repository/checkout?aggregateId={aggregateId}");
		variables.put("aggregateId", aggregateId);
		Optional.ofNullable(majorVersion).ifPresent(result -> {
			url.append("&majorVersion={majorVersion}");
			variables.put("majorVersion", majorVersion);
		});
		Optional.ofNullable(beforeDate).ifPresent(result -> {
			url.append("&beforeDate={beforeDate}");
			variables.put("beforeDate", result.format(DateTimeFormatter.ISO_DATE_TIME));
		});
		try {
			return Arrays.asList((T[]) restTemplate.exchange(url.toString(), HttpMethod.POST, requestEntity(null),
					Class.forName("[L" + aggregateClass.getName() + ";"), variables).getBody());
		} catch (ClassNotFoundException e) {
			throw new OnsRuntimeException(e);
		}
	}

	public List<Event> getUpdatesAfterMinorVersionBeforeDate(String aggregateId, Long majorVersion, Long minorVersion,
			ZonedDateTime beforeDate) {
		Assert.notNull(aggregateId);
		Assert.notNull(minorVersion);
		StringBuilder url = new StringBuilder(platformEndpoint);
		Map<String, Object> variables = new HashMap<>();
		url.append("/api/es-repository/updates?aggregateId={aggregateId}");
		variables.put("aggregateId", aggregateId);
		Optional.ofNullable(majorVersion).ifPresent(result -> {
			url.append("&majorVersion={majorVersion}");
			variables.put("majorVersion", majorVersion);
		});
		url.append("&minorVersion={minorVersion}");
		variables.put("minorVersion", minorVersion);
		Optional.ofNullable(beforeDate).ifPresent(result -> {
			url.append("&beforeDate={beforeDate}");
			variables.put("beforeDate", result.format(DateTimeFormatter.ISO_DATE_TIME));
		});
		return Arrays.asList(restTemplate.exchange(url.toString(), HttpMethod.GET,
				requestEntity(null), Event[].class, variables).getBody());
	}

	@SuppressWarnings("unchecked")
	public void checkIn(Command command, CommandMetadata metadata, List<T> aggregates) {
		Assert.notNull(command);
		Assert.notNull(metadata);
		Assert.notNull(aggregates);
		Assert.notEmpty(aggregates);
		restTemplate.exchange(platformEndpoint + "/api/es-repository/checkin", HttpMethod.POST,
				requestEntity(new Commit(command, metadata, (List<Aggregate>) aggregates)), Void.class,
				new HashMap<String, Object>());
	}

	public void setAggregateClass(Class<T> aggregateClass) {
		this.aggregateClass = aggregateClass;
	}

	public static class Commit {
		private Command command;
		private CommandMetadata metadata;
		private List<Aggregate> aggregates;

		public Commit(Command command, CommandMetadata metadata, List<Aggregate> aggregates) {
			this.command = command;
			this.metadata = metadata;
			this.aggregates = aggregates;
		}

		public Command getCommand() {
			return command;
		}

		public void setCommand(Command command) {
			this.command = command;
		}

		public CommandMetadata getMetadata() {
			return metadata;
		}

		public void setMetadata(CommandMetadata metadata) {
			this.metadata = metadata;
		}

		public List<Aggregate> getAggregates() {
			return aggregates;
		}

		public void setAggregates(List<Aggregate> aggregates) {
			this.aggregates = aggregates;
		}
	}
}