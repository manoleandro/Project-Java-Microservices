package br.org.ons.portal.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import br.org.ons.portal.web.rest.dto.UserDTO;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

	public UserDTO getUserWithAuthorities() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null) {
			if (authentication.getDetails() instanceof UserDTO) {
				return (UserDTO) authentication.getDetails();
			} else if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
				Set<String> authorities = new HashSet<>();
				springSecurityUser.getAuthorities().forEach(authority -> authorities.add(authority.getAuthority()));
				return new UserDTO(springSecurityUser.getUsername(), springSecurityUser.getUsername(), null, null,
						authorities);
			}
		}
		return null;
	}
}
