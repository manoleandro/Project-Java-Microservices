package br.org.ons.portal.cucumber.calcularTaxas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.JavascriptExecutor;
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
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Então;
import cucumber.api.java.pt.Quando;

public class CucumberCN02_VerificarFiltros {

	int port = 8078;
	String urlLocal = "localhost";
	private WebDriver dr;
	
		@Before
		public void setup() {
	        DriverPathLoader.loadDriverPaths(null);
			dr = new ChromeDriver();
			
			// Configuração para esperar a tela completar por no  máximo 10s
			dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			WebDriverExtensionsContext.setDriver(dr);
		}

	    @After
	    public void cleanUp(){
	    	dr.close();
	        WebDriverExtensionsContext.removeDriver();
	    }

		@Dado("^a funcionalidade \"([^\"]*)\" deveria estar disponível$")
		public void a_funcionalidade_deveria_estar_disponível(String arg1) throws Throwable {
//TODO Utilizado no Valida Usuário. já implementado pelo UC_2402
			
//			JavascriptExecutor js = (JavascriptExecutor) dr;
//			
//			WebElement menu = dr.findElement(By.cssSelector(".menuTopoBar > ul > li > ul"));
//			js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu);
//			WebElement menu2 = dr.findElement(By.cssSelector("li #menuSager"));
//			js.executeScript("arguments[0].setAttribute('style', 'display:block')", menu2);
//		    dr.findElement(By.cssSelector("ul li a#"+arg1)).click(); 
//		    dr.quit();		
		}	

		@Dado("^eu esteja na página \"([^\"]*)\"$")
		public void eu_esteja_na_página(String arg1) throws Throwable {

			if("Solicitar cálculo de taxa".equalsIgnoreCase(arg1)){
				dr.get("http://localhost:"+port+"/onsportal/#/calculo-taxa");
				Assert.assertTrue(dr.findElement(By.id("calc-taxa")).isDisplayed());
			}else if("Consultar taxas de referência".equalsIgnoreCase(arg1)){
				dr.get("http://localhost:"+port+"/onsportal/#/consulta-taxa-ref");
				Assert.assertTrue(dr.findElements(By.xpath("//h4[contains(text(),'Consultar Taxas de Referência')]")).size()>0);
			}else if("Consultar taxas".equalsIgnoreCase(arg1)){
				dr.get("http://localhost:"+port+"/onsportal/#/consulta-taxa");
				Assert.assertTrue(dr.findElements(By.xpath("//h2[contains(text(),'Consultar Taxas')]")).size()>0);
			}
		}
		
		@Dado("^que eu esteja na página \"([^\"]*)\"$")
		public void que_eu_esteja_na_página(String arg1) throws Throwable {
			this.eu_esteja_na_página(arg1);
		}		
		
//		@Dado("^eu esteja na página \"([^\"]*)\"$")
//		public void eu_esteja_na_página(String arg1) throws Throwable {
//			if(arg1.equals("Consultar taxas")){
//				arg1 = "consulta-taxa";
//			}
//			try{
//				dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
//				dr.findElement(By.id("paginaHome"));
//				dr.get("http://"+urlLocal+":"+port+"/onsportal/#/"+arg1);
//				
//				
//			}catch(Exception e){
//				Assert.fail();
//			}
//		}		

		@Então("^eu deveria ver (\\d+) itens na lista \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_na_lista(int arg1, String arg2) throws Throwable {
			
			switch(arg2){
				case "Interligações Internacionais":
				case "Taxas de Referência - Interligações Internacionais":
					arg2 = "tableInterlig";
					break;
				case "Usinas":
				case "Taxas de Referência - Usinas":
					arg2 = "tableUsinas";
					break;
			}
			WebElement baseTable = dr.findElement(By.id(arg2));
			Assert.assertTrue(baseTable.findElements(By.xpath("//tr[@id='rpt"+arg2+"']")).size()==arg1);
			
		}

		@Quando("^eu aperto a guia \"([^\"]*)\"$")
		public void eu_aperto_a_guia(String arg1) throws Throwable {
			
			switch(arg1){
			case "Usinas":
				arg1 = "tabUsinas"; 
				break;
			case "Interligações Internacionais":
				arg1 = "tabInterlig";
				break;
			}
		   
			WebElement aba = dr.findElement(By.id(arg1));
			JavascriptExecutor js = (JavascriptExecutor) dr;
			js.executeScript("arguments[0].click()",aba);
		    
		}
		
