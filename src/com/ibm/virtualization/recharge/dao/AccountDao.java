/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \***************************************************************************/
package com.ibm.virtualization.recharge.dao;

import java.util.ArrayList;

import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

/**
 * Interface definition for account management.
 * 
 * @author bhanu
 * 
 */
public interface AccountDao {

	/**
	 * This method inserts data into VR_ACCOUNT_DETAILS and VR_ACCOUNT_BALANCE
	 * tables
	 * 
	 * @param account
	 * @return
	 * @throws DAOException
	 */
	public void insertAccount(Account account) throws DAOException;

	/**
	 * This method will be used to get account list based on search
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return list
	 * @throws DAOException
	 */
	public ArrayList getAccountList(ReportInputs mtDTO, int lowerBound,
			int upperBound) throws DAOException;

	/**
	 * This method will be used to get account list for a parent account based
	 * on search
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return list
	 * @throws DAOException
	 */
	public ArrayList getChildAccountList(ReportInputs mtDTO,
			int lowerBound, int upperBound) throws DAOException;

	/**
	 * This method return the account level assigned to the given group id
	 * 
	 * @param groupId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getAccesstLevelAssignedList(long groupId)
			throws DAOException;

	/**
	 * This method returns account details based on account id
	 * 
	 * @param accountId
	 * @return Account
	 * @throws DAOException
	 */
	public Account getAccount(long accountId) throws DAOException;

	/**
	 * This method updates VR_ACCOUNT_DETAILS and VR_ACCOUNT_BALANCE tables
	 * 
	 * @param accountDto
	 * @return
	 * @throws DAOException
	 */
	public void updateAccount(Account accountDto) throws DAOException;

	/**
	 * This method returns account id for an account code
	 * 
	 * @param accountCode
	 * @return long
	 * @throws DAOException
	 */
	public long getAccountId(String accountCode) throws DAOException;

	/**
	 * This method returns account id for an account code
	 * 
	 * @param accountCode
	 * @param circleId
	 * @return long
	 * @throws DAOException
	 */
	public long getAccountId(String accountCode, int circleId)
			throws DAOException;

	/**
	 * This method returns account code for an account id
	 * 
	 * @param accountId
	 * @return String
	 * @throws DAOException
	 */

	public String getAccountCode(long accountId) throws DAOException;

	/**
	 * This method returns list of distributor accounts for the given circle Id
	 * 
	 * @param circleId
	 * @return list
	 * @throws DAOException
	 */
	public ArrayList getDistributorAccounts(int circleId) throws DAOException;

	/**
	 * This method returns rate for an Account id in distributor Transaction
	 * 
	 * @param circleId
	 * @return rate
	 * @throws DAOException
	 */
	public double getAccountRate(long accountId) throws DAOException;

	/**
	 * This method updates Account Available Balance for the transaction
	 * 
	 * @param creditedAmt
	 * @param accId
	 * @param updatedBy
	 * @throws DAOException
	 */
	public void updateAccountAvailBalance(double creditedAmt, long accId,
			long updatedBy) throws DAOException;

	/**
	 * This method updates the operating balance for the AccountID
	 * 
	 * @param accountId
	 * @param debitAmount:
	 *            Amount to be added or subtracted
	 * @throws DAOException
	 */
	public void checkUpdateOperatingBalance(long accountId, double debitAmount)
			throws DAOException, VirtualizationServiceException;

	/**
	 * This method updates the operating balance for the AccountID
	 * 
	 * @param accountId
	 * @param debitAmount:
	 *            Amount to be added or subtracted
	 * @throws DAOException
	 */
	public void updateOperatingBalance(long accountId, double debitAmount)
			throws DAOException;

	/**
	 * This method updates the available balance for the AccountID
	 * 
	 * @param accountId
	 * @param debitAmount:
	 *            Amount to be added or subtracted
	 * 
	 * @throws DAOException
	 */
	public void updateAvailableBalance(long accountId, double debitAmount)
			throws DAOException;

