/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Resources Class to get resource bundle for web Application,Recharge core
 * 
 */
public class ResourceReader {
	/* Logger for this class. */
	protected static Logger logger = Logger.getLogger(ResourceReader.class
			.getName());

	private static ResourceBundle webResourceBundle = null;

	private static ResourceBundle coreResourceBundle = null;

	private static ResourceBundle postPaidResourceBundle = null;
	 
	private static ResourceBundle webserviceResourceBundle = null;
	
	//DPDTH-Production
	
//	private static String path="/home/dpportal/";
	
	//private static String path="C:\\DPDTH_WS\\DPDTH\\src\\com\\ibm\\virtualization\\recharge\\resources\\";
	               
	private static String path="C:\\";  
	
	//############  Test env. 10.5.176.56 #############
//	private static String path="/home/wasadmin/";
//	private static String path="C:\\";
	
	              
	// private static Resources instance = null;
	private static Properties applicationResources = new Properties();
	
	private static FileInputStream fis  = null;
	private static String bundleName;

//	 Last access time for properties file
	private static Date lastAccessedDate = new Date();

	// Referesh Interval in minutes
	private static final int REFERESH_INTERVAL = 1;

	// To refresh the bundle 
	private static boolean refreshFromBundle = true;

	// constructor
	private ResourceReader() {

	}
	
	static
	{
		try
		{
			logger.info("************static block here******************");
		applicationResources.clear();
//		setBundleName("/home/appsmf/config/VirtualizationResources");
		setBundleName(path+"RechargeCore");
		fis = new FileInputStream(bundleName);
		applicationResources.load(fis);
		}
		catch(Exception ex)
		{
			//System.out.println("*******************&&&&&&&&");
		}
	}

