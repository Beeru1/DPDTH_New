/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.utils.email;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author abhipsa
 *
 */
public class EmailException extends Exception{
	/**
	 * 
	 */
	public EmailException(){
//		 TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public EmailException(String message){
//		 TODO Auto-generated constructor stub
		super(message);
	}
	
	/**
	 * @param cause
	 */
	public EmailException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public EmailException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method returns the exception stack trace as <code>String</code> object.
	 * @return the exception stack trace as <code>String</code> object.
	 */
	public String traceString() {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		printStackTrace(printWriter);
		printWriter.flush();
		stringWriter.flush();		
		return stringWriter.toString();
	}

	
}
