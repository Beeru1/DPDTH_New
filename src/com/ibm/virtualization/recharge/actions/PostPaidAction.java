package com.ibm.virtualization.recharge.actions;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.framework.bean.ConnectionBean;
import com.ibm.virtualization.framework.bean.TransactionStatus;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.PostPaidBean;
import com.ibm.virtualization.recharge.cache.CacheFactory;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.ResponseConstants;
import com.ibm.virtualization.recharge.common.TransactionState;
import com.ibm.virtualization.recharge.common.TransactionType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.MobileNumberSeries;
import com.ibm.virtualization.recharge.dto.PostpaidTransaction;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.PostPaidMessageBean;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.PostPaidService;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.PostpaidServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SequenceServiceImpl;

public class PostPaidAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(ChangePasswordAction.class
			.getName());

	/* Local Variables */
	private static final String INIT_SUCCESS = "initSuccess";
	
	private static final String INIT_PAGE = "initPage";

	private static final String POSTPAID_SUCCESS = "initBillPaymentAction";

	private static final String POSTPAID_FAILURE = "initSuccess";

	private static final String ERROR_MESSAGE_FOR_CONFIRMATION = "error.postPaid.Confirmation";

	private static final String POSTPAID_TRANSACTION_BEFORE_CONFIRMATION = "openPostPaidJSP";
	
	private static final String OPEN_SEARCH_PAGE_CUSTOMER_TRANS_ID = "searchCustomerTransactionIdjsp";
	
	private static final String ERROR_RECORD= "error";

	
	private static String hostAddress = "";

	static {

		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();

			logger.info("host address is :" + hostAddress);
		} catch (UnknownHostException e) {
			logger.error("exception while finding host address", e);
		}
	}

	/**
	 * init method is called to redirect to Bill Payment Jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_POSTPAID_PAYMENT)) {
			logger.info(" user not auth to perform this ROLE_POSTPAID_PAYMENT activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		/* Redirect  to Bill Payment Jsp. */
		PostPaidBean bean = (PostPaidBean) form;
		if (bean != null) {
			bean.reset(mapping, request);
		}
		
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	
	/**
	 * init method is called to redirect to Bill Payment Jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward initAsync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		logger.info(" Started... ");
		
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_POSTPAID_PAYMENT)) {
			logger.info(" user not auth to perform this ROLE_POSTPAID_PAYMENT activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		/* Redirect  to Bill Payment Jsp. */
		PostPaidBean bean = (PostPaidBean) form;
		if (bean != null) {
			bean.reset(mapping, request);
		}
		
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}
	
	
	/**
	 * This method diplays all the required information to the user which is
	 * necessary for its confirmation
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward calculationBeforeConfirmation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info(" Started... ");
		logger.info(" Initiating the PostPaid Bill Payment Transaction");

		/* ActionForward class initilization */
		PostPaidService postPaidService = null;//new PostpaidServiceImpl();
		ActionForward forward = new ActionForward();
		PostpaidTransaction dto = new PostpaidTransaction();
		ActionErrors errors = new ActionErrors();
		PostPaidBean bean = (PostPaidBean) form;
		try {
			bean.setTransactionId(null);
			/* Set the Source Account */
			UserSessionContext userSessionContext = (UserSessionContext) request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER);
			/* Set the RequestTime into the bean */
			// bean.setRequestTime(new java.util.Date().toString());
			// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			// bean.setPhysicalId(i.getHostAddress());
			/* Set the RequestTime into the dto */
			dto.setRequestTime(bean.getRequestTime());
			/* Presently setting the Ip Address in the dto */
			// dto.setPhysicalId(bean.getPhysicalId());
			dto.setSourceAccountId(userSessionContext.getId());
			dto.setCircleId(userSessionContext.getCircleId());
			dto.setTransactionState(TransactionState.WEB);
			dto.setConfirmationMobileNo(bean.getConfirmMobileNo());
			dto.setDestinationNo(bean.getReceivingNo());
			dto.setAccountCode(userSessionContext.getLoginName());
			bean.setAccountCode(dto.getAccountCode());
			double transactionAmount = Double.parseDouble(bean.getAmount());
			dto.setTransactionAmount(transactionAmount);
			dto.setTransactionChannel(ChannelType.WEB);
			// Temporary setting request type: need to be asked
			
			if (bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_ABTS)) {
				dto.setRequestType(RequestType.POSTPAID_ABTS);
				dto.setTransactionType(TransactionType.POSTPAID_ABTS);
			} else if(bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_DTH)) {
				dto.setTransactionType(TransactionType.POSTPAID_DTH);
				dto.setRequestType(RequestType.POSTPAID_DTH);
			} else {
				dto.setTransactionType(TransactionType.POSTPAID_MOBILE);
				dto.setRequestType(RequestType.POSTPAID_MOBILE);
			}

			logger.info(" Request to perform post paid bill payment "
					+ userSessionContext.getId());
			dto.setUserSessionContext(userSessionContext);
			postPaidService.calculateESPforConfirmation(dto);
			bean.setCreditAmount(String.valueOf(dto.getCreditedAmount()));
			bean.setDebitAmount(String.valueOf(dto.getDebitAmount()));
			bean.setEspCommission(String.valueOf(dto.getEspCommission()));
	
			
			/* Add the confrimation message to the errors */
			
			Object[] params = new Object[] { bean.getAmount(),bean.getReceivingNo(),bean.getEspCommission(),
					userSessionContext.getLoginName(),bean.getDebitAmount(),bean.getCreditAmount()};
			logger.info("Successfully calculated the Transaction Details");
			errors.add("ConfirmError", new ActionError(
					ERROR_MESSAGE_FOR_CONFIRMATION, params));
			
			logger.info(" generating confrimation message successful. ");
			forward = mapping
					.findForward(POSTPAID_TRANSACTION_BEFORE_CONFIRMATION);// action
			bean.setFlag(true);
		} catch (VirtualizationServiceException e) {
			bean.setFlag(false);
			logger.error(" VirtualizationServiceException caught  redirected to target  "
					);
			if (ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED
					.equalsIgnoreCase(e.getMessage())) {
				errors.add("genericError", new ActionError(
						Constants.POSTPAID_USER_NOT_AUTHORISED));
			} else {
				errors.add("genericError", new ActionError(e.getMessage()));
			}
			forward = mapping.findForward(POSTPAID_FAILURE);
		} catch (Exception e) {
			bean.setFlag(false);
			logger.error(" Exception caught " + e.getMessage()
					+ "  redirected to target  " + POSTPAID_FAILURE, e);
			errors.add("genericError", new ActionError(
					ExceptionCode.Transaction.ERROR_TRANSACTION_RECHARGE));
			forward = mapping.findForward(POSTPAID_FAILURE);
		}
		logger.info(" successful  redirected to : Success");
		saveErrors(request, errors);
		/* Forward control to the appropriate 'Success' to View the jsp Page */
		logger.info(" Executed... ");
		return forward;
	}

	/**
	 * This method is called on when the page submits. It Creates the record for
	 * the bill payment made by the source account towards the receiving account
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @returns ActionForward which describes where and how control should be
	 *          forwarded.
	 * @throws java.lang.Exception
	 */
	public ActionForward makeBillPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// Call Service
		logger.info(" Started... ");
		logger.info(" Initiating the PostPaid Bill Payment Transaction");

		/* ActionForward class initilization */
		PostPaidService postPaidService = null;//new PostpaidServiceImpl();
		ActionForward forward = new ActionForward();
		PostpaidTransaction dto = new PostpaidTransaction();
		ActionErrors errors = new ActionErrors();
		PostPaidBean bean = (PostPaidBean) form;
		try {

			/* Set the Source Account */
			UserSessionContext userSessionContext = (UserSessionContext) request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER);

			/* Set the RequestTime into the bean */
			// bean.setRequestTime(new java.util.Date().toString());
			// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			// bean.setPhysicalId(i.getHostAddress());
			/* Set the RequestTime into the dto */
			dto.setRequestTime(bean.getRequestTime());
			/* Presently setting the Ip Address in the dto */
			// dto.setPhysicalId(bean.getPhysicalId());
			dto.setSourceAccountId(userSessionContext.getId());
			dto.setCircleId(userSessionContext.getCircleId());
			dto.setTransactionState(TransactionState.WEB);
			dto.setConfirmationMobileNo(bean.getConfirmMobileNo());
			dto.setDestinationNo(bean.getReceivingNo());
			double transactionAmount = Double.parseDouble(bean.getAmount());
			dto.setTransactionAmount(transactionAmount);
			dto.setMessage(hostAddress);
			dto.setTransactionChannel(ChannelType.WEB);
			// Temporary setting request type: need to be asked
			
			if (bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_ABTS)) {
				dto.setTransactionType(TransactionType.POSTPAID_ABTS);
				dto.setRequestType(RequestType.POSTPAID_ABTS);
				bean.setTransactionType(TransactionType.POSTPAID_ABTS.name());
			} else if(bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_DTH)) {
				dto.setTransactionType(TransactionType.POSTPAID_DTH);
				dto.setRequestType(RequestType.POSTPAID_DTH);
				bean.setTransactionType(TransactionType.POSTPAID_DTH.name());
			} else {
				dto.setTransactionType(TransactionType.POSTPAID_MOBILE);
				dto.setRequestType(RequestType.POSTPAID_MOBILE);
				bean.setTransactionType(TransactionType.POSTPAID_MOBILE.name());
			}
			/* ServiceObj is an object of type RechargeService */
			SequenceService seqService = new SequenceServiceImpl();
			long transactionId = seqService.getNextRechargeTransID();
			/* Set the transactionId */
			dto.setTransactionId(transactionId);

			/*
			 * Call Method to complete postPaidBillPayment transacation and
			 * check whether user is authenticate for this service or not
			 */
			logger.info(" Request to perform post paid bill payment "
					+ userSessionContext.getId());
			dto.setUserSessionContext(userSessionContext);
			int transStatus = postPaidService.doPostpaidTransaction(dto);
			if (TransactionStatus.DUPLICATE.getValue() == transStatus
					|| TransactionStatus.BLACKOUT.getValue() == transStatus) {
				errors.add("genericError", new ActionError(
						ExceptionCode.Transaction.DUPLICATE_REQUEST));
				saveErrors(request, errors);
				return mapping.findForward(POSTPAID_FAILURE);
			}
			/* Add the Success message to the errors */
			errors.add("genericError", new ActionError(
					Constants.SUCCESS_TRANSACTION_SYNC));
			logger.info(" Post paid bill payment successful. ");
			forward = mapping.findForward(INIT_PAGE);// action
		} catch (VirtualizationServiceException e) {

			logger.error("VirtualizationServiceException caught  redirected to target ");
			if (ExceptionCode.Authentication.ERROR_USER_NOT_AUTHORIZED
					.equalsIgnoreCase(e.getMessage())) {
				errors.add("genericError", new ActionError(
						Constants.POSTPAID_USER_NOT_AUTHORISED));
			} else {
				errors.add("genericError", new ActionError(e.getMessage()));
			}
			forward = mapping.findForward(POSTPAID_FAILURE);
		} catch (Exception e) {
			logger.error(" Exception caught " + e.getMessage()
					+ "  redirected to target  " + POSTPAID_FAILURE, e);
			errors.add("genericError", new ActionError(
					ExceptionCode.Transaction.ERROR_TRANSACTION_RECHARGE));
			forward = mapping.findForward(POSTPAID_FAILURE);
		}
		logger.info(" successful  redirected to : Success");
		saveErrors(request, errors);
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
	public ActionForward postPaidTransactionAsync(ActionMapping mapping,
			ActionForm form, HttpServletRequest req, HttpServletResponse res)
			throws Exception {
		logger.info(" Started... ");
		/* errors is an object of type ActionErrors */
		ActionErrors errors = new ActionErrors();
		/* forward is an object of type ActionForward */
		ActionForward forward = null;

		// Bean Used throughout the Recharge Process
		PostPaidMessageBean postPaidBean = new PostPaidMessageBean();
		PostPaidBean bean = (PostPaidBean) form;
		postPaidBean.setRequestTime(Utility.getRequestTimeDateFormat());
		// Set Channel Type
		postPaidBean.setChannelType(ChannelType.WEB);
		// Connection Properties For Request Queue
		ConnectionBean connBeanReq = null;

		/* beanObj is an objet of type CustomerTransactionFormBean */
		PostPaidBean beanObj = (PostPaidBean) form;
		logger
				.info(" Initiating the RechargeService to recharge Customers Account");
		UserSessionContext userSessionContext = (UserSessionContext) req
				.getSession().getAttribute(Constants.AUTHENTICATED_USER);
		AccountService accountService = new AccountServiceImpl();
		Account account = accountService.getAccount(userSessionContext.getId());
		
		if (bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_ABTS)) {
			//postPaidBean.setRequestType(RequestType.POSTPAID_ABTS);
			postPaidBean.setTransactionType(TransactionType.POSTPAID_ABTS);
			bean.setTransactionType(TransactionType.POSTPAID_ABTS.name());
			postPaidBean.setAbtsContactNumber(beanObj.getConfirmMobileNo());
			
		} else if(bean.getPaymentType().equals(Constants.POSTPAID_REQUEST_DTH)) {
			postPaidBean.setTransactionType(TransactionType.POSTPAID_DTH);
			bean.setTransactionType(TransactionType.POSTPAID_DTH.name());
			postPaidBean.setAbtsContactNumber(beanObj.getConfirmMobileNo());
			//postPaidBean.setRequestType(RequestType.POSTPAID_DTH);
		} else {
			postPaidBean.setTransactionType(TransactionType.POSTPAID_MOBILE);
			//postPaidBean.setRequestType(RequestType.POSTPAID_MOBILE);
			bean.setTransactionType(TransactionType.POSTPAID_MOBILE.name());
			postPaidBean.setAbtsContactNumber(beanObj.getReceivingNo());
		}
		
		// set mobile no
		
		postPaidBean.setSubscriberMobileNo(beanObj.getReceivingNo());
		postPaidBean.setSourceAccountId(userSessionContext.getId());
 		postPaidBean.setRequesterMobileNo(account.getMobileNumber());
		postPaidBean.setPassword(account.getMPassword());
		postPaidBean.setLoginId(account.getMobileNumber());
		/* Setting the card group in the rechargeBean */
	//	postPaidBean.setCardGroup(beanObj.getCardGroup());
