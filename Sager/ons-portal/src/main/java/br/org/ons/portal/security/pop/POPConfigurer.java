package br.org.ons.portal.security.pop;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class POPConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	public static final String AUTHORIZATION_HEADER = "Authorization";

	private POPClient popClient;

	public POPConfigurer(POPClient popClient) {
		this.popClient = popClient;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		POPFilter popFilter = new POPFilter(popClient);
		http.addFilterBefore(popFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
