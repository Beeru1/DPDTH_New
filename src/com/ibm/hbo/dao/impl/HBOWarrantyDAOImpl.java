package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.dao.HBOWarrantyDAO;
import com.ibm.hbo.dto.HBOProductDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOWarrantyDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.forms.HBOBundleStockFormBean;
import com.ibm.hbo.forms.HBOBusinessCategoryFormBean;
import com.ibm.hbo.forms.HBOProductFormBean;
import com.ibm.hbo.forms.HBOWarehouseFormBean;
import com.ibm.hbo.forms.HBOWarrantyFormBean;

public class HBOWarrantyDAOImpl implements HBOWarrantyDAO {

	/** 
		* Logger for this class. Use logger.log(message) for logging. Refer to @link http://java.sun.com/j2se/1.4.2/docs/guide/util/logging/overview.html for logging options and configuration.
	**/

	private static Logger logger = Logger.getLogger(HBOWarrantyDAOImpl.class.toString());
	
	
	

	public ArrayList findByPrimaryKey(String userId,String master,String imeino,String condition) throws DAOException {
		logger.info("Get Master Data for "+ master);

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT RP.IMEI_NO,RP.LAST_WAREHOUSE_ID,RP.DAMAGE_FLAG,  PM.PRODUCT_WARRANTY, RP.EXTENDED_WARRANTY,  RH.REQUISITION_PRODUCT_CODE, ");	
	   sql.append(" WAR.WAREHOUSE_NAME FROM  REQUISITION_PRODUCTS RP,  REQUISITION_HEADER RH,  WAREHOUSE_MASTER WAR, PRODUCT_MASTER PM, USER_MASTER um  ");
	   sql.append(" WHERE RH.REQUISITION_ID = RP.REQUISITION_ID AND RP.LAST_WAREHOUSE_ID = WAR.WAREHOUSE_ID AND  RP.IMEI_NO = ? ");
	   sql.append(" AND RH.REQUISITION_PRODUCT_CODE = PM.PRODUCT_CODE and RP.LAST_WAREHOUSE_ID =um.WAREHOUSE_ID and um.USER_LOGIN_ID = '");
	   sql.append(userId);
	   sql.append("'");


		if(condition != null && !condition.equals("")){
			sql.append(condition);
			logger.info(sql.toString());
			
		}
		logger.info("reached here in DAO");
		logger.info(sql.toString());
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1,imeino);
			rs = ps.executeQuery();
			return fetchSingleResult(rs,master);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
	
	protected ArrayList fetchSingleResult(ResultSet rs,String master) throws SQLException {
		HBOWarrantyDTO warrantyDTO = null;
		
	
		
		ArrayList arrMasterData = new ArrayList();
		int i= 0;
		logger.info("i:"+i+master);
		
	
		while (master.equals("warranty") && rs.next()) {
			logger.info("warranty:"+i); i++;
			warrantyDTO = new HBOWarrantyDTO();
			
			warrantyDTO.setImeino(rs.getString("IMEI_NO"));
			warrantyDTO.setWarehouseId(rs.getInt("LAST_WAREHOUSE_ID"));
			warrantyDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
			warrantyDTO.setDamageStatus(rs.getString("DAMAGE_FLAG"));
			warrantyDTO.setStWarranty(rs.getString("PRODUCT_WARRANTY"));
			warrantyDTO.setPrCode(rs.getString("REQUISITION_PRODUCT_CODE"));
			warrantyDTO.setExtWarranty(rs.getInt("EXTENDED_WARRANTY"));
			/*
			if(rs.getString("ACTIVE_FLAG").equals("A")){
				wareHouseMasterDTO.setStatus("Active"); 	
			}
			else{
				wareHouseMasterDTO.setStatus("DeActive"); 
			}
			*/
			
			
			arrMasterData.add(warrantyDTO); 
		} 
		
		
		return arrMasterData;
	}
	
 
	public void insert(String userId,Object formBean,String master,String condition)throws DAOException {
		logger.info("Insert Master Data for "+ master);
		logger.info("Insert Master Data for "+ master);
		HBOWarrantyFormBean warrantyFormBean = null;
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("");
		StringBuffer SQL_UPDATE_REQUISITION_PRODUCTS=new  StringBuffer("");
		
		ArrayList arrPassParameters = new ArrayList();
		String transaction = "";
		// Queries for EXTENDED WARRANTY UPDATE IN REQUISITION_PRODUCTS
		
		SQL_UPDATE_REQUISITION_PRODUCTS.append("UPDATE REQUISITION_PRODUCTS SET EXTENDED_WARRANTY = ? WHERE IMEI_NO=?"); 
        
		logger.info("reached after query");
		
		if (master.equalsIgnoreCase("warranty")) {
			warrantyFormBean = (HBOWarrantyFormBean) formBean;
			String imeiNo = warrantyFormBean.getImeino();
			int extWarranty=warrantyFormBean.getExtWarranty();
			logger.info("Value of imeiNo==="+imeiNo);
			if(imeiNo != "" && imeiNo !=null) {
			sql.append(SQL_UPDATE_REQUISITION_PRODUCTS);
			transaction = "update";
			logger.info("in update dear");
			}
			
		}
	logger.info(sql.toString());
		try {
			con = DBConnection.getDBConnection();
			ps = con.prepareStatement(sql.toString());
			if (master.equalsIgnoreCase("warranty")) {
 					int pup=1;
 					ps.setInt(pup++,warrantyFormBean.getExtWarranty());
					ps.setString(pup++,warrantyFormBean.getImeino());
 					
 				 					 
 				}
			
			ps.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(
				"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("SQLException: " + e.getMessage(), e);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
				"Exception occured while inserting." + "Exception Message: " + e.getMessage());
			throw new DAOException("Exception: " + e.getMessage(), e);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
	

