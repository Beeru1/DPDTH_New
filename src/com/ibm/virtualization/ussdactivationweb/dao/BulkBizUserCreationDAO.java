/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BulkBizUserCreationInterface;
//import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BusinessUserInterface;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;

import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
//import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Nitesh
 *
 */
public class BulkBizUserCreationDAO {

	private static final Logger logger = Logger
	.getLogger(BulkBizUserCreationDAO.class);
	

	/**
	 * This method getScheduledFiles() gets The files which are to be uploaded
	 * 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
/*
	public ArrayList getScheduledFilesforBulkUserCreation(String fileType) throws DAOException {
		logger
				.debug("Entering  BulkBizUserCreationDAO : getScheduledFilesforBulkUserCreation().");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList files = new ArrayList();
		BulkBizUserCreationDTO bulkBizUserCreationDTO = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getReportDetails = BulkBizUserCreationInterface.FETCH_SCHEDULE_FILES;

			prepareStatement = connection.prepareStatement(getReportDetails);
			prepareStatement.setString(1, Constants.FILE_STATUS_SAVED);
			prepareStatement.setString(2, fileType);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				bulkBizUserCreationDTO = new BulkBizUserCreationDTO();
				bulkBizUserCreationDTO.setFileId(resultSet.getInt("FILE_ID"));
				bulkBizUserCreationDTO.setFileName(resultSet.getString("FILE_NAME"));
				bulkBizUserCreationDTO.setRelatedFileId(resultSet
						.getInt("RELATED_FILE_ID"));
				bulkBizUserCreationDTO.setStatus(resultSet.getString("STATUS"));
				bulkBizUserCreationDTO.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				bulkBizUserCreationDTO.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				bulkBizUserCreationDTO.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				bulkBizUserCreationDTO.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				bulkBizUserCreationDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				bulkBizUserCreationDTO.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				bulkBizUserCreationDTO.setFileType(resultSet.getInt("FILE_TYPE"));
				bulkBizUserCreationDTO.setFailedMsg(resultSet.getString("FAILED_MSG"));
				
				bulkBizUserCreationDTO.setUserMasterId(resultSet.getInt("USER_MASTER_ID"));
				bulkBizUserCreationDTO.setLocationId(resultSet.getInt("LOC_ID"));
				bulkBizUserCreationDTO.setParentId(resultSet.getInt("PARENT_ID"));
				bulkBizUserCreationDTO.setZoneId(resultSet.getInt("ZONE_ID"));
				
				files.add(bulkBizUserCreationDTO);
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
						.error("Exception occured when trying to close database resources in BulkBizUserCreationDAO:getScheduledFilesforBulkUserCreation()");
			}
			logger
					.debug("Exiting BulkBizUserCreationDAO : getScheduledFilesforBulkUserCreation().");

		}
		return files;
	}
	*/
	
	/**
	 * This method update the file status as UPLOADING befor process the uploaded file.
	 * @param fileId is file which status to be change as UPLOADING
	 * @param connection database connection reference
	 * @return true in case of status updated successfully else return false
	 * @exception DAOException,when
	 *     there is some problem while locating an object in
	 *                database.
	 */

