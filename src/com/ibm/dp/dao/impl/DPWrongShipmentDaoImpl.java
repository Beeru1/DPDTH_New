package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPWrongShipmentDao;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPWrongShipmentDaoImpl extends BaseDaoRdbms implements DPWrongShipmentDao
{
	private Logger logger = Logger.getLogger(DPWrongShipmentDaoImpl.class.getName());
	public DPWrongShipmentDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public  List<WrongShipmentDTO> getListOfRejectedDCNo(Long distId , String dc_status) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WrongShipmentDTO> invoiceList = new ArrayList<WrongShipmentDTO>();
		
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_REJECTED_INVOCES_OF_DISTRIBUTOR);
			ps.setString(1, dc_status);
			ps.setInt(2, distId.intValue());
			rs = ps.executeQuery();
			WrongShipmentDTO wrongShipmentDTO = null;
			while(rs.next())
			{
				wrongShipmentDTO = new WrongShipmentDTO();
				wrongShipmentDTO.setInvoiceNo(rs.getString("INV_NO"));
				wrongShipmentDTO.setDeliveryChallanNo(rs.getString("DC_NO"));
				invoiceList.add(wrongShipmentDTO);
			}
			connection.commit();
		}catch(SQLException sqle)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.Excess_Stock_Full_Final_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			
			}
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				try
				{
					String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PO_Stock_Acceptance_MissingDC_Critical);
					logger.info(err + "::" +Utility.getCurrentDate());
				}
				catch(Exception ex1)
				{
					ex1.printStackTrace();
				}
			
			}
			e.printStackTrace();
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PO_Stock_Acceptance_MissingDC_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
			return invoiceList;
	}
	
	public List<WrongShipmentDTO> getAllSerialNos(String invoiceNo, String dcNo, Integer intCircleID) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WrongShipmentDTO> invoiceList = new ArrayList<WrongShipmentDTO>();
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_ALL_SERIALS_NO);
			//Commented by Nazim Hussain for Serial no.s based on circle
