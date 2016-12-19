package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.util.List;
import org.apache.log4j.Logger;
import com.ibm.dp.dao.AccountManagementActivityReportDao;
import com.ibm.dp.dto.AccountManagementActivityReportDto;
import com.ibm.dp.service.AccountManagementActivityReportService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class AccountManagementActivityReportServiceImpl implements AccountManagementActivityReportService{

	private Logger logger = Logger.getLogger(AccountDetailReportServiceImpl.class.getName());
	public List<AccountManagementActivityReportDto> getAccountMngtActivityExcel(AccountManagementActivityReportDto reportDto) throws VirtualizationServiceException {
		Connection connection=null;
		List<AccountManagementActivityReportDto> accountMngtActivityList = null;
		try
		{
			connection = DBConnectionManager.getDBConnection();
			AccountManagementActivityReportDao accountMngdActReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountManagementActivityReportDao(connection);
			
			accountMngtActivityList = accountMngdActReportDao.getAccountMngtActivityExcel(reportDto);
			
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
		return accountMngtActivityList;
	}
	public List<SelectionCollection> getLoginIdList() throws  VirtualizationServiceException {
		Connection connection=null;
		List<SelectionCollection> loginIdList = null;
		try
		{
			logger.info("= in service impl dao getLoginIdList id pratap=");
			connection = DBConnectionManager.getDBConnection();
			AccountManagementActivityReportDao accountMngdActReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountManagementActivityReportDao(connection);
			
			loginIdList = accountMngdActReportDao.getLoginIdList();
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
			logger.info("= in service impl dao getAccountNameList id pratap=");
			connection = DBConnectionManager.getDBConnection();
			AccountManagementActivityReportDao accountMngdActReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountManagementActivityReportDao(connection);
			
			accountNameList = accountMngdActReportDao.getAccountNameList();
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
			logger.info("= in service impl dao getparent id pratap=");
			connection = DBConnectionManager.getDBConnection();
			AccountManagementActivityReportDao accountMngdActReportDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getAccountManagementActivityReportDao(connection);
			
			parentID = accountMngdActReportDao.getParentId(accountId);
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
