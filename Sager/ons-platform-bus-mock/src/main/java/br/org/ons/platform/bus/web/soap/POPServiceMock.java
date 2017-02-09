package br.org.ons.platform.bus.web.soap;

import javax.inject.Inject;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.org.ons.platform.bus.security.TokenProvider;
import br.org.ons.platform.bus.security.pop.service.ArrayOfEscopoDTO;
import br.org.ons.platform.bus.security.pop.service.ArrayOfstring;
import br.org.ons.platform.bus.security.pop.service.EscopoDTO;
import br.org.ons.platform.bus.security.pop.service.ObterAcessos;
import br.org.ons.platform.bus.security.pop.service.ObterAcessosResponse;
import br.org.ons.platform.bus.security.pop.service.ObterUsuarioPOP;
import br.org.ons.platform.bus.security.pop.service.ObterUsuarioPOPResponse;
import br.org.ons.platform.bus.security.pop.service.UsuarioPOP;

@Endpoint
public class POPServiceMock {

	private static final String NAMESPACE_URI = "http://POPService/v1";

	@Inject
	private TokenProvider tokenProvider;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObterAcessos")
	@ResponsePayload
	public ObterAcessosResponse obterAcessos(@RequestPayload ObterAcessos request) {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) tokenProvider
				.getAuthentication(request.getTicket());
		ArrayOfstring permissoes = new ArrayOfstring();
		authentication.getAuthorities().forEach(authority -> permissoes.getString().add(authority.getAuthority()));
		EscopoDTO escopo = new EscopoDTO();
		escopo.setPermissaoLista(permissoes);
		ArrayOfEscopoDTO escopos = new ArrayOfEscopoDTO();
		escopos.getEscopoDTO().add(escopo);
		ObterAcessosResponse response = new ObterAcessosResponse();
		response.setObterAcessosResult(escopos);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "ObterUsuarioPOP")
	@ResponsePayload
	public ObterUsuarioPOPResponse obterUsuarioPOP(@RequestPayload ObterUsuarioPOP request) {
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) tokenProvider
				.getAuthentication(request.getTicket());
		User user = (User) authentication.getPrincipal();
		UsuarioPOP usuario = new UsuarioPOP();
		usuario.setLogin(user.getUsername());
		usuario.setNome(user.getUsername() + ' ' + user.getUsername());
		usuario.setEmail(user.getUsername() + "@ons.org.br");
		ObterUsuarioPOPResponse response = new ObterUsuarioPOPResponse();
		response.setObterUsuarioPOPResult(usuario);
		return response;
	}
}
