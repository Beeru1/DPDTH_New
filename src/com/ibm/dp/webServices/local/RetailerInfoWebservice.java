package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

/**
 * @author DP Team
 *
 */
public class RetailerInfoWebservice {
	
	public static Logger logger = Logger.getLogger(RetailerInfoWebservice.class.getName());
	
	public String [] getRetailerInfo (String mobilenumber, String userid, String password)
	{
		logger.info("********** getRetailerInfo WebService Started for Mobile number ----------->"+mobilenumber);
		//System.out.println("********** getRetailerInfo WebService **********");
		String [] retailerInfoArray = null;
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			if(encPassword.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.password")) && userid.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.userid")))
			{
				DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
				retailerInfoArray = accountService.getRetailerInfo(mobilenumber);
			}
			else
			{
				logger.error("*********** Invalid Userid/Password ****************");
				logger.error("*********** User id entered -------->"+userid);
				logger.error("*********** Password entered ------->"+password);
				retailerInfoArray = new String []{"Invalid Userid/Password"};
								
			}
		
		}
		catch(Exception e)
		{
			logger.error("************ Error in getRetailerInfo WebService -------->",e);
			e.printStackTrace();
		}
		logger.info("********** getRetailerInfo WebService Ended for Mobile number ----------->"+mobilenumber);		
		return retailerInfoArray;
	}
	
//	 For cannon
	public String [] getRetailerInfoCannon(String mobilenumber, String userid, String password)
	{
		logger.info("********** getRetailerInfo WebService Started for Mobile number ----------->"+mobilenumber);
		//System.out.println("********** getRetailerInfo WebService **********");
		String [] retailerInfoArray = null;
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(mobilenumber == null || mobilenumber.equals("") || (mobilenumber.indexOf(" ") >= 0))
			{
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
			else if(encPassword.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.cannon.password")) && userid.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.userid")))
			{
				DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
				retailerInfoArray = accountService.getRetailerInfoCannon(mobilenumber);
			}
			else
			{
				logger.error("*********** Invalid Userid/Password ****************");
				logger.error("*********** User id entered -------->"+userid);
				logger.error("*********** Password entered ------->"+password);
				retailerInfoArray = new String []{"Invalid Userid/Password"};
			}
		
		}
		catch(Exception e)
		{
			logger.error("************ Error in getRetailerInfo WebService -------->",e);
			e.printStackTrace();
		}
		logger.info("********** getRetailerInfo WebService Ended for Mobile number ----------->"+mobilenumber);		
		return retailerInfoArray;
	}

//	 For cannon and Self Care
	public String [] getRetailerDistInfoCannon(String mobilenumber, String pinCode, String userid, String password)
	{
		logger.info("********** getRetailerInfo WebService Started for Mobile number ----------->"+mobilenumber);
		//System.out.println("********** getRetailerInfo WebService **********");
		String [] retailerInfoArray = null;
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(mobilenumber == null || mobilenumber.equals("") || (mobilenumber.indexOf(" ") >= 0)||mobilenumber.length()>10)
			{
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
			else if(encPassword.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.cannon.password")) && userid.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.userid")))
			{
				DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
				retailerInfoArray = accountService.getRetailerDistInfoCannon(mobilenumber, pinCode);
			}
			else
			{
				logger.error("*********** Invalid Userid/Password ****************");
				logger.error("*********** User id entered -------->"+userid);
				logger.error("*********** Password entered ------->"+password);
				retailerInfoArray = new String []{"Invalid Userid/Password"};
			}
		
		}
		catch(Exception e)
		{
			logger.error("************ Error in getRetailerInfo WebService -------->",e);
			e.printStackTrace();
			return new String[]{"DPDTH Exception occured"};
		}
		logger.info("********** getRetailerInfo WebService Ended for Mobile number ----------->"+mobilenumber);		
		return retailerInfoArray;
	}
	
	public String[] getRetailerDistInfoCannonNew(String mobilenumber, String pinCode, String userid, String password)
	{

		logger.info("********** getRetailerInfo WebService Started for Mobile number ----------->"+mobilenumber);
		//System.out.println("********** getRetailerInfo WebService **********");
		String [] retailerInfoArray = null;
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) {
				webserviceResourceBundle = ResourceBundle
						.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}
			
			if(mobilenumber == null || mobilenumber.equals("") || (mobilenumber.indexOf(" ") >= 0))
			{
				retailerInfoArray = new String []{"Invalid Mobile Number"};
			}
			else if(encPassword.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.cannon.password")) && userid.equals(webserviceResourceBundle.getString("dpwebservice.retailerinfo.userid")))
			{
				DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
				retailerInfoArray = accountService.getRetailerDistInfoCannonNew(mobilenumber, pinCode);
			}
			else
			{
				logger.error("*********** Invalid Userid/Password ****************");
				logger.error("*********** User id entered -------->"+userid);
				logger.error("*********** Password entered ------->"+password);
				retailerInfoArray = new String []{"Invalid Userid/Password"};
			}
		
		}
		catch(Exception e)
		{
			logger.error("************ Error in getRetailerInfo WebService -------->",e);
			e.printStackTrace();
			return new String[]{"DPDTH Exception occured"};
		}
		logger.info("********** getRetailerInfo WebService Ended for Mobile number ----------->"+mobilenumber);		
		return retailerInfoArray;
	
	}
	
}