		@Dado("^eu não deveria ver a aba Taxas de Referência - \"([^\"]*)\"$")
		public void eu_não_deveria_ver_a_aba_Taxas_de_Referência(String arg1) throws Throwable {
			Assert.assertTrue(dr.findElements(ByLinkText.linkText(arg1)).size()==0);
		}
		
		@Dado("^eu deveria ver um item na lista \"([^\"]*)\" com os valores Instalação: \"([^\"]*)\"; Mês Ref: \"([^\"]*)\"; TEIF Ref: \"([^\"]*)\"; IP Ref: \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_na_lista_com_os_valores_Instalação_Mês_Ref_TEIF_Ref_IP_Ref(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {

			switch(arg1){
			case "Interligações Internacionais":
			case "Taxas de Referência - Interligações Internacionais":
				arg1 = "tableInterlig";
				break;
			case "Usinas":
			case "Taxas de Referência - Usinas":
				arg1 = "tableUsinas";
				break;
			}
			
			WebElement baseTable = dr.findElement(By.id(arg1));
			List<WebElement> list = baseTable.findElements(By.xpath("//tr[@id='rpt"+arg1+"']"));
			
			boolean linhaEncontrada = false;
			
			for(WebElement linha : list){
				
				String linhatxt = linha.getText().replace(",", ".");
	
				if( linhatxt.toUpperCase().contains(arg2.toUpperCase().replace(",", ".")) &&
					linhatxt.toUpperCase().contains(arg3.toUpperCase().replace(",", ".")) &&
					linhatxt.toUpperCase().contains(arg4.toUpperCase().replace(",", ".")) &&
					linhatxt.toUpperCase().contains(arg5.toUpperCase().replace(",", "."))){
					linhaEncontrada = true;
				}
			}
			
			Assert.assertTrue(linhaEncontrada);
		
		}
		
		@Dado("^eu informo o valor \"([^\"]*)\" no campo \"([^\"]*)\" na guia \"([^\"]*)\"$")
		public void eu_informo_o_valor_no_campo_na_guia(String arg1, String arg2, String arg3) throws Throwable {

			//arg1
			if("Interligações internacionais".equalsIgnoreCase(arg3)){
				arg3 = "Interlig";
			}
			
			//arg2
			if("Mês de Referência".equalsIgnoreCase(arg2)){
				arg2 = "MesRef";
			}else if("Instalação".equalsIgnoreCase(arg2)){
				arg2 = "Instalacao";
			}
			
			WebElement searchField = dr.findElement(By.id(arg3+"_"+arg2));
			searchField.sendKeys(arg1.replace(",", "."));
			
			Assert.assertTrue(searchField.getAttribute("value").equalsIgnoreCase(arg1.replace(",", ".")));
		}
		

		@Dado("^eu aperto o botão \"([^\"]*)\"$")
		public void eu_aperto_o_botão(String arg1) throws Throwable {
			
			if("Agendar Cálculo".equalsIgnoreCase(arg1)){
				arg1 = "btnAgendarCalculo";
			}else if("Agendar".equalsIgnoreCase(arg1)){
				arg1 = "btnAgendar";
			}else if("Cancelar".equalsIgnoreCase(arg1)){
				arg1 = "btnCancelar";
			}else if("Pesquisar".equalsIgnoreCase(arg1)){
				arg1 = "pesquisar";
			}
			
			WebElement btnAgendarCalculo = dr.findElement(By.id(arg1));
			JavascriptExecutor js = (JavascriptExecutor) dr;
			js.executeScript("arguments[0].click()",btnAgendarCalculo);
			
		}
//		@Quando("^eu aperto o botão \"([^\"]*)\"$")
//		public void eu_aperto_o_botão(String arg1) throws Throwable {
//			JavascriptExecutor js = (JavascriptExecutor) dr;
//			js.executeScript("document.getElementById('"+arg1.toLowerCase()+"').click();");
//		    
//		}		

		@Dado("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {

			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
			Assert.assertTrue(erros.getText().contains(arg1));
			
		}
		
//		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
//		public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
//			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
//			
//			Assert.assertTrue(erros.getText().contains(arg1));
//			
//		}		
		
		@Então("^eu deveria ver a mensagem de sucesso de código \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_sucesso_de_código(String arg1) throws Throwable {
			
			WebElement erros =  dr.findElement(By.cssSelector("#mensagemSucesso"));
			Assert.assertTrue(erros.getText().contains(arg1));
		}
		
		@Então("^eu vejo a mensagem de sucesso de código \"([^\"]*)\"$")
		public void eu_vejo_a_mensagem_de_sucesso_de_código(String arg1) throws Throwable {
			
			this.eu_deveria_ver_a_mensagem_de_sucesso_de_código(arg1);
		}		
		
		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
			
			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
			Assert.assertTrue(erros.getText().contains(arg1));
			Assert.assertTrue(erros.getText().contains(arg2));			
		}
		
