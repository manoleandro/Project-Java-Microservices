package br.org.ons.sager.agendamento.web.rest;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import br.org.ons.sager.agendamento.OnsSagerAgendamentoApp;
import br.org.ons.sager.agendamento.security.jwt.JWTConfigurer;
import br.org.ons.sager.agendamento.security.jwt.JWTFilter;
import br.org.ons.sager.agendamento.security.jwt.TokenProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerAgendamentoApp.class)
public class JWTFilterTest {

	@Inject
	private JWTFilter jwtFilter;

	@Inject
	private TokenProvider tokenProvider;
	
	@Before
	public void initTest() {

	}

	@Test
	public void criarToken() throws Exception {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_CNOS"));
		
		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken("cnos", "", authorities);
	
		String token = tokenProvider.createToken(user, true);	
		
		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
			mockRequest.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + token);
		
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		
		MockFilterChain mockFilter = new MockFilterChain();
		
		jwtFilter.doFilter(mockRequest, mockResponse , mockFilter);
	}

	@Test
	public void criarTokenInvalido() throws Exception {
		
		String tokenInvalido = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjbm9zIiwiYXV0aCI6IlJPTEVfQ05PUyIsImV4cCI6MTQ4NDMzMTI2MH0.JPPXg0gzd8RBWAJh5YwnXZ940FXlkBcxuMrVOxm6Ozt-QwEBtt_Sn1IOnoKNIkaBV6jH9H7v0UJPbntSN262fasd";

		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
			mockRequest.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenInvalido);
		
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		
		MockFilterChain mockFilter = new MockFilterChain();
		
		jwtFilter.doFilter(mockRequest, mockResponse , mockFilter);

	}
	
	@Test
	public void criarTokenNull() throws Exception {
		
		String tokenInvalido = "";

		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
			mockRequest.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, tokenInvalido);
		
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		
		MockFilterChain mockFilter = new MockFilterChain();
		
		jwtFilter.doFilter(mockRequest, mockResponse , mockFilter);

	}
	@After
	public void exitTest() {

	}

}
