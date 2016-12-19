package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibm.dp.dao.AccountDetailReportDao;
import com.ibm.dp.dto.AccountDetailReportDto;
import com.ibm.dp.service.AccountDetailReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class AccountDetailReportServiceImpl implements AccountDetailReportService{

	private Logger logger = Logger.getLogger(AccountDetailReportServiceImpl.class.getName());
	public List<AccountDetailReportDto> getAccountDetailExcel(AccountDetailReportDto reportDto,String reportName) throws VirtualizationServiceException {
		Connection connection=null;
		List<AccountDetailReportDto> accountDetailList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			AccountDetailReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountDetailReportDao(connection);
			if(reportName!=null && reportName.equals("updateReport"))
			{
				logger.info("service if");
				accountDetailList = accountDetailReportDao.getAccountUpdateExcel(reportDto);
			}
			else
			{
				logger.info("service else");
				accountDetailList = accountDetailReportDao.getAccountDetailExcel(reportDto);
			}
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
		return accountDetailList;
	}
	public List<SelectionCollection> getLoginIdList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<SelectionCollection> loginIdList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			AccountDetailReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountDetailReportDao(connection);
			
			loginIdList = accountDetailReportDao.getLoginIdList();
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
		return loginIdList;
	}
	public List<SelectionCollection> getAccountNameList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<SelectionCollection> accountNameList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			AccountDetailReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountDetailReportDao(connection);
			
			accountNameList = accountDetailReportDao.getAccountNameList();
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
		return accountNameList;
	}
	
	public int getParentId(String accountId) throws  VirtualizationServiceException {
		Connection connection=null;
		int parentID = 0;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			AccountDetailReportDao accountDetailReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountDetailReportDao(connection);
			
			parentID = accountDetailReportDao.getParentId(accountId);
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
		return parentID;
	}
}