	public String updateFilesforUsers(int fileId, Connection connection)
			throws DAOException {
		logger
				.debug("Entering  BulkBizUserCreationDAO : updateFilesforUsers()");

		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		String statusUpdated = Constants.STATUS_UPDATED;
		try {			

			prepareStatement = connection.prepareStatement(BulkBizUserCreationInterface.UPDATE_SCHEDULE_FILES);
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
					.debug("Exiting BulkBizUserCreationDAO : updateFilesforUsers()");
		}
		return statusUpdated;
	}
	
	
	/**
	 * This method interacts with database and insert the data in the table.
	 * Also checks whether the user exists in table or not.
	 * Checks for valid service classes also.
	 * 
	 * 
	 * @param bulkBizUserCreationDTO :
	 *            RegistrationOfAllDTO
	 * @param creatorUserId :
	 *            int
	 * @return Sting constant
	 */
	/*
	public void insert(BulkBizUserCreationDTO bulkBizUserCreationDTO, int creatorUserId, Connection connection)
			throws DAOException {
		logger
				.debug("Entering method insert() in class RegistrationOfAllUsersDAO.One of the parameter used , UserId : "
						+ bulkBizUserCreationDTO.getUploadedBy());
		
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement5 = null;
		
		int businessId = 0;		
		ResultSet rs3 = null;
				
		ResultSet rs1 = null;
		ResultSet rs = null;
		
		Connection connectionFta = null;
		
		try {
						
			bulkBizUserCreationDTO.setFosActivationCheck(Constants.N);
			bulkBizUserCreationDTO.setFosCheckRequired(Constants.Y);
			bulkBizUserCreationDTO.setIPhoneActRights(Constants.N);
			
			
			connectionFta = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			connectionFta.setAutoCommit(false);
			bulkBizUserCreationDTO.setCreatedBy(this.getUserIdByLogiId(connectionFta, bulkBizUserCreationDTO.getUploadedBy()));
			
			
			// generating code from sequences.
			
			if (bulkBizUserCreationDTO.getUserMasterId() != Constants.DISTIBUTOR) {
				String prefix = "";
				int code = 0;
				preparedStatement1 = connection
						.prepareStatement(BulkBizUserCreationInterface.Code_FOR_BIZ_USER);
				rs1 = preparedStatement1.executeQuery();
				if (rs1.next()) {
					code = rs1.getInt(1);
				}
				preparedStatement2 = connection
						.prepareStatement(BulkBizUserCreationInterface.Prefix_FOR_BIZ_USER_CODE);
				preparedStatement2.setInt(1, bulkBizUserCreationDTO.getUserMasterId());
				rs = preparedStatement2.executeQuery();
				if (rs.next()) {
					prefix = rs.getString(Constants.CODE_PREFIX);
				}
				String fullCode = "" + prefix + "" + code;
				bulkBizUserCreationDTO.setUserCode(fullCode);

			}
			preparedStatement3 = connection
					.prepareStatement(BulkBizUserCreationInterface.GET_NEXT_BIZ_USER_ID);
			rs3 = preparedStatement3.executeQuery();
			if (rs3.next()) {
				businessId = rs3.getInt(1);
			}

			preparedStatement = connection
					.prepareStatement(BulkBizUserCreationInterface.INSERT_BIZ_USER_DATA);
			preparedStatement.setInt(1, businessId);
			preparedStatement.setString(2, bulkBizUserCreationDTO.getUserCode());
			preparedStatement.setString(3, bulkBizUserCreationDTO.getUserName());
			preparedStatement.setInt(4, bulkBizUserCreationDTO.getUserMasterId());
			preparedStatement.setInt(5, bulkBizUserCreationDTO.getParentId());
			preparedStatement.setString(6, Constants.ActiveState);
			preparedStatement.setString(7, bulkBizUserCreationDTO.getContactNo());
			preparedStatement.setString(8, null);
			preparedStatement.setString(9, bulkBizUserCreationDTO.getCircleCode());
			preparedStatement.setString(10, bulkBizUserCreationDTO.getHubCode());
			preparedStatement.setString(11, bulkBizUserCreationDTO.getFosCheckRequired());
			preparedStatement.setString(12, bulkBizUserCreationDTO.getFosActivationCheck());
			
			if( bulkBizUserCreationDTO.getUserMasterId() == Constants.ZBM ||  bulkBizUserCreationDTO.getUserMasterId() == Constants.ZSM)
			{
				preparedStatement.setInt(13, bulkBizUserCreationDTO.getZoneId());
			}
			else
			{
				preparedStatement.setInt(13, bulkBizUserCreationDTO.getLocationId());
			}
			
			preparedStatement.setInt(14, bulkBizUserCreationDTO.getCreatedBy());
			preparedStatement.setInt(15, bulkBizUserCreationDTO.getCreatedBy());
			preparedStatement
					.setString(16, bulkBizUserCreationDTO.getAllServiceClass());
			preparedStatement.setString(17, bulkBizUserCreationDTO.getIncludeService());
			preparedStatement.setString(18, bulkBizUserCreationDTO.getExcludeService());
			preparedStatement.executeUpdate();

			// updating in Registered number table
			preparedStatement5 = connection
					.prepareStatement(BulkBizUserCreationInterface.INSERT_STMT_DIST);
			preparedStatement5.setString(1, bulkBizUserCreationDTO.getRegistrationNo());
			preparedStatement5.setInt(2, businessId);
			preparedStatement5.setString(3,Constants.ActiveState);
			preparedStatement5.setInt(4, bulkBizUserCreationDTO.getCreatedBy());
			preparedStatement5.setInt(5, bulkBizUserCreationDTO.getCreatedBy());
			preparedStatement5.executeUpdate();
			
			logger.debug("data inserted successfully in the database for code:"+ bulkBizUserCreationDTO.getUserCode());
			logger	.debug("Exiting method insert() in class BulkBizUserCreationDAO");
			
			
			
		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while inserting business user bulk data in the database :"
							+ bulkBizUserCreationDTO.getUserCode(), e);
		} finally {
			try {	
					if(rs1 != null)
					{
						rs1.close();
					}
					if(rs != null)
					{
						rs.close();
					}
					if(preparedStatement != null)
					{
						preparedStatement.close();
					}
					if(preparedStatement1 != null)
					{
						preparedStatement1.close();
					}
					if(preparedStatement2 != null)
					{
						preparedStatement2.close();
					}
					if(connectionFta != null)
					{
						try {
							
							DBConnectionUtil.closeDBResources(connectionFta, null);							
							
						} catch (Exception e) {
							logger.error("Error" + e.getMessage(), e);
						}
						
					}
					
				
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class insert.",
						e);
			}
		}		
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
	public long insertFileInfoForBizUser(String fileName,
			BulkBizUserCreationDTO bulkBizUserCreationDTO, String fileStatus,
			String failedmsg, int filetype) throws DAOException {

		logger.debug("Entering BulkBizUserAssoDAO : fileInfoForSubscriber()");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement pstmt1 = null;
		ResultSet resultSet = null;
		int fileid = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			pstmt1 = connection
					.prepareStatement(BulkBizUserCreationInterface.NEXT_VALUE_FOR_FILE);
			resultSet = pstmt1.executeQuery();
			if (resultSet.next()) {
				fileid = resultSet.getInt(1);
			}
			prepareStatement = connection
					.prepareStatement(BulkBizUserCreationInterface.FILE_ENTRY_BIZ_USER);
			prepareStatement.setInt(1, fileid);
			prepareStatement.setString(2, fileName);
			prepareStatement.setString(3, bulkBizUserCreationDTO.getCircleCode());
			prepareStatement.setString(4, fileStatus);
			prepareStatement.setString(5, bulkBizUserCreationDTO
					.getOriginalFileName());
			prepareStatement.setInt(6, 0);
			prepareStatement.setInt(7, 0);
			prepareStatement.setInt(8, filetype);
			prepareStatement.setString(9, bulkBizUserCreationDTO.getUploadedBy());
			prepareStatement.setInt(10, bulkBizUserCreationDTO.getRelatedFileId());
//			if(bulkBizUserCreationDTO.getFileId() == -1)
//			{
//				prepareStatement.setInt(10, bulkBizUserCreationDTO.getFileId());
//			}
//			else
//			{
//				prepareStatement.setString(10, null);
//			}
			
			prepareStatement.setString(11, failedmsg);			
			prepareStatement.setInt(12, bulkBizUserCreationDTO.getLocationId());
			prepareStatement.setInt(13, bulkBizUserCreationDTO.getParentId());
			prepareStatement.setInt(14, bulkBizUserCreationDTO.getUserMasterId());
			prepareStatement.setInt(15, bulkBizUserCreationDTO.getZoneId());
			
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
					.prepareStatement(BulkBizUserCreationInterface.UPDATE_FILE_ENTRY_BULK_BIZ_USER_CREATION);
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
					.prepareStatement(BulkBizUserCreationInterface.FILE_FAILED_UPLOAD);
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
	 * This method is called to count the circles.
	 * @param containsRoleType TODO
	 * 
	 * @return object of int:total number of circles.
	 * 
	 *@exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public int countFilesListForBizUserCreation(String circleCode, String fileStatus, String containsRoleType)
			throws DAOException {
		logger
				.debug("Entering BulkBizUserCreationDAO : countFilesListForBizUserCreation()");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;

		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getFileDetails = BulkBizUserCreationInterface.COUNT_FILES_LIST_FOR_BULK_BIZ_USER_CREATION;
			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " And USER_MASTER_ID >= ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, containsRoleType);
				

			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? And USER_MASTER_ID >= ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
				prepareStatement.setString(5, containsRoleType);
			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails + " AND STATUS = ? And USER_MASTER_ID >= ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_UPLOADING);
				prepareStatement.setString(5, containsRoleType);
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? And USER_MASTER_ID >= ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4,
						Constants.FILE_STATUS_FILEUPLOADED);
				prepareStatement.setString(5, containsRoleType);
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails + " AND STATUS = ? And USER_MASTER_ID >= ? WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_FAILED);
				prepareStatement.setString(5, containsRoleType);
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
				.debug("Exiting BulkBizUserCreationDAO : countFilesListForBizUserCreation()");
		return noofPages;
	}
	
	
	/**
	 * This method returns the list of subscriber files is the respective circle. 
	 * @param circleCode 
	 * @param fileStatus status of the file
	 * @param intLb lower bound for pagination
	 * @param intUb upper bound for pagination
	 * @param conatinsRoleList TODO
	 * @return list of subscriber files is the respective circle. 
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	/*
	public List getfilesListForBizUser(String circleCode, String fileStatus,
			int intLb, int intUb, String conatinsRoleList) throws DAOException {

		logger
				.debug("Entering BulkBizUserCreationDAO : getfilesListForBizUser()");
		ArrayList fileList = new ArrayList();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		BulkBizUserCreationDTO bulkBizUserCreationDTO = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);

			String getFileDetails = BulkBizUserCreationInterface.FILES_LIST_FOR_BIZ_USER_CREATION;

			if ((Constants.SEARCH_STATUS_ALL).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " ) a )b Where rnum<=? and rnum>=? And USER_MASTER_ID >= ? Order By UPLOADED_DT Desc WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				;
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, String.valueOf(intUb));
				prepareStatement.setString(5, String.valueOf(intLb + 1));
				prepareStatement.setString(6, conatinsRoleList);

			}
			if ((Constants.SEARCH_STATUS_SAVED).equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " AND super.STATUS = ? ) a )b Where rnum<=? and rnum>=? And USER_MASTER_ID >= ? Order By UPLOADED_DT Desc WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_SAVED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				prepareStatement.setString(7, conatinsRoleList);

			}
			if ((Constants.SEARCH_STATUS_UPLOADING)
					.equalsIgnoreCase(fileStatus)) {

				getFileDetails = getFileDetails
						+ " AND super.STATUS = ? ) a )b Where rnum<=? and rnum>=? And USER_MASTER_ID >= ? Order By UPLOADED_DT Desc WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_UPLOADING);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				prepareStatement.setString(7, conatinsRoleList);
			}
			if ((Constants.SEARCH_STATUS_UPLOADED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails
						+ " AND super.STATUS = ? ) a )b Where rnum<=? and rnum>=? And USER_MASTER_ID >= ? Order By UPLOADED_DT Desc WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4,
						Constants.FILE_STATUS_FILEUPLOADED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				prepareStatement.setString(7, conatinsRoleList);
			}
			if ((Constants.SEARCH_STATUS_FAILED).equalsIgnoreCase(fileStatus)) {
				getFileDetails = getFileDetails
						+ " AND super.STATUS = ? ) a )b Where rnum<=? and rnum>=? And USER_MASTER_ID >= ? Order By UPLOADED_DT Desc WITH UR";
				prepareStatement = connection.prepareStatement(getFileDetails);
				prepareStatement.setInt(1, Constants.FILE_TYPE_BULK_BIZ_USER_FILE);
				prepareStatement.setString(2, circleCode);
				prepareStatement
						.setString(3, Constants.FILE_STATUS_FAILED_FILE);
				prepareStatement.setString(4, Constants.FILE_STATUS_FAILED);
				prepareStatement.setString(5, String.valueOf(intUb));
				prepareStatement.setString(6, String.valueOf(intLb + 1));
				prepareStatement.setString(7, conatinsRoleList);
			}

			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				bulkBizUserCreationDTO = new BulkBizUserCreationDTO();
				bulkBizUserCreationDTO.setFileId(resultSet.getInt("FILE_ID"));
				bulkBizUserCreationDTO.setFileName(resultSet.getString("FILE_NAME"));
				bulkBizUserCreationDTO.setRelatedFileId(resultSet
						.getInt("RELATED_FILE_ID"));
				bulkBizUserCreationDTO.setStatus(resultSet.getString("STATUS"));
				bulkBizUserCreationDTO.setTotalFailureCount(resultSet
						.getInt("TOTAL_FAILURE_COUNT"));
				bulkBizUserCreationDTO.setTotalSuccessCount(resultSet
						.getInt("TOTAL_SUCCESS_COUNT"));
				bulkBizUserCreationDTO.setUploadedBy(resultSet.getString("UPLOADED_BY"));
				bulkBizUserCreationDTO.setUploadedDate(String.valueOf(resultSet
						.getTimestamp("UPLOADED_DT")));
				bulkBizUserCreationDTO.setUserRole(resultSet.getString("USER_TYPE"));
				bulkBizUserCreationDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				bulkBizUserCreationDTO.setOriginalFileName(resultSet
						.getString("ORIGINAL_FILE_NAME"));
				bulkBizUserCreationDTO.setFileType(resultSet.getInt("FILE_TYPE"));
				bulkBizUserCreationDTO.setFailedFile(resultSet.getString("FAILED_FILE")); 
				bulkBizUserCreationDTO.setRownum(resultSet.getString("RNUM"));
				
				fileList.add(bulkBizUserCreationDTO);
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
						.debug("Exception occured when trying to close database resources in BulkBizUserCreationDAO : getfilesListForBizUser()");
			}
			logger.debug("Exiting method BulkBizUserCreationDAO : getfilesListForBizUser() DAOException");

		}
		logger
				.debug("Exiting BulkBizUserCreationDAO : getfilesListForBizUser()");
		return fileList;
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
	public long fileInfoForBizUser(String fileName,
			BulkBizUserCreationDTO bulkBizUserCreationDTO, String fileStatus,
			String failedmsg, int filetype) throws DAOException {

		logger.debug("Entering BulkBizUserCreationDAO : fileInfoForBizUser()");
		Connection connectionDistportal = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement pstmt1 = null;
		ResultSet resultSet = null;
		int fileid = 0;
		try {
			connectionDistportal = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			pstmt1 = connectionDistportal
					.prepareStatement(BulkBizUserCreationInterface.NEXT_VALUE_FOR_FILE);
			resultSet = pstmt1.executeQuery();
			if (resultSet.next()) {
				fileid = resultSet.getInt(1);
			}
			prepareStatement = connectionDistportal
					.prepareStatement(BulkBizUserCreationInterface.FILE_ENTRY_BIZ_USER);
			prepareStatement.setInt(1, fileid);
			prepareStatement.setString(2, fileName);
			prepareStatement.setString(3, bulkBizUserCreationDTO.getCircleCode());
			prepareStatement.setString(4, fileStatus);
			prepareStatement.setString(5, bulkBizUserCreationDTO
					.getOriginalFileName());
			prepareStatement.setInt(6, 0);
			prepareStatement.setInt(7, 0);
			prepareStatement.setInt(8, filetype);
			prepareStatement.setString(9, bulkBizUserCreationDTO.getUploadedBy());
			prepareStatement.setInt(10, bulkBizUserCreationDTO.getFileId());
			prepareStatement.setString(11, failedmsg);
			if(bulkBizUserCreationDTO.getLocationId() <= 0)
			{
				prepareStatement.setString(12, null);
			}
			else
			{
				prepareStatement.setInt(12, bulkBizUserCreationDTO.getLocationId());
			}
			if(bulkBizUserCreationDTO.getParentId() <= 0)
			{
				prepareStatement.setString(13, null);
			}
			else
			{
				prepareStatement.setInt(13, bulkBizUserCreationDTO.getParentId());
			}
			if(bulkBizUserCreationDTO.getUserMasterId() <= 0)
			{
				prepareStatement.setString(14, null);
			}
			else
			{
				prepareStatement.setInt(14, bulkBizUserCreationDTO.getUserMasterId());
			}
			if(bulkBizUserCreationDTO.getZoneId() <= 0)
			{
				prepareStatement.setString(15, null);
			}
			else
			{
				prepareStatement.setInt(15, bulkBizUserCreationDTO.getZoneId());
			}
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(
					"Exection Occured During call of fileInfoForBusinessUser() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connectionDistportal, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in database resources closing.", e);
			}
		}
		logger.debug("Exiting BulkBizUserCreationDAO : fileInfoForBizUser()");
		return fileid;
	}
*/	
	public int getUserIdByLogiId(Connection connection,String loginId)
	throws DAOException {

		logger.debug("Entering BulkBizUserCreationDAO : getUserIdByLogiId()");
		PreparedStatement psmt = null;
		ResultSet resultSet = null;
		int userId=0;
		try {
			
				psmt = connection
						.prepareStatement(BulkBizUserCreationInterface.GET_USER_ID_BY_LOGIN_ID);
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
		logger.debug("Exiting BulkBizUserCreationDAO : getUserIdByLogiId()");
		return userId;
	}
}
