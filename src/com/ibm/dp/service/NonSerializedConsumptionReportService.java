package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.NonSerializedConsumptionReportDTO;
import com.ibm.dp.dto.CollectionReportDTO;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface NonSerializedConsumptionReportService {
	public List<DpProductCategoryDto> getProductCategory()throws Exception ;
	List<NonSerializedConsumptionReportDTO> getNonSerializedConsumptionExcel(NonSerializedConsumptionReportDTO reportDto) throws VirtualizationServiceException;
	
}
