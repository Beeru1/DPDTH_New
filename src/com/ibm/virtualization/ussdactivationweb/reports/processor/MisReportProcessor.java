/**************************************************************************
 * File     : MisReportProcessor.java
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
package com.ibm.virtualization.ussdactivationweb.reports.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.dao.MisReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.reports.dao.ReportDaoImpl;
import com.ibm.virtualization.ussdactivationweb.utils.CSVWriter;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class calls MisReportProcessor periodically
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public class MisReportProcessor {
	private static Logger logger = Logger.getLogger(MisReportProcessor.class
			.getName());

	/**
	 * This method take report date as input parameter and generate the daily
	 * reports for corresponding date
	 * 
	 * @param reportDTO
	 *            ReportDTO contains all the information about te report to be
	 *            generated.
	 */
	public static void generateMisReportsDaily(ReportDTO reportDTO) {

		logger.debug("Entering in generateMisReportsDaily() method ");
		int reportId = reportDTO.getReportId();
		String circleCode = reportDTO.getCircleCode();
		String reportDate = Utility.getTimestampAsString(reportDTO
				.getReportDateTime(), "dd/MMM/yyyy");
		MisReportDaoImpl misReportDaoImpl = null;
		ReportDaoImpl reportDaoImpl = null;
		String fileStartName = "MISReport-" + circleCode + "-"
				+ reportDTO.getReportName() + "-";
		String fileName = getFullFileName(fileStartName, reportDate);
		String filePath = Utility.getValueFromBundle(
				Constants.KEY_MIS_REPORT_FILE_STORAGE_PATH,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
		try {
			misReportDaoImpl = new MisReportDaoImpl();
			reportDaoImpl = new ReportDaoImpl();
			// update status on start report
			// re-run flag = N
			reportDaoImpl.updateParametersForReportSchedule(reportId, Constants.DATA_FEED_PROCESS_START);

			if (reportId == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_DAILY_ZONE_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer.parseInt(Utility.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_DAILY_CITY_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) { // Loc
																			// wise
				createFile(filePath + fileName, misReportDaoImpl
						.getMisReportByLocActvCountSingleDay(reportId,
								reportDate, circleCode));
			} else if (reportId == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_DAILY_DEALER_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_DAILY_DISTRIBUTER_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer.parseInt(Utility.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_DAILY_TM_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) { // Biz
																			// user
																			// wise
				createFile(filePath + fileName, misReportDaoImpl
						.getMisReportByBizUserActvCountSingleDay(reportId,
								reportDate, circleCode));
			} else if (reportId == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_DAILY_DETAILED_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) { // Detailed
				createFile(filePath + fileName, misReportDaoImpl
						.getMisDtlReportActvCountSingleDay(reportId,
								reportDate, circleCode));
			} else {
				logger.debug("There is no scheduled report. ");
				}
				
			misReportDaoImpl.insertMISReportFileInfo(fileName, filePath,
					circleCode, reportId, reportDate);
			logger.debug("Exiting from generateMisReportsDaily() method ");
		} catch (Exception e) {
			logger
					.error("Exception Occured in generateMisReportsDaily() method : "
							+ e);
		} finally {
			endReportProcess(reportDTO.getForceStart(), reportId, reportDaoImpl);
		}
	}

	
	/**
	 * This method take report date as input parameter and generate the daily
	 * reports for corresponding date
	 * 
	 * @param reportDTO
	 *            ReportDTO contains all the information about te report to be
	 *            generated.
	 */
	public static void generatePendingMisReportsDaily(ReportDTO reportDTO) {

		logger.debug("Entering in generatePendingMisReportsDaily() method ");
		int reportId = reportDTO.getReportId();
		String circleCode = reportDTO.getCircleCode();
		String reportDate = Utility.getTimestampAsString(reportDTO
				.getReportDateTime(), "dd/MMM/yyyy");
		MisReportDaoImpl misReportDaoImpl = null;
		ReportDaoImpl reportDaoImpl = null;
		String fileStartName = "MISReport-" + circleCode + "-"
				+ reportDTO.getReportName() + "-";
		String fileName = getFullFileName(fileStartName, reportDate);
		String filePath = Utility.getValueFromBundle(
				Constants.KEY_MIS_REPORT_FILE_STORAGE_PATH,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
		try {
			misReportDaoImpl = new MisReportDaoImpl();
			reportDaoImpl = new ReportDaoImpl();
			// update status on start report
			// re-run flag = N
			reportDaoImpl.updateParametersForReportSchedule(reportId, Constants.DATA_FEED_PROCESS_START);

			 if (reportId == Integer.parseInt(Utility.getValueFromBundle(
						Constants.MIS_PENDING_REPORT_GENERATION_DAILY_WISE,
						Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) { // Detailed
					createFile(filePath + fileName, misReportDaoImpl
							.getMisPendingReportActCntSingleDay(reportId,
									reportDate, circleCode));
				}
				
			misReportDaoImpl.insertMISReportFileInfo(fileName, filePath,
					circleCode, reportId, reportDate);
			logger.debug("Exiting from generatePendingMisReportsDaily() method ");
		} catch (Exception e) {
			logger
					.error("Exception Occured in generatePendingMisReportsDaily() method : "
							+ e);
		} finally {
			endReportProcess(reportDTO.getForceStart(), reportId, reportDaoImpl);
		}
	}

	
	/**
	 * This method take report date as input parameter and generate the monthly
	 * reports for corresponding date
	 * 
	 * @param reportDTO
	 *            ReportDTO contains all the information about te report to be
	 *            generated.
	 * 
	 */
	public static void generateMisReportsMonthly(ReportDTO reportDTO) {
		logger.debug("Entering in generateMisReportsMonthly() method ");
		int reportId = reportDTO.getReportId();
		int reportMonth = Integer.parseInt(Utility.getTimestampAsString(
				reportDTO.getReportDateTime(), "MM"));
		int reportYear = Integer.parseInt(Utility.getTimestampAsString(
				reportDTO.getReportDateTime(), "yyyy"));
		String circleCode = reportDTO.getCircleCode();

		MisReportDaoImpl misReportDaoImpl = null;
		ReportDaoImpl reportDaoImpl = null;
		String fileStartName = "MISReport-" + circleCode + "-"
				+ reportDTO.getReportName() + "-";
		;
		String fileName = getFullFileName(fileStartName,
				Utility.getTimestampAsString(reportDTO.getReportDateTime(),
						"MMM-yyyy"));
		String filePath = Utility.getValueFromBundle(
				Constants.KEY_MIS_REPORT_FILE_STORAGE_PATH,
				Constants.WEB_APPLICATION_RESOURCE_BUNDLE);

		try {

			misReportDaoImpl = new MisReportDaoImpl();
			reportDaoImpl = new ReportDaoImpl();
			// update status on start report
			// re-run flag = N
			reportDaoImpl.updateParametersForReportSchedule(reportId, Constants.DATA_FEED_PROCESS_START);
			if (reportId == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_MONTHLY_ZONE_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer.parseInt(Utility.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_CITY_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				createFile(filePath + fileName, misReportDaoImpl
						.getMisReportByLocActvCountByMonth(reportId,
								reportMonth, reportYear, circleCode));
			} else if (reportId == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_MONTHLY_DEALER_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_DISTRIBUTER_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
					|| reportId == Integer.parseInt(Utility.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_TM_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				createFile(filePath + fileName, misReportDaoImpl
						.getMisReportByBizUserActvCountByMonth(reportId,
								reportMonth, reportYear, circleCode));
			}
			// Generated file details to be added into the database.
			misReportDaoImpl.insertMISReportFileInfo(fileName, filePath,
					circleCode, reportId, Utility.getTimestampAsString(
							reportDTO.getReportDateTime(), "dd/MMM/yyyy"));
			logger.debug("Exiting from generateMisReportsMonthly() method ");
		} catch (Exception e) {
			logger
					.error("Exception Occured in generateMisReportsMonthly() method : "
							+ e);
		} finally {
			endReportProcess(reportDTO.getForceStart(), reportId, reportDaoImpl);
		}
	}

	/**
	 * This function return the name of the file with path.
	 * 
	 * @param fileStartName
	 *            String File start name to be added into full file name.
	 * @param reportDate
	 *            String Date of the report to be added into the file name.
	 * @return
	 */
	public static String getFullFileName(String fileStartName,
			String reportDate) {
		String fileName = "";

		/** code for file path and file name* */
		fileName = new StringBuffer(200).append(fileStartName).append(
				reportDate.replace('/', '-'))
				// .append(Utility.getDateAsString(new
				// Date(),"dd-MM-yyyy-hh-mm-ss").substring(10))
				.append(
						Utility.getValueFromBundle(
								Constants.CSV_FILE_EXTENSION,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE))
				.toString();
		return fileName;
	}

	/**
	 * This function is a generic function of CVS File writing on the given path
	 * and data
	 * 
	 * @param fileFullPathAndName
	 *            String Name of the file with full file path.
	 * @param reportList
	 *            List contains the data to be written in the CVS file.
	 */
	public static void createFile(String fullFilePathWithName, List reportList) {

		try {
			File file = new File(fullFilePathWithName);
			CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
			csvWriter.writeAll(reportList);
			csvWriter.close();
		} catch (IOException ie) {
			logger
					.error("IOException Occured in generateRuleExeReport() method : "
							+ ie);
		} catch (Exception e) {
			logger
					.error("Exception Occured in generateRuleExeReport() method : "
							+ e);
		}

	}

	/**
	 * This method run when a report is completed. This method update the next
	 * schedule into the database by running a procedure
	 * 
	 * re-run flag = Y if force start = Y then force start = N if force start =
	 * N then call a procedure
	 * 
	 * @param forceStartFlag
	 *            String has as string value Y/N.
	 * @param reportId
	 *            int Id of the report.
	 * @param reportDaoImpl
	 *            ReportDaoImpl object of ReportDaoImpl
	 */
	public static void endReportProcess(String forceStartFlag, int reportId,
			ReportDaoImpl reportDaoImpl) {
		try {
			if ((Constants.FORCE_START_PROCESS_Y).equalsIgnoreCase(forceStartFlag)) {
				reportDaoImpl.updateParametersForReportSchedule(reportId, Constants.DATA_FEED_END_PROCESS_END);
			} else if ((Constants.FORCE_START_PROCESS_N).equalsIgnoreCase(forceStartFlag)) {
				//CALL A PROCEDURE and make rerun flag to YES and update the next schedule.
				reportDaoImpl.updateMISReportScheduleTimeProc(reportId);
			}
		} catch (Exception e) {
			logger
			.error("Exception Occured in endReportProcess() method : "
					+ e);
		}
	}
}
