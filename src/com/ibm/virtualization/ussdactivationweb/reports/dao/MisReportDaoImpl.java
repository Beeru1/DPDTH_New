/**************************************************************************
 * File     : MisReportDaoImpl.java
 * Author   : Aalok Sharma
 * Created  : Oct 3, 2008
 * Modified : Oct 3, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 3, 2008 	Aalok Sharma	First Cut.
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dto.FileUploadDTO;
//import com.ibm.virtualization.ussdactivationweb.dto.MisReportDto;
import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.MisReportDAOInterface;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class contains methods for MisReportDaoImpl
 * 
 * @author Aalok Sharma
 * @version 1.0
 ******************************************************************************/
public class MisReportDaoImpl implements MisReportDAOInterface {

	private static final Logger logger = Logger
			.getLogger(MisReportDaoImpl.class.toString());

	/**
	 * This method gets the file info data on the basis of circleCode,
	 * reportDate, reportId.
	 * 
	 * @param circleCode
	 *            Code for the circle
	 * @param reportDate
	 *            Report date on which the report is required.
	 * @param reportId
	 *            Report Id
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	/*
	public ArrayList getFileInfoData(String circleCode, String reportDate,
			String reportId) throws DAOException {
		logger.debug("Entering method getFileInfoData throws DAOException ");
		ArrayList fileInfoDataList = new ArrayList();
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn
					.prepareStatement(MisReportDAOInterface.QUERY_GET_FILE_INFO_DATA);
			stmt.setDate(1, Utility.getSqlDateFromString(reportDate,
					"dd/MM/yyyy"));
			stmt.setString(2, circleCode);
			stmt.setString(3, reportId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MisReportDto misReportDto = new MisReportDto();
				misReportDto.setFileId(rs.getString("FILE_ID"));
				misReportDto.setFileName(rs.getString("FILE_NAME"));
				misReportDto.setFilePath(rs.getString("FILE_PATH"));
				misReportDto.setReportDate(Utility.getDateAsStringFromString(
						reportDate, "dd-MMM-yyyy"));
				fileInfoDataList.add(misReportDto);
			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.error("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("EXCEPTION" + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger
						.error("SQLException occur while closing database resources."
								+ e.getMessage());
				new DAOException(
						"SQLException occur while closing database resources.");

			}
			logger.debug("Exiting method getFileInfoData throws DAOException ");

		}

		return fileInfoDataList;
	}
*/
	/**
	 * This method get the data by file information by month on the basis of
	 * circleCode, firstDateOfMonth, lastDateOfMonth, reportId
	 * 
	 * @param circleCode
	 *            Code for the circle.
	 * @param firstDateOfMonth
	 *            First day of the month.
	 * @param lastDateOfMonth
	 *            Last day of the month.
	 * @param reportId
	 *            Report Id
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	/*
	public ArrayList getFileInfoDataByMonth(String circleCode,
			String firstDateOfMonth, String lastDateOfMonth, String reportId)
			throws DAOException {
		logger
				.debug("Entering method getFileInfoDataByMonth throws DAOException ");

		ArrayList fileInfoDataList = new ArrayList();
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn
					.prepareStatement(MisReportDAOInterface.QUERY_GET_FILE_INFO_DATA_BY_MONTH);
			Date firstDateOfMonthDate = Utility.getSqlDateFromString(
					firstDateOfMonth, "dd/MM/yyyy");
			Date lastDateOfMonthDate = Utility.getSqlDateFromString(
					lastDateOfMonth, "dd/MM/yyyy");
			stmt.setDate(1, firstDateOfMonthDate);
			stmt.setDate(2, lastDateOfMonthDate);
			stmt.setString(3, circleCode);
			stmt.setString(4, reportId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				MisReportDto misReportDto = new MisReportDto();
				misReportDto.setFileId(rs.getString("FILE_ID"));
				misReportDto.setFileName(rs.getString("FILE_NAME"));
				misReportDto.setFilePath(rs.getString("FILE_PATH"));
				misReportDto.setReportDate(Utility.getDateAsStringFromString1(
						rs.getString("REPORT_DATE"), "dd-MMM-yyyy"));// "2008-12-2008"
				// converted
				// to
				// "dd-MMM-yyyy"
				fileInfoDataList.add(misReportDto);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.error("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getFileInfoDataByMonth throws DAOException ");

		}
		return fileInfoDataList;
	}
*/
	/**
	 * This method gets the file information of the file as file 
	 * name and file path by file Id from the database.
	 * 
	 * @param fileId Id of the file.
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public FileUploadDTO getFileInfoByFileId(int fileId) throws DAOException {
		logger
				.debug("Entering method getFileInfoByFileId throws DAOException ");

		FileUploadDTO fileInfoDto = new FileUploadDTO();
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn
					.prepareStatement(MisReportDAOInterface.QUERY_GET_FILE_INFO_BY_FILE_ID);
			stmt.setInt(1, fileId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				fileInfoDto.setFileName(rs.getString("FILE_NAME"));
				fileInfoDto.setFilePath(rs.getString("FILE_PATH"));
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.error("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getFileInfoByFileId throws DAOException ");
		}
		return fileInfoDto;
	}

	/**
	 * Get All the Report Id by report Type Date/Month wise. This function is
	 * for view and generation both
	 * 
	 * @param reportType
	 *            It takes data/month as report type.
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public LinkedHashMap getReportIdsByReportType(String reportType)
			throws DAOException {
		logger
				.debug("Entering method getReportIdsByReportType throws DAOException ");

		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		LinkedHashMap reportIdNameMap = new LinkedHashMap();

		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn
					.prepareStatement(MisReportDAOInterface.QUERY_GET_REPORT_IDS_BY_REPORT_TYPE);
			stmt.setString(1, reportType);
			rs = stmt.executeQuery();

			while (rs.next()) {
				reportIdNameMap.put(rs.getString("REPORT_ID"), rs
						.getString("REPORT_NAME"));
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.error("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getReportIdsByReportType throws DAOException ");
		}
		return reportIdNameMap;
	}

	/**
	 * This method generate the List of business user report data.
	 * For example : Dealer Distributer TM
	 * 
	 * @param reportIdAsBizUser reoport id for biz user.
	 * @param reportDate reoport date for biz user.
	 * @param circleCode circle code for biz user.
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisReportByBizUserActvCountSingleDay(int reportIdAsBizUser,
			String reportDate, String circleCode) throws DAOException {
		logger
				.debug("Entering method getMisReportByBizUserActvCountSingleDay throws DAOException ");

		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List reportBizUserWiseActvCountSingleDayList = new ArrayList();
		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_DAILY_DEALER_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_DEL_WISE_ACTV_COUNT_SINGLE_DAY);
				// This First Row, Heading of the File.
				reportBizUserWiseActvCountSingleDayList.add(new String[] {
						"CIRCLE CODE-NAME", "ZBM CODE-NAME", "ZSM CODE-NAME",
						"TM CODE-NAME", "DISTRIBUTOR CODE-NAME-REGISTERED NO",
						"FOS CODE-NAME-REGISTERED NO", "RETAILER CODE-NAME-REGISTERED NO", "ACTV COUNT" });
			} else if (reportIdAsBizUser == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_DAILY_DISTRIBUTER_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_DIS_WISE_ACTV_COUNT_SINGLE_DAY);
				// This First Row, Heading of the File.
				reportBizUserWiseActvCountSingleDayList
						.add(new String[] { "CIRCLE CODE-NAME",
								"ZBM CODE-NAME", "ZSM CODE-NAME",
								"TM CODE-NAME", "DISTRIBUTOR CODE-NAME-REGISTERED NO",
								"ACTV COUNT" });
			} else if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_DAILY_TM_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_TM_WISE_ACTV_COUNT_SINGLE_DAY);
				// This First Row, Heading of the File.
				reportBizUserWiseActvCountSingleDayList.add(new String[] {
						"CIRCLE CODE-NAME", "ZBM CODE-NAME", "ZSM CODE-NAME",
						"TM CODE-NAME", "ACTV COUNT" });
			}
			stmt.setString(1, circleCode);
			stmt.setDate(2, Utility.getSqlDateFromString(reportDate,
					"dd/MMM/yyyy"));
			rs = stmt.executeQuery();

			while (rs.next()) {
				if (reportIdAsBizUser == Integer
						.parseInt(Utility
								.getValueFromBundle(
										Constants.MIS_REPORT_GENERATION_DAILY_DEALER_WISE,
										Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					if(rs.getString("FOS_CODE") != null && !"".equalsIgnoreCase(rs.getString("FOS_CODE").trim()) 
							&&
							rs.getString("DEALER_CODE") != null && !"".equals(rs.getString("DEALER_CODE").trim())) {
					reportBizUserWiseActvCountSingleDayList.add(new String[] {
							rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"),
							rs.getString("ZBM_CODE") + "-"
									+ rs.getString("ZBM_NAME"),
							rs.getString("ZSM_CODE") + "-"
									+ rs.getString("ZSM_NAME"),
							rs.getString("TM_CODE") + "-"
									+ rs.getString("TM_NAME"),
							rs.getString("DISTRIBUTOR_CODE") + "-"
									+ rs.getString("DISTRIBUTOR_NAME") + "-"
									+ rs.getString("DISTRIBUTOR_MOBILE_NO"),
							rs.getString("FOS_CODE") + "-"
									+ rs.getString("FOS_NAME")+ "-"
									+ rs.getString("FOS_MOBILE_NO"),
							rs.getString("DEALER_CODE") + "-"
									+ rs.getString("DEALER_NAME")+ "-"
									+ rs.getString("DEALER_MOBILE_NO"),
							rs.getString("ACTV_COUNT") });
					}
					
				} else if (reportIdAsBizUser == Integer
						.parseInt(Utility
								.getValueFromBundle(
										Constants.MIS_REPORT_GENERATION_DAILY_DISTRIBUTER_WISE,
										Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					reportBizUserWiseActvCountSingleDayList.add(new String[] {
							rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"),
							rs.getString("ZBM_CODE") + "-"
									+ rs.getString("ZBM_NAME"),
							rs.getString("ZSM_CODE") + "-"
									+ rs.getString("ZSM_NAME"),
							rs.getString("TM_CODE") + "-"
									+ rs.getString("TM_NAME"),
							rs.getString("DISTRIBUTOR_CODE") + "-"
									+ rs.getString("DISTRIBUTOR_NAME")+ "-"
									+ rs.getString("DISTRIBUTOR_MOBILE_NO"),
							rs.getString("ACTV_COUNT") });
				} else if (reportIdAsBizUser == Integer.parseInt(Utility
						.getValueFromBundle(
								Constants.MIS_REPORT_GENERATION_DAILY_TM_WISE,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					reportBizUserWiseActvCountSingleDayList.add(new String[] {
							rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"),
							rs.getString("ZBM_CODE") + "-"
									+ rs.getString("ZBM_NAME"),
							rs.getString("ZSM_CODE") + "-"
									+ rs.getString("ZSM_NAME"),
							rs.getString("TM_CODE") + "-"
									+ rs.getString("TM_NAME"),
							rs.getString("ACTV_COUNT") });
				}
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisReportByBizUserActvCountSingleDay throws DAOException ");

		}
		return reportBizUserWiseActvCountSingleDayList;
	}

	/**
	 * This method generate the List of String[] of location wise report data.
	 * 
	 * @param reportIdAsLoc report id for location
	 * @param reportDate report date for location
	 * @param circleCoder circle Code for location
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisReportByLocActvCountSingleDay(int reportIdAsLoc,
			String reportDate, String circleCode) throws DAOException {
		logger
				.debug("Entering method getMisReportByLocActvCountSingleDay throws DAOException ");
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List reportLocWiseActvCountSingleDayList = new ArrayList();

		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsLoc == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_DAILY_ZONE_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_ZONE_WISE_REPORT_SINGLE_DAY);
				// This First Row, Heading of the File.
				reportLocWiseActvCountSingleDayList.add(new String[] {
						"CIRCLE CODE-NAME", "ZONE CODE-NAME",
						"ACTV COUNT" });
			} else if (reportIdAsLoc == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_DAILY_CITY_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_CITY_WISE_REPORT_SINGLE_DAY);
				// This First Row, Heading of the File.
				reportLocWiseActvCountSingleDayList.add(new String[] {
						"CIRCLE CODE-NAME", "ZONE CODE-NAME" , "CITY CODE-NAME", "ACTV COUNT" });
			}
			stmt.setString(1, circleCode);
			stmt.setDate(2, Utility.getSqlDateFromString(reportDate,
					"dd/MMM/yyyy"));

			rs = stmt.executeQuery();

			while (rs.next()) {
				if (reportIdAsLoc == Integer
						.parseInt(Utility
								.getValueFromBundle(
										Constants.MIS_REPORT_GENERATION_DAILY_ZONE_WISE,
										Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					reportLocWiseActvCountSingleDayList.add(new String[] {
							rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"),
							rs.getString("ZONE_ID") + "-"
									+ rs.getString("ZONE_NAME"),
							rs.getString("ACTV_COUNT") });
				} else if (reportIdAsLoc == Integer
						.parseInt(Utility
								.getValueFromBundle(
										Constants.MIS_REPORT_GENERATION_DAILY_CITY_WISE,
										Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
					reportLocWiseActvCountSingleDayList.add(new String[] {
							rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"),
							rs.getString("ZONE_ID") + "-"
									+ rs.getString("ZONE_NAME"),
							rs.getString("CITY_ID") + "-"
									+ rs.getString("CITY_NAME"),
							rs.getString("ACTV_COUNT") });
				}
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisReportByLocActvCountSingleDay throws DAOException ");

		}
		return reportLocWiseActvCountSingleDayList;
	}

	/**
	 * This method generate the List of String[] of detailed report data.
	 * 
	 * @param reportIdAsDetailed report id for detailed report
	 * @param reportDate report date for detailed report
	 * @param circleCoder circle Code for detailed report
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisDtlReportActvCountSingleDay(int reportIdAsDetailed,
			String reportDate, String circleCode) throws DAOException {
		logger
				.debug("Entering method getMisDtlReportActvCountSingleDay throws DAOException ");
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List reportDtlWiseActvCountSingleDayList = new ArrayList();
		String distCode = "";
		String distName = "";
		String distNo = "";
		String fosCode = "";
		String fosName = "";
		String fosNo = "";
		String dealerCode = "";
		String dealerName ="";
		String dealerNo ="";

		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsDetailed == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_DAILY_DETAILED_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_DTL_WISE_REPORT_SINGLE_DAY);
			}
			stmt.setString(1, circleCode);
			stmt.setDate(2, Utility.getSqlDateFromString(reportDate,
					"dd/MMM/yyyy"));
			rs = stmt.executeQuery();
			// This First Row, Heading of the File.
			reportDtlWiseActvCountSingleDayList.add(new String[] {
					"CIRCLE CODE-NAME", "ZONE CODE-NAME", "CITY CODE-NAME",
					"ZBM CODE-NAME", "ZSM CODE-NAME",
					"TM CODE-NAME", "DISTRIBUTOR CODE-NAME-REGISTERED NO", "FOS CODE-NAME-REGISTERED NO",
					"RETAILER CODE-NAME-REGISTERED NO", "SUBS MSISDN","SUBS SIM","IS IPHONE","REGISTRATION COMPLETED DATE",
					"VERIFICATION COMPLETED DATE", "ACTIVATION COMPLETED DATE", "TAT(IN MIN)" });
			while (rs.next()) {
				distCode =rs.getString("DISTRIBUTOR_CODE"); 
				distName = rs.getString("DISTRIBUTOR_NAME");
				distNo = rs.getString("DISTRIBUTOR_MOBILE_NO");
				fosCode = rs.getString("FOS_CODE");
				fosName = rs.getString("FOS_NAME");
				fosNo = rs.getString("FOS_MOBILE_NO");
				dealerCode = rs.getString("DEALER_CODE");
				dealerName =rs.getString("DEALER_NAME");
				dealerNo =rs.getString("DEALER_MOBILE_NO");
				distCode = (distCode == null) ? "": distCode;
				distName = (distName == null) ? "": distName;
				distNo = (distNo == null) ? "": distNo;
				fosCode = (fosCode == null) ? "" : fosCode;
				fosName = (fosName == null) ? "" : fosName;
				fosNo = (fosNo == null) ? "" : fosNo;
				dealerCode = (dealerCode == null) ? "" : dealerCode;
				dealerName = (dealerName == null) ? "" : dealerName;
				dealerNo = (dealerNo== null) ? "" : dealerNo;
	
				reportDtlWiseActvCountSingleDayList
						.add(new String[] {
								rs.getString("CIRCLE_CODE") + "-"
										+ rs.getString("CIRCLE_NAME"),
								rs.getString("ZONE_ID") + "-"
										+ rs.getString("ZONE_NAME"),
								rs.getString("CITY_ID") + "-"
										+ rs.getString("CITY_NAME"),
								rs.getString("ZBM_CODE") + "-"
										+ rs.getString("ZBM_NAME"),
								rs.getString("ZSM_CODE") + "-"
										+ rs.getString("ZSM_NAME"),
								rs.getString("TM_CODE") + "-"
										+ rs.getString("TM_NAME"),
										distCode + "-"
										+distName + "-"
										+distNo,
										fosCode + "-"
										+ fosName+ "-"
										+fosNo,
										dealerCode + "-"
										+ dealerName+ "-"
										+dealerNo,
								rs.getString("SUBS_MSISDN"),
								rs.getString("SUBS_SIM"),
								rs.getString("IS_IPHONE"),
								rs.getString("REGISTRATION_COMPLETED_DATE"),
								rs.getString("VARIFICATION_COMPLETED_DATE"),
								rs.getString("ACTIVATION_COMPLETED_DATE"),
								rs.getString("TAT_IN_MIN").trim() });

			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisDtlReportActvCountSingleDay throws DAOException ");

		}
		return reportDtlWiseActvCountSingleDayList;
	}

	/**
	 * This method generate the List of String[] of business user report data
	 * month wise. For example : Dealer Distributer TM
	 * 
	 * @param reportIdAsBizUser reoport id for biz user.
	 * @param reportDate reoport date for biz user.
	 * @param circleCode circle code for biz user.
	 * 
	 * @return 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisReportByBizUserActvCountByMonth(int reportIdAsBizUser,
			int reportMonth, int reportYear, String circleCode)
			throws DAOException {
		logger
				.debug("Entering method getMisReportByBizUserActvCountByMonth throws DAOException ");
		Connection dataConn = null;
		PreparedStatement getInfoStatement = null;
		ResultSet rs = null;
		List reportDistWiseActvCountSingleDayList = new ArrayList();
		//final int arraySise = 50;
		//String[] currentRow = null;
		String currentRowValue = "";
		String firstDateOfMonth = "";
		String lastDateOfMonth = "";
		String lastDateOfMonth_MMM_Format = "";
		Calendar calendar = null;
		LinkedHashMap dateWiseActvCount = new LinkedHashMap();
		String currentDateOfMonth = "";
		String currentDateOfMonth_MMM_Format = "";
		//int i = 0;
		try {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, reportMonth - 1);

			// Calculating the first day of the selected month
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			firstDateOfMonth = Utility.getDateAsString(calendar.getTime(),
					"dd/MM/yyyy");

			// Calculating the last day of the selected month
			calendar.set(Calendar.DAY_OF_MONTH, calendar
					.getActualMaximum(Calendar.DATE));
			lastDateOfMonth = Utility.getDateAsString(calendar.getTime(),
					"dd/MM/yyyy");
			lastDateOfMonth_MMM_Format = Utility.getDateAsString(calendar
					.getTime(), "dd/MMM/yyyy");

			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsBizUser == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_MONTHLY_DEALER_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				getInfoStatement = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_DEL_WISE_ACTV_COUNT_MONTH_WISE);
				// This First Row, Heading of the File.
/*				currentRow = new String[arraySise];
				currentRow[i++] = "CIRCLE CODE-NAME";
				currentRow[i++] = "ZBM CODE-NAME";
				currentRow[i++] = "ZSM CODE-NAME";
				currentRow[i++] = "TM CODE-NAME";
				currentRow[i++] = "DISTRIBUTOR CODE-NAME";
				currentRow[i++] = "FOS CODE-NAME";
				currentRow[i++] = "DEALER CODE-NAME";*/
				
