/**************************************************************************
 * File     : BulkSubscriberScheduler.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler;

/**
 * @author a_gupta
 * 
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager.BulkSubscriberThreadManager;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkSubscriberServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkSubscriberService;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

//public class BulkSubscriberScheduler extends TimerTask {
public class BulkSubscriberScheduler {

	private static Logger logger = Logger
			.getLogger(BulkSubscriberScheduler.class.getName());

	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
			.getValueFromBundle(Constants.BULK_UPLOAD_RUN_INTERVAL,
					Constants.BULK_UPLOAD_RESOURCE_BUNDLE));

	/**
	 * 
	 * /** This method schedules a periodic call to FileUploadProcessor
	 * 
	 * @param arguments
	 */
	/*
	public static void main(String[] arguments) {
		TimerTask bulkSubscriberScheduler = new BulkSubscriberScheduler();
		Timer timer = new Timer();
		timer
				.scheduleAtFixedRate(bulkSubscriberScheduler, new Date(),
						INTERVAL);
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	/*
	public void run() {
		logger
				.debug("\n======================================================================");
		logger.debug("Entering BulkSubscriberScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		ArrayList fileSchedule = new ArrayList();
		BulkSubscriberDTO bulkSubscriberDTO = new BulkSubscriberDTO();
		try {
			BulkSubscriberService bulkSubscriberService = new BulkSubscriberServiceImpl();
			fileSchedule = bulkSubscriberService
					.getScheduledFilesforSubscriber();
			if (fileSchedule.size() == 0) {
				logger.debug("No schedules found");
			} else {
				logger.debug(fileSchedule.size()
						+ " files need to be processed");
				if (BulkSubscriberThreadManager.getCurrentThreadCount() == 0) {
					logger
							.info("Scheduler not running for Bulk Subscriber Upload. Starting the Process...");
					Iterator itr = fileSchedule.iterator();
					while (itr.hasNext()) {
						bulkSubscriberDTO = (BulkSubscriberDTO) itr.next();
						BulkSubscriberThreadManager bulkSubscriberThreadManager = new BulkSubscriberThreadManager(
								bulkSubscriberDTO);
						Thread fileUploadThread = new Thread(
								bulkSubscriberThreadManager);
						fileUploadThread.start();

					}
				} else {
					logger
							.info("Scheduler already running for Bulk Subscriber Upload. Exiting...");
				}

			}
		} catch (Exception ex) {
			logger.error("Exception occured :" + ex.getMessage(), ex);
		}
		logger.debug("Exiting BulkSubscriberScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");

	}
*/
}