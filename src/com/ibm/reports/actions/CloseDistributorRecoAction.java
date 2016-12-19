package com.ibm.reports.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPDistPinCodeMapFormBean;
import com.ibm.dp.beans.StbFlushOutFormBean;
import com.ibm.dp.beans.StockRecoSummReportBean;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DPDistPinCodeMapDto;
import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.dto.GenericReportPararmeterDTO;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.reports.common.ReportConstants;
import com.ibm.dp.service.CloseDistributorRecoService;
import com.ibm.dp.service.DPDistPinCodeMapService;
import com.ibm.dp.service.RecoDetailReportService;
import com.ibm.dp.service.StockRecoSummReportService;
import com.ibm.dp.service.impl.CloseDistributorRecoServiceImpl;
import com.ibm.dp.service.impl.DPDistPinCodeMapServiceImpl;
import com.ibm.dp.service.impl.RecoDetailReportServiceImpl;
import com.ibm.dp.service.impl.StockRecoSummReportServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.service.DropDownUtilityAjaxService;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.reports.beans.CloseDistributorRecoBean;
import com.ibm.reports.beans.GenericReportsFormBean;
import com.ibm.reports.dto.CriteriaDTO;
import com.ibm.reports.dto.GenericOptionDTO;
import com.ibm.reports.dto.GenericReportsOutputDto;
import com.ibm.reports.dto.ReportDetailDTO;
import com.ibm.reports.service.GenericReportsService;
import com.ibm.reports.service.impl.GenericReportsServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;

