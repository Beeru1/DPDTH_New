package com.ibm.reports.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.reports.dao.GenericReportsDao;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class GenericReportsProcDaoImpl extends GenericReportsDaoImpl
{

	
	private Logger logger = Logger.getLogger(GenericReportsProcDaoImpl.class.getName());

	private static GenericReportsDao genericReportsProcDao = new GenericReportsProcDaoImpl();

	private GenericReportsProcDaoImpl()
	{
	}

	public static GenericReportsDao getInstance()
	{
		return genericReportsProcDao;
	}

	public GenericReportsOutputDto exportToExcel(GenericReportPararmeterDTO genericDTO)
			throws DAOException
	{

		GenericReportsOutputDto genericOutputDto = new GenericReportsOutputDto();
		CallableStatement cstmt = null;
		Connection conRDB = null;
		String dbSchema = "";
		String procName = "";
		ResultSet rs = null;

		try
		{

			// logger.info("asasasasas::::::::::::::::::::::::::::::::");
			// String zone = genericDTO.getZONE();

			// logger.info("genericDTO:::::::RecoPeriod:::"+genericDTO.
			// getRecoPeriod());
			// logger.info("genericDTO::::::::::"+genericDTO);
			logger.info("genericDTO.getAccountLevel()::::" + genericDTO.getAccountLevel());
			logger.info("genericDTO.getOtherUserData()::::::" + genericDTO.getOtherUserData());

			int reportId = genericDTO.getReportId();
			int groupId = genericDTO.getGroupId();

			// if this request is for stock eligibility
/*			if(reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT)
			{
				logger.info("Main DB report : STOCK ELIGIBILITY");
				// get connection from Main DB
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(reportId);
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,? ) }";
				conRDB = DBConnectionManager.getDBConnection();	
				
			}
			*/
			if (reportId == ReportConstants.STOCK_ELIGIBILITY_REPORT)
			{
				logger.info("Main DB report : STOCK ELIGIBILITY");
				// get connection from Main DB
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(reportId);
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }";
				conRDB = DBConnectionManager.getDBConnection();
			}
			// condition for getting connection from Main DB. If Distributor is
			// login and Dist_Data column is 0
			else if (genericDTO.getReportId() == ReportConstants.SERIALIZED_STOCK_AS_ONDATE_REPORT)
			{

				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				// Date asOnDate = sdf.parse(genericDTO.getFromDate());
				Date currentDate = new Date();
				String currentDateStr = sdf.format(currentDate);
				if (currentDateStr.equals(genericDTO.getFromDate()))
				{
					logger.info("Main DB Report this is........");
					conRDB = DBConnectionManager.getDBConnection();
					dbSchema = ResourceReader
							.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
					ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
					procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? , ? ) }";

				}
				else
				{
					logger.info("Report DB Report this is........");
					// get connection from Report DB
					dbSchema = ResourceReader
							.getCoreResourceBundleValue(Constants.DP_REPORT_DB_SCHEMA);
					ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
					procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
							+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ? ) }";
					conRDB = DBConnectionManager.getMISReportDBConnection();

				}

			}
			else if (!genericDTO.getAccountLevel().equals(String.valueOf(Constants.ACC_LEVEL_DIST))
					&& genericDTO.getOtherUserData().equals("0"))
			{
				// get connection from Main DB and null the old connection
				conRDB = DBConnectionManager.getDBConnection();
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ? ) }";

			}
			else if (genericDTO.getAccountLevel().equals(String.valueOf(Constants.ACC_LEVEL_DIST))
					&& genericDTO.getDistData().equals("0") && reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT)
			{
				// get connection from Main DB and null the old connection
				conRDB = DBConnectionManager.getDBConnection();
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }";

			}
			/*else if (genericDTO.getAccountLevel().equals(String.valueOf(Constants.ACC_LEVEL_DIST))
					&& genericDTO.getDistData().equals("0") && reportId != ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT)
			{
				// get connection from Main DB and null the old connection
				conRDB = DBConnectionManager.getDBConnection();
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }";

			}
*/
			else if(reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT  )
			{
				// get connection from Report DB
			//	dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_REPORT_DB_SCHEMA);
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_MAIN_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) }";
			//	conRDB = DBConnectionManager.getMISReportDBConnection();
				conRDB = DBConnectionManager.getDBConnection();
				
				
			}
			else
			{
				// get connection from Report DB
				dbSchema = ResourceReader.getCoreResourceBundleValue(Constants.DP_REPORT_DB_SCHEMA);
				ReportDetailDTO reportDetailDTO = getReportDetails(genericDTO.getReportId());
				procName = "{ call " + dbSchema + "." + reportDetailDTO.getReportProcName()
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ? ) }";
				conRDB = DBConnectionManager.getMISReportDBConnection();
			}
			String productType = genericDTO.getProductType();
			String prodIds = null;

			if (productType != null && !productType.equals(""))
			{
				prodIds = DropDownUtility.getInstance().getProductIds(conRDB, productType,
						genericDTO.getCircleIds());
			}

			if (genericDTO.getSearchOption() != null && !genericDTO.getSearchOption().equals("-1"))
			{

				if (genericDTO.getGroupId() == Constants.DISTIBUTOR)
				{
					genericDTO.setDistIds(String.valueOf(genericDTO.getAccountId()));
				}
				else if (genericDTO.getGroupId() == Constants.TM)
				{
					genericDTO.setDistIds("-1");
					genericDTO.setTsmIds(String.valueOf(genericDTO.getAccountId()));
				}
				else if (genericDTO.getGroupId() == Constants.ZBM
						|| genericDTO.getGroupId() == Constants.ZSM
						|| genericDTO.getGroupId() == Constants.SALES_HEAD)
				{
					genericDTO.setDistIds("-1");
					genericDTO.setTsmIds("-1");
				}
			}

			logger.info("proc name :" + procName);
			/*
			 * logger.info("1:"+genericDTO.getReportId());
			 * logger.info("2:"+genericDTO.getDistIds());
			 * logger.info("3:"+prodIds);
			 * logger.info("4:"+genericDTO.getCollectionType());
			 * logger.info("5:"+genericDTO.getStatus());
			 * logger.info("6:"+genericDTO.getStbStatus());
			 * logger.info("7:"+genericDTO.getFromDate());
			 * logger.info("8:"+genericDTO.getTodate());
			 * logger.info("9:"+genericDTO.getTsmIds());
			 * logger.info("10:"+prodIds);
			 * logger.info("11:"+genericDTO.getAccountType());
			 * logger.info("12:"+genericDTO.getSearchOption());
			 * logger.info("13:"+genericDTO.getSearchCriteria());
			 * logger.info("14:"+genericDTO.getPoStatus());
			 * logger.info("15:"+genericDTO.getDateOption());
			 * logger.info("16:"+genericDTO.getDateOption());
			 * logger.info("17:"+genericDTO.getPendingAt());
			 * logger.info("18:"+genericDTO.getCircleIds());
			 * logger.info("19:"+genericDTO.getTransferType());
			 * logger.info("20:"+genericDTO.getDcNo());
			 * logger.info("21:"+genericDTO.getAccountName());
			 * logger.info("22:"+genericDTO.getRecoStatus());
			 * logger.info("23:"+genericDTO.getDcStatus());
			 * logger.info("24:"+genericDTO.getBusinessCategory());
			 */

			// logger.info("Reco Period:::"+genericDTO.getRecoPeriod());
			logger.info("===== logging properly ===============");
			logger.info("Proc name :" + procName);
			cstmt = conRDB.prepareCall(procName);
			cstmt.setInt(1, genericDTO.getReportId());
			logger.info("parameter 1:" + genericDTO.getReportId());
			cstmt.setString(2, genericDTO.getDistIds());
			logger.info("parameter 2:" + genericDTO.getDistIds());
			// Added by Nehil
			if (reportId == ReportConstants.STOCK_ELIGIBILITY_REPORT)
			{
				cstmt.setString(3, genericDTO.getPtId());
				logger.info("parameter 3:" + genericDTO.getPtId());
			}
			else
			{
				cstmt.setString(3, prodIds);
				logger.info("parameter 3:" + prodIds);
			}

			cstmt.setString(4, genericDTO.getCollectionType());
			logger.info("parameter 4:" + genericDTO.getCollectionType());
			if (genericDTO.getStbStatus() != null && !(genericDTO.getStbStatus().equals("")))
			{
				cstmt.setString(5, genericDTO.getStbStatus());
				logger.info("parameter 5:" + genericDTO.getStbStatus());
			}
			else
			{
				cstmt.setString(5, genericDTO.getStatus());
				logger.info("parameter 5:" + genericDTO.getStatus());
			}

			cstmt.setString(6, genericDTO.getFromDate());
			logger.info("parameter 6:" + genericDTO.getFromDate());
			cstmt.setString(7, genericDTO.getTodate());
			logger.info("parameter 7:" + genericDTO.getTodate());
			cstmt.setString(8, genericDTO.getTsmIds());
			logger.info("parameter 8:" + genericDTO.getTsmIds());
			// Added by Nehil
			if (reportId == ReportConstants.STOCK_ELIGIBILITY_REPORT)
			{
				cstmt.setString(9, genericDTO.getDtId());
				logger.info("parameter 9:" + genericDTO.getDtId());
			}
			else
			{
				cstmt.setString(9, prodIds);
				logger.info("parameter 9:" + prodIds);
			}
			cstmt.setString(10, genericDTO.getAccountType());
			logger.info("parameter 10:" + genericDTO.getAccountType());
			cstmt.setString(11, genericDTO.getSearchOption());
			logger.info("parameter 11:" + genericDTO.getSearchOption());
			cstmt.setString(12, genericDTO.getSearchCriteria());
			logger.info("parameter 12:" + genericDTO.getSearchCriteria());
			cstmt.setString(13, genericDTO.getPoStatus());
			logger.info("parameter 13:" + genericDTO.getPoStatus());
			cstmt.setString(14, genericDTO.getDateOption());
			logger.info("parameter 14:" + genericDTO.getDateOption());
			cstmt.setString(15, genericDTO.getDateOption());
			logger.info("parameter 15:" + genericDTO.getDateOption());
			cstmt.setString(16, genericDTO.getPendingAt());
			logger.info("parameter 16:" + genericDTO.getPendingAt());
			cstmt.setString(17, genericDTO.getCircleIds());
			logger.info("parameter 17:" + genericDTO.getCircleIds());
			System.out.println("******************getActivity************"
					+ genericDTO.getActivity());
			if (genericDTO.getReportId() == ReportConstants.DISTRIBUTOR_ACTIVITY_REPORT)
			{
				System.out.println("******************inside if************");
				cstmt.setString(18, genericDTO.getActivity());
				logger.info("18 :" + genericDTO.getActivity());
			}
			else
			{
				System.out.println("******************inside else************");
				cstmt.setString(18, genericDTO.getTransferType());
				logger.info("parameter 18:" + genericDTO.getTransferType());
			}

			cstmt.setString(19, genericDTO.getDcNo());
			logger.info("parameter 19:" + genericDTO.getDcNo());
			cstmt.setString(20, genericDTO.getAccountName());
			logger.info("parameter 20:" + genericDTO.getAccountName());

			if (genericDTO.getReportId() == 43
					|| genericDTO.getReportId() == ReportConstants.DEBIT_NOTE_REPORT) // report
			// id
			// = 50added by aman for debit note report on 7 / 3 / 14
			{
				cstmt.setString(21, genericDTO.getRecoPeriod());
				logger.info("parameter 21:" + genericDTO.getRecoPeriod());
			}
			else
			{
				cstmt.setString(21, genericDTO.getRecoStatus());
				logger.info("parameter 21:" + genericDTO.getRecoStatus());
			}

			cstmt.setString(22, genericDTO.getDcStatus());
			logger.info("parameter 22:" + genericDTO.getDcStatus());
			cstmt.setString(23, genericDTO.getBusinessCategory());
			logger.info("parameter 23:" + genericDTO.getBusinessCategory());

			if (reportId == ReportConstants.STOCK_ELIGIBILITY_REPORT)
			{
				cstmt.setString(24, genericDTO.getZoneIds());
				logger.info("parameter 24:" + genericDTO.getZoneIds());
				cstmt.registerOutParameter(25, Types.VARCHAR);
				logger.info("parameter 25:" + Types.VARCHAR);
				cstmt.registerOutParameter(26, Types.VARCHAR);
				logger.info("parameter 26:" + Types.VARCHAR);
			}
			else if(reportId == ReportConstants.STB_WISE_SERIALIZED_STOCK_REPORT )//|| reportId == ReportConstants.PENDING_FOR_RETURN_DETAILS_REPORT)
			{
				String configId = "";
				ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
				
				if(rb.getKeys().equals("configuration.configId"));
				{
					configId = rb.getString("configuration.configId");
				}
				
				cstmt.setString(24, configId);
				logger.info("parameter 24:" + configId);
				cstmt.registerOutParameter(25, Types.VARCHAR);
				logger.info("parameter 25:" + Types.VARCHAR);
				cstmt.registerOutParameter(26, Types.VARCHAR);
				logger.info("parameter 26:" + Types.VARCHAR);
			
			}
			else
			{
				cstmt.registerOutParameter(24, Types.VARCHAR);
				logger.info("parameter 24:" + Types.VARCHAR);
				cstmt.registerOutParameter(25, Types.VARCHAR);
				logger.info("parameter 25:" + Types.VARCHAR);
			}
			
			logger.info("== now executing query ====");
			cstmt.execute();

			// System.out.println(cstmt.getString(23));
			// System.out.println(cstmt.getString(24));
			rs = cstmt.getResultSet();
			
			
			
			
			if (genericDTO.getReportId() == 43)
			{
				genericOutputDto = getResultSet(rs, genericOutputDto, genericDTO.getReportId());
			}
			else
			{
				getResultSet(rs, genericOutputDto, genericDTO.getReportId());
			}

			logger.info("Executed successfylly :::::::::::");

		}
		catch (SQLException sqe)
		{

			logger.error("SQL exception occured::" + sqe.getMessage());
			logger.error(sqe.getMessage(), sqe);
			throw new DAOException(sqe.getMessage());
		}
		catch (DAOException daoException)
		{
			// logger.error(daoException.getMessage(), daoException);
			try
			{
				logger.error(cstmt.getString(24));
			}
			catch (SQLException e)
			{
			}
			throw daoException;
		}
		catch (Exception e)
		{
			logger.error("Exception occured::" + e.getMessage());
			logger.error(e.getMessage(), e);
			logger.error(e.getLocalizedMessage());
			throw new DAOException(e.getMessage());
		}
		finally
		{

			DBConnectionManager.releaseResources(null, rs);
			try
			{
				if (cstmt != null)
				{
					cstmt.close();
					cstmt = null;
				}
			}
			catch (Exception ex)
			{
				logger.info(ex);
			}
			DBConnectionManager.releaseResources(conRDB);
		}
		return genericOutputDto;
	}	
	
	
}
