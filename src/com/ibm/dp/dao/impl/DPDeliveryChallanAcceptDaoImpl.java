package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.dp.common.DBQueries;
import com.ibm.dp.dao.DPDeliveryChallanAcceptDao;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;

public class DPDeliveryChallanAcceptDaoImpl  extends BaseDaoRdbms implements DPDeliveryChallanAcceptDao 
{
	private static Logger logger = Logger.getLogger(DPDeliveryChallanAcceptDaoImpl.class.getName());
	
	protected static final String SQL_DELIVERY_CHALLAN_INIT_KEY = "SQL_DELIVERY_CHALLAN_INIT_KEY";
	protected static final String SQL_DELIVERY_CHALLAN_INIT = DBQueries.SQL_DELIVERY_CHALLAN_INIT;
	
	protected static final String SQL_REPORT_DC_INVOICE_HDR_KEY = "SQL_REPORT_DC_INVOICE_HDR_KEY";
	protected static final String SQL_REPORT_DC_INVOICE_HDR = DBQueries.SQL_REPORT_DC_INVOICE_HDR;	
	
	protected static final String SQL_ACCEPT_DC_STOCK_INVENT_KEY = "SQL_ACCEPT_DC_STOCK_INVENT_KEY";
	protected static final String SQL_ACCEPT_DC_STOCK_INVENT = DBQueries.SQL_ACCEPT_DC_STOCK_INVENT;
	
	protected static final String SQL_DC_STOCK_ALL_SERIALS_NO_KEY = "SQL_DC_STOCK_ALL_SERIALS_NO_KEY";
	protected static final String SQL_DC_STOCK_ALL_SERIALS_NO = DBQueries.SQL_DC_STOCK_ALL_SERIALS_NO;
	
	protected static final String SQL_INVOICE_ALL_SERIALS_NO = DBQueries.SQL_INVOICE_ALL_SERIALS_NO;
	

	
	
	public DPDeliveryChallanAcceptDaoImpl(Connection connection)
	{
		super(connection);
		
		queryMap.put(SQL_DELIVERY_CHALLAN_INIT_KEY, SQL_DELIVERY_CHALLAN_INIT);
		
		queryMap.put(SQL_REPORT_DC_INVOICE_HDR_KEY, SQL_REPORT_DC_INVOICE_HDR);
		
		queryMap.put(SQL_ACCEPT_DC_STOCK_INVENT_KEY, SQL_ACCEPT_DC_STOCK_INVENT);
		
		queryMap.put(SQL_DC_STOCK_ALL_SERIALS_NO_KEY, SQL_DC_STOCK_ALL_SERIALS_NO);
	}
	
