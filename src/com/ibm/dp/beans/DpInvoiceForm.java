package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class DpInvoiceForm extends ActionForm {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	FormFile file;
	String fileFlag;
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMethodName() {
		return methodName;
	}
	
	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	public String getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}
	

}
