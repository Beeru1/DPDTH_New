package com.ibm.dp.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts.upload.FormFile;
import org.apache.log4j.Logger;

import com.ibm.dp.service.InvUploadService;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.utils.PropertyReader;

public class InvUploadServiceImpl implements InvUploadService {

	
	FileOutputStream fous;
	private Logger logger=Logger.getLogger(InvUploadServiceImpl.class);
	
	public String uploadInvoice(FormFile file) throws Exception {

		// To be read from Property file.
		
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

}