public class CloseDistributorRecoAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(CloseDistributorRecoAction.class
			.getName());
	private static final String INIT_SUCCESS = "initsuccess";
	private static final String INIT_EXCEL = "initExcel";
	private static final String INIT_EXCEL_RECO = "initExcel_reco";
	private static final String ERROR = "error";
	private static final String REPORT_ID = "reportId";
	private static final String OUTPUT = "output";
	
	public ActionForward closeRecoForDistributorInit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("**********inside CloseDistributorRecoAction ********");
		ActionErrors errors = new ActionErrors();
		
		try {
			CloseDistributorRecoBean bean = (CloseDistributorRecoBean) form;
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			GenericReportsService genericReportsService = GenericReportsServiceImpl.getInstance();
			int groupId = sessionContext.getGroupId();
			
			populateInitialValues(sessionContext, bean);
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			productList = utilityAjaxService.getProductCategoryLst1();		
			bean.setProductList(productList);
			//session.setAttribute("productList", productList);
			//ajaxCall(request,response,productList);
			List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
			List recoListTotal = new ArrayList();	
			
			CloseDistributorRecoService service = CloseDistributorRecoServiceImpl.getInstance();
			//recoList = service.getRecoHistory();
			
			recoList = service.getRecoHistoryNotClosed();
		
			for(RecoPeriodDTO recoPeriodDTO :recoList){
				SelectionCollection recoPeriod=new SelectionCollection();
				 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
				 /* Commented By Parnika 
				 recoPeriod.setStrValue( recoPeriodDTO.getToDate()); */
				 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());
				recoListTotal.add(recoPeriod);
			}
			
			bean.setRecoListTotal(recoListTotal);

		} catch (DAOException se) { 
			
			logger.error("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		} catch (DPServiceException se) {
			logger.info("EXCEPTION OCCURED ::  " + se.getMessage());
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		} catch (Exception e) {
			logger.info("EXCEPTION OCCURED ::  " + e.getMessage());
			errors.add("errors", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(ERROR);
		}
		return mapping.findForward(INIT_SUCCESS);
	}


	private void populateInitialValues(UserSessionContext sessionContext, CloseDistributorRecoBean bean)
			throws DPServiceException, DAOException {

		DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
		GenericReportsService reportService = GenericReportsServiceImpl.getInstance();
		List<SelectionCollection> listAllCircles = new ArrayList<SelectionCollection>();
		List<SelectionCollection> listTsm = new ArrayList<SelectionCollection>();
		List<SelectionCollection> listDist = new ArrayList<SelectionCollection>();
		int groupId = sessionContext.getGroupId();
		String accountID = String.valueOf(sessionContext.getId());
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		bean.setGroupId(String.valueOf(groupId));
		logger.info("groupId:::::"+groupId);
				if (groupId == Constants.ADMIN_GROUP_ID || groupId == Constants.DTH_ADMIN_GROUP_ID) {
					listAllCircles = utilityAjaxService.getAllCirclesForDTHAdmin();
					bean.setCircleList(listAllCircles);
				} 
				
	}
		
		
	private void ajaxCall(HttpServletRequest request, HttpServletResponse response, List productList)throws Exception{

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		DpProductCategoryDto productMasterDto = null;

		for (int counter = 0; counter < productList.size(); counter++) {

			optionElement = root.addElement("option");
			productMasterDto = (DpProductCategoryDto)productList.get(counter);
			if (productMasterDto != null) {
				optionElement.addAttribute("value", String.valueOf(productMasterDto.getProductCategoryId()));		
				optionElement.addAttribute("text", String.valueOf(productMasterDto.getProductCategory()));	
			} else {
				optionElement.addAttribute("value", "None");		
				optionElement.addAttribute("text", "None");	
			}				
		}

		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();		
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush(); 
		out.flush(); 
	}

	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{	ActionErrors errors = new ActionErrors();
		logger.info("In exportExcel()  of StockRecoSummReportAction");
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String accountID = String.valueOf(sessionContext.getId());
		String circleIdSr = String.valueOf(sessionContext.getCircleId());
		int loginRole = Integer.parseInt(sessionContext.getAccountLevel());
		
		try {
			CloseDistributorRecoBean bean = (CloseDistributorRecoBean) form;
			String circleList = bean.getHiddenCircleSelecIds();
			String tsmList = bean.getHiddenTsmSelecIds();
			String distList = bean.getHiddenDistSelecIds();
			String prodList = bean.getHiddenProductSeletIds();
			String recoPeriod = bean.getRecoID();
			String distType = bean.getOknotok();
			
			
			//System.out.println("Circle List in Action : "+circleList);
			//System.out.println("TSM List in Action : "+tsmList);
			//System.out.println("Distributor List in Action : "+distList);
			//System.out.println("Product List in Action : "+prodList);
			//System.out.println("Reco Period in Action : "+recoPeriod);
			//System.out.println("Distributor Type in Action : "+distType);

			session.removeAttribute("recoPeriod");
			session.setAttribute("recoPeriod", recoPeriod);
			System.out.println("session.getAttribute(recoPeriod) exportExcel : "+session.getAttribute("recoPeriod").toString());

			CloseDistributorRecoService service =new CloseDistributorRecoServiceImpl();
			List<DistRecoDto> detailsList = service.getExportToExcel(circleList, tsmList, distList, prodList,recoPeriod,distType);
			request.setAttribute("detailedReport", detailsList);
			bean.setDetailsList(detailsList);
			
			String filePath = (String) getResources(request).getMessage("UPLOAD_PATH_DAMAGE") + "/" + "closeDistributorReco.xls";
 		    ServletOutputStream out = response.getOutputStream();
 		    //System.out.println("filePath"+filePath);
 			response.setHeader( "Pragma", "public" );
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Cache-Control", "must-revalidate");
			response.setHeader("Content-Disposition", "attachment;filename=closeDistributorReco.xls");
			
			 InputStream in = null;
	 		   String path=ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DOWNLOAD_FILE);
				String fpath=path  + "closeDistributorReco.xls";
				logger.info("fpath=in flush out ="+fpath);
				in = new FileInputStream(fpath);
		        // Create a workbook 
	            HSSFWorkbook workbook = new HSSFWorkbook(in);
	            logger.info("fpath=in flush out after workbook ="+fpath);
	            HSSFSheet sheet = workbook.getSheetAt(0);
				Iterator rows = sheet.rowIterator();
				int rowNumber = 0;
				HSSFRow row = (HSSFRow) rows.next();  
				HSSFCell cell;

				DistRecoDto distDto = null;
				while (rowNumber < detailsList.size() ) 
				{
					distDto = detailsList.get(rowNumber);
					 row = sheet.createRow(++rowNumber);
		             	            
		             	cell = row.createCell(0);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        			cell.setCellValue(rowNumber);

	        			cell = row.createCell(1);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getDistOlmId()) || null == distDto.getDistOlmId())
		        			cell.setCellValue(" ");
		        		else
		        			cell.setCellValue(distDto.getDistOlmId());
	        			
	        			cell = row.createCell(2);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getAccountName()) || null == distDto.getAccountName())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getAccountName());
	        			
	        			cell = row.createCell(3);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getCircleName()) || null == distDto.getCircleName())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getCircleName());
	        			
	        			cell = row.createCell(4);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getTsmName()) || null == distDto.getTsmName())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getTsmName());
	        			
	        			cell = row.createCell(5);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getProductName()) || null == distDto.getProductName())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getProductName()); 

		             	cell = row.createCell(6);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getTypeOfStock()) || null == distDto.getTypeOfStock())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getTypeOfStock());

	        			cell = row.createCell(7);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getSerialNo()) || null == distDto.getSerialNo())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getSerialNo());
	        			
	        			cell = row.createCell(8);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getDistFlag()) || null == distDto.getDistFlag())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getDistFlag());
	        			
	        			cell = row.createCell(9);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getDistRemarks()) || null == distDto.getDistRemarks())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getDistRemarks());
	        			
	        			cell = row.createCell(10);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getFlushOutFlag()) || null == distDto.getFlushOutFlag())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getFlushOutFlag()); 

	        			cell = row.createCell(11);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getDebitFlag()) || null == distDto.getDebitFlag())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getDebitFlag()); 
		        		
		             	cell = row.createCell(12);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getDthRemarks()) || null == distDto.getDthRemarks())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getDthRemarks());
		        		
		        		cell = row.createCell(13);
		        		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		        		if("".equals(distDto.getOtherRemarks()) || null == distDto.getOtherRemarks())
		        			cell.setCellValue(" ");
		        		else 
		        			cell.setCellValue(distDto.getOtherRemarks());
	        			
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
					
		}catch (IOException ioe) { 
	    	
	    	 logger.info("Message from FLUSHOUT =================================="+ioe.getMessage());
	    	 ioe.printStackTrace();
			  // if this happens there is probably no way to report the error to the user
			  if (!response.isCommitted()) {
			    response.setContentType("text/html");
			    logger.error("Error in writing XLS file");
			  }
	     } 
		catch (RuntimeException e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		//return mapping.findForward("detailedReportExcel");
		return  null;
	}
	
	public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
			{
			logger.info("-----------CloseDistributorRecoAction------init uploadExcel CALLED-----------------");
			ActionErrors errors = new ActionErrors();
			CloseDistributorRecoBean bean = (CloseDistributorRecoBean) form;
			ActionMessages messages = new ActionMessages();
			try 
			{	
				String recoPeriodId = null;
				HttpSession session = request.getSession();	
				UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				long loginUserId1 = sessionContext.getId();
				String loginUserId=String.valueOf(loginUserId1);
				logger.info("session.getAttribute(recoPeriod) exportExcel : "+session.getAttribute("recoPeriod"));
				logger.info("Session User Login Id : "+loginUserId);
				//String recoPeriodToDate = session.getAttribute("recoPeriod").toString();
				//logger.info("recoPeriodToDate In Action : "+recoPeriodToDate);
				if(request.getParameter("recoId") != null){
					recoPeriodId = request.getParameter("recoId").toString();
				}			
				bean = (CloseDistributorRecoBean) form;
				FormFile file = bean.getUploadedFile();
				InputStream myxls = file.getInputStream();
				CloseDistributorRecoService excelBulkupload = new CloseDistributorRecoServiceImpl();
				String arr= (file.getFileName().toString()).substring(file.getFileName().toString().lastIndexOf(".")+1,file.getFileName().toString().length());
				logger.info("*********1.."+arr);
				String special=ResourceReader.getCoreResourceBundleValue("specialChars");
				boolean found=false;
				boolean found2=false;
							
				
				if(!arr.equalsIgnoreCase("xls"))
				{
				logger.info("*********2..");
				bean.setStrmsg("Only XLS File is allowed here");
				found=true;
				}
				 if(found==false)
				{
				for (int i=0; i<special.length(); i++) 
				{
				   if ((file.getFileName().toString()).indexOf(special.charAt(i)) > 0) {
				     
				      bean.setStrmsg("Special Characters are not allowed in File Name");
				      logger.info("*********Special Characters are not allowed in File Name");
				      found2=true;
				      break;
				   }
				}
				if ((file.getFileName().toString()).indexOf("..")>-1)
				{
					 found2=true;	
					 bean.setStrmsg("2 consecutive dots are not allowed in File Name");
					 logger.info("*********2 consecutive dots are not allowed in File Name");
				}
				}
				 if(found2==false && found==false)
				{
				logger.info("*********3.. FNC correct");
				Map dataToFlush = excelBulkupload.uploadExcel(myxls, recoPeriodId);
				session.removeAttribute("error_file");
				session.setAttribute("error_file",dataToFlush.get("error"));
				DistRecoDto dto = null;
				boolean checkValidate = true;
				if(dataToFlush!=null && dataToFlush.size() > 0)
				{
					logger.info("Size of list in action::"+dataToFlush.size());
					Iterator itr = ((List<DistRecoDto>) dataToFlush.get("error")).iterator();
					while(itr.hasNext())
					{
						dto = (DistRecoDto) itr.next();
						logger.info("dto.getErr_msg():::::"+dto.getErr_msg());
						if(dto.getErr_msg()!= null && !dto.getErr_msg().equalsIgnoreCase("SUCCESS"))
						{
							checkValidate = false;
							break;
						} 
					}
				}

					if(checkValidate)
					{
						bean.setError_flag("false");
						logger.info("***File is correct. Now goes for DB transaction****");
						if((List)dataToFlush.get("upload")!=null && (List)dataToFlush.get("query")!=null)
						{
							String strMessage = excelBulkupload.uploadToSystem((List<DistRecoDto>)dataToFlush.get("error"), (List<DuplicateSTBDTO>)dataToFlush.get("upload"),(List<DuplicateSTBDTO>)dataToFlush.get("query"),recoPeriodId,loginUserId);
							bean.setStrmsg(strMessage);
						
						}
						// For populating the details after form submission

						populateInitialValues(sessionContext, bean);
						List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
						DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
						productList = utilityAjaxService.getProductCategoryLst1();		
						bean.setProductList(productList);
						List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
						List recoListTotal = new ArrayList();	
						
						CloseDistributorRecoService service = CloseDistributorRecoServiceImpl.getInstance();		
						recoList = service.getRecoHistoryNotClosed();	
						for(RecoPeriodDTO recoPeriodDTO :recoList){
							SelectionCollection recoPeriod=new SelectionCollection();
							 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
							 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());
							recoListTotal.add(recoPeriod);
						}
						
						bean.setRecoListTotal(recoListTotal);
						// End For populating the details after form submission

						return mapping.findForward(INIT_SUCCESS);

					}
					else
					{
						logger.info("Error in File:::"+dataToFlush.size());
						bean.setError_flag("true");
						bean.setError_file((List) dataToFlush.get("error"));
						bean.setStrmsg("");
						
						// For populating the details after form submission

						populateInitialValues(sessionContext, bean);
						List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
						DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
						productList = utilityAjaxService.getProductCategoryLst1();		
						bean.setProductList(productList);
						List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
						List recoListTotal = new ArrayList();	
						
						CloseDistributorRecoService service = CloseDistributorRecoServiceImpl.getInstance();		
						recoList = service.getRecoHistoryNotClosed();	
						for(RecoPeriodDTO recoPeriodDTO :recoList){
							SelectionCollection recoPeriod=new SelectionCollection();
							 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
							 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());
							recoListTotal.add(recoPeriod);
						}
						
						bean.setRecoListTotal(recoListTotal);
						// End For populating the details after form submission

						
						return mapping.findForward(INIT_SUCCESS);	

					}
				}
				

		}
			catch(Exception e)
			{
				e.printStackTrace();
				logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
				//errors.add("errors",new ActionError("errors.excelupload",e.getMessage()));
				bean.setStrmsg("Please Upload a valid Excel Format File ");
				messages.add("WRONG_FILE_UPLOAD", new ActionMessage("upload.error.file",e.getMessage()));
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("upload.error.file"));
				saveErrors(request, errors);
				saveMessages(request, messages);
				//request.setAttribute("errors", e.getMessage());
				
				// For populating the details after form submission
				
				HttpSession session = request.getSession();
				UserSessionContext sessionContext = (UserSessionContext) session
						.getAttribute(Constants.AUTHENTICATED_USER);
				populateInitialValues(sessionContext, bean);
				List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
				DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
				productList = utilityAjaxService.getProductCategoryLst1();		
				bean.setProductList(productList);
				List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
				List recoListTotal = new ArrayList();	
				
				CloseDistributorRecoService service = CloseDistributorRecoServiceImpl.getInstance();		
				recoList = service.getRecoHistoryNotClosed();	
				for(RecoPeriodDTO recoPeriodDTO :recoList){
					SelectionCollection recoPeriod=new SelectionCollection();
					 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
					 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());
					recoListTotal.add(recoPeriod);
				}
				
				bean.setRecoListTotal(recoListTotal);
				// End For populating the details after form submission
				return mapping.findForward(INIT_SUCCESS);
			}
			
			// For populating the details after form submission
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			populateInitialValues(sessionContext, bean);
			List<DpProductCategoryDto> productList = new ArrayList<DpProductCategoryDto>();	
			DropDownUtilityAjaxService utilityAjaxService = new DropDownUtilityAjaxServiceImpl();
			productList = utilityAjaxService.getProductCategoryLst1();		
			bean.setProductList(productList);
			List<RecoPeriodDTO> recoList = new ArrayList<RecoPeriodDTO>();	
			List recoListTotal = new ArrayList();	
			
			CloseDistributorRecoService service = CloseDistributorRecoServiceImpl.getInstance();		
			recoList = service.getRecoHistoryNotClosed();	
			for(RecoPeriodDTO recoPeriodDTO :recoList){
				SelectionCollection recoPeriod=new SelectionCollection();
				 recoPeriod.setStrText(recoPeriodDTO.getFromDate() + " to " +recoPeriodDTO.getToDate());
				 recoPeriod.setStrValue( recoPeriodDTO.getRecoPeriodId());
				recoListTotal.add(recoPeriod);
			}
			
			bean.setRecoListTotal(recoListTotal);
			// End For populating the details after form submission
			
		return mapping.findForward(INIT_SUCCESS);
}
	
	
	public String getLastSchedulerDate(int groupId, String distData, String otherData) throws DPServiceException, DAOException {
		GenericReportsService genericService = GenericReportsServiceImpl.getInstance();
		
		return genericService.getLastSchedulerDate(groupId,distData,otherData);
	}

	public ActionForward getReportDownloadStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception 
	{
		//System.out.println("inside getReportDownloadStatus");
		try
		{
			HttpSession session = request.getSession();
			String reportId = request.getParameter("reportId");
			String status = (String) session.getAttribute("Report"+reportId);
			String result = "";
			if(status != null)
				result = status;
			//logger.info("result:" + result);

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			XMLWriter writer = new XMLWriter(out);
			writer.write(result);
			writer.flush();
			out.flush();
			//logger.info("out now getReportDownloadStatus");

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error("Exception occured while getReportDownloadStatus  ::  "+e.getMessage());
		}
		return null;
	}
	
	
	
	private int getDateValidation(int groupId, String distData, String otherData) {
		if (groupId != Constants.DISTIBUTOR && otherData.equals("0")) {
			return 0;
		} else if (groupId == Constants.DISTIBUTOR && distData.equals("0")) {
			return 0;
		} else {
			return -1;
		}
	}
	
	private int getMsgValidation(int groupId, String distData, String otherData) {
		if (groupId != Constants.DISTIBUTOR && otherData.equals("0")) {
			return 0;
		} else if (groupId == Constants.DISTIBUTOR && distData.equals("0")) {
			return 0;
		} else {
			return -1;
		}
	}
	
	public ActionForward showErrorFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception 
	{
		CloseDistributorRecoBean formBean = (CloseDistributorRecoBean) form;
		logger.info("*******************"+formBean.getError_file().size());
		logger.info("***Excel Action:::"+((List)(request.getSession()).getAttribute("error_file")).size());
		return mapping.findForward("errorFile");
	}
	
}