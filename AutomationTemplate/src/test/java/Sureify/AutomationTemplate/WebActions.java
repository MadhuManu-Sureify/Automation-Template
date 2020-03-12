package Sureify.AutomationTemplate;

/**
 * @author Madhu Babu
 * @date 11-Mar-2020
 * @desc Selenium and Generic functions class
 */

import java.io.File;
import java.text.SimpleDateFormat;

import java.util.Date;

import org.apache.commons.io.FileUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.ScreenRecorder;
import utilities.ScreenRecorder.Quality;

public class WebActions 
{
	static ScreenRecorder recorder;
	static boolean recordFlag = true;
	public static WebDriverWait w = Suite.w;
	public static WebDriver dr;
	
	public static String launchSite() 
    {
		Suite.driver.manage().window().maximize();
		Suite.driver.get(Suite.siteURL_UAT);
		Suite.testInfo.log(LogStatus.INFO, " <h6>About this Test</h6>");
		Suite.testInfo.log(LogStatus.INFO, "<b>Url: </b>" + Suite.driver.getCurrentUrl());
		
		return Suite.driver.getTitle();
    }
	
	public static void get(String url) 
    {
		Suite.driver.manage().window().maximize();
		Suite.driver.get(url);
    }
	
	public static void logTestPass(ExtentTest test, String elm)
	{
		test.log(LogStatus.PASS, elm + " Test Passed");
	}
	
	public static void logTestFail(ExtentTest test, String elm) throws Exception
	{
		if(elm.contains("Txt"))
			moveToElement(Suite.driver.findElement(By.id(elm)));
		else if(elm.contains("//"))
			moveToElement(Suite.driver.findElement(By.xpath(elm)));

		test.log(LogStatus.FAIL, elm + "Test Failed " + test.addBase64ScreenShot(getScreenshot(Suite.driver)));
	}
	
	public static void logTestError(ExtentTest test, int exType) throws Exception
	{
		dr = Suite.driver;
		switch(exType) {
		
		case 1:
			test.log(LogStatus.ERROR, "Element no longer available", test.addScreenCapture(getScreenshot(dr)));;
		    break;
		case 2:
			test.log(LogStatus.ERROR, "No Session is Running or session closed", test.addScreenCapture(getScreenshot(dr)));
		    break;
		case 3:
			test.log(LogStatus.ERROR, "Webdriver initiation error", test.addScreenCapture(getScreenshot(dr)));
		    break;
		case 4:
			test.log(LogStatus.ERROR, "Element is not found", test.addScreenCapture(getScreenshot(dr)));
		    break;
		case 5:
			test.log(LogStatus.ERROR, "Element is overlayed by another element", test.addScreenCapture(getScreenshot(dr)));
		    break;
		default:
			test.log(LogStatus.ERROR, "No Definition");
		}
	}
	
	public static void logVideo(ExtentTest test, String mediaPath)
	{
		test.log(LogStatus.INFO,  "<video controls><source src=\"" + System.getProperty("user.dir") + mediaPath + "\" type=\"video/mp4\"></video>");
	}
	
	public static void handleException(WebDriver dr, ExtentTest test, Exception e) throws Exception
	{
		if(e.getMessage().contains("StaleElementReferenceException"))
			logTestError(test, 1);
		else if(e.getMessage().contains("NoSuchSessionException"))
			logTestError(test, 2);
		else if(e.getMessage().contains("WebDriverException"))
			logTestError(test, 3);
		else if(e.getMessage().contains("NoSuchElementException") || e.getMessage().contains("no such element: Unable to locate element:"))
			logTestError(test, 4);
		else if(e.getMessage().contains("element click intercepted"))
			logTestError(test, 5);
		else
			test.log(LogStatus.ERROR, "Exception: " + e.getMessage(), test.addScreenCapture(getScreenshot(dr)));
	}
	
	public static String getScreenshot(WebDriver dr) throws Exception
	{
		dr = Suite.driver;
		TakesScreenshot scrShot =((TakesScreenshot)dr);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		String srcBASE64 = scrShot.getScreenshotAs(OutputType.BASE64);
		String filePath = System.getProperty("user.dir") + "target/Report/fail-screens/Screenshot_" + System.getProperties().get("user.name") +
				"_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + ".png";
		System.out.println(filePath);
		File DestFile=new File(filePath);
		FileUtils.copyFile(SrcFile, DestFile);
		System.out.println(srcBASE64);
		return "data:image/jpeg;base64,"+ srcBASE64;
	}
	
