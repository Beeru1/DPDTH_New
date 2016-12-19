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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.ibm.virtualization.recharge.beans.AccountTransactionBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.ResponseConstants;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.AccountTransaction;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.AccountTransferMessageBean;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AccountTransactionService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.RechargeService;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.AccountTransactionServiceImpl;
import com.ibm.virtualization.recharge.service.impl.RechargeServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SequenceServiceImpl;
import com.ibm.virtualization.recharge.common.ResourceReader;

/**
 * Controller class for Account to Account Transaction
 * 
 * @author Mohit
 */
public class AccountTransactionAction extends LookupDispatchAction {

	/* Logger for this Action class. */
	private static Logger logger = Logger
			.getLogger(AccountTransactionAction.class.getName());

	/* Local Variables */
	private static final String ACC_TRANSACTION_INIT_SUCCESS = "openAccountTransactionJSP";

	private static final String ACC_TRANSACTION_INIT_ASYNC = "openAccountTransactionJSPAsync";

	private static final String ACC_TRANSACTION_SUCCESS = "initAccountAction";

	private static final String ACC_TRANSACTION_SUCCESS_ASYNC = "initAccountTransactionAsync";

	private static final String ACC_TRANSACTION_FAILURE = "openAccountTransactionJSP";

	private static final String ACC_TRANSACTION_FAILURE_AYNC = "openAccountTransactionJSPAsync";

	// private static final String ACC_TRANSACTION_BEFORE_CONFIRMATION =
	// "openAccountTransactionJSP";

	private static final String ERROR_MESSAGE_FOR_CONFIRMATION = "error.transaction.Confirmation";
	
	private static final String ACC_TRANSACTION_BEFORE_CONFIRMATION = "confirmAccountTransactionJSP";

	private static final String ACC_CANCEL_CONFIRMATION = "cancelConfirmTransactionJSP";

	private static final String ACC_CANCEL_CONFIRMATION_ASYNC = "cancelConfirmTransactionAsyncJSP";

	private static final String ACC_TRANSACTION_BEFORE_ASYNC = "confirmAccountTransactionAsyncJSP";

	// Connection Properties For QueryQueue,OUT Queue
	private static ConnectionBean connBeanAcc = new ConnectionBean();

