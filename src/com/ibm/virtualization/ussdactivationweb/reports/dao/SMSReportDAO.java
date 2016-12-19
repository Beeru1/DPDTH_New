
/**************************************************************************
 * File     : SMSReportDAO.java
 * Author   : Ashad
 * Created  : Oct 10, 2008
 * Modified : Oct 10, 2008
 * Version  : 1.0
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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.daoInterfaces.CircleMasterInterface;
//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportDTO;
import com.ibm.virtualization.ussdactivationweb.reports.daoInterface.SMSReportDAOInterface;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportConfigInitializer;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnection;
import com.ibm.virtualization.ussdactivationweb.utils.DBConnectionUtil;
import com.ibm.virtualization.ussdactivationweb.utils.SQLConstants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author Ashad
 * This class is used for SMSReport
 */

public class SMSReportDAO implements SMSReportDAOInterface {

	private static final Logger log = Logger.getLogger(SMSReportDAO.class);

	static ResourceBundle  resourceBundle = null;
	
	static {
		/**  Getting resource bundle refrence **/
		resourceBundle = ResourceBundle.getBundle("com.ibm.virtualization.ussdactivationweb.resources.SMSReport");
	}	
	

	/**
	 * This method check the requester permission
	 * @param businessUserDTO contais user info
	 *  @param reportId is the requested report 
	 * @return int value (1 (Daily)/2 (Monthly)/-1 (having No Permission))
	 */
	public int verifyRequestorPermission(BusinessUserDTO businessUserDTO,String reportId) throws DAOException{

		log.debug("Entering in verifyRequestorPermission() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int userTypeId = businessUserDTO.getTypeOfUserId();
		String circleCode = businessUserDTO.getCircleCode();
		int rptTypeValue = Constants.NO_PERMISSION;
		try{

			/*if(circleCode == null) {
   				throw new DAOException("Business User " + userTypeId + " does not have circle information.");
   			}*/
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(circleCode == null ){
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.QUERY_SMS_PERMISSION);
				preparedStatement.setString(1, reportId);
				preparedStatement.setInt(2, userTypeId);
			} else {
				StringBuffer query =new StringBuffer(300).append(SMSReportDAOInterface.QUERY_SMS_PERMISSION).append(" AND SG.CIRCLE_CODE=? WITH UR ");
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.setString(1, reportId);
				preparedStatement.setInt(2, userTypeId);
				preparedStatement.setString(3, circleCode);
			}
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				String rptTypeFlag = resultSet.getString("REPORT_TYPE");

				if(Constants.InActive.equalsIgnoreCase(rptTypeFlag)){
					rptTypeValue = Constants.DAILY_REPORT;
				} else if("M".equalsIgnoreCase(rptTypeFlag)){
					rptTypeValue = Constants.MONTHLY_REPORT;
				}

			} 

		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException("Unauthorized Access", sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		log.debug("Exiting in verifyRequestorPermission() method of class SMSReportDAO.");	
		return rptTypeValue;
	}

	/**
	 * Returns  pendig status for the for the requester registered number.
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param rptTypeFlag (1 (daily) / 2 (Monthly))
	 * @throws DAOException is thrown in case of any exception.
	 */
	/*
	public String getDistPendingActCount(String requesterRegdNo, String rptDate, int rptTypeFlag)
	throws DAOException {
		log.debug("Entering in getDistPendingActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage ="";
		String strRptDate = "";
		SMSReportDTO smsReportDTO = null;
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(rptDate == null || "".equals(rptDate)){
				
				smsReportDTO = getCurrentDate();
				strRptDate = smsReportDTO.getCurrentDateAsString();
				reportDate = smsReportDTO.getSqlRptDate();
				
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT).
				append(" AND STATUS=? AND DATE(UPDATED_DT)=? ").append("GROUP BY REQUESTER_TYPE WITH UR");

				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, requesterRegdNo);
				preparedStatement.setString(2, Constants.PENDING_REPORT);
				preparedStatement.setDate(3, reportDate);

			} else if( rptTypeFlag == Constants.DAILY_REPORT){
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT).append(" AND STATUS=? AND DATE(UPDATED_DT)=? ")
				.append("GROUP BY REQUESTER_TYPE WITH UR");

				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, requesterRegdNo);
				preparedStatement.setString(2, Constants.PENDING_REPORT);
				preparedStatement.setDate(3, reportDate);

			} else if( rptTypeFlag == Constants.MONTHLY_REPORT){
				strRptDate = rptDate;
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT).append(" WHERE STATUS=? AND DATE(UPDATED_DT) BETWEEN ? AND ? ")
				.append("GROUP BY REQUESTER_TYPE WITH UR");;

				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, requesterRegdNo);
				preparedStatement.setString(2, Constants.PENDING_REPORT);
				preparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				isMonthlyReport=true;
			}

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				String userType = resultSet.getString("REQUESTER_TYPE");
				if(isMonthlyReport){
					smsMessage =  new StringBuffer(500)
					.append("Pending Activation Count for ").append(userType).append(" ")
					.append(requesterRegdNo).append(" is ")
					.append(resultSet.getString("ACTV_COUNT"))
					.append(" for the month of ").append(monthOftheDate).append(" as of the date ").append(strRptDate).toString();
					log.debug(smsMessage);
				} else {
					smsMessage =  new StringBuffer(500)
					.append("Pending Activation Count for ").append(userType).append(" ")
					.append(requesterRegdNo).append(" is ")
					.append(resultSet.getString("ACTV_COUNT")).append(" as of the date ").append(strRptDate).toString();
					log.info(smsMessage);
				}

			} else {
				smsMessage =  new StringBuffer(500)
				.append("Pending Activation Count for ").append(requesterRegdNo).append(" is 0 as of date ").append(strRptDate).toString();
				log.info(smsMessage);
			}

		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;

	}
*/

	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param rptTypeFlag (1 (daily) / 2 (Monthly))
	 * @throws DAOException is thrown in case of any exception.
	 */
/*
	public String getDistActCount(BusinessUserDTO businessUserDTO,String requesterRegdNo, 
			String rptDate) throws DAOException{
		log.debug("Entering  in getDistActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage = "";
		String strRptDate = "";
		SMSReportDTO smsReportDTO =null;
		String ftdCount="";
		String mtdCount="";
		String distName = businessUserDTO.getName();
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(rptDate == null || "".equals(rptDate)){
					//FTD Count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();
					
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT)
					.append(" AND STATUS=? AND DATE(UPDATED_DT)=?  ")
					.append("GROUP BY REQUESTER_TYPE WITH UR");
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, requesterRegdNo);
					preparedStatement.setString(2, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(3, reportDate);	
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						ftdCount = resultSet.getString("ACTV_COUNT");
					}

					//MTD Count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT)
					.append(" AND STATUS=? AND DATE(UPDATED_DT) BETWEEN ? AND ? ")
					.append("GROUP BY REQUESTER_TYPE WITH UR");
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, requesterRegdNo);
					preparedStatement.setString(2, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					preparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						mtdCount = resultSet.getString("ACTV_COUNT");
					}

				log.info("completeQuery = "+completeQuery.toString());

			} else {
				//FTD Count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT)
				.append(" AND STATUS=? AND DATE(UPDATED_DT)=?  ")
				.append("GROUP BY REQUESTER_TYPE WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, requesterRegdNo);
				preparedStatement.setString(2, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(3, reportDate);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					ftdCount = resultSet.getString("ACTV_COUNT");
				}
				//MTD Count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTIVATION_COUNT)
				.append(" AND STATUS=? AND DATE(UPDATED_DT) BETWEEN ? AND ? ")
				.append("GROUP BY REQUESTER_TYPE WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, requesterRegdNo);
				preparedStatement.setString(2, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					mtdCount = resultSet.getString("ACTV_COUNT");
				}
			}if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
					smsMessage =  new StringBuffer(500)
					.append("Total Sales for Distributor ")
					.append(distName).append(" FTD/MTD is: ")
					.append(ftdCount)
					.append("/")
					.append(mtdCount).toString();
					log.debug(smsMessage);
				} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}

			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);
		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/
	/**
	 * This method returns total activation done for specific HUB
	 * @param hubCode hub code
	 * @param rptDate requested date (if rptDate is null then the report requested date would be current date)
	 * @param rptTypeFlag (1 (daily) / 2 (Monthly))
	 * @throws DAOException is thrown in case of any exception.
	 */
	/*
	public String getHubWiseActCount(String hubCode,String rptDate)throws DAOException{
		log.debug("Entering  in getHubWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String strCircle="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";

		try{

			ArrayList  circleList = getCircleListByHub(hubCode);	
			if(circleList != null && !circleList.isEmpty()){
				String roleArray [] = (String []) circleList.toArray (new String [0]);
				strCircle =Utility.arrayToString(roleArray,null); 
			} else {
				strCircle="'"+"1238765CA"+"'"; // Dummy Data
			}
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);

			if(rptDate == null || "".equals(rptDate)){
					//FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_HUB_WISE.replaceAll("<CIRCLE_CODE>", strCircle))
					.append(" AND DATE(UPDATED_DT)=? WITH UR"); 
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1,Constants.SUCCESS_REPORT);
					preparedStatement.setDate(2, reportDate);
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						ftdCount = resultSet.getString("ACTV_COUNT");
					}
					//MTD count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_HUB_WISE.replaceAll("<CIRCLE_CODE>", strCircle)).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1,Constants.SUCCESS_REPORT);
					preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						mtdCount = resultSet.getString("ACTV_COUNT");
					}

					log.info("firstDateOfMonth , lastDateOfMonth == "+new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy"))+","+new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
			} else{
				//FTD count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_HUB_WISE.replaceAll("<CIRCLE_CODE>", strCircle))
				.append(" AND DATE(UPDATED_DT)=? WITH UR"); 
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1,Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					ftdCount = resultSet.getString("ACTV_COUNT");
				}
				//MTD COUNT
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_HUB_WISE.replaceAll("<CIRCLE_CODE>", strCircle)).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1,Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					mtdCount = resultSet.getString("ACTV_COUNT");
				}
				log.info("firstDateOfMonth , lastDateOfMonth == "+new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy"))+","+new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
			}
				if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
					smsMessage =  new StringBuffer(500)
					.append("Total Sales for Hub ")
					.append(hubCode).append(" FTD/MTD is: ")
					.append(ftdCount)
					.append("/")
					.append(mtdCount).toString();
					log.debug(smsMessage);
				} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}

		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}

		return smsMessage;
	}
*/
	/**
	 * This method returns total activation done for specific Circle
	 * @param circleCode  circle code
	 * @param rptDate requested date (if rptDate is null then the report requested date would be current date)
	 * @param rptTypeFlag (1 (daily) / 2 (Monthly))
	 * @throws DAOException is thrown in case of any exception.
	 */
	/*
	public String getCircleWiseActCount(String circleCode,String rptDate) throws DAOException {

		log.debug("Entering  in getCircleWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate =null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";

		if(circleCode != null && !"".equals(circleCode)){
			circleCode = circleCode.trim().toUpperCase();
		}
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(rptDate == null || "".equals(rptDate)){
					// FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CIRCLE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, circleCode);
					preparedStatement.setString(2, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(3, reportDate);
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						ftdCount = resultSet.getString("ACTV_COUNT");
					}
					//MTD Count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CIRCLE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, circleCode);
					preparedStatement.setString(2, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					preparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						mtdCount = resultSet.getString("ACTV_COUNT");
					}
			} else{
				//FTD count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CIRCLE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, circleCode);
				preparedStatement.setString(2, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(3, reportDate);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					ftdCount = resultSet.getString("ACTV_COUNT");
				}
				//MTD Count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CIRCLE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, circleCode);
				preparedStatement.setString(2, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					mtdCount = resultSet.getString("ACTV_COUNT");
				}
			}
			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for Circle ")
				.append(circleCode).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
			log.error(Constants.SMS_COMMON_MESAGE);
			return Constants.SMS_COMMON_MESAGE;
		}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/
	/**
	 * Returns no of activation done by requester(Dealer) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param rptTypeFlag (1 (daily) / 2 (Monthly))
	 * @throws DAOException is thrown in case of any exception.
	 */
	/*
	public String getDealerActCount(BusinessUserDTO businessUserDTO,String requesterRegdNo, String rptDate) throws DAOException{
		log.debug("Entering  in getDealerActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";
		String dlrName = businessUserDTO.getName();
		try{
			String MSISDN=""; // dummy data
			ArrayList msisdnList= new ArrayList();
			msisdnList= getMSISDNList(requesterRegdNo);
			if(msisdnList != null && !msisdnList.isEmpty()){
				String msisdArray [] = (String []) msisdnList.toArray (new String [0]);
				MSISDN =Utility.arrayToString(msisdArray,null);
			} else {
				MSISDN="'"+219871+"'";
			}
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			String dealerWiseQuery = SMSReportDAOInterface.SELECT_DEALER_ACT_COUNT.replaceAll("<MSISDN_LIST>", MSISDN);
			if(rptDate == null || "".equals(rptDate)){
					//FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();
					
					completeQuery.append(dealerWiseQuery).append(" AND STATUS=? AND DATE(UPDATED_DT)=? ")
					.append(" GROUP BY REQUESTER_TYPE WITH UR");;
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(2, reportDate);
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						ftdCount = resultSet.getString("ACTV_COUNT");
					}
					//MTD count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					
					completeQuery.append(dealerWiseQuery).append(" AND STATUS=? AND DATE(UPDATED_DT) BETWEEN ? AND ? ")
					.append(" GROUP BY REQUESTER_TYPE WITH UR"); 
					preparedStatement = connection.prepareStatement(completeQuery.toString());
					preparedStatement.setString(1, Constants.SUCCESS_REPORT);
					preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					resultSet = preparedStatement.executeQuery();
					if(resultSet.next()){
						mtdCount = resultSet.getString("ACTV_COUNT");
					}

			} else{
				//FTD count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(dealerWiseQuery).append(" AND STATUS=? AND DATE(UPDATED_DT)=? ")
				.append(" GROUP BY REQUESTER_TYPE WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);
				log.debug("completeQuery "+completeQuery);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					ftdCount = resultSet.getString("ACTV_COUNT");
				}

				//MTD count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(dealerWiseQuery).append(" AND STATUS=? AND DATE(UPDATED_DT) BETWEEN ? AND ? ")
				.append(" GROUP BY REQUESTER_TYPE WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					mtdCount = resultSet.getString("ACTV_COUNT");
				}
			}
			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for Dealer ")
				.append(dlrName).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
			log.error(Constants.SMS_COMMON_MESAGE);
			return Constants.SMS_COMMON_MESAGE;
		}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;

	}
*/
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getActStatusForSubs(String requesterRegdNo,String subscriberMSISDN, String rptDate)throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			preparedStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_ACTV_STATUS_FOR_SINGLE_MOBILE); 
			log.debug("SELECT_ACTV_STATUS_FOR_SINGLE_MOBILE "+SELECT_ACTV_STATUS_FOR_SINGLE_MOBILE);
			preparedStatement.setString(1, subscriberMSISDN);
			preparedStatement.setString(2, subscriberMSISDN);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				smsMessage =  new StringBuffer(500)
				.append("Status for Subscriber ").append(subscriberMSISDN).append(" : ")
				.append(resultSet.getString("SUBR_STATUS")).toString();
				log.debug(smsMessage);


			} else {
				smsMessage =  new StringBuffer(500)
				.append("Transaction has not been initiated for ")
				.append(subscriberMSISDN).append(" subscriber ").toString();
				log.debug(smsMessage);
			}

		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}

	/**
	 * Returns no of activation for specific service category 
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	/*
	public String getActServiceClassWise(String serviceClassId,String circleCode,String rptDate, int rptTypeFlag) throws DAOException{

		log.debug("Entering  in getActServiceCatWise() method of class SMSReportDAO.");	
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String strMsisdn=null;
		try{

			if(circleCode != null && !"".equals(circleCode)){
				circleCode = circleCode.trim().toUpperCase();
			}
			
			/** getting service class for specific category id *
			
			ArrayList msisdnList = getMSISDNByIdCode(serviceClassId,circleCode);
			
			log.info("msisdnList == "+msisdnList);
			
			if(msisdnList != null && !msisdnList.isEmpty()){
				String msisdnArray [] = (String []) msisdnList.toArray (new String [0]);
				strMsisdn =Utility.arrayToString(msisdnArray,null); 
			} 
			log.debug ("strServiceClass == "+strMsisdn);
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			StringBuffer completeQuery = new StringBuffer(SMSReportDAOInterface.SELECT_ACTV_COUNT_SERVICE_CLASS_WISE.replaceAll("<MSISDN>",strMsisdn));
			log.debug("completeQuery "+completeQuery.toString());
			if(rptDate == null || "".equals(rptDate)){
				
				smsReportDTO = getCurrentDate();
				strRptDate = smsReportDTO.getCurrentDateAsString();
				reportDate = smsReportDTO.getSqlRptDate();
				
				completeQuery.append(" AND DATE(TM.UPDATED_DT)= ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);

			} else if( rptTypeFlag == Constants.DAILY_REPORT){
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(" AND  DATE(TM.UPDATED_DT)= ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);

			} else if( rptTypeFlag == Constants.MONTHLY_REPORT){
				
				strRptDate = rptDate;
				
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(" AND DATE(TM.UPDATED_DT) BETWEEN ? AND ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				isMonthlyReport=true;

			}

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
               // String serviceClassName = new ServiceClassDAOImpl().getServiceClassNameById(Integer.parseInt(serviceClassId));
				if(isMonthlyReport){
					smsMessage =  new StringBuffer(500)
					.append("Activation Count for Service class ")
					.append(serviceClassId).append(" and ").append(circleCode).append(" Circle is ")
					.append(resultSet.getString("ACTV_COUNT"))
					.append(" for the month of ").append(monthOftheDate).append(" as of the date ").append(strRptDate).toString();
					log.debug(smsMessage);
				} else {
					smsMessage =  new StringBuffer(500)
					.append("Activation Count for Service class ")
					.append(serviceClassId).append(" and ").append(circleCode).append(" Circle is ")
					.append(resultSet.getString("ACTV_COUNT")).append(" as of the date ").append(strRptDate).toString();
					log.info(smsMessage);
				}

			} else {
				log.debug(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/
	/**
	 * This method is used to get ServiceClass list for specific categoryId
	 * 
	 * @ param categoryId object used for retrieving ServiceClass Information @ param
	 * 
	 * @throws DAOException is thrown in case of any exception.
	 * 
	 */
	public ArrayList  getSCListByCategoryId(String categoryDESC)
	throws DAOException {
		log.debug("Entering  in getSCListByCategoryId() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList scList=new ArrayList();
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_SERVICE_CLASS_BY_CATEGORY_NAME);
			prepareStatement.setString(1, categoryDESC);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				scList.add(resultSet.getString("SERVICECLASS_ID"));
			}
			if (scList.size() != 0) {
				return scList;
			} else {
				String noServiceClassFound =new StringBuffer(200).append("No Service class found in ")
				.append(categoryDESC).append(" Service Category").toString();
				log.debug(noServiceClassFound);
				throw new DAOException(noServiceClassFound);
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getServiceClassList in DAOImpl");
		}

	}

	/**
	 * method takes hub code as parameter and return the circle
	 * @return cicle code
	 * @throws DAOException is thrown in case of any exception.
	 */
	public ArrayList  getCircleListByHub(String hubCode)
	throws DAOException {
		log.debug("Entering  in getCircleListByHub() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList circleList=new ArrayList();
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_CIRCLE_BY_HUB);
			prepareStatement.setString(1, hubCode);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				circleList.add("'"+resultSet.getString("CIRCLE_CODE")+"'");
			}
			if (circleList.size() != 0) {
				return circleList;
			} else {
				log.debug("No Circle Exist in "+hubCode+" HUB");
				throw new DAOException("No Circle Exist in "+hubCode+" HUB");		
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getCircleListByHub in DAOImpl");
		}

	}
	
	/**
	 */
	public ArrayList getMSISDNList(String requesterNo) throws DAOException{

		log.debug("Entering  in getBUsersInfo() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList msisdnList=new ArrayList();
		Connection connection = null;
		//HashMap hashMap = new HashMap();
		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_MSISDN_BY_DEALER);
			prepareStatement.setString(1, requesterNo);
			resultSet = prepareStatement.executeQuery();
			//String userType = null;
			while (resultSet.next()) {
				//userType = resultSet.getString("REQUESTER_TYPE");
				msisdnList.add("'"+resultSet.getString("SUBS_MSISDN")+"'");
			}
			/*if(!msisdnList.isEmpty()){
				hashMap.put("msisdnList", msisdnList);
				hashMap.put("userType", userType);
			}*/
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getBUsersInfo in DAOImpl");
		}
		return msisdnList;
	} 

	public boolean getActiveMSISDNList(String subMSISDN) throws DAOException{

		log.debug("Entering  in getBUsersInfo() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		boolean isMSISDNExist=false;
		Connection connection = null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_ACTIVE_MSISDN);
			prepareStatement.setString(1, Constants.ActiveState);
			prepareStatement.setString(2, subMSISDN);
			resultSet = prepareStatement.executeQuery();
			if (resultSet.next()) {
				isMSISDNExist = true;
			}
		} catch (SQLException sqe) {
			sqe.printStackTrace();
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getBUsersInfo in DAOImpl");
		}
		return isMSISDNExist;
	} 

	
	public ArrayList  getMSISDNByIdCode(String serviceClassId,String circleCode)
	throws DAOException {
		log.debug("Entering  in getSCListByCategoryId() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList msisdnList=new ArrayList();
		Connection connection = null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_MSISDN_BY_SERVICEID_AND_CIRCLE_CODE);
			prepareStatement.setString(1, serviceClassId);
			prepareStatement.setString(2, circleCode);
			prepareStatement.setString(3, Constants.ActiveState);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				msisdnList.add("'"+resultSet.getString("MSISDN")+"'");
			}
			if (msisdnList.size() != 0) {
				return msisdnList;
			} else {
				
				msisdnList.add("' '");
				return msisdnList;
				/*String noMSISDNFound =new StringBuffer(200).append("No MSISDN found in ")
				.append(serviceClassId).append(" Service class , ")
				.append(circleCode).append("circle code").toString();
				log.debug(noMSISDNFound);
				throw new DAOException(noMSISDNFound);*/
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getServiceClassList in DAOImpl");
		}

	}

	/**
	 * This method getReportsSchedule() gets report ids and report
	 * date
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return ruleIdList
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */

	/*public ArrayList getReportsSchedule()
	throws DAOException {
		log.debug("Entering  in getReportsSchedule() method of class SMSReportDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList reports = new ArrayList();
		SMSReportDTO reptDto= null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			String getReportDetails = SMSReportDAOInterface.FETCH_REPORT_SCHEDULE;
			prepareStatement = connection.prepareStatement(getReportDetails);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				reptDto = new SMSReportDTO();
				reptDto.setReportId(String.valueOf(resultSet.getString("REPORT_ID")));
				if("Y".equalsIgnoreCase(resultSet.getString("FORCE_START"))){
					reptDto.setReportTime(resultSet.getTimestamp("FORCE_START_DATE"));
				} else {
					reptDto.setReportTime(resultSet.getTimestamp("REPORT_START_DATE"));					
				}

				reptDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				reptDto.setReportType(resultSet.getString("REPORT_TYPE"));

				reports.add(reptDto);
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION",sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.error("Exception",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getReportsSchedulethrows DAOException");

		}
		return reports;
	}
	*/
	/*
	public ArrayList getNewReportsSchedule()
	throws DAOException {
		log.debug("Entering  in getReportsSchedule() method of class SMSReportDAO.");
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		ArrayList reports = new ArrayList();
		SMSReportDTO reptDto= null;
		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			String getReportDetails = SMSReportDAOInterface.FETCH_NEW_REPORT_SCHEDULE;
			prepareStatement = connection.prepareStatement(getReportDetails);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				reptDto = new SMSReportDTO();
				reptDto.setReportId(String.valueOf(resultSet.getString("SMS_REPORT_ID")));
				reptDto.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				reptDto.setUserRole(resultSet.getString("SMS_REQUESTER_ID"));
				reptDto.setReportCode(resultSet.getString("REPORT_CODE"));
				reptDto.setSmsKeyword(resultSet.getString("SMS_KEYWORD"));
				reports.add(reptDto);
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION",sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.error("Exception",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getReportsSchedulethrows DAOException");

		}
		return reports;
	}
	
	/**
	 * This method updateReportEngineScheduleTime() updates Report Schedule Time
	 * in the database
	 * 
	 * @param connection
	 *            object of Connection, holds connection properties
	 * @return
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	
	/*
	public void updateReportEngineScheduleTime(SMSReportDTO reptDTO) throws DAOException {

		log.debug("Entering  in updateReportEngineScheduleTime() method of class SMSReportDAO.");

		Connection connection = null;
		PreparedStatement prepareStatement = null;

		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.UPDATE_REPORT_ENGINE_SCHEDULE_TIME);
			prepareStatement.setString(1,reptDTO.getCircleCode());
			prepareStatement.executeUpdate();
		} catch (SQLException sqe) {
			log.debug("SQL EXCEPTION",sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.debug("Exception",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method updateReportEngineScheduleTime throws DAOException");

		}

	}
	*/
	/**
	 * This method returns list of business users by report id and circle code 
	 * @param reportId input report 
	 * @param circleCode circle code 
	 * @return list of users
	 * * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	/*public ArrayList  getUsersByRtIdCode(String reportId,String circleCode) throws DAOException{

		log.debug("Entering  in getUsersByRtIdCode() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		ArrayList userList=new ArrayList();
		Connection connection = null;

		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(circleCode == null){
				prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_USER_ROLE_BY_REPORT_ID.concat(" AND CIRCLE_CODE IS NULL WITH UR"));
				prepareStatement.setString(1, reportId);
			} else {
				prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_USER_ROLE_BY_REPORT_ID.concat(" AND CIRCLE_CODE=? WITH UR"));
				prepareStatement.setString(1, reportId);
				prepareStatement.setString(2, circleCode);
			}
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {

				if(!circleListt.contains("'"+resultSet.getString("CIRCLE_CODE")+"'")){
							circleListt.add("'"+resultSet.getString("CIRCLE_CODE")+"'");
						}
				//if(!roleList.contains(resultSet.getString("SMS_REQUESTER_ID"))){
				SMSReportDTO  smsReportDTO =new SMSReportDTO();
				String userRole =resultSet.getString("SMS_REQUESTER_ID");
				smsReportDTO.setUserRole(userRole);
				if("1".equals(userRole) || "2".equals(userRole)){
					smsReportDTO.setCircleCode(null);
				} else {
					smsReportDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				}

				userList.add(smsReportDTO);
				//roleList.add(resultSet.getString("SMS_REQUESTER_ID"));
				//}

			}
			if(userList.size() ==0){
				log.debug("Users not found for reportid: " + reportId
						+ " , and circleCode " + circleCode);
				throw new DAOException("Users not found for reportid: " + reportId
						+ " , and circleCode " + circleCode);

			}

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getUserRoleByReportId in DAOImpl");
		}
		return userList; 

	}*/

	/*
	public ArrayList  getNewUsersByRtIdCode(String reportId,String circleCode) throws DAOException{

		log.debug("Entering  in getUsersByRtIdCode() method of class SMSReportDAO.");
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		ArrayList userList=new ArrayList();
		Connection connection = null;

		try {
			connection = DBConnection
			.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(circleCode == null || "".equals(circleCode)){
				prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_USER_ROLE_BY_REPORT_ID.concat(" AND CIRCLE_CODE IS NULL WITH UR"));
				prepareStatement.setString(1, reportId);
			} else {
				prepareStatement = connection.prepareStatement(SMSReportDAOInterface.SELECT_USER_ROLE_BY_REPORT_ID.concat(" AND CIRCLE_CODE=? WITH UR"));
				prepareStatement.setString(1, reportId);
				prepareStatement.setString(2, circleCode);
			}
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {

				/*if(!circleListt.contains("'"+resultSet.getString("CIRCLE_CODE")+"'")){
							circleListt.add("'"+resultSet.getString("CIRCLE_CODE")+"'");
						}
				//if(!roleList.contains(resultSet.getString("SMS_REQUESTER_ID"))){
				SMSReportDTO  smsReportDTO =new SMSReportDTO();
				String userRole =resultSet.getString("SMS_REQUESTER_ID");
				smsReportDTO.setUserRole(userRole);
				if("1".equals(userRole)){
					smsReportDTO.setCircleCode(null);
				} else {
					smsReportDTO.setCircleCode(resultSet.getString("CIRCLE_CODE"));
				}

				userList.add(smsReportDTO);
				//roleList.add(resultSet.getString("SMS_REQUESTER_ID"));
				//}

			}
			if(userList.size() ==0){
				log.debug("Users not found for reportid: " + reportId
						+ " , and circleCode " + circleCode);
				throw new DAOException("Users not found for reportid: " + reportId
						+ " , and circleCode " + circleCode);

			}

		} catch (SQLException sqe) {
			sqe.printStackTrace();
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage(), sqe);

		} catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement,
						resultSet);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log.debug("Exiting getUserRoleByReportId in DAOImpl");
		}
		return userList; 

	}
*/

	/**
	 * This method return the Hub code of the circle
	 * 
	 * @param circleCode
	 *           
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public String getHubCodeByCircleCode(String circleCode) throws DAOException {

		log.debug("Entering  in getHubCodeByCircleCode() method of class SMSReportDAO.");

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet=null;
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_HUB_CODE_BY_CIRCLE_CODE);
			prepareStatement.setString(1,circleCode);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()){
				return resultSet.getString("HUB_CODE");
			} else {

				throw new DAOException("No Hub found for Circle Code "+circleCode);
			}

		} catch (SQLException sqe) {
			log.debug("SQL EXCEPTION "+sqe.getErrorCode(),sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.debug("Exception ",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getHubCodeByCircleCode throws DAOException");

		}

	}


	/**
	 * This method return the Hub code of the circle
	 * 
	 * @param circleCode
	 *           
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	public boolean isCircleExist(String circleCode) throws DAOException {

		log.debug("Entering  in getHubCodeByCircleCode() method of class SMSReportDAO.");

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet=null;
		boolean isCircleExists =false;
		if(circleCode !=null && !"".equals(circleCode)){
			circleCode = circleCode.toUpperCase().trim();
		}
		try {
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_ACTIVE_CIRCLE);
			prepareStatement.setInt(1,Constants.Active);
			prepareStatement.setString(2,circleCode);
			resultSet = prepareStatement.executeQuery();
			if(resultSet.next()){
				isCircleExists=true;
			} 
		} catch (SQLException sqe) {
			log.debug("SQL EXCEPTION "+sqe.getErrorCode(),sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.debug("Exception ",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getHubCodeByCircleCode throws DAOException");

		}
		return isCircleExists;
	}
	
	public boolean isValidRegNo(String regNO,String userIdentifier)throws DAOException {
			log
			.debug("Entering  throws DAOException in SMSReportDAO ");
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;
			Connection connection = null;
            boolean isDistExist =false;
            String sqlQuery = null;
			try {
				
					
				if("distributor".equals(userIdentifier)){
					sqlQuery = new StringBuffer(SMSReportDAOInterface.RETRIEVE_REQUESTER_DETAILS)
					.append(" AND (LOWER(USER_TYPE)='distributor' OR LOWER(USER_TYPE)='fos') WITH UR ").toString();	
					
				} else {
					sqlQuery = new StringBuffer(SMSReportDAOInterface.RETRIEVE_REQUESTER_DETAILS)
					.append(" AND LOWER(USER_TYPE)='retailer' WITH UR ").toString();
				}
				connection = DBConnection.getDBConnection(SQLConstants.DISTPORTAL_JNDI_NAME);
				prepareStatement = connection
				.prepareStatement(sqlQuery);
				prepareStatement.setString(1, regNO);
				prepareStatement.setString(2, Constants.ActiveState);
				prepareStatement.setString(3, Constants.ActiveState);
				resultSet = prepareStatement.executeQuery();
				if(resultSet.next()) {
					isDistExist = true;
			    } 
				}catch (SQLException sqe) {
				log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
				throw new DAOException(sqe.getMessage(), sqe);

			} catch (Exception ex) {
				log.error("Exception" + ex.getMessage(), ex);
				throw new DAOException(ex.getMessage(), ex);

			} finally {
				try {
					DBConnectionUtil.closeDBResources(connection, prepareStatement,
							resultSet);
				} catch (Exception e) {
					log
					.debug("Exception occured when trying to close database resources");
				}
			}
			log.debug("Exiting getRequesterDetails in DistportalServiceDAOImpl");
			
		return isDistExist;
		
	}

	
/*	*//**
	 * This method return the service class id and circle circel code
	 * 
	 * @param rptDate
	 *           
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 *//*
	public ArrayList getSCIdCircleCode(String rptDate) throws DAOException {

		log.debug("Entering  in getHubCodeByCircleCode() method of class SMSReportDAO.");

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet=null;
		Date reportDate =null;
		SMSReportDTO smsReportDTO = null;
		ArrayList idCodeList = new ArrayList();
		try {
			reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
			connection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_SC_ID_CIRCLE_CODE);
			prepareStatement.setDate(1,reportDate);
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next()){
				smsReportDTO.setServiceClassId(resultSet.getString("SERVICECLASS_ID"));
				smsReportDTO.setCircleCode1(resultSet.getString("CIRCLE_CODE"));
				idCodeList.add(smsReportDTO);
			} 

		} catch (SQLException sqe) {
			log.debug("SQL EXCEPTION "+sqe.getErrorCode(),sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.debug("Exception ",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getHubCodeByCircleCode throws DAOException");

		}
		return idCodeList;

	}*/

	
	
	/**
	 * This method return the service class id and circle circel code
	 * 
	 * @param rptDate
	 *           
	 * @exception DAOException,when
	 *                there is some problem while locating an object in
	 *                database.
	 */
	/*
	public ArrayList getNewSCIdCircleCode() throws DAOException {

		log.debug("Entering  in getHubCodeByCircleCode() method of class SMSReportDAO.");

		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet=null;
		SMSReportDTO smsReportDTO = null;
		ArrayList idCodeList = new ArrayList();
		try {
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prepareStatement = connection.prepareStatement(SMSReportDAOInterface.GET_NEW_SC_ID_CIRCLE_CODE);
			resultSet = prepareStatement.executeQuery();
			while(resultSet.next()){
				smsReportDTO.setServiceClassId(resultSet.getString("SERVICECLASS_ID"));
				smsReportDTO.setCircleCode1(resultSet.getString("CIRCLE_CODE"));
				idCodeList.add(smsReportDTO);
			} 

		} catch (SQLException sqe) {
			log.debug("SQL EXCEPTION "+sqe.getErrorCode(),sqe);
			throw new DAOException(sqe.getMessage(), sqe);
		} catch (Exception ex) {
			log.debug("Exception ",ex);
			throw new DAOException(ex.getMessage(), ex);
		} finally {
			try {
				DBConnectionUtil.closeDBResources(connection, prepareStatement);
			} catch (Exception e) {
				log
				.debug(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
			log
			.debug("Exiting method getHubCodeByCircleCode throws DAOException");

		}
		return idCodeList;

	}
*/	
	/*
	public SMSReportDTO getFirstAndLstDateOfMonth(String strDate){
		SMSReportDTO smsReportDTO = new SMSReportDTO();
		try{
		HashMap hashMap	= Utility.getFtAndLtDateOfMonth(strDate);
		smsReportDTO.setFirstDateOfMonth(hashMap.get("firstDateOfMonth").toString());
		smsReportDTO.setLastDateOfMonth(hashMap.get("lastDateOfMonth").toString());
		smsReportDTO.setMonthOftheDate(hashMap.get("monthOftheDate").toString());
		
		} catch (Exception e) {
			log.error("Exception occured when getting the first and last date of the month ",e);
		}
		return smsReportDTO;
	}
	*/
	/*
	public SMSReportDTO getCurrentDate(){
		Date reportDate = null;
		SMSReportDTO smsReportDTO = new SMSReportDTO();
		try{
			Calendar cal=Calendar.getInstance();
			String rptDate = Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy");
			reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
			smsReportDTO.setCurrentDateAsString(rptDate);
			smsReportDTO.setSqlRptDate(reportDate);
		}catch (Exception e) {
			log.error("Exception occured when getting current date ",e);
		}
	   return smsReportDTO;
		
	}
	*/
	/*
	public String getReportDetails(String serviceClassId,String circleCode,String rptDate, int rptTypeFlag) throws DAOException{

		log.debug("Entering  in getActServiceCatWise() method of class SMSReportDAO.");	
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Date reportDate = null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String strMsisdn=null;
		try{

			if(circleCode != null && !"".equals(circleCode)){
				circleCode = circleCode.trim().toUpperCase();
			}
			
			/** getting service class for specific category id * 
			
			ArrayList msisdnList = getMSISDNByIdCode(serviceClassId,circleCode);
			
			log.info("msisdnList == "+msisdnList);
			
			if(msisdnList != null && !msisdnList.isEmpty()){
				String msisdnArray [] = (String []) msisdnList.toArray (new String [0]);
				strMsisdn =Utility.arrayToString(msisdnArray,null); 
			} 
			log.debug ("strServiceClass == "+strMsisdn);
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			StringBuffer completeQuery = new StringBuffer(SMSReportDAOInterface.SELECT_ACTV_COUNT_SERVICE_CLASS_WISE.replaceAll("<MSISDN>",strMsisdn));
			log.debug("completeQuery "+completeQuery.toString());
			if(rptDate == null || "".equals(rptDate)){
				
				smsReportDTO = getCurrentDate();
				strRptDate = smsReportDTO.getCurrentDateAsString();
				reportDate = smsReportDTO.getSqlRptDate();
				
				completeQuery.append(" AND DATE(TM.UPDATED_DT)= ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);

			} else if( rptTypeFlag == Constants.DAILY_REPORT){
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(" AND  DATE(TM.UPDATED_DT)= ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, reportDate);

			} else if( rptTypeFlag == Constants.MONTHLY_REPORT){
				
				strRptDate = rptDate;
				
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(" AND DATE(TM.UPDATED_DT) BETWEEN ? AND ? WITH UR");
				preparedStatement = connection.prepareStatement(completeQuery.toString());
				preparedStatement.setString(1, Constants.SUCCESS_REPORT);
				preparedStatement.setDate(2, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				preparedStatement.setDate(3, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				isMonthlyReport=true;

			}

			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
               // String serviceClassName = new ServiceClassDAOImpl().getServiceClassNameById(Integer.parseInt(serviceClassId));
				if(isMonthlyReport){
					smsMessage =  new StringBuffer(500)
					.append("Activation Count for Service class ")
					.append(serviceClassId).append(" and ").append(circleCode).append(" Circle is ")
					.append(resultSet.getString("ACTV_COUNT"))
					.append(" for the month of ").append(monthOftheDate).append(" as of the date ").append(strRptDate).toString();
					log.debug(smsMessage);
				} else {
					smsMessage =  new StringBuffer(500)
					.append("Activation Count for Service class ")
					.append(serviceClassId).append(" and ").append(circleCode).append(" Circle is ")
					.append(resultSet.getString("ACTV_COUNT")).append(" as of the date ").append(strRptDate).toString();
					log.info(smsMessage);
				}

			} else {
				log.debug(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/	
	
	/**
	 * This method check the requester permission
	 * @param businessUserDTO contais user info
	 *  @param reportId is the requested report 
	 * @return int value (1 (Daily)/2 (Monthly)/-1 (having No Permission))
	 */
	public String verifyNewRequestorPermission(BusinessUserDTO businessUserDTO,String reportCode) throws DAOException{

		log.debug("Entering in verifyNewRequestorPermission() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int userTypeId = businessUserDTO.getTypeOfUserId();
		String circleCode = businessUserDTO.getCircleCode();
		String smsKeyword = Constants.NO_PERM;
		try{

			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			if(userTypeId == Constants.ED){
				if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
					smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.HUB_TOTAL_SALES);
				}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
					smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.HUB_CIRCLE_TOTAL_SALES);
				}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
					smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_HUB_TOTAL_SALES);
				}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
					smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_HUB_CIRCLE_TOTAL_SALES);
				}
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.QUERY_SMS_REPORT_PERMISSION);
				preparedStatement.setInt(1, userTypeId);
				preparedStatement.setString(2, smsKeyword);
			}else {
				if(userTypeId == Constants.CEO){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.CEO_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.CEO_ZONE_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.CEO_ZBM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_ZONE_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_CEO_ZBM_TOTAL_SALES);
					}
				}else if(userTypeId == Constants.SALES_HEAD){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SH_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SH_ZONE_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SH_ZBM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_SH_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_SH_ZONE_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_SH_ZBM_TOTAL_SALES);
					}
				}else if(userTypeId == Constants.ZBM){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZBM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZBM_ZSM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZBM_TM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_ZSM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZBM_TM_TOTAL_SALES);
					}
				
				}else if(userTypeId == Constants.ZSM){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZSM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZSM_TM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.ZSM_DIST_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_TM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_ZSM_DIST_TOTAL_SALES);
					}
					
				}else if(userTypeId == Constants.TM){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.TM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.TM_DIST_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.TM_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_TM_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_TM_DIST_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_TM_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.PENDING)){
						smsKeyword =resourceBundle.getString(SMSReportConfigInitializer.TM_PENDING_REPORT);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SUB_STATUS)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.TM_SUBSCRIBER_STATUS);
					}
					
				}else if(userTypeId == Constants.DISTIBUTOR){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DIST_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DIST_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DIST_DLR_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword =resourceBundle.getString( SMSReportConfigInitializer.SC_DIST_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_DIST_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.THIRD_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_DIST_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.PENDING)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DIST_PENDING_REPORT);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SUB_STATUS)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DIST_SUBSCRIBER_STATUS);
					}
				
				}else if(userTypeId == Constants.FOS){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.FOS_DLR_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_FOS_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SECOND_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_FOS_DLR_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.PENDING)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.FOS_PENDING_REPORT);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SUB_STATUS)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.FOS_SUBSCRIBER_STATUS);
					}
				
				}else if(userTypeId == Constants.DEALER){
					if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_TT)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DLR_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.FIRST_SC)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.SC_DLR_TOTAL_SALES);
					}else if(reportCode.equalsIgnoreCase(SMSReportConfigInitializer.SUB_STATUS)){
						smsKeyword = resourceBundle.getString(SMSReportConfigInitializer.DLR_SUBSCRIBER_STATUS);
					}
				
				}
				StringBuffer query =new StringBuffer(300).append(SMSReportDAOInterface.QUERY_SMS_REPORT_PERMISSION).append(" AND SG.CIRCLE_CODE=? WITH UR ");
				preparedStatement = connection.prepareStatement(query.toString());
				preparedStatement.setInt(1, userTypeId);
				preparedStatement.setString(2, smsKeyword);
				preparedStatement.setString(3, circleCode);
			}
			
			resultSet = preparedStatement.executeQuery();

			if(resultSet.next()){
				return smsKeyword;

			} 

		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException("Unauthorized Access", sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		log.debug("Exiting in verifyRequestorPermission() method of class SMSReportDAO.");	
		return smsKeyword;
	}
	
	/*
	public String getZoneWiseActCountZBM(int locId,String baseLoc,String rptDate,String circleCode) throws DAOException {

		log.debug("Entering  in getZoneWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection ftaConnection=null;
		Connection prodConnection=null;
		PreparedStatement ftaPreparedStatement = null;
		PreparedStatement prodPreparedStatement = null;
		ResultSet ftaResultSet = null;
		ResultSet prodResultSet = null;
		Date reportDate =null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";
		try{
			ftaConnection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prodConnection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prodPreparedStatement = prodConnection
				.prepareStatement(CircleMasterInterface.RETRIEVE_LOC_DETAILS);
			prodPreparedStatement.setInt(1, locId);
			prodPreparedStatement.setString(2, baseLoc);
			prodPreparedStatement.setInt(3, Constants.Active);
			prodResultSet = prodPreparedStatement.executeQuery();
			if(prodResultSet.next()){
				String zoneName = prodResultSet.getString("LOCATION_NAME");
			
			if(rptDate == null || "".equals(rptDate)){
					//FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();

					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, reportDate);
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						ftdCount = ftaResultSet.getString("ACTV_COUNT");
					}
					
					//MTD count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						mtdCount = ftaResultSet.getString("ACTV_COUNT");
					}
			} else{
				//FTD count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, reportDate);
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}

				//MTD count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					mtdCount = ftaResultSet.getString("ACTV_COUNT");
				}
			}
			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for Zone ")
				.append(zoneName).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
			}else{
				smsMessage ="Zone does not exist or may be inactive.";
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(ftaConnection,ftaPreparedStatement,ftaResultSet);
				DBConnectionUtil.closeDBResources(prodConnection,prodPreparedStatement,prodResultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/	
	/*
	public String getZoneWiseActCountZSM(int locId,String baseLoc,String rptDate,String circleCode) throws DAOException {

		log.debug("Entering  in getZoneWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection ftaConnection=null;
		Connection prodConnection=null;
		PreparedStatement ftaPreparedStatement = null;
		PreparedStatement prodPreparedStatement = null;
		ResultSet ftaResultSet = null;
		ResultSet prodResultSet = null;
		Date reportDate =null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";
		try{
			ftaConnection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prodConnection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prodPreparedStatement = prodConnection
				.prepareStatement(CircleMasterInterface.RETRIEVE_LOC_DETAILS);
			prodPreparedStatement.setInt(1, locId);
			prodPreparedStatement.setString(2, baseLoc);
			prodPreparedStatement.setInt(3, Constants.Active);
			prodResultSet = prodPreparedStatement.executeQuery();
			if(prodResultSet.next()){
				String zoneName = prodResultSet.getString("LOCATION_NAME");
			
			if(rptDate == null || "".equals(rptDate)){
					//FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();

					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, reportDate);
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						ftdCount = ftaResultSet.getString("ACTV_COUNT");
					}
					
					//MTD count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						mtdCount = ftaResultSet.getString("ACTV_COUNT");
					}
			} else{
				//FTD count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, reportDate);
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}

				//MTD count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_ZONE_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					mtdCount = ftaResultSet.getString("ACTV_COUNT");
				}
			}
			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for Zone ")
				.append(zoneName).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
			}else{
				smsMessage ="Zone does not exist or may be inactive.";
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(ftaConnection,ftaPreparedStatement,ftaResultSet);
				DBConnectionUtil.closeDBResources(prodConnection,prodPreparedStatement,prodResultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/	
	/*
	public String getCityWiseTMActCount(int locId,String baseLoc,String rptDate, String circleCode) throws DAOException {

		log.debug("Entering  in getCityWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection ftaConnection=null;
		Connection prodConnection=null;
		PreparedStatement ftaPreparedStatement = null;
		PreparedStatement prodPreparedStatement = null;
		ResultSet ftaResultSet = null;
		ResultSet prodResultSet = null;
		Date reportDate =null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";
		try{
			ftaConnection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prodConnection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prodPreparedStatement = prodConnection
				.prepareStatement(CircleMasterInterface.RETRIEVE_LOC_DETAILS);
			prodPreparedStatement.setInt(1, locId);
			prodPreparedStatement.setString(2, baseLoc);
			prodPreparedStatement.setInt(3, Constants.Active);
			prodResultSet = prodPreparedStatement.executeQuery();
			if(prodResultSet.next()){
				String cityName = prodResultSet.getString("LOCATION_NAME");
			
			if(rptDate == null || "".equals(rptDate)){
					// FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();

					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, reportDate);
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						ftdCount = ftaResultSet.getString("ACTV_COUNT");
					}

					//MTD Count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						mtdCount = ftaResultSet.getString("ACTV_COUNT");
					}

				}else{
					//FTD Count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, reportDate);
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}

				//MTD Count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}
			}

			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for City ")
				.append(cityName).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
			}else{
				smsMessage ="City does not exist or may be inactive.";
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(ftaConnection,ftaPreparedStatement,ftaResultSet);
				DBConnectionUtil.closeDBResources(prodConnection,prodPreparedStatement,prodResultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/	
	/*
	public String getCityWiseFOSActCount(int locId,String baseLoc,String rptDate, String circleCode) throws DAOException {

		log.debug("Entering  in getCityWiseActCount() method of class SMSReportDAO.");
		StringBuffer completeQuery= new StringBuffer();
		Connection ftaConnection=null;
		Connection prodConnection=null;
		PreparedStatement ftaPreparedStatement = null;
		PreparedStatement prodPreparedStatement = null;
		ResultSet ftaResultSet = null;
		ResultSet prodResultSet = null;
		Date reportDate =null;
		boolean isMonthlyReport=false;
		String monthOftheDate="";
		String smsMessage="";
		SMSReportDTO smsReportDTO = null;
		String strRptDate = "";
		String ftdCount="";
		String mtdCount="";
		try{
			ftaConnection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			prodConnection = DBConnection.getDBConnection(SQLConstants.PRODCAT_JNDI_NAME);
			prodPreparedStatement = prodConnection
				.prepareStatement(CircleMasterInterface.RETRIEVE_LOC_DETAILS);
			prodPreparedStatement.setInt(1, locId);
			prodPreparedStatement.setString(2, baseLoc);
			prodPreparedStatement.setInt(3, Constants.Active);
			prodResultSet = prodPreparedStatement.executeQuery();
			if(prodResultSet.next()){
				String cityName = prodResultSet.getString("LOCATION_NAME");
			
			if(rptDate == null || "".equals(rptDate)){
					// FTD count
					smsReportDTO = getCurrentDate();
					strRptDate = smsReportDTO.getCurrentDateAsString();
					reportDate = smsReportDTO.getSqlRptDate();

					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, reportDate);
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						ftdCount = ftaResultSet.getString("ACTV_COUNT");
					}

					//MTD Count
					smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
					String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
					String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
					monthOftheDate = smsReportDTO.getMonthOftheDate();
					completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
					ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
					ftaPreparedStatement.setInt(1, locId);
					ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
					ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
					ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
					ftaResultSet = ftaPreparedStatement.executeQuery();
					if(ftaResultSet.next()){
						mtdCount = ftaResultSet.getString("ACTV_COUNT");
					}

				}else{
					//FTD Count
				strRptDate = rptDate;
				reportDate = new Date(Utility.getDate(rptDate, "dd/MM/yyyy"));
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT)=? WITH UR");
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, reportDate);
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}

				//MTD Count
				smsReportDTO= getFirstAndLstDateOfMonth(rptDate);
				String firstDateOfMonth = smsReportDTO.getFirstDateOfMonth();
				String lastDateOfMonth = smsReportDTO.getLastDateOfMonth();
				monthOftheDate = smsReportDTO.getMonthOftheDate();
				completeQuery.append(SMSReportDAOInterface.SELECT_ACTV_COUNT_CITY_WISE).append(" AND DATE(UPDATED_DT) BETWEEN ? AND ? WITH UR"); 
				ftaPreparedStatement = ftaConnection.prepareStatement(completeQuery.toString());
				ftaPreparedStatement.setInt(1, locId);
				ftaPreparedStatement.setString(2, Constants.SUCCESS_REPORT);
				ftaPreparedStatement.setDate(3, new Date(Utility.getDate(firstDateOfMonth, "dd/MM/yyyy")));
				ftaPreparedStatement.setDate(4, new Date(Utility.getDate(lastDateOfMonth, "dd/MM/yyyy")));
				ftaResultSet = ftaPreparedStatement.executeQuery();
				if(ftaResultSet.next()){
					ftdCount = ftaResultSet.getString("ACTV_COUNT");
				}
			}

			if(!mtdCount.equalsIgnoreCase("") || !ftdCount.equalsIgnoreCase("")){
				smsMessage =  new StringBuffer(500)
				.append("Total Sales for City ")
				.append(cityName).append(" FTD/MTD is: ")
				.append(ftdCount)
				.append("/")
				.append(mtdCount).toString();
				log.debug(smsMessage);
			} else {
				log.error(Constants.SMS_COMMON_MESAGE);
				return Constants.SMS_COMMON_MESAGE;
			}
			}else{
				smsMessage ="City does not exist or may be inactive.";
			}
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(ftaConnection,ftaPreparedStatement,ftaResultSet);
				DBConnectionUtil.closeDBResources(prodConnection,prodPreparedStatement,prodResultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
*/	
	
	/******************************************* 
	 *  
	 *  Start of pending reports 
	 * 
	 * ****************************************/
	
	
	// TM getting pending report 
	
	public String getTMPendingReports(int userId, int locId )throws DAOException{

		log.debug("Entering  in getTMPendingReports() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_PENDING_REPORT_COUNT); 
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, locId);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalPendingCount=0;
				while(resultSet.next()){
					int pendingCountForDist = Integer.parseInt(resultSet.getString("PENDING_COUNT"));
					smsMessage1.append("Pending report  on ").append(getCurrentDateTime()+" of ");
					smsMessage1.append(resultSet.getString("DIST_NAME"))
					.append(","+resultSet.getString("DIST_REG_NO"))
					.append("="+pendingCountForDist).append(",");
					totalPendingCount+=pendingCountForDist;
					
				} 
				if(smsMessage1.length() != 0){
					smsMessage1.append("Total : ").append(totalPendingCount);
				
				} else {
					smsMessage1.append("Pending report count = ").append(totalPendingCount);
				}
				smsMessage = smsMessage1.toString();
				log.debug("Pending report for TM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	

	/*public String getDistributorPendingCount(int userId, int locId )throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_PENDING_REPORT_COUNT); 
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, locId);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalPendingCount=0;
				
				while(resultSet.next()){
					int pendingCountForDist = Integer.parseInt(resultSet.getString("PENDING_COUNT"));
					
				smsMessage1.append("Pending report  of ");
				smsMessage1.append(resultSet.getString("DIST_NAME"))
				.append(","+resultSet.getString("DIST_REG_NO"))
				.append("on "+getCurrentDateTime()+"="+pendingCountForDist);
				totalPendingCount+=pendingCountForDist;
				
				
			   }
				if(smsMessage1.length() == 0){
					smsMessage1.append("There is no Pending report for the date ").append(getCurrentDateTime());
				} else {
					smsMessage1.append("Total : ").append(totalPendingCount);
					String strMsg = smsMessage1.toString();
					smsMessage1.toString().replace(strMsg.charAt(strMsg.lastIndexOf(strMsg)),'\t');
				}
				smsMessage = smsMessage1.toString();
				log.debug("Pending report for Distributor : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	*/
	
	
	/*public String getFOSPendingCount(int userId, int locId )throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_PENDING_DETAIL_REPORT_COUNT); 
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, locId);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalPendingCount=0;
				
				while(resultSet.next()){
					int pendingCountForDist = Integer.parseInt(resultSet.getString("PENDING_COUNT"));
					
				smsMessage1.append("Pending report of");
				smsMessage1.append(resultSet.getString("FOS_NAME"))
				.append(","+resultSet.getString("FOS_REG_NO"))
				.append("on ").append(getCurrentDateTime()).append("="+pendingCountForDist);
				totalPendingCount+=pendingCountForDist;
			   } 
				smsMessage = smsMessage1.toString();
				log.debug("Pending report for FOS : "+smsMessage);if(smsMessage1.length() == 0){
					smsMessage1.append("There is no Pending report for the date ").append(getCurrentDateTime());
				} else {
					smsMessage1.append("Total : ").append(totalPendingCount);
					String strMsg = smsMessage1.toString();
					smsMessage1.toString().replace(strMsg.charAt(strMsg.lastIndexOf(strMsg)),'\t');
				}
				smsMessage = smsMessage1.toString();
				log.debug("Pending report for Distributor : "+smsMessage);
			
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	*/
	
	private String getCurrentDateTime() {
		return DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
	}

	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getDistPendingReports(int userId, int locId )throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_PENDING_DETAIL_REPORT_COUNT); 
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, locId);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
				int totalPendingCount=0;
				
				while(resultSet.next()){
				int pendingCountForDist = Integer.parseInt(resultSet.getString("PENDING_COUNT"));
				smsMessage1.append("Pending report of");
				smsMessage1.append(resultSet.getString("FOS_NAME"))
				.append(","+resultSet.getString("FOS_REG_NO"))
				.append("on ").append(getCurrentDateTime()).append("="+pendingCountForDist);
				totalPendingCount+=pendingCountForDist;
			   } 
				
				if(smsMessage1.length() != 0){
					smsMessage1.append("Total : ").append(totalPendingCount);
				
				} else {
					smsMessage1.append("Pending report count = ").append(totalPendingCount);
				}
				smsMessage =  smsMessage1.toString();
				log.debug("Pending report for Distributor : "+smsMessage);
			
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getFOSPendingReports(int userId, int locId )throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_PENDING_DETAIL_REPORT_COUNT); 
				preparedStatement.setInt(1, userId);
				preparedStatement.setInt(2, locId);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalPendingCount=0;
				
				while(resultSet.next()){
					int pendingCountForDist = Integer.parseInt(resultSet.getString("PENDING_COUNT"));
					
				smsMessage1.append("Pending report of ");
				smsMessage1.append(resultSet.getString("FOS_NAME"))
				.append(","+resultSet.getString("FOS_REG_NO"))
				.append("on ").append(getCurrentDateTime()).append("="+pendingCountForDist);
				totalPendingCount+=pendingCountForDist;
			   } 
				
				if(smsMessage1.length() != 0){
					smsMessage1.append("Total : ").append(totalPendingCount);
				} else {
					smsMessage1.append("Pending report count = ").append(totalPendingCount);
				}
				smsMessage =  smsMessage1.toString();
				log.debug("Pending report for FOS : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}

	
	private String removeCommaAtLastIndex(String smsMessage) {
		return smsMessage.replace(smsMessage.charAt(smsMessage.lastIndexOf(smsMessage)),'\t');
	}
	
	/********************************************* 
	 * End of pending reports
	 *  ******************************************/
	
	
	
	/******************************************* 
	 *  
	 *  Method which gets subscriber current status
	 * 
	 * ****************************************/
   
	public String getSuscriberStatus(String subMSISDN,String circleCode)throws DAOException{

		log.debug("Entering  in getActStatusForSubs() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		try{

			connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
			preparedStatement = connection.prepareStatement(SMSReportDAOInterface.GET_SUBSCRIBER_CURRENT_STATUS); 
			log.debug("Query "+SMSReportDAOInterface.GET_SUBSCRIBER_CURRENT_STATUS);
			preparedStatement.setString(1, subMSISDN);
			preparedStatement.setString(2, circleCode);
			resultSet = preparedStatement.executeQuery();
			StringBuffer smsMessage1 =  new StringBuffer(500);
			
			//Pre-Message
			smsMessage1.append("Subscriber ").append(subMSISDN).append(" as on ");
			smsMessage1.append(getCurrentDateTime()).append(" : ");
			
			//Report Details
			if(resultSet.next()){
				
				if(null != resultSet.getString("REGISTRATION_COMPLETED_DATE")){
					smsMessage1.append("UN Ret No=").append(resultSet.getString("DEALER_REG_NO"))
					.append(",Time : ")
					.append(resultSet.getString("REGISTRATION_COMPLETED_DATE"));
				}
				if(null != resultSet.getString("VERIFICATION_COMPLETED_DATE")){
					smsMessage1.append(", AP FOS NO ").append(resultSet.getString("FOS_REG_NO"))
					.append(", Time : ")
					.append(resultSet.getString("VERIFICATION_COMPLETED_DATE"));
				}
				if(null != resultSet.getString("ACTIVATION_COMPLETED_DATE")){
					smsMessage1.append(", AC Dist NO ").append(resultSet.getString("DIST_REG_NO"))
					.append(", Time : ")
					.append(resultSet.getString("ACTIVATION_COMPLETED_DATE"));
				}	

			} else {
				smsMessage1.append("No Transaction initiated ").toString();
			}
			smsMessage = smsMessage1.toString();
			log.debug("Subscriber Status for TM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/******************************************* 
	 *  
	 *  Start of Activation repors 
	 * 
	 * ****************************************/
	
	 
    // ED getting total sale fo hub
	
	public String getTotalSaleForHub(String hubCode)throws DAOException{

		log.debug("Entering  in getTotalSaleForHub() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ED_GET_TOTAL_SALE_FOR_HUB); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, hubCode);
				preparedStatement.setDate(3, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(4, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(hubCode).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ED for Hub : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getTotalSaleForCircle(String circleCode)throws DAOException{

		log.debug("Entering  in getTotalSaleForCircle() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_TOTAL_SALE_FOR_CIRCLE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setDate(3, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(4, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(circleCode).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				}else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for CEO/SHO : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String zbmGetTotalSaleForZone(int userId,int locId)throws DAOException{

		log.debug("Entering  in zbmGetTotalSaleForZone() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_TOTAL_SALE_FOR_ZONE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("ZBM_NAME")).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" ZBM's count only.");
				}else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZBM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String zsmGetTotalSaleForZone(int userId,int locId)throws DAOException{

		log.debug("Entering  in zsmGetTotalSaleForZone() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_TOTAL_SALE_FOR_ZONE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("ZSM_NAME")).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" ZSM's count only.");
				}else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZSm : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String tmGetTotalSaleForCity(int userId,int locId)throws DAOException{

		log.debug("Entering  in tmGetTotalSaleForCity() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_TOTAL_SALE_FOR_CITY); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("TM_NAME")).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" TM's count only.");
				} else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for TM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String distGetTotalSaleForCity(int userId,int locId)throws DAOException{

		log.debug("Entering  in distGetTotalSaleForCity() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_TOTAL_SALE_FOR_CITY); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("DIST_NAME")).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" Distributor's count only.");
				}else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String fosGetTotalSaleForCity(int userId,int locId)throws DAOException{

		log.debug("Entering  in fosGetTotalSaleForCity() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_TOTAL_SALE_FOR_CITY); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("FOS_NAME")).append("FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" FOS's count only.");
				} else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for FOS : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	

	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String retailerGetTotalSaleForCity(int userId,int locId)throws DAOException{

		log.debug("Entering  in retailerGetTotalSaleForCity() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DEALER_GET_TOTAL_SALE_FOR_CITY); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("DEALER_NAME")).append(" FTD/MTD:-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(" Retailer's count only.");
				} else {
					smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for FOS : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getCircleWiseTotalSale(String hubCode)throws DAOException{

		log.debug("Entering  in edGetCircleWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ED_GET_CIRCLE_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, hubCode);
				preparedStatement.setDate(3, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(4, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// pre message
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("CIRCLE_CODE")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ED : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getZoneWiseTotalSale(String circleCode)throws DAOException{

		log.debug("Entering  in edGetCircleWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_ZONE_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setDate(3, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(4, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("ZONE_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append("Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			}else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for CEO and SHO : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getZSMWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getZSMWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_ZSM_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("ZSM_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append("Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			}else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZBM  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getTMWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getTMWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_TM_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("TM_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZSM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getDistWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getTMWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_DISTRIBUTOR_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("DIST_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for TM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getFOSWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getFOSWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DISTRIBUTOR_GET_FOS_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("FOS_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	

	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getDealerWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_DEALERS_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("DEALER_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for FOS : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String getZBMWiseTotalSale(String circleCode)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_ZBM_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setDate(3, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(4, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("ZBM_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("CEO and SHO geting ZBM wise report : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String zbmGetTMWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_TM_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("TM_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZBM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String zsmGetDistWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_DISTRIBUTOR_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("DIST_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for ZBM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	

	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String tmGetFOSWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_FOS_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("FOS_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for TM : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * Returns no of activation done by requester(Distributor) registered number
	 * @param registeredNo requester registered number
	 * @param rptDate requested date
	 * @param subscriberMSISDN subscriber mobile number
	 * @throws DAOException is thrown in case of any exception.
	 */
	public String distGetDealerWiseTotalSale(int userId,int locId)throws DAOException{

		log.debug("Entering  in getDealerWiseTotalSale() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_DEALER_WISE_TOTAL_SALE); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				int totalFTDCount = 0;
				int totalMTDCount = 0;
				// Pre Message 
				smsMessage1.append("Sale ").append("FTD/MTD:-");
				
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("DEALER_NAME")+":")
				.append(ftdCount)
				.append("/").append(mtdCount).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}
			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale ").append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	

		/***
		 *  Start of Service class wise reports 
		 *  
		 * ***********************************************/
	
	
	public String edGetTotalSaleHubForSC(String hubCode,String serviecClassId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ED_GET_TOTAL_SALE_FOR_HUB_FOR_SC_ID ); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, hubCode);
				preparedStatement.setString(3, serviecClassId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Sale ").append(resultSet.getString("HUB_NAME"));
				smsMessage1.append("for SC ID "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				}else {
					smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append("FTD/MTD:-0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * 
	 * @param hubCode
	 * @param serviecClassId
	 * @return
	 * @throws DAOException
	 */
	public String edGetCircleWiseTotalSaleForSC(String hubCode,String serviecClassId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ED_GET_CIRCLE_WISE_TOTAL_SALES_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, hubCode);
				preparedStatement.setString(3, serviecClassId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append(" FTD/MTD:-");
				
			int totalFTDCount =0;
			int totalMTDCount =0;
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("CIRCLE_CODE")+":")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}

			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append("FTD/MTD:-0/0");
			}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	/**
	 * 
	 * @param circleCode
	 * @param serviecClassId
	 * @return
	 * @throws DAOException
	 */
	public String ceoAndShoGetTotalSaleForSC(String circleCode,String serviecClassId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_TOTAL_SALE_FOR_CIRCLE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setString(3, serviecClassId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
//				 Pre Message 
				
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale for ").append("SC ID "+serviecClassId).append(" FTD/MTD:-");
				smsMessage1.append("for SC ID "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				} else {
					smsMessage1.append("Total Sale ");
					smsMessage1.append("for SC ID "+serviecClassId+" ")
					.append("FTD/MTD :- ").append("0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * 
	 * @param circleCode
	 * @param serviecClassId
	 * @return
	 * @throws DAOException
	 */
	public String ceoAndShoGetTotalSaleForZoneForSC(String circleCode,String serviecClassId)throws DAOException{

		log.debug("Entering  in ceoAndShoGetZoneWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_TOTAL_SALE_FOR_ZONE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setString(3, serviecClassId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
				
				// Pre Message 
			smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append(" FTD/MTD:-");
				
			int totalFTDCount =0;
			int totalMTDCount =0;
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("ZONE_NAME")+":")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}

			if(smsMessage1.length() != 0){
				smsMessage1.append("Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			}else {
				smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append("FTD/MTD:-0/0");
			}


			smsMessage = smsMessage1.toString();
			log.debug(" Zone wise (SC report ) for (CEO/SHO) : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	/**
	 * 
	 * @param circleCode
	 * @param serviecClassId
	 * @return
	 * @throws DAOException
	 */
	public String ceoAndShoGetZBMWiseTotalSaleForSC(String circleCode,String serviecClassId)throws DAOException{

		log.debug("Entering  in ceoAndShoGetZoneWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.CEO_AND_SHO_GET_ZBM_WISE_SC_ACT_COUNT); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setString(2, circleCode);
				preparedStatement.setString(3, serviecClassId);
				preparedStatement.setDate(4, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(5, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
				
				// Pre Message 
			smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append(" FTD/MTD:-");
				
			int totalFTDCount =0;
			int totalMTDCount =0;
			while(resultSet.next()){
				int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
				int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
				smsMessage1.append(resultSet.getString("ZBM_NAME")+":")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				totalFTDCount+=ftdCount;
				totalMTDCount+=mtdCount;
				}

			if(smsMessage1.length() != 0){
				smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
			} else {
				smsMessage1.append("Sale for ").append("SC ID "+serviecClassId).append("FTD/MTD:-0/0");
			}


			smsMessage = smsMessage1.toString();
			log.debug(" Zone wise (SC report ) for (CEO/SHO) : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	//   ZBM- Zone total sale 
	
	public String zbmGetTotalSaleForZoneForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_TOTAL_SALE_FOR_ZONE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale ").append(resultSet.getString("ZBM_NAME"));
				smsMessage1.append("for SC ID "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("total Sale ");
					smsMessage1.append("for SC ID "+scId+" ")
					.append("FTD/MTD :- ").append("0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	// ZBM-ZSM SC report
	
	public String zbmGetZSMWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_ZSM_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("ZSM_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
// ZBM-ZSM SC report
	
	public String zbmGetTMWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZBM_GET_TM_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("TM_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Activation report for Distributor  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	//   ZSM- Zone total sale 
	
	public String zsmGetTotalSaleForZoneForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_TOTAL_SALE_FOR_ZONE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Sale ");
				smsMessage1.append("for SC Id "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("ZSM- Zone total Sale SC  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	// ZSM -TM SC report
	
	public String zsmGetTMWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in edGetTotalSaleForServiceClass() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_TM_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("TM_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("ZSM -TM SC report  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
// ZSM -Distributor SC report
	
	public String zsmGetDistWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in zsmGetDistWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.ZSM_GET_DISTRIBUTOR_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("DIST_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("ZSM -Distributor SC report  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
	//   TM- City total sale 
	
	public String tmGetTotalSaleForCityForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in tmGetTotalSaleForCityForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_TOTAL_SALE_FOR_CITY_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale ");
				smsMessage1.append("for SC Id "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
				} else {
					smsMessage1.append("Total Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("TM- City total sale SC  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	// TM -Distributor SC report
	
	public String tmGetDistWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in tmGetDistWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_DISTRIBUTOR_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("DIST_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("TM -Distributor SC report  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	// TM -FOS SC report
	
	public String tmGetFOSWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in tmGetFOSWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.TM_GET_FOS_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("FOS_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug(" TM-FOS SC report  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	//   Distriburor - City total sale 
	
	public String distGetTotalSaleForCityForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in distGetTotalSaleForCityForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_TOTAL_SALE_FOR_CITY_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale ");
				smsMessage1.append("for SC Id "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("Total Sale ");
					smsMessage1.append("for SC ID "+scId+" ")
					.append("FTD/MTD :- ").append("0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Service class report for Distributor-City  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	// TM -FOS SC report
	
	public String distGetFOSWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in zsmGetDistWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_FOS_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("FOS_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}
				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Service class report for TM_FOS : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
   // Dist - Dealer SC report
	
	public String distGetDealerWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in distGetDealerWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DIST_GET_DEALER_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC ID "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("DEALER_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Service class report for : DIST-Dealer "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	//   FOS - City total sale 
	
	public String fosGetTotalSaleForCityForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in distGetTotalSaleForCityForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_TOTAL_SALE_FOR_CITY_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale ");
				smsMessage1.append("for SC Id "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("Total Sale ");
					smsMessage1.append("for SC ID "+scId+" ")
					.append("FTD/MTD :- ").append("0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Service class report fo FOS total sale r"+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
 // Distributor - Dealer SC report
	
	public String fosGetDealerWiseTotalSaleForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in zsmGetDistWiseTotalSaleForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.FOS_GET_DEALER_WISE_TOTAL_SALE_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
				// Pre Message 
				smsMessage1.append("Sale for ").append("SC Id "+scId).append(" FTD/MTD:-");
					
				int totalFTDCount =0;
				int totalMTDCount =0;
				while(resultSet.next()){
					int ftdCount =Integer.parseInt(resultSet.getString("FTD_ACTV_COUNT"));
					int mtdCount =Integer.parseInt(resultSet.getString("MTD_ACTV_COUNT"));
					smsMessage1.append(resultSet.getString("DEALER_NAME")+":")
					.append(resultSet.getString("FTD_ACTV_COUNT"))
					.append("/").append(resultSet.getString("MTD_ACTV_COUNT")).append(",");
					totalFTDCount+=ftdCount;
					totalMTDCount+=mtdCount;
					}

				if(smsMessage1.length() != 0){
					smsMessage1.append(" Total:").append(totalFTDCount).append("/").append(totalMTDCount);
				} else {
					smsMessage1.append("Sale for ").append("SC ID "+scId).append("FTD/MTD:-0/0");
				}
			smsMessage = smsMessage1.toString();
			log.debug("Service class report for DIST-DEALER  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
		//   Dealer - City total sale 
	
	public String dealerGetTotalSaleForCityForSC(int userId,int locId,String scId)throws DAOException{

		log.debug("Entering  in distGetTotalSaleForCityForSC() method of class SMSReportDAO.");
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String smsMessage="";
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		java.sql.Date todaysDateOfmonth = new Date(Utility.getDate(Utility.getDateAsString(new java.util.Date(), "dd/MM/yyyy"), "dd/MM/yyyy"));
		try{
				connection = DBConnection.getDBConnection(SQLConstants.FTA_JNDI_NAME);
				preparedStatement = connection.prepareStatement(SMSReportDAOInterface.DEALER_GET_TOTAL_SALE_FOR_CITY_FOR_SC_ID); 
				preparedStatement.setDate(1, todaysDateOfmonth);
				preparedStatement.setInt(2, userId);
				preparedStatement.setInt(3, locId);
				preparedStatement.setString(4, scId);
				preparedStatement.setDate(5, new Date(Utility.getDate(Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy")));
				preparedStatement.setDate(6, todaysDateOfmonth);
			
				resultSet = preparedStatement.executeQuery();
				
				StringBuffer smsMessage1 =  new StringBuffer(500);
					
			if(resultSet.next()){
				smsMessage1.append("Total Sale ");
				smsMessage1.append("for SC Id "+resultSet.getString("SERVICECLASS_ID")+" ")
				.append("FTD/MTD :-")
				.append(resultSet.getString("FTD_ACTV_COUNT"))
				.append("/").append(resultSet.getString("MTD_ACTV_COUNT"));
				} else {
					smsMessage1.append("Total Sale ");
					smsMessage1.append("for SC ID "+scId+" ")
					.append("FTD/MTD :- ").append("0/0");
				}

			smsMessage = smsMessage1.toString();
			log.debug("Service class report for dealer  : "+smsMessage);
			
		} catch (SQLException sqe) {
			log.error("SQL EXCEPTION" + sqe.getMessage(), sqe);
			throw new DAOException(Constants.SMS_COMMON_MESAGE, sqe);
		}catch (Exception ex) {
			log.error("Exception" + ex.getMessage(), ex);
			throw new DAOException(ex.getMessage(), ex);

		}
		finally
		{
			try {
				DBConnectionUtil.closeDBResources(connection,preparedStatement,resultSet);
			} catch (SQLException e) {
				new DAOException(Constants.SQL_EXC_IN_CONN_CLOSING);
			}
		}
		return smsMessage;
	}
	
	
}

