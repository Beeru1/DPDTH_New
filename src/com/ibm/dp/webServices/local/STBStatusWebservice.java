package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.STBStatusService;
import com.ibm.dp.service.impl.STBStatusServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class STBStatusWebservice {
public static Logger logger = Logger.getLogger(STBStatusWebservice.class.getName());
	
	public String stbStatusChange(String strSrNos, String status, String userid, String password)
	{
		logger.info("********** STBStatusWebservice WebService Started for \n strSrNos  :::  " +strSrNos +"\n status      :::  "+status );
		
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
			
			if(!userid.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.userid")))
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
				String[] srNosArray = strSrNos.split(",");
				String[] srStatusArray =status.split(",");
				
				String strSRNO = "";
				String strStatus = "";
				STBStatusService stbService = null;
				
				if(srNosArray.length != srStatusArray.length)
				{
					logger.info("**********INVALID SERIAL NUMBER AND STATUS LISTS**********");
					return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_SR_NO_LIST;
				}
				else
				{
					stbService = new STBStatusServiceImpl();
					
					for(int count=0; count<srNosArray.length; count++)
					{
						strSRNO = srNosArray[count].trim();
						if(strSRNO.length() != 11)
						{
							logger.info("**********INVALID SERIAL NUMBER      :::  "+strSRNO);
							return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_SR_NO_LIST;
						}
						else
						{
							boolean isNumeric = strSRNO.matches("[0-9]+$");
							if(!isNumeric)
							{
								logger.info("**********INVALID SERIAL NUMBER      :::  "+strSRNO);
								return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_SR_NO_LIST;
							}
							else
							{
								// check availability of serial number in DPDTH
								int isAvil =0;
								isAvil = stbService.checkSrNoAvailibility(strSRNO);
								if(isAvil==1)
								{
									logger.info("**********INVALID SERIAL NUMBER      :::  "+strSRNO);
									return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_SR_NO_LIST;
								}
							}
						}
					}
					
					for(int count=0; count<srStatusArray.length; count++)
					{
						strStatus = srStatusArray[count].trim();
						if(strStatus.length()!= 1)
						{
							logger.info("**********INVALID STATUS      :::  "+strStatus);
							return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_STATUS_NO_LIST;
						}
						else
						{
							boolean isNumeric = strStatus.matches("[0-9]+$");
							if(!isNumeric)
							{
								logger.info("**********INVALID STATUS      :::  "+strStatus);
								return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_STATUS_NO_LIST;
							}
							else if(!(strStatus.equals("2") || strStatus.equals("5")))
							{
//								List<String> lisr stbService.checkSrNoAvailibility(strSRNO);
//								if(!(strStatus.equals("2") || strStatus.equals("5")))
								logger.info("**********INVALID STATUS      :::  "+strStatus);
								return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_STATUS_NO_LIST;
							}
						}
					}
				}
						
				serviceMsg = stbService.updateStatus(strSrNos, status);
				logger.info("********** service msg == "+serviceMsg);
			}
		}
		catch (Exception e) 
		{
			logger.error("************ Exception in STBStatusWebservice WebService -------->",e);
			e.printStackTrace();
			serviceMsg = "OTHERS";
		}
		logger.info("********** STBStatus WebService msg returned == "+serviceMsg);
		return serviceMsg;
	}
	
	
}
