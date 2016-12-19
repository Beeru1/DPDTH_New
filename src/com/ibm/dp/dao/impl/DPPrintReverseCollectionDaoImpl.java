package com.ibm.dp.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPPrintBulkAcceptanceDao;
import com.ibm.dp.dao.DPPrintReverseCollectionDao;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class DPPrintReverseCollectionDaoImpl extends BaseDaoRdbms  implements DPPrintReverseCollectionDao{

	//private static Logger logger = Logger.getLogger(DPPrintBulkAcceptanceDaoImpl.class);	
	private static DPPrintReverseCollectionDao printReverseCollectionDao = new DPPrintReverseCollectionDaoImpl();
	//private GenericReportsDaoImpl(){}
	public static DPPrintReverseCollectionDao getInstance() {
		return printReverseCollectionDao;
	}
	


	public String getProductName(String productId) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		String productName="";
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select product_name from dp_product_master where product_id=? " ;
			
			
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, productId);
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			
			
			while (rs.next()) {
				productName=rs.getString("product_name");
			
			}			
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		/*	logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		*/} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return productName;	
	}
	
}
