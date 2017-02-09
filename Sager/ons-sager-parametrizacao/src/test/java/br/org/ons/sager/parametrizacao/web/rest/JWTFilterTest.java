package br.org.ons.sager.parametrizacao.web.rest;

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

import br.org.ons.sager.parametrizacao.OnsSagerParametrizacaoApp;
import br.org.ons.sager.parametrizacao.security.jwt.JWTConfigurer;
import br.org.ons.sager.parametrizacao.security.jwt.JWTFilter;
import br.org.ons.sager.parametrizacao.security.jwt.TokenProvider;
import br.org.ons.sager.parametrizacao.web.rest.util.PaginationUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerParametrizacaoApp.class)
public class JWTFilterTest {

	@Inject
	private JWTFilter jwtFilter;

	@Inject
	private TokenProvider tokenProvider;
	
	PaginationUtil paginationUtil;
	
	@Before
	public void initTest() {

	}

	/* Caso de teste - Criar token de acesso.
	 * Resultado esperado: Retornar um token valido.
	 */
	
	@Test
	public void ct010001() throws Exception {

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
	
	/* Caso de teste - Criar token de acesso invalido.
	 * Resultado esperado: Retornar um token invalido.
	 */

	@Test
	public void ct010002() throws Exception {
		
		String tokenInvalido = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjbm9zIiwiYXV0aCI6IlJPTEVfQ05PUyIsImV4cCI6MTQ4NDMzMTI2MH0.JPPXg0gzd8RBWAJh5YwnXZ940FXlkBcxuMrVOxm6Ozt-QwEBtt_Sn1IOnoKNIkaBV6jH9H7v0UJPbntSN262fasd";

		MockHttpServletRequest mockRequest = new MockHttpServletRequest();
			mockRequest.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + tokenInvalido);
		
		MockHttpServletResponse mockResponse = new MockHttpServletResponse();
		
		MockFilterChain mockFilter = new MockFilterChain();
		
		jwtFilter.doFilter(mockRequest, mockResponse , mockFilter);

	}
	
	/* Caso de teste - Criar token de acesso nulo.
	 * Resultado esperado: Retornar um token invalido.
	 */
	
	@Test
	public void ct010003() throws Exception {
		
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
