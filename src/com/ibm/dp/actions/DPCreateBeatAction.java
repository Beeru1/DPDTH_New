package com.ibm.dp.actions;

import java.io.PrintWriter;
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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.ibm.dp.beans.DPCreateBeatFormBean;
import com.ibm.dp.dto.DPCreateBeatDto;
import com.ibm.dp.service.DPCreateBeatService;
import com.ibm.dp.service.impl.DPCreateBeatServiceImpl;
import com.ibm.virtualization.framework.bean.ChannelType;
import com.ibm.virtualization.recharge.actions.CircleAction;
import com.ibm.virtualization.recharge.authorization.AuthorizationConstants;
import com.ibm.virtualization.recharge.authorization.AuthorizationServiceImpl;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.Utility;
import com.ibm.virtualization.recharge.dto.UserSessionContext;
import com.ibm.virtualization.recharge.exception.DAOException;
import com.ibm.virtualization.recharge.exception.VirtualizationServiceException;
import com.ibm.virtualization.recharge.service.AuthorizationInterface;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.virtualization.recharge.utils.VirtualizationUtils;

public class DPCreateBeatAction extends DispatchAction {

	/* Logger for this class. */
	private static Logger logger = Logger.getLogger(CircleAction.class
			.getName());

	/* Local Variables */
	private static final String STATUS_ERROR = "error";

	private static final String INIT_SUCCESS = "initSuccess";

	private static final String CREATE_BEAT_SUCCESS = "createBeatSuccess";

	private static final String CREATE_BEAT_FAILURE = "createBeatFailure";

	// private static final String EDIT_CIRCLE_FAILURE = "error";

	private static final String SHOW_LIST_SUCCESS = "showListJsp";

	private static final String SHOW_LIST_FAILURE = "showListJsp";

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
		ActionErrors errors = new ActionErrors();
		/* To populate Region Dropdown in Create JSP */
		try {

			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(sessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_ADD_BEAT)) {
				logger
						.info(" user not auth to perform ROLE_ADD_BEAT this activity  ");
				
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			DPCreateBeatFormBean createBeatFormBean = (DPCreateBeatFormBean) form;
			createBeatFormBean.reset();
			createBeatFormBean.setAccountCityName(sessionContext.getAccountCityName());
			createBeatFormBean.setAccountCityId(sessionContext.getAccountCityId());
//			DPCreateBeatService createBeatService = new DPCreateBeatServiceImpl();

			/* Get list of regions from Service Layer */
//			ArrayList cityList = createBeatService.getCities();
//			createBeatFormBean.setCityList(cityList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(INIT_SUCCESS);
		}
		
		
		
