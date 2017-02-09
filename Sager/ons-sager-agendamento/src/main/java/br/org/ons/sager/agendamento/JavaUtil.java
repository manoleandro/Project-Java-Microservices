package br.org.ons.sager.agendamento;

import java.util.Collections;

public class JavaUtil {
	
	private JavaUtil(){}
	
	public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
	    return iterable == null ? Collections.<T>emptyList() : iterable;
	}

}
