package com.ibm.dp.dao;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.beans.DistStockDeclarationDetailsBean;
import com.ibm.dp.dto.DpDcCreationDto;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPDcCreationDao {
	
	public List<DpDcCreationDto> getCollectionTypeMaster() throws DAOException ;
	public List<DpDcReverseStockInventory> getStockCollectionList(String collectionId,Long lngCrBy) throws DAOException ;
	public String insertStockCollection(ListIterator<DpDcReverseStockInventory> dcCreationDtoListItr,Long lngDistId,String circleId,String strRemarks,String agencyName,String docketNumber) throws DAOException;
	public String checkERR(String rowSrNo) throws DAOException ;
	public DpDcReverseStockInventory getStockDetail(String srNoCollect) throws DAOException;
	public int updateInactiveSecondaryStock(
			String prodId, long distId, String serialNum) throws Exception;
	
}
