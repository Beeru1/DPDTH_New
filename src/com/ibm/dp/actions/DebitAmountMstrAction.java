package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.dp.beans.DebitAmountMstrFormBean;
import com.ibm.dp.beans.RecoSummaryFormBean;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.DebitAmountMasterService;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dp.service.RecoPeriodConfService;
import com.ibm.dp.service.impl.DebitAmountMasterServiceImpl;
import com.ibm.dp.service.impl.RecoDetailReportServiceImpl;
import com.ibm.dp.service.impl.RecoPeriodConfServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class DebitAmountMstrAction extends DispatchAction {
private static Logger logger = Logger.getLogger(DebitAmountMstrAction.class.getName());

	private final static String SUCCESS = "initSuccess";
	private final static String LOGINSUCCESS = "loginSuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info("inside debitamountmaster action:::");

		HttpSession session = request.getSession();
		DebitAmountMstrFormBean debitAmountMstrFormBean = (DebitAmountMstrFormBean)form;
        if(debitAmountMstrFormBean != null)
        {
        	debitAmountMstrFormBean.reset(mapping, request);
        }
        UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");

        ActionErrors errors = new ActionErrors();
        
        DebitAmountMasterService debitAmountMasterService= new DebitAmountMasterServiceImpl();
		
		List<DebitAmountMstrFormBean> listAllProducts = new ArrayList<DebitAmountMstrFormBean>();
		
		listAllProducts=debitAmountMasterService.getProductList();
		
		logger.info("listAllProducts:::"+listAllProducts);
		
		debitAmountMstrFormBean.setProductList(listAllProducts);
		
		return mapping.findForward(SUCCESS);
	}
		
	public ActionForward insertAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		boolean booMessage;
		String message;
		String amount;
		String prodType;
		try{
		HttpSession session = request.getSession();
		
		DebitAmountMstrFormBean debitAmountMstrFormBean = (DebitAmountMstrFormBean)form;
		
		//prodType=request.getParameter("productType");
		//amount=request.getParameter("amount");
		
		UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");
		
		//debitAmountMstrFormBean.setProductType(prodType);
		//debitAmountMstrFormBean.setAmount(amount);
		debitAmountMstrFormBean.setUpdatedBy(sessionContext.getLoginName());
		
		DebitAmountMasterService debitAmountMasterService= new DebitAmountMasterServiceImpl();
		
		
        List<DebitAmountMstrFormBean> listAllProducts = new ArrayList<DebitAmountMstrFormBean>();
		
		listAllProducts=debitAmountMasterService.getProductList();
		
		logger.info("listAllProducts:::"+listAllProducts);
		
		debitAmountMstrFormBean.setProductList(listAllProducts);
		
		booMessage=debitAmountMasterService.insertDebitAmount(debitAmountMstrFormBean);
		
		if(booMessage)
		{
			message="Amount Inserted Successfully";
		}
		else
		{
			message="Amount Not Inserted";
		}
		
		
		debitAmountMstrFormBean.setMessage(message);
		logger.info("::::::::::::::::::::::::::::::::::message::::"+message);
		
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
	}
		return mapping.findForward(SUCCESS);
	}
	
	
	public ActionForward getAmount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean booMessage;
		String message;
		String amount;
		String prodType;
		
		HttpSession session = request.getSession();
		
		DebitAmountMstrFormBean debitAmountMstrFormBean = (DebitAmountMstrFormBean)form;
		
		prodType=request.getParameter("productType");
		amount=request.getParameter("amount");
		
		UserSessionContext sessionContext = (UserSessionContext)session.getAttribute("USER_INFO");
		
		debitAmountMstrFormBean.setProductType(prodType);
		
		DebitAmountMasterService debitAmountMasterService= new DebitAmountMasterServiceImpl();
		
		amount=debitAmountMasterService.getDebitAmount(debitAmountMstrFormBean);
		
		
		PrintWriter out=response.getWriter();
		out.println(amount);
		debitAmountMstrFormBean.setAmount(amount);

		return null;
	}
	
	
}
