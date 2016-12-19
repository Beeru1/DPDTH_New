/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/

package com.ibm.virtualization.recharge.common;

import org.apache.log4j.Logger;

/**
 * Class to write the data in to the CSV file.
 * 
 * @ Mohit Aggarwal
 */
public class CSVWriter {
    
	/** logger to write the data to CSV file in case error occurs while database interaction 
	 *  or while putting the message in queue */
	private static Logger logger = Logger.getLogger(CSVWriter.class);
    
    /**
     * log the data in to the CSV file using log4j.
     * 
     * @param content
     */
    public static void write(StringBuilder content){
    	logger.info(content);
    }
}
