package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.POStatusUpdateService;
import com.ibm.dp.service.impl.POStatusUpdateServiceImpl;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;

public class POStatusUpdateWebservice {
public static Logger logger = Logger.getLogger(POStatusUpdateWebservice.class.getName());
	
	public String poStatusUpdate(String poNo, String poStatus, String poStatusTime, String remarks, String productCode, String poQuantity, String userid, String password)
	{
		String serviceMsg = null;
		
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			logger.info("poNo  ::  "+poNo);
			logger.info("poStatus  ::  "+poStatus);
			logger.info("poStatusTime  ::  "+poStatusTime);
			logger.info("poremarks  ::  "+remarks);
			if(password==null || password.trim().equals(""))
			{
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
			}
			else
				encPassword = crypt.encrypt(password);
			
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(poNo==null || poNo.trim().equals("") || poStatus==null 
					||poStatus.trim().equals("") || poStatusTime==null || poStatusTime.trim().equals(""))
			{
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_NULL_INPUT;
			}
			
			if(userid==null || userid.trim().equals("")|| !userid.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.userid")))
			{
				logger.info("**********INVALID USER NAME**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_USER_NAME;
			}
			else if(!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.password")))
			{
				logger.info("**********INVALID PASSWORD**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
			}
			else
			{
				POStatusUpdateService pOStatusUpdateService = new POStatusUpdateServiceImpl(); 
				serviceMsg = pOStatusUpdateService.updateStatus(poNo, poStatus, poStatusTime, remarks, productCode, poQuantity, userid, password);
				logger.info("********** service msg == "+serviceMsg);
			}
		}
		catch (Exception e) 
		{
			logger.error("************ Exception in STBStatusWebservice WebService -------->",e);
			e.printStackTrace();
			serviceMsg = "OTHERS";
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PO_FLOW_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		logger.info("********** STBStatus WebService msg returned == "+serviceMsg);
		return serviceMsg;
	}
	
	public String processDeliveryChallan(String prNo, String poNo, String poDate,String poStatusDate , String invoiceNo,String invoiceDate, String dcNo, String dcDate, String ddChequeNo , String ddChequeDate,String productId,String serialNo, String userid, String password)
	{
		
		/* PR_ID, PO_ID,PO_DATE,PO_STATUSDATE,INVOICE_NO,INVOICE_DATE,DC_NO,DC_DATE,DD_CHEQUE_NO,DD_CHEQUE_DATE
		PRODUCT_ID,SERIALNO,USERNAME,PASSWORD */ 
		String serviceMsg = null;
		
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			logger.info("prId  "+prNo);
			logger.info("poId  ::  "+poNo);
			logger.info("invoiceNo  ::  "+invoiceNo);
			logger.info("dcNo  ::  "+dcNo);
			logger.info("productId  ::  "+productId);
			logger.info("serialNo  ::  "+serialNo);
			if(password==null || password.trim().equals(""))
			{
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
			}
			else
				encPassword = crypt.encrypt(password);
			
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(prNo==null || prNo.trim().equals("") || poNo==null ||poNo.trim().equals("") || poDate==null || poDate.trim().equals("") || poStatusDate==null || poStatusDate.trim().equals("") || invoiceNo==null 
					||invoiceNo.trim().equals("") || invoiceDate==null || invoiceDate.trim().equals("") ||
			dcNo==null || dcNo.trim().equals("") || dcDate==null || dcDate.trim().equals("") || productId==null 
					||productId.trim().equals("") || serialNo==null || serialNo.trim().equals(""))
			{
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_NULL_INPUT;
			}
			
			StringTokenizer productST=new StringTokenizer(productId, ",");
			StringTokenizer serialNumberST=new StringTokenizer(serialNo, ",");
			
			if(userid==null || userid.trim().equals("")|| !userid.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.userid")))
			{
				logger.info("**********INVALID USER NAME**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_USER_NAME;
			}
			else if(!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.password")))
			{
				logger.info("**********INVALID PASSWORD**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
			}else if(productST.countTokens() != serialNumberST.countTokens())
			{
				logger.info("**********Different Product Ids and Serial Nos List **********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_STB_PRODUCT_NOS;
			}
			else
			{
				POStatusUpdateService pOStatusUpdateService = new POStatusUpdateServiceImpl(); 
				serviceMsg = pOStatusUpdateService.updateDeliveryChallan(prNo,  poNo,  poDate,poStatusDate ,  invoiceNo, invoiceDate,  dcNo,  dcDate,  ddChequeNo ,  ddChequeDate, productId, serialNo, userid, password);
				logger.info("********** service msg == "+serviceMsg);
			}
		}
		catch (Exception e) 
		{
			logger.error("************ Exception in STBStatusWebservice WebService -------->",e);
			e.printStackTrace();
			serviceMsg = "OTHERS";
			try
			{
				String err = ResourceReader.getCoreResourceBundleValue(com.ibm.dp.common.Constants.PO_FLOW_Critical);
				logger.info(err + "::" +Utility.getCurrentDate());
			}
			catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		logger.info("********** STBStatus WebService msg returned == "+serviceMsg);
		return serviceMsg;
	}
}
