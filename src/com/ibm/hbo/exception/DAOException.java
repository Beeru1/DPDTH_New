/*
 * Created on Apr 27, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.hbo.exception;

import java.sql.SQLException;

import javax.naming.NamingException;

/**
 * @author Puneet Lakhina
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DAOException extends Exception {
	/**
	 * @param message
	 * @param throwable
	 */
	public DAOException(String message, Throwable throwable) {
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param string
	 * @param sqlException
	 */
	public DAOException(String string, SQLException sqlException) {
		
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param string
	 * @param namingException
	 */
	public DAOException(String string, NamingException namingException) {
		
		// TODO Auto-generated constructor stub
	}
	public DAOException() {
		super();
	}
//	public DAOException(String message,Throwable cause) {
//		super(message,cause);
//	}
	public DAOException(String message) {
		super(message);
	}
	
}
