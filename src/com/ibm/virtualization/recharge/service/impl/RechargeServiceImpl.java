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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.inadapter.air.beans.SuperRefillRequestDTO;
import com.ibm.virtualization.inadapter.air.beans.SuperRefillResponseDTO;
import com.ibm.virtualization.inadapter.air.common.AIRAdapterConstants;
import com.ibm.virtualization.inadapter.air.service.impl.AIRAdapter;
import com.ibm.virtualization.inadapter.service.INAdapterService;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ResponseCode;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LogTransactionDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.dto.RechargeTransaction;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.RechargeService;
/**
 * This class provides implementation for recharge functionalities.
 * 
 * @author Sushil
 * 
 */
public class RechargeServiceImpl implements RechargeService {

	/* Logger for this class. */
	private Logger logger = Logger.getLogger(RechargeServiceImpl.class
			.getName());

	public void calculateTransactionInfo(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		logger.trace("Started...calculations for transaction.");
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			// performs validations along with subscriber no validation
			validate(rechargeTransaction);
			computeCreditAndDebitAmt(rechargeTransaction, connection);

		} catch (DAOException de) {
			logger.warn("DAOException occured  " + "Exception Message: "
					+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}

		finally {
			DBConnectionManager.releaseResources(connection);
		}

		logger.trace("successfully inserted transaction details");

	}

	/**
	 * This method inserts input details into transaction master table.
	 * 
	 * @param connection
	 *            Connection
	 * @param dto
	 *            RechargeTransaction
	 * @return returnTransStatus
	 * @throws DAOException
	 * @throws VirtualizationServiceException 
	 * @throws NumberFormatException 
	 */ 
	private int insertTransactionMaster(Connection connection,
			RechargeTransaction dto) throws DAOException, NumberFormatException, VirtualizationServiceException {
		logger.debug("Started...TransctionId: " + dto.getTransactionId());
		int returnTransStatus = -1;
		/* insert into customer transaction table */
		RechargeDao rechargeDao = DAOFactory.getDAOFactory(
				Integer.parseInt(ResourceReader.getCoreResourceBundleValue(
						Constants.DATABASE_TYPE)))
				.getRechargeDao(connection);
		TransactionMaster transactionMaster = new TransactionMaster();
		transactionMaster.setCreatedBy(dto.getSourceAccountId());
		transactionMaster.setCreatedDate(Utility.getDateTime());
		transactionMaster.setDestinationId(dto.getDestinationMobileNo());
		transactionMaster.setSourceId(dto.getSourceAccountId());
		transactionMaster.setTransactionAmount(dto.getTransactionAmount());
		transactionMaster.setTransactionChannel(dto.getTransactionChannel());
		transactionMaster.setTransactionDate(Utility.getDateTime());
		StringBuffer xml=new StringBuffer();
		xml.append("<RechargeTransaction>")
			.append("<destinationMobileNo>").append(dto.getDestinationMobileNo()).append("</destinationMobileNo>")
		.append("<sourceMobileNo>").append(dto.getSourceMobileNo()).append("</sourceMobileNo>")
		.append("<BillableAccId>").append(dto.getUserSessionContext().getBillableAccId()).append("</BillableAccId>")
				.append(dto.getMessage())
		.append("<CellId>").append(dto.getCellId()).append("</CellId>")		
		.append("</RechargeTransaction>");
		transactionMaster.setTransactionMessage(xml.toString());
		transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
		transactionMaster.setRequestType(RequestType.RECHARGE);
		transactionMaster.setTransactoinId(dto.getTransactionId());
		transactionMaster.setPhysicalId(dto.getPhysicalId());
		transactionMaster.setRequestTime(dto.getRequestTime());
		logger.debug("TransId in transMaster=" + dto.getTransactionId());
		returnTransStatus = rechargeDao
				.insertTransactionMasterPreProcess(transactionMaster);
		logger.trace("Successfully Inserted: ");
		return returnTransStatus;
	}

