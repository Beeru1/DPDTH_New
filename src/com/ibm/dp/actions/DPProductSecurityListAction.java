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


import com.ibm.dp.beans.DPProductSecurityListBean;

import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.service.DPProductSecurityListService;
import com.ibm.dp.service.impl.DPProductSecurityListServiceImpl;


import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

import javax.servlet.ServletOutputStream;
public class DPProductSecurityListAction  extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPProductSecurityListAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String SUCCESS_EXCEL = "successExcel";
	
	
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
			
			
			System.out.println("arti*********sessionContext.getId();*******"+sessionContext.getId());
			
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
		DPProductSecurityListBean dpProductSecurityListBean = null;
		try 
		{	
			System.out.println("ARTI ACTION::&&&&&&&&&&&&&&&&&&&");
			/* Logged in user information from session */
			// Getting login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
			.getAttribute(Constants.AUTHENTICATED_USER);

			String userId = sessionContext.getId()+"";
			System.out.println("arti*********userId*******"+userId);
			dpProductSecurityListBean = 	(DPProductSecurityListBean) form;
			dpProductSecurityListBean.setUserLoginId(userId);
			
			FormFile file = dpProductSecurityListBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			 
			InputStream myxls = file.getInputStream();
			System.out.println("ARTI ACTION::&&&&&&&&&&&&&&&&&&&"+myxls);
			DPProductSecurityListService bulkupload = new DPProductSecurityListServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				dpProductSecurityListBean.setStrmsg("Only XLS File is allowed here");
			}
			else if(file.getFileSize() > com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE)
			{
				dslBulkUploadBean.setStrmsg("File size exceed to "+(com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE/1000)+" KB");
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
				dpProductSecurityListBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      dpProductSecurityListBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 dpProductSecurityListBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				
				System.out.println("ARTI else::&&&&&&&&&&&&&&&&&&&");
				List list = bulkupload.uploadExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				DPProductSecurityListDto dpProductSecurityListDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						dpProductSecurityListDto = (DPProductSecurityListDto) itr.next();
						logger.info("nsBulkUploadDTO.getErr_msg():::::"+dpProductSecurityListDto.getErr_msg());
						if(dpProductSecurityListDto.getErr_msg()!= null && !dpProductSecurityListDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						dpProductSecurityListBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****"+userId);
						String strMessage = bulkupload.updateProductSecurityList(list,userId);
						dpProductSecurityListBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						dpProductSecurityListBean.setError_flag("true");
						dpProductSecurityListBean.setError_file(list);
						dpProductSecurityListBean.setStrmsg("");
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
		DPProductSecurityListBean dslBulkUploadBean = (DPProductSecurityListBean) form;
		System.out.println("*******************"+dslBulkUploadBean.getError_file().size());
		System.out.println("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");
		DPProductSecurityListBean dpDistSecurityDepositLoanBean = (DPProductSecurityListBean) form;
		DPProductSecurityListService excelBulkupload = new DPProductSecurityListServiceImpl();
		List<DPProductSecurityListDto> productSecurityList = excelBulkupload.getALLProuductSecurityList();	
		logger.info("Inside showFormatFile function");
		InputStream in = null;
		ServletOutputStream out = null;
		PrintWriter out1 = null;
		try {
			String [][] excelData = null;
			Iterator itr =productSecurityList.iterator();
			System.out.println("dpProductSecurityListBean.getProductSecurityList()"+productSecurityList.size());
			excelData =  new String [productSecurityList.size()+2][productSecurityList.size()+2];
			System.out.println("dpProductSecurityListBean.excelData.length()"+excelData.length);
			excelData[0][0] = "Product Name" ;
			excelData[0][1] = "Security" ;
			int count = 1;
			while(itr.hasNext()){
				
				DPProductSecurityListDto dto =	(DPProductSecurityListDto) itr.next();
				excelData[count][0] = dto.getProductName();
				excelData[count][1] = dto.getSecurityAmt();
				count++;
			}
			UserSessionContext sessionContext = (UserSessionContext) request.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			String strValue = ResourceReader.getValueFromBundle(Constants.UPLOAD_FILE_PATH,Constants.APLLICATION_RESOURCE_BUNDLE);
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/ProductSecuritySampleBulkUploadFile.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "ProductSecuritySampleBulkUploadFile.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "ProductSecuritySampleBulkUploadFile.xls");
			//in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "ProductSecuritySampleBulkUploadFile.xls";
			in = new FileInputStream(fpath);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet dataSheet = workbook.getSheetAt(0);
			HSSFRow myRow = null;         
			HSSFCell myCell = null;  
			for (int rowNum = 0; rowNum < excelData.length; rowNum++){      
			myRow = dataSheet.createRow(rowNum);               
			for (int cellNum = 0; cellNum < 3 ; cellNum++){     
			myCell = myRow.createCell(cellNum);   
			System.out.println("excelData[rowNum][cellNum]"+excelData[rowNum][cellNum]);
			myCell.setCellValue(excelData[rowNum][cellNum]);             
			}         
			}      
			
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
			String timestamp =Long.toString(System.currentTimeMillis());
			System.out.println("timestamp*************"+timestamp);
			response.setHeader("Content-Disposition", "attachment;filename=ProductSecuritySampleBulkUpload"+timestamp+".xls");
			
			FileOutputStream fos  = new FileOutputStream(new File(strValue + "ProductSecuritySampleBulkUpload"+timestamp+".xls")); 
			
			workbook.write(fos);
			in = new FileInputStream(strValue  + "ProductSecuritySampleBulkUpload"+timestamp+".xls");
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			
			
		} catch (IOException e) {
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
		}	
		
		dpDistSecurityDepositLoanBean.setProductSecurityList(productSecurityList);
		request.setAttribute("productSecurityList",productSecurityList);
	//	return mapping.findForward(SUCCESS_EXCEL);
		return null;
		}
	
	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=ProductSecuritySampleBulkUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/ProductSecuritySampleBulkUploadFile.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "ProductSecuritySampleBulkUploadFile.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "ProductSecuritySampleBulkUploadFile.xls");
 		   //in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "ProductSecuritySampleBulkUploadFile.xls";
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
