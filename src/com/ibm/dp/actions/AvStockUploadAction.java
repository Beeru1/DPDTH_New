package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.struts.upload.FormFile;
import com.ibm.dp.beans.AvStockUploadFormBean;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.service.AvStockUploadService;
import com.ibm.dp.service.impl.AvStockUploadServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class AvStockUploadAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(AvStockUploadAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init called from avStockUploadAction.");
		ActionErrors errors = new ActionErrors();
		try 
		{	
			/* Logged in user information from session */
			HttpSession session = request.getSession();			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_STB_FLUSHOUT)) 
			{
				logger.info(" user not auth to perform this ROLE_STB_FLUSHOUT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			logger.info(loginUserId);			
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
		logger.info("------- uploadExcel called from AvStockUploadAction.");
		ActionErrors errors = new ActionErrors();
		
		HttpSession session = request.getSession();			
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AvStockUploadFormBean avStockUploadFormBean = (AvStockUploadFormBean) form;
		try		
		{
			AvStockUploadService avStockUploadService = new AvStockUploadServiceImpl();
			FormFile file = avStockUploadFormBean.getStockList();
			
			
			
			//=======
			
			logger.info("**"+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
			
			String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
			//String special = "!@#$%^*()'\\";
			boolean found=false;
			boolean found2=false;
						
			
			if(!arr.equalsIgnoreCase("csv"))  //changed by neetika while testing.. it must not be workin in production after release 3 till now not reported
			{
				avStockUploadFormBean.setStrmsg("Only CSV File is allowed here");
				request.setAttribute("msg","Only CSV File is allowed here");
				found=true;
			}
			 if(found==false)
			{
			for (int i=0; i<special.length(); i++) 
			{
			   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
			     
				   avStockUploadFormBean.setStrmsg("Special Characters are not allowed in File Name");
				   request.setAttribute("msg","Special Characters are not allowed in File Name");
				   found2=true;
			      	break;
			   }
			}
			if ((file.getFileName().toString()).indexOf("..")>-1)
			{
				 found2=true;	
				 avStockUploadFormBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				 request.setAttribute("msg","Special Characters are not allowed in File Name");
			}
			}
			 if(found2==false && found==false)
			{
			

			/*if(!arr.equalsIgnoreCase("xls"))
			{
				avStockUploadFormBean.setStrmsg("Only XLS File is allowed here");
				request.setAttribute("msg","Only XLS File is allowed here");
			}else
			{*/
			//==========
			InputStream myxls = file.getInputStream();
			List list = avStockUploadService.uploadExcel(file);
			
			DPPoAcceptanceBulkDTO dpPoAcceptanceBulkDTO = null;
			boolean checkValidate = true;
			if(list.size() > 0)
			{
				Iterator itt = list.iterator();
				while(itt.hasNext())
				{
					dpPoAcceptanceBulkDTO = (DPPoAcceptanceBulkDTO) itt.next();
					logger.info("getErr_msg === "+dpPoAcceptanceBulkDTO.getErr_msg());
					if(dpPoAcceptanceBulkDTO.getErr_msg()!= null && !dpPoAcceptanceBulkDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
					{
						logger.info("Error found in file");
						
						checkValidate = false;
						break;
					} 
				}
				
				logger.info("checkValidate  ::  "+checkValidate);
				
				if(checkValidate)
				{
					logger.info("validate Action  Success  ::"+checkValidate);
					String strMsg=avStockUploadService.insertAVStockinDP(list);
					request.setAttribute("msg",strMsg);
					logger.info("GetSuccess_msg==="+strMsg);
				}
				else
				{
					session.removeAttribute("error_file");
					session.setAttribute("error_file",list);
					avStockUploadFormBean.setError_flag("true");
					//avStockUploadFormBean.setError_file(list);
					return mapping.findForward(INIT_UPLOAD_SUCCESS);	
				}
			}
		}
		}
		catch(Exception e) 
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);		
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		AvStockUploadFormBean avStockUploadFormBean = (AvStockUploadFormBean) form;
		return mapping.findForward("errorFile");
	}
}
