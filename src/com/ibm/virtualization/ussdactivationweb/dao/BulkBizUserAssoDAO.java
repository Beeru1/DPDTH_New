/**************************************************************************
 * File     : BulkBizUserAssoDAO.java
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BulkBizUserAssoInterface;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkBizUserAssoDAO {

	private static final Logger log = Logger
			.getLogger(BulkBizUserAssoDAO.class);

	/**
	 * This method interacts with database and returns the list of Files which
	 * are scheduled for uploading.
	 * 
	 * 
	 * @return ArrayList
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */

	public ArrayList getScheduledFiles() throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : getScheduledFiles().");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList files = new ArrayList();
		BulkBizUserAssoDTO assoDto = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getFileDetails = BulkBizUserAssoInterface.FETCH_SCHEDULE_FILES;

			prepareStatement = connection.prepareStatement(getFileDetails);
			prepareStatement.setString(1, Constants.FILE_STATUS_SAVED);

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				assoDto = new BulkBizUserAssoDTO();
				assoDto.setFileId(resultSet.getInt("FILE_ID"));
				assoDto.setFileName(resultSet.getString("FILE_NAME"));
				assoDto.setMappingType(resultSet.getInt("MAPPING_TYPE"));
				assoDto.setRelatedFileId(resultSet.getInt("RELATED_FILE_ID"));
				assoDto.setStatus(resultSet.getString("STATUS"));
				assoDto.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				assoDto.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				assoDto.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				assoDto.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				assoDto.setUserDataId(resultSet.getInt("USER_DATA_ID"));
				assoDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				assoDto.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				assoDto.setFileType(resultSet.getInt("FILE_TYPE"));
				assoDto.setFailedMsg(resultSet.getString("FAILED_MSG"));
				files.add(assoDto);
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.error("Exception", ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement,resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : getScheduledFiles().");
		return files;
	}

	/**
	 * This method interacts with database. Status of the file is changed from
	 * saved to uploading.
	 * 
	 * @param int :
	 *            fileId
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String updateStatusToUploading(int fileId, Connection connection)
			throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : updateStatusToUploading().");
		String statusUpdated = Constants.STATUS_UPDATED;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {

			String updateFiles = BulkBizUserAssoInterface.UPDATE_SCHEDULE_FILES;
			prepareStatement = connection.prepareStatement(updateFiles);
			prepareStatement.setString(1, Constants.FILE_STATUS_UPLOADING);
			prepareStatement.setInt(2, fileId);
			prepareStatement.setString(3, Constants.FILE_STATUS_SAVED);
			int i = prepareStatement.executeUpdate();
			if (i <= 0) {
				statusUpdated = Constants.STATUS_NOT_UPDATED;
				return statusUpdated;
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION", sqe);
			statusUpdated = Constants.STATUS_NOT_UPDATED;
			return statusUpdated;
		} catch (Exception ex) {
			log.error("Exception", ex);
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
		}
		log.debug("Exiting  BulkBizUserAssoDAO : updateStatusToUploading().");
		return statusUpdated;
	}

	/**
	 * This method interacts with databse. Checking User Type
	 * 
	 * @param String :
	 *            user
	 * @param int :
	 *            mappingType
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String userTypeCheck(String user, int mappingType,
			Connection connection) throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : userTypeCheck().");
		String correctType = Constants.FALSE;
		PreparedStatement psmt = null;
		int masterId = mappingType + 1;
		ResultSet mappingCheck = null;
		try {
			// checking mapping type
			psmt = connection
					.prepareStatement(BulkBizUserAssoInterface.CHECK_MAPPING_TYPE);
			psmt.setString(1, user);
			psmt.setInt(2, masterId);
			mappingCheck = psmt.executeQuery();
			if (mappingCheck.next()) {
				correctType = Constants.TRUE;
			}

		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException("Some error in Checking mapping type.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (mappingCheck != null) {
					mappingCheck.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : userTypeCheck().");
		return correctType;
	}

	/**
	 * This method interacts with databse. Checking User Status
	 * 
	 * @param String :
	 *            str
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String userStatusCheck(String str, Connection connection)
			throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : userStatusCheck().");
		String status = Constants.FALSE;
		PreparedStatement psmt = null;
		ResultSet statusCheck = null;
		try {
			psmt = connection
					.prepareStatement(BulkBizUserAssoInterface.CHECK_STATUS);
			psmt.setString(1, str);
			psmt.setString(2, Constants.ActiveState);
			statusCheck = psmt.executeQuery();
			if (statusCheck.next()) {
				status = Constants.TRUE;
			}

		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException("Some error in Checking Status of User.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (statusCheck != null) {
					statusCheck.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : userStatusCheck().");
		return status;
	}

	/**
	 * This method interacts with databse. Checking whether the user is already
	 * mapped or not
	 * 
	 * @param String :
	 *            user
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String userMappedCheck(String user, Connection connection)
			throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : userMappedCheck().");
		String alreadyMapped = Constants.FALSE;
		PreparedStatement psmt = null;
		ResultSet statusCheck = null;
		try {
			psmt = connection
					.prepareStatement(BulkBizUserAssoInterface.CHECK_AREADY_MAPPED);
			psmt.setString(1, user);
			psmt.setInt(2, 0);
			statusCheck = psmt.executeQuery();
			if (statusCheck.next()) {
				alreadyMapped = Constants.TRUE;
			}

		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException(
					"Some error in Checking wether the User is already mapped or not.",
					e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (statusCheck != null) {
					statusCheck.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : userMappedCheck().");
		return alreadyMapped;
	}

	/**
	 * This method interacts with databse. Updating File Status to Failed when
	 * whole filed failed to upload.
	 * 
	 * @param int :
	 *            fileId
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String fileFailedToUpload(int fileId, Connection connection)
			throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : fileFailedToUpload().");
		String Updated = Constants.FALSE;
		PreparedStatement psmt = null;
		try {
			psmt = connection
					.prepareStatement(BulkBizUserAssoInterface.FILE_FAILED_UPLOAD);
			psmt.setString(1, Constants.FILE_STATUS_FAILED);
			psmt.setString(2, "File Upload Failed.");
			psmt.setInt(3, fileId);
			int i = psmt.executeUpdate();
			if (i != -1) {
				Updated = Constants.TRUE;
			}
		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException(
					"Some error in Updating the failed msg for the file.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : fileFailedToUpload().");
		return Updated;
	}

	/**
	 * This method interacts with databse. check the Location of the User.
	 * 
	 * @param int :
	 *            mappingType
	 * @param String :
	 *            user
	 * @param String :
	 *            locId
	 * @param Connection :
	 *            connection
	 * 
	 * @return String
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String checkLocation(String user, String locId,
			Connection connection, int mappingType) throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : checkLocation().");
		String sameLocation = Constants.FALSE;
		PreparedStatement psmt = null;
		ResultSet locationCheck = null;
		try {
			if ((mappingType == Constants.TM)
					|| (mappingType == Constants.DISTIBUTOR)
					|| (mappingType == Constants.FOS)
					|| (mappingType == Constants.ZBM)) {
				psmt = connection
						.prepareStatement(BulkBizUserAssoInterface.CHECK_LOCATION);
				psmt.setString(1, user);
				psmt.setInt(2, Integer.parseInt(locId));
			} else if (mappingType == Constants.ZSM
					|| mappingType == Constants.SALES_HEAD) {
				psmt = connection
						.prepareStatement(BulkBizUserAssoInterface.CHECK_UNDER_LOCATION
								.replaceAll("<LOCATION>", locId));
				psmt.setString(1, user);
			} else if (mappingType == Constants.ED) {
				psmt = connection
						.prepareStatement(BulkBizUserAssoInterface.CHECK_UNDER_CIRCLE
								.replaceAll("<LOCATION>", locId));
				psmt.setString(1, user);
			} else if (mappingType == Constants.CEO) {
				psmt = connection
						.prepareStatement(BulkBizUserAssoInterface.CHECK_UNDER_HUB);
				psmt.setString(1, user);
				psmt.setString(2, locId);
			}
			locationCheck = psmt.executeQuery();
			if (locationCheck.next()) {
				sameLocation = Constants.TRUE;
			}
		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException(
					"Some error in Checking the location of the user.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (locationCheck != null) {
					locationCheck.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : checkLocation().");
		return sameLocation;

	}

	/**
	 * This method interacts with databse. Mapping the Valid users.
	 * 
	 * @param int :
	 *            userId
	 * @param ArrayList :
	 *            validUsers
	 * @param Connection :
	 *            connection
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public void mappUsers(int userId, ArrayList validUsers,
			Connection connection) throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : mappUsers().");
		PreparedStatement psmt = null;

		try {
			Iterator itr = validUsers.iterator();
			ArrayList codeList = new ArrayList();
			while (itr.hasNext()) {
				String code = (String) itr.next();
				codeList.add("'" + code + "'");
			}

			String fileArray[] = (String[]) codeList.toArray(new String[0]);
			String userCode = Utility.arrayToString(fileArray, null);

			// update Query

			String updateFiles = BulkBizUserAssoInterface.UPDATE_MAPPING;
			psmt = connection.prepareStatement(updateFiles.replaceAll(
					"<USER_CODE>", userCode));
			psmt.setInt(1, userId);
			psmt.executeUpdate();

		} catch (Exception e) {
			log.error("Exception", e);
			throw new DAOException(
					"Some error in updating the mapping of the users.", e);
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : mappUsers().");
	}

	/**
	 * By Abhipsa
	 * 
	 * This method is responsible for insert The uploding file data into
	 * database and return the file id to the caller of this method
	 * 
	 * This method is used for Business Users
	 * 
	 * @param fileName
	 *            :String
	 * @param circleCode :
	 *            String
	 * @param userId
	 *            :int
	 * @return :long
	 * @throws DAOException
	 */

	public long fileInfoForBusinessUser(String fileName,
			BulkBizUserAssoDTO bulkBizUserAssoDTO, String fileStatus,
			String failedmsg, int filetype) throws DAOException {

		log.debug("Entering  BulkBizUserAssoDAO : fileInfoForBusinessUser().");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement pstmt1 = null;
		ResultSet resultSet = null;
		int fileid = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			pstmt1 = connection
					.prepareStatement(BulkBizUserAssoInterface.NEXT_VALUE_Business_User);
			resultSet = pstmt1.executeQuery();
			if (resultSet.next()) {
				fileid = resultSet.getInt(1);
			}

			StringBuffer UPDATE_FILE_ENTRY = new StringBuffer(500)
					.append(BulkBizUserAssoInterface.FILE_ENTRY_Business_User
							.replaceAll("<FILE_ID>", String.valueOf(fileid))
							.replaceAll("<FILE_NAME>", fileName).replaceAll(
									"<CIRCLE_CODE>",
									bulkBizUserAssoDTO.getCircleCode())
							.replaceAll("<STATUS>", fileStatus).replaceAll(
									"<ORIGINAL_FILE_NAME>",
									bulkBizUserAssoDTO.getOriginalFileName())
							.replaceAll(
									"<MAPPING_TYPE>",
									String.valueOf(bulkBizUserAssoDTO
											.getMappingType())).replaceAll(
									"<TOTAL_SUCCESS_COUNT>", String.valueOf(0))
							.replaceAll("<TOTAL_FAILURE_COUNT>",
									String.valueOf(0)).replaceAll(
									"<FILE_TYPE>", String.valueOf(filetype))
							.replaceAll("<UPLOADED_BY>",
									bulkBizUserAssoDTO.getUploadedBy())
							.replaceAll(
									"<RELATED_FILE_ID>",
									String.valueOf(bulkBizUserAssoDTO
											.getFileId())).replaceAll(
									"<USER_DATA_ID>",
									String.valueOf(bulkBizUserAssoDTO
											.getUserDataId())).replaceAll(
									"<FAILED_MSG>", failedmsg));
			String query = UPDATE_FILE_ENTRY.toString();

			prepareStatement = connection.prepareStatement(query);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			log.error("Exception", e);
			throw new DAOException(
					"Exection Occured During call of fileInfoForBusinessUser() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement,resultSet);
			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : fileInfoForBusinessUser().");
		return fileid;
	}

	/**
	 * This method interacts with databse. Updating the file info after it has
	 * been read.
	 * 
	 * @param int :
	 *            relatedFileid
	 * @param int :
	 *            fileId
	 * @param int :
	 *            failure
	 * @param int :
	 *            success
	 * @param Connection :
	 *            connection
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 */
	public void updatefileInfoAfterUploading(int relatedFileid, int fileId,
			int failure, int success, Connection connection)
			throws DAOException {

		log
				.debug("Entring  BulkBizUserAssoDAO : updatefileInfoAfterUploading().");
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection
					.prepareStatement(BulkBizUserAssoInterface.UPDATE_FILE_ENTRY);
			prepareStatement.setInt(1, success);
			prepareStatement.setInt(2, failure);
			prepareStatement.setInt(3, relatedFileid);
			prepareStatement.setString(4, Constants.FILE_STATUS_FILEUPLOADED);
			prepareStatement.setInt(5, fileId);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			log.error("Exception", e);
			throw new DAOException(
					"Exection Occured During call of updatefileInfoAfterUploading() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(null, prepareStatement);
			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log
				.debug("Exiting  BulkBizUserAssoDAO : updatefileInfoAfterUploading().");
	}

	/**
	 * This method interacts with databse. getting location details.
	 * 
	 * @param BulkBizUserAssoDTO :
	 *            bulkBizUserAssoDTO
	 * @param Connection :
	 *            connection
	 * 
	 * @return BulkBizUserAssoDTO
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 */
	public BulkBizUserAssoDTO getLocDetails(
			BulkBizUserAssoDTO bulkBizUserAssoDTO, Connection connection)
			throws DAOException {
		log.debug("Entering  BulkBizUserAssoDAO : getLocDetails().");
		PreparedStatement psmtLocId = null;
		ResultSet rsLocId = null;
		BulkBizUserAssoDTO bulkBizUserAssoDTO1 = new BulkBizUserAssoDTO();
		try {
			// getting the location id
			psmtLocId = connection
					.prepareStatement(BulkBizUserAssoInterface.Bussiness_data);
			psmtLocId.setInt(1, bulkBizUserAssoDTO.getUserDataId());
			rsLocId = psmtLocId.executeQuery();
			if (rsLocId.next()) {
				bulkBizUserAssoDTO1.setLocId(rsLocId.getInt("LOC_ID"));
				bulkBizUserAssoDTO1.setCircleCode(rsLocId
						.getString("CIRCLE_CODE"));
				bulkBizUserAssoDTO1.setHubCode(rsLocId.getString("HUB_CODE"));
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception sqe) {
			log.error("EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} finally {
			try {
				if (psmtLocId != null) {
					psmtLocId.close();
				}
				if (rsLocId != null) {
					rsLocId.close();
				}

			} catch (SQLException e) {
				log.error("Exception", e);
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		log.debug("Exiting  BulkBizUserAssoDAO : getLocDetails().");
		return bulkBizUserAssoDTO1;
	}

	/**
	 * This method interacts with databse. Files List from DataBase.
	 * 
	 * @param String :
	 *            circleCode
	 * @param int :
	 *            mappingType
	 * @param String :
	 *            fileStatus
	 * @param int :
	 *            intLb
	 * @param int :
	 *            intUb
	 * 
	 * @return List
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 */
	public List getfilesList(String circleCode, int mappingType,
			String fileStatus, int intLb, int intUb, String userName, String userType) throws DAOException {

		log.debug("Entering  BulkBizUserAssoDAO : getfilesList().");
		ArrayList fileList = new ArrayList();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		BulkBizUserAssoDTO assoDto = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getFileDetails = BulkBizUserAssoInterface.FILES_LIST;
			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {
				if(userType.equalsIgnoreCase(Constants.zoneLogin) ){
					getFileDetails = getFileDetails +
					" AND UPLOADED_BY= ? ) a )b Where rnum<=? and rnum>=? WITH UR ";
					prepareStatement = connection.prepareStatement(getFileDetails);
					prepareStatement.setString(1, circleCode);
					prepareStatement.setInt(2, mappingType);
					prepareStatement
							.setString(3, Constants.FILE_STATUS_FAILED_FILE);
					prepareStatement.setString(4, userName);
					prepareStatement.setString(5, String.valueOf(intUb));
					prepareStatement.setString(6, String.valueOf(intLb + 1));
				}else{
				getFileDetails = getFileDetails
					+ " ) a )b Where rnum<=? and rnum>=? WITH UR ";
				
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));
				}
			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {
				if(userType.equalsIgnoreCase(Constants.zoneLogin) ){
					getFileDetails = getFileDetails +
					" AND STATUS = ? AND UPLOADED_BY= ? ) a )b Where rnum<=? and rnum>=? WITH UR ";
					prepareStatement = connection.prepareStatement(getFileDetails);
					prepareStatement.setString(1, circleCode);
					prepareStatement.setInt(2, mappingType);
					prepareStatement
							.setString(3, Constants.FILE_STATUS_FAILED_FILE);
					prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
					prepareStatement.setString(5, userName);
					prepareStatement.setString(6, String.valueOf(intUb));
					prepareStatement.setString(7, String.valueOf(intLb + 1));
				}else{
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				}
			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {
				if(userType.equalsIgnoreCase(Constants.zoneLogin) ){
					getFileDetails = getFileDetails +
					" AND STATUS = ? AND UPLOADED_BY= ? ) a )b Where rnum<=? and rnum>=? WITH UR ";
					prepareStatement = connection.prepareStatement(getFileDetails);
					prepareStatement.setString(1, circleCode);
					prepareStatement.setInt(2, mappingType);
					prepareStatement
							.setString(3, Constants.FILE_STATUS_FAILED_FILE);
					prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
					prepareStatement.setString(5, userName);
					prepareStatement.setString(6, String.valueOf(intUb));
					prepareStatement.setString(7, String.valueOf(intLb + 1));
				}else{
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_UPLOADING);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				}
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				if(userType.equalsIgnoreCase(Constants.zoneLogin) ){
					getFileDetails = getFileDetails +
					" AND STATUS = ? AND UPLOADED_BY= ? ) a )b Where rnum<=? and rnum>=? WITH UR ";
					prepareStatement = connection.prepareStatement(getFileDetails);
					prepareStatement.setString(1, circleCode);
					prepareStatement.setInt(2, mappingType);
					prepareStatement
							.setString(3, Constants.FILE_STATUS_FAILED_FILE);
					prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
					prepareStatement.setString(5, userName);
					prepareStatement.setString(6, String.valueOf(intUb));
					prepareStatement.setString(7, String.valueOf(intLb + 1));
				}else{
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4,
						Constants.FILE_STATUS_FILEUPLOADED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				}
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				if(userType.equalsIgnoreCase(Constants.zoneLogin) ){
					getFileDetails = getFileDetails +
					" AND STATUS = ? AND UPLOADED_BY= ? ) a )b Where rnum<=? and rnum>=? WITH UR ";
					prepareStatement = connection.prepareStatement(getFileDetails);
					prepareStatement.setString(1, circleCode);
					prepareStatement.setInt(2, mappingType);
					prepareStatement
							.setString(3, Constants.FILE_STATUS_FAILED_FILE);
					prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
					prepareStatement.setString(5, userName);
					prepareStatement.setString(6, String.valueOf(intUb));
					prepareStatement.setString(7, String.valueOf(intLb + 1));
				}else{
				getFileDetails = getFileDetails
						+ " AND STATUS = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_FAILED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				}
			}

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				assoDto = new BulkBizUserAssoDTO();
				assoDto.setFileId(resultSet.getInt("FILE_ID"));
				assoDto.setFileName(resultSet.getString("FILE_NAME"));
				assoDto.setMappingType(resultSet.getInt("MAPPING_TYPE"));
				assoDto.setRelatedFileId(resultSet.getInt("RELATED_FILE_ID"));
				assoDto.setStatus(resultSet.getString("STATUS"));
				assoDto.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				assoDto.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				assoDto.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				assoDto.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				assoDto.setUserDataId(resultSet.getInt("USER_DATA_ID"));
				assoDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				assoDto.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				assoDto.setFileType(resultSet.getInt("FILE_TYPE"));
				assoDto.setFailedFile(resultSet.getString("FAILED_FILE"));
				assoDto.setRownum(resultSet.getString("RNUM"));
				fileList.add(assoDto);
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION", sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.error("Exception", ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log.error("Exception", e);
				log
						.debug("Exception occured when trying to close database resources");
			}

		}
		log.debug("Exiting  BulkBizUserAssoDAO : getfilesList().");
		return fileList;
	}

	/**
	 * This method is called to count the list of files.
	 * 
	 * @return object of int:total number of circles.
	 * 
	 * @throws VCircleMstrDaoException
	 */
	public int countFilesList(String circleCode, int mappingType,
			String fileStatus) throws DAOException {
		log.debug("Entering BulkBizUserAssoDAO : countFilesList().");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;

		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getFileDetails = BulkBizUserAssoInterface.COUNT_FILES_LIST;
			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);

			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_UPLOADING);
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4,
						Constants.FILE_STATUS_FILEUPLOADED);
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setString(1, circleCode);
				prepareStatement.setInt(2, mappingType);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_FAILED);
			}

			ResultSet countFiles = prepareStatement.executeQuery();
			if (countFiles.next()) {
				noofRow = countFiles.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqlEx) {
			log.error("Exception", sqlEx);
			log
					.error("SQL exception encountered: " + sqlEx.getMessage(),
							sqlEx);
			throw new DAOException("SQLException: " + sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			log.error("Exception encountered: " + ex.getMessage(), ex);
			throw new DAOException("Exception: " + ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				log.error("Exception", e);
				new DAOException(
						"Exception occur while closing database resources in method countFilesList() of class DAOException.",
						e);
			}
		}
		log.debug("Exiting BulkBizUserAssoDAO : countFilesList().");
		return noofPages;
	}
}
