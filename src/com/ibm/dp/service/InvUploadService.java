package com.ibm.dp.service;

import org.apache.struts.upload.FormFile;

public interface InvUploadService {

	public String uploadInvoice(FormFile file) throws Exception;
}
