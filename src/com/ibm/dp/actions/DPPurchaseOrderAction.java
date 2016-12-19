package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPPurchaseOrderFormBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.DPDeliveryChallanAcceptDTO;
import com.ibm.dp.dto.DPPurchaseOrderDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPCommonService;
import com.ibm.dp.service.DPDeliveryChallanAcceptService;
import com.ibm.dp.service.DPPurchaseOrderService;
import com.ibm.dp.service.impl.DPCommonServiceImpl;
import com.ibm.dp.service.impl.DPDeliveryChallanAcceptServiceImpl;
import com.ibm.dp.service.impl.DPPurchaseOrderServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

/**
 * DPPurchaseOrderAction class is gateway for other class and interface for Purchase Order Requisition related data
 *
 * @author Rohit Kunder
 */
public class DPPurchaseOrderAction extends DispatchAction {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPPurchaseOrderAction.class.getName());
	
	private static final String INIT_SUCCESS = "initSuccess";
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  success or failure
	 * @throws Exception
	 * @desc initial information which fetch the category from the table and display in category dropdown [infront end screen].
	 */
	public ActionForward initPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long loginId = sessionContext.getId();
			ActionErrors errors = new ActionErrors();
			DPPurchaseOrderService dppo = new DPPurchaseOrderServiceImpl();
			
			ArrayList bcaList = new ArrayList(); // for business category
			ArrayList typeList = new ArrayList(); //for product type list
			
			AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			//int fnfcount =0;
			try 
			{
				// fnfcount = dppo.checkFNFDone(loginId);
				bcaList = dppo.getBusinessCategory();
				System.out.println("Business Category List "+ bcaList);
				typeList = dppo.getProductTypeList();

				System.out.println("Product Type Category List "+ typeList);
				DPPurchaseOrderFormBean dppbean = (DPPurchaseOrderFormBean) form;
				String distTransactionType = dppo.checkTransactionType(loginId);
				 logger.info("distTransactionType == "+distTransactionType);
				session.setAttribute("FNFdone", distTransactionType);
				dppbean.setHiddenTransaction(distTransactionType);
				request.setAttribute("hiddenTransaction", distTransactionType);
				if(roleList.contains("ROLE_CA")){
					dppbean.setRoleCircleAdmin("Y");
				}	
				else
					dppbean.setRoleCircleAdmin("N");
				dppbean.setCategoryList(bcaList); 
				dppbean.setProductTypeList(typeList);
				
				// logger.info("distTransactionType == "+distTransactionType+"fnfcount"+fnfcount);
			
			}catch (Exception e) {
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				errors.add("errors",new ActionError(e.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			saveToken(request);
			/*if(fnfcount!=0)
				session.setAttribute("FNFdone", "true");
			else
				session.setAttribute("FNFdone", "false");*/
			return mapping.findForward(INIT_SUCCESS);
		
			
	}// end initPO

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @desc This method fetch the product list from the table 
	 */
	public ActionForward getProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;

			String selected = "";
			selected = request.getParameter("selected");
			int selectedValue = Integer.parseInt(selected.trim());

			String productType = "";
			productType = request.getParameter("productType");
			int  productTypeValue= Integer.parseInt(productType.trim());
          	DPPurchaseOrderService ddpo = new DPPurchaseOrderServiceImpl();
			DPPurchaseOrderDTO dppoDTO = new DPPurchaseOrderDTO();
			ArrayList pnaList = new ArrayList(); // for product name

			try {
				//pnaList = ddpo.getProductName(selectedValue);
				HttpSession session = request.getSession();
				UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				pnaList = ddpo.getProductName(hboUser.getCircleId(),selectedValue,productTypeValue);
				for (int counter = 0; counter < pnaList.size(); counter++) {
					optionElement = root.addElement("option");
					dppoDTO = (DPPurchaseOrderDTO) pnaList.get(counter);
					if (dppoDTO != null) {
						optionElement.addAttribute("value", dppoDTO.getProductCode());//change to product_id
						optionElement.addAttribute("text", dppoDTO.getProductName());
					} else {
						optionElement.addAttribute("value", "None");
						optionElement.addAttribute("text", "None");
					}
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				OutputFormat outputFormat = OutputFormat.createCompactFormat();
				XMLWriter writer = new XMLWriter(out);
	
				writer.write(document);
				writer.flush();
				out.flush();
			
			}catch (Exception e) {
				logger	.error("**********->ERROR IN FETCHING PRODUCT NAME LIST [getProductList] function"+e);
				
			}
		return null;
	}//end getProductList
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return success or failure
	 * @throws Exception
	 * @desc This method insert the data for purchase order requisition
	 */
	public ActionForward insertPurchaseOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
				HttpSession session = request.getSession();
				ActionErrors errors = new ActionErrors();
				ActionMessages msg = new ActionMessages();
			
			
				String message = "";
				try {
					UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
					HBOUser hboUser = new HBOUser(userSessionContext);
					
					
					DPPurchaseOrderFormBean dppfb = (DPPurchaseOrderFormBean)form;
					DPPurchaseOrderDTO dpoDTO = new DPPurchaseOrderDTO();
					DPPurchaseOrderService dppos = new DPPurchaseOrderServiceImpl();
					
					int loginID = (hboUser.getId()).intValue();//PR_Dist_ID
					int circleID=Integer.parseInt(hboUser.getCircleId());
					
					if (isTokenValid(request,true)){
							
							message=dppos.insertPurchaseOrder(dppfb,circleID,loginID);
						}
						if(message.equalsIgnoreCase(Constants.ERROR_MESSAGE)){
							errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError("error.occured"));
							msg.add("file",new ActionError("error.occured"));
							saveErrors(request, (ActionErrors) errors);
							return mapping.findForward(message);
						}else if(message.equalsIgnoreCase(Constants.ERROR_PRODUCT_NOT_PROCESSED)){
							logger.info("in Product has not flowed to botree condition");
							msg.add("file",new ActionMessage("PO.insert.Error"));
							saveMessages(request, msg);
							
							
							/*errors.add(HBOConstants.ERROR_OCCURED_MESSAGE,new ActionError("error.occured"));
							msg.add("file",new ActionError("error.occured"));
							msg.add("file",new ActionMessage("error.occured"));
							saveErrors(request, (ActionErrors) errors);*/
							return mapping.findForward("success");
						}else if(message.equalsIgnoreCase(HBOConstants.SUCCESS_MESSAGE)){
							msg.add("file",new ActionMessage("PO.insert"));
							saveMessages(request, msg);
						}
						
						log.info("***: Exit from insertPurchaseOrder :***");
				} catch (RuntimeException e) {
					e.printStackTrace();
					logger.info("EXCEPTION OCCURED  in Action::  "+e.getMessage());
					msg.add("file",new ActionError("error.occured"));
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward(message);
				}
				catch (DPServiceException e) {
					e.printStackTrace();
					logger.info("DPServiceException OCCURED  in Action::  "+e.getMessage());
					msg.add("file",new ActionError("error.occured"));
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward(message);
				}
				catch (Exception e) {
					e.printStackTrace();
					logger.info("EXCEPTION OCCURED  in Action::  "+e.getMessage());
					msg.add("file",new ActionError("error.occured"));
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward(message);
				}
					
					return mapping.findForward(message);
		}//end insertPurchaseOrder
				
