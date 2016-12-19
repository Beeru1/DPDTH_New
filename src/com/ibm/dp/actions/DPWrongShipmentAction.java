package com.ibm.dp.actions;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPWrongShipmentBean;
import com.ibm.dp.beans.InventoryStatusBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.WrongShipmentDTO;
import com.ibm.dp.service.DPWrongShipmentService;
import com.ibm.dp.service.impl.DPWrongShipmentServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
/**
 * 
 *	Action class for Reports with External System.
 *
 */
public class DPWrongShipmentAction extends DispatchAction 
{
private static final String INIT_SUCCESS = "initsuccess";
private static final String SUCCESS = "success";
private static final String SUCCESS_EXCEL = "successExcel";

/*
 * Method for getting stored password for logged-in user group
 */
  
private Logger logger = Logger.getLogger(DPProductStatusReportAction.class.getName());

public ActionForward init(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	
	
	logger.info("***************************** init() method.*****************************");
	
	// Get account Id form session.
	DPWrongShipmentBean wrongShipBean = (DPWrongShipmentBean)form; 
	HttpSession session = request.getSession();
	ActionErrors errors = new ActionErrors();
	try {
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
			
		List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>(); 
		List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();	
		DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
		List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId , "REJECT");
			
		// Set temp DTO.
		WrongShipmentDTO wrongShipmentTemp = new WrongShipmentDTO(); 
		shortShipmentSerialsList.add(wrongShipmentTemp);
		assignedSerialsSerialsList.add(wrongShipmentTemp);
		wrongShipBean.setHidtkprint("");
		wrongShipBean.setShortShipmentSerialsList(shortShipmentSerialsList);
		wrongShipBean.setAssignedSerialsSerialsList(assignedSerialsSerialsList);
		wrongShipBean.setDcNoList(dcNoList);
		wrongShipBean.setInvoiceNo("");
		request.setAttribute("dcNoList", dcNoList);
		wrongShipBean.setTransMessage("");
		request.setAttribute("transMessage", "");
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(SUCCESS);
	}
	
	return mapping.findForward(INIT_SUCCESS);
}


