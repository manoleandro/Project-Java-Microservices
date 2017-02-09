import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(  monochrome = true,
                         //tags = "@tags",
                     features = "src/test/resources/features/",
                       format = { "pretty","html: cucumber-html-reports",
                                  "json: cucumber-html-reports/cucumber.json" },
                        dryRun = true,
                         glue = "glue_code_folder" )

public class CucumberStepDefinitionGenerator {
  //Run this
}