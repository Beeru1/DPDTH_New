package com.ibm.virtualization.recharge.service;

import java.util.ArrayList;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.virtualization.recharge.dto.DPCreateAccountDto;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPCreateAccountService {
	/**
	 * This mehtod creates a new account
	 * 
	 * @param loginBean
	 * @param accountId
	 * @return
	 * @throws VirtualizationServiceException
	 * @throws EncryptionException 
	 */
	public void createAccount(Login loginBean, DPCreateAccountDto accountdto)
			throws VirtualizationServiceException, EncryptionException;

	/**
	 * This mehtod returns list of accounts searched on : mobile number, account
	 * code, email.
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAccountList(ReportInputs mtDTO, int lowerBound,
			int upperBound) throws VirtualizationServiceException;

	/**
	 * This method return account level assigned to the given group id
	 * 
	 * @param groupId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAccessLevelAssignedList(long groupId)
			throws VirtualizationServiceException;

	/**
	 * This mehtod will use to Get account Information for edit based on Account
	 * id used in Distributor Transaction
	 * 
	 * @param accountId
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public DPCreateAccountDto getAccount(long accountId)
			throws VirtualizationServiceException;

	/**
	 * This mehtod will use to update account information based on account id
	 * used in Distributor Transaction
	 * 
	 * @param account
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public void updateAccount(DPCreateAccountDto account)
			throws VirtualizationServiceException;

	/**
	 * This mehtod will use to Get Distributor Accounts Information based On
	 * cicle ID used in Distributor Transaction
	 * 
	 * @param circleId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getDistributorAccounts(int circleId)
			throws VirtualizationServiceException;

	/**
	 * This method will be used to get mobile number based on account code
	 * passed as argument
	 * 
	 * @param accountId
	 * @return mobileNumber corresponding to account code. or
	 * @throws VirtualizationServiceException
	 *             if mobileNumber not found(invalid account code)
	 */
	public String getMobileNumber(long accountId)
			throws VirtualizationServiceException;

	/**
	 * This method search parent Account according to circle Id
	 * @param searchType
	 * @param searchValue
	 * @param sessionContext
	 * @param parentCircleId
	 * @param accountLevelId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getParentAccountList(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int parentCircleId, String accountLevelId, int lb, int ub)
			throws VirtualizationServiceException;

	/**
	 * This method returns the account list count
	 * @param mtDTO
	 * @return int
	 * @throws VirtualizationServiceException
	 */
	public int getAccountListCount(ReportInputs mtDTO)
			throws VirtualizationServiceException;

	/**
	 * This mehtod returns list of all accounts searched on : mobile number, account
	 * code, email.
	 * 
	 * @param mtDTO
	 * @return ArrayList
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAccountListAll(ReportInputs mtDTO)
			throws VirtualizationServiceException;

	/**
	 * This method returns the Parent account list count
	 * @param mtDTO
	 * @return int
	 * @throws VirtualizationServiceException
	 */
	public int getParentAccountListCount(String searchType, String searchValue,
			UserSessionContext sessionContext, int parentCircle,
			String accountLevelId) throws VirtualizationServiceException;

	/**
	 * This method returns the Account Opening date for the account
	 * @param accountId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public String getAccoutOpeningDate(long accountId)
			throws VirtualizationServiceException;

	/**
	 * this method returns all active states 
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getAllStates() throws VirtualizationServiceException;

	/**
	 * this method return cities according to state 
	 * @param stateId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getCites(int stateId)
			throws VirtualizationServiceException;

	/**
	 * this method return billable list 
	 * @param searchType
	 * @param searchValue
	 * @param sessionContext
	 * @param parentCircleId
	 * @param accountLevelId
	 * @param lb
	 * @param ub
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ArrayList getBillableAccountList(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int parentCircleId, String accountLevelId, int lb, int ub)
			throws VirtualizationServiceException;

	/**
	 * this method return billable list count 
	 * @param searchType
	 * @param searchValue
	 * @param sessionContext
	 * @param parentCircle
	 * @param accountLevelId
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public int getBillableAccountListCount(String searchType,
			String searchValue, UserSessionContext sessionContext,
			int parentCircle, String accountLevelId)
			throws VirtualizationServiceException;
	
	/**
	 * fetch account info according to mobile no
	 * @param mobileNo
	 * @return
	 * @throws DAOException
	 */
	public DPCreateAccountDto getAccountByMobileNo(long mobileNo)  throws VirtualizationServiceException;


}
