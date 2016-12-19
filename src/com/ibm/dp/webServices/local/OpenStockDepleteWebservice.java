package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DPOpenStockDepleteService;
import com.ibm.dp.service.impl.DPOpenStockDepleteServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class OpenStockDepleteWebservice 
{
	public static Logger logger = Logger.getLogger(OpenStockDepleteWebservice.class.getName());
	
	public String depleteOpenStock(String distributorCode, String productCode, int productQuantity, String userid, String password)
	{
		logger.info("********** depleteOpenStock WebService Started for" +
							"\ndistributorCode  :::  "+distributorCode +
							"\nproductCode      :::  "+productCode +
							"\nproductQuantity  :::  "+productQuantity);
		
		String serviceMsg = null;
		
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
			if(encPassword.equals(webserviceResourceBundle.getString("dpwebservice.openstock.password")) 
				&& userid.equals(webserviceResourceBundle.getString("dpwebservice.openstock.userid")))
			{
				//DPOpenStockDepleteService depleteService = new DPOpenStockDepleteServiceImpl();
				//serviceMsg = depleteService.depleteOpenStock(distributorCode, productCode, productQuantity);
				serviceMsg = "No more integration with CRM";
			}
			else
			{
				logger.error("*********** Invalid Userid/Password ****************");
				logger.error("*********** User id entered -------->"+userid);
				logger.error("*********** Password entered ------->"+password);
				serviceMsg = "Invalid Userid/Password";
			}
		}
		catch (Exception e) 
		{
			logger.error("************ Exception in depleteOpenStock WebService -------->",e);
			e.printStackTrace();
			serviceMsg = "OTHERS";
		}
		
		return serviceMsg;
	}
}
