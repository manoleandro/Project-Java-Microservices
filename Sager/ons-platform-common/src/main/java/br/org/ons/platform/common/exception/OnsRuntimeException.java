package br.org.ons.platform.common.exception;

/**
 * Classe de exceção para encapsular RuntimeExceptions
 *
 */
public class OnsRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OnsRuntimeException() {
		super();
	}

	public OnsRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public OnsRuntimeException(String message) {
		super(message);
	}

	public OnsRuntimeException(Throwable cause) {
		super(cause);
	}
}
