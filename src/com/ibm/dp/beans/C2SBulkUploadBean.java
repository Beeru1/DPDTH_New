package com.ibm.dp.beans;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class C2SBulkUploadBean extends ActionForm

{
	private static final long serialVersionUID = 1098305345870553453L;
	
	private FormFile  stbsrno;
	private String methodName ="";
	private String strmsg = "";
	
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public FormFile getStbsrno() {
		return stbsrno;
	}

	public void setStbsrno(FormFile stbsrno) {
		this.stbsrno = stbsrno;
	}

	public String getStrmsg() {
		return strmsg;
	}

	public void setStrmsg(String strmsg) {
		this.strmsg = strmsg;
	}

	
}
