package com.ibm.dp.actions;


import java.io.*;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;


import com.ibm.dp.beans.DPPendingListTransferBulkUploadBean;
import com.ibm.dp.beans.DPProductSecurityListBean;
import com.ibm.dp.beans.DPStockLevelBulkUploadBean;

import com.ibm.dp.dto.DPPendingListTransferBulkUploadDto;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.service.DPPendingListTransferBulkUploadService;
import com.ibm.dp.service.DPProductSecurityListService;
import com.ibm.dp.service.DPStockLevelBulkUploadService;
import com.ibm.dp.service.impl.DPPendingListTransferBulkUploadServiceImpl;
import com.ibm.dp.service.impl.DPProductSecurityListServiceImpl;
import com.ibm.dp.service.impl.DPStockLevelBulkUploadServiceImpl;


import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

import javax.servlet.ServletOutputStream;
public class DPPendingListTransferBulkUploadAction  extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPPendingListTransferBulkUploadAction.class.getName());
	
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
			
			
			System.out.println("Sanjay*********sessionContext.getId();*******"+sessionContext.getId());
			
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
		DPPendingListTransferBulkUploadBean dpPendingListTransferBean = null;
		try 
		{	
			System.out.println("Sanjay ACTION::&&&&&&&&&&&&&&&&&&&");
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();		
			
			UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);

			String userId = sessionContext.getId()+"";
			System.out.println("Sanjay*********userId*******"+userId);
			
			dpPendingListTransferBean = (DPPendingListTransferBulkUploadBean) form;						
			FormFile file = dpPendingListTransferBean.getUploadedFile();
			
			 
			InputStream myxls = file.getInputStream();
			System.out.println("Upload excel ACTION::------>"+myxls);
			DPPendingListTransferBulkUploadService bulkupload = new DPPendingListTransferBulkUploadServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			
				
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
					//logger.info("*********2..");
					dpPendingListTransferBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
					   dpPendingListTransferBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 dpPendingListTransferBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				
				System.out.println("else::&&&&&&&&&&&&&&&&&&&");
				List list = bulkupload.uploadExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				DPPendingListTransferBulkUploadDto dpPendingListTransferBulkUploadDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						dpPendingListTransferBulkUploadDto = (DPPendingListTransferBulkUploadDto) itr.next();
						logger.info("dpPendingListTransferBulkUploadDto.getErr_msg():::::"+dpPendingListTransferBulkUploadDto.getErr_msg());
						if(dpPendingListTransferBulkUploadDto.getErr_msg()!= null && !dpPendingListTransferBulkUploadDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{									
						dpPendingListTransferBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****"+userId);
						String strMessage = bulkupload.updatePendingListTransfer(list,userId);
						dpPendingListTransferBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						dpPendingListTransferBean.setError_flag("true");
						dpPendingListTransferBean.setError_file(list);
						dpPendingListTransferBean.setStrmsg("");
						return mapping.findForward(INIT_UPLOAD_SUCCESS);	
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		DPPendingListTransferBulkUploadBean dplBulkUploadBean = (DPPendingListTransferBulkUploadBean) form;
		System.out.println("*******************"+dplBulkUploadBean.getError_file().size());
		System.out.println("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	
		
	
	
	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		
		return mapping.findForward("formatFile");
	}
	
	/*public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=PendingListTransferBulkUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/StockLevelBulkUploadFormat.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "StockLevelBulkUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "StockLevelBulkUploadFormat.xls");
 		    //in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "PendingListTransferBulkUploadFormat.xls";
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
	*/
	

}
