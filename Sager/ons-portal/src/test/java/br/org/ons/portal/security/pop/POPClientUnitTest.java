package br.org.ons.portal.security.pop;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.powermock.api.mockito.PowerMockito.*;

import java.io.IOException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import br.org.ons.platform.common.exception.OnsRuntimeException;
import br.org.ons.portal.config.pop.service.ArrayOfEscopoDTO;
import br.org.ons.portal.config.pop.service.ArrayOfstring;
import br.org.ons.portal.config.pop.service.EscopoDTO;
import br.org.ons.portal.config.pop.service.ObterAcessos;
import br.org.ons.portal.config.pop.service.ObterAcessosResponse;
import br.org.ons.portal.config.pop.service.ObterUsuarioPOP;
import br.org.ons.portal.config.pop.service.ObterUsuarioPOPResponse;
import br.org.ons.portal.config.pop.service.UsuarioPOP;

@RunWith(PowerMockRunner.class)
@PrepareForTest(POPClient.class)
public class POPClientUnitTest {

	private POPClient popClient = new POPClient("serviceUrl", "domain", "username", "password");
	
	@Test
	public void ntlmConnect() throws Exception {
		HttpURLConnection connection = mock(HttpURLConnection.class);
		when(connection.getInputStream()).thenReturn(IOUtils.toInputStream("AAAAA"));
		URL url = mock(URL.class);
		when(url.openConnection()).thenAnswer(invocation -> {
			Authenticator.requestPasswordAuthentication(null, 0, null, null, null);
			return connection;
		});
        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        
		assertThat(popClient.ntlmConnect()).isNotNull().isEqualTo("AAAAA");
	}
	
	@Test(expected=OnsRuntimeException.class)
	public void ntlmConnectException() throws Exception {
		URL url = mock(URL.class);
		when(url.openConnection()).thenThrow(new IOException());
        whenNew(URL.class).withArguments(anyString()).thenReturn(url);
        
		assertThat(popClient.ntlmConnect()).isNotNull().isEqualTo("AAAAA");
	}
	
	@Test
	public void getUser() {
		WebServiceTemplate webServiceTemplate = mock(WebServiceTemplate.class);
		
		ObterUsuarioPOPResponse usuario = new ObterUsuarioPOPResponse();
		usuario.setObterUsuarioPOPResult(new UsuarioPOP());
		usuario.getObterUsuarioPOPResult().setLogin("login");
		usuario.getObterUsuarioPOPResult().setNome("nome");
		usuario.getObterUsuarioPOPResult().setEmail("email");
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterUsuarioPOP.class), any()))
				.thenReturn(usuario);
		
		ObterAcessosResponse acessos = new ObterAcessosResponse();
		acessos.setObterAcessosResult(new ArrayOfEscopoDTO());
		EscopoDTO escopo = new EscopoDTO();
		escopo.setPermissaoLista(new ArrayOfstring());
		escopo.getPermissaoLista().getString().add("ADMIN");
		acessos.getObterAcessosResult().getEscopoDTO().add(escopo);
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterAcessos.class), any()))
				.thenReturn(acessos);
		
		ReflectionTestUtils.setField(popClient, "webServiceTemplate", webServiceTemplate);
		
		assertThat(popClient.getUser("cookie"))
				.isNotNull()
				.hasFieldOrPropertyWithValue("login", "login")
				.hasFieldOrPropertyWithValue("firstName", "nome")
				.hasFieldOrPropertyWithValue("email", "email")
				.matches(u -> u.getAuthorities().contains("ADMIN"));
	}
	
	@Test
	public void getUserFirstNameLastName() {
		WebServiceTemplate webServiceTemplate = mock(WebServiceTemplate.class);
		
		ObterUsuarioPOPResponse usuario = new ObterUsuarioPOPResponse();
		usuario.setObterUsuarioPOPResult(new UsuarioPOP());
		usuario.getObterUsuarioPOPResult().setLogin("login");
		usuario.getObterUsuarioPOPResult().setNome("nome sobrenome");
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterUsuarioPOP.class), any()))
		.thenReturn(usuario);
		
		ObterAcessosResponse acessos = new ObterAcessosResponse();
		acessos.setObterAcessosResult(new ArrayOfEscopoDTO());
		EscopoDTO escopo = new EscopoDTO();
		escopo.setPermissaoLista(new ArrayOfstring());
		acessos.getObterAcessosResult().getEscopoDTO().add(escopo);
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterAcessos.class), any()))
		.thenReturn(acessos);
		
		ReflectionTestUtils.setField(popClient, "webServiceTemplate", webServiceTemplate);
		
		assertThat(popClient.getUser("cookie"))
		.isNotNull()
		.hasFieldOrPropertyWithValue("login", "login")
		.hasFieldOrPropertyWithValue("firstName", "nome")
		.hasFieldOrPropertyWithValue("lastName", "sobrenome");
	}
	
	@Test
	public void getUserException() {
		WebServiceTemplate webServiceTemplate = mock(WebServiceTemplate.class);

		when(webServiceTemplate.marshalSendAndReceive(isA(ObterUsuarioPOP.class), any()))
				.thenThrow(mock(SoapFaultClientException.class));

		ReflectionTestUtils.setField(popClient, "webServiceTemplate", webServiceTemplate);

		assertThat(popClient.getUser("cookie"))
				.isNull();
	}

	@Test
	public void getUserNull() {
		WebServiceTemplate webServiceTemplate = mock(WebServiceTemplate.class);

		ObterUsuarioPOPResponse usuario = new ObterUsuarioPOPResponse();
		usuario.setObterUsuarioPOPResult(null);
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterUsuarioPOP.class), any()))
				.thenReturn(usuario);
		
		ObterAcessosResponse acessos = new ObterAcessosResponse();
		acessos.setObterAcessosResult(new ArrayOfEscopoDTO());
		EscopoDTO escopo = new EscopoDTO();
		escopo.setPermissaoLista(new ArrayOfstring());
		acessos.getObterAcessosResult().getEscopoDTO().add(escopo );
		when(webServiceTemplate.marshalSendAndReceive(isA(ObterAcessos.class), any()))
				.thenReturn(acessos);
		
		ReflectionTestUtils.setField(popClient, "webServiceTemplate", webServiceTemplate);

		assertThat(popClient.getUser("cookie"))
				.isNull();
	}
}