	/*
	 * public static Resources getInstance(){ if(instance==null){ instance=new
	 * Resources(); } return instance; }
	 */
	/**
	 * This method returns the resource bundle for web Application and property.
	 * 
	 * @param propertyKey
	 *            TODO
	 * 
	 * @param
	 * @return Resourcebundle
	 * @throws VirtualizationServiceException
	 */
	public static String getWebResourceBundleValue(String propertyKey)
			throws VirtualizationServiceException {
		String value = "";
		try {
			try {
				if (webResourceBundle == null) {
					webResourceBundle = ResourceBundle
							.getBundle(Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
				}
			} catch (MissingResourceException missingResourceExp) {
				logger
						.fatal("Error while loading the Web Application Resource Bundle."
								+ Constants.WEB_APPLICATION_RESOURCE_BUNDLE
								+ " property key :" + propertyKey);
				throw new VirtualizationServiceException(
						"errors.utility.resources_notfound");

			}
			value = webResourceBundle.getString(propertyKey);
		} catch (MissingResourceException missingResourceExp) {
			logger.fatal("Error while finding key :" + propertyKey
					+ " from bundle "
					+ Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
			throw new VirtualizationServiceException(
					"errors.utility.resources_notfound");
		}
		return value;
	}
	
	public static void setBundleName(String string) {
		bundleName = string.replace('.', '/') + ".properties";
		try {
			fis = new FileInputStream(bundleName);
			applicationResources.load(fis);
		} catch (IOException e) {
			logger.error("Error in configuring properties file name : "+ e.getMessage());
		}
	}

	
	/**
	 * This method returns the resource bundle for Recharge core.
	 * 
	 * @param propertyKey
	 *            TODO
	 * 
	 * @param
	 * @return ResourceBundle
	 * @throws VirtualizationServiceException
	 */
	public static String getCoreResourceBundleValue(String propertyKey)
			throws VirtualizationServiceException {
		String value = "";
		try {

			try {
				/*if (coreResourceBundle == null) {
					coreResourceBundle = ResourceBundle
							.getBundle(Constants.APLLICATION_RESOURCE_BUNDLE);
				}
				value = coreResourceBundle.getString(propertyKey);*/
				Date currentDate = new Date();
				if ((currentDate.getTime() - lastAccessedDate.getTime()) > 1 * 1000 * 60 * REFERESH_INTERVAL && refreshFromBundle) {

				//System.out.println("1111111111111-----------------Loading the loader.........................." + REFERESH_INTERVAL);
				applicationResources.clear();
//				setBundleName("/home/appsmf/config/VirtualizationResources");
				setBundleName(path+"RechargeCore");
				fis = new FileInputStream(bundleName);
				applicationResources.load(fis);
				lastAccessedDate = currentDate;
			}

				//System.out.println("222222222222--------------Loading the loader............hahahahha..............");
				
				
			} catch (MissingResourceException missingResourceExp) {
				logger
						.fatal("Error while loading the Web Application Resource Bundle."
								+ Constants.APLLICATION_RESOURCE_BUNDLE
								+ " property key :" + propertyKey);
				throw new VirtualizationServiceException(
						"errors.utility.resources_notfound");
			}
		} catch (Exception missingResourceExp) {
			logger.fatal("Error while finding key :" + propertyKey
					+ " from bundle "
					+ Constants.APLLICATION_RESOURCE_BUNDLE);
			throw new VirtualizationServiceException(
					"errors.utility.resources_notfound");

		}
		return applicationResources.getProperty(propertyKey);
	}

	/**
	 * This method returns the value of property of the given resource bundle
	 * and property.
	 * 
	 * @param propertyKey
	 * @param resourceBundleName
	 * @return String
	 * @throws VirtualizationServiceException
	 */

	public static String getValueFromBundle(String propertyKey,
			String resourceBundleName) {
		String value = null;
		ResourceBundle resourceBundle = null;
		try {
			try {
				resourceBundle = ResourceBundle.getBundle(resourceBundleName);

			} catch (MissingResourceException missingResourceExp) {
				logger
						.fatal("Error while loading the Web Application Resource Bundle."
								+ resourceBundleName
								+ " property key :"
								+ propertyKey);

			}
			value = resourceBundle.getString(propertyKey);
		} catch (MissingResourceException missingResourceExp) {
			logger.fatal("Error while finding key :" + propertyKey
					+ " from bundle " + resourceBundleName);

		}
		return value;
	}
	
	
	/**
	 * This method returns the resource bundle for postpaid .
	 * 
	 * @param propertyKey
	 *            TODO
	 * 
	 * @param
	 * @return ResourceBundle
	 * @throws VirtualizationServiceException
	 */
	public static String getpostPaidResourceBundleValue(String propertyKey)
			throws VirtualizationServiceException {
		String value = "";
		try {

			try {
				if (postPaidResourceBundle == null) {
					postPaidResourceBundle = ResourceBundle
							.getBundle(Constants.POSTPAID_APPLICATION_RESOURCE_BUNDLE);
				}
				value = postPaidResourceBundle.getString(propertyKey);
			} catch (MissingResourceException missingResourceExp) {
				logger
						.fatal("Error while loading the postpaid resource bundle."
								+ Constants.POSTPAID_APPLICATION_RESOURCE_BUNDLE
								+ " property key :" + propertyKey);
				throw new VirtualizationServiceException(
						"errors.utility.resources_notfound");
			}
		} catch (MissingResourceException missingResourceExp) {
			logger.fatal("Error while finding key :" + propertyKey
					+ " from bundle "
					+ Constants.POSTPAID_APPLICATION_RESOURCE_BUNDLE);
			throw new VirtualizationServiceException(
					"errors.utility.resources_notfound");

		}
		return value;
	}
	public static String getPwdChangeParams(String propertyKey)
	throws VirtualizationServiceException {
		String value = "";
		try {
			try {
				if (webserviceResourceBundle == null) {
					webserviceResourceBundle = ResourceBundle
							.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
				}
				value = webserviceResourceBundle.getString(propertyKey);
			} catch (MissingResourceException missingResourceExp) {
				logger
						.fatal("Error while loading the Web Application Resource Bundle."
								+ Constants.WEBSERVICE_RESOURCE_BUNDLE
								+ " property key :" + propertyKey);
				throw new VirtualizationServiceException(
						"errors.utility.resources_notfound");
			}
		} catch (MissingResourceException missingResourceExp) {
			logger.fatal("Error while finding key :" + propertyKey
					+ " from bundle "
					+ Constants.WEBSERVICE_RESOURCE_BUNDLE);
			throw new VirtualizationServiceException(
					"errors.utility.resources_notfound");
		
		}
		return value;
		}

	
	
	
	public static void main(String[] args)
	{
		try
		{
//			CommonPropertyReader.setBundleName(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			
//			String strValue = CommonPropertyReader.getString(Constants.USER_DEFAULT_PASSWORD);
			
			String strValue = ResourceReader.getValueFromBundle(Constants.USER_DEFAULT_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE);
			//System.out.println("strValue  ::  "+strValue);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
