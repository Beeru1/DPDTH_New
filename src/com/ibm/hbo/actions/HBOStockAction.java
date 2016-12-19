package com.ibm.hbo.actions;

import java.io.PrintWriter;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.dto.HBOStockDTO;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOAssignSimStockFormBean;
import com.ibm.hbo.forms.HBOBundleStockFormBean;
import com.ibm.hbo.forms.HBOVerifySimStockFormBean;
import com.ibm.hbo.forms.HBOViewBatchDetailsFormBean;
import com.ibm.hbo.forms.HBOViewBundledStockFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.HBOStockService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.HBOStockServiceImpl;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

import com.ibm.virtualization.recharge.service.AuthorizationInterface;

/**
 * @author Parul
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class HBOStockAction extends DispatchAction {
	/*
	 * Logger for the class.
	 */
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOStockAction.class);
	}


	/**
	 * @author Parul
	 *  This Function is used to get the available SIm Stock  for the Logged In 
	 * circle user and get the list of central /local List	  
	 */
	// Changed on 28/08/08 By Parul Rastogi
	public ActionForward initAssignSimStock(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			HttpSession session = request.getSession();
			ActionForward forward = new ActionForward(); // return value
			ArrayList arrMasterList = new ArrayList();
			HBOMasterService masterService = new HBOMasterServiceImpl();
			HBOStockService stockService = new HBOStockServiceImpl();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser obj=new HBOUser(sessionContext);		
			// Get Available Sim Stock for the logged In Circle Admin			
			HBOAssignSimStockFormBean assignSimStockFormBean =(HBOAssignSimStockFormBean) form;					
		    assignSimStockFormBean.setAvlSimStock(stockService.getAvlStock(obj.getUserId(), obj.getWarehouseID()));
			arrMasterList =	masterService.getMasterList(obj);			
			assignSimStockFormBean.setAccountdetails(arrMasterList);
			saveToken(request); 
			forward = mapping.findForward("success");
			return (forward);
		}

	public ActionForward getValues(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		ArrayList arrMasterList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);		
		//String role =obj.getActorId();
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
		// Get Available Sim Stock for the logged In Circle Admin
		String condition = " circle_id="+obj.getCircleId();
		if (mapping.getPath() != null
			&& mapping.getPath().equals("/initBundleStock")) {
			HBOBundleStockFormBean bundleStockFormBean =(HBOBundleStockFormBean) form;
			if(roleList.contains("ROLE_ND"))
				arrMasterList = masterService.getMasterList(obj.getUserId(),HBOConstants.CIRCLE, null);
			else
				arrMasterList = masterService.getMasterList(obj.getUserId(),HBOConstants.CIRCLE, condition);
			bundleStockFormBean.setCircledetails(arrMasterList);
			arrMasterList =	masterService.getMasterList(obj.getUserId(),HBOConstants.BUNDLEDPRODUCT, null); 
			bundleStockFormBean.setProductdetails(arrMasterList);
		}
		saveToken(request); //parul
		forward = mapping.findForward("success");
		return (forward);
	}

	//Created By Sachin 22 Nov 2007 for Available Unbunled Qty & SIM Quantity Using Ajax 
	public ActionForward getChange(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);	
		HBOMasterService masterService = new HBOMasterServiceImpl();
		ArrayList arrGetValue = new ArrayList();
		ArrayList param = new ArrayList();
		String avlQty = "0";
		param.add(request.getParameter("Id"));
		param.add(request.getParameter("cond"));
		param.add(obj.getWarehouseID());	
		arrGetValue = masterService.getChange(param);	
		if (arrGetValue.size() != 0) {
			HBOStockDTO stockDTO = (HBOStockDTO) arrGetValue.get(0);
			avlQty = stockDTO.getAvlStock();
		}
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(avlQty);
		out.flush();
		return null;
	}

	//--end method--

	
	/**
	 * @author Parul- getSimBatchDetails
	 *  This Function is used to get the detailed List of Sims  is a Batch assigned to Central/Local Warehouse
	 */
	public ActionForward getBatchDetails(ActionMapping mapping,ActionForm form,	HttpServletRequest request,	HttpServletResponse response)throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();							
		HBOStockService stockService = new HBOStockServiceImpl();
		HBOViewBatchDetailsFormBean viewBatchDetailsFormBean = (HBOViewBatchDetailsFormBean) form;
		ArrayList batchdetails = new ArrayList();		
		batchdetails =stockService.getSimBatchDetails(request.getParameter("Batch_No"));		
		logger.info("Returned ArrayList Size:"+batchdetails.size());	
		viewBatchDetailsFormBean.setBatchNo(request.getParameter("Batch_No"));
		viewBatchDetailsFormBean.setSimQty(Integer.parseInt(request.getParameter("AssignedQTY")));
		viewBatchDetailsFormBean.setSimList(batchdetails);		
		forward = mapping.findForward("success");
		return (forward);
	}

	public ActionForward getSimStockList(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		HBOVerifySimStockFormBean verifySimStockFormBean =	(HBOVerifySimStockFormBean) form;
		HBOStockService stockService = new HBOStockServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);		
		ArrayList arrAssignedBatchList = new ArrayList();
		arrAssignedBatchList=stockService.getBatchList(obj.getUserId(), obj.getWarehouseID(), "A");			
		logger.info("Inside Action :arrAssignedBatchList.size---------------"+ arrAssignedBatchList.size());
		verifySimStockFormBean.setAssignedBatch(arrAssignedBatchList);
		forward = mapping.findForward("success");
		return (forward);
	}
	public ActionForward verifySimStockList(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
			HttpSession session = request.getSession();
			ActionForward forward = new ActionForward(); // return value
			HBOVerifySimStockFormBean verifySimStockFormBean =	(HBOVerifySimStockFormBean) form;
			HBOStockService stockService = new HBOStockServiceImpl();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser obj=new HBOUser(sessionContext);		
			ArrayList arrUnverifiedBatchList = new ArrayList();
			ArrayList arrVerifiedBatchList = new ArrayList();			
			arrUnverifiedBatchList =stockService.getBatchList(obj.getUserId(), obj.getWarehouseID(), "U");		
			request.setAttribute("unverifiedBatch", arrUnverifiedBatchList);	
			verifySimStockFormBean.setUnverifiedBatch(arrUnverifiedBatchList);			
			arrVerifiedBatchList = stockService.getBatchList(obj.getUserId(), obj.getWarehouseID(), "V");		
			verifySimStockFormBean.setVerifiedBatch(arrVerifiedBatchList);		
			if(verifySimStockFormBean.getArrStatus() != null) {
				verifySimStockFormBean.setArrStatus(null);
			}
			forward = mapping.findForward("success");
			return (forward);
		}
  //Created By Parul on 30 Nov
  public ActionForward getBundledStock(
		  ActionMapping mapping,
		  ActionForm form,
		  HttpServletRequest request,
		  HttpServletResponse response)
		  throws Exception {
		  HttpSession session = request.getSession();
		  ActionForward forward = new ActionForward(); // return value
		  HBOViewBundledStockFormBean viewBundledStockFormBean =(HBOViewBundledStockFormBean) form;
		  HBOStockService stockService = new HBOStockServiceImpl();
		  UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			HBOUser obj=new HBOUser(sessionContext);		 
		  ArrayList arrBundledStock= new ArrayList();			 
		  arrBundledStock=stockService.getBundledStock(obj,"Bundled");
		  	
		  logger.info("arrBundledStock.size---------------"+ arrBundledStock.size());
		  viewBundledStockFormBean.setBundledBatch(arrBundledStock);		
		  forward = mapping.findForward("success");
		  return (forward);
	  }
	
	public ActionForward getBundledStockDetails(
			  ActionMapping mapping,
			  ActionForm form,
			  HttpServletRequest request,
			  HttpServletResponse response)
			  throws Exception {
			  HttpSession session = request.getSession();
			  String path = HBOConstants.SUCCESS;
			  ActionForward forward = new ActionForward(); // return value
			  HBOViewBundledStockFormBean viewBundledStockFormBean =(HBOViewBundledStockFormBean) form;
			  HBOStockService stockService = new HBOStockServiceImpl();
			  UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			  HBOUser obj=new HBOUser(sessionContext);	 
			  ArrayList batchDetailsList= new ArrayList();		
			  //session.setAttribute("Role",hboUser.getActorId());
			  try{
				  batchDetailsList=stockService.getBundledStockDetails(request.getParameter(HBOConstants.REQUEST_ATT_REQ_ID));
				  viewBundledStockFormBean.setRequestId(request.getParameter(HBOConstants.REQUEST_ATT_REQ_ID));
				  viewBundledStockFormBean.setBundledQTY(Integer.parseInt(request.getParameter(HBOConstants.REQ_PARAM_BUNDLED_QTY)));
				  viewBundledStockFormBean.setBundledBatch(batchDetailsList);	
				  viewBundledStockFormBean.setBundledBatchDetails(batchDetailsList);
				  request.setAttribute(HBOConstants.REQUEST_ATT_BUNDLED_LIST,batchDetailsList);
			  }catch(Exception e){
				  e.printStackTrace();
				  logger.error("Error occured in View Bundled Stock Details");
				  path = HBOConstants.ERROR_MESSAGE;
			  }
			  forward = mapping.findForward(path);
//			  session.setAttribute("Role",obj.getActorId());
//			  batchDetailsList=stockService.getBundledStockDetails(request.getParameter("RequestId"));
//			  viewBundledStockFormBean.setRequestId(request.getParameter("RequestId"));
//			  viewBundledStockFormBean.setBundledQTY(Integer.parseInt(request.getParameter("BundledQTY")));
//			  logger.info("batchDetailsList.size---------------"+ batchDetailsList.size());
//			  viewBundledStockFormBean.setBundledBatch(batchDetailsList);	
//			  viewBundledStockFormBean.setBundledBatchDetails(batchDetailsList);
//			  request.setAttribute("bundledList",batchDetailsList);
//			  forward = mapping.findForward("success");
			  return (forward);
		  }
	// Created By Parul	
	public ActionForward AssignSimStock(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		String message = "";
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		// return value			
		HBOAssignSimStockFormBean assignSimStockFormBean =(HBOAssignSimStockFormBean) form;
		//HBOUserBean sessionUserBean =(HBOUserBean) session.getAttribute("USER_INFO");
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);		
		HBOStockService stockService = new HBOStockServiceImpl();
		HBOStockDTO AssSimStkDto = new HBOStockDTO();
		/* BeanUtil to populate DTO with Form Bean data */
		BeanUtils.copyProperties(AssSimStkDto, assignSimStockFormBean);
		if (isTokenValid(request,true)){  
			message = stockService.AssignSimStock(obj,AssSimStkDto);
		}	
		if(message.equalsIgnoreCase("success")){
			messages.add("INSERT_SUCCESS",new ActionMessage("insert.success"));
		    saveMessages(request, (ActionMessages) messages); 	
		}
		if(message.equalsIgnoreCase("failure")){
			errors.add("ERROR_OCCURED",new ActionError("error.occured"));
			saveErrors(request, (ActionErrors) errors);	
		}
		forward = mapping.findForward(message);
		return (forward);
	}
	// *************************Created By Parul********************************  				
	public ActionForward VerifySimStock(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();		
		HBOVerifySimStockFormBean verifySimStockFormBean =(HBOVerifySimStockFormBean) form;
		String[] arrStatus = verifySimStockFormBean.getArrStatus();
		String[] arrBatch = verifySimStockFormBean.getArrBatch();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);	

		verifySimStockFormBean.setArrStatus(arrStatus);
		verifySimStockFormBean.setArrBatch(arrBatch);		

		HBOStockService stockService = new HBOStockServiceImpl();
		HBOStockDTO simStkDto = new HBOStockDTO();

		/* BeanUtil to populate DTO with Form Bean data */
		BeanUtils.copyProperties(simStkDto, verifySimStockFormBean);

		stockService.VerifySimStock(obj, simStkDto);
		verifySimStockFormBean.setArrStatus(null);
		forward = mapping.findForward("success");
		return (forward);
	}

	//method by sachin kumar for Bundling Stock
	public ActionForward bundleStock(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();		
		ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);		
		HBOUser obj=new HBOUser(sessionContext);	
		//String userId = hboUserBean.getUserLoginId();
		//logger.info("userId:" + userId);
		String message = "";
		HBOStockService stockService = new HBOStockServiceImpl();		
		HBOBundleStockFormBean bundleStockFormBean = (HBOBundleStockFormBean) form;
		if (isTokenValid(request,true)){
				obj.setRoleList(roleList);
				message = stockService.bundleStock(obj,bundleStockFormBean,HBOConstants.BUNDLE_STOCK,null);
				if(message.equalsIgnoreCase(HBOConstants.SUCCESS)){
					errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError(HBOConstants.errorOccuredKey));
					saveErrors(request, (ActionErrors) errors);	
				}
		}
		forward = mapping.findForward("success");
		return (forward);
	}
	//----end method--

}