	/**
	 * 
	 * insert into V_TRANS_DETAIL table
	 * 
	 * @param transactionId
	 *            long
	 * @param state
	 *            TransactionState
	 * @param detailMessage
	 *            String
	 * 
	 * @param createdBy
	 * @throws DAOException
	 * @throws VirtualizationServiceException 
	 * @throws NumberFormatException 
	 */
	private void insertTransactionLog(RechargeTransaction rechargeTransaction) throws DAOException, NumberFormatException, VirtualizationServiceException {
		logger
				.debug("Starting insertion into  transaction detail.  createdBy  :"
						+ rechargeTransaction.getUserSessionContext().getId() );
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			LogTransactionDao logTransactionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(
							Constants.DATABASE_TYPE)))
					.getLogTransactionDao(connection);
			ActivityLog activityLog = new ActivityLog();
			activityLog.setRequestTime(rechargeTransaction.getRequestTime());
			activityLog
					.setTransactionId(rechargeTransaction.getTransactionId());
		
			activityLog.setMessage(rechargeTransaction.toString());
			activityLog.setTransactionState(rechargeTransaction
					.getTransactionState());
			activityLog.setStatus(rechargeTransaction.getTransactionStatus());
			activityLog.setCreatedBy(rechargeTransaction.getUserSessionContext().getId());
			activityLog.setCreatedDate(Utility.getDateTime());
			logTransactionDao.insertLog(activityLog);
			logger.trace("successfully inserted transaction details");
		} finally {

			DBConnectionManager.releaseResources(connection);
		}

	}

	/*
	 * (non-Javadoc) Rollback transaction
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#releaseAmount(java.lang.String,
	 *      double)
	 */

	public void rollbackTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		logger.debug("Rollback recharage transaction started.TransactionId:"
				+ rechargeTransaction.getTransactionId());
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRechargeDao(connection);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
		
		long billableAccountId = (rechargeTransaction.getUserSessionContext()
				.getBillableAccId() == 0) ? rechargeTransaction
						.getSourceAccountId() : rechargeTransaction
						.getUserSessionContext().getBillableAccId()	;
		if(TransactionStatus.INVALID != rechargeTransaction.getTransactionStatus()){
			accountDao.updateOperatingBalance(
					billableAccountId,
					rechargeTransaction.getDebitAmount());
		}
			rechargeTransaction.setTransactionStatus(TransactionStatus.FAILURE);
			// Call method to get Available balance before recharge for the
			// Source Account if its a billable account else it fetches for the
			// Billable Account code set in the table
			AccountBalance accountBalance = new AccountBalance();
			accountBalance = accountDao
					.getBalance(billableAccountId);
			// Set the operating balance in the Dto
			rechargeTransaction.setSourceAvailBalAfterRecharge(accountBalance
					.getAvailableBalance());

			if ((rechargeTransaction.getInStatus() != null)
					|| ("".equalsIgnoreCase(rechargeTransaction.getInStatus()))) {
				rechargeTransaction.setInStatus(Utility
						.getInStatusXMLforAvaiBalBeforeRchg(rechargeTransaction
								.getInStatus(), String
								.valueOf(rechargeTransaction
										.getSourceAvailBalAfterRecharge())));
			}
			logger.info("inStatus="+rechargeTransaction.getInStatus());
			
			rechargeDao.updateTransactionStatus(rechargeTransaction
					.getTransactionId(), rechargeTransaction
					.getTransactionStatus(), rechargeTransaction.getReasonId(),
					rechargeTransaction.getUpdatedBy(), rechargeTransaction.getInStatus());
			DBConnectionManager.commitTransaction(connection);
			logger.trace("Rollback transaction completed successfully.");
		} catch (DAOException de) {
			logger.warn("DAOException occured while doing rollback "
					+ "Exception Message: " + de.getMessage());
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_ROLLBACK);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_ROLLBACK);

		} catch(VirtualizationServiceException virtualizationExp){
			logger.warn("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.trace("Executed...");
	}

	/*
	 * (non-Javadoc) commit transaction
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#commitAmount(java.lang.String,
	 *      double)
	 */
	public void commitTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		logger.debug("commit recharge trasaction started.TransactionId:"
				+ rechargeTransaction.getTransactionId());
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			/* dao is an object of type RechargeDaoRdbms */
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			rechargeTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			
			// it contains the code for the SourceAccount if its a billable
			// account else it contains the code for the billable account
			long billableAccountId = (rechargeTransaction.getUserSessionContext()
					.getBillableAccId() == 0) ? rechargeTransaction
					.getSourceAccountId() : rechargeTransaction
					.getUserSessionContext().getBillableAccId();

			accountDao.updateAvailableBalance(billableAccountId,
					-rechargeTransaction.getDebitAmount());

			// Call method to get Available balance After recharge for
			// the Source Account plus Set the Available balance in the
			// Dto
			// rechargeTransaction.setSourceAvailBalAfterRecharge(accountDao
			// .getBalance(rechargeTransaction.getSourceAccountId())
			// .getAvailableBalance());

			rechargeTransaction.setSourceAvailBalAfterRecharge(accountDao
					.getBalance(billableAccountId).getAvailableBalance());
			
			
			
			// Set the Source Opening Available Balance in the INstatus Xml
			if (rechargeTransaction.getInStatus() != null
					|| "".equalsIgnoreCase(rechargeTransaction.getInStatus())) {
				rechargeTransaction.setInStatus(Utility
						.getInStatusXMLforAvaiBalBeforeRchg(rechargeTransaction
								.getInStatus(), String
								.valueOf(rechargeTransaction
										.getSourceAvailBalAfterRecharge())));
			logger.debug(" InStatus XML is : " + rechargeTransaction.getInStatus());	
			}
			rechargeDao.updateTransactionStatus(rechargeTransaction
					.getTransactionId(), rechargeTransaction
					.getTransactionStatus(), rechargeTransaction.getReasonId(),
					rechargeTransaction.getUserSessionContext().getId(), rechargeTransaction.getInStatus());
			DBConnectionManager.commitTransaction(connection);
			logger.trace("commit recharge trasaction  completed successfully");
		}catch(SQLException se) {
			logger.error("DAOException occured while doing commit transaction "
					+ "Exception Message: " + se.getMessage());
			DBConnectionManager.releaseResources(connection);
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_COMMIT);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_COMMIT);
		}
		catch (DAOException de) {
			logger.warn("DAOException occured while doing commit transaction "
					+ "Exception Message: " + de.getMessage());
			DBConnectionManager.releaseResources(connection);
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_COMMIT);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_COMMIT);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.warn("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#doCustomerTransaction(com.ibm.virtualization.recharge.dto.TransactionDTO)
	 */
	public int doCustomerTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		int returnTransStatus = -1;
		try {
			logger.debug(" Customer transaction Started..TransactionId:"
					+ rechargeTransaction.getTransactionId());
			
			/* start transaction */
			returnTransStatus = startTransaction(rechargeTransaction);
			/*
			 * If the request is Duplicate or Blackout
			 */
			if (returnTransStatus == TransactionStatus.DUPLICATE.getValue()
					|| returnTransStatus == TransactionStatus.BLACKOUT
							.getValue()) {
				return returnTransStatus;
			}
			/* request IN for recharge */
			processIN(rechargeTransaction);
			
			rechargeTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			/* commit transaction */
			commitTransaction(rechargeTransaction);
			logger.trace("  Customer transaction completed successfully");
		} catch (VirtualizationServiceException e) {
			rechargeTransaction.setTransactionStatus(TransactionStatus.FAILURE);
			logger.warn("VirtualizationServiceException occoured: "
					+ e.getMessage());
			/* Rollback Transaction */
			rollbackTransaction(rechargeTransaction);
			throw e;
		} finally {
			try {
				insertTransactionLog(rechargeTransaction);
			} catch (DAOException de) {
				logger
						.warn(" DAOException occured while inserting transaction log: "
								+ de.getMessage());
				rechargeTransaction
				.setReasonId(ExceptionCode.ERROR_RECHARGE_DATABASE_ERROR);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_SYSTEM_ERROR, de.getMessage());

			}
		}
		return returnTransStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#getTransactionId()
	 */
	public long getTransactionId() throws VirtualizationServiceException {
		long transactionId;
		logger.trace(" Get transaction started");
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* dao is an object of type RechargeDaoRdbms */
			RechargeDao dao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);
			transactionId = dao.getTransactionId();

			logger.info("Successfully found the next TransactionId:"
					+ transactionId);
			return transactionId;
		} catch (DAOException de) {
			logger.warn(" DAOException occured  Message: " + de.getMessage());
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_GET_TRANSACTION_ID);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.warn("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}

	}

	/*
	 * 
	 * 
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.RechargeService#startTransaction(java.lang.String,
	 *      java.lang.String, double, java.lang.String)
	 */

	public int startTransaction(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		rechargeTransaction.setTransactionStatus(TransactionStatus.PENDING);

		logger.debug("Initiating start transaction for transaction id:"
				+ rechargeTransaction.getTransactionId());
		int returnTransStatus = -1;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* insert into transaction master table */
			returnTransStatus = insertTransactionMaster(connection,
					rechargeTransaction);
			
			// performs validations along with subscriber no validation
			// including TransactionId
			validateDetails(rechargeTransaction);
			
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				logger.warn(" DUPLICATE Transaction : TransactionId :"
						+ rechargeTransaction.getTransactionId());
				return TransactionStatus.DUPLICATE.getValue();
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				logger.warn(" BLACKOUT Transaction : TransactionId :"
						+ rechargeTransaction.getTransactionId());
				return TransactionStatus.BLACKOUT.getValue();
			}

		

			/* computes credit amount and debit amount */
			computeCreditAndDebitAmt(rechargeTransaction, connection);
			/* Updates ACCOUNT_BALANCE */
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			accountDao.checkUpdateOperatingBalance(
					(rechargeTransaction.getUserSessionContext()
							.getBillableAccId() == 0) ? rechargeTransaction
							.getSourceAccountId() : rechargeTransaction
							.getUserSessionContext().getBillableAccId(),
					-rechargeTransaction.getDebitAmount());
			DBConnectionManager.commitTransaction(connection);
			rechargeTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			logger.trace("Successfully completed the Start Transaction");
		} catch (DAOException de) {
			rechargeTransaction.setErrorMessage(de.getMessage());
			rechargeTransaction.setTransactionStatus(TransactionStatus.INVALID);
			logger.warn(" DAOException occured Message: " + de.getMessage());
			if (ExceptionCode.Transaction.ERROR_INSUFFICIENT_BAL
					.equalsIgnoreCase(de.getMessage())) {
				rechargeTransaction
						.setReasonId(ExceptionCode.ERROR_RECHARGE_INSUFFICIENT_BALANCE);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_INSUFFICIENT_BALANCE, de
								.getMessage());
			} else {
				rechargeTransaction
						.setReasonId(ExceptionCode.ERROR_RECHARGE_DATABASE_ERROR);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_DATABASE_ERROR, de.getMessage());
			}
		} finally {
			DBConnectionManager.releaseResources(connection);
			if (TransactionState.WEB != rechargeTransaction
					.getTransactionState()) {
				try {

					insertTransactionLog(rechargeTransaction);

				} catch (DAOException de) {
					logger.error(" DAOException occured  Message: "
							+ de.getMessage());
				}
			}
		}
		return returnTransStatus;
	}

	/**
	 * validates input bean and checks if account can recharge or not including
	 * the TransactionId
	 * 
	 * @param groupId
	 *            int
	 * @param rechargeTransaction
	 *            RechargeTransaction
	 * @throws VirtualizationServiceException
	 */
	private void validateDetails(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {

		/* performs validation for dto excluding the TransactionId */
		validate(rechargeTransaction);

		/* check Transaction for blank or 0 */
		if (rechargeTransaction.getTransactionId() <= 0) {
			logger.warn("TransactionId is Invalid");
			rechargeTransaction.setTransactionStatus(TransactionStatus.INVALID);
			rechargeTransaction
					.setReasonId(ExceptionCode.ERROR_RECHARGE_INVALID_DETAILS);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_INVALID_DETAILS,
					ExceptionCode.Transaction.ERROR_TRANSACTION_ID_REQ);
		}
	}

	/**
	 * validates input bean and checks if account can recharge or not except the
	 * TrnsactionId
	 * 
	 * @param groupId
	 *            int
	 * @param rechargeTransaction
	 *            RechargeTransaction
	 * @throws VirtualizationServiceException
	 */
	private void validate(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		logger.debug("starting validate .TransactionId:"
				+ rechargeTransaction.getTransactionId());

		try {
			/* validating the input dto */
			ValidatorFactory.getCustomerTransactionValidator().validate(
					rechargeTransaction);
		} catch (VirtualizationServiceException vse) {
			logger.warn(" Error occured while validating. Exception :");
			rechargeTransaction.setTransactionStatus(TransactionStatus.INVALID);
			vse.setMessageId(ResponseCode.RECHARGE_INVALID_DETAILS);
			throw vse;
		}

		// Check Whether User is authorized for recharge

		if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
				rechargeTransaction.getUserSessionContext().getGroupId(),
				rechargeTransaction.getTransactionChannel(),
				AuthorizationConstants.ROLE_CAN_RECHARGE)) {
			logger.warn("User not authorized for recharge "
					+ rechargeTransaction.getSourceMobileNo());
			rechargeTransaction
					.setReasonId(ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			throw new VirtualizationServiceException(ResponseCode.AUTHORIZATION_FAILURE_CODE,
					ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
		}
		/* validate Subscriber mobile no */
		try {
			INRouterServiceDTO routerServiceDTO = CacheFactory.getCacheImpl()
					.getDestinationIn(
							rechargeTransaction.getDestinationMobileNo());
			/* set air url */
			rechargeTransaction.setAirUrl(routerServiceDTO.getAirUrl());
			rechargeTransaction.setSubscriberCircleId(routerServiceDTO
					.getSubsCircleId());
			rechargeTransaction.setSubscriberCircleCode(routerServiceDTO
					.getSubscriberCircleCode());
			logger.trace("Successfully validated .");
		} catch (VirtualizationServiceException e) {
			logger.warn("subscriber is invalid");
			rechargeTransaction
					.setReasonId(ExceptionCode.ERROR_RECHARGE_INVALID_SUBSCRIBER_NO);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_INVALID_SUBSCRIBER_NO,
					ExceptionCode.Transaction.ERROR_TRANSACTION_INVALID_SUBSCRIBER);

		}

	}

	/**
	 * Calculates credit Ammount and Debit Amount
	 * 
	 * @param rechargeTransaction
	 * @param connection
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 * @throws DAOException 
	 */
	private void computeCreditAndDebitAmt(
			RechargeTransaction rechargeTransaction, Connection connection)
			throws VirtualizationServiceException, DAOException {
		logger.trace("Calculating credit and debit amount");
	

		SysConfig sysConfig = CacheFactory.getCacheImpl().getSystemConfig(
				rechargeTransaction.getSubscriberCircleId(),
				rechargeTransaction.getTransactionType(),
				rechargeTransaction.getTransactionChannel());

		if (sysConfig == null) {

			logger
					.warn("No Record Found for SYSTEM_CONFIG details circle id:= "
							+ rechargeTransaction.getSubscriberCircleId()
							+ "transaction type:"
							+ rechargeTransaction.getTransactionType()
							+ "channel type:"
							+ rechargeTransaction.getTransactionChannel());
			rechargeTransaction
					.setReasonId(ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_DATABASE_ERROR,
					ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_FETCH);
		}

		/* validating system config */
		if (rechargeTransaction.getTransactionAmount() < sysConfig
				.getMinAmount()
				|| rechargeTransaction.getTransactionAmount() > sysConfig
						.getMaxAmount()) {
			logger.warn("Transaction amount:"
					+ rechargeTransaction.getTransactionAmount()

					+ " should be between MinAmount:"
					+ sysConfig.getMinAmount() + " and MaxAmount:"
					+ sysConfig.getMaxAmount());
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_AMT_OUT_RANGE);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_INVALID_AMT,
					ExceptionCode.Transaction.ERROR_AMT_OUT_RANGE);
		}
		AccountDao accountDao = DAOFactory.getDAOFactory(
				Integer.parseInt(ResourceReader.
						getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getAccountDao(connection);
		
	long commmissionId=	accountDao.getCommissionByAccountId(rechargeTransaction.getSourceAccountId());
		/* get cricle top up config (slab) details for subscriber circle*/
		CircleTopupConfig destinationCircleTopupConfig = CacheFactory.getCacheImpl()
				.getTopupConfig(rechargeTransaction.getSubscriberCircleId(),
						rechargeTransaction.getTransactionType(),
						rechargeTransaction.getTransactionAmount(),commmissionId);
		if (destinationCircleTopupConfig == null) {
			logger
					.warn("No Record Found for circle top up config.subscriber circle id = "
							+ rechargeTransaction.getSubscriberCircleId()
							+ "transaction type="
							+ rechargeTransaction.getTransactionType().name()
							+ "transaction amount="
							+ rechargeTransaction.getTransactionAmount());
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_SYSTEM_ERROR,
					ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
		}
		/* set card group */
		logger
				.info("sucessfully fetched circle topup config details.service tax:"
						+ destinationCircleTopupConfig.getServiceTax()
						+ "processing fee:"
						+ destinationCircleTopupConfig.getProcessingFee()
						
						+ " for circle id:"
						+ destinationCircleTopupConfig.getCircleId()
						+ " for validity :"
						+ destinationCircleTopupConfig.getValidity());
		rechargeTransaction.setCardGroup(destinationCircleTopupConfig.getInCardGroup());
		/* Get system configruation details */

		/* Calculate the Amount to be credited to the Customer's */
		double creditedAmount = (((rechargeTransaction.getTransactionAmount() / (100 + destinationCircleTopupConfig
				.getServiceTax())) * 100) - destinationCircleTopupConfig
				.getProcessingFee());
		
		creditedAmount = Utility.roundDouble(creditedAmount, 2);
		/* credit amount validation */
		if (creditedAmount < 0) {
			logger.warn("Credit amount is negative");
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_INVALID_AMT,
					ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
		}

		
		
		/* get cricle top up config (slab) details for retailer (Source) circle*/
		CircleTopupConfig sourceCircleTopupConfig = CacheFactory.getCacheImpl()
				.getTopupConfig(rechargeTransaction.getCircleId(),
						rechargeTransaction.getTransactionType(),
						rechargeTransaction.getTransactionAmount(),0);
		if (sourceCircleTopupConfig == null) {
			logger
					.warn("No Record Found for circle top up config.subscriber circle id = "
							+ rechargeTransaction.getSubscriberCircleId()
							+ "transaction type="
							+ rechargeTransaction.getTransactionType().name()
							+ "transaction amount="
							+ rechargeTransaction.getTransactionAmount());
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
			throw new VirtualizationServiceException(
					ResponseCode.RECHARGE_SYSTEM_ERROR,
					ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
		}
		/* set card group */
		logger
				.info("sucessfully fetched circle topup config details. "
					
						+ "esp commmision :"
						+ sourceCircleTopupConfig.getEspCommission()
						+ "circle id:"
						+ sourceCircleTopupConfig.getCircleId());
		//rechargeTransaction.setCardGroup(sourceCircleTopupConfig.getInCardGroup());
		/* Computes Debit Amount then the Transaction Amount */
		double debitAmt = (rechargeTransaction.getTransactionAmount())
				- ((sourceCircleTopupConfig.getEspCommission() / 100) * rechargeTransaction
						.getTransactionAmount());
		/* sets values to return */
		debitAmt = Utility.roundDouble(debitAmt, 2);
		rechargeTransaction.setServiceTax((destinationCircleTopupConfig.getServiceTax()));
		rechargeTransaction.setProcessingFee(destinationCircleTopupConfig
				.getProcessingFee());
		rechargeTransaction.setEspCommission(sourceCircleTopupConfig
				.getEspCommission());
		rechargeTransaction.setCreditedAmount(creditedAmount);
		rechargeTransaction.setDebitAmount(debitAmt);
		logger.debug(" ServiceTax :" + rechargeTransaction.getServiceTax()
				+ " EspCommsion :" + rechargeTransaction.getEspCommission()
				+ " Credit Amount :" + rechargeTransaction.getCreditedAmount()
				+ " Debit Amount :" + rechargeTransaction.getDebitAmount());
		
	}

	/**
	 * send request to in for recharge and return updated subscriber balance
	 * 
	 * @param rechargeTransaction
	 * @return double
	 * @throws VirtualizationServiceException
	 */
	private void processIN(RechargeTransaction rechargeTransaction)
			throws VirtualizationServiceException {
		/**
		 * Request DTO which will be populated with all the mandatory
		 * information required by the AIR Adapter to process the request.
		 */
		logger.debug("Starting IN process.TransactionId:"
				+ rechargeTransaction.getTransactionId());
		double balanceAfterRecharge = 0;
		//RefillRequestDTO refillReqDTO = new RefillRequestDTO();
		SuperRefillRequestDTO refillReqDTO = new SuperRefillRequestDTO();
		refillReqDTO.setSubscriberNumber("91"
				+ rechargeTransaction.getDestinationMobileNo());// 919987047379
		logger.debug("subscriber mobile no:"
				+ refillReqDTO.getSubscriberNumber());
		/* Set AirURL */
		refillReqDTO.setAirURL(rechargeTransaction.getAirUrl());
		/* Set Transaction ID */
		refillReqDTO.setOriginTransactionID(String.valueOf(rechargeTransaction
				.getTransactionId()));

		/* converting rupees to paise */
//---------------------- Check this value --------------------------------
		
		//Double tempCreditValue = (rechargeTransaction.getCreditedAmount() * 100);
		
		
		refillReqDTO.setTransactionAmountRefill(String.valueOf(rechargeTransaction.getCreditedAmount()).replace(".", ""));
		/* set card group */
		refillReqDTO.setPaymentProfileID(rechargeTransaction.getCardGroup());
		/* set the TrnsactionId */
		refillReqDTO.setOriginTransactionID(String.valueOf(rechargeTransaction.getTransactionId()));
		/* set the requesterMobile No*/
		refillReqDTO.setExternalData1(rechargeTransaction.getSourceMobileNo());
		/* set the requesterAddress */
		refillReqDTO.setExternalData2(rechargeTransaction.getUserSessionContext().getAccountAddress());
		INAdapterService airAdapter = new AIRAdapter();
		//RefillResponseDTO refillRespDTO = new RefillResponseDTO();
		SuperRefillResponseDTO refillRespDTO = new SuperRefillResponseDTO();
		/* invoke the RefillTDetails service method */
		//refillRespDTO = airAdapter.getRefillTDetails(refillReqDTO);
		refillRespDTO = airAdapter.getSuperRefillTDetails(refillReqDTO);
		logger.info(" Balance After Recharge :"
				+ refillRespDTO.getAccountValueAfter1() + "Status :"
				+ refillRespDTO.getStatus() + "After Recharge Total :"
				+ refillRespDTO.getRechargeAmount1MainTotal() + "Error Code :"
				+ refillRespDTO.getErrorCode() + "Error Message :"
				+ refillRespDTO.getErrorMessage());
		
		rechargeTransaction.setResponseCode(refillRespDTO.getErrorCode());
		if (refillRespDTO.getStatus().equalsIgnoreCase(
				AIRAdapterConstants.SUCCESS)) {
			String balAfterRefill = refillRespDTO.getAccountValueAfter1();
			if (balAfterRefill != null) {
				balanceAfterRecharge = (new Double(balAfterRefill)
						.doubleValue()) / 100;
				rechargeTransaction.setBalanceAfterRecharge(balanceAfterRecharge);
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(ResourceReader.getCoreResourceBundleValue(
								Constants.IN_VALIDITY_DATE_PATTERN));
				Date date = sdf.parse(refillRespDTO.getServiceFeeDateAfter());
				sdf.applyPattern(ResourceReader.getCoreResourceBundleValue(
								Constants.VALIDITY_DATE_PATTERN));
				rechargeTransaction.setValidity(sdf.format(date));
				rechargeTransaction.setInStatus(Utility.getInStatusXML(refillRespDTO
						.getErrorCode(), rechargeTransaction.getValidity(),null));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logger.warn("date pattern parsing error");
			}
			logger
					.info("IN process completed successfully with balance after recharge:"
							+ balanceAfterRecharge);
			
		} else {
			logger.error("error while getting response from IN : "
					+ refillRespDTO.getErrorCode() + " - "
					+ refillRespDTO.getErrorMessage());
			rechargeTransaction
					.setErrorMessage(refillRespDTO.getErrorMessage());
			
			rechargeTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_IN);
			rechargeTransaction.setInStatus(Utility.getInStatusXML(refillRespDTO
					.getErrorCode(), rechargeTransaction.getValidity(),null));
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_TRANSACTION_IN);
		}
		
		

	}
	
	
	
}