package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.CannonDPDataDAO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.exception.DAOException;

public class CannonDPDataDAOImpl extends BaseDaoRdbms implements  CannonDPDataDAO{
	protected static Logger logger = Logger
	.getLogger(CannonDPDataDAOImpl.class.getName());

	public CannonDPDataDAOImpl(Connection connection) 
	{
		super(connection);
	}
	
	@Override
	public String updateCannonData(String recId, String serialNo,
			String distOlmId, String itemCode, String assignedDate,
			String customerId, String status, String stbType,
			String transactionType, String modelManKey, String modelManKeyOld,
			String userId, String password)
			throws DAOException{
		
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	    
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement psmt1 = null;
		ResultSet rs1= null;
  		int rs,res ;	
  		String serviceMsg = null;
  		logger.info("Enter updateCannonData ----");
  		try {
  			
  			connection.setAutoCommit(false);
  			//if(assignedDate.matches("([0-1][0-9])/([0-3][0-9])/([0-9]{4}) ([0-2][0-9]):([0-5][0-9]):([0-5][0-9])")){
  			java.util.Date date  =df.parse(assignedDate);
  			String datem  =df.format(date);
  			logger.info("Enter date ----"+datem);
  			ps1 =connection.prepareStatement("SELECT * from DP_STOCK_INVENTORY where STATUS = 1 and  SERIAL_NO = " +serialNo);
  			rs1=ps1.executeQuery();
  			if(rs1.next()){
			ps=connection.prepareStatement("INSERT INTO DP_CPE_SERIAL_NUMBER_CANON (REC_ID,SERIAL_NUMBER,DIST_OLM_ID,ITEM_CODE,ASSIGN_DATE,CUSTOMER_ID,STATUS,CREATED_ON,UPDATED_ON,STB_TYPE,TRANSACTION_TYPE,MODEL_MAN_KEY,MODEL_MAN_KEY_OLD) VALUES (?,?,?,?,current Timestamp,?,?,current Timestamp,current Timestamp,?,?,?,?)");
			ps.setString(1, recId);
			ps.setString(2, serialNo);
			ps.setString(3, distOlmId);
			ps.setString(4, itemCode);
			//ps.setString(5, datem);
			ps.setString(5, customerId);
			ps.setString(6, status);
			ps.setString(7, stbType);
			ps.setString(8, transactionType);
			ps.setString(9, modelManKey);
			ps.setString(10, modelManKeyOld);
			rs= ps.executeUpdate();
				//lapuFlag=rs.getString("DD_VALUE");
			
			
			//psmt1 = connection.prepareStatement("delete from DP_STOCK_INVENTORY where SERIAL_NO =? with ur");
			//psmt1.setString(1, serialNo);
			
			//res = psmt1.executeUpdate();
			
			connection.commit();
			serviceMsg="SUCCESS";
  		}
  			else{
  				serviceMsg="Serial Number is NON SERIALIZED and not available with DP STOCK INVENTORY";
  			}
  			/*}
  			else{
  				serviceMsg="Date should be in MM/dd/yyyy format";
  			}*/
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CPE_SERIAL_NUMBER_CANON table");
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return serviceMsg;
	}

	@Override
	public String updateCannonInventory(String recordId,String defectiveSerialNo,String defectiveStbType,String newSerialNo,String newStbType,String inventoryChangeDate,String inventoryChangeType,String distributorOlmId,String status,String errorMsg,String customerId,int defectId,String modelManKey,String modelManKeyOld,String userid,String password)
			throws DAOException {
		PreparedStatement ps = null;
  		int rs ;	
  		String strServiceMsg=null;
  		SimpleDateFormat df1 = new SimpleDateFormat("MM/dd/yyyy");
  		try {
  			//if(inventoryChangeDate.matches("([0-1][0-9])/([0-3][0-9])/([0-9]{4})")){
  	  			java.util.Date date  =df1.parse(inventoryChangeDate);
  	  			String datem  =df1.format(date);
  			
  			//String stbCheck=Utility.stbPresentinDP( connection, defectiveSerialNo);
  			//logger.info("STB Check" + stbCheck);
  			/*if(stbCheck != null || !stbCheck.equals("")){
  				strServiceMsg="Serial Number already exists";
  			}
  			else{*/
			ps=connection.prepareStatement("INSERT INTO DP_CPE_INVENTORY_CHANGE(REC_ID,DEF_SERIAL_NO,DEF_STB_TYPE,NEW_SERIAL_NO,NEW_STB_TYPE,INVT_CHANGE_DT,INVT_CHANGE_TYPE,DIST_OLM_ID,STATUS,CREATED_ON,UPDATED_ON,ERROR_MSG,MODEL_MAN_KEY,MODEL_MAN_KEY_OLD,DEFECT_ID,CUSTOMER_ID) VALUES (?,?,?,?,?,current Timestamp,?,?,?,current Timestamp,current Timestamp,?,?,?,?,?)");
			ps.setString(1, recordId);
			ps.setString(2, defectiveSerialNo);
			ps.setString(3, defectiveStbType);
			ps.setString(4, newSerialNo);
			ps.setString(5, newStbType);
			//ps.setString(6, datem);
			ps.setString(6, inventoryChangeType);
			ps.setString(7, distributorOlmId);
			ps.setString(8, status);
			ps.setString(9, errorMsg);
			ps.setString(10, modelManKey);
			ps.setString(11, modelManKeyOld);
			ps.setInt(12, defectId);
			ps.setString(13, customerId);
			rs= ps.executeUpdate();
			connection.commit();
			strServiceMsg="SUCCESS";
  			//}
  			/*}
  			else{
  				strServiceMsg="Date should be in MM/dd/yyyy format";
  			}*/
		} catch (SQLException e1) {
			logger.info("Exception in executing select on DP_CPE_INVENTORY_CHANGE table");
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return strServiceMsg;
	}

}
