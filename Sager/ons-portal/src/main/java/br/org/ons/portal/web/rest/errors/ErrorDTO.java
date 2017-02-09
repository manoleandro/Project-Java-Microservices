package br.org.ons.portal.web.rest.errors;

import java.io.Serializable;

/**
 * DTO for transferring error message with a list of field errors.
 */
public class ErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;
    private final String description;

    public ErrorDTO(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

}
