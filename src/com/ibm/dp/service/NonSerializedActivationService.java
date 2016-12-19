package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.exception.DPServiceException;


public interface NonSerializedActivationService {
	
	List<CircleDto> getAllCircleList() throws DPServiceException;
	String getActivationStatus(String circleId) throws DPServiceException;
	int updateStatus(String accountid,String circleId, String status, String changeType) throws DPServiceException;
	int insertActivation(String circleId, String changeType, String status) throws DPServiceException;

}
