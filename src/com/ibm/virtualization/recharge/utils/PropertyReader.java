/*
 * Created on Jun 29, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ibm.virtualization.recharge.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Reads the proeprties file. The properties file is refreshed every 20 minutes
 * that is the file is re read every 20 minutes. <br>
 * The property values can be accessed using
 * 
 * @link #getString(String). Before this the bundle name of the property file
 *       must be set. <br>
 *       This can be set using
 * @link #setBundleName(String). To disbale property file refreshing use
 * @link #setRefreshFromBundle(boolean)
 * 
 * @author Puneet
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PropertyReader {
	/**
	 * The bundle name
	 */
	// Initial nulled out the following so that default config file isnt picked
	// up. Done on 3rd Aug 2007 by Puneet Lakhina
	private static String bundleName;//$NON-NLS-1$

	private static final Logger logger = Logger.getLogger(PropertyReader.class);

	private static Properties applicationResources = new Properties();

	private static Date lastAccessedDate = new Date();

	/**
	 * Referesh Interval in minutes
	 */
	private static final int REFERESH_INTERVAL = 20;

	private static boolean refreshFromBundle = true;

	private PropertyReader() {
	}

	public static synchronized String getString(String key) {
		// TODO Auto-generated method stub
		Date currentDate = new Date();
		if ((currentDate.getTime() - lastAccessedDate.getTime()) > 1 * 1000 * 60 * REFERESH_INTERVAL
				&& refreshFromBundle) {
			logger.info("Refereshing bundle");
			try {
				applicationResources.clear();
				applicationResources.load(PropertyReader.class.getClassLoader()
						.getResourceAsStream(bundleName));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			lastAccessedDate = currentDate;
		}

		String returnVal = applicationResources.getProperty(key);
		if (returnVal == null) {
			logger.info("No Value found for key:" + key + " Returning null");
		}
		return returnVal;

	}

	/**
	 * @return
	 */
	public static String getBundleName() {
		return bundleName;
	}

	/**
	 * @param string
	 */
	public static void setBundleName(String string) {
		bundleName = string.replace('.', '/') + ".properties";
		logger.info(bundleName);
		try {
			applicationResources.clear();

			applicationResources.load(PropertyReader.class.getClassLoader()
					.getResourceAsStream(bundleName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the refreshFromBundle.
	 */
	public static boolean isRefreshFromBundle() {
		return refreshFromBundle;
	}

	/**
	 * @param refreshFromBundle
	 *            The refreshFromBundle to set.
	 */
	public static void setRefreshFromBundle(boolean refreshFromBundle) {
		PropertyReader.refreshFromBundle = refreshFromBundle;
	}

	/**
	 * Returns the Count of the Keys that start with a particular String
	 * 
	 * @param strtWith
	 * @return
	 */
	public static int getKeyCnt(String strtWith) {

		int cnt = 0;
		Date currentDate = new Date();

		Set keySet = applicationResources.keySet();
		Iterator it = keySet.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();

			if (key.startsWith(strtWith)) {
				cnt++;
			}
		}

		return cnt;
	}
}