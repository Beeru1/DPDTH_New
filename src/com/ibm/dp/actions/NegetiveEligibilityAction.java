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
import com.ibm.dp.beans.NegetiveEligibilityReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ActivationDetailReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.NegetiveEligibilityDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.service.ActivationDetailReportService;
import com.ibm.dp.service.NegetiveEligibiltyService;
import com.ibm.dp.service.impl.ActivationDetailServiceImpl;
import com.ibm.dp.service.impl.NegetiveEligibiltyServiceImpl;
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
public class NegetiveEligibilityAction extends DispatchAction 
{

	private static final String INIT_SUCCESS = "success";
	private static final String SUCCESS_EXCEL = "successExcel";


	private Logger logger = Logger.getLogger(NegetiveEligibilityAction.class.getName());

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {

		logger.info("In init method.");

		NegetiveEligibilityReportBean formBean = (NegetiveEligibilityReportBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		ActionErrors errors = new ActionErrors();	
		try {
			formBean.setCircleid(-1);
			List<SelectionCollection> ciList = new ArrayList<SelectionCollection>();			
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();										
			ciList = utilityAjaxService.getAllCircles();
			formBean.setArrCIList(ciList);			
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


	public ActionForward getNegetiveEligibilityExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		logger.info("In getNegetiveEligibilityExcel() ");
		try {
		
		session.setAttribute("NegEligibilityReport", "inprogress"); 
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);		

		NegetiveEligibilityReportBean formBean = (NegetiveEligibilityReportBean) form;			
		String circleIds = formBean.getHiddenCircleSelecIds();
		
		NegetiveEligibiltyService negetiveEligibiltyService = NegetiveEligibiltyServiceImpl.getInstance();
		List<NegetiveEligibilityDTO> reportStockDataList = negetiveEligibiltyService.getNegetiveEligibilityReport(circleIds);	

		formBean.setReportStockDataList(reportStockDataList);		
		} catch (RuntimeException e) {
			session.setAttribute("NegEligibilityReport", ""); 
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		return mapping.findForward(SUCCESS_EXCEL);
		}
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("NegEligibilityReport");
			
			String result = "";
			if(status != null)
				result = status;
			//logger.info("result:" + result);

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
			//logger.info("out now getReportDownloadStatus");

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	

}


