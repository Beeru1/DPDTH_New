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

import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * CacheRefreshTask class basically used for refereshing cache by calling
 * getGroupAndRoleInfo method of CacheImpl class
 * 
 * @author Prakash Jashnani
 * 
 */
public class CacheRefreshTask extends TimerTask {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(CacheRefreshTask.class
			.getName());

	/**
	 * Call getGroupAndRoleInfomethod to refresh cache
	 */
	public void run() {
		logger.info("Cache Refresh Event occured, Invalidating Cache ");
		try {
			// Refreshing Cache.
			CacheImpl c = (CacheImpl) CacheFactory.getCacheImpl();
			
			c.populateCache();
		} catch (Throwable e) {
			logger.fatal("Error ocurred while populating the cache" + e.getMessage(), e);
		}
		
		logger.info("Cache refreshed succesfully after cache refresh event");
	}
}
