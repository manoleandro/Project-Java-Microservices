package br.org.ons.portal.cucumber.UC02403;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.webdriverextensions.WebDriverExtensionsContext;
import com.github.webdriverextensions.internal.junitrunner.DriverPathLoader;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class Cucumber {

	// private static Logger log = LogManager.getLogger(ConsultarTaxaRefFiltroTipoInstalacaoStepDefs.class);

//		@Autowired
//		EmbeddedWebApplicationContext server;

//		@LocalServerPort
		int port = 8078;

		private static WebDriver dr;

//		@Before
//		public void setup() {
//
////			System.setProperty("webdriver.gecko.driver", "C:\\IBM\\geckodriver.exe");
//			System.setProperty("webdriver.chrome.driver", "C:\\IBM\\chromedriver.exe");
////			dr = new FirefoxDriver();
//			dr = new ChromeDriver();
//
//	        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//	        ChromeOptions options = new ChromeOptions();
//	        options.addArguments("data:text/html;charset=utf-8");
//	        //addArguments("--disable-web-security");
//	        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
////	        dr = new ChromeDriver();
//			
//			// Configuração para esperar a tela completar por no  máximo 10s
//			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//		}
		
		@Before
		public void setup() {
	        DriverPathLoader.loadDriverPaths(null);
			dr = new ChromeDriver();
			// Configuração para esperar a tela completar por no  máximo 10s
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebDriverExtensionsContext.setDriver(dr);
		}

	    @After
	    public void cleanUp(){
	    	dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        dr.quit();
	        WebDriverExtensionsContext.removeDriver();
	    }

//		--------------------------------------------------------------------------------------------
//			CN01 - Validar acesso.feature
//		--------------------------------------------------------------------------------------------
		
		@Quando("^eu me autentico com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
		public void eu_me_autentico_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
		    this.que_eu_esteja_autenticado_com_usuário_e_perfil(arg1, arg2);
		}
		
		@Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
		public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
			
			dr.get("http://localhost:"+port+"/onsportal");
			
			dr.findElement(By.cssSelector(".botaoTopoLogin")).click();
			WebElement myDynamicElement2 = (new WebDriverWait(dr, 25))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
			myDynamicElement2.sendKeys(arg1);
			dr.findElement(By.cssSelector("input#password")).sendKeys("user");
			dr.findElement(By.cssSelector("button[type=submit].btn-primary")).click();
		}
		
		@Dado("^a funcionalidade \"([^\"]*)\" deveria estar disponível$")
		public void a_funcionalidade_deveria_estar_disponível(String arg1) throws Throwable {
			//TODO Utilizado no Valida Usuário. já implementado pelo UC_2402

			if(arg1.equals("Consultar disponibilidades")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				WebElement menu = dr.findElement(By.cssSelector(".menuTopoBar > ul > li > ul"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu);
				WebElement menu2 = dr.findElement(By.cssSelector("li > #menuSager"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu2);
//				 lin = Assert.assertTrue(dr.findElement(By.cssSelector("a[href*='#/disp']")).isDisplayed());
				boolean link = dr.findElement(By.cssSelector("a[href*='#/disp']")).isDisplayed();

				if(link == true){
					dr.get("http://localhost:"+port+"/onsportal/#/disp");
					Assert.assertTrue(dr.findElement(By.id("filters")).isDisplayed());
				}

			}	
			
			
		}
		
		@Então("^a funcionalidade \"([^\"]*)\" deveria estar indisponível$")
		public void a_funcionalidade_deveria_estar_indisponível(String arg1) throws Throwable {
			if(arg1.equals("Consultar disponibilidades")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				WebElement menu = dr.findElement(By.cssSelector(".menuTopoBar > ul > li > ul"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu);
				WebElement menu2 = dr.findElement(By.cssSelector("li > #menuSager"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu2);
//				 lin = Assert.assertTrue(dr.findElement(By.cssSelector("a[href*='#/disp']")).isDisplayed());
				boolean link = dr.findElement(By.cssSelector("a[href*='#/disp']")).isDisplayed();

				if(link == false){
					dr.get("http://localhost:"+port+"/onsportal/#/disp");
					Assert.assertTrue(dr.findElement(By.cssSelector("h1[translate='error.title']")).isDisplayed());
				}

			}
		}
		
//		--------------------------------------------------------------------------------------------
//		CN02 - Vefif seleção filtros.feature
//		--------------------------------------------------------------------------------------------
		
		@Dado("^eu esteja na página \"([^\"]*)\"$")
		public void eu_esteja_na_página(String arg1) throws Throwable {
			if(arg1.equals("Consultar disponibilidades")){
				dr.get("http://localhost:"+port+"/onsportal/#/disp");

			    WebElement element = dr.findElement(By.id("filters"));
			    JavascriptExecutor executor = (JavascriptExecutor)dr;
			    executor.executeScript("arguments[0].click();", element);
				Assert.assertTrue(dr.findElement(By.id("filters")).isDisplayed());
			}
		}
		@Dado("^que eu esteja na página \"([^\"]*)\"$")
		public void que_eu_esteja_na_página(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			if(arg1.equals("Consultar disponibilidades")){
				dr.get("http://localhost:"+port+"/onsportal/#/disp");

			    WebElement element = dr.findElement(By.id("filters"));
			    JavascriptExecutor executor = (JavascriptExecutor)dr;
			    executor.executeScript("arguments[0].click();", element);
				Assert.assertTrue(dr.findElement(By.id("filters")).isDisplayed());
			}
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions id="dataInicial"
			
			if("Data Inicial".equalsIgnoreCase(arg2)){
				arg2 = "field_startDate";
				
				WebElement searchField = dr.findElement(By.id(arg2));
				JavascriptExecutor js = (JavascriptExecutor) dr;
				js.executeScript("arguments[0].value=''",searchField);
				searchField.sendKeys(arg1);
			}
			
			if("Data Final".equalsIgnoreCase(arg2)){
				arg2 = "field_endDate";
				
				WebElement searchField = dr.findElement(By.id(arg2));
				JavascriptExecutor js = (JavascriptExecutor) dr;
				js.executeScript("arguments[0].value=''",searchField);
				searchField.sendKeys(arg1);
				dr.findElement(By.id("id_startDate")).click();
			}
		}

		@Quando("^eu seleciono a opção \"([^\"]*)\" para o campo \"([^\"]*)\"$")
		public void eu_seleciono_a_opção_para_o_campo(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.findElement(By.id(arg1));
			js.executeScript("document.getElementById('"+arg1+"').click();");

		}


		@Quando("^eu aperto a guia \"([^\"]*)\"$")
		public void eu_aperto_a_guia(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			JavascriptExecutor js = (JavascriptExecutor) dr;
		    try{
				if("usinas".equals(arg1.toLowerCase())){
					dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					dr.findElement(By.id("abaUsina"));
			    	js.executeScript("document.getElementById('abaUsina').click();");
			    	
			    	
			    }else if("interligações internacionais".equals(arg1.toLowerCase())){
			    	dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					dr.findElement(By.id("abaInterligacoesInternacionais"));
			    	js.executeScript("document.getElementById('abaInterligacoesInternacionais').click();");
			    }
		    }catch(Exception e){
		    	Assert.fail();
		    }
		}

		@Quando("^eu seleciono o item \"([^\"]*)\" na lista \"([^\"]*)\"$")
		public void eu_seleciono_o_item_na_lista(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			arg1 = arg1.replaceAll("\\.", "");
			arg1 = arg1.replaceAll("\\s+","");
			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨" + arg1);
			dr.findElement(By.id(arg1));
			WebElement usina = dr.findElement(By.cssSelector("md-checkbox[id='"+arg1+"']"));
			
			try{
				js.executeScript("arguments[0].click();",usina);
			}catch(Exception e){
				usina.click();
			}
		}

		@Quando("^eu aperto o botão \"([^\"]*)\"$")
		public void eu_aperto_o_botão(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			
			JavascriptExecutor js = (JavascriptExecutor) dr;
			js.executeScript("document.getElementById('"+arg1.toLowerCase()+"').click();");
//			dr.findElement(By.id("pesquisar")).click();

//			boolean erro = dr.findElement(By.id("disponibilidades")).isDisplayed();
//			if(erro != true){
//				WebElement we = this.dr.findElement(By.id("disponibilidades"));
//				JavascriptExecutor executor = (JavascriptExecutor) dr;
//				executor.executeScript("arguments[0].click();", we);
//			}
//			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨" + erro);

		}

		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\" com valor \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid_com_valor(int arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//			if(arg3.equals("Equipamento: RJUSCP_13P8_UG1; Tipo Disponibilidade: Operacional")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_O")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 1 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//				
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Operacional")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_O")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 2 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG1; Tipo Disponibilidade: Comercial")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_C")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 2 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Comercial")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_C")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 2 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG1; Tipo Disponibilidade: Eletromecânica")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_E")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 2 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG2; Tipo Disponibilidade: Eletromecânica")){
//				int valor = 0;
//				valor = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_E")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 2 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(valor);
//				if(valor > 0){
//					Assert.assertTrue(valor==arg1);
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG1")){
//				int valor_O  = 0;
//				int valor_C  = 0;
//				int valor_E  = 0;
//				valor_O = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_O")).size();
//				valor_C = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_C")).size();
//				valor_E = dr.findElements(By.cssSelector(".UG13MWUSICAMPOS1RJ_E")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 3 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(arg3);
//				if(valor_O > 0 && valor_C > 0 && valor_E > 0){
//					Assert.assertTrue(valor_O==arg1 && valor_C==arg1 && valor_E==arg1);
//				}else{
//					System.out.println("¨¨¨¨¨VALOR NAO ENCONTRADO O="+valor_O+" C="+valor_C+" E="+valor_E+"¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				}
//			}else if(arg3.equals("Equipamento: RJUSCP_13P8_UG2")){
//				int valor_O  = 0;
//				int valor_C  = 0;
//				int valor_E  = 0;
//				valor_O = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_O")).size();
//				valor_C = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_C")).size();
//				valor_E = dr.findElements(By.cssSelector(".UG12MWUSICAMPOS2RJ_E")).size();
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨ENTRO NO 4 IF¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(arg3);
//				if(valor_O > 0 && valor_C > 0 && valor_E > 0){
//					Assert.assertTrue(valor_O==arg1 && valor_C==arg1 && valor_E==arg1);
//				}else{
//					System.out.println("¨¨¨¨¨VALOR NAO ENCONTRADO O="+valor_O+" C="+valor_C+" E="+valor_E+"¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				}
//			}
//			else{
//				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨FALTA IMPLEMENTAR¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//				System.out.println(arg3);
//			}

			int valor = 0;
			valor = dr.findElements(By.cssSelector("."+arg3+"_O")).size();
			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
			System.out.println(valor);
			if(valor > 0){
				Assert.assertTrue(valor==arg1);
			}
		}
		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\" com valor Equipamento: \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid_com_valor_Equipamento(int arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			int valor = 0;
			valor = dr.findElements(By.cssSelector("."+arg3+"_O")).size();
			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
			System.out.println(valor);
			if(valor > 0){
				Assert.assertTrue(valor==arg1);
			}
		}
		
		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\" com valor Equipamento: \"([^\"]*)\" ; Tipo Disponibilidade: \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid_com_valor_Equipamento_Tipo_Disponibilidade(int arg1, String arg2, String arg3, String arg4) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			int valor = 0;
			if(arg4.equals("Operacional")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_O")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO OP¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨"+arg3);
				System.out.println(valor);
			}else if(arg4.equals("Comercial")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_C")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO CO¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
				System.out.println(valor);
			}else if(arg4.equals("Eletromecânica")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_E")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO EL¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
				System.out.println(valor);
			}
			if(valor > 0){
				Assert.assertTrue(valor==arg1);
			}
		}
		
		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\" com valor Equipamento: \"([^\"]*)\" ; Tipo: \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid_com_valor_Equipamento_Tipo(int arg1, String arg2, String arg3, String arg4) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			int valor = 0;
			if(arg4.equals("Operacional")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_O")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO OP¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨"+arg3);
				System.out.println(valor);
			}else if(arg4.equals("Comercial")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_C")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO CO¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
				System.out.println(valor);
			}else if(arg4.equals("Eletromecânica")){
				valor = dr.findElements(By.cssSelector("."+arg3+"_E")).size();
				System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR LOCALIZADO EL¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
				System.out.println(valor);
			}
			if(valor > 0){
				Assert.assertTrue(valor==arg1);
			}
		}
		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\" com \"([^\"]*)\" entre \"([^\"]*)\" e \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid_com_entre_e(int arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			
			int valorData = 0;
			int count = 0; 
