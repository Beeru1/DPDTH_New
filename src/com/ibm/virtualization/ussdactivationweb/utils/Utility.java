/**************************************************************************
* File     : Utility.java
* Author   : Sampreet
* Created  : Apr 17, 2008
* Modified : Apr 17, 2008
* Version  : 0.1
**************************************************************************
*                               HISTORY
**************************************************************************
* V0.1		Apr 17, 2008 	Sampreet	First Cut.
* V0.2		Apr 17, 2008 	Sampreet	First modification
**************************************************************************
*
* Copyright @ 2002 This document has been prepared and written by 
* IBM Global Services on behalf of Bharti, and is copyright of Bharti
*
**************************************************************************/

package com.ibm.virtualization.ussdactivationweb.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/*********************************************************************************
 * This class 
 * 
 * 
 * @author Sampreet
 * @version 1.0
 ********************************************************************************************/

public class Utility {
	
	
	/*
	 * Logger for this class.
	 *
	 */
	private static Logger logger = Logger.getLogger(Utility.class.getName());	


	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */

	public static Date getDateAsDate(String date, String format) throws Exception {
		
		SimpleDateFormat sdf;
		try {
      		sdf = new SimpleDateFormat(format);
         	return sdf.parse(date);
			
		} catch (ParseException e) {
		    logger.error("Error pasring date string - " + date, e);
			throw new Exception("Invalid Date String");
		}
	}
	
	
	/**
	 * 
	 * @param date
	 * @param format
	 * @return
	 */

	public static long getDate(String date, String format) {
		
		SimpleDateFormat sdf;
		try {
      		sdf = new SimpleDateFormat(format);
         	return sdf.parse(date).getTime();
			
		} catch (ParseException e) {
		    e.printStackTrace();
			logger.info("Exception occured while parsign date", e);
			return 00;
		}
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
	
public static String getDateAsString(Date date, String format) {
		
		format = (format == null) ? "dd-MM-yyyy-hh-mm-ss" : format;
		date = (date == null) ? new Date(): date;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);		
	}
	
	/**
	 * This method reads the values from the input String <code>strInput</code> separated by the <code>delimiter</code>,  
	 * and returns the values as ArrayList of String objects.
	 * 
	 * @param strInput The input String <code>strInput</code> containing the values
	 * @param delimiter The delimiter which separates the values within the input String <code>strInput</code>. Default delimiter is ",".
	 * 
	 * @return The values as ArrayList of String objects.
	 */
	public static ArrayList stringToArrayList(String strInput, String delimiter) {
		
		 ArrayList arrayList = new ArrayList();
		 if(strInput.indexOf(delimiter)!=-1) {
			String arr[]=strInput.split(",");
			for (int num = 0, len = arr.length; num < len; num++){ 
				arrayList.add(arr[num].trim()); 
			}
		 } else{
		 arrayList.add(strInput);
		 }
		return arrayList;
	}
	
	/**
	 * This method reads the values from the input String Array <code>array</code>   
	 * and returns a single String with values separated by the <code>delimiter</code>.
	 * 
	 * @param array The input String Array <code>array</code>
	 * @param delimiter The delimiter which separates the values within the returned String. Default delimiter is ",".
	 * 
	 * @return the String haveing the values of <code>array</code>, separated by the delimiter.
	 */
	public static String arrayToString(String array[], String delimiter ) {
		
		StringBuffer buffer = new StringBuffer(50);
		delimiter = (delimiter != null) ? delimiter : ",";
		if(array != null) {
			for(int index = 0, len = array.length; index < len; index++)
			{
				if( array[index] != null) { buffer.append(array[index]); }
				else continue;
				if(index < (len - 1)) { buffer.append(delimiter); }
			}
		}
		
		return buffer.toString();
	}
	
	public static void main(String args[]) {
		
		String str = "This,is,a,Test";
		//System.out.println("stringToArrayList for String "+ str + " is "+ stringToArrayList(str, null));
		
		String str1 = "This@is@a@Test@";
		//System.out.println("stringToArrayList for String1 "+ str1 + " is "+ stringToArrayList(str1, "@"));
		
		String strArr[] = new String[] {"testing", "one", null, "three"};
		//System.out.println("arrayToString  for  array is "+ arrayToString(strArr, null));
		
		//System.out.println("getDateAsString( , ) = " + getDateAsString(null, null));
		//System.out.println("getDateAsString(dd/MM/yyyy) = " + getDateAsString(null, "dd/MM/yyyy"));
		//System.out.println("getTimestampAsString(dd/MM/yyyy) = " + getTimestampAsString(null, "dd/MM/yyyy"));
	}	
	

	
	public static String getDateAsStringFromString(String date, String formate) {
		long date1= Utility.getDate(date, "dd/MM/yyyy");
		   Timestamp timeStamp=new Timestamp(date1);
		   return Utility.getTimestampAsString(timeStamp, formate);
		
	}
	public static String getDateAsStringFromString1(String date, String strFormate) {

		long longDate= Utility.getDate(date, "yyyy-MM-dd");
		Timestamp timeStamp=new Timestamp(longDate);
		return Utility.getTimestampAsString(timeStamp, strFormate);

	}
	
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
	