	/**
	 * This method updates account Available Balance AND operating balance for
	 * the account Id
	 * 
	 * @param circleId
	 * @return
	 * @throws DAOException
	 */
	public String getMobileNumber(long accountId) throws DAOException;

	/**
	 * This method updates account available andoperating balance
	 * 
	 * @param account
	 * @throws DAOException
	 */
	public void updateAccountBalance(Account account) throws DAOException;

	/**
	 * This method returns the AccountBalanceDto for the Source Account. It sets
	 * the various balances for the account in the AccountBalanceDto
	 * 
	 * @param accountId
	 * @return AccountBalance
	 * @throws DAOException
	 */
	public AccountBalance getBalance(long accountId) throws DAOException;

	/**
	 * This method return the ArrayList of all the Child Account for the Source
	 * Account
	 * 
	 * @param sourceAccountCode
	 * @return ArrayList of the Child Accounts
	 * @throws DAOException
	 */

	public ArrayList getChildAccounts(long sourceAccountId) throws DAOException;

	/**
	 * This method retruns status and parent id based on mobile no
	 * 
	 * @param mobileNO
	 * @return
	 * @throws DAOException
	 */

	public Account getAccountDetailsByMobile(String mobileNO)
			throws DAOException;

	/**
	 * This method updates operating balance and avialable balance for sender
	 * 
	 * @param accountId
	 * @param transactionAmount
	 * @throws DAOException
	 */
	public void doUpdateDestinationBalance(long accountId,
			double transactionAmount) throws DAOException;

	/**
	 * This method updates operating balance and avialable balance for reciever
	 * 
	 * @param accountId
	 * @param transactionAmount
	 * @throws DAOException
	 */
	public void doUpdateSourceBalance(long accountId, double transactionAmount)
			throws DAOException;

	/**
	 * This method is used to Get Circle Id According to Account Id.
	 * 
	 * @param circle
	 * @throws VirtualizationServiceException
	 */

	public int getCircleId(long accountId) throws DAOException;

	/**
	 * This method increase account available balance according to account Id
	 * 
	 * @param creditedAmt,accId,updatedBy
	 * @return
	 * @throws DAOException
	 */
	public void increaseAccountBalance(double creditedAmt, long accId,
			long updatedBy) throws DAOException;

	/**
	 * This method checks if the account is active or not for destination
	 * account.
	 * 
	 * @param accountId
	 * @return
	 * @throws DAOException
	 */
	public long isActive(String mobileNO) throws DAOException;

	// /**
	// * This method return mpassword based on accountId
	// * @param accountId
	// * @return
	// * @throws DAOException
	// */
	// public String getMPassword(long accountId) throws DAOException ;
	/**
	 * This method get Parent Account Id for parent code
	 * 
	 * @param parentCode,circleId
	 * @return long parentId
	 * @throws DAOException
	 */

	public long getParentAccountId(String parentCode, int circleId)
			throws DAOException;

	/**
	 * This method will check the entered mobile no. is Already exist or not
	 * 
	 * @param mobileNo
	 * @return long
	 * @throws DAOException
	 */
	public long checkMobileNumberAlreadyExist(String mobileNo)
			throws DAOException;

	/**
	 * This Method Will Search All Account List According to Selected Circle And
	 * Parent Id
	 * 
	 * @param searchType
	 * @param searchValue
	 * @param parentId
	 * @param parentCircleId
	 * @param accountLevelId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getParentAccountList(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId, int lb, int ub) throws DAOException;

	/**
	 * This Method Will Search All Account List According to Selected Circle
	 * 
	 * @param searchType
	 * @param searchValue
	 * @param circleId
	 * @param accountLevelId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getParentSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub) throws DAOException;

	/**
	 * This Method returns the account list count according to selected Circle
	 * or status or created date range.
	 * 
	 * @param mtDTO
	 * @return int
	 * @throws DAOException
	 */
	public int getAccountListCount(ReportInputs mtDTO) throws DAOException;

