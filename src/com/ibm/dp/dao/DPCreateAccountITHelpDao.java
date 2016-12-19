package com.ibm.dp.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.dto.AccountBalance;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public interface DPCreateAccountITHelpDao {
	/**
	 * This method inserts data into VR_ACCOUNT_DETAILS and VR_ACCOUNT_BALANCE
	 * tables
	 * 
	 * @param account
	 * @return
	 * @throws DAOException
	 */
	public void insertAccount(DPCreateAccountDto accountDto,Timestamp currentTime) throws DAOException;

	/**
	 * This method inserts data for ID Helpdesk User into VR_ACCOUNT_DETAILS and VR_ACCOUNT_BALANCE
	 * tables
	 * 
	 * @param account
	 * @return
	 * @throws DAOException
	 */
	public void insertIdHelpDeskAccount(DPCreateAccountDto accountDto,Timestamp currentTime) throws DAOException;

	/**
	 * This method will be used to get account list based on search
	 * 
	 * @param mtDTO
	 * @param lowerBound
	 * @param upperBound
	 * @return list
	 * @throws DAOException
	 * @throws com.ibm.virtualization.recharge.exception.VirtualizationServiceException 
	 * @throws NumberFormatException 
	 */
	public ArrayList getAccountList(ReportInputs mtDTO, int lowerBound,
			int upperBound) throws DAOException, NumberFormatException, com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

	
	public ArrayList getIdHelpDeskUserAccountList(ReportInputs mtDTO) throws DAOException, NumberFormatException, com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

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
	
	//Added by Shilpa For new search Function
	public ArrayList getChildAccountListItHelp(ReportInputs mtDTO) throws DAOException;
//	Added by Shilpa For new search Function Ends here 
	/**
	 * This method return the account level assigned to the given group id
	 * 
	 * @param groupId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getAccessLevelAssignedList(long groupId)
			throws DAOException;

	/**
	 * This method returns account details based on account id
	 * 
	 * @param accountId
	 * @return Account
	 * @throws DAOException
	 */
	public DPCreateAccountDto getAccount(long accountId) throws DAOException;

	/**
	 * This method updates VR_ACCOUNT_DETAILS and VR_ACCOUNT_BALANCE tables
	 * 
	 * @param accountDto
	 * @return
	 * @throws DAOException
	 */
	public void updateAccount(DPCreateAccountDto accountDto,Timestamp currentTime) throws DAOException;

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

	public DPCreateAccountDto getDPAccountID(int accountId) throws DAOException;
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
	public void updateAccountBalance(DPCreateAccountDto account) throws DAOException;

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

	public DPCreateAccountDto getAccountDetailsByMobile(String mobileNO)
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
	  This method will check the entered mobile no. is Already exist or not
	  @param lapu mobileNo
	  @return long
	  @throws DAOException
	  *** not in use for now
	*/
	
	public long checkMobileNumberAlreadyExist(String lapuMobileNo)throws DAOException;
	public long checkMobileNumberAlreadyExistEdit(String lapuMobileNo,long originalAccountId)throws DAOException;

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
			String accountLevelId, int lb, int ub,int accountLevel) throws DAOException;

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
			String searchValue, int circleId,String parentCircleIdList, String accountLevelId, int lb,
			int ub,DPCreateAccountDto accountDTO) throws DAOException;

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
			String searchValue, long parentId, int parentCircleId,String parentCircleIdList ,
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
	public ArrayList getAllCircles() throws DAOException;
	
	/**
	 * this method get cities according to state id 
	 * @param zoneId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getCites(int zoneId) throws DAOException;
	
	/**
	 * this method get cities according to state id 
	 * @param stateId
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getZones(int stateId) throws DAOException;
	
	public ArrayList getZonesList(String stateId) throws DAOException;
	
	/**
	 * this method get root level id on basis of passed id
	 * @param loginId
	 * @return
	 * @throws DAOException
	 */
	
	
	/**
	 * this method get States according to circle id 
	 * @param circleId
	 * @return
	 * @throws DAOException
	 * @By: Digvijay
	 */
	public ArrayList getStates(int circleId) throws DAOException;
	
	
	/**
	 * this method get WareHouses according to circle id 
	 * @param circleId
	 * @return
	 * @throws DAOException
	 * @By: Arti
	 */
	public ArrayList getWareHouses(int circleId) throws DAOException;
	
	/**
	 * this method get WareHouses according to circle id 
	 * @param circleId
	 * @return
	 * @throws DAOException
	 * @By: Arti
	 */
	public void insertWhDistMap(String warehousecode,long loginid) throws DAOException;
	
	
	
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
	public DPCreateAccountDto getAccountByMobileNo(long mobileNo) throws DAOException;
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
	
	/*
	 * Author Anju
	 * 
	 * This method will return a hashmap
	 * @param circleId
	 * @return hashmap
	 * @throws DAOException
	 * */
	
	/**
	  This method will check the entered mobile no. is Already exist or not
	  @param lapu mobileNo
	  @return long
	  @throws DAOException
	  *** not in use for now
	*/
	
	public String checkAllReatilerMobileNumberAlreadyExist(String mobileNo, String lapuMobileNo,String lapuMobileNo1, String ftaMobileNo,String ftaMobileNo1)throws DAOException;
	
	/**
	  This method will check the entered mobile no. is Already exist or not
	  @param lapu mobileNo
	  @return long
	  @throws DAOException
	  *** not in use for now
	*/
	
	public String checkAllReatilerMobileNumberAlreadyExistEditMode(String accountId, String mobileNo, String lapuMobileNo,String lapuMobileNo1, String ftaMobileNo,String ftaMobileNo1)throws DAOException;

	
	
	
	public ArrayList aggregateView(ReportInputs mtDTO, int lowerbound, int upperbound) throws DAOException, SQLException;
	
	public int aggregateCount(ReportInputs mtDTO)throws DAOException, SQLException;
	
	public ArrayList getAggregateListAll(ReportInputs mtDTO)throws VirtualizationServiceException, DAOException;
	
	public ArrayList getTradeList() throws DAOException;
	
	public ArrayList getTradeCategoryList(int tradeId) throws DAOException;
	
	public String getReportExternalInfo(int groupID) throws DAOException;

	public DPCreateAccountDto getAccountLevelDetails(int levelId)throws DAOException;
	
	public ArrayList getManagerList() throws VirtualizationServiceException, SQLException;
	
	public ArrayList getAllBeats(int cityId) throws VirtualizationServiceException, SQLException;
	
	// Added for Distributor Beat Mapping
	public ArrayList getAllDistBeats(int cityId, Long loginId) throws VirtualizationServiceException, SQLException;
	
