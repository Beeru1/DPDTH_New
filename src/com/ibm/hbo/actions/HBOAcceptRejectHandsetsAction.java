/*
 * Created on Nov 22, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.ibm.hbo.exception.DAOException;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dto.DistStockDTO;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOAssignProdStockFormBean;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

/**
 * @author Admin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


public class HBOAcceptRejectHandsetsAction extends DispatchAction{
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOAcceptRejectHandsetsAction.class);
	}
	public ActionForward viewAssignedStock(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response)
	throws Exception{
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		try{
			HBOStockService stockService = new HBOStockServiceImpl();
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser hboUser = new HBOUser(userSessionContext);
			int warehouseId = Integer.parseInt(hboUser.getWarehouseID());
			ArrayList stockList = new ArrayList();
			stockList = stockService.getAssignedStock(warehouseId,HBOConstants.ALL_ASSIGNED_STOCK);
			assignStockFormBean.setArrAssignedProdList(stockList);
			if(stockList.size()==0){
				errors.add(HBOConstants.NO_RECORD,new ActionError("no.record.found"));
				saveErrors(request, (ActionErrors) errors);
			}
		}
		catch(DAOException e){
			e.printStackTrace();
			errors.add(HBOConstants.DAOEXCEPTION_OCCURED,new ActionError("dao.exception"));
			saveErrors(request, (ActionErrors) errors);
		}
		catch(Exception e){
			e.printStackTrace();
			errors.add(HBOConstants.EXCEPTION_OCCURED,new ActionError("exception.occured"));
			saveErrors(request, (ActionErrors) errors);
		}
		saveToken(request);
		return mapping.findForward(HBOConstants.SUCCESS_MESSAGE);
}

	public ActionForward viewStock(
	ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		HBOStockService stockService = new HBOStockServiceImpl();
		String path = HBOConstants.SUCCESS_MESSAGE;
		try{
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser hboUser = new HBOUser(userSessionContext);
			ArrayList stockList = new ArrayList();
			int warehouseId = Integer.parseInt(hboUser.getWarehouseID());
			ArrayList stockInTransitList = stockService.getAssignedStock(warehouseId,HBOConstants.STOCK_IN_TRANSIT);
			request.setAttribute(HBOConstants.UNVERIFIED_BATCH,stockInTransitList);
			logger.info("stockInTransitList size"+stockInTransitList.size());
			stockList = stockService.getAssignedStock(warehouseId,HBOConstants.ACCEPTED_REJECTED_STOCK);
			request.setAttribute(HBOConstants.VERIFIED_BATCH,stockList);
			logger.info("stockList size"+stockList.size());
			assignStockFormBean.setAcceptRejectProdList(stockList);
			assignStockFormBean.setArrAssignedProdList(stockInTransitList);
			if(assignStockFormBean.getArrStatus() !=null){
				assignStockFormBean.setArrStatus(null);
			}
			if(stockInTransitList.size()==0){
				messages.add("NO_TRANSIT_RECORD",new ActionMessage("no.record.found"));
				saveMessages(request,messages);
			}
			if(stockList.size()==0){
				messages.add("NO_RECORD",new ActionMessage("no.record.found"));
				saveMessages(request,messages);
			}
		} catch(Exception e){
			e.printStackTrace();
			errors.add("ERROR_OCCURED",new ActionError("error.occured"));
			path = HBOConstants.ERROR_MESSAGE;
		}
	    saveToken(request);
		return mapping.findForward(path);

		}

	public ActionForward viewBatchDetails(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		String condition = request.getParameter(HBOConstants.REQUEST_ATT_CONDITION);
		String batch_no  = request.getParameter(HBOConstants.REQUEST_ATT_ID);
		String roleName = "";
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
		assignStockFormBean.setBatch_no(batch_no);
		assignStockFormBean.setBatchNo(batch_no);
		HBOStockService stockService = new HBOStockServiceImpl();
		ArrayList batchDetailsList = stockService.getBatchDetails(batch_no);
		assignStockFormBean.setCond(condition);
		assignStockFormBean.setArrBatchDetailsList(batchDetailsList);
		if(roleList.contains(HBOConstants.ROLE_NATIONALDIST))
			roleName = HBOConstants.ROLE_NATIONALDIST;
		else if(roleList.contains(HBOConstants.ROLE_LOCALDIST))
			roleName = HBOConstants.ROLE_LOCALDIST;
		else if(roleList.contains(HBOConstants.ROLE_DIST))
			roleName = HBOConstants.ROLE_DIST;
		else
			roleName = HBOConstants.ROLE_SUPER;
		//assignStockFormBean.setRole(roleName);
		request.setAttribute(HBOConstants.ROLE, roleName);
		//assignStockFormBean.setDamageFlag("N");
		//request.setAttribute(HBOConstants.BATCH_DETAILS_LIST,batchDetailsList);
		return mapping.findForward(HBOConstants.SUCCESS);
}	
	public ActionForward viewBatchDetailsDamage(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
				HttpSession session = request.getSession();
				HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
				String condition = HBOConstants.ACCEPTED_STOCK;
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				String batch_no  = request.getParameter(HBOConstants.REQUEST_ATT_BATCH);
				assignStockFormBean.setBatch_no(batch_no);
				assignStockFormBean.setBatchNo(batch_no);
				HBOStockService stockService = new HBOStockServiceImpl();
				assignStockFormBean.setWarehouseIdHidden(hboUser.getId().intValue());
				ArrayList batchDetailsList = stockService.getBatchDetails(batch_no);
				assignStockFormBean.setCond(condition);
				assignStockFormBean.setArrBatchDetailsList(batchDetailsList);
				//assignStockFormBean.setDamageFlag("N");
				request.setAttribute("batchDetailsList",batchDetailsList);
				return mapping.findForward(HBOConstants.SUCCESS);
		}
	public ActionForward viewAssignedBatchDetails(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		String condition="";
		String batch_no="";
		condition = request.getParameter("cond");
		batch_no  = request.getParameter("Id");
		assignStockFormBean.setBatch_no(batch_no);
		assignStockFormBean.setBatchNo(batch_no);
		HBOStockService stockService = new HBOStockServiceImpl();
		ArrayList batchDetailsList = stockService.getBatchDetails(batch_no);
		//request.setAttribute("cond",condition);
		assignStockFormBean.setCond(condition);
		assignStockFormBean.setArrBatchDetailsList(batchDetailsList);
		assignStockFormBean.setDamageFlag("Y");
		request.setAttribute("batchDetailsList",batchDetailsList);
		return mapping.findForward("success");
}	

	public ActionForward acceptRejectStock(ActionMapping mapping,
	ActionForm form,
	HttpServletRequest request,
	HttpServletResponse response)
	throws Exception{
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		HBOStockService stockService = new HBOStockServiceImpl();
		ActionForward forward = new ActionForward();
		HBOStockDTO prodStkDto = new HBOStockDTO();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		String[] arrStatus = assignStockFormBean.getArrStatus();
		String[] arrBatch = assignStockFormBean.getArrBatch();
		HBOUser userBean = new HBOUser(userSessionContext);
		userBean.setRoleList(authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB));
		for (int i = 0; i < arrStatus.length; i++) {
			logger.info("&&&&" + i + ":::::" + arrStatus[i]);
			logger.info("&&&&" + i + ":::::" + arrBatch[i]);
		}
		/* BeanUtil to populate DTO with Form Bean data */
		BeanUtils.copyProperties(prodStkDto, assignStockFormBean);
		String path=HBOConstants.SUCCESS_MESSAGE;
		if (isTokenValid(request,true)){ 
		path = stockService.acceptRejectStock(userBean, prodStkDto);
		}
		forward = mapping.findForward(path);
		return forward;
		//viewAssignedStock(mapping,form,request,response);
	}
	public ActionForward viewInvoiceList(ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		HBOAssignProdStockFormBean assignStockFormBean = (HBOAssignProdStockFormBean) form;
		HBOStockService stockService = new HBOStockServiceImpl();
		String path = HBOConstants.SUCCESS_MESSAGE;
		DistStockDTO distDto = new DistStockDTO();
		ArrayList ivoiceList = stockService.getInvoiceList(distDto);
		return mapping.findForward("success");
	}
}	