	public ArrayList<DPDeliveryChallanAcceptDTO> getInitDeliveryChallanDao(long loginUserId) throws DAOException 
	{
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
		try
		{
			pstmt = connection.prepareStatement(queryMap.get(SQL_DELIVERY_CHALLAN_INIT_KEY));
			pstmt.setLong(1, loginUserId);
			
			rset = pstmt.executeQuery();
			
			arrReturn = fetchDeliveryChallanInitData(rset);
						
		}
		catch(SQLException sqlEx)
		{
			logger.error("SQL Exception occured while fetching initial data for Delivery Challan Accptance  ::  "+sqlEx.getMessage());
			throw new DAOException(sqlEx.getMessage());
		}
		catch(Exception e)
		{
			logger.error("Exception occured while fetching initial data for Delivery Challan Accptance  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		return arrReturn;
	}

	private ArrayList<DPDeliveryChallanAcceptDTO> fetchDeliveryChallanInitData(ResultSet rset) throws Exception
	{
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
		DPDeliveryChallanAcceptDTO  dpDCADto = null;
		String strDCDate = null;
		while(rset.next())
		{
			dpDCADto = new DPDeliveryChallanAcceptDTO();
			
			dpDCADto.setIntPRNo(rset.getInt("PR_NO"));
			dpDCADto.setStrPONo(rset.getString("PO_NO"));
			dpDCADto.setStrInvoiceNo(rset.getString("INV_NO"));
			dpDCADto.setStrDCNo(rset.getString("DC_NO"));
			dpDCADto.setStrStatus(rset.getString("STATUS"));
			strDCDate = rset.getString("DC_DT");
			strDCDate = Utility.convertDateFormat(strDCDate, "yyyy-MM-dd", "dd/MM/yyyy");
			dpDCADto.setStrDCDate(strDCDate);
			dpDCADto.setIsError(rset.getInt("IS_ERROR"));  // Added to disable action on PO Stock Acceptance 
			arrReturn.add(dpDCADto);
		}
		
		return arrReturn;
	}
	
	public ArrayList<DPDeliveryChallanAcceptDTO> reportDeliveryChallanDao(String[] arrCheckedDC, String dc_report_accept,
			long loginUserId)throws DAOException 
	{
		PreparedStatement pstmtInvoiceHdr = null;
		PreparedStatement pstmtStockInvent = null;
		PreparedStatement pstmtAccQty = null;
		
		PreparedStatement pstmtGetPoNo = null;
		PreparedStatement pstmtUpdPOHdr = null;
		PreparedStatement pstmtUpdPRHdr = null;
		PreparedStatement pstmtUpdInvoiceDet = null;
		PreparedStatement pstmtUpdAuditTrail = null;
		ResultSet rset	= null;
		
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
		try
		{
			connection.setAutoCommit(false);
			
			pstmtInvoiceHdr = connection.prepareStatement(queryMap.get(SQL_REPORT_DC_INVOICE_HDR_KEY));
			
			pstmtStockInvent = connection.prepareStatement(queryMap.get(SQL_ACCEPT_DC_STOCK_INVENT_KEY));
			
			pstmtUpdPOHdr 	   = connection.prepareStatement(DBQueries.UPDATE_PO_HEADER_INACTIVE);
			pstmtUpdPRHdr 	   = connection.prepareStatement(DBQueries.UPDATE_PR_HEADER_INACTIVE);
			pstmtUpdInvoiceDet = connection.prepareStatement(DBQueries.UPDATE_INVOICE_DETAILS);
			pstmtUpdAuditTrail = connection.prepareStatement(DBQueries.INSERT_AUDIT_TRAIL);
			pstmtAccQty = connection.prepareStatement(DBQueries.ACCEPTED_STOCK_DETAIL);
			
			int intAcceptDC = arrCheckedDC.length;
			int intUpdate = 0;
			
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			
			for(int i=0; i<intAcceptDC; i++)
			{
				//If the Delivery Challan is accepted by Distributor
				if(dc_report_accept.equals(Constants.DC_REPORT_ACCEPT))
				{
//					Updating Invoice_header against the Invoice which is accepted
					pstmtInvoiceHdr.setString(1, dc_report_accept);
					pstmtInvoiceHdr.setTimestamp(2, currentTime);
					pstmtInvoiceHdr.setString(3, arrCheckedDC[i]);
					
					intUpdate = pstmtInvoiceHdr.executeUpdate();
					
					//Updating  DP_Stock_inventory for the DC to make STB available for Distributor
					pstmtStockInvent.setString(1, arrCheckedDC[i]);
					intUpdate = pstmtStockInvent.executeUpdate();
					
					logger.info("\n\n\n intUpdate "+intUpdate);	
					
					if(intUpdate>0)
					{
						//Making an enrty in PO_AUDIT_TRAIL for the PO status update
						pstmtUpdAuditTrail.setString(1,arrCheckedDC[i]);
						pstmtUpdAuditTrail.setInt(2, Constants.PO_STATUS_PO_ACCEPT);
						pstmtUpdAuditTrail.setString(3,arrCheckedDC[i]);
						pstmtUpdAuditTrail.executeUpdate(); 
						
						//Updating  DP_PR_HEADER to make PR inactive
						pstmtUpdPRHdr.setInt(1, Constants.PO_PR_INACTIVE_STATUS);
						pstmtUpdPRHdr.setTimestamp(2, currentTime);
						pstmtUpdPRHdr.setString(3, arrCheckedDC[i]);
						pstmtUpdPRHdr.executeUpdate();
						
						//Updating  PO_HEADER to update status and make PO inactive
						pstmtUpdPOHdr.setInt(1, Constants.PO_PR_INACTIVE_STATUS);
						pstmtUpdPOHdr.setTimestamp(2, currentTime);
						pstmtUpdPOHdr.setInt(3, Constants.PO_STATUS_PO_ACCEPT);
						pstmtUpdPOHdr.setString(4, arrCheckedDC[i]);
						pstmtUpdPOHdr.executeUpdate();
						
						//Getting Accepted Quantity in DC
						//pstmtAccQty.setLong(1, loginUserId);
						pstmtAccQty.setString(1, arrCheckedDC[i]);
						rset = pstmtAccQty.executeQuery();
						
						int intAccptQty = 0;
						int intProdID = 0;
						
						while(rset.next())
						{
							intAccptQty = rset.getInt("ACCEPTED_QTY");
							intProdID = rset.getInt("PRODUCT_ID");
							
							//Updating Invoice_detail against accepted quantity
							pstmtUpdInvoiceDet.setInt(1,intAccptQty);
							pstmtUpdInvoiceDet.setString(2,arrCheckedDC[i]);
							pstmtUpdInvoiceDet.setInt(3,intProdID);
							pstmtUpdInvoiceDet.executeUpdate();
						}
					}
				}
				//Deleivery Challan which is marked as Shipment Error
				else
				{
//					Updating Invoice_header against the Invoice which is marked in Shipment Error
					pstmtInvoiceHdr.setString(1, dc_report_accept);
					pstmtInvoiceHdr.setTimestamp(2, null);			//As dC is not accepted
					pstmtInvoiceHdr.setString(3, arrCheckedDC[i]);
					
					intUpdate = pstmtInvoiceHdr.executeUpdate();
					
					//Making an enrty in PO_AUDIT_TRAIL for the PO status update
					logger.info("INVOICE NUMBER  ::  "+arrCheckedDC[i]);
					pstmtUpdAuditTrail.setString(1,arrCheckedDC[i]);
					pstmtUpdAuditTrail.setInt(2, Constants.PO_STATUS_PO_ERROR);
					pstmtUpdAuditTrail.setString(3,arrCheckedDC[i]);
					pstmtUpdAuditTrail.executeUpdate();
					
					//Updating  PO_HEADER to update status and make keeping PO active
					pstmtUpdPOHdr.setInt(1, Constants.PO_PR_ACTIVE_STATUS);
					pstmtUpdPOHdr.setTimestamp(2, null);
					pstmtUpdPOHdr.setInt(3, Constants.PO_STATUS_PO_ERROR);
					pstmtUpdPOHdr.setString(4, arrCheckedDC[i]);
					pstmtUpdPOHdr.executeUpdate();
				}
			}
			
			arrReturn = getInitDeliveryChallanDao(loginUserId);
			
			connection.commit();
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
			arrReturn = getInitDeliveryChallanDao(loginUserId);
			
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			try
			{
				DBConnectionManager.releaseResources(pstmtAccQty, rset);
				DBConnectionManager.releaseResources(pstmtInvoiceHdr, null);
				DBConnectionManager.releaseResources(pstmtStockInvent, null);
				DBConnectionManager.releaseResources(pstmtGetPoNo, null);
				DBConnectionManager.releaseResources(pstmtUpdPOHdr, null);
				DBConnectionManager.releaseResources(pstmtUpdPRHdr, null);
				DBConnectionManager.releaseResources(pstmtUpdInvoiceDet, null);
				DBConnectionManager.releaseResources(pstmtUpdAuditTrail, null);
				
			}catch (Exception e) {
				e.printStackTrace();
				throw new DAOException(e.getMessage());
			}
		}
		return arrReturn;
	}
	
	
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfDeliveryChallan(String invoiceNo) throws DAOException
	{

		PreparedStatement pstmt = null;
		
		ResultSet rset = null;
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
		try
		{
			connection.setAutoCommit(false);
			
			pstmt = connection.prepareStatement(queryMap.get(SQL_DC_STOCK_ALL_SERIALS_NO_KEY));
			
			pstmt.setString(1, invoiceNo);
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				DPDeliveryChallanAcceptDTO delAcceptDTO= new DPDeliveryChallanAcceptDTO();
				delAcceptDTO.setSerialNo(rset.getString("SERIAL_NO"));
				delAcceptDTO.setStrProductName(rset.getString("PRODUCT_NAME"));
				arrReturn.add(delAcceptDTO);
			}
			
			connection.commit();
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
						
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
		}
		return arrReturn;
	}
	
	public ArrayList<DPDeliveryChallanAcceptDTO> vewAllSerialsOfPOStatus(String invoiceNo,String ProductId, String circleId) throws DAOException
	{

		PreparedStatement pstmt = null;
		PreparedStatement pstmtID = null;
		
		ResultSet rset = null;
		ResultSet rsetID = null;
		
		ResourceBundle rb = ResourceBundle.getBundle("com.ibm.dp.resources.DPResources");
		String configId =rb.getString("android.configId");
		String productIds = null;

	  //  String QueryForGetProductId = "select DD_VALUE from DP_CONFIGURATION_DETAILS where CONFIG_ID = ? with ur";
	    

		String QueryForAndProdId = "SELECT PRODUCT_ID, PRODUCT_NAME FROM DP_PRODUCT_MASTER  WHERE circle_id=?"
			+" and CM_STATUS ='"+Constants.ACTIVE_STATUS+"' and PRODUCT_TYPE = 1 and PRODUCT_CATEGORY in (select VALUE from "
			+" DP_CONFIGURATION_DETAILS where ID in ("+configId+")) with ur ";
	    
		
		ArrayList<DPDeliveryChallanAcceptDTO> arrReturn = new ArrayList<DPDeliveryChallanAcceptDTO>();
		try
		{
			pstmtID = connection.prepareStatement(QueryForAndProdId);
			pstmtID.setString(1,circleId);
			rsetID = pstmtID.executeQuery();
			while (rsetID.next())
			{
				if(productIds != null)
				{
					productIds = productIds+","+rsetID.getString("PRODUCT_ID");
				}
				else
				{
					productIds = rsetID.getString("PRODUCT_ID");
				}
				
				
			}
			
			System.out.println("Product ids and product id "+ productIds +" "+ ProductId);
			if(productIds != null)
			{
				if(productIds.contains(ProductId))
				{
					pstmt = connection.prepareStatement(DBQueries.SQL_INVOICE_ALL_SERIALS_NO_ANDROID);				
					connection.setAutoCommit(false);
				
					pstmt.setString(1, invoiceNo);
					pstmt.setString(2, invoiceNo);
					pstmt.setString(3, invoiceNo);
					pstmt.setString(4, invoiceNo);
					pstmt.setString(5, invoiceNo);
				}	
				else 
				{
					pstmt = connection.prepareStatement(SQL_INVOICE_ALL_SERIALS_NO);
					connection.setAutoCommit(false);
					
					pstmt.setString(1, invoiceNo);
					pstmt.setString(2, ProductId);
					pstmt.setString(3, invoiceNo);
					pstmt.setString(4, ProductId);
					pstmt.setString(5, invoiceNo);
					pstmt.setString(6, ProductId);
					pstmt.setString(7, invoiceNo);
					pstmt.setString(8, ProductId);
					pstmt.setString(9, invoiceNo);
					pstmt.setString(10, ProductId);
				
				
				}
				
			}
			else 
			{
				pstmt = connection.prepareStatement(SQL_INVOICE_ALL_SERIALS_NO);
				connection.setAutoCommit(false);
				
				pstmt.setString(1, invoiceNo);
				pstmt.setString(2, ProductId);
				pstmt.setString(3, invoiceNo);
				pstmt.setString(4, ProductId);
				pstmt.setString(5, invoiceNo);
				pstmt.setString(6, ProductId);
				pstmt.setString(7, invoiceNo);
				pstmt.setString(8, ProductId);
				pstmt.setString(9, invoiceNo);
				pstmt.setString(10, ProductId);
			
			
			}
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				DPDeliveryChallanAcceptDTO delAcceptDTO= new DPDeliveryChallanAcceptDTO();
				delAcceptDTO.setSerialNo(rset.getString("SERIAL_NO"));
				delAcceptDTO.setStrProductName(rset.getString("PRODUCT_NAME"));
				delAcceptDTO.setStrStatus(rset.getString("PO_STB_STATUS"));
				
				arrReturn.add(delAcceptDTO);
			}
			connection.commit();
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
						
			e.printStackTrace();
			logger.error("Exception occured while Saving Delivery Challan  ::  "+e.getMessage());
			throw new DAOException(e.getMessage());			
		}
		finally
		{
			pstmt = null;
		}
		return arrReturn;
	}
}
