/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.scheduler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler.BulkBizUserAssoScheduler;
import com.ibm.virtualization.ussdactivationweb.reports.processor.BulkSubscriberDeletionProcessor;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public class BulkSubscriberDeletionScheduler extends TimerTask{
	
	private static Logger logger = Logger
		.getLogger(BulkSubscriberDeletionScheduler.class.getName());
	
	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
		.getValueFromBundle(Constants.SUBSCRIBER_RELEASE_RUN_INTERVAL,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
	
	/**
	* This method schedules a periodic call to FileUploadProcessor
	* 
	* @param arguments
	*/
	public static void main(String[] arguments) {
	TimerTask bulkSubscriberDeletionScheduler = new BulkSubscriberDeletionScheduler();
	Timer timer = new Timer();
	timer.scheduleAtFixedRate(bulkSubscriberDeletionScheduler, new Date(),
			INTERVAL);
	}
	
	public void run() {

		logger
				.debug("\n======================================================================");
		logger.debug("Entering SubscriberReleaseScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));

		try {
			
			BulkSubscriberDeletionProcessor.deletionReport(); 
			
		} catch (Exception ex) {

			logger.error("Exception occured :" + ex.getMessage(), ex);
		}
		logger.debug("Exiting SubscriberReleaseScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");
	}
}
