package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.InterSSDReportDTO;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPInterSSDStockTransferReportService {

	public List<DpProductCategoryDto> getProductCategory()throws Exception ;
	
	List<InterSSDReportDTO> getInterSSDStockTransferExcel(InterSSDReportDTO reportDto) throws VirtualizationServiceException;
	
}
