/**************************************************************************
 * File     : ReportEngineScheduler.java
 * Author   : Ashad
 * Created  : Nov 3, 2008
 * Modified : Nov 3, 2008
 * Version  : 0.1
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

//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportDTO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.SMSReportDAO;
import com.ibm.virtualization.ussdactivationweb.reports.processor.SMSPushReportEngineProcessor;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportConfigInitializer;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class calls SMSPushReportEngineProcessor periodically
 * 
 * @author Ashad
 * @version 1.0
 ******************************************************************************/
//public final class SMSPushReportEngineScheduler extends TimerTask {
public final class SMSPushReportEngineScheduler  {

	private static Logger logger = Logger.getLogger(SMSPushReportEngineScheduler.class
			.getName());
	
	
	
	//private final static long INTERVAL = 1000 * 60 *(Integer.parseInt(SMSReportConfigInitializer.SMS_REPORT_RUN_INTERVAL));

	/**
	 * This method schedules a periodic call to ReportEngineProcessor
	 * 
	 * @param arguments
	 */
	/*
	public static void main(String[] arguments) {
		TimerTask reportEngScheduler = new SMSPushReportEngineScheduler();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(reportEngScheduler, new Date(), INTERVAL);
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
		logger.debug("Entering SMSPushReportEngineScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
			
		    ArrayList reportsSchedule = new ArrayList();
			SMSReportDTO smsReportDTO = new SMSReportDTO();
			SMSPushReportEngineProcessor smsRptEngineProcessor = new SMSPushReportEngineProcessor();
			try {

				SMSReportDAO smsReportDAO = new SMSReportDAO();
				//reportsSchedule = smsReportDAO.getReportsSchedule();
				reportsSchedule = smsReportDAO.getNewReportsSchedule();

				if (reportsSchedule.size() == 0) {
					logger.debug("No schedules found");
				} else {
					
					Iterator itr = reportsSchedule.iterator();
					while (itr.hasNext()) {
						smsReportDTO = (SMSReportDTO) itr.next();
						//smsRptEngineProcessor.processReport(smsReportDTO);
						smsRptEngineProcessor.processNewReport(smsReportDTO);
					}
				}
			} catch (Exception ex) {
				logger.error("Exception occured :" + ex.getMessage(), ex);
				} 
		logger.debug("Exiting SMSPushReportEngineScheduler's run() at "
				+ new java.util.Date(System.currentTimeMillis()));
		logger
				.debug("======================================================================\n");
	}*/
}

