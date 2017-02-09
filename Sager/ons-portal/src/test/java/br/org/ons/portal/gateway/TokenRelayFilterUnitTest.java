package br.org.ons.portal.gateway;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.netflix.zuul.context.RequestContext;

import br.org.ons.portal.security.jwt.TokenProvider;

public class TokenRelayFilterUnitTest {

	private TokenRelayFilter filter = new TokenRelayFilter(mock(TokenProvider.class));
	
	@SuppressWarnings("unchecked")
	@Test
	public void run() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        
        Set<String> ignoredHeaders = new HashSet<>();
        ignoredHeaders.add("authorization");
        RequestContext.getCurrentContext().set("ignoredHeaders", ignoredHeaders);
        
        filter.run();

        assertThat((Set<String>) RequestContext.getCurrentContext().get("ignoredHeaders")).doesNotContain("authorization");
        assertThat(RequestContext.getCurrentContext().getZuulRequestHeaders()).containsKey("authorization");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void runNotAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.setContext(securityContext);

        Set<String> ignoredHeaders = new HashSet<>();
        ignoredHeaders.add("authorization");
        RequestContext.getCurrentContext().set("ignoredHeaders", ignoredHeaders);
        
		filter.run();
		
		assertThat((Set<String>) RequestContext.getCurrentContext().get("ignoredHeaders")).doesNotContain("authorization");
		assertThat(RequestContext.getCurrentContext().getZuulRequestHeaders()).doesNotContainKey("authorization");
	}
	
	@Test
	public void shouldFilter() {
		assertThat(filter.shouldFilter()).isTrue();
	}
	
	@Test
	public void filterType() {
		assertThat(filter.filterType()).isEqualTo("pre");
	}
	
	@Test
	public void filterOrder() {
		assertThat(filter.filterOrder()).isEqualTo(10000);
	}
}
