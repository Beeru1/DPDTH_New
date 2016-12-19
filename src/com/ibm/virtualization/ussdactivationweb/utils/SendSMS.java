/**************************************************************************
 * File     : SendSMS.java
 * Author   : Abhipsa Gupta
 * Created  : Sep 24, 2008
 * Modified : Sep 24, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sep 24, 2008 	Administrator	First Cut.
 * V0.2		Sep 24, 2008 	Administrator	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.utils;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.virtualization.recharge.sms.SMSConnectionBean;
import com.ibm.virtualization.recharge.sms.SMSSender;
import com.ibm.virtualization.recharge.sms.SMSSenderException;
import com.ibm.virtualization.recharge.sms.SMSSenderFirstHopImpl;

/*******************************************************************************
 * This class
 * 
 * 
 * @author 
 * @version 1.0
 ******************************************************************************/

public class SendSMS {
	
	/** getting logger refrence * */

	private static final Logger logger = Logger.getLogger(SendSMS.class);
	
	private static String smsSenderName;

	private static SMSConnectionBean  smsConnBean = null;

	private static String smsSenderIndia = null;;
	
	static {
				
		try {
			
			String smsSenderHost = Utility
			.getValueFromBundle(INConstants.SMS_SENDER_HOST,
					INConstants.IN_SMS_PROPERTIES_FILE);
			String smsSenderPort = Utility
			.getValueFromBundle(INConstants.SMS_SENDER_PORT,
					INConstants.IN_SMS_PROPERTIES_FILE);
			String smsSenderUserid = Utility.getValueFromBundle(
			INConstants.SMS_SENDER_USRID,
			INConstants.IN_SMS_PROPERTIES_FILE);
			smsSenderIndia = Utility.getValueFromBundle(
					INConstants.SMS_SENDER_PREFIX, INConstants.IN_SMS_PROPERTIES_FILE);
			smsSenderName = Utility.getValueFromBundle(
					INConstants.SMS_SENDER_NAME, INConstants.IN_SMS_PROPERTIES_FILE);
			Encryption encrypt = new Encryption();
			String smsSenderpwd = encrypt.decrypt(Utility.getValueFromBundle(
					INConstants.SMS_SENDER_PASS,
					INConstants.IN_SMS_PROPERTIES_FILE));
		
			smsConnBean = new SMSConnectionBean();
			smsConnBean.setHost(smsSenderHost);
			smsConnBean.setPassword(smsSenderpwd);
			smsConnBean.setPort((new Integer(smsSenderPort)).intValue());
			smsConnBean.setUsername(smsSenderUserid);
		
		} catch (EncryptionException e) {
			logger.error("Exception occured while decrypting password:"
					+ e.getMessage());
		}

		
	}

	public static void sendSMS(String mobileNumbers, String message) {
		logger.debug("Entering into Send SMS class and sendSMS() method , parameters used in the method are : MobileNumber : "+mobileNumbers +" and Message : "+message);

		try {
			SMSSender sms = new SMSSenderFirstHopImpl(smsConnBean);
			String MSISDN = (new StringBuffer(String
						.valueOf(smsSenderIndia))).append(new Long((String)mobileNumbers))
						.toString();
				//System.out.println((new StringBuffer("SMS Sending to "))
						//.append(MSISDN).toString()); 
							
					try{
					
						boolean ret = sms.sendMessage(MSISDN, message, smsSenderName);
						//System.out.println((new StringBuffer(
							//"Response from first hope ::")).append(ret
							//)
							//.toString());
						//logger.info("SMS has been send to the mobile number : "+mobileNumbers);
					}catch (SMSSenderException smsex){
						
						
					} 
			
		}
		catch (RuntimeException e) {
			logger.error("Excetion in sendSMS method() ",e);
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]) {
		Encryption encryption = new Encryption();
			//if(args.length == 2) {
			String action = "-e";
			String password = "dpsmpp123";
			try {
				if(action.equalsIgnoreCase("-e")) {
					System.out.println(
							"The password " + password + " in encrypted form is " + encryption.encrypt(password));
				}
				else {
					System.out.println(
							"The password " + password + " in decrypted form is " + encryption.decrypt(password));
				}
			} catch (EncryptionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//}
		
	}

	
}
