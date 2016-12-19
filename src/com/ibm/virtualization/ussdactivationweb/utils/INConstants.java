/**************************************************************************
* File     : AirRequestListenerConstants.java
* Author   : Abhipsa Gupta
* Created  : Jul 8, 2008
* Modified : Jul 8, 2008
* Version  : 0.1
**************************************************************************
*                               HISTORY
**************************************************************************
* V0.1		Jul 8, 2008 	Ankit K	First Cut.
* V0.2		Jul 8, 2008 	Ankit K	First modification
**************************************************************************
*
* Copyright @ 2002 This document has been prepared and written by 
* IBM Global Services on behalf of Bharti, and is copyright of Bharti
*
**************************************************************************/

package com.ibm.virtualization.ussdactivationweb.utils;

/*********************************************************************************
 * This class 
 * 
 * 
 * @author 
 * @version 1.0
 ********************************************************************************************/

public class INConstants {
	public static final String AIR_REQUEST_Q_HOSTNAME = "air.request.q.host";

	// *** Port of the request Q
	public static final String AIR_REQUEST_Q_PORT = "air.request.q.port";

	// *** channel name of the request Q
	public static final String AIR_REQUEST_Q_CHANNEL = "air.request.q.channel";

	// *** Manager of the request Q
	public static final String AIR_REQUEST_Q_MGR = "air.request.q.mgr";

	// *** Name of the request Q
	public static final String AIR_REQUEST_Q_NAME = "air.request.q.name";

	// *** timeout value for the controller
	public static final String AIR_CONTROLLER_TIMEOUT = "air.controller.timeout";

		
	public static final String AIR_RESPONSE_Q_HOSTNAME = "air.response.q.host";

	// *** Port of the request Q
	public static final String AIR_RESPONSE_Q_PORT = "air.response.q.port";

	// *** channel name of the request Q
	public static final String AIR_RESPONSE_Q_CHANNEL = "air.response.q.channel";

	// *** Manager of the request Q
	public static final String AIR_RESPONSE_Q_MGR = "air.response.q.mgr";

	// *** Name of the request Q
	public static final String AIR_RESPONSE_Q_NAME = "air.response.q.name";
	
	public static final String MAX_TRANSACTION_SIZE = "max.transaction.size";
	
//	 *** name of the AIR resource file
	public static final String AIR_RESOURCE_FILE = "AirRequestListener";
	
	public static final String IN_FTP_HOST = "IN.FTP.host";
	
	public static final String IN_FTP_USERNAME = "IN.FTP.user";
	
	public static final String IN_FTP_PASSWORD = "IN.FTP.pass";
	
	public static final String IN_FTP_AIR_PATH = "IN.FTP.air.path";
	
	public static final String IN_FTP_MINSAT_PATH = "IN.FTP.minsat.path";
	
	public static final String IN_REQUEST_FILE_PATH = "IN.request.file.path";
	
	public static final String IN_RESPONSE_FILE_PATH = "IN.response.file.path";
	
	public static final String IN_REQUEST_FILE_EXTENSION = "IN.request.file.ext";
	
	public static final String IN_RESPONSE_FILE_EXTENSION = "IN.response.file.ext";
	
	public static final String IN_RESQUEST_FILE_TYPE = "REQ";
	
	public static final String IN_RESPONSE_FILE_TYPE = "RES";
	
	public static final String IN_TRANSACTION_STATUS_PENDING = "PENDING";
	
	public static final String IN_SOURCE_ID = "IN.source.id";
	
//	 *** name of the IN SMS properties file
	public static final String IN_SMS_PROPERTIES_FILE = "com.ibm.virtualization.ussdactivationweb.resources.SMS";
	
	public static final String SMS_SENDER_NAME = "SendSmsName.smsSender";
    public static final String SMS_SENDER_HOST = "SendSms.ip";
    public static final String SMS_SENDER_PORT = "SendSms.connect.port";
    public static final String SMS_SENDER_USRID = "SendSms.userid";
    public static final String SMS_SENDER_PASS = "SendSms.pass";
    public static final String SMS_SENDER_PREFIX = "sendSms.india";
    public static final String SMS_SENDER_SEND = "sendSms";
    public static final String RECEIVER_MOBILE_NUMBER = "receiver.mobile";
    public static final String SMS_MAX_TRANSACTION_SIZE = "sms.max.transaction.size";
    public static final String SMS_TRANSACTION_DELAY = "sms.transaction.delay";
    
    public static final String MINSAT_REQUEST_Q_HOSTNAME = "minsat.request.q.host";

	// *** Port of the request Q
	public static final String MINSAT_REQUEST_Q_PORT = "minsat.request.q.port";

	// *** channel name of the request Q
	public static final String MINSAT_REQUEST_Q_CHANNEL = "minsat.request.q.channel";

	// *** Manager of the request Q
	public static final String MINSAT_REQUEST_Q_MGR = "minsat.request.q.mgr";

	// *** Name of the request Q
	public static final String MINSAT_REQUEST_Q_NAME = "minsat.request.q.name";

	// *** timeout value for the controller
	public static final String MINSAT_CONTROLLER_TIMEOUT = "minsat.controller.timeout";

	// *** name of the resource file
	public static final String IN_RESOURCE_BUNDLE = "INresources";
	
	public static final String MINSAT_RESPONSE_Q_HOSTNAME = "minsat.response.q.host";

	// *** Port of the request Q
	public static final String MINSAT_RESPONSE_Q_PORT = "minsat.response.q.port";

	// *** channel name of the request Q
	public static final String MINSAT_RESPONSE_Q_CHANNEL = "minsat.response.q.channel";

	// *** Manager of the request Q
	public static final String MINSAT_RESPONSE_Q_MGR = "minsat.response.q.mgr";

	// *** Name of the request Q
	public static final String MINSAT_RESPONSE_Q_NAME = "minsat.response.q.name";
	// *** Interval for starting IN posting process
	public static String ORDER_PROV_PROCESS_RUN_INTERVAL = "orderProvIN.timer.interval.in.minutes";
	
//	 *** Interval for sending SMS to the customers
	public static String SEND_SMS_PROCESS_RUN_INTERVAL = "sendSmsProcess.timer.interval.in.minutes";

}
