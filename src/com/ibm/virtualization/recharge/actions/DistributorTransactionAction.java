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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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

import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.DistributorTransactionFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.PaymentMode;
import com.ibm.virtualization.recharge.common.RequestType;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.DistributorTransaction;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AccountService;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.DistributorTransactionService;
import com.ibm.virtualization.recharge.service.impl.AccountServiceImpl;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.service.impl.DistributorTransactionServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

/**
 * Controller class for Distributor Transaction management, This class
 * responsible for the following activity 1. Created New Distributor Transaction
 * 2. View Transaction List 3. Verify Or Reject Transaction
 * 
 * @author Bhanu Gupta
 */
public class DistributorTransactionAction extends DispatchAction {
	/* Logger for Action class */
	private static Logger logger = Logger
			.getLogger(DistributorTransactionAction.class.getName());
	
	private static final String SEARCH_DIST_TRANSACTION_FAILURE = "error";
	
	/**
	 * This method open create new Distributor Trasaction jsp
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showDistributorTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		
				
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_TRAN)) {
			logger.info(" user not auth to perform this ROLE_VIEW_TRAN activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		// to open Create new trasaction Form and to make new Distributor
		// Transaction
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_DIST_TRANSACTION);
	}

	/**
	 * this method insert new distributor transaction record into database
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createDistributorTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DistributorTransactionFormBean distributorTransFormBean = (DistributorTransactionFormBean) form;
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_CREATE_TRAN)) {
				logger.info(" user not auth to perform this ROLE_VIEW_TRAN activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			DistributorTransaction distributorTransaction = new DistributorTransaction();
			/* bean util copy property not used due to usage of enum */
			distributorTransaction.setAccountCode(distributorTransFormBean
					.getAccountCode());
			distributorTransaction.setAccountId(Long
					.parseLong(distributorTransFormBean.getAccountId()));
			distributorTransaction.setBankName(distributorTransFormBean
					.getBankName());
			if(distributorTransFormBean.getCcvalidDate()!=null){
			distributorTransaction.setCcvalidDate(Utility.getSqlDate(distributorTransFormBean
					.getCcvalidDate()));
			}
			if(distributorTransFormBean.getChequeDate()!=null){
			distributorTransaction.setChequeDate(Utility.getSqlDate(distributorTransFormBean
					.getChequeDate()));
			}
			distributorTransaction.setTransactionAmount(Double
					.parseDouble(distributorTransFormBean
							.getTransactionAmount()));
			/* set payment mode from payment mode enum into dto */
			distributorTransaction.setPaymentMode(PaymentMode
					.valueOf(distributorTransFormBean.getPaymentMode().trim()));

			if (distributorTransFormBean.getChqccNumber() != null && !distributorTransFormBean.getChqccNumber().equalsIgnoreCase("") ) {
				int len_chqccNum=distributorTransFormBean.getChqccNumber().length();
				if(distributorTransFormBean.getPaymentMode().trim().equalsIgnoreCase(PaymentMode.CREDIT.getName())){
					try{
						distributorTransaction.setChqccNumber(Long
								.parseLong(distributorTransFormBean.getChqccNumber().substring((len_chqccNum-4), len_chqccNum)));
					}catch(IndexOutOfBoundsException indexOut){
						logger.error("Error getting last four characters of credit card Number"
								+indexOut.getMessage());
					}
					
				}else{
					distributorTransaction.setChqccNumber(Long
							.parseLong(distributorTransFormBean.getChqccNumber()));
				}
				
			}
		
			distributorTransaction.setReviewStatus(distributorTransFormBean
					.getReviewStatus());
			
			distributorTransaction.setCreatedBy(userSessionContext.getId());
			distributorTransaction.setUpdatedBy(userSessionContext.getId());
			
			distributorTransaction.setEcsno(distributorTransFormBean.getEcsno());
			distributorTransaction.setPurchaseOrderNo(distributorTransFormBean.getPurchaseOrderNo());
			if(distributorTransFormBean.getPurchaseOrderDate()!=null && !(distributorTransFormBean.getPurchaseOrderDate().equalsIgnoreCase(""))){
				distributorTransaction.setPurchaseOrderDate(Utility.getSqlDate(distributorTransFormBean.getPurchaseOrderDate()));
			}
			distributorTransaction.setReviewComment(distributorTransFormBean.getReviewComment());
			
