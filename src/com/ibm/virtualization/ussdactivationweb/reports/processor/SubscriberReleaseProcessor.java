/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.processor;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.reports.dao.SubscriberReleaseDAO;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseProcessor {
	private static Logger logger = Logger.getLogger(SubscriberReleaseProcessor.class
			.getName());
	
	public static void releaseSubscriber() {
		logger.debug("Entering in SubscriberReleaseProcessor : release() method ");
		SubscriberReleaseDAO subscriberReleaseDAO=null;
		try {
			subscriberReleaseDAO = new SubscriberReleaseDAO();
			subscriberReleaseDAO.releaseSubscriber();
		}catch (Exception e) {
			logger
			.error("Exception Occured in SubscriberReleaseProcessor : release() method : "
					+ e);
		} 
	}
}
