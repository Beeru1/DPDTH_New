/*****************************************************************************\
 **
 ** Distributor Portal.
 **
 ** Copyright (c) 2007-2008 IBM.
 ** All Rights Reserved
 **
 **
 \****************************************************************************/
package com.ibm.dp.actions;

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

import com.ibm.dp.beans.GeographyFormBean;
import com.ibm.dp.dto.Geography;
import com.ibm.dp.service.GeographyService;
import com.ibm.dp.service.impl.GeographyServiceImpl;
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
public class GeographyAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(GeographyAction.class
			.getName());

	/* Local Variables */
	private static final String STATUS_ERROR = "error";

	private static final String INIT_SUCCESS = "initSuccess";

	private static final String CREATE_GEOGRAPHY_FAILURE = "initSuccess";

//	private static final String CREATE_GEOGRAPHY_SUCCESS = "showList";
	
	private static final String CREATE_GEOGRAPHY_SUCCESS = "initSuccess";

	private static final String GET_EDIT_GEOGRAPHY_SUCCESS = "showEditJsp";
	
//	private static final String GET_VIEW_GEOGRAPHY_SUCCESS = "showViewJsp";
	

	private static final String GET_GEOGRAPHY_FAILURE = "showList";

	private static final String EDIT_GEOGRAPHY_SUCCESS = "showList";

	 private static final String EDIT_GEOGRAPHY_FAILURE = "error";

	private static final String SHOW_LIST_SUCCESS = "showListJsp";

	private static final String SHOW_LIST_FAILURE = "showListJsp";

	private static final String SHOW_LIST = "showListJsp";

	private static final String SHOW_DOWNLOAD_GEOGRAPHY_LIST = "listGEOGRAPHYExport";

	/**
	 * init method is called to populate Region Dropdown in CreateGEOGRAPHY.jsp
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
			GeographyFormBean formBean =(GeographyFormBean)form;
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_GEOGRAPHY)) {
				logger.info(" user not auth to perform ROLE_ADD_GEOGRAPHY this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			
			GeographyService GeographyService = new GeographyServiceImpl();
				
			Integer level = Integer.parseInt(request.getParameter("level"));
			//System.out.println("init------level----"+level);
			/* Get list of regions from Service Layer */
			
			ArrayList regionDtoList = new ArrayList();
			if(level >0)
				regionDtoList=GeographyService.getRegions(level);
			formBean.setRegionList(regionDtoList);
			formBean.setLevel(level);
			request.setAttribute("regionList", regionDtoList);
		} catch (VirtualizationServiceException se) {
			ActionErrors errors = new ActionErrors();
			logger.error(" Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(STATUS_ERROR);
		}
		//logger.info(" Executed... ");
		//System.out.println("init executed");
		saveToken(request);
		return mapping.findForward(INIT_SUCCESS);
	}

	/**
	 * Call to display view/dispaly Geography List
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getGeographyListJsp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started... ");
		GeographyFormBean bean = (GeographyFormBean) form;
		// Logged in user information from session 
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		Integer level = Integer.parseInt(request.getParameter("level"));
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		bean.setLevel(level);
		if (!authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_GEOGRAPHY)) {
			logger.info(" user not auth to perform this ROLE_VIEW_GEOGRAPHY activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
		logger.info("Auth..."+authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY));
		
		if (authorizationService.isUserAuthorized(sessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY)) {
			bean.setEditStatus(Constants.EDIT_AUTHORIZED);
			logger.info(" this is the value of edit "+bean.getEditStatus());
		}else{
			bean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
		}
		
		return mapping.findForward(SHOW_LIST);
	}

	/**
	 * Call to create a new Geography
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward createGeography(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		GeographyService GeographyService = new GeographyServiceImpl();
		GeographyFormBean bean = (GeographyFormBean) form;
		Integer level = bean.getLevel();
		ArrayList regionDtoList = new ArrayList();
		
		try {
			if(!isTokenValid(request))
			{
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_ADD_GEOGRAPHY)) {
				logger.info(" user not auth to perform ROLE_ADD_GEOGRAPHY this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}	
			
			Geography Geography = new Geography();
			
			/* Populate DTO object with Bean values */
			/* BeanUtil to populate Geography DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(Geography, bean);
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

		//	Geography.setGeographyName((bean.getGeographyName().toUpperCase()).trim());
			Geography.setGeographyName((bean.getGeographyName()).trim());
			
			/* Logged in user information from session */
			
			Geography.setCreatedBy((int)(userSessionContext.getId()));
			Geography.setUpdatedBy((int)userSessionContext.getId());
			/* Call insertGeographyData of GeographyServiceImpl (Service Layer) */
			GeographyService.createGeography(Geography);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  GeographyIdInUse ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			/* Get list of regions from Service Layer */
			if (level>0)
				regionDtoList = GeographyService.getRegions(level);
			bean.setRegionList(regionDtoList);				
			bean.setLevel(level);
			return mapping.findForward(CREATE_GEOGRAPHY_FAILURE);
		}

		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.Geography.create_success"));
		saveErrors(request, errors);
		/* return to the create page with msg  */
		try {
			/* Get list of regions from Service Layer */
			if (level>0)
				regionDtoList = GeographyService.getRegions(level);
			bean.setRegionList(regionDtoList);	
			bean.setLevel(level);
		} catch (VirtualizationServiceException se) {
			logger.error(" Service Exception occured ", se);
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			mapping.findForward(STATUS_ERROR);
		}
		logger.info(" Executed... ");
		bean.reset(mapping, request);
	//	return mapping.findForward(INIT_SUCCESS);
		return mapping.findForward(CREATE_GEOGRAPHY_SUCCESS);
	}

	/**
	 * Call to get Geography Information to Edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getEditGeography(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY)) {
			logger.info(" user not auth to perform this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		
			getGeographyDetails(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_EDIT_GEOGRAPHY_SUCCESS);
	}

	/*	public ActionForward getViewGeography(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_VIEW_GEOGRAPHY)) {
			logger.info(" user not auth to perform this activity  ");
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(
					"errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		getGeographyDetails(mapping, form, request);
		logger.info(" Executed... ");
		return mapping.findForward(GET_VIEW_GEOGRAPHY_SUCCESS);
	}
*/
	private ActionForward getGeographyDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request) {
		try {
			GeographyFormBean GeographyFormBean = (GeographyFormBean) form;
			Geography Geography = new Geography();
			GeographyService GeographyService = new GeographyServiceImpl();
			//String GeographyName = GeographyFormBean.getGeographyName();
			//String GeographyStatus = GeographyFormBean.getGeographyStatus();
			Integer level = Integer.parseInt(request.getParameter("level"));
			ArrayList regionDtoList = new ArrayList();
			try {
				// Get list of regions from Service Layer 
				if (level>0)
					regionDtoList = GeographyService.getRegions(level);
					
				GeographyFormBean.setRegionList(regionDtoList);
				//request.getSession().setAttribute("regionList", regionDtoList);
			} catch (VirtualizationServiceException se) {
				logger.error("  caught Service Exception  redirected to target  showList ",se);
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
				return mapping.findForward(GET_GEOGRAPHY_FAILURE);
			}
			
			 //get cirlce data from service layer based on GEOGRAPHY id 
			
			int GeographyId =Integer.parseInt(GeographyFormBean.getGeographyId()) ;
			Geography = GeographyService.getGeography(GeographyId,level);

			 //BeanUtil to populate Form Bean with DTO data 
			try {
				BeanUtils.copyProperties(GeographyFormBean, Geography);
		
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

			 //Logged in user information from session 
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
	/*		if (null != userSessionContext && 0 < userSessionContext.getGeographyId()) {
				// If logged in user is Geography admin
				GeographyFormBean.setIsGeographyAdmin("Y");
				GeographyFormBean.setDisabledRegion(String.valueOf(Geography.getRegionId()));
				
			}*/
			GeographyFormBean.setLevel(Integer.parseInt(request.getParameter("level")));
			 //Store in session to populate edit Geography jsp with Geography data 
			session.setAttribute(mapping.getAttribute(), form);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  showList ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(GET_GEOGRAPHY_FAILURE);
		}
		return null;
	}

/**
	 * Call to Edit GEOGRAPHY Information
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward editGeography(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...editGeography ");

		GeographyFormBean GeographyFormBean;
		Integer level = Integer.parseInt(request.getParameter("level"));

		try {
			
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY)) {
				logger.info(" user not auth to perform this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors", new ActionError(
						"errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}					
			GeographyFormBean = (GeographyFormBean) form;
			Geography Geography = new Geography();
			GeographyService GeographyService = new GeographyServiceImpl();
			
			// populate GeographyFormBean data to Geography DTO BeanUtil to populate
			 // DTO with Form Bean data
			 
			try {
				BeanUtils.copyProperties(Geography, GeographyFormBean);
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
			
		/*	if (null != userSessionContext
					&& 0 < userSessionContext.getGeographyId()) {
				// If logged in user is Geography admin
				// GeographyFormBean.setIsGeographyAdmin("Y");
				Geography.setRegionId(Integer.parseInt(GeographyFormBean
						.getDisabledRegion()));
			}*/
		
			Geography.setUpdatedBy((int)userSessionContext.getId());
			logger.info(" Request to update a Geography by user "
					+ userSessionContext.getId());
		//	 send the data to service layer for persistance 
			GeographyService.updateGeography(Geography);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  showEditJsp ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("geographyexist");
			//return mapping.findForward(GET_EDIT_GEOGRAPHY_SUCCESS);
		}
		if ((level >0)&&(null != request.getSession().getAttribute("regionList"))) {
			request.getSession().removeAttribute("regionList");
		}
		ActionErrors errors = new ActionErrors();
		
		GeographyFormBean.setFlag(Constants.LIST_ERROR_FLAG);
		
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.Geography.update_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(EDIT_GEOGRAPHY_SUCCESS);
	}

	/**
	 * Call to get existing GEOGRAPHY list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	
	public ActionForward getGeographyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...");
		
		GeographyFormBean GeographyFormBean = (GeographyFormBean) form;
		Integer level ;
				 level =GeographyFormBean.getLevel();
		try {
			GeographyService GeographyService = new GeographyServiceImpl();
			// ArrayList<Geography> allGeographyDTOList = new ArrayList<Geography>();
			 ///Logged in user information from session 
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_GEOGRAPHY));
			
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY)) {
				GeographyFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
				logger.info(" this is the value of edit "+GeographyFormBean.getEditStatus());
			}else{
				GeographyFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			GeographyFormBean.setLevel(level);
			//session.setAttribute("level", level);
			if (null != userSessionContext) {
				{
					
					ReportInputs inputDto = new ReportInputs();
					//inputDto.setStatus(GeographyFormBean.getStatus());
					inputDto.setStatus(GeographyFormBean.getGeographyStatus());
					inputDto.setEndDt(GeographyFormBean.getEndDate());
					inputDto.setStartDt(GeographyFormBean.getStartDate());
					inputDto.setLevel(GeographyFormBean.getLevel());
				//	inputDto.setGeographyId(userSessionContext.getGeographyId());
							
					/** pagination implementation */
					if (request.getParameter("startDt") == null
							|| request.getParameter("endDt") == null
							|| request.getParameter("GeographyId") == null
							|| request.getParameter("GeographyStatus") == null) {
						request.setAttribute("startDt", GeographyFormBean
								.getStartDate());
						request.setAttribute("endDt", GeographyFormBean
								.getEndDate());
						request.setAttribute("GeographyStatus", GeographyFormBean.getGeographyStatus());
					} else {
						request.setAttribute("startDt", request
								.getParameter("startDt"));
						request.setAttribute("endDt", request
								.getParameter("endDt"));
						request.setAttribute("GeographyStatus", request
								.getParameter("GeographyStatus"));
					}
				
					Pagination pagination = VirtualizationUtils.setPaginationinRequest(request);
					
					// call service to find all Geographys 
					ArrayList allGeographyList = GeographyService.getAllGeographys(
							inputDto, pagination.getLowerBound(),pagination.getUpperBound(),0);
					
					Geography GeographyDto= (Geography)allGeographyList.get(0);
					int totalRecords = GeographyDto.getTotalRecords();
					int noofpages = Utility.getPaginationSize(totalRecords);
					request.setAttribute("PAGES", noofpages);
					
					// set ArrayList of Bean objects to FormBean 
					GeographyFormBean.setGeographyList(allGeographyList);
				
			
				}
			}

		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, se);
			ActionErrors errors = new ActionErrors();
				
			if(GeographyFormBean.getFlag() == null ){
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
			} else if((GeographyFormBean.getFlag() != null) && (GeographyFormBean.getFlag().equals(Constants.LIST_ERROR_FLAG))){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.Geography.update_success"));
				saveErrors(request, errors);
			}						
			return mapping.findForward(SHOW_LIST_FAILURE);
		}

		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}

