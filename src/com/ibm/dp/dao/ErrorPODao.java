package com.ibm.dp.dao;

import java.util.ArrayList;

import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface ErrorPODao 
{
	/**
	 * This method is used to get List of all Error STB PO 
	 * 
	 * @param loginUserId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<DuplicateSTBDTO> getSTBListDao(String  loginUserIdAndGroup)throws DAOException;

	/**
	 * This method is used to get details of all the Error STB PO
	 * 
	 * @param invoiceNo
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<DuplicateSTBDTO> getpoDetailList(String poNumber) throws DAOException;
	
}
