package com.ibm.virtualization.recharge.actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.struts.util.LabelValueBean;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;

import com.ibm.dp.beans.HierarchyTransferBean;
import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.service.StockSummaryReportService;
import com.ibm.dp.service.impl.StockSummaryReportServiceImpl;
import com.ibm.dpmisreports.common.AppContext;
import com.ibm.dpmisreports.common.SpringCacheUtility;
import com.ibm.virtualization.recharge.beans.DPCreateProductFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.DPUserBean;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class DPCreateProductAction extends DispatchAction {

	private static final Logger logger;

	static {
		logger = Logger.getLogger(DPCreateProductAction.class);
	}
	
	
	/*public ActionForward getCG(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
	}*/

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionForward forward = new ActionForward();
		DPCreateProductFormBean prodDPBean = (DPCreateProductFormBean) form;
		prodDPBean.setBusinessCategory("");
		String userId = (String) session.getAttribute("userId");
		String condition = (String) session.getAttribute("condition");
		int circleId = sessionContext.getCircleId();
		prodDPBean.setCircleid(circleId);
		DPMasterService dpms = new DPMasterServiceImpl();
		ArrayList bcaList = new ArrayList(); // for business category 
		ArrayList ciList = new ArrayList();
		ArrayList unitList = new ArrayList();
		ArrayList cgList = new ArrayList(); // for card group list
		//add by harbans on Reservation Obserbation 30th June
		ArrayList parentProductList = new ArrayList(); // for reverse populate with all commercial products.
		
		logger.info("userId----------"+userId);
		logger.info("prodDPBean.getCircleid()----------"+prodDPBean.getCircleid());
		
		try {
			bcaList = dpms.getBusinessCategory(userId);
			//log.info("***ABOVE BUSINESS CATEGORY***");
			prodDPBean.setArrBCList(bcaList);
			
			ciList = dpms.getCircleID(userId);
			
			prodDPBean.setArrCIList(ciList);
			if(prodDPBean.getCircleid() != 0){
				prodDPBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				cgList=dpms.getCardGroups(""+prodDPBean.getCircleid());
				
				
			}else{
				prodDPBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				cgList=dpms.getCardGroups("0");
				
			}
			prodDPBean.setArrCGList(cgList)	;
			unitList = dpms.getUnitList();
			
			//Added By Shilpa
	        setDefaultValue(prodDPBean, request);
			prodDPBean.setUnitList(unitList);
			Date dt=new Date();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			prodDPBean.setEffectivedate(formatter.format(dt));
						
			// Add by harbans on Reservation Obserbation 30th June
			List<DpProductTypeMasterDto> reverseProductList = dpms.getAllCommercialProducts(circleId);
			prodDPBean.setReverseProductList(reverseProductList);
			request.setAttribute("reverseProductList", reverseProductList);
			
			
			
		} catch (Exception e) {
			log
			.error("ERROR IN FETCHING  LIST [init] function");
			forward = mapping.findForward("failure");
			return forward;
		}
		saveToken(request);
		forward = mapping.findForward("success");
		return (forward);
	}

	public ActionForward getChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		HttpSession session = request.getSession();
		DPUserBean dpUserBean = (DPUserBean) session.getAttribute("USER_INFO");
		String userId = dpUserBean.getUserLoginId();
		String warehouseId = dpUserBean.getWarehouseID();
		String cond = "";
		String Id = "";
		String cond2 = "";
		String roleId = dpUserBean.getActorId();
		Id = request.getParameter("cond1");
		cond = request.getParameter("cond2");
		ActionForward forward = new ActionForward();
		DPMasterService masterService = new DPMasterServiceImpl();
		ArrayList arrGetValue = new ArrayList();
		if (cond.startsWith("#NonP") || cond.startsWith("#Pair")) {
			String avlProdQty = "0";
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.flush();
		}
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		return null;
	}

	public ActionForward Insertdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {
		
		//logger.info(" Started... in insert part");
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		DPCreateProductFormBean prodDPBean = (DPCreateProductFormBean) form;
		DPMasterService dpservice = new DPMasterServiceImpl();
		 
		 String userId = (String) session.getAttribute("userId");
		 DPMasterService dpms = new DPMasterServiceImpl();
			ArrayList bcaList = new ArrayList(); // for business category 
			ArrayList ciList = new ArrayList();
			ArrayList cgList = new ArrayList();
			String message="";
		try {
			prodDPBean.setCreatedby(""+sessionContext.getId());
			String productExistflag= "false";
			try
			{
				message = dpservice.insert(prodDPBean);
				
			}
			catch(VirtualizationServiceException ex)
			{
				if(ex.getMessage().equalsIgnoreCase("error.oracle.item"))
				{
					throw new VirtualizationServiceException("error.oracle.item");
				}
				
				System.out.println("***********Inside VirtualizationServiceException nested handling in action class VirtualizationServiceException**********");
				productExistflag = ex.getMessage();
			}
			if(message.equalsIgnoreCase("success")){
				errors.add("message.product", new ActionError(
				"message.product.insert_success"));
				 saveErrors(request, errors);
				 if(prodDPBean != null){
						prodDPBean.reset();
					}
				 
			}
			bcaList = dpms.getBusinessCategory(userId);
			//log.info("***ABOVE BUSINESS CATEGORY***");
			prodDPBean.setArrBCList(bcaList);
			ciList = dpms.getCircleID(userId);
			prodDPBean.setArrCIList(ciList);
			ArrayList unitList = new ArrayList(); 
			unitList = dpms.getUnitList();
			prodDPBean.setUnitList(unitList);
			cgList=dpms.getCardGroups(""+prodDPBean.getCircleid());
			prodDPBean.setArrCGList(cgList);
			 //Added By Shilpa
			setDefaultValue(prodDPBean, request);
			prodDPBean.setProductTypeId("0");
			prodDPBean.setProductCategory("-1");		
			if(!productExistflag.equalsIgnoreCase("false"))
			{
				errors.add("errors.product1",new ActionError(productExistflag));
				saveErrors(request, errors);
				return mapping.findForward("failure");
			}
		} catch (VirtualizationServiceException e) {
			System.out.println("***********Inside exception handling in action class VirtualizationServiceException**********");
			bcaList = dpms.getBusinessCategory(userId);
			//log.info("***ABOVE BUSINESS CATEGORY***");
			prodDPBean.setArrBCList(bcaList);
			ciList = dpms.getCircleID(userId);
			prodDPBean.setArrCIList(ciList);
			ArrayList unitList = new ArrayList(); 
			unitList = dpms.getUnitList();
			cgList=dpms.getCardGroups(""+prodDPBean.getCircleid());
			prodDPBean.setArrCGList(cgList);
			prodDPBean.setUnitList(unitList);
			Date dt=new Date();
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			prodDPBean.setEffectivedate(formatter.format(dt));
			logger.error("Exception occured while reteriving.Create Product Action"
					+ "Exception Message in action part: ", e);
			if(e.getMessage().equalsIgnoreCase("Error.Card.Group")){
				errors.add("errors.product1",new ActionError("Error.Card.Group"));
			}
			if(e.getMessage().equals("exception.insert")){
			errors.add("errors.product1",new ActionError("exception.insert"));
			}
			if(e.getMessage().equals("error.oracle.item")){
				errors.add("errors.product1",new ActionError("error.oracle.item"));
				}
			
			saveErrors(request, errors);
			return mapping.findForward("failure");
		}
		
		catch (Exception e) {
			System.out.println("***********Inside exception handling in action class Exception**********");
			
			log
			.error("ERROR IN FETCHING  LIST [init] function");
			forward = mapping.findForward("failure");
			return forward;
		}
		forward = mapping.findForward(message);
		logger.info("Executed... ");
		return forward;
	}
	
	public ActionForward onCircleChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String circleId=request.getParameter("circleId");
		System.out.println("onCircleChangeonCircleChangeonCircleChangeonCircleChangeonCircleChange--"+circleId);
		ArrayList cgList = new ArrayList(); 
		DPMasterService dpms = new DPMasterServiceImpl();
		
		cgList=dpms.getCardGroups(circleId);
		//System.out.println("---size--"+cgList.size());
		DPCreateProductFormBean prodDPBean = (DPCreateProductFormBean) form;
		prodDPBean.setArrCGList(null);
		//System.out.println(prodDPBean.getClass());
		
