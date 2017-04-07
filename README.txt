


Have you ever used the Page Object Design Pattern .?

Yes, I have worked on  Page Object Model implies  Page Object - Design Pattern

May you Explain what is about  ?

Page Object Model  Design Pattern facilitates automation for enhancing test maintenance and reducing code duplication. 
Page object is a language neutral pattern for representing a complete page or a part of a page in an objected oriented manner. 
We use them to model the application’s user interface. Page objects expose methods that reflect things that a user can see and do on a page.
Page object model encapsulates behaviour of a page.

Our tests then use the methods of this page object class whenever they need to interact with that page of the UI. 
The  advantage is that if the UI changes for the page, the tests themselves don’t need to change, only the code within the page object needs to change. 
Subsequently all changes to support that new UI are located in one place.

What are the main benefits of using it ?

main benefits of using Page Object model 

1) Maintenance
2) clear Readability of automation scripts
3) Reduced or eliminated duplication
4) Re-usability of code, reduce the developers effort


How do you locate UI Elements in a web page using WebDriver ?

There are 8 ways to find UI elements locate elements.

-->By id
-->By name
--> By className
-->By tagName
--> By  linkText
--> By partialLinkText
--> By css
--> By Xpath

In Fire-fox browser adding fireBug and firePath plug-ins which will be easy to identify the elements.

How many locators do you know ?

I have used id,tagName,cssSelector, className and Xpath (depends on page design).

Which one do you prefer ? why ?

I prefer id and cssSelector most of the time. which will reduce time while finding the element and
will be not be a problem while running my automation scripts on cross browser testing.

For dynamic changing elements i go for Xpath. 

By.xpathor By.css ? Why ?

By.cssSelector 

Xpath engines are different in each browser, hence make them inconsistent
IE does not have a native xpath engine, therefore selenium injects its own xpath engine for compatibility of its API. 
Hence we lose the advantage of using native browser features that WebDriver inherently promotes.
Xpath tend to become complex and hence make hard to read in my opinion

How do you normally make assertings in your tests ?

I write assertions in step-definition file. using assertj-assert plug-in tools. 

Which assertion libraries do you know ?

   1) <assertj-assert>3.2.0</assertj-assert>
   2) AssertJUnit from TestNG.

whats your favourite one ?
 assertj-assert
 
Do you know BDD ?
Yes, BDD- behaviour driven development 

I have used cucumber as a BDD tool which provide gherkin language to write BDD feature files.

How can it improve automation ?

BDD or Behaviour driven development is a process of developing software based on TDD (Test Driven Development) which focusses on behavioural specification of software units.












!
Tools & libraries
=================
The test automation framework is comprised of following tools and libraries  

