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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.RecoPeriodConfFormBean;
import com.ibm.dp.beans.SerializedStockReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.SerializedStockReportDTO;
import com.ibm.dp.service.DPInterSSDStockTransferReportService;
import com.ibm.dp.service.SerializedStockReportService;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.DPInterSSDStockTransferReporServiceImpl;
import com.ibm.dp.service.impl.SerializedStockServiceImpl;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class SerializedStockReportAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";


	private Logger logger = Logger.getLogger(SerializedStockReportAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		logger.info("In init method.");

		SerializedStockReportFormBean serializedStockBean = (SerializedStockReportFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Integer groupId = sessionContext.getGroupId();
		serializedStockBean.setGroupId(groupId.toString());		
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		String accountID = String.valueOf(sessionContext.getId());
		serializedStockBean.setCircleid(Integer.parseInt(circleIdSr));
		ActionErrors errors = new ActionErrors();
		serializedStockBean.setTsmList(null);
		serializedStockBean.setDistList(null);
		serializedStockBean.setFseList(null);
		serializedStockBean.setRetList(null);
		try {
			List<SelectionCollection> ciList = new ArrayList<SelectionCollection>();	
			List<SelectionCollection> tsmList= new ArrayList<SelectionCollection>();	
			List<SelectionCollection> distList= new ArrayList<SelectionCollection>();	
			List<SelectionCollection> fseList= new ArrayList<SelectionCollection>();				
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();	
			productList = utilityAjaxService.getProductCategoryLst();
			serializedStockBean.setProductList(productList);
			if(serializedStockBean.getCircleid()!=0){
				serializedStockBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				serializedStockBean.setCircleid(Integer.parseInt(circleIdSr));
				if(groupId ==7){					
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					serializedStockBean.setArrCIList(ciList);
					
					tsmList = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					serializedStockBean.setTsmList(tsmList);
					SelectionCollection sc1 = new SelectionCollection();
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					distList.add(sc1);
					serializedStockBean.setDistList(distList);
					request.setAttribute("distList", distList);
					fseList = utilityAjaxService.getAllAccountsUnderMultipleAccounts(Long.toString(sessionContext.getId()));
					serializedStockBean.setFseList(fseList);					
					serializedStockBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					serializedStockBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}
				else if(groupId ==6){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					serializedStockBean.setArrCIList(ciList);
					
					tsmList = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					serializedStockBean.setTsmList(tsmList);
					
					distList= utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					serializedStockBean.setDistList(distList);
					request.setAttribute("distList", distList);
					serializedStockBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					serializedStockBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}else if(groupId ==5){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					ciList.add(sc);
					serializedStockBean.setArrCIList(ciList);
					
					tsmList = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					serializedStockBean.setTsmList(tsmList);
					
					serializedStockBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					serializedStockBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					distList = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					serializedStockBean.setDistList(distList);
					request.setAttribute("distList", distList);
					
				}else if(groupId ==4 || groupId ==3){
					serializedStockBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					serializedStockBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					serializedStockBean.setArrCIList(ciList);
					serializedStockBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					tsmList = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					serializedStockBean.setTsmList(tsmList);
					request.setAttribute("tsmList", tsmList);
				}else if(groupId==2){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					ciList.add(sc);
					serializedStockBean.setArrCIList(ciList);
				}
				
				
			}else{
				serializedStockBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				serializedStockBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				serializedStockBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
				ciList = utilityAjaxService.getAllCircles();
				serializedStockBean.setArrCIList(ciList);
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
	public ActionForward initProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
		HttpSession session = request.getSession();
		RecoPeriodConfFormBean recoPeriodBean = (RecoPeriodConfFormBean) form;
		String circleId = request.getParameter("circleID");
		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		List<DpProductCategoryDto> productList = utilityAjaxService.getProductCategoryLst();
			recoPeriodBean.setProductList(productList);
		session.setAttribute("productList", productList);
		ajaxCall(request,response,productList);
		return null;
			}
	private void ajaxCall(HttpServletRequest request, HttpServletResponse response, List productList)throws Exception{

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		ProductMasterDto productMasterDto = null;

		for (int counter = 0; counter < productList.size(); counter++) {

			optionElement = root.addElement("option");
			productMasterDto = (ProductMasterDto)productList.get(counter);
			if (productMasterDto != null) {
				optionElement.addAttribute("value", String.valueOf(productMasterDto.getProductId()));		
				optionElement.addAttribute("text", String.valueOf(productMasterDto.getProductName()));	
			} else {
				optionElement.addAttribute("value", "None");		
				optionElement.addAttribute("text", "None");	
			}				
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();		
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush(); 
	}

	public ActionForward getSerializedStockDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");

		SerializedStockReportFormBean serializedStockReportFormBean = (SerializedStockReportFormBean) form;
		String circleId = request.getParameter("circleid");
		String tsmId = request.getParameter("tsmId");
		String distId = request.getParameter("distId");
		String fseId = request.getParameter("fseId");
		String retId = request.getParameter("retId");
		String productId = request.getParameter("productId");
		String date = request.getParameter("date");

		SerializedStockReportService serializedStockReportService = SerializedStockServiceImpl.getInstance();
		List<SerializedStockReportDTO> reportStockDataList = serializedStockReportService.getSerializedStockReport(circleId, tsmId, distId, fseId, retId, productId, date);	

		serializedStockReportFormBean.setReportStockDataList(reportStockDataList);
		request.setAttribute("reportStockDataList",reportStockDataList);

		return mapping.findForward(SUCCESS_EXCEL);
			}
	
}


