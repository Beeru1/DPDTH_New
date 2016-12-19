package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.ibm.dp.beans.NonSerializedConsumptionReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.service.NonSerializedConsumptionReportService;
import com.ibm.dp.service.impl.NonSerializedConsumptionReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class NonSerializedConsumptionReport extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(NonSerializedConsumptionReport.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initReport() method.");
		HttpSession session = request.getSession();
		
		NonSerializedConsumptionReportBean reportBean = (NonSerializedConsumptionReportBean) form;
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		reportBean.setCircleId(circleIdSr);
		
		String accountID = String.valueOf(sessionContext.getId());
		
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		logger.info("in initReport function Login role === "+loginRole +" and login Id == "+accountID);
		//6 for dist
		//5 tsm  
		try {
			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			NonSerializedConsumptionReportService reportService =new NonSerializedConsumptionReportServiceImpl();
			
			logger.info("In initReport() method.   circle == "+circleIdSr);
			List<SelectionCollection> listAllCircles =new ArrayList<SelectionCollection>();
			List<SelectionCollection> listTsm =new ArrayList<SelectionCollection>();
			List<SelectionCollection> listDist =new ArrayList<SelectionCollection>();
			//List<SelectionCollection> listPoStatus = reportService.getPoStatusList();
			reportBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
			reportBean.setTsmList(listTsm);
			reportBean.setDistList(listDist);
			//reportBean.setPoStatusList(listPoStatus);
			request.setAttribute("tsmList", listTsm);
			request.setAttribute("distList", listDist);
			
			List<DpProductCategoryDto> dcProductCategListDTO=reportService.getProductCategory();
			reportBean.setDcProductCategListDTO(dcProductCategListDTO);
			
			//request.setAttribute("poStatusList", listPoStatus);
		/*	if(!reportBean.getCircleId().equals("0")){
				reportBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				reportBean.setCircleId(circleIdSr);
				if(loginRole ==6){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					listAllCircles.add(sc);
					reportBean.setCircleList(listAllCircles);
					
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					reportBean.setTsmList(listTsm);
					
					listDist= utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					reportBean.setDistList(listDist);
					request.setAttribute("distList", listDist);
					reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}else if(loginRole ==5){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					listAllCircles.add(sc);
					reportBean.setCircleList(listAllCircles);
					
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					reportBean.setTsmList(listTsm);
					
					reportBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					reportBean.setDistList(listDist);
					request.setAttribute("distList", listDist);
					
				}else if(loginRole ==4 || loginRole ==3 || loginRole ==2){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					listAllCircles.add(sc);
					reportBean.setCircleList(listAllCircles);
					
					reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					reportBean.setTsmList(listTsm);
					request.setAttribute("tsmList", listTsm);
				}
				
				
			}else{*/
				reportBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				reportBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				reportBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
				listAllCircles = utilityAjaxService.getAllCircles();
				reportBean.setCircleList(listAllCircles);
				request.setAttribute("circleList", listAllCircles);
				
				
		//	}
			
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
	
	public ActionForward exporttoExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of NonSerializedConsumptionReport");
		HttpSession session=request.getSession();
		try {
			//System.out.println("status value in recoDetailReport inprogress");
			
			session.setAttribute("NonSerializedConReport", "inprogress"); //Added by Neetika BFR 16 Release 3 on 16 Aug
		
			NonSerializedConsumptionReportBean reportBean = (NonSerializedConsumptionReportBean) form;
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			String tsmIds = reportBean.getHiddenTsmSelecIds();
			String distIds = reportBean.getHiddenDistSelecIds();
			
			String productType=reportBean.getHiddenSTBTypeSelec(); 
			List<DpProductCategoryDto> dcProductCategListDTO=reportBean.getDcProductCategListDTO();
			//String searchOption = reportBean.getSearchOption();
			//String searchCriteria = reportBean.getSearchCriteria();
			String showCircle =reportBean.getShowCircle();
			String showTsm =reportBean.getShowTSM();
			String showDist =reportBean.getShowDist();
			logger.info("From Date  == "+fromDate+" and to Date == "+toDate+" and circleIds==="+circleIds);
			logger.info("tsmIds  == "+tsmIds+" and distIds == "+distIds);
			NonSerializedConsumptionReportDTO reportDto =new NonSerializedConsumptionReportDTO();
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setHiddenTsmSelecIds(tsmIds);
			reportDto.setHiddenDistSelecIds(distIds);
			//reportDto.setSearchOption(searchOption);
			//reportDto.setSearchCriteria(searchCriteria);
			reportDto.setShowCircle(showCircle);
			reportDto.setShowTSM(showTsm);
			reportDto.setShowDist(showDist);
			reportDto.setHiddenCollectionTypeSelec(productType);
			reportDto.setDcProductCategListDTO(dcProductCategListDTO);
			
			NonSerializedConsumptionReportService reportService =new NonSerializedConsumptionReportServiceImpl();
			
			List<NonSerializedConsumptionReportDTO> reportList =reportService.getNonSerializedConsumptionExcel(reportDto);
			reportBean.setReportList(reportList);
			logger.info("Report list size == "+reportList.size());
			request.setAttribute("reportList",reportList);
		} catch (RuntimeException e) {
			session.setAttribute("NonSerializedConReport", ""); //Added by Neetika BFR 16 Release 3 on 16 Aug
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
			
			String status = (String) session.getAttribute("NonSerializedConReport");
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

