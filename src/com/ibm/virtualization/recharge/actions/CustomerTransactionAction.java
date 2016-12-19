/*****************************************************************************\
 **
 ** Virtualization - Recharge.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.virtualization.recharge.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.CustomerTransactionFormBean;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResponseConstants;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.CustomerTransaction;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.RechargeTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.RechargeMessageBean;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.RechargeService;
import com.ibm.virtualization.recharge.service.ReportService;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.RechargeServiceImpl;
import com.ibm.virtualization.recharge.service.impl.ReportServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SequenceServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;


public class CustomerTransactionAction extends LookupDispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger
			.getLogger(CustomerTransactionAction.class.getName());

	/* Local Variables */
	private static final String CUST_TRANSACTION_INIT_SUCCESS = "openCustomerTransactionjsp";

	private static final String CUST_TRANSACTION_INIT_ASYNC = "openCustomerTransactionAsyncjsp";

	private static final String CUST_TRANSACTION_SUCCESS_ASYNC = "intiCustomerTransactionActionAsync";

	private static final String CUST_TRANSACTION_FAILURE = "openCustomerTransactionjsp";

	// Mobile Number Series
	// private ArrayList noSeries = null;

	private static final String CUST_TRANSACTION_BEFORE_CONFIRMATION = "openCustomerTransactionjsp";

	private static final String CUST_TRANSACTION_BEFORE_CONFIRMATION_ASYNC = CUST_TRANSACTION_INIT_ASYNC;

	private static final String ERROR_MESSAGE_FOR_CONFIRMATION = "error.transaction.customer_Confirmation";

	private static final String OPEN_SEARCH_PAGE_CUSTOMER_TRANS = "searchCustomerTransactionjsp";
	
	private static final String OPEN_SEARCH_PAGE_CUSTOMER_TRANS_ID = "searchCustomerTransactionIdjsp";
	
	private static final String ERROR_RECORD= "error";

	
	
	/* Default constructor for class CustomerTransactionAction */
	public CustomerTransactionAction() {
	}

	/*
	 * (non-javadoc)
	 * 
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 */
	protected Map getKeyMethodMap() {
		logger.info("  Finding the method to be called for Customers Account");
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.CustomerTransaction", "customerTransaction");
		map.put("button.initCustomerTransaction", "initCustomerTransaction");

		map.put("button.customerTransactionAsync", "customerTransactionAsync");
		map.put("button.initCustomerTransactionAsync",
				"initCustomerTransactionAsync");

		map.put("button.CustomerTransaction.confirm", "calculateTransaction");
		map.put("button.CustomerTransaction.callCustomerTrans",
				"callCustomerTrans");
		map.put("button.CustomerTransaction.searchCustomerTrans",
				"searchCustomerTrans");
		map.put("button.CustomerTransaction.downloadCustomerTransList",
				"downloadCustomerTransList");
		map.put("button.CustomerTransaction.searchCustomerTransWithId",
		"searchCustomerTransWithId");
		map.put("button.detail", "searchCustomerTransWithId");
		map.put("button.CustomerTransaction.searchPostPaidTransaction", "searchPostPaidTransaction");
		return map;
	}

	/**
	 * This method will be called when the page CreateTransaction_Customer.jsp
	 * is loaded
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param req
	 *            The HTTP request we are processing
	 * @param res
	 *            The HTTP response we are creating
	 * @return Describes where and how control should be forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward initCustomerTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		/* forward is an object of type ActionForward */
		logger.info(" Started... ");
		HttpSession session = req.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CAN_RECHARGE)) {
			logger.info(" user not auth to perform this ROLE_CAN_RECHARGE activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(req, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		logger.info(" Loading the page to recharge Customers Account");
		// Forward control to the appropriate 'Success' URI
		ActionForward forward = mapping
				.findForward(CUST_TRANSACTION_INIT_SUCCESS);
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;
		
		beanObj.reset(mapping, req);
		logger.info(" Executed... ");
		return forward;
	}

	/**
	 * This method will be called when the page CreateTransaction_Customer.jsp
	 * is loaded
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param req
	 *            The HTTP request we are processing
	 * @param res
	 *            The HTTP response we are creating
	 * @return Describes where and how control should be forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward initCustomerTransactionAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		/* forward is an object of type ActionForward */
		logger.info(" Started... ");
		
		HttpSession session = req.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CAN_RECHARGE)) {
			logger.info(" user not auth to perform this ROLE_CAN_RECHARGE activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(req, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		logger.info(" Loading the page to recharge Customers Account");
		// Forward control to the appropriate 'Success' URI
		ActionForward forward = mapping
				.findForward(CUST_TRANSACTION_INIT_ASYNC);
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;

		if(req.getAttribute("transactionId") != null && 
				!("".equalsIgnoreCase(String.valueOf(req.getAttribute("transactionId"))))){
			beanObj.setTransactionId(String.valueOf(req.getAttribute("transactionId")));
			beanObj.setTransFlag(Boolean.parseBoolean(String.valueOf(req.getAttribute("transFlag"))));
		
		}
		
		beanObj.reset(mapping, req);
		beanObj.setAsync(true);
		logger.info(" Executed... ");
		return forward;
	}

	/**
	 * This method is called on submit of the CreateTransaction_Customer.jsp
	 * page.It Creates the record for the transaction Customer mobile recharged
	 * by the Seller
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param req
	 *            The HTTP request we are processing
	 * @param res
	 *            The HTTP response we are creating
	 * @returns ActionForward which describes where and how control should be
	 *          forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward customerTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		logger.info(" Started... ");
		/* errors is an object of type ActionErrors */
		ActionErrors errors = new ActionErrors();
		/* forward is an object of type ActionForward */
		ActionForward forward = null;
		/* ServiceObj is an object of type RechargeService */
		RechargeService serviceObj = new RechargeServiceImpl();
		/* beanObj is an objet of type CustomerTransactionFormBean */
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;
		/* dto is an object of type TransactionDTO */
		RechargeTransaction dto = new RechargeTransaction();
		logger
				.info(" Initiating the RechargeService to recharge Customers Account");
		try {

			/* Set the RequestTime into the bean */
			//beanObj.setRequestTime(Utility.getFormattedDate());

			/* Presently setting the Ip Address in the PhysiaclId in the bean */
			// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			// beanObj.setPhysicalId(i.getHostAddress());
			/* Set the Source Account */
			UserSessionContext userSessionContext = (UserSessionContext) req
					.getSession().getAttribute(Constants.AUTHENTICATED_USER);

			AccountService accountService = new AccountServiceImpl();
 			Account account = accountService.getAccount(userSessionContext.getId());
			// set requesterMobile no
			dto.setSourceMobileNo(account.getMobileNumber());
			
			
			/* Set the RequestTime into the dto */
			/* Presently setting the Ip Address in the PhysiaclId in the DTO */
			// dto.setPhysicalId(beanObj.getPhysicalId());
			dto.setSourceAccountId(userSessionContext.getId());
			dto.setTransactionState(TransactionState.WEB);
			/* Set the Customers Mobile Number */
			dto.setDestinationMobileNo(beanObj.getMobileNo());
			/* Set the Transaction Amount */
			dto.setTransactionAmount(Double.parseDouble(beanObj
					.getTransactionAmount()));
			/* Set the Transaction Channel */
			dto.setTransactionChannel(ChannelType.WEB);
			/* set circle id */
			dto.setCircleId(userSessionContext.getCircleId());
			
			/* set circle Code */
			dto.setSourceCircleCode(userSessionContext.getCircleCode());
			/* set transaction type */
			dto.setRequestType(RequestType.RECHARGE);
			dto.setTransactionType(TransactionType.RECHARGE);
			long transactionId = serviceObj.getTransactionId();

			dto.setTransactionId(transactionId);
//			set the requesting system IP Address as cell ID
			dto.setCellId(req.getRemoteAddr());
			dto.setUserSessionContext(userSessionContext);
			/*
			 * Call Nethod to complete Customer Transaction and check whether
			 * user is authenticate for this service or not
			 */
			logger.info(" Request to performa customer transaction by user "
					+ userSessionContext.getId());
			int transStatus = serviceObj.doCustomerTransaction(dto);
			if (TransactionStatus.DUPLICATE.getValue() == transStatus
					|| TransactionStatus.BLACKOUT.getValue() == transStatus){
				errors.add("genericError", new ActionError(
						ExceptionCode.Transaction.DUPLICATE_REQUEST));
				saveErrors(req, errors);
				return mapping.findForward(CUST_TRANSACTION_INIT_SUCCESS);
			}

				logger
						.info("Successfully recharged the Customer's Account. TransactionId: "
								+ dto.getTransactionId());
			/* Add the Success message to the errors */
			errors.add("genericError", new ActionError(
					Constants.SUCCESS_TRANSACTION_SYNC));
			forward = mapping.findForward(CUST_TRANSACTION_INIT_SUCCESS);
		} catch (VirtualizationServiceException e) {

			logger
					.error(
							" caught Service Exception  redirected to target  failure ",
							e);
			if (ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED
					.equalsIgnoreCase(e.getMessage())) {
				errors.add("genericError", new ActionError(e.getMessage()));
				forward = mapping.findForward(Constants.USER_NOT_AUTHORISED);
			} else {
				errors.add("genericError", new ActionError(e.getMessage()));
				forward = mapping.findForward(CUST_TRANSACTION_FAILURE);
			}
		} catch (Exception e) {
			logger
					.info(
							" caught Service Exception  redirected to target  failure ",
							e);
			errors.add("genericError", new ActionError(
					ExceptionCode.Transaction.ERROR_TRANSACTION_RECHARGE));
			forward = mapping.findForward(CUST_TRANSACTION_FAILURE);
		}
		logger.info(" successful  redirected to : Success");
		saveErrors(req, errors);
		/* Forward control to the appropriate 'Success' to View the jsp Page */
		logger.info(" Executed... ");
		return forward;
	}

	/**
	 * This method is called on submit of the
	 * CreateTransaction_CustomerAsynch.jsp page.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param req
	 *            The HTTP request we are processing
	 * @param res
	 *            The HTTP response we are creating
	 * @returns ActionForward which describes where and how control should be
	 *          forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward customerTransactionAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		logger.info(" Started... ");
		/* errors is an object of type ActionErrors */
		ActionErrors errors = new ActionErrors();
		/* forward is an object of type ActionForward */
		ActionForward forward = null;

		// Bean Used throughout the Recharge Process
		RechargeMessageBean rechargeBean = new RechargeMessageBean();
		
		rechargeBean.setRequestTime(Utility.getRequestTimeDateFormat());
		// Set Channel Type
		rechargeBean.setChannelType(ChannelType.WEB);
		// Connection Properties For Request Queue
		ConnectionBean connBeanReq = null;

		/* beanObj is an objet of type CustomerTransactionFormBean */
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;
		logger
				.info(" Initiating the RechargeService to recharge Customers Account");
		UserSessionContext userSessionContext = (UserSessionContext) req
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		AccountService accountService = new AccountServiceImpl();
		Account account = accountService.getAccount(userSessionContext.getId());
		// set mobile no
		rechargeBean.setSubscriberMobileNo(beanObj.getMobileNo());
		rechargeBean.setSourceAccountId(userSessionContext.getId());
		rechargeBean.setRequesterMobileNo(account.getMobileNumber());
		rechargeBean.setPassword(account.getMPassword());
		rechargeBean.setLoginId(account.getMobileNumber());
		/* Setting the card group in the rechargeBean */
		rechargeBean.setCardGroup(beanObj.getCardGroup());
