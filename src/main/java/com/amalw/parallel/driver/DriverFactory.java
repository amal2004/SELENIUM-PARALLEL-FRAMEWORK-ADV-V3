package com.amalw.parallel.driver;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import com.amalw.parallel.config.ConfigManager;
import com.amalw.parallel.enums.BrowserType;

/* DriverFactory creates and manages thread-safe WebDriver instances */
public final class DriverFactory {

	// ThreadLocal to allow safe parallel execution
	private static final ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	// Private constructor to prevent instantiation
	private DriverFactory() {
	}

	// Returns the WebDriver instance associated with the current thread
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	// Initializes WebDriver based on the specified browser type
	public static void initDriver(String browserName) throws Exception {

		// If a driver already exists in the current thread, do nothing
		if (tlDriver.get() != null)
			return;

		// If no browser name is passed, get it from properties file
		String browserValue = (browserName == null || browserName.isBlank()) ? ConfigManager.get("browser")
				: browserName;

		// Convert the browser string value into a BrowserType enum
		BrowserType browser = BrowserType.from(browserValue);

		// Read the headless mode flag from config
		boolean headless = ConfigManager.getBoolean("headless", false);

		// Create a WebDriver instance based on browser type
		WebDriver driver = BrowserManager.createDriver(browser, headless);

		// Apply additional configurations to the driver
		configureDriver(driver);

		// Store the driver in ThreadLocal
		tlDriver.set(driver);
	}

	private static void configureDriver(WebDriver driver) {
		// Set window to open maximized
		driver.manage().window().maximize();
		// Disable implicit wait to avoid the conflicts
		driver.manage().timeouts().implicitlyWait(Duration.ZERO);
		// Set page load timeout from config
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigManager.getInt("pageLoadTimeout", 60)));
	}

	// Quits the driver and removes it from ThreadLocal storage
	public static void quitDriver() {
		// get current thread driver
		WebDriver driver = tlDriver.get();
		if (driver != null) {
			driver.quit();
			tlDriver.remove();
		}
	}
}