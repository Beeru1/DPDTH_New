/**************************************************************************
 * File     : SMSPullReportServlet.java
 * Author   : Aalok
 * Created  : july 22, 2008
 * Modified :  july 22, 20088
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
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.SmsPermissionDaoInterface;
import com.ibm.virtualization.ussdactivationweb.dto.SmsPermissionDto;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;


/**
 * This class is used for database transaction
 * to manage the sms configuration. 
 * 
 * @author Aalok Sharma
 * 
 */
public class SmsPermissionDaoImpl implements SmsPermissionDaoInterface{
	final Logger logger = Logger.getLogger(SmsPermissionDaoImpl.class.getName());

	/**
	 * This method gets all the requester Ids form the database.
	 * 
	 * @return map object containing requestor ids
	 * @throws DAOException in case of any database exception
	 */
	public LinkedHashMap getAllRequesterIds()throws DAOException	{
		
		LinkedHashMap requesterIdsMap = new LinkedHashMap();
		Connection dataConn = null;
		PreparedStatement stmt = null; 
		ResultSet rs = null;

		try {
			dataConn=DBConnection.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.QUERY_SMS_REQUESTER_ID);
		
			rs = stmt.executeQuery();
			while(rs.next()){
				//requesterIdsMap.put(rs.getString("SMS_REQUESTER_ID"),rs.getString("SMS_REQUESTER_USER_TYPE"));
				requesterIdsMap.put(rs.getString("MASTER_ID"),rs.getString("USER_TYPE"));
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("Unable to retrieve the information");
		} catch (Exception e1) {

			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		return requesterIdsMap;
	}
	
	/**
	 * This method gets all the report Ids form the database.
	 * 
	 * @return map object containing report ids
	 * @throws DAOException in case of any database exception
	 */
	public LinkedHashMap getAllReportIds()throws DAOException	{
		
		LinkedHashMap reportIdsMap = new LinkedHashMap();
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.QUERY_SMS_REPORT_ID);
			stmt.setString(1, Constants.SMS);
			rs = stmt.executeQuery();
			while(rs.next()){
				//reportIdsMap.put(rs.getString("SMS_REPORT_ID"),rs.getString("SMS_REPORT_NAME"));
				reportIdsMap.put(rs.getString("REPORT_ID"),rs.getString("REPORT_NAME"));
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("Unable to retrieve the information");
		} catch (Exception e1) {

			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		return reportIdsMap;
	}
	
	/**
	 * This method gets all the configuration data between the requester and the 
	 * reports.
	 * 
	 * @param circleCode report circle list
	 * @return permission list data 
	 * @throws DAOException in case of any database exception
	 */
	public ArrayList getSmsPermissionConfiguraton(String circleCode)throws DAOException	{
		ArrayList smsConfigIds =new ArrayList();
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.QUERY_SMS_PERMISSION_CONFIGURATION);
			stmt.setString(1, circleCode);
			rs = stmt.executeQuery();

			while(rs.next()){
				SmsPermissionDto smsPermissionDto = new SmsPermissionDto();
				smsPermissionDto.setSmsReportId(rs.getString("SMS_REPORT_ID"));
				smsPermissionDto.setSmsRequesterId(rs.getString("SMS_REQUESTER_ID"));
				smsConfigIds.add(smsPermissionDto);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {
			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		return smsConfigIds;
	}
	
	/**
	 * This method saves the configuration data 
	 * into the database.
	 * 
	 * @param circleCode  circel code of report
	 * @param smsPermissionDtoList list of dtos having permission data
	 * @return boolean values 
	 * @throws DAOException in case of any database exception
	 */
	public boolean saveSmsPermissionConfiguraton(String circleCode, ArrayList smsPermissionDtoList)throws DAOException	{
		Connection dataConn = null;
		PreparedStatement stmt = null;
		SmsPermissionDto smsPermissionDto = new SmsPermissionDto();
		
		Iterator iterator;
		ResultSet rs = null;
		int[] numRows={};
		try {
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			dataConn.setAutoCommit(false);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.QUERY_DELETE_SMS_PERMISSION_CONFIGURATION_BY_CIRCLE_CODE);
			stmt.setString(1,circleCode);
			stmt.executeUpdate();
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.QUERY_INSERT_SMS_PERMISSION_CONFIGURATION);
			iterator = smsPermissionDtoList.iterator();
			while (iterator.hasNext()) {
				smsPermissionDto=(SmsPermissionDto)iterator.next();
				stmt.setInt(1, Integer.parseInt(smsPermissionDto.getSmsRequesterId()));
				stmt.setInt(2,Integer.parseInt(smsPermissionDto.getSmsReportId()));
				stmt.setString(3,circleCode);
				stmt.addBatch();
			}
			numRows = stmt.executeBatch();
			logger.debug("Execution successful: " + numRows.length + " rows updated");
			dataConn.commit();
		
		} catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			byte counter = 1;
			SQLException sqlExNext = e.getNextException();

			while (sqlExNext != null && counter <= 4) {
				logger.error(sqlExNext.getMessage(), sqlExNext);
				sqlExNext = e.getNextException();
				counter++;
			}
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {

			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
			
		}
		return true;
	}
	
	
	public void getListSmsReportMstr(SmsPermissionDto smsdto)throws DAOException	{
		
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		SmsPermissionDto innersmsdto=null;
		ArrayList edList = new ArrayList();
		ArrayList ceoList = new ArrayList();
		ArrayList shList = new ArrayList();
		ArrayList zbmList = new ArrayList();
		ArrayList zsmList = new ArrayList();
		ArrayList tmList = new ArrayList();
		ArrayList distList = new ArrayList();
		ArrayList fosList = new ArrayList();
		ArrayList deaList = new ArrayList();
		try{
			
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.SELECT_ALL_FROM_SMS_REPORT_MSTR);
			rs=stmt.executeQuery();
			while(rs.next()){
				innersmsdto = new SmsPermissionDto();
				if(rs.getInt("USER_MSTR_ID")==Constants.ED){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					edList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.CEO){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					ceoList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.SALES_HEAD){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					shList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.ZBM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					zbmList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.ZSM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					zsmList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.TM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					tmList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.DISTIBUTOR){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					distList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.FOS){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					fosList.add(innersmsdto);
				}else if(rs.getInt("USER_MSTR_ID")==Constants.DEALER){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					innersmsdto.setReportName(rs.getString("REPORT_NAME"));
					deaList.add(innersmsdto);
				}
				
			}
			
			smsdto.setEdListSmsReport(edList);
			smsdto.setCeoListSmsReport(ceoList);
			smsdto.setShListSmsReport(shList);
			smsdto.setZbmListSmsReport(zbmList);
			smsdto.setZsmListSmsReport(zsmList);
			smsdto.setZbmListSmsReport(zbmList);
			smsdto.setTmListSmsReport(tmList);
			smsdto.setDistListSmsReport(distList);
			smsdto.setFosListSmsReport(fosList);
			smsdto.setDeaListSmsReport(deaList);
		}catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {
			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		
	}
	
