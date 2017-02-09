package br.org.ons.portal.web.rest;

import br.org.ons.portal.config.JHipsterProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProfileInfoResource {

    private Environment env;

    private JHipsterProperties jHipsterProperties;

    @Inject
    public ProfileInfoResource(Environment env, JHipsterProperties jHipsterProperties) {
		this.env = env;
		this.jHipsterProperties = jHipsterProperties;
	}

	@RequestMapping(value = "/profile-info",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ProfileInfoResponse getActiveProfiles() {
        ProfileInfoResponse response = new ProfileInfoResponse();
        response.setActiveProfiles(env.getActiveProfiles());
        response.setRibbonEnv(getRibbonEnv());
		return response ;
    }

    private String getRibbonEnv() {
        String[] activeProfiles = env.getActiveProfiles();
        String[] displayOnActiveProfiles = jHipsterProperties.getRibbon().getDisplayOnActiveProfiles();

        if (displayOnActiveProfiles == null) {
            return null;
        }

        List<String> ribbonProfiles = new ArrayList<>(Arrays.asList(displayOnActiveProfiles));
        List<String> springBootProfiles = Arrays.asList(activeProfiles);
        ribbonProfiles.retainAll(springBootProfiles);

        if (!ribbonProfiles.isEmpty()) {
            return ribbonProfiles.get(0);
        }
        return null;
    }

    class ProfileInfoResponse {

        private String[] activeProfiles;
        private String ribbonEnv;

		public String[] getActiveProfiles() {
			return activeProfiles;
		}

		public void setActiveProfiles(String[] activeProfiles) {
			this.activeProfiles = activeProfiles;
		}

		public String getRibbonEnv() {
			return ribbonEnv;
		}

		public void setRibbonEnv(String ribbonEnv) {
			this.ribbonEnv = ribbonEnv;
		}
    }
}
