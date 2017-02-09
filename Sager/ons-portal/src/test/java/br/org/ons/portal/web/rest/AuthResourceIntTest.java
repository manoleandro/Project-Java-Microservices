package br.org.ons.portal.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.portal.OnsPortalApp;

/**
 * Test class for the AuthResource REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsPortalApp.class)
public class AuthResourceIntTest {

	@Inject
	private Environment env;

	private MockMvc restAuthMockMvc;

	@Before
	public void setup() {
		restAuthMockMvc = MockMvcBuilders.standaloneSetup(new AuthResource(env)).build();
	}

	@Test
	public void getAuthPortal() throws Exception {
		restAuthMockMvc.perform(get("/api/auth/portal")
				.accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().string(env.getProperty("pop.portalUrl")));
	}
}
