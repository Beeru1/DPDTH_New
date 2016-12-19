/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.actions;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;

import com.ibm.appsecure.exception.ValidationException;
import com.ibm.appsecure.service.GSDService;
import com.ibm.dp.beans.DPCreateAccountITHelpFormBean;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.RoleDto;
import com.ibm.dp.service.ChangeUserRoleService;
import com.ibm.dp.service.DPCommonService;
import com.ibm.dp.service.DPCreateAccountITHelpService;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.ChangeUserRoleServiceImpl;
import com.ibm.dp.service.impl.DPCommonServiceImpl;
import com.ibm.dp.service.impl.DPCreateAccountITHelpServiceImpl;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.dpmisreports.common.AppContext;
import com.ibm.dpmisreports.common.SelectionCollection;
import com.ibm.dpmisreports.common.SpringCacheUtility;
import com.ibm.dpmisreports.service.impl.DropDownUtilityAjaxServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationFactory;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.Login;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthenticationService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.LoginService;
import com.ibm.virtualization.recharge.service.impl.AuthenticationServiceImpl;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.LoginServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for account management responsible for. 1. Create New
 * Account 2. view\Search Account 3. Edit Account
 * 
 * @author Anju
 */
public class DPCreateAccountITHelpAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger.getLogger(DPCreateAccountITHelpAction.class
			.getName());

	
	public static final String OPEN_PARENT_ACCOUNT_LIST = "parentaccountlistITHelp";
	public static final String FIND_DISTRIBUTOR = "finddistributor"; //added by Rohit
	private static final String SHOW_DOWNLOAD_ACCOUNT_LIST = "listAccountExportItHelp";

	private static final String SHOW_DOWNLOAD_AGGREGATE_LIST = "listAggregateExport";

	private static final String OPEN_BILLABLE_ACCOUNT_LIST = "billablelist";

	private static final String STATUS_ERROR = "error";
	
	private static final String CREATE_ACCOUNT_IT_HELP = "createAccountITHelp";
	
	private static final String OPEN_ACCOUNT_IT_HELP = "openAccountITHelp"; 
	
	private ArrayList<String> existingCircleList = new ArrayList<String>();
	private ArrayList<String> existingTransacList = new ArrayList<String>();
	
	private String oldParentId="-1";
	
	/* Added By parnika */
	
	private String oldSalesTypeTSMId="-1";
	private String oldDepositeTypeTSMId="-1";
	
	
	/* End of changes by Parnika */
	private static ApplicationContext context = AppContext
	.getApplicationContext();

private SpringCacheUtility springCacheUtility = (SpringCacheUtility) context
.getBean("springCacheUtility");
	
	/**
	 * This met0hod initializes circle list from database  and group list open the create
	 * account jsp page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		logger.info(" Starting init... ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		try {
			/* Logged in user information from session */
			// get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_CREATE_ACCOUNT_IT_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			if (accountFormBean != null) {
				accountFormBean.reset();
			}
			accountFormBean.setAccountLevelId(sessionContext.getAccesslevel());
			accountFormBean.setAccessLevelId(sessionContext.getAccesslevel());
			accountFormBean.setHeirarchyId(sessionContext.getHeirarchyId());
			accountFormBean.setUserType(sessionContext.getType());
//			accountFormBean.setParentAccountId(loginUserId);
			accountFormBean.setParentAccountName(sessionContext.getAccountName());
			accountFormBean.setParentLoginName(sessionContext.getAccountCode());
			accountFormBean.setUserRole(sessionContext.getUserRole());
			
			//accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			//accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			
			accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
			accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
			accountFormBean.setSrNumber("");
			accountFormBean.setApproval1("");
			accountFormBean.setApproval2("");
			
			/*if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID &&  sessionContext.getGroupId()==Constants.ADMIN_ID) {
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			} else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID)) {
				accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
			}else */
			logger.info(" ***** login circle id === "+sessionContext.getCircleId());
			ArrayList<SelectionCollection> distTransList = accountService.getDistTransactionList();
			accountFormBean.setDistTranctionList(distTransList);
			session.setAttribute("distTranctionList", distTransList);
			
			accountFormBean.setTransactionTypeList(distTransList);
			session.setAttribute("transactionTypeList", distTransList);
			if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID)
			{
				logger.info(" ***** in 11111111 ifg cond ");
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
				accountFormBean.setAccountZoneId(0);
				session.setAttribute("zoneList", zoneList);
				accountFormBean.setZoneList(zoneList);
				
// STATE Drop Down for Edit Account
				ArrayList stateList = accountService.getStates(Integer.parseInt(accountFormBean.getCircleId()));
				accountFormBean.setAccountStateId(0);
				session.setAttribute("stateList", stateList);
				accountFormBean.setStateList(stateList);
				
				 
//		Added by ARTI for warehaouse list box BFR -11 release2 for Edit Account ARTI 
				ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
				accountFormBean.setAccountWarehouseCode("0");
				session.setAttribute("wareHouseList", wareHouseList);
				accountFormBean.setWareHouseList(wareHouseList);
		
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				//accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
				
			}
			
			if (accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID
					&& accountFormBean.getHeirarchyId() == Constants.HIERARCHY_1) {
				logger.info(" ***** in 22222 if cond ");
				ArrayList tradeList = accountService.getTradeList();
				session.setAttribute("tradeList", tradeList);
				accountFormBean.setTradeList(tradeList);
				accountFormBean.setTradeId(sessionContext.getTradeId());
				ArrayList categoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
				session.setAttribute("tradeCategoryList", categoryList);
				accountFormBean.setTradeCategoryList(categoryList);
				accountFormBean.setTradeCategoryId(sessionContext
						.getTradeCategoryId());
				accountFormBean.setShowNumbers("Y");
				accountFormBean
				.setShowBillableCode(Constants.ACCOUNT_BILLABLE_YES);
//				accountFormBean.setParentAccountId(loginUserId); 
				accountFormBean.setParentLoginName(sessionContext.getLoginName());
				accountFormBean.setParentAccountName(sessionContext.getAccountName());
				accountFormBean.setBillableCodeId(""+loginUserId);
				accountFormBean.setBillableCode(sessionContext.getAccountName());
				accountFormBean.setBillableAccountName(sessionContext.getAccountName());
				accountFormBean.setBillableLoginName(sessionContext.getLoginName());
				
				
				
				ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
				session.setAttribute("zoneList", zoneList);
				accountFormBean.setZoneList(zoneList);
				accountFormBean.setAccountZoneId(sessionContext.getAccountZoneId());
				ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
				
				// code for STATE start
				ArrayList stateList = accountService.getStates(Integer.parseInt(accountFormBean.getCircleId()));
				session.setAttribute("stateList", stateList);
				accountFormBean.setStateList(stateList);
				accountFormBean.setAccountStateId(sessionContext.getAccountStateId());
				
				//added by ARTI for warehaouse list box BFR -11 release2
				ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
				accountFormBean.setAccountWarehouseCode("0");
				session.setAttribute("wareHouseList", wareHouseList);
				accountFormBean.setWareHouseList(wareHouseList);
				
				
				// code for STATE ends
				session.setAttribute("cityList", cityList);
				accountFormBean.setCityList(cityList);
				accountFormBean.setAccountCityId(sessionContext.getAccountCityId()+"");
				
				ArrayList beatList = accountService.getAllDistBeats(Integer.parseInt(accountFormBean.getAccountCityId()), sessionContext.getId());
				session.setAttribute("beatList", beatList);
				accountFormBean.setBeatList(beatList);
				ArrayList outletList = accountService.getOutletList();
				
				session.setAttribute("outletList", outletList);
				accountFormBean.setOutletType(sessionContext.getOutletType());
		} 
			else 
		{
			logger.info(" ***** in else cond ");
				accountFormBean.setShowNumbers("N");
				accountFormBean.setShowBillableCode(Constants.ACCOUNT_BILLABLE_NO);
			}
				/* Get list of groups from Service Layer */
//				GroupService groupService = new GroupServiceImpl();
//				ArrayList groupDtoList = groupService
//						.getGroups(sessionContext.getGroupId()+"");
//				/* Create Group DropDown List */
//				session.setAttribute("groupList", groupDtoList);
				ArrayList accountLevelList = null;
				accountLevelList = accountService.getAccessLevelAssignedList(sessionContext.getId());
				session.setAttribute("accountLevelList", accountLevelList);
			/* Create state DropDown List */
				ArrayList circleList = accountService.getAllCircles();
				session.setAttribute("circleList", circleList);
				logger.info("circleList init function "+circleList.size());
				accountFormBean.setCircleId("0");
				logger.info("CircleId:"+accountFormBean.getCircleId());
				session.setAttribute("circleId", "0");
			// Distributor type
				ArrayList tradeList = accountService.getTradeList();
				session.setAttribute("tradeList", tradeList);
// retailer category : A, B,C, D and E
				ArrayList retailerCategoryList = accountService.getRetailerCategoryList();
				session.setAttribute("retailerCatList", retailerCategoryList);
	// outlet category		
				ArrayList outletList = accountService.getOutletList();
				session.setAttribute("outletList", outletList);
				accountFormBean.setOutletList(outletList);
	// alternate channel type : IFFCO and IOC		
				ArrayList altChannelList = accountService.getAltChannelList();
				session.setAttribute("altChannelTypeList", altChannelList);
	//added by sugandha transaction type for TSM -sales or deposite*******************************
				ArrayList<SelectionCollection> transactionTypeList = distTransList;
				session.setAttribute("transactionTypeList", transactionTypeList);
	// channel type		
				ArrayList channelList = accountService.getChannelList();
				session.setAttribute("channelTypeList", channelList);
				ArrayList generalList = accountService.getManagerList();
//			if(generalList.size()!=0){
//				accountFormBean.setFinanceList((ArrayList)generalList.get(0));
//				accountFormBean.setSalesList((ArrayList)generalList.get(1));
//				accountFormBean.setCommerceList((ArrayList)generalList.get(2));
//				}
				Date dt=new Date();
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				accountFormBean.setOpeningDt(formatter.format(dt));
			
