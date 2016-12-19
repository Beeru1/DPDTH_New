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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.beans.AuthenticationFormBean;
import com.ibm.virtualization.recharge.beans.UserBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.virtualization.recharge.dto.Link;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.virtualization.recharge.service.UserService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.UserServiceImpl;

/**
 * Controller class for Login with Account Code
 * 
 * @author Paras
 */
public class LoginActionHeader extends Action {

	/* Logger for this class. */

	private static String FORWARD = "PasswordExpired";

	/**
	 * This method authenticates a user
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
		response.setHeader("NAME", "userId");
		RequestDispatcher ss = request.getRequestDispatcher("");
		ss.forward(request,response);
		return null;
	}
}