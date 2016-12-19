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
import com.ibm.dp.beans.STBWiseSerializedStockReportFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.STBWiseSerializedStockReportDTO;
import com.ibm.dp.service.STBWiseSerializedStockReportService;
import com.ibm.dp.service.impl.STBWiseSerializedStockReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class STBWiseSerializedStockReportAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";


	private Logger logger = Logger.getLogger(STBWiseSerializedStockReportAction.class.getName());

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSTBStatus(ActionMapping mapping, ActionForm form,
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
	/**
	 * 
	 */
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
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSerializedStockDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");
		
		STBWiseSerializedStockReportFormBean stbWiseSerializedStockReportFormBean = (STBWiseSerializedStockReportFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountLevel = sessionContext.getAccountLevel();
		
		String stbStatus = stbWiseSerializedStockReportFormBean.getHiddenStbStatus();
		
		String tsmIds = stbWiseSerializedStockReportFormBean.getHiddenTsmSelecIds();
		String distIds = stbWiseSerializedStockReportFormBean.getHiddenDistSelecIds();
		String circleIds = stbWiseSerializedStockReportFormBean.getHiddenCircleSelecIds();
		String fseIds = stbWiseSerializedStockReportFormBean.getHiddenFseSelecIds();
		String retIds = stbWiseSerializedStockReportFormBean.getHiddenRetSeletIds();
		String stbStatuses = stbWiseSerializedStockReportFormBean.getHiddenStbSelecIds();
		String productIds	 = stbWiseSerializedStockReportFormBean.getHiddenProductSeletIds();


		STBWiseSerializedStockReportService stbWiseSerializedStockReportService = STBWiseSerializedStockReportServiceImpl.getInstance();
		STBWiseSerializedStockReportDTO stDTO = new STBWiseSerializedStockReportDTO();

		stDTO.setTsmIds(tsmIds);
		stDTO.setDistIds(distIds);
		stDTO.setCircleIds(circleIds);
		stDTO.setFseID(fseIds);
		stDTO.setRetIds(retIds);
		stDTO.setStbStatuses(stbStatuses);
		stDTO.setProductId(productIds);
		stDTO.setAccount_level(accountLevel);
		stDTO.setLogin_id(sessionContext.getId()+"");
		stDTO.setCircle(sessionContext.getCircleId()+"");
		stDTO.setStbStatus(stbStatus);
		
		List<STBWiseSerializedStockReportDTO> reportStockDataList = stbWiseSerializedStockReportService.getSTBWiseSerializedStockReport(stDTO);	

		stbWiseSerializedStockReportFormBean.setReportStockDataList(reportStockDataList);
		request.setAttribute("reportStockDataList",reportStockDataList);

		return mapping.findForward(SUCCESS_EXCEL);
			}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		logger.info("In init method.");

		STBWiseSerializedStockReportFormBean stbWiseSerializedStockReportFormBean = (STBWiseSerializedStockReportFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Integer groupId = sessionContext.getGroupId();
		stbWiseSerializedStockReportFormBean.setGroupId(groupId.toString());		
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		String accountID = String.valueOf(sessionContext.getId());
		stbWiseSerializedStockReportFormBean.setCircleId(circleIdSr);
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		
		ActionErrors errors = new ActionErrors();
		stbWiseSerializedStockReportFormBean.setTsmList(null);
		stbWiseSerializedStockReportFormBean.setDistList(null);
		stbWiseSerializedStockReportFormBean.setFseList(null);
		stbWiseSerializedStockReportFormBean.setRetList(null);
		try {
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			
			logger.info("In initReport() method.   circle == "+circleIdSr);
			List<SelectionCollection> listAllCircles =null;
			List<SelectionCollection> listTsm =null;
			List<SelectionCollection> listDist =null;
			stbWiseSerializedStockReportFormBean.setCircleList(listAllCircles);
			request.setAttribute("circleList", listAllCircles);
			stbWiseSerializedStockReportFormBean.setTsmList(listTsm);
			stbWiseSerializedStockReportFormBean.setDistList(listDist);
			
			request.setAttribute("tsmList", listTsm);
			request.setAttribute("distList", listDist);
			
			
			if(!stbWiseSerializedStockReportFormBean.getCircleId().equals("0")){
				stbWiseSerializedStockReportFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				stbWiseSerializedStockReportFormBean.setCircleId(circleIdSr);
				if(loginRole ==Constants.TM){
					stbWiseSerializedStockReportFormBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					stbWiseSerializedStockReportFormBean.setShowDist(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				}else if(loginRole ==Constants.ZSM){
					stbWiseSerializedStockReportFormBean.setShowTSM(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					stbWiseSerializedStockReportFormBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
					stbWiseSerializedStockReportFormBean.setDistList(listDist);
					request.setAttribute("distList", listDist);
					
				}else if(loginRole ==Constants.ZBM || loginRole ==Constants.SALES_HEAD || loginRole ==Constants.CEO){
					stbWiseSerializedStockReportFormBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
					stbWiseSerializedStockReportFormBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
					listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
					stbWiseSerializedStockReportFormBean.setTsmList(listTsm);
					request.setAttribute("tsmList", listTsm);
				}
			}else{
				stbWiseSerializedStockReportFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				stbWiseSerializedStockReportFormBean.setShowTSM(Constants.ACCOUNT_SHOW_CIRCLE);
				stbWiseSerializedStockReportFormBean.setShowDist(Constants.ACCOUNT_SHOW_CIRCLE);
				listAllCircles = utilityAjaxService.getAllCircles();
				stbWiseSerializedStockReportFormBean.setCircleList(listAllCircles);
				request.setAttribute("circleList", listAllCircles);
			}
			
			
			List<SelectionCollection> fseList= new ArrayList<SelectionCollection>();				
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			List<SelectionCollection> stbStatusList = new ArrayList<SelectionCollection>();	
				
			productList = utilityAjaxService.getProductCategoryLst();
			stbWiseSerializedStockReportFormBean.setProductList(productList);
			STBWiseSerializedStockReportService stbService = STBWiseSerializedStockReportServiceImpl.getInstance();
			stbStatusList = stbService.getSTBStatusList();	
			stbWiseSerializedStockReportFormBean.setStbStatusList(stbStatusList);

			if(!stbWiseSerializedStockReportFormBean.getCircleId().equals("0")){
				if(groupId ==Constants.DISTIBUTOR){								
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
					
					SelectionCollection sc1 = new SelectionCollection();
					sc1.setStrValue(Long.toString(sessionContext.getId()));
					sc1.setStrText(sessionContext.getLoginName());					
					fseList = utilityAjaxService.getAllAccountsUnderMultipleAccounts(Long.toString(sessionContext.getId()));
					stbWiseSerializedStockReportFormBean.setFseList(fseList);					
					}
				else if(groupId ==Constants.TM){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				}else if(groupId ==Constants.ZSM){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				}else if(groupId ==Constants.ZBM || groupId ==Constants.SALES_HEAD){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				}else if(groupId==Constants.CEO){
					SelectionCollection sc = new SelectionCollection();
					sc.setStrText(sessionContext.getCircleName());
					sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
				}
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
}