//		set the requesting system IP Address as cell ID
		rechargeBean.setCellId(req.getRemoteAddr());
		// Set rechargeAmt in MessageBean Object
		rechargeBean.setRechargeAmount(Double.parseDouble(beanObj
				.getTransactionAmount()));

		/* Set the RequestTime into the bean */
		// rechargeBean.setRequestTime(new java.util.Date().toString());
		/* Setting the IP Address in the PhysicalId field */
		// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
		// rechargeBean.setPhysicalId(i.getHostAddress());

		MobileNumberSeries noSeries = CacheFactory.getCacheImpl()
				.getNumberSeries(
						Long.parseLong(rechargeBean.getSubscriberMobileNo()),
						TransactionType.RECHARGE.name());
		if (noSeries == null) {
			// Subscriber number Not Present In Any Number Series
			logger.info(ResponseConstants.RM_SUBSNO_NUM_SERIES_FAILED);
			forward = mapping.findForward(CUST_TRANSACTION_SUCCESS_ASYNC);
			errors
					.add(
							"name",
							new ActionError(
									ExceptionCode.Authentication.ERROR_USER_SUBSCRIBER_NOT_FOUND));
			saveErrors(req, errors);
			return forward;
		} else {

			logger.info("get mobile number in series ");
			// Set Request Q Properties
			connBeanReq = noSeries.getReqConnBean();
			// Set IN Q Properties
			rechargeBean.setConnBeanIn(noSeries.getInConnBean());
			// Set OUT Q Properties
			rechargeBean.setConnBeanOut(noSeries.getOutConnBean());

			// Generate Transaction ID
			try {

				SequenceService seqService = new SequenceServiceImpl();
				rechargeBean.setTransactionId(seqService
						.getNextRechargeTransID());
				logger.info(" Transaction ID : "
						+ rechargeBean.getTransactionId());
				// Put to Request Queue
				if (!Utility.putToQ(rechargeBean, connBeanReq)) {
					logger.info("Message processing failed.");
					errors.add("name", new ActionError(
							ResponseConstants.RM_MQ_EXCEPTION));
				}
				// Print the response back to the servlet
				logger.info("Thank you for your request. "
						+ " \r\nMobile Number: "
						+ rechargeBean.getRequesterMobileNo() + " Message : "
						+ rechargeBean.getSubscriberMobileNo()
						+ " Id for future assistence "
						+ rechargeBean.getTransactionId());

				errors.add("genericError", new ActionError(
						Constants.SUCCESS_TRANSACTION, rechargeBean
								.getTransactionId()));
				
				req.setAttribute("transactionId", rechargeBean.getTransactionId());
				req.setAttribute("transFlag", Boolean.TRUE);

			} catch (VirtualizationServiceException vSE) {
				logger.fatal("Virtualization Service Exception : " + vSE
						+ " occured while getting transactionID .");
				logger.info(ResponseConstants.RM_SQL_EXCEPTION);
			}
		}
		logger.info(" successful  redirected to : Success");
		forward = mapping.findForward(CUST_TRANSACTION_SUCCESS_ASYNC);
		saveErrors(req, errors);
		/* Forward control to the appropriate 'Success' to View the jsp Page */
		logger.info(" Executed... ");
		return forward;
	}

	/**
	 * This method is called on submit of the CreateTransaction_Customer.jsp
	 * page.It confirms the user for the transaction after displaying the
	 * Transaction Information
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param req
	 *            The HTTP request we are processing
	 * @param res
	 *            The HTTP response we are creating
	 * @returns ActionForward which describes where and how control should be
	 *          forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward calculateTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		logger.info(" Started... ");
		/* errors is an object of type ActionErrors */
		ActionErrors errors = new ActionErrors();
		/* forward is an object of type ActionForward */
		ActionForward forward = null;
		/* ServiceObj is an object of type RechargeService */
		RechargeService serviceObj = new RechargeServiceImpl();
		/* beanObj is an objet of type CustomerTransactionFormBean */
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;
		/* dto is an object of type TransactionDTO */
		RechargeTransaction dto = new RechargeTransaction();
		logger
				.info(" Initiating the RechargeService to recharge Customers Account");
		try {
            
			HttpSession session = req.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CAN_RECHARGE)) {
				logger.info(" user not auth to perform this ROLE_CAN_RECHARGE activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(req, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			// Setting the Dto using the Form Bean
			dto = new RechargeTransaction();
			/* Set the Source Account */
			dto.setSourceAccountId(userSessionContext.getId());

			/* Set the Customers Mobile Number */
			dto.setDestinationMobileNo(beanObj.getMobileNo());
			/* Set the Transaction Amount */
			dto.setTransactionAmount(Double.parseDouble(beanObj
					.getTransactionAmount()));
			/* Set the Transaction Channel */
			dto.setTransactionChannel(ChannelType.WEB);
			/* set circle id */
			dto.setCircleId(userSessionContext.getCircleId());
			/* set transaction type */
			dto.setRequestType(RequestType.RECHARGE);
			dto.setTransactionType(TransactionType.RECHARGE);
			dto.setTransactionState(TransactionState.WEB);
			logger.info("Before Setting Account Code ");
			dto.setAccountCode(userSessionContext.getLoginName());
			logger.info("Account Code is = "+ dto.getAccountCode());
			
			/*
			 * Call Method to compute the trasnaction Details that need to be
			 * shown to user for confirmation
			 * 
			 */
			logger.info(" Confirm from the user. Id: "
					+ userSessionContext.getId());
			dto.setUserSessionContext(userSessionContext);
			serviceObj.calculateTransactionInfo(dto);
			/* Add the Success message to the errors */
			logger.info("MoblIeNo=" + beanObj.getMobileNo());
			logger.info("TransactionAmount=" + beanObj.getTransactionAmount());
			beanObj.setMobileNo(String.valueOf(dto.getDestinationMobileNo()));
			beanObj.setTransactionAmount(String.valueOf(dto
					.getTransactionAmount()));
			beanObj.setCreditedAmount(String.valueOf(dto.getCreditedAmount()));
			beanObj.setDebitAmount(String.valueOf(dto.getDebitAmount()));
			beanObj.setServiceTax(String.valueOf(dto.getServiceTax()));
			beanObj.setProcessingFee(String.valueOf(dto.getProcessingFee()));
			beanObj.setEspCommission(String.valueOf(dto.getEspCommission()));
			logger.info("Before setting AccountCode in Dto");
			beanObj.setAccountCode(dto.getAccountCode());
			logger.info("After setting AccountCode in Dto");
			beanObj.setCardGroup(dto.getCardGroup());
			logger.info("After setting CardGroup in Dto");
			beanObj.setFlag(true);

			Object[] params = new Object[] { beanObj.getDebitAmount(),
					beanObj.getCreditedAmount(), beanObj.getServiceTax(),
					beanObj.getProcessingFee(), beanObj.getEspCommission(),beanObj.getAccountCode(),beanObj.getMobileNo() };
			logger.info("Successfully calculated the Transaction Details");
			errors.add("ConfirmError", new ActionError(
					ERROR_MESSAGE_FOR_CONFIRMATION, params));
			saveErrors(req, errors);
			if (beanObj.getAsync()) {
				forward = mapping
						.findForward(CUST_TRANSACTION_BEFORE_CONFIRMATION_ASYNC);
			} else {
				forward = mapping
						.findForward(CUST_TRANSACTION_BEFORE_CONFIRMATION);
			}
		} catch (VirtualizationServiceException e) {

			logger
					.error(
							" caught Service Exception  redirected to target  failure ",
							e);
			if (ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED
					.equalsIgnoreCase(e.getMessage())) {
				errors.add("genericError", new ActionError(e.getMessage()));
				forward = mapping.findForward(Constants.USER_NOT_AUTHORISED);
			} else {
				errors.add("genericError", new ActionError(e.getMessage()));
				if (beanObj.getAsync()) {
					forward = mapping.findForward(CUST_TRANSACTION_INIT_ASYNC);
				} else {
					forward = mapping.findForward(CUST_TRANSACTION_FAILURE);
				}
			}
		} catch (Exception e) {

			logger
					.info(
							" caught Service Exception  redirected to target  failure ",
							e);
			errors.add("genericError", new ActionError(
					ExceptionCode.Transaction.ERROR_TRANSACTION_RECHARGE));
			if (beanObj.getAsync()) {
				forward = mapping.findForward(CUST_TRANSACTION_INIT_ASYNC);
			} else {
				forward = mapping.findForward(CUST_TRANSACTION_FAILURE);
			}
		}
		logger.info(" successful  redirected to : Success");
		saveErrors(req, errors);
		/* Forward control to the appropriate 'Success' to View the jsp Page */
		logger.info(" Executed... ");
		return forward;
	}

	public ActionForward callCustomerTrans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.info("Started...callCustomerTrans ");
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CUSTOMER_TRANSACTION)) {
			logger.info(" user not auth to perform this ROLE_VIEW_CUSTOMER_TRANSACTION activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		CustomerTransactionFormBean beanObj = (CustomerTransactionFormBean) form;
		// set the current date to be the default date;
		beanObj.setStartDate(Utility.getCurrentDateTime());
		beanObj.setEndDate(Utility.getCurrentDateTime());
		
		return mapping.findForward(OPEN_SEARCH_PAGE_CUSTOMER_TRANS);
	}

	public ActionForward searchCustomerTrans(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.debug("Started...searchCustomerTrans ");
		ActionErrors errors = new ActionErrors();
		CustomerTransactionFormBean bean = (CustomerTransactionFormBean) form;
		logger.debug("SearchCirteria =" +bean.getSearchCriteria());
	
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CUSTOMER_TRANSACTION)) {
			logger.info(" user not auth to perform this ROLE_VIEW_CUSTOMER_TRANSACTION activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		
		String transactionId = bean.getTransactionId();
		String requestType;
		if (null != bean.getTransactionType()) {
			requestType = bean.getTransactionType();
		} else {
			requestType = "0";
		}

		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();
		
		ReportInputs mtDTO = new ReportInputs();

		try {

			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			// CustomerTransaction custTransDTO = new CustomerTransaction();
			ReportService customerService = new ReportServiceImpl();
			
			if (request.getParameter("transactionId") == null
					|| request.getParameter("transactionType") == null
					|| request.getParameter("startDate") == null
					|| request.getParameter("endDate") == null) {
				mtDTO.setSearchFieldValue(transactionId);

				if ("0".equals(requestType)) {
					mtDTO.setRequestType(null);
				} else {
					mtDTO.setRequestType(RequestType.valueOf(requestType));
				}

				mtDTO.setStartDt(startDate);
				mtDTO.setEndDt(endDate);
				request.setAttribute("transactionId", transactionId);
				request.setAttribute("transactionType", requestType);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
			} else {
				mtDTO
						.setSearchFieldValue(request
								.getParameter("transactionId"));
				if ("0".equals(request.getParameter("transactionType"))) {
					mtDTO.setRequestType(null);
				} else {
					mtDTO.setRequestType(RequestType.valueOf(request
							.getParameter("transactionType")));
				}

				mtDTO.setStartDt(request.getParameter("startDate"));
				mtDTO.setEndDt(request.getParameter("endDate"));
				request.setAttribute("transactionId", request
						.getParameter("transactionId"));

				if ("0".equals(request.getParameter("transactionType"))) {
					request.setAttribute("transactionType", null);
				} else {
					request.setAttribute("transactionType", request
							.getParameter("transactionType"));
				}

				request.setAttribute("startDate", request
						.getParameter("startDate"));
				request
						.setAttribute("endDate", request
								.getParameter("endDate"));

				bean.setTransactionId(mtDTO.getSearchFieldValue());

				if (null == mtDTO.getRequestType()) {
					bean.setTransactionType("0");
				} else {
					bean.setTransactionType(mtDTO.getRequestType().toString());
				}

				bean.setStartDate(mtDTO.getStartDt());
				bean.setEndDate(mtDTO.getEndDt());
				
			}
			mtDTO.setSessionContext(sessionContext);
			/* call service to count the no of rows */
			/*
			 * int noofpages =
			 * customerService. getCustomerTransactionCount(mtDTO);
			 * logger.info("Inside searchCustomerTrans().. no of pages..." +
			 * noofpages);
			 */

			/* call to set lower & upper bound for Pagination */
			//request.setAttribute("page",bean.getPage());
			Pagination pagination = VirtualizationUtils
					.setPaginationinRequest(request);

			/* call service to find all subscribers with the entered mobile no */
		       	
			if (bean.getSearchCriteria().equalsIgnoreCase("MOBILE")){
				mtDTO.setMobileNo(bean.getMobileNo());
			}else if(bean.getSearchCriteria().equalsIgnoreCase("ABTS")){
				mtDTO.setMobileNo(bean.getAbtsNo());
			}else if(bean.getSearchCriteria().equalsIgnoreCase("DTH")){
				mtDTO.setMobileNo(bean.getDthNo());
			}
			
				
			ArrayList customerTransactionList = customerService
					.getCustomerTransactionList(mtDTO, pagination
							.getLowerBound(), pagination.getUpperBound(),bean.getTransactionId());

			CustomerTransaction customerTransactionDto = (CustomerTransaction) customerTransactionList
					.get(0);
			int totalRecords = customerTransactionDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);

			/* set the list into form bean */
			bean.setDisplayDetails(customerTransactionList);
			if ("0".equals(requestType)) {
				bean.setTransactionType(RequestType.getRequestType(Integer.valueOf(requestType)).toString());
			}
			bean.setStartDate(mtDTO.getStartDt());
			bean.setEndDate(mtDTO.getEndDt());

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
		logger.debug("Executed... ");

		logger.debug("SerachValue="+mtDTO.getSearchFieldValue());
		logger.debug("TransId="+request.getParameter("transactionId"));
		return mapping.findForward(OPEN_SEARCH_PAGE_CUSTOMER_TRANS);
	}

	public ActionForward downloadCustomerTransList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		logger.debug("Started...downloadCustomerTransList ");
		ActionErrors errors = new ActionErrors();
		CustomerTransactionFormBean bean = (CustomerTransactionFormBean) form;
		
		logger.debug("SearchCirteria =" +bean.getSearchCriteria());
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CUSTOMER_TRANSACTION)) {
			logger.info(" user not auth to perform this ROLE_VIEW_CUSTOMER_TRANSACTION activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		String transactionId = bean.getTransactionId();
		String requestType;
		if (null != bean.getTransactionType()) {
			requestType = bean.getTransactionType();
		} else {
			requestType = "0";
		}
		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();

		try {
			
			
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			// CustomerTransaction custTransDTO = new CustomerTransaction();
			ReportInputs mtDTO = new ReportInputs();
			mtDTO.setSessionContext(sessionContext);

			/* call service to find all subscribers with the entered mobile no */
			if (bean.getSearchCriteria().equalsIgnoreCase("MOBILE")){
				mtDTO.setMobileNo(bean.getMobileNo());
			}
			
			mtDTO.setSearchFieldValue(transactionId);
			if ("0".equals(requestType)) {
				mtDTO.setRequestType(null);
			} else {
				mtDTO.setRequestType(RequestType.valueOf(requestType));
			}
			mtDTO.setStartDt(startDate);
			mtDTO.setEndDt(endDate);

			ReportService customerService = new ReportServiceImpl();

			 
			ArrayList customerTransactionList = customerService
					.getCustomerTransactionListAll(mtDTO,transactionId);

			bean.setDisplayDetails(customerTransactionList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
		}
		logger.debug("Executed... ");

		return mapping.findForward(Constants.EXPORT_CUSTOMER_VIEW_LIST);
	}
	
	
	/**
	 * this method use to search record details 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */	
	public ActionForward searchCustomerTransWithId(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		
		logger.debug("Started...searchCustomerTrans ");
		ActionErrors errors = new ActionErrors();
		CustomerTransactionFormBean bean = (CustomerTransactionFormBean) form;
		logger.debug("SearchCirteria =" +bean.getSearchCriteria());
		logger.debug("request bhanu " +request.getParameter("postPaidFlag"));
		logger.debug("request bhanu " +mapping.getAttribute());
		
		try {
			
			ReportService customerService = new ReportServiceImpl();
			if(bean.getTransactionId()!= null && !("".equalsIgnoreCase(bean.getTransactionId()))){
				bean.setTransDetailId(bean.getTransactionId());
			}
			
			/* call service to find all customers */
			ArrayList customerTransactionList = customerService
					.getCustomerTransactionListWithId(bean.getTransDetailId(),bean.getTransactionType());
						
			/* set the list into form bean */
			bean.setDisplayDetails(customerTransactionList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR_RECORD);
		}
		logger.debug("Executed... ");

		return mapping.findForward(OPEN_SEARCH_PAGE_CUSTOMER_TRANS_ID);
	}

	
	/**
	 * this method use to search record details 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws VirtualizationServiceException
	 */	
	public ActionForward searchPostPaidTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws VirtualizationServiceException {
		
		logger.debug("Started...searchCustomerTrans ");
		ActionErrors errors = new ActionErrors();
		CustomerTransactionFormBean bean = (CustomerTransactionFormBean) form;
				
		try {
			ReportService customerService = new ReportServiceImpl();
			if(bean.getTransactionId()!= null && !("".equalsIgnoreCase(bean.getTransactionId()))){
				bean.setTransDetailId(bean.getTransactionId());
			}
			
			/* call service to find all customers */
			ArrayList customerTransactionList = customerService
					.getCustomerTransactionListWithId(bean.getTransDetailId(),bean.getTransactionType());
			bean.setFlagPostPaid(Boolean.TRUE);			
			/* set the list into form bean */
			bean.setDisplayDetails(customerTransactionList);
		} catch (VirtualizationServiceException vse) {
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(
					"error.account.norecord_found"));
			saveErrors(request, errors);
			return mapping.findForward(ERROR_RECORD);
		}
		logger.debug("Executed... ");

		return mapping.findForward(OPEN_SEARCH_PAGE_CUSTOMER_TRANS_ID);
	}
	
}