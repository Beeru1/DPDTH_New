/**************************************************************************
 * File     : MisReportScheduler.java
 * Author   : Aalok Sharma
 * Modified : Oct 20, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 20, 2008 	Aalok Sharma First Cut.
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

import com.ibm.virtualization.ussdactivationweb.reports.dao.*;
import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MisReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.reports.processor.MisReportProcessor;
import com.ibm.virtualization.ussdactivationweb.reports.service.FTAService;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class called by MisReportScheduler periodically
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public final class MisReportScheduler extends TimerTask {

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
		TimerTask misReportScheduler = new MisReportScheduler();
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
		MisReportDaoImpl misReportDaoImpl;
		ArrayList reportsScheduleList;
		ReportDTO reportDTO = null;
		try {
			misReportDaoImpl = new MisReportDaoImpl();
			reportsScheduleList = new ArrayList();

			// Call for Data feed process.
			MISDataFeedServiceDAO dataFeedServiceDAO = new MISDataFeedServiceDAO();
			FTAService ftaService = new FTAService();

			// Data Feed Process
			reportsScheduleList = misReportDaoImpl
					.getMISReportsSchedule(Utility.getValueFromBundle(
							Constants.DFD,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			if (reportsScheduleList.size() == 0) {
				logger.debug("No schedules found for daily reports");
			} else {
				Iterator reportsScheduleListItr = reportsScheduleList
						.iterator();
				while (reportsScheduleListItr.hasNext()) {
					reportDTO = (ReportDTO) reportsScheduleListItr.next();
					String currentDate = ftaService.getCurrentDate();
					dataFeedServiceDAO.generateReportDetailAndSummary(
							reportDTO, currentDate);
				}
			}

			// Daily report
			reportsScheduleList = misReportDaoImpl
					.getMISReportsSchedule(Utility.getValueFromBundle(
							Constants.DAILY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			if (reportsScheduleList.size() == 0) {
				logger.debug("No schedules found for daily reports");
			} else {
				Iterator reportsScheduleListItr = reportsScheduleList
						.iterator();
				while (reportsScheduleListItr.hasNext()) {
					reportDTO = (ReportDTO) reportsScheduleListItr.next();
					MisReportProcessor.generateMisReportsDaily(reportDTO);
				}
			}

			// Monthly report
			reportsScheduleList = misReportDaoImpl
					.getMISReportsSchedule(Utility.getValueFromBundle(
							Constants.MONTHLY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			if (reportsScheduleList.size() == 0) {
				logger.debug("No schedules found for monthly reports");
			} else {
				Iterator reportsScheduleListItr = reportsScheduleList
						.iterator();
				while (reportsScheduleListItr.hasNext()) {
					reportDTO = (ReportDTO) reportsScheduleListItr.next();
					MisReportProcessor.generateMisReportsMonthly(reportDTO);
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
