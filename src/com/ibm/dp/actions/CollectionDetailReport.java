package com.ibm.dp.actions;

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

import com.ibm.dp.beans.CollectionReportBean;
import com.ibm.dp.beans.PODetailReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.dp.service.CollectionReportService;
import com.ibm.dp.service.DPInterSSDStockTransferReportService;
import com.ibm.dp.service.PODetailReportService;
import com.ibm.dp.service.impl.CollectionReportServiceImpl;
import com.ibm.dp.service.impl.DPInterSSDStockTransferReporServiceImpl;
import com.ibm.dp.service.impl.PODetailReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class CollectionDetailReport extends DispatchAction {
	
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	private Logger logger = Logger.getLogger(CollectionDetailReport.class.getName());

	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		// Get account Id form session.
		logger.info("In initReport() method.");
		HttpSession session = request.getSession();
		
		CollectionReportBean reportBean = (CollectionReportBean) form;
		
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
			CollectionReportService reportService =new CollectionReportServiceImpl();
			
			logger.info("In initReport() method.   circle == "+circleIdSr);
			List<SelectionCollection> listAllCircles =new ArrayList<SelectionCollection>();
			List<SelectionCollection> listTsm =new ArrayList<SelectionCollection>();
			List<SelectionCollection> listDist =new ArrayList<SelectionCollection>();
			List<DpProductCategoryDto> listProduct = reportService.getProductList();
			//List<SelectionCollection> listPoStatus = reportService.getPoStatusList();
			reportBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
			reportBean.setTsmList(listTsm);
			reportBean.setDistList(listDist);
			//reportBean.setPoStatusList(listPoStatus);
			request.setAttribute("tsmList", listTsm);
			request.setAttribute("distList", listDist);
			request.setAttribute("productList", listProduct);
			
			reportBean.setProductList(listProduct);
			
			List<CollectionReportDTO> collectionType=reportService.getCollectionTypeMaster();
			reportBean.setCollectionType(collectionType);
			//request.setAttribute("poStatusList", listPoStatus);
			if(!reportBean.getCircleId().equals("0")){
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

					
					
					listTsm = utilityAjaxService.getParent(accountID);
					reportBean.setTsmList(listTsm);
					reportBean.setTsmId(listTsm.get(0).getStrValue());
					SelectionCollection sc1 = new SelectionCollection();
					/*sc1.setStrValue("-1");
					sc1.setStrText("--All--");	
					listDist.add(sc1);
					sc1 = new SelectionCollection();*/
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					listDist.add(sc1);
					
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
					SelectionCollection sc1 = new SelectionCollection();
					/*sc1.setStrValue("-1");
					sc1.setStrText("--All--");	
					listTsm.add(sc1);
					sc1 = new SelectionCollection();*/
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					listTsm.add(sc1);
					reportBean.setTsmId(sc1.getStrValue());
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
				
				
			}else{
				reportBean.setCircleId("-2");
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
	
	public ActionForward exporttoExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of CollectionDetailReport");
		try {
			CollectionReportBean reportBean = (CollectionReportBean) form;
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			String circleIds = reportBean.getHiddenCircleSelecIds();
			String tsmIds = reportBean.getHiddenTsmSelecIds();
			String distIds = reportBean.getHiddenDistSelecIds();
			String status = reportBean.getHiddenStatusSelec();
			String stbType=reportBean.getHiddenSTBTypeSelec();
			String productCatgss = reportBean.getHiddenProductSeletIds();
			
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
			if(circleIds.equalsIgnoreCase("")){
				circleIds = String.valueOf(sessionContext.getCircleId());
			}			
			if(distIds.equalsIgnoreCase("")){
			distIds = Long.toString(sessionContext.getId());
			}
			
			//List<DpProductCategoryDto> dcProductCategListDTO=reportBean.getDcProductCategListDTO();
			String collectionType=reportBean.getHiddenCollectionTypeSelec();
			//String searchOption = reportBean.getSearchOption();
			//String searchCriteria = reportBean.getSearchCriteria();
			logger.info("From Date  == "+fromDate+" and to Date == "+toDate+" and circleIds==="+circleIds);
			logger.info("tsmIds  == "+tsmIds+" and distIds == "+distIds);
			CollectionReportDTO reportDto =new CollectionReportDTO();
			reportDto.setFromDate(fromDate);
			reportDto.setToDate(toDate);
			reportDto.setHiddenCircleSelecIds(circleIds);
			reportDto.setHiddenTsmSelecIds(tsmIds);
			reportDto.setHiddenDistSelecIds(distIds);
			//reportDto.setSearchOption(searchOption);
			//reportDto.setSearchCriteria(searchCriteria);
			reportDto.setHiddenStatusSelec(status);
			reportDto.setHiddenSTBTypeSelec(stbType);
			reportDto.setHiddenCollectionTypeSelec(collectionType);
			reportDto.setHiddenProductSeletIds(productCatgss);
			
			CollectionReportService reportService =new CollectionReportServiceImpl();
			
			List<CollectionReportDTO> reportList =reportService.getCollectionDetailExcel(reportDto);
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
