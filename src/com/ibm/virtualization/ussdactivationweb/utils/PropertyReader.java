/*
 * Created on Feb 23, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.ussdactivationweb.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertyReader {

	private final static Logger logger = Logger.getLogger(PropertyReader.class);
	private static final boolean DEFAULT_DEBUG_FLAG = true;
	private static ResourceBundle bundle;
	//------------------------------------------------------------------------
	// Static block to load application resource file
	//------------------------------------------------------------------------
	static {
		loadConfiguration();
	}
	
	
	//------------------------------------------------------------------------
	// Function to load application resource file
	//------------------------------------------------------------------------
	private static void loadConfiguration() {
		logger.debug("entering loadConfiguration() function.");
		bundle = ResourceBundle.getBundle(Constants.WEB_APPLICATION_RESOURCE_BUNDLE, Locale.ENGLISH);
		logger.debug("exiting loadConfiguration() function."+bundle);
	}
	
	//------------------------------------------------------------------------
	// Function to return corresponding value of specified key
	//------------------------------------------------------------------------
	public static String getValue(String key) {
		if(key == null || key.trim().length()<=0){ return null;}
		return bundle.getString(key.trim());
	}

	//------------------------------------------------------------------------
	// Function to print all elements of ResourceBundle
	//------------------------------------------------------------------------
	public static void printElements(ResourceBundle bundle) {
		Enumeration enum1 = bundle.getKeys();
		String key = null;
		while (enum1.hasMoreElements()) {
			key = (String)enum1.nextElement();
			writeInConsole(key +" :: "+ bundle.getString(key));
		}
	}

	//------------------------------------------------------------------------
	// Function to write message in log file as well as console [optional]
	//------------------------------------------------------------------------
	public static void writeInConsole(String msg, boolean flag) {
		if (flag){ logger.debug(msg);}
		 
	}

	//------------------------------------------------------------------------
	// Function to write message in log file as well as console [default]
	//------------------------------------------------------------------------
	public static void writeInConsole(String msg) {
		writeInConsole(msg,DEFAULT_DEBUG_FLAG);
	}

}
