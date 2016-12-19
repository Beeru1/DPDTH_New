/**************************************************************************
 * File     : ReportEngineProcessor.java
 * Author   : Ashad
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.reports.processor;


import org.apache.log4j.Logger;

//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportDTO;
import com.ibm.virtualization.ussdactivationweb.reports.dao.ReportDaoImpl;

import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportService;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class responsible to process the pull report. 
 * 
 * @author Ashad
 * @version 1.0
 ******************************************************************************/

public class SMSPushReportEngineProcessor {
	private static Logger logger = Logger.getLogger(SMSPushReportEngineProcessor.class
			.getName());
	/**
	 * This method Fetches all engine-schedules to be run and calls the report engine process 
	 * 
	 * @param argsReportEngineProcessor
	 */
	ReportDaoImpl reportDaoImpl =null;
	
	public SMSPushReportEngineProcessor(){
	reportDaoImpl = new ReportDaoImpl();
		
	}
	
	
	
/*	public void processReport(SMSReportDTO smsReportDTO)  {
		logger.debug(new StringBuffer(100).append(
				"Entering processReport() for... Circle Code=").append(
						smsReportDTO.getCircleCode()).append(" report id = ")
						.append(smsReportDTO.getReportId()).toString());
		int reportId = -1;
		try {
			
			if(smsReportDTO.getReportId() !=null){
				reportId = Integer.parseInt(smsReportDTO.getReportId());
			}

			String circleCode = smsReportDTO.getCircleCode();
			String reportDate = Utility.getTimestampAsString(smsReportDTO
					.getReportTime(), "dd/MM/yyyy");
			String reportTypeflag = smsReportDTO.getReportType();
			
			int reporTypeValue = Constants.NO_PERMISSION;
			if("D".equalsIgnoreCase(reportTypeflag)){
				reporTypeValue = Constants.DAILY_REPORT;
			} else if("M".equalsIgnoreCase(reportTypeflag)){
				reporTypeValue = Constants.MONTHLY_REPORT;
			}
			
			*//**  At the start of report execution SET RE_RUN_FLAG='N' **//*
			reportDaoImpl.updateParametersForReportSchedule(reportId,"S");
			
            *//**  Generate and Send report **//*
			new SMSReportService().pushSMSReport(reportId,circleCode,reportDate,reporTypeValue);
			
			
		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage(), e);
		} finally {
			try{
	      
		  		*//** SET RE_RUN_FLAG='Y', FORCE_START='N', FORCE_START_DATE = NULL For respective reportId **//*
				reportDaoImpl.updateParametersForReportSchedule(reportId,"E");
			
			*//** Call Procedure to update next schedule date**//*
			reportDaoImpl.updateMISReportScheduleTimeProc(reportId);
			
				
			} catch (Exception e) {
				logger.error("Exception occured in updateMISReportScheduleTimeProc() method " + e.getMessage(), e);
			}
			
	}
	}*/
	/*
	public void processNewReport(SMSReportDTO smsReportDTO)  {
		logger.debug(new StringBuffer(100).append(
				"Entering processReport() for... Circle Code=").append(
						smsReportDTO.getCircleCode()).append(" report id = ")
						.append(smsReportDTO.getReportId()).toString());
		try {
			
			String reportId = smsReportDTO.getReportId();
			String circleCode = smsReportDTO.getCircleCode();
			String reportCode = smsReportDTO.getReportCode();
			String smskeyWord = smsReportDTO.getSmsKeyword();
			
			/**  At the start of report execution SET RE_RUN_FLAG='N' * 
			
			//reportDaoImpl.updateParametersForReportSchedule(reportId,"S");
			
            /**  Generate and Send report * 
			new SMSReportService().pushNewSMSReport(reportId,circleCode ,reportCode,smskeyWord);
			
			
		} catch (Exception e) {
			logger.error("Exception occured : " + e.getMessage(), e);
		} finally {
			try{
	      
		  		/** SET RE_RUN_FLAG='Y', FORCE_START='N', FORCE_START_DATE = NULL For respective reportId * 
			//	reportDaoImpl.updateParametersForReportSchedule(reportId,"E");
			
			/** Call Procedure to update next schedule date* 
			//reportDaoImpl.updateMISReportScheduleTimeProc(reportId);
			
				
			} catch (Exception e) {
				logger.error("Exception occured in updateMISReportScheduleTimeProc() method " + e.getMessage(), e);
			}
			
	}
	}
	
	*/
	
	
}
