package tests;

/**
 * @author Madhu Babu
 * @date 11-Mar-2020
 * @desc This Class is Main Test runner class to start the test
 */

import org.testng.annotations.Test;

import Sureify.AutomationTemplate.Suite;
import modules.LandingPage;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class MainTestRunner {
  
	
	@Test
  public void firsttestcase() {
	  LandingPage l = new LandingPage();
	  l.tm();
	  System.out.println("Test Completed");
  }
	
	
  @BeforeTest
  public void beforeTest() {
	  /*
	   * Start the recorder instance before the every test
	   */
	  
//	 WebActions.startRecorder();	  
  }

  @AfterTest
  public void afterTest() {
	  /*
	   * Stops the running recording instance after the every test
	   */
//	  WebActions.stopRecorder();
  }

  @BeforeSuite
  public void beforeSuite() {
	  /*
	   * SuiteUp Driver, WebDriver, Reports 
	   */
	  Suite.suitUp();
  }

  @AfterSuite
  public void afterSuite() {
	  /*
	   * Suite tear down, reports flush
	   */
	  Suite.suitTearDown();
  }

}
