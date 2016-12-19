package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.DCNoListDto;
import com.ibm.dp.dto.DPMissingStockApprovalDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DPMissingStockApprovalDao 
{
	
	/**
	 * This method is used to bring the initial data for Missing Stock Approval
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	List<List> getInitMissingStockDao(long loginUserId,String strSelectedDC) throws DAOException;

	/**
	 * This method is used to save the data for Missing Stock reported by a TSM
	 * @param mapMSAGridData
	 * @param arrCheckedMSA
	 * @param loginUserId
	 * @return
	 */
	List<List> saveMissingStockDao(Map<String, DPMissingStockApprovalDTO> mapMSAGridData, String[] arrCheckedMSA, long loginUserId,String strSelectedDC) throws DAOException;

	List<DCNoListDto> getDCNosList(String loginUserId) throws DAOException;
	
	public ArrayList viewPODetails(long accountID,int circleID,int lowerbound, int upperbound) throws DAOException;
	
}
