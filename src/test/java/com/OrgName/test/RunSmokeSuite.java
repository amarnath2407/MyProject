package com.salmon.test;

import com.cucumber.listener.ExtentCucumberFormatter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@CucumberOptions(features = "target/test-classes", tags = {"@smoke"}, monochrome = true, plugin = {
        "com.cucumber.listener.ExtentCucumberFormatter",
        "pretty", "html:target/cucumber-report/smoke",
        "json:target/cucumber-report/smoke/cucumber.json",
        "rerun:target/cucumber-report/smoke/rerun.txt"},
        glue = "com.salmon.test")
public class RunSmokeSuite extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void setup() {
        ExtentCucumberFormatter.initiateExtentCucumberFormatter(new File("target/feature-overview/smoke/YNAPAutomationReport.html"));
        // Loads the extent config xml to customize on the report.
        ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extent-config.xml"));
        // Also you can add system information using a hash map
        Map systemInfo = new HashMap();
        systemInfo.put("Cucumber version", "v1.2.2");
        ExtentCucumberFormatter.addSystemInfo(systemInfo);
    }
}
