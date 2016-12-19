package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DpDcFreshStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DpDcFreshStatusDao {
	public List<DpDcFreshStatusDto> getAllDCListFresh(Long lngCrBy) throws DAOException ;
	public String setDCStatusFresh(String[] arrDcNos) throws DAOException ;
	public List<DpDcFreshStatusDto> viewSerialsStatusFresh(String dc_no) throws DAOException ;
}
