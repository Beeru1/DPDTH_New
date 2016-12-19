/**
	 * @author Harbans Singh
**/	
package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DPPrintDCDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpReverseInvetryChangeDTO;
import com.ibm.dp.exception.DPServiceException;

public interface DPReverseCollectionService 
{
	/* Return master of collection type
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List<DPReverseCollectionDto> getCollectionTypeMaster() throws DPServiceException;;
	
	/* Return master of collection defect type
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List<DPReverseCollectionDto> getCollectionDefectType(int collectionId) throws DPServiceException;
	
	/* Return master of collection product master
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List<DPReverseCollectionDto> getProductCollection(String productType, int circleId) throws DPServiceException;
	
	/* Submit Reverse Collection 
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public  List<DPReverseCollectionDto> submitReverseCollection(List<DPReverseCollectionDto> collectList, int circleId, long  accountId) throws DPServiceException;
	
	public List<List> getDCDetails(String dc_no) throws DPServiceException;
	
	/* Submit validate serila no
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public String validateCollectSerialNo(int productId, String serialNo) throws DPServiceException;
	
	/* Submit validate serila no
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public boolean validateUniqueCollectSerialNo(int productId, String serialNo, String accId) throws DPServiceException;
	
	//public String validateSerialNoAll(String serialNo , int circle_Id) throws DPServiceException;
		
//	Update by harbans on Reverse Enhancement.
	public boolean validateC2SType(String serialNo)  throws DPServiceException;

	public String validateSerialNumber(String strSerialNo, Integer intCollectionID, Integer intProductID)throws DPServiceException;
	public List<DpReverseInvetryChangeDTO> getInventoryChangeList(String collectionId,String accountId) throws DPServiceException;
	public List<DpReverseInvetryChangeDTO> getGridDefectList(String collectionId) throws DPServiceException;
	public int checkTransactionTypeReverse(Long distId)throws DPServiceException;//Neetika
	
	
}
