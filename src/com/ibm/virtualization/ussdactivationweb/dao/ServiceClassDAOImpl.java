/**************************************************************************
 * File     : ServiceClassDAOImpl.java
 * Author   : Ashad
 * Created  : Sep 4, 2008
 * Modified : Sep 4, 2008
 * Version  : 1.0
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

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.ServiceClassDAOInterface;
import com.ibm.virtualization.ussdactivationweb.dto.ServiceClassDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.exception.VCircleMstrDaoException;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;

/*******************************************************************************
 * This class is used to get list of ServiceClass, verify,create,view and update
 * ServiceClass.
 * 
 * @author Ashad
 * @veresultSetion 1.0
 ******************************************************************************/

public class ServiceClassDAOImpl implements ServiceClassDAOInterface {

	private static Logger logger = Logger.getLogger(ServiceClassDAOImpl.class
			.getName());

	/**
	 * 
	 */
	public ServiceClassDAOImpl() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * This method is used for Creating of ServiceClass using
	 * createServiceClass(ServiceClassDTO serviceDTO)method.
	 * 
	 * @param serviceDTO
	 *            object containing data for creating service class
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#createServiceClass(ServiceClassDTO)
	 */

	public void createServiceClass(ServiceClassDTO serviceDTO)
			throws DAOException {
		logger.debug("Entering  createServiceClass in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String createServiceClassSQL = ServiceClassDAOInterface.CREATE_SERVICE_CLASS;

			prepareStatement = connection
					.prepareStatement(createServiceClassSQL);
			prepareStatement.setInt(1, serviceDTO.getServiceClassId());
			prepareStatement.setString(2, serviceDTO.getServiceClassName());
			prepareStatement.setString(3, serviceDTO.getCircleCode());
			prepareStatement.setString(4, serviceDTO.getCreatedBy());
			prepareStatement.setString(5, null);
			prepareStatement.setString(6, null);
			prepareStatement.setString(7, serviceDTO.getRegistrationReq());
			prepareStatement.setInt(8, serviceDTO.getScCategoryId());
			prepareStatement.setString(9, serviceDTO.getTariffMessage());
			logger.debug("before insert serviceClass query exec.........");

			int recUpdate = prepareStatement.executeUpdate();

			if (recUpdate == 1) {

				logger.debug("ServiceClass "+serviceDTO.getServiceClassName()+" Created.");

			} else {

				logger.debug("serviceClass content is not added ");
			}

		}

		catch (SQLException sqe) {
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			if (sqe != null
					&& sqe.getErrorCode() == Integer
							.parseInt(ErrorCodes.ServiceClass.SQLDUPEXCEPTION))

			{
				throw new DAOException(String.valueOf(sqe.getErrorCode()), sqe);
			} else {
				throw new DAOException(sqe.getMessage(), sqe);
			}
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
			logger.debug("Exiting createService in DAOImpl");
		}
	}

