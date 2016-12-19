package com.ibm.dp.service.impl;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;

import com.ibm.dp.beans.InvoiceDetails;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DpInvoiceDao;
import com.ibm.dp.dao.impl.DpInvoiceDaoImpl;
import com.ibm.dp.service.DpInvoiceUploadService;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.ErrorDto;
import com.ibm.dp.dto.ExcelError;
import com.ibm.dp.dto.InvoiceMsgDto;
import com.ibm.dp.dto.PartnerDetails;
import com.ibm.dp.dto.RateDTO;
import com.ibm.dp.dto.Sheet2Pojo;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.utils.PropertyReader;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.UserInfo;
import com.lowagie.text.Font;

public class DpInvoiceUploadServiceImpl implements DpInvoiceUploadService {

	private Logger logger = Logger.getLogger(DpInvoiceUploadServiceImpl.class);

	public void invFileUploadsxNew(String path) throws Exception {

		File file = new File(path);
		FileInputStream fileIn = new FileInputStream(file);
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook(fileIn);
		} catch (IOException e) {
			logger.info("Exception in reading the file");
			e.printStackTrace();
			logger.info("Message:" + e.getMessage());
		}
		if (workbook == null) {
			throw new Exception();
		}
		logger.info("Processing sheet");
		XSSFSheet sheet = workbook.getSheetAt(0);
	//	XSSFSheet sheet1=workbook.getSheetAt(0);
		logger.info("Sheet="+sheet.getSheetName());
		Iterator itr = sheet.iterator();
	//	Iterator itr1=sheet1.iterator();
		logger.info("Sheet iteretion begins:");
		
		Row row1 = sheet.getRow(1);
		Row row0=sheet.getRow(0);
		
		genericExcelProcess(itr, row0, row1);
		
