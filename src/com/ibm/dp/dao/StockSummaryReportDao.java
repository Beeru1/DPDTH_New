package com.ibm.dp.dao;

import java.util.List;

import com.ibm.dp.dto.ProductMasterDto;
import com.ibm.dp.dto.StockSummaryReportDto;
import com.ibm.virtualization.recharge.exception.DAOException;

public interface StockSummaryReportDao {

	
	public List getRevLogTsmAccountDetails( int levelId) throws DAOException; 
	public List getRevLogTsmDistLogin( int distId) throws DAOException; 
	public List getRevLogDistAccountDetails( int levelId , int circleId) throws DAOException;
	public List getRevLogFromDistAccountDetails( int levelId , int circleId) throws DAOException;
	public List<StockSummaryReportDto> getRevLogReportExcel(int distId , String fromDate, String toDate , String circleId , String tsmId,int loginRole,String productId) throws DAOException;
	public List<StockSummaryReportDto> getRevLogAllTsm(long accountId,String accountLevel) throws DAOException;
	public List<ProductMasterDto> getProductTypeMaster(String circleId,String dcNo) throws DAOException;
	public List<ProductMasterDto> getProductTypeMaster(String circleId) throws DAOException;
}
