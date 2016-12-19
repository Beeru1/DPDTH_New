/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.cache;

import org.apache.log4j.Logger;

/**
 * The CacheFactory class, returns the instance of class implementing cache
 * services
 * 
 * @author Rohit Dhall
 * @date 07-Sep-2007
 * 
 */
public class CacheFactory {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(CacheFactory.class
			.getName());

	/* Instance of Cache for this class. */
	private static Cache cache;

	/**
	 * private constructor
	 */
	private CacheFactory() {
	}

	/**
	 * This method returns an instance of CacheImpl class
	 * 
	 * @return Cache
	 */
	public static Cache getCacheImpl() {
		logger.info("******Cache is :"+cache);
		if (cache == null) {
			long initialMemory = Runtime.getRuntime().freeMemory();
			synchronized(Cache.class){
			if (cache == null) {
				//logger.info("*******New Instance");
				cache = new CacheImpl();
				//logger.info("Cache Service initialized for first time usage ");
			    logger.info("Approx memory used by cache is "
					+ (initialMemory - Runtime.getRuntime().freeMemory())
					/ 1000 + " KiloBytes");

			} else {//logger.info("*******No Instance");
				
			}
			}
		}else {//logger.info("*******No Instance buddy");
			}
		
		return cache;
	}
}
