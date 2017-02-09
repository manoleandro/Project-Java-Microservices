package br.org.ons.portal.cucumber.solicitarCalculoTaxa;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
	    
	    @Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
	    public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
	    	dr.get("http://sager.ons.org.br:8080/onsportal/#/");

			new WebDriverWait(dr, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//form")));
		    	
			JavascriptExecutor js = (JavascriptExecutor) dr;
				js.executeScript("var els=document.getElementsByName('username');els[0].value = '"+arg1+"';");
				js.executeScript("var els=document.getElementsByName('password');els[0].value = 'Temp@123';");
				dr.findElement(By.name("submit")).click();
				
			new WebDriverWait(dr, 5).until(ExpectedConditions.presenceOfElementLocated(By.id("sky-form")));
			
			boolean link = dr.findElement(By.id("sky-form")).isDisplayed();
			
			Assert.assertTrue(link==true);
	    }

	    @Dado("^eu esteja na página \"([^\"]*)\"$")
	    public void eu_esteja_na_página(String arg1) throws Throwable {
	    	JavascriptExecutor js = (JavascriptExecutor) dr;
	    	if(arg1.equals("Solicitar cálculo das taxas")){
	    		
	    		dr.get("http://sager.ons.org.br:8080/onsportal/#/calculo-taxa");
	    		js.executeScript("$('.ng-pristine').click()");
	    		
	    	}else{
	    		
		        throw new PendingException();
		        
	    	}
	    	

	    }

	    @Quando("^eu informo no campo \"([^\"]*)\" o valor \"([^\"]*)\"$")
	    public void eu_informo_no_campo_o_valor(String arg1, String arg2) throws Throwable {
	        // Write code here that turns the phrase above into concrete actions
	    }

	    @Quando("^eu seleciono a instalação \"([^\"]*)\" na guia \"([^\"]*)\"$")
	    public void eu_seleciono_a_instalação_na_guia(String arg1, String arg2) throws Throwable {
	        // Write code here that turns the phrase above into concrete actions
	    	dr.findElement(By.xpath("//*[contains(text(), '"+arg1+"')]")).click();
	    }

	    @Quando("^eu aperto o botão \"([^\"]*)\"$")
	    public void eu_aperto_o_botão(String arg1) throws Throwable {
	        // Write code here that turns the phrase above into concrete actions
	    	dr.findElement(By.xpath("//*[contains(text(), 'Agendar cálculo')]")).click();
	    }

	    @Então("^eu deveria ver a modal \"([^\"]*)\" com o grid \"([^\"]*)\" preenchido com o <Resultado>:$")
	    public void eu_deveria_ver_a_modal_com_o_grid_preenchido_com_o_Resultado(String arg1, String arg2) throws Throwable {
	        // Write code here that turns the phrase above into concrete actions
	        throw new PendingException();
	    }
}
