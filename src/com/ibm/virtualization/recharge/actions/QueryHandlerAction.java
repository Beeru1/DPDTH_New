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
import com.ibm.virtualization.framework.utils.PropertyReader;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.QueryFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ExceptionCode;
import com.ibm.virtualization.recharge.common.QueryRequestType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Account;
import com.ibm.virtualization.recharge.dto.EnquiryTransaction;
import com.ibm.virtualization.recharge.dto.QueryBalanceDTO;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.message.QueryMessageBean;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AccountTransactionService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.QueryService;
import com.ibm.virtualization.recharge.service.SequenceService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.AccountTransactionServiceImpl;
import com.ibm.virtualization.recharge.service.impl.QueryServiceImpl;
import com.ibm.virtualization.recharge.service.impl.SequenceServiceImpl;

/**
 * Controller class for Query management.
 * 
 * @author Prakash
 */
public class QueryHandlerAction extends DispatchAction {
	/* Logger for this class */
	private static Logger logger = Logger.getLogger(QueryHandlerAction.class
			.getName());

	/* Local Variables */
	private static final String LOADPAGE_SUCCESS = "loadBalanceEnquery";

	private static final String LOADPAGE_FAILURE = "userNotAuthenticated";

	private static final String LOADPAGE_SUCCESS_ASYNC = "loadBalanceEnqueryAsync";

	private static final String LOADPAGE_FAILURE_ASYNC = "userNotAuthenticatedAsync";

	private static final String GET_BALANCE_SUCCESS = "showBalance";

	private static final String GET_BALANCE_FAILURE = "showBalanceFailed";

	private static final String GET_CHILD_ACC_BALANCE_SUCCESS = "showChildBalance";

	private static final String GET_CHILD_ACC_BALANCE_FAILURE = "showChildBalanceFailed";

	/**
	 * Used for Loading balanceEnquiry page
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception DpException
	 * 
	 * @return the ActionForward for the next view
	 */

	// Connection Properties For QueryQueue,OUT Queue
	private static ConnectionBean connBeanQuery = null;

	private static ConnectionBean connBeanOut = null;
	static {

		try {
			// Set Resource Bundle Name
			PropertyReader
					.setBundleName(Constants.SRC_FRONT_CONTROLLER_PROPERTY_BUNDLE);
			// Set Query Q Properties

			// Setting Host IP Address
			connBeanQuery = new ConnectionBean();
			connBeanQuery.setHost(PropertyReader.getString("query.q.host"));
			// Setting Port Number
			connBeanQuery.setPort(new Integer(PropertyReader
					.getString("query.q.port")));
			// Setting Channel NAme
			connBeanQuery.setChannel(PropertyReader
					.getString("query.q.channel"));
			// Setting Queue Manager Name
			connBeanQuery.setQMgr(PropertyReader.getString("query.q.mgr"));
			// Setting Queue Name
			connBeanQuery.setQu(PropertyReader.getString("query.q.name"));
			// Set OUT Q Properties
			// Setting Host IP Address
			connBeanOut = new ConnectionBean();
			connBeanOut.setHost(PropertyReader.getString("query.out.q.host"));
			// Setting Port Number
			connBeanOut.setPort(new Integer(PropertyReader
					.getString("query.out.q.port")));
			// Setting Channel NAme
			connBeanOut.setChannel(PropertyReader
					.getString("query.out.q.channel"));
			// Setting Queue Manager Name
			connBeanOut.setQMgr(PropertyReader.getString("query.out.q.mgr"));
			// Setting Queue Name
			connBeanOut.setQu(PropertyReader.getString("query.out.q.name"));
			logger.info("Initialised Successfully");
		} catch (NumberFormatException e) {
			logger.fatal("Number format exception occured.");
		}

	}

