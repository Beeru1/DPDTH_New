package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.beans.DPInterSSDStockTransferReportFormBean;
import com.ibm.dp.beans.DPReverseLogisticReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPInterSSDStockTransferReportDao;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.InterSSDReportDTO;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPInterSSDStockTransferReportDaoImpl extends BaseDaoRdbms implements DPInterSSDStockTransferReportDao{

	private Logger logger = Logger.getLogger(DPReportDaoImpl.class.getName());
	
	public static final String SQL_INTERSSD_DETAIL_REPORT 	= DBQueries.SQL_INTERSSD_DETAIL_REPORT;
	
	
	public DPInterSSDStockTransferReportDaoImpl(Connection con) {
		super(con);
	}
	public List<InterSSDReportDTO> getInterSSDStockTransferExcel(InterSSDReportDTO reportDto) throws DAOException{
		List<InterSSDReportDTO> listReturn = new ArrayList<InterSSDReportDTO>();
		PreparedStatement pstmt = null;
		ResultSet rsetReport = null;
		Connection conRDB = DBConnectionManager.getMISReportDBConnection();
		
		String slectedProd= reportDto.getHiddenSTBTypeSelec();
		String prodIds =DropDownUtility.getInstance().getProductIds(conRDB, slectedProd,reportDto.getHiddenCircleSelecIds());
		String dist = reportDto.getHiddenDistSelecIds();
		String dcNo = reportDto.getDcNo();				
		String dateOption = reportDto.getDateOption();	
		int transferType = Integer.parseInt(reportDto.getTransferType());
		String fromDate = reportDto.getFromDate();
		String toDate = reportDto.getToDate();
		try
		{
		String sql = "Select distinct (select DISTRIBUTOR_OLM_ID from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.FROM_DIST ) as FROM_DISTRIBUTOR_OLM_ID ," +
				"(select DISTRIBUTOR_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.FROM_DIST ) as FROM_DISTRIBUTOR_NAME ," +
				"(select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.FROM_DIST ) as FROM_TSM_NAME ," +
				"(select DISTRIBUTOR_OLM_ID from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.TO_DIST ) as TO_DISTRIBUTOR_OLM_ID ," +
				"(select DISTRIBUTOR_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.TO_DIST ) as TO_DISTRIBUTOR_NAME , " +
				"(select TSM_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.TO_DIST ) as TO_TSM_NAME ," +
				"ISST.ZBM_NAME,(select CIRCLE_NAME from DP_DIST_DETAILS where DISTRIBUTOR_ID = ISST.FROM_DIST) As CIRCLE_NAME,ISST.DC_NO,ISSTPM.PRODUCT_NAME,ISSTPM.SERIAL_NO," +
				"ISST.INITIATION_DATE, ISST.TRANSFER_DATE, ISST.ACCEPTANCE_DATE	from DP_INTER_SSD_STOCK_TRANSFER ISST," +
				"DP_INTER_SSD_TRANSFER_PRODUCT_DETAILS ISSTPM where ISSTPM.TRANSACTION_ID = ISST.TRANSACTION_ID";
		
			
		if(!prodIds.equals("")){
			sql += " and PRODUCT_ID in ("+prodIds+")";
		}		
		if(transferType==0){
			sql+= " And ISST.FROM_DIST In( "+ dist +" )"; 
		}
		if(transferType==1){
			sql+= " And ISST.TO_DIST In( "+ dist +" )"; 
		}
		if(transferType==2){
			sql+= " And (ISST.FROM_DIST In( "+ dist +" ) OR ISST.TO_DIST In( "+ dist +" ))"; 
		}
		
		int index=1;
		if(!dcNo.equals("")){
			sql += " and DC_NO = ?";			
		}
		Date dtFromDate = null;
		Date dtToDate = null;
		if(dateOption.equalsIgnoreCase("INITIATION_DATE")){
			sql+= " And DATE(INITIATION_DATE) between ? and ?";
			
		}
		if(dateOption.equalsIgnoreCase("TRANSFER_DATE")){
			sql+= " And  DATE(TRANSFER_DATE) between ? and ?";
			
		}
		if(dateOption.equalsIgnoreCase("ACCEPTANCE_DATE")){
			sql+= " And DATE(ACCEPTANCE_DATE) between ? and ?";
		
		}
		sql+= " with UR ";
		pstmt =  conRDB.prepareStatement(sql);
		if(!dcNo.equals("")){			
			pstmt.setString(index++, dcNo);
		}
		if(fromDate != null && !fromDate.equals("")) {
			dtFromDate = Utility.getSqlDateFromString(fromDate, Constants.DT_FMT_SQL);
			pstmt.setDate(index++, new java.sql.Date(dtFromDate.getTime()));
		}
		if(toDate != null && !toDate.equals("")) {
			dtToDate = Utility.getSqlDateFromString(toDate, Constants.DT_FMT_SQL);
			pstmt.setDate(index++, new java.sql.Date(dtToDate.getTime()));
		}		
			
		logger.info(" in  &&&&&&&&&&&&&&&&&&&&& getCollectionDetailExcel");
		
			InterSSDReportDTO reportReturnDto =null;			
		
			
			rsetReport= pstmt.executeQuery();
			while(rsetReport.next()){
				reportReturnDto =new InterSSDReportDTO();
				reportReturnDto.setDistLoginId(rsetReport.getString("FROM_DISTRIBUTOR_OLM_ID"));
				reportReturnDto.setDistName(rsetReport.getString("FROM_DISTRIBUTOR_NAME"));
				reportReturnDto.setTsmName(rsetReport.getString("FROM_TSM_NAME"));
				reportReturnDto.setToDistLoginId(rsetReport.getString("TO_DISTRIBUTOR_OLM_ID"));
				reportReturnDto.setToDistName(rsetReport.getString("TO_DISTRIBUTOR_NAME"));
				reportReturnDto.setToTsmName(rsetReport.getString("TO_TSM_NAME"));
				reportReturnDto.setZbmName(rsetReport.getString("ZBM_NAME"));
				reportReturnDto.setCircleName(rsetReport.getString("Circle_Name"));
				reportReturnDto.setDcNo(rsetReport.getString("DC_NO"));
				reportReturnDto.setStbType(rsetReport.getString("PRODUCT_NAME"));
				reportReturnDto.setStbSerialNo("'"+rsetReport.getString("SERIAL_NO"));
				reportReturnDto.setInitiationDate(rsetReport.getDate("INITIATION_DATE"));
				reportReturnDto.setTransferDate(rsetReport.getDate("TRANSFER_DATE"));
				reportReturnDto.setAcceptanceDate(rsetReport.getDate("ACCEPTANCE_DATE"));
				
				
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
			DBConnectionManager.releaseResources(conRDB);
		}
		
		return listReturn;
	}
	
}