// Get all serials, products and Invoices, Dc No. 
public ActionForward getAllSerialNo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
{
	logger.info("In getAllSerialNo() ");
//	 Get account Id form session.
	DPWrongShipmentBean wrongShipBean = (DPWrongShipmentBean)form; 
	HttpSession session = request.getSession();
	String dcNo = request.getParameter("deliveryChallanNo");
	
	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
	Integer intCircleID = sessionContext.getCircleId();
	List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>(); 
	List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();
		
	// Get all 
	DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl();
	//String dcNo = wrongShipment.getDeliveryChallanNo(dcNo);
	String invoiceNo = wrongShipment.getInvoiceNoOfDCNO(dcNo);
	List<WrongShipmentDTO> serialsNoList = wrongShipment.getAllSerialNos(invoiceNo, dcNo, intCircleID);
	List<WrongShipmentDTO> productList  = wrongShipment.getAllProducts(invoiceNo, dcNo);
			
	wrongShipBean.setShortShipmentSerialsList(shortShipmentSerialsList);
	wrongShipBean.setAssignedSerialsSerialsList(assignedSerialsSerialsList);
	wrongShipBean.setHidtkprint("");
	//wrongShipBean.setInvoiceList(invoiceList);
	//request.setAttribute("invoiceList", invoiceList);
		
	// Ajax start
	// Get all serials no of given Invoice NO.
	Document document = DocumentHelper.createDocument();
	Element root = document.addElement("options");
	Element optionElement;
		
	Iterator iter = serialsNoList.iterator();
	while (iter.hasNext()) 
	{
		WrongShipmentDTO wrongShipmentDTO = (WrongShipmentDTO) iter.next();
		optionElement = root.addElement("option");
		optionElement.addAttribute("text", wrongShipmentDTO.getSerialID());
		optionElement.addAttribute("value", wrongShipmentDTO.getSerialID());
	}
		
	// Set all product Id and No.
	Element optionElementProduct;		
	Iterator iterProduct = productList.iterator();
	while (iterProduct.hasNext()) 
	{
		WrongShipmentDTO wrongShipmentDTO = (WrongShipmentDTO) iterProduct.next();
		optionElementProduct = root.addElement("optionProductList");
		optionElementProduct.addAttribute("text", wrongShipmentDTO.getProductName());
		optionElementProduct.addAttribute("value", wrongShipmentDTO.getProductId());
	}
		
	//Set DC No.
	Element optionElementText;
	optionElementText = root.addElement("optionInvoiceNo");
	optionElementText.addAttribute("text", invoiceNo);
	optionElementText.addAttribute("value", invoiceNo);
	
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


public ActionForward submitWrongShipmentDetails(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{
	logger.info("***************************** submitWrongShipmentDetails() method.*****************************");
	
	// Get account Id form session.
	DPWrongShipmentBean wrongShipBean = (DPWrongShipmentBean)form; 
	HttpSession session = request.getSession();
	ActionErrors errors = new ActionErrors();
	try {
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		Long distId = sessionContext.getId();
		
		List<WrongShipmentDTO> shortShipmentSerialsList = new ArrayList<WrongShipmentDTO>(); 
		List<WrongShipmentDTO> assignedSerialsSerialsList = new ArrayList<WrongShipmentDTO>();	
		String[] availableSerialsArray = request.getParameterValues("shortShipmentSerialsBox");
		String[] shortSerialsArray = request.getParameterValues("wrongSerialsNos");
		String[] extraSerialsArray = request.getParameterValues("extraSerialsNoBox");
		String invoiceNo = request.getParameter("invoiceNo");
		String deliveryChallanNo = request.getParameter("deliveryChallanNo");
			
		DPWrongShipmentService wrongShipment = new DPWrongShipmentServiceImpl(); 
		boolean flag = wrongShipment.submitWrongShipmentDetails(availableSerialsArray, shortSerialsArray, extraSerialsArray, invoiceNo, deliveryChallanNo,String.valueOf(distId));
		List<WrongShipmentDTO> dcNoList = wrongShipment.getListOfRejectedDCNo(distId , "REJECT");
		wrongShipBean.setHidtkprint("Yes");
		// Set temp DTO.
		WrongShipmentDTO wrongShipmentTemp = new WrongShipmentDTO(); 
		shortShipmentSerialsList.add(wrongShipmentTemp);
		assignedSerialsSerialsList.add(wrongShipmentTemp);
		wrongShipBean.setShortShipmentSerialsList(shortShipmentSerialsList);
		wrongShipBean.setAssignedSerialsSerialsList(assignedSerialsSerialsList);
		wrongShipBean.setDcNoList(dcNoList);
		wrongShipBean.setInvoiceNo(invoiceNo);
		request.setAttribute("dcNoList", dcNoList);
		request.setAttribute("invoiceNo", invoiceNo);
		session.setAttribute("totalSerialNumbers", availableSerialsArray.length+extraSerialsArray.length);
		if(flag)
		{
			wrongShipBean.setTransMessage(Constants.TRANS_MESSAGE);
			request.setAttribute("transMessage", Constants.TRANS_MESSAGE);
		}
		else
		{
			wrongShipBean.setTransMessage("");
			request.setAttribute("transMessage", "");
		}
	} catch (RuntimeException e) {
		e.printStackTrace();
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		errors.add("errors",new ActionError(e.getMessage()));
		saveErrors(request, errors);
		return mapping.findForward(INIT_SUCCESS);
	}
	
	return mapping.findForward(INIT_SUCCESS);
}


public ActionForward checkWrongInventory(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)	throws Exception 
{ 	 
	System.out.println("************checkWrongInventory******************");
	String chackInv ="";
	String extraSerialNo = request.getParameter("extraSerialNo");
	String productID = request.getParameter("productID");
	UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
	String distId = sessionContext.getId()+"";
	DPWrongShipmentServiceImpl wrongShipment = new DPWrongShipmentServiceImpl(); 
	
	try
	{ 
		String deliveryChallanNo = request.getParameter("deliveryChallanNo");
		//chackInv = wrongShipment.checkWrongInventory(extraSerialNo , productID ,  distId , deliveryChallanNo );
		chackInv = "true";
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		out.write(""+chackInv);
		out.flush();
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		return null;
}

}//END


