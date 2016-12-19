package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.CircleDto;
import com.ibm.dp.dto.DistributorDTO;
import com.ibm.dp.dto.FseDTO;
import com.ibm.dp.dto.HierarchyTransferDto;
import com.ibm.dp.dto.RetailorDTO;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface HierarchyTransferDao {
	public List<DistributorDTO> getAllDistList(String strLoginId,String strAccLevel) throws DAOException ;
	public List<FseDTO> getAllFSeList(String strDistId) throws DAOException ;
	public List<RetailorDTO> getAllRetlerList(String strFseId) throws DAOException ;
	public String insertHierarchyTransfer(HierarchyTransferDto transfDto,String crcleId) throws  DAOException;
	List<CircleDto> getAllCircleList() throws  DAOException ;
	List<CircleDto> getAllCircleListCircleAdmin(long id) throws  DAOException ;
	
	/* Added By parnika  */
	
	public boolean isMutuallyExclusive(String fromDistributorId, String toDistId) throws  DAOException;
	
	/* End of changes By Parnika */
}
