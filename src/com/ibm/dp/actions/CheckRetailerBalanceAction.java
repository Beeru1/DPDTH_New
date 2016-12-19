package com.ibm.dp.actions;

import java.io.InputStream;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.AvStockUploadFormBean;
import com.ibm.dp.beans.CheckRetailerBalanceBean;
import com.ibm.dp.beans.ConsumptionPostingDetailReportBeans;
import com.ibm.dp.beans.DPCreateAccountFormBean;
import com.ibm.dp.beans.DPInterSSDStockTransferReportFormBean;
import com.ibm.dp.beans.DistRecoBean;
import com.ibm.dp.dto.CheckRetailerBalanceDto;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.ConsumptionPostingDetailReportDto;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.InterSSDReportDTO;
//import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.dao.*;
import com.ibm.dp.dao.impl.*;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.ConsumptionPostingDetailReportService;
import com.ibm.dp.service.DPInterSSDStockTransferReportService;
import com.ibm.dp.service.DistAvStockUploadService;
//import com.ibm.dp.service.CloseInactivePartnersService;
//import com.ibm.dp.service.DpInvoiceUploadService;
import com.ibm.dp.service.impl.CloseInactivePartnersServiceImpl;
import com.ibm.dp.service.impl.ConsumptionPostingDetailReportServiceImpl;
import com.ibm.dp.service.impl.DPInterSSDStockTransferReporServiceImpl;
import com.ibm.dp.service.impl.DistRecoServiceImpl;
import com.ibm.dp.service.impl.DpInvoiceUploadServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;

public class CheckRetailerBalanceAction extends DispatchAction{

	private static Logger logger = Logger.getLogger(CheckRetailerBalanceAction.class.getName());
	private static final String SUCCESS_EXCEL = "successExcel";
	
	 public ActionForward init(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception
		{
		 
		 logger.info(" Starting init... ");
			ActionErrors errors = new ActionErrors();
			CheckRetailerBalanceBean bean = (CheckRetailerBalanceBean) form;
			try {
				/* Logged in user information from session */
				// get login ID from session
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session
						.getAttribute(Constants.AUTHENTICATED_USER);
				AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				long loginUserId = sessionContext.getId();
				logger.info("Login Id:"+loginUserId);
				bean.setDistributorid(loginUserId);
				
				CheckRetailerBalanceDao dao= new CheckRetailerBalanceDaoImpl();
				ArrayList<CheckRetailerBalanceDto> records = new ArrayList<CheckRetailerBalanceDto>();
		        records = dao.getDetails( loginUserId);
		        
		        bean.setDisplayDetails(records);
		        
		        ArrayList<CheckRetailerBalanceDto> al= bean.getDisplayDetails();
		             
		        for(int i = 0; i<al.size();i++){
			        logger.info("Arraylist bean "+i+" DATA  --  "+al.get(i).getRetailername());
			        }
		        
		        
		        for(int i = 0; i<records.size();i++){
		        logger.info("Record number "+i+" DATA  --  "+records.get(i).getRetailername());
		        }
		       // request.setAttribute("retDetails", records);
		        request.setAttribute("retDetails", al);
			}
			catch(Exception e)
			{
				
				e.printStackTrace();
				
			}
		 
		 
		 
		 return mapping.findForward("checkretailerbalance");
		}

	 public ActionForward exporttoExcel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)throws Exception 
		{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of CheckRetailerBalanceAction");
		CheckRetailerBalanceBean bean = (CheckRetailerBalanceBean) form;
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);	
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			logger.info("Login Id:"+loginUserId);
			bean.setDistributorid(loginUserId);
			
			CheckRetailerBalanceDao dao= new CheckRetailerBalanceDaoImpl();
			ArrayList<CheckRetailerBalanceDto> records = new ArrayList<CheckRetailerBalanceDto>();
	        records = dao.getDetails(loginUserId);
	        
	        bean.setDisplayDetails(records);
	        
	        ArrayList<CheckRetailerBalanceDto> al= bean.getDisplayDetails();
	             
	        for(int i = 0; i<al.size();i++){
		        logger.info("Arraylist bean "+i+" DATA  --  "+al.get(i).getRetailername());
		        }
	        
	        
	        for(int i = 0; i<records.size();i++){
	        logger.info("Record number "+i+" DATA  --  "+records.get(i).getRetailername());
	        }
	       			
		    bean.setDisplayDetails(al);
			logger.info("Downloaded excel will contain"+":"+al.size()+"records...*+*+*+*+");
			request.setAttribute("displayDetails", al);
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
