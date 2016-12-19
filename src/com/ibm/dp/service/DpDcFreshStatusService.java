package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DpDcFreshStatusDto;
import com.ibm.dp.exception.DPServiceException;


public interface DpDcFreshStatusService {

	public List<DpDcFreshStatusDto> getAllDCListFresh(Long lngCrBy) throws DPServiceException ;
	public String setDCStatusFresh(String[] arrDcNos) throws DPServiceException ;
	public List<DpDcFreshStatusDto> viewSerialsStatusFresh(String dc_no) throws DPServiceException ;
	
	
}
