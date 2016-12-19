package com.ibm.dp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.CloseInactivePartnersDao;
import com.ibm.dp.dao.DpInvoiceDao;
import com.ibm.dp.dao.impl.CloseInactivePartnersDaoImpl;
import com.ibm.dp.dao.impl.DpInvoiceDaoImpl;
import com.ibm.dp.dto.CloseInactivePartnersDto;
import com.ibm.dp.dto.DpInvoiceDto;
import com.ibm.dp.dto.Sheet2Pojo;
import com.ibm.dp.service.CloseInactivePartnersService;
import com.ibm.virtualization.recharge.common.ResourceReader;

public class CloseInactivePartnersServiceImpl implements CloseInactivePartnersService {
	
	FileOutputStream fous;
	private Logger logger=Logger.getLogger(InvUploadServiceImpl.class);

	
	public String serialDataxls(FormFile file) throws Exception {
		
		String fileNamPre=file.getFileName();
		String path=ResourceReader.getWebResourceBundleValue(com.ibm.virtualization.recharge.common.Constants.PAYOUT_PATH);
		//fileNamPre+=System.currentTimeMillis();
		Calendar cal=Calendar.getInstance();
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int date=cal.get(Calendar.DAY_OF_MONTH);
		
		StringBuffer fileNamebuff= new StringBuffer(fileNamPre.substring(0,fileNamPre.indexOf(".")))
		.append("-")
		.append(date)
		.append("-")
		.append(month+1)
		.append("-")
		.append(year)
		.append(System.currentTimeMillis())
		.append(fileNamPre.substring(fileNamPre.indexOf(".")));
		
		File invFile=new File(path,fileNamebuff.toString());
		String absFileName=invFile.getAbsolutePath();
		
		logger.info("File to be saved at "+absFileName);
		logger.info("New File Name:"+ fileNamebuff.toString());
		
		if(!invFile.exists())
		{
			 fous=new FileOutputStream(invFile);
		}
		else
		{
			logger.info("file cannot be created please retry");
			throw new Exception();
		}
		
		fous.write(file.getFileData());
		fous.flush();
		fous.close();
		
		return absFileName;
		
		
		
	}

	public ArrayList invFileUploadls(String path) throws Exception {
		File file = new File(path);
		FileInputStream fileIn = new FileInputStream(file);
		HSSFWorkbook workbook = null;
		ArrayList<CloseInactivePartnersDto> results=new ArrayList<CloseInactivePartnersDto>();
		try {
			workbook = new HSSFWorkbook(fileIn);
		} catch (Exception exp) {
			logger.info("Error in reading file");
			exp.printStackTrace();
			logger.info("Exiting");
			throw new Exception();
		}
		
		HSSFSheet sheet=workbook.getSheetAt(1);
		HSSFSheet sheet1=workbook.getSheetAt(0);
		
		Iterator itr = sheet.iterator();
		Iterator itr1=sheet1.iterator();
		
		
		Row row1 = sheet.getRow(1);
		Row row0=sheet.getRow(0);
		logger.info("Sheet iteretion begins:");
		//genericExcelProcess(itr, row0, row1);
		results=genericExcelprocss(itr1);
		for(int i = 0; i<results.size();i++){
	        logger.info("Service Layer Method 2: "+i+" DATA  --  "+results.get(i).getOlmid());
	      
	        }
		return results;
		
		
	}
	
	public ArrayList genericExcelprocss(Iterator<Row> rowIter) throws Exception{
		Map<String, CloseInactivePartnersDto> cipMap = new HashMap<String, CloseInactivePartnersDto>();
		ArrayList<CloseInactivePartnersDto> results=new ArrayList<CloseInactivePartnersDto>();
		try{
		while(rowIter.hasNext()){
			Row row=rowIter.next();
			int rowNum = row.getRowNum();
			if (row==null)
			{
				
				logger.info(row.getRowNum());
				logger.info("data"+row.getCell(1).getStringCellValue());
				continue;
			}
			
			String olmCelVal = row.getCell(0).getStringCellValue();
		
					
			
			logger.info("OLM--->"+row.getCell(0).getStringCellValue());
			CloseInactivePartnersDto cipDto=new CloseInactivePartnersDto();
			cipDto.setOlmid(row.getCell(0).getStringCellValue());
			cipDto.setStatus(row.getCell(1).getStringCellValue());
			cipMap.put(olmCelVal, cipDto);
			logger.info("putting values in map");
			
	
		 }
		CloseInactivePartnersDao cipDao = new CloseInactivePartnersDaoImpl();
		results=cipDao.uploadInvoiceSheet(cipMap);
		
		for(int i = 0; i<results.size();i++){
	        logger.info("Service Layer Method 1: "+i+" DATA  --  "+results.get(i).getOlmid());
	      
	        }
		
		}catch(Exception expx){
			expx.printStackTrace();
			throw new Exception();
		}
		return results;
	}

	
	
	
	
}
