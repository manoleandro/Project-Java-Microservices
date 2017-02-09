package br.org.ons.portal.security.pop;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import br.org.ons.portal.web.rest.dto.UserDTO;

@RunWith(MockitoJUnitRunner.class)
public class POPFilterUnitTest {

	@Mock
	private POPClient popClient;

	private POPFilter popFilter;

	@Before
	public void setup() {
		popFilter = new POPFilter(popClient);
	}

	@Test
	public void doFilter() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie(".ONSAUTH_VTPOP_01", "AAAAA"));
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();

		when(popClient.getUser(any()))
				.thenReturn(new UserDTO("login", "firstName", "lastName", "email", Collections.emptySet()));

		popFilter.doFilter(request, response, filterChain);

		assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull().extracting("principal.username")
				.contains("login");
	}

	@Test
	public void doFilterNullCookies() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies((Cookie[]) null);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();

		popFilter.doFilter(request, response, filterChain);

		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@Test
	public void doFilterEmptyCookies() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie[]{});
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		
		popFilter.doFilter(request, response, filterChain);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@Test
	public void doFilterOtherCookies() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie("AAAAA", "AAAAA"));
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();
		
		popFilter.doFilter(request, response, filterChain);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Test
	public void doFilterNullUser() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(new Cookie(".ONSAUTH_VTPOP_01", "AAAAA"));
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockFilterChain filterChain = new MockFilterChain();

		when(popClient.getUser(any())).thenReturn(null);

		popFilter.doFilter(request, response, filterChain);

		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
	}
}
