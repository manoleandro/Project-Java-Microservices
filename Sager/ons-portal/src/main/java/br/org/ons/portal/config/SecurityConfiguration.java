package br.org.ons.portal.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

import br.org.ons.portal.security.AuthoritiesConstants;
import br.org.ons.portal.security.Http401UnauthorizedEntryPoint;
import br.org.ons.portal.security.pop.POPClient;
import br.org.ons.portal.security.pop.POPConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private Http401UnauthorizedEntryPoint authenticationEntryPoint;

	private POPClient popClient;

	@Inject
	public SecurityConfiguration(Http401UnauthorizedEntryPoint authenticationEntryPoint, POPClient popClient) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.popClient = popClient;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/app/**/*.{js,html}")
				.antMatchers("/bower_components/**")
				.antMatchers("/i18n/**")
				.antMatchers("/content/**")
				.antMatchers("/swagger-ui/index.html")
				.antMatchers("/test/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
				.and().csrf().disable().headers().frameOptions().disable()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()
					.antMatchers("/api/profile-info").permitAll()
		            .antMatchers("/api/auth/**").permitAll()
					.antMatchers("/api/**").authenticated()
					.antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
					.antMatchers("/v2/api-docs/**").permitAll()
					.antMatchers("/swagger-resources/configuration/ui").permitAll()
					.antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
				.and().apply(securityConfigurerAdapter());

	}

	private POPConfigurer securityConfigurerAdapter() {
		return new POPConfigurer(popClient);
	}

	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}
}
