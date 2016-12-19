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


import com.ibm.dp.beans.DPDistSecurityDepositLoanBean;
import com.ibm.dp.beans.DPStockLevelBulkUploadBean;
import com.ibm.dp.beans.WHdistmappbulkBean;

import com.ibm.dp.dto.DPDistSecurityDepositLoanDto;
import com.ibm.dp.dto.DPProductSecurityListDto;
import com.ibm.dp.dto.WHdistmappbulkDto;
import com.ibm.dp.service.DPDistSecurityDepositLoanService;
import com.ibm.dp.service.WHdistmappbulkService;
import com.ibm.dp.service.impl.DPDistSecurityDepositLoanServiceImpl;
import com.ibm.dp.service.impl.WHdistmappbulkServiceImpl;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

import javax.servlet.ServletOutputStream;
public class DPDistSecurityDepositLoanAction  extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DPDistSecurityDepositLoanAction.class.getName());
	
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
		DPDistSecurityDepositLoanBean dpDistSecurityDepositLoanBean = null;
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
			dpDistSecurityDepositLoanBean = 	(DPDistSecurityDepositLoanBean) form;
			dpDistSecurityDepositLoanBean.setUserLoginId(userId);
			
			FormFile file = dpDistSecurityDepositLoanBean.getUploadedFile();
			//String path = getServlet().getServletContext().getRealPath("")+"/"+file.getFileName();
			 
			InputStream myxls = file.getInputStream();
			System.out.println("ARTI ACTION::&&&&&&&&&&&&&&&&&&&"+myxls);
			DPDistSecurityDepositLoanService bulkupload = new DPDistSecurityDepositLoanServiceImpl();
			logger.info("**  "+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				dpDistSecurityDepositLoanBean.setStrmsg("Only XLS File is allowed here");
			}
			else if(file.getFileSize() > com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE)
			{
				dslBulkUploadBean.setStrmsg("File size exceed to "+(com.ibm.dp.common.Constants.C2S_BULK_UPLOAD_FILE_MAX_SIZE/1000)+" KB");
			}
			else
			{*/
				
				
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
					//logger.info("*********2..");
				dpDistSecurityDepositLoanBean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      dpDistSecurityDepositLoanBean.setStrmsg("Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 dpDistSecurityDepositLoanBean.setStrmsg("2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				
				System.out.println("ARTI else::&&&&&&&&&&&&&&&&&&&");
				List list = bulkupload.uploadExcel(myxls);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",list);
				
				DPDistSecurityDepositLoanDto dpDistSecurityDepositLoanDto = null;
				boolean checkValidate = true;
				if(list.size() > 0)
				{
					logger.info("Size of list in action::"+list.size());
					
					Iterator itr = list.iterator();
					while(itr.hasNext())
					{
						dpDistSecurityDepositLoanDto = (DPDistSecurityDepositLoanDto) itr.next();
						logger.info("nsBulkUploadDTO.getErr_msg():::::"+dpDistSecurityDepositLoanDto.getErr_msg());
						if(dpDistSecurityDepositLoanDto.getErr_msg()!= null && !dpDistSecurityDepositLoanDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
					if(checkValidate)
					{
						dpDistSecurityDepositLoanBean.setError_flag("false");
						System.out.println("***File is correct. Now goes for DB transaction****"+userId);
						String strMessage = bulkupload.addSecurityLoan(list,userId);
						dpDistSecurityDepositLoanBean.setStrmsg(strMessage);
						return mapping.findForward(INIT_UPLOAD_SUCCESS);
					}
					else
					{
						System.out.println("Error in File:::"+list.size());
						dpDistSecurityDepositLoanBean.setError_flag("true");
						dpDistSecurityDepositLoanBean.setError_file(list);
						dpDistSecurityDepositLoanBean.setStrmsg("");
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
		DPDistSecurityDepositLoanBean dslBulkUploadBean = (DPDistSecurityDepositLoanBean) form;
		System.out.println("*******************"+dslBulkUploadBean.getError_file().size());
		System.out.println("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
			{
		logger.info("In getSerializedStockDataExcel() ");
		DPDistSecurityDepositLoanBean dpDistSecurityDepositLoanBean = (DPDistSecurityDepositLoanBean) form;
		DPDistSecurityDepositLoanService excelBulkupload = new DPDistSecurityDepositLoanServiceImpl();
		List<DPDistSecurityDepositLoanDto> distSecLoanList = excelBulkupload.getALLDistSecurityLoan();	
		logger.info("Inside showFormatFile function");
		InputStream in = null;
		ServletOutputStream out = null;
		
		try 
		{
			String [][] excelData = null;
			Iterator itr =distSecLoanList.iterator();
			logger.info("dpProductSecurityListBean.getProductSecurityList()"+distSecLoanList.size());
			excelData =  new String [distSecLoanList.size()+1][5];
			logger.info("dpProductSecurityListBean.excelData.length()"+excelData.length);
			excelData[0][0] = "Distributor OLM ID" ;
			excelData[0][1] = "Distributor Name" ;
			excelData[0][2] = "Security Amount" ;
			excelData[0][3] = "Loan" ;
			excelData[0][4] = "Circle Name" ;
			int count = 1;
			while(itr.hasNext())
			{
				DPDistSecurityDepositLoanDto dto =	(DPDistSecurityDepositLoanDto) itr.next();
				excelData[count][0] = dto.getDistOlmId();
				excelData[count][1] = dto.getDistName();
				excelData[count][2] = dto.getSecurityAmt();
				excelData[count][3] = dto.getLoanAmt();
				excelData[count][4] = dto.getCircleName();
				count++;
			}
			
			String strValue = ResourceReader.getValueFromBundle(Constants.UPLOAD_FILE_PATH,Constants.APLLICATION_RESOURCE_BUNDLE);
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/DPDistSecurityLoanBulkUploadFormat.xls");
			//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DPDistSecurityLoanBulkUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DPDistSecurityLoanBulkUploadFormat.xls");
			//in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "DPDistSecurityLoanBulkUploadFormat.xls";
			in = new FileInputStream(fpath);
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet dataSheet = workbook.getSheetAt(0);
			HSSFRow myRow = null;         
			HSSFCell myCell = null;  
			for (int rowNum = 0; rowNum < excelData.length; rowNum++){      
			myRow = dataSheet.createRow(rowNum);               
			for (int cellNum = 0; cellNum < 5 ; cellNum++){     
			myCell = myRow.createCell(cellNum);   
			//logger.info("excelData[rowNum][cellNum]"+excelData[rowNum][cellNum]);
			myCell.setCellValue(excelData[rowNum][cellNum]);             
			}         
			}      
			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
			String timestamp =Long.toString(System.currentTimeMillis());
			response.setHeader("Content-Disposition", "attachment;filename=DPDistSecurityLoanUpload"+timestamp+".xls");
			FileOutputStream fos  = new FileOutputStream(new File(strValue + "DPDistSecurityLoanUpload"+timestamp+".xls"));
			workbook.write(fos);
			in = new FileInputStream(strValue+"DPDistSecurityLoanUpload"+timestamp+".xls");
			out = response.getOutputStream();
			byte[] outputByte = new byte[4096];
			while (in.read(outputByte, 0, 4096) != -1) 
			{
				out.write(outputByte, 0, 4096);
			}
			
			
		} catch (IOException e) 
		{
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try
			{
				if(in!=null)
					in.close();
				if(out!=null)
				{
					out.flush();
					out.close();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				throw e;
			}
		}	
		
		//dpDistSecurityDepositLoanBean.setDistSecLoanList(distSecLoanList);
		//request.setAttribute("distSecLoanList",distSecLoanList);
		return null;
		}
	
	public ActionForward showFormatFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("Inside showFormatFile function");
		response.setHeader( "Pragma", "public" );
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Cache-Control", "must-revalidate");
		response.setHeader("Content-Disposition", "attachment;filename=DPDistSecurityLoanBulkUploadFormat.xls");			
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/DPDistSecurityLoanBulkUploadFormat.xls");
			// String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DPDistSecurityLoanBulkUploadFormat.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "DPDistSecurityLoanBulkUploadFormat.xls");
 		   //in = new FileInputStream(path);
			String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "DPDistSecurityLoanBulkUploadFormat.xls";
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
