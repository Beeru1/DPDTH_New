
package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

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
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPReverseCollectionBean;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
import com.ibm.dp.service.DPReverseCollectionService;
import com.ibm.dp.service.impl.DPReverseCollectionServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * Controller class for reverse collection  management 
 * @author harbans singh
 */
public class DPReverseCollectionAction extends DispatchAction 
{
   /* Logger for this Action class. */
	private static Logger logger = Logger.getLogger(DPReverseCollectionAction.class.getName());

	public static final String INIT_SUCCESS = "initsuccess";

	/**
	 * This method initializes reverse collection.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initReverseCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{

		//logger.info("***************************** initReverseCollection() method.*****************************");
		DPReverseCollectionBean reverseCollectionBean = (DPReverseCollectionBean)form;
							
		try
		{
			logger.info(":::::initReverseCollection");
			DPReverseCollectionService reverseCollectionService =  new DPReverseCollectionServiceImpl(); 
			List<DPReverseCollectionDto>  reverseCollectionList = reverseCollectionService.getCollectionTypeMaster();
						
			reverseCollectionBean.setReverseCollectionList(reverseCollectionList);
			request.setAttribute("reverseCollectionList", reverseCollectionList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			request.setAttribute("message", Constants.COLLECTION_FAILURE_MESSAGE);
			reverseCollectionBean.setMessage(Constants.COLLECTION_FAILURE_MESSAGE);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);

	}
		
	// Get defect type on collection Id.
	public ActionForward getCollectionDefectType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
			logger.info("In method getCollectionDefectType().");
			String collectionId = request.getParameter("collectionId");
			int collectId = Integer.parseInt(collectionId);
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accountId = sessionContext.getId();
			List<DPReverseCollectionDto> collectionDefectList = reverService.getCollectionDefectType(collectId);
			int countdistTransactionType = reverService.checkTransactionTypeReverse(accountId);//Neetika
			//logger.info("In method countdistTransactionType()."+countdistTransactionType);
			
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = collectionDefectList.iterator();
			while (iter.hasNext()) 
			{
				DPReverseCollectionDto reverseCollectionDto = (DPReverseCollectionDto) iter.next();
				optionElement = root.addElement("option");
				optionElement.addAttribute("text", reverseCollectionDto.getDefectName());
				optionElement.addAttribute("value", reverseCollectionDto.getDefectId());
				optionElement.addAttribute("CPEFlag", ""+countdistTransactionType);//Neetika for IN2050785
			}
		
			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		
		return null;
	}
	
	
//	 Get collection product on collection Id.
	public ActionForward getCollectionProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
			logger.info("In method getCollectionProduct().");
			String collectionId = request.getParameter("collectionId");
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long accountId = sessionContext.getId();
			int circleId = sessionContext.getCircleId();
			
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			List<DPReverseCollectionDto> productCollectList = reverService.getProductCollection(collectionId, circleId);
			
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");

			Element optionElement1;
			Iterator iterProduct = productCollectList.iterator();
			while (iterProduct.hasNext()) 
			{
				DPReverseCollectionDto reverseCollectionDto = (DPReverseCollectionDto) iterProduct.next();
				optionElement1 = root.addElement("optionProduct");
				optionElement1.addAttribute("text", reverseCollectionDto.getProductName());
				optionElement1.addAttribute("value", reverseCollectionDto.getProductId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
	
			return null;
	}
	
//	 Update by harbans on Reverse Enhancement.
	// Validate collect serialno in case of DAO,Input serial should be activate within 15 days befor of collect date.
	public ActionForward validateCollectSerialNoDAOType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
			logger.info("In method validateCollectSerialNoDAOType().");
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
						
			String productId = request.getParameter("productId");
			String serialNo = request.getParameter("serialNo");
			int productIdNo = Integer.parseInt(productId);
								
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			String errorMsg = reverService.validateCollectSerialNo(productIdNo, serialNo);
								
			//	Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");

			//Set flag
			Element optionElementText = root.addElement("optionCollectFlag");
			optionElementText.addAttribute("text", errorMsg);
			optionElementText.addAttribute("value", errorMsg);
			
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
		

		return null;
	}
	
//	 Update by harbans on Reverse Enhancement.
//	 Validate reverse inventory allready collected seial no for same product.
	public ActionForward validateCollectSerialNoAllType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
			logger.info("In method validateCollectSerialNoAllType().");
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String accId = String.valueOf(distId);
			
			String productId = request.getParameter("productId");
			String serialNo = request.getParameter("serialNo");
			int productIdNo = Integer.parseInt(productId);
								
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			boolean collectUniqueFlag = reverService.validateUniqueCollectSerialNo(productIdNo, serialNo, accId);
								
			//	Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
						
//			Set UNIQUE flag
			Element optionElementText1 = root.addElement("optionCollectUniqueFlag");
			if(collectUniqueFlag)
			{
				optionElementText1.addAttribute("text", "TRUE");
				optionElementText1.addAttribute("value", "TRUE");
			}
			else
			{
				optionElementText1.addAttribute("text", "FALSE");
				optionElementText1.addAttribute("value", "FALSE");
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
		

		return null;
	}
	
//	 Update by harbans on Reverse Enhancement.
//	 Validate reverse inventory allready collected seial no for same product.
	public ActionForward validateCollectSerialNoC2SType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
			logger.info("In method validateCollectSerialNoC2SType().");
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			String accId = String.valueOf(distId);
			String serialNo = request.getParameter("serialNo");
											
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			boolean collectC2SFlag = reverService.validateC2SType(serialNo);
								
			//	Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
						
//			Set UNIQUE flag
			Element optionElementText1 = root.addElement("optionCollectC2SFlag");
			if(collectC2SFlag)
			{
				optionElementText1.addAttribute("text", "TRUE");
				optionElementText1.addAttribute("value", "TRUE");
			}
			else
			{
				optionElementText1.addAttribute("text", "FALSE");
				optionElementText1.addAttribute("value", "FALSE");
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
		

		return null;
	}
	
	public ActionForward validateSerialNumber(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		logger.info("in method validateSerialNumber");
		String strSerialNo = request.getParameter("collSRNo");
		Integer intCollectionID = Integer.valueOf(request.getParameter("collID"));
		Integer intProductID = Integer.valueOf(request.getParameter("prodID"));
		
		DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
		String strReturn = reverService.validateSerialNumber(strSerialNo, intCollectionID, intProductID);
		
		Document document = DocumentHelper.createDocument();
		
		Element root = document.addElement("mainRoot");
		
		Element elementText = root.addElement("message");
		
		elementText.addAttribute("text", strReturn);
		elementText.addAttribute("value", strReturn);
		
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
		
		return null;
	}
	/**
	 * This method to submit reverse collection.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitReverseCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{

		logger.info("***************************** submitReverseCollection() method.*****************************");
		
		// Get account Id form session.
		HttpSession session = request.getSession();
		DPReverseCollectionBean reverseCollectionBean = (DPReverseCollectionBean)form;
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long accountId = sessionContext.getId();
		int circleId = sessionContext.getCircleId();
		
		try
		{
			//System.out.println("\n\n"+reverseCollectionBean.getCollectionId()+" "+reverseCollectionBean.getCollectionName()+"\n\n");
			
			Pattern commaPattern = Pattern.compile(",");
			//Fetch from jsp page
			
			// Added by Shilpa Khanna  for new format
			logger.info("reverseCollectionBean.getJsArrNewSrNo()[0]   === "+reverseCollectionBean.getJsArrNewSrNo()[0]);
			String[] arrNewSrno = commaPattern.split(reverseCollectionBean.getJsArrNewSrNo()[0]);
			//System.out.println(arrNewSrno.length);
			ArrayList<String> arrNewSrnoList = new ArrayList<String>();
			for (String s : arrNewSrno) 
			{
				arrNewSrnoList.add(s);
			}
			
			
			//logger.info("reverseCollectionBean.getJsArrNewProduct()[0]   === "+reverseCollectionBean.getJsArrNewProduct()[0]);
			String[] arrNewProduct = commaPattern.split(reverseCollectionBean.getJsArrNewProduct()[0]);
			ArrayList<String> arrNewProductList = new ArrayList<String>();
			for (String s : arrNewProduct) 
			{
				arrNewProductList.add(s);
			}
			
			//logger.info("reverseCollectionBean.getJsArrAgeing()[0]   === "+reverseCollectionBean.getJsArrAgeing()[0]);
			String[] arrAgeing = commaPattern.split(reverseCollectionBean.getJsArrAgeing()[0]);
			ArrayList<String> arrAgeingList = new ArrayList<String>();
			for (String s : arrAgeing) 
			{
				arrAgeingList.add(s);
			}
			
			//logger.info("reverseCollectionBean.getJsArrInventoryCahngDate()[0]   === "+reverseCollectionBean.getJsArrInventoryCahngDate()[0]);
			String[] arrInvetryChngDate = commaPattern.split(reverseCollectionBean.getJsArrInventoryCahngDate()[0]);
			ArrayList<String> arrInvetryChngDateList = new ArrayList<String>();
			for (String s : arrInvetryChngDate) 
			{
				arrInvetryChngDateList.add(s);
			}
			
			String[] arrCollectionId = commaPattern.split(reverseCollectionBean.getJsArrCollectionId() [0]);
			ArrayList<String> arrCollectionlist = new ArrayList<String>();
			for (String s : arrCollectionId) 
			{
				arrCollectionlist.add(s);
			}

			String[] arrDefectId = commaPattern.split(reverseCollectionBean.getJsArrDefectId() [0]);
			ArrayList<String> arrDefectList = new ArrayList<String>();
			for (String s : arrDefectId) 
			{
				arrDefectList.add(s);
			}
			
			String[] arrProductId = commaPattern.split(reverseCollectionBean.getJsArrProductId() [0]);
			ArrayList<String> arrProductlist = new ArrayList<String>();
			for (String s : arrProductId) 
			{
				arrProductlist.add(s);
			}
			
			String[] jsArrSerails = commaPattern.split(reverseCollectionBean.getJsArrSerails() [0]);
			ArrayList<String> arrSerailslist = new ArrayList<String>();
			for (String s : jsArrSerails) 
			{
				arrSerailslist.add(s);
			}
						
			String[] arrRemarks = commaPattern.split(reverseCollectionBean.getJsArrRemarks() [0]);
			ArrayList<String> arrRemarksList = new ArrayList<String>();
			for (String s : arrRemarks) 
			{
				arrRemarksList.add(s);
			}
			
			String[] arrCustomerId = commaPattern.split(reverseCollectionBean.getJsArrCustomerId() [0]);
			ArrayList<String> arrCustomerIdList = new ArrayList<String>();
			for (String s : arrCustomerId) 
			{
				arrCustomerIdList.add(s);
			}
			
			String[] arrReqId = commaPattern.split(reverseCollectionBean.getJsArrReqId() [0]);
			ArrayList<String> arrReqIdList = new ArrayList<String>();
			for (String s : arrReqId) 
			{
				arrReqIdList.add(s);
			}
			
			// Make list of records as DTO list
			ListIterator<String> collectionIdListItr = arrCollectionlist.listIterator();
			ListIterator<String> defectIdListItr = arrDefectList.listIterator();
			ListIterator<String> productListItr = arrProductlist.listIterator();
			ListIterator<String> serialsListItr = arrSerailslist.listIterator();
			ListIterator<String> remarksListItr = arrRemarksList.listIterator();
			ListIterator<String> customerIdLItr = arrCustomerIdList.listIterator();
			ListIterator<String> reqIdLItr = arrReqIdList.listIterator();
			
			//Added by SHilpa
			ListIterator<String> newSrnoItr = arrNewSrnoList.listIterator();
			ListIterator<String> newProductListItr = arrNewProductList.listIterator();
			ListIterator<String> ageing = arrAgeingList.listIterator();
			ListIterator<String> invetroyChngDate = arrInvetryChngDateList.listIterator();
			
			String strCollecvtionID = "";
			String strNewSRNo = "";
			
			List<DPReverseCollectionDto> reverseCollectionDtoList = new ArrayList<DPReverseCollectionDto>();
			List<DPReverseCollectionDto> upgradeCollectionDtoList = new ArrayList<DPReverseCollectionDto>();
			
			String varNewSrNo = "";
			String varNewProduct = "";
			String varAgeing = "";
			String varInventChangeDate = "";
			String takePrint="";
			String varCustId="";
			while(collectionIdListItr.hasNext()&&defectIdListItr.hasNext()&&productListItr.hasNext()&&serialsListItr.hasNext()&&remarksListItr.hasNext()) 
			{
				DPReverseCollectionDto reverseCollectionDto = new DPReverseCollectionDto();
				DPReverseCollectionDto upgradeCollectionDto = new DPReverseCollectionDto();
				String prodId=productListItr.next();
				String sno=serialsListItr.next();
				strCollecvtionID = collectionIdListItr.next();
				//logger.info("COLLECTION ID  ::  "+strCollecvtionID);
				if(strCollecvtionID.equals("5"))
				{
					upgradeCollectionDto.setCollectionId(strCollecvtionID);
					upgradeCollectionDto.setProductId(prodId);
					upgradeCollectionDto.setSerialNo(sno);
					//upgradeCollectionDto.setTakePrint("Yes");
					takePrint="Yes";
					upgradeCollectionDtoList.add(upgradeCollectionDto);
					
				}
				reverseCollectionDto.setCollectionId(strCollecvtionID);
				reverseCollectionDto.setDefectId(defectIdListItr.next());
				reverseCollectionDto.setProductId(prodId);
				reverseCollectionDto.setSerialNo(sno);
				reverseCollectionDto.setRemarks(remarksListItr.next());
				reverseCollectionDto.setReqId(reqIdLItr.next());
//				Added by SHilpa
				if(newSrnoItr.hasNext())
				varNewSrNo = newSrnoItr.next();
				if(customerIdLItr.hasNext())
				varCustId=customerIdLItr.next();
				if(newProductListItr.hasNext())
				varNewProduct = newProductListItr.next();
				if(ageing.hasNext())
				varAgeing = ageing.next();
				if(invetroyChngDate.hasNext())
				varInventChangeDate = invetroyChngDate.next();
				if(!varCustId.equals("-1")){
					reverseCollectionDto.setCustomerId(varCustId);
				}
				if(!varNewSrNo.equals("-1")){
					reverseCollectionDto.setNewSrno(varNewSrNo);
				}
				if(!varNewProduct.equals("-1")){
					reverseCollectionDto.setNewProductId(varNewProduct);
				}
				if(!varAgeing.equals("-1")){
					reverseCollectionDto.setAgeing(varAgeing);
				}
				if(!varInventChangeDate.equals("-1")){
					reverseCollectionDto.setInventryChngDate(varInventChangeDate);
				}
				
				
				
				
				reverseCollectionDtoList.add(reverseCollectionDto);
			}
			
			DPReverseCollectionService reverseCollectionService =  new DPReverseCollectionServiceImpl(); 
			upgradeCollectionDtoList=reverseCollectionService.submitReverseCollection(reverseCollectionDtoList, circleId, accountId);
			List<DPReverseCollectionDto>  reverseCollectionList = reverseCollectionService.getCollectionTypeMaster();
			
			reverseCollectionBean.setReverseCollectionList(reverseCollectionList);
			request.setAttribute("reverseCollectionList", reverseCollectionList);
			//reverseCollectionBean.setUpgradeCollectionList(upgradeCollectionDtoList);
			session.setAttribute("upgradeCollectionDtoList", upgradeCollectionDtoList);
			reverseCollectionBean.setTakePrint(takePrint);
						
			request.setAttribute("message", Constants.COLLECTION_SUCCESS_MESSAGE);
			reverseCollectionBean.setMessage(Constants.COLLECTION_SUCCESS_MESSAGE);
			
		}
		catch(Exception ex)
		{
			if(ex.getMessage()!=null && ex.getMessage().equalsIgnoreCase("error.stockReceived")){
				request.setAttribute("message", "This STB is already pending as a damaged stock. Please contact your TSM");
				reverseCollectionBean.setMessage("This STB is already pending as a damaged stock. Please contact your TSM");
				errors.add("errors.product1",new ActionError("This STB is already pending as a damaged stock. Please contact your TSM"));
				saveErrors(request, errors);
			}
			else
			{
				request.setAttribute("message", Constants.COLLECTION_FAILURE_MESSAGE);
				reverseCollectionBean.setMessage(Constants.COLLECTION_FAILURE_MESSAGE);
				
			}
			ex.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+ex.getMessage());
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);

	}
	//getCollectionProduct

	//Added by Shilpa Khanna
	//	 Get Inventory Change List on collection Id.
	public ActionForward getInventoryList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
		DPReverseCollectionBean reverseCollectionBean = (DPReverseCollectionBean)form;
	
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			logger.info("In method getInventoryList().");
			String collectionId = request.getParameter("collectionId");
			Long accountId = sessionContext.getId();
			//logger.info("In method getInventoryList ().  account ID == "+accountId + "  and collectionId == "+collectionId );
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			List<DpReverseInvetryChangeDTO> collectionDefectList = reverService.getGridDefectList(collectionId);
			int countdistTransactionType = reverService.checkTransactionTypeReverse(accountId);//Neetika
			//logger.info("In method countdistTransactionType()."+countdistTransactionType);
			//session.setAttribute("CPEFlag", countdistTransactionType);
			
			 //logger.info("countdistTransactionType == "+countdistTransactionType);
			List<DpReverseInvetryChangeDTO> inventChangList = reverService.getInventoryChangeList(collectionId, accountId.toString());
			reverseCollectionBean.setInventoryChangeList(inventChangList);
			reverseCollectionBean.setListDefect(collectionDefectList);
			//reverseCollectionBean.setTypedist(countdistTransactionType);
			
//			 Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
		
			Element optionElement1;
			Element optionElement2;
			Element optionElement3;
			Iterator iterProduct = inventChangList.iterator();
			Iterator iterDefect = collectionDefectList.iterator();
			while (iterProduct.hasNext()) 
			{
				
				DpReverseInvetryChangeDTO reverseCollectionDto = (DpReverseInvetryChangeDTO) iterProduct.next();
				optionElement1 = root.addElement("optionProduct");
				optionElement1.addAttribute("collectionType", reverseCollectionDto.getCollectionType());
				optionElement1.addAttribute("collectionId", reverseCollectionDto.getCollectionId());
				
				
				optionElement1.addAttribute("defectiveSerialNo", reverseCollectionDto.getDefectiveSerialNo());
				optionElement1.addAttribute("defectiveProductId", reverseCollectionDto.getDefectiveProductId());
				optionElement1.addAttribute("defectiveProductName", reverseCollectionDto.getDefectiveProductName());
				optionElement1.addAttribute("changedSerialNo", reverseCollectionDto.getChangedSerialNo());
				optionElement1.addAttribute("changedProductId", reverseCollectionDto.getChangedProductId());
				optionElement1.addAttribute("changedProductName", reverseCollectionDto.getChangedProductName());
				optionElement1.addAttribute("inventoryChangedDate", reverseCollectionDto.getInventoryChangedDate());
				optionElement1.addAttribute("ageing", reverseCollectionDto.getAgeing());
				optionElement1.addAttribute("customerId", reverseCollectionDto.getCustomerId());
				//logger.info("Action ..getReqId "+reverseCollectionDto.getReqId());
				optionElement1.addAttribute("reqId", reverseCollectionDto.getReqId());
				
				optionElement1.addAttribute("rejectedDC", reverseCollectionDto.getRejectedDC()); //Neetika
				if(!reverseCollectionDto.getCollectionId().equals("2"))
				{optionElement1.addAttribute("defectId", reverseCollectionDto.getDefectId());
				optionElement1.addAttribute("defectName", reverseCollectionDto.getDefectName());}
				optionElement1.addAttribute("CPEFlag", ""+countdistTransactionType);
				logger.info("rervse  ....  countdistTransactionType"+countdistTransactionType);
			}
			
			optionElement3 = root.addElement("optionflag");
			optionElement3.addAttribute("CPEFlag", ""+countdistTransactionType);
				logger.info("rervse  ....  else part countdistTransactionType"+countdistTransactionType);
				
			
			while (iterDefect.hasNext()) 
			{
				DpReverseInvetryChangeDTO reverseCollectionDto2 = (DpReverseInvetryChangeDTO) iterDefect.next();
				optionElement2 = root.addElement("optionDefect");
				optionElement2.addAttribute("text", reverseCollectionDto2.getDefectType());
				optionElement2.addAttribute("value", reverseCollectionDto2.getDefectId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
	
			return null;
	}
	
	
//	 Get Inventory Change List on collection Id.
	public ActionForward getDefectList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception  
	{
		DPReverseCollectionBean reverseCollectionBean = (DPReverseCollectionBean)form;
		logger.info("In method getDefectList---------------------------().");
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			//logger.info("In method getDefectList---------------------------().");
			String collectionId = request.getParameter("collectionId");
			Long accountId = sessionContext.getId();
			//logger.info("In method getInventoryList ().  account ID == "+accountId + "  and collectionId == "+collectionId );
			DPReverseCollectionService reverService = new DPReverseCollectionServiceImpl(); 
			List<DpReverseInvetryChangeDTO> collectionDefectList = reverService.getGridDefectList(collectionId);
			reverseCollectionBean.setListDefect(collectionDefectList);
			
//			 Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");

			Element optionElement2;
			Iterator iterDefect = collectionDefectList.iterator();
			
			while (iterDefect.hasNext()) 
			{
				DpReverseInvetryChangeDTO reverseCollectionDto2 = (DpReverseInvetryChangeDTO) iterDefect.next();
				optionElement2 = root.addElement("optionDefect");
				optionElement2.addAttribute("text", reverseCollectionDto2.getDefectType());
				optionElement2.addAttribute("value", reverseCollectionDto2.getDefectId());
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			//System.out.println("End of getDefectList()");
			// End for ajax
	
			return null;
	}
	
}