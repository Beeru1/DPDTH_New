/*****************************************************************************\
 ** Virtualization - Activation.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 \****************************************************************************/

package com.ibm.virtualization.ussdactivationweb.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

	/**
	 * @author Ashad
	 *
	 * Utility class contails all utility methods
	 * which are use througth the application. 
	 *  
	 */

   public class USSDCommonUtility {
	   
	   private static final Logger logger = Logger
		.getLogger(USSDCommonUtility.class);

	/**
	 * this method called from the class where pagination required 
	 * it return dto object with four values nextPage prePage 
	 * lowerBound and upperBound 
	 * 
	 * @param request :HttpServletRequest
	 * @return        :USSDUtilityDTO
	 */
   	
	   
	   /**
		 * 
		 * @param strDate input date 
		 * @param strFormat date formate
		 * @return Sql Date corresponding to String date
		 */
		
		public static java.sql.Date getSqlDateFromString(String strDate,String strFormat) {

			SimpleDateFormat sdf;
			try {
				sdf = new SimpleDateFormat(strFormat);
				return new java.sql.Date(sdf.parse(strDate).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.info("Exception occured while parsign date", e);
				return null;
			} 
		}
  
   	public static USSDUtilityDTO paginationImpl(HttpServletRequest request) {
    	int intLBound = 0 , intUBound = 0 ,intPage = 0,intRecordsPerPage=10; 
        //variables used in paging
    	String strSelectedPage=request.getParameter("page");
    	if(strSelectedPage == null || strSelectedPage == "" || strSelectedPage.equals("") )
    	{// page not selected
    		intLBound = 0;
    		intUBound =intRecordsPerPage;
    	}
    	else{
    		// page is selected
    		intPage = Integer.valueOf(strSelectedPage).intValue();
    		intLBound = intRecordsPerPage*(intPage-1);
    		intUBound = intLBound + intRecordsPerPage;
    		
    	}
    	String strNextPage="";
    	String strPrevPage="";
    	if (request.getParameter("page")==null){
    		strNextPage="2";
    		strPrevPage="0";
    	}
    	else{
    		strNextPage=Integer.toString(Integer.parseInt(request.getParameter(Constants.PAGE))+1);
    		strPrevPage=Integer.toString(Integer.parseInt(request.getParameter(Constants.PAGE))-1);
    	}
    	USSDUtilityDTO distFOSReportDTO=new USSDUtilityDTO();
    	distFOSReportDTO.setNext(strNextPage);
    	distFOSReportDTO.setPrevPage(strPrevPage);
    	distFOSReportDTO.setUpperBound(intUBound);
		distFOSReportDTO.setLowerBound(intLBound);
    	return distFOSReportDTO;
    }
	
   	
	/**
	 * return the number of pages for pagination implementation
	 * 
	 * @param recordsPerPage :int
	 * @return : int
	 * 
	 */
	
	public static int noOfPages(int recordsPerPage,int noofRow) {
		int noofpages;
		if(noofRow%recordsPerPage==0) {
			noofpages = noofRow/recordsPerPage;
		}	else{
			noofpages = noofRow/recordsPerPage +1;
		}
		return noofpages;
	}
	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getTimestampAsString(Timestamp timestamp, String format) {
		
		format = (format == null) ? "dd-MM-yyyy-hh-mm-ss" : format;
		Date date = (timestamp == null) ? new Date(): new Date(timestamp.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);		
	}

	    
	    
	    public Date stringToDate(String date) {
			Date dt = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
				String date1 = new String(date);
				dt = sdf.parse(date1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return dt;
		}
    
	    
	    public static long getDate(String date, String format) {

			SimpleDateFormat sdf;
			try {
				sdf = new SimpleDateFormat(format);
				return sdf.parse(date).getTime();

			} catch (ParseException e) {
				e.printStackTrace();
				logger.debug("Exception occured while parsign date", e);
				return 00;
			}
		}
	
}
