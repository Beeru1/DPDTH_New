package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.ibm.dp.beans.AvStockUploadFormBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPPoAcceptanceBulkDTO;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.service.DistAvStockUploadService;
import com.ibm.dp.service.impl.DistAvStockUploadServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;

public class DistAvStockUploadAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(DistAvStockUploadAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	private static final String INIT_UPLOAD_SUCCESS_1= "uploadSuccess1";
	//private static final String INIT_SUCCESS_EXCEL = "successExcel";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init called from DistAvStockUploadAction.");
		ActionErrors errors = new ActionErrors();
		try 
		{	
			/* Logged in user information from session */
			HttpSession session = request.getSession();			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DIST_UPLOAD_AVSTOCK)) 
			{
				logger.info(" user not auth to perform this ROLE_DIST_UPLOAD_AVSTOCK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			logger.info(loginUserId);
			
			int intCircleID = sessionContext.getCircleId();
			logger.info("****************CircleId::**************"+intCircleID);
			AvStockUploadFormBean avStockUploadFormBean = (AvStockUploadFormBean)form;
			DistAvStockUploadService avStockUploadService = new DistAvStockUploadServiceImpl();
			List<List<CircleDto>> getInitData  = avStockUploadService.getInitData(intCircleID);
			List<CircleDto> listBusCategory = getInitData.get(0);
			List<CircleDto> listProduct  = getInitData.get(1);
			avStockUploadFormBean.setListProduct(listProduct);
			avStockUploadFormBean.setListBusCategory(listBusCategory);
			logger.info("****************listProduct::**************"+listProduct.size());
			logger.info("****************listBusCategory::**************"+listBusCategory.size());
			avStockUploadFormBean.setIntBusCatID(Constants.PRODUCT_CATEGORY_CODE_AV);
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
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_DIST_UPLOAD_AVSTOCK)) 
		{
			logger.info(" user not auth to perform this ROLE_DIST_UPLOAD_AVSTOCK activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		AvStockUploadFormBean avStockUploadFormBean = (AvStockUploadFormBean) form;
		try		
		{
			DistAvStockUploadService avStockUploadService = new DistAvStockUploadServiceImpl();
			FormFile file = avStockUploadFormBean.getStockList();
			InputStream myxls = file.getInputStream();
			int intCatCode = avStockUploadFormBean.getIntBusCatHid();
			logger.info("intCatCode  ::  "+intCatCode);
			long lnDistID = sessionContext.getId();
			int intCircleID = sessionContext.getCircleId();
			int intProductID = avStockUploadFormBean.getIntProductID();
			//=======
			
			logger.info("**"+file.getFileName());
			String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
			//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx"))
			/*if(!arr.equalsIgnoreCase("xls"))
			{
				avStockUploadFormBean.setStrmsg("Only XLS File is allowed here");
				request.setAttribute("msg","Only XLS File is allowed here");
			}else
			{
				*/
				String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
				//String special = "!@#$%^*()'\\";
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("csv"))
				{
					//logger.info("*********2..");
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
					 request.setAttribute("msg","2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
					
				
			//==========
			List list = avStockUploadService.uploadExcel(file, intCatCode);
			
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
						checkValidate = false;
						break;
					} 
				}
				if(checkValidate)
				{
					logger.info("validate Success  ::"+checkValidate);
					String strMsg=avStockUploadService.insertAVStockinDP(list, intCatCode, lnDistID, intCircleID, intProductID);
					request.setAttribute("strmsg",strMsg);
					//avStockUploadFormBean.setStrmsg(strMsg);
					
					
				}
				else
				{
					session.removeAttribute("error_file");
					session.setAttribute("error_file",list);
					avStockUploadFormBean.setError_flag("true");
					
					List<List<CircleDto>> getInitData  = avStockUploadService.getInitData(intCircleID);
					List<CircleDto> listBusCategory = getInitData.get(0);
					List<CircleDto> listProduct  = getInitData.get(1);
					avStockUploadFormBean.setListProduct(listProduct);
					avStockUploadFormBean.setListBusCategory(listBusCategory);
					logger.info("****************listProduct::**************"+listProduct.size());
					logger.info("****************listBusCategory::**************"+listBusCategory.size());
					avStockUploadFormBean.setIntBusCatID(Constants.PRODUCT_CATEGORY_CODE_AV);
					avStockUploadFormBean.setIntProductID(intProductID);
					return mapping.findForward(INIT_UPLOAD_SUCCESS);	
				}
			}
			
			List<List<CircleDto>> getInitData  = avStockUploadService.getInitData(intCircleID);
			List<CircleDto> listBusCategory = getInitData.get(0);
			List<CircleDto> listProduct  = getInitData.get(1);
			avStockUploadFormBean.setListProduct(listProduct);
			avStockUploadFormBean.setListBusCategory(listBusCategory);
			logger.info("****************listProduct::**************"+listProduct.size());
			logger.info("****************listBusCategory::**************"+listBusCategory.size());
			avStockUploadFormBean.setIntBusCatID(Constants.PRODUCT_CATEGORY_CODE_AV);
		}
		}
		catch(Exception e) 
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			request.setAttribute("strmsg",e.getMessage());
			avStockUploadFormBean.setStrmsg(e.getMessage());
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
	
	
	
	
	//added by aman for non serialized to serialized conversion
	public ActionForward initConversion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("initConversion method. of serialize conversion Called ");
		ActionErrors errors = new ActionErrors();
		AvStockUploadFormBean formBean =(AvStockUploadFormBean)form ;
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
		}
		
		catch(Exception e) 
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			request.setAttribute("strmsg",e.getMessage());
			formBean.setStrmsg(e.getMessage());
			saveErrors(request, errors);		
		}
		return mapping.findForward("successconversion");
	}
	
	public ActionForward getDistDetail(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception
	{
		logger.info("initConversion method. of serialize conversion Called ");
		ActionErrors errors = new ActionErrors();
		AvStockUploadFormBean formBean =(AvStockUploadFormBean)form ;
		try
		{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			
			
			String distID=formBean.getOlmId();
			DistAvStockUploadService vseService = new DistAvStockUploadServiceImpl();
			
			List<NonSerializedToSerializedDTO> viewStockList = new ArrayList<NonSerializedToSerializedDTO>();	
			
			NonSerializedToSerializedDTO obj=null;
			viewStockList= vseService.getSerializedConversion(distID);
			
			boolean flag=false;
			for(Iterator itt =viewStockList.iterator();itt.hasNext();)
			{
				obj = (NonSerializedToSerializedDTO) itt.next();
				logger.info("obj::::"+obj.getStrStatus());
				if(session.getAttribute("NSER_CIRCLE_ID")==null)
					session.setAttribute("NSER_CIRCLE_ID", obj.getCircleId());
				else
				{
					session.removeAttribute("NSER_CIRCLE_ID");
					session.setAttribute("NSER_CIRCLE_ID", obj.getCircleId());
				}
					
				if(obj.getStrStatus() != null && obj.getStrStatus().equalsIgnoreCase(("FAIL")))
				{
					flag = true;
					formBean.setSuccess("Invalid Distributor OLM ID");
				}
			}
			
			if(distID != null && !distID.equalsIgnoreCase("") && !flag)
			{
				formBean.setStrmsg("true");
			}
			else
			{
				formBean.setStrmsg("false");
			}
			
			logger.info("formBean.setStrmsg::::"+formBean.getStrmsg());
			
			formBean.setNonSerializedToSerializedList(viewStockList);
				
		}
		
		catch(Exception e) 
		{
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			e.printStackTrace();
			errors.add("errors",new ActionError(e.getMessage()));
			request.setAttribute("strmsg",e.getMessage());
			formBean.setStrmsg(e.getMessage());
			saveErrors(request, errors);		
		}
		return mapping.findForward("successconversion");
	}
		//Added by aman to download serial
		public ActionForward exportExcel(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception 
		{
			ActionErrors errors = new ActionErrors();
			logger.info("In getSerializedStockDataExcel() ");
//			AvStockUploadFormBean formBean =(AvStockUploadFormBean)form ;
			HttpSession session = request.getSession();
			String circleId = session.getAttribute("NSER_CIRCLE_ID").toString();
			DistAvStockUploadService vseService = new DistAvStockUploadServiceImpl();
			List<NonSerializedToSerializedDTO> prodList = vseService.getALLProdSerialData(circleId);	
//			formBean.setProdList(prodList);
//			request.setAttribute("prodList",prodList);
			
			InputStream in = null;
			ServletOutputStream out = null;
			PrintWriter out1 = null;
			try 
			{
				String [][] excelData = null;
				Iterator itr =prodList.iterator();
				logger.info("Product List()"+prodList.size());
				excelData =  new String [prodList.size()+2][prodList.size()+2];
				logger.info("formBean.excelData.length()"+excelData.length);
				excelData[0][0] = "product name" ;
				excelData[0][1] = "serial number" ;
				int count = 1;
				while(itr.hasNext())
				{
					NonSerializedToSerializedDTO dto =	(NonSerializedToSerializedDTO) itr.next();
					excelData[count][0] = dto.getProductName();
					excelData[count][1] = "";
					count++;
				}
				logger.info("Data filled in excel");
				String strValue = ResourceReader.getCoreResourceBundleValue(Constants.UPLOAD_FILE_PATH);
				logger.info("File path  ::  "+strValue);
				//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/NonserializedSampleBulkUpload.xls");
				//String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NonserializedSampleBulkUpload.xls";
				//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "NonserializedSampleBulkUpload.xls");
				String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
				String fpath=path  + "NonserializedSampleBulkUpload.xls";
				in = new FileInputStream(fpath);
				HSSFWorkbook workbook = new HSSFWorkbook(in);
				HSSFSheet dataSheet = workbook.getSheetAt(0);
				HSSFRow myRow = null;         
				HSSFCell myCell = null;  
				for (int rowNum = 0; rowNum < excelData.length; rowNum++)
				{      
					myRow = dataSheet.createRow(rowNum);               
					for (int cellNum = 0; cellNum < 3 ; cellNum++)
					{     
						myCell = myRow.createCell(cellNum);   
						logger.info("excelData[rowNum][cellNum]"+excelData[rowNum][cellNum]);
						myCell.setCellValue(excelData[rowNum][cellNum]);             
					}         
				}      
				
				response.setHeader( "Pragma", "public" );
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Cache-Control", "must-revalidate");
				String timestamp =Long.toString(System.currentTimeMillis());
				logger.info("timestamp*************"+timestamp);
				response.setHeader("Content-Disposition", "attachment;filename=NonSerializedToSerialized"+timestamp+".xls");
				
				FileOutputStream fos  = new FileOutputStream(new File(strValue + "NonSerializedToSerialized"+timestamp+".xls")); 
				
				workbook.write(fos);
				in = new FileInputStream(strValue  + "NonSerializedToSerialized"+timestamp+".xls");
				out = response.getOutputStream();
				byte[] outputByte = new byte[4096];
				while (in.read(outputByte, 0, 4096) != -1) {
					out.write(outputByte, 0, 4096);
				}
			}
			catch (IOException e) 
			{
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				e.printStackTrace();
				errors.add("errors",new ActionError(e.getMessage()));
				request.setAttribute("strmsg",e.getMessage());
//				formBean.setStrmsg(e.getMessage());
				saveErrors(request, errors);
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
			return mapping.findForward("successconversion");
		}
	
		public ActionForward uploadExcelConversion(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			logger.info("-----------uploadExcelConversion------init uploadExcel CALLED-----------------");
			ActionErrors errors = new ActionErrors();
			AvStockUploadFormBean formBean = (AvStockUploadFormBean) form;
			ActionMessages messages = new ActionMessages();
			String distOlmID= formBean.getOlmId();
			try 
			{	
				HttpSession session = request.getSession();
				formBean = (AvStockUploadFormBean) form;
				FormFile file = formBean.getUploadedFile();
				InputStream myxls = file.getInputStream();
				DistAvStockUploadService excelBulkupload = new DistAvStockUploadServiceImpl();
				logger.info("**"+file.getFileName());
				String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
				logger.info("*****************uploadExcelConversion******************************");
				//if(!arr.equalsIgnoreCase("xls") || !arr.equalsIgnoreCase("xlsx") )
				/*if(!arr.equalsIgnoreCase("xls"))
				{
					formBean.setStrmsg("Only XLS File is allowed here");
				}
				else
				{*/
					
					String special=ResourceReader.getCoreResourceBundleValue("SPECIAL_CHARS");
					//String special = "!@#$%^*()'\\";
					boolean found=false;
					boolean found2=false;
					
					String msgExcep="";
					if(!arr.equalsIgnoreCase("xls"))
					{
						
					msgExcep="Only XLS File is allowed here";
					formBean.setFile_message(msgExcep);
					//errors.add("errors",new ActionError(msgExcep));
					//saveErrors(request, errors);
					found=true;
					}
					 if(found==false)
					{
				
					for (int i=0; i<special.length(); i++) 
					{
					   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
						  msgExcep="Special Characters are not allowed in File Name";
					      formBean.setFile_message(msgExcep);
					      //errors.add("errors",new ActionError(msgExcep));
					      //saveErrors(request, errors);
					      found2=true;
					      break;
					   }
					}
					if ((file.getFileName().toString()).indexOf("..")>-1)
					{
						 found2=true;	
						 msgExcep="2 consecutive dots are not allowed in File Name";
						// errors.add("errors",new ActionError(msgExcep));
						 //saveErrors(request, errors);
						 formBean.setFile_message(msgExcep);
					}
					}
					 
					 if(found2==false && found==false)
					{
					
					
					String circleId = session.getAttribute("NSER_CIRCLE_ID").toString();
					List list = excelBulkupload.uploadExcel(myxls, distOlmID, circleId);
					/*for (int i = 0; i < list.size(); i++) {
						System.out.println(" >> : "+((NonSerializedToSerializedDTO)list.get(1)).getErr_msg());
					}*/
					logger.info("*********return to action****");
					logger.info("Size of list in action::"+list.size());
					session.removeAttribute("error_file");
					session.setAttribute("error_file",list);
					NonSerializedToSerializedDTO nonDto = null;
					boolean checkValidate = true;
					if(list.size() > 0)
					{
						logger.info("Size of list in action::"+list.size());
						Iterator itr = list.iterator();
						while(itr.hasNext())
						{
							nonDto = (NonSerializedToSerializedDTO) itr.next();
							logger.info("nonDto.getSerial_no):::::"+nonDto.getProductName());
							logger.info("nonDto.getSerial_no):::::"+nonDto.getSerial_no());
							logger.info("nonDto.getErr_msg():::::"+nonDto.getErr_msg());
							if(nonDto.getErr_msg()!= null && !nonDto.getErr_msg().equalsIgnoreCase("SUCCESS"))
							{
								//logger.info("iterator"+checkValidate);
								checkValidate = false;
								break;
							} 
							//itr.next(); // Commented by neetika for IN1825029
						}
						if(checkValidate)
						{
							//formBean = (AvStockUploadFormBean) form;
							logger.info("validate Success  ::"+checkValidate);
							String strMsg = excelBulkupload.convertNserToSerStock(list, distOlmID, circleId);
							//logger.info("**********************action convertNserToSerStock*********");
							request.setAttribute("strmsg",strMsg);
							formBean.setStrmsg(strMsg);
							//logger.info("GetSuccess_msg==="+strMsg);
							formBean.setSuccess(strMsg);
							//logger.info("GetSuccess_msg==="+strMsg);
							//logger.info("GetSuccess_msg==="+strMsg);
							formBean.setSuccess_message("true");
						}
						else
						{
							//logger.info("Error in File:::"+list.size());
							formBean.setError_flag("true");
							formBean.setError_file(list);
							formBean.setStrmsg("");
							return mapping.findForward(INIT_UPLOAD_SUCCESS_1);	
						}
					}
					
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				messages.add("WRONG_FILE_UPLOAD", new ActionMessage("upload.error.file",e.getMessage()));
				saveMessages(request, messages);
				request.setAttribute("errorFile1", e.getMessage());
				return mapping.findForward(INIT_UPLOAD_SUCCESS_1);
			}

			return mapping.findForward(INIT_UPLOAD_SUCCESS_1);
		}
		
		public ActionForward showErrorFileSerialize(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)	throws Exception 
		{
			AvStockUploadFormBean avStockUploadFormBean = (AvStockUploadFormBean) form;
			return mapping.findForward("errorFile1");
		}

}

