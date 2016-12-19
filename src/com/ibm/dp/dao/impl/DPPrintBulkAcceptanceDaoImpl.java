package com.ibm.dp.dao.impl;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.ibm.dp.dao.DPPrintBulkAcceptanceDao;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.dp.dto.DPPrintBulkAcceptanceDTO;
import com.ibm.dpmisreports.common.SelectionCollection;

public class DPPrintBulkAcceptanceDaoImpl extends BaseDaoRdbms  implements DPPrintBulkAcceptanceDao{

	private static Logger logger = Logger.getLogger(DPPrintBulkAcceptanceDaoImpl.class);	
	private static DPPrintBulkAcceptanceDao printBulkAcceptanceDao = new DPPrintBulkAcceptanceDaoImpl();
	//private GenericReportsDaoImpl(){}
	public static DPPrintBulkAcceptanceDao getInstance() {
		return printBulkAcceptanceDao;
	}
	
	public int getInvoiceQty(String invoice_no){
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		int invoiceQty=0;
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select INV_QTY from invoice_details where inv_no=? " ;
			
			
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoice_no);
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			
			
			while (rs.next()) {
				invoiceQty=rs.getInt("INV_QTY");
			
			}			
			
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return invoiceQty;	
		
	}
	

	public List<DPPrintBulkAcceptanceDTO> getCorrectSTB(String dist_id, String invoiceNo) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<DPPrintBulkAcceptanceDTO> listReturn=new ArrayList<DPPrintBulkAcceptanceDTO>();
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select SERIAL_NO,(Select product_name from dp_product_master where PRODUCT_ID=DS.PRODUCT_ID ) "+
						  " from DP_STOCK_INVENTORY DS where  INV_NO=?  and DISTRIBUTOR_ID=?  and SERIAL_NO not in (select serial_no  from dp_wrong_ship_detail "+
							" dpw where   inv_no=? and DISTRIBUTOR_ID =?  and WRONG_SHIP_TYPE = 'SHORT' )" ;
			
			
			
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoiceNo);
			ps.setInt(2,Integer.valueOf(dist_id));
			ps.setString(3, invoiceNo);
			ps.setInt(4,Integer.valueOf(dist_id));
			//ps.setInt(1,Integer.valueOf(productId));
			
			System.out.println(sql);
			rs = ps.executeQuery();
			
			DPPrintBulkAcceptanceDTO printDTO = null;
			while(rs.next())
			{
				
				printDTO = new DPPrintBulkAcceptanceDTO();
				printDTO.setSerial_no(rs.getString("SERIAL_NO"));
				printDTO.setProduct_name(rs.getString("product_name"));
				listReturn.add(printDTO);
			}
				
			
					
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return listReturn;
	}
	

	public List<DPPrintBulkAcceptanceDTO> getWrongShippedSTB(String dist_id, String invoiceNo) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<DPPrintBulkAcceptanceDTO> listReturn= new ArrayList<DPPrintBulkAcceptanceDTO>();
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select serial_no ,(select product_name from dp_product_master where product_id=dpw.product_id ) from dp_wrong_ship_detail "+
							" dpw where  inv_no=? and DISTRIBUTOR_ID =?  and WRONG_SHIP_TYPE = 'WRONG'";
			

			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoiceNo);
			ps.setInt(2,Integer.valueOf(dist_id));
			//ps.setInt(1,Integer.valueOf(productId));
			
			System.out.println(sql);
			rs = ps.executeQuery();
			
			DPPrintBulkAcceptanceDTO printDTO = null;
			while(rs.next())
			{
				
				printDTO = new DPPrintBulkAcceptanceDTO();
				printDTO.setSerial_no(rs.getString("SERIAL_NO"));
				printDTO.setProduct_name(rs.getString("product_name"));
				listReturn.add(printDTO);
			}
				
			
					
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return listReturn;
	}
	
	public List<DPPrintBulkAcceptanceDTO> getMissingSTB(String dist_id, String invoiceNo) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		List<DPPrintBulkAcceptanceDTO> listReturn=new ArrayList<DPPrintBulkAcceptanceDTO>();
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select serial_no ,(select product_name from dp_product_master where product_id=dpw.product_id ) from dp_wrong_ship_detail "+
							" dpw where   inv_no=? and DISTRIBUTOR_ID =?  and WRONG_SHIP_TYPE = 'SHORT' ";
			

			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoiceNo);
			ps.setInt(2,Integer.valueOf(dist_id));
			//ps.setInt(1,Integer.valueOf(productId));
			
			System.out.println(sql);
			rs = ps.executeQuery();
			
			DPPrintBulkAcceptanceDTO printDTO = null;
			while(rs.next())
			{
				
				printDTO = new DPPrintBulkAcceptanceDTO();
				printDTO.setSerial_no(rs.getString("SERIAL_NO"));
				printDTO.setProduct_name(rs.getString("product_name"));
				listReturn.add(printDTO);
			}
				
			
					
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return listReturn;
	}

	public String getPONo(String invoice_no) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		String po_no="";
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select po_no from invoice_header ih where inv_no=?" ;
			
			
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoice_no);
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			
			
			while (rs.next()) {
				po_no=rs.getString("po_no");
			
			}			
			
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return po_no;	
	}

	public String getPOQty(String invoice_no) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		Connection db2conn = null; 
		ResultSet rs = null; 
		int po_qty=0;
		try {
			db2conn = DBConnectionManager.getDBConnection();
								
			String sql = "select po_qty from PO_DETAILS where PO_NO in (select po_no from invoice_header  where inv_no=?)" ;
			
			
			ps = db2conn.prepareStatement(sql);
			ps.setString(1, invoice_no);
			System.out.println(sql);
			
			rs = ps.executeQuery();
			
			
			
			while (rs.next()) {
				po_qty=rs.getInt("po_qty");
			
			}			
			
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception occured while getting Reco Details. Exception message: "
					+ e.getMessage());
		} finally {
			DBConnectionManager.releaseResources(ps ,rs );
		DBConnectionManager.releaseResources(db2conn);
		}
		return String.valueOf(po_qty);
	}
	
	
}
