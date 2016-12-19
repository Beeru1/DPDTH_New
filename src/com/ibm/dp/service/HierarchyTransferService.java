package com.ibm.dp.service;

import java.util.List;
import java.util.ListIterator;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.DpDcReverseStockInventory;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.HierarchyTransferDto;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface HierarchyTransferService {
	public List<DistributorDTO> getAllDistList(String strLoginId,String stracLevel) throws DPServiceException ;
	public List<FseDTO> getAllFSeList(String strDistId) throws DPServiceException ;
	public List<RetailorDTO> getAllRetlerList(String strFseId) throws DPServiceException ;
	public String insertHierarchyTransfer(HierarchyTransferDto transfDto,String stracLevel) throws  DPServiceException;
	List<CircleDto> getAllCircleList() throws DPServiceException;
	List<CircleDto> getAllCircleListCircleAdmin(long id) throws DPServiceException;
	/* Added by Parnika */
	public boolean isMutuallyExclusive(String fromDistributorId, String toDistId) throws  DPServiceException;
	/* End of changes by Parnika */
}
