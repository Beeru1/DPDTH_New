/**************************************************************************
 * File     : ServiceClassAction.java
 * Author   : Ashad
 * Created  : Sep 4, 2008
 * Modified : Sep 4, 2008
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.event.CaretEvent;

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

import com.ibm.virtualization.ussdactivationweb.beans.CreateServiceFormBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.ServiceClassDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.ServiceClassDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.ValidatorUtility;



/**
 * @author Ashad
 *
 */
public class ServiceClassAction extends DispatchAction {

	private static Logger logger = Logger
	.getLogger(ServiceClassAction.class.getName());


	/**
	 * This method is used for initializing Creating of Service Class and retrieves
	 * circle list for logged in user using getCirclesForUser() method.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	public ActionForward initService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering initService()... ");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId() + ": "
				: "";
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		try {
			
			ArrayList circleList = new ArrayList();
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
			}
			createservice.setCircleList(circleList);
			createservice.setScCategoryId("102");
			createservice.setScCategoryList(serviceClassDAOImpl.getServiceClassCategoryList());
			logger.info(userLoginId + " Create Service Class Initialized");
		
		} catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
				"Common.MSG_SQL_EXCEPTION"));
			}else if (ErrorCodes.DATABASEEXCEPTION
					.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
				"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
				"Common.MSG_APPLICATION_EXCEPTION"));
			}
		
		}
		saveErrors(request, errors);
		logger.debug("Exiting initService()... ");
		return  mapping.findForward("initService");
	}
	/**
	 * This method is used for Creating of Service Class using
	 * createServiceClass(ServiceClassDTO serviceDTO)method.
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
	public ActionForward createService(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering createService()... ");
		boolean checkValidNum;
		boolean specChar;
		boolean length;
		ActionErrors errors = new ActionErrors();
		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
		HttpSession session = request.getSession();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId() + ": ": "";
		ServiceClassDTO serviceDTO = new ServiceClassDTO();
		ServiceClassDAOImpl  serviceClassDAOImpl = new ServiceClassDAOImpl();
		
		/**Server side validation**/
		length = ValidatorUtility.isValidMaximumLength((createservice
				.getServiceClassId()), 9);
		checkValidNum = ValidatorUtility.isValidNumber(String
				.valueOf(createservice.getServiceClassId()));
		specChar = ValidatorUtility.isJunkFree(String.valueOf(createservice
				.getServiceClassId()));

		if (length == true) {
			if ((createservice.getServiceClassId().length() != 0)
					&& (checkValidNum != false)) {

				serviceDTO.setServiceClassId(Integer.parseInt(createservice
						.getServiceClassId()));
			}

		} else {
			errors.add("SERVICECLASSID_LENGTH", new ActionError(
			"SERVICECLASSID_LENGTH"));
			saveErrors(request, errors);
			return mapping.findForward("initService");
		}

		if (specChar == true) {
			if ((createservice.getServiceClassId().length() != 0)
					&& (checkValidNum != false)) {

				serviceDTO.setServiceClassId(Integer.parseInt(createservice
						.getServiceClassId()));
			}

		} else {

			errors.add("SERVICE_SPECIAL_CHARACTER", new ActionError(
			"SERVICE_SPECIAL_CHARACTER"));
			saveErrors(request, errors);
			return mapping.findForward("initService");
		}
		checkValidNum = ValidatorUtility.isValidNumber(String
				.valueOf(createservice.getServiceClassId()));
		if ((createservice.getServiceClassId().length() != 0)
				&& (checkValidNum != false)) {

			serviceDTO.setServiceClassId(Integer.parseInt(createservice
					.getServiceClassId()));
		} else {

			errors.add("SERVICECLASSID_BLANK", new ActionError(
			"SERVICECLASSID_BLANK"));
			saveErrors(request, errors);
		}

		serviceDTO.setServiceClassName(createservice.getServiceClassName().trim());
		serviceDTO.setCreatedBy(userDetailsBean.getLoginId());
		serviceDTO.setTariffMessage(createservice.getTariffMessage());
		if(createservice.getCircleCode() == null) {
			serviceDTO.setCircleCode( userDetailsBean.getCircleId());
		} else {
			serviceDTO.setCircleCode(createservice.getCircleCode());
		}
		if(createservice.getScCategoryId().trim()!= null && !"".equals(createservice.getScCategoryId().trim())){
			serviceDTO.setScCategoryId(Integer.parseInt(createservice.getScCategoryId().trim()));
		}
		
		if(createservice.isRegistrationReq()){
			serviceDTO.setRegistrationReq("Y");	
		} else {
			serviceDTO.setRegistrationReq("N");	
		}

		try {
			
			ArrayList circleList = new ArrayList();
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
			}
			