    public static String startRecorder()
    {
    	String outputFilename = "";
    	try {
    		outputFilename = "target/Report/recordedVideo/RecordedVideo_" + new SimpleDateFormat("ddMMyyyy_HHmmssSSS").format(new Date()) + ".mp4";
    		recorder = new ScreenRecorder(outputFilename);
    		recorder.startCapture();
    	}
    	catch(Exception ex){System.out.println(ex.getMessage());}
    	
    	return outputFilename;
    }
    
	public static void stopRecorder()
	{
		try {
			recorder.stopCapture();
		}
		catch(Exception ex){System.out.println(ex.getMessage());}
	}
	
	public static void sendKeys(WebElement varEle, String Data)
	{
		waitFor(varEle);
		varEle.sendKeys(Data);
	}
	
	public static void click(WebElement varEle) throws Exception
	{
		try {
			waitFor(varEle);
			varEle.click();
		}catch(Exception e)
		{
			jsScrollIntoView(varEle);
			varEle.click();
		}	
	}
	
	public static void jsClick(WebElement varEle) throws Exception
	{
		waitFor(varEle);
		Suite.js.executeScript("arguments[0].click();", varEle);
		Thread.sleep(500);
	}
	
	public static void clear(WebElement varEle) throws Exception
	{
		waitFor(varEle);
		varEle.clear();
	}
	
	public static void jsScrollBy(int x, int y) throws Exception
	{
		Suite.js.executeScript("window.scrollBy(" + x +"," + y + ")");
		Thread.sleep(400);
	}
	
	public static void jsScrollToEnd() throws Exception
	{
		Suite.js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(400);
	}
	
	public static void jsScrollIntoView(WebElement varEle) throws Exception
	{
		waitFor(varEle);
		Suite.js.executeScript("arguments[0].scrollIntoView();", varEle);//Define the position change to True
		Thread.sleep(400);
	}
	
	public static void jsScrollIntoView(WebElement varEle, boolean flag) throws Exception
	{
		waitFor(varEle);
		Suite.js.executeScript("arguments[0].scrollIntoView(" + flag + ");", varEle);//Define the position change to True
		Thread.sleep(400);
	}
	
	public static void moveToElement(WebElement varEle)
	{
		new Actions(Suite.driver).moveToElement(varEle).perform();
	}
	
	public static void actionsClick(WebElement varEle)
	{
		new Actions(Suite.driver).click(varEle).perform();
	}
	
	public static void clickAndHold(WebElement varEle)
	{
		new Actions(Suite.driver).clickAndHold(varEle).perform();
	}
	
	public static void release(WebElement varEle)
	{
		new Actions(Suite.driver).release(varEle).perform();
	}
	
	public static void contextClick(WebElement varEle)
	{
		new Actions(Suite.driver).contextClick(varEle).perform();
	}
	
	public static void doubleClick(WebElement varEle)
	{
		new Actions(Suite.driver).doubleClick(varEle).perform();
	}
	
	public static void dragAndDrop(WebElement varEle1, WebElement varEle2)
	{
		new Actions(Suite.driver).dragAndDrop(varEle1, varEle2).perform();
	}
	
	public static void actionsSendKeys(WebElement varEle, String value)
	{
		new Actions(Suite.driver).sendKeys(varEle, value).perform();
	}
	
