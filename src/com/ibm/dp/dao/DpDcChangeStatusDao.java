package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DpDcChangeStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DpDcChangeStatusDao {
	public List<DpDcChangeStatusDto> getAllDCList(Long lngCrBy) throws DAOException ;
	public String setDCStatus(String[] arrDcNos) throws DAOException ;
	public List<DpDcChangeStatusDto> viewSerialsStatus(String dc_no) throws DAOException ;
}
