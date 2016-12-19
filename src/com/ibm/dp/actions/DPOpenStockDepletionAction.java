package com.ibm.dp.actions;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPOpenStockDepletionBean;
import com.ibm.dp.beans.DPWrongShipmentBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPOpenStockDepletionDTO;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPOpenStockDepleteService;
import com.ibm.dp.service.DPWrongShipmentService;
import com.ibm.dp.service.impl.DPOpenStockDepleteServiceImpl;
import com.ibm.dp.service.impl.DPWrongShipmentServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPOpenStockDepletionAction extends DispatchAction 
{
	private static final String SUCCESS = "success";

	/*
	 * Method for getting stored password for logged-in user group
	 */
	
	private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());
	
	public ActionForward initOpenStockDepletion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("initOpenStockDepletion Action called");
		DPOpenStockDepletionBean openStockBean = (DPOpenStockDepletionBean)form; 
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_OPEN_DEPLETION)) 
			{
				logger.info(" user not auth to perform this OPEN_STOCK_DEPLETE activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			//long loginUserId = sessionContext.getId();
			
			int intCircleID = sessionContext.getCircleId();
			
			DPOpenStockDepleteService depleteService = new DPOpenStockDepleteServiceImpl();
			List<List> listInitData = depleteService.getOpenStockDepleteInitData(intCircleID);
			
			logger.info("listInitData  ::  "+listInitData);
			openStockBean.setListTSM(listInitData.get(0));
			openStockBean.setListDist(listInitData.get(1));
			openStockBean.setListProduct(listInitData.get(2));
			
			// Get list of all TSM of given circle admin.
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		
		return mapping.findForward(SUCCESS);
	}
						 
	public ActionForward filterDistributors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		HttpSession session = request.getSession();
		try
		{
			DPOpenStockDepleteService depleteService = new DPOpenStockDepleteServiceImpl();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			int intCircleID = sessionContext.getCircleId();
			
			Integer intTSMID = Integer.valueOf(request.getParameter("intTSMID"));
			logger.info("intTSMID  ::  "+intTSMID);
			List<DPOpenStockDepletionDTO> listDist = depleteService.filterDitributors(intTSMID, intCircleID);
			
			Document resDocument = DocumentHelper.createDocument();
			Element elementOptions = resDocument.addElement("options");
				
			Iterator<DPOpenStockDepletionDTO> itr = listDist.iterator();
			DPOpenStockDepletionDTO depletionDTO = null;
			Element elementOption;
			while(itr.hasNext())
			{
				depletionDTO = itr.next();
				elementOption = elementOptions.addElement("option");
				elementOption.addAttribute("text", depletionDTO.getStrDistName());
				elementOption.addAttribute("value", depletionDTO.getIntDistID().toString());
			}
			
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(resDocument);
			writer.flush();
			out.flush();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
		
	public ActionForward getOpenStockBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		HttpSession session = request.getSession();
		try
		{
			DPOpenStockDepleteService depleteService = new DPOpenStockDepleteServiceImpl();
			
			Integer intDistID = Integer.valueOf(request.getParameter("intDistributorID"));
			Integer intProductID = Integer.valueOf(request.getParameter("intProductID"));
			
			logger.info("intDistID  ::  "+intDistID);
			Integer intOpenStockBal = depleteService.getOpenStockBalance(intDistID, intProductID);
			logger.info("AVAILABALE OPEN STOCK  ::  "+intOpenStockBal);
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(intOpenStockBal.toString());
			writer.flush();
			out.flush();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public ActionForward depleteOpenStock(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		
		try
		{
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long loginUserId = sessionContext.getId();
			
			DPOpenStockDepletionBean openStockBean = (DPOpenStockDepletionBean)form;
			Integer intDistID = openStockBean.getIntDistributorID();
			Integer intProdID = openStockBean.getIntProductID();
			Integer intDepleteStock = openStockBean.getIntStockDeplete();
			int intCircleID = sessionContext.getCircleId();
			
			DPOpenStockDepleteService depleteService = new DPOpenStockDepleteServiceImpl();
			List<List> listInitData = depleteService.depleteOpenStock(loginUserId, intCircleID, intDistID, intProdID, intDepleteStock);
			openStockBean.setListTSM(listInitData.get(0));
			openStockBean.setListDist(listInitData.get(1));
			openStockBean.setListProduct(listInitData.get(2));
			openStockBean.setStrSuccessMsg(Constants.STOCK_DEPLETE_SUCCESS_MSG);
			resetPage(openStockBean);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS);
		}
		
		return mapping.findForward(SUCCESS);
	}

	private void resetPage(DPOpenStockDepletionBean openStockBean) 
	{
		openStockBean.setIntTSMID(0);
		openStockBean.setIntDistributorID(0);
		openStockBean.setIntProductID(0);
		openStockBean.setIntCurrentStock(null);
		openStockBean.setIntStockDeplete(null);
	}
}//END


