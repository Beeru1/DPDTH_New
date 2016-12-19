package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.dp.exception.DPServiceException;


public interface DpDcChangeStatusService {

	public List<DpDcChangeStatusDto> getAllDCList(Long lngCrBy) throws DPServiceException ;
	public String setDCStatus(String[] arrDcNos) throws DPServiceException ;
	public List<DpDcChangeStatusDto> viewSerialsStatus(String dc_no) throws DPServiceException ;
	
	
}
