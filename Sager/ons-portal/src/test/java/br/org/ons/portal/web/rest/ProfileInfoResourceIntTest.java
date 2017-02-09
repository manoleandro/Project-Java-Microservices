package br.org.ons.portal.web.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.portal.OnsPortalApp;
import br.org.ons.portal.config.JHipsterProperties;

/**
 * Test class for the ProfileInfoResource REST controller.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=OnsPortalApp.class)
public class ProfileInfoResourceIntTest {

	@Inject
	private Environment env;

	@Inject
	private JHipsterProperties jHipsterProperties;

	private MockMvc restProfileInfoMockMvc;

	@Test
	public void getActiveProfiles() throws Exception {
		restProfileInfoMockMvc = MockMvcBuilders.standaloneSetup(new ProfileInfoResource(env, jHipsterProperties))
				.build();
		
		restProfileInfoMockMvc.perform(get("/api/profile-info")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.activeProfiles").value("test"));
	}
	
	@Test
	public void getActiveProfilesNullDisplayOnActiveProfiles() throws Exception {
		JHipsterProperties mockProperties = mock(JHipsterProperties.class);
		restProfileInfoMockMvc = MockMvcBuilders.standaloneSetup(new ProfileInfoResource(env, mockProperties))
				.build();
		when(mockProperties.getRibbon()).thenReturn(mock(JHipsterProperties.Ribbon.class));
		when(mockProperties.getRibbon().getDisplayOnActiveProfiles()).thenReturn(null);
		
		restProfileInfoMockMvc.perform(get("/api/profile-info")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ribbonEnv").doesNotExist());
	}
	
	@Test
	public void getActiveProfilesEmptyDisplayOnActiveProfiles() throws Exception {
		JHipsterProperties mockProperties = mock(JHipsterProperties.class);
		restProfileInfoMockMvc = MockMvcBuilders.standaloneSetup(new ProfileInfoResource(env, mockProperties))
				.build();
		when(mockProperties.getRibbon()).thenReturn(mock(JHipsterProperties.Ribbon.class));
		when(mockProperties.getRibbon().getDisplayOnActiveProfiles()).thenReturn(new String[]{});
		
		restProfileInfoMockMvc.perform(get("/api/profile-info")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.ribbonEnv").doesNotExist());
	}
}
