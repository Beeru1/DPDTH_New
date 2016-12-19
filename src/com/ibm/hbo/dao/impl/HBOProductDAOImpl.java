/*
 * Created on Nov 15, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.hbo.common.DBConnection;
import com.ibm.hbo.common.DBQueries;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.dao.HBOProductDAO;
import com.ibm.hbo.dto.HBORequisitionDTO;
import com.ibm.hbo.exception.DAOException;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOProductDAOImpl extends BaseDaoRdbms implements HBOProductDAO{
 
 private static Logger logger = Logger.getLogger(HBOProductDAOImpl.class.toString());
 protected  StringBuffer SQL_SELECT_FROM_TABLE = new StringBuffer("SELECT rh.REQUISITION_ID,rh.CREATED_DT,rh.REQUISITION_PRODUCT_CODE,rh.ASSIGNED_WAREHOUSE_ID,rh.REQUISITION_QTY,rh.FOR_MONTH,rh.UPLOADED_QUANTITY,rp.INVOICE_NO,rp.INVOICE_DT FROM REQUISITION_HEADER rh, ");
 protected  StringBuffer SQL_INSERT = new StringBuffer("insert into ");
 protected  StringBuffer SQL_INSERT_INTO_TABLE = new StringBuffer("requisition_header(REQUISITION_ID, CREATED_BY, ASSIGNED_WAREHOUSE_ID, CREATED_DT, REQUISITION_QTY, FOR_MONTH, REQUISITION_PRODUCT_CODE, UPLOADED_QUANTITY) values(REQ_SEQ.NEXTVAL,?,?,sysdate,?,?,?,?)");
 protected StringBuffer SQL_SELECT_COUNT = new StringBuffer("SELECT rp.INVOICE_NO,COUNT(rp.INVOICE_NO) FROM REQUISITION_PRODUCTS rp");
 
 public HBOProductDAOImpl(Connection con){
	 super(con);
 }
 
 public void insert(String userId,Object dto,String master,String condition) throws DAOException{
	Connection con = null;
	PreparedStatement ps = null;
	
	ResultSet rs = null;
	StringBuffer sql = null;
	
	HBORequisitionDTO hboReq = null;
	logger.info("in insert of productimpl");
	
	try {       
		
		con = DBConnection.getDBConnection();
		
		
		if(master.equals("requisition"))
		{   
			String id_val = "";
			sql = SQL_INSERT;
			sql.append(SQL_INSERT_INTO_TABLE);
			ps = con.prepareStatement(sql.toString());
		
			if(dto instanceof HBORequisitionDTO )
			{  
				hboReq = (HBORequisitionDTO) dto;
				int warehouse_to = hboReq.getWarehouse_to();
				int qtyRequisition = Integer.parseInt(hboReq.getQtyRequisition());
				int month = Integer.parseInt(hboReq.getMonth());
				//ps.setString(1,id_val);
				logger.info("userId>>>>>>>>"+userId);
				logger.info("userId>>>>>>>>"+warehouse_to);
				logger.info("userId>>>>>>>>"+qtyRequisition);
				logger.info("userId>>>>>>>>"+month);
				logger.info("userId>>>>>>>>"+hboReq.getProductCode());
				ps.setString(1,userId);
				ps.setInt(2,warehouse_to);
				ps.setInt(3,qtyRequisition);
				ps.setInt(4,month);
				ps.setString(5,hboReq.getProductCode());
				ps.setInt(6,0);
			}
		}
			
		rs=null;	
		rs = ps.executeQuery();

			} catch (SQLException e) {
				logger.info(">>>>>>>>>>>>>>>> "+e);;
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
 
/* public ArrayList getRequisitionData(int warehouseid,int roleid,String condition) throws DAOException{
		
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ArrayList requisitionList = new ArrayList();
	
		HBORequisitionDTO hboReq = null;
	try {       
			StringBuffer sql = new StringBuffer();
			StringBuffer sql1 = new StringBuffer();
			con = DBConnection.getDBConnection();
			if(roleid==1)//------ASK SIR
			{   logger.info("for warehouse>>>>>>>>>");
				SQL_SELECT_FROM_TABLE = new StringBuffer(PropertyReader.getValue("view_Requisition1"));
				SQL_SELECT_FROM_TABLE.append(PropertyReader.getValue("view_Requisition2")); 
				SQL_SELECT_FROM_TABLE.append(PropertyReader.getValue("view_Requisition3"));
				SQL_SELECT_FROM_TABLE.append(PropertyReader.getValue("view_Requisition4"));
				SQL_SELECT_FROM_TABLE.append(PropertyReader.getValue("view_Requisition5"));
				SQL_SELECT_FROM_TABLE.append(PropertyReader.getValue("view_Requisition6"));
				   
				ps = con.prepareStatement(SQL_SELECT_FROM_TABLE.toString());		
				logger.info(sql1.toString());
				rs = ps.executeQuery();
			}
			else{
				SQL_SELECT_FROM_TABLE = new StringBuffer("SELECT REQUISITION_ID,CREATED_DT,MODEL_CODE,REQUISITION_PRODUCT_CODE, "); 
				SQL_SELECT_FROM_TABLE.append(" WAREHOUSE_NAME,REQUISITION_QTY,FOR_MONTH, "); 
				SQL_SELECT_FROM_TABLE.append(" INVOICE_NO,INVOICE_DT,count(imei_no) UPLOADED_QUANTITY, nvl(uploaded_quantity,0) SUMUPLOADED_QTY ");  
				SQL_SELECT_FROM_TABLE.append(" from( SELECT rh.REQUISITION_ID,to_char(rh.CREATED_DT,'dd-Mon-yyyy')CREATED_DT,pm.MODEL_CODE,rh.REQUISITION_PRODUCT_CODE, "); 
				SQL_SELECT_FROM_TABLE.append(" wm.WAREHOUSE_NAME,rh.REQUISITION_QTY,rh.FOR_MONTH,  ");
				SQL_SELECT_FROM_TABLE.append(" rp.INVOICE_NO,to_char(rp.INVOICE_DT,'dd-Mon-yyyy') INVOICE_DT,rp.imei_no, rh.uploaded_quantity  ");  
				SQL_SELECT_FROM_TABLE.append(" FROM REQUISITION_HEADER rh, REQUISITION_PRODUCTS rp, PRODUCT_MASTER pm, WAREHOUSE_MASTER wm where rh.REQUISITION_ID = rp.REQUISITION_ID(+)  ");  
				SQL_SELECT_FROM_TABLE.append(" AND rh.ASSIGNED_WAREHOUSE_ID = ? AND rh.ASSIGNED_WAREHOUSE_ID = wm.WAREHOUSE_ID  "); 
				SQL_SELECT_FROM_TABLE.append(" AND rh.REQUISITION_PRODUCT_CODE = pm.PRODUCT_CODE ) ");
				SQL_SELECT_FROM_TABLE.append(" group by WAREHOUSE_NAME,FOR_MONTH,REQUISITION_ID,CREATED_DT,MODEL_CODE,REQUISITION_PRODUCT_CODE, ");  
				SQL_SELECT_FROM_TABLE.append(" REQUISITION_QTY,INVOICE_NO,INVOICE_DT,nvl(uploaded_quantity,0)   ");
				SQL_SELECT_FROM_TABLE.append(" order by REQUISITION_ID desc ");
				ps = con.prepareStatement(SQL_SELECT_FROM_TABLE.toString());
				ps.setInt(1,warehouseid);
				rs = ps.executeQuery();
			}
			 logger.info("///////////////"+SQL_SELECT_FROM_TABLE.toString());
				
				//rs1 = ps1.executeQuery();
				while(rs.next()){
					HBORequisitionDTO reqDTO = new HBORequisitionDTO(); 
					reqDTO.setRequisitionId(rs.getInt("REQUISITION_ID"));
					reqDTO.setRequisitionDate(rs.getString("CREATED_DT"));
					reqDTO.setProductCode(rs.getString("REQUISITION_PRODUCT_CODE"));
					reqDTO.setModelCode(rs.getString("MODEL_CODE"));
					reqDTO.setQtyRequisition(rs.getString("REQUISITION_QTY"));
					reqDTO.setWarehouse_to_name(rs.getString("WAREHOUSE_NAME"));
					reqDTO.setMonth(getMonthName(rs.getInt("FOR_MONTH")));
					reqDTO.setQtyuploaded(rs.getInt("UPLOADED_QUANTITY"));
					if(roleid!=1){
					reqDTO.setInvoice_no(rs.getString("INVOICE_NO"));
					reqDTO.setInvoice_dt(rs.getString("INVOICE_DT"));
					}
					reqDTO.setSumqtyuploaded(rs.getInt("SUMUPLOADED_QTY"));
					logger.info("$$$$$$$$$$$$$$$$$$$Date"+reqDTO.getRequisitionDate());
					
					requisitionList.add(reqDTO);
					
				}
		logger.info("sql>>>>>>>>"+sql.toString());
	return requisitionList;
 	
 	
 }
 catch (SQLException e) {
			 logger.info(">>>>>>>>>>>>>>>> "+e);;
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
}*/
  public String insert(ArrayList params) throws DAOException{
		//Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = null;
		HBORequisitionDTO hboReq = null;
		String message = HBOConstants.SUCCESS_MESSAGE;
		try {       
			//con = DBConnectionManager.getDBConnection();
			sql = new StringBuffer(DBQueries.insertRequisition);
			sql.append(HBOConstants.WITH_UR);
			ps = connection.prepareStatement(sql.toString());
			hboReq = (HBORequisitionDTO) (params.get(1));
			ps.setInt(1,(Integer)(params.get(0)));
			ps.setInt(2,hboReq.getWarehouse_to());
			ps.setInt(3,Integer.parseInt(hboReq.getQtyRequisition()));
			ps.setInt(4,Integer.parseInt(hboReq.getMonth()));
			ps.setString(5,hboReq.getProductCode());
			ps.setInt(6,0);
			int i=ps.executeUpdate();
			connection.commit();
    		} catch (SQLException e) {
    			message = HBOConstants.ERROR_MESSAGE;
  					logger.error(
						"SQL Exception occured while inserting." + "Exception Message: " + e.getMessage());
					throw new DAOException("SQLException: " + e.getMessage(), e);
				} catch (Exception e) {
					message = HBOConstants.ERROR_MESSAGE;
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
						if (connection != null) {
							connection.close();
						}
					} catch (Exception e) {
						message = HBOConstants.ERROR_MESSAGE;
					}
				}
			return message;
	 }
  public ArrayList getRequisitionData(ArrayList params) throws DAOException{
		
		//Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList requisitionList = new ArrayList();
	    try {   
	    	ArrayList roleList = (ArrayList)params.get(1);
			StringBuffer sql;
			connection = DBConnectionManager.getDBConnection();
			if(roleList.contains(HBOConstants.ROLE_NATIONALDIST))
			{   
				sql = new StringBuffer(DBQueries.viewRequisitionND);
				sql.append(HBOConstants.WITH_UR);
				ps = connection.prepareStatement(sql.toString());
				ps.setInt(1,(Integer)params.get(0));
				rs = ps.executeQuery();
			}
			else{
				sql = new StringBuffer(DBQueries.viewRequisitionMD);
				sql.append(HBOConstants.WITH_UR);
				ps = connection.prepareStatement(sql.toString());
				rs = ps.executeQuery();	
			}
				while(rs.next()){
					HBORequisitionDTO reqDTO = new HBORequisitionDTO(); 
					reqDTO.setRequisitionId(rs.getInt("REQUISITION_ID"));
					reqDTO.setRequisitionDate(rs.getString("CREATED_DT"));
					reqDTO.setProductCode(rs.getString("REQUISITION_PRODUCT_CODE"));
					reqDTO.setModelCode(rs.getString("PRODUCT_NAME"));
					reqDTO.setQtyRequisition(rs.getString("REQUISITION_QTY"));
					reqDTO.setWarehouse_to_name(rs.getString("ACCOUNT_NAME"));
					reqDTO.setMonth(getMonthName(rs.getInt("FOR_MONTH")));
					reqDTO.setQtyuploaded(rs.getInt("UPLOADED_QUANTITY"));
					if(roleList.contains("ROLE_ND")){
						reqDTO.setInvoice_no(rs.getString("INVOICE_NO"));
						reqDTO.setInvoice_dt(rs.getString("INVOICE_DT"));
					}
					reqDTO.setSumqtyuploaded(rs.getInt("SUMUPLOADED_QTY"));
					requisitionList.add(reqDTO);
				}
	return requisitionList;
	
	
}
catch (SQLException e) {
			 logger.info(">>>>>>>>>>>>>>>> "+e);;
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
				 if (connection != null) {
					 connection.close();
				 }
			 } catch (Exception e) {
			 }
		 }
}
 public String getMonthName(int monthId){ 
	String[] month = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	 return month[monthId-1];
}
}