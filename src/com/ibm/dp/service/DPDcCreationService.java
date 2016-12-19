package com.ibm.dp.service;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPDcCreationService {
	/* Return master of collection type
	 * 
	 * @param 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public List<DpDcCreationDto> getCollectionTypeMaster() throws DPServiceException;
	public List<DpDcReverseStockInventory> getStockCollectionList(String collectionId,Long lngCrBy) throws DPServiceException;
	public String insertStockCollection(ListIterator<DpDcReverseStockInventory> dcCreationDtoListItr,Long lngDistId,String circleId,String strRemarks,String agencyName,String docketNumber) throws VirtualizationServiceException , DAOException;
	
	public String checkERR(String rowSrNo) throws VirtualizationServiceException , DAOException;

	
	public DpDcReverseStockInventory getStockDetails(String srnoCollect) throws DPServiceException ,DAOException;

	
	public int updateInactiveSecondaryStock(String prodId,
			long distId, String serialNum) throws Exception;


}
