/**************************************************************************
 * File     : BulkSubscriberService.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.bulkupload.services.interfaces;

import java.util.ArrayList;

import org.apache.log4j.Logger;

//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;


/**
 * @author a_gupta
 *
 */
public interface BulkSubscriberService {
    
	/**
	 *  getting the logger refrence
	 */
	public static final Logger log = Logger.getLogger(BulkSubscriberService.class);

	/**
	 * 
	 * @return list of subscriber files having status='SAVED'
	 */
	public  ArrayList getScheduledFilesforSubscriber();
	
	/**
	 * read the uploaded file identify the failed and success data and write the failed data in the failed 
	 * file and insert the valid data in V_SUBSCRIBER_MSTR
	 * @param bulkSubscriberDTO having the subscriber information 
	 */
	//public  void uploadFile(BulkSubscriberDTO bulkSubscriberDTO);
		
}
