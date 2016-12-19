package com.ibm.reports.actions;

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
import org.dom4j.io.XMLWriter;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.beans.GenericReportsFormBean;
import com.ibm.reports.beans.UploadTrailReportsBean;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.UploadTrailReportDTO;
import com.ibm.reports.dto.UploadTrailReportsOutputDTO;
import com.ibm.reports.dto.UploadTrailReportsParameterDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.UploadTrailReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.reports.service.impl.UploadTrailReportsServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.ussdactivationweb.dto.ReportDTO;

public class UploadTrailReportsAction extends DispatchAction {
	private static Logger logger = Logger.getLogger(UploadTrailReportsAction.class.getName());
	private static final String INIT_SUCCESS = "initSuccess";
	private static final String INIT_EXCEL = "initExcel";
	private static final String INIT_EXCEL_RECO = "initExcel_reco";
	private static final String ERROR = "error";
	private static final String REPORT_ID = "reportId";
	private static final String OUTPUT = "output";
	
	public ActionForward initReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// logger.info("**********inside GenericReports Action ********");
		ActionErrors errors = new ActionErrors();
		//GenericReportsService reportService = GenericReportsServiceImpl.getInstance();
		UploadTrailReportsService reportService = UploadTrailReportsServiceImpl.getInstance();
		try
		{
			HttpSession session = request.getSession();
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			logger.info("-----------------init ACTION CALLED-----------------");
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_STB_IN_BULK )))
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				//saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			} 
			
					//GenericReportsFormBean genericReportsFormBean = (GenericReportsFormBean) form;
			UploadTrailReportsBean uploadTrailReportBean = (UploadTrailReportsBean) form;
			List<UploadTrailReportDTO> uploadTypeList = reportService.getUploadTypeList();
			uploadTrailReportBean.setUploadTypeList(uploadTypeList);
			
		}
		catch (Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			//saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);
	}

	
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws DPServiceException
	{
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("Get id from sess " + sessionContext.getId());
		long id = sessionContext.getId();
		ActionErrors errors = new ActionErrors();
		UploadTrailReportsBean reportBean = (UploadTrailReportsBean) form;
		try
		{
			String uploadType = reportBean.getUploadType();
			String fromDate = reportBean.getFromDate();
			String toDate = reportBean.getToDate();
			
			UploadTrailReportsParameterDTO reportDTO = new UploadTrailReportsParameterDTO();  
			reportDTO.setUploadType(uploadType);
			reportDTO.setFromDate(fromDate);
			reportDTO.setToDate(toDate);
			
			session.setAttribute("UploadTrailReport", "inprogress");
			request.setAttribute("UploadTrailReport", "");

			UploadTrailReportsService trailService = UploadTrailReportsServiceImpl.getInstance();
			UploadTrailReportsOutputDTO trailOutputDTO = trailService.exportToExcel(reportDTO);


			reportBean.setHeaders(trailOutputDTO.getHeaders());
			reportBean.setOutput(trailOutputDTO.getOutput());
			
			request.setAttribute(OUTPUT, trailOutputDTO.getOutput());
			request.setAttribute("header", trailOutputDTO.getHeaders());
			

		}
		catch (DAOException se)
		{
			logger.error("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		catch (DPServiceException se)
		{
			logger.info("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		catch (Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}

		
			return mapping.findForward(INIT_EXCEL);
	}
	
	
	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			
			String status = (String) session.getAttribute("UploadTrailReport");
			System.out.println("status value in reco details is "+status);
			String result = "";
			if(status != null)
				result = status;
			
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
		

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	
	

}