				// add all the selected month dates
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				
				LinkedList healerList = new LinkedList();
				healerList.add("CIRCLE CODE-NAME");
				healerList.add("ZBM CODE-NAME");
				healerList.add("ZSM CODE-NAME");
				healerList.add("TM CODE-NAME");
				healerList.add("DISTRIBUTOR CODE-NAME-REGISTERED NO");
				healerList.add("FOS CODE-NAME-REGISTERED NO");
				healerList.add("RETAILER CODE-NAME-REGISTERED NO");
				
				
				while (!lastDateOfMonth_MMM_Format
						.equalsIgnoreCase(currentDateOfMonth_MMM_Format)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					currentDateOfMonth_MMM_Format = Utility.getDateAsString(
							calendar.getTime(), "dd/MMM/yyyy");
					//currentRow[i++] = currentDateOfMonth_MMM_Format;
					healerList.add(currentDateOfMonth_MMM_Format);
					
				}
				String reportHeader[] = {};
				if(healerList != null && !healerList.isEmpty()){
					reportHeader  = (String []) healerList.toArray (new String [0]);
				} 
				reportDistWiseActvCountSingleDayList.add(reportHeader);
				//reportDistWiseActvCountSingleDayList.add(healerList);
			} else if (reportIdAsBizUser == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_MONTHLY_DISTRIBUTER_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				getInfoStatement = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_DIS_WISE_ACTV_COUNT_MONTH_WISE);
				// This First Row, Heading of the File.
				/*currentRow = new String[arraySise];
				currentRow[i++] = "CIRCLE CODE-NAME";
				currentRow[i++] = "ZBM CODE-NAME";
				currentRow[i++] = "ZSM CODE-NAME";
				currentRow[i++] = "TM CODE-NAME";
				currentRow[i++] = "DISTRIBUTOR CODE-NAME";*/
				
				// add all the selected month dates
				LinkedList healerList = new LinkedList();
				healerList.add("CIRCLE CODE-NAME");
				healerList.add("ZBM CODE-NAME");
				healerList.add("ZSM CODE-NAME");
				healerList.add("TM CODE-NAME");
				healerList.add("DISTRIBUTOR CODE-NAME-REGISTERED NO");
				
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				
				while (!lastDateOfMonth_MMM_Format
						.equalsIgnoreCase(currentDateOfMonth_MMM_Format)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					currentDateOfMonth_MMM_Format = Utility.getDateAsString(
							calendar.getTime(), "dd/MMM/yyyy");
					//currentRow[i++] = currentDateOfMonth_MMM_Format;
					
					healerList.add(currentDateOfMonth_MMM_Format);
				}
				String reportHeader[] = {};
				if(healerList != null && !healerList.isEmpty()){
					reportHeader  = (String []) healerList.toArray (new String [0]);
				} 
				reportDistWiseActvCountSingleDayList.add(reportHeader);
				
				//reportDistWiseActvCountSingleDayList.add(currentRow);
			} else if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_TM_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				getInfoStatement = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_TM_WISE_ACTV_COUNT_MONTH_WISE);
				// This First Row, Heading of the File.
