package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.SerializedStockReportDao;
import com.ibm.dp.dto.SerializedStockReportDTO;

 
public class SerializedStockReportDaoImpl implements  SerializedStockReportDao{
	
	private static Logger logger = Logger.getLogger(SerializedStockReportDaoImpl.class);	
	private static SerializedStockReportDao  serializedStockReportDao  = new SerializedStockReportDaoImpl();
	private SerializedStockReportDaoImpl(){}
	public static SerializedStockReportDao getInstance() {
		return serializedStockReportDao;
	}
	public List<SerializedStockReportDTO> getSerializedStockReport(String circleId,String tsmId, String distId, String fseId, String retId, String productId, String date) {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
	
		List<SerializedStockReportDTO> reportStockDataList= new ArrayList<SerializedStockReportDTO>();
		
		try {/*
			
			String sql = "Select CASE CREATED_BY WHEN 0 then 'SYSTEM GENERATED' ELSE (SELECT ACCOUNT_NAME FROM VR_ACCOUNT_DETAILS where ACCOUNT_ID=RD.DIST_ID)" +
					" END AS TYPE, (SELECT PRODUCT_NAME from DP_PRODUCT_MASTER WHERE PRODUCT_ID=RD.PRODUCT_ID ) AS PRODUCT_NAME, " +
					"(RD.SER_OPEN_STOCK + RD.NSER_OPEN_STOCK) AS OPEN_STOCK, RECEIVED_WH, RECEIVED_INTRANSIT, RECEIVED_INTER_SSD, RECEIVED_UPGRADE, RECEIVED_CHURN," +
					"RETURNED_FRESH, RETURNED_INTRANSIT, RETURNED_INTER_SSD, RETURNED_DOA_BI, RETURNED_DOA_AI, RETURNED_SWAP, RETURNED_C2S," +
					"( SER_ACTIVATION + NSER_ACTIVATION) AS ACTIVATION, CLOSING_STOCK from DP_RECO_DIST_DETAILS RD where RECO_ID=?  " ;
			
			if(circleId != null && !circleId.equals("")) {		
				if(!circleId.contains(",")){
					sql+= " AND CIRCLE_ID = "+ circleId;
				}else{					
					sql+= " AND CIRCLE_ID IN("+ circleId +")";
				}
				
			}
			if(productIdStr != null && !productIdStr.equals("")) {		
				if(!productIdStr.contains(",")){
					sql+= " AND PRODUCT_ID = "+ productIdStr;
				}else{					
					sql+= " AND PRODUCT_ID IN("+ productIdStr +")";
				}
				
			}			
			db2conn = DBConnectionManager.getDBConnection();
			ps = db2conn.prepareStatement(sql);
			ps.setInt(1, recoID);
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				RecoDetailReportDTO recDTO = new RecoDetailReportDTO();
				
				recDTO.setType(rs.getString("TYPE"));
				recDTO.setProductType(rs.getString("PRODUCT_NAME"));
				recDTO.setOpeningStock(rs.getInt("OPEN_STOCK"));
				recDTO.setReceivedVH(rs.getInt("RECEIVED_WH"));
				recDTO.setReceivedIntransit(rs.getInt("RECEIVED_INTRANSIT"));
				recDTO.setReceivedInterSSD(rs.getInt("RECEIVED_INTER_SSD"));
				recDTO.setReceivedUpgrade(rs.getInt("RECEIVED_UPGRADE"));
				recDTO.setReceivedChurn(rs.getInt("RECEIVED_CHURN"));
				
				recDTO.setReturnedFresh(rs.getInt("RETURNED_FRESH"));
				recDTO.setReturnedInTransit(rs.getInt("RETURNED_INTRANSIT"));
				recDTO.setReturnedInterSSD(rs.getInt("RETURNED_INTER_SSD"));
				recDTO.setReturnedDOABI(rs.getInt("RETURNED_DOA_BI"));
				recDTO.setReturnedDOAAI(rs.getInt("RETURNED_DOA_AI"));
				recDTO.setReturnedSwap(rs.getInt("RETURNED_SWAP"));
				recDTO.setReturnedC2C(rs.getInt("RETURNED_C2S"));
				
				recDTO.setTotalActivation(rs.getInt("ACTIVATION"));
				recDTO.setClosingStock(rs.getInt("CLOSING_STOCK"));
				recoList.add(recDTO);
			}
			
		*/} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Serialized Stock As On Date Report Details. Exception message: "
					+ e.getMessage());
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
			} catch (Exception e) {
			}
		}
		return reportStockDataList;
		
	}
	
	
}