	static {

		try {
			/** * Setting the Resource Bundle Name for the PropertyReader File ** */
			connBeanAcc.setHost(ResourceReader.getValueFromBundle(
					"account.q.host",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Port Number
			connBeanAcc.setPort(new Integer(ResourceReader.getValueFromBundle(
					"account.q.port",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE)));
			// Setting Channel NAme
			connBeanAcc.setChannel(ResourceReader.getValueFromBundle(
					"account.q.channel",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Queue Manager Name
			connBeanAcc.setQMgr(ResourceReader.getValueFromBundle(
					"account.q.mgr",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
			// Setting Queue Name
			connBeanAcc.setQu(ResourceReader.getValueFromBundle(
					"account.q.name",
					Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE));
		} catch (NumberFormatException e) {
			logger.fatal("Number format exception occured.");
		}

	}

	/**
	 * This function loads the page to make Account to Account Transaction + it
	 * also populates all the childAccounts of the SourceAccountCode(login)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAccountTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB,
				AuthorizationConstants.ROLE_TRANSFER_TO_CHILD_ACC)) {
			logger
					.info(" user not auth to perform this ROLE_TRANSFER_TO_CHILD_ACC activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		logger
				.info(" Initiating the page load process for the Account to Account Transaction");
		/* accTransactionBean is an object of type AccountTransactionBean */
		AccountTransactionBean accTransactionBean = (AccountTransactionBean) form;
		/* transactionService is an object of type RechargeService */
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		/* Set the Source Account */
		UserSessionContext userSessionContext = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		/* ArrayList of the Childs of the SourceAccount */
		ArrayList childAccounts = null;
		try {
			/* Set the List for the Child Account */
			accTransactionBean.reset(mapping, request);
			logger.info(" Request to get child accounts for user "
					+ userSessionContext.getId());
			childAccounts = accountTransactionService
					.getChildAccounts(new Long(userSessionContext.getId())
							.longValue());
			logger
					.info(" Successfully found the childs for the Source Account ");
		} catch (VirtualizationServiceException vse) {
			logger
					.error(
							" Service Exception occured while finding ChildList  ",
							vse);
			/* errors is an object of type ActionErrors */
			ActionErrors errors = new ActionErrors();
			errors.add("genericError", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
		}
		/*
		 * Set the setChildAccountList vairable of the Bean to the Child List
		 * obtained
		 */
		accTransactionBean.setChildAccountList(childAccounts);
		logger.info(" Executed... ");
		return mapping.findForward(ACC_TRANSACTION_INIT_SUCCESS);// jsp
	}

	/**
	 * This function loads the page to make Async Account to Account Transaction +
	 * it also populates all the childAccounts of the SourceAccountCode(login)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAccountTransactionAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");

		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB,
				AuthorizationConstants.ROLE_TRANSFER_TO_CHILD_ACC)) {
			logger
					.info(" user not auth to perform this ROLE_ASYNC_ACCOUNT_TRANSFER activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		logger
				.info(" Initiating the page load process for the Account to Account Transaction");
		/* accTransactionBean is an object of type AccountTransactionBean */
		AccountTransactionBean accTransactionBean = (AccountTransactionBean) form;
		/* transactionService is an object of type RechargeService */
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		/* Set the Source Account */
		UserSessionContext userSessionContext = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		/* ArrayList of the Childs of the SourceAccount */
		ArrayList childAccounts = null;
		try {
			/* Set the List for the Child Account */
			accTransactionBean.reset(mapping, request);
			accTransactionBean.setAsync(true);
			logger.info(" Request to get child accounts for user "
					+ userSessionContext.getId());
			childAccounts = accountTransactionService
					.getChildAccounts(new Long(userSessionContext.getId())
							.longValue());
			logger
					.info(" Successfully found the childs for the Source Account ");
		} catch (VirtualizationServiceException vse) {
			logger
					.error(
							" Service Exception occured while finding ChildList  ",
							vse);
			/* errors is an object of type ActionErrors */
			ActionErrors errors = new ActionErrors();
			errors.add("genericError", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
		}
		/*
		 * Set the setChildAccountList vairable of the Bean to the Child List
		 * obtained
		 */
		accTransactionBean.setChildAccountList(childAccounts);
		logger.info(" Executed... ");
		return mapping.findForward(ACC_TRANSACTION_INIT_ASYNC);// jsp
	}

	/**
	 * accountTransaction methods for transaction between two account this
	 * methods called by the ActionServlet based on the key which is passed from
	 * the browser.
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward accountTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		logger.info(" Initiating the Account to Account Transaction");

		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext.getGroupId(),
				ChannelType.WEB,
				AuthorizationConstants.ROLE_TRANSFER_TO_CHILD_ACC)) {
			logger
					.info(" user not auth to perform this ROLE_TRANSFER_TO_CHILD_ACC activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		ActionForward forward = new ActionForward();
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		AccountTransaction dto = new AccountTransaction();
		ActionErrors errors = new ActionErrors();
		AccountTransactionBean bean = (AccountTransactionBean) form;
		long sourceAccountId = -1;
		/* ActionForward class initilization */

		forward = confirmTransaction(accountTransactionService, dto, errors,
				bean, sourceAccountId, request, forward, mapping);

		return forward;
	}

	private ActionForward confirmTransaction(
			AccountTransactionService accountTransactionService,
			AccountTransaction dto, ActionErrors errors,
			AccountTransactionBean bean, long sourceAccountId,
			HttpServletRequest request, ActionForward forward,
			ActionMapping mapping) throws Exception {
		try {
			/* AccountTransactionBean Initilization */
			/* Set the receivers accountId to TransactionDTO */
			UserSessionContext userSessionContext = (UserSessionContext) request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			sourceAccountId = new Long(userSessionContext.getId()).longValue();
			/*
			 * it is set bcoz to update status in trasaction Master table after
			 * updating balance, it requires contextId
			 */
			bean.setContextSourceId(String.valueOf(sourceAccountId));
			double amount = Double.parseDouble(bean.getTransactionAmount());
			/* Set source mobile no */
			logger.info("destination==" + bean.getReceivingAccount());
			dto.setDestinationMobileNo(bean.getReceivingAccount());
			/* set transaction amount */
			dto.setTransactionAmount(amount);
			/* Set the Channel */
			dto.setTransactionChannel(ChannelType.WEB);
			dto.setTransactionState(TransactionState.WEB);
			/* set transaction id */
			// RechargeService rechargeService = new RechargeServiceImpl();
			// dto.setTransactionId(rechargeService.getTransactionId());
			/* set circle id */
			dto.setCircleId(userSessionContext.getCircleId());
			dto.setAccountCode(userSessionContext.getLoginName());
			logger.info("Account Code ===========" + dto.getAccountCode());
			/* set source account id */
			dto.setSourceAccountId(sourceAccountId);
			logger.info(" Request to perform account transaction by user "
					+ userSessionContext.getId());
			dto.setUserSessionContext(userSessionContext);
			accountTransactionService.confirmTransaction(dto);
			logger.info("After performing validation TransactionId="
					+ dto.getTransactionId());
			/*
			 * Set the varibales in bean so that the processing can be performed
			 * after confirmation
			 */
			bean
					.setDestinationId(String.valueOf(dto
							.getDestinationAccountId()));
			bean.setSourceId(String.valueOf(sourceAccountId));
			bean.setCreditedAmount(String.valueOf(dto.getCreditedAmount()));
			logger.info("Rate=" + dto.getRate());
			bean.setAccountCode(dto.getAccountCode());
			logger.info("Account Code ===========" + dto.getAccountCode());
			bean.setRate(String.valueOf(dto.getRate()));
			bean.setTransactionId(String.valueOf(dto.getTransactionId()));
			bean.setReceivingAccount(dto.getDestinationMobileNo());
			logger
					.info(" After Request to perform account transaction by user "
							+ userSessionContext.getId());
			/* call jsp page to display the Jsp page for Confirmation */
			// Added code to displaying child list
			bean.setChildAccountList(accountTransactionService
					.getChildAccounts(sourceAccountId));
			logger.info("Successfully calculated the Transaction Details");

			ArrayList alAcList = bean.getChildAccountList();
			Iterator itr = alAcList.iterator();
			Account objAccount;
			String recievingAccountCode = "";
			while (itr.hasNext()) {
				objAccount = (Account) itr.next();
				if (bean.getReceivingAccount().equalsIgnoreCase(
						objAccount.getMobileNumber())) {
					 recievingAccountCode = objAccount.getLoginName();
					
				}
			}

//			String[] arrError = { bean.getTransactionAmount(),
//					bean.getCreditedAmount(), bean.getRate(),
//					recievingAccountCode };
			String[] arrError = { String.valueOf(dto.getTransactionAmount()),
					bean.getCreditedAmount(), bean.getRate(),
					recievingAccountCode };
			errors.add("ConfirmError", new ActionError(
					ERROR_MESSAGE_FOR_CONFIRMATION, arrError));

			saveErrors(request, errors);
			if (bean.getAsync()) {
				// forward = mapping.findForward(ACC_TRANSACTION_INIT_ASYNC);
				forward = mapping.findForward(ACC_TRANSACTION_BEFORE_ASYNC);
			} else {
				forward = mapping
						.findForward(ACC_TRANSACTION_BEFORE_CONFIRMATION);
			}

			return forward;
		} catch (VirtualizationServiceException e) {
			bean.setTransactionId("");
			logger.error(" Service Exception caught  redirected to target  "
					+ ACC_TRANSACTION_FAILURE, e);
			bean.setChildAccountList(accountTransactionService
					.getChildAccounts(sourceAccountId));
			errors.add("genericError", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			if (bean.getAsync()) {
				forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);

			} else {
				forward = mapping.findForward(ACC_TRANSACTION_FAILURE);

			}
		} catch (Exception exp) {
			bean.setTransactionId("");
			logger
					.error(" Exception caught " + exp.getMessage()
							+ "  redirected to target  "
							+ ACC_TRANSACTION_FAILURE, exp);
			bean.setChildAccountList(accountTransactionService
					.getChildAccounts(sourceAccountId));
			errors.add("genericError", new ActionError(exp.getMessage()));
			saveErrors(request, errors);
			if (bean.getAsync()) {
				forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);

			} else {
				forward = mapping.findForward(ACC_TRANSACTION_FAILURE);

			}
		}
		logger.info(" Executed... ");
		return forward;
	}

	public ActionForward transferByQ(
			AccountTransactionService accountTransactionService,
			AccountTransaction dto, ActionErrors errors,
			AccountTransactionBean bean, long sourceAccountId,
			HttpServletRequest request, ActionForward forward,
			ActionMapping mapping) throws Exception {
		AccountTransferMessageBean accountTransferBean = new AccountTransferMessageBean();
		Date initialRequestTime=new Date();
		accountTransferBean.setRequestTime(Utility.getRequestTimeDateFormat());
		// Set Channel Type
		accountTransferBean.setChannelType(ChannelType.WEB);
		try {
			/* AccountTransactionBean Initilization */
			/* Set the receivers accountId to TransactionDTO */
			UserSessionContext userSessionContext = (UserSessionContext) request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			dto.setUserSessionContext(userSessionContext);
			sourceAccountId = new Long(userSessionContext.getId()).longValue();
			AccountService accountService = new AccountServiceImpl();
			Account account = accountService.getAccount(userSessionContext
					.getId());

			accountTransferBean.setPassword(account.getMPassword());
			accountTransferBean.setDestinationMobileNo(bean
					.getReceivingAccount());
			accountTransferBean.setRequesterMobileNo(account.getMobileNumber());
//			set the requesting system IP Address as cell ID
			accountTransferBean.setCellId(request.getRemoteAddr());
			double amount = Double.parseDouble(bean.getTransactionAmount());
			dto.setDestinationAccountId(Long.parseLong(bean.getDestinationId()));
			accountTransferBean.setTransactionAmount(amount);
			try {
				// Generate Transaction ID
				SequenceService seqService = new SequenceServiceImpl();
				accountTransferBean.setTransactionId(seqService
						.getNextTransID());
				accountTransferBean
						.setTransactionStatus(TransactionStatus.SUCCESS);

			} catch (VirtualizationServiceException vexp) {
				logger.error("Some exception occured ", vexp);
				logger.info(ResponseConstants.RM_TRANSID_EXCEPTION);
			}
			if (!Utility.putToQ(accountTransferBean, connBeanAcc)) {
				// Print the response back to the servlet
				logger.info("Message processing failed.");

				errors.add("genericError", new ActionError(
						ExceptionCode.ERROR_TRANSACTION_FAILED));
			} else {
				logger
						.info("::Message put Successfully in Q with parameters::\nSourceMobileNumber="
								+ accountTransferBean.getRequesterMobileNo()
								+ "\n Destination MobileNumber="
								+ accountTransferBean.getDestinationMobileNo()
								+ "\n Amount To Transfer="
								+ accountTransferBean.getTransactionAmount()
								+ "\n Transaction Id = "
								+ accountTransferBean.getTransactionId());

				errors.add("ConfirmError", new ActionError(
						ERROR_MESSAGE_FOR_CONFIRMATION, bean
								.getTransactionAmount(), bean
								.getCreditedAmount(), bean.getRate(), bean
								.getReceivingAccount()));
				errors.add("genericError", new ActionError(
						Constants.SUCCESS_TRANSACTION, accountTransferBean
								.getTransactionId()));
			}

			saveErrors(request, errors);
			forward = mapping.findForward(ACC_TRANSACTION_SUCCESS_ASYNC);

			return forward;
		} catch (VirtualizationServiceException e) {
			bean.setTransactionId("");
			logger.error(" Service Exception caught  redirected to target  "
					+ ACC_TRANSACTION_FAILURE_AYNC, e);
			bean.setChildAccountList(accountTransactionService
					.getChildAccounts(sourceAccountId));
			errors.add("genericError", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);
		} catch (Exception exp) {
			bean.setTransactionId("");
			logger.error(
					" Exception caught " + exp.getMessage()
							+ "  redirected to target  "
							+ ACC_TRANSACTION_FAILURE_AYNC, exp);
			bean.setChildAccountList(accountTransactionService
					.getChildAccounts(sourceAccountId));
			errors.add("genericError", new ActionError(exp.getMessage()));
			saveErrors(request, errors);
			forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);// a
		}
		logger.info(" Executed... ");
		return forward;

	}

	public ActionForward UpdateBalancesAfterConfirmation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		logger.info(" Initiating function UpdateAfterConfirmation");
		ActionForward forward = new ActionForward();
		AccountTransaction dto = new AccountTransaction();
		ActionErrors errors = new ActionErrors();
		AccountTransactionBean bean = (AccountTransactionBean) form;
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		UserSessionContext userSessionContext = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		//double debitAmount = Double.parseDouble(bean.getTransactionAmount());
		// Convert the transaction Amount to contain digit upto 2 decimal places
		double debitAmount = Double.parseDouble(String.valueOf(Utility
				.roundDouble(Double.valueOf(bean.getTransactionAmount()), 2)));
		double creditAmount = Double.parseDouble(bean.getCreditedAmount());
		long contextSourceId = Long.parseLong(bean.getContextSourceId());

		dto.setUserSessionContext(userSessionContext);
		/* if async */

		if (bean.getAsync()) {
			forward = transferByQ(accountTransactionService, dto, errors, bean,
					contextSourceId, request, forward, mapping);
		}
		/* if sync */
		else {

			/* Set the RequestTime into the bean */
			// bean.setRequestTime(Utility.getFormattedDate());
			/* Set the RequestTime into the dto */
			dto.setRequestTime(bean.getRequestTime());

			// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			/* Presently setting the IP Address in the PhysicalId into the DTO */
			// bean.setPhysicalId(i.getHostAddress());
			/* Presently setting the IP Address in the PhysicalId into the DTO */
			// dto.setPhysicalId(bean.getPhysicalId());
			//set the requesting system IP Address as cell ID
			dto.setCellId(request.getRemoteAddr());
			dto.setDestinationMobileNo(bean.getReceivingAccount());
			dto.setTransactionChannel(ChannelType.WEB);
			dto.setSourceAccountId(Long.parseLong(bean.getSourceId()));
			dto.setTransactionAmount(debitAmount);
			dto
					.setDestinationAccountId(Long.parseLong(bean
							.getDestinationId()));
			dto.setCreditedAmount(creditAmount);
			dto.setTransactionId(Long.parseLong(bean.getTransactionId()));
			dto.setContextSourceId(contextSourceId);
			dto.setTransactionState(TransactionState.WEB);
			/* set transaction id */
			RechargeService rechargeService = new RechargeServiceImpl();
			dto.setTransactionId(rechargeService.getTransactionId());
			logger.info("TransactionId=" + bean.getTransactionId());
			logger.info("contextSourceId=" + contextSourceId);
			errors.clear();
			try {
				/* transactionService is an object of type RechargeService */
				logger.info("Before updating balances of related users. ");
				int transStatus = accountTransactionService
						.doAccountTransactionSyncAfterConfirmation(dto);

				if (TransactionStatus.DUPLICATE.getValue() == transStatus
						|| TransactionStatus.BLACKOUT.getValue() == transStatus) {
					errors.add("name", new ActionError(
							ExceptionCode.Transaction.DUPLICATE_REQUEST));
					saveErrors(request, errors);
					return mapping.findForward(ACC_TRANSACTION_SUCCESS);
				}

				bean.setTransactionId("");
				errors.add("name", new ActionError(
						Constants.SUCCESS_TRANSACTION_SYNC));
				logger.info(" Account to Account recharge successful. ");
				if (bean.getAsync()) {
					forward = mapping
							.findForward(ACC_TRANSACTION_SUCCESS_ASYNC);// action
				} else {
					forward = mapping.findForward(ACC_TRANSACTION_SUCCESS);// action
				}

			} catch (VirtualizationServiceException e) {
				bean.setFlag(true);
				logger.info("contextSourceId=" + contextSourceId);
				logger.error(
						" Service Exception caught  redirected to target  "
								+ ACC_TRANSACTION_FAILURE, e);
				bean.setChildAccountList(accountTransactionService
						.getChildAccounts(contextSourceId));
				errors.add("genericError", new ActionError(e.getMessage()));
				if (bean.getAsync()) {
					forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);// action
				} else {
					forward = mapping.findForward(ACC_TRANSACTION_FAILURE);
				}

			} catch (Exception exp) {
				bean.setFlag(true);
				logger.info("contextSourceId=" + contextSourceId);
				logger.error(" Exception caught " + exp.getMessage()
						+ "  redirected to target  " + ACC_TRANSACTION_FAILURE,
						exp);
				bean.setChildAccountList(accountTransactionService
						.getChildAccounts(contextSourceId));
				errors.add("genericError", new ActionError(exp.getMessage()));
				if (bean.getAsync()) {
					forward = mapping.findForward(ACC_TRANSACTION_FAILURE_AYNC);// action
				} else {
					forward = mapping.findForward(ACC_TRANSACTION_FAILURE);
				}
			}
		}
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return forward;
	}

	/* Method called on cancellation of Account to Account Transaction */
	public ActionForward cancelConfirmation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Starting cancellation process for confirmation");
		AccountTransactionBean accTransactionBean = (AccountTransactionBean) form;
		/* transactionService is an object of type RechargeService */
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		/* Set the Source Account */
		UserSessionContext userSessionContext = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		/* ArrayList of the Childs of the SourceAccount */
		ArrayList childAccounts = null;
		try {
			/* Set the List for the Child Account */
			// accTransactionBean.reset(mapping, request);
			logger.info(" Request to get child accounts for user "
					+ userSessionContext.getId());
			childAccounts = accountTransactionService
					.getChildAccounts(new Long(userSessionContext.getId())
							.longValue());
			logger
					.info(" Successfully found the childs for the Source Account ");
		} catch (VirtualizationServiceException vse) {
			logger
					.error(
							" Service Exception occured while finding ChildList  ",
							vse);
			/* errors is an object of type ActionErrors */
			ActionErrors errors = new ActionErrors();
			errors.add("genericError", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
		}
		/*
		 * Set the setChildAccountList vairable of the Bean to the Child List
		 * obtained
		 */
		accTransactionBean.setChildAccountList(childAccounts);
		logger.info(" Executed... ");
		return mapping.findForward(ACC_CANCEL_CONFIRMATION);

	}

	/* Method called on cancellation of Account to Account Transaction Async */
	public ActionForward cancelConfirmationAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("Starting cancellation process for confirmation");
		AccountTransactionBean accTransactionBean = (AccountTransactionBean) form;
		/* transactionService is an object of type RechargeService */
		AccountTransactionService accountTransactionService = new AccountTransactionServiceImpl();
		/* Set the Source Account */
		UserSessionContext userSessionContext = (UserSessionContext) request
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		/* ArrayList of the Childs of the SourceAccount */
		ArrayList childAccounts = null;
		try {
			/* Set the List for the Child Account */
			// accTransactionBean.reset(mapping, request);
			logger.info(" Request to get child accounts for user "
					+ userSessionContext.getId());
			childAccounts = accountTransactionService
					.getChildAccounts(new Long(userSessionContext.getId())
							.longValue());
			logger
					.info(" Successfully found the childs for the Source Account ");
		} catch (VirtualizationServiceException vse) {
			logger
					.error(
							" Service Exception occured while finding ChildList  ",
							vse);
			/* errors is an object of type ActionErrors */
			ActionErrors errors = new ActionErrors();
			errors.add("genericError", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
		}
		/*
		 * Set the setChildAccountList vairable of the Bean to the Child List
		 * obtained
		 */
		accTransactionBean.setChildAccountList(childAccounts);
		logger.info(" Executed... ");
		return mapping.findForward(ACC_CANCEL_CONFIRMATION_ASYNC);

	}

	/*
	 * 
	 * @see org.apache.struts.actions.LookupDispatchAction#getKeyMethodMap()
	 */
	protected Map getKeyMethodMap() {
		logger
				.info(" Find the Action to be called for Account to Account Transaction");
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.account_transfer", "accountTransaction");
		map.put("button.initAccountTransaction", "initAccountTransaction");
		map.put("button.initAccountTransactionAsync",
				"initAccountTransactionAsync");
		map.put("button.confirm_transfer", "UpdateBalancesAfterConfirmation");
		map.put("button.confirm_cancel", "cancelConfirmation");
		map.put("button.confirm_cancel_async", "cancelConfirmationAsync");

		return map;
	}

}