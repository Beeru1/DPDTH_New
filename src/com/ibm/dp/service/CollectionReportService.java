package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DPReverseCollectionDto;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CollectionReportService {
	public List<DpProductCategoryDto> getProductCategory()throws Exception ;
	
	List<CollectionReportDTO> getCollectionDetailExcel(CollectionReportDTO reportDto) throws VirtualizationServiceException;
	
	public List<CollectionReportDTO> getCollectionTypeMaster() throws DAOException ;
	List<DpProductCategoryDto> getProductList() throws  VirtualizationServiceException ;
}
