package modules;

/**
 * @author Madhu Babu
 * @date 11-Mar-2020
 * @desc Module wise test steps/ test Cases/ test scenarios
 */

import com.relevantcodes.extentreports.ExtentTest;

import Sureify.AutomationTemplate.Suite;
import Sureify.AutomationTemplate.WebActions;

public class LandingPage 
{
	ExtentTest landingPage;
	
	public LandingPage()
	{
		/*
		 * In this construction module wise test need to declare
		 */
		landingPage = Suite.report.startTest("Testing Module Name");
		Suite.Tests.add(landingPage);
	}
	
	public void testMethod()
	{
		/*
		* Write the test steps here
		* WebActions have the common bindings (Click, SendKeys, clear, submit)
		*/
		
    	WebActions.launchSite();
    	WebActions.logTestPass(landingPage, "Given webpage");
//    	WebActions.logTestFail(landingPage, "Given webpage");
//    	WebActions.logTestError(landingPage, 0);
    	
//    	Assert.assertTrue(b);
    	
	}
	
	public void tm()
	{
		try {
			WebActions.launchSite();
			System.out.println("Before");
		WebActions.logTestFail(landingPage, "1");
		System.out.println("After");
		WebActions.logTestFail(landingPage, "2");
		WebActions.logTestFail(landingPage, "3");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
