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

import com.ibm.dp.beans.StockRecoSummReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.dp.service.StockRecoSummReportService;
import com.ibm.dp.service.impl.StockRecoSummReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class StockRecoSummReportAction extends DispatchAction {

	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(StockRecoSummReportAction.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initReport() method.");
		HttpSession session = request.getSession();
		
		StockRecoSummReportBean reportBean = (StockRecoSummReportBean) form;
		
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
			StockRecoSummReportService reportService =new StockRecoSummReportServiceImpl();
			
			logger.info("In initReport() method.   circle == "+circleIdSr);
			List<SelectionCollection> listAllCircles =null;
			List<SelectionCollection> listTsm =null;
			List<SelectionCollection> listDist =null;
			List<DpProductCategoryDto> listProduct = reportService.getProductList();
			reportBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
			reportBean.setTsmList(listTsm);
			reportBean.setDistList(listDist);
			reportBean.setProductList(listProduct);
			request.setAttribute("tsmList", listTsm);
			request.setAttribute("distList", listDist);
			request.setAttribute("productList", listProduct);
			if(!reportBean.getCircleId().equals("0")){
				reportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				reportBean.setCircleId(circleIdSr);
				if(loginRole ==6){
					reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}else if(loginRole ==5){
					reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					reportBean.setDistList(listDist);
					request.setAttribute("distList", listDist);
					
				}else if(loginRole ==4 || loginRole ==3 || loginRole ==2){
					reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					reportBean.setTsmList(listTsm);
					request.setAttribute("tsmList", listTsm);
				}
				
				
			}else{
				reportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
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
	{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of StockRecoSummReportAction");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountID = String.valueOf(sessionContext.getId());
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		
		try {
			StockRecoSummReportBean reportBean = (StockRecoSummReportBean) form;
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			String tsmIds = reportBean.getHiddenTsmSelecIds();
			String distIds = reportBean.getHiddenDistSelecIds();
			logger.info("OUT IF COND DIST ID=== "+reportBean.getHiddenDistSelecIds());
			if(loginRole ==6){
				distIds = accountID;
			}
			if(loginRole ==6 || loginRole ==5 ||loginRole ==4 || loginRole ==3 || loginRole ==2){
				circleIds = circleIdSr;
			}
			String productCatgss = reportBean.getHiddenProductSeletIds();
			String showCircle =reportBean.getShowCircle();
			String showTsm =reportBean.getShowTSM();
			String showDist =reportBean.getShowDist();
			logger.info("From Date  == "+fromDate+" and to Date == "+toDate+" and circleIds==="+circleIds);
			logger.info("tsmIds  == "+tsmIds+" and distIds == "+distIds+" and prodCategory==="+productCatgss);
			StockRecoSummReportDto reportDto =new StockRecoSummReportDto();
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setHiddenTsmSelecIds(tsmIds);
			reportDto.setHiddenDistSelecIds(distIds);
			reportDto.setHiddenProductSeletIds(productCatgss);
			reportDto.setShowCircle(showCircle);
			reportDto.setShowTSM(showTsm);
			reportDto.setShowDist(showDist);
			
			
			StockRecoSummReportService reportService =new StockRecoSummReportServiceImpl();
			List<StockRecoSummReportDto> reportList =reportService.getRecoSummaryExcel(reportDto);
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
	
	
}
