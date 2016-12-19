package com.ibm.dp.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.beans.StbFlushOutFormBean;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dp.service.STBFlushOutService;
import com.ibm.dp.service.impl.STBFlushOutServiceImpl;
import com.ibm.reports.beans.GenericReportsFormBean;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;

public class StbFlushOutAction extends DispatchAction
{
	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(StbFlushOutAction.class.getName());
	
	private static final String INIT_SUCCESS = "init";
	private static final String INIT_UPLOAD_SUCCESS = "uploadSuccess";
	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("-----------------init called from StbFlushOutAction.");
		ActionErrors errors = new ActionErrors();
		try 
		{	
			/* Logged in user information from session */
			HttpSession session = request.getSession();			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_STB_FLUSHOUT)) 
			{
				logger.info(" user not auth to perform this ROLE_STB_FLUSHOUT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			logger.info(loginUserId);			
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
	
	public ActionForward uploadSearchFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("------- uploadSearchFile called from StbFlushOutAction.");
		ActionErrors errors = new ActionErrors();
		/* Logged in user information from session */
		HttpSession session = request.getSession();			
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_STB_FLUSHOUT)) 
		{
			logger.info("user not auth to perform this ROLE_STB_FLUSHOUT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}		
		StbFlushOutFormBean stbFlushOutFormBean = (StbFlushOutFormBean) form;
		try		
		{
			STBFlushOutService STBFlushService = new STBFlushOutServiceImpl();
			stbFlushOutFormBean  = STBFlushService.getSTBList(stbFlushOutFormBean);
			
			if(stbFlushOutFormBean.isFileEmptyFlag()){
				logger.info("File is empty");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("fileEmpty")); 
			//	errors.add("fileEmpty", new ActionError("File trying to upload is empty."));
				saveErrors(request, errors);
				logger.info("File trying to upload is empty.");
				
				return mapping.findForward(INIT_SUCCESS);
			}	
			ArrayList<DuplicateSTBDTO> sTBDtoList = stbFlushOutFormBean.getFreshSTBList();
			if(sTBDtoList.size() ==0 ){
				logger.info("No record found for search");
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("noRecordFound"));	
				saveErrors(request, errors);
				return mapping.findForward(INIT_SUCCESS);
			}
			
			if(session.getAttribute("StbFlushOutFormBean")==null)
				session.setAttribute("StbFlushOutFormBean", stbFlushOutFormBean);
			else
			{
				session.removeAttribute("StbFlushOutFormBean");
				session.setAttribute("StbFlushOutFormBean", stbFlushOutFormBean);
			}

		}
		catch(Exception e) 
		{
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("fileException"));
			saveErrors(request, errors);
			e.printStackTrace();			
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);		
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	
	public ActionForward downloadFreshStockList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
			HttpSession session = request.getSession();	
			StbFlushOutFormBean stbFlushOutFormBean = (StbFlushOutFormBean)session.getAttribute("StbFlushOutFormBean");
			ArrayList<DuplicateSTBDTO> sTBDtoList = stbFlushOutFormBean.getFreshSTBList();
					 
		try
	     {
			String filePath = (String) getResources(request).getMessage("UPLOAD_PATH_DAMAGE") + "/" + "ErrorStbtemp.xls";
 		    ServletOutputStream out = response.getOutputStream();
 		    //logger.info("filePath"+filePath);
 			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setHeader("Content-Disposition", "attachment;filename=Stock_Details_STB_Flush_Out.xls");
			
 		    InputStream in = null;
			//in = getServlet().getServletContext().getResourceAsStream("/WEB-INF/file/Stock_Details_STB_Flush_Out.xls");
 		    //String path=(String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "Stock_Details_STB_Flush_Out.xls";
			//in = new FileInputStream((String) getResources(request).getMessage("DOWNLOAD_PATH") + "/" + "Stock_Details_STB_Flush_Out.xls");
 		   //in = new FileInputStream(path);
 		   String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
			String fpath=path  + "StockDetailsSTBFlushOut.xls";
			logger.info("fpath=in flush out ="+fpath);
			in = new FileInputStream(fpath);
	        // Create a workbook 
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            logger.info("fpath=in flush out after workbook ="+fpath);
            HSSFSheet sheet = workbook.getSheetAt(0);
			Iterator rows = sheet.rowIterator();
			//System.out.println("fpath====--------=="+fpath);
			int rowNumber = 0;
			HSSFRow row = (HSSFRow) rows.next();  
			//System.out.println("fpath====-------+++-=="+fpath);
			HSSFCell cell;
			DuplicateSTBDTO errorSTBDTO=null;
			//System.out.println("niki=="+fpath);
			while (rowNumber < sTBDtoList.size() ) 
			{
				 errorSTBDTO = sTBDtoList.get(rowNumber);
				 row = sheet.createRow(++rowNumber);
	             	            
	             	cell = row.createCell(0);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        			cell.setCellValue(errorSTBDTO.getStrSerialNo());

        			cell = row.createCell(1);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrProductName()) || null == errorSTBDTO.getStrProductName())
	        			cell.setCellValue(" ");
	        		else
	        			cell.setCellValue(errorSTBDTO.getStrProductName());
        			
        			cell = row.createCell(2);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrSTBStatus()) || null == errorSTBDTO.getStrSTBStatus())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrSTBStatus());
        			
        			cell = row.createCell(3);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrPONumber()) || null == errorSTBDTO.getStrPONumber())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrPONumber());
        			
        			cell = row.createCell(4);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrPOStatus()) || null == errorSTBDTO.getStrPOStatus())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrPOStatus());
        			
        			cell = row.createCell(5);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrDCNo()) || null == errorSTBDTO.getStrDCNo())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrDCNo()); 

	             	cell = row.createCell(6);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrDCDate()) || null == errorSTBDTO.getStrDCDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrDCDate());

        			cell = row.createCell(7);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrInventoryChangeDt()) || null == errorSTBDTO.getStrInventoryChangeDt())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrInventoryChangeDt());
        			
        			cell = row.createCell(8);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrReceivedOn()) || null == errorSTBDTO.getStrReceivedOn())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrReceivedOn());
        			
        			cell = row.createCell(9);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrStockReceivedDate()) || null == errorSTBDTO.getStrStockReceivedDate())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrStockReceivedDate());
        			
        			cell = row.createCell(10);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrDistOLMId()) || null == errorSTBDTO.getStrDistOLMId())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrDistOLMId()); 

	             	cell = row.createCell(11);
	        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        		if("".equals(errorSTBDTO.getStrDistName()) || null == errorSTBDTO.getStrDistName())
	        			cell.setCellValue(" ");
	        		else 
	        			cell.setCellValue(errorSTBDTO.getStrDistName());
        			
			  }
			logger.info("Excel written...");
		     FileOutputStream fos  = new FileOutputStream(new File(filePath));
				workbook.write(fos);			
				logger.info("Excel writtennn..n.");
				in = new FileInputStream(filePath);
				byte[] outputByte = new byte[4096];
				while (in.read(outputByte, 0, 4096) != -1) {
					out.write(outputByte, 0, 4096);
				}
				out.flush();
				out.close();
				
		     //logger.info("Excel written...");
	     }catch (IOException ioe) { 
	    	
	    	 logger.info("Message from FLUSHOUT =================================="+ioe.getMessage());
	    	 ioe.printStackTrace();
			  // if this happens there is probably no way to report the error to the user
			  if (!response.isCommitted()) {
			    response.setContentType("text/html");
			    logger.error("Error in writing XLS file");
			  }
	     }
	     catch (Throwable t){
	     logger.info("Message from FLUSHOUT =================================="+t.getMessage()+" CAUSE IS :: EXCEPTION "+t.getCause());
	     t.printStackTrace();
	     }
	     return null;
	}

	
	@SuppressWarnings("unchecked")
	public ActionForward flushErrorPO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info(" flushErrorPO called from StbFlushOutAction.");
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		/* Logged in user information from session */
		HttpSession session = request.getSession();			
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB, AuthorizationConstants.ROLE_STB_FLUSHOUT)) 
		{
			logger.info(" user not auth to perform this ROLE_STB_FLUSHOUT activity  ");
			errors.add("errors",new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}		
		long loginUserId1 = sessionContext.getId();
		String loginUserId=String.valueOf(loginUserId1);
		StbFlushOutFormBean stbFlushOutFormBean = (StbFlushOutFormBean) form;
		
		FormFile freshStockFlushFile = null;
		freshStockFlushFile=stbFlushOutFormBean.getFreshStockFlushFile();
	//	freshStockFlushFile=stbFlushOutFormBean.getFreshStockFlushFile();

		STBFlushOutService STBFlushService = new STBFlushOutServiceImpl();
		try		
		{
			// calling service method to process damageStockFlushFile
			if(freshStockFlushFile !=null && freshStockFlushFile.getFileName()!=null && !(freshStockFlushFile.getFileName().equalsIgnoreCase("")))
			{
				logger.info("freshStockFlushFile uploaded");
				InputStream freshStockxls = freshStockFlushFile.getInputStream();
				String freshStockFlushFormat= (freshStockFlushFile.getFileName().toString()).substring(freshStockFlushFile.getFileName().toString().lastIndexOf(".")+1,freshStockFlushFile.getFileName().toString().length());
				if(!freshStockFlushFormat.equalsIgnoreCase("xls") )
				{
					stbFlushOutFormBean.setStrmsg("Only XLS File is allowed here");
				}
				else
				{
					Map dataToFlush = STBFlushService.uploadFreshExcel(freshStockxls);
				/*	//aman 
					
					List<DuplicateSTBDTO> asaList=(List<DuplicateSTBDTO>) dataToFlush.get("query");
					Iterator itr99=asaList.iterator();
					DuplicateSTBDTO DuplicateSTBDTO1= null;
					while(itr99.hasNext())
					{
						
						DuplicateSTBDTO1=(DuplicateSTBDTO) itr99.next();
						logger.info("asa:::::::::::::;action::::::;"+DuplicateSTBDTO1.getStrSerialNo());
						logger.info("asa:::::::::::::;action:::::remarks:;"+DuplicateSTBDTO1.getStrRemarks());
					}
					//aman end
*/					session.setAttribute("error_file",dataToFlush.get("error"));
					DuplicateSTBDTO flushUploadDTO = null;
					boolean checkValidate = true;
					//HashMap<String ,ArrayList<DuplicateSTBDTO>> flushMap = new HashMap<String ,ArrayList<DuplicateSTBDTO>>();
					//HashMap errorMap = new HashMap();
					
					if(dataToFlush!=null && dataToFlush.size() > 0)
					{
						logger.info("Size of list in action::"+dataToFlush.size());
						
						if(dataToFlush.get("error")!=null) 
						{   
								
									Iterator itr = ((List<DuplicateSTBDTO>) dataToFlush.get("error")).iterator();
									while(itr.hasNext())
									{
										flushUploadDTO = (DuplicateSTBDTO) itr.next();
										logger.info("flushUploadDTO.getErr_msg():::::"+flushUploadDTO.getErr_msg());
										if(flushUploadDTO.getErr_msg()!= null && !flushUploadDTO.getErr_msg().equalsIgnoreCase(""))
										{
											checkValidate = false;
											break;
										} 
									}
						}
						
						if(checkValidate)
						{
							stbFlushOutFormBean.setError_flag("false");
							logger.info("***File is correct. Now goes for DB transaction****");
							if((List)dataToFlush.get("upload")!=null && (List)dataToFlush.get("query")!=null)
							{
								//String strMessage = STBFlushService.flushDPSerialNumbers((List<DuplicateSTBDTO>)dataToFlush.get("upload"),(List<DuplicateSTBDTO>)dataToFlush.get("query"));
								//changed by aman 
								
								String strMessage = STBFlushService.flushDPSerialNumbers((List<DuplicateSTBDTO>)dataToFlush.get("upload"),(List<DuplicateSTBDTO>)dataToFlush.get("query"),loginUserId);
								
								stbFlushOutFormBean.setStrmsg(strMessage);
							}
							return mapping.findForward(INIT_UPLOAD_SUCCESS);
						}
						else
						{
							logger.info("Error in File:::"+dataToFlush.size());
							stbFlushOutFormBean.setError_flag("true");
							stbFlushOutFormBean.setError_file((List) dataToFlush.get("error"));
							stbFlushOutFormBean.setStrmsg("");
							return mapping.findForward(INIT_UPLOAD_SUCCESS);	
						}
					}
					else 
					{
						checkValidate = false;
						if(!(checkValidate))
						{
							logger.info("Error in File:::"+dataToFlush.size());
							stbFlushOutFormBean.setError_flag("true");
							stbFlushOutFormBean.setError_file((List)dataToFlush.get("error"));
							
							stbFlushOutFormBean.setStrmsg("");
							return mapping.findForward(INIT_UPLOAD_SUCCESS);	
						}
						
					}
					//flushUploadBean.setStrmsg(str);
				}
			}
			saveMessages(request, messages);
		}
		catch(Exception e)
		{
			errors.add("fileUploadEexception", new ActionError("file.exception"));
			
			saveErrors(request, errors);
			e.printStackTrace();
			
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errorUpload",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_UPLOAD_SUCCESS);
		}
		return mapping.findForward(INIT_UPLOAD_SUCCESS);
	}
	
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		StbFlushOutFormBean stbFlushOutFormBean = (StbFlushOutFormBean) form;
		logger.info("*******************"+stbFlushOutFormBean.getError_file().size());
		logger.info("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	public ActionForward closeRecoForDistributor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		logger.info("===============closeRecoForDistributor======");
		GenericReportsFormBean genericReportsFormBean = (GenericReportsFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		GenericReportsService genericReportsService = GenericReportsServiceImpl.getInstance();
		int groupId = sessionContext.getGroupId();
		int reportId = 30;
		genericReportsFormBean.setReportLabel("Close Distributor Reco");
		populateCriteria(reportId, genericReportsFormBean, groupId,request);
		populateInitialValues(sessionContext, genericReportsFormBean,reportId);
		return mapping.findForward("initCriteriaForDist");
		
	}
	
	private void populateCriteria(int reportId,GenericReportsFormBean genericReportsFormBean,int groupId,HttpServletRequest request) throws DPServiceException, DAOException{
		GenericReportsService genericReportsService =  GenericReportsServiceImpl.getInstance(); 
		List<String> idList = new ArrayList<String>();
		List<CriteriaDTO>  criteriaList = genericReportsService.getReportCriterias(reportId,groupId);
			for (CriteriaDTO criteriaDTO : criteriaList) {
				
				//System.out.println("*******************************");
				System.out.println("*********"+criteriaDTO.getCriteriaName()+"****************");
				
				if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_CIRCLE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableCircle(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalCircle(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setCircleRequired(true);
					idList.add(ReportConstants.CRITERIA_CIRCLE);
					
				}
			/*	else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_BUSINESS_CATEGORY))
				{
					System.out.println("Business category....................");
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnablebusinessCategory(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalBusinessCategory(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setBusinessCategoryRequired(true);
					//idList.add(ReportConstants.CRITERIA_BUSINESS_CATEGORY);// commmmentin it business category not needed
				}*/
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_TSM))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableTSM(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalTSM(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setTsmRequired(true);
					idList.add(ReportConstants.CRITERIA_TSM);
					
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DISTRIBUTOR))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableDistributor(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalDistributor(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setDistributorRequired(true);
					idList.add(ReportConstants.CRITERIA_DISTRIBUTOR);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_COLLECTION_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableCollectionType(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalCollectionType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setCollectionTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_COLLECTION_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_STB_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableSTBType(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalSTBType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setStbTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_STB_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_PRODUCT_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableProductType(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalProductType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setProductTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_PRODUCT_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_PO_STATUS))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnablePOStatus(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalPOStatus(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setPoStatusRequired(true);
					idList.add(ReportConstants.CRITERIA_PO_STATUS);
				}
			/*	else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DATEOPTION))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableDateOption(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalDateOption(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setDateOptionRequired(true);
					//idList.add(ReportConstants.CRITERIA_DATEOPTION);//commenting it , its nott need for this
				}
				*/
				//added by aman
				
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_ACTIVITY))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableActivity(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalActivity(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setActivityRequired(true);
					idList.add(ReportConstants.CRITERIA_ACTIVITY);
				}
				// end of changes by aman
				/*else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_FROM_DATE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableFromDate(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalFromDate(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setFromDateRequired(true);
					idList.add(ReportConstants.CRITERIA_FROM_DATE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_TO_DATE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableToDate(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalToDate(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setToDateRequired(true);
					idList.add(ReportConstants.CRITERIA_TO_DATE);
				}*/
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_SEARCH_OPTION))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableSearchOption(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalSearchOption(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setSearchOptionRequired(true);
					idList.add(ReportConstants.CRITERIA_SEARCH_OPTION);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_SEARCH_CRITERIA))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableSearchCriteria(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalSearchCriteria(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setSearchCreteriaRequired(true);
					idList.add(ReportConstants.CRITERIA_SEARCH_CRITERIA);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_STATUS))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableStatus(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalStatus(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setStatusRequired(true);
					idList.add(ReportConstants.CRITERIA_STATUS);
				}		
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_PENDING_AT))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnablePendingAt(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalPendingAt(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setPendingAtRequired(true);
					idList.add(ReportConstants.CRITERIA_PENDING_AT);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_TRANSFER_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableTransferType(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvaltransferType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setTransferTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_TRANSFER_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_RECO_STATUS))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnablerecoStatus(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalrecoStatus(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setRecoStatusRequired(true);
					idList.add(ReportConstants.CRITERIA_RECO_STATUS);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_STB_STATUS))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableStbStatus(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalStbStatus(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setStbStatusRequired(true);
					idList.add(ReportConstants.CRITERIA_STB_STATUS);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_STOCK_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableStockType(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalStockType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setStockTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_STOCK_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_FSE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableFSE(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalFse(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setFseRequired(true);
					idList.add(ReportConstants.CRITERIA_FSE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_RETAILER))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableRetailer(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalRetailer(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setRetailerRequired(true);
					idList.add(ReportConstants.CRITERIA_RETAILER);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DCSTATUS))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableDCStatus(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalDCStatus(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setDcStatusRequired(true);
					idList.add(ReportConstants.CRITERIA_DCSTATUS);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_ACCOUNT_TYPE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableAccountType(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalaccountType(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setAccountTypeRequired(true);
					idList.add(ReportConstants.CRITERIA_ACCOUNT_TYPE);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_ACCOUNT_NAME))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableAccountName(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalaccountName(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setAccountNameRequired(true);
					idList.add(ReportConstants.CRITERIA_ACCOUNT_NAME);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DC_NO))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableDCNo(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalDCNo(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setDcNoRequired(true);
					idList.add(ReportConstants.CRITERIA_DC_NO);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_RECO_PERIOD))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableRecoPeriod(criteriaDTO.isEnabledFlag());
					}
					 if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalRecoPeriod(criteriaDTO.isInitvalFlag());
					}
					genericReportsFormBean.setRecoPeriodRequired(true);
					idList.add(ReportConstants.CRITERIA_RECO_PERIOD);
				}
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_DATE))
				{
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableDate(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalDateOption(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setDateRequired(true);
					idList.add(ReportConstants.CRITERIA_DATE);
				}
				
				
				
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_RECO))
				{
					//System.out.println("criteriaDTO.getCriteriaName()::::::"+criteriaDTO.getCriteriaName());
					if(criteriaDTO.isEnabledFlag())
					{
						genericReportsFormBean.setEnableReco(criteriaDTO.isEnabledFlag());
					}
					if(criteriaDTO.isInitvalFlag())
					{
						genericReportsFormBean.setInitvalReco(criteriaDTO.isInitvalFlag());
					}
					
					genericReportsFormBean.setRecoRequired(true);
					idList.add(ReportConstants.CRITERIA_RECO);
					//System.out.println("Reco true*************");
				}
				
				
				else if(criteriaDTO.getCriteriaName().equalsIgnoreCase(ReportConstants.CRITERIA_BLANK))
				{
					idList.add(ReportConstants.CRITERIA_BLANK);
				}
			}
			request.setAttribute("idList",idList);
			genericReportsFormBean.setCriteriaList(criteriaList);
	}
	
	private void populateInitialValues(UserSessionContext sessionContext, GenericReportsFormBean genericReportsFormBean,int reportId)
	throws DPServiceException, DAOException {

DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
GenericReportsService reportService = GenericReportsServiceImpl.getInstance();
List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
List<SelectionCollection> listTsm = new ArrayList<SelectionCollection>();
List<SelectionCollection> listDist = new ArrayList<SelectionCollection>();
int groupId = sessionContext.getGroupId();
String accountID = String.valueOf(sessionContext.getId());
String circleIdSr = String.valueOf(sessionContext.getCircleId());
//HashMap<Integer, String> idMap=new HashMap<Integer, String>();
genericReportsFormBean.setGroupId(String.valueOf(groupId));
if (genericReportsFormBean.isCircleRequired() && genericReportsFormBean.isInitvalCircle())
	{
		logger.info("groupId:::::"+groupId);
		if (groupId == Constants.ADMIN_GROUP_ID || groupId == com.ibm.dp.common.Constants.DTH_ADMIN_GROUP_ID) {
			listAllCircles = utilityAjaxService.getAllCircles();
			/*for (int i=0;i<listAllCircles.size();i++){
				logger.info("groupId:::::...... circle s"+listAllCircles.get(i).getStrText());
				}*/
			genericReportsFormBean.setCircleList(listAllCircles);
		} 
		else if (groupId == com.ibm.dp.common.Constants.TSM_GROUP_ID || groupId == com.ibm.dp.common.Constants.GROUP_ID_ZBM  || groupId == com.ibm.dp.common.Constants.GROUP_ID_ZSM  || groupId==com.ibm.dp.common.Constants.GROUP_ID_CIRCLE_ADMIN) { //circle admin added by neetika for incident IN1821528
			listAllCircles = utilityAjaxService.getTsmCircles(sessionContext.getId());
			
			/*for (int i=0;i<listAllCircles.size();i++){
			logger.info("groupId:::::...tsm zsm zbm  circles"+listAllCircles.get(i).getStrText());
			}*/
			genericReportsFormBean.setCircleList(listAllCircles);
		} 
		else {
			
			SelectionCollection sc = new SelectionCollection();
			sc.setStrText(sessionContext.getCircleName());
			sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
			logger.info("groupId:::::... circle s"+sessionContext.getCircleId());
			listAllCircles.add(sc);
			genericReportsFormBean.setCircleList(listAllCircles);

	}
	
	
}
if (genericReportsFormBean.isTsmRequired() && genericReportsFormBean.isInitvalTSM())
	{
	logger.info("circleIdSr::::::::::::::::::::::::::::::::::"+circleIdSr);
	if (groupId == com.ibm.dp.common.Constants.TM) {  //if TSM logged in
		SelectionCollection sc1 = new SelectionCollection();
		sc1.setStrValue(accountID);
		sc1.setStrText(sessionContext.getAccountName());
		listTsm = null;
		listTsm = new ArrayList<SelectionCollection>();
		listTsm.add(sc1);
		genericReportsFormBean.setTsmList(listTsm);
	} else { 
		logger.info("grp::::::::::::::::::::::::::::::::::"+groupId+"sessionContext.getId() "+sessionContext.getId());
	//	if (groupId == Constants.GROUP_ID_ZBM  || groupId == Constants.GROUP_ID_ZSM  ) {
		if (groupId == com.ibm.dp.common.Constants.GROUP_ID_ZBM  || groupId == com.ibm.dp.common.Constants.GROUP_ID_ZSM ) { 
			listTsm = utilityAjaxService.getAllAccountsMultipleCircle(com.ibm.dp.common.Constants.ACC_LEVEL_TSM, sessionContext.getId());
			genericReportsFormBean.setTsmList(listTsm);
		} 
		else if(groupId ==com.ibm.dp.common.Constants.GROUP_ID_CIRCLE_ADMIN ) { //changed by neetika
			listTsm = utilityAjaxService.getAllAccountsCircleAdmin(com.ibm.dp.common.Constants.ACC_LEVEL_TSM, sessionContext.getId(),circleIdSr);
			genericReportsFormBean.setTsmList(listTsm);
		}
		else 
		{
			listTsm = utilityAjaxService.getAllAccountsSingleCircle(com.ibm.dp.common.Constants.ACC_LEVEL_TSM,circleIdSr );
			genericReportsFormBean.setTsmList(listTsm);
		}
	}	
	

}
if (genericReportsFormBean.isDistributorRequired() && genericReportsFormBean.isInitvalDistributor())
	{
	if (groupId == com.ibm.dp.common.Constants.DISTIBUTOR || groupId == com.ibm.dp.common.Constants.FOS || groupId == com.ibm.dp.common.Constants.DEALER) {
			/*SelectionCollection sc1 = new SelectionCollection();
			sc1.setStrValue("-1");
			sc1.setStrText("--All--");
			listDist.add(sc1);*/
			SelectionCollection sc1 = new SelectionCollection();
			sc1.setStrValue(Long.toString(sessionContext.getId()));
			sc1.setStrText(sessionContext.getAccountName());
			listDist.add(sc1);
			genericReportsFormBean.setDistId(sc1.getStrValue());
			genericReportsFormBean.setDistList(listDist);
			//listTsm = utilityAjaxService.getAllAccountsSingleCircle(Constants.ACC_LEVEL_TSM,circleIdSr);
			//genericReportsFormBean.setTsmList(listTsm);
			
			listTsm = utilityAjaxService.getParent(accountID);
			genericReportsFormBean.setTsmList(listTsm);
			genericReportsFormBean.setTsmId(listTsm.get(0).getStrValue());
		}
		if (groupId == com.ibm.dp.common.Constants.ZSM || groupId == com.ibm.dp.common.Constants.TM || groupId == com.ibm.dp.common.Constants.ZBM) {
			listDist = utilityAjaxService.getAllAccountsUnderSingleAccount(accountID);
			genericReportsFormBean.setDistList(listDist);
		}

	
}
if (genericReportsFormBean.isStbTypeRequired() && genericReportsFormBean.isInitvalSTBType())
{
	List<DpProductCategoryDto> listProduct = reportService.getProductList(reportId);
		genericReportsFormBean.setProductList(listProduct);
		
		

}
if (genericReportsFormBean.isProductTypeRequired() && genericReportsFormBean.isInitvalProductType())
{
	List<DpProductCategoryDto> listProduct = reportService.getProductList(reportId);
		genericReportsFormBean.setProductList(listProduct);
		
		

}
logger.info("genericReportsFormBean.isBusinessCategoryRequired()::"+genericReportsFormBean.isBusinessCategoryRequired());
logger.info("genericReportsFormBean.isInitvalBusinessType()::"+genericReportsFormBean.isInitvalBusinessCategory());
if (genericReportsFormBean.isBusinessCategoryRequired() && genericReportsFormBean.isInitvalBusinessCategory())
{
	List<DpProductCategoryDto> listBusinessCategory = reportService.getTransactionType(reportId);
	logger.info("listBusinessCategory size::"+listBusinessCategory.size());
		genericReportsFormBean.setBusinessCategoryList(listBusinessCategory);
}
if (genericReportsFormBean.isCollectionTypeRequired() && genericReportsFormBean.isInitvalCollectionType())
	{
	List<CollectionReportDTO> collectionType = reportService.getCollectionTypeMaster(reportId);
	genericReportsFormBean.setCollectionType(collectionType);
	
	
}

if (genericReportsFormBean.isDateOptionRequired() && genericReportsFormBean.isInitvalDateOption())
{

	List<GenericOptionDTO> dateOptionList = reportService.getDateOptions(reportId);
	genericReportsFormBean.setDateOptionList(dateOptionList);

}

//added by aman
if (genericReportsFormBean.isActivityRequired() && genericReportsFormBean.isInitvalActivity())
{

	List<GenericOptionDTO> activityList = reportService.getActivity(reportId);
	genericReportsFormBean.setActivityList(activityList);

}

//end of changes by aman

if (genericReportsFormBean.isPoStatusRequired() && genericReportsFormBean.isInitvalPOStatus())
{

	List<GenericOptionDTO> poStatusList = reportService.getPOStatusList();
	genericReportsFormBean.setPoStatusList(poStatusList);

}
if (genericReportsFormBean.isSearchOptionRequired() && genericReportsFormBean.isInitvalSearchOption())
{

	List<GenericOptionDTO> searchOptionList = reportService.getSearchOptions(reportId);
	genericReportsFormBean.setSearchOptionList(searchOptionList);

}

if (genericReportsFormBean.isPendingAtRequired() && genericReportsFormBean.isInitvalPendingAt())
{

	List<GenericOptionDTO> pendingAtList = reportService.getPendingOptions(reportId);
	genericReportsFormBean.setPendingAtList(pendingAtList);

}
if (genericReportsFormBean.isTransferTypeRequired() && genericReportsFormBean.isInitvaltransferType())
{

	List<GenericOptionDTO> transferTypeList = reportService.getTransferTypeOptions(reportId);
	genericReportsFormBean.setTransferTypeList(transferTypeList);

}
if (genericReportsFormBean.isStatusRequired() && genericReportsFormBean.isInitvalStatus())
{

	List<GenericOptionDTO> statusOptionList = reportService.getStatusOptions(reportId);
	genericReportsFormBean.setStatusOptionList(statusOptionList);

}
if (genericReportsFormBean.isRecoStatusRequired() && genericReportsFormBean.isInitvalrecoStatus())
{

	List<GenericOptionDTO> recoStatusList = reportService.getRecoStatus(reportId);
	genericReportsFormBean.setRecoStatusList(recoStatusList);

}
if (genericReportsFormBean.isStbStatusRequired() && genericReportsFormBean.isInitvalStbStatus())
{

	List<GenericOptionDTO> stbStatusList = reportService.getSTBStatusList(reportId);
	genericReportsFormBean.setStbStatusList(stbStatusList);

}
if (genericReportsFormBean.isStockTypeRequired() && genericReportsFormBean.isInitvalStockType())
{

	List<GenericOptionDTO> stockTypeList = reportService.getStockType();
	genericReportsFormBean.setStockTypeList(stockTypeList);

}
if (genericReportsFormBean.isDcStatusRequired() && genericReportsFormBean.isInitvalDCStatus())
{

	List<GenericOptionDTO> dcStatusList = reportService.getDcStatusList(reportId);
	//System.out.println("size:"+dcStatusList.size());
	genericReportsFormBean.setDcStatusList(dcStatusList);

}
if (genericReportsFormBean.isAccountTypeRequired() && genericReportsFormBean.isInitvalaccountType())
{

	List<GenericOptionDTO> accountTypeList = reportService.getAccountTypeList(reportId, groupId);
	genericReportsFormBean.setAccountTypeList(accountTypeList);

}
if (genericReportsFormBean.isAccountNameRequired() && genericReportsFormBean.isInitvalaccountName())
{

	

}
if (genericReportsFormBean.isFseRequired() && genericReportsFormBean.isInitvalFse())
{
	if(groupId ==com.ibm.dp.common.Constants.DISTIBUTOR){	
	List<SelectionCollection> fseList= new ArrayList<SelectionCollection>();				
	
	SelectionCollection sc = new SelectionCollection();
	sc.setStrText(sessionContext.getCircleName());
	sc.setStrValue((Integer.toString(sessionContext.getCircleId())));
	
	SelectionCollection sc1 = new SelectionCollection();
	sc1.setStrValue(Long.toString(sessionContext.getId()));
	sc1.setStrText(sessionContext.getLoginName());					
	fseList = utilityAjaxService.getAllAccountsUnderMultipleAccounts(Long.toString(sessionContext.getId()));
	genericReportsFormBean.setFseList(fseList);
	genericReportsFormBean.setHiddendistIds(Long.toString(sessionContext.getId()));
	}
}

if (genericReportsFormBean.isRecoRequired() && genericReportsFormBean.isInitvalReco())
{

	List<GenericOptionDTO> recoPeriodList = reportService.getRecoPeriodList(reportId, groupId);
	//System.out.println("recoPeriodList   size in action:::"+recoPeriodList.size());
	genericReportsFormBean.setRecoPeriodList(recoPeriodList);

}


}
}
