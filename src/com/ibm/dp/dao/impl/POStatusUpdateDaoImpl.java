package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.soap.SOAPElement;

import org.apache.log4j.Logger;

import com.ibm.db2.jcc.b.SqlException;
import com.ibm.dp.beans.POStatusProductBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.POStatusUpdateDao;
import com.ibm.dp.dto.ErrorStbDto;
import com.ibm.dp.dto.POStatusUpdateDto;
import com.ibm.dp.dto.SMSDto;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class POStatusUpdateDaoImpl extends BaseDaoRdbms implements POStatusUpdateDao{
	Logger logger = Logger.getLogger(POStatusUpdateDaoImpl.class.getName());
	
	
	public POStatusUpdateDaoImpl(Connection connection) 
	{
		super(connection);
	}
	
	public String updateStatus(String poNo, String poStatus, String poStatusTime, String remarks, String productCode, String poQuantity, String userid, String password) throws DAOException 
	{
		String serviceMsg = null;
		String poNum="";
	
		PreparedStatement pstmtGetPO = null;
		PreparedStatement pstmtPOHdrUpd = null;
		PreparedStatement psPRDtlUpd = null;
		PreparedStatement psProdCheck = null;
		PreparedStatement pstmtPOAudtUpd = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement psCheckPO = null;
		
		ResultSet rsk = null;
		ResultSet rs2 = null;
		
		ResultSet rsAlert=null;
		PreparedStatement psAlert=null;
		ArrayList mobileNo=new ArrayList<String>();	
		int result=0;
		int res=0;
		try
		{
			connection.setAutoCommit(false);
			
			pstmtGetPO = connection.prepareStatement(DBQueries.GET_PO);
			pstmtGetPO.setString(1,poNo);
			
			rsk = pstmtGetPO.executeQuery();
			
			while(rsk.next())
			{
			   poNum = rsk.getString("PO_NO");
			}
			DBConnectionManager.releaseResources(pstmtGetPO, rsk);
			
			logger.info("PO NO Validation  ::  "+poNum);	
		   
			if(poNum==null || poNum.trim().equals(""))
			{
				return Constants.STB_STATUS_WS_MSG_INVALID_PO;
			}
			
			int poStatusInt = Integer.parseInt(poStatus);
			Timestamp statusTime = null;
		   
			try
			{
				if(poStatusTime != null && !poStatusTime.equals("")) 
					statusTime = Utility.stringToTimestampAmPm(poStatusTime);
				else
					return Constants.PO_STATUS_WS_MSG_MANDATORY_STATUS_DATE;
			}
			catch(ParseException pe)
			{
				logger.error("::::::::::::::::::: Error in POStatusUpdate WebService -------->",pe);
				pe.printStackTrace();
				return Constants.PO_STATUS_WS_MSG_INVALID_STATUS_DATE;
			}
			
			if(poStatusInt==Constants.PO_STATUS_DC_CREATED)
				return Constants.PO_STATUS_WS_MSG_INVALID_STATUS;
			
			int sd=0;
			
			String SELECT_PO_STATUS_DATE = "select timestampdiff(8,char( '"+statusTime + "' - (SELECT STATUS_DATE  FROM PO_HEADER where PO_NO = ? ) ) ) " +
											"AS SD from sysibm.sysdummy1";
			
			psProdCheck = connection.prepareStatement("select PRODUCT_ID from DP_PR_DETAILS where PR_NO=(select PR_NO from PO_HEADER where PO_NO=?) and EXT_PRODUCT_ID=? with ur");
			//psAlert = connection.prepareStatement("select b.PRODUCT_NAME,c.PR_DIST_ID ,d.value from DP_PR_DETAILS a , DP_PRODUCT_MASTER b ,DP_PR_HEADER c , DP_CONFIGURATION_DETAILS d where a.PR_NO=(select PO.PR_NO from PO_HEADER PO where PO_NO=?)  and a.EXT_PRODUCT_ID=? and a.PRODUCT_ID=b.PRODUCT_ID and a.pr_no=c.pr_no and d.config_id=2 and d.ID= ? with ur");
			psAlert = connection.prepareStatement("select c.PR_DIST_ID ,d.value  "
												+" from DP_PR_DETAILS a , DP_PR_HEADER c , DP_CONFIGURATION_DETAILS d " 
												+" where a.PR_NO=(select PO.PR_NO from PO_HEADER PO where PO_NO=? )   "
												+" and a.pr_no=c.pr_no and d.config_id=2 and d.ID= ?  "
												+" with ur");
															
			psPRDtlUpd = connection.prepareStatement("update DP_PR_DETAILS set APPROVED_QTY=? where PR_NO=(select PR_NO from PO_HEADER where PO_NO=?) and PRODUCT_ID=?");
			
			pstmt = connection.prepareStatement(SELECT_PO_STATUS_DATE);
			pstmt.setString(1,poNo);
			
			rsk = pstmt.executeQuery();
			
			while(rsk.next())
			{
			   sd = rsk.getInt("SD");
			}
			
			DBConnectionManager.releaseResources(pstmt, rsk);
			
			pstmtPOAudtUpd = connection.prepareStatement(DBQueries.UPDATE_PO_STATUS_AUDIT_TRAIL);
			pstmtPOAudtUpd.setString(1, poNo);
			pstmtPOAudtUpd.setInt(2, poStatusInt);
			pstmtPOAudtUpd.setTimestamp(3, statusTime);
			pstmtPOAudtUpd.setInt(4, sd);
			
			res = pstmtPOAudtUpd.executeUpdate();
			
			DBConnectionManager.releaseResources(pstmtPOAudtUpd, null);
			
			String strQuery = "select * from PO_HEADER where PO_NO=? and STATUS_DATE>? with ur";
			psCheckPO = connection.prepareStatement(strQuery);
			psCheckPO.setString(1, poNo);
			psCheckPO.setTimestamp(2, statusTime);
			ResultSet rs = psCheckPO.executeQuery();
			
			if(rs.next())
			{
				logger.info("OLD PO STATUS COMING NO NEED TO UPDATE PO_HEADER");
				logger.info("PO_NO  ::  "+poNo);
				logger.info("PO_STATUS  ::  "+poStatusInt);
				logger.info("PO_STATUS_TIME ::  "+statusTime);
			}
			else
			{
				pstmtPOHdrUpd = connection.prepareStatement(DBQueries.UPDATE_PO_HEADER);
				  
				if(poStatusInt==Constants.PO_STATUS_INVOICE_CANCELLED)
					poStatusInt = Constants.PO_STATUS_DC_CANCELLED;
				
				pstmtPOHdrUpd.setInt(1, poStatusInt);
				pstmtPOHdrUpd.setTimestamp(2, statusTime);
				
				if(poStatusInt==Constants.PO_STATUS_INVOICE_GENERATED)
					pstmtPOHdrUpd.setString(3, "");
				else
					pstmtPOHdrUpd.setString(3, remarks);
				
				pstmtPOHdrUpd.setString(4, poNo);
				
				if(!(poStatusInt==Constants.PO_STATUS_INVOICE_GENERATED
						|| poStatusInt==Constants.PO_STATUS_DISPATCHED 
						|| poStatusInt==Constants.PO_STATUS_DC_CANCELLED_REQ 
						|| poStatusInt==Constants.PO_STATUS_INVOICE_CANCELLED_REQ))
				{
					result = pstmtPOHdrUpd.executeUpdate();
					
					if(!(poStatusInt==Constants.PO_STATUS_REJECTED_SALES
							|| poStatusInt==Constants.PO_STATUS_REJECTED_COMMERCIAL
							|| poStatusInt==Constants.PO_STATUS_REJECTED_FINANCE))
					{
						if(productCode!=null && poQuantity!=null 
								&& productCode.trim()!="" && poQuantity.trim()!="")
						{
							StringTokenizer prodToken = new StringTokenizer(productCode, ",");
							StringTokenizer qtyToken = new StringTokenizer(poQuantity, ",");
							
							if(prodToken.countTokens()!=qtyToken.countTokens())
								return Constants.PO_STATUS_PROD_QTY_INCORRECT;
							else
							{
								while(prodToken.hasMoreElements())
								{
									psProdCheck.setString(1, poNo);
									psProdCheck.setString(2, prodToken.nextToken());
									
									ResultSet rs1 = psProdCheck.executeQuery();
									
									if(rs1.next())
									{
										int prodID = rs1.getInt(1);
										
										int appQty = Integer.parseInt(qtyToken.nextToken());
										
										psPRDtlUpd.setLong(1, appQty);
										psPRDtlUpd.setString(2, poNo);
										psPRDtlUpd.setInt(3, prodID);
										psPRDtlUpd.executeUpdate();
									}
									else
										return Constants.PO_STATUS_PROD_INCORRECT;
								}
							}
						}
					}
				}
			}
			
			//Added to fix DEFECT  ::  MASDB00185655 (To close Open REsultset Cursor)
			DBConnectionManager.releaseResources(psCheckPO, rs);

			DBConnectionManager.releaseResources(pstmtPOHdrUpd, null);
			
			if(poStatusInt==Constants.PO_STATUS_INVOICE_GENERATED)
			{
				String strInvNo = "";
				
				pstmt = connection.prepareStatement("select INV_NO from INVOICE_HEADER where PO_NO=? with ur");
				pstmt.setString(1, poNo);
				
				ResultSet rset = pstmt.executeQuery();
				
				if(rset.next())
					strInvNo = rset.getString("INV_NO");
				
				//Added to fix DEFECT  ::  MASDB00185655 (To close Open REsultset Cursor)
				DBConnectionManager.releaseResources(pstmt, rset);
				
				if(strInvNo.equals(""))
				{
					pstmt = connection.prepareStatement(DBQueries.INSERT_INVOICE_DETAILS);
					
					pstmt.setString(1, poNo);
					pstmt.setString(2, remarks.trim());
					pstmt.setTimestamp(3, statusTime);
					pstmt.setString(4, Constants.PO_STATUS_DC_PENDING);
					res = pstmt.executeUpdate();
					DBConnectionManager.releaseResources(pstmt, null);
				}
			}
			else if(poStatusInt==Constants.PO_STATUS_DC_CANCELLED_REQ 
						|| poStatusInt==Constants.PO_STATUS_INVOICE_CANCELLED_REQ)
			{
				String strInvNo = "";
				String strDCNo = "";
				java.sql.Date dtDistAction = null;
				pstmt = connection.prepareStatement("select DIST_ACTION_DATE from INVOICE_HEADER where PO_NO=? with ur");
				pstmt.setString(1, poNo);
				
				ResultSet rset = pstmt.executeQuery();
				
				if(rset.next())
					dtDistAction = rset.getDate("DIST_ACTION_DATE");
				
				DBConnectionManager.releaseResources(pstmt, rset);
				
				if(dtDistAction!=null)
					return Constants.PO_STATUS_DC_PROCESSED;
				else
					return Constants.PO_STATUS_WS_MSG_SUCCESS;
				
			}
			else if(poStatusInt==Constants.PO_STATUS_DC_CANCELLED
						|| poStatusInt==Constants.PO_STATUS_INVOICE_CANCELLED)
			{
				String strInvNo = "";
				String strDCNo = "";
				//Added by shilpa on 2-7-12 to release STBs of Cancel DC
				ArrayList<String> releasedSerialNos =new ArrayList<String>();
				String SELECT_STOCK_INVENTORY_SRNO = "Select SERIAL_NO from DP_STOCK_INVENTORY where INV_NO=? with ur";
				
				pstmt1 = connection.prepareStatement("select INV_NO, DC_NO from INVOICE_HEADER where PO_NO=? with ur");
				pstmt1.setString(1, poNo);
				
				ResultSet rset = pstmt1.executeQuery();
				
				if(rset.next())
				{
					strInvNo = rset.getString("INV_NO");
					strDCNo = rset.getString("DC_NO");
				}
				
				if(strDCNo!=null || strDCNo!="")
				{
//					Added by shilpa on 2-7-12 to release STBs of Cancel DC
					pstmt2 = connection.prepareStatement(SELECT_STOCK_INVENTORY_SRNO);
					pstmt2.setString(1, strInvNo);
					rs2 = pstmt2.executeQuery();
					while(rs2.next()){
						releasedSerialNos.add(rs2.getString("SERIAL_NO"));
					}
					
					pstmt1 = connection.prepareStatement(DBQueries.DELETE_STOCK_INVENTORY_SRNO);
					pstmt1.setString(1, strInvNo);
					
					res = pstmt1.executeUpdate();
					
					DBConnectionManager.releaseResources(pstmt1, null);
					
//					Moving from Error Po to Error PO History
					pstmt1 = connection.prepareStatement(DBQueries.MOVE_ERROR_PO_HIST);
					pstmt1.setString(1, poNo);
					
					res = pstmt1.executeUpdate();
					
					DBConnectionManager.releaseResources(pstmt1, null);
					
//					Deleting from Error Po
					pstmt1 = connection.prepareStatement(DBQueries.DELETE_ERROR_PO);
					pstmt1.setString(1, poNo);
					
					res = pstmt1.executeUpdate();
					
					DBConnectionManager.releaseResources(pstmt1, null);
					
//					Moving from Error Reverse to Error Reverse History
					pstmt1 = connection.prepareStatement(DBQueries.MOVE_ERROR_REV_HIST);
					pstmt1.setString(1, poNo);
					
					res = pstmt1.executeUpdate();
					
					DBConnectionManager.releaseResources(pstmt1, null);
					
//					Deleting from Error Reverse
					pstmt1 = connection.prepareStatement(DBQueries.DELETE_ERROR_REV);
					pstmt1.setString(1, poNo);
					
					res = pstmt1.executeUpdate();
					
					DBConnectionManager.releaseResources(pstmt1, null);
					
				}
				Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
				
				pstmt1 = connection.prepareStatement(DBQueries.UPDATE_INVOICE_HEADER);
				pstmt1.setString(1, Constants.PO_DC_CANCELLED);
				pstmt1.setTimestamp(2, currentTime);
				pstmt1.setString(3, strInvNo);
				
				res = pstmt1.executeUpdate();
				if(releasedSerialNos.size()>0){
					logger.info("Calling releaseSTB function in STBFlushOutDaoImpl");
					
					STBFlushOutDaoImpl relesePoObj = new STBFlushOutDaoImpl(connection);
					relesePoObj.releaseSTB(releasedSerialNos);
			
					logger.info("Exit from releaseSTB function in STBFlushOutDaoImpl");
					
					}
				DBConnectionManager.releaseResources(pstmt1, rset);
				DBConnectionManager.releaseResources(pstmt2, rs2);
			}
			
			
			/**
			 * ********************************ALERT MANAGEMENT********************************
			 */
			logger.info("*********************Sending SMS alerts***********************");
			System.out.println("poStatusInt::::::::"+poStatusInt);
			
			
			if(poStatusInt==Constants.PO_STATUS_REJECTED_SALES
					|| poStatusInt==Constants.PO_STATUS_REJECTED_COMMERCIAL
					|| poStatusInt==Constants.PO_STATUS_DC_CANCELLED
				  || poStatusInt==Constants.PO_STATUS_INVOICE_CANCELLED
					|| poStatusInt==Constants.PO_STATUS_REJECTED_FINANCE)
			{
				//if(productCode!=null && productCode.trim()!="" )
				//{
					//String prodName = "";
					String dist_id="";
					//StringTokenizer prodToken = new StringTokenizer(productCode, ",");
					SMSDto sMSDto = null;
					//String mobileno=null;
					int transactionType=0;
						//while(prodToken.hasMoreElements())
						//{		
							psAlert.setString(1, poNo);
							//psAlert.setString(2, prodToken.nextToken());
							psAlert.setInt(2, poStatusInt);
							
							rsAlert = psAlert.executeQuery();
							
							if(rsAlert.next())
							{
								//prodName = rsAlert.getString(1);
								dist_id = rsAlert.getString(1);
								try{
								transactionType=SMSUtility.getTransactionType(poNo, connection);
								sMSDto = SMSUtility.getUserDetailsasPerTransaction(dist_id, connection,transactionType);//changed by neetika
								//sMSDto.setProductName(prodName);
								sMSDto.setPoNo(poNo);
								sMSDto.setRemarks(remarks);
								sMSDto.setPoRejectDesc(rsAlert.getString(2));
								sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT);
								//mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(dist_id), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT, connection);
								//if(mobileno!=null)
								//{
								//For Dist
								String message=null;
								String message1=null;
								String message2=null;
								
									message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT, sMSDto, connection,dist_id);
									if(message1!=null && !message1.equalsIgnoreCase(""))
									{
									sMSDto.setMessage(message1);
									mobileNo.add(sMSDto.getLapuMobile1());
									}
									
								//For TSM
									
									message2=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT, sMSDto, connection,sMSDto.getTsmID());
									if(message2!=null && !message2.equalsIgnoreCase(""))
									{
										sMSDto.setMessage(message2);
										if(!sMSDto.getParentMobileNumber().equalsIgnoreCase("")) //if TSM mobile number  availablle
										{
											mobileNo.add(sMSDto.getParentMobileNumber());
										}
									}
									message =SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT, sMSDto);
									
									
									if(message != null && !message.equalsIgnoreCase(""))
									{
										
											SMSUtility.saveSMSInDBMessages(connection,mobileNo, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_PO_REJECT);
										
									}
								//}
								}
								catch(Exception exe) //try catch added by Neetika because if some exception in SMS sending occurs functionality should work as normal
								
								{
									exe.printStackTrace();
									logger.info("Exception in sending SMS while status update reject::  "+exe);
									logger.info("Exception in sending SMS while status update reject "+exe.getMessage());
								}
								
							}
							DBConnectionManager.releaseResources(null, rsAlert);
						//}
				//}
			}
			
			logger.info("*********************Sending SMS alerts ends***********************");
			
			/**
			 * ********************************ALERT MANAGEMENT ENDS********************************
			 */
			
			
			serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		}
		catch(NumberFormatException e)
		{
			logger.error("::::::::::::::::::: Error in POStatusUpdate WebService -------->",e);
			serviceMsg = Constants.PO_STATUS_WS_MSG_INVALID_PO_STATUS;
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			logger.error("::::::::::::::::::: Error in POStatusUpdate WebService -------->",e);
			
			serviceMsg = Constants.OPEN_STOCK_WS_MSG_OTHERS;
			
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in POStatusUpdate WebService -------->",sqle);
			}
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, null);
			DBConnectionManager.releaseResources(pstmtGetPO, null);
			DBConnectionManager.releaseResources(pstmtPOHdrUpd, null);
			DBConnectionManager.releaseResources(pstmtPOAudtUpd, null);
			DBConnectionManager.releaseResources(psCheckPO, null);
			DBConnectionManager.releaseResources(psAlert, rsAlert);
		}
		return serviceMsg;
	}
	
	public String updateDeliveryChallan(String prNo, String poNo, String poDate,String poStatusDate , String invoiceNo,String invoiceDate, String dcNo, String dcDate, String ddChequeNo , String ddChequeDate,String productId,String serialNo, String userid, String password) throws DAOException {
		logger.info("in updateDeliveryChallan Dao Impl ");
		String serviceMsg = null;
		POStatusUpdateDto dtoObj = null;
		PreparedStatement pstmt = null;
		ResultSet rset =null;
		PreparedStatement pstmt1 = null;
		ResultSet rset1 =null;
		ArrayList mobileNo=new ArrayList<String>();	
		int transactionType=0;
		try
		{
			connection.setAutoCommit(false);
			// **** Added by Shilpa Khanna on 13-12-2012 To Check Already dispatched DCs.  STARTS Here *****
			pstmt1 =connection.prepareStatement("Select PO_STATUS from PO_STATUS_AUDIT_TRAIL where PO_STATUS=7 and PO_NO=? with ur");
			pstmt1.setString(1, poNo);
			rset1 = pstmt1.executeQuery();
			
			if(rset1.next()){
				serviceMsg = ResourceReader.getCoreResourceBundleValue(Constants.DC_ALREADY_DISPATCHED);
				return serviceMsg;
			}
			
			//**** Added by Shilpa Khanna on 13-12-2012 To Check Already dispatched DCs.  ENDS Here *****
		
			
		/*	pstmt = connection.prepareStatement("select PRODUCT_ID from DP_PRODUCT_MASTER  where CM_STATUS='A' and CIRCLE_ID=(select CIRCLE_ID from VR_ACCOUNT_DETAILS where ACCOUNT_ID =" +
			"(select PR_DIST_ID from DP_PR_HEADER where PR_NO=?))  and BT_PRODUCT_CODE=? with ur");
	*/
			
			pstmt = connection.prepareStatement("select a.PRODUCT_ID,a.PRODUCT_NAME,c.PR_DIST_ID  "
					+" from DP_PRODUCT_MASTER a,VR_ACCOUNT_DETAILS b,DP_PR_HEADER c "
					+" where b.account_id=c.PR_DIST_ID and b.circle_id=a.circle_id and a.CM_STATUS='A' " 
					+" and PR_NO=? and BT_PRODUCT_CODE=?   with ur");
			
			logger.info("in updateDeliveryChallan Dao Impl , PRNO === "+prNo+" and PoNo == "+poNo+ " and Inv No == "+invoiceNo +" and Dcno == "+dcNo+ " and ProductList== "+productId+" and Sr No List == "+serialNo);
			dtoObj =new POStatusUpdateDto();
			dtoObj.setPrNO(prNo);
			dtoObj.setPoNO(poNo);
			dtoObj.setPoDate(poDate);
			dtoObj.setPoStatus(7);
			dtoObj.setPoStatusDate(poStatusDate);
			//dtoObj.setPoRemarks("");
			dtoObj.setInvoiceNo(invoiceNo);
			dtoObj.setInvoiceDT(invoiceDate);
			dtoObj.setDc_no(dcNo);
			
			StringTokenizer productST=new StringTokenizer(productId, ",");
			StringTokenizer serialNumberST=new StringTokenizer(serialNo, ",");
			List<String> prodCodeListunique= new ArrayList<String>();
			Map<String, POStatusProductBean> poProductMap = new HashMap<String, POStatusProductBean>();
			POStatusProductBean poBean= null;
			List serialNum = null;
			String srNum ="";
			String dynamicSMSContent=""; 
			
			while(productST.hasMoreTokens())
			{
				poBean=new POStatusProductBean();
				String productCode = productST.nextToken();
				POStatusProductBean tempBean=new POStatusProductBean();
				srNum = serialNumberST.nextToken();
				logger.info("productCode === " +productCode +"  and SrNumber in while loop == "+srNum);
				if(poProductMap!=null && poProductMap.containsKey(productCode))
				{
					tempBean= poProductMap.get(productCode);
					serialNum = tempBean.getSerialNos();
					serialNum.add(srNum);
					tempBean.setSerialNos(serialNum);
					logger.info("In if cond ");
					
				}
				else
				{	
					logger.info("In else cond ");
					tempBean.setProductid(productCode);
					serialNum = new ArrayList();
					serialNum.add(srNum);
					tempBean.setSerialNos(serialNum);
					prodCodeListunique.add(productCode);
				}	
				
				poProductMap.put(productCode, tempBean);
			}
			
			if(prodCodeListunique.size()>0)
			{
				POStatusProductBean tempBean= null;
				List<POStatusProductBean> productList = new ArrayList<POStatusProductBean>();
				int intProdID=-1;
				
				String prodName = "";
				String dist_id="";
				SMSDto sMSDto = null;
				String mobileno=null;
				
				
				for(int i=0;i<prodCodeListunique.size();i++)
				{
					
					tempBean= (POStatusProductBean)poProductMap.get(prodCodeListunique.get(i));
					logger.info("Product Code == "+ prodCodeListunique.get(i)+"  and getSerialNos().size() == "+tempBean.getSerialNos().size());
					tempBean.setInvQty(tempBean.getSerialNos().size());
					tempBean.setPrdQty(tempBean.getSerialNos().size());
	//				Get Product Id for this Product Code 
					logger.info("Get Product Id for this Product Code  == "+prodCodeListunique.get(i));
					pstmt.setInt(1, Integer.parseInt(prNo.trim()));
					pstmt.setString(2, prodCodeListunique.get(i));
					rset = pstmt.executeQuery();
					
					if(rset.next()){
						intProdID = rset.getInt("PRODUCT_ID");
						dist_id = rset.getString("PR_DIST_ID");
						prodName = rset.getString("PRODUCT_NAME");
						
					}
					
					dynamicSMSContent = dynamicSMSContent + prodName +"," + tempBean.getSerialNos().size() + ";";
					
					logger.info("PRODUCT_ID found  ::  "+intProdID);
					if(intProdID !=-1){
						tempBean.setIntProdID(intProdID);
					}else{
						serviceMsg =Constants.PO_STATUS_WS_MSG_INVALID_PRODUCT_CODE; 
						return serviceMsg ;
					}
						
					
					productList.add(i,tempBean);
					
				}
				
				
				
				dtoObj.setPoPrdID(productList);
				
				dtoObj.setDc_dt(dcDate);
				dtoObj.setDd_cheque_no(ddChequeNo);
				dtoObj.setDd_cheque_dt(ddChequeDate);
				dtoObj.setInvoiceNo(invoiceNo);
				dtoObj.setInvoiceNo(invoiceNo);
				
				serviceMsg = insertIntoDB(dtoObj);
				
				try
				{
					/**
					 * ********************************ALERT MANAGEMENT********************************
					 */
					transactionType=SMSUtility.getTransactionType(poNo, connection);
					sMSDto = SMSUtility.getUserDetailsasPerTransaction(dist_id, connection,transactionType);//changed by neetika
					sMSDto.setDcNo(dcNo);
					sMSDto.setDynamicSMSContent(dynamicSMSContent);
					sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED);
					
					//For Dist
					String message=null;
					String message1=null;
					String message2=null;
					
						message1=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED, sMSDto, connection,dist_id);
						if(message1!=null && !message1.equalsIgnoreCase(""))
						{
						sMSDto.setMessage(message1);	
						mobileNo.add(sMSDto.getLapuMobile1());
						}
						
					//For TSM
						
						message2=SMSUtility.flagsCheck(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED, sMSDto, connection,sMSDto.getTsmID());
						if(message2!=null && !message2.equalsIgnoreCase(""))
						{
							sMSDto.setMessage(message2);
							if(!sMSDto.getParentMobileNumber().equalsIgnoreCase("")) //if TSM mobile number  availablle
							{
								mobileNo.add(sMSDto.getParentMobileNumber());
							}
						}
						message =SMSUtility.createMessageContentOnly(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED, sMSDto);
						
						
						if(message != null && !message.equalsIgnoreCase(""))
						{
								
								SMSUtility.saveSMSInDBMessages(connection,mobileNo, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED);
							
						}
						//
					
					/*
					
					sMSDto = SMSUtility.getUserDetails(dist_id, connection);
					sMSDto.setDcNo(dcNo);
					sMSDto.setDynamicSMSContent(dynamicSMSContent);
					sMSDto.setAlertType(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED);
					mobileno=SMSUtility.getMobileNoForSms(Long.valueOf(dist_id), com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED, connection); //Neetika changed alert code inititally it was Rejected .. 
					if(mobileno!=null)
					{
						String message=SMSUtility.createMessageContent(com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED, sMSDto, connection);
						if(message != null && !message.equalsIgnoreCase(""))
						{
							SMSUtility.saveSMSInDB(connection, new String[] {sMSDto.getLapuMobile1(),sMSDto.getLapuMobile2(),sMSDto.getParentMobileNumber(),sMSDto.getParentMobileNumber2()}, message,com.ibm.virtualization.recharge.common.Constants.CONFIRM_ID_DC_DISPATCHED);
						}
					}
					*/
					/**
					 * ********************************ALERT MANAGEMENT ENDS********************************
					*/
				}
				catch(Exception exe) //Nazim
				{
					exe.printStackTrace();
					logger.info("Exception in sending SMS while PO dispatch  ::  "+exe);
					logger.info("Exception in sending SMS while PO dispatch  ::  "+exe.getMessage());
				}
				
			}
			connection.commit();
			//serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		}
		catch(NumberFormatException e)
		{
			logger.error("::::::::::::::::::: Error in updateDeliveryChallan WebService -------->",e);
			serviceMsg = Constants.PO_STATUS_WS_MSG_INVALID_PR_NO;
			e.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			logger.error("::::::::::::::::::: Error in updateDeliveryChallan WebService -------->",e);
			
			serviceMsg = Constants.OPEN_STOCK_WS_MSG_OTHERS;
			
			try
			{				
				connection.rollback();
			}
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in updateDeliveryChallan WebService -------->",sqle);
			}
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt1, rset1);
			DBConnectionManager.releaseResources(pstmt, rset);
			
			if(connection !=null){
				try {
					//logger.info("prepareStatement release..");
					connection.close();
					//logger.info("connection release..");
				} catch (Exception e) {
					 e.printStackTrace();
					logger.info("Exception error.."+e.getMessage());
					
				}
			}
			
		}
		return serviceMsg;
	}
	
	/**
	 * This method is to insert data in DB after XML parsing
	 * @param pDTO
	 * @throws SqlException
	 * @throws VirtualizationServiceException
	 * @throws DAOException
	 */
	public String  insertIntoDB(POStatusUpdateDto pDTO) throws DAOException 
	{
		
		logger.info("################ InsertIntoDB Started for PR No - "+pDTO.getPrNO()+" #######################");
				
		//PropertyReader.setBundleName(Constants.CONFIG_RESOURCES);
		String serviceMsg="";
		int poStatus =pDTO.getPoStatus();
		String invoiceNO=pDTO.getInvoiceNo();
		String poNO=pDTO.getPoNO();//poNO considering as String
		//String poRemarks = pDTO.getPoRemarks();
		String poStatusDate = pDTO.getPoStatusDate();
		try 
		{			
								
			if(!connection.isClosed())
			{ 
				 // check for connection
				if((invoiceNO !="")&&(poNO != null))
		  		{
					//check for INVOICE DETAILS
					// updated by Shilpa khanna To Update Remarks
					String strMessage = updatePOStatus( poStatus, poNO, poStatusDate);
			  		
		  			if(strMessage == Constants.PO_STATUS_WS_MSG_SUCCESS)
		  				strMessage = insertPODetails(pDTO);
		  			
		  			if(strMessage == Constants.PO_STATUS_WS_MSG_SUCCESS)
		  				serviceMsg = insertInvoiceDetails(pDTO);
		  		}
		  		connection.commit();
		  		//serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		 }
		 else { logger.error(" No Connection Present "); }
		 }catch (Exception e) 
			{
				e.printStackTrace();
				
				logger.error("::::::::::::::::::: Error in updateDeliveryChallan WebService -------->",e);
				
				serviceMsg = Constants.OPEN_STOCK_WS_MSG_OTHERS;
				
				try
				{				
					connection.rollback();
				}
				catch (SQLException sqle) 
				{
					sqle.printStackTrace();
					logger.error("::::::::::::::::::: Error in updateDeliveryChallan WebService -------->",sqle);
				}
			} finally {
			if(connection !=null){
				try {
					//logger.info("prepareStatement release..");
					//connection.close();
					//logger.info("connection release..");
				} catch (Exception e) {
					 e.printStackTrace();
					logger.info("Exception error.."+e.getMessage());
					
				}
			}
		}
			logger.info("################ InsertIntoDB Completed for PR No - "+pDTO.getPrNO()+" #######################");
			return serviceMsg;
} 
	
	private String insertPOAuditTrail(int poStatus, String poNO, String poStatusDate)throws Exception 
	{
		Timestamp statusTime = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmtPOAudtUpd = null;
		ResultSet rsk = null;
		try
		{
			int sd=0;
			
			try
			{
				if(poStatusDate != null && !poStatusDate.equals("")) 
					statusTime = Utility.stringToTimestampAmPm(poStatusDate);
				else
					return Constants.PO_STATUS_WS_MSG_MANDATORY_STATUS_DATE;
			}
			catch(ParseException pe)
			{
				logger.error("::::::::::::::::::: Error in POStatusUpdate WebService -------->",pe);
				pe.printStackTrace();
				return Constants.PO_STATUS_WS_MSG_INVALID_STATUS_DATE;
			}
			
			String SELECT_PO_STATUS_DATE = "select timestampdiff(8,char( '"+statusTime + "' - (SELECT STATUS_DATE  FROM PO_HEADER where PO_NO = ? ) ) ) " +
			"AS SD from sysibm.sysdummy1";
			
			pstmt = connection.prepareStatement(SELECT_PO_STATUS_DATE);
			pstmt.setString(1, poNO);
			
			rsk = pstmt.executeQuery();
			
			while(rsk.next())
			{
			   sd = rsk.getInt("SD");
			}
			
			DBConnectionManager.releaseResources(pstmt, rsk);
			
			pstmtPOAudtUpd = connection.prepareStatement(DBQueries.UPDATE_PO_STATUS_AUDIT_TRAIL);
			System.out.println("poNO  :: "+poNO);
			pstmtPOAudtUpd.setString(1, poNO);
			System.out.println("poStatus  :: "+poStatus);
			pstmtPOAudtUpd.setInt(2, poStatus);
			System.out.println("statusTime  :: "+statusTime);
			pstmtPOAudtUpd.setTimestamp(3, statusTime);
			System.out.println("sd  :: "+sd);
			pstmtPOAudtUpd.setInt(4, sd);
			
			int res = pstmtPOAudtUpd.executeUpdate();
			
			DBConnectionManager.releaseResources(pstmtPOAudtUpd, null);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.info("Exception in PO Dispatch  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());
			// TODO: handle exception
		}
		finally
		{
			DBConnectionManager.releaseResources(pstmt, rsk);
			DBConnectionManager.releaseResources(pstmtPOAudtUpd, null);
		}
		return Constants.PO_STATUS_WS_MSG_SUCCESS;
	}

	/**
	 * This method will update status in table "PO_HEADER"
	 * @param poStatus
	 * @param poNO
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */
	public String updatePOStatus(int poStatus,String poNO, String poStatusDate)
	throws DAOException,VirtualizationServiceException
	{
		PreparedStatement pst=null;
		String serviceMsg="";
		try
		{
			Timestamp statusTime = null;
			
			if(poStatusDate != null && !poStatusDate.equals("")) 
				statusTime = Utility.stringToTimestampAmPm(poStatusDate);
			
			// updated by Shilpa khanna To Update Remarks
				pst = connection.prepareStatement("UPDATE PO_HEADER SET PO_STATUS=?, STATUS_DATE=? WHERE PO_NO=? ");
				pst.setInt(1, poStatus);
				pst.setTimestamp(2, statusTime);
				pst.setString(3, poNO);
							
				pst.executeUpdate();
				pst.clearParameters();
				
				pst.close();
				
				String sql = "Insert into PO_STATUS_AUDIT_TRAIL(PO_NO ,PO_STATUS ,PO_STATUS_DATE ,CREATED_ON, TAT) values(?,?,?,current timestamp, timestampdiff(8, char(postatusdate-(select STATUS_DATE from PO_HEADER where PO_NO=?))))";
				pst = connection.prepareStatement(sql.replaceAll("postatusdate", "'"+statusTime.toString()+"'"));
				logger.info("PO_NO  ::  "+poNO);
				pst.setString(1, poNO);
				logger.info("poStatus  ::  "+poStatus);
				pst.setInt(2, poStatus);
				logger.info("statusTime  ::  "+statusTime);
				pst.setTimestamp(3, statusTime);
//				logger.info("statusTime  ::  "+statusTime);
//				pst.setTimestamp(4, statusTime);
				logger.info("PO_NO  ::  "+poNO);
				pst.setString(4, poNO);
				int res = pst.executeUpdate();
				serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
			}
			catch (Exception e) 
			{
				serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
				logger.error("Exception error occurred in updatePOStatus.."+e.getMessage());
				throw new VirtualizationServiceException(e.getMessage());
			
			}
			finally 
			{
				DBConnectionManager.releaseResources(pst, null);
			}
			return serviceMsg;
		}
	
	/**
	 * This method is for:-
	 * Inserting record in PO_DETAILS Table 
	 * 
	 * @param pDTO
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */
	
	public String insertPODetails(POStatusUpdateDto pDTO)throws DAOException,VirtualizationServiceException
	{
		PreparedStatement pst=null;
		String serviceMsg="";
		try 
		{
			pst = connection.prepareStatement("INSERT INTO PO_DETAILS(PO_NO, EXT_PRODUCT_ID, PO_QTY, PRODUCT_ID) VALUES(?,?,?,?)");
		
			//Getting Product List from the DTO
			List prdListPOD= new ArrayList();
			prdListPOD=pDTO.getPoPrdID();
			logger.info("Inside method insertPODetails Product List size ---------->"+prdListPOD.size());
				
			for (int i=0;i<prdListPOD.size();i++)
			{
				pst.setString(1, (pDTO.getPoNO()));
				POStatusProductBean bean= (POStatusProductBean)prdListPOD.get(i);
				pst.setString(2, (bean.getProductid()));						
				pst.setInt(3, bean.getPrdQty());
				pst.setInt(4, bean.getIntProdID());
				int resultPODetals=	pst.executeUpdate();
				logger.info("PO Details inserted for Product Id -------------------->"+bean.getProductid());
				pst.clearParameters();
			}
			serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		} 
		catch (Exception e) 
		{
			serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
			e.printStackTrace();
				
			logger.error("Exception error occurred in insertPODetails.."+e.getMessage());
			throw new VirtualizationServiceException(e.getMessage());
			
		}
		finally
		{
			DBConnectionManager.releaseResources(pst, null);
		}
		return serviceMsg;
	}
	
	/**
	 * This method is to insert details in INVOICE_HEADER,INVOICE_DETAILS,DP_STOCK_INVENTORY  Table
	 * Select data from tables VR_ACCOUNT_DETAILS,DP_DISTRIBUTOR_MAPPING  
	 * @param pDTO
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */

	public String insertInvoiceDetails(POStatusUpdateDto poDTO)throws DAOException,VirtualizationServiceException
	{
		int resultCount=0;
		ResultSet CheckDistID_ProdIDRS= null;
		ResultSet rset =null;
		ResultSet rs=null;
		PreparedStatement pst=null;
		String serviceMsg ="";
		try
		{
			String strInvNo = "";
			
			pst = connection.prepareStatement("select INV_NO from INVOICE_HEADER where PO_NO=? with ur");
			pst.setString(1, poDTO.getPoNO());
			logger.info("PO NO  ::  "+poDTO.getPoNO());
			rset = pst.executeQuery();
			
			if(rset.next())
				strInvNo = rset.getString("INV_NO");
			
			logger.info("INV NO  ::  "+strInvNo);
			rset.close();
			pst.close();
			
			rset = null;
			pst = null;
			
			if(strInvNo.equals(""))
			{
				logger.info("...................>> "+strInvNo);
				pst = connection.prepareStatement("INSERT INTO INVOICE_HEADER(PO_NO, INV_NO, INV_DATE, DC_NO, DC_DT, DD_CHEQUE_NO, DD_CHEQUE_DATE, CREATE_DATE, STATUS , CATEGORY_CODE ) VALUES(?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, 'INTRANSIT' , (select CATEGORY_CODE from PO_HEADER where PO_NO=? ) )");
				pst.setString(1, poDTO.getPoNO());
				pst.setString(2,poDTO.getInvoiceNo());
				java.sql.Date sqlInvoiceDt  = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getInvoiceDT()).getTime());
				pst.setDate(3,sqlInvoiceDt);
				pst.setString(4,poDTO.getDc_no());
				
				
				java.sql.Date sqlDCDt  = null;
				
				if(poDTO.getDc_dt()!=null && !(poDTO.getDc_dt().equalsIgnoreCase("")))
				{
					try
					{
						sqlDCDt= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getDc_dt()).getTime());
					}
					catch(ParseException pe)
					{
						sqlDCDt=null;
					}
				}
				pst.setDate(5,sqlDCDt);
				
				pst.setString(6,poDTO.getDd_cheque_no());
				java.sql.Date sqlChequeDt=null;
				if(poDTO.getDd_cheque_dt()!=null&&!(poDTO.getDd_cheque_dt()).equals(null)&&!(poDTO.getDd_cheque_dt()==""))
				{
					try
					{
						sqlChequeDt= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(poDTO.getDd_cheque_dt()).getTime());
					}
					catch(ParseException pe)
					{
						sqlChequeDt=null;
					}
				}
				pst.setDate(7,sqlChequeDt);
				pst.setString(8,poDTO.getPoNO());
				logger.info(poDTO.getPoNO()+poDTO.getInvoiceNo()+sqlInvoiceDt+":"+poDTO.getDc_no()+sqlDCDt+"::"+poDTO.getDd_cheque_no()+sqlChequeDt);
			//	PO132000553 12-13/VATINV6/0002 2012-06-24: DC6100995 2012-06-24::null
				resultCount = pst.executeUpdate();
				
				logger.info("Data inserted in Invoice Header DC No ------------->"+poDTO.getDc_no()+" : "+resultCount);
				pst.clearParameters();
			}
			else
			{
				pst = connection.prepareStatement("update INVOICE_HEADER set DC_NO=?, DC_DT=?, DD_CHEQUE_NO=?, DD_CHEQUE_DATE=?, STATUS='INTRANSIT' WHERE INV_NO=?");
				pst.setString(1,poDTO.getDc_no());
				java.sql.Date sqlDCDt  = null;
				if(poDTO.getDc_dt()!=null && !(poDTO.getDc_dt().equalsIgnoreCase("")))
				{
					try
					{
						sqlDCDt= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getDc_dt()).getTime());
					}
					catch(ParseException pe)
					{
						sqlDCDt=null;
					}
				}
				pst.setDate(2,sqlDCDt);
				
				pst.setString(3,poDTO.getDd_cheque_no());
				java.sql.Date sqlChequeDt=null;
				if(poDTO.getDd_cheque_dt()!=null&&!(poDTO.getDd_cheque_dt()).equals(null)&&!(poDTO.getDd_cheque_dt()==""))
				{
					try
					{
						sqlChequeDt= new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(poDTO.getDd_cheque_dt()).getTime());
					}
					catch(ParseException pe)
					{
						sqlChequeDt=null;
					}
				}
				pst.setDate(4,sqlChequeDt);
				
				pst.setString(5, poDTO.getInvoiceNo());
				
				resultCount = pst.executeUpdate();
				logger.info("Data updated in Invoice Header DC No ------------->"+poDTO.getDc_no()+" : "+resultCount);
				pst.clearParameters();
				
				if(resultCount<1)
				{
					serviceMsg=Constants.INVALID_INV_NO_WS_MSG_FAIL;
					return serviceMsg;
				}
			}

			// Inserting record in Invoice_DETAILS Table
		
			pst = connection.prepareStatement("INSERT INTO INVOICE_DETAILS(INV_NO, EXT_PRODUCT_ID, INV_QTY, PRODUCT_ID)VALUES(?,?,?,?)");
		 	List prdListINVD= new ArrayList();
			prdListINVD = poDTO.getPoPrdID();
			
			logger.info("INVOICE DETAILS  ::  "+prdListINVD.size());
			
			for (int i=0;i<prdListINVD.size();i++)
			{
				POStatusProductBean bean= (POStatusProductBean)prdListINVD.get(i);
				pst.setString(1,poDTO.getInvoiceNo());
				pst.setString(2, (bean.getProductid()));						
				pst.setInt(3, bean.getInvQty());
				pst.setInt(4, bean.getIntProdID());
				pst.executeUpdate();
				pst.clearParameters();			
			}
	
			/***
			 *	Inserting record in DP_STOCK_INVENTORY Table 
			 */
			int internal_distId=0;		
			

			pst=connection.prepareStatement("SELECT PR_DIST_ID FROM DP_PR_HEADER WHERE PR_NO=? WITH UR ");
			pst.setInt(1,Integer.parseInt(poDTO.getPrNO().trim()));
			rs=pst.executeQuery();
			
			if(rs.next())
				internal_distId=rs.getInt("PR_DIST_ID");
			
			pst.clearParameters();
			
			logger.info("Distributor Id for which Stock inventory to be updated ---------> "+internal_distId);
		    logger.info(">> Validation Starts here<<");	
		    //************* Validating all the STBs for this PO  
			//Getting Product List from the DTO
			List prdListPOD= new ArrayList();
			prdListPOD=poDTO.getPoPrdID();
			ErrorStbDto errorStbDto=null;
			List <ErrorStbDto> stbDtoList = new ArrayList<ErrorStbDto>();
			String duplicateSTBSLs = ""; 
			
			for(int ii=0;ii<prdListPOD.size();ii++)
			{
				POStatusProductBean bean= (POStatusProductBean)prdListPOD.get(ii);
				List serialNoList = bean.getSerialNos();
				
				int distId=0, prodID =0;	
				   
		    	if(pst != null)
				pst.clearParameters();				
				pst = connection.prepareStatement("select PRODUCT_ID,PR_DIST_ID from DP_PRODUCT_MASTER PM ,DP_PR_HEADER PH where PH.PR_NO = ? and PM.BT_PRODUCT_CODE = ? and PM.CM_STATUS= 'A' and PM.CIRCLE_ID= ( select CIRCLE_ID from VR_ACCOUNT_DETAILS where ACCOUNT_ID = PH.PR_DIST_ID ) with ur");
			
				pst.setInt(1, Integer.parseInt(poDTO.getPrNO().trim()));
				pst.setString(2, bean.getProductid());
				rs = pst.executeQuery();
						
				if(rs.next())
				{
					 distId = rs.getInt("PR_DIST_ID"); 
					 prodID = rs.getInt("PRODUCT_ID");			
				}

				for (int ss=0;ss<serialNoList.size();ss++)
				{
					// Populating ErrorStbDto to check Error STB into tables.
					errorStbDto=new ErrorStbDto();
					errorStbDto.setPrNO(Integer.parseInt(poDTO.getPrNO().trim())); //PR_NO
					errorStbDto.setPoNo(poDTO.getPoNO());
					errorStbDto.setPoDt(new Timestamp(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getPoDate()).getTime()));
					errorStbDto.setDcNo(poDTO.getDc_no());
					errorStbDto.setDcDt(new Timestamp(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getDc_dt()).getTime()));
					errorStbDto.setInvoiceNo(poDTO.getInvoiceNo());
					errorStbDto.setBtProductCode(bean.getProductid()); 	
					errorStbDto.setProdQty(bean.getPrdQty());
					errorStbDto.setSerialNo(serialNoList.get(ss).toString());
					errorStbDto.setDistId(distId); 
					errorStbDto.setProdId(prodID);
					
					errorStbDto.setConflictStatus(0);
										
					stbDtoList.add(errorStbDto);
				}
			}
								
            // Checking into tables for existing PO, if available then insert into DP_ERROR_PO_DETAIL	
			stbDtoList = isPOSerialNoAvailable(stbDtoList);
			
			
			for (int ss=0;ss<stbDtoList.size();ss++)
			{
				errorStbDto = stbDtoList.get(ss);
				if(errorStbDto.getConflictStatus()>0)
				{
					duplicateSTBSLs = duplicateSTBSLs+","+errorStbDto.getSerialNo();
				}					
			}

			//************* Validation Ends
		    logger.info("Validation Ends here, Error STBs found : "+duplicateSTBSLs);
		    
			pst=connection.prepareStatement("INSERT INTO DP_STOCK_INVENTORY(PRODUCT_ID,SERIAL_NO,DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE, INV_NO,MSISDN) VALUES(?,?,?,?,?,?)");
			//boolean flagInsert=true;
			int batchCount = 0;
			int dist_ID = 0,prod_ID = 0, receipt = 0, closingBalance = 0;
			List prdListSINVT= new ArrayList();
			prdListSINVT=poDTO.getPoPrdID();
			int index=0;
			String msisdn="";
			String serialno="";
			POStatusProductBean bean= null;
			int dpProdID =0;
			String str = "";
			java.sql.Date sqlDistPurchaseDt = null;
			for (int i=0;i<prdListSINVT.size();i++)
			{
				bean= (POStatusProductBean)prdListINVD.get(i);
				dpProdID =getDPProductID(bean.getProductid(),Integer.parseInt(poDTO.getPrNO().trim())); 
				logger.info("Stock being inserted for Product id -----------------> "+dpProdID);					
				
				for(int j=0;j<bean.getSerialNos().size();j++)
				{
					str = bean.getSerialNos().get(j).toString();
					
					// Excape if STB already available into DP tables
					logger.info("Error PO Serial No. : "+str);
					if(duplicateSTBSLs.contains(str))
					{
						continue;   // To escape from inserting into Stock Inventory Deatails
					}
					// validation ends 
					
					if(str!=null&&!str.equals("")&&str!="")
					{
						index=str.indexOf(",");
						if(index==-1)
						{
							serialno=str;
						}
						else
						{
							String serial[]=str.split(",");
							serialno=serial[0];
							msisdn=serial[1];
						}
					}
					
					pst.setInt(1, dpProdID);
					pst.setString(2, serialno);
					pst.setInt(3, internal_distId);
					try
					{
						sqlDistPurchaseDt  = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a").parse(poDTO.getInvoiceDT()).getTime());
					}
					catch(ParseException pe)
					{
						sqlDistPurchaseDt = null;
					}
					pst.setDate(4,sqlDistPurchaseDt);
					pst.setString(5,poDTO.getInvoiceNo());
					pst.setString(6,msisdn);
					pst.executeUpdate();
					pst.clearParameters();
					
				}
			}
			serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		}
		catch (SQLException e) 
		{
			serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
			e.printStackTrace();
			logger.info(e.getNextException());
			try 
			{
				connection.rollback();	
			} 
			catch (SQLException ex) 
			{
				//e.printStackTrace();
				logger.error("rollback exception in insertInvoiceDetails() method..");
				throw new DAOException(e.getMessage());
			}
			logger.error("Exception error occurred in insertInvoiceDetails().."+e.getMessage());
			throw new DAOException(e.getMessage());
		
		}
		catch (Exception e) 
		{
			serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
			e.printStackTrace();
			
			try 
			{
				connection.rollback();	
			} 
			catch (Exception ex) 
			{
				//e.printStackTrace();
				logger.error("rollback exception in insertInvoiceDetails() method..");
				throw new VirtualizationServiceException(e.getMessage());
			}
			logger.error("Exception error occurred in insertInvoiceDetails().."+e.getMessage());
			throw new VirtualizationServiceException(e.getMessage());
			
		}
		finally 
		{
			DBConnectionManager.releaseResources(pst, rset);
			DBConnectionManager.releaseResources(null, rs);
		}
		return serviceMsg;
	}
	
	/* This method checks whether PO serial no. is present in tables if so then return true; based on this an Error Log record is inserted
	 * in the table for further action, otherwise PO serial is inserted into DP_STOCK_INVENTORY in the calling method.
	 */
	
	public List <ErrorStbDto> isPOSerialNoAvailable(List<ErrorStbDto> stbDtoList)throws DAOException,VirtualizationServiceException
	{
		logger.info("isPOSerialNoAvailable method starts");
		ResultSet rs = null; 
		PreparedStatement pst =null;
		
	try {  
		
		Map<String ,ErrorStbDto> stbSerialNosMap = new HashMap<String , ErrorStbDto>();
		ErrorStbDto errorStbDto=null;
		for (int ss=0;ss<stbDtoList.size();ss++)
		{
			stbSerialNosMap.put(stbDtoList.get(ss).getSerialNo(),stbDtoList.get(ss));
		}
		
		String stbSerialNos = getSTBSerialNos(stbDtoList);		
		logger.info("stbSerialNos : "+stbSerialNos);
		//stbSerialNos = "'00032012056','01286032316','01286032555','05032012071'";
			
		// Validating STBs in the table DP_STOCK_INVENTORY
		String sql ="SELECT SERIAL_NO,PRODUCT_ID,DISTRIBUTOR_ID, DISTRIBUTOR_PURCHASE_DATE,INV_NO,STATUS from DP_STOCK_INVENTORY where SERIAL_NO IN ( ? ) with ur";
		pst = connection.prepareStatement(sql.replace("?",stbSerialNos));
		rs = pst.executeQuery();
		String invNo ="";
		while(rs.next())
		{	
		    String serialNo = rs.getString("SERIAL_NO");
		    invNo =  rs.getString("INV_NO");
			errorStbDto = (ErrorStbDto)stbSerialNosMap.get(serialNo);	
			errorStbDto.reset();
			
			errorStbDto.setOldProdId(rs.getInt("PRODUCT_ID")); 	
			logger.info("1 SL & ProdID : "+serialNo+" :: "+errorStbDto.getOldProdId());
			ResultSet rsInvNo = null; 
			PreparedStatement pstInvNo = null;
			pstInvNo = connection.prepareStatement("select PO_NO,ACCEPT_DATE from INVOICE_HEADER where inv_no = ? with ur");
			//pstInvNo.setString(1, serialNo);
			pstInvNo.setString(1, invNo);
			
			rsInvNo = pstInvNo.executeQuery();  
			
			if(rsInvNo.next())
			{
				errorStbDto.setOldPoNo(rsInvNo.getString("PO_NO"));
				errorStbDto.setOldPoAcceptDt(rsInvNo.getTimestamp("ACCEPT_DATE"));				
			}
			else
			{
				errorStbDto.setOldPoNo(rs.getString("INV_NO"));
				errorStbDto.setOldPoAcceptDt(rs.getTimestamp("DISTRIBUTOR_PURCHASE_DATE"));	
			}	
			
			errorStbDto.setOldDistId(rs.getInt("DISTRIBUTOR_ID"));
			errorStbDto.setOldStbStatus(rs.getInt("STATUS")+"");
			errorStbDto.setConflictStatus(1);
			
			// Inserting Error PO details  
			insertErrorPODetails(errorStbDto);
		}
		
		// Validating STBs in the table DP_REV_INVENTORY_CHANGE
		
		if(!"".equals(stbSerialNos))
		{
			String sql2 ="SELECT IC.DEFECTIVE_SR_NO as DEFECTIVE_SR_NO, IC.DEFECTIVE_PRODUCT_ID as DEFECTIVE_PRODUCT_ID, IC.DEFECTIVE_DIST_ID as DEFECTIVE_DIST_ID, IC.REQ_ID as REQ_ID, IC.STATUS as STATUS, IC.INV_CHANGE_DATE as INV_CHANGE_DATE, IC.COLLECTION_ID as COLLECTION_ID, IC.DEFECT_ID as DEFECT_ID, '' as REV_COLL_DT from DP_REV_INVENTORY_CHANGE IC where IC.DEFECTIVE_SR_NO IN ( ? ) and IC.STATUS = 'REC' with ur";
			
			pst = connection.prepareStatement(sql2.replace("?",stbSerialNos));
			
			rs = pst.executeQuery();  					
			while(rs.next())
			{ 
				errorStbDto = stbSerialNosMap.get(rs.getString("DEFECTIVE_SR_NO"));
				
				errorStbDto.reset();
				
				errorStbDto.setOldProdId(rs.getInt("DEFECTIVE_PRODUCT_ID")); 
				errorStbDto.setOldDistId(rs.getInt("DEFECTIVE_DIST_ID"));
				errorStbDto.setOldStbStatus(rs.getString("STATUS")); 
				
				errorStbDto.setOldInvChangeDt(rs.getTimestamp("INV_CHANGE_DATE")); 
				errorStbDto.setOldCollectionId(rs.getString("COLLECTION_ID"));
				errorStbDto.setOldDefectId(rs.getInt("DEFECT_ID")); 
			//	errorStbDto.setOldReverceCollectionDt(rs.getTimestamp("REV_COLL_DT"));	
				errorStbDto.setConflictStatus(2);
			
				// Inserting Error PO details  
				insertErrorPODetails(errorStbDto);
							
			}
		}		
		
		// Validating STBs in the table DP_REV_STOCK_INVENTORY
		if(!"".equals(stbSerialNos))
		{
			String sql3 = "SELECT SERIAL_NO_COLLECT, PRODUCT_ID ,CREATED_BY, CREATED_ON,STATUS,COLLECTION_DATE ,COLLECTION_ID, DEFECT_ID ,INV_CHANGE_DT from DP_REV_STOCK_INVENTORY where  SERIAL_NO_COLLECT IN (?) and status in ('COL','ERR')  with ur";
			pst = connection.prepareStatement(sql3.replace("?",stbSerialNos));
			rs = pst.executeQuery();  				
			while(rs.next())
			{ 
				String serialNo = rs.getString("SERIAL_NO_COLLECT");				
				errorStbDto = stbSerialNosMap.get(serialNo);		
				errorStbDto.reset();
				
				errorStbDto.setOldProdId(rs.getInt("PRODUCT_ID")); 
				errorStbDto.setOldDistId(rs.getInt("CREATED_BY"));
				errorStbDto.setOldStbStatus(rs.getString("STATUS"));			
				errorStbDto.setOldInvChangeDt(rs.getTimestamp("INV_CHANGE_DT")); 
				errorStbDto.setOldCollectionId(rs.getString("COLLECTION_ID"));
				errorStbDto.setOldDefectId(rs.getInt("DEFECT_ID")); 
				errorStbDto.setConflictStatus(3);
				
				// Inserting Error PO details  
				insertErrorPODetails(errorStbDto);
			}
		}
		
		
		// Validating STBs in the table DP_REV_CHURN_INVENTORY
		if(!"".equals(stbSerialNos))
		{
			//Commented by Nazim Hussain as it does not bring Distributor ID for Churn Error Records
			//String sql4 = "SELECT SERIAL_NUMBER,REQ_ID,PRODUCT_ID, CREATED_ON, STATUS from DP_REV_CHURN_INVENTORY WHERE SERIAL_NUMBER IN (?)   AND STATUS in ('COL','ERR','REC')  WITH UR";
			//Changed by Nazim Hussain to bring Distributor ID for Churn Error Records
			String sql4 = "SELECT SERIAL_NUMBER,REQ_ID,PRODUCT_ID, CREATED_BY, CREATED_ON, STATUS from DP_REV_CHURN_INVENTORY WHERE SERIAL_NUMBER IN (?)   AND STATUS in ('COL','ERR','REC')  WITH UR";
			
			pst = connection.prepareStatement(sql4.replace("?",stbSerialNos));
			rs = pst.executeQuery();  					
			while(rs.next())
			{  	
				errorStbDto = stbSerialNosMap.get(rs.getString("SERIAL_NUMBER"));	
				errorStbDto.reset();
				
				errorStbDto.setOldProdId(rs.getInt("PRODUCT_ID")); 
				errorStbDto.setOldPoNo(rs.getString("REQ_ID"));
				errorStbDto.setOldDistId(rs.getInt("CREATED_BY"));
				errorStbDto.setOldStbStatus(rs.getString("STATUS"));			
				errorStbDto.setOldInvChangeDt(rs.getTimestamp("CREATED_ON")); 
				
				errorStbDto.setConflictStatus(7);

				// Inserting Error PO details  
				insertErrorPODetails(errorStbDto);
			}
		}		
		
		logger.info("isPOSerialNoAvailable method ends");
	} catch (SQLException e) {
		e.printStackTrace();
		try {
			connection.rollback();	
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.error("rollback exception in isPOSerialNoAvailable method..");
			throw new DAOException(e.getMessage());
		}
	logger.error("Exception error occurred in isPOSerialNoAvailable.."+e.getMessage());
	throw new DAOException(e.getMessage());
	} catch (Exception e) {
		e.printStackTrace();
			try {
				connection.rollback();	
			} catch (Exception ex) {
				//e.printStackTrace();
				logger.error("rollback exception in isPOSerialNoAvailable method..");
				throw new VirtualizationServiceException(e.getMessage());
			}
		logger.error("Exception error occurred in isPOSerialNoAvailable.."+e.getMessage());
		throw new VirtualizationServiceException(e.getMessage());
	} finally {
		DBConnectionManager.releaseResources(pst, rs);
	} 
	return stbDtoList;
  }	
	
	/**
	 * This method inserts data into DP_ERROR_PO_DETAIL incase of Error STB found.
	 * @param errorStbDto
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */
	
	public String insertErrorPODetails(ErrorStbDto errorStbDto)throws DAOException,VirtualizationServiceException
	{
		
		String serviceMsg ="";
		PreparedStatement pst =null;
		try 
		{
			pst = connection.prepareStatement("INSERT INTO DP_ERROR_PO_DETAIL( SERIAL_NO, PRODUCT_ID, DIST_ID, PR_NO, PO_NO, PO_DATE, DC_NO, DC_DATE, CONF_STATUS, CREATED_ON, OLD_PO_NO, OLD_PO_ACCEPT_DT, OLD_PRODUCT_ID, OLD_DIST_ID, OLD_STB_STATUS, OLD_INV_CHANGE_DT, OLD_COLLECTION_ID, OLD_DEFECT_ID, OLD_REV_COLL_DT, OLD_REV_DC_NO, OLD_REV_DC_DATE, INV_NO) VALUES(?,?,?,?,?,?,?,?,?,current timestamp,?,?,?,?,?,?,?,?,?,?,?,?)");
			logger.info("Inside method insertErrorPODetails to insert Error STB details.");
			logger.info("PR No   : "+errorStbDto.getPrNO());
			logger.info("PO no   : "+errorStbDto.getPoNo());
			logger.info("PO DT   : "+errorStbDto.getPoDt());
			logger.info("DC No   : "+errorStbDto.getDcNo());
			logger.info("DC DT   : "+errorStbDto.getDcDt());
			logger.info("SL. No  : "+errorStbDto.getSerialNo());
			logger.info("Inv No. : "+errorStbDto.getInvoiceNo());
			logger.info("btProd code: "+errorStbDto.getBtProductCode());
			logger.info("getProdQty: "+errorStbDto.getProdQty());
		
			logger.info("prod id "+errorStbDto.getProdId());
			logger.info("dist id "+errorStbDto.getDistId());					
			logger.info("conf id "+errorStbDto.getConflictStatus());
			
			logger.info("old po no "+errorStbDto.getOldPoNo());	
			logger.info("old po accept dt "+errorStbDto.getOldPoAcceptDt());
			logger.info("old prod id "+errorStbDto.getOldProdId());		
			logger.info("old dist id "+errorStbDto.getOldDistId());		
			logger.info("old stb status "+errorStbDto.getOldStbStatus());	
			logger.info("inv change dt "+errorStbDto.getOldInvChangeDt());		
			logger.info("Collection ID "+errorStbDto.getOldCollectionId());		
			logger.info("Defect ID "+errorStbDto.getOldDefectId());		
			logger.info("stb getReverceCollectionDt "+errorStbDto.getOldReverceCollectionDt());	
			logger.info("stb getReverce dc no "+errorStbDto.getOldRevDcNo());
			logger.info("stb getReverce dc dt "+errorStbDto.getOldRevDcDt());	
			//SERIAL_NO, PRODUCT_ID, DIST_ID, PR_NO, PO_NO, PO_DATE, DC_NO, DC_DATE, CONF_STATUS, CREATED_ON, 
			//OLD_PO_NO, OLD_PO_ACCEPT_DT, OLD_PRODUCT_ID, OLD_DIST_ID, OLD_STB_STATUS, OLD_INV_CHANGE_DT, OLD_COLLECTION_ID,
			//OLD_DEFECT_ID, OLD_REV_COLL_DT, OLD_REV_DC_NO, OLD_REV_DC_DATE, INV_NO) VALUES(?,?,?,?,?,?,?,?,?,
			//current timestamp,?,?,?,?,?,?,?,?,?,?,?,?)
				
			pst.setString(1, errorStbDto.getSerialNo());
			pst.setInt(2, errorStbDto.getProdId());
			pst.setInt(3, errorStbDto.getDistId());		
			pst.setInt(4,errorStbDto.getPrNO());
			pst.setString(5, errorStbDto.getPoNo());
			pst.setTimestamp(6, errorStbDto.getPoDt());
			pst.setString(7, errorStbDto.getDcNo());	
			pst.setTimestamp(8, errorStbDto.getDcDt());
			pst.setInt(9, errorStbDto.getConflictStatus());
			pst.setString(10, errorStbDto.getOldPoNo());	
			pst.setTimestamp(11, errorStbDto.getOldPoAcceptDt());
			pst.setInt(12, errorStbDto.getOldProdId());		
			pst.setInt(13, errorStbDto.getOldDistId());		
			pst.setString(14, errorStbDto.getOldStbStatus());	
			pst.setTimestamp(15, errorStbDto.getOldInvChangeDt());		
			pst.setString(16, errorStbDto.getOldCollectionId());		
			pst.setInt(17, errorStbDto.getOldDefectId());		
			pst.setTimestamp(18, errorStbDto.getOldReverceCollectionDt());	
			pst.setString(19, errorStbDto.getOldRevDcNo());
			pst.setTimestamp(20, errorStbDto.getOldRevDcDt());		
			pst.setString(21, errorStbDto.getInvoiceNo());		
					
			pst.executeUpdate();
			logger.info("Error Details inserted into DP_ERROR_PO_DETAIL for PO No. : "+errorStbDto.getPoNo()+" & Serial No. : "+errorStbDto.getSerialNo());
			pst.clearParameters();
			serviceMsg=Constants.PO_STATUS_WS_MSG_SUCCESS;
		} 
		catch (SQLException e) 
		{
			serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
			e.printStackTrace();
			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex) 
			{
				ex.printStackTrace();
				logger.error("rollback exception in insertErrorPODetails method..");
				throw new DAOException(e.getMessage());
			}
			logger.error("Exception error occurred in insertErrorPODetails.."+e.getMessage());
			throw new DAOException(e.getMessage());
	    }
		catch (Exception e) 
		{
			serviceMsg= Constants.OPEN_STOCK_WS_MSG_OTHERS;
			e.printStackTrace();
			try {
				connection.rollback();	
			} catch (Exception ex) {
				//e.printStackTrace();
				logger.error("rollback exception in insertErrorPODetails method..");
				throw new VirtualizationServiceException(e.getMessage());
			}
		logger.error("Exception error occurred in insertErrorPODetails.."+e.getMessage());
		throw new VirtualizationServiceException(e.getMessage());
	  }finally {
		  DBConnectionManager.releaseResources(pst, null);
	}
	return serviceMsg;
  }
	
	/*
	 * Method to return stb serial nos. from STB list for which table is to be checked.  
	 * @param stbDtoList
	 * @return stbSerialNos
	 */
	
	public String getSTBSerialNos(List<ErrorStbDto> stbDtoList)
	{
		String stbSerialNos = "";
	   
		for (int ss=0;ss<stbDtoList.size();ss++)
		{
			stbSerialNos = stbSerialNos+",'"+stbDtoList.get(ss).getSerialNo()+"'";
		}
		stbSerialNos=stbSerialNos.replaceFirst(",", "");
	
	return stbSerialNos;
	}
	
	/**
	 * This method to fetch records from table DP_PR_DETAILS
	 * @param extprdID
	 * @param pRID
	 * @return
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */
	
	public int getDPProductID(String extprdID,int pRID)throws DAOException,VirtualizationServiceException
	{
		logger.info("Inside getDPProductID PRD ID & PR ID   -----------------> "+extprdID+" & "+pRID);
		ResultSet prodIDRS= null;
		int prodID=0;
		PreparedStatement pst1=null; 
		try{
			pst1 = connection.prepareStatement("select PRODUCT_ID from DP_PR_DETAILS where PR_NO=? and EXT_PRODUCT_ID=? with ur");
			logger.info("PR ID  ::  "+pRID);
			logger.info("BT PROD ID  ::  "+extprdID);
			
			pst1.setInt(1, pRID);
			pst1.setString(2,extprdID);
			prodIDRS =pst1.executeQuery();
			pst1.clearParameters();
			
			while (prodIDRS.next()) {
				System.out.println("=== result is yes========");
			 	 prodID = prodIDRS.getInt(1);
	 		 
			}
			System.out.println("=== product id  :"+prodID);
			if(prodID==0){
				throw new Exception("Error in product Id");
			}
		}catch (SQLException e) {
			logger.info(e.getNextException());
			e.printStackTrace();
			try {
				connection.rollback();	
			} catch (SQLException ex) {
				ex.printStackTrace();
				logger.error("rollback exception in getDPProductID method..");
				throw new DAOException(ex.getMessage());
				
			}
		logger.error("Exception error occurred in getDPProductID.."+e.getMessage());
		throw new DAOException(e.getMessage());
		
	}catch (Exception e) {
		e.printStackTrace();
				try {
					connection.rollback();	
				} catch (Exception ex) {
					//e.printStackTrace();
					logger.error("rollback exception in getDPProductID method..");
					throw new VirtualizationServiceException(e.getMessage());
					// TODO: handle exception
				}
			logger.error("Exception error occurred in getDPProductID.."+e.getMessage());
			throw new VirtualizationServiceException(e.getMessage());
			
		}finally {
				if((pst1 !=null )||(connection !=null)){
					try {
						pst1.close();
						pst1=null;
						prodIDRS.close();
						prodIDRS=null;
						//logger.info("prepareStatement release..");
												
					} catch (Exception exx) {
						logger.info("Exception.."+exx.getMessage());
						
					}
				}
			}
		//logger.info("Executed getDPProductID() .."+extprdID+"..."+pRID);
	return prodID;	
		
	}
}
