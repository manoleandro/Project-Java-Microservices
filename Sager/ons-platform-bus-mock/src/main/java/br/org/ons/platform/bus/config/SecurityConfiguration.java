package br.org.ons.platform.bus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/**/*.js")
				.antMatchers("/bower_components/**");
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
            	.headers().frameOptions().disable()
        	.and().formLogin()
            .and().authorizeRequests()
	        	.antMatchers("/").permitAll()
	        	.antMatchers("/AuthorizationServices/*").permitAll()
				.anyRequest().authenticated();
	}

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("sageradmin").password("Temp@123")
                	.roles("ADMIN", "CNOS", "COSR_S", "COSR_SE", "COSR_NE", "COSR_NCO", "AGENTE").and()
                .withUser("cnos").password("Temp@123").roles("CNOS").and()
                .withUser("cosr-s1").password("Temp@123").roles("COSR_S").and()
                .withUser("cosr-se1").password("Temp@123").roles("COSR_SE").and()
                .withUser("cosr-ne1").password("Temp@123").roles("COSR_NE").and()
                .withUser("cosr-nco1").password("Temp@123").roles("COSR_NCO").and()
                .withUser("cosr-nco2").password("Temp@123").roles("COSR_NCO").and()
                .withUser("cosr-nco").password("Temp@123").roles("COSR_NCO").and()
                .withUser("cos-sp").password("Temp@123").roles("COS").and()

                .withUser("chesf").password("chesf").roles("AGENTE");
        
    }
}