	public static void deselectAll(WebElement varEle)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.deselectAll();
	}
	
	public static void deselectByIndex(WebElement varEle, int index)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.deselectByIndex(index);
	}
	
	public static void deselectByValue(WebElement varEle, String value)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.deselectByValue(value);
	}
	
	public static void deselectByVisibleText(WebElement varEle, String text)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.deselectByVisibleText(text);
	}
	
	public static void selectByIndex(WebElement varEle, int index)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.selectByIndex(index);
	}
	
	public static void selectByValue(WebElement varEle, String value)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.selectByValue(value);
	}
	
	public static void selectByVisibleText(WebElement varEle, String text)
	{
		waitFor(varEle);
		Select s = new Select(varEle);
		s.selectByVisibleText(text);
	}
	
	public static void selectItem(WebElement varEle, String itemName) throws Exception
	{
		waitFor(varEle);
		click(varEle);
		Thread.sleep(200);
		click(Suite.driver.findElement(By.xpath("//*[text()='"+ itemName + "']")));
		Thread.sleep(200);
	}
	
	public static void navigate(String navigation)
	{
		if(navigation.equalsIgnoreCase("BACK")) {
			Suite.driver.navigate().back();
		}else if(navigation.equalsIgnoreCase("REFRESH")) {
			Suite.driver.navigate().refresh();
		} else if(navigation.equalsIgnoreCase("FORWARD")) {
			Suite.driver.navigate().forward();
		} 
	}
	
	public static void navigate(String navigation, String url)
	{
		if(navigation.equalsIgnoreCase("to")) {
			Suite.driver.navigate().to(url);
		}
	}
	
	public static void switchToActive()
	{
		Suite.driver.switchTo().activeElement();
	}
	
	public static void switchToDefault()
	{
		Suite.driver.switchTo().defaultContent();
	}
	
	public static void waitFor(String xpath)
	{
		w.until(ExpectedConditions.visibilityOf(Suite.driver.findElement(By.xpath(xpath))));	
	}
	
	public static void waitFor(WebElement varEle)
	{
		w.until(ExpectedConditions.visibilityOf(varEle));
	}
	
	//Update this By send keys/ by selectDOB
	public static void selectDOB(WebElement varEle, int date, int month, int year) throws Exception
	{		
		WebElement dateWidget = varEle;
		click(dateWidget);
		
		Select yearSelect = new Select(Suite.driver.findElement(By.xpath("//select[@class='react-datepicker__year-select']")));
		yearSelect.selectByVisibleText(Integer.toString(year));
		
		Select monthSelect = new Select(Suite.driver.findElement(By.xpath("//select[@class='react-datepicker__month-select']")));
		monthSelect.selectByIndex(month-1);

		click(Suite.driver.findElement(By.xpath("//div[@class='react-datepicker__day react-datepicker__day--0"+ Integer.toString(date) + "']")));
	}
	
	public static void clickContinue() throws Exception
	{
		waitFor(Suite.driver.findElement(By.xpath(AppData.continueBtn)));
		Thread.sleep(200);
		jsScrollIntoView(Suite.driver.findElement(By.xpath(AppData.continueBtn)));
		click(Suite.driver.findElement(By.xpath(AppData.continueBtn)));
	}
	
	public static void clickNext() throws Exception
	{
		waitFor(Suite.driver.findElement(By.xpath(AppData.nextBtn)));
		w.until(ExpectedConditions.elementToBeClickable(Suite.driver.findElement(By.xpath(AppData.nextBtn))));
		int msTime = 30000; 
		int doUntil = (int) (msTime + System.currentTimeMillis());
		while(System.currentTimeMillis() != doUntil)
		{
			try {
				if(Suite.driver.findElement(By.xpath(AppData.nextBtn)).isEnabled())
				{
					click(Suite.driver.findElement(By.xpath(AppData.nextBtn)));
					break;
				}
			}catch(Exception nextEx) {}
		}
	}
	
	public static void clickSave() throws Exception
	{
		waitFor(Suite.driver.findElement(By.xpath(AppData.saveBtn)));
		Thread.sleep(1000);
		jsScrollIntoView(Suite.driver.findElement(By.xpath(AppData.saveBtn)));
		click(Suite.driver.findElement(By.xpath(AppData.saveBtn)));
	}
	
	public static void clickYes() throws Exception
	{
		click(Suite.driver.findElement(By.xpath(AppData.yesBtn)));
	}
	
	public static void clickNo() throws Exception
	{
		click(Suite.driver.findElement(By.xpath(AppData.noBtn)));
	}
	
	public static void doDocSign(WebDriver dr) throws Exception
	{
		try {
			try {
				w.until(ExpectedConditions.visibilityOfElementLocated(By.id(AppData.docSignAcceptCheckBoxID)));
				Thread.sleep(5000);
				jsClick(dr.findElement(By.id(AppData.docSignAcceptCheckBoxID)));
			}catch(TimeoutException te){
				w.until(ExpectedConditions.visibilityOfElementLocated(By.id(AppData.docSignContinueBtnID)));
				}
			
			Thread.sleep(400);
			jsClick(dr.findElement(By.id(AppData.docSignContinueBtnID)));
	
			waitFor(AppData.docSignStartBtn);
	
			Thread.sleep(400);
			jsClick(dr.findElement(By.xpath(AppData.docSignStartBtn)));
			
			Thread.sleep(400);
			jsScrollIntoView(dr.findElement(By.xpath(AppData.docSignDownloadIcon)));
			
			Thread.sleep(400);
			
			try {
				waitFor(AppData.docSignAdoptSignPopUp);
				Thread.sleep(5000);
				jsClick(dr.findElement(By.xpath(AppData.docSignAdoptSignPopUpAdoptSignBtn)));
			}catch(TimeoutException te){System.out.println(te.getMessage());}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
