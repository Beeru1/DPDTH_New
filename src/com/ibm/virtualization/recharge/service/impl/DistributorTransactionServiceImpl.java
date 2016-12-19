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
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.DistributorTransactionDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.DistributorTransactionService;
/**
 * This class provides implementation for Distributor Transaction .
 * 
 * @author bhanu
 * 
 */
public class DistributorTransactionServiceImpl implements
		DistributorTransactionService {
	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(DistributorTransactionServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DistributorTransactionService#createDistributorTransaction(com.ibm.virtualization.recharge.dto.DistributorTransaction)
	 */
	public void createDistributorTransaction(
			DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException {
		logger.info("Started..." + distributorTransaction);
		Connection connection = null;
		try {

			// TODO bhanu Sep.4,2007 Implement Validation
			ValidatorFactory.getDistributorTransactionValidator()
					.validateInsert(distributorTransaction);
			/**
			 * getting rate according to account Code if no rate found then
			 * throw invalid account Code exception
			 */
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistributorTransactionDao distributorTransactionDao = DAOFactory
					.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getDistributorTransactionDao(connection);
			AccountDao account = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			double rate = (account.getAccountRate(distributorTransaction
					.getAccountId()));
			logger.info(" retreived Rate ----------->" + rate);
			// set rate into dto and will to get insert
			distributorTransaction.setRate(rate);

			double calculatedAmt = distributorTransaction
					.getTransactionAmount()
					* 100 / rate;

			logger.info("The calculated Amount For Distributor Transaction:: "
					+ calculatedAmt);
			distributorTransaction.setCreditedAmount(calculatedAmt);

			/**
			 * Calling Circle DAO Method to update the operating balance of
			 * circle when a request for new transaction is made
			 */

			int circleID = account.getCircleId(distributorTransaction
					.getAccountId());

			CircleDao circledao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getCircleDao(connection);
			int numRows = circledao.updateCircleOperatingBal(
					distributorTransaction.getTransactionAmount(),
					distributorTransaction.getUpdatedBy(), circleID);

			if (numRows != 0) {

				/** Calling Distributor DAO Method to created new transaction */

				distributorTransactionDao
						.insertDistributorTransactionData(distributorTransaction);
				DBConnectionManager.commitTransaction(connection);
			} else {
				logger
						.error(" Error While updating circle operating balance  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_OPERATING_BALANCE);
			}

		} catch (DAOException e) {
			logger.error("  createDistributorTransaction()" + e.getMessage());
			throw new VirtualizationServiceException(e.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed... Distributor Transaction Created SuccessFully ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DistributorTransactionService#getDistributorTransactionList(java.lang.String)
	 */
	public ArrayList getDistributorTransactionList(ReportInputs mtDTO,
			int lowerBound, int upperBound)
			throws VirtualizationServiceException {
		logger.info(" Started...");
		ArrayList list = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();

			/*
			 * this method will call to get Tranasaction List Based On status
			 * value that will return distributorTransaction dto objects with
			 * values from database that will further shown on page
			 */

			DistributorTransactionDao distributorTransaction = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistributorTransactionDao(connection);
			list = distributorTransaction.getDistributorTransactionList(mtDTO,
					lowerBound, upperBound);
			/**
			 * if distributor transaction list null or list.size()==0 means no
			 * record found throw exception no record found
			 */
			if (list == null || list.size() == 0) {
				logger
						.info("Searched Distributor Transaction List Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_NO_RECORD);
			}
		} catch (DAOException exception) {
			logger.info(" Exception occured getting list ");
			throw new VirtualizationServiceException(exception.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed...Information SuccessFully Retrieve For Distributor Transaction");
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DistributorTransactionService#getDistributorTransactionReviewDetail(long)
	 */
	public DistributorTransaction getDistributorTransactionReviewDetail(
			long trasactionId) throws VirtualizationServiceException {
		logger.info(" Started...");

		DistributorTransaction distributorTransaction = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistributorTransactionDao distributorTransactionDao = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistributorTransactionDao(connection);
			// calling mehtod to get distributor review fine.
			distributorTransaction = distributorTransactionDao
					.getDistributorTransactionReviewDetail(trasactionId);
			if (distributorTransaction == null) {
				logger
						.error("Selected Distributor Transaction Record Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_INVALID_TRANSACTION_ID);
			}
		} catch (DAOException Exception) {
			logger
					.info(":Exception occured while Reteriving Distributor Review Transaction data ");
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_INVALID_TRANSACTION_ID);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info(" Executed...Successfully Retreive  Distribtor Transaction Details ");
		return distributorTransaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.DistributorTransactionService#updateDistributorTransaction(com.ibm.virtualization.recharge.dto.DistributorTransaction)
	 */
	public void updateDistributorTransaction(
			DistributorTransaction distributorTransaction)
			throws VirtualizationServiceException {
		logger.info(" Started..." + distributorTransaction);
		Connection connection = null;
		try {
			ValidatorFactory.getDistributorTransactionValidator()
					.validateUpdate(distributorTransaction);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistributorTransactionDao distributorTransactionDao = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistributorTransactionDao(connection);
			// method only update if Pending transaction
			DistributorTransaction distTrans = distributorTransactionDao
					.getDistributorTransactionReviewDetail(distributorTransaction
							.getTransactionId());
			if (distTrans.getReviewStatus() != null
					&& distTrans.getReviewStatus().equalsIgnoreCase(
							Constants.TRANS_DIST_PENDING)) {
				logger.info(" updateDistributorTransaction : "
						+ distTrans.getReviewStatus());

				/**
				 * Calling Circle DAO Method and Account DAO to update the
				 * available balance of circle and available balance,operating
				 * balance of account.
				 */
				AccountDao accountdao = DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getAccountDao(connection);

				int circleID = accountdao.getCircleId(distributorTransaction
						.getAccountId());

				CircleDao circledao = DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getCircleDao(connection);

				if (distributorTransaction.getReviewStatus().equalsIgnoreCase(
						Constants.TRANS_DIST_APPROVED)) {
					accountdao.increaseAccountBalance(distributorTransaction
							.getCreditedAmount(), distributorTransaction
							.getAccountId(), distributorTransaction
							.getUpdatedBy());

					circledao.reduceCircleAvailableBalance(
							distributorTransaction.getTransactionAmount(),
							distributorTransaction.getUpdatedBy(), circleID);

				} else {
					if (distributorTransaction.getReviewStatus()
							.equalsIgnoreCase(Constants.TRANS_DIST_REJECTED)) {
						circledao
								.increaseCircleOperatingBalance(
										distributorTransaction
												.getTransactionAmount(),
										distributorTransaction.getUpdatedBy(),
										circleID);
					}

				}

				// calling distrinbutor transaction update method
				int rowsUpdated = distributorTransactionDao
						.updateDistributorTransaction(distributorTransaction);

				if (rowsUpdated == 0) {
					logger
							.error("DAOException occured while update Distributor Transaction updatefailure: Exception Message:Service ");
					throw new DAOException(
							ExceptionCode.Transaction.ERROR_UPDATE_FAILURE);
				}
				DBConnectionManager.commitTransaction(connection);
			}

		} catch (DAOException de) {
			logger
					.error("DAOException occured while update Distributor Transaction : Exception Message:Service "
							+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed::::");

	}

	/**
	 * This method will be used provide total count of the distributor
	 * transactions for internal user logged in.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return int - total number of records.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while counting the records.
	 */
	public int getDistributorTransactionCount(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		logger.info("Started...getDistributorTransactionCount()");
		Connection connection = null;
		int noOfPages = 0;
		try {
			/** get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistributorTransactionDao distributorTransaction = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistributorTransactionDao(connection);
			noOfPages = distributorTransaction
					.getDistributorTransactionCount(mtDTO);
		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}  finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed :::: successfully count no of rows "
						+ noOfPages);
		return noOfPages;
	}

	/**
	 * This method will be used provide a list of all distributor transactions
	 * for internal user logged in. This will provide all the records and is
	 * independent of pagination logic.
	 * 
	 * @param mtDTO -
	 *            reports input dto which will contain all the mandatory
	 *            information required by the dao to fetch the records.
	 * 
	 * @return list - contains the records fetched.
	 * @throws DAOException -
	 *             exception which will be thrown incase any exception occurs
	 *             while fetching the records.
	 */
	public ArrayList getDistributorTransactionListAll(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		logger.info(" Started getDistributorTransactionListAll()...");
		ArrayList list = null;
		Connection connection = null;
		try {
			/** get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DistributorTransactionDao distributorTransaction = DAOFactory
			.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getDistributorTransactionDao(connection);
			list = distributorTransaction
					.getDistributorTransactionListAll(mtDTO);
			/**
			 * if distributor transaction list null or list.size()==0 means no
			 * record found throw exception no record found
			 */
			if (list == null || list.size() == 0) {
				logger
						.info("Searched Distributor Transaction List Not Found ");
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_NO_RECORD);
			}
		} catch (DAOException exception) {
			logger.info(" Exception occured getting list ");
			throw new VirtualizationServiceException(exception.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("VirtualizationServiceException:"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed...Information SuccessFully Retrieve For Distributor Transaction");
		return list;
	}

}