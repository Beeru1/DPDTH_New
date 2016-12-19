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

import com.ibm.dp.beans.DPDeliveryChallanAcceptFormBean;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.service.DPDeliveryChallanAcceptService;
import com.ibm.dp.service.impl.DPDeliveryChallanAcceptServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DPDeliveryChallanAcceptAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPDeliveryChallanAcceptAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_VIEW_SUCCESS = "initViewSerialAndProduct";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPDeliveryChallanAcceptFormBean dpDCAFormBean = (DPDeliveryChallanAcceptFormBean) form;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DC_ACCEPTANCE)) 
			{
				logger.info(" user not auth to perform this ROLE_DC_ACCEPTANCE activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.getInitDeliveryChallan(loginUserId);
			
			
			dpDCAFormBean.setDeliveryChallanList(dpDCAList);
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
	
	public ActionForward acceptDeliveryChallan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------acceptDeliveryChallan ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPDeliveryChallanAcceptFormBean dpDCAFormBean = (DPDeliveryChallanAcceptFormBean) form;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DC_ACCEPTANCE)) 
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			String[] arrCheckedDC = request.getParameterValues("strCheckedDC");
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
			
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.reportDeliveryChallan(arrCheckedDC, Constants.DC_REPORT_ACCEPT, loginUserId);
			
			dpDCAFormBean.setDeliveryChallanList(dpDCAList);
			dpDCAFormBean.setErrMsg("PO has been accepted Successfully.");
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
	
	public ActionForward wrongShipment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------wrongShipment ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPDeliveryChallanAcceptFormBean dpDCAFormBean = (DPDeliveryChallanAcceptFormBean) form;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
						
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DC_ACCEPTANCE)) 
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			String[] arrCheckedDC = request.getParameterValues("strCheckedDC");
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
			
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.reportDeliveryChallan(arrCheckedDC, Constants.DC_REPORT_REJECT, loginUserId);
			
			dpDCAFormBean.setDeliveryChallanList(dpDCAList);
			dpDCAFormBean.setErrMsg("PO has been marked as Shipment Error. Kindly take further action");
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
	
	public ActionForward viewSerialsAndProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------  viewSerialsAndProductList ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		DPDeliveryChallanAcceptFormBean dpDCAFormBean = (DPDeliveryChallanAcceptFormBean) form;
		try 
		{	
			String invoiceNo = request.getParameter("invoiceNo");
			
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
	
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.vewAllSerialsOfDeliveryChallan(invoiceNo);
			
			dpDCAFormBean.setDeliveryChallanList(dpDCAList);
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