//			ps.setString(1, invoiceNo);
//			
//			ps.setString(2, dcNo);
			
			ps.setInt(1, intCircleID);
			ps.setString(2, invoiceNo);
			ps.setString(3, dcNo);			
			rs = ps.executeQuery();
			WrongShipmentDTO wrongShipmentDTO = null;
			while(rs.next())
			{
				wrongShipmentDTO = new WrongShipmentDTO();
				wrongShipmentDTO.setSerialID(rs.getString("SERIAL_NO"));
				invoiceList.add(wrongShipmentDTO);
			}
			connection.commit();
		}catch(SQLException sqle)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception ex)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ex.printStackTrace();
			throw new DAOException("Exception: " + ex.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
			return invoiceList;
	}
	
	public List<WrongShipmentDTO> getAllProducts(String invoiceNo, String dcNo) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WrongShipmentDTO> productList = new ArrayList<WrongShipmentDTO>();
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_ALL_PRODUCT_NOS);
			ps.setString(1, invoiceNo);
			ps.setString(2, dcNo);
			rs = ps.executeQuery();
			WrongShipmentDTO wrongShipmentDTO = null;
			while(rs.next())
			{
				wrongShipmentDTO = new WrongShipmentDTO();
				wrongShipmentDTO.setProductId(rs.getString("PRODUCT_ID"));
				wrongShipmentDTO.setProductName(rs.getString("PRODUCT_NAME"));
				productList.add(wrongShipmentDTO);
			}
			
			connection.commit();
		}catch(SQLException sqle)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		return productList;
	}
	
	
	
	// Get Delivery Challan of given Invoice NO. 
	public String getDeliveryChallanNo(String invoiceNo) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String dcNo = "";
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_DCNO_ON_INVOICE_NO);
			ps.setString(1, invoiceNo);
			rs = ps.executeQuery();
			WrongShipmentDTO wrongShipmentDTO = null;
			if(rs.next())
			{
				dcNo = rs.getString("DC_NO");
			}
			connection.commit();
		}catch(SQLException sqle)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception ex)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			ex.printStackTrace();
			throw new DAOException("Exception: " + ex.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
			return dcNo;
	}
	
	public String getInvoiceNoOfDCNO(String dcNo) throws DAOException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String invoiceNo = "";
		try 
		{
			ps = connection.prepareStatement(DBQueries.GET_INVOICE_NO_ON_DC_NO);
			ps.setString(1, dcNo);
			rs = ps.executeQuery();
			WrongShipmentDTO wrongShipmentDTO = null;
			if(rs.next())
			{
				invoiceNo = rs.getString("INV_NO");
			}
			
			connection.commit();
		}catch(SQLException sqle)
		{
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqle.printStackTrace();
			throw new DAOException("SQLException: " + sqle.getMessage());
		}catch(Exception e)
		{
			try {
				connection.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			e.printStackTrace();
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return invoiceNo;
	}
		
	// Insert wrong, short and accept right serials nos.
	public boolean submitWrongShipmentDetails(String[] availableSerialsArray, String[] shortSerialsArray, String[] extraSerialsArray, String invoiceNo, String deliveryChallanNo, String distId) throws DAOException
	{
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
//		PreparedStatement pstmtUpdInvoiceDet = null;
		PreparedStatement pstmtUpdAuditTrail = null;
		
		ResultSet rs = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		boolean flag = false;
		int catCode = 0;
		int parentAccount = 0;
		
		PreparedStatement pstmtShortAlert = null;
		PreparedStatement pstmtExtraAlert = null;
		ResultSet rsShortAlert=null;
		ResultSet rsExtraAlert=null;
		String dynamicString="";
		String mobileno=null;
		try 
		{
			/* Added By Parnika for checking TSM for forwarding */
			
			ps3 = connection.prepareStatement(DBQueries.SQL_SELECT_CATEGORY_CODE);
			ps3.setString(1, invoiceNo);
			rs3 = ps3.executeQuery();
			if(rs3.next()){
				catCode = rs3.getInt("CATEGORY_CODE");
			}
			
			// CPE type PO
			if(catCode == 1){ 
				ps4 = connection.prepareStatement(DBQueries.SQL_SELECT_PARENT_TSM_DEPOSIT);				
			}
			else{
				ps4 = connection.prepareStatement(DBQueries.SQL_SELECT_PARENT__TSM_SALES);			
			}
			
			ps4.setInt(1, Integer.parseInt(distId));
			rs4 = ps4.executeQuery();
			if(rs4.next()){
				parentAccount = rs4.getInt("PARENT_ACCOUNT");
			}
			
			/* End of changes by Parnika */
			
			// Insert short shipment records into table DP_WRONG_SHIP_DETAIL  
			ps = connection.prepareStatement(DBQueries.INSERT_SHORT_SHIPMENT_RECORDS);
			pstmtUpdAuditTrail = connection.prepareStatement(DBQueries.INSERT_AUDIT_TRAIL);
			//System.out.println("deliveryChallanNo"+deliveryChallanNo);
			if(shortSerialsArray != null && shortSerialsArray.length > 0)
			{
				System.out.println("length > 0");
				for(int i = 0; i < shortSerialsArray.length; i++)
				{
					ps.setString(1, deliveryChallanNo);
					// CPE type PO
					if(catCode == 1){ 
						ps.setInt(2, 2);			
					}
					else{
						ps.setInt(2, 1);			
					}
					ps.setString(3, shortSerialsArray[i]);
				  
				   ps.addBatch();
				   //ps.execute();
				}
			}
			
			int recordsShort[]= ps.executeBatch();
			ps.clearParameters();
			logger.info("Total Records insert on short shipment " + recordsShort.length);
			
			// Insert extra shipment records into table DP_WRONG_SHIP_DETAIL  
			ps = connection.prepareStatement(DBQueries.INSERT_EXTRA_SHIPMENT_RECORDS);

			if(extraSerialsArray != null && extraSerialsArray.length > 0)
			{
				for(int i = 0; i < extraSerialsArray.length; i++)
				{
				   String serialNo = getSplitInvoiceNo(extraSerialsArray[i]);
				   String productId = getSplitProductId(extraSerialsArray[i]); 
				   ps.setString(1, serialNo);
				   ps.setInt(2, Integer.parseInt(productId));
				   ps.setInt(3, Integer.parseInt(distId));
				   ps.setString(4, invoiceNo);
				   ps.setString(5, deliveryChallanNo);
				   ps.setInt(6, parentAccount);
				   ps.setInt(7, Integer.parseInt(distId));
				   //ps.execute();
				   ps.addBatch();
				}
				int recordsExtra[]= ps.executeBatch();
				ps.clearParameters();
			}
			 	
			
			//logger.info("Total Records insert on extra shipment " + recordsExtra.length);
						
			// Change status Mark_Damage 'N' into DP_STOCK_INVENTORY for correct serials stock. 
			// Commented by Nazim Hussain against BFR-60 of CBR_CSR_20120424-XX-07575_DP-DTH Enhancements Release 2
			/*
			ps = connection.prepareStatement(DBQueries.UPDATE_CORRECT_STOCK);
			if(availableSerialsArray != null && availableSerialsArray.length > 0)
			{
				for(int i = 0; i < availableSerialsArray.length; i++)
				{
				   ps.setString(1, availableSerialsArray[i]);
				   ps.setInt(2, Integer.parseInt(distId));
				   ps.setString(3, invoiceNo);
				   ps.addBatch();
				   //ps.execute();
				}
			}
			
			int recordsCorrected[]= ps.executeBatch();
			ps.clearParameters();
			logger.info("Total Records insert on correct shipment " + recordsCorrected.length);
						
			pstmtUpdInvoiceDet = connection.prepareStatement(DBQueries.UPDATE_INVOICE_DETAILS);
			int intAccptQty = 0;
			
			ps = connection.prepareStatement(DBQueries.ACCEPTED_STOCK_DETAIL);
			ps.setInt(1, Integer.parseInt(distId));
			ps.setString(2, invoiceNo);
			rs = ps.executeQuery();
			
			int intProdID = 0;
			
			while(rs.next())
			{
				intAccptQty = rs.getInt("ACCEPTED_QTY");
				intProdID = rs.getInt("PRODUCT_ID");
				
				pstmtUpdInvoiceDet.setInt(1,intAccptQty);
				pstmtUpdInvoiceDet.setString(2,invoiceNo);
				pstmtUpdInvoiceDet.setInt(3,intProdID);
				pstmtUpdInvoiceDet.executeUpdate();
			}
			
			if (rs != null)
			{
				rs.close();
				rs = null;
			}
			*/
			
			//Update status of INVOICE_HEADER TO 'TSM_APPROVAL_PENDING'.
			 logger.info("invoiceNo .."+invoiceNo);
			ps2 = connection.prepareStatement(DBQueries.UPDATE_STATUS_TSM_PENDING);
			ps2.setString(1, invoiceNo);
			ps2.execute();
			ps2.clearParameters();
			
			//Making an enrty in PO_AUDIT_TRAIL for the PO status update
			pstmtUpdAuditTrail.setString(1, invoiceNo);
			pstmtUpdAuditTrail.setInt(2, Constants.PO_STATUS_PO_TSM_ACTION_PENDING);
			pstmtUpdAuditTrail.setString(3, invoiceNo);
			pstmtUpdAuditTrail.executeUpdate(); 
			
			//Updating PO_HEADER for TSM action Pending Status
			ps2 = connection.prepareStatement("update PO_HEADER set PO_STATUS=?, STATUS_DATE=current_timestamp where PO_NO=(select PO_NO from INVOICE_HEADER where INV_NO=?) ");
			ps2.setInt(1, Constants.PO_STATUS_PO_TSM_ACTION_PENDING);
			ps2.setString(2, invoiceNo);
			ps2.execute();
			
			ps2.clearParameters();
			String poNo=null;
			
			try{
			/**
			 * ********************************ALERT MANAGEMENT********************************
			 */
			logger.info("*********************Sending SMS alerts***********************"+distId);
			String po_no=SMSUtility.getPONo(invoiceNo, connection);
			int transactionType=SMSUtility.getTransactionType(po_no, connection);


			SMSDto sMSDto = SMSUtility.getUserDetailsasPerTransaction(distId+"", connection,transactionType);//changed by neetika
			sMSDto.setInvNumber(invoiceNo);
			sMSDto.setDeliveryChallanNo(deliveryChallanNo);
			sMSDto.setPoNo(po_no);
			sMSDto.setAlertType(Constants.CONFIRM_ID_MISSING_DC);
			String message=null;
			
			String message2=null;
			ArrayList mobileNo=new ArrayList<String>();
			if(availableSerialsArray != null && availableSerialsArray.length ==0 && extraSerialsArray != null && extraSerialsArray.length ==0)
			{
				
				logger.info("missing DC"+message);
							
			//For TSM
				
				message2=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC, sMSDto, connection,sMSDto.getTsmID());
				if(message2!=null && !message2.equalsIgnoreCase(""))
				{
					sMSDto.setMessage(message2);
					if(!sMSDto.getParentMobileNumber().equalsIgnoreCase("")) //if TSM mobile number  availablle
					{
						mobileNo.add(sMSDto.getParentMobileNumber());
					}
				}
				message =SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC, sMSDto);
				
				
				if(message != null && !message.equalsIgnoreCase(""))
				{
					
						SMSUtility.saveSMSInDBMessages(connection,mobileNo, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_MISSING_DC);
					
				}
				//	
			
			}
			else
			{
				//logger.info(sMSDto.getDynamicSMSContent());
				System.out.println("shipment error");
				String sql= "select distinct PRODUCT_CATEGORY from DP_WRONG_SHIP_DETAIL a ,DP_PRODUCT_MASTER b "
							+" 	where a.PRODUCT_ID=b.PRODUCT_ID "
							+" 	and INV_NO=? with ur";	
				
				pstmtExtraAlert=connection.prepareStatement(sql);
				pstmtExtraAlert.setString(1, invoiceNo);
				rsExtraAlert=pstmtExtraAlert.executeQuery();
				while(rsExtraAlert.next())
				{
					dynamicString = dynamicString + rsExtraAlert.getString("PRODUCT_CATEGORY")+",";
				}
				if(dynamicString != null && !dynamicString.equals("") && dynamicString.indexOf(",") > 0)
				{
					dynamicString = dynamicString.substring(0, dynamicString.length()-1);
				}
	 			DBConnectionManager.releaseResources(pstmtExtraAlert, rsExtraAlert);
				sMSDto.setDynamicSMSContent(dynamicString);
				//For TSM
				
				message2=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common. Constants.CONFIRM_ID_WRONG_SHIPMENT, sMSDto, connection,sMSDto.getTsmID());
				if(message2!=null && !message2.equalsIgnoreCase(""))
				{
					sMSDto.setMessage(message2);
					if(!sMSDto.getParentMobileNumber().equalsIgnoreCase("")) //if TSM mobile number  availablle
					{
						mobileNo.add(sMSDto.getParentMobileNumber());
					}
				}
				message =SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common. Constants.CONFIRM_ID_WRONG_SHIPMENT, sMSDto);
				
				
				if(message != null && !message.equalsIgnoreCase(""))
				{
					
						SMSUtility.saveSMSInDBMessages(connection,mobileNo, message,com.ibm.virtualization.recharge.common. Constants.CONFIRM_ID_WRONG_SHIPMENT);
					
				}
				//	
			
				
				/*
				mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(distId), Constants.CONFIRM_ID_WRONG_SHIPMENT, connection);
				if(mobileno!=null)
				{	
				String message=SMSUtility.createMessageContent(Constants.CONFIRM_ID_WRONG_SHIPMENT, sMSDto, connection);
				if(message != null && !message.equalsIgnoreCase(""))
				{
					SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getParentMobileNumber(),sMSDto.getParentMobileNumber2()}, message,Constants.CONFIRM_ID_WRONG_SHIPMENT);
				}
				}
				*/
					
			}	
			
			logger.info("*********************Sending SMS alerts ends***********************");
			
			/**
			 * ********************************ALERT MANAGEMENT ENDS********************************
			 */
			
		}
			catch(Exception exe) //try catch added by Neetika because if some exception in SMS sending occurs functionality should work as normal
			
			{
				exe.printStackTrace();
				logger.info("Exception in sending SMS while wrong shipment::  "+exe);
				logger.info("Exception in sending SMS while wrong shipment::  "+exe.getMessage());
			}
			connection.commit();
			flag=true; //Neetika added this .. there is some problm with baselining code of Release 3. found on 9 sept 2013
		}
		catch (SQLException ex) {
			flag=false;
			while (ex != null) {
				logger.error("SQL State:" + ex.getSQLState());
				logger.error("Error Code:" + ex.getErrorCode());
				logger.error("Message:" + ex.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					logger.error("Cause:" + t);
					t = t.getCause();
				}
				ex = ex.getNextException();
				ex.printStackTrace();
			}
			try {
				connection.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new DAOException("SQLException: " + ex.getMessage());
		}
		/*catch(SQLException sqle)
		{
			sqle.printStackTrace();
			try 
			{
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DAOException("SQLException: " + sqle.getMessage());
			}
			throw new DAOException("SQLException: " + sqle.getMessage());
		}*/
		catch(Exception e)
		{
			flag=false;
			e.printStackTrace();
			try 
			{
				connection.rollback();
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
				throw new DAOException("SQLException: " + ex.getMessage());
			}
			throw new DAOException("Exception: " + e.getMessage());
		}
		finally
		{
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				
				if (pstmtUpdAuditTrail != null)
					pstmtUpdAuditTrail.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error Occured in ReportDAOImpl "+e.getMessage());
			}
		}
		
		return flag;
	}
	
	// Split product from string 'INV1111#42'.
	public String getSplitProductId(String inputStr)
	{
		String productStr = "";
	
		if(!inputStr.equalsIgnoreCase(""))
		{
			int start = inputStr.indexOf("#");  
			productStr = inputStr.substring((start+1),inputStr.length());
		}
		
		return productStr;
	}
	
