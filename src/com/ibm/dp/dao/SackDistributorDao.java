package com.ibm.dp.dao;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.SackDistributorDetailDto;
import com.ibm.dp.dto.TSMDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface SackDistributorDao {

	public List<TSMDto> getTSMMaster(String parentId)  throws DAOException;
	public List<SackDistributorDetailDto> getDistDetailList(String tsmId,String circleId)throws DAOException;
	public String sackDistributor(ListIterator<SackDistributorDetailDto> distDetailDtoListItr) throws DAOException;
}