//		set the requesting system IP Address as cell ID
		postPaidBean.setCellId(req.getRemoteAddr());
		// Set rechargeAmt in MessageBean Object
		postPaidBean.setRechargeAmount(Double.parseDouble(beanObj.getAmount()));

		/* Set the RequestTime into the bean */
		// rechargeBean.setRequestTime(new java.util.Date().toString());
		/* Setting the IP Address in the PhysicalId field */
		// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
		// rechargeBean.setPhysicalId(i.getHostAddress());

		MobileNumberSeries noSeries = CacheFactory.getCacheImpl()
				.getNumberSeries(
						Long.parseLong(bean.getReceivingNo()),
						postPaidBean.getTransactionType().name());
		if (noSeries == null) {
			// Subscriber number Not Present In Any Number Series
			logger.info(ResponseConstants.RM_SUBSNO_NUM_SERIES_FAILED);
			forward = mapping.findForward(INIT_SUCCESS);
			errors
					.add(
							"genericError",
							new ActionError(
									"errors.user.invalidsubscriber"));
			saveErrors(req, errors);
			return forward;
		} else {

			logger.info("get mobile number in series ");
			// Set Request Q Properties
			connBeanReq = noSeries.getReqConnBean();
			
			// Set OUT Q Properties
			postPaidBean.setConnBeanOut(noSeries.getOutConnBean());

			// Generate Transaction ID
			try {

				SequenceService seqService = new SequenceServiceImpl();
				postPaidBean.setTransactionId(seqService
						.getNextRechargeTransID());
				logger.info(" Transaction ID : "
						+ postPaidBean.getTransactionId());
				// Put to Request Queue
				if (!Utility.putToQ(postPaidBean, connBeanReq)) {
					logger.info("Message processing failed.");
					errors.add("name", new ActionError(
							ResponseConstants.RM_MQ_EXCEPTION));
				}
				// Print the response back to the servlet
				logger.info("Thank you for your request. "
						+ " \r\nMobile Number: "
						+ postPaidBean.getRequesterMobileNo() + " Message : "
						+ postPaidBean.getSubscriberMobileNo()
						+ " Id for future assistence "
						+ postPaidBean.getTransactionId());

				errors.add("genericError", new ActionError(
						Constants.SUCCESS_TRANSACTION, postPaidBean
								.getTransactionId()));
				
				bean.setTransactionId(String.valueOf(postPaidBean.getTransactionId()));
				saveErrors(req, errors);
				return mapping.findForward(INIT_SUCCESS);
			} catch (VirtualizationServiceException vSE) {
				logger.fatal("Virtualization Service Exception : " + vSE
						+ " occured while getting transactionID .");
				logger.info(ResponseConstants.RM_SQL_EXCEPTION);
			}
		}
		logger.info(" successful  redirected to : Success");
		forward = mapping.findForward(POSTPAID_SUCCESS);
		saveErrors(req, errors);
		/* Forward control to the appropriate 'Success' to View the jsp Page */
		logger.info(" Executed... ");
		return forward;
	}
	
	/**
	 * for report view 
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
		PostPaidBean bean = (PostPaidBean) form;
		logger.debug("SearchCirteria =" +bean.getTransactionId());
		if(bean.getTransactionId()!= null && !("".equalsIgnoreCase(bean.getTransactionId()))){
				bean.setTransactionId(bean.getTransactionId());
			}
		
		logger.debug("Executed... ");
		return mapping.findForward(OPEN_SEARCH_PAGE_CUSTOMER_TRANS_ID);
	}
	
}