//		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
//		public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
//			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
//			
//			Assert.assertTrue(erros.getText().contains(arg1));
//			Assert.assertTrue(erros.getText().contains(arg2));
//
//		}		

		@Então("^eu deveria ver a mensagem de erro \"([^\"]*)\" e crítica \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_e_crítica(String arg1, String arg2) throws Throwable {
			
			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
			Assert.assertTrue(erros.getText().contains(arg1));
			Assert.assertTrue(erros.getText().contains(arg2));
		}		
		
		@Dado("^eu deveria poder baixar um arquivo Excel \"([^\"]*)\"$")
		public void eu_deveria_poder_baixar_um_arquivo_Excel(String arg1) throws Throwable {
			
			WebElement btnExportarExcel = dr.findElement(By.id("Exportar Excel"));
			Assert.assertTrue(btnExportarExcel.isEnabled());
			
		}
		
		@Dado("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo(String arg1, String arg2) throws Throwable {
			
			if("Março 2016".equalsIgnoreCase(arg1)){
				arg1 = "2016-03";
			}else if("Abril 2010".equalsIgnoreCase(arg1)){
				arg1 = "2010-04";
			}else if("Março 2000".equalsIgnoreCase(arg1)){
				arg1 = "2000-03";
			}else if("Março 2010".equalsIgnoreCase(arg1)){
				arg1 = "2010-03";
			}else if("Março 2030".equalsIgnoreCase(arg1)){
				arg1 = "2030-03";
			}else if("Janeiro 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-01";
			}else if("Julho 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-07";
			}else if("Fevereiro 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-01";
			}else if("Setembro 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-09";
			}else if("Janeiro 2002".equalsIgnoreCase(arg1)){
				arg1 = "2002-01";
			}else if("Março 2002".equalsIgnoreCase(arg1)){
				arg1 = "2002-03";
			}else if("Novembro 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-11";
			}else if("Março 2015".equalsIgnoreCase(arg1)){
				arg1 = "2015-03";
			}else if("Março 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-03";
			}else if("Agosto 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-08";
			}else if("Fevereiro 2015".equalsIgnoreCase(arg1)){
				arg1 = "2015-02";
			}else if("Outubro 2016".equalsIgnoreCase(arg1)){
				arg1 = "2016-10";
			}else if("Novembro 2016".equalsIgnoreCase(arg1)){
				arg1 = "2016-11";
			}else if("Outubro 2014".equalsIgnoreCase(arg1)){
				arg1 = "2014-10";
			}else if(arg1 != null && arg1.length() == 7 &&arg1.contains("/")){
				String mes = arg1.substring(0, 2);
				String ano = arg1.substring(3);
				arg1 = ano+"-"+mes;
			}

		   switch(arg2){
		   case "Mês inicial":
			   arg2 = "mesInicial";
			   break;
		   case "Mês final":
			   arg2 = "mesFinal";
			   break;
		   case "Data de agendamento":
			   if(arg1.length() == 10){
				   arg1 = arg1.substring(6,10)+"-"+arg1.substring(3, 5)+"-"+arg1.substring(0, 2);
			   }
			   arg2 = "dataAgendamento";
			   break;
		   case "Hora de agendamento":
			   if(arg1.length() == 5){ arg1 += ":00";}
			   arg2 = "horaAgendamento";
			   break;
		   }
		   
		   JavascriptExecutor js = (JavascriptExecutor) dr;
		   WebElement element = dr.findElement(By.id(arg2));
		   js.executeScript("arguments[0].value='"+arg1+"'",element);
		   
		   Assert.assertTrue(element.getAttribute("value").equalsIgnoreCase(arg1));

		}