//		 Add by harbans on Reservation Obserbation 30th June
		
		List<DpProductTypeMasterDto> reverseProductList = dpms.getAllCommercialProducts(Integer.parseInt(circleId));
		//prodDPBean.setReverseProductList(reverseProductList);
		//request.setAttribute("reverseProductList", reverseProductList);
		
		
		ajaxCall(request,response,cgList,reverseProductList);
		prodDPBean.setArrCGList(cgList);
		return null;
	}
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,ArrayList arrGetValue,List<DpProductTypeMasterDto> reverseProductList)throws Exception{
		 
	 	Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		LabelValueBean lVB;
		for (int counter = 0; counter < arrGetValue.size(); counter++) {

				optionElement = root.addElement("option");
				lVB = (LabelValueBean)arrGetValue.get(counter);
				if (lVB != null) {
					optionElement.addAttribute("value",lVB.getValue());
					
					optionElement.addAttribute("text",lVB.getLabel());
				} else {
					optionElement.addAttribute("value", "None");
					optionElement.addAttribute("text", "None");
				}				
			}
		

		
		Element optionElement1;
		
		Iterator iter = reverseProductList.iterator();
		DpProductTypeMasterDto masterDTO = new DpProductTypeMasterDto();
		while(iter.hasNext())
		{
			optionElement1 = root.addElement("optionrevesre");
			masterDTO =	(DpProductTypeMasterDto)iter.next();
			optionElement1.addAttribute("value", masterDTO.getProductId());
			optionElement1.addAttribute("text", masterDTO.getProductName());
			
		}
	
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush();
	 
 }
