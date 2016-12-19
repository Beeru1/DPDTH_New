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

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.TransactionStatus;
//import com.ibm.virtualization.postbillpaymentadapter.beans.PostBillPaymentRequestDTO;
//import com.ibm.virtualization.postbillpaymentadapter.beans.PostBillPaymentResponseDTO;
//import com.ibm.virtualization.postbillpaymentadapter.common.PostpaidConstants;
//import com.ibm.virtualization.postbillpaymentadapter.service.PostBillPaymentService;
//import com.ibm.virtualization.postbillpaymentadapter.service.impl.PostBillPaymentImpl;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ResponseCode;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LogTransactionDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.ActivityLog;
import com.ibm.virtualization.recharge.dto.CircleTopupConfig;
import com.ibm.virtualization.recharge.dto.INRouterServiceDTO;
import com.ibm.virtualization.recharge.dto.PostpaidTransaction;
import com.ibm.virtualization.recharge.dto.SysConfig;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.PostPaidService;
/**
 * This class provides implementation for post paid functionalities.
 * 
 * @author Sushil
 * 
 */
//public class PostpaidServiceImpl implements PostPaidService {
public class PostpaidServiceImpl {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(PostpaidServiceImpl.class
			.getName());

	/**
	 * This method inserts input details into transaction master table.
	 * 
	 * @param connection
	 *            Connection
	 * @param dto
	 *            PostpaidTransaction
	 * @return returnTransStatus
	 * @throws DAOException
	 */
	private int insertTransactionMaster(Connection connection,
			PostpaidTransaction dto) throws DAOException {
		logger.info("Started...Postpaid TransctionId: "
				+ dto.getTransactionId());
		int returnTransStatus = -1;
		/* insert into customer transaction table */
		RechargeDao rechargeDao;
		try{
			rechargeDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.
					getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getRechargeDao(connection);
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}
		
		TransactionMaster transactionMaster = new TransactionMaster();
		transactionMaster.setCreatedBy(dto.getUserSessionContext().getId());
		transactionMaster.setUpdatedBy(dto.getUserSessionContext().getId());
		transactionMaster.setCreatedDate(Utility.getDateTime());
		transactionMaster.setDestinationId(dto.getDestinationNo());
		transactionMaster.setSourceId(dto.getSourceAccountId());
		transactionMaster.setTransactionAmount(dto.getTransactionAmount());
		transactionMaster.setTransactionChannel(dto.getTransactionChannel());
		transactionMaster.setTransactionDate(Utility.getDateTime());
		StringBuffer xml=new StringBuffer();
		xml.append("<PostPaidTransaction>")
		.append("<CellId>").append(dto.getCellId()).append("</CellId>")
		.append("<BillableAccId>").append(dto.getUserSessionContext().getBillableAccId()).append("</BillableAccId>")
			.append("<ConfirmationMobileNo>").append(dto.getConfirmationMobileNo()).append("</ConfirmationMobileNo>")
			.append("<HostAddress>").append(dto.getMessage()).append("</HostAddress>")
		.append("</PostPaidTransaction>");
		transactionMaster.setTransactionMessage(xml.toString());
		transactionMaster.setTransactionStatus(TransactionStatus.PENDING);
		transactionMaster.setRequestType(dto.getRequestType());
		transactionMaster.setTransactoinId(dto.getTransactionId());
		transactionMaster.setRequestTime(dto.getRequestTime());
		transactionMaster.setPhysicalId(dto.getPhysicalId());

		returnTransStatus = rechargeDao
				.insertTransactionMasterPreProcess(transactionMaster);

		logger.info("Successfully Inserted: returnTransStatus "+returnTransStatus);
		return returnTransStatus;
	}

