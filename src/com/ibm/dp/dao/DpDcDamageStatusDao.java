package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DpDcDamageStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DpDcDamageStatusDao {
	public List<DpDcDamageStatusDto> getAllDCList(Long lngCrBy) throws DAOException ;
	public String setDCStatus(String[] arrDcNos) throws DAOException ;
	public List<DpDcDamageStatusDto> viewSerialsStatus(String dc_no) throws DAOException ;
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DAOException ;
}
