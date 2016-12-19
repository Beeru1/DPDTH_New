package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DistRecoDto;
import com.ibm.dp.dto.PrintRecoDto;
import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.dto.CloseInactivePartnersDto;

public interface CloseInactivePartnersService {
	
	public String serialDataxls(FormFile myfile) throws Exception;
	public ArrayList invFileUploadls(String path) throws Exception;
	
		

}
