package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.SignInvoiceService;
import com.ibm.dp.service.impl.SignInvoiceServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class SignInvoiceWebService {

	public static Logger logger = Logger.getLogger(SignInvoiceWebService.class
			.getName());

	public String[] updateStatus(String invoice_No,String dist_olmId, String userid, String password)
	{
     logger.info("********** SignInvoiceWebService Started for \n Invoice No  :::  " +invoice_No +"\n OLM id      :::  "+dist_olmId );
		
		String[] serviceMsg = new String[2]; // [0] = header ,[1] = message
		serviceMsg[0]="FAIL";
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(!userid.equals(webserviceResourceBundle.getString("dpwebservice.signstatus.userid")))
			{
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[1]=com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_USER_NAME;
				return serviceMsg;
			}
			else if(!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.signstatus.password")))
			{
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[1]=com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
				return serviceMsg;
			}
			else{
		if(invoice_No == null || invoice_No.equalsIgnoreCase(""))
		{	serviceMsg[1]="Invoice Number is Null or Empty";
		}
		else if(dist_olmId == null || dist_olmId.equalsIgnoreCase(""))
		{
			serviceMsg[1]="olm id is Null or Empty";
		}
		else
		{
			SignInvoiceService invoice = new SignInvoiceServiceImpl();
			
			serviceMsg = invoice.updateStatus(invoice_No, dist_olmId);
			
			
		}
	}
			
		}catch (Exception e) 
		{
			logger.error("************ Exception in SignInvoiceWebService WebService -------->",e);
			e.printStackTrace();
			serviceMsg[1] = "OTHERS";
		}
		logger.info("********** SignInvoiceWebService WebService msg returned == "+serviceMsg);
		return serviceMsg;
	}
}
