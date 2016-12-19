package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPMissingStockApprovalDao;
import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.dto.DPMissingStockApprovalDTO;
import com.ibm.dp.dto.DPPurchaseOrderDTO;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPMissingStockApprovalDaoImpl extends BaseDaoRdbms implements DPMissingStockApprovalDao 
{
	private static Logger logger = Logger.getLogger(DPMissingStockApprovalDaoImpl.class.getName());
	
	protected static final String SQL_MISSING_STOCK_INIT_KEY = "SQL_MISSING_STOCK_INIT_KEY";
	protected static final String SQL_MISSING_STOCK_INIT = DBQueries.SQL_MISSING_STOCK_INIT;
	
	protected static final String SQL_STOCK_STATUS_LIST_KEY = "SQL_STOCK_STATUS_LIST_KEY";
	protected static final String SQL_STOCK_STATUS_LIST = DBQueries.SQL_STOCK_STATUS_LIST;
	
	protected static final String SQL_WRONG_SHIP_DETAIL_UPD_KEY = "SQL_WRONG_SHIP_DETAIL_UPD_KEY";
	protected static final String SQL_WRONG_SHIP_DETAIL_UPD = DBQueries.SQL_WRONG_SHIP_DETAIL_UPD;

	protected static final String SQL_STOCK_INVENT_UPD_KEY = "SQL_STOCK_INVENT_UPD_KEY";
	protected static final String SQL_STOCK_INVENT_UPD = DBQueries.SQL_STOCK_INVENT_UPD;
	
	protected static final String SQL_STOCK_INVENT_DELETE_KEY = "SQL_STOCK_INVENT_DELETE_KEY";
	protected static final String SQL_STOCK_INVENT_DELETE = DBQueries.SQL_STOCK_INVENT_DELETE;
	
	protected static final String SQL_STOCK_INVENT_INSERT_KEY = "SQL_STOCK_INVENT_INSERT_KEY";
	protected static final String SQL_STOCK_INVENT_INSERT = DBQueries.SQL_STOCK_INVENT_INSERT;
	
	protected static final String SQL_STOCK_INVENT_CANCEL_UPD_KEY = "SQL_STOCK_INVENT_CANCEL_UPD_KEY";
	protected static final String SQL_STOCK_INVENT_CANCEL_UPD = DBQueries.SQL_STOCK_INVENT_CANCEL_UPD;
	
	protected static final String SQL_INVOICE_HDR_UPD_KEY = "SQL_INVOICE_HDR_UPD_KEY";
	protected static final String SQL_INVOICE_HDR_UPD = DBQueries.UPDATE_INVOICE_HEADER;
	
	protected static final String SQL_PO_VIEW = DBQueries.SQL_MISSING_PO_VIEW;
	
	
	public DPMissingStockApprovalDaoImpl(Connection connection) 
	{
		super(connection);
		
		queryMap.put(SQL_MISSING_STOCK_INIT_KEY, SQL_MISSING_STOCK_INIT);
		
		queryMap.put(SQL_STOCK_STATUS_LIST_KEY, SQL_STOCK_STATUS_LIST);
		
		queryMap.put(SQL_WRONG_SHIP_DETAIL_UPD_KEY, SQL_WRONG_SHIP_DETAIL_UPD);
		
		queryMap.put(SQL_STOCK_INVENT_UPD_KEY, SQL_STOCK_INVENT_UPD);
		
		queryMap.put(SQL_STOCK_INVENT_DELETE_KEY, SQL_STOCK_INVENT_DELETE);
		
		queryMap.put(SQL_STOCK_INVENT_INSERT_KEY, SQL_STOCK_INVENT_INSERT);
		
		queryMap.put(SQL_STOCK_INVENT_CANCEL_UPD_KEY, SQL_STOCK_INVENT_CANCEL_UPD);
		
		queryMap.put(SQL_INVOICE_HDR_UPD_KEY, SQL_INVOICE_HDR_UPD);
	}
	
	public List<List> getInitMissingStockDao(long loginUserId,String strSelectedDC ) throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<List> listReturn = new ArrayList<List>();
		
		List<DPMissingStockApprovalDTO> listGridData = null;
		List<DPMissingStockApprovalDTO> listAction = null;
		logger.info("strSelectedDC == "+strSelectedDC);
		try
		{
			pstmt = connection.prepareStatement(queryMap.get(SQL_MISSING_STOCK_INIT_KEY));
			
			//String strUserID = loginUserId+"";
			
			//int intUserID = Integer.parseInt(strUserID);
			
			pstmt.setLong(1, loginUserId);
			pstmt.setLong(2, loginUserId);
			pstmt.setLong(3, loginUserId);
			pstmt.setLong(4, loginUserId);
			pstmt.setLong(5, loginUserId);
			pstmt.setLong(6, loginUserId);
			//pstmt.setLong(7, loginUserId);
			//pstmt.setLong(8, loginUserId);	
			pstmt.setString(7, strSelectedDC);	
			
			rset = pstmt.executeQuery();
			
			listGridData = fetchMissingStockInitData(rset);
			
			pstmt = null;
			rset = null;
			
			pstmt = connection.prepareStatement(queryMap.get(SQL_STOCK_STATUS_LIST_KEY));
			
			rset = pstmt.executeQuery();
			
			listAction = getStockStatusList(rset);
			
			listReturn.add(listGridData);
			listReturn.add(listAction);
		}
		catch(SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			logger.error("SQL Exception occured while fetching initial data for Missing Stock Approval  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while fetching initial data for Missing Stock Approval  ::  "+e.getMessage());
			//throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
			rset = null;
			listGridData = null;
			listAction = null;
		}
		return listReturn;
	}

	private List<DPMissingStockApprovalDTO> getStockStatusList(ResultSet rset) throws Exception
	{
		List<DPMissingStockApprovalDTO> listReturn = new ArrayList<DPMissingStockApprovalDTO>();
		DPMissingStockApprovalDTO  dpMSADto = null;
		while(rset.next())
		{
			dpMSADto = new DPMissingStockApprovalDTO();
			
			dpMSADto.setStrActionValue(rset.getString("STOCK_STATUS_ID"));
			
			dpMSADto.setStrActionLabel(rset.getString("STOCK_STATUS_NAME"));
			
			listReturn.add(dpMSADto);
		}
		
		return listReturn;
	}

	private List<DPMissingStockApprovalDTO> fetchMissingStockInitData(ResultSet rset) throws Exception
	{
		List<DPMissingStockApprovalDTO> listReturn = new ArrayList<DPMissingStockApprovalDTO>();
		DPMissingStockApprovalDTO  dpMSADto = null;
		String strSwapped, strDate, strStatus;

		while(rset.next())
		{
			dpMSADto = new DPMissingStockApprovalDTO();
			
			dpMSADto.setStrSerialNo(rset.getString("SERIAL_NO"));
			
			dpMSADto.setIntProductID(rset.getInt("PRODUCT_ID"));
			
			dpMSADto.setStrProductName(rset.getString("PRODUCT_NAME"));
			
			dpMSADto.setStrDCNo(rset.getString("DC_NO"));
			
			dpMSADto.setStrInvoiceNo(rset.getString("INV_NO"));
			
			dpMSADto.setIntDistID(rset.getInt("DIST_ID"));
			
			dpMSADto.setStrDistName(rset.getString("DIST_NAME"));
			
			strStatus = rset.getString("WRONG_SHIP_TYPE");
			dpMSADto.setStrStatus(strStatus);
			
			strDate = rset.getString("DIST_CLAIM_DT");			
			strDate = Utility.convertDateFormat(strDate, "yyyy-MM-dd", "dd/MM/yyyy");			
			dpMSADto.setStrDistClaimDate(strDate);
			
			strSwapped = rset.getString("SWAPPED");			
			dpMSADto.setStrSwapped(strSwapped);
			
			if(strSwapped.equals(Constants.MSA_SWAPPED_YES))
				dpMSADto.setStrAction(Constants.MSA_STOCK_STATUS_ID_SWAP);
			else if(strStatus.equals(Constants.MSA_STOCK_STATUS_WRONG))
				dpMSADto.setStrAction(Constants.MSA_STOCK_STATUS_ID_WRONG);
			else
				dpMSADto.setStrAction(Constants.MSA_STOCK_STATUS_ID_SELECT);
			
			dpMSADto.setStrNewDistID(rset.getString("NEW_DIST_ID"));
			
			dpMSADto.setStrNewDistName(rset.getString("NEW_DIST_NAME"));
			
			dpMSADto.setStrNewDCNo(rset.getString("NEW_DC_NO"));
			
			dpMSADto.setStrNewInvoiceNo(rset.getString("NEW_INV_NO"));
			
			dpMSADto.setTimeStampCreated(rset.getTimestamp("CREATED_DATE"));
			
			listReturn.add(dpMSADto);
		}
		
		return listReturn;
	}
	
	
	public List<List> saveMissingStockDao(Map<String, DPMissingStockApprovalDTO> mapMSAGridData, 
			String[] arrCheckedMSA, long loginUserId,String strSelectedDC) throws DAOException
	{
		List<List> listReturn = new ArrayList();
		PreparedStatement pstmtUpdAuditTrail= null;
		PreparedStatement pstmtWrongShip= null;
		PreparedStatement pstmtStockInvt= null;
		PreparedStatement pstmtStockDelete= null;
		PreparedStatement pstmtStockInsert= null;
		PreparedStatement pstmtStockCancel= null;
		PreparedStatement psmtUpdateInvoiceHdr= null;
		PreparedStatement pstmtStockInvUpd = null;
		PreparedStatement pstmtUpdPOHdr = null;
		PreparedStatement pstmtUpdPRHdr = null;
		PreparedStatement pstmtAccQty = null;
		PreparedStatement pstmtUpdInvoiceDet = null;
		PreparedStatement pstmtAlert = null;
		ResultSet rsAlert=null;
		ResultSet rset = null;
		String distId="";
		String invoiceno=null;
		try
		{
			connection.setAutoCommit(false);
			
			int intSelSrNo = arrCheckedMSA.length;
			ArrayList<String> releasedSerialNos =new ArrayList<String>();
			String strSerialNo, strAction;
			int intUpd = 0;
			
			pstmtUpdAuditTrail = connection.prepareStatement(DBQueries.INSERT_AUDIT_TRAIL);
			pstmtWrongShip = connection.prepareStatement(queryMap.get(SQL_WRONG_SHIP_DETAIL_UPD_KEY));
			pstmtStockInvt = connection.prepareStatement(queryMap.get(SQL_STOCK_INVENT_UPD_KEY));
			pstmtStockDelete = connection.prepareStatement(queryMap.get(SQL_STOCK_INVENT_DELETE_KEY));
			pstmtStockInsert = connection.prepareStatement(queryMap.get(SQL_STOCK_INVENT_INSERT_KEY));
			pstmtStockCancel = connection.prepareStatement(queryMap.get(SQL_STOCK_INVENT_CANCEL_UPD_KEY));			
			psmtUpdateInvoiceHdr=connection.prepareStatement(queryMap.get(SQL_INVOICE_HDR_UPD_KEY));
			pstmtUpdPOHdr 	   = connection.prepareStatement(DBQueries.UPDATE_PO_HEADER_INACTIVE);
			pstmtUpdPRHdr 	   = connection.prepareStatement(DBQueries.UPDATE_PR_HEADER_INACTIVE);
			pstmtStockInvUpd = connection.prepareStatement(DBQueries.SQL_ACCEPT_DC_STOCK_INVENT);
			pstmtAccQty = connection.prepareStatement(DBQueries.ACCEPTED_STOCK_DETAIL);
			pstmtUpdInvoiceDet = connection.prepareStatement(DBQueries.UPDATE_INVOICE_DETAILS);
			pstmtAlert = connection.prepareStatement(DBQueries.SELECT_PO_NUMBER);
			
			for(int i=0; i<intSelSrNo; i++)
			{
				strSerialNo = arrCheckedMSA[i].split("#")[0];
				strAction = arrCheckedMSA[i].split("#")[1];
				logger.info("Serial no == "+strSerialNo+" and Taken Action == "+strAction);
				if(strAction.trim().equals("LIT") || strAction.trim().equals("EIB")){
					releasedSerialNos.add(strSerialNo);
				}
				
				DPMissingStockApprovalDTO objMSADto = mapMSAGridData.get(strSerialNo);
				
				distId = objMSADto.getIntDistID()+"";
				invoiceno=objMSADto.getStrInvoiceNo();
				logger.info(distId+" : Dist ID ** Invoice Number :  "+invoiceno);
				pstmtWrongShip.setString(1, strAction);
				pstmtWrongShip.setLong(2, loginUserId);
				pstmtWrongShip.setString(3, strSerialNo);
				//pstmtWrongShip.setInt(4, objMSADto.getIntProductID()); Commented by Nazim Hussain to process inter circle wrong shipment
				
//				Commented by shilpa on 08-09-2012
				//pstmtWrongShip.setInt(5, objMSADto.getIntDistID());
				//Ends here
				pstmtWrongShip.executeUpdate();
				
				if(objMSADto.getStrSwapped().equals(Constants.MSA_SWAPPED_YES))
				{
					//Changed by Nazim Hussain to process inter circle wrong shipment
					int intNewDistID = Integer.parseInt(objMSADto.getStrNewDistID().trim());
					pstmtStockInvt.setLong(1, intNewDistID);
					//Commented by shilpa on 08-09-2012
					//pstmtStockInvt.setLong(1, loginUserId);
					//Ends here 
					//pstmtStockInvt.setTimestamp(2, objMSADto.getTimeStampCreated());
					pstmtStockInvt.setString(2, objMSADto.getStrInvoiceNo());
					pstmtStockInvt.setInt(3, objMSADto.getIntProductID());
					pstmtStockInvt.setInt(4, objMSADto.getIntProductID());
					pstmtStockInvt.setLong(5, intNewDistID);
					pstmtStockInvt.setString(6, strSerialNo);
					pstmtStockInvt.setInt(7, objMSADto.getIntProductID());
					pstmtStockInvt.setInt(8, objMSADto.getIntDistID());
					//Changed by Nazim Hussain ends here
					intUpd = pstmtStockInvt.executeUpdate();
				}
				else if(strAction.equals(Constants.MSA_STOCK_STATUS_ID_WRONG))
				{
					pstmtStockInsert.setInt(1, objMSADto.getIntProductID());
					pstmtStockInsert.setString(2, strSerialNo);
					pstmtStockInsert.setInt(3, objMSADto.getIntDistID());
					//pstmtStockInsert.setTimestamp(4, objMSADto.getTimeStampCreated());
					pstmtStockInsert.setString(4, objMSADto.getStrInvoiceNo());
					pstmtStockInsert.setInt(5, Constants.MSA_STATUS_RESTRICTED);
					
					intUpd = pstmtStockInsert.executeUpdate();
				}
				else if(strAction.equals(Constants.MSA_STOCK_STATUS_ID_CANCEL))
				{
					pstmtStockCancel.setString(1, strSerialNo);
					pstmtStockCancel.setInt(2, objMSADto.getIntProductID());
					pstmtStockCancel.setInt(3, objMSADto.getIntDistID());
					
					intUpd = pstmtStockCancel.executeUpdate();
				}
				else
				{
					pstmtStockDelete.setString(1, strSerialNo);
					pstmtStockDelete.setInt(2, objMSADto.getIntProductID());
					pstmtStockDelete.setInt(3, objMSADto.getIntDistID());
					
					intUpd = pstmtStockDelete.executeUpdate();
				}
			}
			logger.info("All releasedSerialNos.Size   == "+releasedSerialNos.size());
			if(releasedSerialNos.size()>0){
				logger.info("Calling releaseSTB function in STBFlushOutDaoImpl");
				
				STBFlushOutDaoImpl relesePoObj = new STBFlushOutDaoImpl(connection);
				relesePoObj.releaseSTB(releasedSerialNos);
		
				logger.info("Exit from releaseSTB function in STBFlushOutDaoImpl");
				
				}
			
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			
			//Making stock to be available for distributor
			pstmtStockInvUpd.setString(1, strSelectedDC);
			pstmtStockInvUpd.execute();
			
			
			//To update status TSM_ACTION_TAKEN in INVOICE HEADER
			psmtUpdateInvoiceHdr.setString(1, Constants.PO_TSM_ACTION_TAKEN);
			psmtUpdateInvoiceHdr.setTimestamp(2, currentTime);
			psmtUpdateInvoiceHdr.setString(3, strSelectedDC);
			psmtUpdateInvoiceHdr.executeUpdate();
			
//			Getting Accepted Quantity in DC
			//pstmtAccQty.setLong(1, loginUserId);
			pstmtAccQty.setString(1, strSelectedDC);
			rset = pstmtAccQty.executeQuery();
			
			int intAccptQty = 0;
			int intProdID = 0;
			
			while(rset.next())
			{
				intAccptQty = rset.getInt("ACCEPTED_QTY");
				intProdID = rset.getInt("PRODUCT_ID");
				
				//Updating Invoice_detail against accepted quantity
				pstmtUpdInvoiceDet.setInt(1,intAccptQty);
				pstmtUpdInvoiceDet.setString(2, strSelectedDC);
				pstmtUpdInvoiceDet.setInt(3,intProdID);
				pstmtUpdInvoiceDet.executeUpdate();
			}
			
			//Updating  DP_PR_HEADER to make PR inactive
			pstmtUpdPRHdr.setInt(1, Constants.PO_PR_INACTIVE_STATUS);
			pstmtUpdPRHdr.setTimestamp(2, currentTime);
			pstmtUpdPRHdr.setString(3, strSelectedDC);
			pstmtUpdPRHdr.executeUpdate();

			//Making an enrty in PO_AUDIT_TRAIL for the PO status update
			pstmtUpdAuditTrail.setString(1, strSelectedDC);
			pstmtUpdAuditTrail.setInt(2, Constants.PO_STATUS_PO_TSM_ACTION_TAKEN);
			pstmtUpdAuditTrail.setString(3, strSelectedDC);
			pstmtUpdAuditTrail.executeUpdate();
			
			//Updating  PO_HEADER to update status and make PO inactive
			pstmtUpdPOHdr.setInt(1, Constants.PO_PR_INACTIVE_STATUS);
			pstmtUpdPOHdr.setTimestamp(2, currentTime);
			pstmtUpdPOHdr.setInt(3, Constants.PO_STATUS_PO_TSM_ACTION_TAKEN);
			pstmtUpdPOHdr.setString(4, strSelectedDC);
			pstmtUpdPOHdr.executeUpdate();
			
			

			/**
			 * ********************************ALERT MANAGEMENT********************************
			 */
			logger.info("*********************Sending SMS alerts***********************");
			
			
			SMSDto sMSDto = null;
			String po_no=SMSUtility.getPONo(invoiceno, connection);
			int transactionType=SMSUtility.getTransactionType(po_no, connection);
			sMSDto = SMSUtility.getUserDetailsasPerTransaction(distId, connection,transactionType);//changed by neetika
			pstmtAlert.setString(1,strSelectedDC );
			String mobileno=null;
			sMSDto.setAlertType(Constants.CONFIRM_ID_PO_APPROVE_TSM);
			rsAlert = pstmtAlert.executeQuery();
			if(rsAlert.next())
			{
				sMSDto.setPoNo(rsAlert.getString(1));
				try{
					
					//For Dist
					String message=null;
					String message1=null;
					String message2=null;
					ArrayList mobileNo=new ArrayList<String>();
					
						message1=SMSUtility.flagsCheck(Constants.CONFIRM_ID_PO_APPROVE_TSM, sMSDto, connection,distId);
						
						if(message1!=null && !message1.equalsIgnoreCase(""))
						{
						sMSDto.setMessage(message1);	
						mobileNo.add(sMSDto.getLapuMobile1());
						}
						
					//For TSM
						
						/*message2=SMSUtility.flagsCheck(Constants.CONFIRM_ID_PO_APPROVE_TSM, sMSDto, connection,sMSDto.getTsmID());
						

						if(message2!=null && !message2.equalsIgnoreCase(""))
						{
							sMSDto.setMessage(message2);
							if(!sMSDto.getParentMobileNumber().equalsIgnoreCase("")) //if TSM mobile number  availablle
							{
								mobileNo.add(sMSDto.getParentMobileNumber());
							}
						}*/
						
						message =SMSUtility.createMessageContentOnly(Constants.CONFIRM_ID_PO_APPROVE_TSM, sMSDto);
						
						
						if(message != null && !message.equalsIgnoreCase(""))
						{
							
								SMSUtility.saveSMSInDBMessages(connection,mobileNo, message,Constants.CONFIRM_ID_PO_APPROVE_TSM);
							
						}
						//	
				/*mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(distId), Constants.CONFIRM_ID_PO_APPROVE_TSM, connection);
				if(mobileno!=null)
				{
					String SMSmessage=SMSUtility.createMessageContent(Constants.CONFIRM_ID_PO_APPROVE_TSM, sMSDto, connection);
					
					if(SMSmessage != null && !SMSmessage.equalsIgnoreCase(""))
					{
						SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getLapuMobile1(),sMSDto.getLapuMobile2(),sMSDto.getParentMobileNumber(),sMSDto.getParentMobileNumber2()}, SMSmessage,Constants.CONFIRM_ID_PO_APPROVE_TSM);
					}
				}*/
					
				}
				catch(Exception exe) //try catch added by Neetika because if some exception in SMS sending occurs functionality should work as normal
				
				{
					exe.printStackTrace();
					logger.info("Exception in sending SMS while save missing stock ::  "+exe);
					logger.info("Exception in sending SMS while save missing stock::  "+exe.getMessage());
				}
			}
			
			logger.info("*********************Sending SMS alerts ends***********************");
			
			/**
			 * ********************************ALERT MANAGEMENT ENDS********************************
			 */
			
			listReturn = getInitMissingStockDao(loginUserId,strSelectedDC);

			connection.commit();
			//connection.rollback();
		}
		catch(Exception e)
		{
			try
			{
				connection.rollback();
			}
			catch(Exception e2)
			{
				e2.printStackTrace();
			}
			listReturn = getInitMissingStockDao(loginUserId,strSelectedDC);
			e.printStackTrace();
			throw new DAOException("Exception occured while saving Missing Stock  ::  "+e.getMessage());
		}
		finally
		{
			try
			{
				DBConnectionManager.releaseResources(pstmtAccQty, rset);
				DBConnectionManager.releaseResources(pstmtAlert, rsAlert);
				
				if(pstmtUpdAuditTrail!=null)
				{
					pstmtUpdAuditTrail.close();
				}
				if(pstmtWrongShip!=null)
				{
					pstmtWrongShip.close();
				}
				if(pstmtStockInvt!=null)
				{
					pstmtStockInvt.close();
				}
				if(pstmtStockDelete!=null)
				{
					pstmtStockDelete.close();
				}
				if(pstmtStockInsert!=null)
				{
					pstmtStockInsert.close();
				}
				if(pstmtStockCancel!=null)
				{
					pstmtStockCancel.close();
				}
				if(psmtUpdateInvoiceHdr!=null)
				{
					psmtUpdateInvoiceHdr.close();
				}
				
				if(pstmtUpdInvoiceDet!=null)
				{
					pstmtUpdInvoiceDet.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return listReturn;
	}
	
	
	public List<DCNoListDto> getDCNosList(String loginUserId) throws DAOException
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<DCNoListDto> listReturn = null;
		DCNoListDto dcNo = null;
		
		
		
		try
		{
			pstmt = connection.prepareStatement(DBQueries.GET_DC_NOS_LIST);
			pstmt.setString(1, loginUserId);
			rset = pstmt.executeQuery();
			listReturn = new ArrayList<DCNoListDto>();
			while(rset.next()){
				dcNo = new DCNoListDto();
				dcNo.setDcNos(rset.getString("DC_NO"));
				dcNo.setInvoiceNos(rset.getString("INV_NO"));
				listReturn.add(dcNo);
			}
			
		
			
		}
		catch(SQLException sqlEx)
		{
			sqlEx.printStackTrace();
			logger.error("SQL Exception occured while fetching DC Nos  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("Exception occured while fetching DC Nos ::  "+e.getMessage());
			//throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
			rset = null;
			
		}
		return listReturn;
	}
public ArrayList viewPODetails(long accountID,int circleID,int lowerbound, int upperbound)throws DAOException  {
		
		ResultSet rst = null;
		DPPurchaseOrderDTO dto;
		
		PreparedStatement pst = null;
		ArrayList<DPPurchaseOrderDTO> POList = new ArrayList<DPPurchaseOrderDTO>();
		
		String sql="";
		
		try {
			
					sql = SQL_PO_VIEW;
				
			
			if(lowerbound != -1 && upperbound != -1) {
				sql = sql += " where  rx<=? and rx>=?";
			}
			sql += " WITH UR";
			pst = connection.prepareStatement(sql);
			pst.setLong(1, accountID);
			if(lowerbound != -1 && upperbound != -1) {
				pst.setString(2, String.valueOf(upperbound));
				pst.setString(3, String.valueOf(lowerbound ));
			}
			rst = pst.executeQuery();

			while (rst.next()) {
				dto = new DPPurchaseOrderDTO();
				dto.setPo_no(rst.getString("po_no"));
				dto.setDc_no(rst.getString("dc_no"));
				dto.setDcNo(rst.getString("dc_no"));
				dto.setDistName(rst.getString("Dist_Name"));
				dto.setPoqty(rst.getInt("PO_QTY"));
				dto.setInvqty(rst.getInt("INV_QTY"));
				dto.setAccptqty(rst.getInt("PO_QTY")-rst.getInt("MISSING")+rst.getInt("ADDED"));
				dto.setMissingqty(rst.getInt("MISSING"));
				dto.setAddedty(rst.getInt("ADDED"));
				dto.setInvoice_no(rst.getString("INV_NO"));
				
					POList.add(dto);
			} // while
			return POList;
			 			
		} catch (SQLException e) {
		    e.printStackTrace();
		    logger.error("SQL Exception occured while inserting.Into PURCHASE ORDER REQUEST Tables"
							+ "Exception Message: ", e);
		    try
			{
				String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.Shipment_Error_Correction_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			throw new DAOException(e.getMessage());
			
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
			.error(" **: ERROR INSIDE EXIT insertPurchaseOrder(DPPurchaseOrderFormBean dppfb) OF -> DPPurchaseOrderDaoImpl Class :** ");
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.Shipment_Error_Correction_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			throw new DAOException(ex.getMessage());
		} finally {

			{
				/* Close the statement,resultset */
				DBConnectionManager.releaseResources(pst,rst);
			}
		}
	//	return POList;

	}
}
