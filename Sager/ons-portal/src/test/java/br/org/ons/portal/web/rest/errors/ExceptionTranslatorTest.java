package br.org.ons.portal.web.rest.errors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.portal.OnsPortalApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsPortalApp.class)
public class ExceptionTranslatorTest {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = standaloneSetup(new ExceptionResource())
				.setControllerAdvice(new ExceptionTranslator())
				.build();
	}

	@Test
	public void processAccessDeniedException() throws Exception {
		mockMvc.perform(get("/acess-denied"))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void processMethodNotSupportedException() throws Exception {
		mockMvc.perform(delete("/acess-denied"))
				.andExpect(status().isMethodNotAllowed());
	}
	
	@Test
	public void processRuntimeException() throws Exception {
		mockMvc.perform(get("/runtime-exception"))
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void processAnnotatedRuntimeException() throws Exception {
		mockMvc.perform(get("/conflict-exception"))
				.andExpect(status().isConflict());
	}

	@RestController
	private class ExceptionResource {

		@RequestMapping(value = "/acess-denied", method = RequestMethod.GET)
		public void accessDenied() {
			throw new AccessDeniedException("Access denied");
		}
		
		@RequestMapping(value = "/runtime-exception", method = RequestMethod.GET)
		public void runtimeException() {
			throw new RuntimeException("Runtime exception");
		}
		
		@RequestMapping(value = "/conflict-exception", method = RequestMethod.GET)
		public void conflictException() {
			throw new ConflictException();
		}
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	private class ConflictException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
