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

import com.ibm.dp.beans.ConsumptionPostingDetailReportBeans;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.service.ConsumptionPostingDetailReportService;
import com.ibm.dp.service.impl.ConsumptionPostingDetailReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class ConsumptionPostingDetailReportAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(ConsumptionPostingDetailReportAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";

	public ActionForward initConsDetailReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		logger.info("In initInventoryStatusReport() method.");
		HttpSession session = request.getSession();
		DPMasterService dpms = new DPMasterServiceImpl();
		ActionErrors errors = new ActionErrors();
		ArrayList ciList = new ArrayList();
		String userId = (String) session.getAttribute("userId");
		try{
		ciList = dpms.getCircleID(userId);
		ConsumptionPostingDetailReportBeans reportBean = (ConsumptionPostingDetailReportBeans) form;
		ConsumptionPostingDetailReportService reportService = new ConsumptionPostingDetailReportServiceImpl();
		
		UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		Long strLoginId = sessionContext.getId();
		reportBean.setArrCIList(ciList);
		int circleId = sessionContext.getCircleId();
		
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		logger.info("Login Users Circle id == "+String.valueOf(sessionContext.getCircleId()));
		logger.info(" in Summary report Action..... level id == " +loginRole);
		 if(loginRole == 1 || loginRole == 0)
		{
			reportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
			
		}else{
			
			ConsumptionPostingDetailReportService revLogReportService = new ConsumptionPostingDetailReportServiceImpl();
			
			List accountList = revLogReportService.getRevLogTsmAccountDetails(circleId);
			reportBean.setTsmList(accountList);
			request.setAttribute("tsmList",accountList);
			logger.info("Account list size : " + accountList.size());
			reportBean.setCircleid(circleId);
			request.setAttribute("circleid",circleId);
			reportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			
		}	
	}catch(Exception ex)
		{
		ex.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
		errors.add("errors",new ActionError(ex.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(INIT_SUCCESS);
		}
			
		
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward getReportDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		logger.info("In getReportDataExcel() ");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		System.out.println("--------------getReportDataExcel---------------------------");
		// Get From date and TO date.
		try{
		ConsumptionPostingDetailReportBeans reportBean = (ConsumptionPostingDetailReportBeans) form;
		String dateFromDt = request.getParameter("fromDate");
		String dateToDt = request.getParameter("toDate");
		String circleId = String.valueOf(sessionContext.getCircleId());
		int selectedCircleId = reportBean.getCircleid();
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		if(loginRole ==2){
			selectedCircleId =Integer.parseInt(circleId);
		}
		String strTSMId = reportBean.getAccountId();
		int strDistId = Integer.parseInt(request.getParameter("distributorId"));
		logger.info("In getReportDataExcel() dateFromDt == "+dateFromDt+"   dateToDt=="+dateToDt+"  strDistId=="+strDistId+" selected circleId=="+selectedCircleId);
		
		ConsumptionPostingDetailReportService reportService = new ConsumptionPostingDetailReportServiceImpl();
		List<ConsumptionPostingDetailReportDto> reportDataList =reportService.getReportExcel(strDistId, dateFromDt,dateToDt, circleId,selectedCircleId,strTSMId);
		reportBean.setReportDataList(reportDataList);
		request.setAttribute("reportDataList",reportDataList);
		}catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			errors.add("errors",new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}

	public ActionForward initTsmReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method initStatusAccount().");
		ActionErrors errors = new ActionErrors();
		
		try 
		{
			//	Get account Id form session.
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accId = sessionContext.getId();
			int tsmId = Integer.parseInt(String.valueOf(accId));
			
			ConsumptionPostingDetailReportBeans revLogReportBean = (ConsumptionPostingDetailReportBeans) form;
			String levelId = request.getParameter("levelId");
			logger.info("Fetch account name of role :" + levelId);
			int roleId = Integer.parseInt(levelId);
			ConsumptionPostingDetailReportService revLogReportService = new ConsumptionPostingDetailReportServiceImpl();
			
			List accountList = revLogReportService.getRevLogTsmAccountDetails(roleId);
			revLogReportBean.setTsmList(accountList);
			request.setAttribute("tsmList",accountList);
			logger.info("Account list size : " + accountList.size());
			
						
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = accountList.iterator();
			ConsumptionPostingDetailReportBeans statusReportBean = null;
			while (iter.hasNext()) 
			{
				statusReportBean = (ConsumptionPostingDetailReportBeans) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", statusReportBean.getAccountName());
				logger.info("statusReportBean.getAccountName()  ::  "+statusReportBean.getAccountName());
				optionElement.addAttribute("value", statusReportBean.getAccountId());
				logger.info("statusReportBean.getAccountId()  ::  "+statusReportBean.getAccountId());
			}

			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();

			// End for ajax
		
		} catch (Exception ex) 
		{
			ex.printStackTrace();

			return null;

		}

		return null;
	}


	public ActionForward initDistReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method initStatusAccount().");
				ActionErrors errors = new ActionErrors();
				
				try 
				{
					//	Get account Id form session.
					HttpSession session = request.getSession();
					UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					Long accId = sessionContext.getId();
					int distId = Integer.parseInt(String.valueOf(accId));
					
					ConsumptionPostingDetailReportBeans revLogReportBean = (ConsumptionPostingDetailReportBeans) form;
					String levelId = request.getParameter("parentId");
					int circleId = Integer.parseInt(request.getParameter("circleId"));
					
					logger.info("Fetch account name of role :" + levelId);
					int roleId = Integer.parseInt(levelId);
					ConsumptionPostingDetailReportService revLogReportService = new ConsumptionPostingDetailReportServiceImpl();
					
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
						ConsumptionPostingDetailReportBeans statusReportBean = (ConsumptionPostingDetailReportBeans) iter.next();
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

					return null;

				}
				
				return null;
			}
	

}
