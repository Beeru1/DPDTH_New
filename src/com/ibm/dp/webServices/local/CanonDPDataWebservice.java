package com.ibm.dp.webServices.local;


import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.service.CannonDPDataService;
import com.ibm.dp.service.impl.CannonDPDataServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;



public class CanonDPDataWebservice {
	public static Logger logger = Logger.getLogger(CanonDPDataWebservice.class.getName());
	private static final String REGEX = "[0](\\d){10}";
	private static final String STAREGEX="^[a-zA-Z0-9]{1,3}$";
	public String canonDataUpdate(String recId, String serialNo, String distOlmId, String itemCode, String assignedDate, String customerId, String status, String stbType, String transactionType, String modelManKey, String modelManKeyOld, String userId,String password)
	{
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		String serviceMsg = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 df.setLenient(false);
		//private static Pattern pattern;
		try
		{
			logger.info("recId  ::  "+recId);
			logger.info("serialNo  ::  "+serialNo);
			logger.info("distOlmId  ::  "+distOlmId);
			logger.info("modelManKeyOld  ::  "+modelManKeyOld);
			Pattern  pattern = Pattern.compile(REGEX);
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
			
			logger.info(itemCode+ " is the item code");
			logger.info("Assigned Date is ======" + assignedDate);
			
			if(userId==null || userId.trim().equals("")|| !userId.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.userid")))
			{
				logger.info("**********INVALID USER NAME**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_USER_NAME;
			}
			else if(!encPassword.equals(webserviceResourceBundle.getString("dpwebservice.stbstatus.password")))
			{
				logger.info("**********INVALID PASSWORD**********");
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_INVALID_PASSWORD;
			}
			
			else if(( null !=serialNo && "NULL".equalsIgnoreCase(serialNo.trim())) || (null !=serialNo && serialNo.trim().equals("?")) || (null !=serialNo && serialNo.trim().equals("")) 
					|| (null!=modelManKeyOld && modelManKeyOld.trim().equals("?")) || (null !=recId && recId.trim().equals("?")) 
					|| (null !=recId && "NULL".equalsIgnoreCase(recId.trim())) || (null !=recId && recId.trim().equals(""))  || (null !=customerId && customerId.trim().equals("?")) || (null !=customerId && "NULL".equalsIgnoreCase(customerId.trim()))
					|| (null !=customerId && customerId.trim().equals("")) || (null !=distOlmId && distOlmId.trim().equals("?")) || (null !=distOlmId && "NULL".equalsIgnoreCase(distOlmId.trim())) || (null !=distOlmId && distOlmId.trim().equals(""))
					|| (null !=itemCode && itemCode.trim().equals("?")) || (null !=assignedDate && assignedDate.trim().equals("?"))|| (null !=assignedDate && "NULL".equalsIgnoreCase(assignedDate.trim()))|| (null !=assignedDate && assignedDate.trim().equals(""))
					|| (null !=status && status.trim().equals("?")) || (null !=status && "NULL".equalsIgnoreCase(status.trim()))|| (null !=status && status.trim().equals(""))|| (null !=stbType && stbType.trim().equals("?")) || (null !=stbType && "NULL".equalsIgnoreCase(stbType.trim()))
					|| (null !=stbType && stbType.trim().equals("")) || (null !=transactionType &&  transactionType.trim().equals("?")) || (null !=transactionType && "NULL".equalsIgnoreCase(transactionType.trim()))|| (null !=transactionType && transactionType.trim().equals("")) 
					|| (null !=modelManKey && modelManKey.trim().equals("?"))
					)
			{
				logger.info("**********Null ENTERIES**********");
				logger.info("**********regex********"+serialNo.trim().matches(REGEX));
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_NULL_INPUT;
			}
			
			else if(serialNo.trim().matches(REGEX))
			{
				//if(status.trim().matches(STAREGEX)){
				if(status.trim().equals("CPE")){
				CannonDPDataService canonDPDataSer = new CannonDPDataServiceImpl();
			 serviceMsg =canonDPDataSer.updateCannonData(recId, serialNo, distOlmId, itemCode, assignedDate, customerId, status, stbType, transactionType, modelManKey, modelManKeyOld, userId, encPassword);
				}
				else{
					//return "STATUS Field Length should be max 3";
					return "STATUS Field should be CPE only";
				}
				}
			else{
				return "SERIAL NUMBER length should be 11 digits starting with 0";
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		logger.info("********** canonDataUpdate WebService msg returned == ");
		return serviceMsg;
	}
	
	public String updateCannonInventory(String recordId, String defectiveSerialNo, String defectiveStbType, String newSerialNo, String newStbType, String inventoryChangeDate, String inventoryChangeType, String distributorOlmId, String status ,String errorMsg, String customerId, int defectId,String modelManKey, String modelManKeyOld, String userid, String password)
	{
		String serviceMsg = null;
		IEncryption crypt = new Encryption();
		String encPassword = "";
		ResourceBundle webserviceResourceBundle = null;
		try
		{
			logger.info("recId  ::  "+recordId);
			logger.info("defectiveSerialNo  ::  "+defectiveSerialNo);
			logger.info("defectiveStbType  ::  "+defectiveStbType);
			logger.info("modelManKeyOld  ::  "+modelManKeyOld);
			logger.info("Customer Id :"+customerId);
			logger.info("newSerial No :"+newSerialNo);
			logger.info("newStbType :"+newStbType);
			logger.info("inventoryChangeDate:"+inventoryChangeDate);
			logger.info("inventoryChangeType No :"+inventoryChangeType);
			logger.info("distributorOlmId No :"+distributorOlmId);
			logger.info("defectId No :"+defectId);
			logger.info("modelManKey No :"+modelManKey);
			logger.info("errorMsg :"+errorMsg);
			logger.info("STATUS :"+status);
			logger.info(status.length());
			
			
			Pattern  pattern = Pattern.compile(REGEX);
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
			else if((null !=defectiveSerialNo && "NULL".equalsIgnoreCase(defectiveSerialNo.trim())) ||(null !=defectiveSerialNo && defectiveSerialNo.trim().equals("?")) ||(null !=defectiveSerialNo && defectiveSerialNo.trim().equals("")) || (null!=modelManKeyOld &&  modelManKeyOld.trim().equals("") )
					||(null!=modelManKeyOld && modelManKeyOld.trim().equals("?")) || (null!=modelManKeyOld && "NULL".equalsIgnoreCase(modelManKeyOld.trim()) )|| (null !=recordId && recordId.trim().equals("?")) 
					|| (null !=recordId && "NULL".equalsIgnoreCase(recordId.trim())) || (null !=recordId && recordId.trim().equals(""))  || (null !=customerId && customerId.trim().equals("?")) || (null !=customerId && "NULL".equalsIgnoreCase(customerId.trim()))
					|| (null !=customerId && customerId.trim().equals("")) || (null !=defectiveStbType && defectiveStbType.trim().equals("?")) || (null !=defectiveStbType && "NULL".equalsIgnoreCase(defectiveStbType.trim())) || (null !=defectiveStbType && defectiveStbType.trim().equals(""))
					|| (null !=errorMsg && errorMsg.trim().equals("?")) || newSerialNo.trim().equals("?")|| (null !=newSerialNo && "NULL".equalsIgnoreCase(newSerialNo.trim()))|| (null !=newSerialNo && newSerialNo.trim().equals(""))
					|| (null !=newStbType && newStbType.trim().equals("?"))|| (null !=newStbType && "NULL".equalsIgnoreCase(newStbType.trim()))|| (null !=newStbType && newStbType.trim().equals(""))
					|| (null !=status && status.trim().equals("?")) || (null !=status && "NULL".equalsIgnoreCase(status.trim()))|| (null !=status && status.trim().equals(""))|| (null !=distributorOlmId && distributorOlmId.trim().equals("?")) || (null !=distributorOlmId && "NULL".equalsIgnoreCase(distributorOlmId.trim()))
					|| (null !=distributorOlmId && distributorOlmId.trim().equals("")) || (null !=newStbType && newStbType.trim().equals("?")) || (null !=newStbType && "NULL".equalsIgnoreCase(newStbType.trim()))|| (null !=newStbType && newStbType.trim().equals("")) 
					|| (null !=modelManKey && modelManKey.trim().equals("?"))
					)
			{
				logger.info("**********Null ENTERIES**********");
				logger.info("**********regex********"+defectiveSerialNo.trim().matches(REGEX)+"cc--"+ newSerialNo.trim().matches(REGEX));
				return com.ibm.dp.common.Constants.STB_STATUS_WS_MSG_NULL_INPUT;
			}
			else if(defectiveSerialNo.trim().matches(REGEX) && newSerialNo.trim().matches(REGEX) )
			{
				//if(status.trim().matches(STAREGEX)){
				if(status.trim().equals("CPE")){
				CannonDPDataService canonDPDataSer = new CannonDPDataServiceImpl();
				serviceMsg = canonDPDataSer.updateCannonInventory(recordId, defectiveSerialNo, defectiveStbType, newSerialNo, newStbType, inventoryChangeDate, inventoryChangeType, distributorOlmId, status, errorMsg, customerId, defectId, modelManKey, modelManKeyOld, userid, password);
			}
			else{
				//return "STATUS length should be max 3";
				return "STATUS length should be CPE only";
			}
			}
			else{
				return "SERIAL NUMBER length should be 11 digits starting with 0";
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		logger.info("********** updateCannonInventory WebService msg returned == ");
		return serviceMsg;
	}
	}