package br.org.ons.platform.config;


  import javax.inject.Inject;

  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.http.HttpMethod;
  import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
  import org.springframework.security.config.annotation.web.builders.HttpSecurity;
  import org.springframework.security.config.annotation.web.builders.WebSecurity;
  import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
  import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
  import org.springframework.security.config.http.SessionCreationPolicy;
  import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;

  import br.org.ons.platform.security.AuthoritiesConstants;
  import br.org.ons.platform.security.jwt.JWTConfigurer;
  import br.org.ons.platform.security.jwt.TokenProvider;

  @Configuration
  @EnableWebSecurity
  @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
  public class MicroserviceSecurityConfiguration extends WebSecurityConfigurerAdapter {

      private TokenProvider tokenProvider;
      
      @Inject
      public MicroserviceSecurityConfiguration(TokenProvider tokenProvider) {
    	  this.tokenProvider = tokenProvider;
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
              .antMatchers("/test/**")
              .antMatchers("/h2-console/**");
      }

      @Override
      protected void configure(HttpSecurity http) throws Exception {
          http
              .csrf()
              .disable()
              .headers()
              .frameOptions()
              .disable()
          .and()
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
              .authorizeRequests()
              .antMatchers("/api/**").authenticated()
              .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)
              .antMatchers("/swagger-resources/configuration/ui").permitAll()
              // [ONS] Acesso liberado para o resource TimelineStore
              .antMatchers("/timeline-store/**").permitAll()
          .and()
              .apply(securityConfigurerAdapter());

      }

      private JWTConfigurer securityConfigurerAdapter() {
          return new JWTConfigurer(tokenProvider);
      }

      @Bean
      public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
          return new SecurityEvaluationContextExtension();
      }
  }


