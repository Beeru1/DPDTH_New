package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.ibm.dp.dto.DPSTBHistDTO;
import com.ibm.dp.dto.DPStbInBulkDTO;
import com.ibm.hbo.exception.HBOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPStbInBulkService {

	public List uploadExcel(FormFile myXls)
			throws VirtualizationServiceException;

	public ArrayList getAvailableSerialNos(DPStbInBulkDTO dpStbInBulkDTO)
			throws HBOException;
	
	public List uploadExcelHistory(FormFile myXls)	throws VirtualizationServiceException;
	
	public ArrayList<DPSTBHistDTO> getSerialNosHist(DPSTBHistDTO dpStbInBulkDTO)	throws HBOException;
}
