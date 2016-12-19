package com.ibm.dp.webServices.local;

import java.util.ResourceBundle;
import org.apache.log4j.Logger;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.DistributorSTBMappingService;
import com.ibm.dp.service.impl.DistributorSTBMappingServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;

public class DistributorAVMapping {
	
	public static Logger logger = Logger.getLogger(DistributorAVMapping.class
			.getName());
      
	
	public String[] checkAVMapping (String lapuMobileNo,  String avSerialNumber,  String userid, String password) 
	{
		
		logger.info("DistributorAVMapping webservice Started for \n lapu mobile number  :::  "+ lapuMobileNo 
				+ "\n avSerialNumber      :::  " + avSerialNumber+ "\n  " );
		
		
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

				String distId = "0";
				if (distId != "-1") 
				{
					if (avSerialNumber.length() != 16) 
					{
						logger.info("********** Not 16 digit length, INVALID AVSERIAL NUMBER      :::  "+ avSerialNumber);
						serviceMsg[0] = "Failure";
						serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
						logger.info("********** DistributorAVMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
						return serviceMsg;
					} 
					
					
					else 
					{
						boolean isNumeric = avSerialNumber.matches("[0-9]+$");
						if(null!= lapuMobileNo)
						{
						if (!isNumeric) 
						{
							logger.info("********** Non Numeric value, INVALID SERIAL NUMBER      :::  "+ avSerialNumber);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
							logger.info("********** DistributorAVMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;
						} 
					else if (avSerialNumber.indexOf("0") != 0) 
						{
							logger.info("**********Not started with 0, INVALID SERIAL NUMBER      :::  "+ avSerialNumber);
							serviceMsg[0] = "Failure";
							serviceMsg[1] = com.ibm.dp.common.Constants.DIST_STB_MAP_WS_INVALID_STB+ " " + avSerialNumber;
							logger.info("********** DistributorAVMapping WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
							return serviceMsg;

						} 
						else 
						{
							// check availability of serial number in DPDTH with
							// OLM Id
							serviceMsg = stbService.avRestrictionCheck2( avSerialNumber, lapuMobileNo);

							logger.info("********** DistributorAVMapping2 WebService msg returned == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
						}
					}
				}
					//Checking for Lapu Mobile no
					//serviceMsg = stbService.avRestrictionCheck2(avSerialNumber, lapuMobileNo);
				} 
				
		}
			
			//}
			}
			
		catch(Exception e)
		{
			logger.error("************ DistributorAVMapping webservice -------->",e);
			e.printStackTrace();
			serviceMsg[0] = "Failure";
			serviceMsg[1] = "Error in WebService";
			logger.info("********** DistributorAVMapping webservice == "+ serviceMsg[0]+"  ::  "+serviceMsg[1]);
			
		}
		return serviceMsg;
	}
	
}
	