/*
	public ActionForward getGeographyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...");
		
		GeographyFormBean GeographyFormBean = (GeographyFormBean) form;
		Integer level =GeographyFormBean.getLevel();
		try {
			GeographyService GeographyService = new GeographyServiceImpl();
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			
			logger.info("Auth..."+authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,AuthorizationConstants.ROLE_EDIT_GEOGRAPHY));
			
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(),ChannelType.WEB, AuthorizationConstants.ROLE_EDIT_GEOGRAPHY)) {
				System.out.println("ROLE EDIT");
				GeographyFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
			}else{
				System.out.println("Not Edit Role");
				
				GeographyFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
		
			if (null != userSessionContext) {
				{
					
					ArrayList allGeographyList = GeographyService.getAllGeography(level);			
					// set ArrayList of Bean objects to FormBean 
					GeographyFormBean.setGeographyList(allGeographyList);
					
				}
			}

		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, se);
			ActionErrors errors = new ActionErrors();
				
			if(GeographyFormBean.getFlag() == null ){
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
			} else if((GeographyFormBean.getFlag() != null) && (GeographyFormBean.getFlag().equals(Constants.LIST_ERROR_FLAG))){
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError("messages.Geography.update_success"));
				saveErrors(request, errors);
			}						
			return mapping.findForward(SHOW_LIST_FAILURE);
		}

		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}*/

	/**
	 * Call to get existing Geography list for download purpose
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	/*public ActionForward downloadGeographyList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info(" Started...");

		try {
			GeographyFormBean GeographyFormBean = (GeographyFormBean) form;
			GeographyService GeographyService = new GeographyServiceImpl();
			 Logged in user information from session 
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);

			if (null != userSessionContext) {

				ReportInputs inputDto = new ReportInputs();
				inputDto.setStatus(GeographyFormBean.getGeographyStatus());
				inputDto.setEndDt(GeographyFormBean.getEndDate());
				inputDto.setStartDt(GeographyFormBean.getStartDate());
				inputDto.setGeographyId(userSessionContext.getGeographyId());
				ArrayList allGeographyList = GeographyService.getAllGeographys(inputDto);
				 set ArrayList of Bean objects to FormBean 
				GeographyFormBean.setDisplayDetails(allGeographyList);
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
		return mapping.findForward(SHOW_DOWNLOAD_GEOGRAPHY_LIST);
	}
	
*/	

}