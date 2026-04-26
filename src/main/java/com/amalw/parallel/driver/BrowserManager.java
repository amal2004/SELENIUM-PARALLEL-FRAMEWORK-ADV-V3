package com.amalw.parallel.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.amalw.parallel.enums.BrowserType;
import com.amalw.parallel.exceptions.FrameworkException;

import io.github.bonigarcia.wdm.WebDriverManager;

public final class BrowserManager {

	private BrowserManager() {
	}

	// Create driver based on browser type using values from enums
	public static WebDriver createDriver(BrowserType type, boolean headless) {

		switch (type) {
		case CHROME:
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver(chromeOptions(headless));

		case FIREFOX:
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver(firefoxOptions(headless));

		case EDGE:
			WebDriverManager.edgedriver().setup();
			return new EdgeDriver(edgeOptions(headless));

		default:
			throw new FrameworkException("Unsupported browser: " + type);
		}
	}

	// Configure Chrome options
	private static ChromeOptions chromeOptions(boolean headless) {
		ChromeOptions options = new ChromeOptions();
		if (headless)
			options.addArguments("--headless=new");
		options.addArguments("--disable-notifications", "--start-maximized");
		return options;
	}

	// Configure Firefox options
	private static FirefoxOptions firefoxOptions(boolean headless) {
		FirefoxOptions options = new FirefoxOptions();
		if (headless)
			options.addArguments("-headless");
		return options;
	}

	// Configure Edge options
	private static EdgeOptions edgeOptions(boolean headless) {
		EdgeOptions options = new EdgeOptions();
		if (headless)
			options.addArguments("--headless=new");
		return options;
	}
}