	public boolean insertSmsConfig(ArrayList listofpermissions,String userName,String circleCode)throws DAOException	{
		Connection dataConn = null;
		PreparedStatement deleteStmt = null;
		PreparedStatement insertStmt = null;
		int[] numRows={};
				Iterator iterator;
		try{
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			dataConn.setAutoCommit(false);
			
			//deleting 
			deleteStmt = dataConn.prepareStatement(SmsPermissionDaoInterface.DELETE_FROM_SMS_REPORT_MSTR);
			deleteStmt.setString(1,circleCode);
			deleteStmt.setInt(2,Constants.ED);
			deleteStmt.executeUpdate();
			
			//dataConn.setAutoCommit(false);
			insertStmt = dataConn.prepareStatement(SmsPermissionDaoInterface.INSERT_INTO_SMS_REPORT_MSTR);			
			iterator = listofpermissions.iterator();
			while (iterator.hasNext()) {
				SmsPermissionDto smsPermissionDto=(SmsPermissionDto)iterator.next();
				insertStmt.setInt(1,smsPermissionDto.getUserType());
				insertStmt.setInt(2,smsPermissionDto.getReportId());
				insertStmt.setString(3,userName);
				insertStmt.setString(4,userName);
				insertStmt.setString(5,smsPermissionDto.getCircleCode());
				insertStmt.addBatch();
			}
			numRows = insertStmt.executeBatch();
			logger.debug("Execution successful: " + numRows.length + " rows updated");
			dataConn.commit();
		}catch (SQLException e) {
			e.printStackTrace();
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			byte counter = 1;
			SQLException sqlExNext = e.getNextException();

			while (sqlExNext != null && counter <= 4) {
				logger.error(sqlExNext.getMessage(), sqlExNext);
				//System.out.println(sqlExNext);
				sqlExNext = e.getNextException();
				counter++;
			}
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {

			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(null, deleteStmt);
				DBConnectionUtil.closeDBResources(dataConn,insertStmt);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
			
		}
		
		return true;
	}
	
	public void getListSmsConfig(SmsPermissionDto smsdto,String circleCode)throws DAOException	{
		
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		SmsPermissionDto innersmsdto=null;
		ArrayList ceoList = new ArrayList();
		ArrayList shList = new ArrayList();
		ArrayList zbmList = new ArrayList();
		ArrayList zsmList = new ArrayList();
		ArrayList tmList = new ArrayList();
		ArrayList distList = new ArrayList();
		ArrayList fosList = new ArrayList();
		ArrayList deaList = new ArrayList();
		try{
			
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.SELECT_ALL_FROM_SMS_CONFIG);
			stmt.setString(1, circleCode);
			rs=stmt.executeQuery();
			while(rs.next()){
				innersmsdto = new SmsPermissionDto();
				if(rs.getInt("SMS_REQUESTER_ID")==Constants.CEO){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					ceoList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.SALES_HEAD){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					shList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.ZBM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					zbmList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.ZSM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					zsmList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.TM){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					tmList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.DISTIBUTOR){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					distList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.FOS){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					fosList.add(innersmsdto);
				}else if(rs.getInt("SMS_REQUESTER_ID")==Constants.DEALER){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					deaList.add(innersmsdto);
				}
				
			}
			
			smsdto.setCeoListSmsConfig(ceoList);
			smsdto.setShListSmsConfig(shList);
			smsdto.setZbmListSmsConfig(zbmList);
			smsdto.setZsmListSmsConfig(zsmList);
			smsdto.setZbmListSmsConfig(zbmList);
			smsdto.setTmListSmsConfig(tmList);
			smsdto.setDistListSmsConfig(distList);
			smsdto.setFosListSmsConfig(fosList);
			smsdto.setDeaListSmsConfig(deaList);
		}catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {
			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		
	}
	
	public void getListSmsConfigOfED(SmsPermissionDto smsdto)throws DAOException	{
		
		Connection dataConn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		SmsPermissionDto innersmsdto=null;
		ArrayList edList = new ArrayList();
		try{
			
			dataConn=DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			stmt = dataConn.prepareStatement(SmsPermissionDaoInterface.SELECT_ALL_FROM_SMS_CONFIG_ED);
			stmt.setInt(1,Constants.ED);
			rs=stmt.executeQuery();
			while(rs.next()){
				innersmsdto = new SmsPermissionDto();
				if(rs.getInt("SMS_REQUESTER_ID")==Constants.ED){				
					innersmsdto.setReportId(rs.getInt("SMS_REPORT_ID"));
					edList.add(innersmsdto);
				}
				
			}			
			smsdto.setEdListSmsConfig(edList);
			
		}catch (SQLException e) {
			logger.error("SQL Exception occured while databsae operation .Exception Message : ", e);
			throw new DAOException("INsertion Failed");
		} catch (Exception e1) {
			logger.error("System Exception occured while operation .Exception Message : ", e1);
			throw new DAOException("SYSTEM-ERROR");
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(dataConn,stmt,rs);
			} catch (SQLException e) {
				new DAOException("SQLException occur while closing database resources.");
			}
		}
		
	}
}
