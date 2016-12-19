package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;
public interface StockRecoSummReportDao {
	List<StockRecoSummReportDto> getRecoSummaryExcel(StockRecoSummReportDto reportDto) throws DAOException;
	List<DpProductCategoryDto> getProductList() throws  DAOException ;
}
