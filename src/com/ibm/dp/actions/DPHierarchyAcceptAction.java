package com.ibm.dp.actions;

import java.util.List;

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

import com.ibm.dp.beans.DPHierarchyAcceptBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPHierarchyAcceptDTO;
import com.ibm.dp.service.DPHierarchyAcceptService;
import com.ibm.dp.service.impl.DPHierarchyAcceptServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DPHierarchyAcceptAction extends DispatchAction{
	
	private static final String SUCCESS = "initSuccess";
	private static final String SUCCESS_VIEW = "SuccessView";
	private static final String ERROR ="error";
	private Logger logger = Logger.getLogger(DPHierarchyAcceptAction.class.getName());
	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		logger.info("init Action called");
		DPHierarchyAcceptBean acceptBean = (DPHierarchyAcceptBean)form;
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_HIERARCHY_ACCEPT)) 
			{
				logger.info(" user not auth to perform this ROLE_HIERARCHY_ACCEPT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			long loginUserId = sessionContext.getId();
			
			DPHierarchyAcceptService acceptService = new DPHierarchyAcceptServiceImpl();
			List<DPHierarchyAcceptDTO> listInitData = acceptService.getHierarchyTransferInit(loginUserId);
			logger.info(" listInitData  ::  "+listInitData.size());
			acceptBean.setListTransferAccept(listInitData);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		
		return mapping.findForward(SUCCESS);
	}

	public ActionForward viewHeirarchyAccept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("viewHeirarchyAccept Action called");
		DPHierarchyAcceptBean hierarchyAccBean = (DPHierarchyAcceptBean)form;
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long longLoginId = sessionContext.getId();
			String strTRNo = request.getParameter("TR_NO");
			Integer intTransferBy = Integer.valueOf(request.getParameter("TRNS_BY"));
			String strTrnsTime = request.getParameter("TRNS_TIME");
			
			DPHierarchyAcceptService acceptService = new DPHierarchyAcceptServiceImpl();
			List<DPHierarchyAcceptDTO> listInitData = acceptService.viewHierarchyAcceptList(longLoginId, strTRNo, intTransferBy, strTrnsTime);
			hierarchyAccBean.setListHierarchyView(listInitData);
			request.setAttribute("listHierarchyView", listInitData);
			
			String account_id = request.getParameter("account_id");
			String role = request.getParameter("role"); 
			
			if(account_id != null && role != null)
			{
				List<DPHierarchyAcceptDTO> listStockData = acceptService.getStockDetails(account_id, role);
				hierarchyAccBean.setList_stock_details(listStockData);
				request.setAttribute("list_stock_details", listStockData);
				hierarchyAccBean.setStrStockCount("true");
			}
			
			
			
			
			logger.info(" listInitData  ::  "+listInitData.size());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		
		logger.info("viewHeirarchyAccept Action completes");
		return mapping.findForward(SUCCESS_VIEW);
	}
	
	public ActionForward acceptHierarchy(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("-----------------acceptHierarchy ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPHierarchyAcceptBean acceptBean = (DPHierarchyAcceptBean)form;
		try 
		{	
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
						
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_HIERARCHY_ACCEPT)) 
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			String[] arrCheckedTR = request.getParameterValues("strCheckedTR");
			
			DPHierarchyAcceptService acceptService = new DPHierarchyAcceptServiceImpl();
			
			List<DPHierarchyAcceptDTO> listInitData = acceptService.acceptHierarchy(arrCheckedTR, loginUserId);
			
			acceptBean.setListTransferAccept(listInitData);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		return mapping.findForward(SUCCESS);
	
	}
	public ActionForward getStockDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("getStockDetails Action called");
		DPHierarchyAcceptBean hierarchyAccBean = (DPHierarchyAcceptBean)form;
		
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long longLoginId = sessionContext.getId();
			String account_id = request.getParameter("account_id");
			String role = request.getParameter("role"); 
			
			DPHierarchyAcceptService acceptService = new DPHierarchyAcceptServiceImpl();
			List<DPHierarchyAcceptDTO> listInitData = acceptService.getStockDetails(account_id, role);
			hierarchyAccBean.setList_stock_details(listInitData);
			//hierarchyAccBean.setListHierarchyView(listInitData);
			//request.setAttribute("listHierarchyView", listInitData);
			logger.info(" listInitData  ::  "+listInitData.size());
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		
		logger.info("viewHeirarchyAccept Action completes");
		return mapping.findForward(SUCCESS_VIEW);
	}
	
	
	
	
}