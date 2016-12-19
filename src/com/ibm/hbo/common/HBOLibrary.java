/*
 * Created on Aug 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.hbo.actions.HBOMasterAction;
import com.ibm.hbo.exception.HBOException;
import com.ibm.utilities.ErrorCodes;

/**
 * @author avanagar
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HBOLibrary {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOLibrary.class);
		
	}
	private static final String TEMPFILENAME = "tempFile";
	private static final String csv = ".csv";
	private static final String CLASSNAME = "PrepaidLibrary";
	public String replaceWord(String original, String find, String replacement) {

		int i = original.indexOf(find);
		StringBuffer strBuff = new StringBuffer();
		//logger.info("Value of i is ::"+i);
		String str = null;
		String partBefore = original.substring(0, i);
		//logger.info("Value of partBefore is ::"+partBefore);
		String partAfter = original.substring(i + find.length());
		strBuff.append(partBefore);
		strBuff.append(replacement);
		strBuff.append(partAfter);
		if (strBuff.toString().indexOf(find) > 0) {
			str = replaceWord(strBuff.toString(), find, replacement);
			return str;
		}
		return strBuff.toString();
	}

	public String truncDateTime(String fileName) {
		//String file = fileName.substring(20, fileName.length());
		//logger.info("HBO :file-----------"+file);
		//int stopPos = file.lastIndexOf("-");
		int stopPos = fileName.lastIndexOf("-");
		return fileName.substring(0, stopPos);
	}

	public void writeToFile(String format, byte[] fileData, String filePath, String changedFileName) throws HBOException {
		StringBuffer tempFileNamestr = new StringBuffer();
		StringBuffer fileNamestr = new StringBuffer();
		String methodName = "writeToFile";
		RandomAccessFile raf = null;
		logger.info("file path="+filePath);
		tempFileNamestr.append(filePath).append(TEMPFILENAME).append(csv);
		fileNamestr.append(filePath).append(changedFileName).append(csv);
		File f1 = new File(tempFileNamestr.toString());
		File f2 = new File(fileNamestr.toString());
		try {

		//	String lineSeparator = (String)java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
			if (f1.exists()) {
				f1.delete();
			}
			logger.info("format-----------"+format);
			raf = new RandomAccessFile(tempFileNamestr.toString(), "rw");
		//	raf.write(format.getBytes());
		//	raf.write(lineSeparator.getBytes());
			raf.write(fileData);
			raf.close();
			//f2.delete();
			f1.renameTo(f2);
		}
		catch (IOException e) {
			if (f1.exists()) {
				f1.delete();

			}
			e.printStackTrace();
			throw new HBOException(CLASSNAME, methodName, e.getMessage(), ErrorCodes.UPLOADEXCEPTION);

		}
		finally {
			try {
				raf.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void writeToFile(byte[] fileData, String filePath, String changedFileName) throws HBOException {
		java.io.FileOutputStream fos = null;
		String methodName = "writeToFile";
		FileWriter fileWriter = null;
		StringBuffer fileNamestr = new StringBuffer();
		fileNamestr.append(filePath).append(changedFileName).append(csv);
		try {

			fos = new java.io.FileOutputStream(fileNamestr.toString());
			fos.write(fileData);
			fos.flush();
			fos.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new HBOException(CLASSNAME, methodName, e.getMessage(), ErrorCodes.UPLOADEXCEPTION);

		}
		finally {
			try {
				fos.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
					 * This method returns current Date Time.
					 * @param String dateFormat
			 */

	public String getCurrentDate(String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		long currentTime = System.currentTimeMillis();
		Date curDate = new Date(currentTime);
		return df.format(curDate);
	}

	public String changedFileName(String fileName, String circleId,String warehouseId, String contentOfFile ,int fileId) {
		String subString = null;
		int stopPos = fileName.lastIndexOf(".");
		subString = fileName.substring(0, stopPos);
		StringBuffer changedFileName = new StringBuffer();
		changedFileName.append(circleId).append("-").append(warehouseId).append("-").append(getCurrentDate("yyyyMMdd-HHmmss")).append("-").append(subString).append("-").append(
			contentOfFile).append("-").append(fileId);
		return changedFileName.toString();
	}
	
	public String changedFileName(String fileName,String warehouseId){ //,int fileId) {
		String subString = null;
		int stopPos = fileName.lastIndexOf(".");
		subString = fileName.substring(0, stopPos);
		StringBuffer changedFileName = new StringBuffer();
		changedFileName.append(warehouseId).append("-").append(getCurrentDate("yyyyMMdd-HHmmss")).append("-").append(subString); //.append("-").append(fileId);
		return changedFileName.toString();
	}
	
	public boolean isInteger(String check)
	{
		String theInput = check.trim();
		int theLength = theInput.length();

		for (int i = 0 ; i < theLength ; i++)
		{
			if (theInput.charAt(i) < '0' || theInput.charAt(i) > '9')
			{
				 //the text field has a non numeric entry
				return(false);
			}
		}// for ends

		return (true);
	}// function isInteger ends
	
	public String checkNull(String value) {
		return ((value==null)?"":value);
	}
	public String checkNullInteger(String value) {
		if(value==null) {
		 value = "0";
		}
		if(value.equals("")) {
			value = "0";
		}
		return value;
	}


}
