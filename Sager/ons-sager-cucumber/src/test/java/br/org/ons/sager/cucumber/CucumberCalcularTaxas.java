package br.org.ons.sager.cucumber;

import org.junit.runner.RunWith;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = "pretty", features = "src/test/features/calcularTaxas", glue= "br.org.ons.sager.cucumber.calcularTaxas")
public class CucumberCalcularTaxas  {

}
