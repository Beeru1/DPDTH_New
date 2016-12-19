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
import java.util.Date;

import org.apache.log4j.Logger;

import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ResponseCode;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.QueryDao;
import com.ibm.virtualization.recharge.dao.RechargeDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.EnquiryTransaction;
import com.ibm.virtualization.recharge.dto.QueryBalanceDTO;
import com.ibm.virtualization.recharge.dto.TransactionMaster;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.QueryService;
import com.ibm.virtualization.recharge.validation.ValidatorUtility;

/**
 * This class provides implementation for Query related functionalities.
 * 
 * @author prakash
 * 
 */
public class QueryServiceImpl implements QueryService {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(QueryServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.QueryService#getBalance(com.ibm.virtualization.recharge.dto.UserSessionContext,
	 *      com.ibm.virtualization.recharge.dto.AccountTransaction)
	 */
	public QueryBalanceDTO getBalance(EnquiryTransaction dto)
			throws VirtualizationServiceException {
		logger.info("Started....  accID  "
				+ dto.getUserSessionContext().getId() + "billable id:"
				+ dto.getUserSessionContext().getBillableAccId());

		QueryBalanceDTO accountDetails = new QueryBalanceDTO();
		Connection connection = null;
		try {
			/* insert into transaction manager */
			int returnTransStatus = insertTransactionMaster(dto);
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {

				accountDetails
						.setTransactionStatus(TransactionStatus.DUPLICATE);
				return accountDetails;
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				accountDetails.setTransactionStatus(TransactionStatus.BLACKOUT);
				return accountDetails;

			} else {
				accountDetails.setTransactionStatus(TransactionStatus
						.getTransactionStatus(returnTransStatus));
			}

			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			QueryDao queryDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getQueryDao(connection);
			// Check whether user is authorized for balance query
			if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
					dto.getUserSessionContext().getGroupId(),
					dto.getTransactionChannel(),
					AuthorizationConstants.ROLE_QUERY_BALANCE)) {
				throw new VirtualizationServiceException(ResponseCode.AUTHORIZATION_FAILURE_CODE,
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			}
			// Get the balance details from DAO
			accountDetails = queryDao.getBalance((dto.getUserSessionContext()
					.getBillableAccId() == 0) ? dto.getSourceAccountId() : dto
					.getUserSessionContext().getBillableAccId());
			accountDetails.setTransactionStatus(TransactionStatus.SUCCESS);
		} catch (DAOException de) {
			logger
					.fatal("DAO Exception occured while finding account balance for account id:"
							+ ((dto.getUserSessionContext().getBillableAccId() == 0) ? dto
									.getSourceAccountId()
									: dto.getUserSessionContext()
											.getBillableAccId()));
			accountDetails.setTransactionStatus(TransactionStatus.FAILURE);
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			accountDetails.setTransactionStatus(TransactionStatus.FAILURE);
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed  Succefully found account balance for loginID "
				+ dto.getUserSessionContext().getId());
		return accountDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.QueryService#getChildBalanceByMobileNo(com.ibm.virtualization.recharge.dto.UserSessionContext,
	 *      java.lang.String,
	 *      com.ibm.virtualization.recharge.dto.AccountTransaction)
	 */
	public QueryBalanceDTO getChildBalanceByMobileNo(EnquiryTransaction dto)
			throws VirtualizationServiceException {
		logger.info("Executed ....  parentAccId "
				+ dto.getUserSessionContext().getId() + " childMobileNumber "
				+ dto.getChildMobileNo());

		QueryBalanceDTO accountDetails = new QueryBalanceDTO();
		Connection connection = null;
		try {
			dto.setChildMobileNo(dto.getChildMobileNo());
			/* insert into transaction manager */
			int returnTransStatus = insertTransactionMaster(dto);
			if (TransactionStatus.DUPLICATE.getValue() == returnTransStatus) {
				accountDetails
						.setTransactionStatus(TransactionStatus.DUPLICATE);
				return accountDetails;
			} else if (TransactionStatus.BLACKOUT.getValue() == returnTransStatus) {
				accountDetails.setTransactionStatus(TransactionStatus.BLACKOUT);
				return accountDetails;
			} else {
				accountDetails.setTransactionStatus(TransactionStatus
						.getTransactionStatus(returnTransStatus));
			}
			// Check whether user is authorized for balance query
			if (!AuthorizationFactory.getAuthorizerImpl().isUserAuthorized(
					dto.getUserSessionContext().getGroupId(),
					dto.getTransactionChannel(),
					AuthorizationConstants.ROLE_QUERY_CHILD_BALANCE)) {
				throw new VirtualizationServiceException(ResponseCode.AUTHORIZATION_FAILURE_CODE,
						ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED);
			}
			/* Check whether user enter valid data or not */
			if (!(ValidatorUtility.isValidMobileNumber(dto
					.getChildMobileNo()))) {
				logger
						.error(" Exception occured : Validation Failed for child mobile number.");
				throw new VirtualizationServiceException(
						ExceptionCode.Query.ERROR_INVAILD_CHILD_NO);
			}
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			QueryDao queryDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.
							getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getQueryDao(connection);
			/* Check whether it is valid child */
			long childId = queryDao.isImmediateChild(dto
					.getUserSessionContext().getId(), dto
					.getChildMobileNo());
			if (childId != -1) {
				/* Get balance details from dao */
				accountDetails = queryDao.getBalance(childId);
			} else {
				logger
						.error(" Exception occured : invalid child account code.");
				throw new VirtualizationServiceException(
						ExceptionCode.Query.ERROR_INVALID_CHILD_MOBILE_NO);
			}
			accountDetails.setTransactionStatus(TransactionStatus.SUCCESS);
		} catch (DAOException de) {
			logger
					.fatal("DAO Exception occured while finding accbalance for parent "
							+ dto.getUserSessionContext().getId()
							+ " child account " + dto.getChildMobileNo());
			accountDetails.setTransactionStatus(TransactionStatus.FAILURE);
			throw new VirtualizationServiceException(de.getMessage());
		} catch(VirtualizationServiceException virtualizationExp){
			logger.error("caught VirtualizationServiceException:" +
					"unable to get value from resource bundle"+virtualizationExp.getMessage());
			accountDetails.setTransactionStatus(TransactionStatus.FAILURE);
			throw new VirtualizationServiceException(virtualizationExp.getMessage());
		}finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed");

		return accountDetails;
	}

	/**
	 * This method inserts input details into transaction master table.
	 * 
	 * @param dto
	 * @return returnTransStatus
	 * @throws DAOException
	 */
	private int insertTransactionMaster(EnquiryTransaction dto)
			throws DAOException, VirtualizationServiceException {
		int returnTransStatus = -1;
		/* insert into customer transaction table */
		if (dto != null) {
			Connection connection = null;
			try {
				/* get the database connection */
				connection = DBConnectionManager.getDBConnection();

				RechargeDao rechargeDao = DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.
								getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getRechargeDao(connection);
				TransactionMaster transactionMaster = new TransactionMaster();

				transactionMaster.setCreatedBy(dto.getSourceAccountId());
				transactionMaster.setCreatedDate(new Date());
				transactionMaster.setSourceId(dto.getSourceAccountId());
				transactionMaster.setTransactionChannel(dto
						.getTransactionChannel());
				transactionMaster.setTransactionDate(new Date());
				transactionMaster.setTransactionMessage(dto.toString());
				transactionMaster
						.setTransactionStatus(TransactionStatus.PENDING);
				transactionMaster.setRequestType(RequestType.QUERY);
				transactionMaster.setTransactoinId(dto.getTransactionId());
				transactionMaster.setRequestTime(dto.getRequestTime());
				transactionMaster.setPhysicalId(dto.getPhysicalId());

				returnTransStatus = rechargeDao
						.insertTransactionMasterPreProcess(transactionMaster);

				/* Commit the transaction */
				DBConnectionManager.commitTransaction(connection);
			} finally {
				logger
						.info("createAccount() Method : Finally release connection.");
				DBConnectionManager.releaseResources(connection);
			}
		} else {
			logger.error("RechargeTransaction dto is null");
			throw new VirtualizationServiceException(
					"error.transaction.recharge_dto_null");
		}
		return returnTransStatus;
	}

}
