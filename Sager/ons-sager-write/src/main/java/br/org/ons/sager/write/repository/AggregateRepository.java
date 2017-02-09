package br.org.ons.sager.write.repository;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.sager.write.security.jwt.TokenProvider;
import br.org.ons.sager.write.service.Aggregate;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.CommandMetadata;
import br.org.ons.platform.common.Event;

@Repository
public class AggregateRepository<T extends Aggregate> {

	// FIXME
	private static final String PLATFORM_ENDPOINT = "http://localhost/onsplatform";

	private RestTemplate restTemplate = new RestTemplate();

	private Class<T> aggregateClass;

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private TokenProvider tokenProvider;

	@PostConstruct
	private void initRestTemplate() {
		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(objectMapper);
		converters.add(jsonConverter);
		restTemplate.setMessageConverters(converters);
	}

	private <B> HttpEntity<B> requestEntity(B body) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization",
				"Bearer " + tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), false));
		return new HttpEntity<B>(body, headers);
	}

	public T createMain(String aggregateId, ZonedDateTime startDate) {
		Assert.notNull(aggregateId);
		String params = "aggregateId=" + aggregateId + "&aggregateType=" + aggregateClass.getName();
		if (startDate != null) {
			params = params + "&startDate=" + startDate.format(DateTimeFormatter.ISO_DATE_TIME);
		}
		return restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/main?" + params, HttpMethod.POST,
				requestEntity(null), aggregateClass).getBody();
	}

	public Version createBranchFromMain(String aggregateId, ZonedDateTime branchStartDate, String branchName) {
		Assert.notNull(aggregateId);
		Assert.notNull(branchStartDate);
		String params = "aggregateId=" + aggregateId + "&branchStartDate="
				+ branchStartDate.format(DateTimeFormatter.ISO_DATE_TIME);
		if (branchName != null) {
			params = params + "&branchName=" + branchName;
		}
		return restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/branch?" + params, HttpMethod.POST,
				requestEntity(null), Version.class).getBody();
	}

	public void mergeBranchIntoMain(String aggregateId, Long majorVersion) {
		Assert.notNull(aggregateId);
		Assert.notNull(majorVersion);
		String params = "aggregateId=" + aggregateId + "&majorVersion=" + majorVersion;
		restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/merge?" + params, HttpMethod.POST,
				requestEntity(null), Void.class);
	}

	@SuppressWarnings("unchecked")
	public List<T> checkOutLatestSnapshotBeforeDate(String aggregateId, Long majorVersion, ZonedDateTime beforeDate) {
		Assert.notNull(aggregateId);
		Assert.notNull(majorVersion);
		String params = "aggregateId=" + aggregateId + "&majorVersion=" + majorVersion;
		if (beforeDate != null) {
			params = params + "&beforeDate=" + beforeDate.format(DateTimeFormatter.ISO_DATE_TIME);
		}
		try {
			return Arrays.asList((T[]) restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/checkout?" + params,
					HttpMethod.POST, requestEntity(null), Class.forName("[L" + aggregateClass.getName() + ";"))
					.getBody());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Event> getUpdatesAfterMinorVersionBeforeDate(String aggregateId, Long majorVersion, Long minorVersion,
			ZonedDateTime beforeDate) {
		Assert.notNull(aggregateId);
		Assert.notNull(minorVersion);
		String params = "aggregateId=" + aggregateId;
		if (majorVersion != null) {
			params = params + "&majorVersion=" + majorVersion;
		}
		params = params + "&minorVersion=" + minorVersion;
		if (beforeDate != null) {
			params = params + "&beforeDate=" + beforeDate.format(DateTimeFormatter.ISO_DATE_TIME);
		}
		return Arrays.asList(restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/updates?" + params,
				HttpMethod.GET, requestEntity(null), Event[].class).getBody());
	}

	@SuppressWarnings("unchecked")
	public void checkIn(Command command, CommandMetadata metadata, List<T> aggregates) {
		Assert.notNull(command);
		Assert.notNull(metadata);
		Assert.notNull(aggregates);
		Assert.notEmpty(aggregates);
		restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/checkin", HttpMethod.POST,
				requestEntity(new Commit(command, metadata, (List<Aggregate>) aggregates)), Void.class);
	}

	public List<CommandMessage<?>> getCommitsAfterMinorVersion(String aggregateId, Long majorVersion,
			Long minorVersion) {
		Assert.notNull(aggregateId);
		Assert.notNull(minorVersion);
		String params = "aggregateId=" + aggregateId;
		if (majorVersion != null) {
			params = params + "&majorVersion=" + majorVersion;
		}
		params = params + "&minorVersion=" + minorVersion;
		return Arrays.asList(restTemplate.exchange(PLATFORM_ENDPOINT + "/api/es-repository/commits?" + params,
				HttpMethod.GET, requestEntity(null), CommandMessage[].class).getBody());
	}

	public void setAggregateClass(Class<T> aggregateClass) {
		this.aggregateClass = aggregateClass;
	}

	static class Commit {
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
	
	static class Version {
		private Long majorVersion;
		private Long minorVersion;

		public Long getMajorVersion() {
			return majorVersion;
		}

		public void setMajorVersion(Long majorVersion) {
			this.majorVersion = majorVersion;
		}

		public Long getMinorVersion() {
			return minorVersion;
		}

		public void setMinorVersion(Long minorVersion) {
			this.minorVersion = minorVersion;
		}

	}
}