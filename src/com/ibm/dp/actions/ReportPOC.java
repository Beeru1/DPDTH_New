package com.ibm.dp.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 * @author Rohit kunder
 *	Action class for report poc with S&D
 *
 */
public class ReportPOC extends DispatchAction {


private static final String INIT_SUCCESS = "initsuccess";

public ActionForward initReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		session.getAttribute("USER_INFO");
		request.setAttribute("user", "disttest");
		request.setAttribute("password", "disttest@1");
		return mapping.findForward(INIT_SUCCESS);
		
		
		
		
		
}

public ActionForward Report(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			
				
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				session.getAttribute("USER_INFO");
				request.setAttribute("user", "disttest");
				request.setAttribute("password", "disttest@1");
				return mapping.findForward("success");
		
}
}// end initPO



