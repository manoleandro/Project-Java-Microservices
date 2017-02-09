package br.org.ons.sager.instalacao.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.org.ons.geracao.evento.Disponibilidade;
import br.org.ons.geracao.evento.TipoDisponibilidade;
import br.org.ons.platform.common.CommandMessage;
import br.org.ons.platform.common.util.IdGenerator;
import br.org.ons.sager.instalacao.OnsSagerInstalacaoAggregateApp;
import br.org.ons.sager.instalacao.command.CalcularDisponibilidadesCommand;
import br.org.ons.sager.instalacao.security.AuthoritiesConstants;
import br.org.ons.sager.regra.parameters.CalcularDisponibilidadeResponse;

/**
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OnsSagerInstalacaoAggregateApp.class)
@WebAppConfiguration
@IntegrationTest
public class InstalacaoAggregateServiceIntTest {

	public static final DateTimeFormatter DATE_TIME_FORMAT =
	        DateTimeFormatter.ofPattern("yyyy-MM-dd' 'HH:mm:ss").withZone(ZoneId.of("Z"));
	
	@Inject
	private InstalacaoAggregateService instalacao;

	@Before
	public void setup() {
		Collection<? extends GrantedAuthority> authorities = Arrays
				.asList(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(new User("admin", "", authorities), "", authorities));
	}

//	@Test
	public void calcularDisponibilidades() throws Exception {
		List<Disponibilidade> dispList = new ArrayList<>();
		
		List<String> ugs = Arrays.asList("CEUTFO0UG1", "CEUTFO0UG2", "CEUTFO0UG3");
		// Enviando comandos para cada unidade geradora
		for (String ug : ugs) {
			CalcularDisponibilidadesCommand command = new CalcularDisponibilidadesCommand();
			command.setDataInicio(Date.from(Instant.from(ZonedDateTime.of(2003, 12, 1, 0, 0, 0, 0, ZoneOffset.UTC))));
			command.setDataFim(Date.from(Instant.from(ZonedDateTime.of(2003, 12, 1, 0, 0, 0, 0, ZoneOffset.UTC).plusMonths(1L)
					.minusNanos(1L))));
			command.setTiposDisponibilidade(Arrays.asList(TipoDisponibilidade.OPERACIONAL, TipoDisponibilidade.COMERCIAL,
					TipoDisponibilidade.ELETROMECANICA));
			command.setIdUnidadeGeradora(ug);
			
			CommandMessage<CalcularDisponibilidadesCommand> message = new CommandMessage<>();
			message.getMetadata().setId(IdGenerator.newId());
			message.getMetadata().setAggregateId("CEUTFO");
			message.getMetadata().setTimelineDate(ZonedDateTime.now());
			message.getMetadata().setMinorVersion(0L);
			message.setCommand(command);
			
			CalcularDisponibilidadeResponse response = instalacao.calcularDisponibilidades(message).getBody().getResults().get(0);
			dispList.addAll(response.getDisponibilidades());
		}
		
		// Imprimindo resultados para importação no Excel		
		dispList.sort(Comparator.comparing(Disponibilidade::getDataInicio).thenComparing(Disponibilidade::getIdEquipamento)
				.thenComparing(Disponibilidade::getTipoDisponibilidade));
		
		MultiValueMap<Date, MultiValueMap<String, Disponibilidade>> dispMap = new LinkedMultiValueMap<>();
		dispList.forEach(disp -> {
			MultiValueMap<String, Disponibilidade> dateMap = dispMap.getFirst(disp.getDataInicio());
			if (dateMap == null) {
				dateMap = new LinkedMultiValueMap<>();
				dispMap.add(disp.getDataInicio(), dateMap);
			}
			dateMap.add(disp.getIdEquipamento(), disp);
		});
		
		System.out.print("\t");
		for (String idEquipamento : ugs) {
			System.out.print(idEquipamento);
			System.out.print("\t\t\t");
		}
		System.out.println("");
		System.out.print("\t");
		for (String idEquipamento : ugs) {
			System.out.print("O\tC\tE\t");
		}
		System.out.println("");
		for (Date date : dispMap.keySet()) {
			MultiValueMap<String, Disponibilidade> dateMap = dispMap.getFirst(date);
			System.out.print(DATE_TIME_FORMAT.format(date.toInstant()));
			System.out.print("\t");
			for (String idEquipamento: dateMap.keySet()) {
				for (Disponibilidade disp : dateMap.get(idEquipamento)) {
					System.out.print(disp.getValor());
					System.out.print("\t");
				}
			}
			System.out.println("");
		}
	}
	
	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}
}
