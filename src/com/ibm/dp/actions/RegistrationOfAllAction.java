package com.ibm.dp.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.ibm.core.exception.DAOException;
import com.ibm.dp.beans.RegistrationOfAllBean;
import com.ibm.dp.beans.UserDetailsBean;
import com.ibm.dp.dao.RegistrationOfAllUsersDAO;
import com.ibm.dp.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.dp.dto.LocationDataDTO;
import com.ibm.dp.dto.RegistrationOfAllDTO;
import com.ibm.virtualization.recharge.utils.Pagination;
import com.ibm.utilities.PaginationUtils;
import com.ibm.dp.common.Constants;
import com.ibm.dp.common.SQLConstants;
import com.ibm.utilities.USSDCommonUtility;
import com.ibm.dp.dao.LocationDataDAO;

/**************************************************************************
 * File     : RegistrationOfAllAction.java
 * Author   : Abhipsa
 * Created  : Sept 10, 2008
 * Modified : Sept 10, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sept 10, 2008 	Abhipsa	First Cut.
 * V0.2		Sept 10, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
public class RegistrationOfAllAction extends DispatchAction {

	private static final Logger logger = Logger.getLogger(RegistrationOfAllAction.class);

	/**
	 * 
	 * This method is used to initiate RegistrationOfAll.jsp
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering init() : RegistrationOfAllAction.");
		ActionErrors errors =null;
		ActionForward forward = new ActionForward();
		RegistrationOfAllBean RegBean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO allRegDAO = new RegistrationOfAllUsersDAO();
		LocationDataDAO locDao = new LocationDataDAO();
		ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
		List circleList = new ArrayList();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
		.getAttribute(Constants.AUTHENTICATED_USER);
		
		try {
			
			logger.info(" Registration of Business user has been initiated by "+userBean.getLoginId());
			// control enters if logged in user is Super Admin
			
			if (userBean.getCircleId() == null) {
				//populating users list
				RegBean.setTypeOfUserList(allRegDAO.getUserTypeList(Constants.SUPER_ADMIN));
				if(RegBean.getSearchBasis()==null||("".toString()).equalsIgnoreCase(RegBean.getSearchBasis()))
				{
					RegBean.setSearchBasis(Constants.all);
				}	
				RegBean.setHubList(circledao.getHubList());
				RegBean.setFosCheck(true);
				RegBean.setFosActRightsBool(false);
				RegBean.setUserRole(Constants.ADMIN_GROUP_ID +"");
				//checking null values of circle in two ways one will null other with ""
				if("".equalsIgnoreCase(RegBean.getCircleCode()))
				{
					RegBean.setCircleCode(null);
				}
				if (RegBean.getCircleCode() != null) {
					String aepfcheck = allRegDAO.aepfcheck(RegBean
							.getCircleCode());
					RegBean.setAepfCheck(aepfcheck);
										
				}
				if (RegBean.getTypeOfUserId() != 0) {
					RegBean.setBaseLoc(allRegDAO.getBaseLoc(RegBean
							.getTypeOfUserId()));
					if ((RegBean.getBaseLoc().equals(Constants.strCircle))
							|| (RegBean.getBaseLoc().equals(Constants.strZone))
							|| (RegBean.getBaseLoc().equals(Constants.strCity))) {
						// circle List
						RegBean.setCircleList(circledao
								.getCircleListByHub(RegBean.getHubCode()));
						//if there are no circles corresponding to given hub
						if(RegBean.getCircleList().size()==0&&!(RegBean.getHubCode().equalsIgnoreCase(Constants.minusOne)))
						{	errors= new ActionErrors();
							errors.add(Constants.no_cicles, new ActionError(Constants.no_cicles));
							saveErrors(request, errors);
						}
						
					}
				}
				/**Fetching Zone list ***
				if (RegBean.getCircleCode() != null) {
					if ((RegBean.getBaseLoc().equals(Constants.strZone))
							|| (RegBean.getBaseLoc().equals(Constants.strCity))) {
					/** Setting Values for zonal User only ***
//					LocationDataDTO locationDTO= new LocationDataDTO();
//					if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
//						ArrayList locationList= new ArrayList();
//						locationDTO.setLocationDataName(userBean.getZoneName());
//							locationList.add(locationDTO);
//							RegBean.setZoneList(locationList);
//						}else{
						// Zone list
						RegBean.setZoneList(locDao.getLocationList(RegBean
								.getCircleCode(), Constants.Zone,Constants.PAGE_FALSE,0,0));
						//}
//						if there are no Zone corresponding to given city
						if(RegBean.getZoneList().size()==0)
						{
							errors= new ActionErrors();
							errors.add(Constants.no_zone, new ActionError(Constants.no_zone));
							saveErrors(request, errors);
							ArrayList emptyList = new ArrayList();
							LocationDataDTO locDTO = new LocationDataDTO();
							locDTO.setLocDataId(0);
							locDTO.setLocationDataName("");
							emptyList.add(locDTO);
							RegBean.setZoneId(-1);
							RegBean.setZoneList(emptyList);
						}
					}
				}
				
				/**Fetching City list ***
				if (RegBean.getZoneId() != 0 && RegBean.getZoneId() != -1) {
					if ((RegBean.getBaseLoc().equals(Constants.strCity))) {
						// city list
						RegBean.setCityList(locDao.getLocationList(String
								.valueOf(RegBean.getZoneId()), Constants.City,Constants.PAGE_FALSE,0,0));
						//if there are no cities corresponding to given circle
						if(RegBean.getCityList().size()==0)
						{	
							errors= new ActionErrors();
							errors.add(Constants.no_cities, new ActionError(Constants.no_cities));
							saveErrors(request, errors);
							ArrayList emptyList = new ArrayList();
							LocationDataDTO locDTO = new LocationDataDTO();
							locDTO.setLocDataId(0);
							RegBean.setCityId(-1);
							locDTO.setLocationDataName("");
							emptyList.add(locDTO);
							RegBean.setCityList(emptyList);
						}
					}
				}
				
				request.setAttribute(Constants.USER_ROLE, Constants.SUPER_ADMIN);
				request.setAttribute(Constants.hubList, RegBean.getHubList());
				request.setAttribute(Constants.CIRCLE_LIST, RegBean
						.getCircleList());
				request.setAttribute(Constants.typeOfUserList, RegBean
						.getTypeOfUserList());
				request.setAttribute(Constants.baseLoc, RegBean.getBaseLoc());
				request.setAttribute(Constants.cityList, RegBean.getCityList());
				request.setAttribute(Constants.zoneList, RegBean.getZoneList());
				// control enters else if logged in user is Circle Admin
			} else {
//				populating users list
				if(userBean.getUserType().equalsIgnoreCase(Constants.CIRCLE_ADMIN_USERBEAN)){
					RegBean.setTypeOfUserList(allRegDAO.getUserTypeList(Constants.CIRCLE_ADMIN));
				}else{
					RegBean.setTypeOfUserList(allRegDAO.getUserTypeList(Constants.Zone_User));
				}
				LabelValueBean lvBean = new LabelValueBean(userBean
						.getCircleId(), userBean.getCircleName());
				circleList.add(lvBean);
				RegBean.setFosCheck(true);
				RegBean.setFosActRightsBool(true);
				String aepfcheck = allRegDAO.aepfcheck(userBean
						.getCircleId());
				RegBean.setAepfCheck(aepfcheck);
				if (RegBean.getTypeOfUserId() != 0) {
					RegBean.setBaseLoc(allRegDAO.getBaseLoc(RegBean
							.getTypeOfUserId()));
				}
				RegBean.setCircleCode(userBean.getCircleId());
				RegBean.setHubCode(userBean.getHubCode());
				if (RegBean.getTypeOfUserId() != 0) {
					RegBean.setBaseLoc(allRegDAO.getBaseLoc(RegBean
							.getTypeOfUserId()));
					
					/**Fetching Zone list ***
					if ((RegBean.getBaseLoc().equals(Constants.strZone))
							|| (RegBean.getBaseLoc().equals(Constants.strCity))) {
						/** Setting Values for zonal User only ***
						LocationDataDTO locationDTO= new LocationDataDTO();
						if(userBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
						ArrayList locationList= new ArrayList();
						locationDTO.setLocationDataName(userBean.getZoneName());
						locationDTO.setLocDataId(userBean.getZoneCode());
							locationList.add(locationDTO);
							if(!locationList.isEmpty()){
							RegBean.setZoneList(locationList);
							RegBean.setZoneId(userBean.getZoneCode());
							}else 
								logger.error("Zone List empty for zone user");
						}else{
						// Zone list
						RegBean.setZoneList(locDao.getLocationList(userBean
								.getCircleId(), Constants.Zone,Constants.PAGE_FALSE,0,0));
						}
//						if there are no Zone corresponding to given city
						if(RegBean.getZoneList().size()==0)
						{
							errors= new ActionErrors();
							errors.add(Constants.no_zone, new ActionError(Constants.no_zone));
							saveErrors(request, errors);
							ArrayList emptyList = new ArrayList();
							LocationDataDTO locDTO = new LocationDataDTO();
							locDTO.setLocDataId(0);
							locDTO.setLocationDataName("");
							emptyList.add(locDTO);
							RegBean.setZoneId(-1);
							RegBean.setZoneList(emptyList);
						}
					}

				}
				if (RegBean.getZoneId() != 0&&RegBean.getZoneId() != -1) {
					if ((RegBean.getBaseLoc().equals(Constants.strCity))){
					
						// city list
						RegBean.setCityList(locDao.getLocationList(String
								.valueOf(RegBean.getZoneId()), Constants.City,Constants.PAGE_FALSE,0,0));
//						if there are no cities corresponding to given circle
						if(RegBean.getCityList().size()==0)
						{
							errors= new ActionErrors();
							errors.add(Constants.no_cities, new ActionError(Constants.no_cities));
							saveErrors(request, errors);
							ArrayList emptyList = new ArrayList();
							LocationDataDTO locDTO = new LocationDataDTO();
							locDTO.setLocDataId(0);
							RegBean.setCityId(-1);
							locDTO.setLocationDataName("");
							emptyList.add(locDTO);
							RegBean.setCityList(emptyList);
						}
					}
				}
				request.setAttribute(Constants.CIRCLE_LIST, circleList);
				request.setAttribute(Constants.baseLoc, RegBean.getBaseLoc());
				request.setAttribute(Constants.typeOfUserList, RegBean
						.getTypeOfUserList());
				request.setAttribute(Constants.cityList, RegBean.getCityList());
				request.setAttribute(Constants.zoneList, RegBean.getZoneList());
				RegBean.setUserRole(Constants.CIRCLE_ADMIN);
			}
			// forward to registrationOfAll.jsp
			forward = mapping.findForward(Constants.INIT);
		} catch (DAOException e) {
			logger.error("Exception cause :" + e.getMessage());
			logger.info(userBean.getLoginId()+" Exception occured during the initialization of Registration of Business user ");
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			// forward to error.jsp
			forward = mapping.findForward(Constants.ERROR);
		}
		logger.debug("Exiting init() : RegistrationOfAllAction.");
		return forward;
	}

	/**
	 * 
	 * This method is called when we press submit button on
	 * RegistrationOfAll.jsp This method interacts with data base to create new
	 * business user with the information entered through jsp.
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering insert() method of RegistrationOfAllAction.");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();
		RegistrationOfAllBean RegBean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO allRegDAO = new RegistrationOfAllUsersDAO();
		RegistrationOfAllDTO allRegDTO = new RegistrationOfAllDTO();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.AUTHENTICATED_USER);
		try {
			
			if (userBean.getCircleId() == null) {
				allRegDTO.setCircleCode(RegBean.getCircleCode());
				allRegDTO.setHubCode(RegBean.getHubCode());
			} else {
				allRegDTO.setCircleCode(userBean.getCircleId());
				allRegDTO.setHubCode(userBean.getHubCode());
			}
			// to enter the location id for the last location in the hierarchy
			if (RegBean.getZoneId() != 0) {
				if (RegBean.getCityId() != 0) {
					allRegDTO.setLocId(RegBean.getCityId());
				}else{
					allRegDTO.setLocId(RegBean.getZoneId());
				}
			}
			allRegDTO.setTypeOfUserId(RegBean.getTypeOfUserId());
			allRegDTO.setBusinessUserName(RegBean.getBusinessUserName());
			allRegDTO.setAddress(RegBean.getAddress());
			if (RegBean.getCode() != null) {
				allRegDTO.setCode(RegBean.getCode());
			}
			allRegDTO.setContactNo(RegBean.getContactNo());
			allRegDTO.setFosCheck(RegBean.isFosCheck());
			allRegDTO.setAepfCheck(RegBean.getAepfCheck());
			if (RegBean.isFosActRightsBool()) {
				allRegDTO.setFosActRights(Constants.Y);
			} else {
				allRegDTO.setFosActRights(Constants.N);
			}
			allRegDTO.setSearchBasis(RegBean.getSearchBasis());
			
			//entering service class information only for distributor.
			if (RegBean.getTypeOfUserId() == Constants.DISTIBUTOR) {
				if (RegBean.getIncludeServiceClass() != null) {
					allRegDTO.setIncludeServiceClass(RegBean
							.getIncludeServiceClass());
					allRegDTO.setAllServiceClass(Constants.N);
				} else {
					allRegDTO.setAllServiceClass(Constants.Y);
					if (RegBean.getExcludeServiceClass() != null) {
						allRegDTO.setExcludeServiceClass(RegBean
								.getExcludeServiceClass());
					}
				}
			}
			allRegDTO.setRegNumber(RegBean.getRegNumber());
			int intUserId = Integer.parseInt(userBean.getUserId());
			String strResult = null;

			// inserting data
			strResult = allRegDAO.insert(allRegDTO, intUserId);
			String strResult1="";
			String code="";
			if(strResult.length()>Constants.LengthOfInserted)
			{
			code=strResult.substring(Constants.LengthOfInserted);
			strResult1=strResult.substring(0,Constants.LengthOfInserted);
			}

			if (strResult1.equalsIgnoreCase(Constants.INSERTED)) {
				errors.add("UserCreated",new ActionError("ErrorKey","Data saved successfully for business user with code: "+code));
				saveMessages(request, errors);
				logger.info("Business User Inserted for login id  " + userBean.getLoginId());
				// forward to success.jsp
				forward = mapping.findForward(Constants.SUCCESSCAPS);
			} else if (strResult.equalsIgnoreCase(Constants.SERVICE_CLASS_EXSISTS)) {
				errors.add(Constants.errors_service_class, new ActionError(
						Constants.errors_service_class));
				saveErrors(request, errors);
				logger.info("Serive Class id's entered by  " + userBean.getLoginId()+" are invalid.");
				forward = init(mapping, form, request, response);

			} else {
				errors.add(Constants.errors_already, new ActionError(Constants.errors_already));
				saveErrors(request, errors);
				logger.info("Business user which " + userBean.getLoginId()+" wants to create already exists.");
				forward = init(mapping, form, request, response);
			}
			
		} catch (DAOException e) {
			logger.error(Constants.EXCEPTION + e.getMessage());
			logger.info(" Exception occured while inserting a new Business user by "+userBean.getLoginId());
			errors.add(Constants.errors_insert, new ActionError(Constants.errors_insert));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			return mapping.findForward(Constants.ERROR);
		}
		logger.debug("Exiting insert() method of RegistrationOfAllAction.");
		return forward;

	}

	/**
	 * 
	 * This method is used to initiate ViewEditProfile.jsp
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **

	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering initView() method of RegistrationOfAllAction.");
		ActionErrors errors = new ActionErrors();
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO dao = null;
		UserDetailsBean userBean = (UserDetailsBean) (request
				.getSession(true)).getAttribute(Constants.AUTHENTICATED_USER);
		try {
			
			dao = new RegistrationOfAllUsersDAO();
			List bussinessUserList = null;
			String name = "";
			String status = bean.getStatus();
			if (bean.getBusinessUserName() != null) {
				name = bean.getBusinessUserName().toUpperCase();
			}
			name += "%";
			int noofpages = 0;
			if (bean.getCircleCode() != null) 
			{
				if (("null").equals(bean.getCircleCode()))
				{
					bean.setCircleCode(null);
				}
			}
			if (userBean.getCircleId() == null) {
				bean.setTypeOfUserList(dao.getUserTypeList(Constants.SUPER_ADMIN));
				bean.setUserRole(Constants.SUPER_ADMIN);
				if (bean.getCircleCode() != null) {
					noofpages = dao.countBusinessUsers(bean.getCircleCode(),
							bean.getTypeOfUserId(), bean.getSearchBasis(),
							name, bean.getCode(), bean.getStatus(), bean
									.getRegNumber(),bean.getIdentifier());
					request.setAttribute("PAGES", new Integer(noofpages));
					Pagination pagination = null;//PaginationUtils.setPaginationinRequest(request);
					bussinessUserList = dao.getBussinessUserList(bean
							.getCircleCode(), bean.getTypeOfUserId(), bean
							.getSearchBasis(), name, bean.getCode(), bean
							.getStatus(), bean.getRegNumber(), pagination
							.getLowerBound(), pagination.getUpperBound(),bean.getIdentifier());
				}
				request.setAttribute(Constants.CIRCLE_LIST, USSDCommonUtility
						.getCircleList());
			} else {
				if(userBean.getUserType().equalsIgnoreCase(Constants.CIRCLE_ADMIN_USERBEAN)){
					bean.setTypeOfUserList(dao.getUserTypeList(Constants.CIRCLE_ADMIN));
				}else{
					bean.setTypeOfUserList(dao.getUserTypeList(Constants.Zone_User));
				}
				if (bean.getSearchBasis() != null) 
				{
					if(("null").equals(bean.getSearchBasis()))
					{
						bean.setSearchBasis(null);
					}
				}	
				if (bean.getSearchBasis() != null) {
					noofpages = dao.countBusinessUsers(userBean.getCircleId(),
							bean.getTypeOfUserId(), bean.getSearchBasis(),
							name, bean.getCode(), bean.getStatus(), bean
									.getRegNumber(),bean.getIdentifier());
					request.setAttribute("PAGES", new Integer(noofpages));
					Pagination pagination = null;//PaginationUtils.setPaginationinRequest(request);
					bussinessUserList = dao.getBussinessUserList(userBean
							.getCircleId(), bean.getTypeOfUserId(), bean
							.getSearchBasis(), name, bean.getCode(), bean
							.getStatus(), bean.getRegNumber(), pagination
							.getLowerBound(), pagination.getUpperBound(),bean.getIdentifier());
					bean.setUserRole(Constants.CIRCLE_ADMIN);
				}
			}
			logger.info("Common Search Business user page initialised for loginId: "+ userBean.getLoginId());
			bean.setBussinessUserList(bussinessUserList);
			request.setAttribute("bussinessUserList", bussinessUserList);
			if(bean.getSearchBasis()==null||bean.getSearchBasis().equals("null"))
			{
				bean.setSearchBasis(null);
			}
		
			if (bean.getSearchBasis()!=null) {
				if (bussinessUserList.isEmpty()) {
					errors.add(Constants.error_list_noListFound, new ActionError(
							Constants.error_list_noListFound));
					saveErrors(request, errors);

				} else {
					bean.setBussinessUserList(bussinessUserList);
					bean.setStatus(status);
				}
			}
			logger
					.debug("Exiting initView() method of RegistrationOfAllAction.");
			// forward to ViewEditProfile.jsp
			request.setAttribute("typeOfUserList", bean.getTypeOfUserList());
			return mapping.findForward(bean.getIdentifier());

		} catch (Exception e) {
			logger.error("Exception has occur in class initView.", e);
			logger.info("Exception occured while viewing Business user by "+userBean.getLoginId());
			errors.add("exception", new ActionError("exception"));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			// forwad the page on error page
			return mapping.findForward(Constants.ERROR);
		}

	}

	/**
	 * 
	 * When you have selected the user from ViewEditProfile.jsp and pressed
	 * submit button This method is called. This method will retrieve all the
	 * required information of the selected user. And will redirect you to new
	 * jsp called EditProfile.jsp
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **
	public ActionForward viewProfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger
				.debug("Entering viewProfile() method of RegistrationOfAllAction.");
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		ActionErrors errors = new ActionErrors();
		RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
		UserDetailsBean userBean = (UserDetailsBean) (request
				.getSession(true)).getAttribute(Constants.AUTHENTICATED_USER);
		ArrayList movebizUsers = new ArrayList();
		ArrayList finalmovebizUsers = new ArrayList();
		try {
			
			dto = dao.getUserProfile(Integer
					.parseInt(bean.getBussinessUserId()));
			
					
			request.setAttribute("typeOfUserId", String.valueOf(bean
					.getTypeOfUserId()));
			boolean bolFsoCheck = false;
			boolean actRight = false;
			if (Constants.Y.equalsIgnoreCase(dto.getFsoCheck())) {
				bolFsoCheck = true;
			} else {
				bolFsoCheck = false;
			}
			if (Constants.Y.equalsIgnoreCase(dto.getFosActRights())) {
				actRight = true;
			} else {
				actRight = false;
			}
			if (userBean.getCircleId() == null) {
				bean.setUserRole(Constants.SUPER_ADMIN);
				bean.setTypeOfUserValue(dto.getTypeOfUserValue());
				bean.setAddress(dto.getAddress());
				bean.setCode(dto.getCode());
				bean.setBusinessUserName(dto.getBusinessUserName());
				bean.setContactNo(dto.getContactNo());
				bean.setStatus(dto.getStatus());

				// setting list of Hub.
				bean.setHubCode(dto.getHubCode());
				bean.setHubList(dto.getHubList());
				bean.setHubCode2(dto.getHubCode());
				
				// setting list of circle.
				bean.setCircleCode(dto.getCircleCode());
				bean.setCircleList(dto.getCircleList());
				bean.setCircleCode2(dto.getCircleCode());

				// setting city list.
				bean.setCityList(dto.getCityList());
				bean.setCityId(dto.getCityId());
				bean.setCityId2(dto.getCityId());

				// setting zone list.
				bean.setZoneList(dto.getZoneList());
				bean.setZoneId(dto.getZoneId());
				bean.setZoneId2(dto.getZoneId());

				// setting registered Number
				bean.setRegNumber(dto.getRegNumber());
				bean.setRegNumberId(dto.getRegNumberId());
				
//				AEPF check for a circle
				if (bean.getCircleCode() != null) {
					String aepfcheck = dao.aepfcheck(bean
							.getCircleCode());
					bean.setAepfCheck(aepfcheck);
										
				}else{
					bean.setAepfCheck(Constants.N);
				}
				
				//getting move business user list
				
				if(dto.getTypeOfUserId()==Constants.ED){
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), dto.getHubCode());
				}else if(dto.getTypeOfUserId()==Constants.CEO || dto.getTypeOfUserId()==Constants.SALES_HEAD){
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), dto.getCircleCode());
				}else{
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), String.valueOf(dto.getLocId()));
				}
				
				if(!movebizUsers.isEmpty()){
					// to move the original user from the list
					Iterator itr = movebizUsers.iterator();
					while(itr.hasNext()){
						RegistrationOfAllDTO dto1 = (RegistrationOfAllDTO)itr.next();
						if(!(dto1.getBussinessUserId()).equalsIgnoreCase(dto.getBussinessUserId()) )
						{
							finalmovebizUsers.add(dto1);
						}
					}
					// setting move to list
					bean.setMoveBizUsers(finalmovebizUsers);
				}else{
					ArrayList moveUsers=new ArrayList();
					RegistrationOfAllDTO regDTO = new RegistrationOfAllDTO();
					regDTO.setBussinessUserId("");
					moveUsers.add(regDTO);
					bean.setMoveBizUsers(moveUsers);
				}
				
				
				bean.setFosCheck(bolFsoCheck);
				bean.setFosActRightsBool(actRight);
				bean.setRegNumber(dto.getRegNumber());
				bean.setAllServiceClass(dto.getAllServiceClass());
//				 to control the page load function on the jsp. So that it is called only once , when the value is 0
				
				bean.setJspPageLoadControl(0);
				if (dto.getTypeOfUserId() == Constants.DISTIBUTOR) {
					if (dto.getExcludeServiceClass() != null) {
						bean.setExcludeServiceClass(dto
								.getExcludeServiceClass());
					} else {
						bean.setExcludeServiceClass("");
					}
					if (dto.getIncludeServiceClass() != null) {
						bean.setIncludeServiceClass(dto
								.getIncludeServiceClass());
					} else {
						bean.setIncludeServiceClass("");
					}

					if (bean.getAllServiceClass() != null) {
						if (bean.getAllServiceClass().equals(Constants.Y)) {
							bean.setSearchBasis(Constants.all);
						} else {
							bean.setSearchBasis(Constants.Include);
						}
					}
				}

			} else {
				
				if(userBean.getUserType().equalsIgnoreCase(Constants.CIRCLE_ADMIN_USERBEAN)){
					bean.setUserRole(Constants.CIRCLE_ADMIN);
				}else{
					bean.setUserRole(Constants.Zone_User);
				}				
				bean.setTypeOfUserValue(dto.getTypeOfUserValue());
				bean.setAddress(dto.getAddress());
				bean.setCode(dto.getCode());
				bean.setBusinessUserName(dto.getBusinessUserName());
				bean.setContactNo(dto.getContactNo());
				bean.setStatus(dto.getStatus());

				// setting list of Hub.
				bean.setHubCode(dto.getHubCode());
				bean.setHubList(dto.getHubList());
				bean.setHubCode2(dto.getHubCode());

				// setting list of circle.
				bean.setCircleCode(dto.getCircleCode());
				bean.setCircleList(dto.getCircleList());
				bean.setCircleCode2(dto.getCircleCode());

				// setting city list.
				bean.setCityList(dto.getCityList());
				bean.setCityId(dto.getCityId());
				bean.setCityId2(dto.getCityId());

				// setting zone list.
				bean.setZoneList(dto.getZoneList());
				bean.setZoneId(dto.getZoneId());
				bean.setZoneId2(dto.getZoneId());
				
				//AEPF check for a circle
				String aepfcheck = dao.aepfcheck(userBean.getCircleId());
				bean.setAepfCheck(aepfcheck);
				
				
				//getting move business user list
				
				if(dto.getTypeOfUserId()==Constants.ED)
				{
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), dto.getHubCode());
				}else if(dto.getTypeOfUserId()==Constants.CEO || dto.getTypeOfUserId()==Constants.SALES_HEAD)
				{
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), dto.getCircleCode());
				}else{
					movebizUsers=dao.getmoveBizUserList(dto.getTypeOfUserId(), String.valueOf(dto.getLocId()));
				}
				
				if(!movebizUsers.isEmpty()){
					// to move the original user from the list
					Iterator itr = movebizUsers.iterator();
					while(itr.hasNext()){
						RegistrationOfAllDTO dto1 = (RegistrationOfAllDTO)itr.next();
						if(!(dto1.getBussinessUserId()).equalsIgnoreCase(dto.getBussinessUserId()) )
						{
							finalmovebizUsers.add(dto1);
						}
					}
					// setting move to list
					bean.setMoveBizUsers(finalmovebizUsers);	
			}else{	
				ArrayList moveUsers=new ArrayList();
				RegistrationOfAllDTO regDTO = new RegistrationOfAllDTO();
				regDTO.setBussinessUserId("");
				moveUsers.add(regDTO);
				bean.setMoveBizUsers(moveUsers);
			}

				bean.setFosCheck(bolFsoCheck);
				bean.setFosActRightsBool(actRight);
				bean.setRegNumber(dto.getRegNumber());
				bean.setRegNumberId(dto.getRegNumberId());
				bean.setAllServiceClass(dto.getAllServiceClass());
				bean.setJspPageLoadControl(0);
				if (dto.getTypeOfUserId() == Constants.DISTIBUTOR) {
					if (dto.getExcludeServiceClass() != null) {
						bean.setExcludeServiceClass(dto
								.getExcludeServiceClass());
					} else {
						bean.setExcludeServiceClass("");
					}
					if (dto.getIncludeServiceClass() != null) {
						bean.setIncludeServiceClass(dto
								.getIncludeServiceClass());
					} else {
						bean.setIncludeServiceClass("");
					}

					if (bean.getAllServiceClass() != null) {
						if (bean.getAllServiceClass().equals(Constants.Y)) {
							bean.setSearchBasis(Constants.all);
						} else {
							bean.setSearchBasis(Constants.Include);
						}
					}
				}

			}
			logger.info("viewing of a specific user initialised for login ID: "+ userBean.getLoginId());
			// forward to EditProfile.jsp
			return mapping.findForward(Constants.Profile);

		} catch (Exception e) {
			logger.error("Exception has occur in class viewProfile.", e);
			logger.info("Exception occured while viewing details of Business user by "+userBean.getLoginId());
			errors.add("exception", new ActionError("exception"));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			// forwad the page on error page
			return mapping.findForward(Constants.ERROR);
		}

	}

	/**
	 * 
	 * This method is called when we press Submit button from Editprofile.jsp
	 * This method updates the information of the user in the data base.
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **
	public ActionForward updateProfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger
				.debug("Entering updateProfile() method of RegistrationOfAllAction.");
		RegistrationOfAllBean bean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession(true))
				.getAttribute(Constants.AUTHENTICATED_USER);
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward();

		try {
			int intUserId = Integer.parseInt(userBean.getUserId());
			
			// setting DTO
			RegistrationOfAllDTO allRegDTO = new RegistrationOfAllDTO();
			allRegDTO.setBussinessUserId(bean.getBussinessUserId());
			
			if (userBean.getCircleId() == null) {
				allRegDTO.setCircleCode(bean.getCircleCode());
				allRegDTO.setHubCode(bean.getHubCode());
			} else {
				allRegDTO.setCircleCode(userBean.getCircleId());
				allRegDTO.setHubCode(userBean.getHubCode());
			}
			allRegDTO.setTypeOfUserId(bean.getTypeOfUserId());
			allRegDTO.setBusinessUserName(bean.getBusinessUserName());
			allRegDTO.setAddress(bean.getAddress());
			if (bean.getCode() != null) {
				allRegDTO.setCode(bean.getCode());
			}
			allRegDTO.setRegNumber(bean.getRegNumber());
			allRegDTO.setRegNumberId(bean.getRegNumberId());
			allRegDTO.setContactNo(bean.getContactNo());
			allRegDTO.setFosCheck(bean.isFosCheck());
			allRegDTO.setStatus(bean.getStatus());
			allRegDTO.setAepfCheck(bean.getAepfCheck());
			if (bean.isFosActRightsBool()) {
				allRegDTO.setFosActRights(Constants.Y);
			} else {
				allRegDTO.setFosActRights(Constants.N);
			}
			if(bean.isFosCheck())
			{
				allRegDTO.setFsoCheck(Constants.Y);
			}else
			{
				allRegDTO.setFsoCheck(Constants.N);
			}
			allRegDTO.setSearchBasis(bean.getSearchBasis());
			if (bean.getTypeOfUserId() == Constants.DISTIBUTOR) {
				if (bean.getIncludeServiceClass() != null) {
					allRegDTO.setIncludeServiceClass(bean
							.getIncludeServiceClass());
					allRegDTO.setAllServiceClass(Constants.N);
				} else {
					allRegDTO.setAllServiceClass(Constants.Y);
					if (bean.getExcludeServiceClass().length() != 0) {
						allRegDTO.setExcludeServiceClass(bean
								.getExcludeServiceClass());
					}
				}
			}
			if (bean.getZoneId() != 0) {
				if ( bean.getCityId()!= 0) {
						allRegDTO.setLocId(bean.getCityId());
				}else {
					allRegDTO.setLocId(bean.getZoneId());
				}
			}
			//getting the bisness user under with the reporting users have to be moved
			allRegDTO.setMoveToBizuser(bean.getMoveToBizUser());
			
			//changing the parent of the reporting users
			int checkstatus=0;
			if(bean.getMoveToBizUser()!=-1)
			{
				checkstatus=dao.updateParentOfBizUserList(Integer.parseInt(allRegDTO.getBussinessUserId()), allRegDTO.getMoveToBizuser());
			}
			if(checkstatus>0)
			{
				logger.debug("reporting users moved under BisnessUser with Id : "+allRegDTO.getMoveToBizuser());
			}
			
			// deleting mapping if loaction is changed.
			dao.deleteMapping(Integer.parseInt(bean.getBussinessUserId()), bean
					.getTypeOfUserId(),allRegDTO);
			// updating the value of user
			String strResult = dao.update(allRegDTO, intUserId);
			//changing the status of registered number
			dao.inactiveRegNo(bean);
			if (strResult.equalsIgnoreCase(Constants.UPDATED)) {
				ActionMessages messages = new ActionMessages();
				ActionMessage actionMsg = new ActionMessage("Saved");
				messages.add(ActionMessages.GLOBAL_MESSAGE, actionMsg);
				saveMessages(request, messages);
				logger.info("User"+userBean.getLoginId() + "Updated successfully for method updateProfile()");
				// forward to success.jsp
				return mapping.findForward(Constants.SUCCESSCAPS);
			} else if (strResult.equalsIgnoreCase(Constants.SERVICE_CLASS_EXSISTS)) {
				errors.add(Constants.errors_service_class, new ActionError(
						Constants.errors_service_class));
				saveErrors(request, errors);
				forward = viewProfile(mapping, form, request, response);
				return forward;
			} else if (strResult.equalsIgnoreCase(Constants.REGISTERNUMBER)) {
				errors.add(Constants.errors_registered_number, new ActionError(
						Constants.errors_registered_number));
				saveErrors(request, errors);
				forward = viewProfile(mapping, form, request, response);
				return forward;
			}else {
				request.setAttribute(Constants.CIRCLE_LIST, USSDCommonUtility
						.getCircleList());
				errors.add("errors.dealer.already", new ActionError("ErrorKey",
						"User name already exist."));
				saveErrors(request, errors);
				logger
						.debug("Exiting updateProfile() method of RegistrationOfAllAction.");
				// forward to error.jsp
				return mapping.findForward("Failure");
			}
		} catch (Exception e) {
			logger.error("Exception has occur in class updateProfile.", e);
			logger.info("Exception occured while updating the details of Business user by "+userBean.getLoginId());
			errors.add("exception", new ActionError("exception"));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			// forwad the page on error page
			return mapping.findForward(Constants.ERROR);
		}

	}

	/**
	 * 
	 * This method is used to reset editProfile.jsp
	 * 
	 * 
	 * @param mapping
	 *            :ActionMapping
	 * @param form
	 *            :ActionForm
	 * @param request
	 *            :HttpServletRequest
	 * @param response:HttpServletResponse
	 * @return forward:ActionForward
	 * @throws Exception
	 **
	public ActionForward reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering reset() method of RegistrationOfAllAction.");
		RegistrationOfAllBean RegBean = (RegistrationOfAllBean) form;
		RegistrationOfAllUsersDAO allRegDAO = new RegistrationOfAllUsersDAO();
		ActionErrors errors =null;
		LocationDataDAO locDao = new LocationDataDAO();
		ViewEditCircleMasterDAOImpl circledao = new ViewEditCircleMasterDAOImpl();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession(true))
				.getAttribute(Constants.AUTHENTICATED_USER);
		ArrayList movebizUsers = new ArrayList();
		ArrayList finalmovebizUsers = new ArrayList();
		
		// to control the page load function on the jsp. So that it is called only once , when the value is 0 - in viewProfile method
		int jspPageLoad = RegBean.getJspPageLoadControl();
		
		try {
			jspPageLoad++;
	
			RegBean.setJspPageLoadControl(jspPageLoad);
			
			// setting hub list
			RegBean.setHubList(circledao.getHubList());
			
			
				
			if (userBean.getCircleId() == null) {
				// nothing to be done
			} else {
				RegBean.setCircleCode(userBean.getCircleId());
			}
			
			
			if (RegBean.getTypeOfUserId() != 0) {
				RegBean.setBaseLoc(allRegDAO.getBaseLoc(RegBean.getTypeOfUserId()));
				if ((RegBean.getBaseLoc().equals(Constants.strCircle))
						|| (RegBean.getBaseLoc().equals(Constants.strZone))
						|| (RegBean.getBaseLoc().equals(Constants.strCity)) ) {
					// circle List
					RegBean.setCircleList(circledao.getCircleListByHub(RegBean
							.getHubCode()));
					if(RegBean.getCircleList().size()==0&&!(RegBean.getHubCode().equalsIgnoreCase("-1")))
					{
						if(userBean.getCircleId() == null) {
							errors = new ActionErrors();
							errors.add(Constants.no_cicles, new ActionError(Constants.no_cicles));
							saveErrors(request, errors);
						}
						
					}
				}
			}
			if(("").equalsIgnoreCase(RegBean.getCircleCode()))
			{
				RegBean.setCircleCode(null);
			}
			
			if (RegBean.getCircleCode() != null) {
				if ((RegBean.getBaseLoc().equals(Constants.strZone))
						|| (RegBean.getBaseLoc().equals(Constants.strCity))) {
					//city list
					RegBean.setZoneList(locDao.getLocationList(RegBean
							.getCircleCode(), Constants.Zone,Constants.PAGE_FALSE,0,0));
	//				if there are no zones corresponding to given circle
					if(RegBean.getZoneList().size()==0)
					{
						errors = new ActionErrors();
						errors.add(Constants.no_zone, new ActionError(Constants.no_zone));
						saveErrors(request, errors);
						ArrayList emptyList = new ArrayList();
						LocationDataDTO locDTO = new LocationDataDTO();
						locDTO.setLocDataId(0);
						locDTO.setLocationDataName("");
						emptyList.add(locDTO);
						RegBean.setZoneId(-1);
						RegBean.setZoneList(emptyList);
					}
					
				}
			}
			if (RegBean.getZoneId() != 0&&RegBean.getZoneId() != -1) {
				if ((RegBean.getBaseLoc().equals(Constants.strCity)) ) {
					//Zone list
					RegBean.setCityList(locDao.getLocationList(String
							.valueOf(RegBean.getZoneId()), Constants.City,Constants.PAGE_FALSE,0,0));
	//				if there are no city corresponding to given zone
					if(RegBean.getCityList().size()==0)
					{
						errors = new ActionErrors();
						errors.add(Constants.no_cities, new ActionError(Constants.no_cities));
						saveErrors(request, errors);
						ArrayList emptyList = new ArrayList();
						LocationDataDTO locDTO = new LocationDataDTO();
						locDTO.setLocDataId(0);
						RegBean.setCityId(-1);
						locDTO.setLocationDataName("");
						emptyList.add(locDTO);
						RegBean.setCityList(emptyList);
					}
				}
			}
			
			if(RegBean.getTypeOfUserId()==Constants.ED){
				if(!("-1").equalsIgnoreCase(RegBean.getHubCode())){
					movebizUsers=allRegDAO.getmoveBizUserList(RegBean.getTypeOfUserId(), RegBean.getHubCode2());
				}else{
					RegBean.setMoveBizUsers(null);
				}
			}else if(RegBean.getTypeOfUserId()==Constants.CEO || RegBean.getTypeOfUserId()==Constants.SALES_HEAD){
				if(!("0").equalsIgnoreCase(RegBean.getCircleCode())){
					movebizUsers=allRegDAO.getmoveBizUserList(RegBean.getTypeOfUserId(), RegBean.getCircleCode2());
				}else{
					RegBean.setMoveBizUsers(null);
				}
			}else if(RegBean.getTypeOfUserId()==Constants.ZBM || RegBean.getTypeOfUserId()==Constants.ZSM)
			{
				if((!("-1").equalsIgnoreCase(String.valueOf(RegBean.getZoneId()))) && (!("0").equalsIgnoreCase(String.valueOf(RegBean.getZoneId()))) )
				{
					movebizUsers=allRegDAO.getmoveBizUserList(RegBean.getTypeOfUserId(), String.valueOf(RegBean.getZoneId2()));
				}else{
					RegBean.setMoveBizUsers(null);
				}
			}else if(RegBean.getTypeOfUserId()==Constants.TM || RegBean.getTypeOfUserId()==Constants.DISTIBUTOR || RegBean.getTypeOfUserId()==Constants.DEALER || RegBean.getTypeOfUserId()==Constants.FOS)
			{
				if((!("-1").equalsIgnoreCase(String.valueOf(RegBean.getCityId()))) && (!("0").equalsIgnoreCase(String.valueOf(RegBean.getCityId()))) )
				{
					movebizUsers=allRegDAO.getmoveBizUserList(RegBean.getTypeOfUserId(), String.valueOf(RegBean.getCityId2()));
				}else{
					RegBean.setMoveBizUsers(null);
				}
					
			}
			
			if(!movebizUsers.isEmpty()){
				// to move the original user from the list
				Iterator itr = movebizUsers.iterator();
				while(itr.hasNext()){
					RegistrationOfAllDTO dto1 = (RegistrationOfAllDTO)itr.next();
					if(!(dto1.getBussinessUserId()).equalsIgnoreCase(RegBean.getBussinessUserId()))
					{
						finalmovebizUsers.add(dto1);
					}
				}
				//setting move to list
				RegBean.setMoveBizUsers(finalmovebizUsers);
			}
			if(movebizUsers.isEmpty()){
				ArrayList moveUsers=new ArrayList();
				RegistrationOfAllDTO regDTO = new RegistrationOfAllDTO();
				regDTO.setBussinessUserId("");
				moveUsers.add(regDTO);
				RegBean.setMoveBizUsers(moveUsers);
			}
			logger.debug("Exiting reset() method of RegistrationOfAllAction.");
			return mapping.findForward(Constants.Profile);
		}catch (Exception e) {
			logger.error(Constants.EXCEPTION + e.getMessage());
			logger.info(" Exception occured while reseting location values by "+userBean.getLoginId());
			errors.add("There is some problem in application, please contact administrator.", new ActionError("There is some problem in application, please contact administrator."));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
			}
			return mapping.findForward(Constants.ERROR);
		}
	}*/
}
