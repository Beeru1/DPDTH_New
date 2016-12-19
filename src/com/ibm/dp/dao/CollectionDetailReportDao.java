package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.PODetailReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface CollectionDetailReportDao {

	List<CollectionReportDTO> getCollectionDetailExcel(CollectionReportDTO reportDto) throws DAOException;
	List<DpProductCategoryDto> getProductList() throws  DAOException ;
}
