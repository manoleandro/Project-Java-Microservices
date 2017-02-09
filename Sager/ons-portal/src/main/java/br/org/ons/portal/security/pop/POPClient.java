package br.org.ons.portal.security.pop;

import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.google.common.io.CharStreams;

import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.portal.config.pop.service.ObterAcessos;
import br.org.ons.portal.config.pop.service.ObterAcessosResponse;
import br.org.ons.portal.config.pop.service.ObterUsuarioPOP;
import br.org.ons.portal.config.pop.service.ObterUsuarioPOPResponse;
import br.org.ons.portal.config.pop.service.UsuarioPOP;
import br.org.ons.portal.web.rest.dto.UserDTO;

/**
 * Cliente para acesso ao web service do POP
 */
public class POPClient extends WebServiceGatewaySupport {

	private static final Logger LOG = LoggerFactory.getLogger(POPClient.class);

	private String serviceUrl;
	private String domain;
	private String username;
	private String password;

	public POPClient(String serviceUrl, String domain, String username, String password) {
		this.serviceUrl = serviceUrl;
		this.domain = domain;
		this.username = username;
		this.password = password;
	}

	public String ntlmConnect() {
		try {
			Authenticator.setDefault(new Authenticator() {
				@Override
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(domain + "\\" + username, password.toCharArray());
				}
			});
			HttpURLConnection conn = (HttpURLConnection) new URL(serviceUrl).openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			return CharStreams.toString(new InputStreamReader(conn.getInputStream()));
		} catch (Exception e) {
			throw new OnsRuntimeException(e);
		}
	}

	public UserDTO getUser(String cookie) {
		LOG.debug("getUser {}", cookie);
		UsuarioPOP usuario = null;
		Set<String> authorities = new HashSet<>();
		try {
			ObterUsuarioPOP usuarioRequest = new ObterUsuarioPOP();
			usuarioRequest.setTicket(cookie);
			ObterUsuarioPOPResponse usuarioResponse = (ObterUsuarioPOPResponse) getWebServiceTemplate()
					.marshalSendAndReceive(usuarioRequest,
							new SoapActionCallback("http://POPService/v1/POPService/ObterUsuarioPOP"));
			usuario = usuarioResponse.getObterUsuarioPOPResult();

			ObterAcessos acessosRequest = new ObterAcessos();
			acessosRequest.setCodAplicacao("SAGER");
			acessosRequest.setTicket(cookie);
			ObterAcessosResponse acessosResponse = (ObterAcessosResponse) getWebServiceTemplate().marshalSendAndReceive(
					acessosRequest, new SoapActionCallback("http://POPService/v1/POPService/ObterAcessos"));
			acessosResponse.getObterAcessosResult().getEscopoDTO()
					.forEach(escopo -> authorities.addAll(escopo.getPermissaoLista().getString()));
		} catch (SoapFaultClientException e) {
			LOG.info("Erro ao consumir POPService {}", e);
		}
		return usuario == null ? null
				: new UserDTO(usuario.getLogin(), firstName(usuario.getNome()), lastName(usuario.getNome()),
						usuario.getEmail(), authorities);
	}

	private String firstName(String nome) {
		int i = nome.indexOf(' ');
		return i < 0 ? nome : nome.substring(0, i);
	}

	private String lastName(String nome) {
		int i = nome.indexOf(' ');
		return i < 0 ? null : nome.substring(i + 1);
	}
}
