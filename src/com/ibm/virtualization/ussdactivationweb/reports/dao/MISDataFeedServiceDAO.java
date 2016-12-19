/**************************************************************************
 * File     : MISDataFeedServiceDAO.java
 * Author   : Banita
 * Created  : Nov 04, 2008
 * Modified : Nov 04, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Nov 04, 2008 	Banita	First Cut.
 * V0.2		Nov 04, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.dao;

import java.sql.BatchUpdateException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.FTAReportServiceInterface;
import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.reports.processor.MisReportProcessor;
import com.ibm.virtualization.ussdactivationweb.reports.service.FTAService;
import com.ibm.virtualization.ussdactivationweb.services.DistportalService;
import com.ibm.virtualization.ussdactivationweb.services.ProdcatService;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationMstDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.RequesterDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class is used to fetch and insert the details of users,location and data
 * feed into serives into fta database.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/

public class MISDataFeedServiceDAO {

	private static final Logger logger = Logger
	.getLogger(MISDataFeedServiceDAO.class);

	/**
	 * This method insertAllLatestBusinessUserDetails() first fetches the
	 * business user data from the distprortal on the basis of current date(from
	 * database) and the last modified date(from database) and then inserts or
	 * updates into the fta database
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */

	public void insertAllLatestBusinessUserDetails() throws DAOException {
		logger.debug("Entering insertAllLatestBusinessUserDetails() method of MISDataFeddServiceDAO");
		DistportalService distportalService = new DistportalService();
		String modifiedFromDate = "";
		String modifiedtoDate = "";
		RequesterDTO requesterDTO = null;
		FTAService ftaservice = new FTAService();
		Connection connection = null;
		PreparedStatement inserBizUserPrepStmt = null;
		PreparedStatement updateBizUserPrepStmt = null;
		try {
			modifiedFromDate = ftaservice
			.getLastModifiedDate(Constants.BizUser);
			modifiedtoDate = ftaservice.getCurrentDate();
			Timestamp newCurrentDate = Utility
			.stringToTimestamp(modifiedtoDate);
			Timestamp newModifyDate = Utility
			.stringToTimestamp(modifiedFromDate);

			/* for business user entries */
			int noofpages = 0;
			int rrp = 10;
			int lb = Integer.parseInt(Utility.getValueFromBundle(
					Constants.LOWER_BOUND_REPORT,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			int ub = Integer.parseInt(Utility.getValueFromBundle(
					Constants.UPPER_BOUND_REPORT,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			noofpages = distportalService.getLatestBusinessUserCount(
					modifiedFromDate, modifiedtoDate);
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			connection.setAutoCommit(false);
			inserBizUserPrepStmt = connection
			.prepareStatement(FTAReportServiceInterface.INSERT_BIZ_USER_DETAILS);
			updateBizUserPrepStmt = connection
			.prepareStatement(FTAReportServiceInterface.UPDATE_BIZ_USER_DETAILS);
			if (noofpages == 0) {
				logger
				.debug("There is no record to insert business user details");
			} else {
				for (int i = 0; i < noofpages; i++) {
					requesterDTO = distportalService.getBusinessUserDetails(
							modifiedFromDate, modifiedtoDate, lb, ub);
					if (requesterDTO.getErrorCode() != null) {
						logger
						.debug("There is no record to insert business user details");
					} else {

						BusinessUserDTO[] businessUserDTOArray = null;
						businessUserDTOArray = requesterDTO
						.getBusinessUserArray();
						BusinessUserDTO businessUserDTO2 = null;
						for (int j = 0; j < businessUserDTOArray.length; j++) {
							businessUserDTO2 = new BusinessUserDTO();
							if (businessUserDTOArray[j] == null) {
								break;
							} else {
								businessUserDTO2 = businessUserDTOArray[j];
							}
							if (businessUserDTO2.getUpdatedDt() == null) { 
								// insert business user details when there is no update date
								inserBizUserPrepStmt = insertBizDetailsIntoBatch(
										inserBizUserPrepStmt, businessUserDTO2,
										modifiedtoDate);
								inserBizUserPrepStmt.addBatch();
							} else if (((Utility.stringToTimestamp(businessUserDTO2.getCreatedDt()).before(newCurrentDate)) && 
											(Utility.stringToTimestamp(businessUserDTO2.getCreatedDt()).after(newModifyDate)))
															|| (Utility.stringToTimestamp(businessUserDTO2.getCreatedDt())
																			.equals(newCurrentDate))
																			|| (Utility.stringToTimestamp(businessUserDTO2.getCreatedDt())
																							.equals(newModifyDate))) {

								// insert business user details when there is created date is between defined range
								inserBizUserPrepStmt = insertBizDetailsIntoBatch(
										inserBizUserPrepStmt, businessUserDTO2,
										modifiedtoDate);
								inserBizUserPrepStmt.addBatch();
							} else {
								// update business user details
								updateBizUserPrepStmt = insertBizDetailsIntoBatch(
										updateBizUserPrepStmt,
										businessUserDTO2, modifiedtoDate);
								updateBizUserPrepStmt.setInt(13,
										businessUserDTO2.getUserId());
								updateBizUserPrepStmt.addBatch();
							}
						}
					}
					if (inserBizUserPrepStmt != null) {
						inserBizUserPrepStmt.executeBatch();
					}
					if (updateBizUserPrepStmt != null) {
						updateBizUserPrepStmt.executeBatch();
					}
					lb = lb + rrp;
					ub = ub + rrp;
				}
				connection.commit();
			}
		} catch (BatchUpdateException bExp) {
			logger.error("SQL exception encountered: "
					+ bExp.getNextException(), bExp);
			throw new DAOException(bExp.getMessage(), bExp);
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				connection.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(null, updateBizUserPrepStmt);
				DBConnectionUtil.closeDBResources(connection,
						inserBizUserPrepStmt);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting insertAllLatestBusinessUserDetails() of MISDataFEEDServiceDAO");
	}

	/**
	 * This method insertAllLatestLocationDetails() first fetches the circle
	 * data from the prodcat on the basis of current date(from database) and the
	 * last modified date(from database) and then inserts or updates into the
	 * fta database and similarly function for hub and other locations
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public void insertAllLatestLocationDetails() throws DAOException {

		logger.debug("Entering insertAllLatestLocationDetails() method of MISDataFeddServiceDAO");
		ProdcatService prodService = new ProdcatService();
		String modifiedfromDate = "";
		String modifiedtoDate = "";
		LocationMstDTO locationMstrDTO = null;
		FTAService ftaservice = new FTAService();
		Connection connection = null;
		PreparedStatement insertLocPrepStmt = null;
		PreparedStatement updateLocPrepStmt = null;

		try {
			modifiedfromDate = ftaservice
			.getLastModifiedDate(Constants.Location);
			modifiedtoDate = ftaservice.getCurrentDate();
			Timestamp newCurrentDate = Utility
			.stringToTimestamp(modifiedtoDate);
			Timestamp newModifyDate = Utility
			.stringToTimestamp(modifiedfromDate);
			
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			connection.setAutoCommit(false);
			insertLocPrepStmt = connection
			.prepareStatement(FTAReportServiceInterface.INSERT_LOC_DETAILS);
			updateLocPrepStmt = connection
			.prepareStatement(FTAReportServiceInterface.UPDATE_LOC_DETAILS);
			
			/* for hub entries */
			locationMstrDTO = prodService.getAllHubs(modifiedfromDate,
					modifiedtoDate);
			if (locationMstrDTO.getErrorCode() != null) {
				logger
				.debug("There is no record to insert hub details");
			} else {
				LocationDTO[] locationdtoArray = null;
				locationdtoArray = locationMstrDTO.getLocationArray();
				for (int k = 0; k < locationdtoArray.length; k++) {
					LocationDTO location = new LocationDTO();
					if (locationdtoArray[k] == null) {
						break;
					} else {
						location = locationdtoArray[k];
					}

					if (location.getUpdatedDt() == null) {
						// insert hub details when there is no modified date
						insertLocPrepStmt = insertLocDetailsIntoBatch(
								insertLocPrepStmt, location, modifiedtoDate);
						insertLocPrepStmt.addBatch();
					} else if (((Utility.stringToTimestamp(location
							.getCreatedDt()).before(newCurrentDate)) && (Utility
									.stringToTimestamp(location.getCreatedDt())
									.after(newModifyDate)))
									|| (Utility.stringToTimestamp(location
											.getCreatedDt()).equals(newCurrentDate))
											|| (Utility.stringToTimestamp(location
													.getCreatedDt()).equals(newModifyDate))) {

						// insert hub details when the created date is in the
						// range
						insertLocPrepStmt = insertLocDetailsIntoBatch(
								insertLocPrepStmt, location, modifiedtoDate);
						insertLocPrepStmt.addBatch();
					} else {

						// insert hub details when the details has to be updated
						updateLocPrepStmt = insertLocDetailsIntoBatch(
								updateLocPrepStmt, location, modifiedtoDate);
						updateLocPrepStmt.setString(8, location.getLocDataId());
						updateLocPrepStmt.addBatch();
					}
				}
			}
			if (insertLocPrepStmt != null) {
				insertLocPrepStmt.executeBatch();
			}
			if (updateLocPrepStmt != null) {
				updateLocPrepStmt.executeBatch();
			}
			
			
			/* for circle data entry */
			locationMstrDTO = prodService.getAllCircles(modifiedfromDate,
					modifiedtoDate);
			if (locationMstrDTO.getErrorCode() != null) {
				logger
				.debug("There is no record to insert circle details");
			} else {
				LocationDTO[] locationdtoArray = null;
				locationdtoArray = locationMstrDTO.getLocationArray();
				for (int k = 0; k < locationdtoArray.length; k++) {
					LocationDTO location = new LocationDTO();
					if (locationdtoArray[k] == null) {
						break;
					} else {
						location = locationdtoArray[k];
					}

					if (location.getUpdatedDt() == null) {
						// insert circle details when there is no modified date
						insertLocPrepStmt = insertLocDetailsIntoBatch(
								insertLocPrepStmt, location, modifiedtoDate);
						insertLocPrepStmt.addBatch();
					} else if (((Utility.stringToTimestamp(location
							.getCreatedDt()).before(newCurrentDate)) && (Utility
									.stringToTimestamp(location.getCreatedDt())
									.after(newModifyDate)))
									|| (Utility.stringToTimestamp(location
											.getCreatedDt()).equals(newCurrentDate))
											|| (Utility.stringToTimestamp(location
													.getCreatedDt()).equals(newModifyDate))) {

						// insert circle details when the created date is in
						// range
						insertLocPrepStmt = insertLocDetailsIntoBatch(
								insertLocPrepStmt, location, modifiedtoDate);
						insertLocPrepStmt.addBatch();
					} else {

						// insert circle details when the details has to be
						// updated
						updateLocPrepStmt = insertLocDetailsIntoBatch(
								updateLocPrepStmt, location, modifiedtoDate);

						updateLocPrepStmt.setString(8, location.getLocDataId());
						updateLocPrepStmt.addBatch();
					}
				}
			}
			if (insertLocPrepStmt != null) {
				insertLocPrepStmt.executeBatch();
			}
			if (updateLocPrepStmt != null) {
				updateLocPrepStmt.executeBatch();
			}
			/* for other location entries */

			int noofpages = 0;
			int rrp = 10;
			int lb = Integer.parseInt(Utility.getValueFromBundle(
					Constants.LOWER_BOUND_REPORT,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			int ub = Integer.parseInt(Utility.getValueFromBundle(
					Constants.UPPER_BOUND_REPORT,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			noofpages = prodService.getLatestLocationCount(modifiedfromDate,
					modifiedtoDate);
			if (noofpages == 0) {
				logger.debug("There is no location details to be entered");
			} else {
				for (int i = 0; i < noofpages; i++) {
					locationMstrDTO = prodService.getLatestLocationDetails(
							modifiedfromDate, modifiedtoDate, lb, ub);
					if (locationMstrDTO.getErrorCode() != null) {
						logger
						.debug("There is no location details to be entered");
					} else {
						LocationDTO[] locationdtoArray = null;
						locationdtoArray = locationMstrDTO.getLocationArray();
						for (int j = 0; j < locationdtoArray.length; j++) {
							LocationDTO location = new LocationDTO();
							if (locationdtoArray[j] == null) {
								break;
							} else {
								location = locationdtoArray[j];
							}
							if (location.getUpdatedDt() == null) {
								// insert location details when there is no
								// modified date
								insertLocPrepStmt = insertLocDetailsIntoBatch(
										insertLocPrepStmt, location,
										modifiedtoDate);
								insertLocPrepStmt.addBatch();
							} else if (((Utility.stringToTimestamp(location
									.getCreatedDt()).before(newCurrentDate)) && (Utility
											.stringToTimestamp(location.getCreatedDt())
											.after(newModifyDate)))
											|| (Utility.stringToTimestamp(location
													.getCreatedDt())
													.equals(newCurrentDate))
													|| (Utility.stringToTimestamp(location
															.getCreatedDt())
															.equals(newModifyDate))) {
								// insert loc details when the created data is
								// in the range
								insertLocPrepStmt = insertLocDetailsIntoBatch(
										insertLocPrepStmt, location,
										modifiedtoDate);
								insertLocPrepStmt.addBatch();
							} else {
								// insert loc details when the details has to be
								// updated
								updateLocPrepStmt = insertLocDetailsIntoBatch(
										updateLocPrepStmt, location,
										modifiedtoDate);
								updateLocPrepStmt.setString(8, location
										.getLocDataId());
								updateLocPrepStmt.addBatch();
							}
						}
					}
					if (insertLocPrepStmt != null) {
						insertLocPrepStmt.executeBatch();
					}
					if (updateLocPrepStmt != null) {
						updateLocPrepStmt.executeBatch();
					}
					lb = lb + rrp;
					ub = ub + rrp;
				}
				connection.commit();
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				connection.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(null, updateLocPrepStmt);
				DBConnectionUtil
				.closeDBResources(connection, insertLocPrepStmt);

			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting insertAllLatestLocationDetails() from MISDataFeddServiceDAO");
	}

	
	/**
	 * This method generateReportDetailAndSummary()calls the varioue methods for data fetching and entering
	 * into fta,then refresh MQT tables and then inserts or updates into Report Detail or Report Summary 
	 * 
	 * @param reportDTO: that contains all the information about report
	 * @param currentDate:the basis on which data is inserted into report
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public void generateReportDetailAndSummary(ReportDTO reportDTO, 
			String currentDate) throws DAOException {
		logger.debug("Entering into generateReportDetailAndSummary()method of MISDataFeddServiceDAO");
		String dataFeedStartDate = null;
		Connection connection = null;
		PreparedStatement refreshBuisUserMQT = null;
		PreparedStatement refreshLocationMQT = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		String sevenDaysBfrCurrDate = Utility.getDateAsString(calendar
				.getTime(), "dd/MM/yyyy");
		FTAService ftaService = new FTAService();
		ReportDaoImpl reportDaoImpl = null;
		try {
			reportDaoImpl = new ReportDaoImpl();
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
			//Update the re-run flag to 'N'
			reportDaoImpl.updateParametersForReportSchedule(reportDTO.getReportId(), "S");

			dataFeedStartDate = Utility.getTimestampAsString(reportDTO.getReportDateTime(), "dd/MM/yyyy");
			/* data fetch and feed for all locations */
			insertAllLatestLocationDetails();
			ftaService.updateLastModifiedDate(connection, Constants.Location,
					currentDate);
			/* data fetch and feed for business users */
			insertAllLatestBusinessUserDetails();
			ftaService.updateLastModifiedDate(connection, Constants.BizUser,
					currentDate);
			/* refresh MQT tables */
			refreshLocationMQT = connection
			.prepareStatement(FTAReportServiceInterface.REFRESH_LOCATION);
			refreshLocationMQT.executeUpdate();
			refreshBuisUserMQT = connection
			.prepareStatement(FTAReportServiceInterface.REFRESH_BIZ_USER);
			refreshBuisUserMQT.executeUpdate();
			long date1 = Utility.getDate(currentDate, "dd-MM-yyyy-HH-mm-ss");
			Timestamp timeStamp = new Timestamp(date1);
			String newCurrentDate = Utility.getTimestampAsString(timeStamp,
			"dd/MM/yyyy");
			Date currentDateConverted = Utility.stringToDate(newCurrentDate);

			Date dateFeedConverted = Utility.stringToDate(dataFeedStartDate);

			Date sevenDaysBefore = Utility.stringToDate(sevenDaysBfrCurrDate);

			/* data feed into reports */
			if (((dateFeedConverted).before(currentDateConverted))
					&& ((dateFeedConverted).after(sevenDaysBefore))) {
				insertIntoReportDetail(connection, dataFeedStartDate);
				insertIntoReportSummary(connection, dataFeedStartDate);
			} else {
				logger.debug("There is no data for report generation");
			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			//	Update the re-run flag to 'Y'
			MisReportProcessor.endReportProcess(reportDTO.getForceStart(), reportDTO.getReportId(), reportDaoImpl);
			try {
				DBConnectionUtil.closeDBResources(null, refreshLocationMQT);
				DBConnectionUtil.closeDBResources(connection,
						refreshBuisUserMQT);

			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		
		logger.debug("Exiting from generateReportDetailAndSummary()method of MISDataFeddServiceDAO");
	}

	
	
	/**
	 * This method dataFeedForPendingReport()calls the varioue methods for data fetching and entering
	 * into fta,then refresh MQT tables and then inserts or updates into Report Detail or Report Summary 
	 * 
	 * @param reportDTO: that contains all the information about report
	 * @param currentDate:the basis on which data is inserted into report
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public void dataFeedForPendingReport(String currentDate) throws DAOException {
		logger.debug("Entering into generatePendingReport()method of MISDataFeddServiceDAO");
		
		Connection connection = null;
		PreparedStatement refreshBuisUserMQT = null;
		PreparedStatement refreshLocationMQT = null;
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		FTAService ftaService = new FTAService();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
				/*** data fetch and feed for all locations ***/
				insertAllLatestLocationDetails();
				ftaService.updateLastModifiedDate(connection, Constants.Location,
						currentDate);
				/*** data fetch and feed for business users ***/
				insertAllLatestBusinessUserDetails();
				ftaService.updateLastModifiedDate(connection, Constants.BizUser,
						currentDate);
				/*** refresh MQT tables ***/
				refreshLocationMQT = connection
				.prepareStatement(FTAReportServiceInterface.REFRESH_LOCATION);
				refreshLocationMQT.executeUpdate();
				refreshBuisUserMQT = connection
				.prepareStatement(FTAReportServiceInterface.REFRESH_BIZ_USER);
				refreshBuisUserMQT.executeUpdate();
		
				long date1 = Utility.getDate(currentDate, "dd-MM-yyyy-HH-mm-ss");
				Timestamp timeStamp = new Timestamp(date1);
				String newCurrentDate = Utility.getTimestampAsString(timeStamp,
				"dd/MM/yyyy");
			   insertIntoReportDetail(connection, newCurrentDate);
			
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			
			try {
				DBConnectionUtil.closeDBResources(null, refreshLocationMQT);
				DBConnectionUtil.closeDBResources(connection,
						refreshBuisUserMQT);

			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		
		logger.debug("Exiting from generatePendingReport()method of MISDataFeddServiceDAO");
	}

	
	
	
	/**
	 * This method insertBizDetailsIntoBatch() is being called for inserting or updating data
	 * 
	 * @param bizUserPrepStmt
	 * @param businessUserDTO2: from where all values are set
	 * @param modifiedtoDate : this date has to be set into database
	 * 
	 * @return PreparedStatement
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public PreparedStatement insertBizDetailsIntoBatch(
			PreparedStatement bizUserPrepStmt,
			BusinessUserDTO businessUserDTO2, String modifiedtoDate)
	throws DAOException {
		logger.debug("Entering method insertBizDetailsIntoBatch() of MISDataFeddServiceDAO");
		try {
			bizUserPrepStmt.setInt(1, businessUserDTO2.getUserId());
			bizUserPrepStmt.setString(2, businessUserDTO2.getCode());
			bizUserPrepStmt.setString(3, businessUserDTO2.getName());
			bizUserPrepStmt.setInt(4, businessUserDTO2.getTypeOfUserId());
			bizUserPrepStmt.setInt(5, businessUserDTO2.getParentId());
			bizUserPrepStmt.setInt(6, businessUserDTO2.getLocId());
			bizUserPrepStmt.setString(7, businessUserDTO2.getBaseLoc());
			bizUserPrepStmt.setString(8, businessUserDTO2.getCircleCode());
			bizUserPrepStmt.setString(9, businessUserDTO2.getStatus());
			bizUserPrepStmt.setString(10, businessUserDTO2.getRegNumber());

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String modifiedDate = new String(modifiedtoDate);
			Date dt = sdf.parse(modifiedDate);
			Timestamp timestamp = new Timestamp(dt.getTime());
			bizUserPrepStmt.setTimestamp(11, timestamp);
			bizUserPrepStmt
			.setString(12, businessUserDTO2.getTypeOfUserValue());

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		}
		logger.debug("Exiting method insertBizDetailsIntoBatch() of MISDataFeddServiceDAO");
		return bizUserPrepStmt;
	}

	
	/**
	 * This method insertLocDetailsIntoBatch() is being called for inserting or updating data
	 * 
	 * @param locPrepStmt
	 * @param location: from where all values are set
	 * @param modifiedtoDate : this date has to be set into database
	 * 
	 * @return PreparedStatement
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public PreparedStatement insertLocDetailsIntoBatch(
			PreparedStatement locPrepStmt, LocationDTO location,
			String modifiedtoDate) throws DAOException {
		logger.debug("Entering method insertLocDetailsIntoBatch() of MISDataFeddServiceDAO");
		try {
			locPrepStmt.setString(1, location.getLocDataId());
			locPrepStmt.setString(2, location.getLocationDataName());
			locPrepStmt.setInt(3, location.getStatus());
			locPrepStmt.setInt(4, location.getLocMstrId());
			locPrepStmt.setString(5, location.getParentId());

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String modifiedDate = new String(modifiedtoDate);
			Date dt = sdf.parse(modifiedDate);
			Timestamp timestamp = new Timestamp(dt.getTime());
			locPrepStmt.setTimestamp(6, timestamp);

			locPrepStmt.setString(7, location.getLocationName());
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		logger.debug("Exiting method insertLocDetailsIntoBatch() of MISDataFeddServiceDAO");
		return locPrepStmt;
	}

	
	/**
	 * This method insertIntoReportDetail() is being called for inserting or updating data into report details
	 * 
	 * @param connection
	 * @param modifiedfromDate : filter to fetch the data from the database
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public void insertIntoReportDetail(Connection connection,
			String modifiedfromDate) throws DAOException {
		logger.debug(" Entering method insertIntoReportDetail() -- MISDataFeddServiceDAO..");
		CallableStatement cs = null;
		String procedureStatus = null;
		String procedureMsg = null;
		java.sql.Date newModifyDate = USSDCommonUtility.getSqlDateFromString(
				modifiedfromDate, "dd/MM/yyyy");
		try {
			cs = connection
			.prepareCall(FTAReportServiceInterface.GENERATE_REPORT_DETAIL);
			cs.setDate(1, newModifyDate);
			cs.registerOutParameter(2, Types.VARCHAR);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.execute();
			procedureStatus = cs.getString(2);
			procedureMsg = cs.getString(3);
			if (!"11111".equalsIgnoreCase(procedureStatus)) {
				logger.debug("Procedure failed reason code - "
						+ procedureStatus + ",Message =" + procedureMsg);
				throw new DAOException("Status =" + procedureStatus
						+ ", Message=" + procedureMsg);
			}
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				cs.close();
			} catch (SQLException sqe) {
				logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
				throw new DAOException(sqe.getMessage(), sqe);
			}
		}
		logger.debug(" Exiting method insertIntoReportDetail() -- MISDataFeddServiceDAO.. ");

	}

	/**
	 * This method insertIntoReportSummary() is being called for inserting or updating data into report summary
	 * 
	 * @param connection
	 * @param modifiedfromDate : filter to fetch the data from the database
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	
	public void insertIntoReportSummary(Connection connection,
			String modifiedfromDate) throws DAOException {
		logger
		.debug(" Entering method insertIntoReportSummary() -- MISDataFeddServiceDAO..");
		CallableStatement cs = null;
		String procedureStatus = null;
		String procedureMsg = null;
		java.sql.Date newModifyDate = USSDCommonUtility.getSqlDateFromString(
				modifiedfromDate, "dd/MM/yyyy");
		try {
			cs = connection
			.prepareCall(FTAReportServiceInterface.GENERATE_REPORT_SUMMARY);
			cs.setString(1, SQLConstants.FTA_SCHEMA);
			cs.setDate(2, newModifyDate);
			cs.registerOutParameter(3, Types.VARCHAR);
			cs.registerOutParameter(4, Types.VARCHAR);
			cs.execute();
			procedureStatus = cs.getString(3);
			procedureMsg = cs.getString(4);
			if (!"11111".equalsIgnoreCase(procedureStatus)) {
				logger.debug("Procedure failed reason code - "
						+ procedureStatus + ",Message =" + procedureMsg);
				throw new DAOException("Status =" + procedureStatus
						+ ", Message=" + procedureMsg);
			}
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				cs.close();
			} catch (SQLException sqe) {
				logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
				throw new DAOException(sqe.getMessage(), sqe);

			}
		}
		logger
		.debug(" Exiting method insertIntoReportSummary() -- MISDataFeddServiceDAO.. ");
	}

}