//	Added by Shilpa 
	
	
	private void setDefaultValue(DPCreateProductFormBean productFormBean,
			HttpServletRequest request) throws Exception {
			logger.info("in setDefaultValue... ");
			DPMasterService dcProductService = new DPMasterServiceImpl();
			List<DpProductTypeMasterDto> dcProductTypeList = dcProductService.getProductTypeMaster();

			productFormBean.setDcProductTypeList(dcProductTypeList);
			request.setAttribute("dcProductTypeList", dcProductTypeList);
			String productCategory = productFormBean.getBusinessCategory();	
			logger.info("in setDefaultValue productCategory == "+productCategory);
			//Added by Shilpa Khanna for Population product category list
			if(productCategory != null  && !productCategory.equals("")) {
				List<DpProductCategoryDto> dcProductCategList = dcProductService.getProductCategoryLst(productCategory);
				productFormBean.setDcProductCategoryList(dcProductCategList);
				request.setAttribute("dcProductCategoryList", dcProductCategList);
			}
	}
	
	public ActionForward getParentProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException 
			{
				logger.info("In method getParentProduct().");
				
				try 
				{
					DPMasterService dcProductService = new DPMasterServiceImpl();
					String productCategory = request.getParameter("businessCategory");
					List<DpProductCategoryDto> dcProductCategList = dcProductService.getProductCategoryLst(productCategory);
					
								
					// Ajax start
					Document document = DocumentHelper.createDocument();
					Element root = document.addElement("options");
					Element optionElement;
					
					Iterator iter = dcProductCategList.iterator();
					while (iter.hasNext()) 
					{
						DpProductCategoryDto prodCateDto = (DpProductCategoryDto) iter.next();
						optionElement = root.addElement("option");
						optionElement.addAttribute("text", prodCateDto.getProductCategory());
						optionElement.addAttribute("value", prodCateDto.getProductCategory());
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
	//Added by sugandha
	public ActionForward getCheckedParentProduct(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws IOException, ServletException 
	{
		logger.info("In method getCheckedParentProduct11111().");
		
		try 
		{
			DPMasterService dcProductService = new DPMasterServiceImpl();
			String circleId = request.getParameter("circleid");
			logger.info("getting selected value of circleID  ::  "+circleId);
			String productCategory = request.getParameter("productCategory");
			logger.info("selected productCategory::"+productCategory);
			
			String strCheck = dcProductService.checkProductName(productCategory,Integer.parseInt(circleId));
			
			logger.info("strCheck  ::  "+strCheck);
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			optionElement = root.addElement("option");
			optionElement.addAttribute("message", strCheck);
			
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			
		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("error in validating parent product  ::  "+ex.getMessage());
			logger.error("error in validating parent product  ::  "+ex);
		}
		
		return null;
	}
}