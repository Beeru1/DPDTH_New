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

import com.ibm.dp.beans.ActivationDetailReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.service.ActivationDetailReportService;
import com.ibm.dp.service.impl.ActivationDetailServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;
/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class ActivationDetailReportAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";


	private Logger logger = Logger.getLogger(ActivationDetailReportAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		logger.info("In init method.");

		ActivationDetailReportFormBean activationDetailReportFormBean = (ActivationDetailReportFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Integer groupId = sessionContext.getGroupId();
		activationDetailReportFormBean.setGroupId(groupId.toString());		
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		String accountID = String.valueOf(sessionContext.getId());
		activationDetailReportFormBean.setCircleid(-1);
		activationDetailReportFormBean.setProductId("");
		activationDetailReportFormBean.setTsmId("");
		ActionErrors errors = new ActionErrors();
		activationDetailReportFormBean.setFromDate("");
		activationDetailReportFormBean.setToDate("");
		activationDetailReportFormBean.setTsmList(null);
		activationDetailReportFormBean.setDistList(null);
		try {
			List<SelectionCollection> ciList = new ArrayList<SelectionCollection>();	
			List<SelectionCollection> tsmList= new ArrayList<SelectionCollection>();	
			List<SelectionCollection> distList= new ArrayList<SelectionCollection>();					
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
			productList = utilityAjaxService.getProductCategoryLst();
			activationDetailReportFormBean.setProductList(productList);
			if(Integer.parseInt(circleIdSr)!=0){				
				activationDetailReportFormBean.setCircleid(Integer.parseInt(circleIdSr));
				if(groupId ==7){					
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					activationDetailReportFormBean.setArrCIList(ciList);
					
					tsmList = utilityAjaxService.getParent(accountID);
					activationDetailReportFormBean.setTsmList(tsmList);
					activationDetailReportFormBean.setTsmId(tsmList.get(0).getStrValue());
					SelectionCollection sc1 = new SelectionCollection();
					sc1.setStrValue("-1");
					sc1.setStrText("--All--");	
					distList.add(sc1);
					sc1 = new SelectionCollection();
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					distList.add(sc1);
					activationDetailReportFormBean.setDistId(sc1.getStrValue());
					activationDetailReportFormBean.setDistList(distList);
					request.setAttribute("distList", distList);					
				}
				else if(groupId ==6){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					activationDetailReportFormBean.setArrCIList(ciList);
					
					SelectionCollection sc1 = new SelectionCollection();
					sc1.setStrValue("-1");
					sc1.setStrText("--All--");	
					tsmList.add(sc1);
					sc1 = new SelectionCollection();
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					tsmList.add(sc1);
					activationDetailReportFormBean.setTsmId(sc1.getStrValue());
					activationDetailReportFormBean.setTsmList(tsmList);
					
					distList= utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					activationDetailReportFormBean.setDistList(distList);
					request.setAttribute("distList", distList);													
				}else if(groupId ==3 || groupId ==4 ||groupId ==5){					
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					activationDetailReportFormBean.setArrCIList(ciList);					
					tsmList = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					activationDetailReportFormBean.setTsmList(tsmList);
					request.setAttribute("tsmList", tsmList);
				}else if(groupId==2){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					activationDetailReportFormBean.setArrCIList(ciList);
				}
				
				
			}else{				
				activationDetailReportFormBean.setCircleid(-2);
				ciList = utilityAjaxService.getAllCircles();
				activationDetailReportFormBean.setArrCIList(ciList);
				request.setAttribute("circleList", ciList);
			}
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
		}


		return mapping.findForward(INIT_SUCCESS);
	}


	public ActionForward getActionDetailReportDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		ActionErrors errors = new ActionErrors();
		logger.info("In getSerializedStockDataExcel() ");
		try {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);		

		ActivationDetailReportFormBean activationDetailReportFormBean = (ActivationDetailReportFormBean) form;
		
		String tsmIds = activationDetailReportFormBean.getHiddenTsmSelecIds();
		String distIds = activationDetailReportFormBean.getHiddenDistSelecIds();
		String circleIds = activationDetailReportFormBean.getHiddenCircleSelecIds();
		String productType=activationDetailReportFormBean.getHiddenProductTypeSelec();
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		
		if(circleIds.equalsIgnoreCase("")){
			circleIds = String.valueOf(sessionContext.getCircleId());
		}			
		if(distIds.equalsIgnoreCase("")){
		distIds = Long.toString(sessionContext.getId());
		}
		
		ActivationDetailReportDTO activationDetailReportDTO = new ActivationDetailReportDTO();
		activationDetailReportDTO.setTsmIds(tsmIds);
		activationDetailReportDTO.setDistIds(distIds);
		activationDetailReportDTO.setCircleIds(circleIds);
		activationDetailReportDTO.setProductType(productType);
		activationDetailReportDTO.setFromDate(fromDate);
		activationDetailReportDTO.setTodate(toDate);
		
		ActivationDetailReportService activationDetailReportService = ActivationDetailServiceImpl.getInstance();
		List<ActivationDetailReportDTO> reportStockDataList = activationDetailReportService.getActivationDetailReport(activationDetailReportDTO);	

		activationDetailReportFormBean.setReportStockDataList(reportStockDataList);
		request.setAttribute("reportStockDataList",reportStockDataList);
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


