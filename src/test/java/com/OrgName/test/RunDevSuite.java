package com.salmon.test;

import com.cucumber.listener.ExtentCucumberFormatter;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@CucumberOptions(features = "target/test-classes", tags = {"@boutiquecrud"}, monochrome = true, plugin = {
        "com.cucumber.listener.ExtentCucumberFormatter",
        "pretty", "html:target/cucumber-report/runwebat",
        "json:target/cucumber-report/runwebat/cucumber.json",
        "rerun:target/cucumber-report/runwebat/rerun.txt"},
        glue = "com.salmon.test")
public class RunDevSuite extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void setup() {
        ExtentCucumberFormatter.initiateExtentCucumberFormatter(new File("target/feature-overview/YNAPAutomationReport.html"));
        // Loads the extent config xml to customize on the report.
        ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extent-config.xml"));
        // Also you can add system information using a hash map
        Map systemInfo = new HashMap();
        systemInfo.put("Cucumber version", "v1.2.2");
        ExtentCucumberFormatter.addSystemInfo(systemInfo);
    }
}
