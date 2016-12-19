package com.ibm.dp.service;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface RepairSTBService {

	
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DPServiceException;
	public List<RepairSTBDto> getStockCollectionList(Long lngCrBy) throws DPServiceException;
	public String insertStockCollectionRepair(ListIterator<RepairSTBDto> repairStbDtoListItr,Long lngDistId,String circleId) throws VirtualizationServiceException , DPServiceException;
	public String isValid(Long lngLoginId) throws DPServiceException;
}
