package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.List;
import com.ibm.virtualization.recharge.common.ResourceReader;
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
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.AccountDetailReportBean;
import com.ibm.dp.common.Constants; 
import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dp.service.AccountDetailReportService;
import com.ibm.dp.service.impl.AccountDetailReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class AccountDetailReportAction extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(AccountDetailReportAction.class.getName());
	String multiselectusers;
	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initAccountDetailReport() method..........!!!");
		HttpSession session = request.getSession();
		multiselectusers=ResourceReader.getCoreResourceBundleValue("Account.Detail.Report.MultiselectUsers");
		logger.info("MultiSelect Users  list  :"+multiselectusers);
		AccountDetailReportBean reportBean = (AccountDetailReportBean) form;
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		String accountID = String.valueOf(sessionContext.getId());
		reportBean.setCircleId(circleIdSr);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		logger.info("in initReport function Login role === "+loginRole +" and login Id == "+accountID);
		//6 for dist
		//5 tsm  
		try {
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			//AccountDetailReportService reportService =new AccountDetailReportServiceImpl();
			GenericReportsService genericReportsService = GenericReportsServiceImpl.getInstance();
			List<GenericOptionDTO> accountTypeList = genericReportsService.getAccountTypeList(2, sessionContext.getGroupId());
			logger.info("In initReport() method.   circle == "+circleIdSr);
			List<SelectionCollection> listAllCircles =null;
			//List<SelectionCollection> listTsm =null;
			//List<SelectionCollection> listDist =null;
			//List<SelectionCollection> listLoginId = reportService.getLoginIdList();
			//List<SelectionCollection> listAccountName = reportService.getAccountNameList();
			reportBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
			//reportBean.setTsmList(listTsm);
			//reportBean.setDistList(listDist);
			reportBean.setAccountTypeList(accountTypeList);
			//request.setAttribute("tsmList", listTsm);
			//request.setAttribute("distList", listDist);
			//request.setAttribute("loginIdList", listLoginId);
			//request.setAttribute("accountNameList", listAccountName);
			request.setAttribute("accountTypeList", accountTypeList);
			//reportBean.setLoginIdList(listLoginId);
			//reportBean.setAccountNameList(listAccountName);
			if(!reportBean.getCircleId().equals("0")){
				reportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				reportBean.setCircleId(circleIdSr);
				if(loginRole ==6){
					//reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					//reportBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}else if(loginRole ==5){
					//reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					//reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					//listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					//reportBean.setDistList(listDist);
					//request.setAttribute("distList", listDist);
					
				}else if(loginRole ==4 || loginRole ==3 || loginRole ==2){
					//reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					//reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					//listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					//reportBean.setTsmList(listTsm);
					//request.setAttribute("tsmList", listTsm);
				}
				
				
			}else{
				reportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				//reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				//reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
				listAllCircles = utilityAjaxService.getAllCircles();
				reportBean.setCircleList(listAllCircles);
				request.setAttribute("circleList", listAllCircles);
				
				
			}
			//revLogReportBean.setArrCGList(cgList);
			String multiselecturserlistarr[]=multiselectusers.split(",");
			for(int i=0;i<multiselecturserlistarr.length;i++)
			{
				System.out.println("multiselecturserlistarr[i] :"+multiselecturserlistarr[i]);
				System.out.println("login role :"+loginRole);
			if((loginRole)== Integer.parseInt(multiselecturserlistarr[i]))
			{
				reportBean.setAccountTypeMultiSelect("YES");
				System.out.println("MultiSelect set to yes ::::;");
				break;
			}
			}
			reportBean.setAccountLevel(loginRole);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			//return mapping.findForward(INIT_SUCCESS);
		}
			
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		String reportName=request.getParameter("reportName");
		logger.info("report name :"+reportName);
		ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of AccountDetailReportAction...................!!!");	
		HttpSession session = request.getSession();
		session.setAttribute("ReportAccountDetails", "inprogress"); 
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		String accountID = String.valueOf(sessionContext.getId());
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	//Neetika
		try {
			
			AccountDetailReportBean reportBean = (AccountDetailReportBean) form;
			//==
			logger.info(" cirlce id :"+reportBean.getCircleId());
			logger.info(" cirlce list :"+reportBean.getCircleList());
			//===
			logger.info("accountTypeMultiSelectIds :"+reportBean.getAccountTypeMultiSelectIds());
			if(reportName!=null) reportBean.setReportName(reportName);
			//String fromDate = reportBean.getFromDate();
			//String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			//String tsmIds = reportBean.getHiddenTsmSelecIds();
			//String distIds = reportBean.getHiddenDistSelecIds();
			String searchOption = reportBean.getSearchOption();
			String status = reportBean.getStatus();
			//String loginId = reportBean.getLoginId();
			//String accountName = reportBean.getAccountName();
			String accountType = reportBean.getAccountType();
			logger.info("accountType :"+accountType);
			String searchCriteria = reportBean.getSearchCriteria();
			String showCircle =reportBean.getShowCircle();
			//String showTsm =reportBean.getShowTSM();
			//String showDist =reportBean.getShowDist();
			if(circleIds.equalsIgnoreCase("")){
			
				//circleIds = String.valueOf(sessionContext.getCircleId()); //Neetika 3 April SR4806635
				
				List circles = utilityAjaxService.getCircles(Integer.parseInt(accountID));
				for(int i=1;i<=circles.size();i++)
				{
					if(i==circles.size())
					{
						circleIds=circleIds+""+circles.get(i-1)+"";	
					//logger.info("circles :::"+circleIds);
					}
					else
						circleIds=circleIds+""+circles.get(i-1)+",";
					
				}
				logger.info( String.valueOf(sessionContext.getCircleId()) + "Circles for account details report "+circleIds);
			}		
			String accountTypeMultiSelectIds=reportBean.getAccountTypeMultiSelectIds();
			/*if(distIds.equalsIgnoreCase("")){
			distIds = Long.toString(sessionContext.getId());
			}*/
			AccountDetailReportService reportService =new AccountDetailReportServiceImpl();
			int parentId = reportService.getParentId(accountID);
			AccountDetailReportDto reportDto =new AccountDetailReportDto();
			reportDto.setStatus(status);
			reportDto.setParentLoginId(String.valueOf(parentId));
			reportDto.setAccountLevel(loginRole);
			//reportDto.setFromDate(fromDate);
			//reportDto.setToDate(toDate);
			reportDto.setHiddenCircleSelecIds(circleIds);
			//reportDto.setHiddenTsmSelecIds(tsmIds);
			//reportDto.setHiddenDistSelecIds(distIds);
			reportDto.setAccountType(accountType);
			//reportDto.setLoginId(loginId);
			//reportDto.setAccountName(accountName);
			reportDto.setSearchOption(searchOption);
			reportDto.setSearchCriteria(searchCriteria);
			reportDto.setShowCircle(showCircle);
			reportDto.setAccountId(sessionContext.getId());
			reportDto.setAccountTypeMultiSelectIds(accountTypeMultiSelectIds);
			//reportDto.setShowTSM(showTsm);
			//reportDto.setShowDist(showDist);
	
			List<AccountDetailReportDto> reportList =reportService.getAccountDetailExcel(reportDto,reportName);
			reportBean.setReportList(reportList);
			logger.info("Report list size == "+reportList.size());
			request.setAttribute("reportList",reportList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			session.setAttribute("ReportAccountDetails", ""); 
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("ReportAccountDetails");
			
			String result = "";
			if(status != null)
				result = status;
			//logger.info("result:" + result);

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
			//logger.info("out now getReportDownloadStatus");

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	

}
