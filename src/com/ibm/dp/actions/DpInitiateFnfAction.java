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

import com.ibm.dp.beans.DPCreateAccountITHelpFormBean;
import com.ibm.dp.beans.DpDcDamageStatusBean;
import com.ibm.dp.beans.DpInitiateFnfFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.dto.DpInitiateFnfDto;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.service.DpDcDamageStatusService;
import com.ibm.dp.service.DpInitiateFnfService;
import com.ibm.dp.service.impl.DpDcDamageStatusServiceImpl;
import com.ibm.dp.service.impl.DpInitiateFnfServiceImpl;
import com.ibm.reports.beans.GenericReportsFormBean;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;



public class DpInitiateFnfAction extends DispatchAction {
private static Logger logger = Logger.getLogger(DpInitiateFnfAction.class.getName());
private static final String OUTPUT = "output";
public ActionForward initPendingDistributorList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
   logger.info("************** DpinitiateFnF -> PendingDistributorList() method. Called****************");
		
   try
	{
	   HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_INITIATE_FNF))) 
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
	   List<DpInitiateFnfDto> distList = null;
	   DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
		//HttpSession session = request.getSession();
		// Getting login ID from session
		//UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		//AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		long loginUserId = sessionContext.getId();
		DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
		distList = distService.getDistList(loginUserId);
		formBean.setDistList(distList);
		request.setAttribute("DIST_LIST", distList);
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
		
	}
	return mapping.findForward("success");
			
	}
public ActionForward getDistRecord(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
logger.info("************** DpinitiateFnF -> getDistRecord() method. Called for a particular distribuotr whose fnf has to be done regardless whether he has done any activation in past 31 days or not****************");
	
try
{
	
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
		String searchedDistOlmId = formBean.getSearchedDistOlmId();
		logger.info("*******************getsearchedDistributorOLMID******"+formBean.getSearchedDistOlmId());
		// formBean.setSearchedDistOlmId(searchedDistOlmId);
		DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
		long loginUserId = sessionContext.getId();
		logger.info("*******************getsearchedDistributorparentId******"+sessionContext.getId());
		DpInitiateFnfDto dto = distService.getDistListSearch(searchedDistOlmId,loginUserId);
		
		System.out.println("dto.getStrMsg().::"+dto.getStrMsg());
		if(dto != null && !dto.getStrMsg().equalsIgnoreCase("true"))
		{
			formBean.setStrMsg(dto.getStrMsg());
		}
		else
		{
			System.out.println("else");
			formBean.setDistList(dto.getDataList());
			request.setAttribute("DIST_LIST", dto.getDataList());
		}
}
catch(Exception ex)
{
	ex.printStackTrace();
	logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
	
}
return mapping.findForward("success");
		
}


public ActionForward requestForDistFnF(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
logger.info("************** DpinitiateFnF -> requestForDistFnF() method. Called****************");
	
try
{
	 HttpSession session = request.getSession();
	 UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	 AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
	 long loginUserId = sessionContext.getId();
	 DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
	 DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
	 String accountId = request.getParameter("distId");
	 String distName= request.getParameter("distOlmId_"+accountId);
	 String distRemark = request.getParameter("remark_"+accountId);
	 String debitrequired= request.getParameter("strChecked_"+accountId);
	 String days= request.getParameter("activationDays_"+accountId);
	 if(debitrequired==null)
	 {
		  debitrequired=("N");
	 }
		//logger.info("***********distName*********"+distName);
		//logger.info("***********distId**********"+accountId);
		//logger.info("***********distRemark**********"+distRemark);
		//logger.info("***********debitrequired**********"+debitrequired);
		//logger.info("***********days**********"+days);
		String msg=distService.requestForDistFnF(accountId,distRemark,debitrequired,loginUserId,days,distName);
		formBean.setStrMsg(msg);
	
}
catch(Exception ex)
{
	ex.printStackTrace();
	logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
	
}
return mapping.findForward("successList");
		
}