			/*
			 * calling method to insert or create new transaction data
			 * implemented into service layer
			 */
			logger
					.info(" Request to perform a new distributor transaction by user "
							+ userSessionContext.getId());
			//	 call service method to insert new Distributor Transaction data
			DistributorTransactionService transactionService = new DistributorTransactionServiceImpl();
			transactionService
					.createDistributorTransaction(distributorTransaction);
			/* if no exception occurs then shows insertion success message */
			errors.add("message.transaction.insertion", new ActionError(
					"transaction.insert.success"));
			/* reset form bean if insertion success */
			distributorTransFormBean.reset(mapping, request);
		} catch (NumberFormatException numExp) {
			logger.error("  Error while parsing cheque no or account id "
					+ distributorTransFormBean.getChqccNumber() + ", "
					+ distributorTransFormBean.getAccountId(), numExp);
			errors.add("errors.transaction", new ActionError(
					"transaction.insert.error"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DISTRIBUTOR_LIST_ERROR);
		} catch (VirtualizationServiceException ex) {
			logger
					.error(
							"  Exception Occured During Creation of New Distributor Transaction. ",
							ex);
			/* if insertion error occurs then show error on page */
			errors.add("errors.transaction", new ActionError(ex.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(Constants.CREATE_DIST_TRANSACTION);
		}

		saveErrors(request, errors);
		logger.info("  Transaction created successfully.");
		logger.info(" Executed... ");
		return mapping.findForward(Constants.CREATE_DIST_TRANSACTION);
	}

	/**
	 * This method get the circle list and open the Select Distributor search
	 * Page According To Circle Id jsp page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSelectDistributor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		try {
			/* populate circle drop down list */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleDtoList = circleService.getCircles();
			request.setAttribute("circleList", circleDtoList);
          
			//get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			if (sessionContext.getCircleId() != 0 ) {
				DistributorTransactionFormBean distributorTransFormBean = (DistributorTransactionFormBean) form;
				distributorTransFormBean.setDisableCircle(Constants.CIRCLE_DISABLED);
				distributorTransFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
			}
			
			logger.info("  Circle Dropdown created.");
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("Error Occured while retrieving Circle ", se);
			errors.add("errors.transaction.distribtor", new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DISTRIBUTOR_LIST_ERROR);
		}
		logger
				.info("  Successully retrived circle list redirect to : Distriburtor List");
		logger.info(" Executed... ");
		return mapping.findForward(Constants.DISTRIBUTOR_LIST);
	}

	/**
	 * This method get Distributor list based on circle Id
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDistributorList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DistributorTransactionFormBean distributorTransFormBean = (DistributorTransactionFormBean) form;
		logger.info(" Started... ");
		logger.info(" :get Distributor List for Circle Id = "
				+ request.getParameter("circleId"));
		ActionErrors errors = new ActionErrors();
		try {

			/* populate circle drop down list */
			CircleService circleService = new CircleServiceImpl();
			ArrayList circleDtoList = circleService.getCircles();
			request.setAttribute("circleList", circleDtoList);
            
			//get login ID from session
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			if (sessionContext.getCircleId() != 0 ) {
				distributorTransFormBean.setCircleId(String.valueOf(sessionContext.getCircleId()));
				distributorTransFormBean.setDisableCircle(Constants.CIRCLE_DISABLED);
			} 
			/*
			 * Caling Method From Account Service to get Distributor list
			 * According To Circle Id
			 */
			AccountService accountService = new AccountServiceImpl();
			ArrayList accountDtoList = accountService
					.getDistributorAccounts(Integer.parseInt(distributorTransFormBean.getCircleId()));

			distributorTransFormBean.setCircleId(distributorTransFormBean
					.getCircleId());
			/*
			 * set accountlist into Distributor transaction Form bean to display
			 * accont details
			 */
			distributorTransFormBean.setAccountList(accountDtoList);

		} catch (NumberFormatException numExp) {
			logger.error("  Error During Parsing Transaction Id "
					+ numExp.getMessage(), numExp);
			errors.add("errors.transaction.distribtor", new ActionError(
					"errors.transaction.id_invalid"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DISTRIBUTOR_LIST_ERROR);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  Service Exception occured while retreiving  account information",
							se);
			errors.add("errors.transaction.distribtor", new ActionError(se
					.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DISTRIBUTOR_LIST);
		}
		logger.info("  Successfully redirect to Distribtor list page.");
		logger.info(" Executed... ");
		return mapping.findForward(Constants.DISTRIBUTOR_LIST);
	}

	/**
	 * This method open the transaction list search page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showSearchDistributorTransaction(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		// to open Search Type Distributor Transaction Type page
		logger.info("  Successfully Redirect To TransactionList.");
		logger.info(" Executed... ");
		try{
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRAN)) {
				logger.info(" user not auth to perform this VIEW_TRANSACTION activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			DistributorTransactionFormBean distributorTransactionFormBean = (DistributorTransactionFormBean)form;
			//String circleid = distributorTransactionFormBean.getCircleId();
			//String circleNm = distributorTransactionFormBean.getCircleName();
			CircleService circleService = new CircleServiceImpl();
			/* Get list of circles from Service Layer */
			ArrayList circleDtoList = circleService.getCircles();
			/* Create Circle Drop Down */
			
			session.setAttribute("circleList", circleDtoList);
			
		
			// It checks whether the login user is admin or not
			if (sessionContext.getCircleId() != 0 ) {
				logger.info("User is not Administrator "
				+ sessionContext.getId());
				Circle dto = new Circle();
				dto = circleService.getCircle(sessionContext.getCircleId());
				distributorTransactionFormBean.setCircleId(String.valueOf(dto.getCircleId()));
				distributorTransactionFormBean.setCircleName(dto.getCircleName());
				distributorTransactionFormBean.setFlagForCircleDisplay(true);
			}
		}
		catch (NumberFormatException numFormatExp) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Number format Exception occured  ", numFormatExp);
			errors.add("errors", new ActionError(numFormatExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SEARCH_DIST_TRANSACTION_FAILURE);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Service Exception occured. ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SEARCH_DIST_TRANSACTION_FAILURE);
		}catch (NullPointerException ne) {
			ActionErrors errors = new ActionErrors();
			logger.error("  Null Pointer Exception occured. ", ne);
			errors.add("errors", new ActionError(ne.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SEARCH_DIST_TRANSACTION_FAILURE);
		}
		return mapping.findForward(Constants.SEARCH_DIST_TRANSACTION);
	}

	/**
	 * This method will use to get Transaction list based on search Type like
	 * for pending --P,Approved--A Selected From Jsp Form
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAllDistributorTransactionList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		
		DistributorTransactionFormBean distributorFormBean = (DistributorTransactionFormBean) form;
		ActionErrors errors = new ActionErrors();
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRAN)) {
				logger.info(" user not auth to perform this VIEW_TRANSACTION activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			CircleService circleService = new CircleServiceImpl();
			/* Call service method to get Distributor transaction list */
			DistributorTransactionService distributorTransactionService = new DistributorTransactionServiceImpl();
			ReportInputs mtDTO = new ReportInputs();
			
			String status = request.getParameter("transactionListType");
			String circleValue = request.getParameter("circleId");
			//checks whether user is authorized to edit or not 
			
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_AUTH_TRAN));
			
			if (authorizationService.isUserAuthorized(userSessionContext.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_AUTH_TRAN) && request.getParameter("transactionListType").equals("P")) {
				distributorFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+distributorFormBean.getEditStatus());
			}else{
				distributorFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			
			if(userSessionContext.getCircleId() != 0 ){
				circleValue=String.valueOf(userSessionContext.getCircleId());
				
				Circle dto = new Circle();
				dto = circleService.getCircle(userSessionContext.getCircleId());
				
				distributorFormBean.setCircleId(dto.getCircleId()+"");
				distributorFormBean.setCircleName(dto.getCircleName());
				distributorFormBean.setFlagForCircleDisplay(true);
			}
			String startDate =request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			mtDTO.setStatus(status);
		    mtDTO.setStartDt(startDate);
			mtDTO.setEndDt(endDate);
			
			if(request.getParameter("circleId")==null || request.getParameter("transactionListType")==null
					|| request.getParameter("startDate")==null || request.getParameter("endDate")==null){
				mtDTO.setCircleId(Integer.parseInt(circleValue));
				mtDTO.setStatus(status);
				mtDTO.setStartDt(startDate);
				mtDTO.setEndDt(endDate); 
				request.setAttribute("circleId",circleValue);
				request.setAttribute("transactionListType",status);
				request.setAttribute("startDate",startDate);
				request.setAttribute("endDate",endDate);
			}else{
				mtDTO.setCircleId(Integer.parseInt(circleValue));
				mtDTO.setStatus(request.getParameter("transactionListType"));
				mtDTO.setStartDt(request.getParameter("startDate"));
				mtDTO.setEndDt(request.getParameter("endDate"));
				request.setAttribute("circleId",request.getParameter("circleId"));
				request.setAttribute("transactionListType",request.getParameter("transactionListType"));
				request.setAttribute("startDate",request.getParameter("startDate"));
				request.setAttribute("endDate",request.getParameter("endDate"));
			}
			/* call service to count the no of rows */
			/*int noofpages=distributorTransactionService.getDistributorTransactionCount(mtDTO);
			logger.info("Inside getAllDistributorTransactionList().. no of pages..."+noofpages);*/
			
			/* call to set lower & upper bound for Pagination */
			//request.setAttribute("page",distributorFormBean.getPage());
			Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
			
			/* call service to find all distributor */
			ArrayList transactionDtoList = distributorTransactionService.getDistributorTransactionList(mtDTO,pagination.getLowerBound(),pagination.getUpperBound());
			
			DistributorTransaction distributorTranDto = (DistributorTransaction)transactionDtoList.get(0);
			int totalRecords = distributorTranDto.getTotalRecords();
			int noofpages = Utility.getPaginationSize(totalRecords);
			request.setAttribute("PAGES", noofpages);
			
			/* Delimit Remarks field to 10 chars while displaying in grid*/
			Iterator iter = transactionDtoList.iterator();
			while(iter.hasNext()){
			
				DistributorTransaction dto = (DistributorTransaction) iter.next();
				dto.setReviewComment(Utility.delemitDesctiption(dto.getReviewComment()));
				
			}
			/* set ArrayList of Bean objects to FormBean */
			distributorFormBean.setTransactionList(transactionDtoList);
			
		} catch (VirtualizationServiceException e) {
			logger.error("  Service Exception occured getting Distributor List  ", e);
			
			// check flag value to display an error message
			if(distributorFormBean.getFlag() == null){
				errors.add("error.transaction", new ActionError(e.getMessage()));
				saveErrors(request, errors);
			} else if( distributorFormBean.getFlag() != null && (distributorFormBean.getFlag().equals(Constants.TRANSACTION_FLAG_VALUE))){
				
				if(distributorFormBean.getReviewStatus().equalsIgnoreCase("A")){
					errors.add("error.transaction", new ActionError("messages.transaction.approve_success"));
				}
				else if(distributorFormBean.getReviewStatus().equalsIgnoreCase("A")){
					errors.add("error.transaction", new ActionError("messages.transaction.reject_success"));
				}
			
					saveErrors(request, errors);
				}
			return mapping.findForward(Constants.SEARCH_DIST_TRANSACTION);
		}  
		logger.info(" Executed... ");
		return mapping.findForward(Constants.SEARCH_DIST_TRANSACTION);
	}

	/**
	 * This Method use to get Distributor Transaction Review Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getVerifyTransactionId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_AUTH_TRAN)) {
			logger.info(" user not auth to perform this ROLE_AUTH_TRAN activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		
		getdistributorTransactionReviewDetail(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.DIST_REVIEW_DETAILS);
	}
	
	/**
	 * This Method use to get Distributor Transaction Review Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getViewTransactionId(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_TRAN)) {
			logger.info(" user not auth to perform this ROLE_VIEW_TRAN activity  ");
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		getdistributorTransactionReviewDetail(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(Constants.DIST_REVIEW_DETAILS_VIEW);		
	}
	
	
	/**
	 * This Method use to get Distributor Transaction Review Details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ActionForward getdistributorTransactionReviewDetail(
			ActionMapping mapping, ActionForm form, HttpServletRequest request) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();

		try {
			DistributorTransactionService distributorTransactionService = new DistributorTransactionServiceImpl();
			DistributorTransactionFormBean distributorTransFormBean = (DistributorTransactionFormBean) form;
			DistributorTransaction distributorTransaction = new DistributorTransaction();
			distributorTransaction = distributorTransactionService
					.getDistributorTransactionReviewDetail(Long
							.parseLong(distributorTransFormBean
									.getTransactionId()));
			/* Copying the DTO object data to Form Bean Object */
			String page=distributorTransFormBean.getPage();
			BeanUtils.copyProperties(distributorTransFormBean,
					distributorTransaction);
			distributorTransFormBean.setPage(page);
			distributorTransFormBean.setRequestType(RequestType.ACCOUNT.name());

			if (distributorTransFormBean.getChqccNumber().equalsIgnoreCase("0")) {
				distributorTransFormBean.setChqccNumber("");
			}
			distributorTransFormBean
					.setTransactionListType(distributorTransFormBean
							.getTransactionListType());
			
			distributorTransFormBean.setPurchaseOrderDate(distributorTransFormBean.getPurchaseOrderDate());
		} catch (NumberFormatException numExp) {
			logger.error("  Error During Parsing Transaction Id ", numExp);
			errors.add("error.NoRecord", new ActionError(numExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DIST_TRANSACTION_LIST);
		} catch (VirtualizationServiceException vsExp) {
			logger.error("  Service Exception occured :", vsExp);
			errors.add("error.NoRecord", new ActionError(vsExp.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DIST_REVIEW_DETAILS);
		} catch (IllegalAccessException iaExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							iaExp);
		} catch (InvocationTargetException itExp) {
			logger
					.error(
							"  caught Exception while using BeanUtils.copyProperties()",
							itExp);
		}

		logger.info("  Transaction details retrieved successfully");
		logger.info(" Executed... ");
		// return mapping.findForward(Constants.DIST_REVIEW_DETAILS);
		return null;
	}

	/**
	 * It will use to verify or reject Distributor Transaction
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward updateDistributorTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		ActionErrors errors = new ActionErrors();
		DistributorTransactionFormBean distributorTransFormBean = (DistributorTransactionFormBean) form;
		try {
			
			logger.info(" Started... ");
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_AUTH_TRAN)) {
				logger.info(" user not auth to perform this ROLE_AUTH_TRAN activity  ");
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			DistributorTransaction distributorTransaction = new DistributorTransaction();

			distributorTransaction.setTransactionId(Long
					.parseLong(distributorTransFormBean.getTransactionId()));
			distributorTransaction.setAccountId(Long
					.parseLong(distributorTransFormBean.getAccountId()));
			distributorTransaction.setTransactionAmount(Double
					.parseDouble(distributorTransFormBean
							.getTransactionAmount()));
			distributorTransaction.setReviewStatus(distributorTransFormBean
					.getReviewStatus());
			distributorTransaction.setReviewComment(distributorTransFormBean
					.getReviewComment());
			distributorTransaction.setCreditedAmount(Double
					.parseDouble(distributorTransFormBean.getCreditedAmount()));

			distributorTransaction.setUpdatedBy(userSessionContext.getId());

			DistributorTransactionService distributorTransactionService = new DistributorTransactionServiceImpl();
			logger
					.info(" Request to update status of distributor transaction by user "
							+ userSessionContext.getId());
			distributorTransactionService
					.updateDistributorTransaction(distributorTransaction);

		} catch (NumberFormatException numExp) {
			logger.error("  Error During Parsing Transaction Id ", numExp);
			errors.add("errors.transaction", new ActionError(
					"errors.transaction.update"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DIST_REVIEW_DETAILS);
		} catch (VirtualizationServiceException e) {
			logger.error("  Service Exception occured  ", e);
			// set the transaction type parameter like 'P'for select list
			request.setAttribute("transactionlistType",
					distributorTransFormBean.getTransactionListType());
			errors.add("message.transaction", new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.DIST_REVIEW_DETAILS);
		}
		logger.info("  Transaction Updated successfully.");
		logger.info(" Executed... ");
		distributorTransFormBean.setFlag(Constants.TRANSACTION_FLAG_VALUE);
		if(distributorTransFormBean.getReviewStatus().equalsIgnoreCase("A")){
			errors.add("error.transaction", new ActionError("messages.transaction.approve_success"));
		}
		else if(distributorTransFormBean.getReviewStatus().equalsIgnoreCase("A")){
			errors.add("error.transaction", new ActionError("messages.transaction.reject_success"));
		}
		saveErrors(request, errors);
		return mapping.findForward(Constants.UPDATED_SEARCH_LIST);

	}

}