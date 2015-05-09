package org.acacia.log.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Logger_Java{
	
	private static String ACACIA_LOGGER_NAME = "org.acacia.log.acacia";
	
	public static void specifyLogFile(String logFileName){
		Properties log4jprops = new Properties();
		InputStream is = Logger_Java.class.getResourceAsStream("/log4j.properties");
		try {
			log4jprops.load(is);
			log4jprops.setProperty("log4j.appender.AcaciaAppender.File", logFileName);
			PropertyConfigurator.configure(log4jprops);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void trace(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).trace(message);
	}
	
	public static void debug(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).debug(message);
	}
	
	public static void info(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).info(message);
	}
	
	public static void warn(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).warn(message);
	}
	
	public static void error(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).error(message);
	}
	
	public static void fatal(String message){
		Logger.getLogger(ACACIA_LOGGER_NAME).fatal(message);
	}
}

