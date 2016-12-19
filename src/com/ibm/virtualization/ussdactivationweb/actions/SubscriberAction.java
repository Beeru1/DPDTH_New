/**************************************************************************
 * File     : SubscriberAction.java
 * Author   : Ashad
 * Created  : Sep 10, 2008
 * Modified : Sep 10, 2008
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;

import com.ibm.virtualization.ussdactivationweb.beans.SubscriberBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.beans.VSubscriberMstrFormBean;

import com.ibm.virtualization.ussdactivationweb.dao.ServiceClassDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dao.VSubscriberMstrDaoImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.SubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.dto.VSubscriberMstr;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;

import com.ibm.virtualization.ussdactivationweb.reports.processor.MisReportProcessor;
import com.ibm.virtualization.ussdactivationweb.reports.service.FTAService;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;

/**
 * 
 * @author Ashad This class would handle the Subscriber registration and
 *         updation of this usecase.
 */

public class SubscriberAction extends DispatchAction {

	private static final Logger log = Logger.getLogger(SubscriberAction.class);

	/**
	 * This method will be called when Subscriber Registration usecase would be
	 * initialized.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 */

	public ActionForward initSubscriber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering initSubscriber()... ");
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		ArrayList circleList = new ArrayList();
		ActionErrors errors = new ActionErrors();
		VSubscriberMstrFormBean subscriberBean = (VSubscriberMstrFormBean) form;
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		ArrayList serviceClassList = new ArrayList();
		try {
			if (userBean.getCircleId() == null) {
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl.getCircleList());
				subscriberBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				subscriberBean.setCircleCode(userBean.getCircleId());
				subscriberBean.setInitialCircleValue(userBean.getCircleId());
				LabelValueBean lvBean = new LabelValueBean(userBean
						.getCircleId(), userBean.getCircleName());
				circleList.add(lvBean);
				request.setAttribute(Constants.CIRCLE_LIST, circleList);

			}
			if (subscriberBean.getCircleCode() != null) {
				try {
					/** * getting service classes for selected circle ** */
					serviceClassList = serviceClassDAOImpl
							.getServiceClassListForDropDown(subscriberBean
									.getCircleCode());
				} catch (DAOException de) {

					/*
					 * if (ErrorCodes.ServiceClass.RESULT_NOT_FOUND.equals(de
					 * .getMessage())) { errors.add("Common.RESULT_NOT_FOUND",
					 * new ActionError( "Common.RESULT_NOT_FOUND")); }
					 */
					subscriberBean.setServiceClassList(serviceClassList);
				}

			}
			subscriberBean.setServiceClassList(serviceClassList);

