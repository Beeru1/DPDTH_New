package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.exception.DPServiceException;


public interface DpDcDamageStatusService {

	public List<DpDcDamageStatusDto> getAllDCList(Long lngCrBy) throws DPServiceException ;
	public String setDCStatus(String[] arrDcNos) throws DPServiceException ;
	public List<DpDcDamageStatusDto> viewSerialsStatus(String dc_no) throws DPServiceException ;
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DPServiceException ;
}
