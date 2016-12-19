/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/
package com.ibm.virtualization.recharge.service.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
//import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.AccountDao;
import com.ibm.virtualization.recharge.dao.CircleDao;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.User;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.common.ResourceReader;
/**
 * This class provides the implementation for the method declarations in
 * AccountService interface
 * 
 * @author bhanu
 * 
 */
public class AccountServiceImpl implements AccountService {
	/* Logger for this class. */
	private Logger logger = Logger
			.getLogger(AccountServiceImpl.class.getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#createAccount(com.ibm.virtualization.recharge.dto.Account)
	 */

	public void createAccount(Login loginBean, Account account)
			throws VirtualizationServiceException {
		logger.info("Starting :::::" + loginBean + " and " + account);
		Connection connection = null;
		try {
			User user = new User();	
			user.setNewAccountLevel(Integer.parseInt(account.getAccountLevelId()));
			ValidatorFactory.getAccountValidator().validateInsert(account);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			/*
			 * if user not authorized for updating group then setting default
			 * group id for Dealer and retailer
			 */
			if (loginBean.getGroupId() == 0) {
				// loginBean.setGroupId(Constants.ACC_DEFAULT_GROUP_ID);
				int acGroupLevelId = accountDao
						.getAccountDefaultGroupId(Integer.parseInt(account
								.getAccountLevel()));
				if (acGroupLevelId == -1) {
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_INVALID_PARENT_CODE);
				}
				loginBean.setGroupId(acGroupLevelId);
				logger
						.info("user are not auth. to update group hence default Group Id is "
								+ acGroupLevelId);
			}

			// check mobile no. exist or not
			if (accountDao.checkMobileNumberAlreadyExist(account
					.getMobileNumber()) != -1) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
			}

			// if administrator or internal user is creating a DISTRIBUTOR OR
			// MTP USER
			if (loginBean.getType().trim().equalsIgnoreCase(
					Constants.USER_TYPE_INTERNAL)) {
				if (account.getAccountLevelId().trim().equalsIgnoreCase(
						Constants.ACC_LEVEL_MTP_DISTRIBUTOR)) {
					// set parent account as null for MTP and Distributor
					account.setParentAccountId(-1);
					// if created account top level(i.e MTP,Distributor) that is
					// always billable
					// account.setBillableCodeId(-1);
					// control is disabled so value will be set
					account.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);

					// updating operating balance of circle case if
					// Distributor/MTP
					// account creation
					/*Code being commented to avoid update of circle when a new account is created
					CircleDao circleDao = DAOFactory.getDAOFactory(
							Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
							.getCircleDao(connection);
					int updateRow = circleDao.doUpdateCircleBalance(account.getOpeningBalance(), account.getUpdatedBy(),account.getCircleId());
					if (updateRow == 0) {
						logger
								.info(" Exception Insufficient circle balance   ");
						throw new VirtualizationServiceException(
								ExceptionCode.Transaction.ERROR_INSUFFICIENT_BAL);
					}
				} else {
					// if administrator or internal user is creating accounts
					// with levels below MTP/Distributors
					// get parent account id from parent code
					account.setParentAccountId(accountDao.getAccountId(account
							.getParentAccount(), account.getCircleId()));
					// if no parent account found for the parent code
					if (account.getParentAccountId() == -1) {
						logger.info(" Exception invalid parent account id  ");
						throw new VirtualizationServiceException(
								ExceptionCode.Account.ERROR_INVALID_PARENT_CODE);
					}*/

					// if admin. create a non billable account then billable
					// code required
					if (account.getIsbillable().equalsIgnoreCase(
							Constants.ACCOUNT_BILLABLE_NO)) {

						account.setBillableCodeId(accountDao.getAccountId(
								account.getBillableCode(), account
										.getCircleId()));

						if (account.getBillableCodeId() == -1) {
							logger
									.info(" Exception invalid billable account Code  ");
							throw new VirtualizationServiceException(
									ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
						}
					}

					long rootlevelId = accountDao.getAccountRootLevelId(account
							.getParentAccountId());
					if (rootlevelId == -1) {
						logger
								.info(" Error getting   Account Root Level Id  As of account code :- "
										+ account.getParentAccountId());
						throw new VirtualizationServiceException(
								ExceptionCode.Account.ERROR_ACCOUNT_CANNOT_CREATE_ACCOUNT);
					} else {
						account.setRootLevelId(rootlevelId);
					}

					// updating operating and available balance of parent
					accountDao.updateAccountBalance(account);
				}
			} else if (loginBean.getType().trim().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {

				// if user type external then parent account id is login id
				account.setParentAccountId(account.getCreatedBy());
				if (account.getIsbillable().equalsIgnoreCase(
						Constants.ACCOUNT_BILLABLE_NO)) {
					// if login user billable and creating non-billable account
					if (account.getBillableCodeId() != 0) {
						account.setBillableCodeId(accountDao.getAccountId(
								account.getBillableCode(), account
										.getCircleId()));
						if (account.getBillableCodeId() == -1) {
							logger
									.info(" Exception while getting billable code id for code :-  "
											+ account.getBillableCode());
							throw new VirtualizationServiceException(
									ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
						}
					} else {

						account.setBillableCodeId(loginBean.getLoginId());
					}
				}

				long rootlevelId = accountDao.getAccountRootLevelId(loginBean
						.getLoginId());
				if (rootlevelId == -1) {
					logger.info(" Exception invalid Account Root Level Id  ");
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
				} else {
					account.setRootLevelId(accountDao
							.getAccountRootLevelId(loginBean.getLoginId()));
				}

				// updating operating and available balance of parent
				accountDao.updateAccountBalance(account);

			}

			/* Password Encryption */
			IEncryption encrypt = new Encryption();
			// for I-password
			String encryptedPassword = encrypt.generateDigest(loginBean
					.getPassword());
			loginBean.setPassword(encryptedPassword);

			// for m-password
			String encryptedMPassword = encrypt.generateDigest(loginBean
					.getMPassword());
			loginBean.setMPassword(encryptedMPassword);

			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			long accountid = loginDao.insertAccountUser(loginBean,currentTime);
			/* set the Login ID returned from User Master table */
			account.setAccountId(accountid);
			// in case of account creation all balance are same as opening
			// balance
			account.setAvailableBalance(account.getOpeningBalance());
			account.setOperatingBalance(account.getOpeningBalance());
			// inserting data in account_detail and VR_ACCOUNT_BALANCE table
			accountDao.insertAccount(account);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (EncryptionException encryptExp) {
			logger.error(" Exception while encrypting the password.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_PASSWORD_ENCRYPTION);
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ::::Account SuccessFully Created :");

	}

	// method to get update circle amount

	// method to update account details

	// method to update account balance.

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountList(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getAccountList(ReportInputs mtDTO, int lowerBound,
			int upperBound) throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			/*
			 * Added by Paras on Oct 04, 2007.
			 * 
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			// int circleId = 0;
			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					// if (0 != circleId) {
					// circleId = sessionContext.getCircleId();
					// }
					accountList = accountDao.getAccountList(mtDTO, lowerBound,
							upperBound);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					mtDTO.setParentId(parentId);
					accountList = accountDao.getChildAccountList(mtDTO,
							lowerBound, upperBound);
				}
			}

			if (accountList == null || accountList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
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
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountLevelAssignedList(int)
	 */
	public ArrayList getAccessLevelAssignedList(long groupId)
			throws VirtualizationServiceException {

		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList accessLevelAssignedList = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();

			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			;
			accessLevelAssignedList = accountDao
					.getAccesstLevelAssignedList(groupId);
			if (accessLevelAssignedList.size() == 0) {
				logger.fatal(" No Record Found For Account Level : ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_ACCOUNT_LEVEL_RECORD_NOT_FOUND);
			}

		} catch (DAOException daoExp) {
			logger
					.fatal(" caught DAOException : unable to get access level list."
							+ daoExp.getMessage());
			throw new VirtualizationServiceException(daoExp.getMessage());
		} finally {

			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ....");
		return accessLevelAssignedList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccount(long)
	 */
	public Account getAccount(long accountId)
			throws VirtualizationServiceException {
		logger.info("Starting ::::Account Id " + accountId);
		Connection connection = null;
		Account account = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			account = accountDao.getAccount(accountId);
			if (account == null) {
				logger.error("  Account Not found with Account Id = "
						+ accountId);
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info(" Executed :::: Successfully retreived Account Information  ");
		return account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#updateAccountInformation(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void updateAccount(Account account)
			throws VirtualizationServiceException {
		logger.info("Started...account" + account);
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/*
			 * Call the validate method of AccountValidator to validate account
			 * DTO data.
			 */
			User user = new User();	
			user.setNewAccountLevel(Integer.parseInt(account.getAccountLevelId()));
			ValidatorFactory.getAccountValidator().validateUpdate(account);
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			// if mtp or distributor's account is being updating then it will
			// always
			// billable
			if (account.getParentAccountId() == 0) {
				account.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);
			}

			if ((!Constants.ACTIVE_STATUS.equalsIgnoreCase(account.getStatus()))
					|| account.getIsbillable().equalsIgnoreCase(
							Constants.ACCOUNT_BILLABLE_NO)) {
				long count = accountDao.getCountBillableAccountId(account
						.getAccountId());
				if (count != 0) {
					logger
							.error(" Exception occured : can't make non billable while this account code use as billable by other account count: "
									+ count);
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_ACCOUNT_BILLABLE_INACTIVE);

				}

			}

			long accountId = accountDao.checkMobileNumberAlreadyExist(account
					.getMobileNumber());
			// check for mobile no. exist or not
			if ((accountId != account.getAccountId()) && (accountId != -1)) {
				logger
						.error(" Exception occured : mobile no. already exist  : ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
			}

			// if mtp or distributor's account is being updating then it will
			// always
			// billable
			if (0 == account.getParentAccountId()) {
				account.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);

			}

			if (account.getUserType().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {

				if (account.getIsbillable().equalsIgnoreCase(
						Constants.ACCOUNT_BILLABLE_NO)) {
					if (account.getBillableCodeId() == 0) {
						account.setBillableCodeId(account.getUpdatedBy());
					} else {

						// if non billable user updating a non billable account
						if (account.getBillableCodeId() != 0
								&& account.getBillableCode() != null) {
							long billableid = accountDao.getAccountId(account
									.getBillableCode());
							if (billableid != -1) {
								account.setBillableCodeId(billableid);
							} else {
								logger
										.error(" Exception occured : Searching billable code  :Invalid billable code  ");
								throw new VirtualizationServiceException(
										ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
							}
						}

					}
				}

			} else {
				if (account.getIsbillable().equalsIgnoreCase(
						Constants.ACCOUNT_BILLABLE_NO)) {
					// if non billable user updating a non billable account
					if (account.getBillableCode() != null) {
						long billableid = accountDao.getAccountId(account
								.getBillableCode());
						if (billableid != -1) {
							account.setBillableCodeId(billableid);
						} else {
							logger
									.error(" Exception occured : Searching billable code  : ");
							throw new VirtualizationServiceException(
									ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
						}
					}

				}

			}

			LoginDao loginDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			loginDao.updateAccountUser(account.getAccountId(), account
					.getAccountCode(), account.getStatus(), account
					.getGroupId(), account.getParentAccount(),currentTime);

			accountDao.updateAccount(account);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			logger
					.error(" Exception occured : while Updating Account  Message : "
							+ de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ::::Successfully Updated Account  ");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccounts(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getDistributorAccounts(int circleId)
			throws VirtualizationServiceException {
		logger.info("Started... circleId" + circleId);
		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			accountList = accountDao.getDistributorAccounts(circleId);
			if (accountList == null || accountList.size() == 0) {
				logger.error("  Account Not found for circleId = " + circleId);
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error(": Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}

		logger.info("Executed ::::  Successfully retrieved Distributor List");
		return accountList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getMobileNumber(long)
	 */
	public String getMobileNumber(long accountId)
			throws VirtualizationServiceException {
		logger.info("Started...accountId" + accountId);
		String mobileNumber = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			/* Get mobile Number from DAO layer */
			mobileNumber = accountDao.getMobileNumber(accountId);
		} catch (DAOException de) {
			logger
					.fatal("Exception "
							+ de.getMessage()
							+ " Occured While fiding mobilenumber corresponding to accountId"
							+ accountId);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed :::: successfully found mobileNumber for accountId "
						+ accountId);
		return mobileNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getParentAccountList(java.lang.String,
	 *      java.lang.String,
	 *      com.ibm.virtualization.recharge.dto.UserSessionContext, int,
	 *      java.lang.String)
	 */
	public ArrayList getParentAccountList(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int parentCircle, String accountLevelId, int lb, int ub)

	throws VirtualizationServiceException {
		logger.info("Starting... SearchType : " + searchType + " and Value : "
				+ searchValue);
		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			// if user type is External
			long parentId = sessionContext.getId();
			if (sessionContext.getType().equalsIgnoreCase(
					Constants.USER_TYPE_INTERNAL)) {
				accountList = accountDao.getParentSearchAccountList(searchType,
						searchValue, parentCircle, accountLevelId, lb, ub);
			} else {
				accountList = accountDao.getParentAccountList(searchType,
						searchValue, parentId, parentCircle, accountLevelId,
						lb, ub);
			}

			if (accountList == null || accountList.size() == 0) {
				logger.info("Executed ::::No record Found  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
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
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getUsersCountList(mtDTO)
	 */
	public int getAccountListCount(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		logger.info("Started...getUsersCountList()");

		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					noOfPages = accountDao.getAccountListCount(mtDTO);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					mtDTO.setParentId(parentId);
					noOfPages = accountDao.getChildAccountListCount(mtDTO);
				}
			}

		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed :::: successfully count no of rows "
						+ noOfPages);
		return noOfPages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountListAll(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getAccountListAll(ReportInputs mtDTO)
			throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = mtDTO.getSessionContext();

		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			/*
			 * The code will be used to identify which all accounts the logged
			 * in user can view/edit.
			 * 
			 */
			// int circleId = 0;
			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					// if (0 != circleId) {
					// circleId = sessionContext.getCircleId();
					// }
					accountList = accountDao.getAccountListAll(mtDTO);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					mtDTO.setParentId(parentId);
					accountList = accountDao.getChildAccountListAll(mtDTO);
				}
			}

			if (accountList == null || accountList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
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
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getParentAccountListCount(java.lang.String,
	 *      java.lang.String,
	 *      com.ibm.virtualization.recharge.dto.UserSessionContext, int,
	 *      java.lang.String)
	 */
	public int getParentAccountListCount(String searchType, String searchValue,
			UserSessionContext sessionContext, int parentCircle,
			String accountLevelId) throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					noOfPages = accountDao.getParentAccountListCount(
							searchType, searchValue, parentCircle,
							accountLevelId);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					noOfPages = accountDao.getParentChildAccountListCount(
							searchType, searchValue, parentId, parentCircle,
							accountLevelId);
				}
			}

		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed.... successfully count no of rows " + noOfPages);
		return noOfPages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccoutOpeningDate(long)
	 */
	public String getAccoutOpeningDate(long accountId)
			throws VirtualizationServiceException {

		logger.info("Started...");
		Connection connection = null;
		String openingDate = "";
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);
			openingDate = accountDao.getAccountOpeningDate(accountId);
		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While retrieving account opening date ");
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed.... successfully retrieved opening date : "
				+ openingDate);
		return openingDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAllStates()
	 */
	public ArrayList getAllStates() throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList stateList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			stateList = accountDao.getAllStates();

			if (stateList == null || stateList.size() == 0) {
				logger.info("Executed ::::No record Found  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved States List ");
		return stateList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getCites(int)
	 */
	public ArrayList getCites(int stateId)
			throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList cityList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			cityList = accountDao.getCites(stateId);

//			if (cityList == null || cityList.size() == 0) {
//				logger.info("Executed ::::No record Found  ");
//				throw new VirtualizationServiceException(
//						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
//			}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Cites List ");
		return cityList;
	}
		/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getBillableAccountListCount(java.lang.String,
	 *      java.lang.String,
	 *      com.ibm.virtualization.recharge.dto.UserSessionContext, int,
	 *      java.lang.String)
	 */

	public int getBillableAccountListCount(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int circleId, String accountLevelId)
			throws VirtualizationServiceException {
		logger.info("Started...");
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			if (null != sessionContext) {
				// Check if user type is Internal

				noOfPages = accountDao.getBillableSearchAccountListCount(
						searchType, searchValue, circleId, accountLevelId,
						sessionContext.getType(), sessionContext.getId());

			}

		} catch (DAOException de) {
			logger
					.fatal("Exception "
							+ de.getMessage()
							+ " Occured While counting number of rows for billable search accounts "
							+ noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed.... successfully count no of rows for billable search accounts is  "
						+ noOfPages);
		return noOfPages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getBillableAccountList(java.lang.String,
	 *      java.lang.String,
	 *      com.ibm.virtualization.recharge.dto.UserSessionContext, int,
	 *      java.lang.String, int, int)
	 */
	public ArrayList getBillableAccountList(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int circleId, String accountLevelId, int lb, int ub)

	throws VirtualizationServiceException {
		logger.info("Starting... SearchType : " + searchType + " and Value : "
				+ searchValue);
		Connection connection = null;
		ArrayList billableAccountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			AccountDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getAccountDao(connection);

			// if user type is External
			long loginId = sessionContext.getId();

			billableAccountList = accountDao.getBillableSearchAccountList(
					searchType, searchValue, circleId, accountLevelId, lb, ub,
					sessionContext.getType(), loginId);

			if (billableAccountList == null || billableAccountList.size() == 0) {
				logger.info("Executed ::::No record Found  ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info("Executed ::::Successfully retrieved billable Account List ");
		return billableAccountList;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccount(long)
	 */
	public Account getAccountByMobileNo(long mobileNo) 	throws VirtualizationServiceException{
			logger.info("Starting ::::Account Id " + mobileNo);
			Connection connection = null;
			Account account = null;
			
			try {
				/* get the database connection */
				connection = DBConnectionManager.getDBConnection();
				AccountDao accountDao = DAOFactory.getDAOFactory(
						Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getAccountDao(connection);
				account = accountDao.getAccountByMobileNo(mobileNo);
				if (account == null) {
					logger.error("  Account Not found with mobileNo = "
							+ mobileNo);
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
				}
			} catch (DAOException de) {
				logger.error("Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger
					.info(" Executed :::: Successfully retreived Account Information  ");
			return account;
           }

	

}