//	 Split invoiceNo from string 'INV1111#42'.
	public String getSplitInvoiceNo(String inputStr)
	{
		String productStr = "";
	
		if(!inputStr.equalsIgnoreCase(""))
		{
			int end = inputStr.indexOf("#");  
			productStr = inputStr.substring(0,end);
		}
		
		return productStr;
	}
	
public String checkWrongInventory(String extraSerialNo , String productID , String distId,String deliveryChallanNo)  throws VirtualizationServiceException
	{
			PreparedStatement ps = null;
			ResultSet rs = null;
			String chackInv = "";
		try 
		{
			/* get the database connection */
			
			/*ps = connection.prepareStatement(DBQueries.CHECK_WRONG_INVENTORY);
			
			ps.setString(1, extraSerialNo);
			ps.setString(2, productID);
			rs = ps.executeQuery();
			if(rs.next())
			{
				chackInv = "true";
			}*/
			 String categoryCode = Utility.getDcDetail(connection, deliveryChallanNo);
			
			String presentInDp = Utility.stbPresentinDPForPO(connection, extraSerialNo , deliveryChallanNo , categoryCode);
			if( presentInDp != null && !presentInDp.equals(""))
			{
				chackInv = presentInDp;  
			}
			else
			{
				chackInv = "true";
			}
		} 
		catch(SQLException ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.info("Method getListOfRejectedInvoices() in service.");
		}
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(ps, rs);
			DBConnectionManager.releaseResources(connection);
		}
		return chackInv;
	}
public String checkErrorDC(String dcNo)  throws DAOException
{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String checkError= "false";
	try 
	{
		/* get the database connection */
		
		ps = connection.prepareStatement(DBQueries.CHECK_ERROR_DC);
		
		ps.setString(1, dcNo);
		
		rs = ps.executeQuery();
		if(rs.next())
		{
			checkError = "true";
		}
	} 
	catch(SQLException ex)
	{
		ex.printStackTrace();
		logger.info("Method getListOfRejectedInvoices() in service.");
	}
	finally
	{
		//Releasing the database connection
		DBConnectionManager.releaseResources(ps, rs);
		DBConnectionManager.releaseResources(connection);
	}
	return checkError;
}

}