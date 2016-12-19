/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager.BulkBizUserCreationThreadManager;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserCreationServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserCreationService;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Nitesh
 *
 */
//public class BulkBizUserCreationScheduler extends TimerTask {
public class BulkBizUserCreationScheduler {

	private static Logger logger = Logger
			.getLogger(BulkBizUserCreationScheduler.class.getName());

	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
			.getValueFromBundle(Constants.BULK_UPLOAD_BIZ_USER_CREATION_RUN_INTERVAL,
					Constants.BULK_UPLOAD_RESOURCE_BUNDLE));

	/**
	 * 
	 * /** This method schedules a periodic call to FileUploadProcessor
	 * 
	 * @param arguments
	 */
	/*
	public static void main(String[] arguments) {
		TimerTask bulkBizUserCreationScheduler = new BulkBizUserCreationScheduler();
		Timer timer = new Timer();
		timer
				.scheduleAtFixedRate(bulkBizUserCreationScheduler, new Date(),
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
		logger.debug("Entering BulkBizUserCreationScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		ArrayList fileSchedule = new ArrayList();
		BulkBizUserCreationDTO bulkBizUserCreationDTO = new BulkBizUserCreationDTO();
		try {
			BulkBizUserCreationService bulkBizUserCreationService = new BulkBizUserCreationServiceImpl();
			fileSchedule = bulkBizUserCreationService.getScheduledFilesforBulkUserCreation();
			if (fileSchedule == null || fileSchedule.size() == 0) {
				logger.debug("No schedules found");
			} else {
				logger.debug(fileSchedule.size()
						+ " files need to be processed");
				if (BulkBizUserCreationThreadManager.getCurrentThreadCount() == 0) {
					logger
							.info("Scheduler not running for Bulk Business User Creation Upload. Starting the Process...");
					Iterator itr = fileSchedule.iterator();
					while (itr.hasNext()) {
						bulkBizUserCreationDTO = (BulkBizUserCreationDTO) itr.next();
						BulkBizUserCreationThreadManager bulkBizUserCreationThreadManager = new BulkBizUserCreationThreadManager(
								bulkBizUserCreationDTO);
						Thread fileUploadThread = new Thread(
								bulkBizUserCreationThreadManager);
						fileUploadThread.start();

					}
				} else {
					logger
							.info("Scheduler already running for Bulk Business User Creation Upload. Exiting...");
				}

			}
		} catch (Exception ex) {
			logger.error("Exception occured :" + ex.getMessage(), ex);
		}
		logger.debug("Exiting BulkBizUserCreationScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");

	}
*/
}