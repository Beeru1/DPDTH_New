/**************************************************************************
 * File     : BulkSubscriberDAO.java
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
package com.ibm.virtualization.ussdactivationweb.dao;

import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BulkSubscriberInterface;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BulkUploadInterface;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.VSubscriberMstrDao;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.CSVReader;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkSubscriberDAO {

	private static final Logger logger = Logger
			.getLogger(BulkSubscriberDAO.class);

	/**
	 * This method getScheduledFiles() gets The files which are to be uploaded
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
/*
	public ArrayList getScheduledFilesforSubscriber() throws DAOException {
		logger
				.debug("Entering  BulkBizUserAssoDAO : getScheduledFilesforSubscriber().");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList files = new ArrayList();
		BulkSubscriberDTO subscriberDto = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			String getReportDetails = BulkSubscriberInterface.FETCH_SCHEDULE_FILES;

			prepareStatement = connection.prepareStatement(getReportDetails);
			prepareStatement.setString(1, Constants.FILE_STATUS_SAVED);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				subscriberDto = new BulkSubscriberDTO();
				subscriberDto.setFileId(resultSet.getInt("FILE_ID"));
				subscriberDto.setFileName(resultSet.getString("FILE_NAME"));
				subscriberDto.setRelatedFileId(resultSet
						.getInt("RELATED_FILE_ID"));
				subscriberDto.setStatus(resultSet.getString("STATUS"));
				subscriberDto.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				subscriberDto.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				subscriberDto.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				subscriberDto.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				subscriberDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				subscriberDto.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				subscriberDto.setFileType(resultSet.getInt("FILE_TYPE"));
				subscriberDto.setFailedMsg(resultSet.getString("FAILED_MSG"));
				files.add(subscriberDto);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception", ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
						.error("Exception occured when trying to close database resources");
			}
			logger
					.debug("Exiting BulkBizUserAssoDAO : getScheduledFilesforSubscriber().");

		}
		return files;
	}

*/
	/**
	 * This method update the file status as UPLOADING befor frocess the uploaded file.
	 * @param fileId is file which status to be change as UPLOADING
	 * @param connection database connection reference
	 * @return true in case of status updated successfully else return false
	 * @exception DAOException,when
	 *     there is some problem while locating an object in
	 *                database.
	 */

	public String updateFilesforSubscriber(int fileId, Connection connection)
			throws DAOException {
		logger
				.debug("Entering  BulkBizUserAssoDAO : updateFilesforSubscriber()");

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String statusUpdated = Constants.STATUS_UPDATED;
		try {

			String updateFiles = BulkSubscriberInterface.UPDATE_SCHEDULE_FILES;

			prepareStatement = connection.prepareStatement(updateFiles);
			prepareStatement.setString(1, Constants.FILE_STATUS_UPLOADING);
			prepareStatement.setInt(2, fileId);
			prepareStatement.setString(3, Constants.FILE_STATUS_SAVED);
			int i = prepareStatement.executeUpdate();
			if (i < 0 || i == 0) {
				statusUpdated = Constants.STATUS_NOT_UPDATED;
				return statusUpdated;
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			statusUpdated = Constants.STATUS_NOT_UPDATED;
			return statusUpdated;
		} catch (Exception ex) {
			logger.error("Exception", ex);
			statusUpdated = Constants.STATUS_NOT_UPDATED;
			return statusUpdated;
		} finally {
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}

			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
			logger
					.debug("Exiting BulkBizUserAssoDAO : updateFilesforSubscriber()");
		}
		return statusUpdated;
	}

	/**
	 *  This method returns the list of active service class id in the respective circle.
	 * @param circleCode 
	 * @param connection database connection reference
	 * @return list of active service class id in respective circle
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public ArrayList activeServiceIdInCircle(String circleCode,
			Connection connection) throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : activeServiceIdInCircle()");
		ArrayList strServiceClassArrayList = new ArrayList();
		PreparedStatement serviceCheckInCircle = null;
		ResultSet resultSet = null;
		try {
			serviceCheckInCircle = connection
					.prepareStatement(BulkUploadInterface.IS_SERVICE_EXIST_CIRCLE);
			serviceCheckInCircle.setString(1, circleCode);
			serviceCheckInCircle.setInt(2, Constants.Active);
			resultSet = serviceCheckInCircle.executeQuery();
			while (resultSet.next()) {
				strServiceClassArrayList.add(resultSet
						.getString("SERVICECLASS_ID"));
			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception sqe) {
			logger.error("EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} finally {
			try {
				if (serviceCheckInCircle != null) {
					serviceCheckInCircle.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}

			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : activeServiceIdInCircle()");
		return strServiceClassArrayList;

	}


	/**
	 * Check subscriber already in V_SUBSCRIBER_MSTR table or not
	 * @param nextLine complete row for the subscriber information(MSISDN,SIM,SERVICE CLASS ID)
	 * @param lastSeven last seven digits of complete sim
	 * @param circleCode 
	 * @param connection database connection reference
	 * @return true if subscriber already exist else return false.
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public boolean checkSubscriber(String nextLine, String lastSeven,
			String circleCode, Connection connection) throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : checkSubscriber()");
		ResultSet resultSet = null;
		boolean isSubscriberExist = false;
		PreparedStatement checkPsmt = null;
		try {
			checkPsmt = connection
					.prepareStatement(BulkUploadInterface.CHK_SUBSCRIBER);

			checkPsmt.setString(1, nextLine);
			checkPsmt.setString(2, lastSeven);
			checkPsmt.setString(3, nextLine);
			checkPsmt.setString(4, lastSeven);
			checkPsmt.setString(5, Constants.ActiveState);
			checkPsmt.setString(6, circleCode);
			resultSet = checkPsmt.executeQuery();
			if (resultSet.next()) {
				isSubscriberExist = true;
			}

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception sqe) {
			logger.error("EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} finally {
			try {
				if (checkPsmt != null) {
					checkPsmt.close();
				}
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : checkSubscriber()");
		return isSubscriberExist;
	}

	/*
	
     public boolean insertBulkSubscriber(ArrayList validSub,Connection connection) throws DAOException {
		
    	logger.debug("Entering BulkBizUserAssoDAO : insertBulkSubscriber()");
		PreparedStatement prepareStatement = null;
		CallableStatement callStatement = null;
		String procedureStatus = null;
		String procedureMsg = null;
		boolean resultFlag = false;
		try {
			int userId = 0;
			//connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			callStatement = connection.prepareCall(VSubscriberMstrDao.PROC_CREATE_SUBSCRIBER);
			
			  for(int index =0, size = validSub.size(); index < size ; index++ ){
            	BulkSubscriberDTO bulkSubsDTO = (BulkSubscriberDTO)validSub.get(index);
            	if(index == 0){
            		userId = getUserIdByLogiId(connection,bulkSubsDTO.getUploadedBy());
            	}   
            	callStatement.setString(1, SQLConstants.FTA_SCHEMA);
				callStatement.setString(2, bulkSubsDTO.getMsisdn());
				callStatement.setString(3, bulkSubsDTO.getImsi());
				callStatement.setString(4, bulkSubsDTO.getCompleteSim());
				callStatement.setString(5, bulkSubsDTO.getSim());
				callStatement.setString(6, bulkSubsDTO.getCircleCode());
				callStatement.setInt(7, Integer.parseInt(bulkSubsDTO.getServiceClassId()));
				callStatement.setString(8, null);
				callStatement.setInt(9, userId);
				callStatement.setString(10, null);
				
				callStatement.registerOutParameter(11, Types.VARCHAR);
				callStatement.registerOutParameter(12, Types.VARCHAR);
				callStatement.execute();
				procedureStatus = callStatement.getString(11);
				procedureMsg = callStatement.getString(12);
				if(!(Constants.PROC_STATUC_11111).equalsIgnoreCase(procedureStatus)) {
					logger.debug("Procedure failed reason code - " +procedureStatus +",Message =" + procedureMsg );
					throw new DAOException("Status =" + procedureStatus + ", Message=" +procedureMsg);
				} else {
					
					resultFlag = true;
				}
				
			logger.debug("Record successfull inserted on table:V_SUBSCRIBER_MSTR.");
			
		}
			  
		}catch (SQLException sqe) {
			System.out.println(sqe.getMessage());
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception sqe) {
			logger.error("EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} finally {
			try {
				if (prepareStatement != null) {
					prepareStatement.close();
				}

			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : insertBulkSubscriber()");
		return resultFlag;
	}
*/	
	/** 
	 * This method is responsible for insert The uploding file data into
	 * database and return the file id to the caller of this method
	 *
	 * @param fileName
	 *            :String name of the file to be uploaded
	 * @param circleCode :
	 *            String circle code 
	 * @param userId
	 *            :int logged in user id
	 * @return  file id of the uploded file 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
/*
	public long fileInfoForSubscriber(String fileName,
			BulkSubscriberDTO bulkSubscriberDTO, String fileStatus,
			String failedmsg, int filetype) throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : fileInfoForSubscriber()");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement pstmt1 = null;
		ResultSet resultSet = null;
		int fileid = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			pstmt1 = connection
					.prepareStatement(BulkSubscriberInterface.NEXT_VALUE_Subscriber);
			resultSet = pstmt1.executeQuery();
			if (resultSet.next()) {
				fileid = resultSet.getInt(1);
			}
			prepareStatement = connection
					.prepareStatement(BulkSubscriberInterface.FILE_ENTRY_Subscriber);
			prepareStatement.setInt(1, fileid);
			prepareStatement.setString(2, fileName);
			prepareStatement.setString(3, bulkSubscriberDTO.getCircleCode());
			prepareStatement.setString(4, fileStatus);
			prepareStatement.setString(5, bulkSubscriberDTO
					.getOriginalFileName());
			prepareStatement.setInt(6, 0);
			prepareStatement.setInt(7, 0);
			prepareStatement.setInt(8, filetype);
			//prepareStatement.setString(9, String.valueOf(bulkSubscriberDTO.getIntUploadedBy()));
			prepareStatement.setString(9, bulkSubscriberDTO.getUploadedBy());
			prepareStatement.setInt(10, bulkSubscriberDTO.getFileId());
			prepareStatement.setString(11, failedmsg);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(
					"Exection Occured During call of fileInfoForBusinessUser() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : fileInfoForSubscriber()");
		return fileid;
	}
*/

	/** 
	 * Updating the file info after it has been read
	 * @param relatedFileid ralated file id of the orginal file
	 * @param fileId orginal file id
	 * @param failure file status 
	 * @param success file status
	 * @param connection database connection reference
	 * @exception DAOException,when
	 * there is some problem while locating an object in
	 * database.
	 */

	public void updatefileInfoAfterUploading(int relatedFileid, int fileId,
			int failure, int success, Connection connection)
			throws DAOException {

		logger
				.debug("Entering BulkBizUserAssoDAO : updatefileInfoAfterUploading()");
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection
					.prepareStatement(BulkSubscriberInterface.UPDATE_FILE_ENTRY_Subscriber);
			prepareStatement.setInt(1, success);
			prepareStatement.setInt(2, failure);
			prepareStatement.setInt(3, relatedFileid);
			prepareStatement.setString(4, Constants.FILE_STATUS_FILEUPLOADED);
			prepareStatement.setInt(5, fileId);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(
					"Exection Occured During call of fileInfoForBusinessUser() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(null, prepareStatement);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger
				.debug("Exiting BulkBizUserAssoDAO : updatefileInfoAfterUploading()");
	}

	/**
	 * This method returns the list of subscriber files is the respective circle. 
	 * @param circleCode 
	 * @param fileStatus status of the file
	 * @param intLb lower bound for pagination
	 * @param intUb upper bound for pagination
	 * @return list of subscriber files is the respective circle. 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
/*
	public List getfilesListForSubscriber(String circleCode, String fileStatus,
			int intLb, int intUb) throws DAOException {

		logger
				.debug("Entering BulkBizUserAssoDAO : getfilesListForSubscriber()");
		ArrayList fileList = new ArrayList();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		BulkSubscriberDTO subscriberDTO = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			String getFileDetails = BulkSubscriberInterface.FILES_LIST_FOR_SUBSCRIBER;

			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				;
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, String.valueOf(intUb));
				prepareStatement.setString(4, String.valueOf(intLb + 1));

			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_SAVED);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));

			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_UPLOADING);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3,
						Constants.FILE_STATUS_FILEUPLOADED);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_FAILED);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));
			}

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				subscriberDTO = new BulkSubscriberDTO();
				subscriberDTO.setFileId(resultSet.getInt("FILE_ID"));
				subscriberDTO.setFileName(resultSet.getString("FILE_NAME"));
				subscriberDTO.setRelatedFileId(resultSet
						.getInt("RELATED_FILE_ID"));
				subscriberDTO.setStatus(resultSet.getString("STATUS"));
				subscriberDTO.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				subscriberDTO.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				subscriberDTO.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				subscriberDTO.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				subscriberDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				subscriberDTO.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				subscriberDTO.setFileType(resultSet.getInt("FILE_TYPE"));
				subscriberDTO.setFailedFile(resultSet.getString("FAILED_FILE"));
				subscriberDTO.setRownum(resultSet.getString("RNUM"));
				fileList.add(subscriberDTO);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception", ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			logger.debug("Exiting method getScheduledFiles DAOException");

		}
		logger
				.debug("Exiting BulkBizUserAssoDAO : getfilesListForSubscriber()");
		return fileList;
	}
*/

	/**
	 * This method is called to count the circles.
	 * 
	 * @return object of int:total number of circles.
	 * 
	 *@exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public int countFilesListForSubscriber(String circleCode, String fileStatus)
			throws DAOException {
		logger
				.debug("Entering BulkBizUserAssoDAO : countFilesListForSubscriber()");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;

		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			String getFileDetails = BulkSubscriberInterface.COUNT_FILES_LIST_FOR_SUBSCRIBER;
			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);

			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_SAVED);
			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_UPLOADING);
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3,
						Constants.FILE_STATUS_FILEUPLOADED);
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement
						.setString(2, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(3, Constants.FILE_STATUS_FAILED);
			}
			ResultSet countFiles = prepareStatement.executeQuery();
			if (countFiles.next()) {
				noofRow = countFiles.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new DAOException("SQLException: " + sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new DAOException("Exception: " + ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countFilesListForSubscriber() of class DAOException.",
						e);
			}
		}
		logger
				.debug("Exiting BulkBizUserAssoDAO : countFilesListForSubscriber()");
		return noofPages;
	}

	/** 
	 * Updating File Status to Failed when whole file failed to upload
	 * @param fileId  
	 * @param connection database connection reference
	 * @return true if status updated else return false.
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String fileFailedToUpload(int fileId, Connection connection)
			throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : fileFailedToUpload()");
		String Updated = Constants.FALSE;
		PreparedStatement psmt = null;
		try {
			psmt = connection
					.prepareStatement(BulkSubscriberInterface.FILE_FAILED_UPLOAD);
			psmt.setString(1, Constants.FILE_STATUS_FAILED);
			psmt.setString(2, "File Upload Failed.");
			psmt.setInt(3, fileId);
			int i = psmt.executeUpdate();
			if (i != -1) {
				Updated = Constants.TRUE;
			}
		} catch (Exception e) {
			throw new DAOException(
					"Some error in Updating the failed msg for the file.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}

			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : fileFailedToUpload()");
		return Updated;
	}
	/** 
	 * Updating File Status to Failed when whole file failed to upload
	 * @param fileId  
	 * @param connection database connection reference
	 * @return true if status updated else return false.
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public int getUserIdByLogiId(Connection connection,String loginId)
			throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : getUserIdByLogiId()");
		PreparedStatement psmt = null;
		ResultSet resultSet = null;
		int userId=0;
		try {
			//connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			psmt = connection
					.prepareStatement(BulkSubscriberInterface.GET_USER_ID_BY_LOGIN_ID);
			psmt.setString(1,loginId);
			resultSet= psmt.executeQuery();
			if (resultSet.next()){
				userId = resultSet.getInt("USER_ID");
			}
		} catch (Exception e) {
			throw new DAOException(
					"Some error occured when getting the user id .", e);
		} finally {
			try {
				if(psmt != null ){
					psmt.close();
				}
				if(resultSet != null ){
					resultSet.close();
				}
				
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserAssoDAO : getUserIdByLogiId()");
		return userId;
	}
	
	public void deleteSubscribers(String fileName,String circleCode)throws DAOException {
		
		Connection connection = null;
		PreparedStatement psmt = null;
		int fileCounter = 0;
		boolean connCommitStatus = false;
		
		try{
			String filePath = Utility.getValueFromBundle(
					Constants.FILE_STORAGE_PATH,
					Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE)
					+ fileName;
			
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine=null;
			
			//setting file batch size
			int fileUploadBatchSize = Integer.parseInt(Utility
					.getValueFromBundle(Constants.FILE_UPLOAD_BATCH_SIZE,
							Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE));
			
			//making connection
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			
			/** Starting database transactions. * */
			connCommitStatus = connection.getAutoCommit();
			connection.setAutoCommit(false);
			
			// setting array length
			int intSubInfoLength = 0;
			String subInfoArrayLength = Utility
					.getValueFromBundle(
							Constants.SUBSCRIBER_DEL_INFO_ARRAY_LENGH,
							Constants.BULK_UPLOAD_APPLICATION_RESOURCE_BUNDLE);
			if (subInfoArrayLength != null) {
				intSubInfoLength = Integer
						.parseInt(subInfoArrayLength);
			}
			
			do{
				psmt=connection.prepareStatement(BulkSubscriberInterface.BULK_DELETE);	
				while (((++fileCounter <= fileUploadBatchSize)
							&& (nextLine = reader.readNext()) != null))
					{
						
						if(nextLine.length==intSubInfoLength)
						{
							if(!("".equalsIgnoreCase(nextLine[0])) && !("".equalsIgnoreCase(nextLine[1])) )
							{
								psmt.setString(1, Constants.InActive);
								psmt.setString(2, Constants.DELETED);
								psmt.setString(3, nextLine[0]);
								psmt.setString(4, nextLine[1]);
								psmt.setString(5, Constants.ActiveState);
								psmt.setString(6, circleCode);
								psmt.addBatch();	
							}
			
						}					
					
					}//while
					int a[] = psmt.executeBatch();	
					for(int i=0; i<a.length; i++){
						System.out.println("a  "+a[i]);
					}
					
					fileCounter = 0;	
				}while(nextLine != null);
				connection.commit();
		}catch (Exception e) {
			try {
				/** file failed to upload * */
				connection.rollback();
				connection.setAutoCommit(connCommitStatus);

			} catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

			logger.error("Error" + e.getMessage(), e);
		} finally {
			try {
				if (connection != null) {
					connection.setAutoCommit(connCommitStatus);
					connection.close();
				}
				logger
						.debug("Exiting BulkSubscriberServiceImpl : uploadFile() ");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error" + e.getMessage(), e);
			}
		}
		
		
		
	}
	

}
