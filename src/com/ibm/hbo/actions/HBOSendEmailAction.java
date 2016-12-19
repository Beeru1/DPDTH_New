/*
 * Created on Aug 7, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.hbo.forms.HBOSendEmailFormBean;
import com.ibm.hbo.services.HBOSendEmailService;
import com.ibm.hbo.services.impl.HBOSendEmailServiceImpl;

/**
 * @author Anju
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOSendEmailAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(HBOSendEmailAction.class
			.getName());
	
	public ActionForward sendEmailMethod(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String message = "";
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors error = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		HBOSendEmailFormBean sendMail = (HBOSendEmailFormBean) form;
		ActionForward forward = new ActionForward();
		HBOSendEmailService sendEmailService = new HBOSendEmailServiceImpl();
		try {
			if ("/initSendEmail".equalsIgnoreCase(mapping.getPath())) {
				sendMail.reset();
				ArrayList subjectList = sendEmailService.getSubjectList();
				session.setAttribute("subjectList", subjectList);
				sendMail.setSubjectList(subjectList);
				String contactName = sendEmailService
						.getTSMEmailId(sessionContext.getId());
				String receiverId=contactName.substring(0, contactName.indexOf("@")-1);
				sendMail.setReceiverId(receiverId);
				sendMail.setSenderId(Constants.SENDER_ID);
				sendMail.setCcReceiverId(Constants.CC_RECEIVER_ID);
				logger.info("Send Email init called--Reciver id"+receiverId);
				forward = mapping.findForward("reachPage");
			}
			if ("/sendEmail".equalsIgnoreCase(mapping.getPath())) {

				//Mailing service called
			/*	String subject = sessionContext.getAccountCode() + " & "
						+ sessionContext.getLapuMobileNo() + " : "
						+ sendMail.getSubject();
				sendMail.setDistCode(sessionContext.getAccountCode()); */
				String subject = "Account Code: "+sessionContext.getId() + " & Lapu Mobile:"
									+ sessionContext.getLapuMobileNo() + " : "
										+ sendMail.getSubject();
				sendMail.setDistCode(sessionContext.getId()+"");
				logger.info("Account code "+sessionContext.getId());
				sendMail.setSenderName(sessionContext.getContactName());
				sendMail.setSenderId(Constants.SENDER_MAIL);
				
				logger.info("send Email called Contact Name"+sessionContext.getContactName());
				String TsmEmailId = sendEmailService
				.getTSMEmailId(sessionContext.getId());
				sendMail.setReceiverId(TsmEmailId.substring(TsmEmailId.indexOf("@")+1, TsmEmailId.length()));
				
				sendMail.setCcReceiverId(Constants.CC_RECEIVER_MAIL);
				message = sendEmailService.sendMail(sendMail, subject);
				sendMail.setMessage("Your message has been set successfully");
				if (message.equalsIgnoreCase("failure")) {
					logger.info("ERROR_OCCURED IN SEND EMAIL");
					messages.add("ERROR_OCCURED", new ActionMessage(
							"error.occured"));
				} else {
					logger.info("MESSAGE_SENT_SUCCESS THROUGH SEND EMAIL");
					messages.add("MESSAGE_SENT_SUCCESS", new ActionMessage(
							"sent.success"));
				}
				saveMessages(request, messages);
				forward = mapping.findForward("SendEmail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendMail
					.setMessage("An error occurred while sending your message.");
			error.add("errors", new ActionError(
					"An error occurred while sending your message."));
		}
		return forward;
	}
}