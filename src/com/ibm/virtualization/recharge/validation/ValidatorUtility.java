/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class have utility methods to help validate data.
 * 
 * @author Paras
 * 
 */
public class ValidatorUtility {

	// TODO : Paras, Sep 05, 2007 Need to discuss whether to use java.util or
	// use Regular Expressions to check for numbers validation

	/**
	 * This method returns true if the strString provided as an argument is a
	 * valid Alpha Numeric String else returns false.
	 * 
	 * @param strString
	 * @return boolean
	 */
	public static boolean isAlphaNumeric(String strString) {
		// Set the AlphaNumeric pattern string
		Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9\\s/\'@_*(.)]+");

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
		String junk[] = {"*",">","<","'",";","--","!","==","|"};
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
		// Set the amount pattern string
		 //boolean checkNumeric = false;
		/*for(int i=0 ; i<= str.length(); i++)
		{
			if(amount.indexOf(i) > 0 )
			{
				checkNumeric =  true;
				break;
			}
		}
		   
		for (int i = 0; i < amount.length(); i++) {
			if (Character.isDigit(amount.charAt(i)))
			{
				checkNumeric =  true;
				return true;
			}
		}	 
		return checkNumeric;*/
  		Pattern p = Pattern.compile("([^0-9]+[0-9]+[^0-9]+)");
  		
		// Match the given string with the pattern
		Matcher m = p.matcher(amount);

		// check whether match is found
		System.out.println("3333---"+m.matches());
		return m.matches();

	}
	public static void main(String[] args) {
		ValidatorUtility.isNumeric("Ibm@987a");
	}
}