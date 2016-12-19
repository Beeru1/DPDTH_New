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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Controller class for processing the index page of application
 * 
 * @author Paras
 */
public class IndexAction extends Action {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(IndexAction.class.getName());

	/* Local Variables */
	private static final String INVALID_SESSION = "invalidSession";

	private static final String DISPLAY_LOGIN = "displayLogin";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		if (!request.getSession().isNew()) {
			logger.info("  invalidate old Session if any .");
			request.getSession().invalidate();
			return mapping.findForward(INVALID_SESSION);
		}
		logger.info("   Redirect to login jsp.");
		logger.info(" Executed... ");
		return mapping.findForward(DISPLAY_LOGIN);
	}
}
