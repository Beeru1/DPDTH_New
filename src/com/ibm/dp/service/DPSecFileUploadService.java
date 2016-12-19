/**
 * 
 */
package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.dp.exception.DPServiceException;

/**
 * @author Rohit Kunder
 *
 */
public interface DPSecFileUploadService {
	
	public String SecFileUploadSave(byte[] fileData,String filePath,String changedFileName,int loginID,int circleID)throws DPServiceException;
}
