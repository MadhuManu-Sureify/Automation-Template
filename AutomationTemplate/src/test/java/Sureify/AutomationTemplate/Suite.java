package Sureify.AutomationTemplate;

/**
 * @author Madhu Babu
 * @date 11-Mar-2020
 * @desc This Class for set up the driver instance for given browser with the given configuration
 */

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Suite
{	
	DesiredCapabilities capability;
	public static WebDriver driver;
	public static WebDriverWait w;
	public static String siteURL_UAT;
	public static JavascriptExecutor js;
	public static ExtentReports report;
	public static ArrayList<ExtentTest> Tests;
	public static ExtentTest testInfo;

	public static Properties properties;
	
	public static void suitUp()
	{
		try {
			properties = new Properties();
			properties.load(new FileInputStream(System.getProperty("user.dir") + "/src/config/config.properties"));
			
			//Driver Setup
			System.setProperty("webdriver.chrome.driver", properties.getProperty("chromeDriverLOC"));
			System.setProperty("webdriver.gecko.driver", properties.getProperty("firefoxDriverLOC"));
			
			if(properties.getProperty("Browser").equalsIgnoreCase("Chrome")) {
				driver = new ChromeDriver();
			}else if(properties.getProperty("Browser").equalsIgnoreCase("Firefox")) {
				try {
					driver = new FirefoxDriver();
				}
				catch(Exception e) {
					File pathToBinary = new File("/snap/firefox/323/firefox");
					FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);   
					DesiredCapabilities desired = DesiredCapabilities.firefox();
					FirefoxOptions options = new FirefoxOptions();
					desired.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options.setBinary(firefoxBinary));
					driver = new FirefoxDriver(options);
				}
			}else if(properties.getProperty("Browser").equalsIgnoreCase("IE")) {
				driver = new InternetExplorerDriver();
			}
			else if(properties.getProperty("Browser").equalsIgnoreCase("Edge")) {
				driver = new EdgeDriver();
			}
	   	 	
	        w = new WebDriverWait(driver, Integer.parseInt(properties.getProperty("explicitWait")));
	        js = (JavascriptExecutor) driver;
	        driver.manage().timeouts().implicitlyWait(Integer.parseInt(properties.getProperty("implicitWait")), TimeUnit.SECONDS);
	        
	        siteURL_UAT = properties.getProperty("siteUATURL");
	        
	        //Reports Setup
	        Tests = new ArrayList<ExtentTest>();
	        Tests.add(testInfo);
	        report = new ExtentReports("target/Report/TestReport_" + new SimpleDateFormat("yyyyMMddHHmm").format(new Date()) + ".html");
	        testInfo = report.startTest("Test Information");
		}
		catch(Exception e){
			System.out.println("Error");
			System.out.println(e.getMessage());
		}
		
	}
	
	public static void suitTearDown()
	{
		System.out.println(Tests.size());
		for(int i = 0; i<Tests.size(); i++)
		{
			report.endTest(Tests.get(i));
		}
		System.out.println("end Test");
		
		//Reports save
		report.flush();
		System.out.println("report flush");
		driver.quit();
	}
}
