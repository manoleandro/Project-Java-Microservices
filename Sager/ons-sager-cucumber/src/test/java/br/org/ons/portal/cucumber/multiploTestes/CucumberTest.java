package br.org.ons.portal.cucumber.multiploTestes;

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

import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class CucumberTest {

		int port = 8078;

		private static WebDriver dr;
		
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

		@Given("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
		public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			System.out.println("------------------------------------------");
		    System.out.println(arg1);
		    System.out.println(arg2);
		}
		
		@Given("^eu esteja na página \"([^\"]*)\"$")
		public void eu_esteja_na_página(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		}

		@When("^eu informo no campo \"([^\"]*)\" o valor \"([^\"]*)\"$")
		public void eu_informo_no_campo_o_valor(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		}

		@Then("^eu deveria ver no grid o Resultado \"([^\"]*)\" com o mes \"([^\"]*)\" e com o valor <TEIP acumulada>$")
		public void eu_deveria_ver_no_grid_o_Resultado_com_o_mes_e_com_o_valor_TEIP_acumulada(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		    throw new PendingException();
		}
		
		@Given("^the following people exist:$")
		public void the_following_people_exist(DataTable arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		    // For automatic transformation, change DataTable to one of
		    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		    // E,K,V must be a scalar (String, Integer, Date, enum etc)
			return;
		}
//		@Then("^eu deveria ver no grid o Resultado \"([^\"]*)\" com:$")
//		public void eu_deveria_ver_no_grid_o_Resultado_com(String arg1, DataTable arg2) throws Throwable {
//		    // Write code here that turns the phrase above into concrete actions
//		    // For automatic transformation, change DataTable to one of
//		    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
//		    // E,K,V must be a scalar (String, Integer, Date, enum etc)
//		return;		
//		}
		
		@Então("^eu deveria ver no grid <Instalacao> o <Resultado>:$")
		public void eu_deveria_ver_no_grid_Instalacao_o_Resultado(DataTable arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
		    // For automatic transformation, change DataTable to one of
		    // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
		    // E,K,V must be a scalar (String, Integer, Date, enum etc)
		    throw new PendingException();
		}

}
