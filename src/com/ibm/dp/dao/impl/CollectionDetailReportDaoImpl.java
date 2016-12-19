package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CollectionDetailReportDao;
import com.ibm.dp.dao.PODetailReportDao;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class CollectionDetailReportDaoImpl   extends BaseDaoRdbms implements CollectionDetailReportDao {
	private Logger logger = Logger.getLogger(PODetailReportDaoImpl.class.getName());
	public static final String SQL_COLLECTION_DETAIL_REPORT 	= DBQueries.SQL_COLLECTION_DETAIL_REPORT;
	
	public CollectionDetailReportDaoImpl(Connection connection) {
		super(connection);
	}
	
	public List<CollectionReportDTO> getCollectionDetailExcel(CollectionReportDTO reportDto) throws DAOException{
		List<CollectionReportDTO> listReturn = new ArrayList<CollectionReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getCollectionDetailExcel");
		try
		{
			CollectionReportDTO reportReturnDto =null;
			Connection conRDB = DBConnectionManager.getMISReportDBConnection();
			java.sql.Date dtFromDate,dtToDate;
			DropDownUtility dropDownUtility=DropDownUtility.getInstance();
			String productIds=dropDownUtility.getProductIds(conRDB,reportDto.getHiddenProductSeletIds(),reportDto.getHiddenCircleSelecIds());
			String strQuery = DBQueries.SQL_COLLECTION_DETAIL_REPORT;
			strQuery=strQuery.replace("?1", reportDto.getHiddenDistSelecIds());
			strQuery=strQuery.replace("?2", productIds);
			strQuery=strQuery.replace("?3", reportDto.getHiddenCollectionTypeSelec());
			String statusArray[]=reportDto.getHiddenStatusSelec().split(",");
			String status="";
			for (String string : statusArray) {
				status=status+"'"+string+"',";
			}
			status=status.substring(0, status.length()-1);
			strQuery=strQuery.replace("?4", status);
			System.out.println("strQuery111111111111"+strQuery);
			pstmt =  conRDB.prepareStatement(strQuery);
			if(reportDto.getFromDate() != null && !reportDto.getFromDate().equals("")) {
				dtFromDate = Utility.getSqlDateFromString(reportDto.getFromDate(), Constants.DT_FMT_SQL);
				pstmt.setDate(1, new java.sql.Date(dtFromDate.getTime()));
				
				
				
			}
			if(reportDto.getToDate() != null && !reportDto.getToDate().equals("")) {
				dtToDate = Utility.getSqlDateFromString(reportDto.getToDate(), Constants.DT_FMT_SQL);
				pstmt.setDate(2, new java.sql.Date(dtToDate.getTime()));
				
			}
			
			
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				reportReturnDto =new CollectionReportDTO();
				reportReturnDto.setDistLoginId(rsetReport.getString("DISTRIBUTOR_OLM_ID"));
				reportReturnDto.setDistName(rsetReport.getString("DISTRIBUTOR_NAME"));
				reportReturnDto.setTsmName(rsetReport.getString("TSM_NAME"));
				reportReturnDto.setCircleName(rsetReport.getString("CIRCLE_NAME"));
				reportReturnDto.setCollectionType(rsetReport.getString("COLLECTION_TYPE"));
				reportReturnDto.setStbType(rsetReport.getString("PRODUCT_NAME"));
				reportReturnDto.setInventoryChangeDate(rsetReport.getDate("INVENTORY_CHANGE_DATE"));
				reportReturnDto.setRecoveredSTB(rsetReport.getString("RECOVERED_STB_NUM"));
				reportReturnDto.setInstalledSTB(rsetReport.getString("INSTALLED_STB_NUM"));
				reportReturnDto.setDcNo(rsetReport.getString("DC_NO"));
				String status1 = rsetReport.getString("STATUS");
				//reportReturnDto.setStatus("test");
				if("REC".equalsIgnoreCase(status1)){
					reportReturnDto.setStatus("Pending for Receive");
				} else if("COL".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Received by distributor");
				} else if("IDC".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Ready to Dispatch");
				} else if("S2W".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Sent to Warehouse");
				} else if("AIW".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Accepted in Warehouse");
				} else if("MSN".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Not Accepted in Warehouse");
				} else if("ABW".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Added by Warehouse");
				} else if("ERR".equalsIgnoreCase(status1)) {
					reportReturnDto.setStatus("Error in sending to warehouse");
				}
				listReturn.add(reportReturnDto);
			}
			logger.info("getCollectionDetailExcel executed successfuly in CollectionReportDaoImpl");
			
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
	
	public List<DpProductCategoryDto> getProductList() throws DAOException{
		List<DpProductCategoryDto> listReturn = new ArrayList<DpProductCategoryDto>();
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getProductList");
		try
		{
			listReturn =DropDownUtility.getInstance().getProductCategoryLst();
			logger.info("getProductList executed successfuly in StockRecoSummReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		
		
		return listReturn;
	}
	
}
