package br.org.ons.portal.cucumber.consultarParam;


import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
//	    	dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	        dr.quit();
	    	dr.close();
	        WebDriverExtensionsContext.removeDriver();
	    }
	    
//	    ---------------------------------------------- CN01 - VALIDAR ACESSO ---------------------------------------------
	    
	    @Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
	    public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
	        // Write code here that turns the phrase above into concrete actions
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
			if(arg1.equals("Consultar Parametrização")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				WebElement menu = dr.findElement(By.cssSelector(".menuTopoBar > ul > li > ul"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu);
				WebElement menu2 = dr.findElement(By.cssSelector("li > #menuSager"));
				js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu2);
				boolean link = dr.findElement(By.cssSelector("a[href*='#/manter-param']")).isDisplayed();
				if(link == true){
					dr.get("http://localhost:"+port+"/onsportal/#/manter-param");
					Assert.assertTrue(dr.findElement(By.id("manterParamDiv")).isDisplayed());
				}	
			}
		}
	    
//	    ---------------------------------------------- CN02 - VERIFICAR SELECAO DE FILTROS ---------------------------------------------
	    
		@Dado("^eu esteja na tela \"([^\"]*)\"$")
		public void eu_esteja_na_tela(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			if(arg1.equals("Consultar Parametrização")){
				dr.get("http://localhost:"+port+"/onsportal/#/manter-param");
				Assert.assertTrue(dr.findElement(By.id("manterParamDiv")).isDisplayed());
			}
		}
		@Quando("^eu aperto o botão \"([^\"]*)\" na coluna \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
		public void eu_aperto_o_botão_na_coluna_do_o_item_do_grid(String arg1, String arg2, int arg3, String arg4) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			int  rowCount = dr.findElements(By.xpath("//table[@id='tabManterParam']/tbody/tr")).size();
			System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨" + rowCount);
			if(arg1.equals("Incluir")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				dr.findElement(By.id("consultParam"));
				js.executeScript("document.getElementById('btnIncluir').click();");
			}else if(arg1.equals("Alterar")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				arg3 = arg3-1;
				js.executeScript("$('td .buttons button:eq("+arg3+")').click()");
			}else if(arg1.equals("Excluir")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				js.executeScript("document.getElementById('btnExcluir').click();");
			}
			
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo_do_o_item_do_grid(String arg1, String arg2, int arg3, String arg4) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			int  rowCount = dr.findElements(By.xpath("//table[@id='tabManterParam']/tbody/tr")).size();
			if(arg2.equals("Dia útil")){
		    	dr.findElement(By.xpath("//table/tbody/tr["+rowCount+"]/td[1]/span/span/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"),arg1);
		    }else if(arg2.equals("Hora")){
		    	dr.findElement(By.xpath("//table/tbody/tr["+rowCount+"]/td[2]/span/span/input")).sendKeys(Keys.chord(Keys.CONTROL, "a"),arg1);
		    }
		    
		}
		
		@Quando("^eu aperto o botão \"([^\"]*)\"$")
		public void eu_aperto_o_botão(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			int  rowCount = dr.findElements(By.xpath("//table[@id='tabManterParam']/tbody/tr")).size();
			if(arg1.equals("Salvar")){
				dr.findElement(By.xpath("//table/tbody/tr["+rowCount+"]/td[3]/form/button[1]")).click();
			}else if(arg1.equals("Sim")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				js.executeScript("document.getElementsByClassName('md-confirm-button')[0].click();");
			}
		}
		
		
		@Então("^eu deveria ver a mensagem de sucesso de código \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_sucesso_de_código(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			String msg = dr.findElement(By.id("sucessoCod")).getText();
			Assert.assertTrue(msg.equals(arg1));
		}

		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			String msg = dr.findElement(By.id("erroCod")).getText();
			Assert.assertTrue(msg.equals(arg1));
		}
		
		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			String msgCod = dr.findElement(By.id("erroCod")).getText();
			String msgCri = dr.findElement(By.id("erroCritico")).getText();
			msgCri.trim();
			if(arg2.equals("Hora")){
				Assert.assertTrue(msgCod.equals(arg1) && msgCri.equals("RS_CONF082 - Campo Hora.") );
			}else if(arg2.equals("Dia útil")){
				Assert.assertTrue(msgCod.equals(arg1) && msgCri.equals("RS_CONF082 - Campo Dia.") );
			}
		}
		
		@Quando("^eu aperto o botão \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
		public void eu_aperto_o_botão_do_o_item_do_grid(String arg1, int arg2, String arg3) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			if(arg1.equals("Incluir")){
				JavascriptExecutor js = (JavascriptExecutor) dr;
				dr.findElement(By.id("consultParam"));
				js.executeScript("document.getElementById('btnIncluir').click();");
			}
		}
		
		@Então("^eu deveria ver o valor \"([^\"]*)\" para o campo \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
		public void eu_deveria_ver_o_valor_para_o_campo_do_o_item_do_grid(String arg1, String arg2, int arg3, String arg4) throws Throwable {
		    // Write code here that turns the phrase above into concrete actions
			if(arg2.equals("Hora")){
				String valor = dr.findElement(By.xpath("//table/tbody/tr["+arg3+"]/td[1]/span/span/input")).getText();
				Assert.assertTrue(valor.equals(valor));
			}else if(arg2.equals("Dia útil")){
				String valor = dr.findElement(By.xpath("//table/tbody/tr["+arg3+"]/td[2]/span/span/input")).getText();
				Assert.assertTrue(valor.equals(valor));
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		

		


		

//		
//		@Dado("^eu aperto o botão \"([^\"]*)\"$")
//		public void eu_aperto_o_botão(String arg1) throws Throwable {
//		    // Write code here that turns the phrase above into concrete actions
//			if(arg1.equals("Incluir")){
//				JavascriptExecutor js = (JavascriptExecutor) dr;
//				dr.findElement(By.id("consultParam"));
//				js.executeScript("document.getElementById('btnIncluir').click();");
//			}
//		}
		

		
//		@Então("^eu deveria ver a mensagem de sucesso de código \"([^\"]*)\"$")
//		public void eu_deveria_ver_a_mensagem_de_sucesso_de_código(String arg1) throws Throwable {
//		    // Write code here that turns the phrase above into concrete actions
//			String msg = dr.findElement(By.id("sucessoCod")).getText();
//			Assert.assertTrue(msg.equals(arg1));
//		}
//
//		@Quando("^eu aperto o botão \"([^\"]*)\" na coluna \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
//		public void eu_aperto_o_botão_na_coluna_do_o_item_do_grid(String arg1, String arg2, int arg3, String arg4) throws Throwable {
//		    // Write code here that turns the phrase above into concrete actions
//			JavascriptExecutor js = (JavascriptExecutor) dr;
//			js.executeScript("document.getElementById('btnExcluir').click();");
//			js.executeScript("document.getElementsByClassName('md-confirm-button')[0].click();");
//			js.executeScript("document.getElementById('btnExcluir').click();");
//			js.executeScript("document.getElementsByClassName('md-confirm-button')[0].click();");
//		}
//
//		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
//		public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
//		    // Write code here that turns the phrase above into concrete actions
//			String msg = dr.findElement(By.id("erroCod")).getText();
//			Assert.assertTrue(msg.equals(arg1));
//		}
//	    
//
//
//@Quando("^eu aperto o botão \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
//public void eu_aperto_o_botão_do_o_item_do_grid(String arg1, int arg2, String arg3) throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
//public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}
//
//@Então("^eu deveria ver o valor \"([^\"]*)\" para o campo \"([^\"]*)\" do (\\d+) o item do grid \"([^\"]*)\"$")
//public void eu_deveria_ver_o_valor_para_o_campo_do_o_item_do_grid(String arg1, String arg2, int arg3, String arg4) throws Throwable {
//    // Write code here that turns the phrase above into concrete actions
//    throw new PendingException();
//}


}