package com.ibm.dp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.beans.StockSummaryReportBean;
import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.InterSsdTransferAdminDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface InterSsdTransferAdminDao {
	
	List<CircleDto> getAllCircleList() throws  DAOException ;

	ArrayList getAvailableSerialNos(InterSsdTransferAdminDto ssdDto) throws  DAOException ;
	public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr) throws DAOException;

	List<List<CircleDto>> getInitData()throws  DAOException ;

	List<StockSummaryReportBean> getFromDistAccountDetails(int intTSMID, int circleId, int intBusCat)throws  DAOException ;
	
	List<StockSummaryReportBean> getFromInactiveDistAccountDetails(int intTSMID, int circleId, int intBusCat)throws  DAOException ;
	
	List<StockSummaryReportBean> getToDistAccountDetailsDAO(int intTSMID, int circleId, int intBusCat)throws  DAOException ;
}
