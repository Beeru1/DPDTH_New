package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.StockRecoSummReportDao;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class StockRecoSummReportDaoImpl   extends BaseDaoRdbms implements StockRecoSummReportDao{
	private Logger logger = Logger.getLogger(StockRecoSummReportDaoImpl.class.getName());
	public static final String SQL_STOCK_RECO_SUMMARY_REPORT 	= DBQueries.SQL_STOCK_RECO_SUMMARY_REPORT;
	
	public StockRecoSummReportDaoImpl(Connection connection) {
		super(connection);
	}

	public List<StockRecoSummReportDto> getRecoSummaryExcel(StockRecoSummReportDto reportDto) throws DAOException{
		Connection conRDB = DBConnectionManager.getMISReportDBConnection();
		List<StockRecoSummReportDto> listReturn = new ArrayList<StockRecoSummReportDto>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getRecoSummaryExcel");
		try
		{
			
			String slectedProd= reportDto.getHiddenProductSeletIds() ;
			logger.info("Circle id == "+reportDto.getHiddenCircleSelecIds() );
			logger.info("Dist id == "+reportDto.getHiddenDistSelecIds() );
			logger.info("Product id == "+slectedProd );
			logger.info("TSM id == "+reportDto.getHiddenTsmSelecIds() );
			String prodIds =DropDownUtility.getInstance().getProductIds(conRDB, slectedProd,reportDto.getHiddenCircleSelecIds());
			
			String query = DBQueries.SQL_STOCK_RECO_SUMMARY_REPORT;
			query += " and RECO.DIST_ID in ("+reportDto.getHiddenDistSelecIds()+") ";
			//For No product Id under the selected Category
			if(prodIds.equals("")){
				prodIds = "0";
			}
			if(!prodIds.equals("")){
				query += " and RECO.PRODUCT_ID in ("+prodIds+")";
			}
			query += " and CREATED_ON between FROMDATE and TODATE group by RECO.product_id,RECO.dist_id,RECO.PRODUCT_NAME,DIST.DISTRIBUTOR_OLM_ID,DIST.DISTRIBUTOR_NAME,DIST.TSM_NAME,DIST.CIRCLE_NAME order by RECO.DIST_ID,RECO.PRODUCT_ID";
			
			String fromDate = Utility.convertDateFormat(reportDto.getFromDate(), "MM/dd/yyyy", "yyyy-MM-dd");
			String toDate = Utility.convertDateFormat(reportDto.getToDate(), "MM/dd/yyyy", "yyyy-MM-dd");
			Date  frmDateDt= Utility.getDateAsDate(reportDto.getFromDate(),"MM/dd/yyyy") ;
			long frmdateTime = frmDateDt.getTime();
			long revDateTime = frmdateTime -(1000*60*60*24);
			Date prevDateDt = new Date();
			prevDateDt.setTime(revDateTime);
			logger.info("From Date in Date format =="+frmDateDt+"  And Prev Date == "+prevDateDt);
			String prevDate =Utility.converUtilDateToString(prevDateDt, "yyyy-MM-dd");
			
			fromDate = "'"+fromDate + "-00.00.00.000000'";
			toDate = "'"+toDate + "-23.59.59.999999'";
			prevDate = "'"+prevDate + "-00.00.00.000000'";
			logger.info("IN DAO IMPL From date == "+fromDate+" and To date  == "+toDate +"  and PREV Date == "+prevDate);
			query = query.replaceAll("PREVDATE", prevDate);
			query = query.replaceAll("FROMDATE", fromDate);
			query = query.replaceAll("TODATE", toDate);
			logger.info("Query  == "+query);
			StockRecoSummReportDto reportReturnDto =null;
			pstmt =  conRDB.prepareStatement(query);
			rsetReport= pstmt.executeQuery();
			Integer receivedStock=0;
			Integer returnStock=0;
			while(rsetReport.next()){
				receivedStock = rsetReport.getInt("RECEIVED_WH")+rsetReport.getInt("RECEIVED_INTER_SSD")+rsetReport.getInt("RECEIVED_UPGRADE")+rsetReport.getInt("RECEIVED_CHURN");
				returnStock =  rsetReport.getInt("RETURNED_DOA_BI")+rsetReport.getInt("RETURNED_DOA_AI")+rsetReport.getInt("RETURNED_SWAP")+rsetReport.getInt("RETURNED_C2S");
				reportReturnDto =new StockRecoSummReportDto();
				reportReturnDto.setDistLoginId(rsetReport.getString("DISTRIBUTOR_OLM_ID"));
				reportReturnDto.setDistName(rsetReport.getString("DISTRIBUTOR_NAME"));
				reportReturnDto.setTsmName(rsetReport.getString("TSM_NAME"));
				reportReturnDto.setCircleName(rsetReport.getString("CIRCLE_NAME"));
				reportReturnDto.setActivation(rsetReport.getString("ACTIVATION"));
				reportReturnDto.setClosingStock(rsetReport.getString("CLOSE_STOCK"));
				reportReturnDto.setOpeningStock(rsetReport.getString("OPEN_STOCK"));
				reportReturnDto.setReceivedStock(receivedStock.toString());
				reportReturnDto.setReturnedStock(returnStock.toString());
				reportReturnDto.setProductName(rsetReport.getString("PRODUCT_NAME"));	
			
				listReturn.add(reportReturnDto);
			}
			
			logger.info("getRecoSummaryExcel executed successfuly in StockRecoSummReportDaoImpl");
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			throw new DAOException(e.getMessage());
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt ,rsetReport );
			DBConnectionManager.releaseResources(conRDB);
			
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
