package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

import com.ibm.dp.beans.DPPoAcceptanceBulkBean;
import com.ibm.dp.beans.DPStockLevelBulkUploadBean;
import com.ibm.dp.beans.NSBulkUploadBean;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.DPStockLevelBulkUploadDto;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.dp.service.DPStockLevelBulkUploadService;
import com.ibm.dp.service.NSBulkUploadService;
import com.ibm.dp.service.impl.DPStockLevelBulkUploadServiceImpl;
import com.ibm.dp.service.impl.NSBulkUploadServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import javax.servlet.ServletOutputStream;
public class NSBulkUploadAction  extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(NSBulkUploadAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String SUCCESS_EXCEL = "formatFile";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_ADD_CIRCLE )))
		{
			logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		} 
		logger.info("-----------------init ACTION CALLED-----------------");
		//ActionErrors errors = new ActionErrors();
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			// session = request.getSession();
			
		//	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
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
		NSBulkUploadBean nSBulkUploadBean = null;
		try 
		{	
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			
			nSBulkUploadBean = (NSBulkUploadBean) form;
			
			FormFile file = nSBulkUploadBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			 
			InputStream myxls = file.getInputStream();
			 
			NSBulkUploadService bulkupload = new NSBulkUploadServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
		/*	if(!arr.equalsIgnoreCase("xls"))
			{
				nSBulkUploadBean.setStrmsg("Only XLS File is allowed here");
			}
			else if(file.getFileSize() > com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE)
			{
				nSBulkUploadBean.setStrmsg("File size exceed to "+(com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE/1000)+" KB");
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
				nSBulkUploadBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      nSBulkUploadBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 nSBulkUploadBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				List list = bulkupload.uploadExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				NSBulkUploadDto nsBulkUploadDTO = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						nsBulkUploadDTO = (NSBulkUploadDto) itr.next();
						logger.info("nsBulkUploadDTO.getErr_msg():::::"+nsBulkUploadDTO.getErr_msg());
						if(nsBulkUploadDTO.getErr_msg()!= null && !nsBulkUploadDTO.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						nSBulkUploadBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****");
						String strMessage = bulkupload.updateStockQty(list);
						nSBulkUploadBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						nSBulkUploadBean.setError_flag("true");
						nSBulkUploadBean.setError_file(list);
						nSBulkUploadBean.setStrmsg("");
						return mapping.findForward(INIT_UPLOAD_SUCCESS);	
					}
				}
				
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
	
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		NSBulkUploadBean nSBulkUploadBean = (NSBulkUploadBean) form;
		System.out.println("*******************"+nSBulkUploadBean.getError_file().size());
		System.out.println("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=NSBulkUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/NSBulkUploadFormat.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NSBulkUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NSBulkUploadFormat.xls");
 		   //in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "NSBulkUploadFormat.xls";
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
	
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");
		NSBulkUploadBean nSBulkUploadBean = (NSBulkUploadBean) form;
		NSBulkUploadService excelBulkupload = new NSBulkUploadServiceImpl();
		List<NSBulkUploadDto> stockLevelList = excelBulkupload.getALLStockLevelList();	
		logger.info("Inside showFormatFile function");
		InputStream in = null;
		ServletOutputStream out = null;
		PrintWriter out1 = null;
		try {
			String [][] excelData = null;
			Iterator itr =stockLevelList.iterator();
		//	logger.info("dpProductSecurityListBean.getProductSecurityList()"+stockLevelList.size());
			excelData =  new String [stockLevelList.size()+2][3];
			//logger.info("dpProductSecurityListBean.excelData.length()"+excelData.length);
			excelData[0][0] = "Distributor OLM ID" ;
			excelData[0][1] = "Product Name" ;
			excelData[0][2] = "STB Quantity" ;
			
			
			
			int count = 1;
			while(itr.hasNext()){
				
				NSBulkUploadDto dto =	(NSBulkUploadDto) itr.next();
				excelData[count][0] = dto.getDistOlmId();
				excelData[count][1] = dto.getProductName();
				excelData[count][2] = dto.getStockQty();
			
				count++;
			}
			UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			String strValue = ResourceReader.getValueFromBundle(Constants.UPLOAD_FILE_PATH,Constants.APLLICATION_RESOURCE_BUNDLE);
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/NonserializedSampleBulkUpload.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NonserializedSampleBulkUpload.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NonserializedSampleBulkUpload.xls");
 		  // in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "NonserializedSampleBulkUpload.xls";
			in = new FileInputStream(fpath);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet dataSheet = workbook.getSheetAt(0);
			HSSFRow myRow = null;         
			HSSFCell myCell = null;  
			for (int rowNum = 0; rowNum < excelData.length; rowNum++){      
			myRow = dataSheet.createRow(rowNum);               
			for (int cellNum = 0; cellNum < 3 ; cellNum++){     
			myCell = myRow.createCell(cellNum);   
			//logger.info("excelData[rowNum][cellNum]"+excelData[rowNum][cellNum]);
			myCell.setCellValue(excelData[rowNum][cellNum]);             
			}         
			}      
			
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
			String timestamp =Long.toString(System.currentTimeMillis());
			logger.info("timestamp*************"+timestamp);
			response.setHeader("Content-Disposition", "attachment;filename=NonserializedSampleBulkUpload"+timestamp+".xls");
			
			FileOutputStream fos  = new FileOutputStream(new File(strValue + "NonserializedSampleBulkUpload"+timestamp+".xls")); 
			
			workbook.write(fos);
			in = new FileInputStream(strValue  + "NonserializedSampleBulkUpload"+timestamp+".xls");
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			
			
		} catch (IOException e) {
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {

			if(in!=null)
			{
				in.close();
			}
			if(out!=null)
			{
				out.flush();
				out.close();
			}
//			in.close();
//			out.flush();
//			out.close();
		}	
		
		
		return mapping.findForward(SUCCESS_EXCEL);
		
		}

}
