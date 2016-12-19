package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface StockRecoSummReportService {
	List<StockRecoSummReportDto> getRecoSummaryExcel(StockRecoSummReportDto reportDto) throws VirtualizationServiceException;
	List<DpProductCategoryDto> getProductList() throws  VirtualizationServiceException ;

}