			createservice.setCircleList(circleList);
			
			/** Check for duplicate service class in a circle**/
			int rowCount = serviceClassDAOImpl.getServiceNameByIdCode(serviceDTO);
			
			if(rowCount>0){
				errors.add("ServiceClass.DUPLICATE_SC_NAME", new ActionError("ServiceClass.DUPLICATE_SC_NAME"));
				
			} else {
				/** save the service class data in the corresponding table **/ 
				serviceClassDAOImpl.createServiceClass(serviceDTO);
				ActionMessages msg = new ActionMessages();
	            ActionMessage message = new ActionMessage("Saved");
			    msg.add(ActionMessages.GLOBAL_MESSAGE, message);
			    saveMessages(request, msg);
			    return mapping.findForward(Constants.SUCCESS);
				/*errors.add("ServiceClass.CREATED_SUCCESSFULLY", new ActionError(
				"ServiceClass.CREATED_SUCCESSFULLY"));*/
         	}
				logger.info(new StringBuffer(100).append(userLoginId).append(
					serviceDTO.getServiceClassName()).append(
					"ServiceClass.CREATED_SUCCESSFULLY").toString());

		}  catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
				"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
				"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION
					.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
				"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
				"Common.MSG_APPLICATION_EXCEPTION"));
			}
			logger.error(new StringBuffer(100).append(userLoginId).append(
			"Create Service Class Error - ").append(de.getMessage())
			.toString());

		}
		createservice.setScCategoryList(serviceClassDAOImpl.getServiceClassCategoryList());
		saveErrors(request, errors);
		logger.debug("Exiting createService()... ");
		return mapping.findForward("initService");
	}

	/**
	 * This method is used for initializing viewing of ServiceClass and
	 * retrieves circlesLIst using getCirclesForUser() method.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * @return a JSP View_Service_Class
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException, ServletException {
		logger.debug("Entering initView()... ");
		ActionErrors errors = new ActionErrors();
		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
		HttpSession session = request.getSession();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId() + ": ": "";
		try {
			ArrayList circleList = new ArrayList();
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
				createservice.setUserRole(Constants.CIRCLE_ADMIN);
			}
			createservice.setCircleList(circleList);
			logger.info(userLoginId + " View Service Class Inititalized");

		} catch (Exception de) {
			
			logger.error("Exception cause :"+de.getMessage());
			errors.clear();
			errors.add(Constants.GENERAL_ERROR,new ActionError(Constants.GENERAL_ERROR));
			logger.info(new StringBuffer(100).append(userLoginId)
					.append("View Service Class Inititalization Error - ").append(de.getMessage())
					.toString());
			return mapping.findForward(Constants.ERROR);
		}

		saveErrors(request, errors);
		logger.debug("Exiting initView()... ");
		return mapping.findForward("viewService");

	}
	/**
	 * This method is used for viewing of ServiceClass using getServiceClassList(String
	 * CircleCode) method.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	
	
	public ActionForward viewServiceClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		logger.debug("Entering viewServiceClass()... ");
		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
		ActionErrors errors = new ActionErrors();

		HttpSession session = request.getSession();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null ) ? userDetailsBean.getLoginId() + ": ": "";
		ServiceClassDAOImpl serviceClassDAOImpl = new ServiceClassDAOImpl();
		try {

			int noofpages = 0;
			noofpages = serviceClassDAOImpl.countServiceClass(createservice
					.getCircleCode());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils.setPaginationinRequest(request);
			ArrayList circleList = new ArrayList();
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
				createservice.setUserRole(Constants.CIRCLE_ADMIN);
			}
			createservice.setCircleList(circleList);
			
			
			ArrayList serviceClassList = serviceClassDAOImpl.getServiceClassList(createservice
					.getCircleCode(),pagination.getLowerBound(), pagination.getUpperBound());
			
			createservice.setServiceList(serviceClassList);

			logger.info(new StringBuffer(100).append(userLoginId)
					.append(" View Service Class for All Circle ").toString());

		}  catch (DAOException de) {
			de.printStackTrace();
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
				"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION
					.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
				"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
				"Common.MSG_APPLICATION_EXCEPTION"));
			}else if (ErrorCodes.ServiceClass.RESULT_NOT_FOUND.equals(de
					.getMessage())) {
				errors.add("Common.RESULT_NOT_FOUND", new ActionError(
				"Common.RESULT_NOT_FOUND"));
			}

			logger.error(new StringBuffer(100).append(userLoginId)
					.append("View Service Class Error - ").append(de.getMessage())
					.toString());
		}catch (Exception e) {
			logger.error("Exception has occur in creating a user.", e);
		}
		saveErrors(request, errors);
		logger.debug("Exiting viewServiceClass()... ");
		return mapping.findForward("viewService");
	}

	/**
	 * This method is used for initializing Editing of ServiceClass using
	 * getServiceClassListByIdCode(integer serviceClassId,
					String circleCode) method.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	public ActionForward initEditServiceClass(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{

		logger.debug("Entering initEditServiceClass()... ");

		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
        
		ActionErrors errors = (ActionErrors)request.getSession().getAttribute("errors");
		errors = errors != null ? errors : new ActionErrors();
		HttpSession session = request.getSession();
		int serviceClassId=0;
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		if(createservice.getServiceClassId() != null && !"".equals(createservice.getServiceClassId())){
			serviceClassId = Integer.parseInt(createservice
					.getServiceClassId());
		}

		String circleCode = createservice
				.getCircleCode();
		String userLoginId = (userDetailsBean != null ) ? userDetailsBean.getLoginId() + ": ": "";
		ServiceClassDTO serviceDTO = new ServiceClassDTO();
		ServiceClassDAOImpl  serviceClassDAOImpl =new ServiceClassDAOImpl();
		ArrayList circleList = new ArrayList();
		
		try {
			
			serviceDTO = serviceClassDAOImpl.getServiceClassListByIdCode(serviceClassId,circleCode);

			String serviceId = String.valueOf(serviceDTO.getServiceClassId());
			createservice.setServiceClassId(serviceId);
			createservice.setServiceClassName(serviceDTO.getServiceClassName());
			createservice.setOldServiceClassName(serviceDTO.getServiceClassName());
			createservice.setCircleCode(serviceDTO.getCircleCode());
			createservice.setStatus(serviceDTO.getStatus());
			createservice.setServiceId1(serviceClassId);
			createservice.setScCategoryId(String.valueOf(serviceDTO.getScCategoryId()));
			createservice.setTariffMessage(serviceDTO.getTariffMessage());
			
			if("Y".equalsIgnoreCase(serviceDTO.getRegistrationReq())){
				createservice.setRegistrationReq(true);
			} else {
				createservice.setRegistrationReq(false);
			}
			
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
				createservice.setUserRole(Constants.CIRCLE_ADMIN);
			}
			createservice.setCircleList(circleList);
			createservice.setServiceId1(serviceClassId);
			createservice.setOldServiceClassName(createservice.getServiceClassName());
			createservice.setCircleCode1(serviceDTO.getCircleCode());
			createservice.setScCategoryList(serviceClassDAOImpl.getServiceClassCategoryList());
			logger.info(userLoginId + "Edit Service Class Initialized");

		}  catch (DAOException de) {
			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
				"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.DATABASEEXCEPTION
					.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
				"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
				"Common.MSG_APPLICATION_EXCEPTION"));
			}
			logger.error(new StringBuffer(100).append(userLoginId)
					.append("Edit Service Class Inititalization Error - ").append(de.getMessage())
					.toString());

		}catch (Exception e) {
			logger.error("Exception has occur in creating a user.", e);
		}
		session.removeAttribute("errors");
		if(!errors.isEmpty()){
			saveErrors(request, errors);
		}
		logger.debug("Exiting initEditServiceClass()... ");
		return mapping.findForward("editService");

	}
	/**
	 * This method is used for saving changes to ServicesClass information after
	 * modification using updateServiceClass(ServiceClassDTO serviceDTO, integer Id) method.
	 * 
	 * @param  mapping object of ActionMapping class that holds the action mapping information used to invoke a Struts action.
	 * @param  form object of ActionForm which is a JavaBean optionally associated with one or more ActionMappings
	 * @param  request object of HttpServletRequest which Wraps the standard request object to override some methods.
	 * @param  response object of HttpServletResponse which Wraps the standard response object to override some methods.
	 * @return a JSP Create_Service_Class
	 * 
	 * @exception IOException,Signals that an I/O exception of some sort has occurred.
	 * @exception ServletException,This exception is thrown to indicate a servlet problem.
	 */
	public ActionForward saveChangesToServices(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("Entering saveChangesToServices()... ");
		ActionErrors errors = new ActionErrors();
		
		CreateServiceFormBean createservice = (CreateServiceFormBean) form;
		ServiceClassDTO serviceDTO = new ServiceClassDTO();

		HttpSession session = request.getSession();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null ) ? userDetailsBean.getLoginId() + ": ": "";
		int rowCount=0;
		
		serviceDTO.setCircleCode(createservice.getCircleCode1());
		serviceDTO.setServiceClassId(createservice.getServiceId1());
		serviceDTO.setServiceClassName(String.valueOf(createservice
				.getServiceId1()));
		serviceDTO.setStatus(createservice.getStatus());
		serviceDTO.setCircleCode(createservice.getCircleCode1());
		serviceDTO.setTariffMessage(createservice.getTariffMessage());
		int Id = createservice.getServiceId1();
		serviceDTO.setModifiedBy(userDetailsBean.getLoginId());
		serviceDTO.setServiceClassName(createservice.getServiceClassName());
		
		if(createservice.getScCategoryId() != null && !"".equals(createservice.getScCategoryId())){
			serviceDTO.setScCategoryId(Integer.parseInt(createservice.getScCategoryId()));	
		}
		if(createservice.isRegistrationReq()){
			serviceDTO.setRegistrationReq("Y");
		} else {
			serviceDTO.setRegistrationReq("N");
		}

		try {
			ServiceClassDAOImpl serviceClassDAOImpl =new ServiceClassDAOImpl();
			ArrayList circleList = new ArrayList();
			int noofpages = 0;
			noofpages = serviceClassDAOImpl.countServiceClass(createservice
					.getCircleCode());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils.setPaginationinRequest(request);
			if ( userDetailsBean.getCircleId() == null ){
				circleList=ViewEditCircleMasterDAOImpl.getCircleList();
				createservice.setUserRole(Constants.SUPER_ADMIN);
			}else{
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean.getCircleId(),userDetailsBean.getCircleName()); 
				circleList.add(lvBean);
				createservice.setUserRole(Constants.CIRCLE_ADMIN);
			}
			
			createservice.setCircleList(circleList);
			createservice.setCircleCode(createservice.getCircleCode1());
			createservice.setServiceClassId(String.valueOf(createservice.getServiceId1()));
			/** Check for duplicate service class in a circle**/
			if(!(createservice.getServiceClassName().trim().equalsIgnoreCase(createservice.getOldServiceClassName().trim()))){
				rowCount = serviceClassDAOImpl.getServiceNameByIdCode(serviceDTO);
				if(rowCount>0){
					errors.add("ServiceClass.DUPLICATE_SC_NAME", new ActionError("ServiceClass.DUPLICATE_SC_NAME"));
					session.setAttribute("errors", errors);
					createservice.setServiceList(serviceClassDAOImpl.getServiceClassList(createservice.getCircleCode(),pagination.getLowerBound(), pagination.getUpperBound()));
					return initEditServiceClass(mapping,form, request,response);
					//return mapping.findForward("viewService");
				}else {
					/** Upadate service class data**/
					serviceClassDAOImpl.updateServiceClass(serviceDTO, Id);
					//createservice.setServiceList(serviceClassDAOImpl.getServiceClassList(createservice.getCircleCode(),pagination.getLowerBound(), pagination.getUpperBound()));
					ActionMessages msg = new ActionMessages();
		            ActionMessage message = new ActionMessage("Saved");
				    msg.add(ActionMessages.GLOBAL_MESSAGE, message);
				    saveMessages(request, msg);
				    logger.info(new StringBuffer(100).append(userLoginId)
							.append(serviceDTO.getServiceClassName())
							.append(" ServiceClass.MODIFIED_SUCCESSFULLY for Service class Code ").toString());
				    return mapping.findForward(Constants.SUCCESS);
				   
				}
			}else {
				/** Upadate service class data**/
				serviceClassDAOImpl.updateServiceClass(serviceDTO, Id);
				//createservice.setServiceList(serviceClassDAOImpl.getServiceClassList(createservice.getCircleCode(),pagination.getLowerBound(), pagination.getUpperBound()));
				ActionMessages msg = new ActionMessages();
	            ActionMessage message = new ActionMessage("Saved");
			    msg.add(ActionMessages.GLOBAL_MESSAGE, message);
			    saveMessages(request, msg);
			    logger.info(new StringBuffer(100).append(userLoginId)
						.append(serviceDTO.getServiceClassName())
						.append(" ServiceClass.MODIFIED_SUCCESSFULLY for Service class Code ").toString());
			    return mapping.findForward(Constants.SUCCESS);
			}
			/*errors.add("ServiceClass.MODIFIED_SUCCESSFULLY", new ActionError(
			"ServiceClass.MODIFIED_SUCCESSFULLY"));
		*/	

		}  catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
				"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
				"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION
					.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
				"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
				"Common.MSG_APPLICATION_EXCEPTION"));
			}
			logger.error(new StringBuffer(100).append(userLoginId)
					.append("Edit Service Class Error - ").append(de.getMessage())
					.toString());
			saveErrors(request, errors);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception has occur in creating a user.", e);
		}
		finally{
			logger.debug("Exiting saveChangesToServices()... ");
		}
		
		saveErrors(request, errors);
		return mapping.findForward("viewService");
	}

}
