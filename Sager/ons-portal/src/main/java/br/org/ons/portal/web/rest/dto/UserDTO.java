package br.org.ons.portal.web.rest.dto;

import java.util.Set;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import br.org.ons.portal.config.Constants;
/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    private String password;
    
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private Set<String> authorities;

    public UserDTO() {
    	super();
    }

	public UserDTO(String login, String firstName, String lastName, String email, Set<String> authorities) {
		this.login = login;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.authorities = authorities;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public Set<String> getAuthorities() {
		return authorities;
	}

	@Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
