package com.ibm.dp.service;

import java.util.ArrayList;

import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.dp.exception.DPServiceException;

public interface ErrorPOService 
{
	/**
	 * This method can be used to get List of all Error STB PO
	 * 
	 * @param loginUserId
	 * @return
	 * @throws DPServiceException
	 */
	ArrayList<DuplicateSTBDTO> getDuplicateSTB(String loginUserIdAndGroup)throws DPServiceException;

	/**
	 * This method is used to get a detail list of all Error STB PO
	 * @param invoiceNo
	 * @return
	 * @throws DPServiceException
	 */
	public ArrayList<DuplicateSTBDTO> viewPODetailList(String poNumber) throws DPServiceException;
		
}