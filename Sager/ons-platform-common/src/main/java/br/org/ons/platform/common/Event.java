package br.org.ons.platform.common;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Classe abstrata que deve ser estendida por todos os eventos do sistema.
 * 
 * Um evento representa um fato ocorrido no sistema, mais especificamente, uma
 * altera��o ocorrida no estado de um aggregate do dom�nio do sistema.
 * 
 * Eventos s�o publicados para notificar partes interessadas (tanto internas
 * quanto externas ao sistema) sobre altera��es no estado dos aggregates.
 * 
 * Eventos s�o distribu�dos para servi�os do tipo EventHandler, sendo que um
 * evento pode ser entregue a mais de um servi�o.
 * 
 * O nome do evento deve descrever o fato ocorrido, preferencialmente
 * referenciando a entidade afetada e contendo um verbo no tempo passado. Ex:
 * Taxas Calculadas, Usina Alterada.
 */
@JsonTypeInfo(
		use = JsonTypeInfo.Id.CLASS, 
		include = JsonTypeInfo.As.EXISTING_PROPERTY, 
		property = "name")
public abstract class Event implements Serializable {

	public String getName() {
		return getClass().getName();
	}
}
