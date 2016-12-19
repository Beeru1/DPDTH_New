/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.ReportDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.TransactionReport;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.ReportService;
/**
 * This class provides implementation for Report related functionalities.
 * 
 * @author ashish
 * 
 */
public class ReportServiceImpl implements ReportService {

	/* Logger for this class. */
	private Logger logger = Logger.getLogger(ReportServiceImpl.class.getName());

	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransferSummaryRptA2AData(com.ibm.virtualization.recharge.dto.ReportInputs,
	 *      int, int)
	 */
	public ArrayList getTransferSummaryRptA2AData(ReportInputs mtDTO,
			int lowerBound, int upperBound)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			int circleId = 0;
			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					if (0 != circleId) {
						circleId = sessionContext.getCircleId();
					}
					mtDTO.setCircleId(circleId);
					accountList = reportDao
							.getTransferSummaryRptA2AListAll(mtDTO);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					mtDTO.setParentId(parentId);
					accountList = reportDao
							.getTransferSummaryRptA2AChildListAll(mtDTO);
				}
			}

			if (accountList == null || accountList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed ::::Successfully retrieved Account Transfer List ");
		return accountList;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransferSummaryRptA2ADataAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getTransferSummaryRptA2ADataAll(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			// int circleId = 0;
			if (null != sessionContext) {

				mtDTO.setCircleId(sessionContext.getCircleId());
				accountList = reportDao.getTransferSummaryRptA2AListAll(mtDTO);
			}

			if (accountList == null || accountList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed ::::Successfully retrieved Account Transfer List ");
		return accountList;
	}


	

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransactionRptData(com.ibm.virtualization.recharge.dto.ReportInputs,
	 *      int, int)
	 */
	public ArrayList getTransactionRptData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList dataList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			
			if (null != sessionContext) {
				mtDTO.setCircleId(sessionContext.getCircleId());
				if(mtDTO.getRequestType()== null){
					throw new VirtualizationServiceException(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
				}

				else if(mtDTO.getRequestType().getValue()==RequestType.VAS.ordinal()){
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
					
				}
				dataList = reportDao.getTransactionRptList(mtDTO,
						lowerBound, upperBound,isExport);
			}

			if (dataList == null || dataList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(IllegalArgumentException illegalArg){
			logger.error("IllegalArgumentException occured:Invalid RequestType "+illegalArg.getMessage());
			throw new VirtualizationServiceException(illegalArg.getMessage()); 
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved report data ");
		return dataList;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.ReportService#getRechargeTransactionRptData(com.ibm.virtualization.recharge.dto.ReportInputs, int, int, boolean)
	 */
	public ArrayList getRechargeTransactionRptData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport,String transId)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList dataList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			
			if (null != sessionContext) {
				//mtDTO.setCircleId(sessionContext.getCircleId());
				dataList = reportDao.getRechargeTransactionRptList(mtDTO,
						lowerBound, upperBound,isExport,transId);
			}

			if (dataList == null || dataList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.debug("Executed ::::Successfully retrieved report data ");
		return dataList;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransactionRptDataAll(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	public ArrayList getTransactionRptDataAll(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		logger.info(" Started...... getTransactionRptDataAll ");
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList dataList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			int circleId = 0;
			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					if (0 != circleId) {
						circleId = sessionContext.getCircleId();
					}
					mtDTO.setCircleId(circleId);
					dataList = reportDao.getTransactionRptListAll(mtDTO);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					mtDTO.setParentId(parentId);
					dataList = reportDao.getTransactionRptChildListAll(mtDTO);
				}
			}

			if (dataList == null || dataList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved report data ");
		return dataList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransactionSummaryRptData(com.ibm.virtualization.recharge.dto.ReportInputs)
	 */
	// start 1
	public ArrayList getTransactionSummaryRptData(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();
		Connection connection = null;
		ArrayList transactionList = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getReportDao(connection);

			if (null != sessionContext) {
				mtDTO.setCircleId(sessionContext.getCircleId());
				if(mtDTO.getRequestType()== null){
					throw new VirtualizationServiceException(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
				}
				else if(mtDTO.getRequestType().getValue()==RequestType.VAS.ordinal()){
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
					
				}
				transactionList = reportDao
						.getTransactionSummaryRptData(mtDTO);
			}

			if (transactionList == null || transactionList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(IllegalArgumentException illegalArg){
			logger.error("IllegalArgumentException occured:Invalid RequestType "+illegalArg.getMessage());
			throw new VirtualizationServiceException(illegalArg.getMessage()); 
		}
		finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed ::::Successfully retrieved TransactionSummary List  ");
		return transactionList;

	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.ReportService#getTransactionRptA2AData1(com.ibm.virtualization.recharge.dto.ReportInputs, int, int, boolean)
	 */
	public ArrayList getTransactionRptA2AData(ReportInputs mtDTO,
			int lowerBound, int upperBound,boolean isExport)
			throws VirtualizationServiceException {
			/* get sessionContext */
			UserSessionContext sessionContext = mtDTO.getSessionContext();
			Connection connection = null;
			ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			if (null != sessionContext) {
				
					mtDTO.setCircleId(sessionContext.getCircleId());
					accountList = reportDao.getTransactionRptA2AList(mtDTO,
							lowerBound, upperBound,isExport);
			}

			if (accountList == null || accountList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
			
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Account List ");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.ReportService#getCustomerTransactionListWithId(java.lang.String)
	 */
	public ArrayList getCustomerTransactionListWithId(String transactionId,String transType)
			throws VirtualizationServiceException {
		logger.info(" Started getCustomerTransactionListWithId()...");

		ArrayList list = null;
		Connection connection = null;
		try {
			/** get the database connection */
			connection = DBConnectionManager.getDBConnection();

			ReportDao reportDao = DAOFactory
					.getDAOFactory(
							Integer.parseInt(ResourceReader
									.getCoreResourceBundleValue(
											Constants.DATABASE_TYPE)))
					.getReportDao(connection);

			list = reportDao
					.getCustomerTransactionListWithId(transactionId,transType);

			/**
			 * if distributor transaction list null or list.size()==0 means no
			 * record found throw exception no record found
			 */
			if (list == null || list.size() == 0) {
				logger.info("Searched Customer Transaction List Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_NO_RECORD);
			}
		} catch (DAOException exception) {
			logger.info(" Exception occured getting Customer list ");
			throw new VirtualizationServiceException(exception.getMessage());
		} catch (VirtualizationServiceException virtualizationExp) {
			logger.error("VirtualizationServiceException:"
					+ virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp
					.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed...Information SuccessFully Retrieve list of Customer Transactions done");
		return list;
	}


	public TransactionReport getTransactionRptDataView(int transactionId,String requestType) 
	throws VirtualizationServiceException {
		// TODO Auto-generated method stub
		logger.debug("Started... transactionId = " + transactionId);
		TransactionReport transactionReport = new TransactionReport();
		
		Connection connection = null;
		
		try {
			/* get the database connection */
			connection = DBConnectionManager.getReportDBConnection();
			ReportDao reportDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getReportDao(connection);
			
			if(requestType == null){
				throw new VirtualizationServiceException(ExceptionCode.Transaction.ERROR_TRANSACTION_TYPE);
			}else {
				//If RequestType is other than defined in Enum
				RequestType.valueOf(requestType).getValue();
			}
			transactionReport = reportDao.getTransactionRptListView(transactionId,requestType);
			if(transactionReport==null){
				logger.error("caught VirtualizationServiceException no record found");
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
				
			}
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(IllegalArgumentException illegalArg){
			logger.error("IllegalArgumentException occured:Invalid RequestType "+illegalArg.getMessage());
			throw new VirtualizationServiceException(illegalArg.getMessage()); 
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.debug("Executed::::Successfully retrieved report data to view ");
		return transactionReport;
	}

	
	/**
	 * This method will be used provide a list of customer transactions.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * @param lowerBound -
	 *            specifies the number from which to fetch the records
	 * @param upperBound -
	 *            specifies the number up to which to fetch the records
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException -
	 *             exception which will be thrown incase any exception occurs at
	 *             the service level.
	 */
	public ArrayList getCustomerTransactionList(ReportInputs mtDTO,
			int lowerBound, int upperBound,String transId)
			throws VirtualizationServiceException {
		logger.debug(" Started getCustomerTransactionList()...mobileNo="+mtDTO.getMobileNo());

		/** get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		ArrayList list = null;
		Connection connection = null;
		try {
			/** get the database connection */
			connection = DBConnectionManager.getDBConnection();

			ReportDao reportDao = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getReportDao(connection);
			int circleId = 0;
			if (null != sessionContext) {
				/** Check if user type is Internal */
				
					if (0 != sessionContext.getCircleId()) {
						circleId = sessionContext.getCircleId();
					}
					mtDTO.setCircleId(circleId);
					
					if((transId!=null)&& (!transId.equalsIgnoreCase("")) ){
						list = reportDao.getCustomerTransactionRptList(mtDTO, 0, 0, true,transId);
						
					}else{
						list =reportDao.getCustomerTransactionRptList(mtDTO, lowerBound, upperBound, false,transId);
					}
					
				
			}

			/**
			 * if distributor transaction list null or list.size()==0 means no
			 * record found throw exception no record found
			 */
			if (list == null || list.size() == 0) {
				logger.error("Searched Customer Transaction List Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_NO_RECORD);
			}
		} catch (DAOException exception) {
			logger.error(" Exception occured getting Customer list ");
			throw new VirtualizationServiceException(exception.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.trace("Executed...Information SuccessFully Retrieve list of Customer Transactions done");
		return list;
	}


	/**
	 * This method will be used provide a list of all customer transactions.
	 * This will provide all the records and is independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws VirtualizationServiceException -
	 *             exception which will be thrown incase any exception occurs at
	 *             the service level.
	 */
	public ArrayList getCustomerTransactionListAll(ReportInputs mtDTO,String transId)
			throws VirtualizationServiceException {
		logger.info(" Started getCustomerTransactionListAll() ...");

		/** get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		ArrayList list = null;
		Connection connection = null;
		try {
			/** get the database connection */
			connection = DBConnectionManager.getDBConnection();
			ReportDao reportDao = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getReportDao(connection);
			int circleId = 0;
			if (null != sessionContext) {
				/** Check if user type is Internal */
					if (0 != sessionContext.getCircleId()) {
						circleId = sessionContext.getCircleId();
					}
					mtDTO.setCircleId(circleId);
					if((transId!=null)&&(!transId.equalsIgnoreCase("")) ){
						list = reportDao.getCustomerTransactionRptList(mtDTO, 0, 0, true,transId);
						
					}else{
						list =reportDao.getCustomerTransactionRptList(mtDTO, 0, 0, true,transId);
					}
				
			}
			/**
			 * if distributor transaction list null or list.size()==0 means no
			 * record found throw exception no record found
			 */
			if (list == null || list.size() == 0) {
				logger.info("Searched Customer Transaction List Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_NO_RECORD);
			}
		} catch (DAOException exception) {
			logger.info(" Exception occured getting Customer list ");
			throw new VirtualizationServiceException(exception.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed...Information SuccessFully Retrieve all transactions done by Customer");
		return list;
	}

	
}
