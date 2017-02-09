package br.org.ons.sager.parametrizacao.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.org.ons.sager.parametrizacao.OnsSagerParametrizacaoApp;
import br.org.ons.sager.parametrizacao.domain.RetificacoesParam;
import br.org.ons.sager.parametrizacao.repository.RetificacoesParamRepository;
import br.org.ons.sager.parametrizacao.service.RetificacoesParamService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerParametrizacaoApp.class)
public class ParametrizacaoResourceTest {

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restParametrizacaoMockMvc;

	@Inject
	private RetificacoesParamService parametrizacaoService;

	@Inject
	private RetificacoesParamRepository retificacoesParamRepository;

	@PostConstruct
	public void setup() {
		
		ParametrizacaoResource parametrizacaoResource = new ParametrizacaoResource(parametrizacaoService);
		
		this.restParametrizacaoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
		
	}

	@Before
	public void initTest() {
		retificacoesParamRepository.deleteAll();
		
		LocalTime localTime = LocalTime.of(12, 00, 00, 00);
		localTime = LocalTime.of(11, 00, 00, 00);
		
		SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
		
		RetificacoesParam retificacao = new RetificacoesParam();
			retificacao.setDia(14);
			retificacao.setHora(localTime);

		RetificacoesParam retificacao2 = new RetificacoesParam();
			retificacao2.setDia(18);
			retificacao2.setHora(localTime);
		
		retificacoesParamRepository.save(retificacao);
		retificacoesParamRepository.save(retificacao2);
		
	}

