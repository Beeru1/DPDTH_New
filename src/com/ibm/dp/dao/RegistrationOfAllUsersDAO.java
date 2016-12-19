/**
 * 
 */
package com.ibm.dp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

import com.ibm.dp.beans.RegistrationOfAllBean;
import com.ibm.dp.common.USSDConstants;
import com.ibm.dp.dao.BusinessUserInterface;
import com.ibm.dp.dto.LocationDataDTO;
import com.ibm.dp.dto.RegistrationOfAllDTO;
import com.ibm.dp.dto.ServiceClassDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
//import com.ibm.virtualization.ussdactivationweb.exception.VCircleMstrDaoException;
import com.ibm.utilities.Utility;

/**************************************************************************
 * File     : RegistrationOfAllUsersDAO.java
 * Author   : Abhipsa
 * Created  : Sept 10, 2008
 * Modified : Sept 10, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sept 10, 2008 	Abhipsa	First Cut.
 * V0.2		Sept 10, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
public class RegistrationOfAllUsersDAO {

	private static final Logger logger = Logger
			.getLogger(RegistrationOfAllUsersDAO.class.toString());

	/**
	 * This method interacts with database and insert the data in the table.
	 * Also checks whether the user exists in table or not.
	 * Checks for valid service classes also.
	 * 
	 * 
	 * @param form :
	 *            RegistrationOfAllDTO
	 * @param intUserId :
	 *            int
	 * @return Sting constant
	 */
	/*public String insert(RegistrationOfAllDTO form, int intUserId){
//			throws DAOException {
//		logger
//				.debug("Entering method insert() in class RegistrationOfAllUsersDAO.One of the parameter used , UserId : "
//						+ intUserId);
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement preparedStatement2 = null;
		PreparedStatement preparedStatement3 = null;
		PreparedStatement preparedStatement5 = null;
		String status = "";
		int businessId = 0;
		String RETURN_CONSTANT = null;
		ResultSet rs3 = null;
		String fsoCheck = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			// checking availability of registered number and code
			status = checkAvailability(form.getCircleCode(), form.getCode(),
					form.getTypeOfUserId());
			if (status.equalsIgnoreCase(USSDConstants.VALID)) {
				status = checkRegNoAvail(form.getRegNumber(),form.getTypeOfUserId());
			}
			// Checking for Service Class
			String strInput = "";
			String delimiter = ",";
			ArrayList arrayList = new ArrayList();
			if (form.getIncludeServiceClass() != null) {
				strInput = form.getIncludeServiceClass();
			} else {
				if (form.getExcludeServiceClass() != null) {
					strInput = form.getExcludeServiceClass();
				}
			}
			if (strInput.length() != 0) {
				if (strInput.indexOf(delimiter) != -1) {
					String arr[] = strInput.split(",");
					for (int num = 0, len = arr.length; num < len; num++) {
						arrayList.add(arr[num].trim());
						
					}
				} else {
					arrayList.add(strInput);
				}
				for (int i = 0; i < arrayList.size(); i++) {
					// for null object
					if (arrayList.get(i).toString().length() == 0) {
						return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
					}
					
					// length check of service class
					String serviceclass=arrayList.get(i).toString();
					if(serviceclass.length()>9)
					{
						return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
					}
					ServiceClassDTO serviceClassDTO = new ServiceClassDTO();
					ServiceClassDAOImpl dao = new ServiceClassDAOImpl();
					serviceClassDTO = dao.getServiceClassListByIdCodeActive(Long.parseLong(serviceclass), form
							.getCircleCode());
					if (serviceClassDTO == null) {
						return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
					}
				}
			}
			// if code of the is available
			if (status.equalsIgnoreCase(USSDConstants.VALID)) {
				// check the fos check required or not 
				if (form.isFosCheck()) {
					fsoCheck = USSDConstants.Y;
				} else {
					fsoCheck = USSDConstants.N;
				}
				if (form.getAepfCheck().equals(USSDConstants.Y)) {
					fsoCheck = USSDConstants.Y;
				} else {
					if (form.isFosCheck()) {
						fsoCheck = USSDConstants.Y;
					} else {
						fsoCheck = USSDConstants.N;
					}
				}
				// generating code from sequences.
				if (form.getTypeOfUserId() != 7) {
					String prefix = "";
					int code = 0;
					preparedStatement1 = connection
							.prepareStatement(BusinessUserInterface.Code_for_Business_User_Data);
					ResultSet rs1 = preparedStatement1.executeQuery();
					if (rs1.next()) {
						code = rs1.getInt(1);
					}
					preparedStatement2 = connection
							.prepareStatement(BusinessUserInterface.Prefix_For_Code);
					preparedStatement2.setInt(1, form.getTypeOfUserId());
					ResultSet rs = preparedStatement2.executeQuery();
					if (rs.next()) {
						prefix = rs.getString(USSDConstants.CODE_PREFIX);
					}
					String fullCode = "" + prefix + "" + code;
					form.setCode(fullCode);

				}
				preparedStatement3 = connection
						.prepareStatement(BusinessUserInterface.GET_NEXT_BusinessUser_ID);
				rs3 = preparedStatement3.executeQuery();
				if (rs3.next()) {
					businessId = rs3.getInt(1);
				}

				preparedStatement = connection
						.prepareStatement(BusinessUserInterface.INSERT_Business_User_Data);
				int intCol = 1;
				preparedStatement.setInt(intCol++, businessId);
				preparedStatement.setString(intCol++, form.getCode());
				preparedStatement.setString(intCol++, form.getBusinessUserName());
				preparedStatement.setInt(intCol++, form.getTypeOfUserId());
				preparedStatement.setInt(intCol++, 0);
				preparedStatement.setString(intCol++, USSDConstants.ActiveState);
				preparedStatement.setString(intCol++, form.getContactNo());
				preparedStatement.setString(intCol++, form.getAddress());
				preparedStatement.setString(intCol++, form.getCircleCode());
				preparedStatement.setString(intCol++, form.getHubCode());
				preparedStatement.setString(intCol++, fsoCheck);
				preparedStatement.setString(intCol++, form.getFosActRights());
				preparedStatement.setInt(intCol++, form.getLocId());
				preparedStatement.setInt(intCol++, intUserId);
				preparedStatement.setInt(intCol++, intUserId);
				preparedStatement
						.setString(intCol++, form.getAllServiceClass());
				preparedStatement.setString(intCol++, form
						.getIncludeServiceClass());
				preparedStatement.setString(intCol++, form
						.getExcludeServiceClass());
				preparedStatement.executeUpdate();

				// updating in Registered number table
				preparedStatement5 = connection
						.prepareStatement(BusinessUserInterface.INSERT_STMT_DIST);
				preparedStatement5.setString(1, form.getRegNumber());
				preparedStatement5.setInt(2, businessId);
				preparedStatement5.setString(3,USSDConstants.ActiveState);
				preparedStatement5.setInt(4, intUserId);
				preparedStatement5.setInt(5, intUserId);
				preparedStatement5.executeUpdate();
				logger
						.debug("data inserted successfully in the database for code:"
								+ form.getCode());
				RETURN_CONSTANT = "INSERTED"+form.getCode();
			} else {
				RETURN_CONSTANT = status;
			}
			logger
					.debug("Exiting method insert() in class RegistrationOfAllUsersDAO");
		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while inserting FSO data in the database for code:"
							+ form.getCode(), e);
		} finally {
			try {
				DBConnectionManager.releaseResources(preparedStatement, null);
				DBConnectionManager.releaseResources(preparedStatement1, null);
				DBConnectionManager.releaseResources(preparedStatement2, null);
				DBConnectionManager.releaseResources(connection);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class insert.",
						e);
			}
		}
		return RETURN_CONSTANT;
	}
	*/	
	/**
	 * This method checks if the user id which we are trying to insert in the
	 * table , is already there in the table or not.
	 * 
	 * 
	 * @param circleCode :
	 *            String
	 * @param strCode :
	 *            String
	 * @param typeOfUser :
	 *            int
	 * @return Sting constant
	 */
	/*
	public String checkAvailability(String circleCode, String strCode,
			int typeOfUser) throws DAOException {
		logger
				.debug("Entering method checkAvailability() in class RegistrationOfAllUsersDAO.Parameters used are , UserCode : "
						+ strCode
						+ " circleCode : "
						+ circleCode
						+ " typeoOfUser : " + typeOfUser);
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		String RETURN_CONSTANT = USSDConstants.VALID;
		try {
			connection = DBConnectionManager.getDBConnection();
			preparedStatement = connection
					.prepareStatement(BusinessUserInterface.CHECK_Avilable_User);
			preparedStatement.setString(1, strCode);
			preparedStatement.setInt(2, typeOfUser);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getInt(1) > 0) {
					RETURN_CONSTANT = USSDConstants.CODE_EXIST;
				} else {
					RETURN_CONSTANT = USSDConstants.VALID;
				}
			}
		} catch (SQLException e) {
			RETURN_CONSTANT = USSDConstants.INVALID;
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						preparedStatement, resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class checkAvailability.",
						e);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				if (resultSet.getInt(1) > 0) {
					RETURN_CONSTANT = USSDConstants.CODE_EXIST;
				} else {
					RETURN_CONSTANT = USSDConstants.VALID;
				}
			}
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection,
						preparedStatement, resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class checkAvailability.");
			}
		}
		logger
				.debug("Exiting method checkRegNoAvail() in class RegistrationOfAllUsersDAO");
		return RETURN_CONSTANT;
		}
	}
	*/
	/**
	 * This method retrievs the list of Users from the database. List is based
	 * on three conditions. 1) All - all users are shown 2) Code - Users where
	 * the code is like given code 3) Name - Users where the name is like given
	 * name.
	 * 
	 * 
	 * @param strCircleId :
	 *            String
	 * @param search :
	 *            String
	 * @param name :
	 *            String
	 * @param code :
	 *            String
	 * @param status :
	 *            String
	 * @param regNumber :
	 *            String
	 * @param typeOfUser :
	 *            int
	 * @param Lb :
	 *            int
	 * @param Ub :
	 *            int
	 * @return List
	 */
	/*
	public List getBussinessUserList(String strCircleId, int typeOfUser,
			String search, String name, String code, String status,
			String regNumber, int intLb, int intUb,String identifier) {
		logger
				.debug("Entering method getBussinessUserList() in class RegistrationOfAllUsersDAO Parameters used are , CircleId : "
						+ strCircleId
						+ " typeOfUser : "
						+ typeOfUser
						+ " searchType : "
						+ search
						+ " RegisteredNumber : "
						+ regNumber);
		ArrayList usersList = new ArrayList();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			if (typeOfUser < 0) {
				if (strCircleId == null) {
					RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
					dto.setBussinessUserId(null);
					dto.setBusinessUserName(null);
					dto.setCircleCode(null);
					usersList.add(dto);
				}
			} else {
				RegistrationOfAllDTO dto = null;
				// only active users
				if(identifier.equalsIgnoreCase(USSDConstants.singleUpload) || identifier.equalsIgnoreCase(USSDConstants.bulkUpload) )
				{
					
					if (search.equals(USSDConstants.all)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Business_User_Active);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, USSDConstants.ActiveState);
							prepareStatement.setString(3, String.valueOf(intUb));
							prepareStatement.setString(4, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Business_User_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3,USSDConstants.ActiveState);
							prepareStatement.setString(4, String.valueOf(intUb));
							prepareStatement.setString(5, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.Code)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Busniess_User_CODE_Active);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, code);
							prepareStatement.setString(3,USSDConstants.ActiveState);
							prepareStatement.setString(4, String.valueOf(intUb));
							prepareStatement.setString(5, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Busniess_User_CODE_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, code);
							prepareStatement.setString(4,USSDConstants.ActiveState);
							prepareStatement.setString(5, String.valueOf(intUb));
							prepareStatement.setString(6, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.Name)) {
						if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Business_user_NAME_Active);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, name);
								prepareStatement.setString(3,USSDConstants.ActiveState);
								prepareStatement.setString(4, String.valueOf(intUb));
								prepareStatement.setString(5, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Business_user_NAME_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, name);
							prepareStatement.setString(4,USSDConstants.ActiveState);
							prepareStatement.setString(5, String.valueOf(intUb));
							prepareStatement.setString(6, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.regNumber)) {
						if (status.equals(USSDConstants.all)) {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SELECT_STMT_CEO_ED_REGNUMBER_Business_User_Active);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3,USSDConstants.ActiveState);
								prepareStatement.setString(4, String.valueOf(intUb));
								prepareStatement
										.setString(5, String.valueOf(intLb + 1));
							}else{
								prepareStatement = connection
										.prepareStatement(BusinessUserInterface.SELECT_STMT_REGNUMBER_Business_User_Active);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								prepareStatement.setString(4,USSDConstants.ActiveState);
								prepareStatement.setString(5, String.valueOf(intUb));
								prepareStatement
										.setString(6, String.valueOf(intLb + 1));
							}
						} else {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SELECT_STMT_CEO_ED_REGNUMBER_Business_User_STATUS_Active);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, status);
								prepareStatement.setString(4,USSDConstants.ActiveState);
								prepareStatement.setString(5, String.valueOf(intUb));
								prepareStatement
										.setString(6, String.valueOf(intLb + 1));
							}else{
								prepareStatement = connection
										.prepareStatement(BusinessUserInterface.SELECT_STMT_REGNUMBER_Business_User_STATUS_Active);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								prepareStatement.setString(4, status);
								prepareStatement.setString(5,USSDConstants.ActiveState);
								prepareStatement.setString(6, String.valueOf(intUb));
								prepareStatement
										.setString(7, String.valueOf(intLb + 1));
							}
						}
					}
				}//end of upper if
				// all users
				else
				{
					if (search.equals(USSDConstants.all)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Business_User);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, String.valueOf(intUb));
							prepareStatement.setString(3, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Business_User);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, String.valueOf(intUb));
							prepareStatement.setString(4, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.Code)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Busniess_User_CODE);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, code);
							prepareStatement.setString(3, String.valueOf(intUb));
							prepareStatement.setString(4, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Busniess_User_CODE);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, code);
							prepareStatement.setString(4, String.valueOf(intUb));
							prepareStatement.setString(5, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.Name)) {
						if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_CEO_ED_Business_user_NAME);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, name);
								prepareStatement.setString(3, String.valueOf(intUb));
								prepareStatement.setString(4, String.valueOf(intLb + 1));
						}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SEARCH_STMT_ALL_Business_user_NAME);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, name);
							prepareStatement.setString(4, String.valueOf(intUb));
							prepareStatement.setString(5, String.valueOf(intLb + 1));
						}
					} else if (search.equals(USSDConstants.regNumber)) {
						if (status.equals(USSDConstants.all)) {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SELECT_STMT_CEO_ED_REGNUMBER_Business_User);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, String.valueOf(intUb));
								prepareStatement
										.setString(4, String.valueOf(intLb + 1));
							}else{
								prepareStatement = connection
										.prepareStatement(BusinessUserInterface.SELECT_STMT_REGNUMBER_Business_User);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								prepareStatement.setString(4, String.valueOf(intUb));
								prepareStatement
										.setString(5, String.valueOf(intLb + 1));
							}
						} else {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.SELECT_STMT_CEO_ED_REGNUMBER_Business_User_STATUS);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, status);
								prepareStatement.setString(4, String.valueOf(intUb));
								prepareStatement
										.setString(5, String.valueOf(intLb + 1));
							}else{
								prepareStatement = connection
										.prepareStatement(BusinessUserInterface.SELECT_STMT_REGNUMBER_Business_User_STATUS);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								prepareStatement.setString(4, status);
								prepareStatement.setString(5, String.valueOf(intUb));
								prepareStatement
										.setString(6, String.valueOf(intLb + 1));
							}
						}
					}
				}
				resultSet = prepareStatement.executeQuery();
				while (resultSet.next()) {
					dto = new RegistrationOfAllDTO();
					dto.setBussinessUserId(resultSet.getString("DATA_ID"));
					dto.setBusinessUserName(resultSet.getString("USER_NAME"));
					dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
					dto.setStatus(resultSet.getString("STATUS"));
					dto.setRegNumber(resultSet.getString("MOBILE_NO"));
					usersList.add(dto);
				}
			}
			logger
					.debug("Exiting method getBussinessUserList() in class RegistrationOfAllUsersDAO");
		} catch (DAOException e) {
			new DAOException("Exception occur while getting all users.");
		} catch (SQLException e) {

			new DAOException("Exception occur while getting all users.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method getBussinessUserList() of class SearchDistributorDAO.");

			}
		}
		return usersList;
	}
	*/
	/**
	 * This method retrievs the list of The Type of Users from the database.
	 * 
	 * @return List
	 */
	/*
	public List getUserTypeList(String admin) {
		logger
				.debug("Entering method getUserTypeList() in class RegistrationOfAllUsersDAO");
		ArrayList usersList = new ArrayList();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		RegistrationOfAllDTO dto = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			String query = BusinessUserInterface.SEARCH_USER_TYPE_ALL;
			if(admin == USSDConstants.CIRCLE_ADMIN){
				query += " AND BASE_LOC NOT IN (?)";
			}
			if(admin == USSDConstants.Zone_User){
				query += " AND BASE_LOC NOT IN (?,?)";
			}
			prepareStatement = connection
					.prepareStatement(query);
			prepareStatement.setString(1, USSDConstants.ActiveState);
			if(admin == USSDConstants.CIRCLE_ADMIN){
				prepareStatement.setString(2, "Hub");
			}
			if(admin == USSDConstants.Zone_User){
				prepareStatement.setString(2, "Hub");
				prepareStatement.setString(3, "Circle");
			}
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				dto = new RegistrationOfAllDTO();
				dto.setTypeOfUserId(resultSet.getInt("MASTER_ID"));
				dto.setTypeOfUserValue(resultSet.getString("USER_TYPE"));
				dto.setStatus(resultSet.getString("STATUS"));
				dto.setParentId(resultSet.getInt("PARENT_ID"));
				dto.setBaseLoc(resultSet.getString("BASE_LOC"));
				if(resultSet.getString("USER_MAPPING")==null || (("null").equals(resultSet.getString("USER_MAPPING"))) || (("").equals(resultSet.getString("USER_MAPPING"))) ){
					// do nothing
				}else{
					dto.setMappingType(resultSet.getString("USER_MAPPING"));
				}
				usersList.add(dto);
			}
			logger
					.debug("Exiting method getUserTypeList() in class RegistrationOfAllUsersDAO");
		} catch (DAOException e) {
			new DAOException("Exception occur while getting all users type.");
		} catch (SQLException e) {
			new DAOException("Exception occur while getting all users type.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method getUserTypeList() of class SearchDistributorDAO.");

			}
		}
		return usersList;
	}
	*/
	/**
	 * This method gets the base loction from Master Table for a given id.
	 * 
	 * @param masterId :
	 *            int
	 * @return String
	 */
	/*
	public String getBaseLoc(int masterid) {
		logger
				.debug("Entering method getBaseLoc() in class RegistrationOfAllUsersDAO.Paramter Used is MasterId : "
						+ masterid);
		String baseLoc = "";
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			prepareStatement = connection
					.prepareStatement(BusinessUserInterface.SEARCH_USER_TYPE_BASE_LOC);
			prepareStatement.setString(1, USSDConstants.ActiveState);
			prepareStatement.setInt(2, masterid);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				baseLoc = resultSet.getString("BASE_LOC");
			}
		} catch (DAOException e) {
			new DAOException("Exception occur while getting base loc.");
		} catch (SQLException e) {
			new DAOException("Exception occur while getting base loc.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method getBaseLoc() of class SearchDistributorDAO.");

			}
		}
		logger
				.debug("Exiting method getBaseLoc() in class RegistrationOfAllUsersDAO");
		return baseLoc;
	}
*/
	/**
	 * This method gets the details of te user from the database on the bases of
	 * the Id.
	 * 
	 * @param Ub :
	 *            int
	 * @return RegistrationOfAllDTO
	 */
	/*
	public RegistrationOfAllDTO getUserProfile(int intId) {
		logger
				.debug("Entering method getUserProfile() in class RegistrationOfAllUsersDAO.Parameter used is , UserId : "
						+ intId);
		RegistrationOfAllDTO Dto = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
		LocationDataDAO locDao = new LocationDataDAO();
		Connection connection1 = null;
		PreparedStatement prepareStatement1 = null;
		PreparedStatement prepareStatement2 = null;
		ResultSet resultSet1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			prepareStatement = connection
					.prepareStatement(BusinessUserInterface.DIST_PROFILE_Business_User);
			prepareStatement.setInt(1, intId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				Dto = new RegistrationOfAllDTO();
				Dto.setAddress(resultSet.getString("ADDRESS"));
				Dto.setCode(resultSet.getString("USER_CODE"));
				Dto.setContactNo(resultSet.getString("CONTACT_NO"));
				Dto.setBussinessUserId(String.valueOf(resultSet
						.getInt("DATA_ID")));
				Dto.setBusinessUserName(resultSet.getString("USER_NAME"));
				Dto.setStatus(resultSet.getString("STATUS"));
				Dto.setCreatedDate(resultSet.getDate("CREATED_DT"));
				Dto.setCreatedBy(resultSet.getInt("CREATED_BY"));
				Dto.setUpdatedBy(resultSet.getInt("UPDATED_BY"));
				Dto.setUpdatedDate(resultSet.getDate("UPDATED_DT"));
				Dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				Dto.setHubCode(resultSet.getString("HUB_CODE"));
				Dto.setFsoCheck(resultSet.getString("FOS_CHECK_REQ"));
				Dto.setFosActRights(resultSet.getString("FOS_ACTV_CHK"));
				Dto.setUpdatedBy(resultSet.getInt("PARENT_ID"));
				Dto.setTypeOfUserId(resultSet.getInt("MASTER_ID"));
				Dto.setLocId(resultSet.getInt("LOC_ID"));
				Dto
						.setAllServiceClass(resultSet
								.getString("ALL_SERVICE_CLASS"));
				Dto.setExcludeServiceClass(resultSet
						.getString("EXCLUDE_SERVICE"));
				Dto.setIncludeServiceClass(resultSet
						.getString("INCLUDE_SERVICE"));
				Dto.setTypeOfUserValue(resultSet.getString("USER_TYPE"));

			}
			// getting the base location of the user.
			Dto.setBaseLoc(getBaseLoc(Dto.getTypeOfUserId()));

			// getting all the location details of the user , throught upward
			// location search query.
			connection = DBConnectionManager.getDBConnection();
			prepareStatement1 = connection1
					.prepareStatement(BusinessUserInterface.Loc_Upwards_Query);
			prepareStatement1.setInt(1, Dto.getLocId());
			resultSet1 = prepareStatement1.executeQuery();
			while (resultSet1.next()) {
				if (resultSet1.getInt("LOC_MSTR_ID") == USSDConstants.City) {
					if(resultSet1.getInt("STATUS")==USSDConstants.Active)
					{
						Dto.setCityId(resultSet1.getInt("LOC_DATA_ID"));
					}
					else
					{
						Dto.setCityId(0);
					}
					
				}
				if (resultSet1.getInt("LOC_MSTR_ID") == USSDConstants.Zone) {
					if(resultSet1.getInt("STATUS")==USSDConstants.Active)
					{
						Dto.setZoneId(resultSet1.getInt("LOC_DATA_ID"));
					}
					else
					{
						Dto.setZoneId(0);
					}
					
				}
			}

			// setting the lists of locations
			Dto.setHubList(circledao.getHubList());
			if (Dto.getBaseLoc().equals(USSDConstants.strCircle)
					|| Dto.getBaseLoc().equals(USSDConstants.strZone)
					|| Dto.getBaseLoc().equals(USSDConstants.strCity)) {
				Dto.setCircleList(circledao
						.getCircleListByHub(Dto.getHubCode()));
//				 Setting empty list
				if(Dto.getCircleList().isEmpty())
				{
					ArrayList emptyList = new ArrayList();
					LocationDataDTO locDTO = new LocationDataDTO();
					locDTO.setLocDataId(0);
					locDTO.setLocationDataName("");
					emptyList.add(locDTO);
					Dto.setCircleList(emptyList);
				}
				
			}
			if (Dto.getBaseLoc().equals(USSDConstants.strZone)
					|| Dto.getBaseLoc().equals(USSDConstants.strCity)) {

				Dto.setZoneList(locDao.getLocationList(Dto.getCircleCode(), USSDConstants.Zone,USSDConstants.PAGE_FALSE,0,0));
//				 Setting empty list
				if(Dto.getZoneList().isEmpty())
				{
					ArrayList emptyList = new ArrayList();
					LocationDataDTO locDTO = new LocationDataDTO();
					locDTO.setLocDataId(0);
					locDTO.setLocationDataName("");
					emptyList.add(locDTO);
					Dto.setZoneList(emptyList);
				}
			}
			if (Dto.getBaseLoc().equals(USSDConstants.strCity)) {
				Dto.setCityList(locDao.getLocationList(String.valueOf(Dto
						.getZoneId()),
						USSDConstants.City,USSDConstants.PAGE_FALSE,0,0));
				// Setting empty list
				if(Dto.getCityList().isEmpty())
				{
					ArrayList emptyList = new ArrayList();
					LocationDataDTO locDTO = new LocationDataDTO();
					locDTO.setLocDataId(0);
					locDTO.setLocationDataName("");
					emptyList.add(locDTO);
					Dto.setCityList(emptyList);
				}
			}
			
			// getting details of registered number
			prepareStatement2 = connection
					.prepareStatement(BusinessUserInterface.VIEW_REG_NOS_All_Business_User);
			prepareStatement2.setInt(1, intId);
			resultSet2 = prepareStatement2.executeQuery();
			while (resultSet2.next()) {
				Dto.setRegNumber(resultSet2.getString("MOBILE_NO"));
				Dto.setRegNumberId(String.valueOf(resultSet2
						.getInt("MOBILE_ID")));

			}

			logger
					.debug("Exiting method getUserProfile() in class RegistrationOfAllUsersDAO");
		} catch (DAOException e) {
			new DAOException("Exception occur while getting user.");
		} catch (SQLException e) {
			new DAOException("Exception occur while getting user.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
				DBConnectionUtil.closeDBResources(connection1,
						prepareStatement1, resultSet1);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method getUserProfile() of class SearchDistributorDAO.");
			}
		}
		return Dto;

	}
*/
	/**
	 * Called from RegistrationOfAllAction for Deleting Mapping of users if its
	 * changed.
	 * 
	 * @param intId :
	 *            int
	 * @param typeOfUser :
	 *            int
	 */
	
	/*
	public void deleteMapping(int intId, int typeOfUser,RegistrationOfAllDTO regDTO) throws DAOException {
		logger
				.debug("Entering method deleteMapping() in class RegistrationOfAllUsersDAO.Paramters used are , UserId : "
						+ intId + " TypeOfUser : " + typeOfUser);
		Connection con = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs1 = null;
		ResultSet rs = null;
		String circleCode="";
		ArrayList locationlist =  new ArrayList();
		LocationDataDAO locDao = new LocationDataDAO();
		int change=1;
		try {
			connection = DBConnectionManager.getDBConnection();
			con.setAutoCommit(false);
			// self information
			pstmt1 = con
			.prepareStatement(BusinessUserInterface.GET_Avilable_User);
			pstmt1.setInt(1, intId);
			rs1 = pstmt1.executeQuery();
			
			if(rs1.next())
			{
				if(rs1.getInt("PARENT_ID")!=0){
				//parents information
				pstmt = con
				.prepareStatement(BusinessUserInterface.GET_Avilable_User);
				pstmt.setInt(1, rs1.getInt("PARENT_ID"));
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					if(("null").equalsIgnoreCase(rs.getString("CIRCLE_CODE")) || rs.getString("CIRCLE_CODE") == null){
						
						circleCode="null";
					}
					
					if(rs1.getInt("LOC_ID")!=0)
					{
						
						if(rs1.getString("BASE_LOC").equalsIgnoreCase(rs.getString("BASE_LOC")))
						{
							if( regDTO.getLocId()!=rs1.getInt("LOC_ID")){
								pstmt2 = con.prepareStatement(BusinessUserInterface.DELETE_own_MAPPING);
								pstmt2.setInt(1, 0);
								pstmt2.setInt(2, intId);
								pstmt2.executeUpdate();
								
							}
						}else{
								
							if(rs1.getInt("MASTER_ID")==USSDConstants.TM){
								locationlist = locDao.getLocationList(String.valueOf(rs.getInt("LOC_ID")), USSDConstants.City, 0, 0, 0);
								Iterator itr = locationlist.iterator();
								while(itr.hasNext()){
									LocationDataDTO dto = (LocationDataDTO)itr.next();
									if(dto.getLocDataId()==regDTO.getLocId()){
										change=0;
										break;
									}
									
								}
								if(change==1){
									pstmt2 = con.prepareStatement(BusinessUserInterface.DELETE_own_MAPPING);
									pstmt2.setInt(1, 0);
									pstmt2.setInt(2, intId);
									pstmt2.executeUpdate();
								}
							}
							
							if(rs1.getInt("MASTER_ID")==USSDConstants.ZBM){
								if(!rs1.getString("CIRCLE_CODE").equalsIgnoreCase(regDTO.getCircleCode())){
									pstmt2 = con.prepareStatement(BusinessUserInterface.DELETE_own_MAPPING);
									pstmt2.setInt(1, 0);
									pstmt2.setInt(2, intId);
									pstmt2.executeUpdate();
								}
							}
							
						}
						
					}else if(!(circleCode).equalsIgnoreCase("null") ){
						
						if(rs1.getInt("MASTER_ID")==USSDConstants.SALES_HEAD){
							if(! regDTO.getCircleCode().equalsIgnoreCase(rs1.getString("CIRCLE_CODE")) ){
								pstmt2 = con.prepareStatement(BusinessUserInterface.DELETE_own_MAPPING);
								pstmt2.setInt(1, 0);
								pstmt2.setInt(2, intId);
								pstmt2.executeUpdate();
							}
						}
						if(rs1.getInt("MASTER_ID")==USSDConstants.CEO){
							if(! regDTO.getHubCode().equalsIgnoreCase(rs1.getString("HUB_CODE")) ){
								pstmt2 = con.prepareStatement(BusinessUserInterface.DELETE_own_MAPPING);
								pstmt2.setInt(1, 0);
								pstmt2.setInt(2, intId);
								pstmt2.executeUpdate();
							}
						}
					}	
				}
			}
			}
			con.commit();
			logger
					.debug("Exiting method deleteMapping() in class RegistrationOfAllUsersDAO");
		} catch (SQLException e) {

			throw new DAOException(
					"Exception occured while deleting the maping .");
		} catch (Exception e) {
			throw new DAOException(
					"Exception occured while deleting the maping .");
		} finally {
			try {
				con.setAutoCommit(true);
				DBConnectionUtil.closeDBResources(con, pstmt,rs);
				DBConnectionUtil.closeDBResources(con, pstmt1);
				DBConnectionUtil.closeDBResources(con, pstmt2);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class RegistrationOfAllUsersDAO.");
			}
		}
	}
*/
	/**
	 * Method updates the user details in the database , including its registered Number.
	 * 
	 * @param form :
	 *            RegistrationOfAllBean
	 * 
	 * 
	 * @param intUserId :
	 *            int
	 * @param intCircleId :
	 *            int
	 * @return object of string
	 * @throws DAOException
	 */
	/*
	public String update(RegistrationOfAllDTO form, int intUserId)
			throws DAOException {
		logger
				.debug("Entering method update() in class RegistrationOfAllUsersDAO");
		Connection con = null;
		
		RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
		String RETURN_CONSTANT = "";
		String status="VALID";
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;

		try {
			connection = DBConnectionManager.getDBConnection();
//			 getting details of registered number
			pstmt = con
					.prepareStatement(BusinessUserInterface.VIEW_REG_NOS_All_Business_User);
			pstmt.setInt(1, Integer.parseInt(form.getBussinessUserId()));
			ResultSet resultSet2 = pstmt.executeQuery();
			while (resultSet2.next()) {
				dto.setRegNumber(resultSet2.getString("MOBILE_NO"));
				dto.setRegNumberId(String.valueOf(resultSet2
						.getInt("MOBILE_ID")));
			}
			
			// Checking for Service Class
			if( !dto.getRegNumber().equals(form.getRegNumber()) ){
					status = checkRegNoAvail(form.getRegNumber(),form.getTypeOfUserId());
			}
			if (status.equalsIgnoreCase(USSDConstants.VALID)) 
			{
				String strInput = "";
				String delimiter = ",";
				ArrayList arrayList = new ArrayList();
				if (form.getIncludeServiceClass() != null) {
					strInput = form.getIncludeServiceClass();
				} else {
					if (form.getExcludeServiceClass() != null) {
						strInput = form.getExcludeServiceClass();
					}
				}
				if (strInput.length() != 0) {
					if (strInput.indexOf(delimiter) != -1) {
						String arr[] = strInput.split(",");
						for (int num = 0, len = arr.length; num < len; num++) {
							arrayList.add(arr[num].trim());
	
						}
					} else {
						arrayList.add(strInput);
					}
					for (int i = 0; i < arrayList.size(); i++) {
						// for null object
						if (arrayList.get(i).toString().length() == 0) {
							return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
						}
						
	
						// length check of service class
						String serviceclass=arrayList.get(i).toString();
						if(serviceclass.length()>9)
						{
							return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
						}
						ServiceClassDTO serviceClassDTO = new ServiceClassDTO();
						ServiceClassDAOImpl dao = new ServiceClassDAOImpl();
						serviceClassDTO = dao.getServiceClassListByIdCodeActive(Long.parseLong(serviceclass), form
								.getCircleCode());
						if (serviceClassDTO == null) {
							return RETURN_CONSTANT = USSDConstants.SERVICE_CLASS_EXSISTS;
						}
					}
				}
//				 updating registered number
				String regStatus = USSDConstants.VALID;
				if(form.getStatus().equals(USSDConstants.ActiveState))
				{
					pstmt2 = con
							.prepareStatement(BusinessUserInterface.SELECT_REG_FROMALL_DIST_DEAL_FOS_Two);
					pstmt2.setString(1, form.getRegNumber());
					pstmt2.setInt(2, form.getTypeOfUserId());
					ResultSet resultSet = pstmt2.executeQuery();
					if (resultSet.next()) {
						if (resultSet.getInt(1) == 2 ) {
							regStatus = USSDConstants.CODE_EXIST;
						} else {
							regStatus = USSDConstants.VALID;
						}
					}
				}
				
				if(regStatus.equalsIgnoreCase(USSDConstants.VALID)||form.getStatus().equals(USSDConstants.InActive))
				{
					pstmt1 = con.prepareStatement(BusinessUserInterface.UPDATE_DIST);
					pstmt1.setString(1, form.getRegNumber());
					pstmt1.setString(2, String.valueOf(intUserId));
					pstmt1.setString(3, form.getStatus());
					pstmt1.setString(4, form.getRegNumberId());
					pstmt1.executeUpdate();
				}else{
					return RETURN_CONSTANT = "REGISTERNUMBER";
				}
				//updating data of business user.
				pstmt = con
						.prepareStatement(BusinessUserInterface.UPDATE_STMT_FOR_Business_User);
				int intCol = 1;
				pstmt.setString(intCol++, form.getBusinessUserName());
				pstmt.setString(intCol++, form.getContactNo());
				pstmt.setString(intCol++, form.getAddress());
				pstmt.setInt(intCol++, intUserId);
				pstmt.setString(intCol++, form.getCircleCode());
				pstmt.setString(intCol++, form.getStatus());
				pstmt.setString(intCol++, form.getFsoCheck());
				pstmt.setString(intCol++, form.getFosActRights());
				pstmt.setString(intCol++, form.getAllServiceClass());
				pstmt.setString(intCol++, form.getIncludeServiceClass());
				pstmt.setString(intCol++, form.getExcludeServiceClass());
				pstmt.setInt(intCol++, form.getLocId());
				pstmt.setString(intCol++, form.getHubCode());
				pstmt.setInt(intCol++, Integer.parseInt(form.getBussinessUserId()));
				pstmt.executeUpdate();

				
	
				pstmt1.close();
				pstmt.close();
				con.close();
				RETURN_CONSTANT = USSDConstants.UPDATED;
				logger
						.debug("Exiting method update() in class RegistrationOfAllUsersDAO");
			}else
			{
				RETURN_CONSTANT = "REGISTERNUMBER";
			}
			return RETURN_CONSTANT;

		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while updating data in the database for Code:"
							+ form.getBussinessUserId());
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt);
				DBConnectionUtil.closeDBResources(con, pstmt1);
				DBConnectionUtil.closeDBResources(con, pstmt2);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class DealerRegistrationDAO.");
			}
		}
	}
*/
	/**
	 * This method called from RegistrationOfAllAction for Updating the status
	 * of the registered Number mapped to a particular user if its Status
	 * changed
	 * 
	 * 
	 * @param form
	 */
	/*
	public void inactiveRegNo(RegistrationOfAllBean form) throws DAOException {
		logger
				.debug("Entering method inactiveRegNo() in class RegistrationOfAllUsersDAO");
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			if ("D".equalsIgnoreCase(form.getStatus())) {
				connection = DBConnectionManager.getDBConnection();
				pstmt = con
						.prepareStatement(BusinessUserInterface.REG_NUMBER_Business_User);
				pstmt.setInt(1, Integer.parseInt(form.getBussinessUserId()));
				pstmt.executeUpdate();
			}
			logger
					.debug("Exiting method inactiveRegNo() in class RegistrationOfAllUsersDAO");
		} catch (SQLException e) {
			throw new DAOException(
					"Error occurs while udating mapping of dealer registered number.");
		} catch (DAOException e) {
			throw new DAOException(
					"Error occurs while udating mapping of dealer registered number.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in class DealerRegistrationDAO.");
			}
		}
	}

	*/
	/**
	 * This method is supproting method of getBussinessUserList()
	 * It gives the count of bussiness users , used for the pagination.
	 * 
	 * @return object of int
	 * @throws VCircleMstrDaoException
	 *             This method is called to count the circles.
	 */
	/*
	public int countBusinessUsers(String strCircleId, int typeOfUser,
			String search, String name, String code, String status,
			String regNumber,String identifier) throws VCircleMstrDaoException {
		logger
				.debug("Entering method countBusinessUsers() throws VCircleMstrDaoException");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;
		ArrayList usersList = new ArrayList();

		try {
			connection = DBConnectionManager.getDBConnection();
			if (typeOfUser < 0) {
				if (strCircleId == null) {
					RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
					dto.setBussinessUserId(null);
					dto.setBusinessUserName(null);
					dto.setCircleCode(null);
					usersList.add(dto);
				}
			} else {
//				 only active users
				if(identifier.equalsIgnoreCase(USSDConstants.singleUpload) || identifier.equalsIgnoreCase(USSDConstants.bulkUpload) )
				{
				
					if (search.equals(USSDConstants.all)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_ED_CEOActive);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, USSDConstants.ActiveState);
						}
						else{
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, USSDConstants.ActiveState);
						}	
					} else if (search.equals(USSDConstants.Code)) {
						
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_CODE_ED_CEO_Active);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, code);
							prepareStatement.setString(3, USSDConstants.ActiveState);
						}else{
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_CODE_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, code);
							prepareStatement.setString(4, USSDConstants.ActiveState);
							
						}
					} else if (search.equals(USSDConstants.Name)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_NAME_ED_CEO_ACTIVE);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, name);
							prepareStatement.setString(3, USSDConstants.ActiveState);
						}else{
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_NAME_Active);
							prepareStatement.setString(1, strCircleId);
							
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, name);
							prepareStatement.setString(4, USSDConstants.ActiveState);
						}
					} else if (search.equals(USSDConstants.regNumber)) {
						if (status.equals(USSDConstants.all)) {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_All_Busniess_ED_CEO_Active);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, USSDConstants.ActiveState);
							}else{
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_All_Busniess_Active);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								prepareStatement.setString(4, USSDConstants.ActiveState);
							}
						} else {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_Business_User_STATUS_ED_CEO_Active);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, status);
								prepareStatement.setString(4, USSDConstants.ActiveState);
							}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_Business_User_STATUS_Active);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, regNumber);
							prepareStatement.setString(4, status);
							prepareStatement.setString(5, USSDConstants.ActiveState);
							}
						}
					}
				}//end of upper if
				// all users
				else
				{
					if (search.equals(USSDConstants.all)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_ED_CEO);
							prepareStatement.setInt(1, typeOfUser);
						}
						else{
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
						}	
					} else if (search.equals(USSDConstants.Code)) {
						
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_CODE_ED_CEO);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, code);
						}else{
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_CODE);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, code);
							
						}
					} else if (search.equals(USSDConstants.Name)) {
						if((typeOfUser==USSDConstants.ED)){
							prepareStatement = connection
							.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_NAME_ED_CEO);
							prepareStatement.setInt(1, typeOfUser);
							prepareStatement.setString(2, name);
						}else{
							prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_ALL_Business_User_NAME);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, name);
						}
					} else if (search.equals(USSDConstants.regNumber)) {
						if (status.equals(USSDConstants.all)) {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_All_Busniess_ED_CEO);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
							}else{
								prepareStatement = connection
									.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_All_Busniess);
								prepareStatement.setString(1, strCircleId);
								prepareStatement.setInt(2, typeOfUser);
								prepareStatement.setString(3, regNumber);
								
							}
						} else {
							if((typeOfUser==USSDConstants.ED)){
								prepareStatement = connection
								.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_Business_User_STATUS_ED_CEO);
								prepareStatement.setInt(1, typeOfUser);
								prepareStatement.setString(2, regNumber);
								prepareStatement.setString(3, status);
							}else{
							prepareStatement = connection
									.prepareStatement(BusinessUserInterface.COUNT_STMT_REGNUMBER_Business_User_STATUS);
							prepareStatement.setString(1, strCircleId);
							prepareStatement.setInt(2, typeOfUser);
							prepareStatement.setString(3, regNumber);
							prepareStatement.setString(4, status);
							}
						}
					}
				}

				ResultSet countCircle = prepareStatement.executeQuery();
				if (countCircle.next()) {
					noofRow = countCircle.getInt(1);
				}
				noofPages = PaginationUtils.getPaginationSize(noofRow);

			}
		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new DPServiceException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new DPServiceException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countCircle() of class ViewEditCircleMasterDAOImpl.");
			}
		}
		logger
				.debug("Exiting method countBusinessUsers() throws VCircleMstrDaoException");
		return noofPages;
	}
	
	/**
	 * This method gets the value of AEPF_CHECK_REQ column from CIRCLE_MSTR
	 * table on the bases of the circle code given
	 * 
	 * @param circleCode :
	 *            String
	 * @return String
	 **
	public String aepfcheck(String circleCode) throws DAOException {
		logger
				.debug("Exiting method aepfcheck() in class RegistrationOfAllUsersDAO.Paramter Used is CircleCode : "
						+ circleCode);
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String aepfcheck = "";
		try {
			connection = DBConnectionManager.getDBConnection();
			pstmt = connection.prepareStatement(CircleMasterInterface.AEPF_CHECK);
			pstmt.setString(1, circleCode);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				aepfcheck = rs.getString("AEPF_CHECK_REQ");
			}
		} catch (SQLException e) {
			throw new DAOException("Error occurs in method aepfcheck.");
		} catch (DAOException e) {
			throw new DAOException("Error occurs in method aepfcheck.");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(con, pstmt,rs);
			} catch (SQLException e) {
				throw new DAOException(
						"Exception in closing database resources in method aepfcheck.");
			}
		}
		logger
				.debug("Exiting method aepfcheck() in class RegistrationOfAllUsersDAO");
		return aepfcheck;
	}

	/** getting list of users which belong to any of the loctaion from the list of locations ***
	
	public ArrayList getBizUserListInLocList(ArrayList locList)throws DAOException
	{
		ArrayList UserIdList=new ArrayList();
		ArrayList stringLocList=new ArrayList();
		int userId=0;
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs = null;
		try{
			Iterator itr = locList.iterator();
			while(itr.hasNext())
			{
				LocationDataDTO locDto = (LocationDataDTO)itr.next();
				stringLocList.add(String.valueOf((locDto.getLocDataId())));
			}
			String roleArray [] = (String []) stringLocList.toArray (new String [0]);
			String strterr =Utility.arrayToString(roleArray,null);
			
			conn = DBConnectionManager.getDBConnection();
			psmt = conn.prepareStatement(BusinessUserInterface.GET_BIZ_LIST_WITH_LOC_LIST.replaceAll("<LOC_LIST>", strterr));
			rs=psmt.executeQuery();
			while(rs.next())
			{
				userId=rs.getInt("DATA_ID");
				UserIdList.add(String.valueOf(userId));
			}
		}catch (SQLException e) {
			throw new DAOException(
			"Excetion Occured During call of getBizUserListInLocList() method ");
		}
		catch(Exception ex){
			throw new DAOException(
			"Excetion Occured During call of getBizUserListInLocList() method ");
		}
		finally {
			try {
				DBConnectionManager.releaseResources(psmt, null);
				DBConnectionManager.releaseResources(conn);
			} catch (Exception e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting getBizUserListInLocList() method of RegistrationOfAllUsersDAO.");
		}

		return UserIdList;
	}
	
	/** setting the status of the list of users to inactive ***
	
	public int updateBizUserListToInactive (List bizUserList , String status)throws DAOException
	{
		int update = 0;
		Connection conn=null;
		PreparedStatement psmt=null;
		boolean connCommitStatus = false;
		try{

			String roleArray [] = (String []) bizUserList.toArray (new String [0]);
			String strterr =Utility.arrayToString(roleArray,null);
			conn = DBConnectionManager.getDBConnection();
			connCommitStatus=conn.getAutoCommit();
			conn.setAutoCommit(false);
			if(bizUserList.isEmpty())
			{
				update = 1;
				return update;
			}
			psmt = conn.prepareStatement(BusinessUserInterface.UPDATE_BIZ_LIST_WITH_LOC_LIST.replaceAll("<DATA_LIST>", strterr));
			psmt.setString(1,status);
			update=psmt.executeUpdate();
			conn.commit();
			
		}catch (SQLException e) {
			try{
				conn.rollback();
				conn.setAutoCommit(connCommitStatus);
				return update;
			}catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

		} finally {
			try {
				
				conn.setAutoCommit(false);
				DBConnectionUtil.closeDBResources(conn, psmt);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting getBizUserListInLocList() method of RegistrationOfAllUsersDAO.");
		}
		return update;
	}
	
/** updating the location details of business users ***
	
	public int updateLocIdOfBizUserList (int locDataId , int newLocId,int locMstrId)throws DAOException
	{
		int update = 0;
		Connection conn=null;
		PreparedStatement psmt=null;
		boolean connCommitStatus = false;
		try{
			//String roleArray []={};
			//String roleArray [] = (String []) bizUserList.toArray (new String [0]);
			//String strterr =Utility.arrayToString(roleArray,null);
			conn = DBConnectionManager.getDBConnection();
			//connCommitStatus=conn.getAutoCommit();
			//conn.setAutoCommit(false);
//			if(bizUserList.isEmpty())
//			{
//				update = 1;
//				return update;
//			}
			if(locMstrId==USSDConstants.Zone){
				psmt = conn.prepareStatement(BusinessUserInterface.UPDATE_BIZ_LIST_LOC_ID_ZONE);
				psmt.setInt(1,newLocId);
				psmt.setInt(2,locDataId);
				update=psmt.executeUpdate();
			}
			if(locMstrId==USSDConstants.City){
				psmt = conn.prepareStatement(BusinessUserInterface.UPDATE_BIZ_LIST_LOC_ID_CITY);
				psmt.setInt(1,locDataId);
				psmt.setInt(2,newLocId);
				update=psmt.executeUpdate();
			}
			conn.commit();
			
		}catch (SQLException e) {
			try{
				conn.rollback();
				conn.setAutoCommit(connCommitStatus);
				return update;
			}catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally {
			try {
				
				conn.setAutoCommit(false);
				DBConnectionManager.releaseResources(psmt, null);
				DBConnectionManager.releaseResources(conn);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting getBizUserListInLocList() method of RegistrationOfAllUsersDAO.");
		}
		return update;
	}
	
	/** getting list of business users for move operations. serach criteria will be with same master id and same location id***
	
	public ArrayList getmoveBizUserList(int masterId , String locId)throws DAOException
	{
		ArrayList moveUsers=new ArrayList();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs = null;
		
		try{
			conn = DBConnectionManager.getDBConnection();
			
			if(masterId==USSDConstants.ED)
			{
				psmt = conn.prepareStatement(BusinessUserInterface.GET_BIZ_LIST_FOR_MOVE_CEO_ED);
				psmt.setString(1,locId);
			}else if(masterId==USSDConstants.CEO || masterId==USSDConstants.SALES_HEAD){
				psmt = conn.prepareStatement(BusinessUserInterface.GET_BIZ_LIST_FOR_MOVE_SALESHEAD);
				psmt.setString(1,locId);
			}else{
				psmt = conn.prepareStatement(BusinessUserInterface.GET_BIZ_LIST_FOR_MOVE);
				psmt.setInt(1,Integer.parseInt(locId) );
				
			}
			psmt.setInt(2,masterId);
			psmt.setString(3,USSDConstants.ActiveState);
			rs=psmt.executeQuery();
			while(rs.next())
			{
				RegistrationOfAllDTO regDTO = new RegistrationOfAllDTO();
				regDTO.setBussinessUserId(String.valueOf(rs.getInt("DATA_ID")));
				regDTO.setBusinessUserName(rs.getString("USER_NAME"));
				regDTO.setLocId(rs.getInt("LOC_ID"));
				moveUsers.add(regDTO);
			}
		}catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getBizUserListInLocList() method ");
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt,rs);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting getBizUserListInLocList() method of RegistrationOfAllUsersDAO.");
		}

		return moveUsers;
	}
	
/** updating the parent of business users ***
	
	public int updateParentOfBizUserList (int oldParentId, int newParentId)throws DAOException
	{
		int update = 0;
		Connection conn=null;
		PreparedStatement psmt=null;
		boolean connCommitStatus = false;
		try{

			conn = DBConnectionManager.getDBConnection();
			connCommitStatus=conn.getAutoCommit();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(BusinessUserInterface.UPDATE_BIZ_LIST_PARENT_ID);
			psmt.setInt(1,newParentId);
			psmt.setInt(2,oldParentId);
			update=psmt.executeUpdate();
			conn.commit();
			
		}catch (SQLException e) {
			try{
				conn.rollback();
				conn.setAutoCommit(connCommitStatus);
				return update;
			}catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

		} finally {
			try {
				
				conn.setAutoCommit(false);
				DBConnectionUtil.closeDBResources(conn, psmt);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting updateParentOfBizUserList() method of RegistrationOfAllUsersDAO.");
		}
		return update;
	}
	
/** getting list of users which belong to a given circle ***
	
	public ArrayList getBizUserListInCircle(String circle)throws DAOException
	{
		ArrayList UserIdList=new ArrayList();
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs = null;
		
		try{
			conn = DBConnectionManager.getDBConnection();
			psmt = conn.prepareStatement(BusinessUserInterface.GET_BIZ_LIST_WITH_CIRCLECODE);
			psmt.setString(1, circle);
			rs=psmt.executeQuery();
			while(rs.next())
			{
				RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
				dto.setBussinessUserId(String.valueOf(rs.getInt("DATA_ID")));
				dto.setBusinessUserName(rs.getString("USER_NAME"));
				dto.setCode(rs.getString("USER_CODE"));
				UserIdList.add(dto);

			}
		}catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getBizUserListInLocList() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt,rs);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting getBizUserListInLocList() method of RegistrationOfAllUsersDAO.");
		}

		return UserIdList;
	}
	
/** updating the circle of business users **
	
	public int updateCirclefBizUserList (ArrayList bizUserList , String circle)throws DAOException
	{
		int update = 0;
		Connection conn=null;
		PreparedStatement psmt=null;
		boolean connCommitStatus = false;
		try{
			String dataid = "";
			Iterator itr = bizUserList.iterator();
			while(itr.hasNext())
			{
				RegistrationOfAllDTO dto = (RegistrationOfAllDTO)itr.next();
				dataid=dataid+dto.getBussinessUserId()+",";
			}
			dataid=dataid+'0';			
			conn = DBConnectionManager.getDBConnection();
			connCommitStatus=conn.getAutoCommit();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(BusinessUserInterface.UPDATE_BIZ_LIST_CIRCLE.replaceAll("<DATA_ID>", dataid));
			psmt.setString(1,circle);
			update=psmt.executeUpdate();
			conn.commit();
			
		}catch (SQLException e) {
			try{
				conn.rollback();
				conn.setAutoCommit(connCommitStatus);
				return update;
			}catch (Exception ex) {
				logger.error("Error" + e.getMessage(), ex);
			}

		} finally {
			try {
				
				conn.setAutoCommit(false);
				DBConnectionUtil.closeDBResources(conn, psmt);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.");
			}
			logger
					.debug("Exiting updateParentOfBizUserList() method of RegistrationOfAllUsersDAO.");
		}
		return update;
	}*/
	
}
