package com.ibm.dp.actions;

import java.io.File;
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
import com.ibm.dp.beans.CloseInactivePartnersBean;
import com.ibm.dp.beans.DistRecoBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DpProductTypeMasterDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.service.DistAvStockUploadService;
import com.ibm.dp.service.CloseInactivePartnersService;
import com.ibm.dp.service.DpInvoiceUploadService;
import com.ibm.dp.service.impl.CloseInactivePartnersServiceImpl;
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

public class CloseInactivePartnersAction  extends DispatchAction{

    private static Logger logger = Logger.getLogger(CloseInactivePartnersAction.class.getName());
	
    private static final String INIT_SUCCESS = "init";
    private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
    
    public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	HttpSession session = request.getSession();
		logger.info("***************************** init() method of Close Inactive Partners Called*****************************");
		CloseInactivePartnersBean formBean =(CloseInactivePartnersBean)form ;
		
		String dLink="";
		if(session.getAttribute("disabledLink")!=null)
		{
			dLink = session.getAttribute("disabledLink").toString();
			request.setAttribute("disabledLink", dLink);
			//session.setAttribute("disabledLink", null);
		}
		
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		logger.info("OLM ID -==== "+sessionContext.getLoginName());
		
		return mapping.findForward("uploadExlFileWindow");
  	
    	
	}
    public ActionForward uploadClosingInactivePartnersDetailsXls(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception
			{
		ActionErrors errors = new ActionErrors();
		CloseInactivePartnersBean formBean  = null;
		
		PrintWriter out=null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			Long distId = sessionContext.getId();
			formBean =(CloseInactivePartnersBean)form ;
			FormFile file = formBean.getUploadedFile();
			String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			//DistRecoService bulkupload = new DistRecoServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			logger.info("***** arr :"+arr);
			CloseInactivePartnersBean bean= new CloseInactivePartnersBean();
			ArrayList<CloseInactivePartnersDto> results= new ArrayList<CloseInactivePartnersDto>();
			String absFile="";
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				logger.info("Special from resource reader      ******         "+special);
				boolean found=false;
				boolean found2=false;
				logger.info("***** arr1 :"+arr);
				
				
			boolean isXls=file.getFileName().matches("(^[^\\s]+(.*)(\\.(xls))$)"); // isXlsx=formBean.getFile().getFileName().matches("(^[^\\s]+(.*)(\\.(xlsx))$)");	
				logger.info("hddjshih"+isXls);
				//logger.info("hddjshih618999"+file.getFile().getFileName());
			if(!arr.equalsIgnoreCase("xls") && !arr.equalsIgnoreCase("xlsx"))
				{
					formBean.setStrmsg("Only Excel File is allowed here.");
					found=true;
				}
				//logger.info("***** arr12 :"+arr);
				 if(found==false)
				{
					 //logger.info("***** arr13 :"+arr);
				/*for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
					  // logger.info("***** arr133 :"+arr);
					   formBean.setStrmsg("Special Characters are not allowed in File Name.");
				      found2=true;
				      break;
				   }
				}*/
				
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 formBean.setStrmsg("Two consecutive dots are not allowed in File Name.");
				}
				}
				
				 logger.info("File uploaded successfully"+path);
				
				 				 
				 try{
					 
					    CloseInactivePartnersService cipService=new CloseInactivePartnersServiceImpl();
					
				/////////////////////////////////////////////	    
					    try{
							 
							 path=cipService.serialDataxls(formBean.getUploadedFile());
							}catch(Exception excep){
								
								logger.info("Exception in uploading file");
								throw new Exception();
								
							}
					    
				////////////////////////////////////////////////	    
					    
						if(isXls){
						logger.info("File Type is xls:");
						results=cipService.invFileUploadls(path);
						for(int i = 0; i<results.size();i++){
					        logger.info("Action class: "+i+" DATA  --  "+results.get(i).getOlmid());
					      
					        }
						bean.setDisplayDetails(results);
						logger.info("Updated data of file into DB ");
					
						/*String msg=cipService.invoiceUploaded(absFile, loginId);
						if(msg.equals("SUCCESS")){
							logger.info("DB entry made for file upload");
						}
						
						}*/
						}
						/*else if(isXlsx){
						logger.info("File Type is xlsx:");	
						cipService.invFileUploadsx(absFile);
						String msg=invService.invoiceUploaded(absFile, loginId);
						
						if(msg.equals("SUCCESS")){
							logger.info("DB entry made for file upload");
						}*/
						request.setAttribute("details", results.get(0));
		
						}catch(Exception excep){
							excep.printStackTrace();
							logger.info("Cause- "+ excep.getCause());
							//logger.info("")
							logger.info("Exception in storing file info in DB");
							logger.info("Exception ::::->"+excep.getMessage());
							
						}
						
						ActionMessages messages =new ActionMessages();
						messages.add("message", new ActionMessage("Invoice.upload.success"));
						saveMessages(request, messages);
						
						
						
						formBean.setFile(null);
						
						//return mapping.findForward(jspStat);
						}
			
		catch(Exception e)
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			//errors.add("errors",new ActionError(e.getMessage()));
			//saveErrors(request, errors);
		}
		 		//return mapping.findForward("successuploadExl");
		return mapping.findForward("uploadExlFileWindow");
		 	}





    
    
    
    

    /*public ActionForward viewDistributors(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("***************************** viewDistributors() method. Called*****************************");
		HttpSession session = request.getSession();
		CloseInactivePartnersBean formBean =(CloseInactivePartnersBean)form ;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String certId=null;
		Long distId = sessionContext.getId();
		
		
		CloseInactivePartnersService cipService =new  CloseInactivePartnersServiceImpl();
		CloseInactivePartnersDto dto = new CloseInactivePartnersDto();
		
		
		String dLink="";
		if(session.getAttribute("disabledLink")!=null)
		{
		dLink = session.getAttribute("disabledLink").toString();
		logger.info("In viewRecoProducts method in if cond of disabledLink == "+dLink);
		request.setAttribute("disabledLink", dLink);
		//session.setAttribute("disabledLink", null);
		}
		
		return mapping.findForward("uploadExlFileWindow");
	}*/
  
    
    
    
}
