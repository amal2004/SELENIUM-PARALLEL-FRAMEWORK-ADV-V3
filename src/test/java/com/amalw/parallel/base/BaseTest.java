package com.amalw.parallel.base;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.amalw.parallel.driver.DriverFactory;
import com.amalw.parallel.utils.ScreenshotUtil;

/* BaseTest is the foundation for all test classes. All test classes extend the BaseTest */

public class BaseTest {

	//protected WebDriver driver;

	// Runs before each test method
	@BeforeMethod(alwaysRun = true)
	@Parameters("browser")
	public void setUp(@Optional("chrome") String browser) throws Exception {

		// Initialize WebDriver for current thread
		DriverFactory.initDriver(browser);

	}

	// Runs after each test method
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult result) {

		// Capture screenshot if test has failed
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotPath = ScreenshotUtil.takeScreenshot(result.getName());
			System.out.println("Screenshot captured: " + screenshotPath);
		}
		// Quit WebDriver and clean ThreadLocal
		DriverFactory.quitDriver();
	}

//	// Returns the current thread-safe WebDriver instance
//	protected WebDriver getDriver() {
//		return DriverFactory.getDriver();
//	}
}
