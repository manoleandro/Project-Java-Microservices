package br.org.ons.exemplo.service;

import static org.assertj.core.api.Assertions.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import br.org.ons.exemplo.common.Apuracao;
import br.org.ons.exemplo.common.ApurarEventosCommand;
import br.org.ons.exemplo.common.ApurarEventosCommandModification;
import br.org.ons.exemplo.common.AtualizarUsinaCommand;
import br.org.ons.exemplo.common.CriarUsinaCommand;
import br.org.ons.exemplo.common.Evento;
import br.org.ons.exemplo.common.ExcluirUsinaCommand;
import br.org.ons.exemplo.common.Usina;
import br.org.ons.exemplo.common.UsinaAtualizadaEvent;
import br.org.ons.exemplo.common.UsinaExcluidaEvent;
import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.ScenarioCommandModification;
import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Teste unitário para a lógica de negócio do UsinaAggregate e da superclasse Aggregate
 */
@RunWith(JUnit4.class)
public class UsinaAggregateUnitTest {

	private static final String DEFAULT_USINA_ID = "MTUSCU";
	private static final String DEFAULT_USINA_NOME_CURTO = "TUCURUÍ";
	private static final String DEFAULT_USINA_TIPO = "UTE";
	private static final double DEFAULT_USINA_POTENCIA_CALCULO = 100.0;

	private static final String UPDATED_USINA_ID = "RSUPME";
	private static final String UPDATED_USINA_NOME_CURTO = "P.MEDICI";
	private static final String UPDATED_USINA_TIPO = "UHE";
	private static final double UPDATED_USINA_POTENCIA_CALCULO = 200.0;

