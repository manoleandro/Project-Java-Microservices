package br.org.ons.platform.scheduler;

import java.io.IOException;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.ons.platform.bus.CommandBus;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.platform.domain.model.GenericCommand;
import br.org.ons.platform.security.jwt.TokenProvider;

/**
 * Job agendado que, quando disparado, envia um comando para a fila do
 * barramento
 */
public class SendCommandJob implements Job {

	private static final Logger LOG = LoggerFactory.getLogger(SendCommandJob.class);

	@Autowired
	private CommandBus commandBus;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		LOG.debug("Executing SendCommandJob");
		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		SecurityContextHolder.getContext()
				.setAuthentication(tokenProvider.getAuthentication(jobDataMap.getString("token")));

		List<CommandMessage<GenericCommand>> commands;
		try {
			commands = objectMapper.readValue(jobDataMap.getString("commands"),
					new TypeReference<List<CommandMessage<GenericCommand>>>() {
					});
		} catch (IOException e) {
			throw new OnsRuntimeException(e);
		}
		for (CommandMessage<GenericCommand> command : commands) {
			LOG.debug("Sending message {}", command);
			commandBus.sendAndWait(command);
		}
	}
}