/**
 * 
 */
package com.ibm.dp.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.dp.actions.DPSecFileUploadAction;
import com.ibm.dp.common.Constants;
import com.ibm.dp.dao.DPPurchaseOrderDao;
import com.ibm.dp.dao.DPSecFileUploadDao;
import com.ibm.dp.dao.impl.DPSecFileUploadDaoImpl;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPSecFileUploadService;
import com.ibm.hbo.exception.HBOException;
import com.ibm.utilities.ErrorCodes;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;

/**
 * @author Rohit Kunder
 *
 */
public class DPSecFileUploadServiceImpl implements DPSecFileUploadService{

	public DPSecFileUploadServiceImpl(){
		
	}
	private static Logger logger = Logger.getLogger(DPSecFileUploadServiceImpl.class.getName());

	public String SecFileUploadSave(byte[]fileData, String filePath, String fileName,int loginID,int circleID) throws DPServiceException {
		
		String Sucess= "succes";
		String Error= "error";
		
		java.sql.Connection connection = null;
		StringBuffer tempFileNamestr = new StringBuffer();
		StringBuffer fileNamestr = new StringBuffer();
	
		RandomAccessFile raf = null;
		String FilewithCurrentDT=changedFileName(fileName,loginID);
		fileNamestr.append(filePath).append(FilewithCurrentDT);
		tempFileNamestr.append(filePath).append("DPTemp").append(".csv");
		
		File f1 = new File(tempFileNamestr.toString());
		File f2 = new File(fileNamestr.toString());
		try {

			if (f1.exists()) {
				f1.delete();
			}
			
			raf = new RandomAccessFile(tempFileNamestr.toString(), "rw");
			raf.write(fileData);
			raf.close();
			f1.renameTo(f2);
			f2.delete();
			//f1.renameTo(f2);
		try {

				connection = DBConnectionManager.getDBConnection();
				DPSecFileUploadDao dpsFULDao = DAOFactory	.getDAOFactory(	Integer	.parseInt(ResourceReader
						.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getDPSecFileUploadDao(connection);
				int i =dpsFULDao.insertUploadFileInfo(circleID, fileName, loginID, filePath);
				if(i==0){
					logger.info("**********->ERROR IN INSERTING DATA INTO TABLE DP_UPLOAD_FILE_SEC_DATE , output i value is-> " +i );
				}
				
				
			} catch (Exception e) {
				logger.error("**********->ERROR IN INSERTING SECUPLOAD FILE INFO [SecFileUploadSave(byte[]fileData, String filePath, String fileName,int loginID,int circleID)] function of  DPSecFileUploadServiceImpl ");
				throw new DPServiceException(e.getMessage());
			}
			finally {
				/* Close the connection */
				DBConnectionManager.releaseResources(connection);
			}

			return Sucess;
		}catch (IOException e) {

			if (f1.exists()) {
				f1.delete();
				return Error;
			}
			throw new DPServiceException(e.getMessage());
		}
		finally {
			try {
				raf.close();
			}
			catch (Exception e) {
				logger.error("**********->ERROR IN FINALLY -> INSERTING SECUPLOAD FILE INFO [SecFileUploadSave(byte[]fileData, String filePath, String fileName,int loginID,int circleID)] function of  DPSecFileUploadServiceImpl"+e.getMessage());
				
			}
		}
}	


	public String changedFileName(String fileName, int loginID) {
		String subString = null;
		int stopPos = fileName.lastIndexOf(".");
		subString = fileName.substring(0, stopPos);
		StringBuffer changedFileName = new StringBuffer();
		String currDt =getCurrentDate("yyyyMMdd-HHmmss");
		changedFileName.append(currDt).append("-").append(loginID).append("_").append(subString).append(".csv");

		return changedFileName.toString();
	}

	public String getCurrentDate(String dateFormat) {
		DateFormat df = new SimpleDateFormat(dateFormat);
		long currentTime = System.currentTimeMillis();
		Date curDate = new Date(currentTime);
		return df.format(curDate);
	}



}