	private static final ZonedDateTime DEFAULT_DATA_INICIO = ZonedDateTime.of(2016, 1, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	private static final ZonedDateTime DEFAULT_DATA_FIM = ZonedDateTime.of(2016, 1, 31, 0, 0, 0, 0, ZoneId.systemDefault());
	private static final ZonedDateTime NEW_DATA_INICIO = ZonedDateTime.of(2016, 2, 1, 0, 0, 0, 0, ZoneId.systemDefault());
	private static final ZonedDateTime NEW_DATA_FIM = ZonedDateTime.of(2016, 2, 29, 0, 0, 0, 0, ZoneId.systemDefault());
	
	private UsinaAggregate usinaAggregate;

	@Before
	public void initUsinaAggregate() {
		usinaAggregate = new UsinaAggregate();
		usinaAggregate.setUsina(new Usina());
		usinaAggregate.getUsina().setId(DEFAULT_USINA_ID);
		usinaAggregate.getUsina().setNomeCurto(DEFAULT_USINA_NOME_CURTO);
		usinaAggregate.getUsina().setTipoUsina(DEFAULT_USINA_TIPO);
		usinaAggregate.getUsina().setPotenciaCalculo(DEFAULT_USINA_POTENCIA_CALCULO);

		Apuracao apuracao = new Apuracao();
		apuracao.setDataInicio(DEFAULT_DATA_INICIO);
		apuracao.setDataFim(DEFAULT_DATA_FIM);
		usinaAggregate.getApuracoes().add(apuracao);
	}
	
	@Test
	public void testCriarUsina() {
		// Resetting usinaAggregate
		usinaAggregate = new UsinaAggregate();
		
		CriarUsinaCommand criarUsina = new CriarUsinaCommand();
		criarUsina.setUsina(new Usina());
		criarUsina.getUsina().setId(DEFAULT_USINA_ID);
		criarUsina.getUsina().setNomeCurto(DEFAULT_USINA_NOME_CURTO);
		criarUsina.getUsina().setTipoUsina(DEFAULT_USINA_TIPO);
		criarUsina.getUsina().setPotenciaCalculo(DEFAULT_USINA_POTENCIA_CALCULO);

		usinaAggregate.apply(criarUsina);

		assertThat(usinaAggregate.getUsina()).isNotNull();
		assertThat(usinaAggregate.getUsina().getId()).isEqualTo(DEFAULT_USINA_ID);
		assertThat(usinaAggregate.getUsina().getNomeCurto()).isEqualTo(DEFAULT_USINA_NOME_CURTO);
		assertThat(usinaAggregate.getUsina().getTipoUsina()).isEqualTo(DEFAULT_USINA_TIPO);
		assertThat(usinaAggregate.getUsina().getPotenciaCalculo()).isEqualTo(DEFAULT_USINA_POTENCIA_CALCULO);
	}
	
	@Test(expected = OnsRuntimeException.class)
	public void testCriarUsinaFail() {
		CriarUsinaCommand criarUsina = new CriarUsinaCommand();
		criarUsina.setUsina(new Usina());
		criarUsina.getUsina().setId(DEFAULT_USINA_ID);
		criarUsina.getUsina().setNomeCurto(DEFAULT_USINA_NOME_CURTO);
		criarUsina.getUsina().setTipoUsina(DEFAULT_USINA_TIPO);
		criarUsina.getUsina().setPotenciaCalculo(DEFAULT_USINA_POTENCIA_CALCULO);
		
		usinaAggregate.apply(criarUsina);
	}
	
	@Test
	public void testAtualizarUsina() {
		AtualizarUsinaCommand atualizarUsina = new AtualizarUsinaCommand();
		atualizarUsina.setUsina(new Usina());
		atualizarUsina.getUsina().setId(UPDATED_USINA_ID);
		atualizarUsina.getUsina().setNomeCurto(UPDATED_USINA_NOME_CURTO);
		atualizarUsina.getUsina().setTipoUsina(UPDATED_USINA_TIPO);
		atualizarUsina.getUsina().setPotenciaCalculo(UPDATED_USINA_POTENCIA_CALCULO);

		usinaAggregate.apply(atualizarUsina);

		assertThat(usinaAggregate.getUsina()).isNotNull();
		assertThat(usinaAggregate.getUsina().getId()).isEqualTo(UPDATED_USINA_ID);
		assertThat(usinaAggregate.getUsina().getNomeCurto()).isEqualTo(UPDATED_USINA_NOME_CURTO);
		assertThat(usinaAggregate.getUsina().getTipoUsina()).isEqualTo(UPDATED_USINA_TIPO);
		assertThat(usinaAggregate.getUsina().getPotenciaCalculo()).isEqualTo(UPDATED_USINA_POTENCIA_CALCULO);
	}
	
	@Test(expected = OnsRuntimeException.class)
	public void testAtualizarUsinaFail() {
		// Resetting usinaAggregate
		usinaAggregate = new UsinaAggregate();
		
		AtualizarUsinaCommand atualizarUsina = new AtualizarUsinaCommand();
		atualizarUsina.setUsina(new Usina());
		atualizarUsina.getUsina().setId(UPDATED_USINA_ID);
		atualizarUsina.getUsina().setNomeCurto(UPDATED_USINA_NOME_CURTO);
		atualizarUsina.getUsina().setTipoUsina(UPDATED_USINA_TIPO);
		atualizarUsina.getUsina().setPotenciaCalculo(UPDATED_USINA_POTENCIA_CALCULO);
		
		usinaAggregate.apply(atualizarUsina);
	}

	@Test
	public void testExcluirUsina() {
		ExcluirUsinaCommand excluirUsina = new ExcluirUsinaCommand();

		usinaAggregate.apply(excluirUsina);

		assertThat(usinaAggregate.getUsina()).isNull();
	}

	@Test
	public void testApurarEventos() {
		usinaAggregate.getApuracoes().clear();
		
		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(DEFAULT_DATA_INICIO);
		apurarEventos.setDataFim(DEFAULT_DATA_FIM);
		apurarEventos.setEventos(new ArrayList<>());
		apurarEventos.getEventos().add(new Evento("1", DEFAULT_DATA_INICIO, "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		apurarEventos.getEventos().add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		apurarEventos.getEventos().add(new Evento("3", DEFAULT_DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));

		usinaAggregate.apply(apurarEventos);
		
		assertThat(usinaAggregate.getApuracoes()).isNotNull();
		assertThat(usinaAggregate.getApuracoes()).hasSize(1);
		assertThat(usinaAggregate.getApuracoes().get(0)).isNotNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
		assertThat(usinaAggregate.getApuracoes().get(0).getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
		assertThat(usinaAggregate.getApuracoes().get(0).getEventos()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getParametros()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas()).hasSize(2);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(1).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(2).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(1).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(1).getParametros().get(0).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(1).getParametros().get(1).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(1).getParametros().get(2).getEventos()).hasSize(3);
	}
	
	@Test
	public void testApurarNovosEventos() {
		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(NEW_DATA_INICIO);
		apurarEventos.setDataFim(NEW_DATA_FIM);
		apurarEventos.setEventos(new ArrayList<>());
		apurarEventos.getEventos().add(new Evento("1", NEW_DATA_INICIO, "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		apurarEventos.getEventos().add(new Evento("2", NEW_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		apurarEventos.getEventos().add(new Evento("3", NEW_DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		
		usinaAggregate.apply(apurarEventos);

		assertThat(usinaAggregate.getApuracoes()).isNotNull();
		assertThat(usinaAggregate.getApuracoes()).hasSize(2);
		assertThat(usinaAggregate.getApuracoes().get(0)).isNotNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
		assertThat(usinaAggregate.getApuracoes().get(0).getDataFim()).isEqualTo(DEFAULT_DATA_FIM);
		assertThat(usinaAggregate.getApuracoes().get(0).getEventos()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getParametros()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(1)).isNotNull();
		assertThat(usinaAggregate.getApuracoes().get(1).getDataInicio()).isEqualTo(NEW_DATA_INICIO);
		assertThat(usinaAggregate.getApuracoes().get(1).getDataFim()).isEqualTo(NEW_DATA_FIM);
		assertThat(usinaAggregate.getApuracoes().get(1).getEventos()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(1).getParametros()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas()).hasSize(2);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(0).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(0).getParametros().get(0).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(0).getParametros().get(1).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(0).getParametros().get(2).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(1).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(1).getParametros().get(0).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(1).getParametros().get(1).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(1).getTaxas().get(1).getParametros().get(2).getEventos()).hasSize(3);
	}

	@Test(expected = OnsRuntimeException.class)
	public void testApurarEventosFail() {
		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(DEFAULT_DATA_INICIO);
		apurarEventos.setDataFim(DEFAULT_DATA_FIM);
		apurarEventos.setEventos(new ArrayList<>());
		apurarEventos.getEventos().add(new Evento("1", DEFAULT_DATA_INICIO, "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		apurarEventos.getEventos().add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		apurarEventos.getEventos().add(new Evento("3", DEFAULT_DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		
		usinaAggregate.apply(apurarEventos);
	}
	
	@Test
	public void testApurarEventosCommandModification() {
		usinaAggregate.getApuracoes().clear();
		
		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(DEFAULT_DATA_INICIO);
		apurarEventos.setDataFim(DEFAULT_DATA_FIM);
		apurarEventos.setEventos(new ArrayList<>());
		apurarEventos.getEventos().add(new Evento("1", DEFAULT_DATA_INICIO, "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		apurarEventos.getEventos().add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		apurarEventos.getEventos().add(new Evento("3", DEFAULT_DATA_INICIO.plusDays(15).plusHours(12), "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		
		ApurarEventosCommandModification modification = new ApurarEventosCommandModification();
		modification.getEventosAdicionados().add(new Evento("4", DEFAULT_DATA_INICIO.plusDays(16).plusHours(12), "LIG", "NOR", "-", DEFAULT_USINA_POTENCIA_CALCULO));
		modification.getEventosExcluidos().add(new Evento("3", null, null, null, null, null));
		modification.getEventosModificados().add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GOT", 0.0));
		
		usinaAggregate.modifyAndApply(apurarEventos, modification);

		assertThat(usinaAggregate.getApuracoes()).isNotNull();
		assertThat(usinaAggregate.getApuracoes()).hasSize(1);
		assertThat(usinaAggregate.getApuracoes().get(0)).isNotNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getEventos()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getParametros()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas()).hasSize(2);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(0).getId()).isEqualTo("1");
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(1).getId()).isEqualTo("2");
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(1).getClassificacaoOrigem()).isEqualTo("GOT");
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(2).getId()).isEqualTo("4");
	}
	
	@Test
	public void testApurarEventosScenarioCommandModification() {
		usinaAggregate.getApuracoes().clear();
		
		ApurarEventosCommand apurarEventos = new ApurarEventosCommand();
		apurarEventos.setDataInicio(DEFAULT_DATA_INICIO);
		apurarEventos.setDataFim(DEFAULT_DATA_FIM);
		apurarEventos.setEventos(new ArrayList<>());
		apurarEventos.getEventos().add(new Evento("2", DEFAULT_DATA_INICIO.plusDays(15).plusHours(10), "DPR", "-", "GTR", 0.0));
		
		ScenarioCommandModification modification = new ScenarioCommandModification();
		modification.setScenarioName("Cenario 1");
		
		usinaAggregate.modifyAndApply(apurarEventos, modification);
		
		assertThat(usinaAggregate.getApuracoes()).isNotNull();
		assertThat(usinaAggregate.getApuracoes()).hasSize(1);
		assertThat(usinaAggregate.getApuracoes().get(0)).isNotNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getEventos()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getParametros()).isNull();
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas()).hasSize(2);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros()).hasSize(3);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos()).hasSize(1);
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(0).getId()).isEqualTo("2");
		assertThat(usinaAggregate.getApuracoes().get(0).getTaxas().get(0).getParametros().get(0).getEventos().get(0).getClassificacaoOrigem()).isEqualTo("GOT");
	}
	
	@Test
	public void testGetAggregateType() {
		assertThat(usinaAggregate.getAggregateType()).isEqualTo(UsinaAggregate.class.getName());
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void testApplyInvalidCommand() {
		usinaAggregate.apply(new Command() {
		});
	}
	
	@Test
	public void testNoModifierForCommand() {
		AtualizarUsinaCommand atualizarUsina = new AtualizarUsinaCommand();
		atualizarUsina.setUsina(new Usina());
		atualizarUsina.getUsina().setId(UPDATED_USINA_ID);

		usinaAggregate.modifyAndApply(atualizarUsina, new ScenarioCommandModification());

		assertThat(usinaAggregate.getUsina().getId()).isEqualTo(UPDATED_USINA_ID);
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testCommandModificationException() {
		ApurarEventosCommandModification modification = Mockito.mock(ApurarEventosCommandModification.class);
		Mockito.when(modification.getEventosAdicionados()).thenThrow(Exception.class);
		usinaAggregate.modifyAndApply(new ApurarEventosCommand(), modification);
	}
	
	@Test
	public void testReplay() {
		usinaAggregate.setMinorVersion(1L);
		usinaAggregate.replay(new UsinaExcluidaEvent());
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void testReplayInvalidEvent() {
		usinaAggregate.setMinorVersion(1L);
		usinaAggregate.replay(new Event() {
		});
	}
	
	@SuppressWarnings("unchecked")
	@Test(expected=OnsRuntimeException.class)
	public void testReplayException() {
		usinaAggregate.setMinorVersion(1L);
		UsinaAtualizadaEvent event = Mockito.mock(UsinaAtualizadaEvent.class);
		Mockito.when(event.getUsina()).thenThrow(Exception.class);
		usinaAggregate.replay(event);
	}
}
