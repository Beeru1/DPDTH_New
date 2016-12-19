/**************************************************************************
* File     : FileUploadDTO.java
* Author   : anu
* Created  : Apr 28, 2008
* Modified : Apr 28, 2008
* Version  : 0.1
**************************************************************************
*                               HISTORY
**************************************************************************
* V0.1		Apr 28, 2008 	anu	First Cut.
* V0.2		Apr 28, 2008 	anu	First modification
**************************************************************************
*
* Copyright @ 2002 This document has been prepared and written by 
* IBM Global Services on behalf of Bharti, and is copyright of Bharti
*
**************************************************************************/

package com.ibm.virtualization.ussdactivationweb.dto;

import java.io.Serializable;



/*********************************************************************************
 * This class 
 * 
 * 
 * @author anu
 * @version 1.0
 ********************************************************************************************/

public class FileUploadDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	protected String fileName;	
	protected String filePath;
	
	/**
	 * public constructor
	 * 
	 */
	public FileUploadDTO() {

	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * It returns the String representation of the FileUploadDTO. 
	 * @return Returns a <code>String</code> having all the content information of this object.
	 */
	public String toString() {
		return new StringBuffer (500)
	
			.append(", fileName: ").append(fileName)
			.append(", filePath: ").append(filePath)
			.toString();		
	}


		
}
