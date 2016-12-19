package com.ibm.dp.actions;

import java.io.PrintWriter;
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
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.AccountManagementActivityReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.AccountManagementActivityReportDto;
import com.ibm.dp.service.AccountManagementActivityReportService;
import com.ibm.dp.service.impl.AccountManagementActivityReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class AccountManagementActivityReportAction extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(AccountManagementActivityReportAction.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("In initManagementActivityReport() method..........!!!");
		HttpSession session = request.getSession();
		
		AccountManagementActivityReportBean reportBean = (AccountManagementActivityReportBean) form;
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		String accountID = String.valueOf(sessionContext.getId());
		reportBean.setCircleId(circleIdSr);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		logger.info("in initReport function Login role === "+loginRole +" and login Id == "+accountID);
		try {
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
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
		ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of AccountManagementActivityReportAction...................!!!");	
		HttpSession session = request.getSession();
		session.setAttribute("AccountManagementActivity", "inprogress"); //Added by Neetika BFR 16 Release 3 on 16 Aug
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		String accountID = String.valueOf(sessionContext.getId());
		
		try {
			AccountManagementActivityReportBean reportBean = (AccountManagementActivityReportBean) form;
			System.out.println("Search optio :"+reportBean.getSearchOption());
			String fromDate = reportBean.getStartDt();
			String toDate = reportBean.getEndDt();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			String accountTypes=reportBean.getHiddenAccountType();
			String action=reportBean.getHiddenAction();
			String showCircle =reportBean.getShowCircle();
			String searchOption = reportBean.getSearchOption();
			String searchCriteria = reportBean.getSearchCriteria();
			logger.info("Start date :"+fromDate.toString());
			logger.info("end date :"+toDate);
			System.out.println("1111111111111");
			if(fromDate==null)System.out.println("start date is null ");
			if(toDate==null)System.out.println("to date is null ");
			if(fromDate.trim().equals("") ) System.out.println("start date is blank not blank space  ");
			if(toDate=="") System.out.println("to date is blank no blank space  ");
			if(fromDate==" ") System.out.println("start date is blank having one blank space  ");
			if(fromDate==" ") System.out.println("to date is blank having one blank space  ");
			
			logger.info("circleIds :"+circleIds);
			
			logger.info("accountTypes :"+new StringBuffer(accountTypes).deleteCharAt(0));
			logger.info("action :"+action);
			logger.info("action :"+new StringBuffer(action).deleteCharAt(0));
			
			logger.info("searchOption :"+searchOption);
			
			logger.info("searchCriteria :"+searchCriteria);
			logger.info("show cirlcels :"+showCircle);
			if(circleIds.equalsIgnoreCase("")){
				circleIds = String.valueOf(sessionContext.getCircleId());
			}			
			AccountManagementActivityReportService reportService =new AccountManagementActivityReportServiceImpl();
			int parentId = reportService.getParentId(accountID);
			AccountManagementActivityReportDto reportDto =new AccountManagementActivityReportDto();
			reportDto.setParentLoginId(String.valueOf(parentId));
			reportDto.setAccountLevel(loginRole);
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setAccountType(new StringBuffer(accountTypes).deleteCharAt(0).toString());
			reportDto.setAction(new StringBuffer(action).deleteCharAt(0).toString());
			reportDto.setSearchOption(searchOption);
			reportDto.setSearchCriteria(searchCriteria);
			reportDto.setShowCircle(showCircle);
			reportDto.setAccountId(sessionContext.getId());
			List<AccountManagementActivityReportDto> reportList =reportService.getAccountMngtActivityExcel(reportDto);
			reportBean.setReportList(reportList);
			logger.info("Report list size == "+reportList.size());
			request.setAttribute("reportList",reportList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
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
			
			String status = (String) session.getAttribute("AccountManagementActivity");
			//System.out.println("status value in NonSerializedConReport is "+status);
			String result = "";
			if(status != null)
				result = status;
			
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
		

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}

}
