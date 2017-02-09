package br.org.ons.portal.web.rest;

import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for accessing authentication server.
 */
@RestController
@RequestMapping("/api")
public class AuthResource {

    private Environment env;

    @Inject
	public AuthResource(Environment env) {
		this.env = env;
	}

    @RequestMapping(value = "/auth/portal",
        method = RequestMethod.GET,
        produces = MediaType.TEXT_PLAIN_VALUE)
	public String getAuthPortal() {
		return env.getProperty("pop.portalUrl");
	}
}
