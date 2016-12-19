/**************************************************************************
 * File     : BulkBizUserAssoScheduler.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.ThreadManager.BulkBizUserAssoThreadManager;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.impl.BulkBizUserAssoServiceImpl;
import com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces.BulkBizUserAssoService;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkBizUserAssoScheduler extends TimerTask {

	private static Logger logger = Logger
			.getLogger(BulkBizUserAssoScheduler.class.getName());

	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
			.getValueFromBundle(Constants.BULK_UPLOAD_RUN_INTERVAL,
					Constants.BULK_UPLOAD_RESOURCE_BUNDLE));

	/**
	 * This method schedules a periodic call to FileUploadProcessor
	 * 
	 * @param arguments
	 */
	public static void main(String[] arguments) {
		TimerTask bulkBizUserAssoScheduler = new BulkBizUserAssoScheduler();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(bulkBizUserAssoScheduler, new Date(),
				INTERVAL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.TimerTask#run()
	 */
	public void run() {

		logger
				.debug("\n======================================================================");
		logger.debug("Entering SMSPushReportEngineScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));

		ArrayList fileList = new ArrayList();
		BulkBizUserAssoDTO bulkBizUserAssoDTO = new BulkBizUserAssoDTO();

		try {
			BulkBizUserAssoService bulkBizUserAssoService = new BulkBizUserAssoServiceImpl();
			fileList = bulkBizUserAssoService.getScheduledFiles();

			if (fileList.isEmpty()) {
				logger.debug("No schedules found for Bulk User Association");
			} else {
				logger.debug(fileList.size() + " files need to be processed");
				if (BulkBizUserAssoThreadManager.getCurrentThreadCount() == 0) {

					logger
							.info("Schedular not running for Bulk User Association. Starting the Process...");
					Iterator itr = fileList.iterator();
					while (itr.hasNext()) {
						bulkBizUserAssoDTO = (BulkBizUserAssoDTO) itr.next();

						BulkBizUserAssoThreadManager bulkBizUserAssoThreadManager = new BulkBizUserAssoThreadManager(
								bulkBizUserAssoDTO);
						Thread fileUploadThread = new Thread(
								bulkBizUserAssoThreadManager);
						fileUploadThread.start();
					}
				} else {
					logger
							.info("Schedular already running for Bulk User Association. Exiting...");
				}
			}

		} catch (Exception ex) {

			logger.error("Exception occured :" + ex.getMessage(), ex);
		}
		logger.debug("Exiting SMSPushReportEngineScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");
	}

}