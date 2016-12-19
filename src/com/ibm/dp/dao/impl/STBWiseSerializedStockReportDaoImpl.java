package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.STBWiseSerializedStockReportDao;
import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.common.DropDownUtility;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

 
public class STBWiseSerializedStockReportDaoImpl implements  STBWiseSerializedStockReportDao{
	
	private static Logger logger = Logger.getLogger(STBWiseSerializedStockReportDaoImpl.class);	
	private static STBWiseSerializedStockReportDao  stbWiseSerializedStockReportDao  = new STBWiseSerializedStockReportDaoImpl();
	private STBWiseSerializedStockReportDaoImpl(){}
	public static STBWiseSerializedStockReportDao getInstance() {
		return stbWiseSerializedStockReportDao;
	}
	/**
	 * 
	 */
	public List<STBWiseSerializedStockReportDTO> getSTBWiseSerializedStockReport(STBWiseSerializedStockReportDTO stDTO) throws Exception {
		PreparedStatement ps = null;
		Connection db2conn = null; 
		Connection db2conn_rdb = null; 
		ResultSet rs = null; 
	
		List<STBWiseSerializedStockReportDTO> reportStockDataList= new ArrayList<STBWiseSerializedStockReportDTO>();
		StringBuffer sql_suffix=new StringBuffer();
		String sql_final="";
		STBWiseSerializedStockReportDTO objSTBWiseSerializedStockReportDTO=null;
		String product_ids="";
		try {
			
			// for distributors
			if(stDTO.getAccount_level().equals(Constants.ACC_LEVEL_DIST+""))
			{
				db2conn = DBConnectionManager.getDBConnection();
				
				if(!stDTO.getDistIds().equals("-1")){
					sql_suffix.append(" and a.DISTRIBUTOR_ID in ("+stDTO.getLogin_id()+")");
				} if(!stDTO.getFseID().equals("-1") && stDTO.getFseID() != null && !stDTO.getFseID().equals("")) {
					//sql_suffix.append(" and a.FSE_ID in ("+stDTO.getFseID()+")");
					sql_suffix.append(" and ( a.FSE_ID in ("+stDTO.getFseID()+") or a.FSE_ID is null )");
				} if(!stDTO.getRetIds().equals("-1") && stDTO.getRetIds() != null && !stDTO.getRetIds().equals("")){
					sql_suffix.append(" and ( a.RETAILER_ID in ("+stDTO.getRetIds()+") or a.RETAILER_ID is null )");
				} if(!stDTO.getStbStatus().equals("-1")){
					sql_suffix.append(" and a.STATUS in ("+stDTO.getStbStatus()+")");
				}
				if(!stDTO.getProductId().equals("-1")){
					product_ids=DropDownUtility.getInstance().getProductIds(db2conn, stDTO.getProductId(),stDTO.getCircle() );
					sql_suffix.append(" and f.PRODUCT_ID in ("+product_ids+")");
				}
				
				sql_final = DBQueries.SQL_STB_SERIALZED_STOCK_PART1 + sql_suffix.toString() + " UNION " +
							DBQueries.SQL_STB_SERIALZED_STOCK_PART2 + sql_suffix.toString() + " UNION " +
							DBQueries.SQL_STB_SERIALZED_STOCK_PART3 + sql_suffix.toString() + " UNION " +
							DBQueries.SQL_STB_SERIALZED_STOCK_PART4 + sql_suffix.toString() +" order by ACCOUNT_NAME,FSE_ID,RETAILER_ID,STATUS_DESCRIPTION,PRODUCT_NAME WITH UR ";
				logger.info(sql_suffix);
				logger.info(sql_final);
				
				ps = db2conn.prepareStatement(sql_final);
			}
			else // for other users like TSM,circle admin,zbm/zsm,dthadmin
			{
				db2conn_rdb = DBConnectionManager.getMISReportDBConnection();
				sql_suffix=new StringBuffer();
				if(!stDTO.getDistIds().equals("-1")){
					sql_suffix.append(" and e.DISTRIBUTOR_ID in ("+stDTO.getDistIds()+")");
				} if(!stDTO.getStbStatus().equals("-1")){
					sql_suffix.append(" and e.STATUS in ("+stDTO.getStbStatus()+")");
				} if(!stDTO.getProductId().equals("-1")){
					if(stDTO.getAccount_level().equals(Constants.ADMIN_GROUP_ID+""))
					{
						product_ids=DropDownUtility.getInstance().getProductIds(db2conn_rdb, stDTO.getProductId(),stDTO.getCircleIds() );
						sql_suffix.append(" and f.PRODUCT_ID in ("+product_ids+")");
					}
					else 
					{
						product_ids=DropDownUtility.getInstance().getProductIds(db2conn_rdb, stDTO.getProductId(),stDTO.getCircle() );
						sql_suffix.append(" and f.PRODUCT_ID in ("+product_ids+")");
					}
				}
				logger.info("Suffix::"+sql_suffix);
				sql_final = DBQueries.SQL_STB_SERIALIZED_STOCK_ALL + sql_suffix.toString() + " WITH UR";
				
				logger.info(sql_final);
				ps = db2conn_rdb.prepareStatement(sql_final);
			}
				rs = ps.executeQuery();
				int i =1 ;
				while(rs.next())
				{
					objSTBWiseSerializedStockReportDTO = new STBWiseSerializedStockReportDTO();
					objSTBWiseSerializedStockReportDTO.setSNO(i+"");
					objSTBWiseSerializedStockReportDTO.setDistName(rs.getString("ACCOUNT_NAME"));
					objSTBWiseSerializedStockReportDTO.setDist_login(rs.getString("LOGIN_NAME"));
					objSTBWiseSerializedStockReportDTO.setTsmName(rs.getString("TSM_NAME"));
					objSTBWiseSerializedStockReportDTO.setCircle(rs.getString("CIRCLE_NAME"));
					objSTBWiseSerializedStockReportDTO.setProduct_name(rs.getString("PRODUCT_NAME"));
					objSTBWiseSerializedStockReportDTO.setStbSerialNo(rs.getString("SERIAL_NO"));
					objSTBWiseSerializedStockReportDTO.setStbStatus(rs.getString("STATUS_DESCRIPTION"));
					objSTBWiseSerializedStockReportDTO.setPRNO(rs.getString("PR_NO"));
					objSTBWiseSerializedStockReportDTO.setPONO(rs.getString("PO_NO"));
					objSTBWiseSerializedStockReportDTO.setStockAcceptanceDate(rs.getString("DISTRIBUTOR_PURCHASE_DATE"));
					objSTBWiseSerializedStockReportDTO.setFseID(rs.getString("FSE_ID"));
					objSTBWiseSerializedStockReportDTO.setFseName(rs.getString("FSE_NAME"));
					objSTBWiseSerializedStockReportDTO.setFseAllocationDate(rs.getString("FSE_PURCHASE_DATE"));
					objSTBWiseSerializedStockReportDTO.setRetailerID(rs.getString("RETAILER_ID"));
					objSTBWiseSerializedStockReportDTO.setRetailer_name(rs.getString("RET_NAME"));
					objSTBWiseSerializedStockReportDTO.setRetailerAllocationDate(rs.getString("RETAILER_PURCHASE_DATE"));
					
					reportStockDataList.add(objSTBWiseSerializedStockReportDTO);
					objSTBWiseSerializedStockReportDTO=null;
					i++;
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Serialized Stock As On Date Report Details. Exception message: "
					+ e.getMessage());
			throw new Exception(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (db2conn != null) {
					db2conn.close();
				}
				if (db2conn_rdb != null) {
					db2conn_rdb.close();
				}
			} catch (Exception e) {
			}
		}
		return reportStockDataList;
		
	}
	public List<SelectionCollection> getSTBStatusList() throws DPServiceException
	{
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
			List<SelectionCollection> stbStatusList = new ArrayList<SelectionCollection>();
			
			try {
				String sql = "select STATUS_ID, STATUS_DESCRIPTION from DP_CPE_STATUS_MASTER " +
						"where STATUS_ID>0 and STATUS_ID!=4 order by STATUS_DESCRIPTION with ur";
				db2conn = DBConnectionManager.getDBConnection();
			    ps = db2conn.prepareStatement(sql);				
				logger.info(sql);			
				rs = ps.executeQuery();
				while (rs.next()) {
					SelectionCollection selCol = new SelectionCollection();
					selCol.setStrValue(rs.getString(1));
					selCol.setStrText(rs.getString(2));
					
					stbStatusList.add(selCol);
				}
			} 
			catch (Exception e) {		
				logger.error("Exception occured in Generating Reco Detail in Service Impl:" + e.getMessage());
				throw new DPServiceException(e);
			}
		return stbStatusList;
	}
}