	/**
	 * insert into V_TRANS_DETAIL table
	 * 
	 * @param transactionId
	 *            long
	 * @param state
	 *            TransactionState
	 * @param detailMessage
	 *            String
	 * @param createdBy
	 *            long
	 */
	private void insertTransactionLog(PostpaidTransaction postpaidTransaction)
			throws DAOException {
		logger.info("Starting insertion into  transaction detail.createdBy  :"
				+ postpaidTransaction.getUserSessionContext().getId()
				+ postpaidTransaction.toString());
		Connection connection = null;
		try {

			connection = DBConnectionManager.getDBConnection();
			LogTransactionDao logTransactionDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLogTransactionDao(connection);
			ActivityLog activityLog = new ActivityLog();
			
			activityLog.setMessage(postpaidTransaction.toString());
			activityLog
					.setTransactionId(postpaidTransaction.getTransactionId());
			activityLog.setTransactionState(postpaidTransaction
					.getTransactionState());
			activityLog.setRequestTime(postpaidTransaction.getRequestTime());
			activityLog.setStatus(postpaidTransaction.getTransactionStatus());
			activityLog.setCreatedBy(postpaidTransaction
					.getUserSessionContext().getId());
			activityLog.setCreatedDate(Utility.getDateTime());
			logTransactionDao.insertLog(activityLog);
			logger.info("successfully inserted post paid transaction details");
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			throw new DAOException(virtualizationExp.getMessage());
		}finally {

			DBConnectionManager.releaseResources(connection);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.PostPaidService#rollbackPostpaidTransaction(com.ibm.virtualization.recharge.dto.PostpaidTransaction)
	 */
	public void rollbackPostpaidTransaction(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		logger.info("Rollback post paid transaction started.Transaction id:"
				+ postpaidTransaction.getTransactionId());
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.
					getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			if (TransactionStatus.INVALID != postpaidTransaction
					.getTransactionStatus()) {
				accountDao.updateOperatingBalance(
						(postpaidTransaction.getUserSessionContext()
								.getBillableAccId() == 0) ? postpaidTransaction
								.getSourceAccountId() : postpaidTransaction
								.getUserSessionContext().getBillableAccId(),
						postpaidTransaction.getDebitAmount());
			}
			postpaidTransaction.setTransactionStatus(TransactionStatus.FAILURE);
			rechargeDao.updateTransactionStatus(postpaidTransaction
					.getTransactionId(), postpaidTransaction
					.getTransactionStatus(), postpaidTransaction.getReasonId(),
					postpaidTransaction.getUpdatedBy(), postpaidTransaction.getSelfCareStatus());
			DBConnectionManager.commitTransaction(connection);
			logger
					.info("Rollback post paid transaction completed successfully.");
		} catch (DAOException de) {
			logger
					.fatal("DAOException occured while doing rollback post paid Transaction"
							+ "Exception Message: " + de.getMessage());
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_ROLLBACK);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_ROLLBACK);

		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			postpaidTransaction
			.setReasonId(ExceptionCode.Transaction.ERROR_ROLLBACK);
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.PostPaidService#commitPostpaidTransaction(com.ibm.virtualization.recharge.dto.PostpaidTransaction)
	 */
	public void commitPostpaidTransactions(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		logger.info("commit post paid trasaction started.Transaction id:"
				+ postpaidTransaction.getTransactionId());
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* dao is an object of type RechargeDaoRdbms */
			RechargeDao rechargeDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getRechargeDao(connection);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			postpaidTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			accountDao.updateAvailableBalance(
					(postpaidTransaction.getUserSessionContext()
							.getBillableAccId() == 0) ? postpaidTransaction
							.getSourceAccountId() : postpaidTransaction
							.getUserSessionContext().getBillableAccId(),
					-postpaidTransaction.getDebitAmount());
			rechargeDao.updateTransactionStatus(postpaidTransaction
					.getTransactionId(), postpaidTransaction
					.getTransactionStatus(), postpaidTransaction.getReasonId(),
					postpaidTransaction.getUpdatedBy(),postpaidTransaction.getSelfCareStatus());
			DBConnectionManager.commitTransaction(connection);
			logger.info("commit post paid trasaction  completed successfully");
		} catch (DAOException de) {
			logger
					.fatal("DAOException occured while commiting post paid transaction "
							+ "Exception Message: " + de.getMessage());
			DBConnectionManager.releaseResources(connection);
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_COMMIT);
			throw new VirtualizationServiceException(
					ExceptionCode.Transaction.ERROR_COMMIT);
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			postpaidTransaction
			.setReasonId(ExceptionCode.Transaction.ERROR_COMMIT);
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.PostPaidService#doPostpaidTransaction(com.ibm.virtualization.recharge.dto.UserSessionContext,
	 *      com.ibm.virtualization.recharge.dto.PostpaidTransaction)
	 */
	public int doPostpaidTransaction(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		int returnTransStatus = -1;
		try {
			logger.info(" do post paid transaction Started.Transaction id:"
					+ postpaidTransaction.getTransactionId());
			/* start transaction */
			returnTransStatus = startPostpaidTransaction(postpaidTransaction);
			/*
			 * If the request is Duplicate or Blackout
			 */
			if (returnTransStatus == TransactionStatus.DUPLICATE.getValue()
					|| returnTransStatus == TransactionStatus.BLACKOUT
							.getValue()) {
				return returnTransStatus;
			}
			/* request selfcare for processing */
			//processSelfCare(postpaidTransaction);
			/* commit transaction */
			commitPostpaidTransactions(postpaidTransaction);
			logger.info(" do post paid transaction completed successfully");
		} catch (VirtualizationServiceException e) {
			logger.error("VirtualizationServiceException occoured: "
					,e);
			/* Rollback Transaction */
			rollbackPostpaidTransaction(postpaidTransaction);
			throw e;
		} catch (Throwable e) {
			logger.error("Exception occoured: " ,e);
			/* Rollback Transaction */
			rollbackPostpaidTransaction(postpaidTransaction);
			throw new VirtualizationServiceException(e);
		} finally {
			try {
				insertTransactionLog(postpaidTransaction);
			} catch (DAOException de) {
				logger
						.fatal(" DAOException occured while inserting transaction log: "
								+ de.getMessage());
				throw new VirtualizationServiceException(
						ResponseCode.POSTPAID_SYSTEM_ERROR, de.getMessage());
			}
		}
		return returnTransStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.PostPaidService#startPostpaidTransaction(com.ibm.virtualization.recharge.dto.UserSessionContext,
	 *      com.ibm.virtualization.recharge.dto.PostpaidTransaction)
	 */
	public int startPostpaidTransaction(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		postpaidTransaction.setTransactionStatus(TransactionStatus.PENDING);
		logger.info("Initiating start post paid transaction. transaction id:"
				+ postpaidTransaction.getTransactionId());
		Connection connection = null;
		int returnTransStatus = -1;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/* insert into transaction master table */
			returnTransStatus = insertTransactionMaster(connection,
					postpaidTransaction);
//			 performs validations along with subscriber no validation
			validate(postpaidTransaction);
			
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				postpaidTransaction.setTransactionStatus(TransactionStatus.DUPLICATE);
				return TransactionStatus.DUPLICATE.getValue();
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				postpaidTransaction.setTransactionStatus(TransactionStatus.BLACKOUT);
				return TransactionStatus.BLACKOUT.getValue();
			}
			

