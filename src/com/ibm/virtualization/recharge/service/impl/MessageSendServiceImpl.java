/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.MessageSendService;
import com.ibm.virtualization.recharge.sms.SMSConnectionBean;
import com.ibm.virtualization.recharge.sms.SMSSender;
import com.ibm.virtualization.recharge.sms.SMSSenderException;
import com.ibm.virtualization.recharge.sms.SMSSenderImpl;
import com.ibm.virtualization.recharge.sms.SMSSenderFirstHopImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;

/**
 * This class provides implementation for user related functionalities.
 * 
 * @author Paras
 * 
 */
public class MessageSendServiceImpl implements MessageSendService {
	/*
	 * Logger for this class.
	 */
	private Logger logger = Logger.getLogger(MessageSendServiceImpl.class
			.getName());

	private static String smsSenderName;

	private static SMSConnectionBean smsConnBean;

	private static String smsSenderIndia;

	/** Initializing the SMSConnectionBean and Bundle */
	static {
		// ** * Setting the Resource Bundle Name for the PropertyReader File **
		// *//*
		smsSenderName = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_NAME, Constants.RESOURCE_FILE_PASSWORD);
		smsSenderIndia = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_PREFIX, Constants.RESOURCE_FILE_PASSWORD);

		String smsSenderHost = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_HOST, Constants.RESOURCE_FILE_PASSWORD);
		String smsSenderPort = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_PORT, Constants.RESOURCE_FILE_PASSWORD);
		String smsSenderUserid = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_USRID, Constants.RESOURCE_FILE_PASSWORD);
		String smsSenderpwd = ResourceReader.getValueFromBundle(
				Constants.SMS_SENDER_PASS, Constants.RESOURCE_FILE_PASSWORD);

		smsConnBean = new SMSConnectionBean();
		smsConnBean.setHost(smsSenderHost);
		smsConnBean.setPassword(smsSenderpwd);
		smsConnBean.setPort(new Integer(smsSenderPort));
		smsConnBean.setUsername(smsSenderUserid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.MessageSendService#sendSMS(java.lang.String,
	 *      java.lang.String)
	 */

	public boolean sendSMS(String mobileNo, String message)
			throws VirtualizationServiceException {
		boolean response = false;
		try {
			String smsSender = smsSenderIndia + mobileNo;
			String sendSmsName = ResourceReader.getCoreResourceBundleValue(Constants.SMS_SENDER_NAME);
			logger.info("sending sms="
					+ ResourceReader.getCoreResourceBundleValue(
							Constants.SMS_SENDER_SEND));
			if ("true".equalsIgnoreCase(ResourceReader.getCoreResourceBundleValue(Constants.SMS_SENDER_SEND))) {
				SMSSender sms = new SMSSenderFirstHopImpl(smsConnBean);
				if (message.length() > 0) {
					try {
						response = sms.sendMessage(smsSender, message,
								sendSmsName);
						logger.info("response:" + response);
					} catch (SMSSenderException e) {
						logger.error("Exception occured while sending sms:"
								+ e.getMessage(), e);
					}
				} else {
					/* If no message is defined for the error code log it */
					logger
							.error("General Error occured in RequestQMsgProcessor.processMessage() :  "
									+ " No message defined for message code "
									+ "RS001");
				}
			}else
			{
				logger.info("send sms is disabled, returning true");
				response=true;
			}

		} catch (RuntimeException e) {
			logger.error("Error occured at the time of sending SMS on mobile "
					+ mobileNo + " and the message was " + message, e);
			throw new VirtualizationServiceException(e);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.MessageSendService#sendMailToRequester(java.lang.String,
	 *      java.lang.String)
	 */
	public void sendMailToRequester(String message, String emailAddress,
			String subject) throws VirtualizationServiceException {
		try {
			// Read time interval from resource bundle
			ResourceBundle resourceBundle = ResourceBundle
					.getBundle(Constants.RESOURCE_FILE_PASSWORD);
			if ("true".equalsIgnoreCase(ResourceReader.getCoreResourceBundleValue(Constants.MAIL_SENDER_SEND))) {
				logger.info(" this is the sender email server :- "
						+ resourceBundle.getString("email.server"));
				logger.info(" this is the sender email.sender.name :- "
						+ resourceBundle.getString("email.sender.name"));
				logger.info(" this is the sender email.sender :- "
						+ resourceBundle.getString("email.sender"));
				logger.info(" this is the sender email.subject :- " + subject);
				logger.info(" this is the sender emailAddress :- "
						+ emailAddress);

				/* Create a Session */
				Properties props = System.getProperties();
				/*
				 * Get the SMTP Server Name or IP Address from a properties
				 * file.
				 */
				props.put("mail.smtp.host", resourceBundle
						.getString("email.server"));
				/* Create a Session */
				Session session = Session.getDefaultInstance(props, null);
				/* Create Message Instance */
				Message newMessage = new MimeMessage(session);
				/* Set From Address */
				newMessage.setFrom(new InternetAddress(resourceBundle
						.getString("email.sender"), resourceBundle
						.getString("email.sender.name")));
				/*
				 * Add receivers. Receivers can be multiple. can be added under
				 * type to, cc or bcc
				 */
				newMessage.addRecipient(Message.RecipientType.TO,
						new InternetAddress(emailAddress));
				// Set Subject
				newMessage.setSubject(subject);
				// Set Message Content. The second argument is the mime type.
				// Use
				// appropriate mime type for your requirement
				newMessage.setContent(message, "text/html");
				Transport transport = session.getTransport("smtp");
				// Send Message
				transport.connect();
				Transport.send(newMessage);
				transport.close();
				logger.info(" Mail sent Successfully");
			}
		} catch (Exception e) {
			logger.info(" Error occured while Sending Mail");
		}

	}

}