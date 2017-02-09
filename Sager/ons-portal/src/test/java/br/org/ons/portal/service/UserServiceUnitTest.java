package br.org.ons.portal.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.org.ons.portal.security.AuthoritiesConstants;
import br.org.ons.portal.web.rest.dto.UserDTO;

/**
 * Test class for the UserService service.
 */
@RunWith(JUnit4.class)
public class UserServiceUnitTest {

	private UserService userService = new UserService();

	@Before
	public void setup() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void getNullUser() {
		SecurityContextHolder.getContext().setAuthentication(null);
		assertThat(userService.getUserWithAuthorities()).isNull();
	}

	@Test
	public void getUserDTO() {
		UserDTO user = new UserDTO("test", "john", "doe", "john.doe@jhipster.com",
				Collections.singleton(AuthoritiesConstants.ADMIN));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test", "");
		token.setDetails(user);
		SecurityContextHolder.getContext().setAuthentication(token);

		assertThat(userService.getUserWithAuthorities())
				.isNotNull()
				.hasFieldOrPropertyWithValue("login", "test")
				.hasFieldOrPropertyWithValue("firstName", "john")
				.hasFieldOrPropertyWithValue("email", "john.doe@jhipster.com")
				.matches(u -> u.getAuthorities().contains(AuthoritiesConstants.ADMIN));
	}

	@Test
	public void getUserDetails() {
		Collection<? extends GrantedAuthority> authorities = Collections
				.singleton(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				new User("test", "", authorities), "", authorities);
		SecurityContextHolder.getContext().setAuthentication(token);

		assertThat(userService.getUserWithAuthorities())
				.isNotNull()
				.hasFieldOrPropertyWithValue("login", "test")
				.hasFieldOrPropertyWithValue("firstName", "test")
				.matches(u -> u.getAuthorities().contains(AuthoritiesConstants.ADMIN));
	}
	
	@Test
	public void getUserNullPrincipal() {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				"test", "");
		SecurityContextHolder.getContext().setAuthentication(token);
		
		assertThat(userService.getUserWithAuthorities())
				.isNull();
	}
}