/*				currentRow = new String[arraySise];
				currentRow[i++] = "CIRCLE CODE-NAME";
				currentRow[i++] = "ZBM CODE-NAME";
				currentRow[i++] = "ZSM CODE-NAME";
				currentRow[i++] = "TM CODE-NAME";*/
				
				// add all the selected month dates
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				LinkedList healerList = new LinkedList();
				healerList.add("CIRCLE CODE-NAME");
				healerList.add("ZBM CODE-NAME");
				healerList.add("ZSM CODE-NAME");
				healerList.add("TM CODE-NAME");
				
				while (!lastDateOfMonth_MMM_Format
						.equalsIgnoreCase(currentDateOfMonth_MMM_Format)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					currentDateOfMonth_MMM_Format = Utility.getDateAsString(
							calendar.getTime(), "dd/MMM/yyyy");
					//currentRow[i++] = currentDateOfMonth_MMM_Format;
					
					healerList.add(currentDateOfMonth_MMM_Format);
				}
				
				String reportHeader[] = {};
				if(healerList != null && !healerList.isEmpty()){
					reportHeader  = (String []) healerList.toArray (new String [0]);
				} 
				reportDistWiseActvCountSingleDayList.add(reportHeader);
				
				//reportDistWiseActvCountSingleDayList.add(currentRow);
			}
			getInfoStatement.setString(1, circleCode);
			getInfoStatement.setDate(2, Utility.getSqlDateFromString(
					firstDateOfMonth, "dd/MM/yyyy"));
			getInfoStatement.setDate(3, Utility.getSqlDateFromString(
					lastDateOfMonth, "dd/MM/yyyy"));
			rs = getInfoStatement.executeQuery();

			getInfoStatement.setFetchSize(Integer.parseInt(Utility
					.getValueFromBundle(Constants.MIS_REPORT_FATCH_SIZE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE)));
			do {
				while (rs.next()) {
					if (reportIdAsBizUser == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_DEALER_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						dateWiseActvCount = getActvCountByMonth(
								reportIdAsBizUser, rs.getString("DEALER_CODE"),
								firstDateOfMonth, lastDateOfMonth);
						if(!(rs.getString("DEALER_CODE") == null || "".equalsIgnoreCase(rs.getString("DEALER_CODE").trim())
								|| rs.getString("FOS_CODE") == null || "".equalsIgnoreCase(rs.getString("FOS_CODE").trim()))) {
							//i = 0;
							/*currentRow = new String[arraySise];
							currentRow[i++] = rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME");
							currentRow[i++] = rs.getString("ZBM_CODE") + "-"
									+ rs.getString("ZBM_NAME");
							currentRow[i++] = rs.getString("ZSM_CODE") + "-"
									+ rs.getString("ZSM_NAME");
							currentRow[i++] = rs.getString("TM_CODE") + "-"
									+ rs.getString("TM_NAME");
							currentRow[i++] = rs.getString("DISTRIBUTOR_CODE")
									+ "-" + rs.getString("DISTRIBUTOR_NAME");
							currentRow[i++] = rs.getString("FOS_CODE") + "-"
									+ rs.getString("FOS_NAME");
							currentRow[i++] = rs.getString("DEALER_CODE") + "-"
									+ rs.getString("DEALER_NAME");*/
							
							
							// add all the selected month dates
							calendar.set(Calendar.DAY_OF_MONTH, 0);
							currentDateOfMonth = "";
							
							LinkedList dataList = new LinkedList();
							dataList.add( rs.getString("CIRCLE_CODE") + "-"
									+ rs.getString("CIRCLE_NAME"));
							dataList.add(rs.getString("ZBM_CODE") + "-"
									+ rs.getString("ZBM_NAME"));
							dataList.add(rs.getString("ZSM_CODE") + "-"
									+ rs.getString("ZSM_NAME"));
							
							dataList.add(rs.getString("TM_CODE") + "-"
									+ rs.getString("TM_NAME"));
							dataList.add(rs.getString("DISTRIBUTOR_CODE")
									+ "-" + rs.getString("DISTRIBUTOR_NAME")
									+ "-" + rs.getString("DISTRIBUTOR_MOBILE_NO"));
							dataList.add(rs.getString("FOS_CODE") + "-"
									+ rs.getString("FOS_NAME")
									+ "-" + rs.getString("FOS_MOBILE_NO"));
							dataList.add(rs.getString("DEALER_CODE") + "-"
									+ rs.getString("DEALER_NAME")
									+ "-" + rs.getString("DEALER_MOBILE_NO"));
							while (!lastDateOfMonth
									.equalsIgnoreCase(currentDateOfMonth)) {
								calendar.add(Calendar.DAY_OF_MONTH, 1);
								currentDateOfMonth = Utility.getDateAsString(
										calendar.getTime(), "dd/MM/yyyy");
								currentRowValue = (String) dateWiseActvCount
										.get(currentDateOfMonth);
	
								if (currentRowValue == null) {
									dataList.add("0");
									//currentRow[i++] = "0";
								} else {
									dataList.add(currentRowValue);
									//currentRow[i++] = currentRowValue;
								}
							}
							String[] dataArray = {};
							if(dataList != null && !dataList.isEmpty()){
								dataArray = (String []) dataList.toArray (new String [0]);
							} 
							reportDistWiseActvCountSingleDayList.add(dataArray);
							//reportDistWiseActvCountSingleDayList.add(currentRow);
						}
					} else if (reportIdAsBizUser == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_DISTRIBUTER_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						dateWiseActvCount = getActvCountByMonth(
								reportIdAsBizUser, rs
										.getString("DISTRIBUTOR_CODE"),
								firstDateOfMonth, lastDateOfMonth);
						//i = 0;
						/*currentRow = new String[arraySise];
						currentRow[i++] = rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME");
						currentRow[i++] = rs.getString("ZBM_CODE") + "-"
								+ rs.getString("ZBM_NAME");
						currentRow[i++] = rs.getString("ZSM_CODE") + "-"
								+ rs.getString("ZSM_NAME");
						currentRow[i++] = rs.getString("TM_CODE") + "-"
								+ rs.getString("TM_NAME");
						currentRow[i++] = rs.getString("DISTRIBUTOR_CODE")
								+ "-" + rs.getString("DISTRIBUTOR_NAME");*/
						
						// add all the selected month dates
						
						calendar.set(Calendar.DAY_OF_MONTH, 0);
						currentDateOfMonth = "";
						
						LinkedList dataList = new LinkedList();
						dataList.add( rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME"));
						dataList.add(rs.getString("ZBM_CODE") + "-"
								+ rs.getString("ZBM_NAME"));
						dataList.add(rs.getString("ZSM_CODE") + "-"
								+ rs.getString("ZSM_NAME"));
						
						dataList.add(rs.getString("TM_CODE") + "-"
								+ rs.getString("TM_NAME"));
						dataList.add(rs.getString("DISTRIBUTOR_CODE")
								+ "-" + rs.getString("DISTRIBUTOR_NAME")
								+ "-" + rs.getString("DISTRIBUTOR_MOBILE_NO"));
						while (!lastDateOfMonth
								.equalsIgnoreCase(currentDateOfMonth)) {
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							currentDateOfMonth = Utility.getDateAsString(
									calendar.getTime(), "dd/MM/yyyy");
							currentRowValue = (String) dateWiseActvCount
									.get(currentDateOfMonth);

							if (currentRowValue == null) {
								dataList.add("0");
								//currentRow[i++] = "0";
							} else {
								dataList.add(currentRowValue);
								//currentRow[i++] = currentRowValue;
							}
						}
						String[] dataArray = {};
						if(dataList != null && !dataList.isEmpty()){
							dataArray = (String []) dataList.toArray (new String [0]);
						} 
						reportDistWiseActvCountSingleDayList.add(dataArray);
						//reportDistWiseActvCountSingleDayList.add(currentRow);
					} else if (reportIdAsBizUser == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_TM_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						dateWiseActvCount = getActvCountByMonth(
								reportIdAsBizUser, rs.getString("TM_CODE"),
								firstDateOfMonth, lastDateOfMonth);
						//i = 0;
						/*currentRow = new String[arraySise];
						currentRow[i++] = rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME");
						currentRow[i++] = rs.getString("ZBM_CODE") + "-"
								+ rs.getString("ZBM_NAME");
						currentRow[i++] = rs.getString("ZSM_CODE") + "-"
								+ rs.getString("ZSM_NAME");
						currentRow[i++] = rs.getString("TM_CODE") + "-"
								+ rs.getString("TM_NAME");*/

						// add all the selected month dates
						calendar.set(Calendar.DAY_OF_MONTH, 0);
						currentDateOfMonth = "";
						
						LinkedList dataList = new LinkedList();
						dataList.add( rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME"));
						dataList.add(rs.getString("ZBM_CODE") + "-"
								+ rs.getString("ZBM_NAME"));
						dataList.add(rs.getString("ZSM_CODE") + "-"
								+ rs.getString("ZSM_NAME"));
						
						dataList.add(rs.getString("TM_CODE") + "-"
								+ rs.getString("TM_NAME"));
						
						
						while (!lastDateOfMonth
								.equalsIgnoreCase(currentDateOfMonth)) {
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							currentDateOfMonth = Utility.getDateAsString(
									calendar.getTime(), "dd/MM/yyyy");
							currentRowValue = (String) dateWiseActvCount
									.get(currentDateOfMonth);

							if (currentRowValue == null) {
								dataList.add("0");
								//currentRow[i++] = "0";
							} else {
								dataList.add(currentRowValue);
								//currentRow[i++] = currentRowValue;
							}
						}
						String[] dataArray = {};
						if(dataList != null && !dataList.isEmpty()){
							dataArray = (String []) dataList.toArray (new String [0]);
						} 
						reportDistWiseActvCountSingleDayList.add(dataArray);
						//reportDistWiseActvCountSingleDayList.add(currentRow);
					}
				}
			} while (getInfoStatement.getMoreResults());
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, getInfoStatement,
						rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisReportByBizUserActvCountByMonth throws DAOException ");

		}
		return reportDistWiseActvCountSingleDayList;
	}

	/**
	 * This method generate the List of String[] of location report data month
	 * wise. For example : Dealer Distributer TM
	 * 
	 * @param reportIdAsLoc report id for location
	 * @param reportDate report date for location
	 * @param circleCoder circle Code for location
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisReportByLocActvCountByMonth(int reportIdAsLoc,
			int reportMonth, int reportYear, String circleCode)
			throws DAOException {
		logger
				.debug("Entering method getMisReportByLocActvCountByMonth throws DAOException ");
		Connection dataConn = null;
		PreparedStatement getInfoStatement = null;
		ResultSet rs = null;

		List reportDistWiseActvCountSingleDayList = new ArrayList();
		//final int arraySise = 50;
		//String[] currentRow = null;
		String currentRowValue = "";
		String firstDateOfMonth = "";
		String lastDateOfMonth = "";
		String lastDateOfMonth_MMM_Format = null;

		Calendar calendar = null;
		LinkedHashMap dateWiseActvCount = new LinkedHashMap();
		String currentDateOfMonth = "";
		String currentDateOfMonth_MMM_Format = "";
	//	int i = 0;
		try {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, reportMonth - 1);

			// Calculating the first day of the selected month
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			firstDateOfMonth = Utility.getDateAsString(calendar.getTime(),
					"dd/MM/yyyy");

			// Calculating the last day of the selected month
			calendar.set(Calendar.DAY_OF_MONTH, calendar
					.getActualMaximum(Calendar.DATE));
			lastDateOfMonth = Utility.getDateAsString(calendar.getTime(),
					"dd/MM/yyyy");
			lastDateOfMonth_MMM_Format = Utility.getDateAsString(calendar
					.getTime(), "dd/MMM/yyyy");

			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsLoc == Integer.parseInt(Utility.getValueFromBundle(
					Constants.MIS_REPORT_GENERATION_MONTHLY_ZONE_WISE,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				getInfoStatement = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_ZONE_WISE_ACTV_COUNT_MONTH_WISE);
				// This First Row, Heading of the File.
				/*currentRow = new String[arraySise];
				currentRow[i++] = "CIRCLE CODE-NAME";
				currentRow[i++] = "CITY CODE-NAME";
				currentRow[i++] = "ZONE CODE-NAME";*/
				// add all the selected month dates
				
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				
				LinkedList dateList = new LinkedList();
				dateList.add("CIRCLE CODE-NAME");
				dateList.add("ZONE CODE-NAME");
				
				while (!lastDateOfMonth_MMM_Format
						.equalsIgnoreCase(currentDateOfMonth_MMM_Format)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					currentDateOfMonth_MMM_Format = Utility.getDateAsString(
							calendar.getTime(), "dd/MMM/yyyy");
					//currentRow[i++] = currentDateOfMonth_MMM_Format;
					
					dateList.add(currentDateOfMonth_MMM_Format);
				}
				String reportHeader[] = {};
				if(dateList != null && !dateList.isEmpty()){
					reportHeader  = (String []) dateList.toArray (new String [0]);
				} 
				
                 reportDistWiseActvCountSingleDayList.add(reportHeader);
				//reportDistWiseActvCountSingleDayList.add(currentRow);
				
			} else if (reportIdAsLoc == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_CITY_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				getInfoStatement = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_MIS_REPORT_CITY_WISE_ACTV_COUNT_MONTH_WISE);
				// This First Row, Heading of the File.
				/*currentRow = new String[arraySise];
				currentRow[i++] = "CIRCLE CODE-NAME";
				currentRow[i++] = "CITY CODE-NAME";*/
				
				// add all the selected month dates
				LinkedList dateList = new LinkedList();
				dateList.add("CIRCLE CODE-NAME");
				dateList.add("ZONE CODE-NAME");
				dateList.add("CITY CODE-NAME");
				
				calendar.set(Calendar.DAY_OF_MONTH, 0);
				

				while (!lastDateOfMonth_MMM_Format
						.equalsIgnoreCase(currentDateOfMonth_MMM_Format)) {
					calendar.add(Calendar.DAY_OF_MONTH, 1);
					currentDateOfMonth_MMM_Format = Utility.getDateAsString(
							calendar.getTime(), "dd/MMM/yyyy");
					
					//currentRow[i++] = currentDateOfMonth_MMM_Format;
					
					dateList.add(currentDateOfMonth_MMM_Format);
		
				}
				String reportHeader[] = {};
				if(dateList != null && !dateList.isEmpty()){
					reportHeader  = (String []) dateList.toArray (new String [0]);
				} 
				
				reportDistWiseActvCountSingleDayList.add(reportHeader);
				
				//reportDistWiseActvCountSingleDayList.add(currentRow);
			}
			getInfoStatement.setString(1, circleCode);
			getInfoStatement.setDate(2, Utility.getSqlDateFromString(
					firstDateOfMonth, "dd/MM/yyyy"));
			getInfoStatement.setDate(3, Utility.getSqlDateFromString(
					lastDateOfMonth, "dd/MM/yyyy"));
			rs = getInfoStatement.executeQuery();

			getInfoStatement.setFetchSize(Integer.parseInt(Utility
					.getValueFromBundle(Constants.MIS_REPORT_FATCH_SIZE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE)));
			do {
				while (rs.next()) {

					if (reportIdAsLoc == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_ZONE_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						dateWiseActvCount = getActvCountByMonth(reportIdAsLoc,
								rs.getString("ZONE_ID"), firstDateOfMonth,
								lastDateOfMonth);
						//i = 0;
						/*currentRow = new String[arraySise];
						currentRow[i++] = rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME");
						currentRow[i++] = rs.getString("CITY_ID") + "-"
								+ rs.getString("CITY_NAME");
						currentRow[i++] = rs.getString("ZONE_ID") + "-"
								+ rs.getString("ZONE_NAME");*/
						
						// add all the selected month dates
						calendar.set(Calendar.DAY_OF_MONTH, 0);
						currentDateOfMonth = "";
						
						
						LinkedList dataList = new LinkedList();
						dataList.add( rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME"));
						dataList.add(rs.getString("ZONE_ID") + "-"
								+ rs.getString("ZONE_NAME"));
						
						while (!lastDateOfMonth
								.equalsIgnoreCase(currentDateOfMonth)) {
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							currentDateOfMonth = Utility.getDateAsString(
									calendar.getTime(), "dd/MM/yyyy");
							currentRowValue = (String) dateWiseActvCount
									.get(currentDateOfMonth);

							if (currentRowValue == null) {
								
								dataList.add("0");
								
								//currentRow[i++] = "0";
								
							} else {
								dataList.add(currentRowValue);
								
								//currentRow[i++] = currentRowValue;
							}
						}
						String[] dataArray = {};
						if(dataList != null && !dataList.isEmpty()){
							dataArray = (String []) dataList.toArray (new String [0]);
						} 
						reportDistWiseActvCountSingleDayList.add(dataArray);
						
						//reportDistWiseActvCountSingleDayList.add(currentRow);
						
					} else if (reportIdAsLoc == Integer
							.parseInt(Utility
									.getValueFromBundle(
											Constants.MIS_REPORT_GENERATION_MONTHLY_CITY_WISE,
											Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						dateWiseActvCount = getActvCountByMonth(reportIdAsLoc,
								rs.getString("CITY_ID"), firstDateOfMonth,
								lastDateOfMonth);
						//i = 0;
						/*currentRow = new String[arraySise];
						currentRow[i++] = rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME");
						currentRow[i++] = rs.getString("CITY_ID") + "-"
								+ rs.getString("CITY_NAME");*/	
						// add all the selected month dates
					
						calendar.set(Calendar.DAY_OF_MONTH, 0);
						currentDateOfMonth = "";
						LinkedList dataList = new LinkedList();
						dataList.add( rs.getString("CIRCLE_CODE") + "-"
								+ rs.getString("CIRCLE_NAME"));
						dataList.add(rs.getString("ZONE_ID") + "-"
								+ rs.getString("ZONE_NAME"));
						dataList.add( rs.getString("CITY_ID") + "-"
								+ rs.getString("CITY_NAME"));
						
						while (!lastDateOfMonth
								.equalsIgnoreCase(currentDateOfMonth)) {
							calendar.add(Calendar.DAY_OF_MONTH, 1);
							currentDateOfMonth = Utility.getDateAsString(
									calendar.getTime(), "dd/MM/yyyy");
							currentRowValue = (String) dateWiseActvCount
									.get(currentDateOfMonth);

							if (currentRowValue == null) {
								//currentRow[i++] = "0";
								dataList.add("0");
							} else {
								dataList.add(currentRowValue);
								//currentRow[i++] = currentRowValue;
							}
						}
						String[] dataArray = {};
						if(dataList != null && !dataList.isEmpty()){
							dataArray = (String []) dataList.toArray (new String [0]);
						} 
						//System.out.println("reportHeader  == "+dataArray.toString());
						reportDistWiseActvCountSingleDayList.add(dataArray);
						//reportDistWiseActvCountSingleDayList.add(currentRow);
					}
				}
			} while (getInfoStatement.getMoreResults());
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, getInfoStatement,
						rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisReportByLocActvCountByMonth throws DAOException ");

		}
		return reportDistWiseActvCountSingleDayList;
	}

	/**
	 * Get All the Report Id by report Type Date/Month wise. This function is
	 * for view and generation both
	 * 
	 * @param reportType
	 *            It takes data/month as report type.
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */

	private LinkedHashMap getActvCountByMonth(int reportIdAsBizUser,
			String selectedCode, String firstDateOfMonth, String lastDateOfMonth)
			throws DAOException {
		logger
				.debug("Entering method getActvCountByMonth throws DAOException ");
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		LinkedHashMap dateWiseActvCountOfDealer = new LinkedHashMap();

		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			if (reportIdAsBizUser == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_MONTHLY_DEALER_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_ACTC_COUNT_OF_MONTH_BY_DEALER);
			} else if (reportIdAsBizUser == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_REPORT_GENERATION_MONTHLY_DISTRIBUTER_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_ACTC_COUNT_OF_MONTH_BY_DIST);
			} else if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_TM_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_ACTC_COUNT_OF_MONTH_BY_TM);
			} else if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_ZONE_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_ACTC_COUNT_OF_MONTH_BY_ZONE);
			} else if (reportIdAsBizUser == Integer.parseInt(Utility
					.getValueFromBundle(
							Constants.MIS_REPORT_GENERATION_MONTHLY_CITY_WISE,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_ACTC_COUNT_OF_MONTH_BY_CITY);
			}

			stmt.setDate(1, Utility.getSqlDateFromString(firstDateOfMonth,
					"dd/MM/yyyy"));
			stmt.setDate(2, Utility.getSqlDateFromString(lastDateOfMonth,
					"dd/MM/yyyy"));
			stmt.setString(3, selectedCode);

			rs = stmt.executeQuery();

			while (rs.next()) {
				dateWiseActvCountOfDealer.put(Utility
						.getDateAsStringFromString1(rs
								.getString("SUMMARY_DATE"), "dd/MM/yyyy"), rs
						.getString("ACTV_COUNT"));
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (DAOException sqe) {
			logger.error("DAO EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getActvCountByMonth throws DAOException ");

		}
		return dateWiseActvCountOfDealer;
	}

	/**
	 * This method get MIS Reports Schedule. This is common for daily and
	 * monthly report.
	 * 
	 * @param reportType
	 *            String type of report (D)aily/(M)onthly
	 * 
	 * @return ArrayList
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */

	public ArrayList getMISReportsSchedule(String reportType)
			throws DAOException {
		logger
				.debug("Entering method getMISReportsSchedule throws DAOException ");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList reportsScheduleList = new ArrayList();
		ReportDTO reportDTO = null;
		String getReportDetails = "";
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			if ((Constants.DATA_FEED_SCHEDULE_PROCESS_F).equalsIgnoreCase(reportType)) {
				getReportDetails = MisReportDAOInterface.FETCH_MIS_REPORT_DATA_FEED_SCHEDULE;
			} else {
				getReportDetails = MisReportDAOInterface.FETCH_MIS_REPORT_SCHEDULE;
			}

			prepareStatement = connection.prepareStatement(getReportDetails);
			prepareStatement.setString(1, reportType);
			resultSet = prepareStatement.executeQuery();
			Timestamp reportDateTime = null;
			Calendar calendar1 = Calendar.getInstance();

			while (resultSet.next()) {
				reportDTO = new ReportDTO();
				reportDTO.setReportId(resultSet.getInt("REPORT_ID"));
				reportDTO.setReportName(String.valueOf(resultSet
						.getString("REPORT_NAME")));
				reportDTO.setForceStart(resultSet.getString("FORCE_START"));
				reportDateTime = resultSet.getTimestamp("REPORT_START_DATE");

				// IF it is not force start than it will store the report start
				// date.
				if ((Constants.FORCE_START_PROCESS_N).equalsIgnoreCase(resultSet.getString("FORCE_START"))) {
					if (Utility
							.getValueFromBundle(
									Constants.DAILY,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE).equalsIgnoreCase(reportType)) {
						calendar1.setTime(new Date(Utility.getDate(Utility
								.getTimestampAsString(reportDateTime,
										"dd/MM/yyyy"), "dd/MM/yyyy")));
						calendar1.add(java.util.Calendar.DAY_OF_MONTH, -1);
						reportDateTime = new Timestamp(calendar1.getTime()
								.getTime());
					} else if (Utility
							.getValueFromBundle(
									Constants.MONTHLY,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE).equalsIgnoreCase(reportType)) {
						calendar1.setTime(new Date(Utility.getDate(Utility
								.getTimestampAsString(reportDateTime,
										"dd/MM/yyyy"), "dd/MM/yyyy")));
						calendar1.add(java.util.Calendar.MONTH, -1);
						reportDateTime = new Timestamp(calendar1.getTime()
								.getTime());
					} else if ((Constants.DATA_FEED_SCHEDULE_PROCESS_F).equalsIgnoreCase(reportType)) {
						calendar1.setTime(new Date(Utility.getDate(Utility
								.getTimestampAsString(reportDateTime,
										"dd/MM/yyyy"), "dd/MM/yyyy")));
						calendar1.add(java.util.Calendar.DAY_OF_MONTH, -1);
						reportDateTime = new Timestamp(calendar1.getTime()
								.getTime());
					}
				} else {// IF it is force start then it will store the force
					// start date.
					reportDateTime = resultSet.getTimestamp("FORCE_START_DATE");
				}

				reportDTO.setReportDateTime(reportDateTime);

				reportDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				reportsScheduleList.add(reportDTO);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception occur " + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger.debug("Exiting method getMISReportsSchedule DAOException");

		}
		return reportsScheduleList;
	}

	/**
	 * This method insertMISReportFileInfo() updates Report Schedule Time
	 * in the database
	 * 
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public void insertMISReportFileInfo(String fileName, String filePath,
			String circleCode, int reportId, String reportDate)
			throws DAOException {
		logger
				.debug("Entering method insertMISReportFileInfo throws DAOException");

		Connection connection = null;
		PreparedStatement prepareStatement = null;

		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(MisReportDAOInterface.QUERY_INSERT_MIS_FILE_INFO);
			prepareStatement.setString(1, fileName);
			prepareStatement.setString(2, filePath);
			prepareStatement.setString(3, circleCode);
			prepareStatement.setInt(4, reportId);
			prepareStatement.setDate(5, Utility.getSqlDateFromString(
					reportDate, "dd/MMM/yyyy"));
			prepareStatement.executeUpdate();
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception occur " + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method insertMISReportFileInfo throws DAOException");
		}
	}
	
	/**
	 * This method generate the List of String[] of Prnding report data.
	 * 
	 * @param reportIdAsDetailed report id for detailed report
	 * @param reportDate report date for detailed report
	 * @param circleCoder circle Code for detailed report
	 * 
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public List getMisPendingReportActCntSingleDay(int reportIdAsDetailed,
			String reportDate, String circleCode) throws DAOException {
		logger
				.debug("Entering method getMisPendingReportActCntSingleDay throws DAOException ");
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List pendingReportList = new ArrayList();
		String distCode = "";
		String distName = "";
		String fosCode = "";
		String fosName = "";
		String dealerCode = "";
		String dealerName ="";
		String distRegNo="";
		String fosRegNo = "";
		String dealerRegNo="";
		
		String circleCode1 = "";
		String circleName = "";
		String zoneCode = "";
		String zoneName = "";
		String cityCode = "";
		String cityName = "";
		String zsmCode = "";
		String zsmName = "";
		String zbmCode ="";
		String zbmName="";
		String tmCode = "";
		String tmName="";

		try {
			dataConn = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if (reportIdAsDetailed == Integer
					.parseInt(Utility
							.getValueFromBundle(
									Constants.MIS_PENDING_REPORT_GENERATION_DAILY_WISE,
									Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
				stmt = dataConn
						.prepareStatement(MisReportDAOInterface.QUERY_GET_PENDING_MIS_REPORT_SINGLE_DAY);
			}
			stmt.setString(1, circleCode);
			rs = stmt.executeQuery();
			
		/*	stmt.setDate(2, Utility.getSqlDateFromString(reportDate,
					"dd/MMM/yyyy"));*/
		
			// This First Row, Heading of the File.
			pendingReportList.add(new String[] {
					"CIRCLE CODE-NAME", "ZONE CODE-NAME", "CITY CODE-NAME",
					"ZBM CODE-NAME", "ZSM CODE-NAME",
					"TM CODE-NAME", "DISTRIBUTOR CODE-NAME-REGISTERED NO", "FOS CODE-NAME-REGISTERED NO",
					"RETAILER CODE-NAME-REGISTERED NO", "SUBS MSISDN","SUBS SIM","REGISTRATION COMPLETED DATE",
					"VERIFICATION COMPLETED DATE"});
			while (rs.next()) {
				
				distRegNo = rs.getString("DIST_REG_NO");
				fosRegNo = rs.getString("FOS_REG_NO");
				dealerRegNo =rs.getString("DEALER_REG_NO");
				
				
				distCode = (rs.getString("DIST_CODE") == null) ? "": rs.getString("DIST_CODE");
				distName = (rs.getString("DIST_NAME") == null) ? "": "-"+rs.getString("DIST_NAME");
				fosCode = (rs.getString("FOS_CODE") == null) ? "" :  rs.getString("FOS_CODE");
				fosName = (rs.getString("FOS_NAME") == null) ? "" : "-"+rs.getString("FOS_NAME");
				dealerCode = (rs.getString("DEALER_CODE") == null) ? "" : rs.getString("DEALER_CODE");
				dealerName = (rs.getString("DEALER_NAME") == null) ? "" : "-"+rs.getString("DEALER_NAME");
				
				distRegNo = (rs.getString("DIST_REG_NO") == null) ? "": "-"+rs.getString("DIST_REG_NO");
				fosRegNo = (rs.getString("FOS_REG_NO") == null) ? "": "-"+rs.getString("FOS_REG_NO");
				dealerRegNo = (rs.getString("DEALER_REG_NO") == null) ? "" : "-"+rs.getString("DEALER_REG_NO");
				
				circleCode1 = (rs.getString("CIRCLE_CODE") == null) ? "": rs.getString("CIRCLE_CODE");
				circleName = (rs.getString("CIRCLE_NAME") == null) ? "": "-"+rs.getString("CIRCLE_NAME");
				zoneCode = (rs.getString("ZONE_ID") == null) ? "": rs.getString("ZONE_ID");
				zoneName = (rs.getString("ZONE_NAME") == null) ? "": "-"+rs.getString("ZONE_NAME");
				cityCode = (rs.getString("CITY_ID") == null) ? "": rs.getString("CITY_ID");
				cityName =(rs.getString("CITY_NAME") == null) ? "": "-"+rs.getString("CITY_NAME");
				zbmCode = (rs.getString("ZBM_CODE") == null) ? "": rs.getString("ZBM_CODE");
				zbmName =(rs.getString("ZBM_NAME") == null) ? "": "-"+rs.getString("ZBM_NAME");
				zsmCode=(rs.getString("ZSM_CODE") == null) ? "": rs.getString("ZSM_CODE");
				zsmCode = (rs.getString("ZSM_CODE") == null) ? "": rs.getString("ZSM_CODE");
				
				tmCode =(rs.getString("TM_CODE") == null) ? "": rs.getString("TM_CODE");
				tmName=(rs.getString("TM_NAME") == null) ? "": rs.getString("TM_NAME");

				
				
				
				 
				pendingReportList.add(new String[] {
						circleCode1 + circleName,
						zoneCode + zoneName,
						cityCode + cityName,
						zbmName + zbmName,
						zbmCode + zsmName,
						tmCode + tmName,
						distCode+distName+distRegNo,
						fosCode + fosName+fosRegNo,
						dealerCode + dealerName+dealerRegNo,
						rs.getString("MSISDN"),
						rs.getString("SUBS_SIM"),
						rs.getString("REGISTRATION_COMPLETED_DATE"),
						rs.getString("VERIFICATION_COMPLETED_DATE")});

			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		} catch (Exception e) {
			logger.error("Exception occur " + e.getMessage(), e);
			throw new DAOException(e.getMessage(), e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(dataConn, stmt, rs);
			} catch (SQLException e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger
					.debug("Exiting method getMisPendingReportActCntSingleDay throws DAOException ");

		}
		return pendingReportList;
	}
	/**
	 * 
	 * @param reportType
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getMISPendingRptSchedule(String reportType)
			throws DAOException {
		logger.debug("Entering method getMISReportsSchedule throws DAOException ");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList reportsScheduleList = new ArrayList();
		ReportDTO reportDTO = null;
		String getReportDetails = "";
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
			getReportDetails = MisReportDAOInterface.FETCH_MIS_REPORT_SCHEDULE;
			
			prepareStatement = connection.prepareStatement(getReportDetails);
			prepareStatement.setString(1, reportType);
			resultSet = prepareStatement.executeQuery();
			Timestamp reportDateTime = null;
			Calendar calendar1 = Calendar.getInstance();

			while (resultSet.next()) {
				reportDTO = new ReportDTO();
				reportDTO.setReportId(resultSet.getInt("REPORT_ID"));
				reportDTO.setReportName(String.valueOf(resultSet.getString("REPORT_NAME")));
				reportDTO.setForceStart(resultSet.getString("FORCE_START"));
				reportDateTime = resultSet.getTimestamp("REPORT_START_DATE");

				// IF it is not force start than it will store the report start
				// date.
				if ((Constants.FORCE_START_PROCESS_N)
						.equalsIgnoreCase(resultSet.getString("FORCE_START"))) {
					if ((Constants.DATA_FEED_SCHEDULE_PROCESS_F)
							.equalsIgnoreCase(reportType)) {
						calendar1.setTime(new Date(Utility.getDate(Utility
								.getTimestampAsString(reportDateTime,
										"dd/MM/yyyy"), "dd/MM/yyyy")));
						calendar1.add(java.util.Calendar.DAY_OF_MONTH, 0);
						reportDateTime = new Timestamp(calendar1.getTime()
								.getTime());
					}
				} else {// IF it is force start then it will store the force
					// start date.
					reportDateTime = resultSet.getTimestamp("FORCE_START_DATE");
				}

				reportDTO.setReportDateTime(reportDateTime);
				reportDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				reportsScheduleList.add(reportDTO);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION");
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception occur " + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			
			} catch (Exception e) {
				logger.error(
						"SQLException occur while closing database resources."
								+ e.getMessage(), e);
				new DAOException(
						"SQLException occur while closing database resources.");
			}
			logger.debug("Exiting method getMISReportsSchedule DAOException");

		}
		return reportsScheduleList;
	}
	
}
