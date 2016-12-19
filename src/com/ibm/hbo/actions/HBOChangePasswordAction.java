package com.ibm.hbo.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.forms.HBOChangePasswordFormBean;
import com.ibm.hbo.services.UserService;
import com.ibm.hbo.services.impl.UserServiceImpl;

/**
 * @version 	1.0
 * @author
 */
public class HBOChangePasswordAction extends Action {

	/* Logger for this class. */
	private static Logger logger =
		Logger.getLogger(HBOChangePasswordAction.class.getName());

	/* Local Variables */
	private static String INITCHANGEPASSWORD_SUCCESS =
		"initchangePasswordSuccess";
	private static String CHANGEPASSWORD_SUCCESS = "changePasswordSuccess";

	private static String CHANGEPASSWORD_FAILURE = "changePasswordFailure";

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		HBOChangePasswordFormBean hboChangePasswordFormBean =
			(HBOChangePasswordFormBean) form;
		HttpSession session = request.getSession();
		HBOUserBean sessionUserBean =
			(HBOUserBean) session.getAttribute("USER_INFO");

		try {

			if ("/initChangePassword".equalsIgnoreCase(mapping.getPath())) {
				forward = mapping.findForward(INITCHANGEPASSWORD_SUCCESS);

			}
			if ("/ChangePassword".equalsIgnoreCase(mapping.getPath())) {

				//				Encryption Code for Password 
				IEncryption encrypt = new Encryption();

				HBOUserMstr userMstrDto = new HBOUserMstr();
				userMstrDto.setOldPassword(
					encrypt.generateDigest(
						hboChangePasswordFormBean.getOldPassword()));
				userMstrDto.setNewPassword(
					encrypt.generateDigest(
						hboChangePasswordFormBean.getNewPassword()));
				userMstrDto.setUserLoginId(sessionUserBean.getUserLoginId());
				userMstrDto.setUserId(sessionUserBean.getUserLoginId());

				if (userMstrDto
					.getOldPassword()
					.equals(sessionUserBean.getUserPassword())) {
					/* 
						 * GSD implementation to validate change password
					*/
					GSDService gsdDService = new GSDService();
					gsdDService.validateChangePwd(
						userMstrDto.getUserLoginId(),
						hboChangePasswordFormBean.getNewPassword(),
						hboChangePasswordFormBean.getOldPassword());

					UserService userService = new UserServiceImpl();

					//Change password service called
					
					
					
					int changeCount =
						userService.changePasswordService(userMstrDto);
						logger.info("<<<<<in action for change password>>>>>>");
					if (changeCount == 1) {
						hboChangePasswordFormBean.setMessage(
							"Password Changed Successfully. Please login with new password");
							
						//Setting user information in session
						session.setAttribute("USER_CHANGEPWD", userMstrDto.getNewPassword());
						
						hboChangePasswordFormBean.setOldPassword("");
						hboChangePasswordFormBean.setNewPassword("");
						hboChangePasswordFormBean.setConfirmPassword("");
						

						forward = mapping.findForward(CHANGEPASSWORD_SUCCESS);
					} else {
						hboChangePasswordFormBean.setOldPassword("");
						hboChangePasswordFormBean.setNewPassword("");
						hboChangePasswordFormBean.setConfirmPassword("");
						forward = mapping.findForward(CHANGEPASSWORD_FAILURE);
					}

				} else {
					hboChangePasswordFormBean.setOldPassword("");
					hboChangePasswordFormBean.setNewPassword("");
					hboChangePasswordFormBean.setConfirmPassword("");
					errors.add(
						"errors.incorrectPassword",
						new ActionError("errors.incorrectPassword"));
					saveErrors(request, errors);
					forward = mapping.findForward(CHANGEPASSWORD_FAILURE);

				}
			}

		} catch (EncryptionException e) {
			logger.error(
				"EncryptionException in ArcChangePasswordAction: "
					+ e.getMessage());
			saveErrors(request, errors);
			forward = mapping.findForward(CHANGEPASSWORD_FAILURE);
		} catch (ValidationException ve) {
			hboChangePasswordFormBean.setOldPassword("");
			hboChangePasswordFormBean.setNewPassword("");
			hboChangePasswordFormBean.setConfirmPassword("");
			errors.add(
				"errors.changePassword",
				new ActionError(ve.getMessageId()));
			logger.error(
				"ValidationException in ArcChangePasswordAction: "
					+ ve.getMessage());
			saveErrors(request, errors);
			forward = mapping.findForward(CHANGEPASSWORD_FAILURE);
		} catch (Exception e) {

			logger.error(
				"Exception in ArcChangePasswordAction: " + e.getMessage());
			forward = mapping.findForward(CHANGEPASSWORD_FAILURE);

		}

		return (forward);

	}
}
