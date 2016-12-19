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
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResponseCode;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LogTransactionDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.AccountTransaction;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AccountTransactionService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides implementation for Account related functionalities
 * 
 * @author Mohit
 * 
 */
public class AccountTransactionServiceImpl implements AccountTransactionService {

	/* Logger for this class. */
	private Logger logger = Logger
			.getLogger(AccountTransactionServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#confirmTransaction(com.ibm.virtualization.recharge.dto.TransactionDTO)
	 */
	public void confirmTransaction(AccountTransaction accountTransaction)
			throws VirtualizationServiceException {

		logger.info("Started..." + accountTransaction.getUserSessionContext()
				+ " AND " + accountTransaction);
		Connection connection = null;
		/* Variable to identify the Status of Transaction */
		TransactionStatus transactionStatus;
		transactionStatus = TransactionStatus.PENDING;
		try {
			// *** get the database connection
			connection = DBConnectionManager.getDBConnection();
			accountTransaction.setRequestType(RequestType.ACCOUNT);
			accountTransaction.setTransactionType(TransactionType.ACCOUNT);
			accountTransaction.setUpdatedBy(accountTransaction
					.getUserSessionContext().getId());
			accountTransaction.setCreatedBy(accountTransaction
					.getUserSessionContext().getId());
			// *** Check Whether User is authorized for account transfer
			logger.info("GroupId="
					+ accountTransaction.getUserSessionContext().getGroupId());
			if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
					accountTransaction.getUserSessionContext().getGroupId(),
					accountTransaction.getTransactionChannel(),
					AuthorizationConstants.ROLE_TRANSFER_TO_CHILD_ACC)) {
				logger.error("User is not autherized");
				accountTransaction.setErrorMessage(ResourceReader.getValueFromBundle(
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED,
						Constants.ERROR_RESOURCE_BUNDLE));
				accountTransaction
						.setReasonId(ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
				throw new VirtualizationServiceException(ResponseCode.AUTHORIZATION_FAILURE_CODE,
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			}
			/* validating the Source & Destination Account */
			ValidatorFactory.getAccountTransactionValidator().validate(
					accountTransaction);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			logger.info("destinationAccount.Mobile="
					+ accountTransaction.getDestinationMobileNo());
			/* check if destination account is active */
			Account destinationAccount = accountDao
					.getAccountDetailsByMobile(accountTransaction
							.getDestinationMobileNo());
			logger.info("destinationAccount.getAccountId()="
					+ destinationAccount.getAccountId());
			accountTransaction.setDestinationAccountId(destinationAccount
					.getAccountId());
			if (!destinationAccount.getStatus().equals("A")) {
				logger.error("Destination account is not active");
				accountTransaction.setErrorMessage(ResourceReader.getValueFromBundle(
						ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT,
						Constants.ERROR_RESOURCE_BUNDLE));
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT);
			}
//			 check if source account is billable Account
			if (accountTransaction.getUserSessionContext().getBillableAccId() > 0 ) {
				//TODO BAhuja Jan 31, Proper error code to used.
				logger
						.error("Source account is non billable account.");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_BILLABLE_SOURCE_ACC_INVALID);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_BILLABLE_SOURCE_ACC_INVALID);
			}
//			 check if destination account is billable Account
			if (destinationAccount.getBillableCodeId() > 0 ) {
				//TODO BAhuja Jan 31, Proper error code to used.
				logger
						.error("Destination account is non billable account.");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_BILLABLE_DESTINATION_ACC_INVALID);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_BILLABLE_DESTINATION_ACC_INVALID);
			}

			/* check if transfering account is child of */
			if (destinationAccount.getParentAccountId() != accountTransaction
					.getSourceAccountId()) {
				logger
						.error("Destination account is not a child of source account.");
				accountTransaction.setErrorMessage(ResourceReader.getValueFromBundle(
						ExceptionCode.Transaction.ERROR_IS_NOT_CHILD,
						Constants.ERROR_RESOURCE_BUNDLE));
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_IS_NOT_CHILD);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_IS_NOT_CHILD);
			}
			/* validating configuration slab */
			SysConfig sysConfig = CacheFactory.getCacheImpl().getSystemConfig(
					accountTransaction.getCircleId(),
					accountTransaction.getTransactionType(), ChannelType.WEB);

			if (sysConfig == null) {
				logger.error("No Record Found for VR_SYSTEM_CONFIG_ID = "
						+ accountTransaction.getCircleId());
				accountTransaction.setErrorMessage(ResourceReader.getValueFromBundle(
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ,
						Constants.ERROR_RESOURCE_BUNDLE));
				accountTransaction
						.setReasonId(ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
			}

			logger
					.info("sucessfully fetched system config details.Max rechare value:"
							+ sysConfig.getMaxAmount()
							+ "Min recharge value:"
							+ sysConfig.getMinAmount());

			if (accountTransaction.getTransactionAmount() < sysConfig
					.getMinAmount()
					|| accountTransaction.getTransactionAmount() > sysConfig
							.getMaxAmount()) {
				logger.error("Transfer amount:"
						+ accountTransaction.getTransactionAmount()
						+ " should be between MinTransferValue:"
						+ sysConfig.getMinAmount() + " and MaxTransferValue:"
						+ sysConfig.getMaxAmount());
				accountTransaction.setErrorMessage(
						ExceptionCode.Transaction.ERROR_TRANSFER_OUT_RANGE);
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_TRANSFER_OUT_RANGE);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_TRANSFER_OUT_RANGE);
			}
			logger.info("After the Slab");

			/* ** Setting rate to destination's rate */
			double rate = destinationAccount.getRate();
			accountTransaction.setRate(rate);

			double creditAmount = 0.0;
			/* Set Credited Amount */
			int iRate = (int) rate;
			if (iRate != 0) {
				creditAmount = (accountTransaction.getTransactionAmount() * 100)
						/ rate;
				// Set the Amount to be Credited to be rounded to 2 decimal
				// digits
				creditAmount = Utility.roundDouble(creditAmount, 2);
				accountTransaction.setCreditedAmount(creditAmount);
			} else {
				// Set the Amount to be Credited to be rounded to 2 decimal
				// digits
				creditAmount = Utility.roundDouble(accountTransaction
						.getTransactionAmount(), 2);
				accountTransaction.setCreditedAmount(creditAmount);
			}
			// Set the Transaction Amount to contain upto 2 decimal digits
			accountTransaction.setTransactionAmount(Utility.roundDouble(
					accountTransaction.getTransactionAmount(), 2));
			
			/* credit amount validation */
			if (creditAmount < 0) {
				logger.error("Credit amount is negative");
				accountTransaction.setErrorMessage(ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
						
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
			}

		} catch (DAOException de) {

			transactionStatus = TransactionStatus.FAILURE;
			/* Update status of transaction in Transaction Master Table */
			updateTransactionStatus(transactionStatus, accountTransaction
					.getUserSessionContext().getId(), accountTransaction
					.getTransactionId(), accountTransaction.getReasonId());

			logger
					.fatal(" DAOException occured while doing Account to Account Recharge "
							+ "Exception Message: " + de.getMessage());
		
				accountTransaction.setErrorMessage(ExceptionCode.Transaction.ERROR_TRANSACTON);
						
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_TRANSACTON);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CacheImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
			logger.info("Executed ::::");
		}

	}

	public int doAccountTransactionSyncAfterConfirmation(
			AccountTransaction accountTransaction)
			throws VirtualizationServiceException {

		logger.info("Started...  ");
		int returnTransStatus = -1;
		Connection connection = null;
		/* Variable to identify the Status of Transaction */
		accountTransaction.setTransactionStatus(TransactionStatus.PENDING);
		try {
			// *** get the database connection
			connection = DBConnectionManager.getDBConnection();

			accountTransaction.setRequestType(RequestType.ACCOUNT);
			accountTransaction.setTransactionType(TransactionType.ACCOUNT);
			accountTransaction.setUpdatedBy(accountTransaction
					.getContextSourceId());
			accountTransaction.setCreatedBy(accountTransaction
					.getContextSourceId());
			// *** insert into transaction master table
			returnTransStatus = insertTransactionMaster(connection,
					accountTransaction);
			/* If the request is Duplicate or Blackout */
			if (returnTransStatus == TransactionStatus.DUPLICATE.getValue()
					|| returnTransStatus == TransactionStatus.BLACKOUT
							.getValue()) {
				return returnTransStatus;
			}
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			/*
			 * Update source account balance
			 */
			logger.info("sourceId=" + accountTransaction.getSourceAccountId());
			// *** Update source account balance for billable parent in case of
			// non billabe account as per disscussion
			// accountDao
			// .doUpdateSourceBalance(
			// (transactionDto.getUserSessionContext()
			// .getBillableAccId() == 0) ? transactionDto
			// .getSourceAccountId() : transactionDto
			// .getUserSessionContext().getBillableAccId(),
			// -transactionDto.getTransactionAmount());
			accountDao.doUpdateSourceBalance(accountTransaction
					.getSourceAccountId(), -accountTransaction
					.getTransactionAmount());

			/*
			 * Update destination account balance
			 */
			accountDao.doUpdateDestinationBalance(accountTransaction
					.getDestinationAccountId(), accountTransaction
					.getCreditedAmount());

			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info("Successfully done");
			/* set transactionStatus if transaction is successfull */
			accountTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			logger
					.info("transactionStatus before upadating Transaction Status ="
							+ accountTransaction.getTransactionStatus());
			/* Update status of transaction in Transaction Master Table */
			updateTransactionStatus(TransactionStatus.SUCCESS,
					accountTransaction.getContextSourceId(), accountTransaction
							.getTransactionId(), null);

		} catch (DAOException de) {
			accountTransaction.setTransactionStatus(TransactionStatus.FAILURE);

			if (de.getMessage().equals(
					ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT)) {
				accountTransaction
						.setErrorMessage(ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
								
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
				/* Update status of transaction in Transaction Master Table */
				updateTransactionStatus(accountTransaction
						.getTransactionStatus(), accountTransaction
						.getContextSourceId(), accountTransaction
						.getTransactionId(), accountTransaction.getReasonId());
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
			} else {
				accountTransaction.setErrorMessage(
						ExceptionCode.Transaction.ERROR_TRANSACTON);
				/* Update status of transaction in Transaction Master Table */
				updateTransactionStatus(accountTransaction
						.getTransactionStatus(), accountTransaction
						.getContextSourceId(), accountTransaction
						.getTransactionId(), ExceptionCode.ERROR_DB_INTERNAL);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_TRANSACTON);
			}

		} finally {

			/* Insert into Transaction_Details table */
			String message = "Transaction amount:"
					+ accountTransaction.getTransactionAmount() + "Rate:"
					+ accountTransaction.getRate() + "CircleId:"
					+ accountTransaction.getCircleId() + "Credit Amount:"
					+ accountTransaction.getCreditedAmount() + "Debit Amount:"
					+ accountTransaction.getTransactionAmount();
			logger.info(message);
			/* Insert into Transaction_Details table */
			insertTransactionLog(accountTransaction);

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return returnTransStatus;
	}

	/**
	 * This method inserts input details into transaction master table.
	 * 
	 * @param accountTransaction
	 * @return returnTransStatus
	 * @throws DAOException
	 */

	private int insertTransactionMaster(Connection connection,
			AccountTransaction accountTransaction) throws DAOException {
		logger.info("Started..." + accountTransaction);
		int returnTransStatus = -1;
		/* insert into customer transaction table */
		/* get the database connection */
		RechargeDao rechargeDao;
		try{
		 rechargeDao = DAOFactory.getDAOFactory(
				Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getRechargeDao(connection);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("CacheImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		TransactionMaster transactionMaster = new TransactionMaster();
		// Setting craeted by using the Id from the context
		transactionMaster.setCreatedBy(accountTransaction
				.getUserSessionContext().getId());
		transactionMaster.setUpdatedBy(accountTransaction
				.getUserSessionContext().getId());
		transactionMaster.setCreatedDate(new Date());
		//transactionMaster.setDestinationId(accountTransaction.getDestinationMobileNo());
		
		transactionMaster.setDestinationId(String.valueOf(accountTransaction.getDestinationAccountId()));
		transactionMaster.setSourceId(accountTransaction.getSourceAccountId());
		transactionMaster.setTransactionAmount(accountTransaction
				.getTransactionAmount());
		transactionMaster.setTransactionChannel(accountTransaction
				.getTransactionChannel());
		transactionMaster.setTransactionDate(new Date());
		StringBuffer xml=new StringBuffer();
		xml.append("<AccountTransaction>")
			.append("<DestinationMobileNo>").append(accountTransaction.getDestinationMobileNo()).append("</DestinationMobileNo>")
			.append("<BillableAccId>").append(accountTransaction.getUserSessionContext().getBillableAccId()).append("</BillableAccId>")
		.append("<SourceMobileNo>").append(accountTransaction.getSourceMobileNo()).append("</SourceMobileNo>")
		.append("<CellId>").append(accountTransaction.getCellId()).append("</CellId>")
		.append("</AccountTransaction>");
		transactionMaster.setTransactionMessage(xml.toString());
		transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
		transactionMaster.setRequestType(accountTransaction.getRequestType());
		transactionMaster.setTransactoinId(accountTransaction
				.getTransactionId());
		transactionMaster.setPhysicalId(accountTransaction.getPhysicalId());
		transactionMaster.setRequestTime(accountTransaction.getRequestTime());

		returnTransStatus = rechargeDao
				.insertTransactionMasterPreProcess(transactionMaster);

		logger.info("Executed ::::");
		return returnTransStatus;
	}

	/* insert into VR_TRANS_DETAIL table */
	private void insertTransactionLog(AccountTransaction accountTransaction)
			throws VirtualizationServiceException {
		logger.info("Started..." + accountTransaction.toString());
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			LogTransactionDao logTransactionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLogTransactionDao(connection);
			ActivityLog activityLog = new ActivityLog();
		
			activityLog.setMessage(accountTransaction.toString());
			activityLog.setTransactionId(accountTransaction.getTransactionId());
			activityLog.setTransactionState(accountTransaction
					.getTransactionState());
			activityLog.setStatus(accountTransaction.getTransactionStatus());
			activityLog.setCreatedBy(accountTransaction.getCreatedBy());
			activityLog.setRequestTime(accountTransaction.getRequestTime());
			activityLog.setCreatedDate(Utility.getDateTime());
			logTransactionDao.insertLog(activityLog);
			logger.info("Successfully inserted...");
		} catch (DAOException de) {
			logger
					.fatal(" DAOException occured while inserting into VR_TRANS_DETAIL table "
							+ "Exception Message: " + de.getMessage());
			accountTransaction.setReasonId(de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("CacheImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {

			// insert into transactioon log details

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#getChildAccounts(java.lang.String)
	 */
	public ArrayList getChildAccounts(long sourceAccountId)
			throws VirtualizationServiceException {
		logger.info("Started...");
		ArrayList childAccounts;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			childAccounts = accountDao.getChildAccounts(sourceAccountId);
			logger.info("Successfully found the Child Account");
		} catch (DAOException de) {
			logger.error("DAOException Caught sourceAccountId: "
					+ sourceAccountId + de.getMessage());
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_FINDING_CHILD_LIST);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("CacheImpl:caught VirtualizationServiceException"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return childAccounts;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountTransactionService#doAccountTransaction(com.ibm.virtualization.recharge.dto.AccountTransaction)
	 */
	public int doAccountTransaction(AccountTransaction accountTransaction)
			throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		int returnTransStatus = -1;
		try {
			/* ** get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* ** Set Transaction Type */
			accountTransaction.setRequestType(RequestType.ACCOUNT);
			accountTransaction.setTransactionType(TransactionType.ACCOUNT);
			accountTransaction.setUpdatedBy(accountTransaction
					.getUserSessionContext().getId());
			
			/* ** validating the Source & Destination Account */
			ValidatorFactory.getAccountTransactionValidator().validate(
					accountTransaction);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			/*
			 * ** Get details for the destination account if the account is
			 * active
			 */
			Account destinationAccount= null;
			destinationAccount = accountDao
					.getAccountDetailsByMobile(accountTransaction
							.getDestinationMobileNo());
			
//			 *** Set the Destination Account Id in DTO if destinationAccount is not Null
			if(destinationAccount != null){
				accountTransaction.setDestinationAccountId(destinationAccount
						.getAccountId());
			}
			
						
			/* Make an entry into the TransactionMaster Table */
			returnTransStatus = insertTransactionMaster(connection,
					accountTransaction);
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				return TransactionStatus.DUPLICATE.getValue();
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				return TransactionStatus.BLACKOUT.getValue();
			}
			if (destinationAccount == null) {
				logger.error("Destincation mobile number not valid.");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_INVALID_DESTINATION);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_INVALID_DESTINATION);
			}
			if (!destinationAccount.getStatus().equals("A")) {
				logger.error("Destination account is not active");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_IS_ACTIVE_ACCOUNT);
			}
//			 check if destination account is billable Account
			if (destinationAccount.getBillableCodeId() > 0 ) {
				//TODO BAhuja Jan 31, Proper error code to used.
				logger
						.error("Destination account is non billable account.");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_BILLABLE_DESTINATION_ACC_INVALID);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_BILLABLE_DESTINATION_ACC_INVALID);
			}
			

			
			// check if transfering account is child of Source Account
			if (destinationAccount.getParentAccountId() != accountTransaction
					.getSourceAccountId()) {
				logger
						.error("Destination account is not a child of source account. destination parent account id:"
								+ destinationAccount.getParentAccountId()
								+ "source account id:"
								+ accountTransaction.getSourceAccountId());
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_IS_NOT_CHILD);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_IS_NOT_CHILD);
			}
			
			// Check Whether User is authorized for account transfer
			// *** Check for Group & Role mapping for the Source Account
			logger.info("group="
					+ accountTransaction.getUserSessionContext().getGroupId());
			if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
					accountTransaction.getUserSessionContext().getGroupId(),
					accountTransaction.getTransactionChannel(),
					AuthorizationConstants.ROLE_TRANSFER_TO_CHILD_ACC)) {
				logger.error("User is not Authorized");
				accountTransaction.setErrorMessage(
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
				
				accountTransaction
						.setReasonId(ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
				throw new VirtualizationServiceException(
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			}

			// check if source account is billable Account
			if (accountTransaction.getUserSessionContext().getBillableAccId() > 0 ) {
				//TODO BAhuja Jan 31, Proper error code to used.
				logger
						.error("Source account is non billable account.");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_BILLABLE_SOURCE_ACC_INVALID);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_BILLABLE_SOURCE_ACC_INVALID);
			}

			/* validating configuration slab */
			/* validating configuration slab */
			SysConfig sysConfig = CacheFactory.getCacheImpl().getSystemConfig(
					accountTransaction.getCircleId(),
					accountTransaction.getTransactionType(),
					accountTransaction.getTransactionChannel());

			if (sysConfig == null) {
				logger.error("No Record Found for VR_SYSTEM_CONFIG_ID = "
						+ accountTransaction.getCircleId());
				accountTransaction
						.setReasonId(ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
				throw new VirtualizationServiceException(
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
			}

			logger
					.info("sucessfully fetched system config details.Max rechare value:"
							+ sysConfig.getMaxAmount()
							+ "Min recharge value:"
							+ sysConfig.getMinAmount());

			if (accountTransaction.getTransactionAmount() < sysConfig
					.getMinAmount()
					|| accountTransaction.getTransactionAmount() > sysConfig
							.getMaxAmount()) {
				logger.error("Transfer amount:"
						+ accountTransaction.getTransactionAmount()
						+ " should be between MinTransferValue:"
						+ sysConfig.getMinAmount() + " and MaxTransferValue:"
						+ sysConfig.getMaxAmount());
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_TRANSFER_OUT_RANGE);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_TRANSFER_OUT_RANGE);
			}

			double rate = destinationAccount.getRate();
			accountTransaction.setRate(rate);

			// *** Set Credited Amount
			double creditAmount = 0.0;
			int iRate = (int) rate;
			if (iRate != 0) {
				creditAmount = (accountTransaction.getTransactionAmount() * 100)
				/ rate;

				// Set the Amount to be Credited to be rounded to 2 decimal digits
				creditAmount = Utility.roundDouble(creditAmount,2);
				
				accountTransaction.setCreditedAmount(creditAmount);
			} else {
				// Set the Amount to be Credited to be rounded to 2 decimal
				// digits
				creditAmount = Utility.roundDouble(accountTransaction
						.getTransactionAmount(), 2);
				accountTransaction.setCreditedAmount(creditAmount);
			}
			// *** credit amount validation
			if (creditAmount < 0) {
				logger.error("Credit amount is negative");
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
			}
			// *** Update source account balance for billable parent in case of
			// non billabe account as per disscussion
			// accountDao
			// .doUpdateSourceBalance(
			// (transactionDto.getUserSessionContext()
			// .getBillableAccId() == 0) ? transactionDto
			// .getSourceAccountId() : transactionDto
			// .getUserSessionContext().getBillableAccId(),
			// -transactionDto.getTransactionAmount());
			
			// Set the Amount to be deducted to be rounded to 2 decimal digits  
			accountTransaction.setTransactionAmount(Utility.roundDouble(
					accountTransaction.getTransactionAmount(), 2));
			
			accountDao.doUpdateSourceBalance(accountTransaction
					.getSourceAccountId(), -accountTransaction
					.getTransactionAmount());

			// *** Update destination account balance
			accountDao.doUpdateDestinationBalance(accountTransaction
					.getDestinationAccountId(), creditAmount);
		
			//set available balance
			accountTransaction.setSourceAccountBalance(accountDao.getBalance(accountTransaction
					.getSourceAccountId()).getAvailableBalance());
			accountTransaction.setDestinationAccountBalance(accountDao.getBalance(accountTransaction
					.getDestinationAccountId()).getAvailableBalance());
			// *** Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
			logger.info("Account " + accountTransaction.getSourceAccountId()
					+ " successfully updated by "
					+ accountTransaction.getDestinationAccountId());
			accountTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			returnTransStatus=0;
		} catch (DAOException de) {
			logger
					.fatal(" DAOException occured while doing Account to Account Recharge "
							+ "Exception Message: " + de.getMessage());
			if (de.getMessage().equals(
					ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT)) {
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.ERROR_SOURCE_IN_SUFFICIENT_AMT);
			} else {
				accountTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTON);
				throw new VirtualizationServiceException(

				ExceptionCode.Transaction.ERROR_TRANSACTON);
			}
		} finally {

			// insert into transactioon log details

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");
		return returnTransStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountTransactionService#updateTransactionStatus(com.ibm.virtualization.recharge.common.TransactionStatus,
	 *      long, long)
	 */
	public void updateTransactionStatus(TransactionStatus transactionStatus,
			long accountId, long transactionId, String reasonId)
			throws VirtualizationServiceException {
		logger.info("Started ...transactionStatus" + transactionStatus
				+ "And accountId:" + accountId + " And transactionId:"
				+ transactionId);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();

			RechargeDao dao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);
			// *** Update the Status for the transaction
			logger.info("transactionStatus=" + transactionStatus);

			dao.updateTransactionStatus(transactionId, transactionStatus,
					reasonId, accountId, null);
			DBConnectionManager.commitTransaction(connection);
			logger
					.info("Executed...Successfully updated the Status in the Transaction Master Table");
		} catch (DAOException de) {
			logger
					.fatal(" DAOException occured while Updating status in the TransactionMaster Table "
							+ "Exception Message: " + de.getMessage());

			throw new VirtualizationServiceException(de.getMessage());
		} finally {

			// insert into transactioon log details

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountTransactionService#getBalance(long)
	 */
	public double getBalance(long accountId)
			throws VirtualizationServiceException {
		logger.info("Started ...accountId" + accountId);
		Connection connection = null;
		AccountBalance dto = new AccountBalance();
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao dao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			dto = dao.getBalance(accountId);
			return dto.getAvailableBalance();

		} catch (DAOException de) {
			logger
					.fatal(" DAOException occured while finding balance for the AccountID="
							+ accountId
							+ "Exception Message: "
							+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			// insert into transactioon log details
			DBConnectionManager.releaseResources(connection);
		}

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountTransactionService#getDestinationAccountDetails(String)
	 */
	
	public Account getDestinationAccountDetails(String destinationMobileNo)
	throws VirtualizationServiceException {
			logger.info("Starting ::::");
			Connection connection = null;
			AccountDao accountDao=null;
			Account destinationAccount=null;
			try {
					/* get the database connection */
					connection = DBConnectionManager.getDBConnection();
					accountDao = DAOFactory.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
							.getAccountDao(connection);
					/*
					 * ** Get details for the destination account if the account is
					 * active
					 */
					destinationAccount = accountDao
							.getAccountDetailsByMobile(destinationMobileNo);
					
			} catch (DAOException de) {
				logger.error("Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger
			.info(" Executed :::: Successfully retreived Account Information  ");
			return destinationAccount;
	}

}