	/**
	 * This method return map having all the month and correspong value 
	 * @return map 
	 */
/*	public static HashMap getMonthList(){
		 String months[] = {"January",
			      "February","March","April",
			      "May","June","July","August","September",
			      "October","November","December"};
		 LinkedHashMap monthMap = new LinkedHashMap();
		 for (int i = 1; i <= months.length; i++) {
			 monthMap.put(months[i-1],i);
		} 
		 	return monthMap;
	}*/
	/**
	 * 
	 * @param  intYear input year 
	 * @param intMonth input month for which last date required
	 * @return last date of the month
	 */
	public static int  getDaysInMonth(int intMonth,int intYear) {
		int daysInMonth[] = {31,28,31,30,31,30,31,31,30,31,30,31};
		if (intMonth != 2) return daysInMonth[intMonth - 1];
		if (intYear%4 != 0) return daysInMonth[1];
		if (intYear % 100 == 0 && intYear % 400 != 0) return daysInMonth[1];
		return daysInMonth[1] + 1;
	}
	
	/**
	 * This method returns the value of property of the given resource bundle
	 * and property.
	 *
	 * @param propertyKey
	 * @param resourceBundleName
	 * @return String
	 * @throws VirtualizationServiceException
	 */
	public static String getValueFromBundle(String propertyKey,
			String resourceBundleName) {

		String keyValue = null;
		try {

			ResourceBundle resourceBundle = ResourceBundle
					.getBundle(resourceBundleName);
			return resourceBundle.getString(propertyKey);
				
		}
		catch (MissingResourceException missingResourceExp) {
			missingResourceExp.printStackTrace();
			logger.error(new StringBuffer(75)
				.append(
				"getValueFromBundle() method : caught MissingResourceException : Unable to get ")
				.append(resourceBundleName).append(" : ")
				.append(missingResourceExp.getMessage())
				.toString(), missingResourceExp);
		}
		return keyValue;
	}
	/** return the Map containg first and last date of the month for input date 
	 *  if input data parameter is null it returns first and last date of the current month
	 * @return map object having first and last date of the month 
	 */
	public static HashMap getFtAndLtDateOfMonth(String strDate) {
		Calendar cal=Calendar.getInstance();
		
		Date sqlDate=null;
		
		if(strDate != null && !"".equals(strDate)){
			sqlDate = new Date(Utility.getDate(strDate, "dd/MM/yyyy"));
			cal.setTime(sqlDate);	
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDateOfMonth = Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy");
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		String lastDateOfMonth = Utility.getDateAsString(cal.getTime(), "dd/MM/yyyy");
		String monthOftheDate =Utility.getDateAsString(cal.getTime(),
		"MMM");
		HashMap hashMap = new HashMap();
		hashMap.put("firstDateOfMonth",firstDateOfMonth);
		hashMap.put("lastDateOfMonth",lastDateOfMonth);
		hashMap.put("monthOftheDate",monthOftheDate);
		return hashMap;
	}
	
	
	
	public static Date stringToDate(String date) {
		Date dt = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = new String(date);
			dt = sdf.parse(date1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}
	
	public static Timestamp stringToTimestamp(String date) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat sdf1 = new SimpleDateFormat(
			"dd-MM-yyyy-HH-mm-ss");
			String date1 = new String(date);
			java.util.Date dt1 = sdf1.parse(date1);
			timestamp = new Timestamp(dt1.getTime());
				

		} catch (Exception e) {
			e.printStackTrace();
		}
		return timestamp;
	}

	public static String checkForMachingCharactersInPwdLogin(String password , String loginId)
	{
		String matched = "false";
		try{
				for(int i =0;i<loginId.length();i++)
				{
					for(int j=0;j<password.length();j++)
					{
						if(password.charAt(j)==loginId.charAt(i))
						{
							if(password.length()-1 > j+1 && loginId.length()-1>i+1)
							{
								if(password.charAt(j+1)==loginId.charAt(i+1))
								{
									if(password.length()-1 > j+2 && loginId.length()-1>i+2)
									{
										if(password.charAt(j+2)==loginId.charAt(i+2))
										{
											if(password.length()-1 >= j+3 && loginId.length()-1 >= i+3)
											{
												if(password.charAt(j+3)==loginId.charAt(i+3))
												{
													matched = "true";
													break;
												}
											}
										}
									}
								}
							}
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return matched; 
	}
	
	
	/**
	 * Method to validate whether the number is in range or not.
	 * 
	 * @param start
	 * @param end
	 * @param value
	 * @return
	 */
	public static boolean inRange(int start, int end, int value){
		if(value>=start && value<=end){
			return true;
		}
		return false;
	}
	//added by Beeru on 16 Apr 2014
	public static String getStackTrace(Exception e)
	{
	    StringWriter sWriter = new StringWriter();
	    PrintWriter pWriter = new PrintWriter(sWriter);
	    e.printStackTrace(pWriter);
	    return sWriter.toString();
	}

}
