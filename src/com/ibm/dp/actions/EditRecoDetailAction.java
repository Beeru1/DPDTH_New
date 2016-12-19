package com.ibm.dp.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
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

import com.ibm.dp.beans.DPAlertConfigurationBean;
import com.ibm.dp.beans.DPSecFileUploadBean;
import com.ibm.dp.beans.EditRecoDetailBean;
import com.ibm.dp.beans.NSBulkUploadBean;
import com.ibm.dp.beans.WHdistmappbulkBean;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.service.DPAlertConfigurationService;
import com.ibm.dp.service.DPSecFileUploadService;
import com.ibm.dp.service.EditRecoDetailService;
import com.ibm.dp.service.NSBulkUploadService;
import com.ibm.dp.service.WHdistmappbulkService;
import com.ibm.dp.service.impl.DPAlertConfigurationServiceImpl;
import com.ibm.dp.service.impl.DPSecFileUploadServiceImpl;
import com.ibm.dp.service.impl.EditRecoDetailServiceImpl;
import com.ibm.dp.service.impl.NSBulkUploadServiceImpl;
import com.ibm.dp.service.impl.WHdistmappbulkServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.utils.PropertyReader;

public class EditRecoDetailAction  extends DispatchAction {

	private static Logger logger = Logger.getLogger(EditRecoDetailAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		EditRecoDetailBean formBean =(EditRecoDetailBean)form ;
		
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		//*******************selecting RecoPeriod********** 
		EditRecoDetailService editRecoDetailService = new EditRecoDetailServiceImpl();
		
		List<RecoPeriodDTO> recoPeriodList = editRecoDetailService.getrecoPeriodList();
		
		
		formBean.setRecoPeriodList(recoPeriodList);
		
		session.setAttribute("recoPeriodList", recoPeriodList);
		
		return mapping.findForward("initSuccess");
		}	
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init uploadExcel CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		EditRecoDetailBean editRecoDetailBean = null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			editRecoDetailBean = (EditRecoDetailBean) form;
			
			FormFile file = editRecoDetailBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			 
			InputStream myxls = file.getInputStream();
			 
			EditRecoDetailService bulkupload = new EditRecoDetailServiceImpl();
			logger.info("**  "+file.getFileName());
			
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				editRecoDetailBean.setStrmsg("Only XLS File is allowed here");
			}
			else
			{
				*/
				
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
					//logger.info("*********2..");
				editRecoDetailBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      editRecoDetailBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 editRecoDetailBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				List list = bulkupload.validateExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				RecoPeriodDTO recoPeriodDTO = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						recoPeriodDTO = (RecoPeriodDTO) itr.next();
						logger.info("recoPeriodDTO.getErr_msg():::::"+recoPeriodDTO.getErr_msg());
						if(recoPeriodDTO.getErr_msg()!= null && !recoPeriodDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						}
						
					}
					if(checkValidate)
					{
						editRecoDetailBean.setError_flag("false");
						logger.info("***File is correct. Now goes for DB transaction****");
						bulkupload.updateRecoPeriod(list, editRecoDetailBean.getRecoPeriodId(), editRecoDetailBean.getGracePeriod());
					}
					else
					{
						logger.info("Error in File:::"+list.size());
						errors.add("errors",new ActionError(recoPeriodDTO.getErr_msg()));
						saveErrors(request, errors);
						editRecoDetailBean.setError_flag("true");
						editRecoDetailBean.setError_file(list);
						editRecoDetailBean.setStrmsg("");
					}
				}
			}
			List<RecoPeriodDTO> recoPeriodList = bulkupload.getrecoPeriodList();
			editRecoDetailBean.setRecoPeriodId(-1);
			editRecoDetailBean.setRecoPeriodList(recoPeriodList);
			editRecoDetailBean.setGracePeriod(0);
			session.setAttribute("recoPeriodList", recoPeriodList);
		}
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			editRecoDetailBean.setStrmsg("Exception in uploading data");
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
		//********************showing error in  file*********************
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		EditRecoDetailBean editRecoDetailBean = (EditRecoDetailBean) form;
		logger.info("*******************"+editRecoDetailBean.getError_file().size());
		logger.info("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	
	//*************Showing File Format that has to be uploaded***********
	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=DistributorRecoUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/DistributorRecoUploadFormat.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DistributorRecoUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DistributorPincodeMapping.xls");
			//in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "DistributorRecoUploadFormat.xls";
			in = new FileInputStream(fpath);
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}			
		} catch (IOException e) {
			throw e;
		} finally {
			in.close();
			out.flush();
			out.close();
		}	
		return null;
		
	}
	}

	