	/**
	 * This Method returns the child account list count according to selected
	 * Circle or status or created date range.
	 * 
	 * @param mtDTO
	 * @return int
	 * @throws DAOException
	 */
	public int getChildAccountListCount(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This method will be used to get account list based on search
	 * 
	 * @param mtDTO
	 * @return list
	 * @throws DAOException
	 */
	public ArrayList getAccountListAll(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This method will be used to get account list for a parent account based
	 * on search
	 * 
	 * @param mtDTO
	 * @return list
	 * @throws DAOException
	 */
	public ArrayList getChildAccountListAll(ReportInputs mtDTO)
			throws DAOException;

	/**
	 * This Method returns the account list(Admin login) count according to
	 * selected Circle and according to account level for parent code search .
	 * 
	 * @param searchType
	 * @param searchValue
	 * @param circleId
	 * @param accountLevelId
	 * @return
	 * @throws DAOException
	 */

	public int getParentAccountListCount(String searchType, String searchValue,
			int circleId, String accountLevelId) throws DAOException;

	/**
	 * This Method returns the account list count according to selected Circle
	 * and according to account level for parent code search
	 * 
	 * @param searchType
	 * @param searchValue
	 * @param parentId
	 * @param parentCircleId
	 * @param accountLevelId
	 * @return
	 * @throws DAOException
	 */
	public int getParentChildAccountListCount(String searchType,
			String searchValue, long parentId, int parentCircleId,
			String accountLevelId) throws DAOException;

	/**
	 * This method returns Account Opening Date for the Account
	 * 
	 * @param accountId
	 * @return
	 * @throws DAOException
	 */
	public String getAccountOpeningDate(long accountId) throws DAOException;
	
	/**
	 * This method returns Account default Group id 
	 * 
	 * @param accountLevelId
	 * @return
	 * @throws DAOException
	 */
	public int getAccountDefaultGroupId(int accountLevelId) throws DAOException;
	
   /**
    * this method count the accounts that have passed billable id and active    
    * @param accountId
    * @return
    * @throws DAOException
    */
	
	public long getCountBillableAccountId(long accountId)throws DAOException;
	
	/**
	 * this method get all states 
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getAllStates() throws DAOException;
	
	/**
	 * this method get cities according to state id 
	 * @param stateId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCites(int stateId) throws DAOException;
	
	
	/**
	 * this method get States according to circle id 
	 * @param circleId
	 * @return
	 * @throws DAOException
	 * @By: Digvijay
	 */
//	public ArrayList getStates(int circleId) throws DAOException;
	
	
	/**
	 * this method get root level id on basis of passed id
	 * @param loginId
	 * @return
	 * @throws DAOException
	 */
	public long getAccountRootLevelId(long loginId) throws DAOException; 
	
	/**
	 * this method return billable account list 
	 * @param searchType
	 * @param searchValue
	 * @param circleId
	 * @param accountLevelId
	 * @param lb
	 * @param ub
	 * @param userType
	 * @param loginId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getBillableSearchAccountList(String searchType,
			String searchValue, int circleId, String accountLevelId, int lb,
			int ub,String userType,long loginId )  throws DAOException;
    
	/**
	 * this method count no. of record for billable accounts 
	 * @param searchType
	 * @param searchValue
	 * @param circleId
	 * @param accountLevelId
	 * @param userType
	 * @param loginId
	 * @return
	 * @throws DAOException
	 */
	public int getBillableSearchAccountListCount(String searchType, String searchValue,
			int circleId, String accountLevelId,String userType,long loginId)throws DAOException; 
    
	
	/**
	 * fetch account info according to mobile no
	 * @param mobileNo
	 * @return
	 * @throws DAOException
	 */
	public Account getAccountByMobileNo(long mobileNo) throws DAOException;
/**
 * This method returns comission id based on the account id
 * @param accountId
 * @return
 * @throws DAOException
 */
	public long  getCommissionByAccountId(long accountId) throws DAOException;
	
	
	/**
	 * This method returns name of the circle based on circleId provided and the
	 * 
	 * @param circleId
	 * @return circleName
	 * @throws DAOException
	 */
	public String getCircleName(int circleId) throws DAOException;
}