//			 Load the SW Locator List
			//ArrayList<SWLocatorListFormBean> swConcatenatedLocatorList = accountService.getSWLocatorList();
			//session.setAttribute("swConcatenatedLocatorList", swConcatenatedLocatorList);
			
		} 
		catch (VirtualizationServiceException se) 
		{
			logger.error("Error Occured while retrieving Circle or group list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
		}
		catch (Exception ex) 
		{
			logger.error("Error Occured in init:CreateAccountAction ", ex);
			errors.add("errors", new ActionError(ex.getMessage()));
		}
		if (!errors.isEmpty()) 
		{
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		// accountFormBean.reset();
		return mapping.findForward("createAccountITHelp");
	}

	/**
	 * This method will be use to insert account data
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("... Started  createAccount");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		String default_password = accountService.generatePassword(accountFormBean.getAccountName());
		accountFormBean.setIPassword(default_password);
		accountFormBean.setCheckIPassword(default_password);
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String strSalesTSM = "";
		String strSalesTSMAccName = "";
		String strSalesTSMLogName = "";
		long longSalesTSMID = 0;
		String strCPETSM = "";
		String strCPETSMAccName = "";
		String strCPETSMLogName = "";
		long longCPETSMID = 0;
		
		try {		// get login ID from session
			
			
			long parentid = accountFormBean.getParentAccountId();
			logger.info("%%%% in Create Account function getAccountLevel == "+ accountFormBean.getAccountLevel() + " and getAccountLevelId =  "+accountFormBean.getAccountLevelId());
			logger.info("%%%% in Create Account function AccountWare house Code == "+ accountFormBean.getAccountWarehouseCode() );
			/* Added by Parnika for resetting Parent TSM */
			//strSalesTSM = accountFormBean.getSalesTypeTSM();
			strSalesTSM = accountFormBean.getSalesTypeTSMAccountName();
			strSalesTSMAccName = accountFormBean.getSalesTypeTSMAccountName();
			strSalesTSMLogName = accountFormBean.getSalesTypeTSMLoginName();
			longSalesTSMID = accountFormBean.getSalesTypeTSMId();
			
			//strCPETSM = accountFormBean.getDepositeTypeTSM();
			strCPETSM = accountFormBean.getDepositeTypeTSMAccountName();
			strCPETSMAccName = accountFormBean.getDepositeTypeTSMAccountName();
			strCPETSMLogName = accountFormBean.getDepositeTypeTSMLoginName();
			longCPETSMID = accountFormBean.getDepositeTypeTSMId();
			
			session.setAttribute("strSalesTSM",strSalesTSM) ;
			session.setAttribute("strSalesTSMAccName",strSalesTSMAccName) ;
			session.setAttribute("strSalesTSMLogName",strSalesTSMLogName) ;
			session.setAttribute("longSalesTSMID",longSalesTSMID) ;
			session.setAttribute("strCPETSM",strCPETSM) ;
			session.setAttribute("strCPETSMAccName",strCPETSMAccName) ;
			session.setAttribute("strCPETSMLogName",strCPETSMLogName) ;
			session.setAttribute("longCPETSMID",longCPETSMID) ;
			
			
			logger.info("strSalesTSM"+session.getAttribute("strSalesTSM")+"");
			logger.info("strSalesTSMAccName"+session.getAttribute("strSalesTSMAccName")+"");
			logger.info("strSalesTSMLogName"+session.getAttribute("strSalesTSMLogName")+"");
			logger.info("longSalesTSMID"+Long.parseLong(session.getAttribute("longSalesTSMID")+""));
			logger.info("strCPETSM"+session.getAttribute("strCPETSM")+"");
			logger.info("strCPETSMAccName"+session.getAttribute("strCPETSMAccName")+"");
			logger.info("strCPETSMLogName"+session.getAttribute("strCPETSMLogName")+"");
			logger.info("longCPETSMID"+Long.parseLong(session.getAttribute("longCPETSMID")+""));
			
			/* End of changes by Parnika */
			
			if(Integer.parseInt(accountFormBean.getAccountLevel())>Constants.DIST_LEVEL_ID)
			{
				accountFormBean.setAccountZoneId(accountFormBean.getHiddenZoneId());
				accountFormBean.setAccountCityId(accountFormBean.getHiddenCityId()+"");
				accountFormBean.setAccountCircleId(accountFormBean.getHiddenCircleId());
				accountFormBean.setOutletType(accountFormBean.getHiddenOutletType()+"");
			}
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_CREATE_ACCOUNT_IT_HELPDESK))
			{
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			accountFormBean.setHiddenZoneId(accountFormBean.getAccountZoneId());
			if(accountFormBean.getAccountCityId() !=null && !accountFormBean.getAccountCityId().trim().equals(""))
				accountFormBean.setHiddenCityId(Integer.parseInt(accountFormBean.getAccountCityId().trim()));
				
			accountFormBean.setHiddenStateId(String.valueOf(accountFormBean.getAccountStateId()));
			if(accountFormBean.getAccountWarehouseCode() !=null && !accountFormBean.getAccountWarehouseCode().trim().equals(""))
				accountFormBean.setHiddenWarehouseId(accountFormBean.getAccountWarehouseCode().trim());
				
			
			/* Check For HRMS Integration. */
			try {
				logger.info(" Checking HRMS Integration of Login Id = '"	+ accountFormBean.getAccountCode() + "'");
				DPCommonService commonService = new DPCommonServiceImpl();
				String validteHRMSFlag = ResourceReader.getCoreResourceBundleValue(Constants.HRMS_VALIDATION);
				if(validteHRMSFlag != null && validteHRMSFlag.equalsIgnoreCase("Y"))  {
				
				String hrmsResponse = commonService.isValidOLMID(accountFormBean.getAccountCode());
				logger.info("HRMS Integration Response == "+hrmsResponse);
				if(!hrmsResponse.equals(com.ibm.dp.common.Constants.DP_HRMS_SUCCESS_MSG))
				{
					logger.info(" Account Code is not integrated with HRMS");
					errors.add("errors.account", new ActionError(hrmsResponse));
					accountFormBean.setErrorFlag("true");
					saveErrors(request, errors);
					return mapping.findForward(CREATE_ACCOUNT_IT_HELP);
				} }
				logger.info(" Account Code is integrated with HRMS");
			} catch (Exception exp) 
			{
				if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID &&  sessionContext.getGroupId()==Constants.ADMIN_ID) 
				{
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				} 
				else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID)) {
					accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
				}
				else if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID)
				{
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				}

				
				if(null!=session.getAttribute("zoneList"))
					accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
				//Added by ARTI for warehaouse list box BFR -11 release2
				if(null!=session.getAttribute("wareHouseList"))
					accountFormBean.setStateList((ArrayList)session.getAttribute("wareHouseList"));
				if(null!=session.getAttribute("stateList"))
					accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
				
				if(null!=session.getAttribute("cityList"))
					accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
				if(null!=session.getAttribute("beatList"))
					accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
				if(null!=session.getAttribute("tradeCategoryList"))	
					accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
				logger.error(" caught Exception for HRMS Integration  "+ exp.getMessage(), exp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("validationerror", new ActionError("noHRMS"));
				accountFormBean.setErrorFlag("true");
				saveErrors(request, errors);
				
				return mapping.findForward(CREATE_ACCOUNT_IT_HELP);
			}
			
			
			/* Check if Account Code and Password are GSD Compliant. */
			try {
				logger.info(" Checking GSD compliance of Account  Code = '"	+ accountFormBean.getAccountCode() + "'");
				GSDService gSDService = new GSDService();
				if (accountFormBean.getAccountLevelId() < Constants.FSE_ID) 
				{
					// check for special, lower case and upper case characters.
					accountService.checkChars(accountFormBean.getIPassword());
					gSDService.validateCredentials(accountFormBean.getAccountCode(), accountFormBean.getIPassword());
					logger.info(" Account Code and Password are GSD Compliant.");
				}
			} 
			catch (ValidationException validationExp)
			{
				if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID &&  sessionContext.getGroupId()==Constants.ADMIN_ID) 
				{
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				} 
				else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID))
				{
					accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
				}
				else if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID)
				{
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				}

				if(validationExp.getMessage().equalsIgnoreCase("noUppercase")){
					errors.add("validationerror", new ActionError("noUppercase"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noSpecialCharacter")){
					errors.add("validationerror", new ActionError("noSpecialCharacter"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noLowerCase")){
					errors.add("validationerror", new ActionError("noLowerCase"));
				}	
				if(null!=session.getAttribute("zoneList"))
					accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
				//Added by ARTI for warehaouse list box BFR -11 release2
				if(null!=session.getAttribute("wareHouseList"))
					accountFormBean.setStateList((ArrayList)session.getAttribute("wareHouseList"));
				if(null!=session.getAttribute("stateList"))
					accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
				
				if(null!=session.getAttribute("cityList"))
					accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
				if(null!=session.getAttribute("beatList"))
					accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
				if(null!=session.getAttribute("tradeCategoryList"))	
					accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
				logger.error(" caught Exception for GSD  "+ validationExp.getMessage(), validationExp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("errors.account", new ActionError(validationExp.getMessageId(), acount_code));
				accountFormBean.setErrorFlag("true");
				saveErrors(request, errors);
				return mapping.findForward(CREATE_ACCOUNT_IT_HELP);
			}
			/* Level - 1 VR_LOGIN_MASTER */
			Login loginDto = new Login();
			/* transfer values from form bean to dto */
			loginDto.setLoginName(accountFormBean.getAccountCode());
			loginDto.setPassword(accountFormBean.getIPassword());
			loginDto.setStatus(Constants.ACC_STATUS);
			loginDto.setType(accountFormBean.getUserType());
			loginDto.setHeiarachyId(accountFormBean.getAccountLevelFlag());
			if(accountFormBean.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){
				accountFormBean.setTradeId(Integer.parseInt(accountFormBean.getTradeIdInBack()));
				accountFormBean.setTradeCategoryId(Integer.parseInt(accountFormBean.getTradeCategoryIdInBack()));
				accountFormBean.setIsbillable(accountFormBean.getIsBillableInBack());
			}
			ArrayList accountGroup = accountService.getAccountGroupId(Integer.parseInt(accountFormBean.getAccountLevel()));
			accountFormBean.setGroupId(accountGroup.get(0).toString());
			accountFormBean.setGroupName(accountGroup.get(1).toString());
			DPCreateAccountDto accountDto = new DPCreateAccountDto();
			// copy account form bean data into account dto
			
			//accountDto.setTransactionId(transactionId);
			BeanUtils.copyProperties(accountDto, accountFormBean);
			
			/* Added by Parnika for multiple Parent Hierarchy */
			accountDto.setHiddenTranctionIds(accountFormBean.getHiddenTranctionIds());
			System.out.println("accountFormBean.getHiddenTranctionIds():::"+accountFormBean.getHiddenTranctionIds());
			// For distributors only
			if(accountDto.getAccountLevelId() == 7){
				accountDto.setParentAccountId(0);
				if(accountFormBean.getHiddenTranctionIds().length() == 3){
					String selectedTransaction[] = new String[2];
					selectedTransaction[0] = accountFormBean.getHiddenTranctionIds().substring(0,accountFormBean.getHiddenTranctionIds().indexOf(',') );
					selectedTransaction[1] = accountFormBean.getHiddenTranctionIds().substring(accountFormBean.getHiddenTranctionIds().indexOf(',')+1,3 );
					System.out.println("selectedTransaction[0]::::"+selectedTransaction[0]);
					System.out.println("selectedTransaction[1]::::"+selectedTransaction[1]);				
					if(Integer.parseInt(selectedTransaction[0]) == 1 || Integer.parseInt(selectedTransaction[1]) == 1 ){
						accountDto.setSalesTypeTSMLoginName(accountFormBean.getSalesTypeTSMLoginName());
					}
					if(Integer.parseInt(selectedTransaction[0]) == 2 || Integer.parseInt(selectedTransaction[1]) == 2 ){
						accountDto.setDepositeTypeTSMLoginName(accountFormBean.getDepositeTypeTSMLoginName());
					}
					
				}
				else{
					String selectedTransaction = accountFormBean.getHiddenTranctionIds();
					if(Integer.parseInt(selectedTransaction) == 1 )
					{
						accountDto.setSalesTypeTSMLoginName(accountFormBean.getSalesTypeTSMLoginName());
					}
					if(Integer.parseInt(selectedTransaction) == 2 )
					{
						accountDto.setDepositeTypeTSMLoginName(accountFormBean.getDepositeTypeTSMLoginName());
					}
					
				}				
			}
			/* End of changes by Parnika */
			
			//getAccountLevelId ==1 for Admin, 2 for DTH admin and onwards
			if(accountDto.getAccountLevelId() == 1 || accountDto.getAccountLevelId() == 2 || accountDto.getAccountLevelId() == 14){
				accountDto.setParentAccountId(0);
			}
				accountDto.setCircleIdList(accountFormBean.getHiddenCircleIdList());
			if(accountDto.getAccountLevelId() == 7)
			{
				logger.info("In Dist Id check  hidden Transaction ids == "+accountFormBean.getHiddenTranctionIds());
				accountDto.setDistTranctionId(accountFormBean.getHiddenTranctionIds());
			}
			if(accountDto.getAccountLevelId() == 5)
			{
				logger.info("In Transaction Id check  hidden Transaction ids == "+accountFormBean.getHiddenTranctionId());
				accountDto.setTransactionId(accountFormBean.getHiddenTranctionId());
			}

			long userLoginId = sessionContext.getId();
			accountDto.setCreatedBy(userLoginId);
			accountDto.setUpdatedBy(userLoginId);
			loginDto.setLoginId(userLoginId);
			// if circle is disabled then set circle id same as login user
			// circle Id
			logger.info("createAccount::::1234343434"+accountDto.getAccountLevelId());
			if(accountDto.getAccountLevelId() ==1 || accountDto.getAccountLevelId() ==2)
			{
				if ((accountFormBean.getCircleId() == null)	|| (accountFormBean.getCircleId().equals(""))) 
				{
					accountDto.setCircleId(sessionContext.getCircleId());
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				}
			}
			accountDto.setBillableCodeId(sessionContext.getBillableAccId());

			// if account create for mtp or distributor( or any top level
			// account )
			if (accountDto.getAccountLevelId() == Constants.MOBILITY_ID) 
			{
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			// calling accountService method to validate and insert data
			logger.info(" Request to create a new account by user "	+ sessionContext.getId());
			
			logger.info(" Request to create a new account for the circle List ==  "	+ accountDto.getCircleIdList() + " and Circle == "+ accountDto.getCircleId() );
			
			//Commented by Shilpa
			accountService.createAccount(loginDto, accountDto);
			logger.info("+++++++++++++++Distttype*****"+accountDto.getDisttype());
			
			long acId=accountDto.getAccountId();
			
			if(accountFormBean.getAccountLevelId()== Constants.DISTRIBUTOR_ID)
			{
springCacheUtility.updateAccountUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
			}
			
			try
			{
				int accountLevelId = accountDto.getAccessLevelId();
				logger.info("accountLevelId : "+accountLevelId);			
				if(accountLevelId <=6 || accountLevelId==14)
				{
					accountService.sendMail(accountDto.getEmail(), accountDto.getAccountName(), accountDto.getAccountCode(), default_password);
					logger.info("Mail for new user password has been sent successfully");
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				logger.info("Error in sending mail for new User password  ::  "+e.getMessage());
				
				String err = ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_Critical);
				logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
			}
			
			if(accountFormBean.getAccountLevelId()== Constants.TSM_ID)
			{
				accountFormBean.setCircleId(String.valueOf(accountDto.getCircleId()));
				//springCacheUtility.updateAccountsSingleCircle(Integer.valueOf(accountFormBean.getCircleId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsSingleCircle(String.valueOf(accountFormBean.getAccountLevelId()),accountFormBean.getCircleId());
				
			}
			
			// set flag false for city so it would not get focused if any server
			// side error comes
			errors.add("message.account", new ActionError("message.account.insert_success"));
			saveErrors(request, errors);

			if (session.getAttribute("circleList") != null) 
			{
				session.removeAttribute("circleList");
			}
			if (session.getAttribute("groupList") != null) 
			{
				session.removeAttribute("groupList");
			}
			if (session.getAttribute("accountLevelList") != null)
			{
				session.removeAttribute("accountLevelList");
			}
			if (session.getAttribute("displayDetails") != null) 
			{
				session.removeAttribute("displayDetails");
			}
			if (session.getAttribute("cityList") != null) {
				session.removeAttribute("cityList");
			}
			logger.info(" SuccessFully Created new Account ");
		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing User Id", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (IllegalAccessException iaExp) {
			logger.error(" caught Exception while using BeanUtils.copyProperties()",				iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (InvocationTargetException itExp) {
			logger.error(					" caught Exception while using BeanUtils.copyProperties()",					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException se) {
			// if invalid account id then error on page

			if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID	&&  sessionContext.getGroupId()==Constants.ADMIN_ID)
			{
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			} 
			else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID)) 
			{
				accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
			}else if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID){
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			// Adding zone list to form bean in case of a validation exception
//			if(se.getMessage().equalsIgnoreCase("checkConsectiveChars")){
//				errors.add("validationerror", new ActionError("checkConsectiveChars"));
//			}
			logger.info("\n\n\nException Message:"+se.getMessage()+"\n\n\n");
			
			if(se.getMessage().equalsIgnoreCase("DupDist")){
				logger.info("\n\n\nInside Dup Dist\n\n\n");
				errors.add("errors.account", new ActionError("error.account.dupDisLoc"));	
			}
			
			if(se.getMessage().equalsIgnoreCase("noUppercase")){
				errors.add("validationerror", new ActionError("noUppercase"));
			}
			
			if(se.getMessage().equalsIgnoreCase("noSpecialCharacter")){
				errors.add("validationerror", new ActionError("noSpecialCharacter"));
			}
			if(se.getMessage().equalsIgnoreCase("noLowerCase")){
				errors.add("validationerror", new ActionError("noLowerCase"));
			}	
			
			//Changes by Karan on 4 June 2012
			if(se.getMessage().equalsIgnoreCase("dupDistLocator"))
			{
				logger.info("\n\n\nInside Dupdist\n\n\n");
				errors.add("errors.account", new ActionError("errors.account.dupDist"));
				
			}	
			
			
			
//			if(null!=session.getAttribute("circleList"))
//				accountFormBean.setZoneList((ArrayList)session.getAttribute("circleList"));
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
			if(null!=session.getAttribute("cityList"))
				accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
			if(null!=session.getAttribute("beatList"))
				accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
//			if(null!=accountFormBean.getAccountLevel()){
//				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
			if(null!=session.getAttribute("tradeCategoryList"))	
				accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
//			}
			
			/**
			 * SR1764968 to show state drop down after coming error Lapu number already exist
			 */
			if(null!=session.getAttribute("stateList"))
				accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
			//Added by ARTI for warehaouse list box BFR -11 release2
			if(null!=session.getAttribute("wareHouseList"))
				accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
			
			
			if(null!=session.getAttribute("outletList"))
				accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
			
			/* Added by Parnika */
			
			
			accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM")+"");
			accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName")+"");
			accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName")+"");
			accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID")+""));
			accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM")+"");
			accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName")+"");
			accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName")+"");
			accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID")+""));
			
			logger.info("strSalesTSM"+session.getAttribute("strSalesTSM")+"");
			logger.info("strSalesTSMAccName"+session.getAttribute("strSalesTSMAccName")+"");
			logger.info("strSalesTSMLogName"+session.getAttribute("strSalesTSMLogName")+"");
			logger.info("longSalesTSMID"+Long.parseLong(session.getAttribute("longSalesTSMID")+""));
			logger.info("strCPETSM"+session.getAttribute("strCPETSM")+"");
			logger.info("strCPETSMAccName"+session.getAttribute("strCPETSMAccName")+"");
			logger.info("strCPETSMLogName"+session.getAttribute("strCPETSMLogName")+"");
			logger.info("longCPETSMID"+Long.parseLong(session.getAttribute("longCPETSMID")+""));
					
			/* End of changes by parnika */
			
			
			logger.info("CPE TSM   :::  "+accountFormBean.getDepositeTypeTSM());
			
			logger.error(" Service Exception occured   " + se.getMessage());
			errors.add("errors.account", new ActionError(se.getMessage()));
			accountFormBean.setErrorFlag("true");
			saveErrors(request, errors);
			return mapping.findForward(CREATE_ACCOUNT_IT_HELP);
			//return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(OPEN_ACCOUNT_IT_HELP);
	}

	/**
	 * Method will be use to get Account information to Edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountInfoEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Started... ");
		// get login ID from session
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
			logger
					.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		//System.out.println("accountFormBean.getAccountLevelId()=========="+accountFormBean.getAccountLevelId());
		String accountId = request.getParameter("accountId");
		
		/* Added by Parnika for Distributors */
		
		int groupId = accountService.getAccountGroupId(Long.parseLong(accountId));
		
		if(groupId == Constants.DISTRIBUTOR_ID){
				getAccountDetailsDistributor(mapping, form, request, errors, accountId,accountFormBean.getAccountLevelId() );
		}
		else{
			getAccountDetails(mapping, form, request, errors, accountId);
		}

		
		/* End of changes by Parnika */
		
		
		if(accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
			return mapping.findForward(Constants.EDIT_ACCOUNT_DISTRIBUTOR);			
		}else if(accountFormBean.getAccountLevelId() < Constants.DISTRIBUTOR_ID){
			return mapping.findForward(Constants.EDIT_ACCOUNT);
		}else if(accountFormBean.getAccountLevelId() >= Constants.IT_HELP_DESK){
			return mapping.findForward(Constants.EDIT_ACCOUNT);
		}		
		return mapping.findForward(Constants.EDIT_ACCOUNT);
	}

	/**
	 * Method will be use to get Account information to View
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAccountInfoView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Started... ");
		
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
			logger
					.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		String accountId = request.getParameter("accountId");
		getAccountDetails(mapping, form, request, errors, accountId);	
		return mapping.findForward(Constants.VIEW_ACCOUNT);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param errors
	 * @param accountId
	 * @throws DAOException
	 * @throws Exception 
	 */
	private ActionForward getAccountDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, ActionErrors errors,
			String accountId) throws DAOException {
		try {
			
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			DPCreateAccountDto accountDto = accountService.getAccount(Long.parseLong(accountId));
			oldParentId = accountDto.getOldParentAccountId();
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			String searchType = accountFormBean.getSearchFieldName();
			String searchValue = accountFormBean.getSearchFieldValue();
			System.out.println("ARTI ------------->accountFormBean"+accountDto.getAccountWarehouseName()+accountDto.getAccountWarehouseCode());
			/** Copying the DTO object data to Form Bean Objects */
			BeanUtils.copyProperties(accountFormBean, accountDto);
			System.out.println("ARTI ------------->accountFormBean"+accountFormBean.getAccountWarehouseName()+accountFormBean.getAccountWarehouseCode());
			System.out.println("------accountFormBean.getAccountLevelId()======="+accountFormBean.getAccountLevelId());
			// This where mobile nos come
			accountFormBean.setSearchFieldName(searchType);
			accountFormBean.setSearchFieldValue(searchValue);
			accountFormBean.setStatus(accountDto.getStatus().trim());
			accountFormBean.setCircleName(accountDto.getCircleName());
			//****************
			//accountFormBean.setAccountLevelId(account.getAccessLevelId());
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
				/* Get list of groups from Service Layer */
				
			accountFormBean.setUserType(sessionContext.getType());
			/* Create state DropDown List */
			ArrayList circleList = accountService.getAllCircles();
			session.setAttribute("circleList", circleList);
			/* Create Circle DropDown List */
			accountFormBean.setCircleId(String.valueOf(accountDto.getCircleId()));
			
			ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
			session.setAttribute("zoneList", zoneList);
			accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
			
			accountFormBean.setAccountStateId(accountDto.getAccountStateId());
			ArrayList stateList = accountService.getStates(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setStateList(stateList);
			session.setAttribute("stateList", stateList);
			
			ArrayList<SelectionCollection> distTransList = accountService.getDistTransactionList();
			accountFormBean.setDistTranctionList(distTransList);
			session.setAttribute("distTranctionList", distTransList);
			
			ArrayList<SelectionCollection> transactionTypeList = distTransList;
			session.setAttribute("transactionTypeList", transactionTypeList);
			accountFormBean.setTransactionId(accountDto.getTransactionId());
			//Added by ARTI for warehaouse list box BFR -11 release2
			ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setAccountWarehouseCode(accountDto.getAccountWarehouseCode());
			session.setAttribute("wareHouseList", wareHouseList);
			accountFormBean.setWareHouseList(wareHouseList);
			
			ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
			session.setAttribute("cityList", cityList);
			ArrayList beatList = accountService.getAllBeats(Integer.parseInt(accountFormBean.getAccountCityId()));
			session.setAttribute("beatList", beatList);
			
			// ***** Code Added by Shilpa for circle list Display or not Starts here 
			logger.info("in getAccountDetails accountlevel id  == "+accountFormBean.getAccountLevelId());
			String selectedCircleList = "";
			existingCircleList = new ArrayList<String>();
			//Added by sugandha to make change in dit circle admin multicircle as per BFR-61 DP-PHASE-3
			if(accountFormBean.getAccountLevelId() == 3 || accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6 || accountFormBean.getAccountLevelId() ==15){
				accountFormBean.setShowCircle("N");
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectedCircleList = accountService.getSelectedCircleList(accountId);
				logger.info("***** selectedCircleList == "+selectedCircleList);
				String[] strExistCircle = selectedCircleList.split(",");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				logger.info("existingCircleList  ===  "+existingCircleList);
				
			}
			else
			{
				accountFormBean.setShowCircle("A");
			}
			
			accountFormBean.setHiddenCircleIdList(selectedCircleList);
//			****** Code Added by Shilpa for circle list Display or not Ends here
			String selectedTransactions = "";
			existingTransacList = new ArrayList<String>();
			
			if(accountFormBean.getAccountLevelId() == 7)
			{
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectedTransactions = accountService.getSelectedTransactions(accountId);
				logger.info("***** selectedCircleList == "+selectedTransactions);
				String[] strExistTran = selectedTransactions.split(",");
				for(int count=0;count<strExistTran.length;count++){
					existingTransacList.add(strExistTran[count]);
				}			
				accountFormBean.setHiddenTranctionIds(selectedTransactions);
			}
			
			
			String selectTransactions = "";
			existingTransacList = new ArrayList<String>();
			if(accountFormBean.getAccountLevelId() == 5)
			{
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectTransactions = accountService.getSelectedTransactions(accountId);
				logger.info("***** selectedCircleList == "+selectTransactions);
				String[] strExistTran = selectTransactions.split(",");
				for(int count=0;count<strExistTran.length;count++){
					existingTransacList.add(strExistTran[count]);
				}				
				accountFormBean.setHiddenTranctionIds(selectTransactions);
			}
			
			accountFormBean.setHiddenTranctionId(accountDto.getTransactionId());
			
			if(accountFormBean.getAccountLevelId() >= Constants.DISTRIBUTOR_ID)
			{
				accountFormBean.setShowBillableCode(Constants.ACCOUNT_BILLABLE_YES);
				ArrayList tradeList = accountService.getTradeList();
				accountFormBean.setTradeList(tradeList);
				ArrayList categoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
				accountFormBean.setTradeCategoryList(categoryList);
//				 retailer category : A, B,C, D and E
				ArrayList retailerCategoryList = accountService.getRetailerCategoryList();
				session.setAttribute("retailerCatList", retailerCategoryList);
				accountFormBean.setRetailerCatList(retailerCategoryList);
		// outlet category		
				ArrayList outletList = accountService.getOutletList();
				accountFormBean.setOutletList(outletList);
				session.setAttribute("outletList", outletList);
		// alternate channel type : IFFCO and IOC		
				ArrayList altChannelList = accountService.getAltChannelList();
				session.setAttribute("altChannelTypeList", altChannelList);
				accountFormBean.setAltChannelTypeList(altChannelList);
		// channel type		
				ArrayList channelList = accountService.getChannelList();
				session.setAttribute("channelTypeList", channelList);
				accountFormBean.setChannelTypeList(channelList);
				
				// Add by harbans
				accountFormBean.setTinDt(accountDto.getTinDt());
				accountFormBean.setCstDt(accountDto.getCstDt());
				
//				 Load the SW Locator List
				//ArrayList<SWLocatorListFormBean> swConcatenatedLocatorList = accountService.getSWLocatorList();
				//session.setAttribute("swConcatenatedLocatorList", swConcatenatedLocatorList);
				
			}
			else
			{
				accountFormBean.setShowBillableCode(Constants.ACCOUNT_BILLABLE_NO);
			}
		} 
		catch (NumberFormatException numExp) 
		{
			logger.error(" caught Exception while Parsing accountId "
					+ accountId, numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
		} 
		catch (IllegalAccessException iaExp)
		{
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
		} 
		catch (InvocationTargetException itExp) 
		{
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
		}
		catch (VirtualizationServiceException vse) 
		{
			logger.error("  Service Exception occured   ", vse);
			errors.add("errors.account", new ActionError(vse.getMessage()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		return null;
	}
	/* Added by Parnika  for distributors detail*/
	
	private ActionForward getAccountDetailsDistributor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request, ActionErrors errors,
			String accountId, int accountLevel) throws DAOException {
		try {
			
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			DPCreateAccountDto accountDto = accountService.getAccountDistributor(Long.parseLong(accountId));
			oldDepositeTypeTSMId = accountDto.getOldDepositeTypeTSMId();
			oldSalesTypeTSMId = accountDto.getOldSalesTypeTSMId();
			oldParentId = accountDto.getOldParentAccountId();
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			String searchType = accountFormBean.getSearchFieldName();
			String searchValue = accountFormBean.getSearchFieldValue();
			System.out.println("ARTI ------------->accountFormBean"+accountDto.getAccountWarehouseName()+accountDto.getAccountWarehouseCode());
			/** Copying the DTO object data to Form Bean Objects */
			BeanUtils.copyProperties(accountFormBean, accountDto);
			System.out.println("ARTI ------------->accountFormBean"+accountFormBean.getAccountWarehouseName()+accountFormBean.getAccountWarehouseCode());
			System.out.println("------accountFormBean.getAccountLevelId()======="+accountFormBean.getAccountLevelId());
			// This where mobile nos come
			accountFormBean.setSearchFieldName(searchType);
			accountFormBean.setSearchFieldValue(searchValue);
			accountFormBean.setStatus(accountDto.getStatus().trim());
			accountFormBean.setCircleName(accountDto.getCircleName());
			//****************
			//accountFormBean.setAccountLevelId(account.getAccessLevelId());
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
				/* Get list of groups from Service Layer */
				
			accountFormBean.setUserType(sessionContext.getType());
			/* Create state DropDown List */
			ArrayList circleList = accountService.getAllCircles();
			session.setAttribute("circleList", circleList);
			/* Create Circle DropDown List */
			accountFormBean.setCircleId(String.valueOf(accountDto.getCircleId()));
			
			ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
			session.setAttribute("zoneList", zoneList);
			accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
			
			accountFormBean.setAccountStateId(accountDto.getAccountStateId());
			ArrayList stateList = accountService.getStates(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setStateList(stateList);
			session.setAttribute("stateList", stateList);
			
			ArrayList<SelectionCollection> distTransList = accountService.getDistTransactionList();
			accountFormBean.setDistTranctionList(distTransList);
			session.setAttribute("distTranctionList", distTransList);
			
			ArrayList<SelectionCollection> transactionTypeList = distTransList;
			session.setAttribute("transactionTypeList", transactionTypeList);
			accountFormBean.setTransactionId(accountDto.getTransactionId());
			

			//Added by ARTI for warehaouse list box BFR -11 release2
			ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setAccountWarehouseCode(accountDto.getAccountWarehouseCode());
			session.setAttribute("wareHouseList", wareHouseList);
			accountFormBean.setWareHouseList(wareHouseList);
			
			ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
			session.setAttribute("cityList", cityList);
			ArrayList beatList = accountService.getAllBeats(Integer.parseInt(accountFormBean.getAccountCityId()));
			session.setAttribute("beatList", beatList);

			//Added by sugandha to terminate user (distributor)on edit screen (terminationlist) BFR-10 DP-Phase-3
			logger.info("getAccountDetailsDistributor");
			if(accountFormBean.getAccountLevelId() == 7)
			{
				logger.info("***********inside getAccountDetailsDistributor****");
				ArrayList terminationList = accountService.getTerminationList(accountDto.getAccountId());
				session.setAttribute("terminationList", terminationList);
				accountFormBean.setTerminationList(terminationList);
				logger.info("terminationList::::::::::::::"+terminationList.size());
			}
			//changes by sugandha ends here  BFR-10 DP-Phase-3
			

			
			// ***** Code Added by Shilpa for circle list Display or not Starts here 
			logger.info("in getAccountDetails accountlevel id 12345 == "+accountFormBean.getAccountLevelId());
			String selectedCircleList = "";
			existingCircleList = new ArrayList<String>();
			if(accountFormBean.getAccountLevelId() == 3 || accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6){
				accountFormBean.setShowCircle("N");
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectedCircleList = accountService.getSelectedCircleList(accountId);
				logger.info("***** selectedCircleList == "+selectedCircleList);
				String[] strExistCircle = selectedCircleList.split(",");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				
			}else{
				accountFormBean.setShowCircle("A");
			}
			
			accountFormBean.setHiddenCircleIdList(selectedCircleList);
//			****** Code Added by Shilpa for circle list Display or not Ends here
			String selectedTransactions = "";
			existingTransacList = new ArrayList<String>();
			
			if(accountFormBean.getAccountLevelId() == 7){
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectedTransactions = accountService.getSelectedTransactions(accountId);
				logger.info("***** selectedCircleList == "+selectedTransactions);
				String[] strExistTran = selectedTransactions.split(",");
				for(int count=0;count<strExistTran.length;count++){
					existingTransacList.add(strExistTran[count]);
				}
			}
			accountFormBean.setHiddenTranctionIds(selectedTransactions);
			
			/* Commented By parnika
			String selectTransactions = "";
			existingTransacList = new ArrayList<String>();
			if(accountFormBean.getAccountLevelId() == 5){
				accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
				accountFormBean.setHiddenZoneId(accountDto.getAccountZoneId());
				selectTransactions = accountService.getSelectedTransactions(accountId);
				logger.info("***** selectedCircleList == "+selectTransactions);
				String[] strExistTran = selectTransactions.split(",");
				for(int count=0;count<strExistTran.length;count++){
					existingTransacList.add(strExistTran[count]);
				}
			}
			accountFormBean.setHiddenTranctionIds(selectTransactions);
			
			End of commented by Parnika */
			accountFormBean.setHiddenTranctionId(accountDto.getTransactionId());
			
			if(accountFormBean.getAccountLevelId() >= Constants.DISTRIBUTOR_ID){
				accountFormBean.setShowBillableCode(Constants.ACCOUNT_BILLABLE_YES);
				ArrayList tradeList = accountService.getTradeList();
				accountFormBean.setTradeList(tradeList);
				ArrayList categoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
				accountFormBean.setTradeCategoryList(categoryList);
//				 retailer category : A, B,C, D and E
				ArrayList retailerCategoryList = accountService.getRetailerCategoryList();
				session.setAttribute("retailerCatList", retailerCategoryList);
				accountFormBean.setRetailerCatList(retailerCategoryList);
		// outlet category		
				ArrayList outletList = accountService.getOutletList();
				accountFormBean.setOutletList(outletList);
				session.setAttribute("outletList", outletList);
		// alternate channel type : IFFCO and IOC		
				ArrayList altChannelList = accountService.getAltChannelList();
				session.setAttribute("altChannelTypeList", altChannelList);
				accountFormBean.setAltChannelTypeList(altChannelList);
		// channel type		
				ArrayList channelList = accountService.getChannelList();
				session.setAttribute("channelTypeList", channelList);
				accountFormBean.setChannelTypeList(channelList);
				
				// Add by harbans
				accountFormBean.setTinDt(accountDto.getTinDt());
				accountFormBean.setCstDt(accountDto.getCstDt());
				
//				 Load the SW Locator List
				//ArrayList<SWLocatorListFormBean> swConcatenatedLocatorList = accountService.getSWLocatorList();
				//session.setAttribute("swConcatenatedLocatorList", swConcatenatedLocatorList);
				
			}else{
				accountFormBean.setShowBillableCode(Constants.ACCOUNT_BILLABLE_NO);
			}
		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing accountId "
					+ accountId, numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
		} catch (IllegalAccessException iaExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
		} catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
		} catch (VirtualizationServiceException vse) {
			logger.error("  Service Exception occured   ", vse);
			errors.add("errors.account", new ActionError(vse.getMessage()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		return null;
	}
	 
	 /* End of changes by Parnika */
	
	
	/**
	  This function will update the account Edit information
	  
	  @param mapping
	  @param form
	  @param request
	  @param response
	  @return
	  @throws Exception
	 */
	public ActionForward updateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started :: updateAccount of IT Help....");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		DPCreateAccountDto accountDto = new DPCreateAccountDto();
		HttpSession session = request.getSession();
		DPCreateAccountITHelpService accountService = null;
		String selectedCircleList = "";
		String selectedTransactions ="";
		/* Added by Parnika */
		
		String strSalesTSM = "";
		String strSalesTSMAccName = "";
		String strSalesTSMLogName = "";
		long longSalesTSMID = 0;
		String strCPETSM = "";
		String strCPETSMAccName = "";
		String strCPETSMLogName = "";
		long longCPETSMID = 0;
		String[] strExistCircle=null ;
		
		/* End of changes by Parnika */
		try {
			// get login ID from session
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			Integer circleId = sessionContext.getCircleId();
			accountService = new DPCreateAccountITHelpServiceImpl();
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			int accountLevel = accountFormBean.getAccountLevelId();
			
			logger.info("Account level in updateAccount ::  "+accountLevel);
			
			/* Added by Parnika for resetting Parent TSM */
			//strSalesTSM = accountFormBean.getSalesTypeTSM();
			if(accountFormBean.getSalesTypeTSMAccountName()!=null)
			{
				strSalesTSM = accountFormBean.getSalesTypeTSMAccountName();
				strSalesTSMAccName = accountFormBean.getSalesTypeTSMAccountName();
			}
				
			if(accountFormBean.getSalesTypeTSMLoginName()!=null)
			{
				strSalesTSMLogName = accountFormBean.getSalesTypeTSMLoginName();
			}
						
			longSalesTSMID = accountFormBean.getSalesTypeTSMId();	
			
			//strCPETSM = accountFormBean.getDepositeTypeTSM();
			if(accountFormBean.getDepositeTypeTSMAccountName() != null){
				strCPETSM = accountFormBean.getDepositeTypeTSMAccountName();
				strCPETSMAccName = accountFormBean.getDepositeTypeTSMAccountName();
			}
			if(accountFormBean.getDepositeTypeTSMLoginName() != null){
				strCPETSMLogName = accountFormBean.getDepositeTypeTSMLoginName();
			}
					
			longCPETSMID = accountFormBean.getDepositeTypeTSMId();
			
			session.setAttribute("strSalesTSM",strSalesTSM) ;
			session.setAttribute("strSalesTSMAccName",strSalesTSMAccName) ;
			session.setAttribute("strSalesTSMLogName",strSalesTSMLogName) ;
			session.setAttribute("longSalesTSMID",longSalesTSMID) ;
			session.setAttribute("strCPETSM",strCPETSM) ;
			session.setAttribute("strCPETSMAccName",strCPETSMAccName) ;
			session.setAttribute("strCPETSMLogName",strCPETSMLogName) ;
			session.setAttribute("longCPETSMID",longCPETSMID) ;
			
//			logger.info("getting initially set IDs");
//			logger.info("strSalesTSM  ::  "+session.getAttribute("strSalesTSM").toString());
//			logger.info("strSalesTSMAccName  ::  "+session.getAttribute("strSalesTSMAccName").toString());
//			logger.info("strSalesTSMLogName  ::  "+session.getAttribute("strSalesTSMLogName").toString());
//			logger.info("longSalesTSMID  ::  "+Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
//			logger.info("strCPETSM  ::  "+session.getAttribute("strCPETSM").toString());
//			logger.info("strCPETSMAccName  ::  "+session.getAttribute("strCPETSMAccName").toString());
//			logger.info("strCPETSMLogName  ::  "+session.getAttribute("strCPETSMLogName").toString());
//			logger.info("longCPETSMID  ::  "+Long.parseLong(session.getAttribute("longCPETSMID").toString()));
			
			/* End of changes by Parnika */
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			/* Check For HRMS Integration. */
			try 
			{
				logger.info(" Checking HRMS Integration of Login Id = '"	+ accountFormBean.getAccountCode() + "'");
				DPCommonService commonService = new DPCommonServiceImpl();
				String validteHRMSFlag = ResourceReader.getCoreResourceBundleValue(Constants.HRMS_VALIDATION);
								
				if(accountFormBean.getStatus() != null && accountFormBean.getStatus().equalsIgnoreCase("A")) 
				{
					if(validteHRMSFlag != null && validteHRMSFlag.equalsIgnoreCase("Y"))  
					{
						String hrmsResponse = commonService.isValidOLMID(accountFormBean.getAccountCode());
						//String hrmsResponse = "INVALID";
						logger.info("HRMS Integration Response == "+hrmsResponse);
						if(!hrmsResponse.equals(com.ibm.dp.common.Constants.DP_HRMS_SUCCESS_MSG))
						{
							logger.info("\n\n\naccount not active in HRMS\n\n\n");
							errors.add("error.account", new ActionError(hrmsResponse));	
								
							logger.error(" Service Exception occured :  "+"Username is not valid in HRMS System");
							saveErrors(request, errors);
							logger.info(accountFormBean.getAccountLevelId());
							//Added by suagndha to fix bug for BFR-61 DP-PHASE-3 to make circleadmin multicircle
							if(accountFormBean.getAccountLevelId() == 3 || accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6 ||accountFormBean.getAccountLevelId() == 15){
								accountFormBean.setShowCircle("N");
								
								selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
								logger.info("***** selectedCircleListgetSelectedCircleList== "+selectedCircleList);
								strExistCircle = selectedCircleList.split(",");
								for(int count=0;count<strExistCircle.length;count++){
									existingCircleList.add(strExistCircle[count]);
								}
								
							}else
							{
								accountFormBean.setShowCircle("A");
							}
							
							if(accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID)
							{
								System.out.println("editAccountDistributorError...............");
								return mapping.findForward("editAccountDistributorError");			
							}
							else if(accountFormBean.getAccountLevelId() < Constants.DISTRIBUTOR_ID)
							{
								return mapping.findForward(Constants.EDIT_ACCOUNT);
							}
							else if(accountFormBean.getAccountLevelId() >= Constants.IT_HELP_DESK)
							{
								return mapping.findForward(Constants.EDIT_ACCOUNT);
							}
						}
					}
				}
				logger.info("inside update account circle change"+accountFormBean.getAccountLevelId());
				//Added by sugandha to fix bug BFR-61 Phase3accountFormBean.getAccountLevelId() == 3
				if(accountFormBean.getAccountLevelId() == 3 || accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 
					|| accountFormBean.getAccountLevelId() == 6 ||accountFormBean.getAccountLevelId() == 15)
				{
					accountFormBean.setShowCircle("N");
					
					selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
					logger.info("***** selectedCircleList == "+selectedCircleList);
					if(selectedCircleList!=null)
					strExistCircle = selectedCircleList.split(",");
					else
						logger.info("selecte circle list is blank ::");
					for(int count=0;count<strExistCircle.length;count++)
						existingCircleList.add(strExistCircle[count]);
				}
				else
				{
					if(accountFormBean.getAccountLevelId() == 7)
					{
						selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
						logger.info("***** selectedTransactions == "+selectedTransactions);
						String[] strExistTran = selectedTransactions.split(",");
						existingTransacList= new ArrayList<String>();
						
						for(int count=0;count<strExistTran.length;count++)
							existingTransacList.add(strExistTran[count]);
						
						/* Added by Parnika */
						accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
						accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
						accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
						accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
						accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
						accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
						accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
						accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
						
						
						/* End of changes by parnika */
					}
					accountFormBean.setShowCircle("A");
				}
			}
			catch (Exception vse) 
			{
				if(accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6){
					accountFormBean.setShowCircle("N");
					
					selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
					logger.info("***** selectedCircleList == "+selectedCircleList);
					if(selectedCircleList!=null)
						strExistCircle = selectedCircleList.split(",");
						else
							logger.info("selecte circle list is blank ::");
					for(int count=0;count<strExistCircle.length;count++){
						existingCircleList.add(strExistCircle[count]);
					}
					
				}else
				{
					accountFormBean.setShowCircle("A");
					if(accountFormBean.getAccountLevelId() == 7)
					{
						selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
						logger.info("***** selectedTransactions == "+selectedTransactions);
						String[] strExistTran = selectedTransactions.split(",");
						existingTransacList = new ArrayList<String>();
						for(int count=0;count<strExistTran.length;count++)
						{
							existingTransacList.add(strExistTran[count]);
						}
						/* Added by Parnika */
						accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
						accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
						accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
						accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
						accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
						accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
						accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
						accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
						/* End of changes by parnika */
					}
				}
				
				if(null!=session.getAttribute("zoneList"))
					accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
				if(null!=session.getAttribute("cityList"))
					accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
				if(null!=session.getAttribute("beatList"))
					accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
				if(null!=session.getAttribute("stateList"))
					accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
				if(null!=session.getAttribute("wareHouseList"))
					accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
				if(null!=session.getAttribute("outletList"))
					accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
				vse.printStackTrace();
				
				if(vse.getMessage().equalsIgnoreCase("DupDist")){
					logger.info("\n\n\nInside Dup Dist\n\n\n");
					errors.add("error.account", new ActionError("error.account.dupDisLoc"));	
				}
				else if(vse.getMessage().equalsIgnoreCase("error.account.billableisinactive")){
					logger.info("\n\n\nIerror.account.billableisinactive\n\n\n");
					errors.add("error.account", new ActionError("error.account.billableisinactive"));	
				}
				else
				{
					errors.add("error.account", new ActionError(vse.getMessage()));	
				}
				
				logger.error(" Service Exception occured :  "+"Entered Distributor Locator Code Already Exist");
				saveErrors(request, errors);
				if(accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
					return mapping.findForward(Constants.EDIT_ACCOUNT_DISTRIBUTOR);			
				}else if(accountFormBean.getAccountLevelId() < Constants.DISTRIBUTOR_ID){
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}else if(accountFormBean.getAccountLevelId() >= Constants.IT_HELP_DESK){
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}
				//return mapping.findForward(Constants.EDIT_ACCOUNT);
			}

			// set flag false for city so it would not get focused if any server
			// side error comes
			accountFormBean.setFlagForCityFocus(false);
			accountFormBean.setFlagForCategoryFocus(false);
			BeanUtils.copyProperties(accountDto, accountFormBean);
			accountDto.setUserType(sessionContext.getType());
			accountDto.setUpdatedBy(sessionContext.getId());
			logger.info("New Transaction type  ::  "+accountDto.getTransactionId());
			logger.info("In Update Account  is billable ===" + accountFormBean.getIsbillable());
			/*if(accountFormBean.getIsbillable() ==null){
				accountDto.setIsbillable(Constants.ACCOUNT_BILLABLE_YES);
			}*/
			logger.info("In Update Account Existing circle Id list Size == "+existingCircleList.size());
			logger.info("In Update Account circle Id list == "+accountFormBean.getHiddenCircleIdList());
			accountDto.setCircleIdList(accountFormBean.getHiddenCircleIdList());
			int isCircleChanged =0;
			if(accountFormBean.getHiddenCircleIdList()!=null)
			{
				String[] newCircleList = accountFormBean.getHiddenCircleIdList().split(",");
				if(newCircleList.length != existingCircleList.size()){
					logger.info(" Size is not equal");
					isCircleChanged =1;
				}
				else
				{
					Integer intNewCircleVal = 0; 
					for(int count=0; count<newCircleList.length;count++)
					{
						intNewCircleVal = Integer.parseInt(newCircleList[count]);
						logger.info(" Size is equal and curent value is in new list == "+intNewCircleVal);
						if(!existingCircleList.contains(intNewCircleVal.toString())){
							logger.info(" Size is equal but value is not in list == "+newCircleList[count]);
							isCircleChanged =1;
						}
					}
				}
		}
			existingCircleList =null;
			existingCircleList =new ArrayList();
			logger.info(" is circle List changed131231323424 == "+isCircleChanged);
			accountDto.setIsCircleChanged(isCircleChanged);
			
			//*********************** Added by Shilpa to get New Added Transactions if any
			accountDto.setDistTranctionId(accountFormBean.getHiddenTranctionIds());
			String addedTransacId ="";
			logger.info("*************hiddentransaction********"+accountFormBean.getHiddenTranctionIds());
			logger.info("*************existingTransacList********"+existingTransacList);
			
			if(accountFormBean.getHiddenTranctionIds()!=null && !accountFormBean.getHiddenTranctionIds().trim().equals(""))
			{
				String[] newTranList = accountFormBean.getHiddenTranctionIds().split(",");
				logger.info("*********newTranList**********"+newTranList);
				if(newTranList.length != existingTransacList.size())
				{
					logger.info(" Size is not equal");
					Integer intNewTransVal = 0; 
					for(int count=0; count<newTranList.length;count++)
					{
						intNewTransVal = Integer.parseInt(newTranList[count]);
						logger.info("curent value is in new list == "+intNewTransVal);
						if(!existingTransacList.contains(intNewTransVal.toString()))
						{
							logger.info("value is not in list == "+newTranList[count]);
							if(!addedTransacId.trim().equals(""))
							{
								addedTransacId += ",";
							}
							addedTransacId += intNewTransVal.toString();
						}
					}
				}
		}
			/* Added by Parnika for multiple Parent Hierarchy */
			accountDto.setHiddenTranctionIds(accountFormBean.getHiddenTranctionIds());
			logger.info("accountFormBean.getHiddenTranctionIds():::"+accountFormBean.getHiddenTranctionIds());
			// For distributors only
			if(accountDto.getAccountLevelId() == 7){
				accountDto.setParentAccountId(-1);
				if(accountFormBean.getHiddenTranctionIds().length() == 3){
					String selectedTransaction[] = new String[2];
					selectedTransaction[0] = accountFormBean.getHiddenTranctionIds().substring(0,accountFormBean.getHiddenTranctionIds().indexOf(',') );
					selectedTransaction[1] = accountFormBean.getHiddenTranctionIds().substring(accountFormBean.getHiddenTranctionIds().indexOf(',')+1,3 );
					logger.info("selectedTransaction[0]::::"+selectedTransaction[0]);
					logger.info("selectedTransaction[1]::::"+selectedTransaction[1]);				
					if(Integer.parseInt(selectedTransaction[0]) == 1 || Integer.parseInt(selectedTransaction[1]) == 1 ){
						accountDto.setSalesTypeTSMLoginName(accountFormBean.getSalesTypeTSMLoginName());
					}
					if(Integer.parseInt(selectedTransaction[0]) == 2 || Integer.parseInt(selectedTransaction[1]) == 2 ){
						accountDto.setDepositeTypeTSMLoginName(accountFormBean.getDepositeTypeTSMLoginName());
					}
					
				}
				else{
					String selectedTransaction = accountFormBean.getHiddenTranctionIds();
					if(Integer.parseInt(selectedTransaction) == 1 ){
						accountDto.setSalesTypeTSMLoginName(accountFormBean.getSalesTypeTSMLoginName());
					}
					if(Integer.parseInt(selectedTransaction) == 2 ){
						accountDto.setDepositeTypeTSMLoginName(accountFormBean.getDepositeTypeTSMLoginName());
					}
					
				}	
				accountDto.setOldDepositeTypeTSMId(oldDepositeTypeTSMId);
				accountDto.setOldSalesTypeTSMId(oldSalesTypeTSMId);
				accountDto.setExistingTransacId(selectedTransactions);
				
				//existingTransacList =null;
				//existingTransacList =new ArrayList();
			}
			/* End of changes by Parnika */

			logger.info(" addedTransacId == "+addedTransacId);
			accountDto.setAddedTransactionId(addedTransacId);
			
//			*********************** Added by Shilpa to get New Added Transactions if any ENDS here 
			
			//DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			logger.info(" Request to update an account by user "+ sessionContext.getId());
			
			
//			 Add by harbans singh
			ArrayList tradeList = accountService.getTradeList();
			accountFormBean.setTradeList(tradeList);
			
			ArrayList categoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
			accountFormBean.setTradeCategoryList(categoryList);
			accountDto.setOldParentAccountId(oldParentId);
			// calling service method to call dao update
			accountService.updateAccount(accountDto);
			oldParentId ="-1";
			oldDepositeTypeTSMId = "-1";
			oldSalesTypeTSMId = "-1";
			
			long acId=accountDto.getAccountId();
			if(accountFormBean.getAccountLevelId()== Constants.DISTRIBUTOR_ID)
			{
				springCacheUtility.updateAccountUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				
			}
			
			if(accountFormBean.getAccountLevelId()== Constants.TSM_ID)
			{
				String[] circleArr = accountFormBean.getCircleIdList().split(",");
				for(int i=0; i<circleArr.length; i++)
				{
					springCacheUtility.updateAccountsSingleCircle(Integer.valueOf(circleArr[i]));
					DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
					dropdownUtilityAjaxServiceImpl.getAllAccountsSingleCircle(String.valueOf(accountFormBean.getAccountLevelId()),circleArr[i]);
				}
				
			}
			
			errors.add("message.account", new ActionError("message.account.update_success"));
			saveErrors(request, errors);
		} 
		catch (NumberFormatException numExp) 
		{
//			Added by sugandha to fix bug BFR-61 DP-PHASE-3 accountFormBean.getAccountLevelId() == 2
			logger.info("dfgsdgfdshklhghlddfsdfdsf"+accountFormBean.getAccountLevelId());
			if(accountFormBean.getAccountLevelId() == 3 || accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6 || accountFormBean.getAccountLevelId() == 15){
				accountFormBean.setShowCircle("N");
				
				selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
				logger.info("***** selectedCircleList == "+selectedCircleList);
				if(selectedCircleList!=null)
					strExistCircle = selectedCircleList.split(",");
					else
						logger.info("selecte circle list is blank ::");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				
			}else{
				accountFormBean.setShowCircle("A");
				if(accountFormBean.getAccountLevelId() == 7){
					selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
					logger.info("***** selectedTransactions == "+selectedTransactions);
					String[] strExistTran = selectedTransactions.split(",");
					for(int count=0;count<strExistTran.length;count++){
						existingTransacList.add(strExistTran[count]);
					}
					
					/* Added by Parnika */						
					
					accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
					accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
					accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
					accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
					accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
					accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
					accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
					accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
					/*
					logger.info("strSalesTSM"+session.getAttribute("strSalesTSM").toString());
					logger.info("strSalesTSMAccName"+session.getAttribute("strSalesTSMAccName").toString());
					logger.info("strSalesTSMLogName"+session.getAttribute("strSalesTSMLogName").toString());
					logger.info("longSalesTSMID"+Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
					logger.info("strCPETSM"+session.getAttribute("strCPETSM").toString());
					logger.info("strCPETSMAccName"+session.getAttribute("strCPETSMAccName").toString());
					logger.info("strCPETSMLogName"+session.getAttribute("strCPETSMLogName").toString());
					logger.info("longCPETSMID"+Long.parseLong(session.getAttribute("longCPETSMID").toString()));
					
					/* End of changes by parnika */
				}
			}
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
			if(null!=session.getAttribute("cityList"))
				accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
			if(null!=session.getAttribute("beatList"))
				accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
	
// STATE DROP DOWN for Edit Account
			if(null!=session.getAttribute("stateList"))
			accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
			//Added by ARTI for warehaouse list box BFR -11 release2
			if(null!=session.getAttribute("wareHouseList"))
				accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
			if(null!=session.getAttribute("outletList"))
				accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
//			if(null!=accountFormBean.getAccountLevel()){
//				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
			
			/*if(null!=session.getAttribute("tradeCategoryList"))
			session.setAttribute("tradeList", (ArrayList)session.getAttribute("tradeList"));
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));*/
//			}	
			logger.error(" caught Exception while Parsing User Id.", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (IllegalAccessException iaExp) {
			if(accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6){
				accountFormBean.setShowCircle("N");
				
				selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
				logger.info("***** selectedCircleList == "+selectedCircleList);
				if(selectedCircleList!=null)
					strExistCircle = selectedCircleList.split(",");
					else
						logger.info("selecte circle list is blank ::");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				
			}else{
				accountFormBean.setShowCircle("A");
				if(accountFormBean.getAccountLevelId() == 7){
					selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
					logger.info("***** selectedTransactions == "+selectedTransactions);
					String[] strExistTran = selectedTransactions.split(",");
					for(int count=0;count<strExistTran.length;count++){
						existingTransacList.add(strExistTran[count]);
					}
					/* Added by Parnika */	
					accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
					accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
					accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
					accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
					accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
					accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
					accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
					accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
					/*
					logger.info("strSalesTSM"+session.getAttribute("strSalesTSM").toString());
					logger.info("strSalesTSMAccName"+session.getAttribute("strSalesTSMAccName").toString());
					logger.info("strSalesTSMLogName"+session.getAttribute("strSalesTSMLogName").toString());
					logger.info("longSalesTSMID"+Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
					logger.info("strCPETSM"+session.getAttribute("strCPETSM").toString());
					logger.info("strCPETSMAccName"+session.getAttribute("strCPETSMAccName").toString());
					logger.info("strCPETSMLogName"+session.getAttribute("strCPETSMLogName").toString());
					logger.info("longCPETSMID"+Long.parseLong(session.getAttribute("longCPETSMID").toString()));
						// commented by pratap
						*/	
					/* End of changes by parnika */
				}
			}
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
			if(null!=session.getAttribute("cityList"))
				accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
			if(null!=session.getAttribute("beatList"))
				accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
			if(null!=session.getAttribute("stateList"))
				accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
			//Added by ARTI for warehaouse list box BFR -11 release2
			if(null!=session.getAttribute("wareHouseList"))
				accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
			if(null!=session.getAttribute("outletList"))
				accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
//			if(null!=accountFormBean.getAccountLevel()){
//				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
			/*if(null!=session.getAttribute("tradeCategoryList"))
			session.setAttribute("tradeList", (ArrayList)session.getAttribute("tradeList"));
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));*/
//			}	
			logger.error(" caught Exception while using BeanUtils.copyProperties()",iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (InvocationTargetException itExp) 
		{
			if(accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6){
				accountFormBean.setShowCircle("N");
				
				selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
				logger.info("***** selectedCircleList == "+selectedCircleList);
				if(selectedCircleList!=null)
					strExistCircle = selectedCircleList.split(",");
					else
						logger.info("selecte circle list is blank ::");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				
			}else{
				accountFormBean.setShowCircle("A");
				if(accountFormBean.getAccountLevelId() == 7){
					selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
					logger.info("***** selectedTransactions == "+selectedTransactions);
					String[] strExistTran = selectedTransactions.split(",");
					for(int count=0;count<strExistTran.length;count++){
						existingTransacList.add(strExistTran[count]);
					}
				}
				/* Added by Parnika */	
				accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
				accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
				accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
				accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
				accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
				accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
				accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
				accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
				/*
				logger.info("strSalesTSM"+session.getAttribute("strSalesTSM").toString());
				logger.info("strSalesTSMAccName"+session.getAttribute("strSalesTSMAccName").toString());
				logger.info("strSalesTSMLogName"+session.getAttribute("strSalesTSMLogName").toString());
				logger.info("longSalesTSMID"+Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
				logger.info("strCPETSM"+session.getAttribute("strCPETSM").toString());
				logger.info("strCPETSMAccName"+session.getAttribute("strCPETSMAccName").toString());
				logger.info("strCPETSMLogName"+session.getAttribute("strCPETSMLogName").toString());
				logger.info("longCPETSMID"+Long.parseLong(session.getAttribute("longCPETSMID").toString()));
					commented by pratap
					*/	
				/* End of changes by parnika */
			}
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
			if(null!=session.getAttribute("cityList"))
				accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
			if(null!=session.getAttribute("beatList"))
				accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
			if(null!=session.getAttribute("stateList"))
				accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
			if(null!=session.getAttribute("wareHouseList"))
				accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
			if(null!=session.getAttribute("outletList"))
				accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
			//			if(null!=accountFormBean.getAccountLevel()){
//				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
			/*if(null!=session.getAttribute("tradeCategoryList"))
			session.setAttribute("tradeList", (ArrayList)session.getAttribute("tradeList"));
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));*/
//			}	
			logger.error(" caught Exception while using BeanUtils.copyProperties()",itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) 
		{
			if(accountFormBean.getAccountLevelId() == 3 ||accountFormBean.getAccountLevelId() == 4  || accountFormBean.getAccountLevelId() == 5 || accountFormBean.getAccountLevelId() == 6){
				accountFormBean.setShowCircle("N");
				
				selectedCircleList = accountService.getSelectedCircleList(accountFormBean.getAccountId());
				logger.info("***** selectedCircleList == "+selectedCircleList);
				if(selectedCircleList!=null)
					strExistCircle = selectedCircleList.split(",");
					else
						logger.info("selecte circle list is blank ::");
				for(int count=0;count<strExistCircle.length;count++){
					existingCircleList.add(strExistCircle[count]);
				}
				
			}else{
				accountFormBean.setShowCircle("A");
				if(accountFormBean.getAccountLevelId() == 7){
					selectedTransactions = accountService.getSelectedTransactions(accountFormBean.getAccountId());
					logger.info("***** selectedTransactions == "+selectedTransactions);
					String[] strExistTran = selectedTransactions.split(",");
					for(int count=0;count<strExistTran.length;count++){
						existingTransacList.add(strExistTran[count]);
					}
				}
			}
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList)session.getAttribute("zoneList"));
			if(null!=session.getAttribute("cityList"))
				accountFormBean.setCityList((ArrayList)session.getAttribute("cityList"));
			if(null!=session.getAttribute("beatList"))
				accountFormBean.setBeatList((ArrayList)session.getAttribute("beatList"));
			if(null!=session.getAttribute("stateList"))
				accountFormBean.setStateList((ArrayList)session.getAttribute("stateList"));
			if(null!=session.getAttribute("wareHouseList"))
				accountFormBean.setWareHouseList((ArrayList)session.getAttribute("wareHouseList"));
			if(null!=session.getAttribute("outletList"))
				accountFormBean.setOutletList((ArrayList)session.getAttribute("outletList"));
//			if(null!=accountFormBean.getAccountLevel()){
//				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
			/*if(null!=session.getAttribute("tradeCategoryList"))
			session.setAttribute("tradeList", (ArrayList)session.getAttribute("tradeList"));
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));*/
//			}	
			vse.printStackTrace();
			System.out.println("vse.getMessage()::::::::::::::::::::::::::"+vse.getMessage());
			
			if(vse.getMessage().equalsIgnoreCase("DupDist")){
				logger.info("\n\n\nInside Dup Dist\n\n\n");
				errors.add("error.account", new ActionError("error.account.dupDisLoc"));	
			}
			else if(vse.getMessage().equalsIgnoreCase("error.account.billableisinactive")){
				logger.info("\n\n\nIerror.account.billableisinactive\n\n\n");
				errors.add("error.account", new ActionError("error.account.billableisinactive"));	
			}
			else
			{
				errors.add("error.account", new ActionError(vse.getMessage()));	
			}
			
			/* Added by Parnika */	
			accountFormBean.setSalesTypeTSM(session.getAttribute("strSalesTSM").toString());
			accountFormBean.setSalesTypeTSMAccountName(session.getAttribute("strSalesTSMAccName").toString());
			accountFormBean.setSalesTypeTSMLoginName(session.getAttribute("strSalesTSMLogName").toString());
			accountFormBean.setSalesTypeTSMId(Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
			accountFormBean.setDepositeTypeTSM(session.getAttribute("strCPETSM").toString());
			accountFormBean.setDepositeTypeTSMAccountName(session.getAttribute("strCPETSMAccName").toString());
			accountFormBean.setDepositeTypeTSMLoginName(session.getAttribute("strCPETSMLogName").toString());
			accountFormBean.setDepositeTypeTSMId(Long.parseLong(session.getAttribute("longCPETSMID").toString()));
			
			logger.info("strSalesTSM  ::  "+session.getAttribute("strSalesTSM").toString());
			logger.info("strSalesTSMAccName  ::  "+session.getAttribute("strSalesTSMAccName").toString());
			logger.info("strSalesTSMLogName  ::  "+session.getAttribute("strSalesTSMLogName").toString());
			logger.info("longSalesTSMID  ::  "+Long.parseLong(session.getAttribute("longSalesTSMID").toString()));
			logger.info("strCPETSM  ::  "+session.getAttribute("strCPETSM").toString());
			logger.info("strCPETSMAccName  ::  "+session.getAttribute("strCPETSMAccName").toString());
			logger.info("strCPETSMLogName  ::  "+session.getAttribute("strCPETSMLogName").toString());
			logger.info("longCPETSMID  ::  "+Long.parseLong(session.getAttribute("longCPETSMID").toString()));
			
			/* End of changes by parnika */
			
			logger.error(" Service Exception occured :  "+"Entered Distributor Locator Code Already Exist");
			saveErrors(request, errors);
			if(accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID){
				return mapping.findForward(Constants.EDIT_ACCOUNT_DISTRIBUTOR);			
			}else if(accountFormBean.getAccountLevelId() < Constants.DISTRIBUTOR_ID){
				return mapping.findForward(Constants.EDIT_ACCOUNT);
			}else if(accountFormBean.getAccountLevelId() >= Constants.IT_HELP_DESK){
				return mapping.findForward(Constants.EDIT_ACCOUNT);
			}
			//return mapping.findForward(Constants.EDIT_ACCOUNT);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.SEARCH_ACCOUNT);
	}

	/**
	 * this method will use to open account search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward callSearchAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("Started... ");
		ActionErrors errors = new ActionErrors();
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_IT_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;				
			
			// It checks whether the login user is admin or not
			logger.info("Session Id in CallSearch="	+ sessionContext.getCircleId());
			logger.info("Auth..."+ authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_ACCOUNT));
			if (authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				accountFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+ accountFormBean.getEditStatus());
			} else {
				accountFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}

			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "+ sessionContext.getId());
				Circle circleDto = new Circle();
				circleDto = circleService.getCircle(sessionContext.getCircleId());
				accountFormBean.setCircleId(circleDto.getCircleId() + "");
				accountFormBean.setCircleName(circleDto.getCircleName());
				accountFormBean.setFlagForCircleDisplay(true);
			}
		} catch (VirtualizationServiceException se) {
			logger.error("Error Occured while retrieving Circle list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(STATUS_ERROR);
		}
		
		logger.info(" Executed... ");
		
		return mapping.findForward(Constants.OPEN_SEARCH_PAGE);
	}
	/**
	 * This method will use to search the account acording to search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	
	public ActionForward searchAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started... searchAccount");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		String searchFieldName = accountFormBean.getSearchFieldName();
		String searchFieldValue = accountFormBean.getSearchFieldValue();
		String circleValue = accountFormBean.getCircleId();
		String listStatus = accountFormBean.getListStatus();
		String startDate = accountFormBean.getStartDt();
		String endDate = accountFormBean.getEndDt();
		String userRole = accountFormBean.getUserRole();
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		// Search an account
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			sessionContext.setRoleList(roleList);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_IT_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			  
			 

			CircleService circleService = new CircleServiceImpl();
			try {
				ArrayList circleDtoList = circleService.getCircles();
				session.setAttribute("circleList", circleDtoList);
			} catch (VirtualizationServiceException se) {
				logger.error("  Service Exception occured ", se);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle circleDto = new Circle();
				circleDto = circleService.getCircle(sessionContext.getCircleId());
				accountFormBean.setCircleId(circleDto.getCircleId() + "");
				accountFormBean.setCircleName(circleDto.getCircleName());
				accountFormBean.setFlagForCircleDisplay(true);
			}
			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_EDIT_ACCOUNT));
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				accountFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ accountFormBean.getEditStatus());
			} else {
				accountFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				accountFormBean.setResetStatus(Constants.RESET_AUTHORIZED);
				logger.info(" this is the value of reset "
						+ accountFormBean.getResetStatus());
			} else {
				accountFormBean.setResetStatus(Constants.RESET_UNAUTHORIZED);
			}
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UNLOCK_USER)) {
				accountFormBean.setUnlockStatus(Constants.UNLOCK_AUTHORIZED);
				logger.info(" this is the value of unlock "
						+ accountFormBean.getUnlockStatus());
			} else {
				accountFormBean.setUnlockStatus(Constants.UNLOCK_UNAUTHORIZED);
			}
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();
			// set data from form bean
			if (request.getParameter("searchType") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("listStatus") == null
					|| request.getParameter("startDate") == null
					|| request.getParameter("endDate") == null) {
				reportInputDto.setSearchFieldName(searchFieldName);
				reportInputDto.setSearchFieldValue(searchFieldValue);
				reportInputDto.setSessionContext(sessionContext);
				
				if(circleValue != null && !"".equalsIgnoreCase(circleValue)) {
				reportInputDto.setCircleId(Integer.parseInt(circleValue));
				}
				reportInputDto.setStatus(listStatus);
				reportInputDto.setStartDt(startDate);
				reportInputDto.setEndDt(endDate);
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("circleId", circleValue);
				request.setAttribute("listStatus", listStatus);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
				request.setAttribute("userRole", userRole);			
				if(!userRole.equalsIgnoreCase("") && !userRole.equalsIgnoreCase(null))
				reportInputDto.setUserRole(Integer.parseInt(userRole));
			} else {
				reportInputDto.setSearchFieldName(request.getParameter("searchType"));
				reportInputDto.setSearchFieldValue(request.getParameter("searchValue"));
				reportInputDto.setCircleId(Integer.parseInt(request
						.getParameter("circleId")));
				reportInputDto.setStatus(request.getParameter("listStatus"));
				reportInputDto.setStartDt(request.getParameter("startDate"));
				reportInputDto.setEndDt(request.getParameter("endDate"));				
				if(!userRole.equalsIgnoreCase("") && !userRole.equalsIgnoreCase(null))
					reportInputDto.setUserRole(Integer.parseInt(userRole));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("listStatus", request
						.getParameter("listStatus"));
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request.setAttribute("endDate", request.getParameter("endDate"));
				request.setAttribute("userRole", request.getParameter("userRole"));
				reportInputDto.setSessionContext(sessionContext);
				accountFormBean.setStartDt(request.getParameter("startDate"));
				accountFormBean.setEndDt(request.getParameter("endDate"));
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(request.getParameter("circleId"));
				accountFormBean.setUserRole(request.getParameter("userRole"));
			}
			logger.info("**********searchType************"+(request.getParameter("searchType")));
			logger.info("**********searchValue************"+(request.getParameter("searchValue")));
			ArrayList accountList = accountService.getAccountList(reportInputDto,0, 0);
					logger.info("Inside searchAccount().. no of Records..." + accountList.size());

			//Delimit accountaddress field to 10 chars while displaying in grid 
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) {

				DPCreateAccountDto accountDto = (DPCreateAccountDto) iter.next();
				accountDto.setAccountAddress(Utility.delemitDesctiption(accountDto.getAccountAddress()));
			}

			//request.setAttribute("PAGES", noofpages);

			accountFormBean.setDisplayDetails(accountList);
			if(session.getAttribute("displayDetailsList") !=null ){
				session.removeAttribute("displayDetailsList");
			}
			session.setAttribute("displayDetailsList", accountList);

		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing..", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(Constants.OPEN_SEARCH_PAGE);
	}
	/*public ActionForward searchAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		String searchFieldName = accountFormBean.getSearchFieldName();
		String searchFieldValue = accountFormBean.getSearchFieldValue();
		String circleValue = accountFormBean.getCircleId();
		String listStatus = accountFormBean.getListStatus();
		String startDate = accountFormBean.getStartDt();
		String endDate = accountFormBean.getEndDt();
		String userRole = accountFormBean.getUserRole();
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		// Search an account
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			sessionContext.setRoleList(roleList);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_IT_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			  The code will be used to identify which all accounts the logged
			  in user can view/edit.
			  Passing the sessionContext to service layer.
			 

			 Get list of circles from Service Layer 
			CircleService circleService = new CircleServiceImpl();
			 //Create Circle DropDown List 
			try {
				ArrayList circleDtoList = circleService.getCircles();
				session.setAttribute("circleList", circleDtoList);
			} catch (VirtualizationServiceException se) {
				logger.error("  Service Exception occured ", se);
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(STATUS_ERROR);
			}
			if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
				logger.info("User is not Administrator "
						+ sessionContext.getId());
				Circle circleDto = new Circle();
				circleDto = circleService.getCircle(sessionContext.getCircleId());
				accountFormBean.setCircleId(circleDto.getCircleId() + "");
				accountFormBean.setCircleName(circleDto.getCircleName());
				accountFormBean.setFlagForCircleDisplay(true);
			}
			logger.info("Auth..."
					+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_EDIT_ACCOUNT));
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				accountFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "
						+ accountFormBean.getEditStatus());
			} else {
				accountFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				accountFormBean.setResetStatus(Constants.RESET_AUTHORIZED);
				logger.info(" this is the value of reset "
						+ accountFormBean.getResetStatus());
			} else {
				accountFormBean.setResetStatus(Constants.RESET_UNAUTHORIZED);
			}
			if (authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UNLOCK_USER)) {
				accountFormBean.setUnlockStatus(Constants.UNLOCK_AUTHORIZED);
				logger.info(" this is the value of unlock "
						+ accountFormBean.getUnlockStatus());
			} else {
				accountFormBean.setUnlockStatus(Constants.UNLOCK_UNAUTHORIZED);
			}
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();
			// set data from form bean
			if (request.getParameter("searchType") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("listStatus") == null
					|| request.getParameter("startDate") == null
					|| request.getParameter("endDate") == null) {
				reportInputDto.setSearchFieldName(searchFieldName);
				reportInputDto.setSearchFieldValue(searchFieldValue);
				reportInputDto.setSessionContext(sessionContext);

				reportInputDto.setCircleId(Integer.parseInt(circleValue));
				reportInputDto.setStatus(listStatus);
				reportInputDto.setStartDt(startDate);
				reportInputDto.setEndDt(endDate);
				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				request.setAttribute("circleId", circleValue);
				request.setAttribute("listStatus", listStatus);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
				request.setAttribute("userRole", userRole);			
				if(!userRole.equalsIgnoreCase("") && !userRole.equalsIgnoreCase(null))
				reportInputDto.setUserRole(Integer.parseInt(userRole));
			} else {
				reportInputDto.setSearchFieldName(request.getParameter("searchType"));
				reportInputDto.setSearchFieldValue(request.getParameter("searchValue"));
				reportInputDto.setCircleId(Integer.parseInt(request
						.getParameter("circleId")));
				reportInputDto.setStatus(request.getParameter("listStatus"));
				reportInputDto.setStartDt(request.getParameter("startDate"));
				reportInputDto.setEndDt(request.getParameter("endDate"));				
				if(!userRole.equalsIgnoreCase("") && !userRole.equalsIgnoreCase(null))
					reportInputDto.setUserRole(Integer.parseInt(userRole));
				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("listStatus", request
						.getParameter("listStatus"));
				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request.setAttribute("endDate", request.getParameter("endDate"));
				request.setAttribute("userRole", request.getParameter("userRole"));
				reportInputDto.setSessionContext(sessionContext);
				accountFormBean.setStartDt(request.getParameter("startDate"));
				accountFormBean.setEndDt(request.getParameter("endDate"));
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(request.getParameter("circleId"));
				accountFormBean.setUserRole(request.getParameter("userRole"));
			}
			 //call service to count the no of rows 
			int noofpages = accountService.getAccountListCount(reportInputDto);
			
			logger.info("Inside searchAccount().. no of pages..." + noofpages);

			// call to set lower & upper bound for Pagination 
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			// call service to find all accounts 
			ArrayList accountList = accountService.getAccountList(reportInputDto,
					pagination.getLowerBound(), pagination.getUpperBound());
			
			Account accountDto = (Account) accountList.get(0); int
			 * totalRecords = accountDto.getTotalRecords(); int noofpages =
			 * Utility.getPaginationSize(totalRecords);
			 

			 //Delimit accountaddress field to 10 chars while displaying in grid 
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) {

				DPCreateAccountDto accountDto = (DPCreateAccountDto) iter.next();
				accountDto.setAccountAddress(Utility
						.delemitDesctiption(accountDto.getAccountAddress()));
			}

			request.setAttribute("PAGES", noofpages);

			// set ArrayList of Bean objects to FormBean 
			accountFormBean.setDisplayDetails(accountList);

		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing..", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(Constants.OPEN_SEARCH_PAGE);
	}*/

	/**
	 * This method will use to search the Parent account acording to Circle And
	 * search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward getParentAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... getParentAccountList in DPCreateAccountITHelpAction class");
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			DPCreateAccountDto accountDTO=new DPCreateAccountDto();
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			sessionContext.setRoleList(roleList);
			BeanUtils.copyProperties(accountDTO, accountFormBean);
			accountDTO.setAccountId(sessionContext.getId());
			logger.info("accountFormBean.getAccountLevel()****::"+accountFormBean.getAccountLevel());
			logger.info("accountFormBean.getDistTranctionId()*****::"+accountFormBean.getDistTranctionId());
			//String distTranctionId = request.getParameter("distTranctionId");
			
			/** pagination implementation */
			if (accountFormBean.getSearchFieldName() == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("circleIdList") == null
					|| request.getParameter("accountLevel") == null) {

				request.setAttribute("searchType", accountFormBean.getSearchFieldName());
				request.setAttribute("searchValue", accountFormBean.getSearchFieldValue());
				request.setAttribute("circleId", accountFormBean.getCircleId());
				request.setAttribute("circleIdList", accountFormBean.getCircleIdList());
				request.setAttribute("accountLevel", accountFormBean.getAccountLevel());

			} else {
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(request.getParameter("circleId"));
				accountFormBean.setCircleIdList(request.getParameter("circleIdList"));
				accountFormBean.setStatus(request.getParameter("accountLevel"));

				request.setAttribute("searchType", request.getParameter("searchType"));
				request.setAttribute("searchValue", request.getParameter("searchValue"));
				request.setAttribute("circleId", request.getParameter("circleId"));
				request.setAttribute("circleIdList", request.getParameter("circleIdList"));
				request.setAttribute("accountLevel", request.getParameter("accountLevel"));
			}
			/* call service to count the no of rows */
			logger.info(" ******** in getParentAccountList accountFormBean.getAccountLevel() == "+accountFormBean.getAccountLevel()+" and  getCircleId == "+accountFormBean.getCircleId() + " and circle list == "+accountFormBean.getCircleIdList());
			int parentCircleId = -1;
			String parentCircleIdList = accountFormBean.getCircleIdList();
			if(null != accountFormBean.getCircleId() && !accountFormBean.getCircleId().trim().equals("")){
				parentCircleId = Integer.parseInt(accountFormBean.getCircleId());
			}
				
			
			/*int noofpages = accountService.getParentAccountListCount(accountFormBean.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext,parentCircleId ,parentCircleIdList ,accountFormBean.getAccountLevel());
			logger.info("Inside getCircleList().. no of pages..." + noofpages);
			// noofpages = Utility.getPaginationSize(noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));*/
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);
			/* call service to find all accounts */
			accountDTO.setSessionContaxt(sessionContext);
			ArrayList accountList = null;
			
			logger.info("accountFormBean.getAccountLevel()::"+accountFormBean.getAccountLevel());
			logger.info("accountFormBean.getDistTranctionId()::"+accountFormBean.getDistTranctionId());
			
			String distTranctionId = (String) request.getSession().getAttribute("distTranctionId");
			logger.info("distTranctionId::"+distTranctionId);
			if(accountFormBean.getAccountLevel() != null && accountFormBean.getAccountLevel().equals("6") && distTranctionId.contains("2"))
			{
				accountList = accountService.getTransactionBasedTypeTSMList(accountFormBean.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
									sessionContext, parentCircleId,parentCircleIdList, accountFormBean.getAccountLevel(), pagination.getLowerBound(),
									pagination.getUpperBound(), Integer.parseInt(accountFormBean.getAccountLevel()),accountDTO,2);
			}
			else if(accountFormBean.getAccountLevel() != null && accountFormBean.getAccountLevel().equals("6") && distTranctionId.contains("1"))
			{
				accountList = accountService.getTransactionBasedTypeTSMList(accountFormBean.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
									sessionContext, parentCircleId,parentCircleIdList, accountFormBean.getAccountLevel(), pagination.getLowerBound(),
									pagination.getUpperBound(), Integer.parseInt(accountFormBean.getAccountLevel()),accountDTO,1);
			}
			else
			{
				accountList = accountService.getParentAccountList(accountFormBean.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
										sessionContext, parentCircleId,parentCircleIdList, accountFormBean.getAccountLevel(), pagination.getLowerBound(),
										pagination.getUpperBound(), Integer.parseInt(accountFormBean.getAccountLevel()),accountDTO);
			}
			// Account accountDto = (Account) accountList.get(0);
			// int totalRecords = accountDto.getTotalRecords();

			//logger.info("No of pages" + noofpages);

			/* set the list into form accountFormBean */
			accountFormBean.setDisplayDetails(accountList);
			accountFormBean.setTradeList((ArrayList) (session.getAttribute("tradeList")));
			ArrayList tradeCategoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
			session.setAttribute("tradeCategoryList", tradeCategoryList);
			accountFormBean.setTradeCategoryList((ArrayList) (session
					.getAttribute("tradeCategoryList")));
			request.setAttribute("displayDetails", accountList);
		} catch (VirtualizationServiceException vse) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"errors.account.parentrecord_notfound"));
			saveErrors(request, errors);
		}catch (Exception se) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Exception in Action occured   ", se);
			errors.add("error.account", new ActionError(
					"errors.account.parentrecord_notfound"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(OPEN_PARENT_ACCOUNT_LIST);

	}
	/**
	 * This method will use to search the Parent account acording to Circle And
	 * search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException by Rohit kunder
	 */
	public ActionForward getSearchParentAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("###########  Started...getSearchParentAccountList ############ ");
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();
			DPCreateAccountDto accountDTO=new DPCreateAccountDto();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			/** pagination implementation */
			if (accountFormBean.getSearchFieldName() == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("accountLevel") == null) {

				request.setAttribute("searchType", accountFormBean.getSearchFieldName());
				request.setAttribute("searchValue", accountFormBean.getSearchFieldValue());
				request.setAttribute("circleId", sessionContext.getCircleId());
				accountFormBean.setCircleId(Integer.toString(sessionContext.getCircleId()));
				request.setAttribute("accountLevel", accountFormBean.getAccountLevel());

			} else {
				
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(Integer.toString(sessionContext.getCircleId()));
				accountFormBean.setStatus(request.getParameter("accountLevel"));

				request.setAttribute("searchType", request.getParameter("searchType"));
				request.setAttribute("searchValue", request.getParameter("searchValue"));
				request.setAttribute("circleId", request.getParameter("circleId"));
				request.setAttribute("accountLevel", request.getParameter("accountLevel"));
			}
			
			String circleIdList ="";
			/* call service to count the no of rows */
			int noofpages = accountService.getParentAccountListCount(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()),circleIdList, accountFormBean
							.getAccountLevel());
			logger.info("Inside getCircleList().. no of pages..." + noofpages);
			// noofpages = Utility.getPaginationSize(noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);
			/* call service to find all accounts */
			ArrayList accountList = accountService.getParentAccountList(accountFormBean.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()),circleIdList, accountFormBean
							.getAccountLevel(), pagination.getLowerBound(),
					pagination.getUpperBound(), Integer.parseInt(accountFormBean
							.getAccountLevel()),accountDTO);

			// Account accountDto = (Account) accountList.get(0);
			// int totalRecords = accountDto.getTotalRecords();

			logger.info("No of pages" + noofpages);

			/* set the list into form accountFormBean */
			accountFormBean.setDisplayDetails(accountList);
			accountFormBean.setTradeList((ArrayList) (session.getAttribute("tradeList")));
			ArrayList tradeCategoryList = accountService
					.getTradeCategoryList(accountFormBean.getTradeId());
			session.setAttribute("tradeCategoryList", tradeCategoryList);
			accountFormBean.setTradeCategoryList((ArrayList) (session
					.getAttribute("tradeCategoryList")));
			request.setAttribute("displayDetails", accountList);
		} catch (VirtualizationServiceException vse) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"errors.account.parentrecord_notfound"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(FIND_DISTRIBUTOR);

	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward openSearchParentAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		if (request.getSession().getAttribute("displayDetails") != null) {
			request.getSession().removeAttribute("displayDetails");
		}

		logger.info("Executed... ");
		return mapping.findForward(OPEN_PARENT_ACCOUNT_LIST);

	}
	/**
	 * This method will use to search the deposite type of TSM acording to Circle ,transaction type And
	 * search option type
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException by Rohit kunder
	 */

	//added by sugandha
	public ActionForward OpenTransactionTypeTSMPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		if (request.getSession().getAttribute("displayDetails") != null) {
			request.getSession().removeAttribute("displayDetails");
		}
		String distTranctionId = request.getParameter("distTranctionId");
		System.out.println("distTranctionId in OpenDepositeTypeTSMPage method::"+distTranctionId);
		request.getSession().setAttribute("distTranctionId", distTranctionId);
		logger.info("Executed... ");
		return mapping.findForward(OPEN_PARENT_ACCOUNT_LIST);

	}
	//Rohit kunder
	public ActionForward openSearchDistributor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		
		logger.info(" Inside openSearchDistributor method... ");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		
		int circleID=userSessionContext.getCircleId();
		String accountlevel = userSessionContext.getAccountLevel();

		request.setAttribute("circleId", circleID);
		request.setAttribute("accountlvl", accountlevel);
		logger.info("Executed... ");
		return mapping.findForward(FIND_DISTRIBUTOR);
	}

	public ActionForward downloadAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;

		String searchFieldName = accountFormBean.getSearchFieldName();
		String searchFieldValue = accountFormBean.getSearchFieldValue();
		String circleValue = accountFormBean.getCircleId();
		String status = accountFormBean.getListStatus();
		String startDate = accountFormBean.getStartDt();
		String endDate = accountFormBean.getEndDt();

		// Search an account
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
			ArrayList circleDtoList = circleService.getCircles();
			session.setAttribute("circleList", circleDtoList);

			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();
			// set data from form accountFormBean

			/*reportInputDto.setSearchFieldName(searchFieldName);
			reportInputDto.setSearchFieldValue(searchFieldValue);
			reportInputDto.setSessionContext(sessionContext);
			reportInputDto.setCircleId(Integer.parseInt(circleValue));
			reportInputDto.setStatus(status);
			reportInputDto.setStartDt(startDate);
			reportInputDto.setEndDt(endDate);

			ArrayList accountList = accountService.getAccountListAll(reportInputDto);*/
			ArrayList accountList =  (ArrayList) session.getAttribute("displayDetailsList");
			logger.info("Account List Size in  downloadAccountList === "+accountList.size());
			/** **End of pagination**** */
			// set the list into form accountFormBean
			accountFormBean.setDisplayDetails(accountList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_DOWNLOAD_ACCOUNT_LIST);
	}

	/**
	 * this method search city according to state id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getCity... ");
		ActionErrors errors = new ActionErrors();
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;

			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();
			// if circle is disabled then set circle id same as login user
			// circle Id
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			/* Create Circle DropDown List */
			accountFormBean.setUserType(sessionContext.getType());
			int accountZoneId = accountFormBean.getAccountZoneId();
			String accountZoneName = accountFormBean.getAccountZoneName();
			ArrayList cityList = accountService.getCites(accountFormBean
					.getAccountZoneId());
			session.setAttribute("cityList", cityList);
			if(null!=session.getAttribute("zoneList"))
				accountFormBean.setZoneList((ArrayList) session
					.getAttribute("zoneList"));
			if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID){
			if(null!=session.getAttribute("tradeCategoryList"))
				accountFormBean.setTradeCategoryList((ArrayList) session
						.getAttribute("tradeCategoryList"));
			}
			accountFormBean.setAccountZoneName(accountZoneName);
			accountFormBean.setAccountZoneId(accountZoneId);
			accountFormBean.setCityList(cityList);
			accountFormBean.setParentAccountId(sessionContext.getId());
			accountFormBean.setParentAccount(sessionContext.getContactName());
		} catch (VirtualizationServiceException vse) {

			logger
					.error(
							"Error Occured while retrieving Circle or group list ",
							vse);
			errors.add("errors", new ActionError(vse.getMessage()));

		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}

	/**
	 * this method get billable account list based on search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ActionForward getBillableAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started..getBillableAccountList. ");
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			/** pagination implementation */
			if (request.getParameter("searchType") == null
					|| request.getParameter("searchValue") == null
					|| request.getParameter("circleId") == null
					|| request.getParameter("accountLevel") == null) {

				request.setAttribute("searchType", accountFormBean.getSearchFieldName());
				request.setAttribute("searchValue", accountFormBean.getSearchFieldValue());
				request.setAttribute("circleId", accountFormBean.getCircleId());
				request.setAttribute("accountLevel", accountFormBean.getAccountLevel());

			} else {
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(request.getParameter("circleId"));
				accountFormBean.setStatus(request.getParameter("accountLevel"));

				request.setAttribute("searchType", request
						.getParameter("searchType"));
				request.setAttribute("searchValue", request
						.getParameter("searchValue"));
				request.setAttribute("circleId", request
						.getParameter("circleId"));
				request.setAttribute("accountLevel", request
						.getParameter("accountLevel"));

			}
			/* call service to count the no of rows */
			int noofpages = accountService.getBillableAccountListCount(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()), accountFormBean
							.getAccountLevel());
			logger.info(" getBillableAccountListCount().. no of pages..."
					+ noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all accounts */
			ArrayList billableAccountList = accountService
					.getBillableAccountList(accountFormBean.getSearchFieldName(), accountFormBean
							.getSearchFieldValue(), sessionContext, Integer
							.parseInt(accountFormBean.getCircleId()), accountFormBean
							.getAccountLevel(), pagination.getLowerBound(),
							pagination.getUpperBound());

			/* set the list into form accountFormBean */
			accountFormBean.setDisplayDetails(billableAccountList);
			request.setAttribute("billableAccountList", billableAccountList);
		} catch (VirtualizationServiceException vse) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"errors.account.billablerecord_notfound"));
			saveErrors(request, errors);
		}
		logger.info("Executed... ");
		return mapping.findForward(OPEN_BILLABLE_ACCOUNT_LIST);

	}

	/**
	 * this method open billable account search pop-up window
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */

	public ActionForward openSearchBillableAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		if (request.getSession().getAttribute("displayDetails") != null) {
			request.getSession().removeAttribute("displayDetails");
		}

		logger.info("Executed... ");
		return mapping.findForward(OPEN_BILLABLE_ACCOUNT_LIST);

	}

	/**
	 * this method use for get city list for edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCityForEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getCity... ");
		ActionErrors errors = new ActionErrors();
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;

			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();

			/* Create Circle DropDown List */
			ArrayList cityList = accountService.getCites(accountFormBean
					.getAccountZoneId());
			session.setAttribute("cityList", cityList);

		} catch (VirtualizationServiceException se) {

			logger.error(
					"Error Occured while retrieving Circle or group list ", se);
			errors.add("errors", new ActionError(se.getMessage()));

		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.EDIT_ACCOUNT);
	}

	/**
	 * This method unlock user form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward UnlockUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_UNLOCK_USER)) {
				logger
						.info(" user not auth to perform this ROLE_UNLOCK_USER activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			LoginService loginService = new LoginServiceImpl();
			loginService.unlockUser(Long.parseLong(accountFormBean
					.getAccountId()));

		} catch (VirtualizationServiceException vs) {
			logger.error("  caught Service Exception  redirected to  ", vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(Constants.SEARCH_ACCOUNT);

		}
		errors.add("error.account", new ActionError("errors.user.unlock"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.SEARCH_ACCOUNT);
	}

	/**
	 * this is method will use to reset i-password
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetIPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" Started... ");
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();

		try {

			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_RESET_I_PASSWORD)) {
				logger
						.info(" user not auth to perform this ROLE_RESET_I_PASSWORD activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			if (sessionContext != null) {
				AuthenticationService authenticationService = new AuthenticationServiceImpl();
				authenticationService.resetPassword(Long
						.parseLong(accountFormBean.getAccountId()),
						Constants.USER_TYPE_EXTERNAL);
			}
		} catch (VirtualizationServiceException vs) {

			logger.error("  caught Service Exception  redirected to  "
					+ Constants.OPEN_SEARCH_PAGE, vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward(Constants.SEARCH_ACCOUNT);

		}
		errors.add("error.account", new ActionError(
				"errors.resetipasswordupdatesuccessfully"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		// redirect to the specified target
		return mapping.findForward(Constants.SEARCH_ACCOUNT);

	}

	public ActionForward aggregateView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = new ActionForward();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		int circleId = sessionContext.getCircleId();
		ReportInputs reportInputDto = new ReportInputs();
		reportInputDto.setCircleId(circleId);
		reportInputDto.setSessionContext(sessionContext);
		// to get the count of list in order to fetch no. of pages
		int noofpages = accountService.aggregateCount(reportInputDto);
		Pagination pagination = VirtualizationUtils
				.setPaginationinRequest(request);
		// to fetch the contents of list.
		ArrayList aggregate = accountService.aggregateView(reportInputDto, pagination
				.getLowerBound(), pagination.getUpperBound());
		accountFormBean.setAggList(aggregate);
		request.setAttribute("aggList", aggregate);
		request.setAttribute("PAGES", noofpages);
		forward = mapping.findForward("AggregateList");
		if (sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID) {
			logger.info("User is not Administrator " + sessionContext.getId());
			accountFormBean.setCircleId(String.valueOf(circleId));
		}
		return forward;
	}

	public ActionForward downloadAggregateList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
			throws VirtualizationServiceException, NumberFormatException,
			DAOException {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_AGGREGATE_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_AGGREGATE_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			int circleValue = sessionContext.getCircleId();
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();

			reportInputDto.setCircleId(circleValue);
			reportInputDto.setSessionContext(sessionContext);
			ArrayList accountList = accountService.getAggregateListAll(reportInputDto);
			String circleName = accountService.getCircleName(circleValue);
			accountFormBean.setCircleName(circleName);
			accountFormBean.setAggList(accountList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_DOWNLOAD_AGGREGATE_LIST);
	}

	public ActionForward getZone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getZone... ");
		ActionErrors errors = new ActionErrors();
		ArrayList zoneList = new ArrayList();
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();
			// if circle is disabled then set circle id same as login user
			// circle Id
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			accountFormBean.setUserType(sessionContext.getType());
			/* Create Circle DropDown List */
			zoneList = accountService.getZones(Integer.parseInt(accountFormBean
					.getCircleId()));
			session.setAttribute("zoneList", zoneList);
			if(null!=accountFormBean.getAccountLevel()){
				if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID){
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
				}
			}	
			accountFormBean.setZoneList(zoneList);
			accountFormBean.setParentAccountId(sessionContext.getId());
			accountFormBean.setParentAccount(sessionContext.getContactName());
		} catch (VirtualizationServiceException vse) {
			vse.printStackTrace();
			logger.error(	"Error Occured while retrieving Circle or group list ",
							vse);
			errors.add("errors", new ActionError(vse.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}

	//** STATE Code by Digvijay start
	
	public ActionForward getState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting get State Action... ");
		ActionErrors errors = new ActionErrors();
		ArrayList stateList = new ArrayList();
		try {
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			HttpSession session = request.getSession();

			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);

			accountFormBean.setUserType(sessionContext.getType());
			/* Create State DropDown List */
			stateList = accountService.getStates(Integer.parseInt(accountFormBean
					.getCircleId()));
			session.setAttribute("stateList", stateList);
			if(null!=accountFormBean.getAccountLevel()){
				if(Integer.parseInt(accountFormBean.getAccountLevel())== 2 || Integer.parseInt(accountFormBean.getAccountLevel())== 3){
					if(null!=session.getAttribute("tradeCategoryList"))	
						accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
				}
			}	
			accountFormBean.setStateList(stateList);
			accountFormBean.setParentAccountId(sessionContext.getId());
			accountFormBean.setParentAccount(sessionContext.getContactName());
		} catch (VirtualizationServiceException vse) {
			vse.printStackTrace();
			logger.error(	"Error Occured while retrieving State Drop down Action or group list ",
							vse);
			errors.add("errors", new ActionError(vse.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}
	//** STATE Code by Digvijay ends
	
	public ActionForward getCategory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Starting getCategory... ");
		ActionErrors errors = new ActionErrors();
		ArrayList tradeCategoryList = new ArrayList();
		try {
			HttpSession session = request.getSession();
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			// if circle is disabled then set circle id same as login user
			// circle Id
			if ((accountFormBean.getCircleId() == null)
					|| (accountFormBean.getCircleId().equals(""))) {

				accountFormBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));
			}
			/* Create Circle DropDown List */
			tradeCategoryList = accountService
					.getTradeCategoryList(accountFormBean.getTradeId());
			session.setAttribute("tradeCategoryList", tradeCategoryList);
			accountFormBean.setTradeCategoryList(tradeCategoryList);
			accountFormBean.setUserType(sessionContext.getType());
			accountFormBean.setZoneList((ArrayList) session
					.getAttribute("zoneList"));
	//		Code for State
			accountFormBean.setStateList((ArrayList) session
					.getAttribute("stateList"));
			
			
			accountFormBean.setCityList((ArrayList) session
					.getAttribute("cityList"));
			accountFormBean.setFlagForCategoryFocus(false);
			if (accountFormBean.getAccountLevelId() <= Constants.DISTRIBUTOR_ID) {
				accountFormBean.setShowNumbers("Y");
			} else
				accountFormBean.setShowNumbers("N");
		} catch (VirtualizationServiceException vse) {
			logger.error(	"Error Occured while retrieving Circle or group list ",
							vse);
			errors.add("errors", new ActionError(vse.getMessage()));
		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_ACCOUNT);
	}

	
	
	
	//Added by Shilpa for multiple Circle list
	public ActionForward getParentCategoryList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Starting getZone... ");
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try {
			String tradeId = request.getParameter("OBJECT_ID");
			String condition = request.getParameter("condition");
			logger.info("****** In getParentCategoryList function Circle IDs === "+tradeId);
			
					if(condition.equalsIgnoreCase("zone")){
						if(tradeId.indexOf(",0") < 0 && !tradeId.equals("0"))
						objectList = accountService.getZonesList(tradeId);
						logger.info("**** Zone LIst Size === "+objectList.size());
						session.setAttribute("zoneList", objectList);
					}
			DPCreateAccountDto accountDto = null;
			for (int counter = 0; counter < objectList.size(); counter++) {
				accountDto = (DPCreateAccountDto) objectList.get(counter);
				optionElement = root.addElement("option");
				
				if (accountDto != null) {
					if(condition.equalsIgnoreCase("category")){	
						optionElement.addAttribute("value", accountDto.getTradeCategoryId()
								+ "");
						optionElement.addAttribute("text", accountDto
								.getTradeCategoryName());
					}
					else if(condition.equalsIgnoreCase("beat")){
						optionElement.addAttribute("value", accountDto.getBeatId()+ "");
						optionElement.addAttribute("text", accountDto.getBeatName());
					}
					else if(condition.equalsIgnoreCase("zone")){
						optionElement.addAttribute("value", accountDto.getAccountZoneId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountZoneName());
					}
					else if(condition.equalsIgnoreCase("state")){
						optionElement.addAttribute("value", accountDto.getAccountStateId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountStateName());
					}
					else if(condition.equalsIgnoreCase("warehouse")){
						optionElement.addAttribute("value", accountDto.getAccountWarehouseCode()+ "");
						optionElement.addAttribute("text", accountDto.getAccountWarehouseName());
					}
					else if(condition.equalsIgnoreCase("city")){
						optionElement.addAttribute("value", accountDto.getAccountCityId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountCityName());
					}
				}else {
					optionElement.addAttribute("value", "None");
					optionElement.addAttribute("text", "None");
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}
	///Ends here
	public ActionForward getParentCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Starting getZone... ");
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try {
			int tradeId = Integer.parseInt(request.getParameter("OBJECT_ID"));
			String condition = request.getParameter("condition");
			
			
			if(condition.equalsIgnoreCase("category")){
				objectList = accountService.getTradeCategoryList(tradeId);
				session.setAttribute("tradeCategoryList", objectList);
			}else
				if(condition.equalsIgnoreCase("beat")){
					objectList = accountService.getAllBeats(tradeId);
					session.setAttribute("beatList", objectList);
				}else
					if(condition.equalsIgnoreCase("zone")){
						objectList = accountService.getZones(tradeId);
						session.setAttribute("zoneList", objectList);
					}else
						if(condition.equalsIgnoreCase("city")){
							objectList = accountService.getCites(tradeId);
							session.setAttribute("cityList", objectList);
						}
						else
							if(condition.equalsIgnoreCase("state")){
								
								objectList = accountService.getStates(tradeId);
								session.setAttribute("stateList", objectList);
							}
							else
								if(condition.equalsIgnoreCase("warehouse")){
									;
									objectList = accountService.getWareHouses(tradeId);
									session.setAttribute("wareHouseList", objectList);
								}
			DPCreateAccountDto accountDto = null;
			for (int counter = 0; counter < objectList.size(); counter++) {
				accountDto = (DPCreateAccountDto) objectList.get(counter);
				optionElement = root.addElement("option");
				
				if (accountDto != null) {
					if(condition.equalsIgnoreCase("category")){	
						optionElement.addAttribute("value", accountDto.getTradeCategoryId()
								+ "");
						optionElement.addAttribute("text", accountDto
								.getTradeCategoryName());
					}
					else if(condition.equalsIgnoreCase("beat")){
						optionElement.addAttribute("value", accountDto.getBeatId()+ "");
						optionElement.addAttribute("text", accountDto.getBeatName());
					}
					else if(condition.equalsIgnoreCase("zone")){
						optionElement.addAttribute("value", accountDto.getAccountZoneId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountZoneName());
					}
					else if(condition.equalsIgnoreCase("state")){
						optionElement.addAttribute("value", accountDto.getAccountStateId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountStateName());
					}
					else if(condition.equalsIgnoreCase("warehouse")){
						optionElement.addAttribute("value", accountDto.getAccountWarehouseCode()+ "");
						optionElement.addAttribute("text", accountDto.getAccountWarehouseName());
					}
					else if(condition.equalsIgnoreCase("city")){
						optionElement.addAttribute("value", accountDto.getAccountCityId()+ "");
						optionElement.addAttribute("text", accountDto.getAccountCityName());
					}
				}else {
					optionElement.addAttribute("value", "None");
					optionElement.addAttribute("text", "None");
				}
			}
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward getAccountLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int accountLevel =Integer.parseInt(request.getParameter("cond1"));
		String flag="";
		DPCreateAccountITHelpService accountService= new DPCreateAccountITHelpServiceImpl();
		DPCreateAccountDto accountDto = accountService.getAccountLevelDetails(accountLevel);		
		ajaxCall(request, response, accountDto,"text");
		return null;
	}
	public void ajaxCall(HttpServletRequest request, HttpServletResponse response,DPCreateAccountDto accountDto,String text)throws Exception{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "No-Cache");
		String flag = accountDto.getAccountLevelName()+"@"+accountDto.getHeirarchyId();
		PrintWriter out = response.getWriter();
		out.write(flag);
		out.flush();				
		}
	
	public ActionForward getRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		logger.info("In method getRole().");
		
		try 
		{
			DPCreateAccountITHelpFormBean formBean = (DPCreateAccountITHelpFormBean) form;
			DPCreateAccountITHelpService searchUserRoleService= new DPCreateAccountITHelpServiceImpl();
			List roleList = searchUserRoleService.getUserRoleList(Constants.DISTRIBUTOR_ID +1);
			ChangeUserRoleService changeRoleService = new ChangeUserRoleServiceImpl();
			
			//List roleList = changeRoleService.getRoleList(Constants.DISTRIBUTOR_ID +1);
			//formBean.setRoleList(roleList);
							
			// Ajax start
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
			Iterator iter = roleList.iterator();
			while (iter.hasNext()) 
			{
				RoleDto roleDto = (RoleDto) iter.next();
				if(!roleDto.getRoleId().trim().equals("1")){
					optionElement = root.addElement("option");
					optionElement.addAttribute("text", roleDto.getRoleName());
					optionElement.addAttribute("value", roleDto.getRoleId());
				}
			}

			// For ajax
			response.setHeader("Cache-Control", "No-Cache");
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
			// End for ajax
		

		} catch (Exception ex) 
		{
			ex.printStackTrace();
			logger.error("Exception in ::"+ex);
		}

		return null;
	}
	
	/**
	 * this method will use to open account search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */
	public ActionForward callCreateIdHelpUserAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("Started... ");
		ActionErrors errors = new ActionErrors();
		try {
			HttpSession session = request.getSession();
			
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_CREATE_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_CREATE_ID_HELPDESK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;				
			if (accountFormBean != null && !accountFormBean.getErrorFlag().equals("true")) {
				accountFormBean.reset();
			}
			

		} catch (Exception se) {
			logger.error("Error Occured while retrieving Circle list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		}
		
		logger.info(" Executed...callCreateIdHelpUserAccount ");
		
		return mapping.findForward("createIdHelpUserJsp");
	}
	/**
	 * This method will be use to insert account data
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createIdHelpdeskAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("... Started  createIdHelpdeskAccount");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try {
			
		String default_password = accountService.generatePassword(accountFormBean.getAccountName());
		//String default_password = ResourceReader.getValueFromBundle(Constants.USER_DEFAULT_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE);
		accountFormBean.setIPassword(default_password);
		accountFormBean.setCheckIPassword(default_password);
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
				// get login ID from session
			
			
		
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			//DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_CREATE_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_CREATE_ID_HELPDESK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			/* Check For HRMS Integration. */
			try {
				logger.info(" Checking HRMS Integration of Login Id = '"	+ accountFormBean.getAccountCode() + "'");
				DPCommonService commonService = new DPCommonServiceImpl();
				String validteHRMSFlag = ResourceReader.getCoreResourceBundleValue(Constants.HRMS_VALIDATION);
				if(validteHRMSFlag != null && validteHRMSFlag.equalsIgnoreCase("Y"))  {
				
				String hrmsResponse = commonService.isValidOLMID(accountFormBean.getAccountCode());
				logger.info("HRMS Integration Response == "+hrmsResponse);
				if(!hrmsResponse.equals(com.ibm.dp.common.Constants.DP_HRMS_SUCCESS_MSG))
				{
					logger.info(" Account Code is not integrated with HRMS");
					errors.add("errors.account", new ActionError(hrmsResponse));
					accountFormBean.setErrorFlag("true");
					saveErrors(request, errors);
					return mapping.findForward("errorIDHelpDesk");
				} }
				logger.info(" Account Code is integrated with HRMS");
			} catch (Exception exp) {
			
				logger.error(" caught Exception for HRMS Integration  "+ exp.getMessage(), exp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("validationerror", new ActionError("noHRMS"));
				accountFormBean.setErrorFlag("true");
				saveErrors(request, errors);
				
				return mapping.findForward("errorIDHelpDesk");
			}
			
			
			/* Check if Account Code and Password are GSD Compliant. */
			try {
				logger.info(" Checking GSD compliance of Account  Code = '"	+ accountFormBean.getAccountCode() + "'");
				GSDService gSDService = new GSDService();
				if (accountFormBean.getAccountLevelId() < Constants.FSE_ID || accountFormBean.getAccountLevelId()==Constants.FINANCE_USER_ID) {

					// check for special, lower case and upper case characters.
					accountService.checkChars(accountFormBean.getIPassword());
					gSDService.validateCredentials(accountFormBean.getAccountCode(), accountFormBean.getIPassword());
					logger.info(" Account Code and Password are GSD Compliant.");
				}
			} catch (ValidationException validationExp) {
				
				if(validationExp.getMessage().equalsIgnoreCase("noUppercase")){
					errors.add("validationerror", new ActionError("noUppercase"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noSpecialCharacter")){
					errors.add("validationerror", new ActionError("noSpecialCharacter"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noLowerCase")){
					errors.add("validationerror", new ActionError("noLowerCase"));
				}	
				logger.error(" caught Exception for GSD  "+ validationExp.getMessage(), validationExp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("errors.account", new ActionError(validationExp.getMessageId(), acount_code));
				accountFormBean.setErrorFlag("true");
				saveErrors(request, errors);
				return mapping.findForward("errorIDHelpDesk");
			}
			/* Level - 1 VR_LOGIN_MASTER */
			Login loginDto = new Login();
			/* transfer values from form bean to dto */
			loginDto.setLoginName(accountFormBean.getAccountCode());
			loginDto.setPassword(accountFormBean.getIPassword());
			loginDto.setStatus(Constants.ACC_STATUS);
			loginDto.setType(accountFormBean.getUserType());
			////// Commented by SHilpa
			ArrayList accountGroup = accountService.getAccountGroupId(13);
			
			accountFormBean.setGroupId(accountGroup.get(0).toString());
			accountFormBean.setGroupName(accountGroup.get(1).toString());
			DPCreateAccountDto accountDto = new DPCreateAccountDto();
			// copy account form bean data into account dto
			
			
			BeanUtils.copyProperties(accountDto, accountFormBean);
			
			long userLoginId = sessionContext.getId();
			accountDto.setCreatedBy(userLoginId);
			accountDto.setUpdatedBy(userLoginId);
			loginDto.setLoginId(userLoginId);
			// if circle is disabled then set circle id same as login user
			// circle Id
			
				if ((accountFormBean.getCircleId() == null)	|| (accountFormBean.getCircleId().equals(""))) {
					accountDto.setCircleId(0);
					accountFormBean.setCircleId("0");
				}
			// calling accountService method to validate and insert data
			logger.info(" Request to create a new account by user "	+ sessionContext.getId());
			
			//Commented by Shilpa
			accountService.createIdHelpDeskAccount(loginDto, accountDto);
			
			try
			{
				accountService.sendMail(accountDto.getEmail(), accountDto.getAccountName(), accountDto.getAccountCode(), default_password);
				logger.info("Mail for new User password has been sent successfully");
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				logger.info("Error in sending mail for new User password  ::  "+e.getMessage());
				
				String err = ResourceReader.getCoreResourceBundleValue(Constants.NEW_USER_PASSWRD_MAIL_Critical);
				logger.info(err + "::" +com.ibm.utilities.Utility.getCurrentDate());
			}
			
			
			long acId=accountDto.getAccountId();
			
			errors.add("message.account", new ActionError("message.account.insert_success"));
			saveErrors(request, errors);
			
			
			logger.info(" SuccessFully Created new Account ");
		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing User Id", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		} catch (IllegalAccessException iaExp) {
			logger.error(" caught Exception while using BeanUtils.copyProperties()",				iaExp);
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		} catch (InvocationTargetException itExp) {
			logger.error(					" caught Exception while using BeanUtils.copyProperties()",					itExp);
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		} catch (VirtualizationServiceException se) {
			// if invalid account id then error on page

		
			logger.info("\n\n\nException Message:"+se.getMessage()+"\n\n\n");
			
			if(se.getMessage().equalsIgnoreCase("DupDist")){
				logger.info("\n\n\nInside Dup Dist\n\n\n");
				errors.add("errors.account", new ActionError("error.account.dupDisLoc"));	
			}
			
			if(se.getMessage().equalsIgnoreCase("noUppercase")){
				errors.add("validationerror", new ActionError("noUppercase"));
			}
			
			if(se.getMessage().equalsIgnoreCase("noSpecialCharacter")){
				errors.add("validationerror", new ActionError("noSpecialCharacter"));
			}
			if(se.getMessage().equalsIgnoreCase("noLowerCase")){
				errors.add("validationerror", new ActionError("noLowerCase"));
			}	
			
			//Changes by Karan on 4 June 2012
			if(se.getMessage().equalsIgnoreCase("dupDistLocator"))
			{
				logger.info("\n\n\nInside Dupdist\n\n\n");
				errors.add("errors.account", new ActionError("errors.account.dupDist"));
				
			}	
			
			logger.error(" Service Exception occured   " + se.getMessage());
			errors.add("errors.account", new ActionError(se.getMessage()));
			accountFormBean.setErrorFlag("true");
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		}
		logger.info(" Executed... ");
		return mapping.findForward("openIDHelpAccount");
	}
	
	public ActionForward callSearchIdHelpUserAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("Started...callSearchIdHelpUserAccount ");
		ActionErrors errors = new ActionErrors();
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ID_HELPDESK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;				
			
			

		} catch (Exception se) {
			logger.error("Error Occured while retrieving Circle list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("errorIDHelpDesk");
		}
		
		logger.info(" Executed...callSearchIdHelpUserAccount ");
		
		return mapping.findForward("callSearchIdHelpUserAccount");
	}
	
	public ActionForward searchIdHelpUserAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws VirtualizationServiceException {
		logger.info(" Started...searchIdHelpUserAccount ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		String searchFieldName = accountFormBean.getSearchFieldName();
		String searchFieldValue = accountFormBean.getSearchFieldValue();
		logger.info("searchFieldName == "+searchFieldName + "  and searchFieldValue==="+searchFieldValue);
		AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
		// Search an account
		try {
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			sessionContext.setRoleList(roleList);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ID_HELPDESK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
		
			
			logger.info("Auth..."+ authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,	AuthorizationConstants.ROLE_VIEW_ID_HELPDESK));
		
			
			DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();
			// set data from form bean
			if (request.getParameter("searchType") == null) {
				reportInputDto.setSearchFieldName(searchFieldName);
				reportInputDto.setSearchFieldValue(searchFieldValue);
				reportInputDto.setSessionContext(sessionContext);

				request.setAttribute("searchType", searchFieldName);
				request.setAttribute("searchValue", searchFieldValue);
				
			} else {
				reportInputDto.setSearchFieldName(request.getParameter("searchType"));
				reportInputDto.setSearchFieldValue(request.getParameter("searchValue"));
				request.setAttribute("searchType", request.getParameter("searchType"));
				request.setAttribute("searchValue", request.getParameter("searchValue"));
				reportInputDto.setSessionContext(sessionContext);
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
			}
			/* call service to find all accounts */
			ArrayList accountList = accountService.getIdHelpDeskUserAccountList(reportInputDto);
			
			/* Delimit accountaddress field to 10 chars while displaying in grid */
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) {

				DPCreateAccountDto accountDto = (DPCreateAccountDto) iter.next();
				accountDto.setAccountAddress(Utility.delemitDesctiption(accountDto.getAccountAddress()));
			}

			
			/* set ArrayList of Bean objects to FormBean */
			accountFormBean.setDisplayDetails(accountList);

		} catch (NumberFormatException numExp) {
			logger.error(" caught Exception while Parsing..", numExp);
			errors.add("errors.account", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("errorViewIDHelpDesk");
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError("error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward("errorViewIDHelpDesk");
		}
		logger.info("Executed... ");
		return mapping.findForward("callSearchIdHelpUserAccount");
	}
	/**
	 * This method unlock user form with previous values
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward inActiveIdHelpdeskUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...inActiveIdHelpdeskUser ");
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_UNLOCK_USER activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			LoginService loginService = new LoginServiceImpl();
			loginService.inActiveIdHelpdeskUser(Long.parseLong(accountFormBean.getAccountId()));

		} catch (VirtualizationServiceException vs) {
			logger.error("  caught Service Exception  redirected to  ", vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward("errorViewIDHelpDesk");

		}
		errors.add("error.account", new ActionError("errors.user.inactive"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward("callSearchIdHelpUserAccount");
	}
	public ActionForward activeIdHelpdeskUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...activeIdHelpdeskUser ");
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_ID_HELPDESK)) {
				logger.info(" user not auth to perform this ROLE_VIEW_ID_HELPDESK activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			/* Check For HRMS Integration. */
			try {
				logger.info(" Checking HRMS Integration of Login Id = '"	+ accountFormBean.getAccountCode() + "'");
				DPCommonService commonService = new DPCommonServiceImpl();
				String validteHRMSFlag = ResourceReader.getCoreResourceBundleValue(Constants.HRMS_VALIDATION);
				if(validteHRMSFlag != null && validteHRMSFlag.equalsIgnoreCase("Y"))  {
				
					String hrmsResponse = commonService.isValidOLMID(accountFormBean.getAccountCode());
					logger.info("HRMS Integration Response == "+hrmsResponse);
					if(!hrmsResponse.equals(com.ibm.dp.common.Constants.DP_HRMS_SUCCESS_MSG))
					{
						logger.info(" Account Code is not integrated with HRMS");
						errors.add("error.account", new ActionError(hrmsResponse));
						accountFormBean.setErrorFlag("true");
						saveErrors(request, errors);
						return mapping.findForward("callSearchIdHelpUserAccount");
					} 
				}
				logger.info(" Account Code is integrated with HRMS");
			} catch (Exception exp) {
				logger.info("in exception function");
				logger.error(" caught Exception for HRMS Integration  "+ exp.getMessage(), exp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("validationerror", new ActionError("noHRMS"));
				accountFormBean.setErrorFlag("true");
				saveErrors(request, errors);
				
				return mapping.findForward("callSearchIdHelpUserAccount");
				
			}
			
			LoginService loginService = new LoginServiceImpl();
			loginService.activeIdHelpdeskUser(Long.parseLong(accountFormBean.getAccountId()));

		} catch (VirtualizationServiceException vs) {
			logger.error("  caught Service Exception  redirected to  ", vs);
			logger.info("Message is " + vs.getMessage());

			errors.add("error.account", new ActionError(vs.getMessage()));
			saveErrors(request, errors);
			logger.info("in exception function");
			return mapping.findForward("callSearchIdHelpUserAccount");

		}
		errors.add("error.account", new ActionError("errors.user.active"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward("callSearchIdHelpUserAccount");
	}
	
	
	public ActionForward checkCircleChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Starting checkCircleChange... ");
		HttpSession session = request.getSession();
		ArrayList objectList = new ArrayList();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try {
			String selectedCircleList = request.getParameter("selectedCircleList");
			String accountId = request.getParameter("accountId");
			logger.info("Selected Circle List  == "+ selectedCircleList + " and accountId == "+accountId);
			String errMessage ="NA";
			String missingCircle ="";
			if(selectedCircleList!=null){
				String[] newCircleList = selectedCircleList.split(",");
				ArrayList<String> newCircleArrayList = new ArrayList<String>();
				for(int i=0; i<newCircleList.length;i++){
					logger.info("What the user has selected is "+newCircleList[0]);
					
					newCircleArrayList.add(String.valueOf(Integer.parseInt(newCircleList[i])));
				}
				if(Integer.parseInt(newCircleList[0])==0)
				{
					newCircleArrayList=new ArrayList<String>();
					ArrayList circlewithname=accountService.getAllCircles();
					for(int i=0; i<circlewithname.size();i++){
						if((((DPCreateAccountDto)circlewithname.get(i)).getCircleId())!=0)
						newCircleArrayList.add(String.valueOf(((DPCreateAccountDto)circlewithname.get(i)).getCircleId()));
					}
					logger.info("new circl is "+newCircleList[0]);
				}
				
				Integer intOldCircleVal = 0; 
				
				String selectedCircleListtemp = "";
				ArrayList<String> existingCircle = new ArrayList<String>();
				//Added by sugandha to make change in dit circle admin multicircle as per BFR-61 DP-PHASE-3
					selectedCircleListtemp = accountService.getSelectedCircleListPAN(accountId);
					logger.info("***** selectedCircleList == "+selectedCircleListtemp);
					String[] strExistCircle = selectedCircleListtemp.split(",");
					for(int count=0;count<strExistCircle.length;count++){
						existingCircle.add(strExistCircle[count]);
					}
					logger.info("existingCircle  ===  "+existingCircle);
					
				
				for(int count=0; count<existingCircle.size();count++){
					intOldCircleVal = Integer.parseInt(existingCircle.get(count));
					logger.info(" Existing value is == "+intOldCircleVal);
					if(!newCircleArrayList.contains(intOldCircleVal.toString())){
						logger.info(" Circle is not in new list == "+intOldCircleVal.toString());
						if(!missingCircle.equals("")){
							missingCircle += ",";
						}
						missingCircle += intOldCircleVal.toString();
					}
				}
				logger.info(" is removed circle list  == "+missingCircle);
				
				if(!missingCircle.equals("")){
					errMessage =accountService.checkChildUnderParent(missingCircle, accountId);
					if(errMessage.trim().equals("")){
						errMessage ="NA";
					}
				}
		}
			
			Document document = DocumentHelper.createDocument();
			Element root = document.addElement("options");
			Element optionElement;
			
				optionElement = root.addElement("option");
				optionElement.addAttribute("errMessage", errMessage);
				response.setHeader("Cache-Control", "No-Cache");
				response.setContentType("text/xml");
				PrintWriter out = response.getWriter();			
				XMLWriter writer = new XMLWriter(out);
				writer.write(document);
				writer.flush();
				out.flush();
			
			//objectList = accountService.getTradeCategoryList(tradeId);
				//session.setAttribute("tradeCategoryList", objectList);
			
			
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward validateTSMTransChange(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		logger.info(" Starting validateTSMTransChange... ");
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try 
		{
			int accountID = Integer.parseInt(request.getParameter("accountID"));
			String bolReturn = accountService.validateTSMTransChange(accountID);
			optionElement = root.addElement("option");
			optionElement.addAttribute("text", bolReturn);
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}
	public ActionForward checkDistAccountUpdateBoTree(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
	{
		logger.info(" Inside Action of checkDistAccountUpdateBoTree... ");
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateAccountITHelpService accountService = new DPCreateAccountITHelpServiceImpl();
		try 
		{
			int accountID = Integer.parseInt(request.getParameter("accountId"));
			String bolReturn = accountService.checkDistAccountUpdateBoTree(accountID);
			optionElement = root.addElement("option");
			optionElement.addAttribute("text", bolReturn);
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "No-Cache");
			PrintWriter out = response.getWriter();
			OutputFormat outputFormat = OutputFormat.createCompactFormat();
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
			writer.flush();
			out.flush();
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward terminateDist(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	
		logger.info(" Started terminateDist:: ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountITHelpFormBean accountFormBean = (DPCreateAccountITHelpFormBean) form;
		DPCreateAccountDto accountDto = new DPCreateAccountDto();
		HttpSession session = request.getSession();
		try {
			// get login ID from session
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			
			logger.info(" termination id "+ request.getParameter("terminationId")+" account Id is "+request.getParameter("accountId"));
			
			accountDto.setTerminationId(request.getParameter("terminationId"));
			accountDto.setAccountId(Long.parseLong(request.getParameter("accountId")));
			accountDto.setUserType(sessionContext.getType());
			accountDto.setUpdatedBy(sessionContext.getId());
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
		
			accountService.terminateAccount(accountDto);			
			errors.add("error.account", new ActionError("errors.terminate_success"));
			
			saveErrors(request, errors);
		}  catch (VirtualizationServiceException vse) {
			saveErrors(request, errors);
			
		}
		logger.info(" Executed. terminate dist.. ");
		return mapping.findForward(Constants.SEARCH_ACCOUNT);
	}


}
