//package com.salmon.test;
//
//import com.cucumber.listener.ExtentCucumberFormatter;
//import com.salmon.test.framework.helpers.UrlBuilder;
//import cucumber.api.CucumberOptions;
//import cucumber.api.testng.AbstractTestNGCucumberTests;
//import org.testng.IHookCallBack;
//import org.testng.ITestResult;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeClass;
//
//import java.io.File;
//import java.util.*;
//
//@CucumberOptions(features = "target/test-classes", tags = {"@suda"}, monochrome = true, plugin = {
//        "pretty", "html:target/cucumber-report/webapp",
//        "json:target/cucumber-report/webapp/cucumber.json",
//        "rerun:target/cucumber-report/webapp/rerun.txt"},
//        glue = "com.salmon.test.step_definitions")
//public class SudaCukeRunner extends AbstractTestNGCucumberTests{
//    private ITestResult iTestResult;
//
//    @BeforeClass
//    public void setup() {
//        ExtentCucumberFormatter.initiateExtentCucumberFormatter(new File("target/feature-overview/YNAPAutomationReport.html"));
//        ExtentCucumberFormatter.loadConfig(new File("src/test/resources/extent-config.xml"));
//    }
//
//    @AfterClass
//    public void moveReportsAfterRun() {
//        System.out.printf("----in after CLASS------");
//    }
//
//    @AfterSuite
//    public void moveReports() {
//        System.out.printf("----in after suite------");
//    }
//
//    @Override
//    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
//        Arrays.asList(UrlBuilder.readValueFromConfig("store.identifiers").split(","))
//                .stream()
//                .forEach(store -> {
//                    this.iTestResult = iTestResult;
//                    setCurrentStore(store);
//                    iHookCallBack.runTestMethod(iTestResult);
//                });
//    }
//
//    private void setCurrentStore(String currentStore) {
//        System.setProperty("currentStore", currentStore);
//    }
//}
