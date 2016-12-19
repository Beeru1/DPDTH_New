package com.ibm.dp.service;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface SackDistributorService {

	public List<TSMDto> getTSMMaster(String parentId) throws DPServiceException;
	public List<SackDistributorDetailDto> getDistDetailList(String tsmId,String circleId) throws VirtualizationServiceException ,DPServiceException;
	public String sackDistributor(ListIterator<SackDistributorDetailDto> distDetailDtoListItr) throws VirtualizationServiceException ,DPServiceException;
}
