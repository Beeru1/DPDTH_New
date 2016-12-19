package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.ibm.dp.beans.DPMissingStockApprovalFormBean;
import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.dto.DPMissingStockApprovalDTO;
import com.ibm.dp.dto.DPPurchaseOrderDTO;
import com.ibm.dp.service.DPMissingStockApprovalService;
import com.ibm.dp.service.impl.DPMissingStockApprovalServiceImpl;
import com.ibm.dp.service.impl.DPWrongShipmentServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

public class DPMissingStockApprovalAction extends DispatchAction 
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPMissingStockApprovalAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	
	public ActionForward init(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
				HttpSession session = request.getSession();
				ActionForward forward = new ActionForward(); // return value
				DPMissingStockApprovalFormBean dppfb = (DPMissingStockApprovalFormBean)form;
				DPMissingStockApprovalService dppos = new DPMissingStockApprovalServiceImpl();
				DPPurchaseOrderDTO  dto = new DPPurchaseOrderDTO (); 
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				String roleName="";
				ActionErrors errors = new ActionErrors();
				
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
				ArrayList porList = new ArrayList();
				ArrayList prCountList = new ArrayList();
				ArrayList countList = new ArrayList();
				String strSuccessMsg="";
				
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				long loginUserId = sessionContext.getId();
				
				if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_MISSING_STOCK_APPROVAL)) 
				{
					logger.info(" user not auth to perform this MISSING_STOCK_APPROVAL activity  ");
					errors.add("errors",new ActionError("errors.usernotautherized"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
				}
				
			/**
			 * pagination code
			 * **/
				int circleID=sessionContext.getCircleId();
				//int noofpages = dppos.viewPORCount(circleID);
				Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
				//countList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,-1,-1));
				//porList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,pagination.getLowerBound(),pagination.getUpperBound()));
				countList = ((ArrayList)dppos.viewPOList((sessionContext.getId()),circleID,-1,-1));
				porList = ((ArrayList)dppos.viewPOList((sessionContext.getId()),circleID,pagination.getLowerBound(),pagination.getUpperBound()));
				System.out.println("porList::::::::::::::"+porList.size());
				System.out.println("countList::::::::::::::"+countList.size());
				
				//prCountList = ((ArrayList)dppos.getPrCount(hboUser.getId()));
				int noofpages = Utility.getPaginationSize(countList.size());
				try {
					
					dppfb.setPoList(porList);
					if(session.getAttribute("err_msg_tsm") != null)
					{
						System.out.println("if---------");
						strSuccessMsg=(String) session.getAttribute("err_msg_tsm");
						session.removeAttribute("err_msg_tsm");
					}
					else
					{
						System.out.println("else");
						strSuccessMsg = "";
					}
				dppfb.setStrSuccessMsg(strSuccessMsg);
				
					System.out.println("session:::::::::::::"+session.getAttribute("err_msg_tsm"));
				//	System.out.println("request**********8"+request.getParameter("strSuccessMsg").toString());
					//dppfb.setPrCountList(prCountList);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward("viewPODetails");
				}
				request.setAttribute("porList", porList);
				request.setAttribute("PAGES", noofpages);
				
			return mapping.findForward("viewMissingPODetails");
		}//end init
	
	
	
	public ActionForward getGridData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPMissingStockApprovalFormBean dpMSAFormBean = (DPMissingStockApprovalFormBean) form;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_MISSING_STOCK_APPROVAL)) 
			{
				logger.info(" user not auth to perform this MISSING_STOCK_APPROVAL activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			logger.info("Loging USER ID  ::  "+loginUserId);
			String poNo="";
			String strSelectedDC = dpMSAFormBean.getDcNo();
			if(request.getParameter("poNo")!=null && request.getParameter("poNo")!="")
				poNo=request.getParameter("poNo");
			
			DPMissingStockApprovalService dpMSAService = new DPMissingStockApprovalServiceImpl();
			List<DCNoListDto> DCNoList = dpMSAService.getDCNosList(String.valueOf(loginUserId)); 
			List<List> initDataList = dpMSAService.getInitMissingStock(loginUserId,strSelectedDC);
			
			List<DPMissingStockApprovalDTO> listGridData = initDataList.get(0);
			
			setGridDataInSession(listGridData, session);
			
			List<DPMissingStockApprovalDTO> listAction = initDataList.get(1);
			
			dpMSAFormBean.setMissingStockList(listGridData);
			
			dpMSAFormBean.setListAction(listAction);
			dpMSAFormBean.setLstDc(DCNoList);
			dpMSAFormBean.setPoNo(poNo);
			
			request.setAttribute("poNo", poNo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	private void setGridDataInSession(List<DPMissingStockApprovalDTO> listGridData, HttpSession session) 
	{
		Map<String, DPMissingStockApprovalDTO> mapMSAGridData = new HashMap<String, DPMissingStockApprovalDTO>();
		
		Iterator itr = listGridData.iterator();
		
		while(itr.hasNext())
		{
			DPMissingStockApprovalDTO objDTO = (DPMissingStockApprovalDTO) itr.next();
			mapMSAGridData.put(objDTO.getStrSerialNo(), objDTO);
		}
		
		session.setAttribute(Constants.MSA_GRID_MAP, mapMSAGridData);
	}

	public ActionForward saveMissingStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------saveMissingStock ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPMissingStockApprovalFormBean dpMSAFormBean = (DPMissingStockApprovalFormBean) form;
		HttpSession session = null;
		try 
		{	
			session=request.getSession();
			/* Logged in user information from session */
			// Getting login ID from session
			
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_MISSING_STOCK_APPROVAL)) 
			{
				logger.info(" user not auth to perform this MISSING_STOCK_APPROVAL activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			//Updated by Shilpa Khanna on 08-09-2012
			//String[] arrCheckedMSA = request.getParameterValues("strCheckedMSA");
			String strCheckedMSA = dpMSAFormBean.getHidCheckedMSA();   
			logger.info("Checked MSA  == "+ strCheckedMSA);
			String[] arrCheckedMSA = strCheckedMSA.split(",");
			logger.info("Checked MSA length == "+ arrCheckedMSA.length);
			//Ends here 
			
			
			
			String strSelectedDC = dpMSAFormBean.getDcNo();
			
			DPMissingStockApprovalService dpDCAService = new DPMissingStockApprovalServiceImpl();			
			List<List> initDataList = null;
			Map<String, DPMissingStockApprovalDTO> mapMSAGridData = (Map<String, DPMissingStockApprovalDTO>)session.getAttribute(Constants.MSA_GRID_MAP);
			try
			{
				initDataList = dpDCAService.saveMissingStock(mapMSAGridData, arrCheckedMSA, loginUserId,strSelectedDC);
				//dpMSAFormBean.setStrSuccessMsg("Action taken Successfully on "+strSelectedDC);
				session.setAttribute("err_msg_tsm","Action taken Successfully on "+strSelectedDC);
				request.setAttribute("strSuccessMsg",strSelectedDC);
				
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				logger.info("EXCEPTION OCCURED *************---------*******************::  "+e.getMessage());
				//errors.add("errors",new ActionError(e.getMessage()));
				//saveErrors(request, errors);
				session.setAttribute("err_msg_tsm","Internal error occured.Please try again");
				return null;// mapping.findForward(null);
			}
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			List<DPMissingStockApprovalDTO> listGridData = initDataList.get(0);
			List<DPMissingStockApprovalDTO> listAction = initDataList.get(1);
			List<DCNoListDto> DCNoList = dpDCAService.getDCNosList(String.valueOf(loginUserId)); 
			
			dpMSAFormBean.setMissingStockList(listGridData);
			
			dpMSAFormBean.setListAction(listAction);
			dpMSAFormBean.setLstDc(DCNoList);
			System.out.println("##################################################");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			session.setAttribute("err_msg_tsm","Internal error occured.Please try again");
			return null;//mapping.findForward(INIT_SUCCESS);
		}
		
		
		return mapping.findForward("submitSuccess");

	}
	
	/*public ActionForward initRefresh(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward(); // return value
		DPMissingStockApprovalFormBean dppfb = (DPMissingStockApprovalFormBean)form;
		DPMissingStockApprovalService dppos = new DPMissingStockApprovalServiceImpl();
		DPPurchaseOrderDTO  dto = new DPPurchaseOrderDTO (); 
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String roleName="";
		ActionErrors errors = new ActionErrors();
		
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		ArrayList porList = new ArrayList();
		String strSuccessMsg="";
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		long loginUserId = sessionContext.getId();
		
		
		int circleID=sessionContext.getCircleId();
		//int noofpages = dppos.viewPORCount(circleID);
		Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
		DPMissingStockApprovalService dpDCAService = new DPMissingStockApprovalServiceImpl();	
		
		//countList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,-1,-1));
		//porList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,pagination.getLowerBound(),pagination.getUpperBound()));
		porList = ((ArrayList)dppos.viewPOList((sessionContext.getId()),circleID,pagination.getLowerBound(),pagination.getUpperBound()));
		System.out.println("porList::::::::::::::"+porList.size());
		System.out.println("countList::::::::::::::"+countList.size());
		
		//prCountList = ((ArrayList)dppos.getPrCount(hboUser.getId()));
		int noofpages = Utility.getPaginationSize(countList.size());
		try {
			
			dppfb.setPoList(porList);
			if(request.getParameter("strSuccessMsg")!=null && request.getParameter("strSuccessMsg")!="")
			{
				//strSuccessMsg=request.getParameter("strSuccessMsg");
				
				
			}
			else
			{
				if(session.getAttribute("err_msg_tsm") != null)
				{
					strSuccessMsg=(String) session.getAttribute("err_msg_tsm");
					session.removeAttribute("err_msg_tsm");
				}
				else
				{
					strSuccessMsg = "";
				}
			}
			dppfb.setStrSuccessMsg(strSuccessMsg);
		//	System.out.println("request**********8"+request.getParameter("strSuccessMsg").toString());
			//dppfb.setPrCountList(prCountList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("viewPODetails");
		}
		request.setAttribute("porList", porList);
		request.setAttribute("PAGES", noofpages);
		
		request.setAttribute("strSuccessMsg",strSuccessMsg);
	return mapping.findForward("viewMissingPODetails");
} */
	
	public ActionForward checkWrongInventory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{ 	 
		System.out.println("************checkWrongInventory in missing approval action******************");
		String chackInv ="";
		String extraSerialNo = request.getParameter("extraSerialNo");
		String deliveryChallanNo = request.getParameter("dc_no");
		System.out.println("deliveryChallanNo*************************************"+deliveryChallanNo);
		String productID="";
		UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		String distId = sessionContext.getId()+"";
		DPWrongShipmentServiceImpl wrongShipment = new DPWrongShipmentServiceImpl(); 
		
		try
		{ 
			chackInv = wrongShipment.checkWrongInventory(extraSerialNo , productID ,  distId , deliveryChallanNo );
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.write(""+chackInv);
			out.flush();
			}
			catch(Exception ex)
			{
				System.out.println(ex);
			}
			return null;
	}	
	
	
	
}
