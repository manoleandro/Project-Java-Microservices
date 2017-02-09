package br.org.ons.platform.bus.web.rest;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.ons.platform.bus.security.TokenProvider;

@RestController
public class POPResourceMock {

	@Inject
	private TokenProvider tokenProvider;

	@RequestMapping(value = "/ons.pop.federation/", method = RequestMethod.GET)
	public void redirect(@RequestParam String wa, @RequestParam String wtrealm, @RequestParam String wctx,
			@RequestParam String wreply, HttpServletResponse response) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			Cookie cookie = new Cookie(".ONSAUTH_VTPOP_01", tokenProvider.createToken(authentication));
			cookie.setPath("/");
			cookie.setMaxAge(3600);
			((HttpServletResponse) response).addCookie(cookie);
		}
		response.sendRedirect(wreply);
	}

	@RequestMapping(value = "/pop/SignOut.aspx", method = RequestMethod.GET)
	public void logout(@RequestParam String ReturnUrl, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Cookie popCookie = new Cookie(".ONSAUTH_VTPOP_01", null);
		popCookie.setPath("/");
		popCookie.setMaxAge(0);
		((HttpServletResponse) response).addCookie(popCookie);
		
		Cookie jCookie = new Cookie("JSESSIONID", null);
		jCookie.setPath("/");
		jCookie.setMaxAge(0);
		((HttpServletResponse) response).addCookie(jCookie);
		
		SecurityContextHolder.getContext().setAuthentication(null);
		SecurityContextHolder.clearContext();
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		response.sendRedirect(ReturnUrl);
	}
}
