/** This class is used for sending Email in a formatted manner.
 * 
 */
package com.ibm.virtualization.ussdactivationweb.utils.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.PropertyReader;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;



/**
 * @author abhipsa
 * 
 */
public class EmailUtility {

	private static Logger logger = Logger.getLogger(EmailUtility.class
			.getName());

	public static String FormatMsg(String loginId, String password, String name) {

		String formatedMsg = null;

		String strMessage = "  LoginId  is : " + loginId;
		String strMessage1 = "  Password is : " + password;
		StringBuffer sb = new StringBuffer();
		sb.append("<B><FONT FACE='ARIAL' SIZE='2'color='BLUE'>Dear " + name
				+ ",<BR><BR>");
		sb.append("\n\n<FONT FACE='ARIAL' SIZE='2'>" + strMessage + "<BR><BR>");
		sb.append("\n<FONT FACE='ARIAL' SIZE='2'>" + strMessage1 + "<BR><BR>");
		sb.append("\n\n<FONT FACE='ARIAL' SIZE='2'>Regards<BR>");
		sb
				.append("\n<FONT FACE='ARIAL' SIZE='2'>FTA Administration</FONT></B><BR><BR>");
		sb
				.append("\n\n  /** This is an Auto generated email.Please do not reply to this.**/");
		sb.append("<BR><BR>");
		formatedMsg = sb.toString();
		return formatedMsg;
	}

	/*public static String formatMsgForRuleFailure(String strMsg,
			RuleInstanceDTO ruleInstanceDTO, String status) {
		StringBuffer sbMsg = new StringBuffer();
		sbMsg
				.append("<font face='arial' size='2'>")
				.append(strMsg)
				.append("<br><br>Details:<br><br>")
				.append(
						"<table><tr><th>RuleInstance ID</th><th>Status</th><th>Remarks</th></tr>")
				.append(
						"<tr align=center><td>"
								+ ruleInstanceDTO.getRuleInstId() + "</td>")
				.append("<td>" + status + "</td>")
				.append(
						"<td>" + ruleInstanceDTO.getRemarks()
								+ "</td></tr></table>")
				.append(
						"<br><br>This alert is system generated. Please do not reply.")
				.append("</font");
		return sbMsg.toString();
	}*/
	public static String FormatMsgForChangePwd(String loginId) {

		String formatedMsg = null;

		String strMessage = "  LoginId  is : " + loginId;
		String emailSub = Utility.getValueFromBundle(
				Constants.EMAIL_MESSAGE_CHANGE_PASSWORD,
				Constants.EMAIL_RESOURCE_BUNDLE);
		StringBuffer sb = new StringBuffer();
		sb.append("<B><FONT FACE='ARIAL' SIZE='2'color='BLUE'>Dear " + loginId
				+ ",<BR><BR>");
		sb.append("\n<FONT FACE='ARIAL' SIZE='2'>" + emailSub + "<BR><BR>");
		sb.append("\n\n<FONT FACE='ARIAL' SIZE='2'>Regards<BR>");
		sb
				.append("\n<FONT FACE='ARIAL' SIZE='2'>FTA Administration</FONT></B><BR><BR>");
		sb
				.append("\n\n  /** This is an Auto generated email.Please do not reply to this.**/");
		sb.append("<BR><BR>");
		formatedMsg = sb.toString();
		return formatedMsg;
	}
	
	
	public static void sendEmail(EmailDTO emailDTO) throws EmailException {

		String sendTo = null;
		String From = null;
		String message = null;
		String Subject = null;
		try {
			
			//String strHost ="9.182.227.151";
			String strHost = PropertyReader.getValue(EmailConstants.Host_Email_Address);
			sendTo = emailDTO.getTo();
			From = emailDTO.getFrom();
			message = emailDTO.getMessage();
			Subject = emailDTO.getSubject();
			Properties props = new Properties();
			props.put("mail.smtp.host", strHost);
			Session session = Session.getInstance(props, null);
			MimeMessage msg = new MimeMessage(session);
			Address to = new InternetAddress(sendTo);
			Address from = new InternetAddress(From);
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					sendTo));
			msg.setFrom(from);

			msg.setRecipient(Message.RecipientType.TO, to);

			msg.setSubject(Subject);

			msg.setContent(message, "text/html");

			Transport.send(msg);
		} catch (javax.mail.internet.AddressException ae) {

			ae.printStackTrace();
			throw new EmailException(EmailConstants.INVALID_EMAIL_ADDRESS);
		} catch (javax.mail.MessagingException me) {
			me.printStackTrace();
			// throw new EmailException(EmailConstants.INVALID_EMAIL_ADDRESS);
		} catch (Exception e) {
			e.printStackTrace();
			// throw new EmailException(EmailConstants.INVALID_EMAIL_ADDRESS);
		}

	}
}
