/*
 * Created on July 20, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.virtualization.ussdactivationweb.utils;

/**
 * This class stores Error Codes for USSD APP.
 * @author Ashad
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ErrorCodes {
	//	Stores Error Code for System Exception
	/**
	 * Automatically generated constructor: ErrorCodes
	 */
	public ErrorCodes() {
	}

	//	Stores Error Code for System Exception

	public static final String SYSTEMEXCEPTION = "USSD-EXCEPTION-001";

	//Stores Error Code for Sql Exception
	public static final String SQLEXCEPTION = "FTA-SQL-001";
	
	public static final String DATABASEEXCEPTION = "FTA-DBDOWN-EXCEPTION";

	
	public abstract class ServiceClass
	{
		public static final String SQLDUPEXCEPTION = "-803";
		public static final String RESULT_NOT_FOUND="RESULT_NOT_FOUND";
		public static final String DUPLICATE_SERVICECLASS_NAME="DUPLICATE_SERVICECLASS_NAME";
	}
	
	public abstract class Location
	{
		public static final String SQLDUPEXCEPTION = "-803";
		public static final String RESULT_NOT_FOUND="RESULT_NOT_FOUND";
		public static final String DUPLICATE_LOCATION_NAME="DUPLICATE_LOCATION_NAME";
	}

  public abstract class Connection {
		
		public static final String ERROR_CONNECTION_NOT_FOUND = "errors.db.create_connection";

		public static final String ERROR_CLOSE_RESULTSET = "errors.dbconnection.close_rsps";

		public static final String ERROR_CONNECTION_CLOSE = "errors.dbconnection.close_connection";
		
		public static final String ERROR_CONNECTION_COMMIT = "errors.transaction.commit";

	}
	
	

	
	public abstract class User
	{
		public static final String USER_UPDATED = "USER_UPDATED";

		public static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

		public static final String USER_DOES_NOT_EXISTS = "USER_DOES_NOT_EXISTS";
		
		public static final String NOT_VALID_EMAILID = "NOT_VALID_EMAILID";
		
		public static final String NOT_VALID_FIRSTNAME = "NOT_VALID_FIRSTNAME";
		
		public static final String NOT_VALID_FIRSTNAME_LENGTH = "NOT_VALID_FIRSTNAME_LENGTH";
		
		public static final String NOT_VALID_LASTNAME = "NOT_VALID_LASTNAME";
		
		public static final String NOT_VALID_LASTNAME_LENGTH = "NOT_VALID_LASTNAME_LENGTH";
		
		public static final String SELECT_ROLE = "SELECT_ROLE";
		
		public static final String SELECT_CIRCLE = "SELECT_CIRCLE";

		public static final String ADJ_PASSWORD_INVALID = "ADJ-INVALID-PASSWORD-EXCEPTION";
		
		public static final String MSG_PASSWORD_BLANK = "MSG_PASSWORD_BLANK";
		public static final String MSG_NEW_PASSWORD_BLANK = "MSG_NEW_PASSWORD_BLANK";
		public static final String MSG_CONFIRM_NEW_PASSWORD_BLANK = "MSG_CONFIRM_NEW_PASSWORD_BLANK";
		public static final String BOTH_NEW_CONFIRM_PASSWORD_NOT_SAME= "BOTH_NEW_CONFIRM_PASSWORD_NOT_SAME";
	}
	

}