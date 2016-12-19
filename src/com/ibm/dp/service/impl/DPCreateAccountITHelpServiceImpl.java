
package com.ibm.dp.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.ibm.appsecure.exception.EncryptionException;
import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.util.Encryption;
import com.ibm.appsecure.util.IEncryption;
import com.ibm.dp.dao.ChangeUserRoleDao;
import com.ibm.dp.dao.DPCreateAccountDao;
import com.ibm.dp.dao.DPCreateAccountITHelpDao;
import com.ibm.dp.dao.impl.ChangeUserRoleDaoImpl;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.exception.DPServiceException;
import com.ibm.dp.service.DPCreateAccountITHelpService;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.ValidatorFactory;
import com.ibm.virtualization.recharge.dao.DAOFactory;
import com.ibm.virtualization.recharge.dao.LoginDao;
import com.ibm.virtualization.recharge.db.DBConnectionManager;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;

public class DPCreateAccountITHelpServiceImpl  implements DPCreateAccountITHelpService  {
	/* Logger for this class. */
	private Logger logger = Logger.getLogger(DPCreateAccountITHelpServiceImpl.class
			.getName());

	
	public void createAccount(Login loginBean, DPCreateAccountDto accountDto)
			throws VirtualizationServiceException {
		logger.info("Starting :::::" + loginBean + " and " + accountDto);
		Connection connection = null;
		try {
			ValidatorFactory.getDPAccountValidator().validateInsert(accountDto);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			/*
			  if user not authorized for updating group then setting default
			  group id for Dealer and retailer
			 */
			if(accountDto.getAccountLevelId() != 1 && accountDto.getAccountLevelId() != 2 ){
				/* Added By Parnika */
				if(accountDto.getAccountLevelId() == 7 ){
					if(accountDto.getHiddenTranctionIds().length() == 3){						
						String selectedTransaction[] = new String[2];
						selectedTransaction[0] = accountDto.getHiddenTranctionIds().substring(0,accountDto.getHiddenTranctionIds().indexOf(',') );
						selectedTransaction[1] = accountDto.getHiddenTranctionIds().substring(accountDto.getHiddenTranctionIds().indexOf(',')+1,3 );
						
							if(Integer.parseInt(selectedTransaction[0]) == 1 || Integer.parseInt(selectedTransaction[1]) == 1){
								accountDto.setSalesTypeTSMId(accountDao.getAccountId(accountDto.getSalesTypeTSMLoginName(), accountDto.getCircleId()));
							}
							if (Integer.parseInt(selectedTransaction[0]) == 2 || Integer.parseInt(selectedTransaction[1]) == 2){
								accountDto.setDepositeTypeTSMId(accountDao.getAccountId(accountDto.getDepositeTypeTSMLoginName(), accountDto.getCircleId()));
							}							
					}
					else{
						String selectedTransaction = accountDto.getHiddenTranctionIds();
						if(Integer.parseInt(selectedTransaction) == 1 ){
							accountDto.setSalesTypeTSMId(accountDao.getAccountId(accountDto.getSalesTypeTSMLoginName(), accountDto.getCircleId()));
						}
						if(Integer.parseInt(selectedTransaction) == 2 ){
							accountDto.setDepositeTypeTSMId(accountDao.getAccountId(accountDto.getDepositeTypeTSMLoginName(), accountDto.getCircleId()));
						}
						
					}
				}
				
				else if(accountDto.getAccountLevelId() == 3)
				{
					accountDto.setParentAccountId(-1);
				}
				
				else {
					//aman
					if(accountDto.getAccountLevelId() != 15 )
						accountDto.setParentAccountId(accountDao.getAccountId(accountDto.getParentLoginName(), accountDto.getCircleId()));
				}
				
				
				/* End of changes By Parnika */		
				}
			//else{
				//accountDto.setParentAccountId(accountDao.getAccountId(accountDto.getParentLoginName(), accountDto.getCircleId()));
			//}
				loginBean.setGroupId(accountDto.getGroupId());
				logger.info("user are not auth. to update group hence default Group Id is "+ accountDto.getGroupId());
			// if administrator or internal user is creating a DISTRIBUTOR
			if (accountDto.getHeirarchyId() == Constants.HIERARCHY_2) {
				////accountDto.setParentAccountId(accountDto.getCreatedBy());
				////accountDto.setRootLevelId(accountDto.getCreatedBy());
			} else if (accountDto.getHeirarchyId() == Constants.HIERARCHY_1) {
				// if user type external then parent account id is login id
				if(accountDto.getAccountLevelId() == (accountDto.getAccessLevelId()+1)){
				////accountDto.setParentAccountId(accountDto.getCreatedBy());
				}else{
					////accountDto.setParentAccountId(accountDao.getAccountId(accountDto.getParentLoginName(), accountDto.getCircleId()));
					//aman
					if(accountDto.getAccountLevelId()!=3)
					{
					if (accountDto.getParentAccountId() == -1) {
						logger.info(" Exception while getting parent account id for parent name :-  "+ accountDto.getBillableCode());
						throw new VirtualizationServiceException(
								ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
					}
					}
				}
				//accountDto.setRootLevelId(accountDao.getAccountRootLevelId(accountDto.getParentAccountId()));
				long rootlevelId = accountDto.getRootLevelId();
				if (rootlevelId == -1) {
					logger.info(" Exception invalid Account Root Level Id  ");
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_INVALID_ROOT_CODE);
				} else {
					rootlevelId = accountDao.getAccountRootLevelId(loginBean.getLoginId());
				}
				if(accountDto.getAccountLevelId() > Constants.DISTRIBUTOR_ID){	
//					if (accountDto.getIsbillable().equalsIgnoreCase(Constants.ACCOUNT_BILLABLE_NO)) 
//					{
//						// if login user billable and creating non-billable accountDto
//						if (accountDto.getBillableCodeId() == Constants.BILLABLE_CODE_ID ){		//+"").equalsIgnoreCase("") || (accountDto.getBillableCodeId()+"").equalsIgnoreCase(null)) {
//							accountDto.setBillableCodeId(accountDao.getAccountId(accountDto.getBillableLoginName(), accountDto.getCircleId()));
//							if (accountDto.getBillableCodeId() == -1) {
//								logger.info(" Exception while getting billable code id for code :-  "
//												+ accountDto.getBillableCode());
//								throw new VirtualizationServiceException(
//										ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
//							}
//						} 
//							else {
//							accountDto.setBillableCodeId(loginBean.getLoginId());
//						}
//					}
					accountDto.setBillableCodeId(loginBean.getLoginId());
//					 in case of account creation all balance are same as opening
					// balance
					accountDto.setAvailableBalance(accountDto.getOpeningBalance());
					accountDto.setOperatingBalance(accountDto.getOpeningBalance());
				}
				
		
				
	if(accountDto.getAccountLevelId() == 7)
		{
			/* Added by Parnika for not checking lapu mobile number when it is blank */
			if(accountDto.getLapuMobileNo() != null && !accountDto.getLapuMobileNo().trim().equals("") ){
				long accountid = accountDao.checkMobileNumberAlreadyExist(accountDto.getLapuMobileNo());
				if(accountid != -1)
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU);
				}
			}
			/* End of changes by Parnika */
			if(accountDto.getLapuMobileNo1() != null && !accountDto.getLapuMobileNo1().trim().equals("")){
				long accountid2 = accountDao.checkMobileNumberAlreadyExist(accountDto.getLapuMobileNo1());
				if(accountid2 != -1)
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU_2);
				}
				}
		}
		else if(accountDto.getAccountLevelId() == 9 )// For reatilers // add by harbans
		{
			String flag = accountDao.checkAllReatilerMobileNumberAlreadyExist(accountDto.getMobileNumber(), accountDto.getLapuMobileNo(), accountDto.getLapuMobileNo1(), accountDto.getFTAMobileNo(), accountDto.getFTAMobileNo1());
			if(flag.equals("MOBILE_FLAG"))
			{
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
			}
			else if(flag.equals("LAPU_FLAG"))
			{
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU);
			}
			else if(flag.equals("LAPU_FLAG2"))
			{
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU_2);
			}
			else if(flag.equals("FTA_FLAG"))
			{
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_FTA);
			}
			else if(flag.equals("FTA_FLAG2"))
			{
				throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_FTA_2);			
			}
		}
		else
		{
			long accountid = accountDao.checkMobileNumberAlreadyExist(accountDto.getMobileNumber());
			if(accountid != -1)
			throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
		}
	}
			if(accountDto.getAccountLevel().equals("6"))
			{
				/* Added  By Parnika for making dist locator non- mandatory */
				if(accountDto.getDistLocator() != null && !accountDto.getDistLocator().trim().equals("")){
					logger.info("\n\n\nValidating Dist Locator Code........\n\n\n ");
					String distLocator = accountDao.disLocCodeExist(accountDto.getDistLocator());
							if(distLocator == null || distLocator.equals(""))
							{
								System.out.println("\n\ndistLocator"+distLocator);
							}
							else
							{
								throw new DAOException("DupDist");
							}
				}
				/* End of changes by Parnika */
			}
			
			
			/* Password Encryption */
			IEncryption encrypt = new Encryption();
			// for I-password
			String encryptedPassword = encrypt.generateDigest(loginBean.getPassword());
			loginBean.setPassword(encryptedPassword);
			LoginDao loginDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			long accountid = loginDao.insertAccountUser(loginBean,currentTime);
			//Added by SHilpa for Admin parent Account Id
			if(accountDto.getAccountLevelId() == 1 || accountDto.getAccountLevelId() == 2){
				accountDto.setParentAccountId(accountid);
				accountDto.setRootLevelId(accountid);
			}else{
				accountDto.setRootLevelId(accountDao.getAccountRootLevelId(accountDto.getParentAccountId()));
				}
			//Added by SHilpa for Admin parent Account Id Ends here
			if(accountDto.getAccountLevel().equalsIgnoreCase("8"))
				accountDto.setAccountCode("RET"+accountid);
			else if(accountDto.getAccountLevel().equalsIgnoreCase("7"))
				accountDto.setAccountCode("FSE"+accountid);
			/* set the Login ID returned from User Master table */
			accountDto.setAccountId(accountid);
			//Added by ARTI for warehaouse list box BFR -11 release2
			if(accountDto.getAccountWarehouseCode() != null && !accountDto.getAccountWarehouseCode().equals("-1") && !accountDto.getAccountWarehouseCode().equals("") )
			{
				accountDao.insertWhDistMap(accountDto.getAccountWarehouseCode(), accountDto.getAccountId());
			}
			logger.info("calling : insertAccount of Dao Impl getParentAccountId == ::::"+accountDto.getParentAccountId());
			
			
			accountDao.insertAccount(accountDto,currentTime);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (EncryptionException encryptExp) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			encryptExp.printStackTrace();
			logger.error(" Exception while encrypting the password.");
			throw new VirtualizationServiceException(
					ExceptionCode.Account.ERROR_PASSWORD_ENCRYPTION);
		} catch (DAOException de) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch(SQLException e)
		{
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ::::Account SuccessFully Created :");
	}
	
	
	public void createIdHelpDeskAccount(Login loginBean, DPCreateAccountDto accountDto)
	throws VirtualizationServiceException {
logger.info("Starting :::::" + loginBean + " and " + accountDto);
Connection connection = null;
try {
	//ValidatorFactory.getDPAccountValidator().validateInsert(accountDto);
	/* get the database connection */
	connection = DBConnectionManager.getDBConnection();
	connection.setAutoCommit(false);
	DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getNewAccountITHelpDao(connection);
	loginBean.setGroupId(accountDto.getGroupId());
	
	long accountidMob = accountDao.checkMobileNumberAlreadyExist(accountDto.getMobileNumber());
	if(accountidMob != -1)
	throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);

	
	
	/* Password Encryption */
	IEncryption encrypt = new Encryption();
	// for I-password
	String encryptedPassword = encrypt.generateDigest(loginBean.getPassword());
	loginBean.setPassword(encryptedPassword);
	LoginDao loginDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getLoginDao(connection);
	Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
	
	long accountid = loginDao.insertIdHelpAccountUser(loginBean,currentTime);
	logger.info("Login Id generated === "+accountid);
	//Added by SHilpa for Admin parent Account Id
		accountDto.setParentAccountId(accountid);
		accountDto.setRootLevelId(accountid);
	
	//Added by SHilpa for Admin parent Account Id Ends here
	/* set the Login ID returned from User Master table */
	accountDto.setAccountId(accountid);
	logger.info("calling : insertAccount of Dao Impl getParentAccountId == ::::"+accountDto.getParentAccountId());
	
	
	accountDao.insertIdHelpDeskAccount(accountDto,currentTime);
	/* Commit the transaction */
	DBConnectionManager.commitTransaction(connection);
} catch (EncryptionException encryptExp) {
	try {
		connection.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	encryptExp.printStackTrace();
	logger.error(" Exception while encrypting the password.");
	throw new VirtualizationServiceException(
			ExceptionCode.Account.ERROR_PASSWORD_ENCRYPTION);
} catch (DAOException de) {
	try {
		connection.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	de.printStackTrace();
	logger.error("Exception occured : Message : " + de.getMessage());
	throw new VirtualizationServiceException(de.getMessage());
}
catch(SQLException e)
{
	try {
		connection.rollback();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
}
finally {
	DBConnectionManager.releaseResources(connection);
}
logger.info(" Executed ::::Account SuccessFully Created :");
}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountList(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getAccountList(ReportInputs reportInputDto, int lowerBound,
			int upperBound) throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = reportInputDto.getSessionContext();
		Connection connection = null;
		ArrayList accountList = null;
		logger.info("asa::getAccountList");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			/*
			  Added by Paras on Oct 04, 2007.
			  The code will be used to identify which all accounts the logged
			  in user can view/edit.
			 */
			if (null != sessionContext) {
				// Check if user type is Internal
				if (Constants.HIERARCHY_2 == sessionContext.getHeirarchyId()) {
					logger.info("%%%%%%% hi in if condition in service IMPL ******* ");
					accountList = accountDao.getAccountList(reportInputDto, lowerBound,
							upperBound);
				} else {
					// if user type is External
					logger.info("%%%%%%%%% hi in else condition in service IMPL******");
					long parentId = sessionContext.getId();
					reportInputDto.setParentId(parentId);
					//accountList = accountDao.getChildAccountList(reportInputDto,lowerBound, upperBound);
					accountList = accountDao.getChildAccountListItHelp(reportInputDto);
					
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
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountList(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getIdHelpDeskUserAccountList(ReportInputs reportInputDto) throws VirtualizationServiceException {
		/* get sessionContext */
		UserSessionContext sessionContext = reportInputDto.getSessionContext();
		Connection connection = null;
		ArrayList accountList = null;
		try {
			logger.info("asa::getIdHelpDeskUserAccountList");
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			/*
			  Added by Paras on Oct 04, 2007.
			  The code will be used to identify which all accounts the logged
			  in user can view/edit.
			 */
			if (null != sessionContext) {
				// Check if user type is Internal
					accountList = accountDao.getIdHelpDeskUserAccountList(reportInputDto);
				
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
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getAccountLevelAssignedList(int)
	 */
	public ArrayList getAccessLevelAssignedList(long groupId)
			throws VirtualizationServiceException {
		logger.info("Started...groupId " + groupId);
		Connection connection = null;
		ArrayList accessLevelAssignedList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(	Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			accessLevelAssignedList = accountDao
					.getAccessLevelAssignedList(groupId);
			if (accessLevelAssignedList.size() == 0) {
				logger.fatal(" No Record Found For Account Level : ");
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_ACCOUNT_LEVEL_RECORD_NOT_FOUND);
			}
		} catch (DAOException daoExp) {
			daoExp.printStackTrace();
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
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getAccount(long)
	 */
	public DPCreateAccountDto getAccount(long accountId)
			throws VirtualizationServiceException {
		logger.info("Starting ::::Account Id " + accountId);
		logger.info("asa::getAccount");
		Connection connection = null;
		DPCreateAccountDto accountDto = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			accountDto = accountDao.getAccount(accountId);
			if (accountDto == null) {
				logger.error("  Account Not found with Account Id = "
						+ accountId);
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			de.printStackTrace();
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger
				.info(" Executed :::: Successfully retreived Account Information  ");
		return accountDto;
	}
	
	/* Added by Parnika */
	
	public DPCreateAccountDto getAccountDistributor(long accountId) throws VirtualizationServiceException {
			logger.info("Starting ::::Account Id " + accountId);
			Connection connection = null;
			DPCreateAccountDto accountDto = null;
			try {
				/* get the database connection */
				connection = DBConnectionManager.getDBConnection();
				DPCreateAccountITHelpDao accountDao = DAOFactory
						.getDAOFactory(
								Integer
										.parseInt(ResourceReader
												.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getNewAccountITHelpDao(connection);
				accountDto = accountDao.getAccountDistributor(accountId);
				if (accountDto == null) {
					logger.error("  Account Not found with Account Id = "
							+ accountId);
					throw new VirtualizationServiceException(
							ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
				}
			} catch (DAOException de) {
				logger.error("Exception occured : Message : " + de.getMessage());
				de.printStackTrace();
				throw new VirtualizationServiceException(de.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger
					.info(" Executed :::: Successfully retreived Account Information  ");
			return accountDto;
}
	
	/* End of changes by Parnika */
	
	/*
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#updateAccountInformation(com.ibm.virtualization.recharge.dto.Account)
	 */
	public void updateAccount(DPCreateAccountDto accountDto)
			throws VirtualizationServiceException {
		logger.info("asa::updateAccount");
		logger.info("Started.. updateAccount .accountDto.getAccountLevelId() == " + accountDto.getAccountLevelId());
		Connection connection = null;
		try {
			
			logger.info("accountDto.getSalesTypeTSMId() :"+accountDto.getSalesTypeTSMId());
			logger.info("accountDto.getDepositeTypeTSMId() :"+accountDto.getDepositeTypeTSMId());
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			/*
			  Call the validate method of AccountValidator to validate account
			  DTO data.
			 */
			ValidatorFactory.getDPAccountValidator().validateUpdate(accountDto);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			// fetch parent account id
			if(accountDto.getAccountLevelId() == 1 || accountDto.getAccountLevelId() == 2 || accountDto.getAccountLevelId() ==3 || accountDto.getAccountLevelId() == 15){ //changed by neetika to handle circl amdin and finance user parent accoutn as self
				logger.info("asa::"+accountDto.getAccountLevelId());
				accountDto.setParentAccountId(accountDto.getAccountId());
			}/*Added By Parnika */
			else if(accountDto.getAccountLevelId() == 7 ){
					if(accountDto.getHiddenTranctionIds().length() == 3){						
						String selectedTransaction[] = new String[2];
						selectedTransaction[0] = accountDto.getHiddenTranctionIds().substring(0,accountDto.getHiddenTranctionIds().indexOf(',') );
						selectedTransaction[1] = accountDto.getHiddenTranctionIds().substring(accountDto.getHiddenTranctionIds().indexOf(',')+1,3 );
						
							if(Integer.parseInt(selectedTransaction[0]) == 1 || Integer.parseInt(selectedTransaction[1]) == 1){
							//Neetika change this
								accountDto.setSalesTypeTSMId(accountDao.getAccountIdInactive(accountDto.getSalesTypeTSMLoginName(), accountDto.getCircleId()));
								logger.info("accountDto.getSalesTypeTSMId : "+accountDto.getSalesTypeTSMId());
								
							}
							if (Integer.parseInt(selectedTransaction[0]) == 2 || Integer.parseInt(selectedTransaction[1]) == 2){
								accountDto.setDepositeTypeTSMId(accountDao.getAccountIdInactive(accountDto.getDepositeTypeTSMLoginName(), accountDto.getCircleId()));
								logger.info("accountDto.getDepositeTypeTSMId : "+accountDto.getDepositeTypeTSMId());
							}							
					}
					else{
						String selectedTransaction = accountDto.getHiddenTranctionIds();
						if(Integer.parseInt(selectedTransaction) == 1 ){
							accountDto.setSalesTypeTSMId(accountDao.getAccountIdInactive(accountDto.getSalesTypeTSMLoginName(), accountDto.getCircleId()));
							logger.info("accountDto.getSalesTypeTSMId : "+accountDto.getSalesTypeTSMId());
							
						}
						if(Integer.parseInt(selectedTransaction) == 2 ){
							accountDto.setDepositeTypeTSMId(accountDao.getAccountIdInactive(accountDto.getDepositeTypeTSMLoginName(), accountDto.getCircleId()));
							logger.info("accountDto.getDepositeTypeTSMId : "+accountDto.getDepositeTypeTSMId());
						}
						
					}
					if (accountDto.getDepositeTypeTSMId() == -1 && accountDto.getSalesTypeTSMId() == -1) {
						throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_PARENT_CHILD_DIFF_CIRCLE);
					}
					// For setting distributor's transaction id as -1 in account details
					accountDto.setTransactionId("-1");
					
				}
				else{
					//logger.info( "create .. account  "+accountDto.getAccountLevelId());//circle admin = 3
					accountDto.setParentAccountId(accountDao.getAccountIdInactive(accountDto.getParentLoginName(), accountDto.getCircleId()));
					if (accountDto.getParentAccountId() == -1 && accountDto.getAccountLevelId()!=3) {
						logger.info("asa::serviceimpl");
						throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_PARENT_CHILD_DIFF_CIRCLE);
					}
				}
				/* End of changes By Parnika */	

			
			// if distributor's account is being updating then it will always billable
			if (accountDto.getAccountLevelId() == Constants.DISTRIBUTOR_ID) {
				accountDto.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);
			}
			
			/* Commented by Nazim Hussain as Billable in no way related to DPDTH accounts
			 * 
			 if(accountDto.getAccountLevelId() >= Constants.FSE_ID || accountDto.getHeirarchyId() == Constants.HIERARCHY_1)
			{
				if ((!Constants.ACTIVE_STATUS.equalsIgnoreCase(accountDto.getStatus()))
						|| accountDto.getIsbillable().equalsIgnoreCase(Constants.ACCOUNT_BILLABLE_NO)) 
				{
					long count = accountDao.getCountBillableAccountId(accountDto.getAccountId());
					if (count != 0) {
						logger.error(" Exception occured : can't make non billable while this account code use as billable by other account count: "+ count);
						throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACCOUNT_BILLABLE_INACTIVE);
					}
				}
			}
			
			// if mtp or distributor's account is being updating then it will
			// always
			// billable
			
			if (accountDto.getAccountLevelId() >= Constants.DISTRIBUTOR_ID && accountDto.getHeirarchyId() == Constants.HIERARCHY_1) {

				if (accountDto.getIsbillable().equalsIgnoreCase(
						Constants.ACCOUNT_BILLABLE_NO)) {
//					if (accountDto.getBillableCodeId() == 0) {
//						accountDto.setBillableCodeId(accountDto.getUpdatedBy());
//					} else {

						// if non billable user updating a non billable accountDto
						if (accountDto.getBillableCode() != null) {
							long billableid = accountDao.getAccountId(accountDto
									.getBillableLoginName());
							if (billableid != -1) {
								accountDto.setBillableCodeId(billableid);
							} else {
								logger
										.error(" Exception occured : Searching billable code  :Invalid billable code  ");
								throw new VirtualizationServiceException(
										ExceptionCode.Account.ERROR_INVALID_BILLABLE_CODE);
							}
						}

	//				}
				}
			} */
			
			// Add for retailer information webservice published for Cannon System
			if(accountDto.getAccountLevelId() == 9 )// For reatilers // add by harbans
			{
				String flag = accountDao.checkAllReatilerMobileNumberAlreadyExistEditMode(String.valueOf(accountDto.getAccountId()), accountDto.getMobileNumber(), accountDto.getLapuMobileNo(), accountDto.getLapuMobileNo1(), accountDto.getFTAMobileNo(), accountDto.getFTAMobileNo1());
				if(flag.equals("MOBILE_FLAG"))
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
				}
				else if(flag.equals("LAPU_FLAG"))
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU);
				}
				else if(flag.equals("LAPU_FLAG2"))
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU_2);
				}
				else if(flag.equals("FTA_FLAG"))
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_FTA);
				}
				else if(flag.equals("FTA_FLAG2"))
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_FTA_2);			
				}
			}
		
			
			if (accountDto.getHeirarchyId() == Constants.HIERARCHY_1)
			{
				// Add by harbans
				String mobileNo = getMobileNumber(accountDto.getAccountId());
			    logger.info("Prev mob no :   "+ mobileNo + " new mob mo :" + accountDto.getMobileNumber());
				if(!accountDto.getMobileNumber().equalsIgnoreCase(mobileNo))
				{
					/*
					if(accountDto.getAccountLevelId() == 7){
						long accountid = accountDao.checkMobileNumberAlreadyExist(accountDto.getLapuMobileNo());
						if(accountid != -1){
							
							throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU);
						}
						}
					else{
					*/
						long accountid = accountDao.checkMobileNumberAlreadyExist(accountDto.getMobileNumber());
						if(accountid != -1)
						throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_MOBILE_NO);
					//}
				}
			}
			if(accountDto.getAccountLevelId() == 7)
			{
				long originalAccountId = accountDto.getAccountId();
				/* Added by Parnika for not checking blank lapu mobile number */
				if(accountDto.getLapuMobileNo() != null && !accountDto.getLapuMobileNo().trim().equals("")){
				long accountid = accountDao.checkMobileNumberAlreadyExistEdit(accountDto.getLapuMobileNo(),originalAccountId);
				if(accountid != -1)
				{
					throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU);
				}
				}
				
				/* End of changes by Parnika */
				if(accountDto.getLapuMobileNo1() != null && !accountDto.getLapuMobileNo1().trim().equals("")){
					long accountid2 = accountDao.checkMobileNumberAlreadyExistEdit(accountDto.getLapuMobileNo1(),originalAccountId);
					if(accountid2 != -1)
					{
						throw new VirtualizationServiceException(ExceptionCode.Account.ERROR_ACC_ALREADY_EXIST_LAPU_2);
					}
				}
			}
			
			LoginDao loginDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getLoginDao(connection);
			Timestamp currentTime = new Timestamp(new java.util.Date().getTime());
			loginDao.updateAccountUser(accountDto.getAccountId(), accountDto.getAccountCode(), accountDto.getStatus(), accountDto.getGroupId(), accountDto.getParentAccount(),currentTime);
			accountDao.updateAccount(accountDto,currentTime);
			/* Commit the transaction */
			DBConnectionManager.commitTransaction(connection);
		} catch (DAOException de) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			de.printStackTrace();
			logger.error("Exception occured : Message ----------------: " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info(" Executed ::::Successfully Updated Account  ");
	}
	/*
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getAccounts(java.lang.String,
	  java.lang.String)
	 */
	public ArrayList getDistributorAccounts(int circleId)
			throws VirtualizationServiceException {
		logger.info("Started... circleId" + circleId);
		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
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
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getMobileNumber(long)
	 */
	public String getMobileNumber(long accountId)
			throws VirtualizationServiceException {
		logger.info("Started...accountId" + accountId);
		String mobileNumber = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
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
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getParentAccountList(java.lang.String,
	  java.lang.String,
	  com.ibm.virtualization.recharge.dto.UserSessionContext, int,
	  java.lang.String)
	 */
	public ArrayList getParentAccountList(String searchType,String searchValue, UserSessionContext sessionContext,
			int parentCircle, String parentCircleIdList,String accountLevelId, int lb, int ub,int accountLevel,DPCreateAccountDto accountDTO)
	throws VirtualizationServiceException {
		logger.info("Starting... SearchType : " + searchType + " and Value : "
				+ searchValue);
		logger.info("asa::getParentAccountList");
		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
//			long parentId = sessionContext.getId();
			if (sessionContext.getType().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {
				accountList = accountDao.getParentSearchAccountList(searchType,searchValue, parentCircle,parentCircleIdList, accountLevelId, lb, ub,accountDTO);
			} 
/*			else {
				accountList = accountDao.getParentAccountList(searchType,
						searchValue, parentId, parentCircle, accountLevelId,
						lb, ub, accountLevel);
			}	*/
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
	  (non-Javadoc)
	  @see com.ibm.virtualization.recharge.service.AccountService#getUsersCountList(reportInputDto)
	 */
	public int getAccountListCount(ReportInputs reportInputDto)
			throws VirtualizationServiceException {
		logger.info("Started...getUsersCountList()");
		logger.info("asa::getAccountListCount");
		/* get sessionContext */
		UserSessionContext sessionContext = reportInputDto.getSessionContext();
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			if (null != sessionContext) {
				logger.info("User Type in Serivce IMPL == "+sessionContext.getType());
				// Check if user type is Internal
				if (Constants.USER_TYPE_INTERNAL
						.equalsIgnoreCase(sessionContext.getType())) {
					noOfPages = accountDao.getAccountListCount(reportInputDto);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					reportInputDto.setParentId(parentId);
					noOfPages = accountDao.getChildAccountListCount(reportInputDto);
				}
			}

		} catch (DAOException de) {
			logger.fatal("Exception " + de.getMessage()
					+ " Occured While counting number of rows" + noOfPages);
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed :::: successfully count no of rows " + noOfPages);
		return noOfPages;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccountListAll(java.lang.String,
	 *      java.lang.String)
	 */
	public ArrayList getAccountListAll(ReportInputs reportInputDto)
			throws VirtualizationServiceException {
		/* get sessionContext */
		logger.info("asa::getAccountListAll");
		UserSessionContext sessionContext = reportInputDto.getSessionContext();

		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
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
					accountList = accountDao.getAccountListAll(reportInputDto);
				} else {
					// if user type is External
					long parentId = sessionContext.getId();
					reportInputDto.setParentId(parentId);
					accountList = accountDao.getChildAccountListAll(reportInputDto);
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
			UserSessionContext sessionContext, int parentCircle,String parentCircleIdList ,
			String accountLevelId) throws VirtualizationServiceException {
		logger.info("asa::getParentAccountListCount");
		logger.info("Started...");
		Connection connection = null;
		int noOfPages = 0;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
						.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);

			if (null != sessionContext) {
					// if user type is External
					long parentId = sessionContext.getId();
					noOfPages = accountDao.getParentChildAccountListCount(searchType, searchValue, parentId, parentCircle,parentCircleIdList,accountLevelId);
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
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
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
	public ArrayList getAllCircles() throws VirtualizationServiceException,
			com.ibm.virtualization.recharge.exception.DAOException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList stateList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			stateList = accountDao.getAllCircles();

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
	public ArrayList getCites(int zoneId) throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList cityList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			cityList = accountDao.getCites(zoneId);

			// if (cityList == null || cityList.size() == 0) {
			// logger.info("Executed ::::No record Found ");
			// throw new VirtualizationServiceException(
			// ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			// }
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
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

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
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

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
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getAccount(long)
	 */
	public DPCreateAccountDto getAccountByMobileNo(long mobileNo)
			throws VirtualizationServiceException {
		logger.info("Starting ::::Account Id " + mobileNo);
		Connection connection = null;
		DPCreateAccountDto accountDto = null;

		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			accountDto = accountDao.getAccountByMobileNo(mobileNo);
			if (accountDto == null) {
				logger.error("  Account Not found with mobileNo = " + mobileNo);
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
		return accountDto;
	}

	public ArrayList aggregateView(ReportInputs reportInputDto, int lowerbound,
			int upperbound) throws VirtualizationServiceException,
			DAOException, SQLException {
		ArrayList agglist = new ArrayList();
		Connection connection = null;
		try{
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader
							.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
	
			agglist = accountDao.aggregateView(reportInputDto, lowerbound, upperbound);
		}catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally{
			DBConnectionManager.releaseResources(connection);
		}
		// TODO Auto-generated method stub
		return agglist;
	}

	public int aggregateCount(ReportInputs reportInputDto)
			throws VirtualizationServiceException, DAOException, SQLException {
		Connection connection = null;
		int Count=0;
		try{
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader
							.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			Count = accountDao.aggregateCount(reportInputDto);
		}catch (DAOException de) {
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
		finally{
			DBConnectionManager.releaseResources(connection);
		}
		return Count;
	}

	public ArrayList getAggregateListAll(ReportInputs reportInputDto)
			throws VirtualizationServiceException {

		Connection connection = null;
		ArrayList aggregateList = null;
		UserSessionContext sessionContext = reportInputDto.getSessionContext();
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			if (null != sessionContext) {
				aggregateList = accountDao.getAggregateListAll(reportInputDto);
			}
			if (aggregateList == null || aggregateList.size() == 0) {
				throw new VirtualizationServiceException(
						ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Aggregate List ");
		return aggregateList;
	}

	public String getCircleName(int circleId) throws DAOException,
			NumberFormatException, VirtualizationServiceException {
		Connection connection = null;
		// ArrayList aggregateList = null;
		String circleName;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			circleName = accountDao.getCircleName(circleId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Aggregate List ");
		return circleName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibm.virtualization.recharge.service.AccountService#getCites(int)
	 */
	public ArrayList getZones(int stateId)
			throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList cityList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			cityList = accountDao.getZones(stateId);

			//	if (cityList == null || cityList.size() == 0) {
			//		logger.info("Executed ::::No record Found  ");
			//		throw new VirtualizationServiceException(
			//				ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			//	}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved zone List ");
		return cityList;
	}

	
	public ArrayList getZonesList(String stateId) throws VirtualizationServiceException {
		logger.info("Starting... getZonesList ");
		Connection connection = null;
		ArrayList cityList = null;
		try {
			/* get the database connection */
	connection = DBConnectionManager.getDBConnection();
	DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getNewAccountITHelpDao(connection);

	cityList = accountDao.getZonesList(stateId);

	
} catch (DAOException de) {
	logger.error(" Exception occured : Message : " + de.getMessage());
	throw new VirtualizationServiceException(de.getMessage());
} finally {
	DBConnectionManager.releaseResources(connection);
}
logger.info("Executed ::::Successfully retrieved zone List ");
return cityList;
}


	// State Drop Down Code Start : Digvijay
	
	public ArrayList getStates(int circleId) throws VirtualizationServiceException {
		logger.info("Starting State Service... ");
		Connection connection = null;
		ArrayList stateList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

					stateList = accountDao.getStates(circleId);
			} catch (DAOException de) {
				logger.error(" Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger.info("Executed ::::Successfully retrieved State List in Service.... ");
			return stateList;
	}
	// State Drop Down Code Ends : Digvijay

	// //Added by ARTI for warehaouse list box BFR -11 release2
	
	public ArrayList getWareHouses(int circleId) throws VirtualizationServiceException {
		Connection connection = null;
		ArrayList stateList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(
					Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

					stateList = accountDao.getWareHouses(circleId);
			} catch (DAOException de) {
				logger.error(" Exception occured : Message : " + de.getMessage());
				throw new VirtualizationServiceException(de.getMessage());
			} finally {
				DBConnectionManager.releaseResources(connection);
			}
			logger.info("Executed ::::Successfully retrieved warehouse List in Service.... ");
			return stateList;
	}
	// State Drop Down Code Ends : Digvijay
	
	
	public ArrayList getTradeList() throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList tradeList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			tradeList = accountDao.getTradeList();

			//if (cityList == null || cityList.size() == 0) {
			//	logger.info("Executed ::::No record Found  ");
			//	throw new VirtualizationServiceException(
			//			ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			//}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Cites List ");
		return tradeList;
	}

	public ArrayList getTradeCategoryList(int tradeId)
			throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		ArrayList categoryList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			categoryList = accountDao.getTradeCategoryList(tradeId);

			//		if (cityList == null || cityList.size() == 0) {
			//			logger.info("Executed ::::No record Found  ");
			//			throw new VirtualizationServiceException(
			//					ExceptionCode.Account.ERROR_ACCOUNT_NO_RECORD_FOUND);
			//		}
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Cites List ");
		return categoryList;
	}
	
	/*
	 * Method to Retrieve External Group Password
	 */ 
	public String getReportExternalInfo(int groupID)
			throws VirtualizationServiceException {
		logger.info("Starting... ");
		Connection connection = null;
		String reportExternalPassword = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);


			reportExternalPassword = accountDao.getReportExternalInfo(groupID);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return reportExternalPassword;
	}	  
	
	public DPCreateAccountDto getAccountLevelDetails(int levelId)throws VirtualizationServiceException, SQLException{
		Connection connection = null;
		DPCreateAccountDto accountDto=new DPCreateAccountDto();
		logger.info("asa::getAccountLevelDetails");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			accountDto = accountDao.getAccountLevelDetails(levelId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return accountDto;
	}
	
	public ArrayList getAccountGroupId(int levelId)throws VirtualizationServiceException, SQLException{
		Connection connection = null;
		ArrayList groupId = null;
		logger.info("asa::getAccountGroupId");
		try {
			// get the database connection
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		groupId = accountDao.getAccountGroupId(levelId);
	}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return groupId;
	}
	// By rohit for SSFI
	public DPCreateAccountDto getDPAccountID(int accCode) throws VirtualizationServiceException, SQLException {
		Connection connection = null;
		DPCreateAccountDto accountdto = new DPCreateAccountDto();
		logger.info("asa::getDPAccountID");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			accountdto = accountDao.getDPAccountID(accCode);
	}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return accountdto;
	}

	public ArrayList getManagerList() throws VirtualizationServiceException, SQLException{
		ArrayList financeList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			financeList = accountDao.getManagerList();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		return financeList;
	}
	
	public ArrayList getAllBeats(int cityId) throws VirtualizationServiceException, SQLException{
		ArrayList financeList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			financeList = accountDao.getAllBeats(cityId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(SQLException sql){
			sql.printStackTrace();
		}finally {
				DBConnectionManager.releaseResources(connection);
		}
		return financeList;
	}
	
	public ArrayList getAllDistBeats(int cityId, Long loginId) throws VirtualizationServiceException, SQLException{
		ArrayList financeList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			financeList = accountDao.getAllDistBeats(cityId, loginId);
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}catch(SQLException sql){
			sql.printStackTrace();
		}finally {
				DBConnectionManager.releaseResources(connection);
		}
		return financeList;
	}
	
	public void checkConsectiveChars(String str, String password,int conschar) throws ValidationException {
		int i = 0;
		boolean found = false;
		while(i < str.length()-2 && !found) {
			if(password.indexOf(str.substring(i,i+conschar)) != -1) {
				found = true;
			}
			i++;
		}
		if (found==true){
			ValidationException valexp = new ValidationException("checkConsectiveChars"); 
			valexp.setMessage("checkConsectiveChars");
			throw valexp;
		}
	}
	
	public void checkChars(String password) throws ValidationException {
		
		 Pattern q = Pattern.compile("\\w+[&,%,$,#,@,!,_]+\\w+");
		 Matcher x = q.matcher(password);
		 boolean a = x.matches();
		 if(a){
			 logger.info("special char check OK");
		 }else{
			ValidationException valexp = new ValidationException("noSpecialCharacter"); 
			valexp.setMessage("noSpecialCharacter");
			throw valexp;
		 }
		 
		 String cue= password.toLowerCase();
			if(cue.equals(password)){
				ValidationException valexp = new ValidationException("noUppercase"); 
				valexp.setMessage("noUppercase");
				throw valexp;
			}else{
				logger.info("contains an Uppercase case");
			}
			
			String cue2=password.toUpperCase();
			
			if(cue2.equals(password)){
				ValidationException valexp = new ValidationException("noLowerCase"); 
				valexp.setMessage("noLowerCase");
				throw valexp;
			}else{
				logger.info("Contains Lowercase case");
			}
		
	}
	
	public ArrayList getRetailerCategoryList()throws VirtualizationServiceException{
		ArrayList retailerList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			retailerList = accountDao.getRetailerCategoryList();
			if(retailerList.isEmpty()){
				try {
					throw new SQLException(ExceptionCode.ERROR_RETAILER);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
				DBConnectionManager.releaseResources(connection);
		}
		return retailerList;
	}
	public ArrayList getOutletList() throws VirtualizationServiceException {
		ArrayList outletList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			outletList = accountDao.getOutletList();
			if(outletList.isEmpty()){
				try {
					throw new SQLException(ExceptionCode.ERROR_RETAILER);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
				DBConnectionManager.releaseResources(connection);
		}
		return outletList;
	}
	
	public ArrayList getAltChannelList() throws VirtualizationServiceException{

		ArrayList altChannelList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			altChannelList = accountDao.getAltChannelList();
			if(altChannelList.isEmpty()){
				try {
					throw new SQLException(ExceptionCode.ERROR_RETAILER);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
				DBConnectionManager.releaseResources(connection);
		}
		return altChannelList;
	}
	
	public ArrayList getChannelList() throws VirtualizationServiceException{


		ArrayList channelList = null;
		Connection connection = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
					.getDAOFactory(
							Integer
									.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
		
			channelList = accountDao.getChannelList();
			if(channelList.isEmpty()){
				try {
					throw new SQLException(ExceptionCode.ERROR_RETAILER);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
				DBConnectionManager.releaseResources(connection);
		}
		return channelList;
	}
	
	
	// For Boss
	public String [] getRetailerInfo(String mobilenumber)throws VirtualizationServiceException
	{
		String []retailerInfoArray = null;
		Connection connection = null;
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
		
			retailerInfoArray = accountDao.getRetailerInfo(mobilenumber);
			connection.commit();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : ",de);
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e) {
			logger.error(" Exception occured : Message : ",e);
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally 
		{
				DBConnectionManager.releaseResources(connection);
		}
		return retailerInfoArray;
	}

	
	/**
	 * getSWLocatorList method is called to return the complete list of all SW Locators
	 * 
	 * @return ArrayList<SWLocatorFormBean>
	 * @throws VirtualizationServiceException
	 */
	/*public ArrayList<SWLocatorListFormBean> getSWLocatorList() throws VirtualizationServiceException {

		Connection connection = null;
		ArrayList<SWLocatorListFormBean> swLocatorList = null;
		
		try {
			
			// get the database connection 
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory
			.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getNewAccountITHelpDao(connection);
			swLocatorList = accountDao.getSWLocatorList();

		} catch (Exception e) {
			if (e instanceof DAOException) {
				logger.error("DAOException occured:" + e.getMessage());
				throw new VirtualizationServiceException(e.getMessage());
			} else {
				logger.error("Exception occured:" + e.getMessage());
			}
		}
		return swLocatorList;
	}*/
	
//	 For Cannon merge on 8th August
	public String [] getRetailerInfoCannon(String mobilenumber)throws VirtualizationServiceException
	{
		String []retailerInfoArray = null;
		Connection connection = null;
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
		
			retailerInfoArray = accountDao.getRetailerInfoCannon(mobilenumber);
			connection.commit();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : ",de);
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e) {
			logger.error(" Exception occured : Message : ",e);
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally 
		{
				DBConnectionManager.releaseResources(connection);
		}
		return retailerInfoArray;
	}
	
	public String[] getRetailerDistInfoCannon(String mobilenumber, String pinCode) throws VirtualizationServiceException 
	{
		String []retailerInfoArray = null;
		Connection connection = null;
		try 
		{
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(
					ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
		
			retailerInfoArray = accountDao.getRetailerDistInfoCannon(mobilenumber, pinCode);
			connection.commit();
		}
		catch (DAOException de) {
			logger.error(" Exception occured : Message : ",de);
			throw new VirtualizationServiceException(de.getMessage());
		}
		catch (Exception e) {
			logger.error(" Exception occured : Message : ",e);
			throw new VirtualizationServiceException(e.getMessage());
		}
		finally 
		{
				DBConnectionManager.releaseResources(connection);
		}
		return retailerInfoArray;
	}
	
	
	public String getSelectedCircleList(String accountId) throws VirtualizationServiceException {
		Connection connection = null;
		// ArrayList aggregateList = null;
		String circleList;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			circleList = accountDao.getSelectedCircleList(accountId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::getSelectedCircleList ");
		return circleList;
	}

	public String getSelectedTransactions(String accountId) throws VirtualizationServiceException {
		Connection connection = null;
		// ArrayList aggregateList = null;
		String transacList;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			transacList = accountDao.getSelectedTransactions(accountId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::getSelectedTransactions ");
		return transacList;
	}


	
	/*
	 * Method to generate random GSD complaint password 
	 */
	public String generatePassword(String userLoginId)throws VirtualizationServiceException 
	 {		 
		String	strPassword ="";
		try
		{
			String upperChars = ResourceReader.getCoreResourceBundleValue(Constants.UPPER_CHARS_PASSWORD).trim();
			String lowerChars = ResourceReader.getCoreResourceBundleValue(Constants.LOWER_CHARS_PASSWORD).trim();
			String specialChars = ResourceReader.getCoreResourceBundleValue(Constants.SPECIAL_CHARS_PASSWORD).trim();
			
			//String upperChars = ResourceReader.getValueFromBundle(Constants.UPPER_CHARS_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE).trim();
			//String lowerChars = ResourceReader.getValueFromBundle(Constants.LOWER_CHARS_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE).trim();
			//String specialChars= ResourceReader.getValueFromBundle(Constants.SPECIAL_CHARS_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE).trim();
			Random randomGenerator =  new Random();
			
			int upperCharsLength = randomGenerator.nextInt(upperChars.length());
			int lowerCharsLength = randomGenerator.nextInt(lowerChars.length());
			int specialCharsLength = randomGenerator.nextInt(specialChars.length());
			
			String strUpperChar = "";	
			String strLowerChars = ResourceReader.getCoreResourceBundleValue(Constants.LOWER_CHARS_CONST).trim();

			int numericVal = Math.abs(new Random().nextInt())%99;
			int nextInt= (numericVal+1)%10;
			String strNumericVal="";	
			
			if (numericVal<10)
				strNumericVal = "0"+numericVal;
			else
				strNumericVal = ""+numericVal;

			strNumericVal = strNumericVal+nextInt;
			
			if(upperCharsLength>0)
			{
				strUpperChar =upperChars.substring(upperCharsLength-1, upperCharsLength);
			}
			else
				strUpperChar =upperChars.substring(0,1);

			int lowerCharsLengthIndex = (Math.abs(new Random().nextInt())%8)+5+((Math.abs(new Random().nextInt())+1)%6);
			strLowerChars = strLowerChars.substring(lowerCharsLengthIndex,lowerCharsLengthIndex+3);
			
			if (specialCharsLength>0)
			{
				specialChars =specialChars.substring(specialCharsLength-1, specialCharsLength);
			}
			else
				specialChars =specialChars.substring(0,1);
			
			if(lowerCharsLength>0)
			{
				 lowerChars =lowerChars.substring(lowerCharsLength-1,lowerCharsLength);
			}
			else
				lowerChars =lowerChars.substring(0,1);
			
			// Composing password :: One Upper case || Three lower case || One special char || One lower case
			
			strPassword = strUpperChar+strLowerChars+specialChars+strNumericVal+lowerChars;
			
			//logger.info("\n\nPassword : "+strPassword+"\n\n");
		}
		catch(Exception de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(ExceptionCode.User.ERROR_MAIL_SEND);
		}
			return strPassword;
	 }



	 


	 /*
	 * Method to send E-Mail to new user.
	 */
	public void sendMail(String managerEmailId,String userFirstName,String account, String password) 
	throws VirtualizationServiceException { 
		
		String subject = ResourceReader.getCoreResourceBundleValue(Constants.MAIL_NEW_USER_PASSWRD_SUBJECT).trim();
		
		try 
		{
			Properties prop = System.getProperties();
			prop.put(Constants.MAIL_SMTP_HOST, ResourceReader.getCoreResourceBundleValue(Constants.LOGIN_SMTP).trim());
			Session ses = Session.getDefaultInstance(prop, null);
			MimeMessage msg = new MimeMessage(ses);
			msg.setFrom(new InternetAddress(ResourceReader.getCoreResourceBundleValue(Constants.SENT_FROM_EMAIL).trim()));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(managerEmailId));
			msg.setSubject(subject);
			StringBuffer strMessage = new StringBuffer();
			String txtMessage = null;
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SURNAME));
			strMessage.append(" ");
			strMessage.append(userFirstName);
			strMessage.append(",");
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SPACE1));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_MSG));
			strMessage.append(" ");
			strMessage.append(account);
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SPACE2));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_PASSWRD_TEXT));
			strMessage.append(" : ");
			strMessage.append(password);
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SPACE3));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_REGARD));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SPACE4));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_REGARDBY));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_SPACE5));
			strMessage.append(ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_FOOTER));
			txtMessage = strMessage.toString();
			
			msg.setText(txtMessage);
			//logger.info("Mail : "+ txtMessage);
			msg.setText(txtMessage);
			msg.setSentDate(new java.util.Date());
			Transport.send(msg);
		
		} catch (javax.mail.internet.AddressException ae) {
			ae.printStackTrace();
			throw new VirtualizationServiceException(ae.getMessage());
		} catch (javax.mail.MessagingException me) {
			me.printStackTrace();
			throw new VirtualizationServiceException(me.getMessage());
		}
	}
	
	public String checkChildUnderParent(String circleList,String accountId)throws VirtualizationServiceException
	{		 
		String	strMessage ="";
		Connection connection = null;
		try {
			//ValidatorFactory.getDPAccountValidator().validateInsert(accountDto);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			strMessage = accountDao.checkChildUnderParent(circleList,accountId);
		}
		catch(Exception de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
			return strMessage;
	 }
	public ArrayList<SelectionCollection> getDistTransactionList()throws VirtualizationServiceException {
		logger.info("Starting... getDistTransactionList ");
		Connection connection = null;
		ArrayList<SelectionCollection> transactionList = null;
		try {
			/* get the database connection */
	connection = DBConnectionManager.getDBConnection();
	DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
			.getNewAccountITHelpDao(connection);

	transactionList = accountDao.getDistTransactionList();

	
} catch (DAOException de) {
	logger.error(" Exception occured : Message : " + de.getMessage());
	throw new VirtualizationServiceException(de.getMessage());
} finally {
	DBConnectionManager.releaseResources(connection);
}
logger.info("Executed ::::Successfully retrieved Transaction List ");
return transactionList;
}


	public String validateTSMTransChange(int accountID) throws VirtualizationServiceException {
		
		String	strMessage ="";
		Connection connection = null;
		try {
			//ValidatorFactory.getDPAccountValidator().validateInsert(accountDto);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			strMessage = accountDao.validateTSMTransChange(accountID);
		}
		catch(Exception de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
			return strMessage;
	}


	public ArrayList getTransactionBasedTypeTSMList(String searchType, String searchValue, UserSessionContext sessionContext, int parentCircleId, String parentCircleIdList, String accountLevelId, int lb, int ub, int accountLevel, DPCreateAccountDto accountDTO, int distTranctionId) throws VirtualizationServiceException 
	{
		logger.info("Starting... SearchType : " + searchType + " and Value : "
				+ searchValue);
		Connection connection = null;
		ArrayList accountList = null;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
//			long parentId = sessionContext.getId();
			if (sessionContext.getType().equalsIgnoreCase(
					Constants.USER_TYPE_EXTERNAL)) {
				accountList = accountDao.getTransactionBasedTypeTSMList(searchType,searchValue, parentCircleId,parentCircleIdList, accountLevelId, lb, ub,accountDTO,distTranctionId);
			} 
/*			else {
				accountList = accountDao.getParentAccountList(searchType,
						searchValue, parentId, parentCircle, accountLevelId,
						lb, ub, accountLevel);
			}	*/
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
	
	/* Added By parnika */
	
	public int getAccountGroupId(long accountId) throws VirtualizationServiceException {
		Connection connection = null;
		int groupId = 0;
		logger.info("asa::getAccountGroupId");
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			groupId = accountDao.getAccountGroupId(accountId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::getAccountGroupId ");
		return groupId;
	}
	/* End of changes by Parnika */

//Added by Sugandha
	public String checkDistAccountUpdateBoTree(int accountID) throws VirtualizationServiceException 
	{
		String	strMessage ="";
		Connection connection = null;
		try {
			//ValidatorFactory.getDPAccountValidator().validateInsert(accountDto);
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			strMessage = accountDao.checkDistAccountUpdateBoTree(accountID);
		}
		catch(Exception de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
			return strMessage;

	}

//Added by sugandha for termination of distributor BFR-10 DP-Release 3
	public ArrayList getTerminationList(long accountID) throws VirtualizationServiceException 
	{
		Connection connection = null;
		ArrayList terminationList = null;
		logger.info("accountID"+accountID);
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);
			terminationList = accountDao.getTerminationList(accountID);
		} 
		catch (DAOException de)
		{
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} 
		finally 
		{
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::Successfully retrieved Account List ");
		return terminationList;
	
	}

	public String terminateDist(int accountID, String terminationID, String distTranctionId) throws VirtualizationServiceException 
	{
		String	strMessage ="";
		Connection connection = null;
		try {
			connection = DBConnectionManager.getDBConnection();
			connection.setAutoCommit(false);
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
					.getNewAccountITHelpDao(connection);

			strMessage = accountDao.terminateDist(accountID,terminationID,distTranctionId);
		}
		catch(Exception de) {
			de.printStackTrace();
			logger.error("Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		}
	
		return strMessage;
	}


	public List getUserRoleList(int groupId)
			throws VirtualizationServiceException {
		List roleList = null;
		Connection connection = null;
		
			try {
				
				connection = DBConnectionManager.getDBConnection();
				DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader.getCoreResourceBundleValue(Constants.DATABASE_TYPE)))
						.getNewAccountITHelpDao(connection);			
				roleList = accountDao.getRoleList(groupId);
			} 
			catch (Exception e) {		
				logger.error("Exception occured :" + e.getMessage());
				e.printStackTrace();
				throw new VirtualizationServiceException(e.getMessage());
			}
		
		finally
		{							
			DBConnectionManager.releaseResources(connection);
		}
		return roleList;
	
		
	}
	
	public String getSelectedCircleListPAN(String accountId) throws VirtualizationServiceException {
		Connection connection = null;
		// ArrayList aggregateList = null;
		String circleList;
		try {
			/* get the database connection */
			connection = DBConnectionManager.getDBConnection();
			DPCreateAccountITHelpDao accountDao = DAOFactory.getDAOFactory(Integer.parseInt(ResourceReader
											.getCoreResourceBundleValue(Constants.DATABASE_TYPE))).getNewAccountITHelpDao(connection);
			circleList = accountDao.getSelectedCircleListPAN(accountId);
		} catch (DAOException de) {
			logger.error(" Exception occured : Message : " + de.getMessage());
			throw new VirtualizationServiceException(de.getMessage());
		} finally {
			DBConnectionManager.releaseResources(connection);
		}
		logger.info("Executed ::::getSelectedCircleList ");
		return circleList;
	}
}
