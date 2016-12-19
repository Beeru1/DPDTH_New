package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DpDcChurnStatusDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DpDcChurnStatusDao {
	public List<DpDcChurnStatusDto> getAllDCListChurn(Long lngCrBy) throws DAOException ;
	public String setDCStatusChurn(String[] arrDcNos) throws DAOException ;
	public List<DpDcChurnStatusDto> viewSerialsStatusChurn(String dc_no) throws DAOException ;
	public void submitDamageDetail(String dc_no,String courierAgency,String docketNum) throws DAOException ;
}
