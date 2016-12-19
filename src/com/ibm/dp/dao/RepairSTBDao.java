package com.ibm.dp.dao;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.RepairSTBDto;
import com.ibm.virtualization.recharge.exception.DAOException;


public interface RepairSTBDao {
	
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DAOException;
	public List<RepairSTBDto> getStockCollectionList(Long lngCrBy) throws DAOException;
	public String insertStockCollectionRepair(ListIterator<RepairSTBDto> repairStbDtoListItr,Long lngDistId,String circleId) throws DAOException;
	public String isValid(Long lngLoginId) throws DAOException;
	
}
