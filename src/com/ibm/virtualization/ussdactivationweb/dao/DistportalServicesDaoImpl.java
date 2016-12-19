/**************************************************************************
 * File     : DistportalServicesDaoImpl.java
 * Author   : Banita
 * Created  : Oct 6, 2008
 * Modified : Oct 6, 2008
 * Version  : 0.1
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.CircleMasterInterface;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.DistportalInterface;
//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.SMSReportDAOInterface;
import com.ibm.virtualization.ussdactivationweb.services.ProdcatService;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.RequesterDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * This class is used to fetch the details of users according to the provided
 * parameters.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class DistportalServicesDaoImpl {

	private static Logger logger = Logger
	.getLogger(DistportalServicesDaoImpl.class.getName());

	/**
	 * This method getRequesterDetails() searches for the register number from
	 * database and gives all the details about that number.
	 * 
	 * @param requesterRegisterNumber
	 *            one parameter on which data can be fetch the details of the
	 *            registration.
	 * @return a BusinessUserDTO that contails all the details of the particular
	 *         register number
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public BusinessUserDTO getRequesterDetails(String requesterRegisterNumber,String userType)
	throws DAOException {
		logger
		.debug("Entering  getRequesterDetails in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement1 = null;
		ResultSet resultSet = null;
		BusinessUserDTO businessUserDTO = new BusinessUserDTO();
		Connection connection = null;
		Connection connection1 = null;
		ProdcatService prodService = new ProdcatService();
		String activeCircle = "";
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(DistportalInterface.RETRIEVE_REQUESTER_DETAILS);
			prepareStatement.setString(1, requesterRegisterNumber);
			prepareStatement.setString(2, Constants.ActiveState);
			prepareStatement.setString(3, Constants.ActiveState);
			prepareStatement.setString(4, userType);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				populateDto(businessUserDTO, resultSet);

				activeCircle = prodService.getActiveCircle(businessUserDTO
						.getCircleCode());

				if (!activeCircle.equals("") && activeCircle != null) {
					businessUserDTO.setCircleCode(activeCircle);
				} else {
					businessUserDTO = null;
				}
			} else {
				businessUserDTO = null;
			}
		} catch (SQLException sqe) {
			businessUserDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			businessUserDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			businessUserDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			businessUserDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
				DBConnectionUtil.closeDBResources(connection1,
						prepareStatement1, resultSet);
			} catch (Exception e) {
				businessUserDTO.setErrorMessage(String.valueOf(e.getMessage()));
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getRequesterDetails in DistportalServiceDAOImpl");
		return businessUserDTO;
	}

	/**
	 * This method getCompleteRequesterDetails() searches for the register
	 * number from database and gives all the details about that number.
	 * 
	 * @param requesterRegisterNumber
	 *            one parameter on which data can be fetch the details of the
	 *            registration.
	 * @return a RequesterDTO that contails all the details of the particular
	 *         register number
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */

	/**
	 * In this there are several conditions and on the basis only u get data:
	 * a)Register Number should be acitve b)Status of the user who has
	 * registered for should be active c)The location to which he belongs and
	 * all its parent heriarchies should be active d)Now you get heriarchical
	 * details only when its parent are in active state. e)The register number
	 * of parent should also be in active state else you will get details only
	 * till the user whose every field is active. . f)Parent's location should
	 * be in active state else you will get details only till the user whose
	 * every field is active. g)Circle should also be in active state,if not no
	 * details will be fetch.
	 * 
	 * Note: In rest all services you get data when they are active else not.
	 */

