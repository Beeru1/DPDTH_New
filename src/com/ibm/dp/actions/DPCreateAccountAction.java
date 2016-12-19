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
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

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
import com.ibm.dp.beans.DPCreateAccountFormBean;
import com.ibm.dp.beans.SWLocatorListFormBean;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.service.DPCreateAccountITHelpService;
import com.ibm.dp.service.DPCreateAccountService;
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
public class DPCreateAccountAction extends DispatchAction {

	/* Logger for this Action class. */

	private static Logger logger = Logger.getLogger(DPCreateAccountAction.class
			.getName());

	
	public static final String OPEN_PARENT_ACCOUNT_LIST = "parentaccountlist";

	public static final String FIND_DISTRIBUTOR = "finddistributor"; //added by Rohit
	private static final String SHOW_DOWNLOAD_ACCOUNT_LIST = "listAccountExport";

	private static final String SHOW_DOWNLOAD_AGGREGATE_LIST = "listAggregateExport";

	private static final String OPEN_BILLABLE_ACCOUNT_LIST = "billablelist";

	private static final String STATUS_ERROR = "error";

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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
		try {
			/* Logged in user information from session */
			// get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			long loginUserId = sessionContext.getId();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_ADD_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID
					&&  sessionContext.getGroupId()==Constants.ADMIN_ID) {
				accountFormBean.setCircleId(String.valueOf(sessionContext
						.getCircleId()));
				accountFormBean
				.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
			} else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID
					&& (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext
							.getGroupId() == Constants.ND_GROUP_ID)) {
				accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
			}else if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID)
			{
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
// added for retailer transaction type
				ArrayList<SelectionCollection> retailerTransList = accountService.getRetailerTransactionList();
				logger.info("Size of retailerTransList in action::"+retailerTransList.size());
				accountFormBean.setRetailerTransList(retailerTransList);
				request.setAttribute("retailerTransList", retailerTransList);
				accountFormBean.setRetailerTransList(retailerTransList);
				
				 
//		Added by ARTI for warehaouse list box BFR -11 release2 for Edit Account ARTI 
				ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
				accountFormBean.setAccountWarehouseCode("0");
				session.setAttribute("wareHouseList", wareHouseList);
				accountFormBean.setWareHouseList(wareHouseList);
		
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
			}
			
			if (accountFormBean.getAccountLevelId() == Constants.DISTRIBUTOR_ID
					&& accountFormBean.getHeirarchyId() == Constants.HIERARCHY_1) {
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
		} else {
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
				accountLevelList = accountService
						.getAccessLevelAssignedList(sessionContext.getId());
			session.setAttribute("accountLevelList", accountLevelList);
			/* Create state DropDown List */
			ArrayList circleList = accountService.getAllCircles();
			session.setAttribute("circleList", circleList);
			// Distributor type
			ArrayList tradeList = accountService.getTradeList();
			session.setAttribute("tradeList", tradeList);
// retailer category : A, B,C, D and E
			ArrayList retailerCategoryList = accountService.getRetailerCategoryList();
			session.setAttribute("retailerCatList", retailerCategoryList);
// retailer transaction type : sales, retailer, sales-retailer
			//ArrayList retailerTransactionList = accountService.getRetailerTransactionList();
			//session.setAttribute("retailerTransList", retailerTransactionList);
			
	// outlet category		
			ArrayList outletList = accountService.getOutletList();
			session.setAttribute("outletList", outletList);
			accountFormBean.setOutletList(outletList);
	// alternate channel type : IFFCO and IOC		
			ArrayList altChannelList = accountService.getAltChannelList();
			session.setAttribute("altChannelTypeList", altChannelList);
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
			
		} catch (VirtualizationServiceException se) {
			logger.error(
					"Error Occured while retrieving Circle or group list ", se);
			errors.add("errors", new ActionError(se.getMessage()));
		}catch (Exception ex) {
			logger.error(
					"Error Occured in init:CreateAccountAction ", ex);
			errors.add("errors", new ActionError(ex.getMessage()));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		}
		logger.info(" Executed... ");
		// accountFormBean.reset();
		return mapping.findForward(Constants.CREATE_ACCOUNT);
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
		logger.info("... Started  ");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
		String default_password = ResourceReader.getValueFromBundle(Constants.USER_DEFAULT_PASSWORD,Constants.WEBSERVICE_RESOURCE_BUNDLE);
		accountFormBean.setIPassword(default_password);
		accountFormBean.setCheckIPassword(default_password);
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		try {		// get login ID from session
			
			if(Integer.parseInt(accountFormBean.getAccountLevel())>Constants.DIST_LEVEL_ID){
				//System.out.println("zone"+accountFormBean.getHiddenZoneId()+"city" +accountFormBean.getHiddenCityId()+"circle"+accountFormBean.getHiddenCircleId()+"outlet"+accountFormBean.getHiddenOutletType());
				accountFormBean.setAccountZoneId(accountFormBean.getHiddenZoneId());
				accountFormBean.setAccountCityId(accountFormBean.getHiddenCityId()+"");
				accountFormBean.setAccountCircleId(accountFormBean.getHiddenCircleId());
				accountFormBean.setOutletType(accountFormBean.getHiddenOutletType()+"");
			}
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
			ArrayList<SelectionCollection> retailerTransList = accountService.getRetailerTransactionList();
			logger.info("Size of retailerTransList in action::"+retailerTransList.size());
			accountFormBean.setRetailerTransList(retailerTransList);
			request.setAttribute("retailerTransList", retailerTransList);
			accountFormBean.setRetailerTransList(retailerTransList);
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_ADD_ACCOUNT)) {
				logger.info(" user not auth to perform this ROLE_ADD_ACCOUNT activity  ");
				errors.add("errors",new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			/* Check if Account Code and Password are GSD Compliant. */
			try {
				logger.info(" Checking GSD compliance of Account  Code = '"	+ accountFormBean.getAccountCode() + "'");
				GSDService gSDService = new GSDService();
				if (accountFormBean.getAccountLevelId() < Constants.FSE_ID) {
					//accountService.checkConsectiveChars(accountFormBean.getAccountCode(),accountFormBean.getIPassword(), Constants.consecutiveChars);
// check for special, lower case and upper case characters.
					accountService.checkChars(accountFormBean.getIPassword());
					gSDService.validateCredentials(accountFormBean.getAccountCode(), accountFormBean.getIPassword());
					logger.info(" Account Code and Password are GSD Compliant.");
				}
			} catch (ValidationException validationExp) {
				if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID &&  sessionContext.getGroupId()==Constants.ADMIN_ID) {
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				} else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID)) {
					accountFormBean.setShowCircle(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
				}else if(sessionContext.getCircleId() != Constants.ADMIN_CIRCLE_ID){
					accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
					accountFormBean.setShowZoneCity(Constants.ACCOUNT_SHOW_CIRCLE);
					accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				}
				// Adding zone list to form bean in case of a validation exception
//				if(validationExp.getMessage().equalsIgnoreCase("checkConsectiveChars")){
//					errors.add("validationerror", new ActionError("checkConsectiveChars"));
//				}
				if(validationExp.getMessage().equalsIgnoreCase("noUppercase")){
					errors.add("validationerror", new ActionError("noUppercase"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noSpecialCharacter")){
					errors.add("validationerror", new ActionError("noSpecialCharacter"));
				}
				if(validationExp.getMessage().equalsIgnoreCase("noLowerCase")){
					errors.add("validationerror", new ActionError("noLowerCase"));
				}	
//				if(null!=session.getAttribute("circleList"))
//					accountFormBean.setZoneList((ArrayList)session.getAttribute("circleList"));
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
//				if(null!=accountFormBean.getAccountLevel()){
//					if(Integer.parseInt(accountFormBean.getAccountLevel())>=Constants.DIST_LEVEL_ID)
				if(null!=session.getAttribute("tradeCategoryList"))	
					accountFormBean.setTradeCategoryList((ArrayList)session.getAttribute("tradeCategoryList"));
//				}	
				logger.error(" caught Exception for GSD  "+ validationExp.getMessage(), validationExp);
				// getting value from resource bundle for GSD Validation
				String acount_code = ResourceReader.getWebResourceBundleValue("account.accountCode");
				errors.add("errors.account", new ActionError(validationExp.getMessageId(), acount_code));
				saveErrors(request, errors);
				
				return mapping.findForward(Constants.CREATE_ACCOUNT);
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
				//Neetika
				System.out.println(accountFormBean.getTradeIdInBack()+ "trade id channel id  and     trade cat.."+Integer.parseInt(accountFormBean.getTradeCategoryIdInBack())); //Neetika check this again
				ArrayList categoryList = accountService.getTradeCategoryList(Integer.parseInt(accountFormBean.getTradeIdInBack()));
				accountFormBean.setTradeCategoryList(categoryList);
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
				
				System.out.println("zone list size == "+zoneList.size()+"sessionContext.getAccountZoneId()"+sessionContext.getAccountZoneId());
				session.setAttribute("zoneList", zoneList);
				accountFormBean.setAccountZoneId(sessionContext.getAccountZoneId());
				accountFormBean.setZoneList(zoneList);
				
				ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
							
				session.setAttribute("cityList", cityList);
				accountFormBean.setCityList(cityList);
				accountFormBean.setAccountCityId(sessionContext.getAccountCityId()+"");
				
				//System.out.println("accountFormBean.getAccountZoneId==="+accountFormBean.getAccountZoneId());
				//end neetika
				accountFormBean.setTradeCategoryId(Integer.parseInt(accountFormBean.getTradeCategoryIdInBack()));
				accountFormBean.setIsbillable(accountFormBean.getIsBillableInBack());
			}
			ArrayList accountGroup = accountService.getAccountGroupId(Integer.parseInt(accountFormBean.getAccountLevel()));
			accountFormBean.setGroupId(accountGroup.get(0).toString());
			accountFormBean.setGroupName(accountGroup.get(1).toString());
			
			if(accountFormBean.getAccountLevelId() == Constants.RETAILER_ID)
			{
				if(! accountService.isMobileExistForDepthThree(accountFormBean.getRetailerMobileNumber()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumber"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.CREATE_ACCOUNT);
				}
				
				//Neetika
				//System.out.println("Lapu "+accountFormBean.getLapuMobileNo()+" Lapu 2"+accountFormBean.getLapuMobileNo1()+" mobile1"+accountFormBean.getMobile1()+" Lapu 2"+accountFormBean.getMobile2());
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile1()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.CREATE_ACCOUNT);
				}
				//Neetika
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile2()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.CREATE_ACCOUNT);
				}
				//Neetika
				//System.out.println("fta "+accountFormBean.getMobile3()+"fta 2"+accountFormBean.getMobile4());
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile3()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.CREATE_ACCOUNT);
				}
				//Neetika
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile4()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.CREATE_ACCOUNT);
				}

			}
			
			//System.out.println("zone===="+accountFormBean.getHiddenZoneId()+"city" +accountFormBean.getHiddenCityId()+"circle"+accountFormBean.getHiddenCircleId()+"outlet"+accountFormBean.getHiddenOutletType());
			
			DPCreateAccountDto accountDto = new DPCreateAccountDto();

			// copy account form bean data into account dto
			BeanUtils.copyProperties(accountDto, accountFormBean);
			
			if(accountDto.getAccountLevelId() == (sessionContext.getAccesslevel()+1)){
				accountDto.setParentAccountId(sessionContext.getId());
			}
			long userLoginId = sessionContext.getId();
			accountDto.setCreatedBy(userLoginId);
			accountDto.setUpdatedBy(userLoginId);
			loginDto.setLoginId(userLoginId);
			// if circle is disabled then set circle id same as login user
			// circle Id
			if ((accountFormBean.getCircleId() == null)	|| (accountFormBean.getCircleId().equals(""))) {
				accountDto.setCircleId(sessionContext.getCircleId());
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			accountDto.setBillableCodeId(sessionContext.getBillableAccId());

			// if account create for mtp or distributor( or any top level
			// account )
			if (accountDto.getAccountLevelId() == Constants.MOBILITY_ID) {
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			
			
			
			// calling accountService method to validate and insert data
			logger.info(" Request to create a new account by user "	+ sessionContext.getId());
			accountService.createAccount(loginDto, accountDto);
			
			long acId=accountDto.getAccountId();
			if(accountFormBean.getAccountLevelId()== Constants.DISTRIBUTOR_ID)
			{
				springCacheUtility.updateAccountUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
					
			}
			
			if(accountFormBean.getAccountLevelId()== Constants.TSM_ID)
			{
				springCacheUtility.updateAccountsSingleCircle(Integer.valueOf(accountFormBean.getCircleId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsSingleCircle(String.valueOf(accountFormBean.getAccountLevelId()),accountFormBean.getCircleId());
				
			}
			
			// set flag false for city so it would not get focused if any server
			// side error comes
			errors.add("message.account", new ActionError("message.account.insert_success"));
			saveErrors(request, errors);

			if (session.getAttribute("circleList") != null) {
				session.removeAttribute("circleList");
			}
			if (session.getAttribute("groupList") != null) {
				session.removeAttribute("groupList");
			}
			if (session.getAttribute("accountLevelList") != null) {
				session.removeAttribute("accountLevelList");
			}
			if (session.getAttribute("displayDetails") != null) {
				session.removeAttribute("displayDetails");
			}
			if (session.getAttribute("cityList") != null) {
				session.removeAttribute("cityList");
			}
			logger.info(" SuccessFully Created new Account ");
		} 
		
		catch (NumberFormatException numExp) 
		{
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
			//System.out.println("accountFormBean.getAccountZoneId in exception ==="+accountFormBean.getAccountZoneId());
			if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID	&&  sessionContext.getGroupId()==Constants.ADMIN_ID) {
				accountFormBean.setShowCircle(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setShowZoneCity(Constants.ACCOUNT_NOT_SHOW_CIRCLE);
				accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			} else if (sessionContext.getCircleId() == Constants.ADMIN_CIRCLE_ID && (sessionContext.getGroupId() == Constants.MOBILITY_ID || sessionContext.getGroupId() == Constants.ND_GROUP_ID)) {
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
			//System.out.println("accountFormBean.getAccountZoneId in exception after set ==="+accountFormBean.getAccountZoneId());
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
			
			
			
			logger.error(" Service Exception occured   " + se.getMessage());
			errors.add("errors.account", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.CREATE_ACCOUNT);
		}
		logger.info(" Executed... ");
		return mapping.findForward(Constants.OPEN_ACCOUNT);
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
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
			logger
					.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		String accountId = request.getParameter("accountId");
		getAccountDetails(mapping, form, request, errors, accountId);
		
		logger.info(" Executed... ");
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
		logger.info(" Executed... ");
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
			logger.info("getAccountDetails=====");
			
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
			DPCreateAccountDto accountDto = accountService.getAccount(Long.parseLong(accountId));
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			String searchType = accountFormBean.getSearchFieldName();
			String searchValue = accountFormBean.getSearchFieldValue();
			/** Copying the DTO object data to Form Bean Objects */
			BeanUtils.copyProperties(accountFormBean, accountDto);
			// This where mobile nos come
			accountFormBean.setSearchFieldName(searchType);
			accountFormBean.setSearchFieldValue(searchValue);
			accountFormBean.setStatus(accountDto.getStatus().trim());
			accountFormBean.setCircleName(accountDto.getCircleName());
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
			
			accountFormBean.setAccountZoneId(accountDto.getAccountZoneId());
			ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
			session.setAttribute("zoneList", zoneList);
			
			accountFormBean.setAccountStateId(accountDto.getAccountStateId());
			ArrayList stateList = accountService.getStates(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setStateList(stateList);
			session.setAttribute("stateList", stateList);
			
			//Added by ARTI for warehaouse list box BFR -11 release2
			ArrayList wareHouseList = accountService.getWareHouses(Integer.parseInt(accountFormBean.getCircleId()));
			accountFormBean.setAccountWarehouseCode(accountDto.getAccountWarehouseCode());
			session.setAttribute("wareHouseList", wareHouseList);
			accountFormBean.setWareHouseList(wareHouseList);
			
			ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
			session.setAttribute("cityList", cityList);
			ArrayList beatList = accountService.getAllBeats(Integer.parseInt(accountFormBean.getAccountCityId()));
			session.setAttribute("beatList", beatList);
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
// 	retailer transaction type
				
				ArrayList retailerTransactionList = accountService.getRetailerTransactionList();
				session.setAttribute("retailerTransList", retailerTransactionList);
				accountFormBean.setRetailerTransList(retailerTransactionList);				
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
		logger.info(" Started :: ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
		DPCreateAccountDto accountDto = new DPCreateAccountDto();
		HttpSession session = request.getSession();
		try {
			// get login ID from session
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			Integer circleId = sessionContext.getCircleId();
		
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_ACCOUNT)) {
				logger
						.info(" user not auth to perform this ROLE_EDIT_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
			
			
			
			
			// set flag false for city so it would not get focused if any server
			// side error comes
			accountFormBean.setFlagForCityFocus(false);
			accountFormBean.setFlagForCategoryFocus(false);
			BeanUtils.copyProperties(accountDto, accountFormBean);
			accountDto.setUserType(sessionContext.getType());
			accountDto.setUpdatedBy(sessionContext.getId());
			
			logger.info(" Request to update an account by user "+ sessionContext.getId());
			
			
//			 Add by harbans singh
			ArrayList tradeList = accountService.getTradeList();
			accountFormBean.setTradeList(tradeList);
			
			ArrayList categoryList = accountService.getTradeCategoryList(accountFormBean.getTradeId());
			accountFormBean.setTradeCategoryList(categoryList);
			
			//Neetika
			if(accountFormBean.getAccountLevelId() == Constants.RETAILER_ID)
			{
			
			//ArrayList categoryList1 = accountService.getTradeCategoryList(Integer.parseInt(accountFormBean.getTradeId())); //chck
			//accountFormBean.setTradeCategoryList(categoryList1);
			accountFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			ArrayList zoneList = accountService.getZones(Integer.parseInt(accountFormBean.getCircleId()));
			
			System.out.println("zone list size == "+zoneList.size()+"sessionContext.getAccountZoneId()"+sessionContext.getAccountZoneId());
			session.setAttribute("zoneList", zoneList);
			accountFormBean.setAccountZoneId(sessionContext.getAccountZoneId());
			accountFormBean.setZoneList(zoneList);
			
			ArrayList cityList = accountService.getCites(accountFormBean.getAccountZoneId());
						
			session.setAttribute("cityList", cityList);
			accountFormBean.setCityList(cityList);
			accountFormBean.setAccountCityId(sessionContext.getAccountCityId()+"");
			
			ArrayList retailerCategoryList = accountService.getRetailerCategoryList();
			session.setAttribute("retailerCatList", retailerCategoryList);
			accountFormBean.setRetailerCatList(retailerCategoryList);
			//	retailer transaction type
			
			ArrayList retailerTransactionList = accountService.getRetailerTransactionList();
			session.setAttribute("retailerTransList", retailerTransactionList);
			accountFormBean.setRetailerTransList(retailerTransactionList);				
	// outlet category		
			ArrayList outletList = accountService.getOutletList();
			accountFormBean.setOutletList(outletList);
			session.setAttribute("outletList", outletList);
			accountFormBean.setOutletType(sessionContext.getOutletType());
	// alternate channel type : IFFCO and IOC		
			ArrayList altChannelList = accountService.getAltChannelList();
			session.setAttribute("altChannelTypeList", altChannelList);
			accountFormBean.setAltChannelTypeList(altChannelList);
	// channel type		
			ArrayList channelList = accountService.getChannelList();
			session.setAttribute("channelTypeList", channelList);
			accountFormBean.setChannelTypeList(channelList);
			
			accountFormBean.setTinDt(accountDto.getTinDt());
			accountFormBean.setCstDt(accountDto.getCstDt());	
			
			
				if(!accountFormBean.getStatus().equalsIgnoreCase("L"))//Neetika
				{
				if(! accountService.isMobileExistForDepthThree(accountFormBean.getRetailerMobileNumber()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumber"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}

				//Neetika
				System.out.println("Lapu "+accountFormBean.getLapuMobileNo()+" Lapu 2"+accountFormBean.getLapuMobileNo1()+" mobile1"+accountFormBean.getMobile1()+" Lapu 2"+accountFormBean.getMobile2());
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile1()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}
				//Neetika
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile2()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}
				//Neetika
				//System.out.println("fta "+accountFormBean.getMobile3()+"fta 2"+accountFormBean.getMobile4());
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile3()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}
				//Neetika
				if(accountService.isMobileExistForDepthThree(accountFormBean.getMobile4()))
				{
					errors.add("message.account", new ActionError("error.account.retailerlapunumbernot"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.EDIT_ACCOUNT);
				}
				}
			}
			//end neetika
			// calling service method to call dao update
			accountService.updateAccount(accountDto);
			long acId=accountDto.getAccountId();
			
			
			if(accountFormBean.getAccountLevelId()== Constants.DISTRIBUTOR_ID)
			{
				springCacheUtility.updateAccountUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsUnderSingleAccount(String.valueOf(accountFormBean.getAccountLevelId()));
				
			}
			
			if(accountFormBean.getAccountLevelId()== Constants.TSM_ID)
			{
			
				springCacheUtility.updateAccountsSingleCircle(Integer.valueOf(accountFormBean.getCircleId()));
				DropDownUtilityAjaxServiceImpl dropdownUtilityAjaxServiceImpl=new DropDownUtilityAjaxServiceImpl();
				dropdownUtilityAjaxServiceImpl.getAllAccountsSingleCircle(String.valueOf(accountFormBean.getAccountLevelId()),accountFormBean.getCircleId());
				
			}
			
			
			
			errors.add("message.account", new ActionError("message.account.update_success"));
			saveErrors(request, errors);
		} catch (NumberFormatException numExp) {
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
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					iaExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (InvocationTargetException itExp) {
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
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			
			
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
			if(vse.getMessage().equalsIgnoreCase("DupDist")){
				logger.info("\n\n\nInside Dup Dist\n\n\n");
				errors.add("error.account", new ActionError("error.account.dupDisLoc"));	
			}
			if(vse.getMessage().equalsIgnoreCase("error.account.alreadyexistmobileno")
			|| vse.getMessage().equalsIgnoreCase("error.account.alreadyexistlapu")
			|| vse.getMessage().equalsIgnoreCase("error.account.alreadyexistlapu2")
			|| vse.getMessage().equalsIgnoreCase("error.account.alreadyexistFta")
			||vse.getMessage().equalsIgnoreCase("error.account.alreadyexistFta2")
			)
			{
				logger.info("\n\n\nInside error.account.alreadyexistmobilenot\n\n\n");
				errors.add("error.account", new ActionError("error.account.alreadyexistmobileno"));	
			}
			logger.error(" Service Exception occured :  "+"Entered Distributor Locator Code Already Exist");
			saveErrors(request, errors);
			return mapping.findForward(Constants.EDIT_ACCOUNT);
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
			if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_VIEW_ACCOUNT_DIST)) {
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
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
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
		String searchFieldName = accountFormBean.getSearchFieldName();
		String searchFieldValue = accountFormBean.getSearchFieldValue();
		String circleValue = accountFormBean.getCircleId();
		String listStatus = accountFormBean.getListStatus();
		String startDate = accountFormBean.getStartDt();
		String endDate = accountFormBean.getEndDt();
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
					AuthorizationConstants.ROLE_VIEW_ACCOUNT_DIST)) {
				logger
						.info(" user not auth to perform this ROLE_VIEW_ACCOUNT activity  ");
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			/*
			  The code will be used to identify which all accounts the logged
			  in user can view/edit.
			  Passing the sessionContext to service layer.
			 */

			/* Get list of circles from Service Layer */
			CircleService circleService = new CircleServiceImpl();
			/* Create Circle DropDown List */
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
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			} else {
				reportInputDto.setSearchFieldName(request.getParameter("searchType"));
				reportInputDto.setSearchFieldValue(request.getParameter("searchValue"));
				reportInputDto.setCircleId(Integer.parseInt(request
						.getParameter("circleId")));
				reportInputDto.setStatus(request.getParameter("listStatus"));
				reportInputDto.setStartDt(request.getParameter("startDate"));
				reportInputDto.setEndDt(request.getParameter("endDate"));
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
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));
				reportInputDto.setSessionContext(sessionContext);
				accountFormBean.setStartDt(request.getParameter("startDate"));
				accountFormBean.setEndDt(request.getParameter("endDate"));
				accountFormBean.setSearchFieldName(request.getParameter("searchType"));
				accountFormBean.setSearchFieldValue(request.getParameter("searchValue"));
				accountFormBean.setCircleId(request.getParameter("circleId"));
			}
			/* call service to count the no of rows */
			//int noofpages = accountService.getAccountListCount(reportInputDto);
			
			//logger.info("Inside searchAccount().. no of pages..." + noofpages);

			/* call to set lower & upper bound for Pagination */
			//Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);

			/* call service to find all accounts */
			ArrayList accountList = accountService.getAccountList(reportInputDto);
			/*
			 * Account accountDto = (Account) accountList.get(0); int
			 * totalRecords = accountDto.getTotalRecords(); int noofpages =
			 * Utility.getPaginationSize(totalRecords);
			 */

			/* Delimit accountaddress field to 10 chars while displaying in grid */
			Iterator iter = accountList.iterator();
			while (iter.hasNext()) {

				DPCreateAccountDto accountDto = (DPCreateAccountDto) iter
						.next();
				accountDto.setAccountAddress(Utility
						.delemitDesctiption(accountDto.getAccountAddress()));
			}

			//request.setAttribute("PAGES", noofpages);

			/* set ArrayList of Bean objects to FormBean */
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
	 * @throws VirtualizationServiceException
	 */
	public ActionForward getParentAccountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info(" Started... ");
		try {
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
			DPCreateAccountDto accountDTO=new DPCreateAccountDto();
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationInterface = AuthorizationFactory.getAuthorizerImpl();
			ArrayList roleList = authorizationInterface.getUserCredentials(sessionContext.getGroupId(), ChannelType.WEB);
			sessionContext.setRoleList(roleList);
			BeanUtils.copyProperties(accountDTO, accountFormBean);
			accountDTO.setAccountId(sessionContext.getId());
			
			/** pagination implementation */
			if (accountFormBean.getSearchFieldName() == null
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
			/*int noofpages = accountService.getParentAccountListCount(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()), accountFormBean
							.getAccountLevel());
			logger.info("Inside getCircleList().. no of pages..." + noofpages);
			// noofpages = Utility.getPaginationSize(noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));*/
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);
			/* call service to find all accounts */
			accountDTO.setSessionContaxt(sessionContext);
			ArrayList accountList = accountService.getParentAccountList(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()), accountFormBean
							.getAccountLevel(), pagination.getLowerBound(),
					pagination.getUpperBound(), Integer.parseInt(accountFormBean
							.getAccountLevel()),accountDTO);

			// Account accountDto = (Account) accountList.get(0);
			// int totalRecords = accountDto.getTotalRecords();

		/*	logger.info("No of pages" + noofpages);*/

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
		logger.info(" Started... ");
		try {
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			int noofpages = accountService.getParentAccountListCount(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()), accountFormBean
							.getAccountLevel());
			logger.info("Inside getCircleList().. no of pages..." + noofpages);
			// noofpages = Utility.getPaginationSize(noofpages);
			request.setAttribute("PAGES", String.valueOf(noofpages));
			/* call to set lower & upper bound for Pagination */
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);
			/* call service to find all accounts */
			ArrayList accountList = accountService.getParentAccountList(accountFormBean
					.getSearchFieldName(), accountFormBean.getSearchFieldValue(),
					sessionContext, Integer.parseInt(accountFormBean.getCircleId()), accountFormBean
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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;

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
			if (!(authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_ACCOUNT) || authorizationService.isUserAuthorized(sessionContext
							.getGroupId(), ChannelType.WEB,
							AuthorizationConstants.ROLE_VIEW_ACCOUNT_DIST))) {
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

			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
			ReportInputs reportInputDto = new ReportInputs();
			// set data from form accountFormBean

			reportInputDto.setSearchFieldName(searchFieldName);
			reportInputDto.setSearchFieldValue(searchFieldValue);
			reportInputDto.setSessionContext(sessionContext);
			reportInputDto.setCircleId(Integer.parseInt(circleValue));
			reportInputDto.setStatus(status);
			reportInputDto.setStartDt(startDate);
			reportInputDto.setEndDt(endDate);

			ArrayList accountList = accountService.getAccountListAll(reportInputDto);

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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;

			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;

			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
		DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
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
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
			DPCreateAccountFormBean accountFormBean = (DPCreateAccountFormBean) form;
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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

	public ActionForward getParentCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Starting getZone... ");
		Document document = DocumentHelper.createDocument();
		HttpSession session = request.getSession();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
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
		DPCreateAccountService accountService= new DPCreateAccountServiceImpl();
		DPCreateAccountDto accountDto = accountService.getAccountLevelDetails(accountLevel);		
		ajaxCall(request, response, accountDto,"text");
		return null;
	}
	
	//Added by sugandha to check whether stock is dere or not before changing the status
	public ActionForward getStockStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DPCreateAccountService statusService = new DPCreateAccountServiceImpl();
		int intAccountID = Integer.parseInt(request.getParameter("accountId"));
		int intLevelID = Integer.parseInt(request.getParameter("levelID"));
		
		String strMessage = statusService.getStockStatus(intAccountID, intLevelID);
		
		
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		optionElement = root.addElement("option");
		optionElement.addAttribute("message", strMessage);
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "No-Cache");
		PrintWriter out = response.getWriter();
		OutputFormat outputFormat = OutputFormat.createCompactFormat();
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
		writer.flush();
		out.flush();
		return null;
	}
	
	//Added by sugandha ends here
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
	
	
}