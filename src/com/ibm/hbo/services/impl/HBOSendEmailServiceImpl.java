/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.hbo.dao.HBOSendEmailDao;
import com.ibm.hbo.dao.impl.HBOSendEmailDaoImpl;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOSendEmailFormBean;
import com.ibm.hbo.services.HBOSendEmailService;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

/**
 * @author Anju
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOSendEmailServiceImpl implements HBOSendEmailService {

	private Logger logger = Logger.getLogger(HBOSendEmailServiceImpl.class
			.getName());

	public String sendMail(HBOSendEmailFormBean sendMail, String subject)
			throws HBOException, DAOException {
		DBConnectionManager.getDBConnection();
		String receiver_add = sendMail.getReceiverId();
		String textMessage = sendMail.getMessage_Sent();
		String message = "success";
		String strHost = PropertyReader.getValue("LOGIN.SMTP");
		String strFromEmail = sendMail.getSenderId();
		StringBuffer str = new StringBuffer();
		str.append(textMessage);
		str.append("\n");
		str.append("\n");
		str.append("Regards,");
		str.append("\n");
		str.append(sendMail.getSenderName());
		str.append("\n");
		str.append("Dist Code : " +sendMail.getDistCode());
		try {
			Properties prop = System.getProperties();
			prop.put("mail.smtp.host", strHost);
			logger.info("Host Email"+strHost);
			Session ses = Session.getDefaultInstance(prop, null);
			MimeMessage msg = new MimeMessage(ses);
			msg.setFrom(new InternetAddress(strFromEmail));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					receiver_add));
			msg.addRecipient(Message.RecipientType.CC, new InternetAddress(
					sendMail.getCcReceiverId()));
			msg.setSubject(subject);
			msg.setText(str.toString());
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (Exception e) {
			message = "failure";
			e.printStackTrace();
			logger.error("Exception occured in sendEmailService():"
					+ e.getMessage());
			throw new HBOException(e.getMessage());
		}
		return message;
	}

	public ArrayList getSubjectList() throws HBOException, DAOException {
		ArrayList subjectList = null;
		HBOSendEmailDao sendMail = new HBOSendEmailDaoImpl();
		subjectList = sendMail.getSubjectList();
		return subjectList;
	}

	public String getTSMEmailId(long loginId) throws HBOException, DAOException {
		HBOSendEmailDao sendMail = new HBOSendEmailDaoImpl();
		String tsmEmail = sendMail.getTSMEmailId(loginId);
		// TODO Auto-generated method stub
		return tsmEmail;
	}
}