public ActionForward viewStock(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
	logger.info("*************************viewStock for a paricular distributor***************");
		HttpSession session = request.getSession();
	 	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	 	long accountLevel = sessionContext.getAccesslevel();
	 	logger.info("accountLevel::"+accountLevel);
	 	long loginUserId=0;
	 	String accountId = request.getParameter("distId");
	 	logger.info("accountId:::"+accountId);
	 	if(accountLevel==6)
	 	{
	 	 loginUserId = sessionContext.getId();
	 	 logger.info("loginUserId::"+loginUserId);
	 	}
	 	//add accountlevel for finance user 
	 	else if(accountLevel==5||accountLevel==15)
	 	{
	 		String tsmid = request.getParameter("tsmId");
	 		logger.info("tsmid::"+tsmid);
	 		 loginUserId=Long.parseLong(tsmid);
	 		 logger.info("loginUserId::"+loginUserId);
//set longid as tsmid for ZSM to view stock 
	 	}
		DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
		
		
		logger.info("Inside viewStock " + accountId);
		try{
			
			List<DpInitiateFnfDto> stockList = distService.viewDistStockDetail(accountId,loginUserId);
			DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
			formBean.setStockList(stockList);
			request.setAttribute("stockList", stockList);
			logger.info("Size::::::::::::::--------------------------------: "+stockList.size());
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return mapping.findForward("successViewStock");
}

public ActionForward viewHierarchyReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
	logger.info("*************************viewHierarchyReport for a paricular distributor***************");
		GenericReportsService genericService = GenericReportsServiceImpl.getInstance();
		GenericReportPararmeterDTO genericDTO = new GenericReportPararmeterDTO();
		DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
		String accountId = request.getParameter("distId");
		logger.info("Inside viewStock " + accountId);
		try{
			HttpSession session = request.getSession();
		 	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		 	String accountLevel = sessionContext.getAccountLevel();
			int reportId = Constants.REPORT_ID_VIEW_HIERARCHY_FNF;
			genericDTO.setReportId(reportId);
			genericDTO.setDistIds(accountId);
			genericDTO.setAccountLevel(accountLevel+"");
			genericDTO.setOtherUserData(0+"");
			GenericReportsOutputDto genericOutputDto = genericService.exportToExcel(genericDTO);
			formBean.setHeaders(genericOutputDto.getHeaders());
			formBean.setOutput(genericOutputDto.getOutput());
			request.setAttribute(OUTPUT, genericOutputDto.getOutput());
			request.setAttribute("header", genericOutputDto.getHeaders());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return mapping.findForward("successViewHierarchyReport");
}
public ActionForward initZSMApprovePendingDistributorList(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
	logger.info("*************************initZSMApprovePendingDistributorList for ZSM Approval***************");
	
	 HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_APPROVE_FNF))) 
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
	
	
		logger.info("Inside viewStock ");
		try{
			List<DpInitiateFnfDto> distList = null;
			   DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
			//	HttpSession session = request.getSession();
				// Getting login ID from session
			//	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			//	AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				long loginUserId = sessionContext.getId();
				logger.info("loginUserId:::::::::::"+loginUserId);
				DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
				distList = distService.getPendingDistApprovalList(loginUserId);
				formBean.setDistList(distList);
				if(distList !=null && distList.size() > 0)
				request.setAttribute("DIST_LIST", distList);
				else
				{
					formBean.setStrMsg("No Record found");
				}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return mapping.findForward("ApproveFnFList");
}
public ActionForward approveDistFnF(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
logger.info("************** approvalForDistFnF****************");
	
try
{
	 HttpSession session = request.getSession();
	 UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	 long loginUserId = sessionContext.getId();
	DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
	DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
	
	String accountId = request.getParameter("distId");
	String distName= request.getParameter("distOlmId_"+accountId);
	String reqId= request.getParameter("reqId_"+accountId);
	String approverRemark = request.getParameter("approverRemark_"+accountId);
	logger.info("***********distId**********"+accountId);
	logger.info("***********approverRemark**********"+approverRemark);
	logger.info("***********reqId**********"+reqId);
	String msg=distService.approveDistFnF(accountId,approverRemark,loginUserId,distName,reqId);
	formBean.setStrMsg(msg);
	
}
catch(Exception ex)
{
	ex.printStackTrace();
	logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
	
}
return mapping.findForward("successApproveFnF");
		
}

public ActionForward initFinanceConfirmFnF(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
		{
	logger.info("*************************initFinanceConfirmationDistributorPendingList for finance user confirmation***************");
		
	 HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_CONFIRM_FNF))) 
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
	
	
		logger.info("Inside viewStock ");
		try{
			List<DpInitiateFnfDto> distList = null;
			   DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
				//HttpSession session = request.getSession();
				// Getting login ID from session
				//UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				//AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				long loginUserId = sessionContext.getId();
				int circleId=sessionContext.getCircleId();
				logger.info("loginUserId:::::::::::"+loginUserId);
				logger.info("circleId:::::::::::"+circleId);
				DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
				distList = distService.getConfirmationPendingDistList(loginUserId,circleId);
				formBean.setDistList(distList);
				if(distList !=null && distList.size() > 0)
				request.setAttribute("DIST_LIST", distList);
				else
				{
					formBean.setStrMsg("No Record found");
				}
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return mapping.findForward("ConifrmFnFList");
}
public ActionForward confirmDistFnF(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
logger.info("************** confirmDistFnF****************");
	
try
{
	 HttpSession session = request.getSession();
	 UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	 long loginUserId = sessionContext.getId();
	 DpInitiateFnfFormBean formBean =(DpInitiateFnfFormBean)form ;
	 DpInitiateFnfService distService = new DpInitiateFnfServiceImpl();
	 	String accountId = request.getParameter("distId");
		String distName= request.getParameter("distOlmId_"+accountId);
		String reqId= request.getParameter("reqId_"+accountId);
		String confRemark = request.getParameter("confirmerRemark_"+accountId);
	 logger.info("***********reqId**********"+reqId);
	 logger.info("***********confirmerRemark_**********"+confRemark);
	 logger.info("***********accountId**********"+confRemark);
	 String msg=distService.confirmFnF(reqId,confRemark,distName,loginUserId);
	 logger.info("**********msg*********"+msg);
	 formBean.setStrMsg(msg);
	
}
catch(Exception ex)
{
	ex.printStackTrace();
	logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
	
}
return mapping.findForward("successConfirmFnF");
		
}
}
