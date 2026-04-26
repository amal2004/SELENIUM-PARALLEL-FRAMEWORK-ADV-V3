package com.amalw.parallel.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.amalw.parallel.exceptions.FrameworkException;

/* ConfigManager is responsible for loading and managing configuration values
from the config.properties file. */

public class ConfigManager {

	// Properties object to store key-value pairs
	private static final Properties PROPS  = new Properties();
	private static final String CONFIG_FILE = "/config.properties";

	// Static block executes once to load configuration at class initialization
	static {
        try (InputStream is = ConfigManager.class.getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new FrameworkException("Config file not found: " + CONFIG_FILE);
            }
            PROPS.load(is);
        } catch (Exception e) {
            throw new FrameworkException("Failed to load config file");
        }
    }

	private ConfigManager() {}
	

	// Get required property by key
	 public static String get(String key) {
		// First check system properties from cmd line
	        String value = System.getProperty(key);
	        if (value == null) {
	        	// Then check properties file 
	            value = PROPS.getProperty(key);
	        }
	     // Validate value
	        if (value == null || value.trim().isEmpty()) {
	            throw new FrameworkException("Missing config key: " + key);
	        }
	        return value.trim();
	    }
	
	
	// Get property with default value
	public static String get(String key, String defaultValue) {

		// Check system property first
		String value = System.getProperty(key);
		if (value == null || value.isBlank()) {
			value = PROPS.getProperty(key, defaultValue);
		}
		return value.trim();

	}

	// Get boolean property with default value
	public static boolean getBoolean(String key, boolean defaultValue) {
		return Boolean.parseBoolean(get(key, String.valueOf(defaultValue)));
	}

	// Get integer property with default value
	public static int getInt(String key, int defaultValue) {
		  try {
	            return Integer.parseInt(get(key, String.valueOf(defaultValue)));
	        } catch (NumberFormatException e) {
	            throw new FrameworkException("Invalid integer for key: " + key);
	        }
	}
	
}
