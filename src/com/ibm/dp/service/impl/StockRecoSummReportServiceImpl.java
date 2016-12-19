package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.StockRecoSummReportDao;
import com.ibm.dp.dto.DpProductCategoryDto;
import com.ibm.dp.dto.StockRecoSummReportDto;
import com.ibm.dp.service.StockRecoSummReportService;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class StockRecoSummReportServiceImpl implements StockRecoSummReportService{

	private Logger logger = Logger.getLogger(StockRecoSummReportServiceImpl.class.getName());
	public List<StockRecoSummReportDto> getRecoSummaryExcel(StockRecoSummReportDto reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<StockRecoSummReportDto> recoSummaryReportList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			StockRecoSummReportDao recoSummaryDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRecoSummaryReportDao(connection);
			recoSummaryReportList = recoSummaryDao.getRecoSummaryExcel(reportDto);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return recoSummaryReportList;
	}
	public List<DpProductCategoryDto> getProductList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<DpProductCategoryDto> productList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			StockRecoSummReportDao recoSummaryDao  = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRecoSummaryReportDao(connection);
			
			productList = recoSummaryDao.getProductList();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}	
		finally
		{
			//Releasing the database connection
			DBConnectionManager.releaseResources(connection);
		}
		return productList;
	}

}
