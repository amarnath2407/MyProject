

https://github.com/alectronic0/fairyLights

Feature: a valid credential  user can able to login


  Scenario: verify log in details
    Given user on william-hill main page
    When user logged in with valid credentials
    Then verify that user has been successfully logged
    
    
    
    
    
    Feature: successful logged-in user can able to bet on various games


  Scenario Outline: verify that betting on function on different games
    Given user logged in with valid credentials
    And navigated to <sport> event section
    When user selected first active bet-slip
    And  placed a bet <money>
    Then verify "To Return" "Total stake" vales on the bet receipt
    And verify that balance has been updated with orginal value
    Examples:
      | sport    | money |
      | football | 5     |
      | tennis   | 7     |
      
      
      <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.test.whill</groupId>
    <artifactId>william-hill</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <log4j2.version>2.7</log4j2.version>
        <slf4j.version>1.7.21</slf4j.version>
        <junit.version>4.12</junit.version>
        <lombok>1.16.8</lombok>
        <cucumber.version>1.2.4</cucumber.version>
        <gson.version>2.7</gson.version>
        <cucumber-report.version>3.4.0</cucumber-report.version>
        <webdrivermanager.version>1.5.0</webdrivermanager.version>
        <selenium.version>2.53.1</selenium.version>
        <maven-resource-plugin>2.7</maven-resource-plugin>
        <maven-compiler-plugin>2.5.1</maven-compiler-plugin>
        <maven-cucumber-reporting>0.0.8</maven-cucumber-reporting>
        <maven-surefire-plugin>2.17</maven-surefire-plugin>
        <mojo-exec-maven-plugin>1.2.1</mojo-exec-maven-plugin>
    </properties>
    <build>
    <plugins>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>${maven-compiler-plugin}</version>
            <configuration>
                <encoding>${utf-8}</encoding>
                <source>${java-version}</source>
                <target>${java-version}</target>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>${maven-surefire-plugin}</version>
            <executions>
                <execution>
                    <id>test</id>
                    <phase>test</phase>
                    <goals>
                        <goal>test</goal>
                    </goals>
                    <configuration>
                        <threadCount>4</threadCount>
                        <perCoreThreadCount>true</perCoreThreadCount>
                        <forkCount>4</forkCount>
                        <reuseForks>false</reuseForks>
                        <argLine>-Duser.language=en</argLine>
                        <argLine>-Xmx1024m</argLine>
                        <argLine>-XX:MaxPermSize=256m</argLine>
                        <argLine>-Dfile.encoding=UTF-8</argLine>
                        <useFile>false</useFile>
                        <includes>
                            <include>${testToRun}</include>
                        </includes>
                        <testFailureIgnore>true</testFailureIgnore>
                    </configuration>
                </execution>
            </executions>

        </plugin>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>${mojo-exec-maven-plugin}</version>
            <executions>
                <execution>
                    <phase>test</phase>
                    <goals>
                        <goal>java</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <mainClass>com.orgname.test.ReportMerger</mainClass>
                <arguments>
                    <argument>target/cucumber-report/</argument>
                </arguments>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <version>${maven-resource-plugin}</version>
            <configuration>
                <!-- specify UTF-8, ISO-8859-1 or any other file encoding -->
                <encoding>${utf-8}</encoding>

                <resources>
                    <resource>
                        <directory>src/main/resources</directory>
                        <filtering>true</filtering>
                    </resource>
                    <resource>
                        <directory>src/test/resources</directory>
                        <filtering>true</filtering>
                    </resource>
                </resources>

            </configuration>
        </plugin>

        <plugin>
            <groupId>net.masterthought</groupId>
            <artifactId>maven-cucumber-reporting</artifactId>
            <version>${maven-cucumber-reporting}</version>
            <executions>
                <execution>
                    <id>execution</id>
                    <phase>test</phase>
                    <goals>
                        <goal>generate</goal>
                    </goals>
                    <configuration>
                        <projectName>cucumbertests</projectName>
                        <outputDirectory>target/cucumber-report/cucumber-html-reports</outputDirectory>
                        <cucumberOutput>target/cucumber-report/cucumber.json</cucumberOutput>
                        <enableFlashCharts>false</enableFlashCharts>
                    </configuration>
                </execution>
            </executions>
        </plugin>

    </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>${log4j2.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>${log4j2.version}</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-simple</artifactId>
            <version>${slf4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-server</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-firefox-driver</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-chrome-driver</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-ie-driver</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>${junit.version}</version>
        </dependency>
        <!-- lombok for Getter Setters-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok}</version>
        </dependency>
        <!--lombok -->

        <!--Cucucmber dependencies -->
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-core</artifactId>
            <version>${cucumber.version}</version>
        </dependency>

        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-picocontainer</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>

    </dependencies>


</project>

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

