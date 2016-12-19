package com.ibm.dp.dao;

import java.sql.Connection;
import java.util.List;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.TransferPendingSTBDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface TransferPendingSTBDao {
	
	List<CircleDto> getAllCircleList() throws  DAOException ;
	List<TransferPendingSTBDto> getCollectionData(String distId,String collectionId) throws DAOException;
	void updateRevInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException;
	void updateRevStockInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException;
	void insertRevPendingTransfer(TransferPendingSTBDto transferDto, Connection connection)throws DAOException;
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DAOException ;
	void updateRevChurnInventory(TransferPendingSTBDto transferDto, Connection connection) throws DAOException;
	
	}
