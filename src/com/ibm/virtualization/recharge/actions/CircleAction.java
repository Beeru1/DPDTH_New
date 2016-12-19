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
import org.springframework.context.ApplicationContext;

import com.ibm.dpmisreports.common.AppContext;
import com.ibm.dpmisreports.common.SpringCacheUtility;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.beans.CircleFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.Circle;
import com.ibm.virtualization.recharge.dto.ReportInputs;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.service.CircleService;
import com.ibm.virtualization.recharge.service.impl.CircleServiceImpl;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;


/**
 * Controller class for circle management.
 * 
 * @author Paras
 */
public class CircleAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(CircleAction.class
			.getName());

	/* Local Variables */
	private static final String STATUS_ERROR = "error";

	private static final String INIT_SUCCESS = "initSuccess";

	private static final String CREATE_CIRCLE_FAILURE = "initSuccess";

//	private static final String CREATE_CIRCLE_SUCCESS = "showList";

	private static final String GET_EDIT_CIRCLE_SUCCESS = "showEditJsp";
	
	private static final String GET_VIEW_CIRCLE_SUCCESS = "showViewJsp";
	

	private static final String GET_CIRCLE_FAILURE = "showList";

	private static final String EDIT_CIRCLE_SUCCESS = "showList";

	// private static final String EDIT_CIRCLE_FAILURE = "error";

	private static final String SHOW_LIST_SUCCESS = "showListJsp";

	private static final String SHOW_LIST_FAILURE = "showListJsp";

	private static final String SHOW_LIST = "showListJsp";

	private static final String SHOW_DOWNLOAD_CIRCLE_LIST = "listCircleExport";

	
	
	/**
	 * init method is called to populate Region Dropdown in CreateCircle.jsp
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
		logger.info(" init Started... ");
		/* To populate Region Dropdown in Create JSP */
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_CIRCLE)) {
				logger.info(" user not auth to perform ROLE_ADD_CIRCLE this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			CircleService circleService = new CircleServiceImpl();

			/* Get list of regions from Service Layer */
			ArrayList regionDtoList = circleService.getRegions();
			request.setAttribute("regionList", regionDtoList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * Call to display view/dispaly Circle List
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCircleListJsp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		CircleFormBean bean = (CircleFormBean) form;
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CIRCLE)) {
			logger.info(" user not auth to perform this ROLE_VIEW_CIRCLE activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		logger.info("Auth..."+authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_CIRCLE));
		
		if (authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_CIRCLE)) {
			bean.setEditStatus(Constants.EDIT_AUTHORIZED);
			logger.info(" this is the value of edit "+bean.getEditStatus());
		}else{
			bean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
		}
		
		return mapping.findForward(SHOW_LIST);
	}

	/**
	 * Call to create a new Circle
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward createCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		CircleService circleService = new CircleServiceImpl();
		CircleFormBean bean = (CircleFormBean) form;
		try {
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_CIRCLE)) {
				logger.info(" user not auth to perform ROLE_ADD_CIRCLE this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			Circle circle = new Circle();
			/* Populate DTO object with Bean values */
			/* BeanUtil to populate Circle DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(circle, bean);
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

		//	circle.setCircleName((bean.getCircleName().toUpperCase()).trim());
			circle.setCircleName((bean.getCircleName()).trim());
			
			/* Logged in user information from session */
			
			circle.setCreatedBy(userSessionContext.getId());
			circle.setUpdatedBy(userSessionContext.getId());
			/* Call insertCircleData of CircleServiceImpl (Service Layer) */
			circleService.createCircle(circle);
			
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  circleIdInUse ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			/* Get list of regions from Service Layer */
			ArrayList regionDtoList = circleService.getRegions();
			request.setAttribute("regionList", regionDtoList);
			return mapping.findForward(CREATE_CIRCLE_FAILURE);
		}

		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.circle.create_success"));
		saveErrors(request, errors);

		/* return to the create page with msg  */
		try {
			/* Get list of regions from Service Layer */
			ArrayList regionDtoList = circleService.getRegions();
			request.setAttribute("regionList", regionDtoList);
		} catch (VirtualizationServiceException se) {
			logger.error(" Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		bean.reset(mapping, request);
		return mapping.findForward(INIT_SUCCESS);
		//return mapping.findForward(CREATE_CIRCLE_SUCCESS);
	}

	/**
	 * Call to get Circle Information to Edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getEditCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_CIRCLE)) {
			logger.info(" user not auth to perform this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		
		getCircleDetails(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_EDIT_CIRCLE_SUCCESS);
	}

	/**
	 * Call to get Circle Information to Edit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getViewCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_CIRCLE)) {
			logger.info(" user not auth to perform this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		
		
		getCircleDetails(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_VIEW_CIRCLE_SUCCESS);
	}

	private ActionForward getCircleDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
		try {
			CircleFormBean circleFormBean = (CircleFormBean) form;
			Circle circle = new Circle();
			CircleService circleService = new CircleServiceImpl();
			//String circleName = circleFormBean.getCircleName();
			String circleStatus = circleFormBean.getCircleStatus();
						
			try {
				/* Get list of regions from Service Layer */
				ArrayList regionDtoList = circleService.getRegions();
				request.getSession().setAttribute("regionList", regionDtoList);
			} catch (VirtualizationServiceException se) {
				logger
						.error(
								"  caught Service Exception  redirected to target  showList ",
								se);
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(GET_CIRCLE_FAILURE);
			}
			
			/* get cirlce data from service layer based on circle id */
			//circle = circleService.getCircle(circleName);
			int circleId =Integer.parseInt(circleFormBean.getCircleId()) ;
			circle = circleService.getCircle(circleId);
			String page=circleFormBean.getPage();
			/* BeanUtil to populate Form Bean with DTO data */
			try {
				BeanUtils.copyProperties(circleFormBean, circle);
				circleFormBean.setPage(page);
				circleFormBean.setCircleStatus(circleStatus);
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

			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			if (null != userSessionContext
					&& 0 < userSessionContext.getCircleId()) {
				// If logged in user is circle admin
				circleFormBean.setIsCircleAdmin("Y");
				circleFormBean.setDisabledRegion(String.valueOf(circle
						.getRegionId()));
			}
			/* Store in session to populate edit circle jsp with circle data */
			session.setAttribute(mapping.getAttribute(), form);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  showList ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_CIRCLE_FAILURE);
		}
		
		return null;
	}

	/**
	 * Call to Edit Circle Information
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward editCircle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");

		CircleFormBean circleFormBean;

		try {
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_CIRCLE)) {
				logger.info(" user not auth to perform this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			circleFormBean = (CircleFormBean) form;
			Circle circle = new Circle();
			CircleService circleService = new CircleServiceImpl();
			/*
			 * populate CircleFormBean data to Circle DTO BeanUtil to populate
			 * DTO with Form Bean data
			 */
			try {
				BeanUtils.copyProperties(circle, circleFormBean);
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
			
			if (null != userSessionContext
					&& 0 < userSessionContext.getCircleId()) {
				// If logged in user is circle admin
				// circleFormBean.setIsCircleAdmin("Y");
				circle.setRegionId(Integer.parseInt(circleFormBean
						.getDisabledRegion()));
			}
			circle.setUpdatedBy(userSessionContext.getId());
			logger.info(" Request to update a circle by user "
					+ userSessionContext.getId());
			/* send the data to service layer for persistance */
			circleService.updateCircle(circle);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  showEditJsp ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_EDIT_CIRCLE_SUCCESS);
		}
		if (null != request.getSession().getAttribute("regionList")) {
			request.getSession().removeAttribute("regionList");
		}
		ActionErrors errors = new ActionErrors();
		
		circleFormBean.setFlag(Constants.LIST_ERROR_FLAG);
		
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.circle.update_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(EDIT_CIRCLE_SUCCESS);
	}

	/**
	 * Call to get existing circle list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCircleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...");
		
		CircleFormBean circleFormBean = (CircleFormBean) form;
		try {
			CircleService circleService = new CircleServiceImpl();
			// ArrayList<Circle> allCircleDTOList = new ArrayList<Circle>();
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_CIRCLE));
			
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_CIRCLE)) {
				circleFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+circleFormBean.getEditStatus());
			}else{
				circleFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			
			
			if (null != userSessionContext) {
				{
					
					ReportInputs inputDto = new ReportInputs();
					//inputDto.setStatus(circleFormBean.getStatus());
					inputDto.setStatus(circleFormBean.getCircleStatus());
					inputDto.setEndDt(circleFormBean.getEndDate());
					inputDto.setStartDt(circleFormBean.getStartDate());
					inputDto.setCircleId(userSessionContext.getCircleId());
					
					
					
					/** pagination implementation */
					if (request.getParameter("startDt") == null
							|| request.getParameter("endDt") == null
							|| request.getParameter("circleId") == null
							|| request.getParameter("circleStatus") == null) {
						request.setAttribute("startDt", circleFormBean
								.getStartDate());
						request.setAttribute("endDt", circleFormBean
								.getEndDate());
						request.setAttribute("circleStatus", circleFormBean.getCircleStatus());
					} else {
						request.setAttribute("startDt", request
								.getParameter("startDt"));
						request.setAttribute("endDt", request
								.getParameter("endDt"));
						request.setAttribute("circleStatus", request
								.getParameter("circleStatus"));
					}
					/* call service to count the no of rows */
					/*int noofpages = circleService.getCirclesCount(inputDto);
					logger.info("Inside getCircleList().. no of pages..."+noofpages);*/
					
					/* call to set lower & upper bound for Pagination */
					
					//circleFormBean.setPage(request.getParameter("page"));
					//request.setAttribute("page",circleFormBean.getPage());
					Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
					
					/* call service to find all circles */
					ArrayList allCircleList = circleService.getAllCircles(
							inputDto, pagination.getLowerBound(),pagination.getUpperBound());
					
					Circle circleDto= (Circle)allCircleList.get(0);
					int totalRecords = circleDto.getTotalRecords();
					int noofpages = Utility.getPaginationSize(totalRecords);
					request.setAttribute("PAGES", noofpages);
					
					/* set ArrayList of Bean objects to FormBean */
					circleFormBean.setDisplayDetails(allCircleList);
				}
			}

		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, se);
			ActionErrors errors = new ActionErrors();
				
			if(circleFormBean.getFlag() == null ){
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
			} else if((circleFormBean.getFlag() != null) && (circleFormBean.getFlag().equals(Constants.LIST_ERROR_FLAG))){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.circle.update_success"));
				saveErrors(request, errors);
			}						
			return mapping.findForward(SHOW_LIST_FAILURE);
		}

		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}

	/**
	 * Call to get existing circle list for download purpose
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward downloadCircleList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started...");

		try {
			CircleFormBean circleFormBean = (CircleFormBean) form;
			CircleService circleService = new CircleServiceImpl();
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			if (null != userSessionContext) {

				ReportInputs inputDto = new ReportInputs();
				inputDto.setStatus(circleFormBean.getCircleStatus());
				inputDto.setEndDt(circleFormBean.getEndDate());
				inputDto.setStartDt(circleFormBean.getStartDate());
				inputDto.setCircleId(userSessionContext.getCircleId());
				ArrayList allCircleList = circleService.getAllCircles(inputDto);
				/* set ArrayList of Bean objects to FormBean */
				circleFormBean.setDisplayDetails(allCircleList);
			}

		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(SHOW_LIST_FAILURE);
		}

		logger.info(" Executed... ");
		return mapping.findForward(SHOW_DOWNLOAD_CIRCLE_LIST);
	}
	
	

}