		// Creating sheet bean to hold sheet indexes.
	//	genericExcelprocss(itr1);

	}
	public void invFileUploadsx(String path) throws Exception {

		File file = new File(path);
		FileInputStream fileIn = new FileInputStream(file);
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook(fileIn);
		} catch (IOException e) {
			logger.info("Exception in reading the file");
			e.printStackTrace();
			logger.info("Message:" + e.getMessage());
		}
		if (workbook == null) {
			throw new Exception();
		}
		logger.info("Processing sheet");
		//XSSFSheet sheet = workbook.getSheetAt(1);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		//logger.info("Sheet="+sheet.getSheetName());
		//Iterator itr = sheet.iterator();
		Iterator itr1=sheet1.iterator();
		logger.info("Sheet iteretion begins:");
		
		//Row row1 = sheet.getRow(1);
		//Row row0=sheet.getRow(0);
		
		//genericExcelProcess(itr, row0, row1);
		
		// Creating sheet bean to hold sheet indexes.
		genericExcelprocss(itr1);

	}

	public void invFileUploadlsNew(String path) throws Exception {
		File file = new File(path);
		FileInputStream fileIn = new FileInputStream(file);
		HSSFWorkbook workbook = null;

		try {
			workbook = new HSSFWorkbook(fileIn);
		} catch (Exception exp) {
			logger.info("Error in reading file");
			exp.printStackTrace();
			logger.info("Exiting");
			throw new Exception();
		}
		
		HSSFSheet sheet=workbook.getSheetAt(0);
		//HSSFSheet sheet1=workbook.getSheetAt(0);
		
		Iterator itr = sheet.iterator();
		//Iterator itr1=sheet1.iterator();
		
		
		Row row1 = sheet.getRow(1);
		Row row0=sheet.getRow(0);
		logger.info("Sheet iteretion begins:");
		genericExcelProcess(itr, row0, row1);
		//genericExcelprocss(itr1);
		
		
	}
	public void invFileUploadls(String path) throws Exception {
		File file = new File(path);
		FileInputStream fileIn = new FileInputStream(file);
		HSSFWorkbook workbook = null;

		try {
			workbook = new HSSFWorkbook(fileIn);
		} catch (Exception exp) {
			logger.info("Error in reading file");
			exp.printStackTrace();
			logger.info("Exiting");
			throw new Exception();
		}
		
		//HSSFSheet sheet=workbook.getSheetAt(1);
		HSSFSheet sheet1=workbook.getSheetAt(0);
		
		//Iterator itr = sheet.iterator();
		Iterator itr1=sheet1.iterator();
		
		
		//Row row1 = sheet.getRow(1);
		//Row row0=sheet.getRow(0);
		logger.info("Sheet iteretion begins:");
		//genericExcelProcess(itr, row0, row1);
		genericExcelprocss(itr1);
		
		
	}
	
	public void genericExcelProcess(Iterator itr,Row row0,Row row1) throws Exception
	{
		logger.info("Inside Generic Excel Method");
		// Creating sheet bean to hold sheet indexes.
		Sheet2Pojo sheetBean=new Sheet2Pojo();
		Iterator taxIter=row0.cellIterator();
		
		//Setting index of "Service Taxes" field
		while(taxIter.hasNext())
		{
			Cell cellIt=(Cell) taxIter.next();
			int cellIdx = ((Cell) cellIt).getColumnIndex();
			String colmn="";
			
			if(cellIt.getCellType()==Cell.CELL_TYPE_STRING)
				colmn = ((Cell) cellIt).getStringCellValue();
			else
				continue;
			if(colmn!=null && colmn.equalsIgnoreCase(Constants.SERV_TX))
			{
				sheetBean.setTaxIdx(cellIdx);
				break;
			}
				//taxIdx=cellIdx;;
		}
		
		
		//srvTxAmtidx=taxIdx;
		//eduTxAmtidx=taxIdx+1;
		//heduTxAmtidx=taxIdx+2;
		// Setting indexes for Service tax,Edu Cess and HEdu cess amount cells
		
		logger.info("Service Tax Column No."+sheetBean.getTaxIdx());
		
		sheetBean.setSrvTxAmtidx(sheetBean.getTaxIdx());
		sheetBean.setEduTxAmtidx(sheetBean.getTaxIdx()+1);
		sheetBean.setKkcAmtidx(sheetBean.getTaxIdx()+2);
		//sheetBean.setHeduTxAmtidx(sheetBean.getTaxIdx()+2);
		
		double taxRates=0.0;
		
		
		
		//Setting tax rates fetched from sheet
		taxRates=row1.getCell(sheetBean.getSrvTxAmtidx()).getNumericCellValue();
		sheetBean.setSrvTxRt(taxRates*Constants.MF);
		
		logger.info("Service Tax Rates:"+taxRates);
		
		taxRates=row1.getCell(sheetBean.getEduTxAmtidx()).getNumericCellValue();
		sheetBean.setEduCesRt(taxRates*Constants.MF);
		
		logger.info("swaccha bharat cess"+taxRates);
		
		/*taxRates=row1.getCell(sheetBean.getHeduTxAmtidx()).getNumericCellValue();
		sheetBean.setHeduCesRt(taxRates*Constants.MF);*/
		
		taxRates=row1.getCell(sheetBean.getKkcAmtidx()).getNumericCellValue();
		sheetBean.setKkcRt(taxRates*Constants.MF);
		
		logger.info("***********+++++++++++++Krishi Kalyan Cess:"+taxRates);
		
		
		logger.info(row1.getCell(1).getStringCellValue());
		Iterator cellItr = row1.cellIterator();

		// DpInvoiceDto dpInvDto=new DpInvoiceDto();
		Map<String, DpInvoiceDto> partnerInvMap = new HashMap<String, DpInvoiceDto>();
		logger.info("Sheet iteretion begins2:");
		
		

		while(cellItr.hasNext()) {
			Cell cellIt=(Cell) cellItr.next();
			int cellIdx = ((Cell) cellIt).getColumnIndex();
			String colmn="";
			if(cellIt.getCellType()==Cell.CELL_TYPE_STRING)
				colmn = ((Cell) cellIt).getStringCellValue();
			else
				continue;
			if (colmn != null && colmn.equalsIgnoreCase(Constants.SR_SLA))
				sheetBean.setSrRwdidx(cellIdx);
				//srRwdidx = cellIdx;
			else if (colmn != null && colmn.equalsIgnoreCase(Constants.CPE_REC))
				sheetBean.setCpeIndex(cellIdx);
				//cpeIndex = ((Cell) cellIt).getColumnIndex();
			else if (colmn != null && colmn.equalsIgnoreCase(Constants.RET))
				sheetBean.setRetIdx(cellIdx);
				//retIdx = cellIdx;
			else if (colmn != null && colmn.equalsIgnoreCase(Constants.PTR))
				sheetBean.setOthIndex(cellIdx);
			//	othIndex = cellIdx;
			else if (colmn != null && colmn.equalsIgnoreCase(Constants.OLM_ID))
				sheetBean.setOlmIdIndex(cellIdx);
			//	olmIdIndex = cellIdx;
			else if (colmn != null && colmn.equalsIgnoreCase(Constants.PARTN_NM))
				sheetBean.setPartNmIdx(cellIdx);
			//	partNmIdx = cellIdx;
			
			else if(colmn !=null && colmn.equalsIgnoreCase(Constants.TOTAL_INV_AM))
				sheetBean.setTotalInvIdx(cellIdx);
			//	totalInvIdx=cellIdx;
			else if(colmn!=null && colmn.equalsIgnoreCase(Constants.OTHER))
				sheetBean.setOthersIdx(cellIdx);
			else if(colmn!=null && colmn.equalsIgnoreCase(Constants.OTHER_1))
				sheetBean.setOthers1Idx(cellIdx);
				
				
		}
		logger.info("SR index=" + sheetBean.getSrRwdidx());
		logger.info("CPE index=" + sheetBean.getCpeIndex());
		logger.info("Retention index=" + sheetBean.getRetIdx());
		String monthFr="";
		while (itr.hasNext()) {
			Row row = (Row) itr.next();
			int rowNum = row.getRowNum();
			if(row.getRowNum()==0){
				String month=row.getCell(0).getStringCellValue();
				String year=row.getCell(1).getStringCellValue();
				if(month!=null && !month.trim().equals("") && year!=null && !year.trim().equals(""))
					monthFr=month+"-"+year;
			}
			if (rowNum == 0 || rowNum == 1 || row==null)
				continue;

			logger.info("getting OLM id:");
			if(row.getCell(sheetBean.getOlmIdIndex())== null  || row.getCell(sheetBean.getOlmIdIndex()).getCellType() != Cell.CELL_TYPE_STRING)
			{
				logger.info("Cell type not string");
				continue;
			}
			String olmCelVal = row.getCell(sheetBean.getOlmIdIndex()).getStringCellValue();
			
			
			if(olmCelVal== null || olmCelVal.trim().isEmpty() || olmCelVal.trim().equals(" "))
			{
				logger.info("Blank Cell OLM ID");
				continue;
			}
				
			
			logger.info("Populating cell values into dto");
			logger.info("Fetching data for OLM ID->"+olmCelVal);
			
		/*	if (partnerInvMap.containsKey(olmCelVal)) {
				DpInvoiceDto dpInvDto = partnerInvMap.get(olmCelVal);

				dpInvDto.setCperecNo(dpInvDto.getCperecNo()
						+ (int) (row.getCell(sheetBean.getCpeIndex()).getNumericCellValue()));

				dpInvDto.setSrrwdNo(dpInvDto.getSrrwdNo()
						+ (int) (row.getCell(sheetBean.getSrRwdidx()).getNumericCellValue()));
				dpInvDto.setRetNo(dpInvDto.getRetNo()
						+ (int) (row.getCell(sheetBean.getRetIdx()).getNumericCellValue()));
				dpInvDto.setOthNo(dpInvDto.getOthNo()
						+ (int) (row.getCell(sheetBean.getOthIndex()).getNumericCellValue()));

				dpInvDto.setCperecAmt();
				dpInvDto.setRetAmt();
				dpInvDto.setSrrwdAmt();
				dpInvDto.setOthAmt();

				dpInvDto.setTotal();

				dpInvDto.setSrvTxAmt(row.getCell(sheetBean.getSrvTxAmtidx()).getNumericCellValue());
				dpInvDto.setEduCesAmt(row.getCell(sheetBean.getEduTxAmtidx()).getNumericCellValue());
				dpInvDto.setHeduCesAmt(row.getCell(sheetBean.getHeduTxAmtidx()).getNumericCellValue());

				dpInvDto.setGrndTotal();

				partnerInvMap.put(olmCelVal, dpInvDto);
				logger.info("Updating map dto");

			}*/

	//		else {

				DpInvoiceDto dpInvDto = new DpInvoiceDto();
				if(monthFr!=null && !monthFr.trim().equals(""))
					dpInvDto.setMonthFr(monthFr);
				dpInvDto.setBilDt(new Date());
				dpInvDto.setCperecNo(dpInvDto.getCperecNo()
						+ (int) (row.getCell(sheetBean.getCpeIndex()).getNumericCellValue()));
				
				dpInvDto.setOthers((int) row.getCell(sheetBean.getOthersIdx()).getNumericCellValue());
				dpInvDto.setOthers1((int)row.getCell(sheetBean.getOthers1Idx()).getNumericCellValue());

				dpInvDto.setSrrwdNo(dpInvDto.getSrrwdNo()
						+ (int) (row.getCell(sheetBean.getSrRwdidx()).getNumericCellValue()));
				dpInvDto.setRetNo(dpInvDto.getRetNo()
						+ (int) (row.getCell(sheetBean.getRetIdx()).getNumericCellValue()));
				dpInvDto.setOthNo(dpInvDto.getOthNo()
						+ (int) (row.getCell(sheetBean.getOthIndex()).getNumericCellValue()));

				dpInvDto.setOlmId(olmCelVal);
				dpInvDto.setPartnerNm(row.getCell(sheetBean.getPartNmIdx())
						.getStringCellValue());

				// configurable details

				dpInvDto.setCperecRt(Constants.RF);
				dpInvDto.setSrrwdRt(Constants.RF);
				dpInvDto.setRetRt(Constants.RF);
				dpInvDto.setOthRt(Constants.RF);
				
				
				dpInvDto.setSrvTxRt(sheetBean.getSrvTxRt());
				dpInvDto.setEduCesRt(sheetBean.getEduCesRt());
				dpInvDto.setKkcrt(sheetBean.getKkcRt());
				dpInvDto.setHeduCesRt(sheetBean.getHeduCesRt());
				// configurable details end

				dpInvDto.setCperecAmt();
				dpInvDto.setRetAmt();
				dpInvDto.setSrrwdAmt();
				dpInvDto.setOthAmt();

				dpInvDto.setTotal();

				dpInvDto.setSrvTxAmt(Math.round(row.getCell(sheetBean.getSrvTxAmtidx()).getNumericCellValue()));
				dpInvDto.setEduCesAmt(Math.round(row.getCell(sheetBean.getEduTxAmtidx()).getNumericCellValue()));
				dpInvDto.setKkcamt(Math.round(row.getCell(sheetBean.getKkcAmtidx()).getNumericCellValue()));
				//dpInvDto.setHeduCesAmt(Math.round(row.getCell(sheetBean.getHeduTxAmtidx()).getNumericCellValue()));
				
				
				dpInvDto.setGrndTotal();
				
				partnerInvMap.put(olmCelVal, dpInvDto);
				logger.info("putting values in map");
				
				
				
				
				

		//	}
			

		}
		logger.info("Populated values now calling DB");
		DpInvoiceDao dpInvDao = new DpInvoiceDaoImpl();
		dpInvDao.uploadInvoiceDetails(partnerInvMap);
		logger.info("Exiting generic Method");
	}

	@Override
	public List<InvoiceDetails> listInvoices(String loginNm) throws Exception {
		
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listInvoices(loginNm);
		
		return invList;
	}

	@Override
	public DpInvoiceDto partnerInvoice(int billNo) throws Exception {
		
		DpInvoiceDto dpinvDto=new DpInvoiceDto();
		
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		dpinvDto=invDao.partnerInvoice(billNo);
		return dpinvDto;
	}

	@Override
	public String acceptInvoice(int billNo, String invoiceNo,String remarks) throws Exception {
		
		String statusMsg="";
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		statusMsg=invDao.genericInvoice(billNo, invoiceNo, "A",remarks);
		
		return statusMsg;
	}

	@Override
	public String rejectInvoice(int billNo, String invoiceNo,String rem) throws Exception {
		
		String statusMsg="";
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		statusMsg=invDao.genericInvoice(billNo, invoiceNo, "R",rem);
		
		return statusMsg;
	}
	public void genericExcelprocss(Iterator<Row> rowIter) throws Exception{
		Map<String, DpInvoiceDto> partnerInvMap = new HashMap<String, DpInvoiceDto>();
		
		try{
			String monthFr="";
		while(rowIter.hasNext()){
			Row row=rowIter.next();
			if(row.getRowNum()==0){
				String month=row.getCell(0).getStringCellValue();
				String year=row.getCell(1).getStringCellValue();
				if(month!=null && !month.trim().equals("") && year!=null && !year.trim().equals(""))
					monthFr=month+"-"+year;
			}
			if(row.getRowNum()<3 || row==null || row.getCell(1)==null)
			{
				
				//logger.info(row.getRowNum());
				continue;
			}
			logger.info("OLM--->"+row.getCell(1).getStringCellValue());
			DpInvoiceDto dpinvDto=new DpInvoiceDto();
			if(monthFr!=null && !monthFr.trim().equals(""))
				dpinvDto.setMonthFr(monthFr);
			dpinvDto.setOlmId(row.getCell(1).getStringCellValue());
			dpinvDto.setPartnerNm(row.getCell(2).getStringCellValue());
			dpinvDto.setAsm(row.getCell(3).getStringCellValue());
			dpinvDto.setCircle(row.getCell(4).getStringCellValue());
			dpinvDto.setDvrT1((int)row.getCell(5).getNumericCellValue());
			dpinvDto.setDvrT2((int)row.getCell(6).getNumericCellValue());
			dpinvDto.setDvrT3((int)row.getCell(7).getNumericCellValue());
			dpinvDto.setHdextT1((int)row.getCell(8).getNumericCellValue());
			dpinvDto.setHdextT2((int)row.getCell(9).getNumericCellValue());
			dpinvDto.setHdextT3((int)row.getCell(10).getNumericCellValue());
			dpinvDto.setMultiT1((int)row.getCell(11).getNumericCellValue());
			dpinvDto.setMultiT2((int)row.getCell(12).getNumericCellValue());
			dpinvDto.setMultiT3((int)row.getCell(13).getNumericCellValue());
			dpinvDto.setMultidvrT1((int)row.getCell(14).getNumericCellValue());
			dpinvDto.setMultidvrT2((int)row.getCell(15).getNumericCellValue());
			dpinvDto.setMultidvrT3((int)row.getCell(16).getNumericCellValue());
			if(dpinvDto.getDvrT1()!=0 || dpinvDto.getHdextT1()!=0 || dpinvDto.getMultiT1()!=0 || dpinvDto.getMultidvrT1() !=0 )
				dpinvDto.setTier(1);
			else if(dpinvDto.getDvrT2()!=0 || dpinvDto.getHdextT2()!=0 || dpinvDto.getMultiT2()!=0 || dpinvDto.getMultidvrT2() !=0 )
				dpinvDto.setTier(2);
			else 
				dpinvDto.setTier(3);
			dpinvDto.setTotalAct((int)row.getCell(17).getNumericCellValue());
			dpinvDto.setTotalAmt((int)row.getCell(18).getNumericCellValue());
			dpinvDto.setHdplusExt((int)row.getCell(19).getNumericCellValue());
			dpinvDto.setDvr((int)row.getCell(20).getNumericCellValue());
			dpinvDto.setAmt((int)row.getCell(21).getNumericCellValue());
			dpinvDto.setOneyrCount((int)row.getCell(22).getNumericCellValue());
			dpinvDto.setOneyrAmt((int)row.getCell(23).getNumericCellValue());
			dpinvDto.setFieldreprelAmt((int)row.getCell(24).getNumericCellValue());
			dpinvDto.setArpPayout((int)row.getCell(25).getNumericCellValue());
			
			dpinvDto.setWeakGeo((int)row.getCell(26).getNumericCellValue());
			dpinvDto.setHillyArea((int)row.getCell(27).getNumericCellValue());
			dpinvDto.setOthers((int)row.getCell(28).getNumericCellValue());
			dpinvDto.setOthers1((int)row.getCell(29).getNumericCellValue());
			dpinvDto.setOthers2((int)row.getCell(30).getNumericCellValue());	
			
			dpinvDto.setTotbasAmt((int)row.getCell(31).getNumericCellValue());
			dpinvDto.setServTx((int)row.getCell(32).getNumericCellValue());
			dpinvDto.setEduCes((int)row.getCell(33).getNumericCellValue());
			dpinvDto.setKkc((int)row.getCell(34).getNumericCellValue());
			dpinvDto.setTotTax((int)row.getCell(35).getNumericCellValue());
			dpinvDto.setTotinvAmt((int)row.getCell(36).getNumericCellValue());
			dpinvDto.setFinRemarks(row.getCell(37).getStringCellValue());
			//dpinvDto.setHeduCes((int)row.getCell(29).getNumericCellValue());
			/*dpinvDto.setTotTax((int)row.getCell(34).getNumericCellValue());
			dpinvDto.setTotinvAmt((int)row.getCell(35).getNumericCellValue());
			dpinvDto.setFinRemarks(row.getCell(36).getStringCellValue());*/
			
			partnerInvMap.put(row.getCell(1).getStringCellValue(), dpinvDto);
			
			
	
		 }
		DpInvoiceDao dpInvDao = new DpInvoiceDaoImpl();
		dpInvDao.uploadInvoiceSheet(partnerInvMap);
		
		}catch(Exception expx){
			expx.printStackTrace();
			throw new Exception();
		}
		
	}

	@Override
	public List<InvoiceDetails> listRejectedInvoices()
			throws Exception {

		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listRejectedInvoices();
		
		return invList;
		
	}

	@Override
	public List<InvoiceDetails> listInvoicesSTB(String loginNm)
			throws Exception {
		
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listInvoicesSTB(loginNm);
		
		return invList;
	}

	@Override
	public DpInvoiceDto partnerInvoiceSTB(int billNo) throws Exception {

		DpInvoiceDto dpinvDto=new DpInvoiceDto();
		
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		dpinvDto=invDao.partnerInvoiceSTB(billNo);
		return dpinvDto;
	}

	@Override
	public String acceptInvoiceSTB(int billNo, String invoiceNo,String rem)
			throws Exception {

		String statusMsg="";
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		statusMsg=invDao.genericInvoiceSTB(billNo, invoiceNo, "A",rem);
		
		return statusMsg;
	}

	@Override
	public String rejectInvoiceSTB(int billNo, String invoiceNo,String remarks)
			throws Exception {
		
		String statusMsg="";
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		statusMsg=invDao.genericInvoiceSTB(billNo, invoiceNo, "R",remarks);
		
		return statusMsg;
	}

	@Override
	public ExcelError validatePayoutInv(String absFile,String fileFlag) throws Exception {
		// TODO Auto-generated method stub
		File file = new File(absFile);
		FileInputStream fileIn = new FileInputStream(file);
		ExcelError exErr=new ExcelError();

		try {
			if(absFile.contains(".xlsx"))
			{
				XSSFWorkbook workbook = null;
				workbook = new XSSFWorkbook(fileIn);
				
				if (workbook == null) {
					throw new Exception();
				}
				XSSFSheet sheet,sheet1;
				if(fileFlag.equals("2")){
					sheet = workbook.getSheetAt(0);
					Iterator itr = sheet.iterator();
					Row row1 = sheet.getRow(0);
					Row row0=sheet.getRow(0);
					exErr=genericValidate(itr, null, row0, row1,fileFlag);
					
					logger.info("Sheet="+sheet.getSheetName());
				}
					 
				else if(fileFlag.equals("1")){
					sheet1=workbook.getSheetAt(0);
					Iterator itr1=sheet1.iterator();
					exErr=genericValidate(null, itr1, null, null,fileFlag);
				}
				  
				
				
				logger.info("Sheet iteretion begins:");
				
				
				
				
			}
			else
			{	HSSFWorkbook workbook=null;
				workbook = new HSSFWorkbook(fileIn);
				
				if (workbook == null) {
					throw new Exception();
				}
				HSSFSheet sheet,sheet1;
				if(fileFlag.equals("2")){
					sheet=workbook.getSheetAt(0);
					Iterator itr = sheet.iterator();
					Row row1 = sheet.getRow(1);
					Row row0=sheet.getRow(0);
					exErr=genericValidate(itr, null, row0, row1,fileFlag);
				}
				else if(fileFlag.equals("1")){
					sheet1=workbook.getSheetAt(0);
					Iterator itr1=sheet1.iterator();
					exErr=genericValidate(null, itr1, null, null,fileFlag);
				}
			
			}
			
			
			
		} catch (Exception e) {
			logger.info("Exception in reading the file");
			e.printStackTrace();
			logger.info("Message:" + e.getMessage());
			logger.info("Changes here +++++++++++");
			throw new Exception(e);
		}
		
		logger.info("Processing sheet");
		  
		
		return exErr;
		
		
	}
		
	
	private ExcelError genericValidate(Iterator itr,Iterator itr1,Row row0,Row row1,String fileFlag) throws Exception{
		
		List <InvoiceMsgDto> invoiceDto=new ArrayList<InvoiceMsgDto>();
		List <InvoiceMsgDto> invoiceDtoSr=new ArrayList<InvoiceMsgDto>();
		String olmId="";
		String partnerNm="";
		String circle="";
		String errMsg="";
		String asm="";
		String monthFr="";
		
		ExcelError exErr=new ExcelError();
		DpInvoiceDao dpinv=new DpInvoiceDaoImpl();
		
		if (fileFlag.equals("2")) {

			while (itr.hasNext()) {
				InvoiceMsgDto invMsgdto = new InvoiceMsgDto();
				Row row = (Row) itr.next();
				if (row == null) {
					break;
				}
				if(row.getRowNum()==0){
					String month=row.getCell(0).getStringCellValue();
					String year=row.getCell(1).getStringCellValue();
					if(month!=null && !month.trim().equals("") && year!=null && !year.trim().equals(""))
						monthFr=month+"-"+year;
				}
				if (row.getRowNum() == 0 || row.getRowNum() == 1) {
					logger.info("Skipping non Data rows");
					continue;
				}
				Cell cell0 = row.getCell(0);
				Cell cell1 = row.getCell(1);
				try {
					olmId = cell0.getStringCellValue();
					if (olmId == null || olmId.equals(""))
						continue;
				} catch (Exception excp) {
					logger.info("CELL_TYPE_ERROR");

					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId("UNREADABLE_OLM_ID");
					invMsgdto.setErrMsg("CELL TYPE MUST BE STRING");
					invoiceDto.add(invMsgdto);
					continue;

				}
				try {
					partnerNm = cell1.getStringCellValue();
				} catch (Exception excp) {
					logger.info("CELL_TYPE_ERROR");
					// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto
							.setErrMsg("PARTNER NAME COLUMN TYPE MUST BE STRING");
					invoiceDto.add(invMsgdto);
					continue;
				}

				ErrorDto errDto = dpinv.validateOlmIdPartner(olmId, partnerNm,monthFr);
				if (!errDto.isStatus()) {
					logger.info("Parnter OLM ID error");
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto.setErrMsg(errDto.getMessage());
					invoiceDto.add(invMsgdto);
					continue;
				}

				for (int i = 2; i < 11; i++) {
					Cell cellGen = row.getCell(i);
					try {
						Double celVal = cellGen.getNumericCellValue();
						logger.info("checking cell: " + i);
					} catch (Exception excp) {

						logger.info("CELL_TYPE_ERROR");
						// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
						invMsgdto.setRowNo(row.getRowNum());
						invMsgdto.setOlmId(olmId);
						invMsgdto
								.setErrMsg("Cell type must be numeric for column no. "
										+ cellGen.getColumnIndex());
						invoiceDto.add(invMsgdto);
						break;

					}
				}

			}
			logger.info("Sheet 1 validated..mmoving to second sheet ");

			exErr.setInvoiceDto(invoiceDto);
		}
		
		else if (fileFlag.equals("1")) {

			while (itr1.hasNext()) {
				Row row = (Row) itr1.next();
				InvoiceMsgDto invMsgdto = new InvoiceMsgDto();
				if(row.getRowNum()==0){
					String month=row.getCell(0).getStringCellValue();
					String year=row.getCell(1).getStringCellValue();
					if(month!=null && !month.trim().equals("") && year!=null && !year.trim().equals(""))
						monthFr=month+"-"+year;
				}
				if (row.getRowNum() == 0 || row.getRowNum() == 1
						|| row.getRowNum() == 2) {
					continue;
				}
				if (row == null) {
					break;
				}
				Cell cell1 = row.getCell(1);
				Cell cell2 = row.getCell(2);
				Cell cell3 = row.getCell(3);
				Cell cell4 = row.getCell(4);

				try {
					olmId = cell1.getStringCellValue();
				} catch (Exception excp) {
					logger.info("CELL_TYPE_ERROR");

					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId("UNREADABLE_OLM_ID");
					invMsgdto.setErrMsg("CELL TYPE MUST BE STRING");
					invoiceDtoSr.add(invMsgdto);
					continue;
				}
				try {
					partnerNm = cell2.getStringCellValue();
				} catch (Exception excep) {
					logger.info("CELL_TYPE_ERROR");
					// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto
							.setErrMsg("PARTNER NAME COLUMN TYPE MUST BE STRING");
					invoiceDtoSr.add(invMsgdto);
					continue;

				}

				ErrorDto errDto = dpinv.validateOlmIdPartnerNew(olmId, partnerNm,monthFr);

				if (!errDto.isStatus()) {
					logger.info("Parnter OLM ID error");
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto.setErrMsg(errDto.getMessage());
					invoiceDtoSr.add(invMsgdto);
					continue;
				}
				try {
					asm = cell3.getStringCellValue();
				} catch (Exception excp) {
					logger.info("CELL_TYPE_ERROR");
					// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto.setErrMsg("ASM COLUMN TYPE MUST BE STRING");
					invoiceDtoSr.add(invMsgdto);
					continue;
				}

				try {
					circle = cell4.getStringCellValue();
				} catch (Exception excp) {
					logger.info("CELL_TYPE_ERROR");
					// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
					invMsgdto.setRowNo(row.getRowNum());
					invMsgdto.setOlmId(olmId);
					invMsgdto.setErrMsg("CIRCLE COLUMN TYPE MUST BE STRING");
					invoiceDto.add(invMsgdto);
					continue;
				}
				/*
				 * errDto=dpinv.validateDistCircle(olmId, circle);
				 * 
				 * if(!errDto.isStatus()) { logger.info("Circle error");
				 * invMsgdto.setRowNo(row.getRowNum());
				 * invMsgdto.setOlmId(olmId);
				 * invMsgdto.setErrMsg(errDto.getMessage());
				 * invoiceDtoSr.add(invMsgdto); continue; }
				 */

				for (int i = 5; i < 31; i++) {
					Cell cellGen = row.getCell(i);
					try {
						Double celVal = cellGen.getNumericCellValue();
					} catch (Exception excp) {

						logger.info("CELL_TYPE_ERROR");
						// InvoiceMsgDto invMsgdto=new InvoiceMsgDto();
						invMsgdto.setRowNo(row.getRowNum());
						invMsgdto.setOlmId(olmId);
						invMsgdto
								.setErrMsg("Cell type must be numeric for column no. "
										+ cellGen.getColumnIndex());
						invoiceDtoSr.add(invMsgdto);
						break;

					}
				}

			}
			exErr.setInvoiceDtoS(invoiceDtoSr);
		}
		return exErr;
		
	}

	@Override
	public String errorFileDownload(ExcelError excErr, String fileFlag) throws Exception {
		HSSFWorkbook workbook=new HSSFWorkbook();
		String path=ResourceReader.getWebResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.PAYOUT_PATH);
		HSSFSheet sheet0=null,sheet1=null;
		Row headerRow=null;
		if(fileFlag!=null && !fileFlag.trim().equals("") && fileFlag.trim().equals("1")){

			 sheet0=workbook.createSheet("Invoice Format");
			 headerRow=sheet0.createRow(0);
		}
		else if(fileFlag!=null && !fileFlag.trim().equals("") && fileFlag.trim().equals("2")){
			 sheet1=workbook.createSheet("Invoice Format2");
			 headerRow=sheet1.createRow(0);
		}
		
		String titles[]=new String[]{"ROW No","OLM ID","ERROR MESSAGE"};	
		HSSFFont font1=workbook.createFont();
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		CellStyle style=workbook.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(font1);
		style.setFillForegroundColor(CellStyle.SOLID_FOREGROUND);
		for(int i=0;i<3;i++){
			Cell cell=headerRow.createCell(i);
			//Cell cell1=headerRow1.createCell(i);
			
			cell.setCellValue(titles[i]);
			//cell1.setCellValue(titles[i]);
			
			cell.setCellStyle(style);
			//cell1.setCellStyle(style);
			
			
		}
		int rowNum=0;
		Iterator listIter;
		if(fileFlag!=null && !fileFlag.trim().equals("") && fileFlag.trim().equals("1")){

		List<InvoiceMsgDto>invMsgDtolist=excErr.getInvoiceDtoS();
		listIter=invMsgDtolist.iterator();
		
		while(listIter.hasNext()){
			rowNum++;
			InvoiceMsgDto invMsgDto=(InvoiceMsgDto) listIter.next();
			Row dataRow=sheet0.createRow(rowNum);
			Cell cell0=dataRow.createCell(0);
			cell0.setCellValue(invMsgDto.getRowNo());
			Cell cell1=dataRow.createCell(1);
			cell1.setCellValue(invMsgDto.getOlmId());
			Cell cell2=dataRow.createCell(2);
			cell2.setCellValue(invMsgDto.getErrMsg());
		}
		}
		rowNum=0;
		
		if(fileFlag!=null && !fileFlag.trim().equals("") && fileFlag.trim().equals("2")){
			List<InvoiceMsgDto>invMsgDtolist1=excErr.getInvoiceDto();
			listIter=invMsgDtolist1.iterator();
		
		
		while(listIter.hasNext()){
			rowNum++;
			InvoiceMsgDto invMsgDto=(InvoiceMsgDto) listIter.next();
			Row dataRow=sheet1.createRow(rowNum);
			Cell cell0=dataRow.createCell(0);
			cell0.setCellValue(invMsgDto.getRowNo());
			Cell cell1=dataRow.createCell(1);
			cell1.setCellValue(invMsgDto.getOlmId());
			Cell cell2=dataRow.createCell(2);
			cell2.setCellValue(invMsgDto.getErrMsg());
			}
		}
			
		String fName="ErrorFile"+System.currentTimeMillis()+".xls";
		File file=new File(path,fName);
		String absPath=file.getAbsolutePath();
		FileOutputStream fout=new FileOutputStream(absPath);
		workbook.write(fout);
		fout.close();
		
		
		return absPath;
	
	}

	@Override
	public String invoiceUploaded(String absFile,long loginId) throws Exception {
		// TODO Auto-generated method stub
		
		String msg="";
		DpInvoiceDao dpinv=new DpInvoiceDaoImpl();
		msg=dpinv.invoiceUploaded(absFile, loginId);
		return msg;
	}

	@Override
	public List<InvoiceDetails> listInvoicesAcc(String status) throws Exception {
		// TODO Auto-generated method stub

		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listInvoicesAcc(status);
		
		return invList;
	}

	@Override
	public List<InvoiceDetails> listInvoicesDist(String loginNm, String statFlag)
			throws Exception {
		// TODO Auto-generated method stub
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listInvoicesDist(loginNm,statFlag);
		
		return invList;
	}

	@Override
	public List<InvoiceDetails> listAllInvoicesDist(String loginNm)
			throws Exception {
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		List<InvoiceDetails> invList=invDao.listAllInvoicesDist(loginNm);
		return invList;
	}

	@Override
	public PartnerDetails partnerDetails(String olmId) throws Exception {
		
		PartnerDetails partnerDet=new PartnerDetails();
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		partnerDet=invDao.partnerDetails(olmId);
		
		return  partnerDet;
	}

	@Override
	public RateDTO fetchRates(Date billDt) throws Exception {
		// TODO Auto-generated method stub
		RateDTO rates=new RateDTO();
		DpInvoiceDao invDao=new DpInvoiceDaoImpl();
		rates=invDao.fetchRates(billDt);
		return rates;
	}

	@Override
	public List<String> listSignedInvoices(String loginNm) throws Exception {
		// TODO Auto-generated method stub
		
		
			String passWord = "";
			String src ="";
			List<String> signedList=new ArrayList<String>();
			
			logger.info("src===="+src);
			UserInfo userinfo = new MyUserInfo();
			JSch jsch = new JSch();
			Session session;
			String username = "";
			String host = "";
			
			 try {
					host = ResourceReader.getCoreResourceBundleValue("payout.sftp.hostname");
					username = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.username");
					passWord = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.password");
					src = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.remotepath");
			        } catch(VirtualizationServiceException vex){
			        	vex.printStackTrace();
			        	logger.info("Unable to read propoerty from Recharge Core");
			      
			        }
			if (src==null || src.trim().equals(""))
				src=loginNm +"/signed";
			else
				src+="/"+loginNm +"/signed";
		//	String dstPath = PropertyReader.getString(com.ibm.utiility.Constants.HR_SFTP_DST);
			int port = 22;
			logger.info("Read Properties");
			
			logger.info("USER :" + username + " trying to connect to sftp host :"
					+ host);

			//passWord = PasswordEncDec.decPassword(passWord);

			((MyUserInfo) userinfo).setPasswd(passWord);

			try {
				logger.info("Creating sftp session with host");
				session = jsch.getSession(username, host, port);
				logger.info("Session info :" + session.getUserName());
				session.setUserInfo(userinfo);
				session.setPassword(passWord);
				session.setConfig("StrictHostKeyChecking", "no");
				session.setConfig("PubkeyAuthentication", "no");
			} catch (JSchException e) {
				logger.info("JSchException occured");
				e.printStackTrace();
				throw new JSchException();
			}

			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			logger.info("Connected to the remote machine");
			ChannelSftp c = (ChannelSftp) channel;
			try {
				logger.info("Listing file from remote host" +src);
				logger.info("Current directory: " + c.pwd());
				c.cd(src);
				logger.info("Directory chaged to :" + c.pwd());
				Vector<ChannelSftp.LsEntry> listFiles = c.ls(c.pwd());
				
				for (ChannelSftp.LsEntry rfile : listFiles) {
					if (!rfile.getFilename().matches(
							loginNm+"_(.*)_Sign.pdf"))
						continue;
					signedList.add(rfile.getFilename());
					logger.info("Signed invoice "+ rfile.getFilename())
;				}
					
					
			}catch(Exception expx){
				expx.printStackTrace();
				logger.info("Exception in listing invoices");
			}
			finally
			{
				session.disconnect();
				channel.disconnect();
			}
		return signedList;
	}

	public File getSignedInvoices(String loginNm,String fileName) throws Exception {
		// TODO Auto-generated method stub
		
		
			String passWord = "";
			String src ="" ;
			List<String> signedList=new ArrayList<String>();
			FileOutputStream fout=null;
			File file=null;
			
			logger.info("src===="+src);
			UserInfo userinfo = new MyUserInfo();
			JSch jsch = new JSch();
			Session session;
			String username = "";
			String host = "";
			
			
			 try {
					host = ResourceReader.getCoreResourceBundleValue("payout.sftp.hostname");
					username = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.username");
					passWord = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.password");
					src = ResourceReader.getCoreResourceBundleValue("payout.sftp.server.remotepath");
			        } catch(VirtualizationServiceException vex){
			        	vex.printStackTrace();
			        	logger.info("Unable to read propoerty from Recharge Core");
			        }

			    if (src==null || src.trim().equals(""))
						src=loginNm +"/signed";
				else
						src+="/"+loginNm +"/signed";
				//	String dstPath = PropertyReader.g
		//	String dstPath = PropertyReader.getString(com.ibm.utiility.Constants.HR_SFTP_DST);
			int port = 22;
			logger.info("Read Properties");
			
			logger.info("USER :" + username + " trying to connect to sftp host :"
					+ host);

			//passWord = PasswordEncDec.decPassword(passWord);

			((MyUserInfo) userinfo).setPasswd(passWord);

			try {
				logger.info("Creating sftp session with host");
				session = jsch.getSession(username, host, port);
				logger.info("Session info :" + session.getUserName());
				session.setUserInfo(userinfo);
				session.setPassword(passWord);
				session.setConfig("StrictHostKeyChecking", "no");
				session.setConfig("PubkeyAuthentication", "no");
			} catch (JSchException e) {
				logger.info("JSchException occured");
				e.printStackTrace();
				throw new JSchException();
			}

			session.connect();
			Channel channel = session.openChannel("sftp");
			channel.connect();

			logger.info("Connected to the remote machine");
			ChannelSftp c = (ChannelSftp) channel;
			try {
				logger.info("Listing file from remote host");
				logger.info("Current directory: " + c.pwd());
				c.cd(src);
				logger.info("Directory chaged to :" + c.pwd());
				Vector<ChannelSftp.LsEntry> listFiles = c.ls(c.pwd());
				
				for (ChannelSftp.LsEntry rfile : listFiles) {
					if (!rfile.getFilename().matches(
							fileName))
						continue;
					logger.info("Signed invoice "+ rfile.getFilename());
					String path=ResourceReader.getWebResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.PAYOUT_PATH);
					
					 file=new File(path,rfile.getFilename());
					 fout=new FileOutputStream(file);
					c.get(rfile.getFilename(), fout);
					fout.flush();
					
					
					}
					
					
			}catch(Exception expx){
				expx.printStackTrace();
				logger.info("Exception in listing invoices");
				throw new Exception();
			}finally {
				try {
					if (fout != null)
						fout.close();
				} catch (IOException e) {
					logger.info("Error in closing file");
					e.printStackTrace();
				}
				finally
				{
					session.disconnect();
					channel.disconnect();
				}
			}
		return file;
	}
	
	@Override
	public boolean sftpInvoiceUpload(String localPath, String loginName) throws JSchException, SftpException,
	IOException{
		int port = 22;
		String username = "";
		String host = "";
		String passWord="";
		String dest="";
		JSch jsch = new JSch();
		Session session;
		UserInfo userinfo = new MyUserInfo();
		boolean flag = false;
		logger.info("Reading properties");
        try {
		host = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DS_HOST);
		username = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DS_USER);
		passWord = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DS_PASS);
		dest = ResourceReader.getCoreResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.DS_DEST);
        } catch(VirtualizationServiceException vex){
        	vex.printStackTrace();
        	logger.info("Unable to read propoerty from Recharge Core");
        }
        logger.info(localPath);
        String fileSep = System.getProperty("file.separator");
		String fileName = localPath.substring(localPath.lastIndexOf("/")+1);
		
		String destPath="";
		if(dest==null || dest.trim().equals(""))
			destPath =loginName+"/unsigned/"+fileName;
		else 
			destPath=dest+"/"+loginName+"/unsigned/"+fileName;
		
		logger.info("File seperator = " +fileSep+"                  File Name = "+fileName+"               Destination Path = "+destPath);
		port = 22;
		
		logger.info("Upload path in remote host   ---    "+destPath);
		
		logger.info("Read Properties");
		logger.info("USER :" + username + " trying to connect to sftp host :"
				+ host);

		//passWord = PasswordEncDec.decPassword(passWord);

		((MyUserInfo) userinfo).setPasswd(passWord);

		try {
			logger.info("Creating sftp session with host");
			session = jsch.getSession(username, host, port);
			logger.info("Session info :" + session.getUserName());
			session.setUserInfo(userinfo);
			session.setPassword(passWord);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setConfig("PubkeyAuthentication", "no");
		} catch (JSchException e) {
			logger.info("JSchException occured");
			e.printStackTrace();
			throw new JSchException();
		}

		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();

		logger.info("Connected to the remote machine");
		ChannelSftp c = (ChannelSftp) channel;
		try {
			logger.info("Current directory: " + c.pwd());
			//c.cd(destPath);
			logger.info("Directory chaged to :" + c.pwd());
			FileInputStream fin = new FileInputStream(localPath);
			try {
				try{
					SftpATTRS attrs = c.lstat(loginName);
					try{
						SftpATTRS attrs1 = c.lstat(loginName+"/unsigned");
						c.put(fin, destPath);
					}
					catch (Exception exp){
					c.mkdir(loginName+"/unsigned");
					c.put(fin, destPath);
					try{
					c.mkdir(loginName+"/signed");
					}catch(Exception expx){}
					
					}
				}
				catch (Exception exp){
					c.mkdir(loginName);
					c.mkdir(loginName+"/unsigned");
					c.mkdir(loginName+"/signed");
					c.put(fin, destPath);
				}
				} catch (Exception exp) {
					logger
							.info("Exception occured while creating file at destination folder"
									+ localPath);
					exp.printStackTrace();
					return false;
				} finally {
					try {
						if (fin != null)
							fin.close();
					} catch (IOException e) {
						logger.info("Error in closing file");
						e.printStackTrace();
						return false;
					}
				}
				logger.info("Uploaded the file  -    " + localPath);
		} catch (SftpException sftpex) {
			logger.info(sftpex.getMessage());
			sftpex.printStackTrace();
			logger.info("Unable to upload the file to remote server");
			throw new SftpException(-1, sftpex.getMessage());
		}
    finally
		{
			session.disconnect();
			channel.disconnect();
		}
		return true;
	}
	public String fetchAddress() throws Exception
	{
		DpInvoiceDao dao= new DpInvoiceDaoImpl();
		
		String address=dao.fetchAddress();
		return address;
	}
	
}

