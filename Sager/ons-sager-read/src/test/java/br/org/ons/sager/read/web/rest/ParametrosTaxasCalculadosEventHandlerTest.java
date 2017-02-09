
package br.org.ons.sager.read.web.rest;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.org.ons.sager.read.OnsSagerReadApp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OnsSagerReadApp.class)
public class ParametrosTaxasCalculadosEventHandlerTest {
//    
//	@Inject
//	private ParametrosTaxasCalculadosSTGService parametrosTaxasCalculadosEventHandler;
//	
//	@Inject
//	private ParametrosTaxasCalculadosSTGRepository parametrosTaxasCalculadosRepository;
//	
//	@Inject
//	private EquipamentoCadastradoRepository equipamentoCadastradoRepository;
//	
//	@Inject
//	private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//	@Inject
//	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//	private MockMvc restCalculoMockMvc;
//
//	@PostConstruct
//	public void setup() {
//
//	}     
//	
//	@Before
//	public void initTest() {
//		ParametrosTaxasCalculadosSTGService parametrizacaoResource = new ParametrosTaxasCalculadosSTGService(parametrosTaxasCalculadosRepository,equipamentoCadastradoRepository);
//		this.restCalculoMockMvc = MockMvcBuilders.standaloneSetup(parametrizacaoResource)
//				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
//				.build();
//		SecurityContextHolder.getContext()
//				.setAuthentication(new UsernamePasswordAuthenticationToken("cnos", ""));
//	}
//	
//	@Test
//	public void inserirTaxasCalculos() throws Exception {
//
//		Date data = new Date("2016/11/28 03:00:00");
//		BigDecimal denominador = new BigDecimal("12");
//
//		List<String> idsEventos = new ArrayList<>();
//			idsEventos.add("evento01");
//		
//		ParametroTaxa param = new ParametroTaxa();
//			param.setAno(2001);
//			param.setCodigo("cod01");
//			param.setDenominador(denominador);
//			param.setDescricao("desc01");
//			param.setId("id01");
//			param.setIdEquipamento("idEquip01");
//			param.setIdsEventos(idsEventos);
//			param.setMes(12);
//			param.setNumerador(denominador);
//			param.setValor(denominador);
//			param.setVersao("01");
//			
//		List<ParametroTaxa> parametros = new ArrayList<>();
//			parametros.add(param);
//			
//		
//		ParametrosTaxasCalculadosEvent event = new ParametrosTaxasCalculadosEvent();	
//			event.setDataInicioApuracao(data);
//			event.setParametros(parametros);
//			
//			
//		EventMetadata metadata= new EventMetadata();
//			metadata.setAggregateId("idUsina01");
//			
//		EventMessage<ParametrosTaxasCalculadosEvent> eventMessage = new EventMessage<>();	
//			eventMessage.setEvent(event);
//			eventMessage.setMetadata(metadata);
//			
//			parametrosTaxasCalculadosEventHandler.handleParametrosTaxasCalculadosEvent(eventMessage);
//			parametrosTaxasCalculadosEventHandler.handleParametrosTaxasCalculadosEvent(eventMessage);
//	}
//		
//	@Test
//	public void inserirTaxasCalculosInvalidas() throws Exception {
//		Date data = new Date("2016/11/28 03:00:00");
//		BigDecimal denominador = new BigDecimal("12");
//
//		List<String> idsEventos = new ArrayList<>();
//			idsEventos.add("evento01");
//		
//		ParametroTaxa param = new ParametroTaxa();
//			param.setCodigo("cod01");
//			param.setDenominador(denominador);
//			param.setDescricao("desc01");
//			param.setId("id01");
//			param.setIdEquipamento("idEquip01");
//			param.setIdsEventos(idsEventos);
//			param.setNumerador(denominador);
//			param.setValor(denominador);
//			param.setVersao("01");
//			
//		List<ParametroTaxa> parametros = new ArrayList<>();
//			parametros.add(param);
//		
//		ParametrosTaxasCalculadosEvent event = new ParametrosTaxasCalculadosEvent();	
//			event.setDataInicioApuracao(data);
//			event.setParametros(parametros);
//			
//			
//		EventMetadata metadata= new EventMetadata();
//			metadata.setAggregateId("idUsina01");
//			
//		EventMessage<ParametrosTaxasCalculadosEvent> eventMessage = new EventMessage<>();	
//			eventMessage.setEvent(event);
//			eventMessage.setMetadata(metadata);
//			
//			parametrosTaxasCalculadosEventHandler.handleParametrosTaxasCalculadosEvent(eventMessage);
//	}
//	
//	@Test
//	public void inserirTaxasCalculosEquipamentoInvalido() throws Exception {
//		Date data = new Date("2016/11/28 03:00:00");
//		BigDecimal denominador = new BigDecimal("12");
//
//		List<String> idsEventos = new ArrayList<>();
//			idsEventos.add("evento01");
//		
//		ParametroTaxa param = new ParametroTaxa();
//			param.setCodigo("cod01");
//			param.setDenominador(denominador);
//			param.setDescricao("desc01");
//			param.setId("id01");
//			param.setIdEquipamento("idEquipInvalido");
//			param.setIdsEventos(idsEventos);
//			param.setNumerador(denominador);
//			param.setValor(denominador);
//			param.setVersao("01");
//			
//		List<ParametroTaxa> parametros = new ArrayList<>();
//			parametros.add(param);
//		
//		ParametrosTaxasCalculadosEvent event = new ParametrosTaxasCalculadosEvent();	
//			event.setDataInicioApuracao(data);
//			event.setParametros(parametros);
//			
//			
//		EventMetadata metadata= new EventMetadata();
//			metadata.setAggregateId("idUsina01");
//			
//		EventMessage<ParametrosTaxasCalculadosEvent> eventMessage = new EventMessage<>();	
//			eventMessage.setEvent(event);
//			eventMessage.setMetadata(metadata);
//			
//			parametrosTaxasCalculadosEventHandler.handleParametrosTaxasCalculadosEvent(eventMessage);
//	}
//	
//	@Test
//	public void inserirTaxasCalculosPotenciaDeCalculoComEquipamentoValido() throws Exception {
//		equipamentoCadastradoRepository.deleteAll();
//		Date data = new Date("2016/11/28 03:00:00");
//		BigDecimal denominador = new BigDecimal("12");
//		
//		Periodo vigencia = new Periodo();
//		vigencia.setDataFim(data);
//		vigencia.setDataInicio(data);
//	
//	PotenciaCalculo potencia = new PotenciaCalculo();
//		potencia.setIdEquipamento("idEquip01");
//		potencia.setValor(10.1);
//		potencia.setVigencia(vigencia);
//	
//	List<PotenciaCalculo> potenciasCalculo = new ArrayList();
//		potenciasCalculo.add(potencia);
//	
//	Periodo periodoValidade = new Periodo();
//		periodoValidade.setDataFim(data);
//		periodoValidade.setDataInicio(data);
//	
//	Periodo periodoVigencia = new Periodo();
//	periodoValidade.setDataFim(data);
//	periodoValidade.setDataInicio(data);
//		
//	Franquia franquia = new Franquia();
//		franquia.setCodigo("cod01");
//		franquia.setIdEquipamento("equip01");
//		franquia.setPeriodoValidade(periodoValidade);
//		franquia.setPeriodoVigencia(periodoVigencia);
//		franquia.setQuantidadeHorasServicoParaUso(12);
//		franquia.setValorDisponivel(13);
//		franquia.setValorLimite(14);
//		franquia.setVersao("01");
//		
//	List<Franquia> franquias = new ArrayList();
//		franquias.add(franquia);
//	
//	List<Periodo> suspensoes = new ArrayList();
//		suspensoes.add(vigencia);
//		
//	EquipamentoCadastrado equip = new EquipamentoCadastrado();
//		equip.setCodid_dpp("codigoDpp01");
//		equip.setCodigoCcee("codigoCcee01");
//		equip.setDataDesativacao(data);
//		equip.setDataEventoEOC(data);
//		equip.setDataImplantacao(data);
//		equip.setDataRenovacaoProrrogacaoConcessao(data);
//		equip.setFranquias(franquias);
//		equip.setId("idEquip01");
//		equip.setIddpp("idDpp01");
//		equip.setIdInstalacao("idInstalacao01");
//		equip.setNome("nome01");
//		equip.setPotenciasCalculo(potenciasCalculo);
//		equip.setQuantidadeHorasServico(12);
//		equip.setSuspensoes(suspensoes);
//		equip.setTipoInstalacao(TipoInstalacao.USINA);
//		equip.setVersao("01");
//		
//	equipamentoCadastradoRepository.save(equip);
//
//		List<String> idsEventos = new ArrayList<>();
//			idsEventos.add("evento01");
//		
//		ParametroTaxa param = new ParametroTaxa();
//			param.setCodigo("cod01");
//			param.setDenominador(denominador);
//			param.setDescricao("desc01");
//			param.setId("id01");
//			param.setIdEquipamento("idEquip01");
//			param.setIdsEventos(idsEventos);
//			param.setNumerador(denominador);
//			param.setValor(denominador);
//			param.setVersao("01");
//			
//		List<ParametroTaxa> parametros = new ArrayList<>();
//			parametros.add(param);
//		
//		ParametrosTaxasCalculadosEvent event = new ParametrosTaxasCalculadosEvent();	
//			event.setDataInicioApuracao(data);
//			event.setParametros(parametros);
//			
//			
//		EventMetadata metadata= new EventMetadata();
//			metadata.setAggregateId("idUsina01");
//			
//		EventMessage<ParametrosTaxasCalculadosEvent> eventMessage = new EventMessage<>();	
//			eventMessage.setEvent(event);
//			eventMessage.setMetadata(metadata);
//			
//			parametrosTaxasCalculadosEventHandler.handleParametrosTaxasCalculadosEvent(eventMessage);
//	}
}

