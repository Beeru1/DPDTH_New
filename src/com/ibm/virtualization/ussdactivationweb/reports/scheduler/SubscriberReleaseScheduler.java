/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager.BulkBizUserAssoThreadManager;
import com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler.BulkBizUserAssoScheduler;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserAssoServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserAssoService;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.reports.processor.SubscriberReleaseProcessor;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseScheduler extends TimerTask{
	
	private static Logger logger = Logger
		.getLogger(SubscriberReleaseScheduler.class.getName());
	
	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
		.getValueFromBundle(Constants.SUBSCRIBER_RELEASE_RUN_INTERVAL,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
	
	/**
	* This method schedules a periodic call to FileUploadProcessor
	* 
	* @param arguments
	*/
	public static void main(String[] arguments) {
	TimerTask subscriberReleaseScheduler = new SubscriberReleaseScheduler();
	Timer timer = new Timer();
	timer.scheduleAtFixedRate(subscriberReleaseScheduler, new Date(),
			INTERVAL);
	}
	
	public void run() {

		logger
				.debug("\n======================================================================");
		logger.debug("Entering SubscriberReleaseScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));

		/*try {
			SubscriberReleaseProcessor.release(); 
			
		} catch (Exception ex) {

			logger.error("Exception occured :" + ex.getMessage(), ex);
		}*/
		logger.debug("Exiting SubscriberReleaseScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");
	}
}
