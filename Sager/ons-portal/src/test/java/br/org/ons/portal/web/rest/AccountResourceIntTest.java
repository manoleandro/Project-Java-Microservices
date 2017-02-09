package br.org.ons.portal.web.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.portal.OnsPortalApp;
import br.org.ons.portal.security.AuthoritiesConstants;
import br.org.ons.portal.service.UserService;
import br.org.ons.portal.web.rest.dto.UserDTO;

/**
 * Test class for the AccountResource REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPortalApp.class)
public class AccountResourceIntTest {

	@Mock
	private UserService mockUserService;

	private MockMvc restUserMockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		this.restUserMockMvc = MockMvcBuilders.standaloneSetup(new AccountResource(mockUserService)).build();
	}

	@Test
	public void testGetExistingAccount() throws Exception {
		Set<String> authorities = new HashSet<>();
		authorities.add(AuthoritiesConstants.ADMIN);

		UserDTO user = new UserDTO("test", "john", "doe", "john.doe@jhipster.com", authorities);
		when(mockUserService.getUserWithAuthorities()).thenReturn(user);

		restUserMockMvc.perform(get("/api/account").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.login").value("test")).andExpect(jsonPath("$.firstName").value("john"))
				.andExpect(jsonPath("$.lastName").value("doe"))
				.andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
				.andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
	}

	@Test
	public void testGetUnknownAccount() throws Exception {
		when(mockUserService.getUserWithAuthorities()).thenReturn(null);

		restUserMockMvc.perform(get("/api/account").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError());
	}
}
