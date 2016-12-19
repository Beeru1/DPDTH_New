package com.ibm.dp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.SignInvoiceDao;
import com.ibm.virtualization.recharge.dao.rdbms.BaseDaoRdbms;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

public class SignInvoiceDaoImpl extends BaseDaoRdbms implements SignInvoiceDao{

	
	Logger logger = Logger.getLogger(SignInvoiceDaoImpl.class
			.getName());

	public SignInvoiceDaoImpl(Connection connection) {
		super(connection);
	}

	
	@Override
	public String[] updateStatus(String invoiceNo, String dist_olmId) {
		// TODO Auto-generated method stub
		String[] servMsg = new String[2];
		servMsg[0]="SUCCESS";
		servMsg[1]="";
		String queryGetBillDetailsFromDP_INVOICE="SELECT BILL_NO,BILL_DT,TIN_NO,PAN_NO,SRVC_TAX_CT,CPE_REC_NO,CPE_REC_RT,CPE_AMT,SR_REWARD_NO,SR_REWARD_RT,SR_REWARD_AMT,RETENTION_NO,RETENTION_RT," +
		"RETENTION_AMT,OTHR_NO,OTHR_RT,OTHR_AMT,TOTAL,SRVC_TAX_RT,SRVC_TX_AMT,EDU_CESS_RT,EDU_CESS_AMT,HIGH_EDU_CESS_RT,HIGH_EDU_AMT,GRAND_TOTAL,NAME_CUST,ADD_CUST,INVOICE_NO,OLM_ID,PARTNER_NM,STATUS,MONTH_FR,REMARKS,ACTION_DATE,OTHERS,OTHERS_1,KKC_RT,KKC_AMT" +
		" FROM DP_INVOICE WHERE INVOICE_NO=? and OLM_ID=? with ur";

		String queryGetBillDetailsFromDP_PARTNER_INVOICE_2 ="SELECT  OLM_ID,PARTNER_NAME,ASM,CIRCLE,DVR_T1,DVR_T2,DVR_T3,HD_T1,HD_T2,HD_T3,MULTI_T1,MULTI_T2,MULTI_T3,MULTI_DVR_T1,MULTI_DVR_T2,MULTI_DVR_3,TOTAL_ACT,TOTAL_AMOUNT,HD_HD_EXT,DVR,AMOUNT,ON_YEAR_COUNT," +
							"ON_YEAR_AMOUNT,FIELD_REPAIRS_AMOUNT,ARP_AMOUNT,TOTAL_BASE_AMOUNT,ST,ES ,HES ,TOTAL_INVOICE_AMT,INVOICE_NO,BILL_NO,STATUS,MONTH_FR,REMARKS,TIER," +
							"ACTION_DATE,BILL_DT,OTHERS,OTHERS_1,OTHERS_2,WEAK_GEO_PAY,HILLY_PAY,FIN_REMARKS,KKC"+
							" FROM DP_PARTNER_INVOICE_2 WHERE INVOICE_NO=? and OLM_ID=? with ur";
		
		String updateInvoiceStatusDP_INVOICE = "UPDATE DP_INVOICE set STATUS = ?,ACTION_DATE=current timestamp where INVOICE_NO=? and OLM_ID=? with UR";
		
		String updateInvoiceStatusDP_PARTNER_INVOICE_2 = "UPDATE DP_PARTNER_INVOICE_2 set STATUS = ? ,ACTION_DATE=current timestamp  where INVOICE_NO=? and OLM_ID=? with UR";
		
		PreparedStatement pstmtDP_INVOICE = null;
		ResultSet rset = null;
		
		PreparedStatement pstmtDP_PARTNER_INVOICE_2 = null;
		ResultSet rsetDP_PARTNER_INVOICE_2 = null;
		
		
		try{
		
			pstmtDP_INVOICE = connection.prepareStatement(queryGetBillDetailsFromDP_INVOICE);
			pstmtDP_INVOICE.setString(1, invoiceNo);
			pstmtDP_INVOICE.setString(2, dist_olmId);
			rset = pstmtDP_INVOICE.executeQuery();
			
			while (rset.next())
			{
				pstmtDP_INVOICE = connection.prepareStatement(updateInvoiceStatusDP_INVOICE);
				pstmtDP_INVOICE.setString(1, "T");
				pstmtDP_INVOICE.setString(2, invoiceNo);
				pstmtDP_INVOICE.setString(3, dist_olmId);
				pstmtDP_INVOICE.executeUpdate();
				connection.commit();
				connection.close();
				logger.info("Query for DP_INVOICE status "+servMsg[0]+ " : " + servMsg[1]);
				return servMsg;
				
			}
			
		
			pstmtDP_PARTNER_INVOICE_2 = connection.prepareStatement(queryGetBillDetailsFromDP_PARTNER_INVOICE_2);
			pstmtDP_PARTNER_INVOICE_2.setString(1, invoiceNo);
			pstmtDP_PARTNER_INVOICE_2.setString(2, dist_olmId);
			rset = pstmtDP_PARTNER_INVOICE_2.executeQuery();
			
			while (rset.next())
			{
				pstmtDP_PARTNER_INVOICE_2 = connection.prepareStatement(updateInvoiceStatusDP_PARTNER_INVOICE_2);
				pstmtDP_PARTNER_INVOICE_2.setString(1, "T");
				pstmtDP_PARTNER_INVOICE_2.setString(2, invoiceNo);
				pstmtDP_PARTNER_INVOICE_2.setString(3, dist_olmId);
				pstmtDP_PARTNER_INVOICE_2.executeUpdate();
				connection.commit();
				connection.close();
				logger.info("Query for DP_INVOICE status "+servMsg[0]+ " : " + servMsg[1]);
				return servMsg;
				
			}
			
			
			servMsg[0] = "Failure";
			servMsg[1] = "Record not found";
			return servMsg;
		}
		catch (Exception e) {

			e.printStackTrace();

			logger.error("::::::::::::::::::: Error in SignInvoiceDaoImpl in updateStatus WebService -------->",e);

			servMsg[0] = "Failure";
			servMsg[1] = "Error in WebService";
			try 
			{
				connection.rollback();
			} 
			catch (SQLException sqle) 
			{
				sqle.printStackTrace();
				logger.error("::::::::::::::::::: Error in SignInvoiceDaoImpl in updateStatus WebService -------->",sqle);
			}
		} 
		finally 
		{
			DBConnectionManager.releaseResources(pstmtDP_INVOICE, rset);
			DBConnectionManager.releaseResources(pstmtDP_PARTNER_INVOICE_2, rset);
		}

		return servMsg;
	}
		

	

}
