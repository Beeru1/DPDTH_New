package com.ibm.dp.actions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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

import com.ibm.dp.beans.DPCreateAccountFormBean;
import com.ibm.dp.beans.RejectedInvBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.dp.dto.NonSerializedToSerializedDTO;
import com.ibm.dp.dto.RejectedInvoiceDTO;
import com.ibm.dp.dto.RejectedPartnerInvDTO;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.DPMasterService;
import com.ibm.virtualization.recharge.service.RejectedInvoiceService;
import com.ibm.virtualization.recharge.service.impl.DPMasterServiceImpl;
import com.ibm.virtualization.recharge.service.impl.RejectedInvoiceServiceImpl;

public class RejectedInvoiceReportAction extends DispatchAction {
	private static final String SHOW_DOWNLOAD_AGGREGATE_LIST = "listAggregateExport";
	private static final String INIT_SUCCESS = "success";
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		HttpSession session = request.getSession();
		RejectedInvoiceService rejInvSer = new RejectedInvoiceServiceImpl();
		RejectedInvBean rejReportBean = (RejectedInvBean)form;
		try{
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		long loginUserId = sessionContext.getId();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(), ChannelType.WEB,
				AuthorizationConstants.ROLE_ADD_ACCOUNT)) {
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		
		}
		
		ArrayList<SelectionCollection> monthList= new ArrayList<SelectionCollection>();
		
		for(int i=0 ;i< rejInvSer.getDateList().size(); i++){
		SelectionCollection sc1 = new SelectionCollection();
		sc1.setStrValue(Long.toString(sessionContext.getId()));
			//sc1.setStrValue(Long.toString());	
		sc1.setStrText(rejInvSer.getDateList().get(i));
		monthList.add(sc1);
		rejReportBean.setMonthId(sc1.getStrValue());
		rejReportBean.setMonthList(monthList);
		}
		
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return mapping.findForward(INIT_SUCCESS);
	}
	
	public ActionForward downloadInvoiceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		
		ActionErrors errors = new ActionErrors();
		RejectedInvBean rejFormBean = (RejectedInvBean) form;
		List<RejectedInvoiceDTO> invList=null;
		List<RejectedPartnerInvDTO> partnerList=null;
		InputStream in = null;
		ServletOutputStream out = null;
		PrintWriter out1 = null;
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_AGGREGATE_ACCOUNT)) {
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			String monthID = request.getParameter("monthId");
			RejectedInvoiceService rejInvSer = new RejectedInvoiceServiceImpl();
			
			invList=rejInvSer.getRejectedInvExcel(monthID);
			partnerList=rejInvSer.getRejectedPartnerInvExcel(monthID);
			//String path="C:\\ErrFile";
			String path=ResourceReader.getWebResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.PAYOUT_PATH);
				String [][] excelData = null;
				Iterator itr =invList.iterator();
				excelData =  new  String[invList.size()+16][invList.size()+16];
				excelData[0][0] = "OLM ID" ;
				excelData[0][1] = "TIN Number" ;
				excelData[0][2] = "ServiceTax" ;
				excelData[0][3] = "InvoiceNo" ;
				excelData[0][4] = "CPE Rec_No" ;
				excelData[0][5] = "CPE Rec_Rt" ;
				excelData[0][6] = "CPE Amount" ;
				excelData[0][7] = "Sr Rew_No" ;
				excelData[0][8] = "Sr Rew_Rt" ;
				excelData[0][9] = "Sr Rew_Amount" ;
				excelData[0][10] = "Reten_No" ;
				excelData[0][11] = "Reten_Rt" ;
				excelData[0][12] = "Reten_Amount" ;
				excelData[0][13] = "Status" ;
				excelData[0][14] = "Remarks" ;
				excelData[0][15] = "ActionDate" ;
				int count = 1;
				while(itr.hasNext())
				{
					RejectedInvoiceDTO dto =	(RejectedInvoiceDTO) itr.next();
					excelData[count][0] = dto.getOlmId();
					excelData[count][1] = dto.getTinNo();
					excelData[count][2] = dto.getSrcvTaxCT();
					excelData[count][3] = dto.getInvoiceNumber();
					excelData[count][4] = dto.getCpeRecNo().toString();
					excelData[count][5] = dto.getCpeRate().toString();
					excelData[count][6] = dto.getCpeAmount().toString();
					excelData[count][7] = dto.getSrRewNo().toString();
					excelData[count][8] = dto.getSrRate().toString();
					excelData[count][9] = dto.getSrRewAmount().toString();
					excelData[count][10] = dto.getRetenNo().toString();
					excelData[count][11] = dto.getRetenRate().toString();
					excelData[count][12] = dto.getRetenAmount().toString();
					excelData[count][13] = dto.getStatus();
					excelData[count][14] = dto.getRemarks();
					excelData[count][15] = dto.getActionDate();
					count++;
				}
				
				
				
				String [][] excelData1 = null;
				Iterator itr1 =partnerList.iterator();
				
				excelData1 =  new  String[partnerList.size()+19][partnerList.size()+19];
				excelData1[0][0] = "OLM_ID" ;
				excelData1[0][1] = "Partner Nm" ;
				excelData1[0][2] = "HDT1" ;
				excelData1[0][3] = "HDT2" ;
				excelData1[0][4] = "HDT3" ;
				excelData1[0][5] = "Inv Number" ;
				excelData1[0][6] = "MULTIT1" ;
				excelData1[0][7] = "MULTIT2" ;
				excelData1[0][8] = "MULTIT3" ;
				excelData1[0][9] = "MULTIDVRT1" ;
				excelData1[0][10] = "MULTIDVRT2" ;
				excelData1[0][11] = "MULTIDVRT3" ;
				excelData1[0][12] = "Amount" ;
				excelData1[0][13] = "Total Act" ;
				excelData1[0][14] = "Total Amount" ;
				excelData1[0][15] = "Total Inv Amt" ;
				excelData1[0][16] = "Status" ;
				excelData1[0][17] = "Remarks" ;
				excelData1[0][18] = "ActionDate" ;
				int countp = 1;
				while(itr1.hasNext())
				{
					RejectedPartnerInvDTO dto =	(RejectedPartnerInvDTO) itr1.next();
					excelData1[countp][0] = dto.getOlmId();
					excelData1[countp][1] = dto.getPartnerName();
					excelData1[countp][2] = dto.getHDT1().toString();
					excelData1[countp][3] = dto.getHDT2().toString();
					excelData1[countp][4] = dto.getHDT3().toString();
					excelData1[countp][5] = dto.getInvoiceNumber();
					excelData1[countp][6] = dto.getMULTIT1().toString();
					excelData1[countp][7] = dto.getMULTIT2().toString();
					excelData1[countp][8] = dto.getMULTIT3().toString();
					excelData1[countp][9] = dto.getMULTIDVRT1().toString();
					excelData1[countp][10] = dto.getMULTIDVRT2().toString();
					excelData1[countp][11] = dto.getMULTIDVRT3().toString();
					excelData1[countp][12] = dto.getAmount().toString();
					excelData1[countp][13] = dto.getTOTALACT().toString();
					excelData1[countp][14] = dto.getTOTALAmount().toString();
					excelData1[countp][15] = dto.getTotalInvAmt().toString();
					excelData1[countp][16] = dto.getStatus();
					excelData1[countp][17] = dto.getRemarks();	
					excelData1[countp][18] = dto.getActionDate();	
					countp++;
				}
				
				
				
				
				
								
				//in = new FileInputStream(fpath);
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet dataSheet = workbook.createSheet("InvList");
				HSSFSheet dataSheet1 = workbook.createSheet("PartnerINVList");
				HSSFRow myRow = null;         
				HSSFCell myCell = null;  
				for (int rowNum = 0; rowNum < excelData.length; rowNum++)
				{      
					myRow = dataSheet.createRow(rowNum);               
					for (int cellNum = 0; cellNum < 16 ; cellNum++)
					{     
						myCell = myRow.createCell(cellNum);   
						myCell.setCellValue(excelData[rowNum][cellNum]);             
					}         
				}   
				for (int rowNumber = 0; rowNumber < excelData1.length; rowNumber++)
				{      
					myRow = dataSheet1.createRow(rowNumber);               
					for (int cellNumber = 0; cellNumber < 19 ; cellNumber++)
					{     
						myCell = myRow.createCell(cellNumber);   
						myCell.setCellValue(excelData1[rowNumber][cellNumber]);             
					}         
				} 
				
				response.setHeader( "Pragma", "public" );
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Cache-Control", "must-revalidate");
				String timestamp =Long.toString(System.currentTimeMillis());
				response.setHeader("Content-Disposition", "attachment;filename=InvoiceStatus.xls");
				
				FileOutputStream fos  = new FileOutputStream(new File(path +"/"+ "InvoiceStatus"+timestamp+".xls")); 
				
				
				workbook.write(fos);
				in = new FileInputStream(path+"/"+ "InvoiceStatus"+timestamp+".xls");
				out = response.getOutputStream();
				byte[] outputByte = new byte[4096];
				while (in.read(outputByte, 0, 4096) != -1) {
					out.write(outputByte, 0, 4096);
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				errors.add("errors",new ActionError(e.getMessage()));
				request.setAttribute("strmsg",e.getMessage());
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
		
	
		return mapping.findForward(SHOW_DOWNLOAD_AGGREGATE_LIST);
	}
	

}
