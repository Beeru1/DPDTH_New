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
import java.io.*;

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
import com.ibm.dp.beans.WHdistmappbulkBean;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.service.WHdistmappbulkService;
import com.ibm.dp.service.impl.WHdistmappbulkServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


public class WHdistmappbulkAction extends DispatchAction {
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(WHdistmappbulkAction.class
			.getName());

	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String SUCCESS_EXCEL = "successExcel";

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		System.out.println("----------WHdistmappbulkAction-------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		try {
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			errors.add("errors", new ActionError("hello "));
			saveErrors(request, errors);
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			
		}
		return mapping.findForward(INIT_SUCCESS);
	}

	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("-----------WHdistmappbulkAction------init uploadExcel CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		WHdistmappbulkBean whdistmappbulkBean = null;
		ActionMessages messages = new ActionMessages();
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			whdistmappbulkBean = (WHdistmappbulkBean) form;
			FormFile file = whdistmappbulkBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			InputStream myxls = file.getInputStream();
			WHdistmappbulkService bulkupload = new WHdistmappbulkServiceImpl();
			logger.info("**"+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx")  )
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				whdistmappbulkBean.setStrmsg("Only XLS File is allowed here");
			}else
			{
				
				*/String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
					//logger.info("*********2..");
				whdistmappbulkBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      whdistmappbulkBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 whdistmappbulkBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				List list = bulkupload.uploadExcel(myxls);
				
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				WHdistmappbulkDto whdistmappbulkDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						whdistmappbulkDto = (WHdistmappbulkDto) itr.next();
						logger.info("whdistmappbulkDTO.getErr_msg():::::"+whdistmappbulkDto.getErr_msg());
						if(whdistmappbulkDto.getErr_msg()!= null && !whdistmappbulkDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						whdistmappbulkBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****");
						String strMessage = bulkupload.addDistWarehouseMap(list);
						whdistmappbulkBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						whdistmappbulkBean.setError_flag("true");
						whdistmappbulkBean.setError_file(list);
						whdistmappbulkBean.setStrmsg("");
						return mapping.findForward(INIT_UPLOAD_SUCCESS);	
					}
				}
				
			}
		}
		catch(Exception e)
		{
			
			//e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			//errors.add("errors",new ActionError("errors.excelupload",e.getMessage()));
			messages.add("WRONG_FILE_UPLOAD", new ActionMessage("upload.error.file",e.getMessage()));
			saveMessages(request, messages);
			//request.setAttribute("errors", e.getMessage());
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}

		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}

	

	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");
		WHdistmappbulkBean whdistmappbulkBean = (WHdistmappbulkBean) form;
		WHdistmappbulkService excelBulkupload = new WHdistmappbulkServiceImpl();
		List<WHdistmappbulkDto> whDistMapList = excelBulkupload.getALLWhDistMapData();	
		whdistmappbulkBean.setWhDistMapList(whDistMapList);
		request.setAttribute("whDistMapList",whDistMapList);
		return mapping.findForward(SUCCESS_EXCEL);
		}

	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=WareHouseDistMappingSampleBulkUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/WareHouseDistMappingSampleBulkUploadFormat.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "WareHouseDistMappingSampleBulkUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "WareHouseDistMappingSampleBulkUploadFormat.xls");
 		   //in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "WareHouseDistMappingSampleBulkUploadFormat.xls";
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
	
	
public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
		WHdistmappbulkBean whdistmappbulkBean = (WHdistmappbulkBean) form;
			return mapping.findForward("errorFile");
		}
}

