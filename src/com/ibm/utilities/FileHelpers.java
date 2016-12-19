package com.ibm.utilities;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.upload.FormFile;

public class FileHelpers {
//start added by Beeru on 20 march 2014
	public static Iterator<Row> readExcelFile(FormFile file) {
		try {
			  InputStream inputstream = file.getInputStream();
			  String fileName =  file.getFileName();
			if(fileName.substring(fileName.lastIndexOf(".")+1).equalsIgnoreCase("xls")) {
				HSSFWorkbook workbook = new HSSFWorkbook(inputstream);
				HSSFSheet  sheet = workbook.getSheetAt(0);
				return sheet.rowIterator();
			}else {
				XSSFWorkbook workbook = new XSSFWorkbook(inputstream);
				XSSFSheet sheet = workbook.getSheetAt(0);
			    return sheet.rowIterator();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
//End added by Beeru
}
