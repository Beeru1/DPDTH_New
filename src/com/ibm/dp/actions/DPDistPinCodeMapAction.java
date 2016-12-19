package com.ibm.dp.actions;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.DPDistPinCodeMapFormBean;
import com.ibm.dp.beans.NSBulkUploadBean;
import com.ibm.dp.beans.WHdistmappbulkBean;
import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.NSBulkUploadDto;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.service.DPDistPinCodeMapService;
import com.ibm.dp.service.NSBulkUploadService;
import com.ibm.dp.service.WHdistmappbulkService;
import com.ibm.dp.service.impl.DPDistPinCodeMapServiceImpl;
import com.ibm.dp.service.impl.NSBulkUploadServiceImpl;
import com.ibm.dp.service.impl.WHdistmappbulkServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
public class DPDistPinCodeMapAction  extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPDistPinCodeMapAction.class.getName());
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpSession session = request.getSession();
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		logger.info("-----------------init ACTION CALLED-----------------");
		ActionErrors errors = new ActionErrors();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,(AuthorizationConstants.ROLE_DISTRIBUTOR_PINCODE_MAPPING )))
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
		//	HttpSession session = request.getSession();
			
		//	UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			logger.info("*********sessionContext.getId();*******"+sessionContext.getId());
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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
			logger.info("-----------DPDistPinCodeMapAction------init uploadExcel CALLED-----------------");
			ActionErrors errors = new ActionErrors();
			DPDistPinCodeMapFormBean distPinCodeMapFormBean = (DPDistPinCodeMapFormBean) form;
			ActionMessages messages = new ActionMessages();
			try 
			{	
				HttpSession session = request.getSession();
				distPinCodeMapFormBean = (DPDistPinCodeMapFormBean) form;
				FormFile file = distPinCodeMapFormBean.getUploadedFile();
				InputStream myxls = file.getInputStream();
				DPDistPinCodeMapService excelBulkupload = new DPDistPinCodeMapServiceImpl();
				//logger.info("*********1..");
				String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
				//logger.info("*********1.."+arr);
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
					//logger.info("*********2..");
				distPinCodeMapFormBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      distPinCodeMapFormBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 distPinCodeMapFormBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
					//logger.info("*********3..");
				List list = excelBulkupload.uploadExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				DPDistPinCodeMapDto distPinCodeMapDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						distPinCodeMapDto = (DPDistPinCodeMapDto) itr.next();
						logger.info("distPinCodeMapDto.getErr_msg():::::"+distPinCodeMapDto.getErr_msg());
						if(distPinCodeMapDto.getErr_msg()!= null && !distPinCodeMapDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						distPinCodeMapFormBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****");
						String strMessage = excelBulkupload.addDistPinCodeMap(list);
						distPinCodeMapFormBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						distPinCodeMapFormBean.setError_flag("true");
						distPinCodeMapFormBean.setError_file(list);
						distPinCodeMapFormBean.setStrmsg("");
						return mapping.findForward(INIT_UPLOAD_SUCCESS);	
					}
				}
				
			}
		}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				//errors.add("errors",new ActionError("errors.excelupload",e.getMessage()));
				messages.add("WRONG_FILE_UPLOAD", new ActionMessage("upload.error.file",e.getMessage()));
				saveMessages(request, messages);
				//request.setAttribute("errors", e.getMessage());
				return mapping.findForward(INIT_UPLOAD_SUCCESS);
			}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
}

public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		DPDistPinCodeMapFormBean distPinCodeMapFormBean = (DPDistPinCodeMapFormBean) form;
		return mapping.findForward("errorFile");
	}
public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)throws Exception 
		{
			logger.info("In getSerializedStockDataExcel() ");
			DPDistPinCodeMapFormBean distPinCodeMapFormBean = (DPDistPinCodeMapFormBean) form;
			DPDistPinCodeMapService excelBulkupload = new DPDistPinCodeMapServiceImpl();
			List<DPDistPinCodeMapDto> distPinList = excelBulkupload.getALLDistPinList();
			logger.info("Inside showFormatFile function");
			InputStream in = null;
			ServletOutputStream out = null;
			//PrintWriter out1 = null;
			try 
			{
				String [][] excelData = null;
				Iterator itr =distPinList.iterator();
				logger.info("distPinCodeMapFormBean.getdistPinList()"+distPinList.size());
				excelData =  new String [distPinList.size()+2][2];
				//logger.info("distPinCodeMapFormBean.excelData.length()"+excelData.length);
				excelData[0][0] = "Distributor OLMID" ;
				excelData[0][1] = "PinCode" ;
				int count = 1;
				while(itr.hasNext())
				{
					DPDistPinCodeMapDto dto =	(DPDistPinCodeMapDto) itr.next();
					excelData[count][0] = dto.getStrDistOLMIds();
					excelData[count][1] = dto.getPincode();
					count++;
				}
				String strValue = ResourceReader.getCoreResourceBundleValue(Constants.UPLOAD_FILE_PATH);
				logger.info("File path  ::  "+strValue);
				//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/DistributorPincodeMapping.xls");
				//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DistributorPincodeMapping.xls";
				//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DistributorPincodeMapping.xls");
				String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
				String fpath=path  + "DistributorPincodeMapping.xls";
				in = new FileInputStream(fpath);
				//in = new FileInputStream(path);
				HSSFWorkbook workbook = new HSSFWorkbook(in);
				HSSFSheet dataSheet = workbook.getSheetAt(0);
				HSSFRow myRow = null;         
				HSSFCell myCell = null;  
				for (int rowNum = 0; rowNum < excelData.length; rowNum++)
					{      
						myRow = dataSheet.createRow(rowNum);               
						for (int cellNum = 0; cellNum < 2  ; cellNum++)
						{     
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
			response.setHeader("Content-Disposition", "attachment;filename=DistributorPincodeMapping"+timestamp+".xls");
			FileOutputStream fos  = new FileOutputStream(new File(strValue + "DistributorPincodeMapping"+timestamp+".xls")); 
			workbook.write(fos);
			in = new FileInputStream(strValue  + "DistributorPincodeMapping"+timestamp+".xls");
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
			out.write(outputByte, 0, 4096);
			}
		} 
	catch (IOException e) {
		logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
		e.printStackTrace();
		throw e;
	} 
	finally 
	{

		if(in!=null)
		{
			in.close();
		}
		if(out!=null)
		{
			out.flush();
			out.close();
		}

	}	
	return mapping.findForward(SUCCESS_EXCEL);
	
	}
}
