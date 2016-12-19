package com.ibm.dp.actions;

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
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.ErrorPOFormBean;
import com.ibm.dp.beans.StbFlushOutFormBean;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.service.ErrorPOService;
import com.ibm.dp.service.STBFlushOutService;
import com.ibm.dp.service.impl.ErrorPOServiceImpl;
import com.ibm.dp.service.impl.STBFlushOutServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class ErrorPOAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ErrorPOAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_VIEW_SUCCESS = "initViewErrorPODetails";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init called from ErrorPOAction.");
		ActionErrors errors = new ActionErrors();
		ErrorPOFormBean ErrorPOBean  = (ErrorPOFormBean) form;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			String loginUserIdAndGroup = sessionContext.getId()+","+sessionContext.getAccesslevel();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ERROR_PO)) 
			{
				logger.info(" user not auth to perform this ROLE_VIEW_ERROR_PO activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			System.out.println(loginUserIdAndGroup);
			ErrorPOService ErrorPOService = new ErrorPOServiceImpl();
			ArrayList<DuplicateSTBDTO> duplicateSTBList  = ErrorPOService.getDuplicateSTB(loginUserIdAndGroup);
			
			
			logger.info("ArrayList returned from DB  ::  "+duplicateSTBList);
			ErrorPOBean.setDuplicateSTBList(duplicateSTBList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward viewPODetailList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("------- viewPODetailList called from ErrorPOAction.");
		ActionErrors errors = new ActionErrors();
		ErrorPOFormBean ErrorPOBean  = (ErrorPOFormBean) form;
		try		
		{	
			String poNumber = request.getParameter("poNumber");		
			
			ErrorPOService ErrorPOService = new ErrorPOServiceImpl();
			ArrayList<DuplicateSTBDTO> duplicateSTBList  = ErrorPOService.viewPODetailList(poNumber);
			
			ErrorPOBean.setDuplicateSTBList(duplicateSTBList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_VIEW_SUCCESS);
		}
		return mapping.findForward(INIT_VIEW_SUCCESS);
	}
		
}
