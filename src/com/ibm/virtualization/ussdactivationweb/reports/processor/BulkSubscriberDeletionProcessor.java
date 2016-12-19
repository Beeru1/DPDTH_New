/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.processor;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.reports.dao.BulkSubscriberDeletionDAO;

/**
 * @author a_gupta
 *
 */
public class BulkSubscriberDeletionProcessor {
	private static Logger logger = Logger.getLogger(BulkSubscriberDeletionProcessor.class
			.getName());
	
	public static void deletionReport() {
		logger.debug("Entering in SubscriberReleaseProcessor : release() method ");
		BulkSubscriberDeletionDAO bulkSubscriberDeletionDAO=null;
		try {
			bulkSubscriberDeletionDAO = new BulkSubscriberDeletionDAO();
			bulkSubscriberDeletionDAO.deletionReport();
		}catch (Exception e) {
			logger
			.error("Exception Occured in SubscriberReleaseProcessor : release() method : "
					+ e);
		} 
	}
}
