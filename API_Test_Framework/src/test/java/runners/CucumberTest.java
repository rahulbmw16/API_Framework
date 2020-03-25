package runners;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

import java.io.File;

@CucumberOptions(strict = true,
        tags = {"@Regression"},
        features = {"src/main/resources/feature/"},
        glue = {"com.company.project.stepdefinition"},
        plugin = {"pretty", "html:target/cucumber-reports/cucumber-pretty",
                "de.monochromata.cucumber.report.PrettyReports:target/cucumber",
                "json:target/report.json",
                "testng:target/testng-cucumber-reports/cuketestng.xml"})
//"html:target/cucumber-reports/cucumber-pretty"
public class CucumberTest extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider
    //@DataProvider (parallel = true) -- For parallel execution support
    public Object[][] scenarios() {
        return super.scenarios();
    }

}