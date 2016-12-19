/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces;

import java.util.ArrayList;

import org.apache.log4j.Logger;

//import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserCreationDTO;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;

/**
 * @author Nitesh
 *
 */
public interface BulkBizUserCreationService {

	/**
	 *  getting the logger refrence
	 */
	public static final Logger log = Logger.getLogger(BulkBizUserCreationService.class);
	
	
	/**
	 * 
	 * @return list of subscriber files having status='SAVED'
	 */
	public  ArrayList getScheduledFilesforBulkUserCreation();
	
	
	/**
	 * read the uploaded file identify the failed and success data and write the failed data in the failed 
	 * file and insert the valid data in V_BUSINESS_USER_DATA on Distportal
	 * @param bulkSubscriberDTO having the subscriber information 
	 */
	//public  void uploadFile(BulkBizUserCreationDTO bulkBizUserCreationDTO, int creatorUserId);
}
