package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DistributorSTBMappingService;
import com.ibm.dp.service.impl.DistributorSTBMappingServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class AVRestrictionWebservice 
{
	public static Logger logger = Logger.getLogger(AVRestrictionWebservice.class
			.getName());

	public String[] checkAVRestriction(String distOLMId, String avSerialNumber, String userid, String password) 
	{
		logger.info("AVRestrictionWebservice WebService Started for \n distributor OLM Id  :::  "+ distOLMId 
				+ "\n AV No      :::  " + avSerialNumber);

		String[] serviceMsg = new String[2]; // [0] = header ,[1] = message
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try 
		{
			encPassword = crypt.encrypt(password);
			if (webserviceResourceBundle == null) 
			{
				webserviceResourceBundle = ResourceBundle.getBundle(Constants.WEBSERVICE_RESOURCE_BUNDLE);
			}

			if (!userid.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.userid"))) 
			{
				logger.info("**********INVALID USER NAME**********");
				serviceMsg[0] = "FAIL";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_USER_NAME;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else if (!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.distStbMap.password"))) 
			{
				logger.info("**********INVALID PASSWORD**********");
				serviceMsg[0] = "FAIL";
				serviceMsg[1] = com.ibm.dp.common.Constants.STB_STB_MAP_WS_INVALID_PASSWORD;
				logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
				return serviceMsg;
			} 
			else 
			{
				DistributorSTBMappingService stbService = new DistributorSTBMappingServiceImpl();
//				if(distOLMId == null || distOLMId.trim().equals(""))
//				{
//					logger.info("**********INVALID OLM ID  ::  "+distOLMId+"   **********");
//					serviceMsg[0] = "Failure";
//					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID;
//					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
//					return serviceMsg;
//				}
//				
//				String distId = stbService.checkOLMId(distOLMId);
//				
//				logger.info("************Distributor ID == " + distId);
				String distId = "0";
				
				if (distId != "-1") 
				{
//					if (avSerialNumber.length() != 11) 
//					{
//						logger.info("********** Not 11 digit length, INVALID SERIAL NUMBER      :::  "+ avSerialNumber);
//						serviceMsg[0] = "Failure";
//						serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
//						logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
//						return serviceMsg;
//					} 
//					else 
//					{
//						boolean isNumeric = avSerialNumber.matches("[0-9]+$");
//						if (!isNumeric) 
//						{
//							logger.info("********** Non Numeric value, INVALID SERIAL NUMBER      :::  "+ avSerialNumber);
//							serviceMsg[0] = "Failure";
//							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
//							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
//							return serviceMsg;
//						} 
//						else if (avSerialNumber.indexOf("0") != 0) 
//						{
//							logger.info("**********Not started with 0, INVALID SERIAL NUMBER      :::  "+ avSerialNumber);
//							serviceMsg[0] = "Failure";
//							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
//							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
//							return serviceMsg;
//
//						} 
//						else 
//						{
//							// check availability of serial number in DPDTH with
//							// OLM Id
//							serviceMsg = stbService.avRestrictionCheck(distId, avSerialNumber, distOLMId);
//
//							logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
//						}
//					}
					
					//Checking for AV serial number
					serviceMsg = stbService.avRestrictionCheck(avSerialNumber, distOLMId);
				} 
				else 
				{
					logger.info("**********INVALID OLM ID     :::  "+ distOLMId);
					serviceMsg[0] = "FAIL";
					serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_OLM_ID+ " " + distOLMId;
					
					logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
					return serviceMsg;
				}
			}
		} 
		catch (Exception e) 
		{
			logger.error("************ Exception in DistributorSTBMapping WebService -------->",e);
			e.printStackTrace();
			serviceMsg[0] = "Failure";
			serviceMsg[1] = "Error in WebService";
			logger.info("********** DistributorSTBMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
		}
		return serviceMsg;
	}
}
