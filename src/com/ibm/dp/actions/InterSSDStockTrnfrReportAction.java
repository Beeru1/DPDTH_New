package com.ibm.dp.actions;

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

import com.ibm.dp.beans.ConsumptionPostingDetailReportBeans;
import com.ibm.dp.beans.InterSSDStockTrnfrReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.InterSSDStockTrnfrReportDto;
import com.ibm.dp.service.ConsumptionPostingDetailReportService;
import com.ibm.dp.service.InterSSDStockTrnfrReportService;
import com.ibm.dp.service.impl.ConsumptionPostingDetailReportServiceImpl;
import com.ibm.dp.service.impl.InterSSDStockTrnfrReportServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class InterSSDStockTrnfrReportAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(InterSSDStockTrnfrReportAction.class.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String SUCCESS_EXCEL = "successExcel";

	public ActionForward initInterSSdReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		HttpSession session = request.getSession();
		logger.info("In InterSSDStockTrnfrReportAction() method.");
		String userId = (String) session.getAttribute("userId");
		
		InterSSDStockTrnfrReportBean reportBean = (InterSSDStockTrnfrReportBean) form;
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		DPMasterService dpms = new DPMasterServiceImpl();
		ArrayList ciList = new ArrayList();
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		try{
		logger.info("Login Users Circle id == "+String.valueOf(sessionContext.getCircleId()));
		ciList = dpms.getCircleID(userId);
		if(loginRole == 3 || loginRole == 4)
		{
			reportBean.setShowCircle("N");
		}else{
			reportBean.setShowCircle("A");
		}
		reportBean.setArrCIList(ciList);
		
		request.setAttribute("arrCIList",ciList);
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
			
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward getReportDataExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In getReportDataExcel() ");
		HttpSession session = request.getSession();
		try {
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			System.out.println("--------------getReportDataExcel---------------------------");
			// Get From date and TO date.
			String dateFromDt = request.getParameter("fromDate");
			String dateToDt = request.getParameter("toDate");
			String strCircleId =request.getParameter("circleid");
			String strLoginCircleID = String.valueOf(sessionContext.getCircleId());
			int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
			logger.info("In getReportDataExcel() dateFromDt == "+dateFromDt+"   dateToDt=="+dateToDt+"  strCircleId=="+strCircleId+"  loginRole=="+loginRole+" strLoginCircleID=="+strLoginCircleID);
			InterSSDStockTrnfrReportBean reportBean = (InterSSDStockTrnfrReportBean) form;
			InterSSDStockTrnfrReportService reportService = new InterSSDStockTrnfrReportServiceImpl();
			List<InterSSDStockTrnfrReportDto> reportDataList =reportService.getReportExcel(strCircleId, dateFromDt,dateToDt, loginRole,strLoginCircleID);
			reportBean.setReportDataList(reportDataList);
			request.setAttribute("reportDataList",reportDataList);
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SUCCESS_EXCEL);
		}
		
		return mapping.findForward(SUCCESS_EXCEL);
	}


}
