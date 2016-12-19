package com.ibm.hbo.actions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.hbo.forms.DistributorHomeForm;
import com.ibm.hbo.services.DistributorHomeService;
import com.ibm.hbo.services.impl.DistributorHomeServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

public class DistributorHomeAction extends DispatchAction{
	
	public ActionForward distStockSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList arr = new ArrayList();
		DistributorHomeService ser = new DistributorHomeServiceImpl(); 
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		arr=ser.getDistributorStockSummary(userSessionContext.getId());
		DistributorHomeForm distHomeForm = (DistributorHomeForm)form;
		distHomeForm.setDistStockSumm(arr);
		return mapping.findForward("success");
	}
	
	public ActionForward prodDistStockSummary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArrayList arr = new ArrayList();
		DistributorHomeService ser = new DistributorHomeServiceImpl(); 
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		arr=ser.getDistributorProdSummary(userSessionContext.getId());
		DistributorHomeForm distHomeForm = (DistributorHomeForm)form;
		distHomeForm.setDistStockSumm(arr);
		return mapping.findForward("prodsummary");
	}


}
