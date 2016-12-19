package com.ibm.dp.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.dp.beans.SWLocatorListFormBean;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dpmisreports.common.SelectionCollection;
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
	throws VirtualizationServiceException;

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
	public ArrayList getAccountList(ReportInputs mtDTO) throws VirtualizationServiceException;

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
	public void updateAccount(DPCreateAccountDto accountDto)
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
	int parentCircleId, String accountLevelId, int lb, int ub,int accountLevel,DPCreateAccountDto accountDTO)
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
 * @throws com.ibm.virtualization.recharge.exception.DAOException 
*/
	public ArrayList getAllCircles() throws VirtualizationServiceException, com.ibm.virtualization.recharge.exception.DAOException;

/**
* this method return cities according to state 
* @param zoneId
* @return
* @throws VirtualizationServiceException
*/
	public ArrayList getCites(int zoneId)
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
	
	/*
	 * Author Anju
	 * 
	 * This method will return a hashmap
	 * @param circleId
	 * @return hashmap
	 * @throws DAOException
	 * */
	
	public ArrayList aggregateView(ReportInputs mtDTO, int lowerbound, int upperbound) throws VirtualizationServiceException, DAOException, SQLException;
	
	public int aggregateCount(ReportInputs mtDTO)throws VirtualizationServiceException, DAOException, SQLException;
	
	public ArrayList getAggregateListAll(ReportInputs mtDTO)throws VirtualizationServiceException;
	
	public String getCircleName(int circleId)throws DAOException, NumberFormatException, VirtualizationServiceException;
	
	/**
	* this method return cities according to state 
	* @param stateId
	* @return
	* @throws VirtualizationServiceException
	*/
		public ArrayList getZones(int stateId)throws VirtualizationServiceException;
		
		public ArrayList getStates(int circleId)throws VirtualizationServiceException;
		//Added by ARTI for warehaouse list box BFR -11 release2
		public ArrayList getWareHouses(int circleId)throws VirtualizationServiceException;
		
		public ArrayList getTradeList()throws VirtualizationServiceException;
		
		public ArrayList getTradeCategoryList(int tradeId)throws VirtualizationServiceException;

		public DPCreateAccountDto getAccountLevelDetails(int levelId)throws VirtualizationServiceException, SQLException;
		
		public String getReportExternalInfo(int groupID)throws VirtualizationServiceException;
		
		public ArrayList getAccountGroupId(int levelId)throws VirtualizationServiceException, SQLException;

		//Rohit for SSIF
		public DPCreateAccountDto getDPAccountID(int accCode)throws VirtualizationServiceException, SQLException;
		
		public ArrayList getManagerList() throws VirtualizationServiceException, SQLException;
		
		public ArrayList getAllBeats(int cityId) throws VirtualizationServiceException, SQLException;
		
		//Added for Distributor-Beat mapping
		public ArrayList getAllDistBeats(int cityId, Long loginId) throws VirtualizationServiceException, SQLException;
		
		public void checkConsectiveChars(String str, String password,int conschar)throws ValidationException;
		
		public void checkChars(String password) throws ValidationException;
		
		public ArrayList getRetailerCategoryList()throws VirtualizationServiceException;
		public ArrayList getOutletList()throws VirtualizationServiceException;
		public ArrayList getAltChannelList()throws VirtualizationServiceException;
		public ArrayList getChannelList()throws VirtualizationServiceException;
		//public ArrayList<SWLocatorListFormBean> getSWLocatorList() throws VirtualizationServiceException;
		
		// For Boss
		public String [] getRetailerInfo(String mobilenumber)throws VirtualizationServiceException;
		
//		 For cannon
		public String [] getRetailerInfoCannon(String mobilenumber)throws VirtualizationServiceException;
		
		public String[] getRetailerDistInfoCannon(String mobilenumber, String pinCode)throws VirtualizationServiceException;

		public String[] getRetailerDistInfoCannonNew(String mobilenumber, String pinCode)throws VirtualizationServiceException;

		public String getStockStatus(int intAccountID, int intLevelID)throws VirtualizationServiceException;

		public ArrayList<SelectionCollection> getRetailerTransactionList()throws VirtualizationServiceException;

		public int terminateAccount(DPCreateAccountDto accountDto) throws VirtualizationServiceException; //Added by Neetika on 2 Aug Release 3

		public boolean isMobileExistForDepthThree(String mobileNo)  throws VirtualizationServiceException;
		
		public int getCutoffDate() throws Exception;
		
		public int getCutOffDateDist(String loginNm) throws Exception;
}
