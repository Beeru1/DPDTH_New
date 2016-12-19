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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.StockTransferFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DistributorDetailsDTO;
import com.ibm.dp.service.StockTransferService;
import com.ibm.dp.service.impl.StockTransferServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
/**
 * @author Mohammad Aslam
 */
public class StockTransferAction extends DispatchAction 
{
	private Logger logger = Logger.getLogger(StockDeclarationAction.class.getName());
	private static final String INIT_SUCCESS = "initSuccess";
	
	public ActionForward initStockTrans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		StockTransferFormBean stfb = (StockTransferFormBean) form;
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long accountId = userSessionContext.getId(); // Login_Id,Account_id,ZBM_ID,ZSM_ID,TSM_Account_id
			
			List<DistributorDetailsDTO> distributorDetailsList =  new ArrayList<DistributorDetailsDTO>(); 	
			String dcNumber="";
			String from="";
			String to="";
			
			StockTransferService sts = new StockTransferServiceImpl();
			
			List<DistributorDTO> distributorFromList = sts.getFromDistributors(accountId);
			request.setAttribute("distributorFromList", distributorFromList);	
			stfb.setDistributorFromList(distributorFromList);
			stfb.setDcNumber(null);
			stfb.setFrom(null);
			stfb.setTo(null);	
			stfb.setAction(null);
			stfb.setTransferRequestedBy("");
			request.setAttribute("distributorDetailsList", distributorDetailsList);
			
			request.setAttribute("strAction", "");
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}

	public ActionForward getToDistributors(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.info("getToDistributors action called");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		long accountId = userSessionContext.getId(); // Login_Id,Account_id,ZBM_ID,ZSM_ID,TSM_Account_id
		StockTransferService sts = new StockTransferServiceImpl();
		List<DistributorDTO> distributorToList = sts.getToDistributors(accountId);	
		Element optionElement;
		DistributorDTO currentDistDTO;
		Document document = DocumentHelper.createDocument();
		Element options = document.addElement("options");		
		Iterator<DistributorDTO> distributorToListItr = distributorToList.iterator();
		while (distributorToListItr.hasNext()) {
			currentDistDTO = distributorToListItr.next();
			optionElement = options.addElement("option");
			optionElement.addAttribute("value", currentDistDTO.getAccountId());
			optionElement.addAttribute("text", currentDistDTO.getAccountName());
		}		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		return null;
	}
	
	public ActionForward getTransferDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		StockTransferFormBean stfb = (StockTransferFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long accountId = userSessionContext.getId();
			StockTransferService sts = new StockTransferServiceImpl();
			String dcNumber = sts.getDCNumber();
			stfb.setDcNumber(dcNumber);
			request.setAttribute("dcNumber", dcNumber);
			logger.info("DC Number : " + dcNumber);
			
			String from = request.getParameter("transferRequestedBy");
			String to = request.getParameter("transferTo");
			sts = new StockTransferServiceImpl();
			
			stfb.setFrom(from);
			request.setAttribute("from", from);
			stfb.setTo(to);
			request.setAttribute("to", to);
			
			List<DistributorDetailsDTO> distributorDetailsList = sts.getTransferDetails(accountId, Long.parseLong(from), Long.parseLong(to));
			stfb.setDistributorDetailsList(distributorDetailsList);
			request.setAttribute("distributorDetailsList", distributorDetailsList);
			logger.info("Distributor Detaisl list : " + distributorDetailsList.size());
			
			//request.setAttribute("fromGetTransferDetails", "yes");
			
			sts = new StockTransferServiceImpl();
			List<DistributorDTO> distributorToList = sts.getToDistributors(accountId);
			stfb.setToDistList(distributorToList);
			request.setAttribute("distributorToList",distributorToList);
			
			sts = new StockTransferServiceImpl();
			List<DistributorDTO> distributorFromList = sts.getFromDistributors(accountId);
			request.setAttribute("distributorFromList", distributorFromList);
			stfb.setTo(distributorToList.get(0).getAccountName());
			stfb.setFrom(distributorFromList.get(0).getAccountName());
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("SUCCESS");
		}	
		
		return mapping.findForward("success");		
	}	

	public ActionForward viewStockSerialNoList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long accountId = userSessionContext.getId();
			long productId = Long.parseLong(request.getParameter("productId"));
			StockTransferService sts = new StockTransferServiceImpl();
			List serialNumberList = sts.getSerailNumberList(accountId, productId);
			request.setAttribute("serialNumberList", serialNumberList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SUCCESS_MESSAGE);
		}
		return mapping.findForward(Constants.SUCCESS_MESSAGE);	
	}
	
	public ActionForward submitTransferDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		StockTransferFormBean stfb = (StockTransferFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			long accountId = userSessionContext.getId();
			StockTransferService sts = new StockTransferServiceImpl();
			String from = stfb.getTransferRequestedBy();
			String to = stfb.getTransferTo();
			
			
			List<DistributorDetailsDTO> distributorDetailsList = sts.getTransferDetails(accountId, Long.parseLong(from), Long.parseLong(to));
			
			sts = new StockTransferServiceImpl();
			
			String submitResult = sts.submitTransferDetails(stfb, distributorDetailsList, accountId);
			String strDCNo = stfb.getDcNumber();
			ActionMessages msg = new ActionMessages();
			if(submitResult.equalsIgnoreCase(Constants.SUCCESS_MESSAGE))
			{
				if(stfb.getAction()[0].equalsIgnoreCase("CANCEL"))
				{
					stfb.setStrSuccessMsg(Constants.ST_STOCK_SUCCESS_MESSAGE_CANCEL+strDCNo);
				}
				else
				{
					stfb.setStrSuccessMsg(Constants.ST_STOCK_SUCCESS_MESSAGE+strDCNo);
				}
				
			}
			
			sts = new StockTransferServiceImpl();
			List<DistributorDTO> distributorFromList = sts.getFromDistributors(accountId);
			request.setAttribute("distributorFromList", distributorFromList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SUCCESS_MESSAGE);
		}	
		
		return mapping.findForward(Constants.SUCCESS_MESSAGE);		
	}	
	
}