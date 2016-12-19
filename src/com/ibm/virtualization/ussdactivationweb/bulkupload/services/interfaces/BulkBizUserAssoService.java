/**************************************************************************
 * File     : BulkBizUserAssoService.java
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

import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;

/**
 * @author a_gupta
 *
 */
public interface BulkBizUserAssoService {

	/**
	 * This method returns the list of Files which are scheduled for uploading.
	 * 
	 * @return ArrayList
	 */
	public abstract ArrayList getScheduledFiles();
	
	/**
	 * This method reads the records in the file. Apply different checks on the
	 * records of the file. Checks are : Record in a file should be of specific
	 * format Record in a file should be a valid type of user Record in a should
	 * be active Record in a file should not be mapped to anyone else Record in
	 * a file should have active location
	 * 
	 * If all the things are valid. A record is mapped to a given user.
	 * 
	 * 
	 * @param bulkBizUserAssoDTO :
	 *            bulkBizUserAssoDTO
	 */
	public abstract void uploadFile(BulkBizUserAssoDTO bulkBizUserAssoDTO);
		
}
