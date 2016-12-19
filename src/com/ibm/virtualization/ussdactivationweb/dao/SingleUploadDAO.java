/**************************************************************************
 * File     : SingleUploadDAO.java
 * Author   : Abhipsa
 * Created  : Sept 23 2008
 * Modified : Sept 23 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sept 23, 2008 	Abhipsa	First Cut.
 * V0.2		Sept 23, 2008 	Abhipsa	First modification
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
import org.apache.struts.util.LabelValueBean;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.BusinessUserInterface;
import com.ibm.virtualization.ussdactivationweb.daoInterfaces.SingleUploadInterface;
import com.ibm.virtualization.ussdactivationweb.dto.CircleMasterDTO;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.dto.SingleUploadDTO;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author abhipsa
 * 
 */
public class SingleUploadDAO implements SingleUploadInterface {
	/** getting logger refrence * */

	private static final Logger logger = Logger
			.getLogger(SingleUploadDAO.class);

	int noofRow = 1;


	/**
	 * this method count the number of parenst to which we could map our user
	 * 
	 * @param strCircleId : String
	 * @param mappingType : int
	 * @param mappedUserId : int
	 * @return : int
	 * @throws DAOException
	 */
	public int countParentList(String strCircleId, int mappingType, int mappedUserId) throws DAOException {
		logger.debug("Entering countParentList() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		SingleUploadDTO singleDto = new SingleUploadDTO();
		int noofPages = 0;
		int masterId = mappingType;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			//getting the location id
			prepareStatement = connection.prepareStatement(SingleUploadInterface.Bussiness_data);
			prepareStatement.setInt(1,mappedUserId);
			resultSet=prepareStatement.executeQuery();
			if(resultSet.next())
			{
				singleDto.setLocId(resultSet.getInt("LOC_ID"));
				singleDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				singleDto.setHubCode(resultSet.getString("HUB_CODE"));
			}
			
			// when base location is same and is territory or Zone
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				//checking the status of the location.
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());
				if(locationDto.getStatus().equalsIgnoreCase(Constants.strActive))
				{
					//getting count of list of parents			
					noofRow=countNumberOfRowsForParent(String.valueOf(singleDto.getLocId()), masterId , mappingType);
				}else{
					noofRow=0;
				}
							
			}// when base location is different and is Zone-City
			else if(mappingType==Constants.ZSM)
			{
				// getting the parent of territory
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());
				
