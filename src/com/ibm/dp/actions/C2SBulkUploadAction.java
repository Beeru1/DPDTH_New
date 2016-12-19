package com.ibm.dp.actions;

import java.io.InputStream;

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
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.C2SBulkUploadBean;
import com.ibm.dp.service.C2SBulkUploadService;
import com.ibm.dp.service.impl.C2SBulkUploadServiceImpl;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class C2SBulkUploadAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(C2SBulkUploadAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init uploadExcel CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		C2SBulkUploadBean c2SBulkUploadBean = null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			
			c2SBulkUploadBean = (C2SBulkUploadBean) form;
			
			FormFile file = c2SBulkUploadBean.getStbsrno();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			 
			InputStream myxls = file.getInputStream();
			 
			C2SBulkUploadService bulkupload = new C2SBulkUploadServiceImpl();
			logger.info("**"+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls")|| !arr.equalsIgnoreCase("xlsx") )
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				c2SBulkUploadBean.setStrmsg("Only XLS File is allowed here");
			}*/
			/*else if(file.getFileSize() > com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE)
			{
				c2SBulkUploadBean.setStrmsg("File size exceed to "+(com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE/1000)+" KB");
			}*/
			
			String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
			//String special = "!@#$%^*()'\\";
			boolean found=false;
			boolean found2=false;
						
			
			if(!arr.equalsIgnoreCase("xls"))
			{
				//logger.info("*********2..");
				c2SBulkUploadBean.setStrmsg("Only XLS File is allowed here");
			found=true;
			}
			 if(found==false)
			{
			for (int i=0; i<special.length(); i++) 
			{
			   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
			     
				   c2SBulkUploadBean.setStrmsg("Special Characters are not allowed in File Name");
			      found2=true;
			      break;
			   }
			}
			if ((file.getFileName().toString()).indexOf("..")>-1)
			{
				 found2=true;	
				 c2SBulkUploadBean.setStrmsg("2 consecutive dots are not allowed in File Name");
			}
			}
			 if(found2==false && found==false)
			{
			/*
			
			else
			{*/
				String str = bulkupload.uploadExcel(myxls);
				c2SBulkUploadBean.setStrmsg(str);
			}
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	
	
	
}
