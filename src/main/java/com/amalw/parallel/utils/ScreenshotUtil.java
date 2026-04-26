package com.amalw.parallel.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.amalw.parallel.driver.DriverFactory;

/* ScreenshotUtil provides utility methods to capture screenshots during test execution. */

public class ScreenshotUtil {

	// base folder for screenshots
	private static final String SCREENSHOT_DIR = "target/screenshots/";

	// Captures a screenshot and saves it with a unique timestamp-based name.
	public static String takeScreenshot(String screenshotName) {

		// Get current thread's WebDriver instance
		WebDriver driver = DriverFactory.getDriver();

		// Validate driver availability
		if (driver == null) {
			System.err.println("Driver is null. Cannot take screenshot.");
			return null;
		}

		// Prepare destination directory
		Path destDir = Paths.get(SCREENSHOT_DIR);
		try {
			Files.createDirectories(destDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Prepare unique file name
		String filename = screenshotName + "_" + System.currentTimeMillis() + ".png";
		Path destPath = destDir.resolve(filename);
		File destFile = destPath.toFile();

		try {
			// Cast driver to TakesScreenshot
			TakesScreenshot ts = (TakesScreenshot) driver;

			// Capture screenshot as file
			File src = ts.getScreenshotAs(OutputType.FILE);

			// Copy screenshot to destination path
			Files.copy(src.toPath(), destFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Return saved screenshot path
		return destPath.toString();
	}
}