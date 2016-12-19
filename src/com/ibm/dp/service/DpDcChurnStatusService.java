package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.exception.DPServiceException;


public interface DpDcChurnStatusService {

	public List<DpDcChurnStatusDto> getAllDCListChurn(Long lngCrBy) throws DPServiceException ;
	public String setDCStatusChurn(String[] arrDcNos) throws DPServiceException ;
	public List<DpDcChurnStatusDto> viewSerialsStatusChurn(String dc_no) throws DPServiceException ;
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DPServiceException ;
	
}
