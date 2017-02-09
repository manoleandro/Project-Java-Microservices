package br.org.ons.portal.cucumber.consultarTaxas;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
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


	@Então("^fim do teste$")
	public void fim_do_teste() throws Throwable {
		
	}
	
	
	@Dado("^que eu esteja autenticado com usuário \"([^\"]*)\" e perfil \"([^\"]*)\"$")
	public void que_eu_esteja_autenticado_com_usuário_e_perfil(String arg1, String arg2) throws Throwable {
		 try{
				dr.get("http://"+urlLocal+":"+port+"/onsportal");
				dr.findElement(By.cssSelector(".botaoTopoLogin")).click();
				WebElement myDynamicElement2 = (new WebDriverWait(dr, 25))
						  .until(ExpectedConditions.presenceOfElementLocated(By.id("username")));
				myDynamicElement2.sendKeys(arg1);
				dr.findElement(By.cssSelector("input#password")).sendKeys("user");
				dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
				dr.findElement(By.cssSelector("button[type=submit].btn-primary")).click();
				
			}catch(Exception e){
				Assert.fail();
			}
	}

	@Dado("^eu esteja na página \"([^\"]*)\"$")
	public void eu_esteja_na_página(String arg1) throws Throwable {
		if(arg1.equals("Consultar taxas")){
			arg1 = "consulta-taxa";
		}
		try{
			dr.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			dr.findElement(By.id("paginaHome"));
			dr.get("http://"+urlLocal+":"+port+"/onsportal/#/"+arg1);
			
			
		}catch(Exception e){
			Assert.fail();
		}
	}

	@Quando("^eu informo o valor \"([^\"]*)\" para o campo \"([^\"]*)\"$")
	public void eu_informo_o_valor_para_o_campo(String arg1, String arg2) throws Throwable {
		dr.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		JavascriptExecutor js = (JavascriptExecutor) dr;
		if(arg2.equals("Mês inicial")){
			String mes = arg1.substring(0, 2);
			String ano = arg1.substring(3);
			System.out.println("mes: "+mes);
			System.out.println("ano: "+ano);
			String data = ano+"-"+mes;
			System.out.println(data);
			dr.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			dr.findElement(By.id("mesInicial"));
			js.executeScript("document.getElementById('mesInicial').value = '"+data+"';");
		}else if(arg2.equals("Mês final")){
			String mes = arg1.substring(0, 2);
			String ano = arg1.substring(3);
			String data = ano+"-"+mes;
			dr.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			dr.findElement(By.id("mesFinal"));
			js.executeScript("document.getElementById('mesFinal').value = '"+data+"';");
		}
		
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

	@Quando("^eu aperto o botão \"([^\"]*)\"$")
	public void eu_aperto_o_botão(String arg1) throws Throwable {
		JavascriptExecutor js = (JavascriptExecutor) dr;
		js.executeScript("document.getElementById('"+arg1.toLowerCase()+"').click();");
	    
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


	
	
	@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"$")
	public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2) throws Throwable {
		WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
		
		Assert.assertTrue(erros.getText().contains(arg1));
		Assert.assertTrue(erros.getText().contains(arg2));

	}
	
	@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\" e crítica \"([^\"]*)\"; \"([^\"]*)\"$")
	public void eu_deveria_ver_a_mensagem_de_erro_de_código_e_crítica(String arg1, String arg2, String arg3) throws Throwable {
		WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
		
		Assert.assertTrue(erros.getText().contains(arg1));
		Assert.assertTrue(erros.getText().contains(arg2));
		Assert.assertTrue(erros.getText().contains(arg3));

	}
	
	@Então("^eu deveria ver a mensagem de erro de código \"([^\"]*)\"$")
	public void eu_deveria_ver_a_mensagem_de_erro_de_código(String arg1) throws Throwable {
		WebElement erros =  dr.findElement(By.cssSelector("#mensagemErro"));
		
		Assert.assertTrue(erros.getText().contains(arg1));
		
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