//		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\"$")
//		public void eu_informo_o_valor_para_o_campo(String arg1, String arg2) throws Throwable {
//			dr.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
//			JavascriptExecutor js = (JavascriptExecutor) dr;
//			if(arg2.equals("Mês inicial")){
//				String mes = arg1.substring(0, 2);
//				String ano = arg1.substring(3);
//				System.out.println("mes: "+mes);
//				System.out.println("ano: "+ano);
//				String data = ano+"-"+mes;
//				System.out.println(data);
//				dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//				dr.findElement(By.id("mesInicial"));
//				js.executeScript("document.getElementById('mesInicial').value = '"+data+"';");
//			}else if(arg2.equals("Mês final")){
//				String mes = arg1.substring(0, 2);
//				String ano = arg1.substring(3);
//				String data = ano+"-"+mes;
//				dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//				dr.findElement(By.id("mesFinal"));
//				js.executeScript("document.getElementById('mesFinal').value = '"+data+"';");
//			}
//			
//		}
		

		@E("^eu seleciono o item \"([^\"]*)\" na lista \"([^\"]*)\"$")
		public void eu_seleciono_o_item_na_lista(String arg1, String arg2) throws Throwable {
			
			JavascriptExecutor js = (JavascriptExecutor) dr;
			WebElement checkbox = dr.findElement(By.id(arg1));
			js.executeScript("arguments[0].click()",checkbox);
			
			Assert.assertTrue(checkbox.getAttribute("aria-checked").equalsIgnoreCase("true"));
			
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\" na lista \"([^\"]*)\"$")
		public void eu_informo_o_valor_para_o_campo_na_lista(String arg1, String arg2, String arg3) throws Throwable {
			
			//arg1
			if("Interligações internacionais".equalsIgnoreCase(arg3)){
				arg3 = "Interlig";
			}
			
			//arg2
			if("Mês de Referência".equalsIgnoreCase(arg2)){
				arg2 = "MesRef";
			}else if("Instalação".equalsIgnoreCase(arg2)){
				arg2 = "Instalacao";
			}
			
			WebElement searchField = dr.findElement(By.id(arg2+arg3));
			JavascriptExecutor js = (JavascriptExecutor) dr;
			js.executeScript("arguments[0].value=''",searchField);
			searchField.sendKeys(arg1);
			
			Assert.assertTrue(searchField.getAttribute("value").equalsIgnoreCase(arg1));
			   
		}
	
		@Então("^eu deveria ver o modal \"([^\"]*)\"$")
		public void eu_deveria_ver_o_modal(String arg1) throws Throwable {
			
			switch(arg1){
			case "Agendamento de cálculo":
				arg1 = "myCalculoTaxaLabel";
				break;
			}		
			Assert.assertTrue(dr.findElements(By.id(arg1)).size()>0);
		}		
		
		@Quando("^eu seleciono todos os itens da lista \"([^\"]*)\"$")
		public void eu_seleciono_todos_os_itens_da_lista(String arg1) throws Throwable {
			
			switch(arg1){
			case "Usinas":
				arg1 = "tableUsinas";
				break;
			case "Interligações Internacionais":
				arg1 = "tableInterlig";
				break;
			}
			
			WebElement table = dr.findElement(By.id(arg1));
			List<WebElement> els = table.findElements(By.xpath("//md-checkbox[@type='checkbox']"));

			for ( WebElement el : els ) {
					JavascriptExecutor js = (JavascriptExecutor) dr;
					js.executeScript("arguments[0].click()",el);			        
			}
		}
		
		@Então("^eu deveria ver o valor \"([^\"]*)\" para o campo \"([^\"]*)\"$")
		public void eu_deveria_ver_o_valor_para_o_campo(String arg1, String arg2) throws Throwable {
			
			Calendar dateHoje = Calendar.getInstance();
			int mes = dateHoje.get(Calendar.MONTH)+1;
			
			if("Data de agendamento".equalsIgnoreCase(arg2)){
				arg2 = "dataAgendamento";
				WebElement element = dr.findElement(By.id(arg2));
				String[] dataAg = element.getAttribute("value").split("-");
				Assert.assertTrue(dateHoje.get(Calendar.YEAR) == Integer.parseInt(dataAg[0]));
				Assert.assertTrue(mes == Integer.parseInt(dataAg[1]));
				Assert.assertTrue(dateHoje.get(Calendar.DAY_OF_MONTH) == Integer.parseInt(dataAg[2]));
				
			}else if("Hora de agendamento".equalsIgnoreCase(arg2)){
				arg2 = "horaAgendamento";
				WebElement element = dr.findElement(By.id(arg2));
				String[] dataAg = element.getAttribute("value").split(":");
				Assert.assertTrue(dateHoje.get(Calendar.HOUR_OF_DAY) == Integer.parseInt(dataAg[0]));
				Assert.assertTrue(dateHoje.get(Calendar.MINUTE) == Integer.parseInt(dataAg[1]));
			}

		}
		