	/**
	 * Caso de teste - Criar retificação informando dia e hora.
	 * Resultado esperado: Criar uma retificação.
	 * @throws Exception
	 */
	@Test
	public void ct010004() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
							.content("{\"dia\":\"1\",\"hora\":\"01:00:00\"}")
							.contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isCreated());
		
		assertThat(retificacoesParamRepository.count() == 3).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));

	}
	
	/**
	 * Caso de teste - Criar retificação informando dia.
	 * Resultado esperado: Não criar a retificação. (400)
	 * @throws Exception
	 */
	@Test
	public void ct010005() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
							.content("{\"dia\":\"1\"}")
							.contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isBadRequest());
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));

	}
	
	/**
	 *  Caso de teste - Criar retificação informando hora.
	 * Resultado esperado: Não criar a retificação. (400)
	 * @throws Exception
	 */
	@Test
	public void ct010006() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
							.content("{\"hora\":\"01:00:00\"}")
							.contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(status().isBadRequest());
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));

	}
	
	/**
	 * Caso de teste - Criar duas retificações com o mesmo valor.
	 * Resultado esperado: Criar a primeirae não criar a segunda retificação. (201) (400)
	 * @throws Exception
	 */
	@Test
	public void ct010007() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
					.content("{\"dia\":\"1\",\"hora\":\"01:00:00\"}")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isCreated());

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
					.content("{\"dia\":\"1\",\"hora\":\"01:00:00\"}")
					.contentType(MediaType.APPLICATION_JSON_UTF8))
					.andExpect(status().isBadRequest());
		
		assertThat(retificacoesParamRepository.count() == 3).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));

	}
	
	/**
	 * Caso de teste - Deletar retificação com id certo.
	 * Resultado esperado: Deletar a retificação id.
	 * @throws Exception
	 */
	@Test
	public void ct010008() throws Exception {

		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(delete("/api/parametrizacao-retificacoes/"+content.getId()))
				.andExpect(status().isOk());
		
		assertThat(retificacoesParamRepository.count() == 1).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	/**
	 * Caso de teste - Deletar retificação com id errado.
	 * Resultado esperado: Não deletar a retificação. (404)
	 * @throws Exception
	 */
	@Test
	public void ct010009() throws Exception {

		restParametrizacaoMockMvc.perform(delete("/api/parametrizacao-retificacoes/TESTE"))
				.andExpect(status().isNotFound());
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	/**
	 * Caso de teste - Deletar retificação com id null.
	 * Resultado esperado: Não deletar a retificação. (404)
	 * @throws Exception
	 */
	@Test
	public void ct010010() throws Exception {

		restParametrizacaoMockMvc.perform(delete("/api/parametrizacao-retificacoes/" + null))
				.andExpect(status().isNotFound());
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	
	/**
	 * Caso de teste - Atualizar retificação com hora, dia e id.
	 * Resultado esperado: Alterar a retificação do id informado.
	 * @throws Exception
	 */
	@Test
	public void ct010011() throws Exception {
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"1\",\"hora\":\"01:00:00\",\"id\":\""+content.getId()+"\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	
	/**
	 * Caso de teste - Atualizar retificação com dia e id.
	 * Resultado esperado:
	 * @throws Exception 
	 */
	@Test
	public void ct010012() throws Exception {
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"1\",\"id\":\""+content.getId()+"\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	
	/**
	 * Caso de teste - Atualizar retificação com hora e id.
	 * Resultado esperado:
	 * @throws Exception 
	 */
	@Test
	public void ct010013() throws Exception {
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"hora\":\"01:00:00\",\"id\":\""+content.getId()+"\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	
	/**
	 * Caso de teste - Atualizar retificação com hora e dia.
	 * Resultado esperado:
	 * @throws Exception 
	 */
	@Test
	public void ct010014() throws Exception {
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"1\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
		
	}
	
	
	/**
	 * Caso de teste - Duplicar atualizar retificação com hora e dia.
	 * Resultado esperado:
	 * @throws Exception
	 */
	@Test
	public void ct010015() throws Exception {
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"1\",\"hora\":\"01:00:00\",\"id\":\""+content.getId()+"\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		
		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"1\",\"hora\":\"01:00:00\",\"id\":\""+content.getId()+"\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(1, LocalTime.of(01, 00, 00, 00)));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(14, LocalTime.of(12, 00, 00, 00)));
	}
	
	
	/**
	 * Caso de teste - Selecionar uma retificação informando id.
	 * Resultado esperado: Retornar a retificação do id informado.
	 * @throws Exception
	 */
	@Test
	public void ct010016() throws Exception {
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		ResultActions response = restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes/"+content.getId()))
				.andExpect(status().isOk());
		String responseReturn = response.andReturn().getResponse().getContentAsString();

		String[] arrayTeste = responseReturn.split(",");
		String valorDia = arrayTeste[1].split(":")[1];
		String valorHora = arrayTeste[2].split("\"")[3];

		RetificacoesParam retificacao = new RetificacoesParam();

		LocalTime localTime = LocalTime.parse(valorHora);
		retificacao.setDia(Integer.parseInt(valorDia));
		retificacao.setHora(localTime);
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(retificacao.getDia(), retificacao.getHora()));
	}
	
	/**
	 * Caso de teste - Selecionar uma retificação informando id errado.
	 * Resultado esperado: Retornar erro na pesquisa. (404)
	 * @throws Exception
	 */
	@Test
	public void ct010017() throws Exception {
		
		String content = "teste";
		
		restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes/" + content))
				.andExpect(status().isNotFound());
		
		assertThat(retificacoesParamRepository.count() == 2).isTrue();

	}
	
	/** 
	 * Caso de teste - Selecionar uma retificação informando id null.
	 * Resultado esperado: Retornar todos as requisições.
	 * @throws Exception
	 */
	@Test
	public void ct010018() throws Exception {
		
		ResultActions response = restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes/"))
				.andExpect(status().isOk());
		
		String responseReturn = response.andReturn().getResponse().getContentAsString();
		System.out.println(responseReturn);
		String[] arrayTeste1 = responseReturn.split("}");
		arrayTeste1 = responseReturn.split(",");
		String valorDia1 = arrayTeste1[1].split(":")[1];
		String valorHora1 = arrayTeste1[2].split("\"")[3];
		
		String[] arrayTeste2 = responseReturn.split("}");
		arrayTeste2 = responseReturn.split(",");
		String valorDia2 = arrayTeste2[4].split(":")[1];
		String valorHora2 = arrayTeste2[5].split("\"")[3];

		RetificacoesParam retificacao1 = new RetificacoesParam();
		RetificacoesParam retificacao2 = new RetificacoesParam();

		LocalTime localTime1 = LocalTime.parse(valorHora1);
		retificacao1.setDia(Integer.parseInt(valorDia1));
		retificacao1.setHora(localTime1);
		
		LocalTime localTime2 = LocalTime.parse(valorHora2);
		retificacao2.setDia(Integer.parseInt(valorDia2));
		retificacao2.setHora(localTime2);
		
		assertThat(retificacoesParamRepository.count() == 2);
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(retificacao1.getDia(), retificacao1.getHora()));
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(retificacao2.getDia(), retificacao2.getHora()));
	}
	
	/**
	 * Caso de teste - Testar paginação da retificação.
	 * Resultado esperado: Verificar o numero fornecido para a paginação.
	 * @throws Exception
	 */
	@Test
	public void ct010019() throws Exception {
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"2\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"3\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"4\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"5\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"6\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"7\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"8\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"9\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"10\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"11\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());
		
		RetificacoesParam content = retificacoesParamRepository.findOneByDiaAndHora(18, LocalTime.of(11, 00, 00, 00));
		
		restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes?&page=1&size=5&sort=dia,asc&sort=hora"))
				.andExpect(status().isOk());
		
		restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes?&page=2&size=5&sort=dia,asc&sort=hora"))
				.andExpect(status().isOk());
		
		restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes?&page=1&size=5&sort=dia,asc&sort=hora"))
				.andExpect(status().isOk());
		
		restParametrizacaoMockMvc.perform(get("/api/parametrizacao-retificacoes/"+content.getId()))
				.andExpect(status().isOk());
		
		assertThat(retificacoesParamRepository.count() == 12).isTrue();

	}
	
	/**
	 * Caso de teste - Deletar retificação sem nenhuma no repositorio.
	 * Resultado esperado: Apresentar exceção.
	 * @throws Exception
	 */
	@Test
	public void ct010023() throws Exception {

		retificacoesParamRepository.deleteAll();
		
		restParametrizacaoMockMvc.perform(delete("/api/parametrizacao-retificacoes/TESTE"))
				.andExpect(status().isBadRequest());
		
		assertThat(retificacoesParamRepository.count() == 0).isTrue();

		
	}
	
	/**
	 * Caso de teste - Criar retificação informando dia e hora.
	 * Resultado esperado: Criar uma retificação.
	 * @throws Exception
	 */
	@Test
	public void ct010024() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"id\":\"TESTE\",\"dia\":\"1\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	
	/**
	 * Caso de teste - Atualizar um registro inexistente.
	 * Resultado esperado: NOT_FOUND.
	 * @throws Exception
	 */
	@Test
	public void ct010025() throws Exception {

		restParametrizacaoMockMvc.perform(put("/api/parametrizacao-retificacoes")
				.content("{\"id\":\"TESTE\",\"dia\":\"1\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	/**
	 * Caso de teste - Criar um registro com dia < 0.
	 * Resultado esperado: BAD_REQUEST.
	 * @throws Exception
	 */
	@Test
	public void ct010026() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"-1\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	/**
	 * Caso de teste - Criar um registro com dia > 18.
	 * Resultado esperado: BAD_REQUEST.
	 * @throws Exception
	 */
	@Test
	public void ct010027() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"19\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	/**
	 * Caso de teste - Criar um registro com dia 0, hora 0 e min <> 0.
	 * Resultado esperado: BAD_REQUEST.
	 * @throws Exception
	 */
	@Test
	public void ct010028() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"0\",\"hora\":\"00:01:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	/**
	 * Caso de teste - Criar um registro com dia 0, hora <> 0 e min 0.
	 * Resultado esperado: BAD_REQUEST.
	 * @throws Exception
	 */
	@Test
	public void ct010029() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"0\",\"hora\":\"01:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isBadRequest());

		assertThat(retificacoesParamRepository.count() == 2).isTrue();
		
	}
	
	/**
	 * Caso de teste - Criar um registro com dia 0, hora 0 e min 0.
	 * Resultado esperado: Criar uma retificação.
	 * @throws Exception
	 */
	@Test
	public void ct010030() throws Exception {

		restParametrizacaoMockMvc.perform(post("/api/parametrizacao-retificacoes")
				.content("{\"dia\":\"0\",\"hora\":\"00:00:00\"}")
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated());

		assertThat(retificacoesParamRepository.count() == 3).isTrue();
		assertThat(retificacoesParamRepository.findOneByDiaAndHora(0, LocalTime.of(00, 00, 00, 00)));
		
	}
	
	@After
	public void exitTest() {
		retificacoesParamRepository.deleteAll();
	}

}
