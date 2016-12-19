package com.ibm.dp.dao;

import java.util.List;


import com.ibm.dp.dto.DpInitiateFnfDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface DpInitiateFnfDao {
	public List<DpInitiateFnfDto> getDistList(long loginUserId) throws DAOException;

	public DpInitiateFnfDto getDistListSearch(String searchedDistOlmId,long loginUserId) throws DAOException;

	public String requestForDistFnF(String accountId, String distRemark, String debitrequired, long loginUserId,String days, String distName)throws DAOException;

	public List<DpInitiateFnfDto> viewDistStockDetail(String accountId, long loginUserId)throws DAOException;

	public List<DpInitiateFnfDto> getPendingDistApprovalList(long loginUserId)throws DAOException;

	public String approveDistFnF(String accountId, String approverRemark, long loginUserId, String distName, String reqId)throws DAOException;

	public List<DpInitiateFnfDto> getConfirmationPendingDistList(long loginUserId, int circleId)throws DAOException;

	public String confirmFnF(String reqId, String confRemark, String distName, long loginUserId)throws DAOException;

}
