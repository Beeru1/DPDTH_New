package com.ibm.hbo.exception;

import java.util.List;

public class ExcelParseException extends Exception {

	private static final long serialVersionUID = 1L;

	List<String> errors = null;
	
	ExcelParseException(List<String> errors){
		this.errors = errors;
	}
	
	public List<String> getErrors() {
		return errors;
	}

}

