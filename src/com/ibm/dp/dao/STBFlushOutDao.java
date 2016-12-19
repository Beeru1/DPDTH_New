package com.ibm.dp.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ibm.dp.dto.DuplicateSTBDTO;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface STBFlushOutDao 
{
	/**
	 * This method is used to get List of STB from Stock
	 */
	public ArrayList<DuplicateSTBDTO> getSTBList(String  stbSLNos)throws DAOException;

	/**
	 * Method to upload Excel file
	 */
	public Map uploadFreshExcel(InputStream inputstream) ;
	
	/**
	 * Method to flush STBs from DP tables
	 */
	//public String flushDPSerialNumbers(List<DuplicateSTBDTO> flushList, List<DuplicateSTBDTO> queryList) throws Exception;
	
	public String flushDPSerialNumbers(List<DuplicateSTBDTO> flushList, List<DuplicateSTBDTO> queryList, String loginUserId) throws Exception;
	
	/**
	 * Method to release STBs from DP_ERROR_PO_Detail 
	 */
	public boolean releaseSTB(ArrayList<String>  stbSerialNos)throws DAOException;
	public Map<String, String> flushDPSerialNumbersCloseReco(List<DuplicateSTBDTO> flushList, List<DuplicateSTBDTO> queryList, String loginUserId,String reco) throws Exception;
	
	
}