			/* validate TransactionId to be not null */
			try {
				if (postpaidTransaction.getTransactionId() <= 0) {
					logger.error("TransactionId is Invalid");
					postpaidTransaction
							.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_ID_REQ);
					throw new VirtualizationServiceException(
							ExceptionCode.Transaction.ERROR_TRANSACTION_ID_REQ);
				}
			} catch (VirtualizationServiceException vse) {
				postpaidTransaction
						.setTransactionStatus(TransactionStatus.INVALID);
				vse.setMessageId(ResponseCode.POSTPAID_INVALID_DETAILS);
				throw vse;
			}

			/* computes credit amount and debit amount */
			computeCreditAnddebitAmount(postpaidTransaction, connection);
			/* Updates ACCOUNT_BALANCE */
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			long billableAccountId=	(postpaidTransaction.getUserSessionContext()
					.getBillableAccId() == 0) ? postpaidTransaction
					.getSourceAccountId()
					: postpaidTransaction.getUserSessionContext()
							.getBillableAccId();
			accountDao.checkUpdateOperatingBalance(billableAccountId,
					-postpaidTransaction.getDebitAmount());
			postpaidTransaction.setSourceAvailBalAfterTrans(accountDao
					.getBalance(billableAccountId).getAvailableBalance());
			DBConnectionManager.commitTransaction(connection);
			postpaidTransaction.setTransactionStatus(TransactionStatus.SUCCESS);
			logger.info("Successfully completed the Start Transaction");
		} catch (DAOException de) {
			postpaidTransaction.setErrorMessage(de.getMessage());
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			logger.warn(" DAOException occured Message: " + de.getMessage());
			if (ExceptionCode.Transaction.ERROR_INSUFFICIENT_BAL
					.equalsIgnoreCase(de.getMessage())) {
				postpaidTransaction
						.setReasonId(ExceptionCode.ERROR_RECHARGE_INSUFFICIENT_BALANCE);
				throw new VirtualizationServiceException(
						ResponseCode.POSTPAID_INSUFFICIENT_BALANCE, de
								.getMessage());
			} else {
				postpaidTransaction
						.setReasonId(ExceptionCode.ERROR_RECHARGE_DATABASE_ERROR);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_DATABASE_ERROR, de.getMessage());
			}
			
			
			/*logger.fatal(" DAOException occured Message: " + de.getMessage());
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_SYSTEM_ERROR, de.getMessage());*/
		}catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			postpaidTransaction.setErrorMessage(virtualizationExp.getMessage());
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
			if (TransactionState.WEB != postpaidTransaction
					.getTransactionState()) {
				try {
					insertTransactionLog(postpaidTransaction);
				} catch (DAOException de) {
					logger.fatal(" DAOException occured  Message: "
							+ de.getMessage());
				}
			}
		}
		return returnTransStatus;
	}

	/**
	 * validates input bean and checks if account can do post paid payment or
	 * not
	 * 
	 * @param groupId
	 *            int
	 * @param PostpaidTransaction
	 *            postpaidTransaction
	 * @throws VirtualizationServiceException
	 */
	private void validate(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		logger.info("starting validate ");
		try {
			/* validating the input dto */
			ValidatorFactory.getPostpaidTransactionValidator().validate(
					postpaidTransaction);
		} catch (VirtualizationServiceException vse) {
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			vse.setMessageId(ResponseCode.POSTPAID_INVALID_DETAILS);
			throw vse;
		}
		// Check Whether User is authorized for post paid payment
		if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
				postpaidTransaction.getUserSessionContext().getGroupId(),
				postpaidTransaction.getTransactionChannel(),
				AuthorizationConstants.ROLE_POSTPAID_PAYMENT)) {
			postpaidTransaction
					.setReasonId(ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			logger.error("User not authorized for post paid transaction "
					+ postpaidTransaction.getSourceMobileNo());
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			throw new VirtualizationServiceException(ResponseCode.AUTHORIZATION_FAILURE_CODE,
					ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
		}
		/* validate Subscriber mobile no */
		try {
			INRouterServiceDTO routerServiceDTO = CacheFactory.getCacheImpl()
					.getDestinationIn(postpaidTransaction.getDestinationNo());
			postpaidTransaction.setSubscriberCircleId(routerServiceDTO
					.getSubsCircleId());
			
			if (TransactionType.POSTPAID_ABTS == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setSubscriberCircleCode(routerServiceDTO
						.getWireLineCode());
			} else if (TransactionType.POSTPAID_MOBILE == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setSubscriberCircleCode(routerServiceDTO
						.getWireLessCode());
			} else if (TransactionType.POSTPAID_DTH == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setSubscriberCircleCode(routerServiceDTO.getDthCode());
				
			}  else {
				logger.info("Invalid transaction type:"
						+ postpaidTransaction.getTransactionType().name());
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.TRANSACTION_INVALID_TRANSACTION_TYPE);
			}
			postpaidTransaction
					.setServiceAddressLocation(ResourceReader
							.getpostPaidResourceBundleValue("PostBillPayment.ServiceAddressLocation"));
//			postpaidTransaction.setServiceAddressLocation(routerServiceDTO.getAirUrl());
			logger.info("Successfully validated post paid  subscriber no .");
		} catch (VirtualizationServiceException e) {
			logger.error("subscriber is invalid");
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_INVALID);
			//		.setReasonId(ExceptionCode.Transaction.ERROR_TRANSACTION_INVALID_SUBSCRIBER);
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_INVALID_RECEIVING_MOBILENO,
					ExceptionCode.Transaction.ERROR_RECEIVING_MOBILENO_INVALID);
					//ResponseCode.POSTPAID_INVALID_SUBSCRIBER_NO,
					//ExceptionCode.Transaction.ERROR_TRANSACTION_INVALID_SUBSCRIBER);
					
		}

	}

	/**
	 * Calculates credit Ammount and Debit Amount
	 * 
	 * @param PostpaidTransaction
	 * @param connection
	 * @throws DAOException
	 * @throws VirtualizationServiceException
	 */
	private void computeCreditAnddebitAmount(
			PostpaidTransaction postpaidTransaction, Connection connection)
			throws DAOException, VirtualizationServiceException {
		logger.info("Calculating credit and debit amount");

		CircleTopupConfig circleTopupConfig = null;
		SysConfig sysConfig = null;
		/* get cricle top up config details */
		AccountDao accountDao = DAOFactory.getDAOFactory(
				Integer.parseInt(ResourceReader.
						getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
				.getAccountDao(connection);
		
	long commmissionId=	accountDao.getCommissionByAccountId(postpaidTransaction.getSourceAccountId());
			circleTopupConfig = CacheFactory.getCacheImpl()
			.getTopupConfig(postpaidTransaction.getSubscriberCircleId(),
					postpaidTransaction.getTransactionType(),
					postpaidTransaction.getTransactionAmount(),commmissionId);
			
			if (circleTopupConfig == null) {
				postpaidTransaction
						.setErrorMessage(ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
				postpaidTransaction
						.setTransactionStatus(TransactionStatus.INVALID);
				logger
						.error("No Record Found for circle top up config.requester circle id = "
								+ postpaidTransaction.getCircleId()
								+ "transaction type="
								+ postpaidTransaction.getTransactionType()
										.name()
								+ "transaction amount="
								+ postpaidTransaction.getTransactionAmount());
				postpaidTransaction
						.setReasonId(ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_DATABASE_ERROR,
						ExceptionCode.Transaction.ERROR_CIRCLE_TOPUP_DETAIL_FIND);
			}
			/* set card group */
			logger
					.info("sucessfully fetched circle topup config details.service tax:"
							+ circleTopupConfig.getServiceTax()
							+ "esp commmision :"
							+ circleTopupConfig.getEspCommission()
							+ "circle id:" + circleTopupConfig.getCircleId());
			/* Get system configruation details */
			
			 sysConfig = CacheFactory.getCacheImpl().getSystemConfig(
					postpaidTransaction.getSubscriberCircleId(),
					postpaidTransaction.getTransactionType(),
					postpaidTransaction.getTransactionChannel());

			if (sysConfig == null) {
				postpaidTransaction
						.setErrorMessage(ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
				postpaidTransaction
						.setTransactionStatus(TransactionStatus.INVALID);
				logger
						.error("No Record Found for SYSTEM_CONFIG details circle id:= "
								+ postpaidTransaction.getSubscriberCircleId()
								+ "transaction type:"
								+ postpaidTransaction.getTransactionType()
								+ "channel type:"
								+ postpaidTransaction.getTransactionChannel());
				postpaidTransaction
						.setReasonId(ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
				throw new VirtualizationServiceException(
						ResponseCode.RECHARGE_DATABASE_ERROR,
						ExceptionCode.SystemConfig.ERROR_SYS_CONFIG_DATA_REQ);
			}
			logger
					.info("sucessfully fetched system config details.Max rechare value:"
							+ sysConfig.getMaxAmount()
							+ "Min post paid value:"
							+ sysConfig.getMinAmount());
		
		/* Calculate the Amount to be credited to the Customer's */
		double creditedAmount = postpaidTransaction.getTransactionAmount();
		creditedAmount = Utility.roundDouble(creditedAmount, 2);
		/* credit amount validation */
		if (creditedAmount < 0) {
			logger.error("Credit amount is negative");
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_INVALID_AMT,
					ExceptionCode.Transaction.ERROR_CREDIT_AMT_NAGETIVE);
		}
		
		/* validating configuration slab */
		if (creditedAmount < sysConfig.getMinAmount()
				|| creditedAmount > sysConfig.getMaxAmount()) {
			logger.error("Credit amount:" + creditedAmount
					+ " should be between MinAmount:"
					+ sysConfig.getMinAmount() + " and MaxAmount:"
					+ sysConfig.getMaxAmount());
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_AMT_OUT_RANGE);
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_INVALID_AMT,
					ExceptionCode.Transaction.ERROR_AMT_OUT_RANGE);
		}
		/* Computes Debit Amount then the Transaction Amount */
		double debitAmt = (postpaidTransaction.getTransactionAmount())
				- ((circleTopupConfig.getEspCommission() / 100) * postpaidTransaction
						.getTransactionAmount());
		/* sets values to return */
		postpaidTransaction.setEspCommission(circleTopupConfig
				.getEspCommission());
	
		postpaidTransaction.setCreditedAmount(creditedAmount);
		debitAmt = Utility.roundDouble(debitAmt, 2);
		postpaidTransaction.setDebitAmount(debitAmt);
		logger.info("Successfully calculated credit and debit amount");
	}

	/**
	 * send request to in for postpaid and return updated subscriber balance
	 * 
	 * @param postpaidTransaction
	 * @return double
	 * @throws VirtualizationServiceException
	 */
	/*
	public double processSelfCare(PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {

		logger.info("Starting selfcare processtransaction id:"
				+ postpaidTransaction.getTransactionId());
		PostBillPaymentService adapter = null;
		PostBillPaymentRequestDTO reqDTO = null;
		try {
			adapter = new PostBillPaymentImpl();
			reqDTO = new PostBillPaymentRequestDTO();
			reqDTO.setSubscriberNo(postpaidTransaction.getDestinationNo());
			reqDTO.setTransAmount((float) postpaidTransaction.getCreditedAmount());
			reqDTO.setTransactionID(String.valueOf(postpaidTransaction
					.getTransactionId()));
			reqDTO.setExternalID(postpaidTransaction.getDestinationNo());
			reqDTO.setChannelType(postpaidTransaction.getTransactionChannel()
					.name());
			reqDTO.setServiceAddressLocation(postpaidTransaction.getServiceAddressLocation());
			reqDTO.setTransType(postpaidTransaction.getTransactionType().name()); 
			reqDTO.setBillRefNo(String.valueOf(postpaidTransaction.getSubscriberCircleCode()));
			if (TransactionType.POSTPAID_ABTS == postpaidTransaction
					.getTransactionType()) {
				reqDTO.setExternalIDType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_ABTS_EXTERNAL_ID));
				reqDTO.setCustomerType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_ABTS_CUSTOMER_TYPE));
			} else if (TransactionType.POSTPAID_MOBILE == postpaidTransaction
					.getTransactionType()) {
				reqDTO.setExternalIDType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_MOBILE_EXTERNAL_ID));
				reqDTO.setCustomerType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_MOBILE_CUSTOMER_TYPE));
			} else if (TransactionType.POSTPAID_DTH == postpaidTransaction
					.getTransactionType()) {
				reqDTO.setExternalIDType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_DTH_EXTERNAL_ID_TYPE));
				reqDTO.setCustomerType(ResourceReader.getpostPaidResourceBundleValue(PostpaidConstants.POSTPAID_DTH_CUSTOMER_TYPE));
				
			}  else {
				logger.info("Invalid transaction type:"
						+ postpaidTransaction.getTransactionType().name());
				throw new VirtualizationServiceException(
						ExceptionCode.Transaction.TRANSACTION_INVALID_TRANSACTION_TYPE);
			}
			logger.info("in service before calling service");
			reqDTO.setRequestType(reqDTO.getCustomerType());			
		} catch (Throwable e) {
			logger.error("error12", e);
			// TODO: handle exception
		}

		PostBillPaymentResponseDTO responseDTO = adapter
				.getPostBillPaymentDetails(reqDTO);
		logger.info("Error Code:" + responseDTO.getErrorCode());
		logger.info("Error Message:" + responseDTO.getErrorMessage());
		logger.info("Error trace:" + responseDTO.getErrorTrace());
		logger.info("Error Serverity:" + responseDTO.getSeverity());
		logger.info("Transaction Amount:" + responseDTO.getTransAmount());
		logger.info("Status Code:" + responseDTO.getOperationStatusCode());
		if (responseDTO.getOperationStatusCode() != 0) {
			logger.info("Exception occured at SelfCare :"
					+ responseDTO.getErrorCode());
			postpaidTransaction.setTransactionStatus(TransactionStatus.FAILURE);
			postpaidTransaction.setSelfCareStatus(Utility.getSelfcareXML(
					responseDTO.getErrorCode(), String
							.valueOf(postpaidTransaction
									.getSourceAvailBalAfterTrans())));
			postpaidTransaction
					.setReasonId(ExceptionCode.Transaction.ERROR_POSTPAID_SELFCARE);
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_SYSTEM_ERROR,
					ExceptionCode.Transaction.ERROR_POSTPAID_SELFCARE);
		} else {
			postpaidTransaction.setSelfCareStatus(Utility.getSelfcareXML(
					responseDTO.getErrorCode(), String
							.valueOf(postpaidTransaction
									.getSourceAvailBalAfterTrans())));
			if (TransactionType.POSTPAID_ABTS == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setResponseCode(ResponseCode.POSTPAID_SUCCESS_ABTS);
			} else if (TransactionType.POSTPAID_MOBILE == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setResponseCode(ResponseCode.POSTPAID_SUCCESS);
			} else if (TransactionType.POSTPAID_DTH == postpaidTransaction
					.getTransactionType()) {
				postpaidTransaction.setResponseCode(ResponseCode.POSTPAID_SUCCESS_DTH);
				
			}
			logger.info("SelfCare executed successfully. TransactionAmount : "
					+ responseDTO.getTransAmount());
		}
		
		return responseDTO.getTransAmount();
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.PostPaidService#calculateESPforConfirmation(com.ibm.virtualization.recharge.dto.PostpaidTransaction)
	 */
	
	public void calculateESPforConfirmation(
			PostpaidTransaction postpaidTransaction)
			throws VirtualizationServiceException {
		postpaidTransaction.setTransactionStatus(TransactionStatus.PENDING);
		logger.info("Initiating calculateESPforConfirmation");
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			// performs validations along with subscriber no validation
			validate(postpaidTransaction);
			/* computes credit amount and debit amount */
			computeCreditAnddebitAmount(postpaidTransaction, connection);
			logger
					.info("Successfully completed the calculateESPforConfirmation");
		} catch (DAOException de) {
			postpaidTransaction.setErrorMessage(de.getMessage());
			postpaidTransaction.setTransactionStatus(TransactionStatus.INVALID);
			logger.fatal(" DAOException occured Message: " + de.getMessage());
			throw new VirtualizationServiceException(
					ResponseCode.POSTPAID_SYSTEM_ERROR, de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
			if (TransactionState.WEB != postpaidTransaction
					.getTransactionState()) {
				try {

					insertTransactionLog(postpaidTransaction);

				} catch (DAOException de) {
					logger.fatal(" DAOException occured  Message: "
							+ de.getMessage());
				}
			}
		}

	}

}