//	public RequesterDTO getCompleteRequesterDetails(
//			String requesterRegisterNumber) throws DAOException {
//		logger
//		.debug("Entering  getCompleteRequesterDetails in DistportalServiceDAOImpl");
//		PreparedStatement prepareStatement = null;
//		PreparedStatement prepareStatement3 = null;
//		ResultSet resultSet = null;
//		ResultSet resultSet2 = null;
//		RequesterDTO requesterDTO = new RequesterDTO();
//		BusinessUserDTO businessUserDTO = null;
//		BusinessUserDTO[] businessUserDTO1 = new BusinessUserDTO[10];
//		Connection connection = null;
//		Connection connection1 = null;
//		ProdcatServicesDaoImpl prodService = new ProdcatServicesDaoImpl();
//		try {
//			connection = DBConnection
//			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
//			prepareStatement = connection
//			.prepareStatement(DistportalInterface.RETRIEVE_COMPLETE_REQUESTER_DETAILS);
//			prepareStatement.setString(1, requesterRegisterNumber);
//			prepareStatement.setString(2, Constants.ActiveState);
//			prepareStatement.setString(3, Constants.ActiveState);
//			prepareStatement.setString(4, Constants.ActiveState);
//			prepareStatement.setString(5, Constants.ActiveState);
//			boolean requesterInfo = true;
//			resultSet = prepareStatement.executeQuery();
//			while (resultSet.next()) {
//				businessUserDTO = new BusinessUserDTO();
//				populateDto(businessUserDTO, resultSet);
//				if (requesterInfo) {
//					requesterDTO.setCircleCode(businessUserDTO.getCircleCode());
//					requesterInfo = false;
//				}
//				String locStatus = prodService
//				.getLocationStatus(businessUserDTO.getLocId());
//				if (locStatus == "InActive") {
//					businessUserDTO = null;
//					businessUserDTO1 = null;
//					break;
//				}
//				// inserting in object array
//				int position = 0;
//				if (businessUserDTO != null) {
//					for (int i = 0; i < businessUserDTO1.length; i++) {
//						if (businessUserDTO1[i] == null) {
//							position = i;
//							break;
//						}
//					}
//					businessUserDTO1[position] = businessUserDTO;
//				}
//			}
//			if (businessUserDTO == null) {
//				requesterDTO = null;
//			} else {
//				requesterDTO.setBusinessUserArray(businessUserDTO1);
//			}
//		} catch (SQLException sqe) {
//			requesterDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
//			requesterDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
//			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
//			throw new DAOException(sqe.getMessage(), sqe);
//		} catch (Exception ex) {
//			logger.error("Exception" + ex.getMessage(), ex);
//			requesterDTO.setErrorCode(Utility.getValueFromBundle(
//					Constants.Error_Code3,
//					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//			requesterDTO.setErrorMessage(String.valueOf(ex.getMessage()));
//			throw new DAOException(ex.getMessage(), ex);
//		} finally {
//			try {
//				DBConnectionUtil.closeDBResources(connection, prepareStatement,
//						resultSet);
//				DBConnectionUtil.closeDBResources(connection1,
//						prepareStatement3, resultSet2);
//			} catch (Exception e) {
//				requesterDTO.setErrorMessage(String.valueOf(e.getMessage()));
//				logger
//				.debug("Exception occured when trying to close database resources");
//			}
//		}
//		logger
//		.debug("Exiting getCompleteRequesterDetails in DistportalServiceDAOImpl");
//		return requesterDTO;
//	}
	//when theere are many users with same registered number
	public RequesterDTO getCompleteRequesterDetails(
			String requesterRegisterNumber,String userType) throws DAOException {
		logger
		.debug("Entering  getCompleteRequesterDetails in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement3 = null;
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		RequesterDTO requesterDTO = new RequesterDTO();
		BusinessUserDTO businessUserDTO = null;
		BusinessUserDTO[] businessUserDTO1 = new BusinessUserDTO[10];
		Connection connection = null;
		Connection connection1 = null;
		ProdcatServicesDaoImpl prodService = new ProdcatServicesDaoImpl();
		CircleDTO circleDto =  new CircleDTO();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(DistportalInterface.RETRIEVE_COMPLETE_REQUESTER_DETAILS);
			prepareStatement.setString(1, requesterRegisterNumber);
			prepareStatement.setString(2, userType);
			prepareStatement.setString(3, Constants.ActiveState);
			prepareStatement.setString(4, Constants.ActiveState);
			prepareStatement.setString(5, Constants.ActiveState);
			prepareStatement.setString(6, Constants.ActiveState);
			boolean requesterInfo = true;
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				businessUserDTO = new BusinessUserDTO();
				populateDto(businessUserDTO, resultSet);
				if (requesterInfo) {
					requesterDTO.setCircleCode(businessUserDTO.getCircleCode());
					requesterInfo = false;
				}
				String locStatus = prodService
				.getLocationStatus(businessUserDTO.getLocId());
				if (locStatus == "InActive") {
					businessUserDTO = null;
					businessUserDTO1 = null;
					break;
				}else{
					String locationName  = prodService.getLocationName(businessUserDTO.getLocId(),
							businessUserDTO.getBaseLoc());
					businessUserDTO.setLocationName(locationName);
					if(("").equalsIgnoreCase(businessUserDTO.getCircleCode())){
						businessUserDTO.setCircleCode(null);
					}
					if(businessUserDTO.getCircleCode()!=null){
						circleDto = prodService.getCircleByCode(businessUserDTO.getCircleCode());
						requesterDTO.setCircleName(circleDto.getCircleName());
					}
					requesterDTO.setHubName(circleDto.getHubName());
				}
				// inserting in object array
				int position = 0;
				if (businessUserDTO != null) {
					for (int i = 0; i < businessUserDTO1.length; i++) {
						if (businessUserDTO1[i] == null) {
							position = i;
							break;
						}
					}
					businessUserDTO1[position] = businessUserDTO;
				}
			}
			if (businessUserDTO == null) {
				requesterDTO = null;
			} else {
				requesterDTO.setBusinessUserArray(businessUserDTO1);
			}
		} catch (SQLException sqe) {
			requesterDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			requesterDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			requesterDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			requesterDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
				DBConnectionUtil.closeDBResources(connection1,
						prepareStatement3, resultSet2);
			} catch (Exception e) {
				requesterDTO.setErrorMessage(String.valueOf(e.getMessage()));
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting getCompleteRequesterDetails in DistportalServiceDAOImpl");
		return requesterDTO;
	}
	
	
	/**
	 * In this there are several conditions and on the basis only u get data:
	 * a)Register Number should be acitve b)Status of the user who has
	 * registered for should be active c)The location to which he belongs and
	 * all its parent heriarchies should be active d)Now you get heriarchical
	 * details only when its parent are in active state. e)The register number
	 * of parent should also be in active state in full heirarchy f)Parent's
	 * location should be in active state else you will get details only till
	 * the user whose every field is active. g)Circle should also be in active
	 * state,if not no details will be fetch.
	 * 
	 * Note: In rest all services you get data when they are active else not.
	 */

	// public RequesterDTO getCompleteRequesterDetails(String
	// requesterRegisterNumber)
	// throws DAOException {
	// logger.debug("Entering getCompleteRequesterDetails in DAOImpl");
	// PreparedStatement prepareStatement = null;
	// PreparedStatement prepareStatement3 = null;
	// ResultSet resultSet = null;
	// ResultSet resultSet2 = null;
	// RequesterDTO requesterDTO = new RequesterDTO();
	// BusinessUserDTO businessUserDTO = null;
	// BusinessUserDTO[] businessUserDTO1 = new BusinessUserDTO[10];
	// Connection connection = null;
	// Connection connection1 = null;
	// ProdcatServicesDaoImpl prodService = new ProdcatServicesDaoImpl();
	//	
	// try {
	// connection = DBConnection
	// .getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
	// prepareStatement = connection
	// .prepareStatement(DistportalInterface.RETRIEVE_COMPLETE_REQUESTER_DETAILS);
	// prepareStatement.setString(1, requesterRegisterNumber);
	// prepareStatement.setString(2, Constants.ActiveState);
	// resultSet = prepareStatement.executeQuery();
	// while (resultSet.next()) {
	// if((resultSet.getString("STATUS").equalsIgnoreCase(Constants.ActiveState))
	// &&
	// (resultSet.getString("REGSTATUS").equalsIgnoreCase(Constants.ActiveState)))
	// {
	// businessUserDTO=new BusinessUserDTO();
	// populateDto(businessUserDTO, resultSet);
	// String locStatus =
	// prodService.getLocationStatus(businessUserDTO.getLocId());
	// if(locStatus == "InActive"){
	// businessUserDTO = null;
	// businessUserDTO1 = null;
	// break;
	// }else{
	// connection1 = DBConnection
	// .getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
	// prepareStatement3 = connection1
	// .prepareStatement(DistportalInterface.RETRIEVE_CIRCLE);
	// prepareStatement3.setInt(1, Constants.Active);
	// prepareStatement3.setString(2, businessUserDTO.getCircleCode());
	// resultSet2 = prepareStatement3.executeQuery();
	// if(resultSet2.next()) {
	// businessUserDTO.setCircleCode(resultSet2.getString("CIRCLE_CODE"));
	// }else{
	// businessUserDTO = null;
	// businessUserDTO1 = null;
	// break;
	// }
	// }
	// // inserting in object array
	// int position=0;
	// if(businessUserDTO !=null){
	// for(int i=0 ; i<businessUserDTO1.length;i++){
	// if(businessUserDTO1[i]==null)
	// {
	// position = i;
	// break;
	// }
	// }
	// businessUserDTO1[position]=businessUserDTO;
	// }
	// }
	// else{
	// businessUserDTO = null;
	// businessUserDTO1 = null;
	// break;
	// }
	// }
	// if(businessUserDTO==null){
	// requesterDTO=null;
	// }else{
	// requesterDTO.setBusinessUserArray(businessUserDTO1);
	// }
	//			
	// } catch (SQLException sqe) {
	// sqe.printStackTrace();
	// requesterDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
	// requesterDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
	// logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
	// throw new DAOException(sqe.getMessage(), sqe);
	//
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// logger.error("Exception" + ex.getMessage(), ex);
	// requesterDTO.setErrorCode(Utility.getValueFromBundle(Constants.Error_Code3,
	// Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
	// requesterDTO.setErrorMessage(String.valueOf(ex.getMessage()));
	// throw new DAOException(ex.getMessage(), ex);
	//
	// } finally {
	// try {
	// DBConnectionUtil.closeDBResources(connection, prepareStatement,
	// resultSet);
	// DBConnectionUtil.closeDBResources(connection1, prepareStatement3,
	// resultSet2);
	// } catch (Exception e) {
	// requesterDTO.setErrorMessage(String.valueOf(e.getMessage()));
	// logger
	// .debug("Exception occured when trying to close database resources");
	// }
	// }
	// logger.debug("Exiting getCompleteRequesterDetails in DAOImpl");
	// return requesterDTO;
	// }
	/**
	 * This method getBusinessUserDetailsById() searches for the dataId from
	 * database and gives all the details about that id.
	 * 
	 * @param parentId
	 *            one parameter on which data can be fetch the details of the
	 *            dataId.
	 * @return a BusinessUserDTO that contails all the details of the particular
	 *         data id
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public int getBizUserId(Connection connection,String requesterRegisterNumber)
	throws DAOException {
		logger
		.debug("Entering  getBizUserId in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int bizUserId = 0;
		int bizUserMstrId = 0;
		try {
			prepareStatement = connection
			.prepareStatement(DistportalInterface.RETREIVE_BIZ_USER_ID);
			prepareStatement.setString(1, requesterRegisterNumber);
			prepareStatement.setString(2, Constants.ActiveState);
			resultSet = prepareStatement.executeQuery();
			if (resultSet != null) {
				while (resultSet.next()) {
					if(bizUserMstrId < (resultSet.getInt("MASTER_ID"))){
						bizUserId = resultSet.getInt("BUS_USER_ID");
						bizUserMstrId = resultSet.getInt("MASTER_ID");
					}
				} 
			}else{
				bizUserId=0;
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting getBizUserId in DistportalServiceDAOImpl");
		return bizUserId;
	}

	/**
	 * 
	 * @param dto :
	 *            BusinessUserDTO
	 * @param resultSet :
	 *            ResultSet
	 * @throws SQLException
	 *             This method is called to populate DTO.
	 */

	public static void populateDto(BusinessUserDTO businessUserDTO,
			ResultSet resultSet) throws SQLException {

		businessUserDTO.setUserId(resultSet.getInt("DATA_ID"));
		businessUserDTO.setCode(resultSet.getString("USER_CODE"));
		businessUserDTO.setName(resultSet.getString("USER_NAME"));
		businessUserDTO.setTypeOfUserId(resultSet.getInt("MASTER_ID"));
		businessUserDTO.setParentId(resultSet.getInt("PARENT_ID"));
		businessUserDTO.setStatus(resultSet.getString("STATUS"));
		businessUserDTO.setContactNo(resultSet.getString("CONTACT_NO"));
		businessUserDTO.setAddress(resultSet.getString("ADDRESS"));
		businessUserDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
		businessUserDTO.setHubCode(resultSet.getString("HUB_CODE"));
		if ((resultSet.getString("FOS_CHECK_REQ")).equals("N")) {
			businessUserDTO.setFsoCheck(false);
		} else {
			businessUserDTO.setFsoCheck(true);
		}
		if ((resultSet.getString("FOS_ACTV_CHK")).equals("N")) {
			businessUserDTO.setFosActRights(false);
		} else {
			businessUserDTO.setFosActRights(true);
		}
		businessUserDTO.setLocId(resultSet.getInt("LOC_ID"));
		if ((null == (resultSet.getString("ALL_SERVICE_CLASS")) || (resultSet
				.getString("ALL_SERVICE_CLASS").equals("N")))) {
			businessUserDTO.setAllServices(false);
		} else {
			businessUserDTO.setAllServices(true);
		}
		businessUserDTO.setIncludeServices(resultSet
				.getString("INCLUDE_SERVICE"));
		businessUserDTO.setExcludeServices(resultSet
				.getString("EXCLUDE_SERVICE"));
		businessUserDTO.setCreatedBy(resultSet.getInt("CREATED_BY"));
		businessUserDTO.setCreatedDt(USSDCommonUtility.getTimestampAsString(
				resultSet.getTimestamp("CREATED_DT"), "dd-MM-yyyy-hh-mm-ss"));
		businessUserDTO.setUpdatedBy(resultSet.getInt("UPDATED_BY"));
		businessUserDTO.setUpdatedDt(USSDCommonUtility.getTimestampAsString(
				resultSet.getTimestamp("UPDATED_DT"), "dd-MM-yyyy-hh-mm-ss"));
		businessUserDTO.setTypeOfUserValue(resultSet.getString("USER_TYPE"));
		businessUserDTO.setBaseLoc(resultSet.getString("BASE_LOC"));
		businessUserDTO.setRegNumber(resultSet.getString("MOBILE_NO"));
	}

	/**
	 * This method getLatestBusinessUserCount() searches for the users who are
	 * created or modified in the range provided
	 * 
	 * @param modifiedFromDate
	 *            one parameter on which count is returned
	 * @param modifiedtoDate
	 *            other parameter on which count is returned
	 * @return a int that counts all the users who is created or modified in the
	 *         range provided
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
	public int getLatestBusinessUserCount(String modifiedFromDate,
			String modifiedtoDate) throws DAOException {
		logger
		.debug("Entering method getLatestBusinessUserCount()of DistportalServiceDAOImpl");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());

			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(DistportalInterface.SQL_BIZ_USER_COUNT);
			prepareStatement.setTimestamp(1, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(2, modifiedtoDateTimestamp);
			prepareStatement.setTimestamp(3, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(4, modifiedtoDateTimestamp);
			ResultSet countLoc = prepareStatement.executeQuery();
			if (countLoc.next()) {
				noofRow = countLoc.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting method getLatestBusinessUserCount() of DistportalServiceDAOImpl");
		return noofPages;
	}

	/**
	 * This method getBusinessUserDetails() searches for the users who are
	 * created or modified in the range provided
	 * 
	 * @param modifiedFromDate
	 *            one parameter on which count is returned
	 * @param modifiedtoDate
	 *            other parameter on which count is returned
	 * @return RequesterDTO that contains detail of all teh users who are
	 *         created or modified in the range provided
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */

	public RequesterDTO getBusinessUserDetails(String modifiedFromDate,
			String modifiedtoDate, int intLb, int intUb) throws DAOException {
		logger
		.debug("Entering  getBusinessUserDetails in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		RequesterDTO requesterDTO = new RequesterDTO();
		BusinessUserDTO businessUserDTO = null;
		BusinessUserDTO[] businessUserDTO1 = new BusinessUserDTO[10];
		Connection connection = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyDate = new String(modifiedFromDate);
			java.util.Date dt = sdf.parse(newModifyDate);
			Timestamp modifiedFromDateTimestamp = new Timestamp(dt.getTime());

			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
			String newModifyToDate = new String(modifiedtoDate);
			java.util.Date dt1 = sdf1.parse(newModifyToDate);
			Timestamp modifiedtoDateTimestamp = new Timestamp(dt1.getTime());

			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(DistportalInterface.RETRIEVE_BIZ_USER_DATA);
			prepareStatement.setTimestamp(1, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(2, modifiedtoDateTimestamp);
			prepareStatement.setTimestamp(3, modifiedFromDateTimestamp);
			prepareStatement.setTimestamp(4, modifiedtoDateTimestamp);
			prepareStatement.setString(5, String.valueOf(intUb));
			prepareStatement.setString(6, String.valueOf(intLb));
			resultSet = prepareStatement.executeQuery();
			int position = 0;
			while (resultSet.next()) {
				businessUserDTO = new BusinessUserDTO();
				businessUserDTO.setUserId(resultSet.getInt("DATA_ID"));
				businessUserDTO.setCode(resultSet.getString("USER_CODE"));
				businessUserDTO.setName(resultSet.getString("USER_NAME"));
				businessUserDTO.setTypeOfUserId(resultSet.getInt("MASTER_ID"));
				businessUserDTO.setParentId(resultSet.getInt("PARENT_ID"));
				businessUserDTO.setLocId(resultSet.getInt("LOC_ID"));
				businessUserDTO.setBaseLoc(resultSet.getString("BASE_LOC"));
				if ((resultSet.getString("CIRCLE_CODE")) != null) {
					businessUserDTO.setCircleCode(resultSet
							.getString("CIRCLE_CODE"));
				} else {
					businessUserDTO.setCircleCode("");
				}
				businessUserDTO.setStatus(resultSet.getString("STATUS"));
				businessUserDTO.setRegNumber(resultSet.getString("MOBILE_NO"));
				businessUserDTO.setTypeOfUserValue(resultSet
						.getString("USER_TYPE"));
				businessUserDTO.setCreatedDt(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("CREATED_DT"),
						"dd-MM-yyyy-HH-mm-ss"));
				businessUserDTO.setUpdatedDt(USSDCommonUtility
						.getTimestampAsString(resultSet
								.getTimestamp("UPDATED_DT"),
						"dd-MM-yyyy-HH-mm-ss"));
				// inserting in object array
				if (businessUserDTO != null) {
					businessUserDTO1[position++] = businessUserDTO;
				}

			}
			if (businessUserDTO == null) {
				requesterDTO = null;
			} else {
				requesterDTO.setBusinessUserArray(businessUserDTO1);
			}

		} catch (SQLException sqe) {
			requesterDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			requesterDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			requesterDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			requesterDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting getBusinessUserDetails() in DistportalServiceDAOImpl");
		return requesterDTO;
	}

	/**
	 * This method returns all business user for specific circle and role id
	 * 
	 * @param userRoleList
	 * @param circleCode
	 * @return
	 * @throws DAOException
	 */
	/*
	public ArrayList getUserInfoByRole(ArrayList userRoleList, String circleCode)
	throws DAOException {

		logger
		.debug("Entering  getUserRoleByReportId in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		ArrayList userInfoList = new ArrayList();
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			SMSReportDTO smsReportDTO = null;
			String strQuery = null;
			if (userRoleList != null && !userRoleList.isEmpty())
				for (int index = 0, size = userRoleList.size(); index < size; index++) {
					smsReportDTO = (SMSReportDTO) userRoleList.get(index);
					circleCode = smsReportDTO.getCircleCode();
					if (circleCode == null || "".equals(circleCode)) {
						strQuery = SMSReportDAOInterface.SELECT_USER_INFO_BY_ROLE_AND_CIRCLE
						.concat(" AND CIRCLE_CODE IS NULL WITH UR");
					} else {
						strQuery = SMSReportDAOInterface.SELECT_USER_INFO_BY_ROLE_AND_CIRCLE
						.concat("  AND CIRCLE_CODE = '").concat(
								circleCode).concat("' WITH UR");
					}
					prepareStatement = connection.prepareStatement(strQuery);
					logger.debug("sqlQuery == " + strQuery);
					prepareStatement.setString(1, smsReportDTO.getUserRole());
					resultSet = prepareStatement.executeQuery();
					while (resultSet.next()) {
						BusinessUserDTO businessUserDTO = new BusinessUserDTO();
						businessUserDTO.setCircleCode(resultSet
								.getString("CIRCLE_CODE"));
						businessUserDTO.setHubCode(resultSet
								.getString("HUB_CODE"));
						businessUserDTO.setContactNo(resultSet
								.getString("CONTACT_NO"));
						businessUserDTO.setRegNumber(resultSet
								.getString("MOBILE_NO"));
						businessUserDTO
						.setStatus(resultSet.getString("STATUS"));
						businessUserDTO.setTypeOfUserId(resultSet
								.getInt("MASTER_ID"));
						if ((resultSet.getString("FOS_ACTV_CHK")).equals("N")) {
							businessUserDTO.setFosActRights(false);
						} else {
							businessUserDTO.setFosActRights(true);
						}
						businessUserDTO.setTypeOfUserValue(resultSet
								.getString("USER_TYPE"));
						businessUserDTO.setLocId(resultSet
								.getInt("LOC_ID"));
						userInfoList.add(businessUserDTO);
					}
				}
			if (userInfoList.size() != 0) {
				return userInfoList;
			} else {
				logger.error("Users info not found for circleCode "
						+ circleCode);
				throw new DAOException("Users info not found for circleCode "
						+ circleCode);
			}
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
			logger
			.debug("Exiting getUserRoleByReportId in DistportalServiceDAOImpl");
		}

	}
	*/
	/**
	 * This method getRequesterDetails() searches for the register number from
	 * database and gives all the details about that number.
	 * 
	 * @param requesterRegisterNumber
	 *            one parameter on which data can be fetch the details of the
	 *            registration.
	 * @return a BusinessUserDTO that contails all the details of the particular
	 *         register number
	 * 
	 * @exception DAOException,when
	 *                there is some problem while creating an object in
	 *                database.
	 */
//	public BusinessUserDTO getRequesterDetailsOfAllUserType(String requesterRegisterNumber)
//	throws DAOException {
//		logger
//		.debug("Entering  getRequesterDetails in DistportalServiceDAOImpl");
//		PreparedStatement prepareStatement = null;
//		PreparedStatement prepareStatement1 = null;
//		ResultSet resultSet = null;
//		BusinessUserDTO businessUserDTO = new BusinessUserDTO();
//		Connection connection = null;
//		Connection connection1 = null;
//		try {
//			connection = DBConnection
//			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
//			prepareStatement = connection
//			.prepareStatement(DistportalInterface.RETRIEVE_REQUESTER_DETAILS);
//			prepareStatement.setString(1, requesterRegisterNumber);
//			prepareStatement.setString(2, Constants.ActiveState);
//			prepareStatement.setString(3, Constants.ActiveState);
//			resultSet = prepareStatement.executeQuery();
//			if (resultSet.next()) {
//				populateDto(businessUserDTO, resultSet);
//	    } else {
//				businessUserDTO = null;
//			}
//		} catch (SQLException sqe) {
//			businessUserDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
//			businessUserDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
//			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
//			throw new DAOException(sqe.getMessage(), sqe);
//
//		} catch (Exception ex) {
//			logger.error("Exception" + ex.getMessage(), ex);
//			businessUserDTO.setErrorCode(Utility.getValueFromBundle(
//					Constants.Error_Code3,
//					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
//			businessUserDTO.setErrorMessage(String.valueOf(ex.getMessage()));
//			throw new DAOException(ex.getMessage(), ex);
//
//		} finally {
//			try {
//				DBConnectionUtil.closeDBResources(connection, prepareStatement,
//						resultSet);
//				DBConnectionUtil.closeDBResources(connection1,
//						prepareStatement1, resultSet);
//			} catch (Exception e) {
//				businessUserDTO.setErrorMessage(String.valueOf(e.getMessage()));
//				logger
//				.debug("Exception occured when trying to close database resources");
//			}
//		}
//		logger.debug("Exiting getRequesterDetails in DistportalServiceDAOImpl");
//		return businessUserDTO;
//	}
	
	
	public ArrayList getRequesterDetailsOfAllUserType(String requesterRegisterNumber)
	throws DAOException {
		logger
		.debug("Entering  getRequesterDetails in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList businessUsers = new ArrayList();
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			 .prepareStatement(DistportalInterface.RETRIEVE_REQUESTER_DETAILS_SMS);
			prepareStatement.setString(1, requesterRegisterNumber);
			prepareStatement.setString(2, Constants.ActiveState);
			prepareStatement.setString(3, Constants.ActiveState);
			resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					BusinessUserDTO businessUserDTO = new BusinessUserDTO();
					populateDto(businessUserDTO, resultSet);
					businessUsers.add(businessUserDTO);
				} 
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement, resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getRequesterDetails in DistportalServiceDAOImpl");
		return businessUsers;
	}
	
	
	public BusinessUserDTO getBusinessUserDetailsById(int parentId)
	throws DAOException {
		logger
		.debug("Entering  getBusinessUserDetailsById in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		BusinessUserDTO businessUserDTO = new BusinessUserDTO();
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			.prepareStatement(DistportalInterface.RETRIEVE_REQUESTER_INFO);
			prepareStatement.setInt(1, parentId);
			prepareStatement.setString(2, Constants.ActiveState);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				populateDto(businessUserDTO, resultSet);
			} else {
				businessUserDTO = null;
			}

		} catch (SQLException sqe) {
			businessUserDTO.setErrorCode(String.valueOf(sqe.getErrorCode()));
			businessUserDTO.setErrorMessage(String.valueOf(sqe.getMessage()));
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			businessUserDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code3,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			businessUserDTO.setErrorMessage(String.valueOf(ex.getMessage()));
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				businessUserDTO.setErrorMessage(String.valueOf(e.getMessage()));
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger
		.debug("Exiting getBusinessUserDetailsById in DistportalServiceDAOImpl");
		return businessUserDTO;
	}

	
	public BusinessUserDTO getNewRequesterDetailsOfAllUserType(String requesterRegisterNumber)
	throws DAOException {
		logger
		.debug("Entering  getRequesterDetails in DistportalServiceDAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		BusinessUserDTO businessUserDTO = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
			 .prepareStatement(DistportalInterface.RETRIEVE_REQUESTER_DETAILS_SMS);
			prepareStatement.setString(1, requesterRegisterNumber);
			prepareStatement.setString(2, Constants.ActiveState);
			prepareStatement.setString(3, Constants.ActiveState);
			resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					businessUserDTO = new BusinessUserDTO();
					populateDto(businessUserDTO, resultSet);
					if(businessUserDTO.getTypeOfUserId()==Constants.ED){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.CEO){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.SALES_HEAD){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.ZBM){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.ZSM){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.TM){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.DISTIBUTOR){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.FOS){
						break;
					}else if(businessUserDTO.getTypeOfUserId()==Constants.DEALER){
						break;
					}
					
				} 
		} catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			logger.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement, resultSet);
			} catch (Exception e) {
				logger
				.debug("Exception occured when trying to close database resources");
			}
		}
		logger.debug("Exiting getRequesterDetails in DistportalServiceDAOImpl");
		return businessUserDTO;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	/**
	 * @param logger the logger to set
	 */
	public static void setLogger(Logger logger) {
		DistportalServicesDaoImpl.logger = logger;
	}
}
