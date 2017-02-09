package br.org.ons.portal.cucumber.uc02501;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.webdriverextensions.WebDriverExtensionsContext;
import com.github.webdriverextensions.internal.junitrunner.DriverPathLoader;

import cucumber.api.java.After;
//import br.org.ons.portal.cucumber.stepdefs.StepDefs;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dado;

public class ConsultarTaxaRefFiltroTipoInstalacaoStepDefs {//extends StepDefs{

// private static Logger log = LogManager.getLogger(ConsultarTaxaRefFiltroTipoInstalacaoStepDefs.class);

//	@Autowired
//	EmbeddedWebApplicationContext server;

//	@LocalServerPort
	int port = 8078;

	protected WebDriver dr;

	@Before
	public void setup() {
        DriverPathLoader.loadDriverPaths(null);
//		System.setProperty("webdriver.gecko.driver", "C:\\IBM\\geckodriver.exe");
//		System.setProperty("webdriver.chrome.driver", "C:\\IBM\\chromedriver.exe");
//		dr = new FirefoxDriver();
		dr = new ChromeDriver();

//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-web-security");
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        dr = new ChromeDriver();
		
		// Configuração para esperar a tela completar por no  máximo 10s
		dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverExtensionsContext.setDriver(dr);
	}
	
    @After
    public void tearDown() throws Exception {
        dr.quit();
        WebDriverExtensionsContext.removeDriver();
    }
	
//	@Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
//	public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
//		
//		dr.get("http://localhost:"+port+"/onsportal");
//		
//		dr.findElement(By.cssSelector(".botaoTopoLogin")).click();
//		WebElement myDynamicElement2 = (new WebDriverWait(dr, 25))
//				  .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
//		myDynamicElement2.sendKeys(arg1);
//		dr.findElement(By.cssSelector("input#password")).sendKeys("user");
//		dr.findElement(By.cssSelector("button[type=submit].btn-primary")).click();		
//		
//	}
//	@Dado("^a funcionalidade \"([^\"]*)\" deveria estar disponível$")
//	public void a_funcionalidade_deveria_estar_disponível(String arg1) throws Throwable {
//
//		dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
////		JavascriptExecutor js = (JavascriptExecutor) dr;
////		
////		WebElement menu = dr.findElement(By.cssSelector(".menuTopoBar > ul > li > ul"));
////		js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu);
////		WebElement menu2 = dr.findElement(By.cssSelector("li #menuSager"));
////		js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu2);
////	    dr.findElement(By.cssSelector("ul li a#"+arg1)).click();
////	    dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);   
////	    dr.quit();		
//	}	
//	@Dado("^eu esteja na página \"([^\"]*)\"$")
//	public void eu_esteja_na_página(String arg1) throws Throwable {
//		// Write code here that turns the phrase above into concrete actions
//		
//		dr.get("http://localhost:"+port+"/onsportal/#/consulta-taxa-ref");
//	}
//
//	@Então("^eu deveria ver (\\d+) itens na lista \"([^\"]*)\"$")
//	public void eu_deveria_ver_itens_na_lista(int arg1, String arg2) throws Throwable {
//		WebElement baseTable = dr.findElement(By.id(arg2));
//		assertThat((baseTable.findElements(By.tagName("tr")).size()-2)==arg1);
//	}
//	@Então("^eu deveria ver um item na lista Taxas de Referência - Usinas com os valores Instalação: \"([^\"]*)\"; Mês Ref: \"([^\"]*)\"-\"([^\"]*)\"; TEIF Ref: \"([^\"]*)\"; IP Ref: \"([^\"]*)\"$")
//	public void eu_deveria_ver_um_item_na_lista_Taxas_de_Referência_Usinas_com_os_valores_Instalação_Mês_Ref_TEIF_Ref_IP_Ref(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
//	    // Write code here that turns the phrase above into concrete actions
//		
//		List<WebElement> list = dr.findElements(By.cssSelector("table.table>tbody>tr>td"));
//		List<String> listTextos = new ArrayList<String>();
//		for(WebElement ee : list){
//			listTextos.add(ee.getText());
//		}
//		assertThat(listTextos.contains(arg1));
//		assertThat(listTextos.contains(arg2));
////		assertThat(listTextos.contains(arg3));
//		assertThat(listTextos.contains(arg4));
//		assertThat(listTextos.contains(arg5));
//		
//	}
//	@Quando("^eu aperto a guia \"([^\"]*)\"$")
//	public void eu_aperto_a_guia(String arg1) throws Throwable {
//	   
//		dr.findElement(ByLinkText.linkText(arg1)).click();
//	    
//	}
//	@Dado("^eu deveria ver um item na lista Taxas de Referência - Interligações Internacionais com os valores Instalação: \"([^\"]*)\"; Mês Ref: \"([^\"]*)\"; TEIF Ref: \"([^\"]*)\"; IP Ref: \"([^\"]*)\"$")
//	public void eu_deveria_ver_um_item_na_lista_Taxas_de_Referência_Interligações_Internacionais_com_os_valores_Instalação_Mês_Ref_TEIF_Ref_IP_Ref(String arg1, String arg2, String arg3, String arg4) throws Throwable {
//
//		List<WebElement> list = dr.findElements(By.cssSelector("table.table>tbody>tr>td"));
//		List<String> listTextos = new ArrayList<String>();
//		for(WebElement ee : list){
//			listTextos.add(ee.getText());
//		}
//		assertThat(listTextos.contains(arg1));
//		assertThat(listTextos.contains(arg2));
//		assertThat(listTextos.contains(arg3));
//		assertThat(listTextos.contains(arg4));
//	}	
//	@Dado("^eu não deveria ver a aba Taxas de Referência - \"([^\"]*)\"$")
//	public void eu_não_deveria_ver_a_aba_Taxas_de_Referência(String arg1) throws Throwable {
//		assertThat(dr.findElements(ByLinkText.linkText(arg1)).size()==0);
//	}
//	@Dado("^eu deveria ver um item na lista \"([^\"]*)\" com os valores Instalação: \"([^\"]*)\"; Mês Ref: \"([^\"]*)\"; TEIF Ref: \"([^\"]*)\"; IP Ref: \"([^\"]*)\"$")
//	public void eu_deveria_ver_um_item_na_lista_com_os_valores_Instalação_Mês_Ref_TEIF_Ref_IP_Ref(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
//
//
//		WebElement lista = dr.findElement(By.id(arg1));
//		List<WebElement> list = lista.findElements(By.cssSelector("table.table>tbody>tr>td"));
//		List<String> listTextos = new ArrayList<String>();
//		for(WebElement ee : list){
//			listTextos.add(ee.getText());
//		}
//		assertThat(listTextos.contains(arg2));
//		assertThat(listTextos.contains(arg3));
//		assertThat(listTextos.contains(arg4));
//		assertThat(listTextos.contains(arg5));
//	
//	}
//	@Dado("^eu informo o valor \"([^\"]*)\" no campo \"([^\"]*)\" na guia \"([^\"]*)\"$")
//	public void eu_informo_o_valor_no_campo_na_guia(String arg1, String arg2, String arg3) throws Throwable {
//
//		WebElement searchField = dr.findElement(By.id(arg3+"_"+arg2));
//		searchField.sendKeys(arg1);
//		
//	}
//	@Dado("^eu aperto o botão \"([^\"]*)\"$")
//	public void eu_aperto_o_botão(String arg1) throws Throwable {
//		
//		dr.findElement(By.id(arg1)).click();
//	}
//	@Dado("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
//	public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
//
////		dr.findElement(ByLinkText.partialLinkText(arg1));
//		assertThat(true); //TODO
//		
//	}
//	@Dado("^eu deveria poder baixar um arquivo Excel \"([^\"]*)\"$")
//	public void eu_deveria_poder_baixar_um_arquivo_Excel(String arg1) throws Throwable {
//		assertThat(true); //TODO
//	}	

}