//Added By Harpreet
	
	public ActionForward initViewPODetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		DPPurchaseOrderFormBean dppfb = (DPPurchaseOrderFormBean)form;
		DPPurchaseOrderService dppos = new DPPurchaseOrderServiceImpl();
		DPPurchaseOrderDTO  dto = new DPPurchaseOrderDTO (); 
		
		
		ArrayList statusList = null;
		statusList =dppos.getPOStatus();
		request.setAttribute("POStatusList", statusList);
		//dppfb.setStatusList(statusList);
		dppfb.setIntStatus("yes");
		
	return mapping.findForward("viewPODetails");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return success or failure
	 * @throws Exception
	 * @desc This method fetch details of purchase order requisition.
	 */		
	public ActionForward viewPODetails(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
				HttpSession session = request.getSession();
				ActionForward forward = new ActionForward(); // return value
				DPPurchaseOrderFormBean dppfb = (DPPurchaseOrderFormBean)form;
				DPPurchaseOrderService dppos = new DPPurchaseOrderServiceImpl();
				DPPurchaseOrderDTO  dto = new DPPurchaseOrderDTO (); 
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				HBOUser hboUser = new HBOUser(userSessionContext);
				String roleName="";
				AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
				ArrayList roleList = authorizationInterface.getUserCredentials(userSessionContext.getGroupId(), ChannelType.WEB);
				ArrayList porList = new ArrayList();
				ArrayList prCountList = new ArrayList();
				ArrayList countList = new ArrayList();
				if(roleList.contains(HBOConstants.ROLE_DIST))
					roleName=HBOConstants.ROLE_DIST;
				else if(roleList.contains(HBOConstants.ROLE_CIRCLE))
					roleName=HBOConstants.ROLE_CIRCLE;
				
				if(roleList.contains("ROLE_CANCEL_PO")){
					dppfb.setRoleCircleAdmin("Y");
				}	
				else
					dppfb.setRoleCircleAdmin("N");
			
				
				
			/**
			 * pagination code
			 * **/ActionErrors errors = new ActionErrors();
				int circleID=Integer.parseInt(hboUser.getCircleId());
				//int noofpages = dppos.viewPORCount(circleID);
				//Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
				//countList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,-1,-1));
				//porList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,pagination.getLowerBound(),pagination.getUpperBound()));
				//countList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,-1,-1,dppfb.getStatusSelect()));
				//logger.info("Total Records fetched == "+countList.size());
				//porList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,pagination.getLowerBound(),pagination.getUpperBound(),dppfb.getStatusSelect()));
				
				porList = ((ArrayList)dppos.viewPOList(hboUser.getId(),circleID,-1,-1,dppfb.getStatusSelect()));
				
				
				//logger.info("Records fetched after pagination"+porList.size());
				//prCountList = ((ArrayList)dppos.getPrCount(hboUser.getId()));
			//	int noofpages = Utility.getPaginationSize(countList.size());
				try {
					dppfb.setPoList(porList);
					//dppfb.setPrCountList(prCountList);
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward("viewPODetails");
				}
				request.setAttribute("porList", porList);
				//request.setAttribute("PAGES", noofpages);
				//Added by harpreet
				dppfb.setIntStatus("no");
				ArrayList statusList = null;
				statusList =dppos.getPOStatus();
				request.setAttribute("POStatusList", statusList);
				
			return mapping.findForward("viewPODetails");
		}//end viewPODetails
		
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @desc This method is for cancellation of purchase order requisition 
	 */
	public ActionForward cancelporNumber(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) 
			throws Exception {
		
				ActionMessages msg = new ActionMessages();
				ActionForward forward = new ActionForward(); // return value
				DPPurchaseOrderFormBean dppfb = (DPPurchaseOrderFormBean)form;
				DPPurchaseOrderService dppos = new DPPurchaseOrderServiceImpl();
				ActionErrors errors = new ActionErrors();
				try {
					int prnoCancel = dppfb.getCancelpor_no();
					int productno =dppfb.getProductID();
									
					dppos.cancelPORNO(prnoCancel,productno);
					dppfb.setPrcancelStatus("CANCEL");
					msg.add("file",new ActionMessage("VPO.Cancel"));//check message
					saveMessages(request, msg);
				} catch (Exception e) {

					e.printStackTrace();
					logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
					errors.add("errors",new ActionError(e.getMessage()));
					saveErrors(request, errors);
					return mapping.findForward("success");
				
				}
				
				return mapping.findForward("success");
			}//end cancelporNumber
	
	public ActionForward acceptStock(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) 
			throws Exception {
		
				ActionMessages msg = new ActionMessages();
				DPPurchaseOrderService purchaseService = new DPPurchaseOrderServiceImpl();
				try {
					String[] selectedInvNos = request.getParameterValues("check1");
					purchaseService.acceptStock(selectedInvNos);
					msg.add("INSERT_SUCCESS",new ActionMessage(HBOConstants.updateSuccessKey));//check message
					saveMessages(request, msg);
				} catch (Exception e) {
					msg.add("ERROR_OCCURED",new ActionMessage(HBOConstants.errorOccuredKey));//check message
					saveMessages(request, msg);
					logger.error("**********->ERROR IN VIEW THE PURCHASE ORDER [viewUserList] function");
				}
				return mapping.findForward("success");
			}
	
	public ActionForward getProductUnit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String productName = request.getParameter("cond1");
		DPPurchaseOrderService poService= new DPPurchaseOrderServiceImpl();
		String productUnit = poService.getProductUnit(productName);		
		ajaxCall(request, response, productUnit,"text");
		return null;
	}
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,String productUnit,String text)throws Exception{
		try {
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			String pro = productUnit;
			PrintWriter out = response.getWriter();
			out.write(productUnit);
			out.flush();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}				
		}
	
	//Added by Mohammad Aslam
	public ActionForward getQuantityAsPerDp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		HttpSession session = request.getSession();
		try {
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long loginId = sessionContext.getId();
			String selectedProductName = request.getParameter("productID");
			DPPurchaseOrderService poService = new DPPurchaseOrderServiceImpl();
			String quantityDP = poService.getDPQuantity(selectedProductName, loginId);		
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.write(quantityDP);
			out.flush();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}//END
	
	public ActionForward getQuantityAsOpenStock(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception 
	{
		HttpSession session = request.getSession();
		try {
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long loginId = sessionContext.getId();
			String productId = request.getParameter("productID");
			
			DPPurchaseOrderService poService = new DPPurchaseOrderServiceImpl();
			
			// Get Total Opening Stock of Open Stock Product.
			String openStockAsPerDP = poService.getDPTotalOpenStock(loginId,productId);
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			out.write(openStockAsPerDP);
			out.flush();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
	}//END
	
	public String getEligibleAmt(String distId,int productId)
			throws Exception {		
		//String secuity="0";
		//String loan ="0";
		String balanceStr = null;
		int intDistId = Integer.parseInt(distId);
		NumberFormat formatter = new DecimalFormat("#0.00");
		DPPurchaseOrderService poService = new DPPurchaseOrderServiceImpl();	
		String elgibilityFlag= poService.getEligibilityFlag();
		//logger.info("elgibilityFlag==================="+elgibilityFlag);
		String prodCat= poService.getProductCat(productId);
		//logger.info("prodCat==================="+prodCat);
	
		if(elgibilityFlag.equalsIgnoreCase("N") || !prodCat.equalsIgnoreCase("1") )//old code
		{
		
		int secuityAndLoan = poService.getSecurityLoan(distId);
		/*String secuityAndLoanArr[] = secuityAndLoan.split(",");
		if(secuityAndLoanArr.length!=0){
		secuity = secuityAndLoanArr[0];
		loan = secuityAndLoanArr[1];
		}
		double secuityAmt = Double.parseDouble(secuity);
		double loanAmt = Double.parseDouble(loan);*/
		DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();
		Map<Integer, Double> totalStockMap =eligibilityService.getDistTotalStockEffectPrice(intDistId);
		double totalStock = 0.0;
		for (Object key: totalStockMap.keySet()){
			totalStock +=totalStockMap.get(key);		
		}	
		double balance = secuityAndLoan -(totalStock);
		 balanceStr = formatter.format(balance);
		}
		//else part added for new eligibility by Neetika for BFR 2 on 25-07-2014
		else
		{
			DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();
			double balanceNew =eligibilityService.getNewUploadedBalance(intDistId,productId);
			balanceStr = formatter.format( balanceNew);
		}
		
		return balanceStr;
	}
	
	public ActionForward getBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
		
		DPPurchaseOrderFormBean DPbean = (DPPurchaseOrderFormBean)form;
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String distId = String.valueOf(sessionContext.getId());
		int productId = Integer.parseInt(request.getParameter("productId"));//uncommented by neetika on 25-07-2014
	//	int intDistId = Integer.parseInt(distId);
		//logger.info("getBalance++++++++++++++++++"+distId+"  : productId"+productId);
		String balanceStr = getEligibleAmt(distId,productId);	
		
	//	DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();
		//Map<Integer, Double> totalStockMap =eligibilityService.getDistTotalStockEffectPriceAll(intDistId);
		//double balance = 0.0;
	//	if(totalStockMap.get(intDistId) != null) {
	//		balance = totalStockMap.get(intDistId);
	//	}
	//	NumberFormat formatter = new DecimalFormat("#0.00");
	//	String balanceStr = formatter.format(balance);
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
			optionElement = root.addElement("option");
			optionElement.addAttribute("balance", balanceStr);
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();			
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			
			return null;
	}
	
	
	public ActionForward getTotalProductCostQty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
		
		int qtyInt=0;
		DPPurchaseOrderFormBean DPbean = (DPPurchaseOrderFormBean)form;
		String qty = request.getParameter("quantity");
		if(qty!=null)
			qtyInt= Integer.parseInt(qty);
		 
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String distId = String.valueOf(sessionContext.getId());
		int distIdInt = Integer.parseInt(distId);
		String productId = request.getParameter("productId");
		int productIdInt = Integer.parseInt(productId);
		
		
		DPPurchaseOrderService poService = new DPPurchaseOrderServiceImpl();		
		DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();
		NumberFormat formatter = new DecimalFormat("#0.00");
		//productPrice = poService.getProductCost(productId);
		 //Neetika
		double eligibleQty=0;
		double eligibleQty1=0;	
		String bal = request.getParameter("balAmt");
		double balDouble = Double.parseDouble(bal);
		String balanceStrg = formatter.format(balDouble);
		double balance =0.0;
		String balanceStr ="";
		String flag="";
		int maxPoQty= 0;
		String totalProductCostStr ="";
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
	    optionElement = root.addElement("option");
	

		
		String elgibilityFlag= poService.getEligibilityFlag();
		int uploadedEligibility=0;
		double totalProductCost =0.0;
		//logger.info("elgibilityFlag==================="+elgibilityFlag);
		String prodCat= poService.getProductCat(productIdInt);
		//logger.info("prodCat==================="+prodCat);
	
		if(elgibilityFlag.equalsIgnoreCase("N") || !prodCat.equalsIgnoreCase("1") )//old code
		{
			
		
			
		double productPrice = eligibilityService.getProductEffectPrice(productIdInt);
		
		 totalProductCost = productPrice * qtyInt;
		
		//String balanceStrAmt = getEligibleAmt(distId);		
	//	Map<Integer, Double> totalStockMap =eligibilityService.getDistTotalStockEffectPriceAll(distIdInt);
	//	double balanceD = 0.0;
	//	balanceD =totalStockMap.get(distIdInt);		
	//	String balanceStrg = formatter.format(balanceD);
		
	//	double balanceAmtdouble = Double.parseDouble(balanceStrg);
		
		
       // String bal = request.getParameter("balAmt");
		//double balDouble = Double.parseDouble(bal);
		//String balanceStrg = formatter.format(balDouble);
		//double balance = balDouble - totalProductCost;		
		 balance = balDouble - totalProductCost;
		
		
		
		eligibleQty = getEligibleQty(distIdInt,productIdInt,bal);
		maxPoQty= eligibilityService.getDistMaxPOQty(distIdInt, productIdInt);
		if(maxPoQty<eligibleQty){
			eligibleQty1 = maxPoQty;
		}else{
			eligibleQty1 = eligibleQty;
		}
	//	eligibleQty2 = eligibilityService.getDistMaxPOQty(distIdInt, productIdInt);
		
		
	/*	if(productPrice!=0.0)
		eligibleQty = (balDouble/productPrice);
	*/	
		  balanceStr = formatter.format(balance);
         		//double eligibleQty = (balance/productCostInt);
		
		if(balDouble<0){		
			flag="1";
		}else{
			if(eligibleQty1<qtyInt){
				flag="1";
			}else{
				flag="0";
			}			
		}
		if(eligibleQty<0){
			eligibleQty=0;		
		}
		 totalProductCostStr = String.valueOf(totalProductCost);
		//int maxPoQty= eligibilityService.getDistMaxPOQty(distIdInt, productIdInt);
		
	/*	DPbean.setBalAmount(balanceStr);
		DPbean.setEligibleQty(eligibleQty);
		DPbean.setMaxPoQty(maxPoQty);
		DPbean.setFlag(flag);
		*/
		 //Neetika
		 	optionElement.addAttribute("balance", balanceStr);
			optionElement.addAttribute("balanceBefore", balanceStrg);
			optionElement.addAttribute("totalProductCostStr", totalProductCostStr);
			optionElement.addAttribute("eligibleQty", formatter.format(eligibleQty));
			optionElement.addAttribute("maxPoQty", formatter.format(maxPoQty));
			optionElement.addAttribute("flag", flag);
		}
	//Neetika
			else
			{
				uploadedEligibility= eligibilityService.getUploadedEligibility(distIdInt, productIdInt);
				eligibleQty=uploadedEligibility;
				logger.info("distIdInt : "+distIdInt+" productIdInt : "+productIdInt+" uploadedEligibility: "+uploadedEligibility);
				
				double balanceNew =eligibilityService.getNewUploadedBalance(distIdInt,productIdInt);
				balanceStr = formatter.format( balanceNew);
				if(balanceNew<0){		
					flag="1";
				}else{
					if(eligibleQty<qtyInt){
						flag="1";
					}else{
						flag="0";
					}			
				}
				optionElement.addAttribute("balance", balanceStr);
				optionElement.addAttribute("balanceBefore", balanceStr);
				//optionElement.addAttribute("totalProductCostStr", totalProductCostStr);
				optionElement.addAttribute("eligibleQty", formatter.format(eligibleQty));
				optionElement.addAttribute("maxPoQty", formatter.format(eligibleQty));//business has confirmed that in maxPoQty we have to put eligibleQty
				optionElement.addAttribute("flag", flag);
			}
			
		 //Neetika
			
		/*	optionElement.addAttribute("balance", balanceStr);
			optionElement.addAttribute("balanceBefore", balanceStrg);
			optionElement.addAttribute("totalProductCostStr", totalProductCostStr);
			optionElement.addAttribute("eligibleQty", formatter.format(eligibleQty));
			optionElement.addAttribute("maxPoQty", formatter.format(maxPoQty));
			optionElement.addAttribute("flag", flag);*/
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();			
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			
			return null;
	}
		public double getEligibleQty(int distIdInt, int productIdInt,String bal) throws Exception{
			
			DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();
			double productPrice = eligibilityService.getProductEffectPrice(productIdInt);
			
			
			double balDouble = Double.parseDouble(bal);
			double eligibleQty=0;
			
			
			if(productPrice!=0.0)
			eligibleQty = (balDouble/productPrice);
			
		//	int maxStockEligibilty = eligibilityService.getDistStockEligibilityMax(distIdInt,productIdInt);
		//	int productCurrentStock = eligibilityService.getDistTotalStock(distIdInt, productIdInt);
			
			
			return eligibleQty;
		}
	public ActionForward getEligibleQty(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {			
	
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		try{
		String distId = String.valueOf(sessionContext.getId());
		int distIdInt = Integer.parseInt(distId);
			
		String product = request.getParameter("product");
		int productIdInt = Integer.parseInt(product);
		String bal = request.getParameter("balAmt");
		
		NumberFormat formatter = new DecimalFormat("#0.00");
		double eligibleQty=0;
		double eligibleQty1=0;
		int uploadedEligibility=0;
		//28-07-2014 Neetika for BFR 2 Eligibility Field Value change
		DPPurchaseOrderService poService= new DPPurchaseOrderServiceImpl();
		String elgibilityFlag= poService.getEligibilityFlag();
		String prodCat= poService.getProductCat(productIdInt);
		//logger.info("prodCat==================="+prodCat);
		DPCommonService eligibilityService = DPCommonServiceImpl.getInstance();		
		if(elgibilityFlag.equalsIgnoreCase("N") || !prodCat.equalsIgnoreCase("1") )//old code
		{
			//logger.info("old code===================");
		eligibleQty1 = getEligibleQty(distIdInt,productIdInt,bal);
		int maxPoQty= eligibilityService.getDistMaxPOQty(distIdInt, productIdInt);
		if(maxPoQty<eligibleQty1){
			eligibleQty = maxPoQty;
		}else{
			eligibleQty = eligibleQty1;
		}
		if(eligibleQty<0){
			eligibleQty=0;		
		}
		}
		else
		{
			uploadedEligibility= eligibilityService.getUploadedEligibility(distIdInt, productIdInt);
			eligibleQty=uploadedEligibility;
			logger.info("distIdInt : "+distIdInt+" productIdInt : "+productIdInt+" uploadedEligibility: "+uploadedEligibility);
		}
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		
			optionElement = root.addElement("option");

			optionElement.addAttribute("eligibleQuantity", formatter.format(Math.round(eligibleQty)));
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();			
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		}catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			
		}
			return null;
	}
	
	public ActionForward viewSerialsAndProductList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//logger.info("-----------------888888888888888  viewSerialsAndProductList ACTION CALLED  -----------------");
		ActionErrors errors = new ActionErrors();
		//DPDeliveryChallanAcceptFormBean dpDCAFormBean = (DPDeliveryChallanAcceptFormBean) form;
		DPPurchaseOrderFormBean dpDCAFormBean = (DPPurchaseOrderFormBean) form;
		
		try 
		{	
			String invoiceNo = request.getParameter("invoiceNo");
			String productId = request.getParameter("productID");
			String circleId = request.getParameter("circleId");
			
			
			DPDeliveryChallanAcceptService dpDCAService = new DPDeliveryChallanAcceptServiceImpl();
	
			ArrayList<DPDeliveryChallanAcceptDTO> dpDCAList = dpDCAService.vewAllSerialsOfPOStatus(invoiceNo,productId,circleId);
			//logger.info("LISt Size of SERILA Nos -=-== "+dpDCAList.size());
			dpDCAFormBean.setDeliveryChallanList(dpDCAList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("viewSerialProdStatus");
		}
		return mapping.findForward("viewSerialProdStatus");
	}
	
/*	public ActionForward checkTransactionType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;

			String selected = "";
			selected = request.getParameter("selected");
			int selectedValue = Integer.parseInt(selected.trim());
			
			DPPurchaseOrderService ddpo = new DPPurchaseOrderServiceImpl();
			String result="";

			try {
				//pnaList = ddpo.getProductName(selectedValue);
				HttpSession session = request.getSession();
				UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				result = ddpo.checkTransactionType(selectedValue);
				if(!result.equals("") && !result.equalsIgnoreCase("SUCCESS")) {
					optionElement = root.addElement("option");
					optionElement.addAttribute("value", result);//change to product_id
				}
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "No-Cache");
				PrintWriter out = response.getWriter();
				OutputFormat outputFormat = OutputFormat.createCompactFormat();
				XMLWriter writer = new XMLWriter(out);
	
				writer.write(document);
				writer.flush();
				out.flush();
			
			}catch (Exception e) {
				logger	.error("**********->ERROR IN checkTransactionType function"+e);
				
			}
		return null;
	}*/
	
}





