package br.org.ons.portal.security.pop;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import br.org.ons.portal.web.rest.dto.UserDTO;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * cookie corresponding to a valid user is found.
 */
public class POPFilter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(POPFilter.class);

	private POPClient popClient;

	public POPFilter(POPClient popClient) {
		this.popClient = popClient;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		// Recupera o ticket a partir da request
		String ticket = resolveTicket((HttpServletRequest) servletRequest);
		if (ticket != null) {
			// Se houver ticket, busca informações do usuário no POP
			popClient.ntlmConnect();
			UserDTO user = popClient.getUser(ticket);
			if (user != null) {
				// Se ticket válido, seta informações do usuário no contexto
				Collection<? extends GrantedAuthority> authorities = user.getAuthorities().stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						new User(user.getLogin(), "", authorities), ticket, authorities);
				authenticationToken.setDetails(user);

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			} else {
				// Se ticket inválido, retorna HTTP 401
				((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		} else {
			// Se não houver ticket, limpa contexto, retorna HTTP 401
			LOG.debug("Ticket not found: logged out");
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	/**
	 * @param request
	 * @return ticket recuperado a partir do cookie do POP, ou null caso não exista
	 */
	private String resolveTicket(HttpServletRequest request) {
		String ticket = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (".ONSAUTH_VTPOP_01".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return ticket;
	}
}
