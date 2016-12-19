/**************************************************************************
 * File     : ValidatorUtility.java
 * Author   : Abhipsa Gupta
 * Created  : Jun 19, 2008
 * Modified : Jun 19, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Jun 19, 2008 	Administrator	First Cut.
 * V0.2		Jun 19, 2008 	Administrator	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class have utility methods to help validate data in Prepaid Action
 * classes and Service Implementation classes.
 *
 * @author Abhipsa Gupta
 *
 */
public class ValidatorUtility {


	/**
	 * This method returns true if the strString provided as an argument is a
	 * valid Alpha Numeric String else returns false.
	 *
	 * @param strString
	 * @return boolean
	 */
	public static boolean isAlphaNumeric(String strString) {
		//checking for null
		if(strString==null)
		{
			return false;
		}
		// Set the AlphaNumeric pattern string
		Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9]+");

		// Match the given string with the pattern
		Matcher m = p.matcher(strString);

		// check whether match is found
		return m.matches();

	}

	/**
	 * This method returns true if the string email provided as an argument is a
	 * valid email else returns false.
	 *
	 * @param email
	 * @return boolean
	 */
	public static boolean isValidEmail(String email) {
		//checking for null
		if(email==null)
		{
			return false;
		}
		// Set the email pattern string
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        // change email to lower to because e-mail is case-in sensitive
		email=email.toLowerCase();
		// Match the given string with the pattern

		Matcher m = p.matcher(email);

		// check whether match is found
		return m.matches();

	}

	/**
	 * This method returns true if the length of the string provided as an
	 * argument is more than or equals to minimum length provided else returns
	 * false.
	 *
	 * @param strValue
	 * @param minLength
	 * @return boolean
	 */
	public static boolean isValidMinimumLength(String strValue, int minLength) {
		//checking for null
		if(strValue==null)
		{
			return false;
		}
		
		if (strValue.length() >= minLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method returns true if the length of the string provided as an
	 * argument is less than or equals to maximum length provided else returns
	 * false.
	 *
	 * @param strValue
	 * @param maxLength
	 * @return boolean
	 */
	public static boolean isValidMaximumLength(String strValue, int maxLength) {
		//checking for null
		if(strValue==null)
		{
			return false;
		}
		if (strValue.length() <= maxLength) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method returns true if the string provided as an argument is a valid
	 * number or else returns false.
	 *
	 * @param number
	 * @return boolean
	 */
	public static boolean isValidNumber(String number) {
		//checking for null
		if(number==null)
		{
			return false;
		}
		// Set the number pattern string
		Pattern p = Pattern.compile("([0-9]*)");

		// Match the given string with the pattern
		Matcher m = p.matcher(number);

		// check whether match is found
		return m.matches();

	}

	/**
	 * This method returns true if the string amount provided as an argument is
	 * a valid amount else returns false.
	 *
	 * @param amount
	 * @return boolean
	 */
	public static boolean isValidAmount(String amount) {
		//checking for null
		if(amount==null)
		{
			return false;
		}
		// Set the amount pattern string
		Pattern p = Pattern.compile("([0-9]*\\.[0-9]+|[0-9]+)");

		// Match the given string with the pattern
		Matcher m = p.matcher(amount);

		// check whether match is found
		return m.matches();

	}

	/**
	 * This method returns true if the string mobile number provided as an
	 * argument is a valid mobile number else returns false.
	 *
	 * @param String
	 * @return boolean
	 */
	public static boolean isValidMobileNumber(String mobileNumber) {
		//checking for null
		if(mobileNumber==null)
		{
			return false;
		}
		if (!isValidNumber(mobileNumber)) {
			return false;
		}
		if (!isValidMinimumLength(mobileNumber, 10)) {
			return false;
		}
		if (!isValidMaximumLength(mobileNumber, 12)) {
			return false;
		}
		return true;
	}

	/**
	 * This method returns true if the string does not contain
	 * any of the junk charcters tested for
	 *
	 * @param String
	 * @return boolean
	 */
	// TODO to use regex
	public static boolean isJunkFree(String inputString) {
		String junk[] = {"/","*","><","'",";","--","!","==","|","!","@","#","$","%","^","*","(",")","+","=","[","]","\",",".","/","{","}","|",":","?","~","&"};
		//checking for null
		if(inputString==null)
		{
			return false;
		}
		if(inputString==null)
		{
			return false;
		}
		for (int i = 0; i < junk.length; i++) {
			if(inputString.indexOf(junk[i].toString()) != -1)
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * This method returns true if the string amount provided as an argument is
	 * a valid amount and also allow negative numbers else returns false.
	 *
	 * @param amount
	 * @return boolean
	 */

	public static boolean isNumeric(String amount) {
		//checking for null
		if(amount==null)
		{
			return false;
		}
		
		// Set the amount pattern string
		Pattern p = Pattern.compile("(\\-*+[0-9]*+\\.?[0-9]*+)");

		// Match the given string with the pattern
		Matcher m = p.matcher(amount);

		// check whether match is found
		return m.matches();

	}
	 
	public static boolean isAlfabet(String str) {

		if(str==null)
		{
			return false;
		}
		Pattern pattern = Pattern.compile("[a-zA-Z]*");
		boolean isMach = pattern.matcher(str).matches();
		if(isMach){
			return true;
		} else {
			return false;

		}
	} 
}