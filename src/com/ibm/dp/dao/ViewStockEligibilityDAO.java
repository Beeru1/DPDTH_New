package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.UploadedStockEligibilityDto;
import com.ibm.dp.dto.ViewStockEligibilityDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ViewStockEligibilityDAO
{

	List<ViewStockEligibilityDTO> getStockEligibility(int distID) throws DAOException;

	ViewStockEligibilityDTO getStockEligibilityMainDtl(int distID) throws DAOException;

	List<ViewStockEligibilityDTO> getStockEligibilityAdminDoa(String strOLMId) throws DAOException;

	// Added by Neetika for BFR 2 of CSR 2
	List<ViewStockEligibilityDTO> getStockEligibilityUploaded(int distID) throws DAOException;

	/**
	 * 
	 * @param olmid
	 * @return
	 */
//Added  by Nehil
	UploadedStockEligibilityDto getUploadedStockEligibility(String olmid) throws DAOException;
}