				//checking the status of the location.
				LocationDataDTO locationDto1=  new LocationDataDTO();
				locationDto1 = locationDao.getLocationDetails(Integer.parseInt(locationDto.getParentId()));
				if(locationDto1.getStatus().equalsIgnoreCase(Constants.strActive))
				{
					//getting count of list of parents
					noofRow=countNumberOfRowsForParent(String.valueOf(locationDto.getParentId()), masterId , mappingType);
				}else{
					noofRow=0;
				}
			}// when base location is different and is Circle-Zone
			else if(mappingType==Constants.SALES_HEAD)
			{
				//getting the parent of zone, that is Cirle
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());
				
				//checking the status of the location.
				CircleMasterDTO circledto =  new CircleMasterDTO();
				ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
				circledto = circledao.findByPrimaryKey(locationDto.getParentId());
				if(circledto.getStatus().equals(String.valueOf(Constants.Active)))
				{
					//getting count of list of parents
					noofRow=countNumberOfRowsForParent(String.valueOf(locationDto.getParentId()), masterId , mappingType);
				}else{
					noofRow=0;
				}
			}// when base location is Same and is Circle-Circle
			else if(mappingType==Constants.CEO)
			{
				noofRow=countNumberOfRowsForParent(singleDto.getCircleCode(), masterId , mappingType);
			}// when base different is same and is Hub-Circle
			else if(mappingType==Constants.ED)
			{
				noofRow=countNumberOfRowsForParent(singleDto.getHubCode(), masterId , mappingType);
			}
				noofPages = PaginationUtils.getPaginationSize(noofRow);
			
		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of countParentList() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting countParentList() method of SingleUploadDAO.");
		return noofPages;
	}
	/**
	 * this method is supporting method for countParentList()
	 * 
	 * @param locID : String
	 * @param mappingType : int
	 * @param masterId : int
	 * @return : int
	 * @throws DAOException
	 */
	public int countNumberOfRowsForParent(String locID,int masterId,int mappingType)throws DAOException
	{
		logger.debug("Entering countNumberOfRowsForParent() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		ResultSet rs1=null;
		int noofRow=0;
		try{
			conn = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			String query = SingleUploadInterface.Number_of_parents;
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) || (mappingType==Constants.ZSM) )
			{
				query = query+" AND LOC_ID = ? WITH UR";
			}
			else if( mappingType==Constants.SALES_HEAD)
			{
				query = query+"  AND CIRCLE_CODE = ?  WITH UR";
			}
			else if((mappingType==Constants.CEO))
			{
				query = query+"  AND CIRCLE_CODE = ? WITH UR";
			}
			else if((mappingType==Constants.ED))
			{
				query = query+"  AND HUB_CODE = ? WITH UR";
			}
			psmt1 = conn.prepareStatement(query);
			psmt1.setInt(1,masterId);
			psmt1.setString(2,Constants.ActiveState);
			if((mappingType==Constants.SALES_HEAD) || (mappingType==Constants.CEO) || (mappingType==Constants.ED))
			{
				psmt1.setString(3, locID);
			}else{
				psmt1.setInt(3, Integer.parseInt(locID));
			}
			
			rs1 = psmt1.executeQuery();
		
			if(rs1.next())
			{
				noofRow=rs1.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Excetion Occured During call of countNumberOfRowsForParent() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1,
						rs1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting countNumberOfRowsForParent() method of SingleUploadDAO.");	
		return noofRow;
			
	}
	/**
	 * this method gets  the list of parenst to which we could map our user
	 * 
	 * @param strCircleId : String
	 * @param mappingType : int
	 * @param mappedUserId : int
	 * @param lb : int
	 * @param ub : int
	 * @return : int
	 * @throws DAOException
	 */
	public List getparentList(String strCircleId, int mappingType , int mappedUserId , int lb, int ub)
			throws DAOException {
		logger.debug("Entering getparentList() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		SingleUploadDTO singleDto = new SingleUploadDTO();
		int masterId = mappingType;
		ArrayList list = new ArrayList();
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
		
//			getting the location id
			prepareStatement = connection.prepareStatement(SingleUploadInterface.Bussiness_data);
			prepareStatement.setInt(1,mappedUserId);
			resultSet=prepareStatement.executeQuery();
			if(resultSet.next())
			{
				singleDto.setLocId(resultSet.getInt("LOC_ID"));
				singleDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				singleDto.setHubCode(resultSet.getString("HUB_CODE"));
			}
			
			// when base location is same and is territory or Zone
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());
				if(locationDto.getStatus().equalsIgnoreCase(Constants.strActive))
				{
					//getting count of list of parents			
					list=listOfParent(String.valueOf(singleDto.getLocId()), masterId , mappingType, lb, ub);
				}else{
					return list;
				}
							
			}// when base location is different and is Zone-City
			else if(mappingType==Constants.ZSM)
			{
				// getting the parent of city
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());
				
				
//				checking the status of the location.
				LocationDataDTO locationDto1=  new LocationDataDTO();
				locationDto1 = locationDao.getLocationDetails(Integer.parseInt(locationDto.getParentId()));
				if(locationDto1.getStatus().equalsIgnoreCase(Constants.strActive))
				{
					//getting count of list of parents
					list=listOfParent(String.valueOf(locationDto.getParentId()), masterId , mappingType, lb, ub);
				}else{
					return list;
				}
			}// when base location is different and is Circle-Zone
			else if(mappingType==Constants.SALES_HEAD)
			{
				//getting the parent of zone, that is circle
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(singleDto.getLocId());

				
//				checking the status of the location.
				CircleMasterDTO circledto =  new CircleMasterDTO();
				ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
				circledto = circledao.findByPrimaryKey(locationDto.getParentId());
				if(circledto.getStatus().equals(String.valueOf(Constants.Active)))
				{
					//getting count of list of parents
					list=listOfParent(String.valueOf(locationDto.getParentId()), masterId , mappingType, lb, ub);
				}else{
					return list;
				}
			}// when base location is Same and is Circle-Circle
			else if(mappingType==Constants.CEO)
			{
				list=listOfParent(singleDto.getCircleCode(), masterId , mappingType, lb, ub);
			}// when base location is different and is Hub-Circle
			else if(mappingType==Constants.ED)
			{
				list=listOfParent(singleDto.getHubCode(), masterId , mappingType, lb, ub);
			}
			
		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getparentList() method ", e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting getparentList() method of SingleUploadDAO.");
		return list;
	}
	/**
	 * this is supporting method for getparentList()
	 * 
	 * @param locID : String
	 * @param masterId : int
	 * @param mappingType : int
	 * @param lb : int
	 * @param ub : int
	 * @return : ArrayList
	 * @throws DAOException
	 */
	public ArrayList listOfParent(String locID,int masterId,int mappingType,int lb,int ub)throws DAOException
	{
		logger.debug("Entering listOfParent() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		ResultSet rs1=null;
		ArrayList list = new ArrayList();
		try{
			conn = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			String query = SingleUploadInterface.List_of_parents;
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) || (mappingType==Constants.ZSM) )
			{
				query = query+" AND LOC_ID = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if( mappingType==Constants.SALES_HEAD)
			{
				query = query+"  AND CIRCLE_CODE = ?  ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if( (mappingType==Constants.CEO) )
			{
				query = query+"  AND CIRCLE_CODE = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if((mappingType==Constants.ED) )
			{
				query = query+"  AND HUB_CODE = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			psmt1 = conn.prepareStatement(query);
			psmt1.setInt(1,masterId);
			psmt1.setString(2,Constants.ActiveState);
			if((mappingType==Constants.SALES_HEAD) || (mappingType==Constants.CEO) || (mappingType==Constants.ED))
			{
				psmt1.setString(3, locID);
			}else{
				psmt1.setInt(3, Integer.parseInt(locID));
			}
			psmt1.setString(4, String.valueOf(ub));
			psmt1.setString(5, String.valueOf(lb+1));
			rs1 = psmt1.executeQuery();
		
			while (rs1.next()) {
				SingleUploadDTO mappingDTO = new SingleUploadDTO();
				mappingDTO
						.setBusinessUserName(rs1.getString("USER_NAME"));
				mappingDTO
						.setBusinessUserCode(rs1.getString("USER_CODE"));
				mappingDTO.setBusinessUserId(rs1.getInt("DATA_ID"));
				mappingDTO.setCircleCode(rs1.getString("CIRCLE_CODE"));
				list.add(mappingDTO);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Excetion Occured During call of listOfParent() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1,
						rs1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting listOfParent() method of SingleUploadDAO.");
		return list;
			
	}

	/**
	 * this method counts the mapped child to a particular parent
	 * 
	 * @param mapType : int
	 * @param userId : int
	 * @return : int
	 * @throws DAOException
	 */
	public int countMappedUser(int mapType, int userId) throws DAOException {
		logger.debug("Entering countMappedUser() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		int noofPages = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(SingleUploadInterface.COUNT_MAPPED_Business_User);
			prepareStatement.setInt(1, userId);
			prepareStatement.setString(2, Constants.ActiveState);
			ResultSet count = prepareStatement.executeQuery();
			if (count.next()) {
				noofRow = count.getInt(1);
			}
			noofPages = PaginationUtils.getPaginationSize(noofRow);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Excetion Occured During call of countMappedUser() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting countMappedUser() method of SingleUploadDAO.");
		return noofPages;
	}

	/**
	 * this method gets the list of child which are mapped with the given parent.
	 * 
	 * @param maptype : int
	 * @param userId : int
	 * @param lb : int
	 * @param ub : int
	 * @return ArrayList
	 * @throws DAOException
	 */
	public SingleUploadDTO getMappingList(int maptype, int userId, int lb,
			int ub) throws DAOException {
		logger.debug("Entering getMappingList() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		PreparedStatement prepareStatement1 = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		ArrayList list = new ArrayList();
		SingleUploadDTO singleDto = new SingleUploadDTO();
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(SingleUploadInterface.LIST_MAPPED_Business_User);
			prepareStatement.setInt(1, userId);
			prepareStatement.setString(2, Constants.ActiveState);
			prepareStatement.setString(3, String.valueOf(ub));
			prepareStatement.setString(4, String.valueOf(lb + 1));
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {

				SingleUploadDTO dto = new SingleUploadDTO();
				dto.setMappedBusinessUserId(resultSet.getInt("DATA_ID"));
				dto.setMappedBusinessUserCode(resultSet.getString("USER_CODE"));
				dto.setMappedBusinessUserName(resultSet.getString("USER_NAME"));
				dto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				dto.setBusinessUserId(resultSet.getInt("DATA_ID"));
				dto.setBusinessUserName(resultSet.getString("PARENT_ID"));
				dto.setMsisdn(resultSet.getString("CONTACT_NO"));
				dto.setRownum(resultSet.getString("RNUM"));
				list.add(dto);
			}
			singleDto.setMappedBusinessUserList(list);
			prepareStatement1 = connection
					.prepareStatement(BusinessUserInterface.DIST_PROFILE_Business_User);
			prepareStatement1.setInt(1, userId);
			resultSet1 = prepareStatement1.executeQuery();

			while (resultSet1.next()) {

				singleDto
						.setBusinessUserCode(resultSet1.getString("USER_CODE"));
				singleDto
						.setBusinessUserName(resultSet1.getString("USER_NAME"));
				singleDto.setBusinessUserId(resultSet1.getInt("DATA_ID"));

			}
		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getMappingList() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
				DBConnectionUtil.closeDBResources(connection,
						prepareStatement1, resultSet1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting getMappingList() method of SingleUploadDAO.");
		return singleDto;

	}

	/**
	 * this method gets the info of particular child mapped to a parenty.
	 * 
	 * @param mappedUserId : int
	 * @param mappingType : int
	 * @return SingleUploadDTO
	 * @throws DAOException
	 */
	public SingleUploadDTO getmappedUserInfo(int mappedUserId, int mappingType)
			throws DAOException {
		logger.debug("Entering getmappedUserInfo() method of SingleUploadDAO.");
		SingleUploadDTO dto = new SingleUploadDTO();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(SingleUploadInterface.GET_MAPPED_Business_User_INFO);
			prepareStatement.setInt(1, mappedUserId);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {

				dto.setMappedBusinessUserId(resultSet.getInt(1));
				dto.setMappedBusinessUserName(resultSet.getString(2));
				dto.setMappedBusinessUserCode(resultSet.getString(3));
				dto.setMsisdn(resultSet.getString(4));
				dto.setCircleCode(resultSet.getString(5));
				dto.setBusinessUserName(resultSet.getString(6));
				dto.setBusinessUserId(resultSet.getInt(7));
				dto.setMappingType(mappingType);
			}
		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getmappedUserInfo() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting getmappedUserInfo() method of SingleUploadDAO.");
		return dto;
	}

	/**
	 * this method updates the mapping for a user.
	 * 
	 * 
	 * @param mappingtype : int
	 * @param userId : int
	 * @param mappedUserId : int
	 * @return String
	 * @throws DAOException
	 */
	public String updateUserMapping(int userId, int mappedUserId,
			int mappingType) throws DAOException {
		logger.debug("Entering updateUserMapping() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			prepareStatement = connection
					.prepareStatement(SingleUploadInterface.UPDATE_MAPPING_Business_User);
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, mappedUserId);
			prepareStatement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of updateUserMapping() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting updateUserMapping() method of SingleUploadDAO.");
		return "success";
	}

	/**
	 * this method counts the unmapped users which can be mapped to a given parent
	 * 
	 * @param businessUserId : int
	 * @param mappingtype : int
	 * @param strCircleId : int
	 * @return int
	 * @throws DAOException
	 */
	public int countUnmappedUsers(String strCircleId, int mappingType,int businessUserId)
			throws DAOException {
		logger
				.debug("Entering countUnmappedUsers() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		SingleUploadDTO dto1 = new SingleUploadDTO();
		ResultSet rs1 = null;
		int noofPages = 0;
		int noofRow = 0;
		int masterId=mappingType+1;
		try {
			conn = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			// getting the location id
			psmt1 = conn.prepareStatement(SingleUploadInterface.Bussiness_data);
			psmt1.setInt(1,businessUserId);
			rs1=psmt1.executeQuery();
			if(rs1.next())
			{
				dto1.setLocId(rs1.getInt("LOC_ID"));
				dto1.setCircleCode(rs1.getString("CIRCLE_CODE"));
				dto1.setHubCode(rs1.getString("HUB_CODE"));
			}
			
			// when base location is same and is city or Zone
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				//checking the status of the location.
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(dto1.getLocId());
				if(locationDto.getStatus().equalsIgnoreCase(Constants.strActive))
				{			
					noofRow=countNumberOfRows(String.valueOf(dto1.getLocId()), masterId , mappingType);
				}else{
					noofRow=0;
				}
							
			}
			// when base location is different and is Zone-city
			else if(mappingType==Constants.ZSM)
			{
				//first getting the list of active cities in the zone.
				
				LocationDataDAO locDao = new LocationDataDAO();
				ArrayList locList = new ArrayList();
				ArrayList cityList = new ArrayList();
				locList = locDao.getLocationList(String.valueOf(dto1.getLocId()), Constants.City, Constants.PAGE_FALSE, 0, 0);
				
				if(locList.isEmpty())
				{
					noofRow=0;
				}
				else{
					// obtaining list of loaction id's
					Iterator itr = locList.iterator();
					while(itr.hasNext())
					{
						LocationDataDTO locDto = (LocationDataDTO)itr.next();
						cityList.add(String.valueOf((locDto.getLocDataId())));
					}
					String roleArray [] = (String []) cityList.toArray (new String [0]);
					String strterr =Utility.arrayToString(roleArray,null);
					
					// list of users in the active territories
					noofRow=countNumberOfRows(strterr, masterId , mappingType);
				}
					
			}
			// when base location is different and is Circle-Zone
			else if(mappingType==Constants.SALES_HEAD)
			{
				LocationDataDAO locDao = new LocationDataDAO();
				ArrayList totalZones = new ArrayList();
				ArrayList totalZonesList = new ArrayList();
				//list of active cities in circles
				totalZones = locDao.getLocationList(strCircleId, Constants.Zone, Constants.PAGE_FALSE, 0, 0);
				if(totalZones.isEmpty())
				{
					noofRow=0;
				}
				else
				{	
						Iterator itr = totalZones.iterator();
						while(itr.hasNext())
						{
							LocationDataDTO locDto = (LocationDataDTO)itr.next();
							totalZonesList.add(String.valueOf((locDto.getLocDataId())));
						}
						
						String roleArray [] = (String []) totalZonesList.toArray (new String [0]);
						String strZones =Utility.arrayToString(roleArray,null);
						
						// list of users in the active zones
						noofRow=countNumberOfRows(strZones, masterId , mappingType);
				}
			}
			// when base location is diff and is HUb-Circle
			else if(mappingType==Constants.ED)
			{
				// getting active circle list under one hub
				ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
				ArrayList locList = new ArrayList();
				locList = circledao.getCircleListByHub(dto1.getHubCode());
				if(locList.isEmpty())
				{
					noofRow=0;
				}
				else
				{
					Iterator itr = locList.iterator();
					ArrayList circleList=new ArrayList();
					while(itr.hasNext())
					{
						LabelValueBean lbean =(LabelValueBean)itr.next();
						circleList.add("'"+lbean.getValue()+"'");
					}
					String roleArray [] = (String []) circleList.toArray (new String [0]);
					String strCircle =Utility.arrayToString(roleArray,null);
									
					// list of users in the active territories
					noofRow=countNumberOfRows(strCircle, masterId , mappingType);
				}
				
			}
			//when base location is same and is circle-circle
			else if(mappingType==Constants.CEO)
			{
				noofRow=countNumberOfRows(dto1.getCircleCode(), masterId , mappingType);
			}
			
			
			noofPages = PaginationUtils.getPaginationSize(noofRow);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Excetion Occured During call of countUnmappedUsers() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1,
						rs1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting countUnmappedUsers() method of SingleUploadDAO.");
		return noofPages;
	}
	/**
	 * this is supproting method for countUnmappedUsers()
	 * 
	 * @param locID : String
	 * @param masterId : int
	 * @param mappingType : int
	 * @return int
	 * @throws DAOException
	 */
	public int countNumberOfRows(String locID,int masterId,int mappingType)throws DAOException
	{
		logger.debug("Entering countNumberOfRows() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		ResultSet rs1=null;
		int noofRow=0;
		try{
			conn = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			String query = SingleUploadInterface.Number_unmapped_users;
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				query = query+" AND LOC_ID = ? WITH UR";
			}
			else if(mappingType==Constants.ZSM || mappingType==Constants.SALES_HEAD)
			{
				query = query+"  AND LOC_ID IN (<LOCATION>) WITH UR";
			}
			else if(mappingType==Constants.ED)
			{
				query = query+"  AND CIRCLE_CODE IN (<LOCATION>) WITH UR";
			}
			else if(mappingType==Constants.CEO)
			{
				query = query+"  AND CIRCLE_CODE = ? WITH UR";
			}
			psmt1 = conn.prepareStatement(query.replaceAll("<LOCATION>", locID));
			psmt1.setInt(1,0);
			psmt1.setInt(2,masterId);
			psmt1.setString(3,Constants.ActiveState);
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM))
			{
				psmt1.setInt(4, Integer.parseInt(locID));
			}else if((mappingType==Constants.CEO) )
			{
				psmt1.setString(4, locID);
			}
			rs1 = psmt1.executeQuery();
		
			if(rs1.next())
			{
				noofRow=rs1.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(
					"Excetion Occured During call of countNumberOfRows() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1,
						rs1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting countNumberOfRows() method of SingleUploadDAO.");	
		return noofRow;
			
	}
	
	
	/**
	 * this method gets the list of unmapped users which can be mapped to a given parent
	 * 
	 * @param businessUserId : int
	 * @param mappingtype : int
	 * @param strCircleId : int
	 * @return int
	 * @throws DAOException
	 */
	public SingleUploadDTO getUnmappedUserList(int id, String circleId, int lb,
			int ub, int mappingType) throws DAOException {
		logger
				.debug("Entering getUnmappedUserList() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		SingleUploadDTO dto1 = new SingleUploadDTO();
		ResultSet rs1 = null;
		int masterId=mappingType+1;
		ArrayList list =new ArrayList();
		
		/*
		 * to get the list of lower end users , for example in Distributor - Fos
		 * Mapping , here we need Fos List and for that we have to do.
		 */

		try {
			conn = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			
//			 getting the location id
			psmt1 = conn.prepareStatement(SingleUploadInterface.Bussiness_data);
			psmt1.setInt(1,id);
			rs1=psmt1.executeQuery();
			if(rs1.next())
			{
				dto1.setBusinessUserId(rs1.getInt("DATA_ID"));
				dto1.setBusinessUserCode(rs1.getString("USER_CODE"));
				dto1.setBusinessUserName(rs1.getString("USER_NAME"));
				dto1.setLocId(rs1.getInt("LOC_ID"));
				dto1.setCircleCode(rs1.getString("CIRCLE_CODE"));
				dto1.setHubCode(rs1.getString("HUB_CODE"));
			}
			
//			 when base location is same and is territory or Zone
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				//checking the status of the location.
				LocationDataDAO locationDao = new LocationDataDAO();
				LocationDataDTO locationDto =  new LocationDataDTO();
				locationDto = locationDao.getLocationDetails(dto1.getLocId());
				if(locationDto.getStatus().equalsIgnoreCase(Constants.strActive))
				{
					list=listOfBizUsers(String.valueOf(dto1.getLocId()), masterId , mappingType,lb,ub);
				}
			}
			// when base location is different and is Zone-city
			else if(mappingType==Constants.ZSM)
			{
				//first getting the list of active cities in the zone.
				
				LocationDataDAO locDao = new LocationDataDAO();
				ArrayList locList = new ArrayList();
				ArrayList cityList = new ArrayList();
				
				locList = locDao.getLocationList(String.valueOf(dto1.getLocId()), Constants.City, Constants.PAGE_FALSE, 0, 0);
//				 obtaining list of loaction id's
				if(locList.isEmpty())
				{
					noofRow=0;
				}
				else
				{
					Iterator itr = locList.iterator();
					while(itr.hasNext())
					{
						LocationDataDTO locDto = (LocationDataDTO)itr.next();
						cityList.add(String.valueOf((locDto.getLocDataId())));
					}
					
					
					String roleArray [] = (String []) cityList.toArray (new String [0]);
					String strterr =Utility.arrayToString(roleArray,null);
					
					// list of users in the active territories
					list=listOfBizUsers(strterr, masterId , mappingType,lb,ub);
				}
						
			}
			
			else if(mappingType==Constants.SALES_HEAD)
			{
				LocationDataDAO locDao = new LocationDataDAO();
				ArrayList totalZones = new ArrayList();
				ArrayList totalZonesList = new ArrayList();
				//list of active cities in circles
				totalZones = locDao.getLocationList(circleId, Constants.Zone, Constants.PAGE_FALSE, 0, 0);
				if(totalZones.isEmpty())
				{
					noofRow=0;
				}
				else
				{	
						Iterator itr = totalZones.iterator();
						while(itr.hasNext())
						{
							LocationDataDTO locDto = (LocationDataDTO)itr.next();
							totalZonesList.add(String.valueOf((locDto.getLocDataId())));
						}
						
						String roleArray [] = (String []) totalZonesList.toArray (new String [0]);
						String strZones =Utility.arrayToString(roleArray,null);
						
						// list of users in the active zones
						list=listOfBizUsers(strZones, masterId , mappingType,lb,ub);
				}
			}
			
			// when base location is different and is HUb-Circle
			else if(mappingType==Constants.ED)
			{
				// getting active circle list under one hub
				ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
				ArrayList locList = new ArrayList();
				locList = circledao.getCircleListByHub(dto1.getHubCode());
				if(locList.isEmpty())
				{
					noofRow=0;
				}
				else
				{
					Iterator itr = locList.iterator();
					ArrayList circleList=new ArrayList();
					while(itr.hasNext())
					{
						LabelValueBean lbean =(LabelValueBean)itr.next();
						circleList.add("'"+lbean.getValue()+"'");
					}
					String roleArray [] = (String []) circleList.toArray (new String [0]);
					String strCircle =Utility.arrayToString(roleArray,null);
									
					// list of users in the active territories
					list=listOfBizUsers(strCircle, masterId , mappingType,lb,ub);
				}
				
				
			}
			//when base location is same and is Hub-Hub
			else if(mappingType==Constants.CEO)
			{
				list=listOfBizUsers(dto1.getCircleCode(), masterId , mappingType,lb,ub);
			}
			dto1.setMappedBusinessUserList(list);
			
			
			
			
		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of getUnmappedUserList() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
			logger
					.debug("Exiting getUnmappedUserList() method of SingleUploadDAO.");
		}
		return dto1;
	}
	/**
	 * this is supproting method for countUnmappedUsers()
	 * 
	 * @param locID : String
	 * @param masterId : int
	 * @param mappingType : int
	 * @return int
	 * @throws DAOException
	 */
	public ArrayList listOfBizUsers(String locID,int masterId,int mappingType,int lb,int ub)throws DAOException
	{
		logger.debug("Entering listOfBizUsers() method of SingleUploadDAO.");
		Connection conn = null;
		PreparedStatement psmt1 = null;
		ResultSet resultSet=null;
		ArrayList list = new ArrayList();
		try{
			conn = DBConnection
			.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			String query = SingleUploadInterface.List_unmapped_users;
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM) )
			{
				query = query+" AND LOC_ID = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if(mappingType==Constants.ZSM || mappingType==Constants.SALES_HEAD)
			{
				query = query+"  AND LOC_ID IN (<LOCATION>) ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if(mappingType==Constants.ED)
			{
				query = query+"  AND CIRCLE_CODE IN (<LOCATION>) ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			else if(mappingType==Constants.CEO)
			{
				query = query+"  AND CIRCLE_CODE = ? ) a )b Where rnum<=? and rnum>=? WITH UR";
			}
			psmt1 = conn.prepareStatement(query.replaceAll("<LOCATION>",locID));
			psmt1.setInt(1,0);
			psmt1.setInt(2,masterId);
			psmt1.setString(3,Constants.ActiveState);
			if( (mappingType==Constants.TM) ||
					(mappingType==Constants.DISTIBUTOR) ||
						(mappingType==Constants.FOS) || (mappingType==Constants.ZBM)  )
			{
				psmt1.setInt(4, Integer.parseInt(locID));
				psmt1.setString(5, String.valueOf(ub));
				psmt1.setString(6, String.valueOf(lb+1));
			}else if((mappingType==Constants.CEO) )
			{
				psmt1.setString(4, locID);
				psmt1.setString(5, String.valueOf(ub));
				psmt1.setString(6, String.valueOf(lb+1));
			}else
			{
				psmt1.setString(4, String.valueOf(ub));
				psmt1.setString(5, String.valueOf(lb+1));
			}
			
			resultSet = psmt1.executeQuery();
			while (resultSet.next()) {
				SingleUploadDTO dto = new SingleUploadDTO();
				dto.setMappedBusinessUserCode(resultSet.getString("USER_CODE"));
				dto.setMappedBusinessUserName(resultSet.getString("USER_NAME"));
				dto.setMappedBusinessUserId(resultSet.getInt("DATA_ID"));
				list.add(dto);

			}

		
		}catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of listOfBizUsers() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(conn, psmt1,
						resultSet);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger.debug("Exiting listOfBizUsers() method of SingleUploadDAO.");	
		return list;
			
	}

	/**
	 * this method insert the new mapping
	 * 
	 * 
	 * @param mappingtype :
	 *            int
	 * @param mappedUserId :
	 *            int
	 * @param userId :
	 *            int
	 * @return int
	 * @throws DAOException
	 */
	public int insertSelectedUserMapping(int userId, int mappedUserId,
			int mappingType) throws DAOException {
		logger
				.debug("Entering insertSelectedUserMapping() method of SingleUploadDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		int rowsUpdated = 0;
		int i = 0;
		try {
			connection = DBConnection
					.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			connection.setAutoCommit(false);
			prepareStatement = connection
					.prepareStatement(SingleUploadInterface.INSERT_Business_User_MAPPING);
			prepareStatement.setInt(1, userId);
			prepareStatement.setInt(2, mappedUserId);
			rowsUpdated = prepareStatement.executeUpdate();
			i++;
			connection.commit();

		} catch (SQLException e) {
			throw new DAOException(
					"Excetion Occured During call of insertSelectedUserMapping() method ",
					e);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (SQLException e) {
				throw new DAOException(
						"Exection in closing database resources.", e);
			}
		}
		logger
				.debug("Exiting insertSelectedUserMapping() method of SingleUploadDAO.");
		return rowsUpdated;
	}
}
