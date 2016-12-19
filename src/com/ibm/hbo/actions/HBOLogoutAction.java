package com.ibm.hbo.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOLoginFormBean;
import com.ibm.hbo.services.LoginService;
import com.ibm.hbo.services.impl.LoginServiceImpl;

/**
 * @version 	1.0
 * @author
 */
public class HBOLogoutAction extends Action {
	/*
			 * Logger for the class.
			 */
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOLogoutAction.class);
	}
	
	/* Local Variables */
	
	private static int logoutCounter = 0;
	
	private static String LOGOUT_SUCCESS = "logoutSuccess";

	private static String LOGOUT_FAILURE = "logoutFailure";

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

			ActionErrors errors = new ActionErrors();
			ActionForward forward = new ActionForward(); 
			HttpSession session = request.getSession();
			HBOLoginFormBean loginformBean = (HBOLoginFormBean) form;
		
		try {
			session.invalidate();

//					logger.info("User Logged out No. >>" + (++logoutCounter) + " at " + new Date());
//					HBOUserBean hboUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
//					LoginService loginService = new LoginServiceImpl();
//					String returnMessage = "";
//					try {
//						returnMessage = loginService.logoutUser(hboUserBean.getUserLoginId());
//						loginformBean.setMessage("Already Logged-in");
//					}catch(Exception e) {
//						;
//					}
							
					forward = mapping.findForward(LOGOUT_SUCCESS);
				} catch (Exception e) {
					logger.error("Exception From ICELogoutAction" + e);
					forward = mapping.findForward(LOGOUT_SUCCESS);

				}
		return (forward);

	}
}
