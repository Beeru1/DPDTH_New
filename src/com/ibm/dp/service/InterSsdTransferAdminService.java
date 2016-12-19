package com.ibm.dp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.InterSsdTransferAdminDto;
import com.ibm.dp.exception.DPServiceException;


public interface InterSsdTransferAdminService {
	
	List<CircleDto> getAllCircleList() throws DPServiceException;
	ArrayList getAvailableSerialNos(InterSsdTransferAdminDto ssdDto) throws DPServiceException;
	public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr) throws DPServiceException;
	List<List<CircleDto>> getInitData() throws DPServiceException;
	List getFromDistAccountDetails(int intTSMID, int circleId, int intBusCat) throws DPServiceException;
	List getFromInactivAccountDetails(int intTSMID, int circleId, int intBusCat) throws DPServiceException;
	List getToDistAccountDetails(int intTSMID, int circleId, int intBusCat)throws DPServiceException;
}
