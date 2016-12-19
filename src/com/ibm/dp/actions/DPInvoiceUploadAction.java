package com.ibm.dp.actions;

import java.io.File;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.log4j.Logger;

import com.ibm.dp.beans.DpInvoiceForm;
import com.ibm.dp.dto.ExcelError;
import com.ibm.dp.service.DpInvoiceUploadService;
import com.ibm.dp.service.InvUploadService;
import com.ibm.dp.service.impl.DpInvoiceUploadServiceImpl;
import com.ibm.dp.service.impl.InvUploadServiceImpl;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;

public class DPInvoiceUploadAction extends DispatchAction {
	
	
	public static Logger logger=Logger.getLogger(DPInvoiceUploadAction.class);
	
	public ActionForward uploadInvoiceFile (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
			
			InvUploadService invUpService=new InvUploadServiceImpl();
			DpInvoiceForm invForm=(DpInvoiceForm) form;
			String absFile="";
			String jspStat="successUpload";
			UserSessionContext userSessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			long loginId=userSessionContext.getId();
			logger.info("STB Installation file upload");
			if(invForm == null ||  invForm.getFile()==null )
			{
				ActionMessages messages =new ActionMessages();
				messages.add("message", new ActionMessage("Invoice.file.null.error"));
				saveMessages(request, messages);
				return mapping.findForward(jspStat);
			}
			boolean isXls=invForm.getFile().getFileName().matches("(^[^\\s]+(.*)(\\.(xls))$)"),isXlsx=invForm.getFile().getFileName().matches("(^[^\\s]+(.*)(\\.(xlsx))$)");
			
			if(!isXls && !isXlsx)
					{
					ActionMessages messages =new ActionMessages();
					messages.add("message", new ActionMessage("Invoice.file.type.error"));
					saveMessages(request, messages);
					 
					return mapping.findForward(jspStat);
					}
			try{
				absFile=invUpService.uploadInvoice(invForm.getFile());
			}catch(Exception excep){
				
				logger.info("Exception in uploading file");
				throw new Exception();
				
			}
			ExcelError excErr=null;
			DpInvoiceUploadService invServiceVal=new DpInvoiceUploadServiceImpl();
			try{
			excErr=invServiceVal.validatePayoutInv(absFile,invForm.getFileFlag());
			}catch(Exception e){
				
				e.printStackTrace();
				ActionMessages messages =new ActionMessages();
				logger.info("Error File download"+e.getCause());
				messages.add("message", new ActionMessage("Invoice.file.format.error"));
				saveMessages(request, messages);
				mapping.findForward(jspStat);
			}
			logger.info("Error Object "+excErr.toString());
			if((excErr.getInvoiceDtoS()!=null && excErr.getInvoiceDtoS().size()!=0))
			{
				ActionMessages messages =new ActionMessages();
				logger.info("Error File download");
				messages.add("message", new ActionMessage("Invoice.data.error"));
				saveMessages(request, messages);
				String errorPath=invServiceVal.errorFileDownload(excErr,invForm.getFileFlag());
				request.setAttribute("ErrPath", errorPath);
				
				return mapping.findForward(jspStat);
			}
			
			logger.info("File has been uploaded proceed for uploading invoice in DP:" + absFile);
			
			try{
			DpInvoiceUploadService invService=new DpInvoiceUploadServiceImpl();
			if(isXls){
			logger.info("File Type is xls:");
			invService.invFileUploadls(absFile);
			logger.info("Uploaded file ");
			String msg=invService.invoiceUploaded(absFile, loginId);
			if(msg.equals("SUCCESS")){
				logger.info("DB entry made for file upload");
			}
			
			}
			else if(isXlsx){
			logger.info("File Type is xlsx:");	
			invService.invFileUploadsx(absFile);
			String msg=invService.invoiceUploaded(absFile, loginId);
			
			if(msg.equals("SUCCESS")){
				logger.info("DB entry made for file upload");
			}
			}
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
			
			
			
			invForm.setFile(null);
			
			return mapping.findForward(jspStat);
			}
	public ActionForward uploadInvoiceFileNew (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
			
			InvUploadService invUpService=new InvUploadServiceImpl();
			DpInvoiceForm invForm=(DpInvoiceForm) form;
			String absFile="";
			String jspStat="successUpload";
			UserSessionContext userSessionContext=(UserSessionContext)request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			long loginId=userSessionContext.getId();
			if(invForm == null ||  invForm.getFile()==null )
			{
				ActionMessages messages =new ActionMessages();
				messages.add("message", new ActionMessage("Invoice.file.null.error"));
				saveMessages(request, messages);
				return mapping.findForward(jspStat);
			}
			boolean isXls=invForm.getFile().getFileName().matches("(^[^\\s]+(.*)(\\.(xls))$)"),isXlsx=invForm.getFile().getFileName().matches("(^[^\\s]+(.*)(\\.(xlsx))$)");
			
			if(!isXls && !isXlsx)
					{
					ActionMessages messages =new ActionMessages();
					messages.add("message", new ActionMessage("Invoice.file.type.error"));
					saveMessages(request, messages);
					 
					return mapping.findForward(jspStat);
					}
			try{
				absFile=invUpService.uploadInvoice(invForm.getFile());
			}catch(Exception excep){
				
				logger.info("Exception in uploading file");
				throw new Exception();
				
			}
			ExcelError excErr=null;
			DpInvoiceUploadService invServiceVal=new DpInvoiceUploadServiceImpl();
			try{
			excErr=invServiceVal.validatePayoutInv(absFile,invForm.getFileFlag());
			}catch(Exception e){
				
				e.printStackTrace();
				ActionMessages messages =new ActionMessages();
				logger.info("Error File download"+e.getCause());
				messages.add("message", new ActionMessage("Invoice.file.format.error"));
				saveMessages(request, messages);
				mapping.findForward(jspStat);
			}
			logger.info("Error Object "+excErr.toString());
			if((excErr.getInvoiceDto()!=null && excErr.getInvoiceDto().size()!=0))
			{
				ActionMessages messages =new ActionMessages();
				logger.info("Error File download");
				messages.add("message", new ActionMessage("Invoice.data.error"));
				saveMessages(request, messages);
				String errorPath=invServiceVal.errorFileDownload(excErr,invForm.getFileFlag());
				request.setAttribute("ErrPath", errorPath);
				
				return mapping.findForward(jspStat);
			}
			
			logger.info("File has been uploaded proceed for uploading invoice in DP:" + absFile);
			
			try{
			DpInvoiceUploadService invService=new DpInvoiceUploadServiceImpl();
			if(isXls){
			logger.info("File Type is xls:");
			invService.invFileUploadlsNew(absFile);
			logger.info("Uploaded file ");
			String msg=invService.invoiceUploaded(absFile, loginId);
			if(msg.equals("SUCCESS")){
				logger.info("DB entry made for file upload");
			}
			
			}
			else if(isXlsx){
			logger.info("File Type is xlsx:");	
			invService.invFileUploadsxNew(absFile);
			String msg=invService.invoiceUploaded(absFile, loginId);
			
			if(msg.equals("SUCCESS")){
				logger.info("DB entry made for file upload");
			}
			}
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
			
			
			
			invForm.setFile(null);
			
			return mapping.findForward(jspStat);
			}
	public ActionForward downloadErr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
			{
		 		String path=request.getParameter("errPath");
		 		File fefile = new File(path);	
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
			            "attachment; filename=" + "ErrorLog.xls");
				java.io.FileInputStream fis = new java.io.FileInputStream(fefile);
				java.io.PrintWriter pw = response.getWriter();
				
				int c=-1;
				while ((c = fis.read()) != -1){
					pw.print((char)c);
				}
				fis.close();
				pw.flush();
				pw.close();
				return null;
			}
}