// to find group id for account created.	
	public ArrayList getAccountGroupId(int levelId)throws VirtualizationServiceException, DAOException;
	
	public ArrayList getRetailerCategoryList() throws VirtualizationServiceException;
	public ArrayList getOutletList() throws VirtualizationServiceException;
	public ArrayList getAltChannelList() throws VirtualizationServiceException;
	public ArrayList getChannelList() throws VirtualizationServiceException;
	public String getEmailId(long accountId) throws DAOException;
//	Created by Shilpa on 17-01-2012 for Critical changes CR
	public String getAccountName(long accountId) throws DAOException;
	
	public String validatePasswordExpiry(Login login) throws DAOException;
	
	//public ArrayList<SWLocatorListFormBean> getSWLocatorList() throws DAOException;

	public String[] getRetailerInfo (String mobilenumber) throws DAOException;
	
//	 For Cannon system
	public String[] getRetailerInfoCannon(String mobilenumber) throws DAOException;

	public String[] getRetailerDistInfoCannon(String mobilenumber, String pinCode)throws DAOException;
	
	/**
	  This method will check the entered Dist.Locator Code is Already exist or not
	  @param disLocCode
	  @return String
	  @throws DAOException
	  *** not in use for now
	*/
	
	public String disLocCodeExist(String disLocCode)throws DAOException;
	public String getSelectedCircleList(String accountId) throws DAOException;
	public String getSelectedCircleListPAN(String accountId) throws DAOException;
	public String checkChildUnderParent(String circleList,String accountId) throws DAOException;
	public ArrayList<SelectionCollection> getDistTransactionList() throws DAOException;
	public String getSelectedTransactions(String accountId) throws DAOException;


	/**
	 * This method returns deposite type of TSM
	 * @param ID
	 * @return
	 * @throws DAOException
	 */
	public ArrayList getTransactionBasedTypeTSMList(String searchType, String searchValue, int parentCircleId,
			String parentCircleIdList, String accountLevelId, int lb, int ub,
			DPCreateAccountDto accountDTO, int distTranctionId)throws DAOException;
//Added by Sugandha
	public String validateTSMTransChange(int accountID)throws DAOException;
	public String checkDistAccountUpdateBoTree(int accountID)throws DAOException;
//	End of changes by Sugandha
	/* Added By parnika */
	
	public DPCreateAccountDto getAccountDistributor(long accountId) throws DAOException;
	public int getAccountGroupId(long accountId) throws DAOException;
	/* End of changes by parnika */
// Added by sugandha for BFR-10 User Termination Release 3
	public ArrayList getTerminationList(long accountID)throws DAOException;
	public String terminateDist(int accountID, String terminationID, String distTranctionId)throws DAOException;
//	End of changes by sugandha	

	public List getRoleList(int groupId)throws DAOException;
	public long getAccountIdInactive(String accountCode, int circleId)
	throws DAOException;

}