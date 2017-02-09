package br.org.ons.sager.instalacao.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import br.org.ons.platform.common.Command;
import br.org.ons.platform.common.CommandModification;
import br.org.ons.platform.common.Event;
import br.org.ons.platform.common.exception.OnsRuntimeException;

/**
 * Um aggregate corresponde a um conjunto de entidades interrelacionadas como
 * uma árvore de objetos, que juntas definem um subdomínio do sistema, com seus
 * próprios ciclo de vida, escopo de consistência transacional e contexto
 * funcional. Um aggregate deve representar um conjunto de entidades capaz de
 * suportar um determinado processo de negócio ou serviço de forma autônoma,
 * coesa e desacoplada de outros aggregates.
 * 
 * Esta classe abstrata deve ser estendida para cada aggregate do sistema. Cada
 * subclasse deve possuir:
 * <li>Campos <code>private</code> que representem o estado atual do aggregate
 * (com getters e setters com visibilidade <code>public</code>)
 * <li>Métodos <code>private handle(Command)</code> para tratamento de comandos
 * <li>Métodos <code>private when(Event)</code> para tratamento de eventos
 * <li>Métodos <code>private modify(Command, CommandModification)</code> para
 * modificação de comandos
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "aggregateType")
public abstract class Aggregate {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private String id;
	/**
	 * Versão da timeline do aggregate. Incrementada quando a linha do tempo do
	 * aggregate é reprocessada ou a cada cenário criado
	 */
	private Long majorVersion;

	/**
	 * Versão do estado do aggregate dentro de uma timeline. Incrementada a cada
	 * vez que um evento é aplicado para modificar o estado do aggregate.
	 */
	private Long minorVersion;
	/**
	 * Nome do aggregate
	 */
	private String name;
	/**
	 * Lista de eventos aplicados ao estado do aggregate, porém ainda não salvos
	 * no repositório
	 */
	private List<Event> eventsToSave = new ArrayList<>();
	private String scenarioName;

	private String handlerVersion;
	
	/**
	 * @return O nome completo da classe do aggregate
	 */
	public String getAggregateType() {
		return getClass().getName();
	}

	/**
	 * Aplica um comando ao aggregate. Utiliza reflexão para invocar um dos
	 * métodos <code>handle(Command)</code> implementados pela subclasse. A
	 * subclasse deve implementar um método <code>handle(Command)</code> para
	 * cada comando suportado pelo aggregate, sendo que cada método deve
	 * executar a seguinte lógica:
	 * <li>Validar se o comando pode ser executado no estado atual do aggregate
	 * <li>Caso o comando seja inválido, lançar
	 * <code>IllegalArgumentException</code>
	 * <li>Caso o comando seja válido, instanciar eventos a serem aplicados ao
	 * aggregate e aplicá-los invocando o método <code>play(Event)</code>
	 * <li>Caso o comando gere um resultado, o método deve possuir como retorno
	 * o tipo de objeto do resultado. Caso não gere resultado, o método deverá
	 * ter retorno <code>void</code>
	 * 
	 * @param <R>
	 *            Tipo de retorno esperado da execução do comando
	 * @param command
	 *            Comando a ser executado
	 * @return Resultado da execução do comando, retornado pelo método
	 *         <code>handle(Command)</code>
	 */
	@SuppressWarnings("unchecked")
	public <R> R apply(Command command) {
		try {
			Method handle = getClass().getDeclaredMethod("handle", command.getClass());
			handle.setAccessible(true);
			return (R) handle.invoke(this, command);
		} catch (InvocationTargetException e) {
			log.debug("[apply] InvocationTargetException: {}", e);
			if (e.getTargetException() instanceof OnsRuntimeException) {
				throw (OnsRuntimeException) e.getTargetException();
			}
			throw new OnsRuntimeException(e.getTargetException());
		} catch (ReflectiveOperationException e) {
			throw new OnsRuntimeException(e);
		}
	}

	/**
	 * Modifica um comando e o aplica ao aggregate. Executa a mesma lógica do
	 * método <code>apply(Command)</code>, porém antes aplica a modificação
	 * descrita pelo parâmetro do tipo <code>CommandModification</code>. Utiliza
	 * reflexão para invocar um dos métodos
	 * <code>modify(Command, CommandModification)</code> implementados pela
	 * subclasse. A subclasse deve implementar um método
	 * <code>modify(Command, CommandModification)</code> para cada comando que
	 * suporta modificação, sendo que cada método deve executar a seguinte
	 * lógica:
	 * <li>Utilizar informações do parâmetro <code>CommandModification</code>
	 * para alterar o objeto passado no parâmetro <code>Command</code>
	 * <li>O método deve possuir retorno <code>void</code>, pois as modificações
	 * devem ser feitas no próprio objeto comando passado como parâmetro
	 * 
	 * 
	 * @param <R>
	 *            Tipo de retorno esperado da execução do comando
	 * @param command
	 *            Comando a ser executado
	 * @param modification
	 *            Modificação a ser aplicada ao comando
	 * @return Resultado da execução do comando, retornado pelo método
	 *         <code>handle(Command)</code>
	 */
	@SuppressWarnings("unchecked")
	public <R> R modifyAndApply(Command command, CommandModification modification) {
		try {
			Method modify = getClass().getDeclaredMethod("modify", command.getClass(), modification.getClass());
			modify.setAccessible(true);
			modify.invoke(this, command, modification);
		} catch (InvocationTargetException e) {
			log.debug("[modifyAndApply] InvocationTargetException: {}", e);
			if (e.getTargetException() instanceof OnsRuntimeException) {
				throw (OnsRuntimeException) e.getTargetException();
			}
			throw new OnsRuntimeException(e.getTargetException());
		} catch (ReflectiveOperationException e) {
			log.debug("NoSuchMethodException: {}", e);
			log.info("No modifier for {}, {}. Nothing to do.", command.getClass(), modification.getClass());
		}
		return (R) apply(command);
	}

	/**
	 * Aplica um novo evento ao estado do aggregate. O novo evento é adicionado
	 * à lista de eventos a serem salvos no repositório. Este método deve ser
	 * invocado pelos métodos <code>handle(Command)</code> implementados pela
	 * subclasse
	 * 
	 * @param event
	 *            Novo evento a ser aplicado
	 */
	protected void play(Event event) {
		redirectToWhen(event);
		eventsToSave.add(event);
	}

	/**
	 * Aplica um evento passado ao estado do aggregate. Utilizado pelo
	 * <code>AggregateService</code> para restaurar o estado do aggregate a
	 * partir do histórico de eventos da linha do tempo. Eventos passados não
	 * são adicionados à lista de eventos a serem salvos no repositório.
	 * 
	 * @param event
	 *            Evento passado a ser aplicado
	 */
	public void replay(Event event) {
		redirectToWhen(event);
		minorVersion++;
	}

	/**
	 * Método interno que aplica um evento ao estado do aggregate. Utiliza
	 * reflexão para invocar um dos métodos <code>when(Event)</code>
	 * implementados pela subclasse. A subclasse deve implementar um método
	 * <code>when(Event)</code> para cada evento que altera o estado do
	 * aggregate, sendo que cada método deve executar a seguinte lógica:
	 * <li>Utilizar as informações do objeto <code>Event</code> para modificar
	 * os campos internos que representam o estado atual do aggregate
	 * 
	 * @param event
	 *            O evento a ser aplicado
	 */
	private void redirectToWhen(Event event) {
		try {
			Method when = getClass().getDeclaredMethod("when", event.getClass());
			when.setAccessible(true);
			when.invoke(this, event);
		} catch (InvocationTargetException e) {
			log.debug("[redirectToWhen] InvocationTargetException: {}", e);
			if (e.getTargetException() instanceof OnsRuntimeException) {
				throw (OnsRuntimeException) e.getTargetException();
			}
			throw new OnsRuntimeException(e.getTargetException());
		} catch (ReflectiveOperationException e) {
			throw new OnsRuntimeException(e);
		}
	}

	/**
	 * @return o campo id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            o campo id a ser definido
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return o campo majorVersion
	 */
	public Long getMajorVersion() {
		return majorVersion;
	}

	/**
	 * @param majorVersion
	 *            o campo majorVersion a ser definido
	 */
	public void setMajorVersion(Long majorVersion) {
		this.majorVersion = majorVersion;
	}

	/**
	 * @return o campo minorVersion
	 */
	public Long getMinorVersion() {
		return minorVersion;
	}

	/**
	 * @param minorVersion
	 *            o campo minorVersion a ser definido
	 */
	public void setMinorVersion(Long minorVersion) {
		this.minorVersion = minorVersion;
	}

	/**
	 * @return o campo name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            o campo name a ser definido
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return o campo scenarioName
	 */
	public String getScenarioName() {
		return scenarioName;
	}

	/**
	 * @param scenarioName
	 *            o campo scenarioName a ser definido
	 */
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}

	public String getHandlerVersion() {
		return handlerVersion;
	}

	public void setHandlerVersion(String handlerVersion) {
		this.handlerVersion = handlerVersion;
	}

	/**
	 * @return o campo eventsToSave
	 */
	public List<Event> getEventsToSave() {
		return eventsToSave;
	}
}
