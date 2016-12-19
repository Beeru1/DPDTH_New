package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.dto.DPMissingStockApprovalDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DPMissingStockApprovalService 
{
	/**
	 * This method is used to bring the initial data for Missing Stock Approval
	 * @param loginUserId
	 * @return
	 * @throws DPServiceException
	 */
	List<List> getInitMissingStock(long loginUserId,String strSelectedDC) throws DPServiceException;

	/**
	 * This method is used to save the data for Missing Stock reported by a TSM
	 * @param mapMSAGridData
	 * @param arrCheckedMSA
	 * @param loginUserId
	 * @return
	 * @throws DPServiceException
	 */
	List<List> saveMissingStock(Map<String, DPMissingStockApprovalDTO> mapMSAGridData, String[] arrCheckedMSA, 
			long loginUserId,String strSelectedDC) throws DPServiceException;

	
	List<DCNoListDto> getDCNosList(String loginUserId) throws DPServiceException;

	ArrayList viewPOList(long accountID,int circleID,int lowerbound, int upperbound) throws DPServiceException;
}
