package com.ibm.virtualization.recharge.actions;

import java.util.ArrayList;
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

import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.recharge.beans.MinReorderFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.MinmreorderService;
import com.ibm.virtualization.recharge.service.impl.MinmreorderServiceImpl;


public class MinReorderAction  extends DispatchAction
{
	/*
	 * Logger for the class.
	 */
	private static final Logger logger;

	static {
		logger = Logger.getLogger(MinReorderAction.class);
	}
		
	public ActionForward getValues(ActionMapping mapping,ActionForm form, 
			HttpServletRequest request,HttpServletResponse response) 
	 throws VirtualizationServiceException, NumberFormatException, DAOException
	 {	
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);

		
		MinReorderFormBean minreorder = (MinReorderFormBean) form;
		minreorder.reset();
		String userId = sessionContext.getId()+"";
		
		
		MinmreorderService dpminreordr = new MinmreorderServiceImpl();
		ArrayList arrPRList = new ArrayList(); // for Product List 
		ArrayList arrDSList = new ArrayList(); // for distributor List
		try {
			arrPRList = dpminreordr.getProductList(userId);
			MinReorderFormBean minodr = (MinReorderFormBean) form;			
			minodr.setArrPRList(arrPRList);		
			HBOUser hboUser = new HBOUser(sessionContext);
			// Fetch Distributor for given Circle
			arrDSList = dpminreordr.getDistributor(hboUser.getCircleId());
			MinReorderFormBean minodr1 = (MinReorderFormBean) form;
			
			minodr1.setArrDSList(arrDSList);
			
	
		} catch (Exception e) {
			log.error("ERROR IN FETCHING  LIST [init] function");
			
		}
		saveToken(request);
		forward = mapping.findForward("success");
		return (forward);
		
		
	}
	public ActionForward Insertorder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws VirtualizationServiceException, NumberFormatException, DAOException {
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
		.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		String userId = sessionContext.getId()+"";
		MinReorderFormBean minodr = (MinReorderFormBean) form;
		MinmreorderService dpservice = new MinmreorderServiceImpl();
		ArrayList arrPRList = new ArrayList(); // for Product List 
		ArrayList arrDSList = new ArrayList(); // for distributor List
		MinmreorderService dpminreordr = new MinmreorderServiceImpl();
		HBOUser hboUser = new HBOUser(sessionContext);
		try {
			minodr.setCreatedby(""+sessionContext.getId());				
			String message = dpservice.insert(minodr);
			//= dpservice.insert(minodr);
			 errors.add("message.reorder", new ActionError(
			"message.minmreorder.insert_success"));
			 saveErrors(request, errors);
			forward = mapping.findForward(message);			
			arrPRList = dpminreordr.getProductList(userId);			
			minodr.setArrPRList(arrPRList);			
			arrDSList = dpminreordr.getDistributor(hboUser.getCircleId());			
			minodr.setArrDSList(arrDSList);			
		} catch (VirtualizationServiceException e) {
			errors.add("errors.reorder", new ActionError(
			"message.minmreorder.insert_error"));
			 saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward("success1");
	}
}