//			if(arg4.equals("01/07/2001 00:00") && arg5.equals("09/07/2001 23:00")){
//				valorData = dr.findElements(By.cssSelector(".DATA01072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA02072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA03072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA04072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA05072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA06072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA07072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA08072001")).size();
//				valorData = valorData + dr.findElements(By.cssSelector(".DATA09072001")).size();
//			}
//			count = dr.findElements(By.cssSelector("md-tab-item")).size();
//			valorData = valorData / count;
//			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR = "+valorData+" VALOR = " + count +"¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
//			Assert.assertTrue(valorData==arg1);
//			
			String idData = "";
			SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date startDate = formatterDate.parse(arg4);
			Date endDate = formatterDate.parse(arg5);
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);
			
			SimpleDateFormat formatterString = new SimpleDateFormat("ddMMyyyy");
			
			for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
				idData = ".DATA" + formatterString.format(date);
				valorData = valorData + dr.findElements(By.cssSelector(idData)).size();
				System.out.println(idData);
			}
			
			count = dr.findElements(By.cssSelector("md-tab-item")).size();
			valorData = valorData / count;
			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨VALOR = "+valorData+" VALOR = " + count +"¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨");
			Assert.assertTrue(valorData==arg1);

		}
		
		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			boolean link = dr.findElement(By.id("mensagemErro")).isDisplayed();
			if(link == true){
				String mensagem = dr.findElement(By.cssSelector(".codigoErro")).getText();
				System.out.println("A mensagem que apareceu foi.... " + mensagem );
			}
		}

		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions

			boolean link = dr.findElement(By.id("mensagemErro")).isDisplayed();
			
			if(link == true){
				String mensagem = dr.findElement(By.cssSelector(".codigoErro")).getText();
				System.out.println("A mensagem que apareceu foi.... " + mensagem );
			}
		}
		
		@Então("^eu deveria ver a mensagem de aviso de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_aviso_de_código_e_crítica(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			boolean link = dr.findElement(By.id("mensagemErro")).isDisplayed();
			
			if(link == true){
				String mensagem = dr.findElement(By.cssSelector(".codigoErro")).getText();
				System.out.println("A mensagem que apareceu foi.... " + mensagem );
			}
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" no campo \"([^\"]*)\" da guia \"([^\"]*)\"$")
		public void eu_informo_o_valor_no_campo_da_guia(String arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			
			if(arg3.equals("Usinas")){
				if(arg2.equals("Nome")){
				    WebElement element = dr.findElement(By.id("instalacoes"));
				    JavascriptExecutor executor = (JavascriptExecutor)dr;
				    executor.executeScript("arguments[0].click();", element);
					WebElement searchField = dr.findElement(By.id("buscaUsinaPornome"));
					dr.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					JavascriptExecutor js = (JavascriptExecutor) dr;
					js.executeScript("arguments[0].value=''",searchField);
					searchField.sendKeys(arg1);
					executor.executeScript("arguments[0].click();", element);
				}
			}

		}
		@Então("^eu deveria ver (\\d+) itens na lista \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_na_lista(int arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			if(arg2.equals("Usinas")){
				int rowCount = dr.findElements(By.xpath("//table[@id='tableUsinas']/tbody/tr")).size();
				Assert.assertTrue(rowCount==arg1);
			}
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\" da guia \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo_da_guia(String arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			this.eu_informo_o_valor_no_campo_da_guia(arg1, arg2, arg3);
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\" na guia \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo_na_guia(String arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			this.eu_informo_o_valor_no_campo_da_guia(arg1, arg2, arg3);
		}
		
		@Então("^eu deveria ver (\\d+) itens selecionados na lista \"([^\"]*)\" com valor \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_selecionados_na_lista_com_valor(int arg1, String arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			if(arg2.equals("Usinas")){
			    WebElement element = dr.findElement(By.id("instalacoes"));
			    JavascriptExecutor executor = (JavascriptExecutor)dr;
			    executor.executeScript("arguments[0].click();", element);
			    
				int rowCount = dr.findElements(By.xpath("//md-chip")).size();
				boolean nome = dr.findElement(By.xpath("//*[contains(text(), 'U.SOBRADINHO')]")).isDisplayed();
//				System.out.println("A mensagem que apareceu foi.... " + nome );
//				System.out.println("O count das linhas foi.... " + rowCount + " e o valor deveria ser ...... " + arg1 );
				

				Assert.assertTrue(rowCount==arg1 && nome==true);
			}
		}
		
		@Quando("^eu seleciono todos os itens na lista \"([^\"]*)\"$")
		public void eu_seleciono_todos_os_itens_na_lista(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions    
			if(arg1.equals("Usinas")){
			    WebElement element = dr.findElement(By.id("selecionarTodasUsinas"));
			    JavascriptExecutor executor = (JavascriptExecutor)dr;
			    executor.executeScript("arguments[0].click();", element);			    
			}
		}
		
}
