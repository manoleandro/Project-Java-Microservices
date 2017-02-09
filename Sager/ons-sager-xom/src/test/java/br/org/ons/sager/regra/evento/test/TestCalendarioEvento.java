package br.org.ons.sager.regra.evento.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import br.org.ons.sager.regra.evento.CalendarioEvento;
import junit.framework.TestCase;

public class TestCalendarioEvento extends TestCase {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	
	public void testDiferencaHoras() {
	
		try {
		
			Date um = sdf.parse("18/12/2016 10:00:00");		
			Date dois = sdf.parse("18/12/2016 09:00:00");	
			assertEquals("Assert 1 ", 1, CalendarioEvento.diferencaHoras(um, dois));
			//fail("Not yet implemented");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	
	public void testDiferecaDias() {
		try {			
			Date um = sdf.parse("18/12/2016 10:00:00");		
			Date dois = sdf.parse("18/12/2016 09:00:00");	
			assertEquals("", 0, CalendarioEvento.diferencaDias(um, dois));
			//fail("Not yet implemented");			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	
	public void testDiferencaMes() {
		try {			
			Date um = sdf.parse("18/12/2016 10:00:00");		
			Date dois = sdf.parse("18/12/2016 09:00:00");	
			assertEquals("", 0, CalendarioEvento.diferencaMes(um, dois));
						
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	
	public void testDiferencaAno() {
		try {			
			Date um = sdf.parse("18/12/2016 10:00:00");		
			Date dois = sdf.parse("18/12/2012 09:00:00");	
			assertEquals("Diferenca em anos ", 3, CalendarioEvento.diferencaAno(new Date(), dois));
					
		} catch (Exception e) {			
			e.printStackTrace();
		}	
	}

}
