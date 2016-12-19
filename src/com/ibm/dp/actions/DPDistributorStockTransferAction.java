package com.ibm.dp.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
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
import com.ibm.dp.beans.DPDistributorStockTransferFormBean;
import com.ibm.dp.beans.DPViewHierarchyFormBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.beans.StockTransferFormBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPDistributorStockTransferDTO;
import com.ibm.dp.service.DPDistributorStockTransferService;
import com.ibm.dp.service.DPViewHierarchyReportService;
import com.ibm.dp.service.InterSsdTransferAdminService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.StockTransferService;
import com.ibm.dp.service.impl.DPDistributorStockTransferServiceImpl;
import com.ibm.dp.service.impl.DPViewHierarchyReportServiceImpl;
import com.ibm.dp.service.impl.InterSsdTransferAdminServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dp.service.impl.StockTransferServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DPDistributorStockTransferAction extends DispatchAction 
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPDistributorStockTransferAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPDistributorStockTransferFormBean formBeanDST = (DPDistributorStockTransferFormBean) form;
		/* Logged in user information from session */
		// Getting login ID from session
		HttpSession session = request.getSession();
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		long loginUserId = sessionContext.getId();
		try 
		{	
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			//long loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DISTRIBUTOR_STOCK_TRANSFER)) 
			{
				logger.info(" user not auth to perform this ROLE_DISTRIBUTOR_STOCK_TRANSFER activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			int intCircleID = sessionContext.getCircleId();
			
			int intAccountZoneID = sessionContext.getAccountZoneId();
			DPDistributorStockTransferService dpDCAService = new DPDistributorStockTransferServiceImpl();
			List<List> listDSTInitData = dpDCAService.getInitDistStockTransfer(intCircleID, intAccountZoneID);
			formBeanDST.setListDistributor(listDSTInitData.get(0));
			formBeanDST.setListProduct(listDSTInitData.get(1));
			
			formBeanDST.setListStockTransfer(new ArrayList<DPDistributorStockTransferDTO>());
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		
		DPDistributorStockTransferFormBean formBean =(DPDistributorStockTransferFormBean)form ;
		//Setting Circle List in Form
		DPDistributorStockTransferService interssdService1 = new DPDistributorStockTransferServiceImpl();
		//List<CircleDto>  circleList = new ArrayList<CircleDto>();
		List<List<CircleDto>> getInitData  = interssdService1.getInitData(loginUserId);
		List<CircleDto> circleList = getInitData.get(0);
		List<CircleDto> busCatList = getInitData.get(1);
		
		if(sessionContext.getAccountLevel().equals("2"))
		{
			formBean.setCircleid(String.valueOf(sessionContext.getCircleId()));
			//sessionContext.getCircleName();
			CircleDto cir = new CircleDto();
			cir.setCircleId(String.valueOf(sessionContext.getCircleId()));
			cir.setCircleName(sessionContext.getCircleName());
			circleList.add(cir);
		}
		else{
			formBean.setCircleList(circleList);
			
			
			//formBean.setCircleList(circleList);
			//circleList = interssdService1.getAllCircleList();
		}
		formBean.setCircleList(circleList);
		formBean.setBusCatList(busCatList);
		//formBean.setCircleList(circleList);
		formBean.setFromTSMList(null);
		formBean.setToTSMList(null);
		return mapping.findForward(INIT_SUCCESS);
	}
	
	
	public ActionForward getTsmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			DPDistributorStockTransferFormBean formBean =(DPDistributorStockTransferFormBean)form ;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch Circle name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			DPViewHierarchyReportService revLogReportService = new DPViewHierarchyReportServiceImpl();
			long loginId = sessionContext.getId();
			//List accountList = revLogReportService.getviewhierarchyTsmAccountDetails(roleId);
			List accountList = revLogReportService.getSameTransactionTsmAccountDetails(roleId,loginId);
			formBean.setFromTSMList(accountList);
			formBean.setToTSMList(accountList);
			request.setAttribute("TsmList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) 
			{
				DPViewHierarchyFormBean statusReportBean = (DPViewHierarchyFormBean) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", statusReportBean.getAccountName());
				optionElement.addAttribute("value", statusReportBean.getAccountId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in getTsmList method of InterSsdTransferAdminAction::"+ex);
		}

		return null;
	}
	
	public ActionForward getFromDistList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					DPDistributorStockTransferFormBean formBean =(DPDistributorStockTransferFormBean)form ;
					int intTSMID = Integer.parseInt(request.getParameter("parentId"));
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					int intBusCat = Integer.parseInt(request.getParameter("busCategory"));
					
					logger.info("TSM ::  " + intTSMID);
					
					DPDistributorStockTransferService revLogReportService = new DPDistributorStockTransferServiceImpl();
					
					List accountList = revLogReportService.getDistAccountDetails(intTSMID , circleId, intBusCat);
					formBean.setFromDistList(accountList);
					logger.info("Account list size : " + accountList.size());
					//	Get account Id form session.
					//DPDistributorStockTransferFormBean formBean =(DPDistributorStockTransferFormBean)form ;
					//String levelId = request.getParameter("parentId");
					//int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					//logger.info("Fetch account name of role :" + levelId);
					//int roleId = Integer.parseInt(levelId);
					//StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					//List accountList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
					//formBean.setFromDistList(accountList);
					//logger.info("Account list size : " + accountList.size());
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = accountList.iterator();
					while (iter.hasNext()) 
					{
						StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", statusReportBean.getDistAccName());
						optionElement.addAttribute("value", statusReportBean.getDistAccId());
					}
				
					// For ajax
					response.setHeader("Cache-Control", "No-Cache");
					response.setContentType("text/xml");
					PrintWriter out = response.getWriter();
					OutputFormat outputFormat = OutputFormat.createCompactFormat();
					XMLWriter writer = new XMLWriter(out);
					writer.write(document);
					writer.flush();
					out.flush();
					// End for ajax
				
				
				} catch (Exception ex) 
				{
					ex.printStackTrace();
				}
				
				return null;
			}
	
	public ActionForward getAvailableStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------getAvailStock ACTION CALLED-----------------");
		
		Integer intDistID = Integer.valueOf(request.getParameter("intFromDist"));
		Integer intProdID = Integer.valueOf(request.getParameter("intProduct"));
		
		try {
			DPDistributorStockTransferService dpDCAService = new DPDistributorStockTransferServiceImpl();
			int intAvailStock = dpDCAService.getAvailableStock(intDistID, intProdID);
			logger.info("AVAILABALE STOCK  ::  "+intAvailStock);
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.write(""+intAvailStock);
			out.flush();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward transferDistStock(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------dpDistributorStockTransfer ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		DPDistributorStockTransferFormBean formBeanDST = (DPDistributorStockTransferFormBean) form;
		try 
		{
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DISTRIBUTOR_STOCK_TRANSFER)) 
			{
				logger.info(" user not auth to perform this ROLE_DISTRIBUTOR_STOCK_TRANSFER activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			int intCircleID = sessionContext.getCircleId();
			int intAccountZoneID = sessionContext.getAccountZoneId();
			DPDistributorStockTransferService dpDCAService = new DPDistributorStockTransferServiceImpl();
			
			List<List> listDSTData = dpDCAService.transferDistStock(formBeanDST, loginUserId, intCircleID, intAccountZoneID);
			formBeanDST.setListDistributor(listDSTData.get(0));
			formBeanDST.setListProduct(listDSTData.get(1));
			formBeanDST.setStrSuccessMsg("Stock Transfer Initiated successfully");
			
			
			int groupId = sessionContext.getGroupId();
			String circleName ="";
			CircleDto  circleDto = null;
			int circle =0;
			List<CircleDto> circleList = new ArrayList();
			if(groupId == 3){
				circleName = sessionContext.getCircleName();
				logger.info("************** circle name ==" + circleName);
				circle = sessionContext.getCircleId();
				logger.info("************** circleId name ==" + intCircleID);
				circleDto = new CircleDto();
				circleDto.setCircleId(String.valueOf(intCircleID));
				circleDto.setCircleName(circleName);
				circleList.add(circleDto);
				formBeanDST.setCircleList(circleList);
				
			}else{
			// Setting Circle List in Form
			DPDistributorStockTransferService dcNosService = new DPDistributorStockTransferServiceImpl();
			circleList = dcNosService.getAllCircleList();
			formBeanDST.setCircleList(circleList);
			formBeanDST.setFromTSMList(null);
			formBeanDST.setToTSMList(null);
			}
			
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
	public int validateToDist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------In method to validate todist and fromdist belongs to same circle-----------------");
		int intValiadte =0;
		Integer intFromDistID = Integer.valueOf(request.getParameter("intFromDist"));
		Integer intToDistID = Integer.valueOf(request.getParameter("intToDist"));
		
		try {
			logger.info("*********************12344545454656 action*********");
			DPDistributorStockTransferService dpDCAService = new DPDistributorStockTransferServiceImpl();
			 intValiadte = dpDCAService.validateToDist(intFromDistID, intToDistID);
			logger.info("distributor info  ::  "+intValiadte);

		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return intValiadte;
	}
	public ActionForward checkCombination(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		boolean intValiadte=false;
		ActionErrors errors = new ActionErrors();
		try {
			UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
			String fromDistID =request.getParameter("intFromDist");
			int intProdID = Integer.parseInt(request.getParameter("intProduct"));
			String toDistid = request.getParameter("toDistId");
			
			DPDistributorStockTransferService dpDCAService = new DPDistributorStockTransferServiceImpl();
			 intValiadte = dpDCAService.checkcombination(fromDistID, intProdID,toDistid);
			logger.info("distributor info  ::  "+intValiadte);
			Element optionElement;
			
			Document document = DocumentHelper.createDocument();
			Element options = document.addElement("options");		
		
				optionElement = options.addElement("option");
				optionElement.addAttribute("value", ""+intValiadte);
				optionElement.addAttribute("text", ""+intValiadte);
				response.setHeader("Cache-Control", "No-Cache");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();
				OutputFormat outputFormat = OutputFormat.createCompactFormat();
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();	
		} catch (RuntimeException e) {
			e.printStackTrace();
			
		}	
		return null;
			
	}	
}
