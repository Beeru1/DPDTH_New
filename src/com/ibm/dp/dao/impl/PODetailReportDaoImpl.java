package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class PODetailReportDaoImpl  extends BaseDaoRdbms implements PODetailReportDao{
	private Logger logger = Logger.getLogger(PODetailReportDaoImpl.class.getName());
	public static final String SQL_PO_DETAIL_REPORT 	= DBQueries.SQL_PO_DETAIL_REPORT;
	public static final String SQL_PO_STATUS_LIST 	= DBQueries.SQL_PO_STATUS_LIST;
	
	public PODetailReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<PODetailReportDto> getPoDetailExcel(PODetailReportDto reportDto) throws DAOException{
		List<PODetailReportDto> listReturn = new ArrayList<PODetailReportDto>();
		PreparedStatement ps = null;
		ResultSet rsetReport = null;
		StringBuffer sql = null;
		logger.info("PO Detail Report Start......................!!!");
		try
		{
			
			System.out.println("List of cIrcles.... " + reportDto.getHiddenCircleSelecIds());

			connection = DBConnectionManager.getMISReportDBConnection();

			String query = "SELECT distinct DISTRIBUTOR_OLM_ID, DISTRIBUTOR_NAME, TSM_NAME, CIRCLE_NAME, PS.PR_NO, PS.PR_DATE, PS.PO_NO, PS.PO_DATE, PS.DC_NO, PS.DC_DATE,  (SELECT VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID =2 AND ID = PSAT.PO_STATUS) PO_ST, " +
			"PPS.PRODUCT_NAME, PSS.STB_NO, (SELECT VALUE FROM DP_CONFIGURATION_DETAILS WHERE CONFIG_ID =13 AND ID = PSS.STATUS) STB_ST ,LAST_PO_ACTION_DATE FROM " +
			" DP_DIST_DETAILS DDD, PO_PRODUCT_SUMMARY PPS, PO_SUMMARY PS, PO_STB_SUMMARY PSS, PO_STATUS_AUDIT_TRAIL PSAT WHERE PPS.PR_NO = PS.PR_NO AND PSAT.PO_NO = PS.PO_NO AND PPS.PRODUCT_ID = PSS.PRODUCT_ID " +
				   " AND DDD.DISTRIBUTOR_ID = PS.DIST_ID AND DDD.DISTRIBUTOR_ID IN ( "+reportDto.getHiddenDistSelecIds()+ ") AND PSAT.PO_STATUS IN ( "+reportDto.getHiddenPoStatusSelec()+ ") "; 
			sql = new StringBuffer(query);
			System.out.println("Query..........................." + query);
			System.out.println(reportDto.getDateOption());
			
			if(reportDto.getDateOption().equalsIgnoreCase("PO_CREATE_DATE")) {
				sql.append("AND (DATE(PSAT.PO_STATUS_DATE) BETWEEN ? AND ?) AND (PSAT.PO_STATUS) = 2 ");
			}
			if(reportDto.getDateOption().equalsIgnoreCase("DISPATCH_DATE")) {
				sql.append("AND (DATE(PSAT.PO_STATUS_DATE) BETWEEN ? AND ?) AND (PSAT.PO_STATUS) = 7");
			}
			if(reportDto.getDateOption().equalsIgnoreCase("PO_ACCEPTANCE_DATE")) {
				sql.append("AND DATE(PSAT.PO_STATUS_DATE) BETWEEN ? AND ? AND (PSAT.PO_STATUS = 31 OR PSAT.PO_STATUS = 33) ");
			}
			if(reportDto.getSearchOption().equalsIgnoreCase("PR_NO")) {
				sql.append(" AND PS.PR_NO = ? ");
			}
			if(reportDto.getSearchOption().equalsIgnoreCase("PO_NO")) {
				sql.append(" AND PS.PO_NO = ? ");
			}
			if(reportDto.getSearchOption().equalsIgnoreCase("DC_NO")) {
				sql.append(" AND PS.DC_NO = ? ");
			}

			int index = 1;
			ps = connection.prepareStatement(sql.toString());			
			System.out.println(sql.toString());
			Date dtFromDate = null;
			Date dtToDate = null;
			
			if(!reportDto.getDateOption().equals("") && reportDto.getDateOption() != null) {
				if(reportDto.getFromDate() != null && !reportDto.getFromDate().equals("")) {
					dtFromDate = Utility.getSqlDateFromString(reportDto.getFromDate(), Constants.DT_FMT_SQL);
					ps.setDate(index++, new java.sql.Date(dtFromDate.getTime()));
				}
				if(reportDto.getToDate() != null && !reportDto.getToDate().equals("")) {
					dtToDate = Utility.getSqlDateFromString(reportDto.getToDate(), Constants.DT_FMT_SQL);
					ps.setDate(index++, new java.sql.Date(dtToDate.getTime()));
				}
			}
			
//			if(reportDto.getDateOption()== "Dispatch Date") {
//				sql.append("AND DATE(SS.SEC_SALE_DATE) BETWEEN ? AND ? ");
//			}
//			if(reportDto.getDateOption()== "PO Acceptance Date") {
//				sql.append("AND DATE(SS.SEC_SALE_DATE) BETWEEN ? AND ? ");
//			}
			
			if(!reportDto.getSearchOption().equalsIgnoreCase("-1")) {
				ps.setString(index++, reportDto.getSearchCriteria());
			}
//			if(reportDto.getSearchOption() == "PO No.") {
//				sql.append(" AND PS.PO_NO = ? ");
//			}
//			if(reportDto.getSearchOption() == "DC No.") {
//				sql.append(" AND PS.DC_NO = ? ");
//			}
			
			System.out.println("Query Final hai...................." + query);
			rsetReport = ps.executeQuery();
			PODetailReportDto reportReturnDto =null;
	
			
			while(rsetReport.next()){
				reportReturnDto =new PODetailReportDto();
				reportReturnDto.setDistLoginId(rsetReport.getString("DISTRIBUTOR_OLM_ID"));
				reportReturnDto.setDistName(rsetReport.getString("DISTRIBUTOR_NAME"));
				reportReturnDto.setTsmName(rsetReport.getString("TSM_NAME"));
				reportReturnDto.setCircleName(rsetReport.getString("CIRCLE_NAME"));
				reportReturnDto.setPrNo(rsetReport.getString("PR_NO"));
				reportReturnDto.setPrDate(rsetReport.getString("PR_DATE"));
				reportReturnDto.setPoNo(rsetReport.getString("PO_NO"));
				reportReturnDto.setPoDate(rsetReport.getString("PO_DATE"));
				reportReturnDto.setDcNo(rsetReport.getString("DC_NO"));
				reportReturnDto.setDcDate(rsetReport.getString("DC_DATE"));
				reportReturnDto.setPoStatusReport(rsetReport.getString("PO_ST"));
				reportReturnDto.setStbType(rsetReport.getString("PRODUCT_NAME"));
				reportReturnDto.setStbSerialNo(rsetReport.getString("STB_NO"));
				reportReturnDto.setLatestStatus(rsetReport.getString("STB_ST"));
				reportReturnDto.setLastPoActionDate(rsetReport.getString("LAST_PO_ACTION_DATE"));
				listReturn.add(reportReturnDto);
			}
			logger.info("getPoDetailExcel executed successfuly in PODetailReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(ps ,rsetReport );
			DBConnectionManager.releaseResources(connection);
		}
		
		return listReturn;
	}
	public List<SelectionCollection> getPoStatusList() throws DAOException{
		List<SelectionCollection> listReturn = new ArrayList<SelectionCollection>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getPoStatusList");
		try
		{
			pstmt =  connection.prepareStatement(SQL_PO_STATUS_LIST);
			SelectionCollection poStatus =null;
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				poStatus =new SelectionCollection();
				poStatus.setStrValue(rsetReport.getString("ID"));
				poStatus.setStrText(rsetReport.getString("VALUE"));
				listReturn.add(poStatus);
			}
			logger.info("getPoStatusList executed successfuly in PODetailReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
		}
		
		return listReturn;
	}
}
