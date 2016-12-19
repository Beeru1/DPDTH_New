/**************************************************************************
 * File     : MisPendingReportShedular.java
 * Author   : Ashad
 * Modified : Feb 12, 2009
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Feb 12, 2009	Ashad First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.reports.scheduler;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MISDataFeedServiceDAO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MisReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.reports.processor.MisReportProcessor;
import com.ibm.virtualization.ussdactivationweb.reports.service.FTAService;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class called by MisReportScheduler periodically
 * 
 * @author Ashad
 * @version 1.0
 ******************************************************************************/

public class MisPendingReportScheduler extends TimerTask  {

	private static Logger logger = Logger.getLogger(MisReportScheduler.class
			.getName());

	private final static long INTERVAL = 1000 * 60 * Integer.parseInt(Utility
			.getValueFromBundle(Constants.REPORT_RUN_INTERVAL,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

	/**
	 * This method schedules a periodic call to MisReportProcessor
	 * 
	 * @param arguments
	 */
	public static void main(String[] arguments) {
		TimerTask misReportScheduler = new MisPendingReportScheduler();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(misReportScheduler, new Date(), INTERVAL);
	}

	/*
	 * This method calls the Misreport processor on the basic of schedule.
	 * 
	 * @see java.util.TimerTask#run()
	 */
	public void run() {
		logger
				.debug("\n======================================================================");
		logger.debug("Entering MisReportScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		ArrayList reportsScheduleList;
		ReportDTO reportDTO = null;
		try {
			reportsScheduleList = new ArrayList();
			
			/*****  Daily Pending report  *******/
			
			reportsScheduleList = new MisReportDaoImpl().getMISPendingRptSchedule(Utility.getValueFromBundle(
							Constants.DAILY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			if (reportsScheduleList.size() == 0) {
				
				logger.debug("No schedules found for daily reports");
			} else {
				
				/*** Data feeding for Pending report ***/ 
				
				//String currentDate = new FTAService().getCurrentDate();
				// new MISDataFeedServiceDAO().dataFeedForPendingReport(currentDate);
				Iterator reportsScheduleListItr = reportsScheduleList
						.iterator();
				while (reportsScheduleListItr.hasNext()) {
					reportDTO = (ReportDTO) reportsScheduleListItr.next();
					MisReportProcessor.generatePendingMisReportsDaily(reportDTO);
				}
			}

		} catch (Exception e) {
			logger
					.error("Exception Occured in generateMisReportsMonthly() method : "
							+ e);
		}
		logger.debug("Exiting MisReportScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");
	}

	

}