//		@Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
//		public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
//			 try{
//			    	System.setProperty("webdriver.chrome.driver","C:\\IBM\\Drivers\\selenium\\chromedriver.exe");
//					dr = new ChromeDriver();
//					
//					dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//					dr.get("http://"+urlLocal+":"+port+"/onsportal");
//					dr.findElement(By.cssSelector(".botaoTopoLogin")).click();
//					WebElement myDynamicElement2 = (new WebDriverWait(dr, 25))
//							  .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
//					myDynamicElement2.sendKeys(arg1);
//					dr.findElement(By.cssSelector("input#password")).sendKeys("user");
//					dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
//					dr.findElement(By.cssSelector("button[type=submit].btn-primary")).click();
//					
//				}catch(Exception e){
//					Assert.fail();
//				}
//		}
		
		@Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
		public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
			
			dr.get("http://localhost:"+port+"/onsportal");
			
			dr.findElement(By.cssSelector(".botaoTopoLogin")).click();
			WebElement myDynamicElement2 = (new WebDriverWait(dr, 25))
					  .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
			myDynamicElement2.sendKeys(arg1);
			dr.findElement(By.cssSelector("input#password")).sendKeys("user");
			dr.findElement(By.cssSelector("button[type=submit].btn-primary")).click();
			
			System.out.println("-> "+arg1+"");
			
			
		}		



		@Quando("^eu seleciono o tipo de taxa \"([^\"]*)\"$")
		public void eu_seleciono_o_tipo_de_taxa(String arg1) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
			dr.findElement(By.id(arg1));
			js.executeScript("document.getElementById('"+arg1+"').click();");
		    
		}

		@Quando("^eu aperto a lista \"([^\"]*)\"$")
		public void eu_aperto_a_lista(String arg1) throws Throwable {
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
				System.out.println("apertou a lista");
		    }catch(Exception e){
		    	System.out.println("não apertou!");
		    	Assert.fail();
		    }
		}

		@Quando("^eu seleciono o elemento \"([^\"]*)\" na lista \"([^\"]*)\"$")
		public void eu_seleciono_o_elemento_na_lista(String arg1, String arg2) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			dr.findElement(By.id(arg1));
			WebElement usina = dr.findElement(By.cssSelector("md-checkbox[id='"+arg1+"']"));
			
			try{
				js.executeScript("arguments[0].click();",usina);
			}catch(Exception e){
				usina.click();
			}
			
		    
		}



		@Então("^eu deveria ver (\\d+) itens no grid \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_no_grid(int arg1, String arg2) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			dr.findElement(By.cssSelector("[id='aba"+arg2+"']"));
			
			js.executeScript("document.getElementById('aba"+arg2+"').click();");
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			List<WebElement> taxas = dr.findElements(By.cssSelector("[id='taxa"+arg2+"']"));
			if(taxas.size() != arg1){
				Assert.fail();
			}
			System.out.println("Tamanho das taxas"+taxas.size());
		}
		
		@Então("^eu deveria ver (\\d+) item no grid \"([^\"]*)\"$")
		public void eu_deveria_ver_item_no_grid(int arg1, String arg2) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			dr.findElement(By.cssSelector("[id='aba"+arg2+"']"));
			
			js.executeScript("document.getElementById('aba"+arg2+"').click();");
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			List<WebElement> taxas = dr.findElements(By.cssSelector("[id='taxa"+arg2+"']"));
			if(taxas.size() != arg1){
				Assert.fail();
			}
			System.out.println("Tamanho das taxas"+taxas.size());
		}


		
		

		
		@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"; \"([^\"]*)\"$")
		public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2, String arg3) throws Throwable {
			WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
			
			Assert.assertTrue(erros.getText().contains(arg1));
			Assert.assertTrue(erros.getText().contains(arg2));
			Assert.assertTrue(erros.getText().contains(arg3));

		}
		

		
		@Então("^fim do teste$")
		public void fim_do_teste() throws Throwable {
			dr.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);   
		    dr.quit();
		}
		
		@Então("^eu deveria ver um item no grid \"([^\"]*)\" com valor: Mês Referência: \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_no_grid_com_valor_Mês_Referência(String arg1, String arg2, String arg3, String arg4) throws Throwable {
				
			DecimalFormat df = new DecimalFormat("##################.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			
			boolean bug = false;
			while(bug == false){
				bug =true;
						
				try{

					//recupera o a <td> que tiver o id = arg3
					
					WebElement elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					String coluna4  = df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					System.out.println("elemento4: "+elemento4.getText());
					//formatando o parametro da feature
					arg4  = df.format(Double.parseDouble(arg4.replaceAll(",", ".")));
					
					System.out.println("td:"+coluna4);
					System.out.println("param:"+arg4);
					
					Assert.assertTrue(coluna4.equals(arg4));
					
				}catch(Exception e){
					bug = false;
					System.out.println("Erro: "+e.getMessage());
				}
			}
		}
		
		@Então("^eu deveria ver um item no grid \"([^\"]*)\" com valor: Mês Referência: \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_no_grid_com_valor_Mês_Referência(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6) throws Throwable {
			DecimalFormat df = new DecimalFormat("##################.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			dr.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			
			//recupera o a <td> que tiver o id = arg3
			boolean bug = false;
			while(bug == false){
				bug =true;
						
				try{
					WebElement elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					String coluna4  = df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					
					//formatando o parametro da feature
					arg4  = df.format(Double.parseDouble(arg4.replaceAll(",", ".")));
					//recupera o a <td> que tiver o id = arg5
					
					WebElement elemento6 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg5+"']"));
					String coluna6  = df.format(Double.parseDouble(elemento6.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg6  = df.format(Double.parseDouble(arg6.replaceAll(",", ".")));
					
					
					System.out.println("td:"+coluna4);
					System.out.println("param:"+arg4);
					
					System.out.println("td:"+coluna6);
					System.out.println("param:"+arg6);
					
					Assert.assertTrue(coluna4.equals(arg4));
					Assert.assertTrue(coluna6.equals(arg6));
				}catch(Exception e){
					bug = false;
					System.out.println("Erro: "+e.getMessage());
				}
				
			}
				
		}
		
		@Então("^eu deveria ver um item no grid \"([^\"]*)\" com valor: Mês Referência: \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_no_grid_com_valor_Mês_Referência(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8) throws Throwable {

			DecimalFormat df = new DecimalFormat("##################.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			
			boolean bug = false;
			while(bug == false){
				bug =true;
						
				try{
			
					//recupera o a <td> que tiver o id = arg3
					WebElement elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					String coluna4  = df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg4  = df.format(Double.parseDouble(arg4.replaceAll(",", ".")));
					
					
					//recupera o a <td> que tiver o id = arg5
					WebElement elemento6 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg5+"']"));
					String coluna6  = df.format(Double.parseDouble(elemento6.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg6  = df.format(Double.parseDouble(arg6.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg7
					WebElement elemento8 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg7+"']"));
					String coluna8  = df.format(Double.parseDouble(elemento8.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg8  = df.format(Double.parseDouble(arg8.replaceAll(",", ".")));
					

					

					System.out.println("td:"+coluna4);
					System.out.println("param:"+arg4);
					
					System.out.println("td:"+coluna6);
					System.out.println("param:"+arg6);
					
					System.out.println("td:"+coluna8);
					System.out.println("param:"+arg8);
					
					

					
					Assert.assertTrue(coluna4.equals(arg4));
					Assert.assertTrue(coluna6.equals(arg6));
					Assert.assertTrue(coluna8.equals(arg8));
					
					
					
				}catch(Exception e){
					System.out.println("Erro: "+e.getMessage());
					bug = false;
				}
				
			}
			
			
		}
		
		@Então("^eu deveria ver um item no grid \"([^\"]*)\" com valor: Mês Referência: \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_no_grid_com_valor_Mês_Referência(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10) throws Throwable {
			
			DecimalFormat df = new DecimalFormat("##################.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			
			boolean bug = false;
			while(bug == false){
				bug =true;
						
				try{
			
					//recupera o a <td> que tiver o id = arg3
					WebElement elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					String coluna4  = df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg4  = df.format(Double.parseDouble(arg4.replaceAll(",", ".")));
					
					
					//recupera o a <td> que tiver o id = arg5
					WebElement elemento6 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg5+"']"));
					String coluna6  = df.format(Double.parseDouble(elemento6.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg6  = df.format(Double.parseDouble(arg6.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg7
					WebElement elemento8 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg7+"']"));
					String coluna8  = df.format(Double.parseDouble(elemento8.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg8  = df.format(Double.parseDouble(arg8.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg9
					WebElement elemento10 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg9+"']"));
					String coluna10  = df.format(Double.parseDouble(elemento10.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg10  = df.format(Double.parseDouble(arg10.replaceAll(",", ".")));
					

					System.out.println("td:"+coluna4);
					System.out.println("param:"+arg4);
					
					System.out.println("td:"+coluna6);
					System.out.println("param:"+arg6);
					
					System.out.println("td:"+coluna8);
					System.out.println("param:"+arg8);
					
					System.out.println("td:"+coluna10);
					System.out.println("param:"+arg10);

					
					Assert.assertTrue(coluna4.equals(arg4));
					Assert.assertTrue(coluna6.equals(arg6));
					Assert.assertTrue(coluna8.equals(arg8));
					Assert.assertTrue(coluna10.equals(arg10));
					
					
				}catch(Exception e){
					System.out.println("Erro: "+e.getMessage());
					bug = false;
				}
				
			}
			
		}
		
		
		@Então("^eu deveria ver um item no grid \"([^\"]*)\" com valor: Mês Referência: \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"; \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_um_item_no_grid_com_valor_Mês_Referência(String arg1, String arg2, String arg3, String arg4, String arg5, String arg6, String arg7, String arg8, String arg9, String arg10, String arg11, String arg12) throws Throwable {

			DecimalFormat df = new DecimalFormat("##################.####");
			df.setRoundingMode(RoundingMode.HALF_UP);
			
			boolean bug = false;
			while(bug == false){
				bug =true;
						
				try{
			
					//recupera o a <td> que tiver o id = arg3
					WebElement elemento4 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg3+"']"));
					String coluna4  = df.format(Double.parseDouble(elemento4.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg4  = df.format(Double.parseDouble(arg4.replaceAll(",", ".")));
					
					
					//recupera o a <td> que tiver o id = arg5
					WebElement elemento6 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg5+"']"));
					String coluna6  = df.format(Double.parseDouble(elemento6.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg6  = df.format(Double.parseDouble(arg6.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg7
					WebElement elemento8 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg7+"']"));
					String coluna8  = df.format(Double.parseDouble(elemento8.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg8  = df.format(Double.parseDouble(arg8.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg9
					WebElement elemento10 =  dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg9+"']"));
					String coluna10  = df.format(Double.parseDouble(elemento10.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg10  = df.format(Double.parseDouble(arg10.replaceAll(",", ".")));
					
					//recupera o a <td> que tiver o id = arg11
					WebElement elemento12 = dr.findElement(By.cssSelector("[id='taxa"+arg1+"'].taxa"+arg2.replaceAll("/","")+" td[id='taxa"+arg11+"']"));
					String coluna12 = df.format(Double.parseDouble(elemento12.getText().replaceAll(",", ".")));
					//formatando o parametro da feature
					arg12  = df.format(Double.parseDouble(arg12.replaceAll(",", ".")));
					System.out.println("td:"+coluna4);
					System.out.println("param:"+arg4);
					
					System.out.println("td:"+coluna6);
					System.out.println("param:"+arg6);
					
					System.out.println("td:"+coluna8);
					System.out.println("param:"+arg8);
					
					System.out.println("td:"+coluna10);
					System.out.println("param:"+arg10);
					
					System.out.println("td:"+coluna12);
					System.out.println("param:"+arg12);
					
					Assert.assertTrue(coluna4.equals(arg4));
					Assert.assertTrue(coluna6.equals(arg6));
					Assert.assertTrue(coluna8.equals(arg8));
					Assert.assertTrue(coluna10.equals(arg10));
					Assert.assertTrue(coluna12.equals(arg12));
					
				}catch(Exception e){
					System.out.println("Erro: "+e.getMessage());
					bug = false;
				}
				
			}
			
		}
		
		@Então("^eu deveria ver a guia \"([^\"]*)\" indisponível$")
		public void eu_deveria_ver_a_guia_indisponível(String arg1) throws Throwable {
		    if(arg1.equals("Interligação Internacionais") || arg1.equals("Interligacao Internacionais") || arg1.equals("Interligacões Internacionais")){
				try{
			    	dr.findElement(By.id("TaxasGeralInterligacoes"));
			    	Assert.fail();
			    }catch(Exception e){
			    	
			    }
		    }
		    if(arg1.equals("Usinas") || arg1.equals("Usina") || arg1.equals("usina")){
				try{
			    	dr.findElement(By.id("TaxasGeralUsinas"));
			    	Assert.fail();
			    }catch(Exception e){
			    	
			    }
		    }
		}
		
		@Então("^eu deveria ver o grid \"([^\"]*)\"$")
		public void eu_deveria_ver_o_grid(String arg1) throws Throwable {
			try{
				dr.findElement(By.cssSelector("[id='aba"+arg1+"']"));
			}catch(Exception e){
				Assert.fail();
			}
		}
		
		@Quando("^eu seleciono todos os elementos na lista \"([^\"]*)\"$")
		public void eu_seleciono_todos_os_elementos_na_lista(String arg1) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			if(arg1.equals("Usinas")){
				try{
					js.executeScript("document.getElementById('selecionarTodasUsinas').click();");
				}catch(Exception e){
					System.out.println("Error ao selecionar todos as instalações");
					Assert.fail();
				}
			}
			else if(arg1.equals("Interligação Internacionais") || arg1.equals("Interligacao Internacionais") || arg1.equals("Interligacões Internacionais") || arg1.equals("Interligacões") || arg1.equals("Interligacoes") || arg1.equals("Interligações internacionais")){
				try{
					js.executeScript("document.getElementById('selecionarTodasInterligacoes').click();");
				}catch(Exception e){
					System.out.println("Error ao selecionar todos as instalações");
					Assert.fail();
				}
			}

		}
		
		@Então("^eu deveria ver (\\d+) itens um em cada grid$")
		public void eu_deveria_ver_itens_um_em_cada_grid(int arg1) throws Throwable {
			dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			
			try{
		    	dr.findElement(By.id("TaxasGeralInterligacoes"));
		    	List <WebElement> linhas = dr.findElements(By.cssSelector("#TaxasGeralInterligacoes tbody tr"));
				System.out.println("Linhas:"+linhas.size());
				System.out.println("elementos:"+arg1);
				Assert.assertTrue(linhas.size() == arg1);
		    }catch(Exception e){
		    	List <WebElement> linhas = dr.findElements(By.cssSelector("#TaxasGeralUsinas tbody tr"));
				System.out.println("Linhas:"+linhas.size());
				System.out.println("elementos:"+arg1);
				Assert.assertTrue(linhas.size() == arg1);
		    }
			
		}
		
		@Quando("^eu informo o valor \"([^\"]*)\" no campo \"([^\"]*)\" na lista \"([^\"]*)\"$")
		public void eu_informo_o_valor_no_campo_na_lista(String arg1, String arg2, String arg3) throws Throwable {
			JavascriptExecutor js = (JavascriptExecutor) dr;
			if(arg3.equals("Usinas") || arg3.equals("usinas")){
				arg3 = "Usina";
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.findElement(By.id("abaUsina"));
		    	js.executeScript("document.getElementById('aba"+arg3+"').click();");
		    	
		    	WebElement busca = dr.findElement(By.id("buscaUsinaPor"+arg2.toLowerCase()));
		    	busca.sendKeys(arg1);
			}else if(arg1.equals("Interligação Internacionais") || arg1.equals("Interligacao Internacionais") || arg1.equals("Interligacões Internacionais") || arg1.equals("Interligacões") || arg1.equals("Interligacoes") || arg1.equals("Interligações internacionais")){
				
				dr.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				dr.findElement(By.id("abaInterligacoesInternacionais"));
		    	js.executeScript("document.getElementById('abaInterligacoesInternacionais').click();");
		    	
		    	WebElement busca = dr.findElement(By.id("buscaInterligacaoPor"+arg2.toLowerCase()));
		    	busca.sendKeys(arg1);
		    	js.executeScript("arguments[0].value = '"+arg1+"';",busca);
			}
			
	    
		}
		
		@Então("^eu não deveria ver nenhuma taxa de \"([^\"]*)\"$")
		public void eu_não_deveria_ver_nenhuma_taxa_de(String arg1) throws Throwable {
			if(arg1.equals("Usinas") || arg1.equals("Usina") || arg1.equals("usinas")){
				List<WebElement> instacalao = dr.findElements(By.cssSelector("tr#TaxaUsina"));
				
				if(instacalao.size() > 0){
					Assert.fail();
				}
				
				
			}else if(arg1.equals("Interligação Internacionais") || arg1.equals("Interligacao Internacionais") || arg1.equals("Interligacões Internacionais") || arg1.equals("Interligacões") || arg1.equals("Interligacoes") || arg1.equals("Interligações internacionais")){
				List<WebElement> instacalao = dr.findElements(By.cssSelector("tr#TaxaInterligacoes"));
				
				if(instacalao.size() > 0){
					Assert.fail();
				}
			}
			
			
			
		}
		
		@Então("^eu deveria ver (\\d+) itens selecionados no grid \"([^\"]*)\"$")
		public void eu_deveria_ver_itens_selecionados_no_grid(int arg1, String arg2) throws Throwable {
			List<WebElement> selecionados = dr.findElements(By.cssSelector("md-chip"));
			Assert.assertTrue(selecionados.size() == arg1);
		}
		
		@Então("^eu deveria ver no grid \"([^\"]*)\" com valor \"([^\"]*)\": \"([^\"]*)\"$")
		public void eu_deveria_ver_no_grid_com_valor(String arg1, String arg2, String arg3) throws Throwable {
			WebElement selecionados = dr.findElement(By.cssSelector("md-chips"));
			Assert.assertTrue(selecionados.getText().contains(arg3));
		}		
}
