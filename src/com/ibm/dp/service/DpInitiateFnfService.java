package com.ibm.dp.service;

import java.util.List;


import com.ibm.dp.dto.DpInitiateFnfDto;
import com.ibm.dp.exception.DPServiceException;


public interface DpInitiateFnfService {
	public List<DpInitiateFnfDto> getDistList(long loginUserId) throws DPServiceException;
	public DpInitiateFnfDto getDistListSearch(String searchedDistOlmId, long loginUserId)throws DPServiceException;
	public List<DpInitiateFnfDto> viewDistStockDetail(String accountId, long loginUserId)throws DPServiceException;
	public List<DpInitiateFnfDto> getPendingDistApprovalList(long loginUserId)throws DPServiceException;
	public String approveDistFnF(String accountId, String approverRemark, long loginUserId, String distName, String reqId)throws DPServiceException;
	public String requestForDistFnF(String accountId, String distRemark, String debitrequired, long loginUserId, String days, String distName)throws DPServiceException;
	public List<DpInitiateFnfDto> getConfirmationPendingDistList(long loginUserId, int circleId)throws DPServiceException;
	public String confirmFnF(String reqId, String confRemark, String distName, long loginUserId)throws DPServiceException;
	

	
	
}
