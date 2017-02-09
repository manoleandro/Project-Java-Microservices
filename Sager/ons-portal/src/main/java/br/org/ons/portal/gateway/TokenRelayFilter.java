package br.org.ons.portal.gateway;

import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import br.org.ons.portal.security.SecurityUtils;
import br.org.ons.portal.security.jwt.TokenProvider;

@Component
public class TokenRelayFilter extends ZuulFilter {

	private static final int FILTER_ORDER = 10000;

	private TokenProvider tokenProvider;

	@Inject
	public TokenRelayFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		Set<String> headers = (Set<String>) ctx.get("ignoredHeaders");
		// We need our JWT tokens relayed to resource servers
		headers.remove("authorization");

		// Adicionar JWT à chamada do serviço
		if (SecurityUtils.isAuthenticated()) {
			ctx.addZuulRequestHeader("authorization", "Bearer "
					+ tokenProvider.createToken(SecurityContextHolder.getContext().getAuthentication(), true));
		}

		return null;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return FILTER_ORDER;
	}
}
