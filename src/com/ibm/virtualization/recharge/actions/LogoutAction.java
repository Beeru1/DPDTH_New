/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.actions;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * Controller class to logout the user
 * 
 * @author Paras
 */
public class LogoutAction extends Action {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(LogoutAction.class
			.getName());

	/* Local Variables */
	private static final String LOGOUT_SSF = "SSFlogout";
	private static final String LOGOUT_FAILURE = "login";

	private static final String LOGOUT_SUCCESS = "logoutSuccess";

	/**
	 * method to logout
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//logger.info(" Started... ");
		HttpSession session = request.getSession(false);
		try {
			if (session != null) {
				@SuppressWarnings("unused")
				/* get the user information from session */
				Object userInfo = request.getSession().getAttribute(
						Constants.AUTHENTICATED_USER);
				if (null != userInfo) {
					UserSessionContext userSessionContext = (UserSessionContext) userInfo;
					if (userSessionContext != null) {
						logger.info("  User Logging out is "
								+ userSessionContext.getId());
						logger.info("  User AuthType is "
								+ userSessionContext.getAuthType());
					}
					if(userSessionContext.getAuthType()!= "SSF"){
					/* set the user information in session to null */
					userSessionContext = null;
					/* invalidate the current session */
					session.invalidate();
					}else{
						
						return mapping.findForward(LOGOUT_SSF);
					}
				}
			}
			/*else {
				return mapping.findForward(LOGOUT_FAILURE);
			}*/
		} catch (Exception exp) {
			logger.error(" Exception occured while logout. Message  "
					+ exp.getMessage(), exp);
		}
		logger.info("  User successfully logout.");
		logger.info(" Executed... ");
		return mapping.findForward(LOGOUT_SUCCESS);
	}
}