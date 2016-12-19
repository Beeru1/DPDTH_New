
package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;


public interface DPReverseCollectionDao 
{
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DAOException ;
	public List<DPReverseCollectionDto> getCollectionDefectType(int collectionId) throws DAOException ;
	public List<DPReverseCollectionDto> getProductCollection(String productType,int circleId) throws DAOException ;
	public List<List> getDCDetails(String dc_no) throws DAOException ;
	public List<DPReverseCollectionDto> submitReverseCollection(List<DPReverseCollectionDto> collectList, int circleId, long  accountId) throws DAOException ;
	public String validateCollectSerialNo(int productId, String serialNo) throws DAOException ;
	public boolean validateUniqueCollectSerialNo(int productId, String serialNo, String accId) throws DAOException ;
	//public String validateSerialNoAll(String serialNo , int circle_Id) throws DAOException ;
	
//	Update by harbans on Reverse Enhancement.
	public boolean validateC2SType(String serialNo)  throws DAOException;
	public String validateSerialNumberDAO(String strSerialNo, Integer intCollectionID, Integer intProductID) throws DAOException;
	public List<DpReverseInvetryChangeDTO> getInventoryChangeList(String collectionId,String accountId) throws DAOException ;
	public List<DpReverseInvetryChangeDTO> getGridDefectList(String collectionId) throws DAOException ;
}