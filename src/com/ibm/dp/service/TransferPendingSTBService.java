package com.ibm.dp.service;

import java.sql.Connection;
import java.util.List;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.TransferPendingSTBDto;
import com.ibm.dp.exception.DPServiceException;


public interface TransferPendingSTBService {
	
	List<CircleDto> getAllCircleList() throws DPServiceException;
	List<TransferPendingSTBDto> getCollectionData(String distId,String collectionId) throws DPServiceException;
	void updateRevInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException;
	void updateRevStockInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException;
	void insertRevPendingTransfer(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException;
	//ArrayList getAvailableSerialNos(InterSsdTransferAdminDto ssdDto) throws DPServiceException;
	//public String insertStockTransfs(ListIterator<InterSsdTransferAdminDto> interssdDtoListItr) throws DPServiceException;
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DPServiceException;
	void updateRevChurnInventory(TransferPendingSTBDto transferDto, Connection connection) throws DPServiceException;
	
}
