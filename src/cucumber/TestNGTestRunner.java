package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//features are used to locate feature file | Glue are used to glue/hook step definition code |
@CucumberOptions(features="src/cucumber", glue = "stepDefinition"
,monochrome=true, tags = "@ErrorValidation", plugin = {"html:reports/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{
	//AbstractTestNGCucumberTests is class to give cucumber capability of testng method
}
