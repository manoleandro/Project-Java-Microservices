package br.org.ons.platform.security;

import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
* Test class for the SecurityUtils utility class.
*
* @see SecurityUtils
*/
public class SecurityUtilsUnitTest {

    @Test
    public void testgetCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        String login = SecurityUtils.getCurrentUserLogin();
        assertThat(login).isEqualTo("admin");
    }
    
    @Test
    public void testgetCurrentUserLoginDetails() {
    	SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    	securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(new User("admin", "", new HashSet<>()), "admin"));
    	SecurityContextHolder.setContext(securityContext);
    	String login = SecurityUtils.getCurrentUserLogin();
    	assertThat(login).isEqualTo("admin");
    }
    
    @Test
    public void testgetCurrentUserLoginNull() {
    	SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    	securityContext.setAuthentication(null);
    	SecurityContextHolder.setContext(securityContext);
    	String login = SecurityUtils.getCurrentUserLogin();
    	assertThat(login).isNull();
    }
    
    @Test
    public void testgetCurrentUserLoginOtherPrincipal() {
    	SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    	securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(1, "admin"));
    	SecurityContextHolder.setContext(securityContext);
    	String login = SecurityUtils.getCurrentUserLogin();
    	assertThat(login).isNull();
    }

}