*Cucumber-JVM:- BDD Framework  
*Custom Page Object Pattern and utility functions  
*Selenium WebDriver: - Browser automation framework
*Selenium Grid: - Distribute test Execution across several machines  
*Android Driver: - Android Mobile Automation  
*Appium: - Mobile Native app, Hybrid App, Web app  
*SauceLabs: - Cloud Based testing of mobile and Web Apps  
*JAVA: - Programming language  
*TestNg: - TestNg Java testing framework  
*Maven: - Build tool  
*Jenkins: - Continuous Integration  
*Lombok: - Java utility api  
*PicoContainer: - Standard Dependency Injection     
*Git OR SVN: - Version Control  
*Gitlab or Local Git Server: - Git repository hosted server  
*Intellij Or Eclipse: - Integrated Development Environment  
*Loggers: - Simple Logging Facade for Java  
*Resource Bundle: - Supporting i18n Localisation and Externalise String 
*Joda-Time: - Java Date time Api  
*SonarQube (optional): - Code Quality and Code Coverage  
*DbUtils Mysql (optional): - Java Database utility api  
*DB2 and Open JDBC: - IBM database API   
*Rest-Assured (optional): - Restful Api framework
*GSON : - Java serialization/deserialization library that can convert Java Objects into JSON and back. 
*PhantomJsDriver, GhostDriver: - Full web stack No browser required supporting headless testing  
*AssertJ: - Used for multiple assertions for further info visit this site (http://joel-costigliola.github.io/assertj/)  
*PODAM:- Auto random data generator for models





Machine Configuration
====================
Configure Ubuntu / Windows and setup: -   
*Java 8  
*Git  / SVN  
*Maven  


Get the latest Source Code
===========================
Open Terminal or command line
cd to the desired folder where the test automation source code needs to be checkout

Run command
git clone https://github.com/orgname-org/master_cucumber_testng.git

This will download the latest template source code

IDE Configuration
==================
Intellij plugins  
----------------
Configure and Install Following Plugins  
File >> Setting >> Plugins >> Browser Repositories>

*Cucumber for Java
*Gherkin
*lombok
*Git Integration/ SVN Integration  
*Maven Integration
*SonarQube (optional)

Eclipse plugins  
----------------
Configure and Install Following Plugins  
Help>>Install new software
*Cucumber for Java
http://cucumber.github.com/cucumber-eclipse/update-site  

*lombok
Download lombok.jar from http://projectlombok.org/download.html
Copy the Jar to eclipse installation directory
right click the Jar>Open with Open JDK or Oracle JDK Java 7 Runtime
Follow the Installation Steps    

*TestNg  

*Git Integration

*SonarQube (optional)

Plugin configuration for Cucumber Feature
Open Run Configurations
Select Cucumber Feature and create one new configuration
Project: orgnameAutomationFramework
Feature: src/orgnameAutomationFramework/src/test/resources/features
Glue:  com.orgname.test.step_definitions
Reports:  monochrome, pretty

File >> Setting >>  
Search for Annotation Processing  
(Java Compiler ... Annotation Processing>> Enable the check box


Import Project into Intellij
----------------------------
File>Import Project>
Browse to orgnameAutomationFramework

run "mvn validate" (To Resolve DB2 dependencies)

Import Project into Eclipse
--------------------------
File>Import>Maven>Existing Maven Projects>Next>
Browse to orgnameAutomationFramework
Ensure pom.xml is found
Finish

open terminal
cd to test root directory
run "mvn clean eclipse:eclipse"
 
run "mvn validate" (To Resolve DB2 dependencies)



Framework Setup steps
============================
The URL, Browser Configuration, jdbc connections etc are defined in the respective config.properties file under each desired profile which you want to run.
In pom.xml we use the relative path within <profile.path> as shown below to invoke or make use of these profiles. 
src/main/resources/profiles

Open "pom.xml" 
Scroll to Profile section : - Choose desired profile e.g "dev" for running locally

        <profile>
			<id>dev</id>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
			<properties>
				<profile.path>/profiles/dev/config.properties</profile.path>
				<testToRun>**/*WebATSuite*.class</testToRun>
			</properties>
		</profile>

Compile Build or Run Tests
==========================

Command Line

cd to root ot orgnameAutomationFramework project directory

To clean and compile the build
-----------------------------
mvn clean install -DskipTests

To run all tests parallel
------------------------
mvn clean install  

OR

mvn clean install -P dev  

*Note -P dev is default profile hence doesn't need to be specified for every run 

To run a single test with tags
------------------------

mvn clean install -Dcucumber.options="--tags @gui --tags ~@api" -P single

** Note "~" before tag means this specific tag will not run

To Rerun failed test scenarios
---------------------------------------------
After a test suite is run. There is rerun.txt created at the project root level in rerun folder. This rerun file contains the details of all the failed scenarios.
e.g master_cucumber_testng\rerun\rerun.txt

mvn clean install -Dcucumber.options="@rerun/rerun.txt" -P single


Jenkins
======

mvn clean install  -P jenkins

** Create 3 string parameters in jenkins 
with the below conventions which will be passed on as an argument to the above maven command  

Key: cucumber.options Default Value: --tags @gui, @api     
Key: driverhost  Default Value: 0000.000.000.00 (RAS server with selenium server configured for the project)  
Key: driverport  Default Value: 4444  


Report
======

Local reports
-------------
Standard HTML Report  
A report will be generated at /target/cucumber-report/index.html  

Preety Cucumber-Html Report  
A report will be generated at /target/cucumber-report/cucumber-html-reports/feature-overview.html 

Jenkins report
--------------
The report will be available as part of configured Jenkins test build  
**Cucumber plugin for Jenkins needs to be installed





Getting Started
===========================


Feature Files
-------------------------------------------------------------------
These files contains the acceptance criteria which are written in Gherkin Language and contains various scenarios.  
The feature files are tagged with "@tagname" to group common feature files 

File Extension:  *.feature    
Location: "/home/dev/src/orgnameAutomationFramework/src/test/resources/features"      
Directory:  Separate directories for GUI and API tests, Group common features files in a 
single directory    
File Conventions:Meaning full name "WebRegister.feature"
Example:   
@gui
Feature: REGISTER:- As a new customer of client I would like to register 
Scenario:Perform a New registration for a customer
    Given I navigate to the client "HOME" page


Page Objects
-------------------------------------------------------------------
PageObjects are used to store the WebElements for a Web Page.
A good practice is to have a separate class for every single WebPage.
To avoid duplication for multiple pages which have common web page elements a Parent class can be created 
and the child class can then inherit.  
Every Page  class extends "PageObject.class" to make use of the WebDriver Object and utility functions.  
In case of Parent and Child Class, Parent class extends PageObject class and child class extends Parent class      
   
Location: /home/dev/src/test/java/com/orgname/test/pageobjects
Directory structure: Group common Page Objects classes in a single directory e.g Login Functionality Classes in Login Directory      
File Conventions:Every Class file ends with Page.class (Homepage.class)  

Example:   

public class HomeSamplePage extends PageObject {

    private By headerSignInLink = By.cssSelector("#headerSignInLink a");

    public void clickSignInLink() {
        waitForExpectedElement(headerSignInLink).click();
    }
}


Step Definitions
--------------------------------------------------------------------
Every steps defined in Feature file needs to be implemented in Step Definitions Class

Location: /home/dev/src/orgnameAutomationFramework/src/test/java/com/orgname/test/step_definitions 
Directory structure: Separate directories for GUI and API tests, Group common step definition files in a 
                     single directory    
File Conventions:Every Class file ends with Steps.class (LoginSteps.class)  

Example:  

public class HomePageSteps {

   private HomeSamplePage homePage =  new HomeSamplePage();

    @And("^i click on Sign In on the Home Page$")
    public void i_click_on_Sign_In_on__the_Home_Page() throws Throwable {
        homePage.clickSignInLink();
    }
}

Run Test Suite
--------------------------------------------------------------------
Test Suites are used to run a group of Tests which are tagged and represented in form of Feature files & Scenarios

Location: /home/dev/src/orgnameAutomationFramework/src/test/java/com/orgname/test
File Conventions:Every Class file ends with Suite.class (RunWebATSuite.class)  


    @CucumberOptions(features = "target/test-classes", tags = {"@gui"}, monochrome = true, plugin = {
            "pretty", "html:target/cucumber-report/runwebat",
            "json:target/cucumber-report/runwebat/cucumber.json",
            "rerun:target/cucumber-report/runwebat/rerun.txt"},
            glue = "com.orgname.test")
    public class RunWebATSuite extends AbstractTestNGCucumberTests {
    }

Where: -  
features: represent the location of feature files from the compiled build  
tags:  multiple tags can be specified by comma separated denotation, if a specific tag needs to be excluded then this 
        can be specified by "~" . e.g "~@api" feature files tagged with "~api" will not be run as a part of Test Suite.  
plugin: html,json and rerun reports are created. if a TesSuite is renamed then change the reporting directory name for both reports  
   

Other ways to run the tests or Test Suite
---------------------------------------------
*command line using Maven:-  mvn clean install -P dev  
*IDE Plugins: - Eclipse or Intellij via TestNg plugin or Maven plugin or Cucumber-Java plugin  
*IDE TestNg Suite xml: - file located at "src/test/resources" TestNGRunTestSuite.xml (Right click and run as TestNg)
  

=======
Initial version

