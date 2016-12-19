package com.ibm.dp.service;

import java.util.List;

import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface StockSummaryReportService {
	public List getRevLogDistAccountDetails(int roleId ,  int circleId) throws VirtualizationServiceException;
	public List getRevLogFromDistAccountDetails(int roleId ,  int circleId) throws VirtualizationServiceException;
	public List<StockSummaryReportDto> getRevLogReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId,int loginRole,String productId) throws VirtualizationServiceException;
	public List getRevLogTsmAccountDetails(int roleId)throws VirtualizationServiceException;
	public List getRevLogTsmDistLogin(int distId)throws VirtualizationServiceException;
	public List<ProductMasterDto> getProductTypeMaster(String circleId , String dcNo) throws DPServiceException, VirtualizationServiceException;
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DPServiceException, VirtualizationServiceException;
}
