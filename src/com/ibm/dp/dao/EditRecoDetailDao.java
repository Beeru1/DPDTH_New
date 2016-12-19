package com.ibm.dp.dao;


import java.io.InputStream;
import java.util.List;

import com.ibm.dp.dto.RecoPeriodDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface EditRecoDetailDao {
	/**
	 * 
	 * @return List<RecoPeriodDTO>
	 * @throws DAOException
	 */
	public List<RecoPeriodDTO> getRecoPeriodList() throws DAOException;
	
	/**
	 * This method is to validate uploaded excel file for OLM & Remarks
	 * @param inputstream - Excel file input stream
	 * @return List<RecoPeriodDTO>
	 * @throws DPServiceException
	 */
	public List validateExcel(InputStream inputstream) throws DPServiceException;
	
	/**
	 * 
	 * @param getRecoPeriodList
	 * @param recoID 
	 * @return int
	 * @throws DPServiceException
	 */
	public int updateRecoPeriod(List<RecoPeriodDTO> recoDtoList, int recoID, int gracePeriod) throws DAOException;
}
