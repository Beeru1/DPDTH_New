package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface NonSerializedActivationDao {
	
	List<CircleDto> getAllCircleList() throws  DAOException ;
	String getActivationStatus(String circleId) throws DAOException;
	int updateStatus(String accountid,String circleId, String status, String changeType) throws DAOException;
	int insertActivation(String circleId, String changeType, String status) throws DAOException;
}
