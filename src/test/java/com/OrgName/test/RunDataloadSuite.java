package com.salmon.test;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(features = "target/test-classes", tags = {"@dataload"}, monochrome = true, plugin = {
        "pretty", "html:target/cucumber-report/dataload",
        "json:target/cucumber-report/dataload/cucumber.json",
        "rerun:target/cucumber-report/dataload/rerun.txt"},
        glue = "com.salmon.test")
public class RunDataloadSuite extends AbstractTestNGCucumberTests {
}
