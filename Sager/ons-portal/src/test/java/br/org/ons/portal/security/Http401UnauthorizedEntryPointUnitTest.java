package br.org.ons.portal.security;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class Http401UnauthorizedEntryPointUnitTest {

	@Test
	public void commence() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		new Http401UnauthorizedEntryPoint().commence(request, response, null);

		assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}
}