			log.info(userBean.getLoginId() + " Create Subscriber Initialized");

		} catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
		}
		saveErrors(request, errors);
		log.info("Exiting initSubscriber()... ");
		return mapping.findForward("initSubscriber");
	}

	/**
	 * This method will insert the Subscriber registration data in repository.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 * 
	 */
	
	
	public ActionForward subscriberRegistration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering subscriberRegistration()... ");
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		ActionErrors errors = new ActionErrors();
		VSubscriberMstr subscriberDTO = new VSubscriberMstr();
		VSubscriberMstrDaoImpl subscriberDAOImpl = new VSubscriberMstrDaoImpl();
		VSubscriberMstrFormBean subscriberBean = (VSubscriberMstrFormBean) form;
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		ArrayList serviceClassList = new ArrayList();
		ArrayList circleList = new ArrayList();
		try {
			subscriberDTO.setImsi(subscriberBean.getImsi());
			subscriberDTO.setMsisdn(subscriberBean.getMsisdn());
			subscriberDTO.setCompSim(subscriberBean.getSim());
			subscriberDTO.setStatus(subscriberBean.getStatus());
			subscriberDTO.setCreatedBy(userBean.getUserId());
			subscriberDTO.setUpdatedBy(userBean.getUserId());
			subscriberDTO.setServiceClassId(Integer.parseInt(subscriberBean
					.getServiceClassId()));
			if (userBean.getCircleId() == null) {
				subscriberDTO.setCircleCode(subscriberBean.getCircleCode());
				circleList =ViewEditCircleMasterDAOImpl.getCircleList();
				subscriberBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				subscriberDTO.setCircleCode(userBean.getCircleId());
				serviceClassList = serviceClassDAOImpl
						.getServiceClassListForDropDown(userBean
								.getCircleId());
			}
			subscriberDAOImpl.insert(subscriberDTO);// Subscriber details
			// are inserted
			ActionMessages msg = new ActionMessages();
			ActionMessage message = new ActionMessage("Saved");
			msg.add(ActionMessages.GLOBAL_MESSAGE, message);
			saveMessages(request, msg);
			log.info(new StringBuffer(100).append(userBean.getLoginId())
					.append(" Subscriber Created Successfully").toString());
			return mapping.findForward(Constants.SUCCESS);
			
		}catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
			saveMessages(request, errors);
			if (userBean.getCircleId() == null) {
				subscriberDTO.setCircleCode(subscriberBean.getCircleCode());
				circleList =ViewEditCircleMasterDAOImpl.getCircleList();
				subscriberBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				subscriberDTO.setCircleCode(subscriberBean.getCircleCode());
				serviceClassList = serviceClassDAOImpl
						.getServiceClassListForDropDown(userBean
								.getCircleId());
			}
		}
		
		request.setAttribute(Constants.CIRCLE_LIST, circleList);
		subscriberBean.setServiceClassList(serviceClassList);
		log.info("Exiting subscriberRegistration()... ");
		return mapping.findForward("subscriberRegistration");
	}
	
	
	
	/**
	 * This method initialize the Search Subscriber page
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 * 
	 */
	public ActionForward initSearchSubscriber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/**
		 * This is called when the Search Subscriber usecase intializes
		 */
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		log.info(userBean.getLoginId() + " Search Subscriber Inititalized");
		return mapping.findForward("initSearchSubscriber");
	}

	/**
	 * This method returns the list of subscriber having the same msisdn
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 */

	public ActionForward searchSubscriber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering searchSubscriber()... ");
		VSubscriberMstrFormBean subscriberBean = (VSubscriberMstrFormBean) form;
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		ActionErrors errors = new ActionErrors();
		// List subscriberList = null;
		try {
			List subscriberList = new ArrayList();
			VSubscriberMstrDaoImpl subscriberDaoImpl = new VSubscriberMstrDaoImpl();
			if (userBean.getCircleId() == null) {
				subscriberList = subscriberDaoImpl.findSubscriberList(
						subscriberBean.getMsisdn(), null);
			} else {
				subscriberList = subscriberDaoImpl.findSubscriberList(
						subscriberBean.getMsisdn(), userBean.getCircleId());
			}

			if (subscriberList != null && subscriberList.isEmpty()) {
				if (userBean.getCircleId() == null) {
					errors.add("smsisdn", new ActionError(
							"createUser.smsisdn.notexists"));
				} else {
					errors.add("smsisdn", new ActionError("ErrorKey",
							"MSISDN does not exists in "
									+ userBean.getCircleName() + " circle"));
				}
				saveErrors(request, errors);
			}
			// subscriberBean.setServiceClassList(new
			// ServiceClassDAOImpl().getServiceClassListForDropDown(subscriberBean.getCircleId()));
			log.info(new StringBuffer(100).append(userBean.getLoginId())
					.append(" Search Subscriber for All Circle ").toString());
			request.setAttribute("SubscriberList", subscriberList);

		} catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
		}
		log.info("Exiting searchSubscriber()... ");
		return mapping.findForward("searchSubscriber");
	}

	/**
	 * This method is responsible to initialize the edit subscriber page
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 * 
	 */
	public ActionForward initUpdateSubscriber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering initUpdateSubscriber()... ");
		VSubscriberMstrFormBean subscriberBean = (VSubscriberMstrFormBean) form;
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		UserDetailsBean userBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		ArrayList serviceClassList = new ArrayList();

		// to control the page load function on the jsp. So that it is called
		// only once , when the value is 0 - in viewProfile method
		int jspPageLoad = subscriberBean.getJspPageLoadControl();
		jspPageLoad++;
		subscriberBean.setJspPageLoadControl(jspPageLoad);
		try {
			subscriberBean.setId(request.getParameter("id"));
			VSubscriberMstrDaoImpl subscriberDaoImpl = new VSubscriberMstrDaoImpl();
			VSubscriberMstr subscriberDTO = new VSubscriberMstr();
			subscriberDTO = subscriberDaoImpl.findByPrimaryKey(request.getParameter("id"));
			subscriberBean.setImsi(subscriberDTO.getImsi());
			subscriberBean.setSim(subscriberDTO.getSim());
			subscriberBean.setMsisdn(subscriberDTO.getMsisdn());
			subscriberBean.setStatus(subscriberDTO.getStatus());
			subscriberBean.setOldMsisdn(subscriberBean.getMsisdn());
			subscriberBean.setOldImsi(subscriberBean.getImsi());
			subscriberBean.setOldSim(subscriberBean.getSim());
			subscriberBean.setOldCompSim(subscriberBean.getSim());
			subscriberBean.setOldCircleId(subscriberBean.getCircleCode());
			subscriberBean.setOldStatus(subscriberBean.getStatus());
			subscriberBean.setId(subscriberDTO.getId());
			subscriberBean.setServiceClassId(String.valueOf(subscriberDTO
					.getServiceClassId()));
			subscriberBean.setOldServiceClassId(String.valueOf(subscriberBean
					.getServiceClassId()));
			if (subscriberBean.getChangedCircleId() == null) {
				subscriberBean.setCircleCode(subscriberDTO.getCircleCode());
			} else {
				subscriberBean.setCircleCode(subscriberBean
						.getChangedCircleId());
			}
			request.setAttribute(Constants.CIRCLECODE, request
					.getParameter(Constants.CIRCLECODE));
			subscriberBean.setId(request.getParameter("id"));
			if (userBean.getCircleId() == null) {
				subscriberBean.setInitialCircleValue(subscriberDTO
						.getCircleCode());
				try {
					/** * get service classes for selected circle ** */
					serviceClassList = new ServiceClassDAOImpl()
							.getServiceClassListForDropDown(subscriberBean
									.getCircleCode());

				} catch (DAOException de) {
					/*
					 * if (ErrorCodes.ServiceClass.RESULT_NOT_FOUND.equals(de
					 * .getMessage())) { errors.add("Common.RESULT_NOT_FOUND",
					 * new ActionError( "Common.RESULT_NOT_FOUND")); }
					 */
				}

				request.setAttribute(Constants.CIRCLE_LIST,  ViewEditCircleMasterDAOImpl.getCircleList());
				subscriberBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				
				try {
					/** * get service classes for selected circle ** */
					serviceClassList = new ServiceClassDAOImpl()
							.getServiceClassListForDropDown(userBean
									.getCircleId());

				} catch (DAOException de) {
					/*
					 * if (ErrorCodes.ServiceClass.RESULT_NOT_FOUND.equals(de
					 * .getMessage())) { errors.add("Common.RESULT_NOT_FOUND",
					 * new ActionError( "Common.RESULT_NOT_FOUND")); }
					 */
					log.error("Exception occured when trying to fetch the service class for circle "+userBean
									.getCircleId(), de);
				}

				subscriberBean.setCircleCode(userBean.getCircleId());
				subscriberBean.setInitialCircleValue(userBean.getCircleId());
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl.getCircleList());
			}

			subscriberBean.setServiceClassList(serviceClassList);
			log.info(userBean.getLoginId() + " Edit Subscriber Initialized");

		} catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

		}
		log.info("Exiting initUpdateSubscriber()... ");
		return mapping.findForward("initUpdateSubscriber");

	}

	
	
	/**
	 * This method is called when the Subscriber details are updated.
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 * 
	 */
	
	public ActionForward updateSubscriber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering updateSubscriber()... ");
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		VSubscriberMstrFormBean subscriberBean = (VSubscriberMstrFormBean) form;
		ActionErrors errors = new ActionErrors();
		VSubscriberMstr subscriberDTO = new VSubscriberMstr();
		VSubscriberMstrDaoImpl subscriberDaoImpl = new VSubscriberMstrDaoImpl();

		try {

			subscriberDTO.setOldCompSim(subscriberBean.getOldCompSim());
			subscriberDTO.setOldImsi(subscriberBean.getOldImsi());
			subscriberDTO.setOldMsisdn(subscriberBean.getOldMsisdn());
			subscriberDTO.setOldSim(subscriberBean.getOldSim());
			subscriberDTO.setOldCircleId(subscriberBean.getOldCircleId());
			subscriberDTO.setOldCircleId(subscriberBean.getOldCircleId());
			if (!"".equals(subscriberBean.getOldServiceClassId())
					&& subscriberBean.getOldServiceClassId() != null) {
				subscriberDTO.setOldServiceClassId(Integer
						.parseInt(subscriberBean.getOldServiceClassId()));
			}
			subscriberDTO.setImsi(subscriberBean.getImsi());
			subscriberDTO.setMsisdn(subscriberBean.getMsisdn());
			subscriberDTO.setCompSim(subscriberBean.getSim());
			subscriberDTO.setStatus(subscriberBean.getStatus());
			subscriberDTO.setOldStatus(subscriberBean.getOldStatus());
			subscriberDTO.setUpdatedBy(userBean.getUserId());
			subscriberDTO.setId(subscriberBean.getId());
			if (!"".equals(subscriberBean.getServiceClassId())
					&& subscriberBean.getServiceClassId() != null) {
				subscriberDTO.setServiceClassId(Integer.parseInt(subscriberBean
						.getServiceClassId()));
			}

			if (request.getParameter("id") == null) {
				log.debug("The ID value in request parameter is null");
			} else {
				subscriberDTO.setId(request.getParameter("id"));
			}
			if (subscriberBean.getId() == null) {
				log.debug("The ID value in bean is null");
			} else {
				subscriberDTO.setId(request.getParameter("id"));
			}
			
			if (userBean.getCircleId() == null) {
				subscriberDTO.setCircleCode(subscriberBean.getCircleCode());
			} else {
				subscriberDTO.setCircleCode(userBean.getCircleId());
			}
            if(!subscriberBean.getStatus().equalsIgnoreCase(subscriberBean.getOldStatus())
            		&& "D".equalsIgnoreCase(subscriberBean.getStatus())) {
            	
            	/**
    			 * Mapping gets deleted for the previous circle from the mapping
    			 * table *
    			 */
    			if (!(subscriberBean.getInitialCircleValue()
    					.equalsIgnoreCase(subscriberBean.getCircleCode()))
    					&& subscriberBean.getCircleCode() != null) {
    				subscriberDaoImpl.delete(subscriberBean.getMsisdn(),
    						subscriberBean.getOldCompSim());
    			}
    			/** Subscriber Status  getting updated * */
            	subscriberDaoImpl.updateSubscriberStatus(subscriberDTO);
            } else {
            	/**
    			 * Mapping gets deleted for the previous circle from the mapping
    			 * table *
    			 */
    			/*if (!(subscriberBean.getInitialCircleValue()
    					.equalsIgnoreCase(subscriberBean.getCircleCode()))
    					&& subscriberBean.getCircleCode() != null) {
    				subscriberDaoImpl.delete(subscriberBean.getMsisdn(),
    						subscriberBean.getOldCompSim());
    			}*/
    			/** Subscriber details  getting updated * */
            	
            	subscriberDaoImpl.updateSubscriber(subscriberDTO);
            	
            }
			
               log.info(new StringBuffer(100).append(userBean.getLoginId())
					.append(" Subscriber Modified for MSISDN ").append(
							userBean.getLoginId()).toString());

		} catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}

		}
		ActionMessages msg = new ActionMessages();
		ActionMessage message = new ActionMessage("Saved");
		msg.add(ActionMessages.GLOBAL_MESSAGE, message);
		saveMessages(request, msg);
		log.info("Exiting from  updateSubscriber()... ");
		return mapping.findForward(Constants.SUCCESS);
	}
	
	
	

	/**
	 * This method is called when you open track Subscriber JSP
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a Jsp TrackSubscriber
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 */

	public ActionForward trackSubscriber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering trackSubscriber()... ");
		ActionForward forward = new ActionForward();
		SubscriberBean bean = (SubscriberBean) form;
		try {
			UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
					.getAttribute(Constants.USER_INFO);
			if (userBean.getCircleId() == null) {
				bean.setUserRole(Constants.SUPER_ADMIN);
				request.setAttribute("USER_ROLE", Constants.SUPER_ADMIN);
				log.info("TrackSubscriber() initialised"
						+ userBean.getLoginId());
			} else {
				bean.setCircleCode(userBean.getCircleId());
				bean.setUserRole(Constants.CIRCLE_ADMIN);
				log.info("TrackSubscriber() initialised"
						+ userBean.getLoginId());
			}

			forward = mapping.findForward(Constants.INIT);

		} catch (Exception e) {
			log.error("Exception has occur in class trackSubscriber.", e);
			// forwad the page on error page

			return mapping.findForward(Constants.ERROR);
		}
		log.info("Exiting trackSubscriber()... ");
		return forward;

	}

	/**
	 * This method is called when you open track Subscriber JSP and search for
	 * the particular MSISDN
	 * 
	 * @param mapping
	 *            object of ActionMapping class that holds the action mapping
	 *            information used to invoke a Struts action.
	 * @param form
	 *            object of ActionForm which is a JavaBean optionally associated
	 *            with one or more ActionMappings
	 * @param request
	 *            object of HttpServletRequest which Wraps the standard request
	 *            object to override some methods.
	 * @param response
	 *            object of HttpServletResponse which Wraps the standard
	 *            response object to override some methods.
	 * @return a Jsp TrackSubscriber
	 * 
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 */

	public ActionForward subscriberDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering subscriberDetails()... ");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
		SubscriberBean bean = (SubscriberBean) form;
		VSubscriberMstrDaoImpl subscriberDaoImpl = new VSubscriberMstrDaoImpl();
		ArrayList subsriberDetails = null;
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		String subscriberInactive = "";
		try {
			ArrayList subscribers = new ArrayList();
			subscribers = subscriberDaoImpl.findSubscriber(userBean
					.getCircleId(), bean.getSmsisdn());
			if (subscribers.isEmpty() && userBean.getUserType().equalsIgnoreCase(Constants.AdminType)) {
				errors.add("error.valid.MSISDN", new ActionError(
						"error.valid.MSISDN"));
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.INIT);
			} 
			else if (subscribers.isEmpty() && !userBean.getUserType().equalsIgnoreCase(Constants.AdminType)) {
				errors.add("error.valid.MSISDN.CIRCLE", new ActionError(
						"error.valid.MSISDN.CIRCLE"));
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.INIT);
			}
			else {
				Iterator itr= subscribers.iterator();
				while(itr.hasNext()){
					SubscriberDTO subscriberDTO = (SubscriberDTO)itr.next();
					if((Constants.ActiveState).equalsIgnoreCase(subscriberDTO
						.getSubscriberStatus())){
						int noofpages = 0;
						noofpages = subscriberDaoImpl.countSubscriberDetails(userBean
								.getCircleId(), bean.getSmsisdn());
						request.setAttribute("PAGES", new Integer(noofpages));
						Pagination pagination = PaginationUtils
								.setPaginationinRequest(request);
						if (userBean == null) {
							errors.add(Constants.NAME1, new ActionError(
									Constants.SESSION_EXPIRED));
							saveErrors(request, errors);
							log.info(userBean.getLoginId() + " session expired");
							forward = mapping.findForward(Constants.FAILURE1);
						} else {
							subsriberDetails = subscriberDaoImpl.subscriberDetails(
									userBean.getCircleId(), bean.getSmsisdn(),
									pagination.getLowerBound(), pagination
											.getUpperBound());
							log.info("subscriberDetails() initialised"
									+ userBean.getLoginId());
							if (subsriberDetails.isEmpty()) {
								errors.add("error.valid.MSISDN", new ActionError(
										"error.valid.MSISDN"));
								saveErrors(request, errors);
							} else {
								bean.setUserList(subsriberDetails);
							}
							subscriberInactive = Constants.ActiveState;
					}
			break;}else{
					//
					subscriberInactive = Constants.InActive;
				}
						
				}if(subscriberInactive.equalsIgnoreCase(Constants.InActive)){
					errors.add("error.inactive.MSISDN", new ActionError(
						"error.inactive.MSISDN"));
					saveErrors(request, errors);
					forward = mapping.findForward(Constants.INIT);
				}
				if(subscriberInactive.equalsIgnoreCase(Constants.ActiveState)){
					forward = mapping.findForward(Constants.INIT);
				}
			}
		} catch (Exception e) {
			log.error("Exception has occur in class subscriberDetails.", e);
			// forwad the page on error page

			return mapping.findForward(Constants.ERROR);
		}
		log.info("Exiting subscriberDetails()... ");
		return forward;

	}
	
	
	public ActionForward initSubscriberReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering initSubscriberReport()... ");
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);
		ArrayList circleList = new ArrayList();
		ActionErrors errors = new ActionErrors();
		SubscriberBean subscriberBean = (SubscriberBean) form;
		try {
			if (userBean.getCircleId() == null) {
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl.getCircleList());
				subscriberBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				subscriberBean.setCircleCode(userBean.getCircleId());
				subscriberBean.setInitialCircleValue(userBean.getCircleId());
				LabelValueBean lvBean = new LabelValueBean(userBean
						.getCircleId(), userBean.getCircleName());
				circleList.add(lvBean);
				request.setAttribute(Constants.CIRCLE_LIST, circleList);

			}
			log.info(userBean.getLoginId() + " View Subscriber Report Initialized");

		} catch (DAOException de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
		}
		saveErrors(request, errors);
		log.info("Exiting initSubscriberReport()... ");
		return mapping.findForward("INIT");
	}
	
	
	public ActionForward getSubscriberReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionErrors errors = new ActionErrors();
		SubscriberBean subsbean = (SubscriberBean) form;
		VSubscriberMstrDaoImpl subscriberDaoImpl = new VSubscriberMstrDaoImpl();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
		.getAttribute(Constants.USER_INFO);
		FTAService ftaservice = new FTAService();
		ArrayList fileInfoDataList = new ArrayList();
		ArrayList circleList = new ArrayList();
		String circleCode="";
		try{
			//to get the current date
			String currentDate = ftaservice.getCurrentDate();
			
			// to get the other date
			int noOfDays = Integer.parseInt(subsbean.getNoOfDays());
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_MONTH, -noOfDays);
			String createdDate = Utility.getDateAsString(calendar
					.getTime(), "dd-MM-yyyy-HH-mm-ss");
			
			
			String newCreatedDate = Utility.getDateAsString(calendar
					.getTime(), "dd-MM-yyyy");
			if (userBean.getCircleId() == null) {
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl.getCircleList());
				subsbean.setUserRole(Constants.SUPER_ADMIN);
				circleCode = subsbean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
				subsbean.setCircleCode(userBean.getCircleId());
				subsbean.setInitialCircleValue(userBean.getCircleId());
				LabelValueBean lvBean = new LabelValueBean(userBean
						.getCircleId(), userBean.getCircleName());
				circleList.add(lvBean);
				request.setAttribute(Constants.CIRCLE_LIST, circleList);

			}
			
			//generate the report for pending subscribers
			MisReportProcessor misReport = new MisReportProcessor();
			String fileStartName = "PendingSubsribersReport-" + circleCode + "-"
				+ newCreatedDate + "-to-";
			String fileName = misReport.getFullFileName(fileStartName, currentDate);
			String filePath = Utility.getValueFromBundle(
			Constants.KEY_SUBS_REPORT_FILE_STORAGE_PATH,
			Constants.WEB_APPLICATION_RESOURCE_BUNDLE);
			// call the method to create file at location
			misReport.createFile(filePath + fileName, subscriberDaoImpl.getPendingSubscriberReport(circleCode,createdDate,currentDate));
			subsbean.setReportCurrentDate(currentDate);
			subsbean.setReportCreatedDate(createdDate);
			subsbean.setFileName(fileName);
			fileInfoDataList.add(subsbean);
			subsbean.setFileInfoDataList(fileInfoDataList);
			
		}catch (Exception de) {
			log.error(de.getMessage(), de);
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
		}saveErrors(request, errors);
		log.info("Exiting initSubscriberReport()... ");
		return mapping.findForward("Success");
	}

}
