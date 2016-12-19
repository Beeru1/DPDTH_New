package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.ActivationDetailReportDao;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

 
public class ActivationDetailReportDaoImpl implements  ActivationDetailReportDao {
	
	private static Logger logger = Logger.getLogger(ActivationDetailReportDaoImpl.class);	
	private static ActivationDetailReportDao  activationDetailReportDao  = new ActivationDetailReportDaoImpl();
	private ActivationDetailReportDaoImpl(){}
	public static ActivationDetailReportDao getInstance() {
		return activationDetailReportDao;
	}
	public List<ActivationDetailReportDTO> getActivationDetailReport(ActivationDetailReportDTO activationDetailReportDTO) throws DAOException {
		PreparedStatement ps = null;
		Connection conRDB = DBConnectionManager.getMISReportDBConnection();
		ResultSet rs = null; 		
	
		List<ActivationDetailReportDTO> reportStockDataList= new ArrayList<ActivationDetailReportDTO>();
		String distId = activationDetailReportDTO.getDistIds();	
		String circleIds = activationDetailReportDTO.getCircleIds();	
		String productIds= activationDetailReportDTO.getProductType();	
		
		String fromDate = activationDetailReportDTO.getFromDate();
		String toDate = activationDetailReportDTO.getTodate();
		try {
			String prodIds =DropDownUtility.getInstance().getProductIds(conRDB, productIds, circleIds);
			String sql = "SELECT distinct DD.DISTRIBUTOR_OLM_ID, DD.DISTRIBUTOR_NAME, DD.TSM_NAME, DD.CIRCLE_NAME, DS.FSE_NAME, DS.RETAILER_NAME,(Select PRODUCT_NAME from DP_PRODUCT_MASTER PM " +
					"Where PM.PRODUCT_ID=DS.PRODUCT_ID),DS.SERIAL_NO,CASE DS.STATUS WHEN 1 then 'UNASSIGNED COMPLETE' WHEN 3 then 'Assigned' WHEN 4 then 'REPAIR' WHEN 5 then 'RESTRICTED' " +
					"WHEN 10 then 'UNASSIGNED PENDING' WHEN 11 then 'RETURNED AS DOA' WHEN 12 then 'Returned As SWAP'WHEN 13 then 'ADDED BY WAREHOUSE' WHEN 14 then 'RETURNED TO WAREHOUSE'" +
					"END  AS STATUS,DP.PR_NO, DP.PO_NO, (SELECT PO_STATUS_DATE FROM PO_STATUS_AUDIT_TRAIL WHERE PO_NO = DP.PO_NO AND (PO_STATUS = 31 OR PO_STATUS = 33) FETCH FIRST 1 ROW ONLY) AS PO_ACCEPTANCE_DATE, " +
					"DS.ASSIGN_DATE, DS.CUSTOMER_ID FROM DP_DIST_DETAILS DD, PO_SUMMARY DP, DP_STOCK_INVENTORY DS where DD.DISTRIBUTOR_ID=DP.DIST_ID " +
					"and DP.DIST_ID=DS.DISTRIBUTOR_ID and DP.INV_NO = DS.INV_NO";
			
			if(distId != null && !distId.equals("")) {								
					sql+= " AND DD.DISTRIBUTOR_ID IN("+ distId +")";
				}
			if(prodIds != null && !prodIds.equals("")) {								
				sql+= " AND DS.PRODUCT_ID IN("+ prodIds +")";
			}
			if(fromDate != null && !fromDate.equals("")) {				
				sql+= " AND DATE(DS.ASSIGN_DATE) BETWEEN ? AND ? ";
			}				
			sql+= " with UR ";
			Date dtFromDate = null;
			Date dtToDate = null;			
		
			ps =  conRDB.prepareStatement(sql);
			int index = 1;
			if(fromDate != null && !fromDate.equals("")) {
				dtFromDate = Utility.getSqlDateFromString(fromDate, Constants.DT_FMT_SQL);
				ps.setDate(index++, new java.sql.Date(dtFromDate.getTime()));
			}
			if(toDate != null && !toDate.equals("")) {
				dtToDate = Utility.getSqlDateFromString(toDate, Constants.DT_FMT_SQL);
				ps.setDate(index++, new java.sql.Date(dtToDate.getTime()));
			}			
		
			logger.info(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				ActivationDetailReportDTO actDTO = new ActivationDetailReportDTO();
				actDTO.setDistLoginId(rs.getString("DISTRIBUTOR_OLM_ID"));
				actDTO.setDistName(rs.getString("DISTRIBUTOR_NAME"));
				actDTO.setTsmName(rs.getString("TSM_NAME"));
				actDTO.setCircleName(rs.getString("CIRCLE_NAME"));		
				actDTO.setFseName(rs.getString("FSE_NAME"));	
				actDTO.setRetName(rs.getString("RETAILER_NAME"));	
				actDTO.setStbType(rs.getString("PRODUCT_NAME"));
				actDTO.setSerial("'"+rs.getString("SERIAL_NO"));	
				actDTO.setStatus(rs.getString("STATUS"));	
				actDTO.setPrNO(rs.getString("PR_NO"));	
				actDTO.setPoNO(rs.getString("PO_NO"));	
				actDTO.setStockAcceptanceDate(rs.getDate("PO_ACCEPTANCE_DATE"));	
				actDTO.setActivationDate(rs.getDate("ASSIGN_DATE"));
				actDTO.setCustomerId(rs.getString("CUSTOMER_ID"));	
				
				reportStockDataList.add(actDTO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Activation Detail Report Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
			DBConnectionManager.releaseResources(conRDB);		
		}
		return reportStockDataList;
		
	}
	
	
}