	/**
	 * This method is used to get ServiceClass information for selected circle
	 * and displaying it for view with the concept of pageination.
	 *  @ param circleCode object used for retrieving ServiceClass Information @ param
	 * Id the Active/Inactive status of ServiceClass @ return an ArrayList
	 * containing list of service class to be displayed for viewing
	 *  @ throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassList(String
	 *      circleCode,int statusId)
	 */
	public ArrayList getServiceClassList(String circleCode, int intLb, int intUb)
			throws DAOException {
		logger.debug("Entering  getServiceClassList in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList getServiceClassList = new ArrayList();
		ServiceClassDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassList = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS_WITH_PAGEINATION;

			prepareStatement = connection
					.prepareStatement(retrieveServiceClassList);

			prepareStatement.setString(1, circleCode);
			prepareStatement.setString(2, circleCode);
			prepareStatement.setString(3, String.valueOf(intUb));
			prepareStatement.setString(4, String.valueOf(intLb + 1));
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				serviceDTO = new ServiceClassDTO();
				serviceDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				serviceDTO.setServiceClassName(resultSet
						.getString("SERVICECLASS_NAME"));
				serviceDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				serviceDTO.setCreatedDate(resultSet.getTimestamp("CREATED_DT"));
				serviceDTO.setStatus(resultSet.getInt("STATUS"));
				serviceDTO.setRegistrationReq(resultSet
						.getString("SPECIAL_SC_CHECK"));
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
				serviceDTO.setScCategoryName(resultSet
						.getString("CATEGORY_DESC"));
				serviceDTO.setRowNum(resultSet.getString("RNUM"));	
				getServiceClassList.add(serviceDTO);
			}
			if (getServiceClassList.size() != 0) {
				return getServiceClassList;
			} else {

				throw new DAOException(ErrorCodes.ServiceClass.RESULT_NOT_FOUND);
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
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				logger
						.debug("Exception occured when trying to close database resources");
			}
			logger.debug("Exiting getServiceClassList in DAOImpl");
		}

	}

	
	

	/**
	 * This method is used to get ServiceClass information for selected circle
	 * and displaying it for view .
	 *  @ param circleCode object used for retrieving ServiceClass Information @ param
	 * Id the Active/Inactive status of ServiceClass @ return an ArrayList
	 * containing list of service class to be displayed for viewing
	 *  @ throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassList(String
	 *      circleCode,int statusId)
	 */
	public ArrayList getServiceClassList(String circleCode)
			throws DAOException {
		logger.debug("Entering  getServiceClassList in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList getServiceClassList = new ArrayList();
		ServiceClassDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassList = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS;

			prepareStatement = connection
					.prepareStatement(retrieveServiceClassList);

			prepareStatement.setString(1, circleCode);
			prepareStatement.setString(2, circleCode);
			
			resultSet = prepareStatement.executeQuery();

			while (resultSet.next()) {
				serviceDTO = new ServiceClassDTO();
				serviceDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				serviceDTO.setServiceClassName(resultSet
						.getString("SERVICECLASS_NAME"));
				serviceDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
				serviceDTO.setCreatedDate(resultSet.getTimestamp("CREATED_DT"));
				serviceDTO.setStatus(resultSet.getInt("STATUS"));
				serviceDTO.setRegistrationReq(resultSet
						.getString("SPECIAL_SC_CHECK"));
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
				serviceDTO.setScCategoryName(resultSet
						.getString("CATEGORY_DESC"));
				getServiceClassList.add(serviceDTO);
			}
			if (getServiceClassList.size() != 0) {
				return getServiceClassList;
			} else {

				throw new DAOException(ErrorCodes.ServiceClass.RESULT_NOT_FOUND);
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
			logger.debug("Exiting getServiceClassList in DAOImpl");
		}

	}
	/**
	 * This method is used to get ServiceClass information for selected circle
	 * and displaying it for view .
	 *  @ param circleCode object used for retrieving ServiceClass Information @ param
	 * Id the Active/Inactive status of ServiceClass @ return an ArrayList
	 * containing list of service class to be displayed for viewing
	 *  @ throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassList(String
	 *      circleCode,int statusId)
	 */
	public ArrayList getServiceClassListForDropDown(String circleCode)
	throws DAOException {
	logger.debug("Entering  getServiceClassList in DAOImpl");
	PreparedStatement prepareStatement = null;
	ResultSet resultSet = null;
	ArrayList getServiceClassList = new ArrayList();
	ServiceClassDTO serviceDTO = null;
	Connection connection = null;
	try {
		connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
		String retrieveServiceClassList = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS_FOR_DROP_DOWN;
	
		prepareStatement = connection
				.prepareStatement(retrieveServiceClassList);
	
		prepareStatement.setString(1, circleCode);
		prepareStatement.setString(2, circleCode);
		prepareStatement.setInt(3, Constants.Active);
		
		resultSet = prepareStatement.executeQuery();
	
		while (resultSet.next()) {
			serviceDTO = new ServiceClassDTO();
			serviceDTO.setServiceClassId(resultSet
					.getInt("SERVICECLASS_ID"));
			serviceDTO.setServiceClassName(resultSet
					.getString("SERVICECLASS_NAME"));
			serviceDTO.setCreatedBy(resultSet.getString("CREATED_BY"));
			serviceDTO.setCreatedDate(resultSet.getTimestamp("CREATED_DT"));
			serviceDTO.setStatus(resultSet.getInt("STATUS"));
			serviceDTO.setRegistrationReq(resultSet
					.getString("SPECIAL_SC_CHECK"));
			serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
			serviceDTO.setScCategoryName(resultSet
					.getString("CATEGORY_DESC"));
			getServiceClassList.add(serviceDTO);
		}
		if (getServiceClassList.size() != 0) {
			return getServiceClassList;
		} else {
	
			throw new DAOException(ErrorCodes.ServiceClass.RESULT_NOT_FOUND);
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
		logger.debug("Exiting getServiceClassList in DAOImpl");
	}

}
	/**
	 * This method is used to get Service Class Information and than displaying
	 * it for editing.
	 * 
	 * @param circleCode
	 *            the circleCode of serviceClass selected for editing
	 * @param serviceClassId
	 *            the ServiceClassId of ServiceClass selected for editing
	 * @return an object containing list of ServiceClass to be edited
	 * 
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassListByIdCode(Integer
	 *      serviceClassId,String circleCode)
	 */
	public ServiceClassDTO getServiceClassListByIdCode(int serviceClassId,
			String circleCode) throws DAOException {
		logger.debug("Entering  getServiceClassListById in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ServiceClassDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassListById = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS_BY_ID;
			prepareStatement = connection
					.prepareStatement(retrieveServiceClassListById);
			prepareStatement.setInt(1, serviceClassId);
			prepareStatement.setString(2, circleCode);
			
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				serviceDTO = new ServiceClassDTO();
				serviceDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				serviceDTO.setServiceClassName(resultSet
						.getString("SERVICECLASS_NAME"));
				serviceDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				serviceDTO.setStatus(resultSet.getInt("STATUS"));
				serviceDTO.setRegistrationReq(resultSet
						.getString("SPECIAL_SC_CHECK"));
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
				serviceDTO.setTariffMessage(resultSet.getString("TARIFF_MESSAGE"));

			}
			return serviceDTO;

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
			logger.debug("Exiting  getServiceClassListById in DAOImpl");
		}

	}
	/**
	 * This method is used to get Active Service Class Information and than displaying
	 * it for editing.
	 * 
	 * @param circleCode
	 *            the circleCode of serviceClass selected for editing
	 * @param serviceClassId
	 *            the ServiceClassId of ServiceClass selected for editing
	 * @return an object containing list of ServiceClass to be edited
	 * 
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassListByIdCode(Integer
	 *      serviceClassId,String circleCode)
	 */
	public ServiceClassDTO getServiceClassListByIdCodeActive(long serviceClassId,
			String circleCode) throws DAOException {
		logger.debug("Entering  getServiceClassListById in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ServiceClassDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassListById = ServiceClassDAOInterface.RETRIEVE_ACTIVE_SERVICE_CLASS_BY_ID;
			prepareStatement = connection
					.prepareStatement(retrieveServiceClassListById);
			prepareStatement.setLong(1, serviceClassId);
			prepareStatement.setString(2, circleCode);
			prepareStatement.setInt(3, Constants.Active);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				serviceDTO = new ServiceClassDTO();
				serviceDTO.setServiceClassId(resultSet
						.getInt("SERVICECLASS_ID"));
				serviceDTO.setServiceClassName(resultSet
						.getString("SERVICECLASS_NAME"));
				serviceDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				serviceDTO.setStatus(resultSet.getInt("STATUS"));
				serviceDTO.setRegistrationReq(resultSet
						.getString("SPECIAL_SC_CHECK"));
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));

			}
			return serviceDTO;

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
			logger.debug("Exiting  getServiceClassListById in DAOImpl");
		}

	}
	
	/**
	 * This method is used to get Service Class Information and than displaying
	 * it for editing.
	 * 
	 * @param circleCode
	 *            the circleCode of serviceClass selected for editing
	 * @param serviceClassId
	 *            the ServiceClassId of ServiceClass selected for editing
	 * @return an object containing list of ServiceClass to be edited
	 * 
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassListByIdCode(Integer
	 *      serviceClassId,String circleCode)
	 */
	public int getServiceClassListById(int serviceClassId) throws DAOException {
		logger.debug("Entering  getServiceClassListById in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int seviceClassCount =0;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassListById = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS_BY_ID_FOR_ALL_CIRCEL;
			prepareStatement = connection
					.prepareStatement(retrieveServiceClassListById);
			prepareStatement.setInt(1, serviceClassId);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				seviceClassCount = resultSet.getInt("SERVICECLASS_ID");
			}
			return seviceClassCount;

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
			logger.debug("Exiting  getServiceClassListById in DAOImpl");
		}

	}
	
	/**
	 * This method is used to get Service Class Information and than displaying
	 * it for editing.
	 * 
	 * @param circleCode
	 *            the circleCode of serviceClass selected for editing
	 * @param serviceClassId
	 *            the ServiceClassId of ServiceClass selected for editing
	 * @return an object containing list of ServiceClass to be edited
	 * 
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#getServiceClassListByIdCode(Integer
	 *      serviceClassId,String circleCode)
	 */
	
	public String getServiceClassNameById(int serviceClassId) throws DAOException {
		logger.debug("Entering  getServiceClassListById in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String seviceClassName =null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String retrieveServiceClassListById = ServiceClassDAOInterface.RETRIEVE_SERVICE_CLASS_NAME_BY_ID;
			prepareStatement = connection
					.prepareStatement(retrieveServiceClassListById);
			prepareStatement.setInt(1, serviceClassId);
			resultSet = prepareStatement.executeQuery();

			if (resultSet.next()) {
				seviceClassName = resultSet.getString("SERVICECLASS_NAME");
			}
			return seviceClassName;

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
			logger.debug("Exiting  getServiceClassListById in DAOImpl");
		}

	}
	
	/**
	 * This method is used to modify Service Class Information.
	 * 
	 * @param serviceDTO
	 *            object containing modified service class data.
	 * @param Id
	 *            service class Id used for identifying service class to be
	 *            modified
	 * 
	 * @throws DAOException
	 * 
	 * @see com.ibm.prepaid.product.catalogue.dao.interfaces.ServiceClassDAOInterface#updateServiceClass(ServiceClassDTO
	 *      serviceDTO,Integer Id)
	 * 
	 */
	public void updateServiceClass(ServiceClassDTO serviceDTO, int Id)
			throws DAOException {
		logger.debug("Entering  updateServiceClass in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			String updtaeServiceClassSQL = ServiceClassDAOInterface.UPDATE_SERVICE_CLASS;
			prepareStatement = connection
					.prepareStatement(updtaeServiceClassSQL);
			prepareStatement.setInt(1, serviceDTO.getStatus());
			prepareStatement.setString(2, serviceDTO.getModifiedBy());
			prepareStatement.setString(3, serviceDTO.getServiceClassName());
			prepareStatement.setString(4, serviceDTO.getRegistrationReq());
			prepareStatement.setInt(5, serviceDTO.getScCategoryId());
			prepareStatement.setString(6, serviceDTO.getTariffMessage());
			prepareStatement.setInt(7, Id);
			prepareStatement.setString(8, serviceDTO.getCircleCode());

			logger.debug("before insert serviceClass query exec.........");
			int recUpdate = prepareStatement.executeUpdate();

			if (recUpdate == 1) {

				// logger.debug("serviceClassDTO "+serviceDTO);

			} else {

				logger.debug("serviceClass content is not added ");
			}

		}

		catch (SQLException sqe) {
			sqe.printStackTrace();
			logger.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			if (sqe != null
					&& sqe.getErrorCode() == Integer
							.parseInt(ErrorCodes.ServiceClass.SQLDUPEXCEPTION))

			{
				throw new DAOException(String.valueOf(sqe.getErrorCode()), sqe);
			} else {
				throw new DAOException(sqe.getMessage(), sqe);
			}

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

		logger.debug("Exiting updateServiceClass in DAOImpl");
	}

	/**
	 * This method is used to get Service class categary information for
	 * selected circle and displaying it for view . @ throws DAOException
	 */

	public ArrayList getServiceClassCategoryList() throws DAOException {
		logger.debug("Entering  getServiceClassCategoryList in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList serviceClassCategoryList = new ArrayList();
		ServiceClassDTO serviceDTO = null;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(ServiceClassDAOInterface.RETRIEVE_SC_CATEGORY_LIST);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				serviceDTO = new ServiceClassDTO();
				serviceDTO.setScCategoryId(resultSet.getInt("CATEGORY_ID"));
				serviceDTO.setScCategoryName(resultSet
						.getString("CATEGORY_DESC"));
				serviceClassCategoryList.add(serviceDTO);
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
			logger.debug("Exiting getServiceClassCategoryList in DAOImpl");
		}
		return serviceClassCategoryList;
	}

	public int getServiceNameByIdCode(ServiceClassDTO serviceClassDTO)
			throws DAOException {
		logger.debug("Entering  getServiceNameByIdCode in DAOImpl");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		int rowCount = 0;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(ServiceClassDAOInterface.VALIDATE_SERVICECLASS_NAME);
			prepareStatement
					.setString(1, serviceClassDTO.getServiceClassName());
			prepareStatement.setString(2, serviceClassDTO.getCircleCode());
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				rowCount++;

			}
		} catch (SQLException sqe) {
			sqe.printStackTrace();
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
			logger.debug("Exiting getServiceNameByIdCode in DAOImpl");
		}
		return rowCount;
	}

	/**
	 * 
	 * @return object of int
	 * @throws VCircleMstrDaoException
	 *             This method is called to count the circles.
	 */
	public int countServiceClass(String circleCode) throws DAOException {
		logger
				.debug("Entering method countCircle() throws VCircleMstrDaoException");
		int noofPages = 0;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofRow = 0;

		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(ServiceClassDAOInterface.COUNT_SERVICECLASS);
			prepareStatement.setString(1, circleCode);
			prepareStatement.setString(2, circleCode);
			ResultSet countCircle = prepareStatement.executeQuery();

			if (countCircle.next()) {
				noofRow = countCircle.getInt(1);
			}

			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException sqlEx) {
			logger.error("SQL exception encountered: " + sqlEx.getMessage(),
					sqlEx);
			throw new VCircleMstrDaoException("SQLException: "
					+ sqlEx.getMessage(), sqlEx);
		} catch (Exception ex) {
			logger.error("Exception encountered: " + ex.getMessage(), ex);
			throw new VCircleMstrDaoException("Exception: " + ex.getMessage(),
					ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				new DAOException(
						"Exception occur while closing database resources in method countCircle() of class ViewEditCircleMasterDAOImpl.",
						e);
			}
		}
		logger
				.debug("Exiting method countCircle() throws VCircleMstrDaoException");
		return noofPages;
	}

}
