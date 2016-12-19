package com.ibm.hbo.exception;

public class HBOException extends DAOException {
//	Added By parul on  4 Dec 2007
	
	private String message = null;
	private String errCode = null;
	private String errMsg = null;
	private int errCtr;
// End
	public HBOException(String message) {
		super(message);
	}

	public HBOException(String message, Throwable throwable) {
		super(message, throwable);

	}


// Added By parul on  4 Dec 2007
	/**
				 * Costructor with arguments initializes error code and error message.
				 * @param className
				 * @param methodName
				 * @param e
				 * @param errCode
				 */
	public HBOException(
		String className,
		String methodName,
		int errCtr,
		String errCode) {
		this.errCtr = errCtr;
		message =
			"An Exception Has Occured in HBO module in following:-  \n Class name: "
				+ className
				+ "\n"
				+ ":\n Method name : "
				+ methodName
				+ "\n"
				+ "Exception is : "
				+ errCtr;
		this.errCode = errCode;
	}

	/**
			 * Costructor with arguments initializes error code and error message.
			 * @param className
			 * @param methodName
			 * @param e
			 * @param errCode
			 */
	public HBOException(
		String className,
		String methodName,
		String errMsg,
		String errCode) {
		this.errMsg = errMsg;
		message =
			"An Exception Has Occured in HBO module in following:-  \n Class name: "
				+ className
				+ "\n"
				+ ":\n Method name : "
				+ methodName
				+ "\n"
				+ "Exception is : "
				+ errMsg;
		this.errCode = errCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {

		return message;
	}
	public String getErrCode() {

		return errCode;
	}
	public int getErrCtr() {

		return errCtr;
	}
	public String getMessage() {

		return this.errMsg;
	}
//End 
}