	public ActionForward loadPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_QUERY_BALANCE)) {
			logger.info(" user not auth to perform this ROLE_QUERY_BALANCE activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}

		if (userSessionContext.getType().equalsIgnoreCase("E")) {
			QueryFormBean formBean = (QueryFormBean) form;
			formBean.setLoginId(String.valueOf(userSessionContext.getId()));
			return (mapping.findForward(LOADPAGE_SUCCESS));
		}
		logger.info(" Executed... ");
		return (mapping.findForward(LOADPAGE_FAILURE));
	}

	/**
	 * Used for Loading balanceEnquiry page
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception VirtualizationServiceException
	 * 
	 * @return the ActionForward for the next view
	 */
	public ActionForward loadPageAsync(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_QUERY_BALANCE)) {
			logger.info(" user not auth to perform this ROLE_QUERY_BALANCE activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		QueryFormBean formBean = (QueryFormBean) form;
		formBean.setAsync(true);
		if (userSessionContext.getType().equalsIgnoreCase("E")) {

			formBean.setLoginId(String.valueOf(userSessionContext.getId()));
			return (mapping.findForward(LOADPAGE_SUCCESS_ASYNC));
		}
		logger.info(" Executed... ");
		return (mapping.findForward(LOADPAGE_FAILURE_ASYNC));
	}

	/**
	 * Process the request and return an <code>ActionForward</code> instance
	 * describing where and how control should be forwarded, or
	 * <code>null</code> if the response has already been completed. Fetches
	 * all the transactions that have been done on the specfied day and for the
	 * specified circles
	 * 
	 * this snippet calls the service layer to get a accountBalance based on
	 * mobilenumber.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception VirtualizationServiceException
	 * 
	 * @return the ActionForward for the next view
	 */
	public ActionForward getBalance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		QueryBalanceDTO queryBalance = new QueryBalanceDTO();
		/* initialize status with failure */
		queryBalance.setTransactionStatus(TransactionStatus.FAILURE);
		UserSessionContext userSessionContext=null;
		QueryFormBean formBean = (QueryFormBean) form;
		EnquiryTransaction dto=new EnquiryTransaction();
		String reasonId=null;
		ActionErrors errors = new ActionErrors();
		try {
			// get UserSessionContext Object from Session
			 userSessionContext = (UserSessionContext) (request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER));
			/* errors is an object of type ActionErrors */

			/* prepare a new AccountTransaction object */
			 dto = prepareAccountTransaction(userSessionContext);
			/*
			 * Get the available balance for mobileNumber from service layer and
			 * also check whether user is authorized for it
			 */

			/* Setting the requestTime */
			//formBean.setRequestTime(Utility.getFormattedDate());
			/* Presently setting the Ip Address in the bean */
			// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
			// formBean.setPhysicalId(i.getHostAddress());
			// dto.setPhysicalId(formBean.getPhysicalId());
			//dto.setRequestTime(formBean.getRequestTime());

			dto.setUserSessionContext(userSessionContext);
			dto.setTransactionChannel(ChannelType.WEB);
//			set the requesting system IP Address as cell ID
			dto.setCellId(request.getRemoteAddr());
			
			
			if (!formBean.getAsync()) {
				
				AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				if (!authorizationService.isUserAuthorized(userSessionContext
						.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_QUERY_BALANCE)) {
					logger.info(" user not auth to perform this ROLE_QUERY_BALANCE activity  ");
					errors.add("errors", new ActionError(
							"errors.usernotautherized"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
				}
				
				QueryService queryService = new QueryServiceImpl();
				queryBalance = queryService.getBalance(dto);
				
				/*
				 * If duplicate request is recieved for Sysn Query Balance
				 */
				if (TransactionStatus.DUPLICATE == queryBalance
						.getTransactionStatus()
						|| TransactionStatus.BLACKOUT == queryBalance
								.getTransactionStatus()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							ExceptionCode.Transaction.DUPLICATE_REQUEST));
					saveErrors(request, errors);
					return mapping.findForward(GET_BALANCE_SUCCESS);

				}
				/* Set query balance in request */
				request.setAttribute("BALANCE_INFO", queryBalance);
			} else {
				AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
				if (!authorizationService.isUserAuthorized(userSessionContext
						.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_QUERY_BALANCE)) {
					logger.info(" user not auth to perform this ROLE_QUERY_BALANCE activity  ");
					errors.add("errors", new ActionError(
							"errors.usernotautherized"));
					saveErrors(request, errors);
					return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
				}
				
				
				QueryMessageBean queryBean = new QueryMessageBean();

				queryBean.setRequestTime(Utility.getRequestTimeDateFormat());
				// Set Channel Type
				queryBean.setChannelType(ChannelType.WEB);
				queryBean.setRequestType(QueryRequestType.QUERY_SELF_BALANCE);
				queryBean.setTransactionId(dto.getTransactionId());
				AccountService accountService = new AccountServiceImpl();
				Account account = accountService.getAccount(userSessionContext
						.getId());
				queryBean.setPassword(account.getMPassword());
//				set the requesting system IP Address as cell ID
				queryBean.setCellId(request.getRemoteAddr());
//				try {
//					// Generate Transaction ID
//					SequenceService seqService = new SequenceServiceImpl();
//					queryBean.setTransactionId(seqService.getNextTransID());
//
//				} catch (VirtualizationServiceException vexp) {
//					logger.error("Some exception occured ", vexp);
//					logger.info(ResponseConstants.RM_TRANSID_EXCEPTION);
//				}
				// Set Distributor Mobile Number
				queryBean.setRequesterMobileNo(account.getMobileNumber());
				queryBean.setTransactionStatus(TransactionStatus.SUCCESS);

				if (!Utility.putToQ(queryBean, connBeanQuery)) {
					// Print the response back to the servlet
					logger.info("Message processing failed.");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							ExceptionCode.Transaction.ERROR_GET_BALANCE));
				} else {
					logger
							.info("::Message put Successfully in Q with parameters::\nSourceMobileNumber="
									+ queryBean.getRequesterMobileNo()
									+ "\n Destination MobileNumber="
									+ "\n Transaction Id = "
									+ queryBean.getTransactionId()
									+ "for self balance");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Constants.SUCCESS_TRANSACTION, queryBean
									.getTransactionId()));

				}
				saveErrors(request, errors);
			}
		} catch (VirtualizationServiceException e) {
			logger.error(" caught Service Exception.", e);
			reasonId=e.getMessage();
			if (e.getMessage().equalsIgnoreCase("user.not.authorized")) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
						.getMessage()));
				saveErrors(request, errors);
				return (mapping.findForward(Constants.USER_NOT_AUTHORISED));
			}
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
					.getMessage()));
			saveErrors(request, errors);
			if (formBean.getAsync()) {
				return mapping.findForward(LOADPAGE_SUCCESS_ASYNC);
			} else {
				return mapping.findForward(GET_BALANCE_FAILURE);
			}

		}
		finally{
			AccountTransactionService service = new AccountTransactionServiceImpl();
			service.updateTransactionStatus(queryBalance.getTransactionStatus(), userSessionContext.getId(), dto.getTransactionId(),
					reasonId);

		}
		logger.info(" Executed...");
		if (formBean.getAsync()) {
			return (mapping.findForward(LOADPAGE_SUCCESS_ASYNC));
		} else {
			return (mapping.findForward(GET_BALANCE_SUCCESS));
		}
	}

	/**
	 * Preapres a new account transaction object
	 * 
	 * @param queryService
	 * @param userSessionContext
	 * @return
	 * @throws VirtualizationServiceException
	 */
	private EnquiryTransaction prepareAccountTransaction(
			UserSessionContext userSessionContext)
			throws VirtualizationServiceException {

		logger.info(" preparing a account transaction dto object...");
		SequenceService service = new SequenceServiceImpl();
		long transactionId = service.getNextTransID();

		EnquiryTransaction dto = new EnquiryTransaction();
		dto.setCircleId(userSessionContext.getCircleId());
		dto.setSourceAccountId(userSessionContext.getId());
		dto.setTransactionChannel(ChannelType.WEB);
		dto.setTransactionId(transactionId);
		return dto;
	}

	/**
	 * Process the request and return an <code>ActionForward</code> instance
	 * describing where and how control should be forwarded, or
	 * <code>null</code> if the response has already been completed. Fetches
	 * all the transactions that have been done on the specfied day and for the
	 * specified circles
	 * 
	 * this snippet calls the service layer to get child account balance based
	 * on mobile number.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * 
	 * @exception VirtualizationServiceException
	 * 
	 * @return the ActionForward for the next view
	 */
	public ActionForward getChildAccBalanceByMobileNumber(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info(" Started... ");
		QueryService queryService = new QueryServiceImpl();
		QueryFormBean queryFormBean = (QueryFormBean) form;
		QueryBalanceDTO queryBalance = new QueryBalanceDTO();
		/* initialize status with failure */
		queryBalance.setTransactionStatus(TransactionStatus.FAILURE);
		String reasonId=null;
		UserSessionContext userSessionContext=null;
		ActionErrors errors = new ActionErrors();
		EnquiryTransaction  dto=new EnquiryTransaction();
		try {
			// get UserSessionContext Object from Session
			 userSessionContext = (UserSessionContext) (request
					.getSession().getAttribute(Constants.AUTHENTICATED_USER));
			  dto = prepareAccountTransaction(userSessionContext);
			if (!queryFormBean.getAsync()) {
				/* prepare a new AccountTransaction object */
				

				/* Setting the requestTime */
				//queryFormBean.setRequestTime(Utility.getFormattedDate());
				/* Presently setting the Ip Address in the bean */
				// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
				// queryFormBean.setPhysicalId(i.getHostAddress());
				dto.setRequestTime(queryFormBean.getRequestTime());
				/* Presently setting the Ip Address in the dto */
				// dto.setPhysicalId(queryFormBean.getPhysicalId());
//				set the requesting system IP Address as cell ID
				dto.setCellId(request.getRemoteAddr());
				dto
						.setChildMobileNo(queryFormBean
								.getChildMobileNumber());
				/* Get the available balance for mobileNumber from service layer */
				dto.setUserSessionContext(userSessionContext);
				dto.setTransactionChannel(ChannelType.WEB);
				dto
						.setChildMobileNo(queryFormBean
								.getChildMobileNumber());
				queryBalance = queryService.getChildBalanceByMobileNo(dto);
				
				if (TransactionStatus.DUPLICATE == queryBalance
						.getTransactionStatus()
						|| TransactionStatus.BLACKOUT == queryBalance
								.getTransactionStatus()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							ExceptionCode.Transaction.DUPLICATE_REQUEST));
					saveErrors(request, errors);
					return mapping.findForward(GET_CHILD_ACC_BALANCE_SUCCESS);

				}
				/* Set DTO object in request */
				request.setAttribute("BALANCE_INFO", queryBalance);
			} else {
				QueryMessageBean queryBean = new QueryMessageBean();
				queryBean.setTransactionId(dto.getTransactionId());
				// Set Channel Type

				/* Setting the requestTime */
				queryBean.setRequestTime(Utility.getRequestTimeDateFormat());
				/* Presently setting the Ip Address in the bean */
				// java.net.InetAddress i = java.net.InetAddress.getLocalHost();
				// queryFormBean.setPhysicalId(i.getHostAddress());
				queryBean.setChannelType(ChannelType.WEB);
				queryBean.setRequestType(QueryRequestType.QUERY_CHILD_BALANCE);
				AccountService accountService = new AccountServiceImpl();
				Account account = accountService.getAccount(userSessionContext
						.getId());
				queryBean.setPassword(account.getMPassword());
				queryBean.setRequesterMobileNo(account.getMobileNumber());
				queryBean.setChdAccountMobileNo(queryFormBean
						.getChildMobileNumber());
//				set the requesting system IP Address as cell ID
				queryBean.setCellId(request.getRemoteAddr());
//				try {
//					// Generate Transaction ID
//					SequenceService seqService = new SequenceServiceImpl();
//					queryBean.setTransactionId(seqService.getNextTransID());
//					queryBean.setChdAccountMobileNo(queryFormBean
//							.getChildMobileNumber());
//
//				} catch (VirtualizationServiceException vexp) {
//					logger.error("Some exception occured ", vexp);
//					logger.info(ResponseConstants.RM_TRANSID_EXCEPTION);
//				}
				// Set Distributor Mobile Number
				queryBean.setTransactionStatus(TransactionStatus.SUCCESS);
				if (!Utility.putToQ(queryBean, connBeanQuery)) {
					// Print the response back to the servlet
					logger.info("Message processing failed.");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							ExceptionCode.Transaction.ERROR_GET_BALANCE));
				} else {
					logger
							.info("::Message put Successfully in Q with parameters::\nSourceMobileNumber="
									+ queryBean.getRequesterMobileNo()
									+ "\n Destination MobileNumber="
									+ "\n Transaction Id = "
									+ queryBean.getTransactionId()
									+ "for self balance");
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							Constants.SUCCESS_TRANSACTION, queryBean
									.getTransactionId()));

				}
				saveErrors(request, errors);
			}
		} catch (VirtualizationServiceException e) {
			logger.error(" caught Service Exception.", e);
			reasonId=e.getMessage();
			// Check whether authorization exception is occured
			if (e.getMessage().equalsIgnoreCase("user.not.authorized")) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
						.getMessage()));
				saveErrors(request, errors);
				return (mapping.findForward(Constants.USER_NOT_AUTHORISED));
			} else {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(e
						.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(GET_CHILD_ACC_BALANCE_FAILURE);
			}
		}finally{
			
				AccountTransactionService service = new AccountTransactionServiceImpl();
				service.updateTransactionStatus(queryBalance.getTransactionStatus(), userSessionContext.getId(), dto.getTransactionId(),
						reasonId);

			
		}
		logger.info(" Executed... ");
		if (queryFormBean.getAsync()) {
			return (mapping.findForward(LOADPAGE_SUCCESS_ASYNC));
		} else {
			return (mapping.findForward(GET_CHILD_ACC_BALANCE_SUCCESS));
		}
	}

}