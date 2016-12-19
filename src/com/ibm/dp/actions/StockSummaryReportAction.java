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

import com.ibm.dp.beans.RepairSTBBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.dp.service.RepairSTBService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.RepairSTBServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class StockSummaryReportAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";

	/*
	 * Method for getting stored password for logged-in user group
	 */

	private Logger logger = Logger.getLogger(StockSummaryReportAction.class.getName());

	public ActionForward initStockSummaryReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initStockSummaryReport() method.");
		
		StockSummaryReportBean revLogReportBean = (StockSummaryReportBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String userId = (String) session.getAttribute("userId");
		revLogReportBean.setCircleid(sessionContext.getCircleId());
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		DPMasterService dpms = new DPMasterServiceImpl();
		ArrayList ciList = new ArrayList();
		ArrayList cgList = new ArrayList(); // for card group list
		
		try {
			
			//log.info("***ABOVE BUSINESS CATEGORY***");
			ciList = dpms.getCircleID(userId);
			String circleIdSr = String.valueOf(sessionContext.getCircleId());
			logger.info("In initStockSummaryReport() method.   circle == "+circleIdSr);
			
			//setDefaultValue(revLogReportBean, request,circleIdSr);
			
			revLogReportBean.setArrCIList(ciList);
			if(revLogReportBean.getCircleid() != 0){
				revLogReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				cgList=dpms.getCardGroups(""+revLogReportBean.getCircleid());
				
				
				int roleId = sessionContext.getCircleId();
				StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
				
				List tsmList = revLogReportService.getRevLogTsmAccountDetails(roleId);
				revLogReportBean.setTsmList(tsmList);
				request.setAttribute("tsmList",tsmList);
				logger.info("tsmList list size : " + tsmList.size());
				System.out.println(request.getParameter("circleId"));
				int circleId = sessionContext.getCircleId();
				
				List distList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
				revLogReportBean.setDistList(distList);
				//request.setAttribute("distList",distList);
				logger.info("distList list size : " + distList.size());
				logger.info("loginRole : " + loginRole);
				if(loginRole == 6)
				{
					logger.info(" in Summary report Action..... level id == 6" );
					revLogReportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					revLogReportBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					StockSummaryReportService revLogReportService1 = new StockSummaryReportServiceImpl();
					tsmList = null;
					tsmList = revLogReportService1.getRevLogTsmDistLogin(Integer.parseInt(sessionContext.getId()+""));
					Iterator it = tsmList.iterator();
					String tsmIdSelected = "";
					while(it.hasNext())
					{
						tsmIdSelected = ((StockSummaryReportBean) it.next()).getAccountId();
					}
					revLogReportBean.setAccountId(tsmIdSelected);
					revLogReportBean.setTsmList(tsmList);
					request.setAttribute("tsmList",tsmList);
					logger.info("tsmList list size : " + tsmList.size());
					StockSummaryReportBean StockSummaryReportBean = new StockSummaryReportBean();
					StockSummaryReportBean.setDistAccName(sessionContext.getAccountName());
					StockSummaryReportBean.setDistAccId(sessionContext.getId()+"");
					distList = new ArrayList<StockSummaryReportBean>();
					distList.add(StockSummaryReportBean);
					revLogReportBean.setDistList(distList);
					revLogReportBean.setDistAccId(sessionContext.getId()+"");
				}else if(loginRole == 5)
				{
					logger.info(" in Summary report Action..... level id == 5" );
					revLogReportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					revLogReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					revLogReportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					
					StockSummaryReportService revLogReportService1 = new StockSummaryReportServiceImpl();
					logger.info("TSM Name == "+sessionContext.getAccountName());
					StockSummaryReportBean stockSummaryReportBean = new StockSummaryReportBean();
					stockSummaryReportBean.setAccountName(sessionContext.getAccountName());
					stockSummaryReportBean.setAccountId(sessionContext.getId()+"");
					tsmList = new ArrayList<StockSummaryReportBean>();
					tsmList.add(stockSummaryReportBean);
					logger.info("tsmList list size : " + tsmList.size());
					revLogReportBean.setTsmList(tsmList);
					request.setAttribute("tsmList",tsmList);
					logger.info("sessionContext.getId() ==="+sessionContext.getId());
					revLogReportBean.setAccountId(sessionContext.getId()+"");
					
					List accountList = revLogReportService.getRevLogDistAccountDetails(Integer.parseInt(String.valueOf(sessionContext.getId()).trim()) , circleId);
					logger.info("Dist List == ==="+accountList.size());
					revLogReportBean.setDistList(accountList);
					
					
				}
				else if(loginRole == 3 || loginRole == 4)
				{
					logger.info(" in Summary report Action..... level id == " +loginRole);
					revLogReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					revLogReportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					revLogReportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					revLogReportBean.setCircleid(circleId);
					StockSummaryReportService revLogReportService1 = new StockSummaryReportServiceImpl();
					logger.info("TSM Name == "+sessionContext.getAccountName());
				
				}
				else
				{
					revLogReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				}
				
				
			
			}else{
				revLogReportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				revLogReportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				revLogReportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
				cgList=dpms.getCardGroups("0");
				
			}
			revLogReportBean.setArrCGList(cgList);
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

	
	public ActionForward initDistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				
				try 
				{
					//	Get account Id form session.
					HttpSession session = request.getSession();
					UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					Long accId = sessionContext.getId();
					int distId = Integer.parseInt(String.valueOf(accId));
					
					StockSummaryReportBean revLogReportBean = (StockSummaryReportBean) form;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
					
					List accountList = revLogReportService.getRevLogDistAccountDetails(roleId , circleId);
					revLogReportBean.setDistList(accountList);
					request.setAttribute("productStatusList",accountList);
					logger.info("Account list size : " + accountList.size());
					
								
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
	
	public ActionForward initTsmReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		
		try 
		{
			//	Get account Id form session.
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accId = sessionContext.getId();
			int tsmId = Integer.parseInt(String.valueOf(accId));
			
			StockSummaryReportBean revLogReportBean = (StockSummaryReportBean) form;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch account name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
			
			List accountList = revLogReportService.getRevLogTsmAccountDetails(roleId);
			revLogReportBean.setTsmList(accountList);
			request.setAttribute("productStatusList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) 
			{
				StockSummaryReportBean statusReportBean = (StockSummaryReportBean) iter.next();
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
		}

		return null;
	}
	
	public ActionForward getReverseLogisticReportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In getReverseLogisticReportExcel()  of StockSummary report Action");
		StockSummaryReportBean reportBean = (StockSummaryReportBean) form;
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String circleId = request.getParameter("circleId");
		String tsmId = request.getParameter("accountId");  
		int distId = Integer.parseInt(request.getParameter("distAccId"));
		String productId =request.getParameter("selectedProductId");
		logger.info("In getReverseLogisticReportExcel()  of StockSummary report Action   productId== "+productId);
		HttpSession session = request.getSession();
		try {
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
			 
			StockSummaryReportService revLogReportService = new StockSummaryReportServiceImpl();
			List<StockSummaryReportDto> revLogReportList = revLogReportService.getRevLogReportExcel(distId , fromDate, toDate ,circleId , tsmId,loginRole,productId);
			logger.info("Size of Report Data Excel :" + revLogReportList.size());
			reportBean.setReportList(revLogReportList);
			request.setAttribute("exceptionReportList",revLogReportList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}
/*	private void setDefaultValue(StockSummaryReportBean reverseCollectionBean,
			HttpServletRequest request,String circleId) throws Exception {
		StockSummaryReportService stbService = new StockSummaryReportServiceImpl();
			List<ProductMasterDto> productList = stbService.getProductTypeMaster(circleId);

		reverseCollectionBean.setProductList(productList);
		request.setAttribute("productList", productList);
		
	}*/

}
