package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.UploadedStockEligibilityDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface ViewStockEligibilityService
{

	List<ViewStockEligibilityDTO> getStockEligibility(int distID)
			throws VirtualizationServiceException;

	ViewStockEligibilityDTO getStockEligibilityMainDtl(int distID)
			throws VirtualizationServiceException;

	List<ViewStockEligibilityDTO> getStockEligibilityAdmin(String strOLMId)
			throws VirtualizationServiceException;

	// Added by Neetika
	List<ViewStockEligibilityDTO> getUploadedEligibility(int distID)
			throws VirtualizationServiceException;

	UploadedStockEligibilityDto getUploadedStockEligibility(String olmId)
			throws VirtualizationServiceException;
}