		finally{
			logger.info("in finally block...");
		}
		logger.info(" Executed... ");
		return mapping.findForward(INIT_SUCCESS);
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
	public ActionForward createBeat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started... ");
		DPCreateBeatService craeteBeatService = new DPCreateBeatServiceImpl();
		DPCreateBeatFormBean createBeatFormBean = (DPCreateBeatFormBean) form;
		try {

			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_ADD_BEAT)) {
				logger
						.info(" user not auth to perform ROLE_ADD_BEAT this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}

			DPCreateBeatDto createBeatDto = new DPCreateBeatDto();
			/* Populate DTO object with Bean values */
			/* BeanUtil to populate Circle DTO with Form Bean data */
			try {
				BeanUtils.copyProperties(createBeatDto, createBeatFormBean);
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

			createBeatDto.setCreatedBy(Long.valueOf(userSessionContext.getId())
					.intValue());
			createBeatDto.setUpdatedBy(Long.valueOf(userSessionContext.getId())
					.intValue());
			/* Call insertCircleData of CircleServiceImpl (Service Layer) */
			craeteBeatService.insertBeat(createBeatDto);
		} catch (VirtualizationServiceException se) {
			logger
					.error(
							"  caught Service Exception  redirected to target  circleIdInUse ",
							se);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(se.getMessage()));
			saveErrors(request, errors);
			// /* Get list of regions from Service Layer */
			// ArrayList regionDtoList = circleService.getRegions();
			// request.setAttribute("regionList", regionDtoList);
			return mapping.findForward(CREATE_BEAT_FAILURE);
		} catch (DAOException dao) {
			logger
					.error(
							"  caught Service Exception  redirected to target  circleIdInUse ",
							dao);
			ActionErrors errors = new ActionErrors();
			errors.add("errors", new ActionError(dao.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CREATE_BEAT_FAILURE);
		}

		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
				"messages.beat.create_success"));
		saveErrors(request, errors);
		logger.info(" Executed... ");
		return mapping.findForward(CREATE_BEAT_SUCCESS);
	}
	
	public ActionForward callViewBeatList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		DPCreateBeatFormBean beatFormBean = (DPCreateBeatFormBean)form;
		ActionErrors errors = new ActionErrors();
		try{
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(), ChannelType.WEB,
				AuthorizationConstants.ROLE_VIEW_BEAT)) {
			logger
					.info(" user not auth to perform ROLE_VIEW_BEAT this activity  ");
			
			errors.add("errors",
					new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		beatFormBean.setAccountCityName(userSessionContext.getAccountCityName());
//		DPCreateBeatService beatService = new DPCreateBeatServiceImpl();
//		ArrayList circleList = beatService.getAllcircles();
//		beatFormBean.setCircleList(circleList);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.SEARCH_BEAT_PAGE);
		}
		forward = mapping.findForward(Constants.SEARCH_BEAT_PAGE);
		return forward;
	}

	public ActionForward viewBeatList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info(" Started...");

		DPCreateBeatFormBean beatFormBean = (DPCreateBeatFormBean) form;
		try {
			DPCreateBeatService beatService = new DPCreateBeatServiceImpl();
			/* Logged in user information from session */
			HttpSession session = request.getSession();
			UserSessionContext userSessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
			AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
			if (!authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_VIEW_BEAT)) {
				logger
						.info(" user not auth to perform ROLE_ADD_BEAT this activity  ");
				ActionErrors errors = new ActionErrors();
				errors.add("errors",
						new ActionError("errors.usernotautherized"));
				saveErrors(request, errors);
				return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
			}
			if (authorizationService.isUserAuthorized(userSessionContext
					.getGroupId(), ChannelType.WEB,
					AuthorizationConstants.ROLE_EDIT_BEAT)){
				beatFormBean.setEditStatus(Constants.EDIT_AUTHORIZED);
			}else{
				beatFormBean.setEditStatus(Constants.EDIT_UNAUTHORIZED);
			}
			if (null != userSessionContext) {
				{
					DPCreateBeatDto beatDto = new DPCreateBeatDto();
					// inputDto.setStatus(circleFormBean.getStatus());
					beatDto.setBeatStatus(beatFormBean.getBeatStatus());
					beatDto.setAccountCityId(userSessionContext.getAccountCityId());
					beatDto.setAccountZoneId(userSessionContext.getAccountZoneId());
					beatDto.setCircleId(userSessionContext.getCircleId());
					beatDto.setLoginId(userSessionContext.getId());
					beatDto.setAccountLevel(userSessionContext.getAccountLevel());
					/** pagination implementation */
					if (request.getParameter("circleId") == null
							|| request.getParameter("accountZoneId") == null
							|| request.getParameter("accountCityId") == null
							|| request.getParameter("beatStatus") == null) {
						request.setAttribute("circleId", beatFormBean
								.getBeatStatus());
						request.setAttribute("accountZoneId", beatFormBean
								.getBeatStatus());
						request.setAttribute("accountCityId", beatFormBean
								.getBeatStatus());
						request.setAttribute("beatStatus", beatFormBean
								.getBeatStatus());
					} else {
						request.setAttribute("circleId", request
								.getParameter("circleId"));
						request.setAttribute("accountZoneId", request
								.getParameter("accountZoneId"));
						request.setAttribute("accountCityId", request
								.getParameter("accountCityId"));
						request.setAttribute("beatStatus", request
								.getParameter("beatStatus"));
					}
					/* call service to count the no of rows */
					/*
					 * int noofpages = circleService.getCirclesCount(inputDto);
					 * logger.info("Inside getCircleList().. no of
					 * pages..."+noofpages);
					 */
					/* call to set lower & upper bound for Pagination */
					// beatFormBean.setPage(request.getParameter("page"));
					// request.setAttribute("page",beatFormBean.getPage());
					Pagination pagination = VirtualizationUtils
							.setPaginationinRequest(request);
					/* call service to find all circles */
					
					ArrayList allBeatList = beatService.getAllBeats(
							beatDto, pagination.getLowerBound(), pagination
									.getUpperBound());
					//beatDto = (DPCreateBeatDto) allBeatList.get(0);
					//System.out.println("Total Rows -------"+allBeatList.size());
					/* Change to fix Pagination issue on View Beat
					int totalRecords = beatDto.getTotalRecords();*/
					int totalRecords = beatService.getAllBeatsCount(beatDto);
					int noofpages = Utility.getPaginationSize(totalRecords);
					request.setAttribute("PAGES", noofpages);
					/* set ArrayList of Bean objects to FormBean */
					beatFormBean.setBeatDetails(allBeatList);
				}
			}
		} catch (VirtualizationServiceException se) {
			logger.error("  caught Service Exception  redirected to target  "
					+ SHOW_LIST_FAILURE, se);
			ActionErrors errors = new ActionErrors();
			if (beatFormBean.getFlag() == null) {
				errors.add("errors", new ActionError(se.getMessage()));
				saveErrors(request, errors);
			} else if ((beatFormBean.getFlag() != null)
					&& (beatFormBean.getFlag()
							.equals(Constants.LIST_ERROR_FLAG))) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						"messages.circle.update_success"));
				saveErrors(request, errors);
			}
			return mapping.findForward(SHOW_LIST_FAILURE);
		}
		logger.info(" Executed... ");
		return mapping.findForward(SHOW_LIST_SUCCESS);
	}
	
	public ActionForward getParentCategory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("options");
		Element optionElement;
		ArrayList objectList = new ArrayList();
		DPCreateBeatService beatService = new DPCreateBeatServiceImpl();
		try {
			int tradeId = Integer.parseInt(request.getParameter("OBJECT_ID"));
			String condition = request.getParameter("condition");
				if(condition.equalsIgnoreCase("zone")){
						objectList = beatService.getZones(tradeId);
					}else
						if(condition.equalsIgnoreCase("city")){
							objectList = beatService.getCites(tradeId);
						}
			DPCreateBeatDto beatDto = null;
			for (int counter = 0; counter < objectList.size(); counter++) {
				beatDto = (DPCreateBeatDto) objectList.get(counter);
				optionElement = root.addElement("option");
				
				if (beatDto != null) {
					if(condition.equalsIgnoreCase("zone")){
						optionElement.addAttribute("value", beatDto.getAccountZoneId()+ "");
						optionElement.addAttribute("text", beatDto.getAccountZoneName());
					}
					else if(condition.equalsIgnoreCase("city")){
						optionElement.addAttribute("value", beatDto.getAccountCityId()+ "");
						optionElement.addAttribute("text", beatDto.getAccountCityName());
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
	
	public ActionForward callEditBeat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionForward forward = null;
		DPCreateBeatFormBean beatFormBean = (DPCreateBeatFormBean)form;
		ActionErrors errors = new ActionErrors();
		try{
		/* Logged in user information from session */
		HttpSession session = request.getSession();
		UserSessionContext userSessionContext = (UserSessionContext) session
				.getAttribute(Constants.AUTHENTICATED_USER);
		AuthorizationInterface authorizationService = new AuthorizationServiceImpl();
		if (!authorizationService.isUserAuthorized(userSessionContext
				.getGroupId(), ChannelType.WEB,
				AuthorizationConstants.ROLE_EDIT_BEAT)) {
			logger
					.info(" user not auth to perform ROLE_VIEW_BEAT this activity  ");
			
			errors.add("errors",
					new ActionError("errors.usernotautherized"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.USER_NOT_AUTHERIZED);
		}
		DPCreateBeatService beatService = new DPCreateBeatServiceImpl();
		DPCreateBeatDto beat = new DPCreateBeatDto();
		beat = beatService.getBeatRecord(beatFormBean.getBeatId());
		BeanUtils.copyProperties(beatFormBean, beat);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.EDIT_BEAT_PAGE);
		}
		forward = mapping.findForward(Constants.EDIT_BEAT_PAGE);
		return forward;
	}
	
	public ActionForward updateBeat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException{
		String message = null;
		DPCreateBeatFormBean beatFormBean = (DPCreateBeatFormBean)form;
		DPCreateBeatDto beat = new DPCreateBeatDto();
		ActionErrors errors = new ActionErrors();
		try{
			HttpSession session = request.getSession();
			UserSessionContext sessionContext = (UserSessionContext) session
					.getAttribute(Constants.AUTHENTICATED_USER);
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
			long loginId = sessionContext.getId();
			beatFormBean.setUpdatedBy(Long.valueOf(loginId).intValue());
			BeanUtils.copyProperties(beat, beatFormBean);
			DPCreateBeatService beatService = new DPCreateBeatServiceImpl();
			message = beatService.updateBeat(beat);
			errors.add("message.account", new ActionError(
			"message.account.update_success"));
			saveErrors(request, errors);
		}catch (InvocationTargetException itExp) {
			logger.error(
					" caught Exception while using BeanUtils.copyProperties()",
					itExp);
			errors.add("message.account", new ActionError(
			"message.account.update_fail"));
			saveErrors(request, errors);
			return mapping.findForward(Constants.ACCOUNT_ERROR);
		} catch (VirtualizationServiceException vse) {
			vse.printStackTrace();
			logger.error(" Service Exception occured   ", vse);
			errors.add("error.account", new ActionError(vse.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(Constants.EDIT_ACCOUNT);
		}
		return mapping.findForward(message);
	}
}