/**************************************************************************
 * File     : LocationDataAction.java
 * Author   : Pragati
 * Created  : oct 6, 2008
 * Modified : oct 6, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

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
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.LabelValueBean;
import com.ibm.virtualization.ussdactivationweb.beans.LocationDataBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.LocationDataDAO;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.ValidatorUtility;


/*********************************************************************************
* This class help in creating , viweing and edit location details
* 
* 
* @author Pragati
* @version 1.0
********************************************************************************************/
public class LocationDataAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(ServiceClassAction.class
			.getName());

	
	/**	This method is used for initializing Creating of Location and retrieves
	 * circle list for logged in user using getCirclesForUser() method.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering initLocation() of craete location page ... ");
		HttpSession session = request.getSession();
		ActionErrors errors = (ActionErrors) session.getAttribute("errors");
		LocationDataDTO locDTO= new LocationDataDTO();
		if (errors != null) {
			saveErrors(request, errors);
			session.removeAttribute("errors");
		} else {
			errors = new ActionErrors();
		}
		LocationDataBean createLoc = (LocationDataBean) form;
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId()+ ": " : "";
		LocationDataDAO locDAO = new LocationDataDAO();
		ArrayList circleList = new ArrayList();
		ArrayList locType= new ArrayList();
		try {
			
			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				createLoc.setUserRole(Constants.SUPER_ADMIN);
				createLoc.setCircleList(circleList);
				request.setAttribute(Constants.CIRCLE_LIST, createLoc
						.getCircleList());
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleId()+"-" + userDetailsBean.getCircleName());
				circleList.add(lvBean);
			}
			/** setting mandatory values to populate the page **/
			createLoc.setCircleList(circleList);
			/** Setting Values for zonal User only **/
			if(userDetailsBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
				locType = locDAO.getLocationMstrList(Constants.zoneLogin);
			}
			else{
				locType = locDAO.getLocationMstrList(Constants.Admin);
			}
			createLoc.setLocationList(locType);
			
			/** getting zone list for drop down* */
			if (createLoc.getCircleCode() != null &&  !("-1").equalsIgnoreCase(createLoc.getCircleCode()))
			{
				if(createLoc.getLocMstrId() == Constants.City) {
					/** Setting Values for zonal User only **/
					if(userDetailsBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
					ArrayList locationList= new ArrayList();
						locDTO.setLocationDataName(userDetailsBean.getZoneName());
						locDTO.setLocDataId(userDetailsBean.getZoneCode());
						locationList.add(locDTO);
					createLoc.setZoneList(locationList);
					}
				else{
					ArrayList locationList = locDAO.getLocationList(createLoc
							.getCircleCode(), Constants.Zone, Constants.PAGE_FALSE,
							0, 0);
					if (!locationList.isEmpty()) {
						createLoc.setZoneList(locationList);
					} else {
						errors.add("Location_ERROR_MSG_Zone", new ActionError(
								"Location_ERROR_MSG_Zone"));
					}
				}
				createLoc.setZoneName("-1");
			}
			}
		
		}catch (DAOException de) {

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
		finally{
			logger.info("Exiting initLocation() og location page... ");
		}
		if(userDetailsBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
			locType = locDAO.getLocationMstrList(Constants.zoneLogin);
		}
		else{
			locType = locDAO.getLocationMstrList(Constants.Admin);
		}
		createLoc.setLocationList(locType);
		
		saveErrors(request, errors);
		return mapping.findForward("initLocation");
	}

	/**
	 *  
	 
	 */
	/**	This method is used for Creation of Location data using LocationDataDTO
	 * locDTO method.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering createLocation() of location data action... ");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		LocationDataBean createLocBean = (LocationDataBean) form;
		LocationDataDTO locDTO = new LocationDataDTO();
		LocationDataDAO locDAO = new LocationDataDAO();
		ArrayList circleList = new ArrayList();
		boolean specChar;
	//	boolean alphsNumChar;
		if (errors != null) {
			saveErrors(request, errors);
			session.removeAttribute("errors");
		} else {
			errors = new ActionErrors();
		}
		
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean
				.getLoginId()
				+ ": " : "";
		/** Server side validation* */
		specChar = ValidatorUtility.isJunkFree(createLocBean
				.getLocationDataName());
		// alphsNumChar =
		// ValidatorUtility.isAlphaNumeric(createLocBean.getLocationDataName());

		if (specChar == false) {
			errors.add("Location_SPECIAL_CHARACTER", new ActionError(
					"Location_SPECIAL_CHARACTER"));
			saveErrors(request, errors);
			session.setAttribute("errors", errors);
			return initLocation(mapping, form, request, response);
		}
		
		try {

			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				createLocBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleId()+"-" + userDetailsBean.getCircleName());
				circleList.add(lvBean);
			}
			/** setting mandatory values to populate the page **/
			createLocBean.setCircleList(circleList);
			createLocBean.setLocationList(locDAO.getLocationMstrList(Constants.Admin));

			if (createLocBean.getCircleCode() == null 
					||  ("-1").equalsIgnoreCase(createLocBean.getCircleCode())) {
				errors.add("CircleCode_NotFound", new ActionError(
						"CircleCode_NotFound"));
				saveErrors(request, errors);
				session.setAttribute("errors", errors);
				return initLocation(mapping, form, request, response);
			}
			if (createLocBean.getLocMstrId() == 0
					|| (createLocBean.getLocMstrId()==-1)) {
				errors.add("Location_LocationType_NotFound", new ActionError(
						"Location_LocationType_NotFound"));
				saveErrors(request, errors);
				session.setAttribute("errors", errors);
				return initLocation(mapping, form, request, response);
			}

			if (createLocBean.getLocationDataName() == null
					|| (createLocBean.getLocationDataName().equals(""))) {
				errors.add("LocationName_NotFound", new ActionError(
						"LocationName_NotFound"));
				saveErrors(request, errors);
				session.setAttribute("errors", errors);
				return initLocation(mapping, form, request, response);
			}

			/** Creating City* */
			if (createLocBean.getLocMstrId() == Constants.City) {

				if (createLocBean.getZoneName() != null 
							&& ! ("-1").equalsIgnoreCase(createLocBean.getZoneName())) {
						
					locDTO.setLocationName(createLocBean.getLocationDataName().trim());
					locDTO.setStatus(String.valueOf(Constants.Active));
					locDTO.setLocMstrId(Constants.City);
					locDTO.setParentId(createLocBean.getZoneName());
					locDTO.setCreatedBy(userDetailsBean.getLoginId());
	
					/** Check for duplicate Location Name in a City * */
					if (createLocBean.getLocationDataName().trim() != "" &&
							createLocBean.getLocationDataName() != null ) {
						locDTO.setLocationName(createLocBean.getLocationDataName().trim());
						int rowCount = locDAO.validateLocationName(locDTO);
						if (rowCount > 0) {
							errors.add("Location.DUPLICATE_LOC_NAME",
									new ActionError("Location.DUPLICATE_LOC_NAME"));
							saveErrors(request, errors);
							session.setAttribute("errors", errors);
							return initLocation(mapping, form, request, response);
						}else{
					/** save the location data in the corresponding table * */
					locDAO.createLocation(locDTO);
					errors.add("Location_CITY.CREATED_SUCCESSFULLY",
							new ActionError("Location_CITY.CREATED_SUCCESSFULLY"));
						}
					}else{
						
						logger.debug("City Name is blank");
						/** location name is blank**/
						errors.add("Location.BLANK_LOC_NAME",
								new ActionError("Location.BLANK_LOC_NAME"));
						saveErrors(request, errors);
						session.setAttribute("errors", errors);
						
						return initLocation(mapping, form, request,
								response);
					}
				}else{
					/** location name is blank**/
					logger.debug("Zone Name is blank");
					errors.add("Location.BLANK_LOC_NAME",
							new ActionError("Location.BLANK_LOC_NAME"));
					saveErrors(request, errors);
					session.setAttribute("errors", errors);
					return initLocation(mapping, form, request, response);
				}
			}

			/** Creating Zone * */
			else if (createLocBean.getLocMstrId() == Constants.Zone ) {

				// setting values to DTO
				locDTO.setLocationName(createLocBean.getLocationDataName().trim());
				locDTO.setStatus(String.valueOf(Constants.Active));
				locDTO.setLocMstrId(Constants.Zone);
				locDTO.setParentId(String.valueOf(createLocBean
						.getCircleCode()));
				locDTO.setCreatedBy(userDetailsBean.getLoginId());

				/** Check for duplicate Location Name in a Zone * */
				if (createLocBean.getLocationDataName() != null && 
						createLocBean.getLocationDataName().trim()!="") 
				{
					locDTO.setLocationName(createLocBean
							.getLocationDataName().trim());
					locDTO.setLocDataId(createLocBean.getLocDataId());
					int rowCount = locDAO.validateLocationName(locDTO);

					if (rowCount > 0) {
						errors.add("Location.DUPLICATE_LOC_NAME",
								new ActionError(
										"Location.DUPLICATE_LOC_NAME"));
						saveErrors(request, errors);
						session.setAttribute("errors", errors);
						return initLocation(mapping, form, request,
								response);
					}else{
						/** save the location data in the corresponding table * */
						locDAO.createLocation(locDTO);
						errors.add("Location_ZONE.CREATED_SUCCESSFULLY",
						new ActionError("Location_ZONE.CREATED_SUCCESSFULLY"));
					}
				}else{
					/** location name is blank**/
					logger.debug("Zone Name is blank");
					errors.add("Location.BLANK_LOC_NAME",
							new ActionError("Location.BLANK_LOC_NAME"));
					saveErrors(request, errors);
					session.setAttribute("errors", errors);
					return initLocation(mapping, form, request, response);
				}

			}

		} catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.Location.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("Location.DUPLICATE_LOC_NAME", new ActionError(
						"Location.DUPLICATE_LOC_NAME"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
			logger.error(new StringBuffer(100).append(userLoginId).append(
					"Create Location Data Error - ").append(de.getMessage()));

		} finally {
			logger.info( userLoginId + "Exiting createLocation() of location page... ");
		}

		saveErrors(request, errors);

		createLocBean.setCircleCode(null);
		createLocBean.setCircleCode("-1");
		createLocBean.setLocMstrId(-1);
		createLocBean.setCityName(null);
		createLocBean.setZoneName(null);

		return mapping.findForward("initLocation");
	}

	
	/**
	 * This method initView() loads the view Edit page and show the location selected 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.debug("Entering initView()... ");
		ActionErrors errors = new ActionErrors();
		
		LocationDataDTO locDTO= new LocationDataDTO();
		LocationDataBean viewLocBean = (LocationDataBean) form;
		HttpSession session = request.getSession();
		LocationDataDAO locDAO = new LocationDataDAO();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean
				.getLoginId()
				+ ": " : "";
		ArrayList circleList = new ArrayList();
		int noofpages = 0;
		String circleCode=null;
		ArrayList locType= new ArrayList();
		try {
				
			
			// Get Values.
			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				viewLocBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleId()+"-" + userDetailsBean.getCircleName());
				circleList.add(lvBean);
				viewLocBean.setUserRole(Constants.CIRCLE_ADMIN);
			}
			viewLocBean.setCircleList(circleList);
			/** Setting Values for zonal User only **/
			if(userDetailsBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
				locType = locDAO.getLocationMstrList(Constants.zoneLogin);
			}
			else{
				locType = locDAO.getLocationMstrList(Constants.Admin);
			}
				viewLocBean.setLocationList(locType);
			
					
		
			if(viewLocBean.getUserRole()==Constants.SUPER_ADMIN)
			{
				if(viewLocBean.getCircleCode() != null)
				{
					circleCode=viewLocBean.getCircleCode();
				}
			}
			else if(viewLocBean.getUserRole()==Constants.CIRCLE_ADMIN)
			{
				circleCode=userDetailsBean.getCircleId();
				
			}
			if (circleCode!= null) {
				
				if(viewLocBean.getLocMstrId()==Constants.Zone){
				
					noofpages = locDAO.countLocationList(
						viewLocBean.getCircleCode(),Constants.Zone);
					request.setAttribute("PAGES", new Integer(noofpages));
					Pagination pagination = PaginationUtils
						.setPaginationinRequest(request);
					ArrayList pageZoneList = locDAO
						.getLocationList(viewLocBean.getCircleCode(), Constants.Zone,
						Constants.PAGE_TRUE, pagination.getLowerBound(),
						pagination.getUpperBound());
					
					if(!pageZoneList.isEmpty()){
						viewLocBean.setShowLocationList(pageZoneList);
					}
					else {
						errors.add("Location_ERROR_MSG_Zone", new ActionError(
							"Location_ERROR_MSG_Zone"));									
					}
		
				}
				else if(viewLocBean.getLocMstrId()==Constants.City){	
					/** Setting Values for zonal User only **/
					if(userDetailsBean.getUserType().equalsIgnoreCase(Constants.zoneLogin)){
					ArrayList locationList= new ArrayList();
						locDTO.setLocationDataName(userDetailsBean.getZoneName());
						locDTO.setLocDataId(userDetailsBean.getZoneCode());
						locationList.add(locDTO);
						viewLocBean.setZoneList(locationList);
					}
					else{
					/** getting city list for drop down display* */
					ArrayList zoneList = locDAO.getLocationList(circleCode,Constants.Zone, Constants.PAGE_FALSE,
							0, 0);					
					if(!zoneList.isEmpty()){
						viewLocBean.setZoneList(zoneList);
						
					}else{
						errors.add("Location_ERROR_MSG_City", new ActionError(
						"Location_ERROR_MSG_City"));						
					}
					}
					/** Getting City paginated List for display **/
					//city paginated list
					if(viewLocBean.getZoneName()!=null && !("-1").equalsIgnoreCase(viewLocBean.getZoneName())){
						noofpages = locDAO.countLocationList(viewLocBean.getZoneName(), Constants.City);
	
						request.setAttribute("PAGES", new Integer(noofpages));
						Pagination pagination = PaginationUtils
								.setPaginationinRequest(request);
	
						ArrayList pageCityList = locDAO.getLocationList(
								viewLocBean.getZoneName(), Constants.City,Constants.PAGE_TRUE,
								pagination.getLowerBound(), pagination
										.getUpperBound());
						if(!pageCityList.isEmpty()){
							viewLocBean.setShowLocationList(pageCityList);
						}
						else{
							errors.add("Location_ERROR_MSG_City", new ActionError(
							"Location_ERROR_MSG_City"));
						}
					}
				}

			} 
			logger.info(userLoginId + " View Location Data Class Inititalized");			
			
		} catch (Exception de) {
			logger.error("Exception cause :" + de.getMessage());
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			logger.info(new StringBuffer(100).append(userLoginId).append(
					"View Location Data Class Inititalization Error - ")
					.append(de.getMessage()));
			return mapping.findForward(Constants.ERROR);
		}
		finally {
			logger.info(userLoginId + "is exiting on change of View-Edit  Location page... ");
		}
		saveErrors(request, errors);
		return mapping.findForward("initViewLoc");

	}
	/**	This method viewIntialized() method is used to see View/edit location page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward viewIntialized(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		logger.info("Entering viewIntialized() for location page... ");
		

		LocationDataBean viewLocBean = (LocationDataBean) form;
		HttpSession session = request.getSession(true);
		LocationDataDAO locDAO = new LocationDataDAO();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId()
				+ ": " : "";
		ArrayList circleList = new ArrayList();
		ActionErrors errors = new ActionErrors();
		try {
			if(viewLocBean.getCircleCode()!= null){
				viewLocBean.setCircleCode(null);
			}
			errors.clear();
			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				viewLocBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleName());
				circleList.add(lvBean);
				viewLocBean.setUserRole(Constants.CIRCLE_ADMIN);
			}
			
			viewLocBean.setCircleList(circleList);
			viewLocBean.setLocationList(locDAO.getLocationMstrList(Constants.Admin));
			logger.debug("circle list and location list is set for view/Edit location");
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		finally{
			logger.info("user -" + userLoginId + " is exiting viewLocBean of location page...");
		}
		return mapping.findForward("initViewLoc");
	}
	

	/**
	 * This method initEditLocation() takes location data id for editting the
	 * field
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ActionForward initEditLocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("Entering initEditLocation() of location page... ");

		LocationDataBean editLocBean = (LocationDataBean) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();

		LocationDataDTO locDetailsDTO = new LocationDataDTO();
		LocationDataDAO locDAO = new LocationDataDAO();
		ArrayList circleList = new ArrayList();
		ArrayList moveLocList = new ArrayList();
		
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);

		String userLoginId = (userDetailsBean != null) ? userDetailsBean
				.getLoginId()
				+ ": " : "";
		try {
			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				editLocBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleName());
				circleList.add(lvBean);
				editLocBean.setUserRole(Constants.CIRCLE_ADMIN);
			}

			if (editLocBean.getLocDataId() != 0) {

				locDetailsDTO = locDAO.getLocationDetails(editLocBean
						.getLocDataId());

				editLocBean.setCircleList(circleList);
				editLocBean.setLocationList(locDAO.getLocationMstrList(Constants.Admin));
				editLocBean.setCircleCode(editLocBean.getCircleCode());
				editLocBean.setLocationDataName(locDetailsDTO.getLocationName());
				editLocBean
						.setOldLocationDataName(locDetailsDTO.getLocationName());
				editLocBean.setStatus(locDetailsDTO.getStatus());
				editLocBean.setOldStatus(locDetailsDTO.getStatus());
				editLocBean.setLocMstrId(locDetailsDTO.getLocMstrId());
				editLocBean.setParentId(locDetailsDTO.getParentId());
			}
			
			/** list of locations to which the child locations of the base location can be moved **/
			
			moveLocList = locDAO.getLocationList(editLocBean.getParentId(), editLocBean.getLocMstrId(), 0, 0, 0);
			//removing the original location from the list
			
			ArrayList finalMoveLocList = new ArrayList();
			Iterator itr=moveLocList.iterator();
			while(itr.hasNext())
			{
				LocationDataDTO locDTO = (LocationDataDTO)itr.next();
				if(editLocBean.getLocDataId()!=locDTO.getLocDataId())
				{
					finalMoveLocList.add(locDTO);
				}
				
			}
			editLocBean.setMoveLocList(finalMoveLocList);
			
		} catch (DAOException de) {
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
			logger.error(new StringBuffer(100).append(userLoginId).append(
					"initEditLocation Inititalization Error - ").append(
					de.getMessage()));

		} catch (Exception e) {
			logger.error("Exception has occur in initEditLocation", e);
		}

		saveErrors(request, errors);
		logger.debug("Exiting initEditLocation()... ");
			logger.info(userLoginId + "Exiting initEditLocation()... ");
		return mapping.findForward("EditLocation");
	}

	/**
	 * This method saveEditLocation() updates the location details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ActionForward saveEditLocation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("Entering saveEditLocations() of location page... ");
		ActionErrors errors = new ActionErrors();

		LocationDataBean editLocBean = (LocationDataBean) form;
		LocationDataDTO locDetailsDTO = new LocationDataDTO();
		LocationDataDAO locDAO = new LocationDataDAO();
		ArrayList circleList = new ArrayList();
		ArrayList locDataList = new ArrayList();
		ArrayList locDataList1 = new ArrayList();
		HttpSession session = request.getSession();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(Constants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean
				.getLoginId()
				+ ": " : "";
		int chkStatus = 0;
		/**Server side validation**/
		boolean specChar;
		specChar = ValidatorUtility.isJunkFree(editLocBean
				.getLocationDataName().trim());
		
		// things related to businessUser Update
		ArrayList bizUserList = new ArrayList();
		RegistrationOfAllUsersDAO regDAO = new RegistrationOfAllUsersDAO();
		int updatebizuserList=1;
		int updateLocationList=1;
		
		if (specChar == false) {
			errors.add("Location_SPECIAL_CHARACTER", new ActionError(
					"Location_SPECIAL_CHARACTER"));
			saveErrors(request, errors);
			session.setAttribute("errors", errors);
			return initEditLocation(mapping, form, request, response);
		}
		/** Fetching Values from bean and putting it to DTO* */
		if (editLocBean.getLocDataId() != 0) {
			locDetailsDTO.setLocDataId(editLocBean.getLocDataId());
			locDetailsDTO.setCircleCode(editLocBean.getCircleCode());
			locDetailsDTO.setLocationName(editLocBean.getLocationDataName().trim());
			locDetailsDTO.setStatus(editLocBean.getStatus());
			locDetailsDTO.setModifiedBy(userDetailsBean.getLoginId());
			locDetailsDTO.setOldStatus(editLocBean.getOldStatus());
		}

		try {
			if (userDetailsBean.getCircleId() == null) {
				circleList = ViewEditCircleMasterDAOImpl.getCircleList();
				editLocBean.setUserRole(Constants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleName());
				circleList.add(lvBean);
				editLocBean.setUserRole(Constants.CIRCLE_ADMIN);
			}

			editLocBean.setCircleList(circleList);
			editLocBean.setLocationList(locDAO.getLocationMstrList(Constants.Admin));
			editLocBean.setLocDataId(editLocBean.getLocDataId());
			/** Check for duplicate Location Name * */
			if(!(editLocBean.getLocationDataName().trim()
					.equalsIgnoreCase(editLocBean.getOldLocationDataName().trim()))) {
				
				int rowCount = locDAO.validateLocationName(locDetailsDTO);	
				if (rowCount > 0) {
					errors.add("Location.DUPLICATE_LOC_NAME", new ActionError(
							"Location.DUPLICATE_LOC_NAME"));
					saveErrors(request, errors);
					session.setAttribute("errors", errors);
					if (editLocBean.getLocDataId() != 0) {
	
						locDetailsDTO = locDAO.getLocationDetails(editLocBean
								.getLocDataId());
	
						editLocBean.setCircleList(circleList);
						editLocBean.setLocationList(locDAO.getLocationMstrList(Constants.Admin));
						editLocBean.setCircleCode(editLocBean.getCircleCode());
						editLocBean.setLocationDataName(locDetailsDTO
								.getLocationName());
						editLocBean.setStatus(locDetailsDTO.getStatus());
					}
					return mapping.findForward("EditLocation");	
				} 
			}
				
			
			/** moving sub locations and users to different location **/
			if(editLocBean.getMoveToLocation()!=-1){
			
				//list of sub-locations
				locDataList=locDAO.getHierarchyLocDataId(editLocBean.getLocDataId(),editLocBean.getStatus());
				
				//list of sub-locations for new location (under which we have to move the sub-locations of our original location.)
				locDataList1=locDAO.getHierarchyLocDataId(editLocBean.getMoveToLocation(),editLocBean.getStatus());
				
				//compairing the two list for same name
				String sameName="false";
				
				if(!locDataList1.isEmpty() && !locDataList.isEmpty()){
					Iterator itr = locDataList.iterator();
					while(itr.hasNext())
					{
						LocationDataDTO DTO = (LocationDataDTO)itr.next();
						Iterator itr1 = locDataList1.iterator();
						while(itr1.hasNext())
						{
							LocationDataDTO DTO1 = (LocationDataDTO)itr1.next();
							if((DTO.getLocationDataName()).equalsIgnoreCase(DTO1.getLocationDataName())){
								sameName="true";
								//stopping update of the location  
								updatebizuserList=0;
								break;
							}
						}if(("true").equalsIgnoreCase(sameName)){
							break;
						}
					}
				}
				
				if(("false").equalsIgnoreCase(sameName))
				{
					if(!locDataList.isEmpty())
					{
						
						
						// updateing there parents with new location
						updateLocationList=locDAO.updateParentIdOfChildHierarchy(locDataList, editLocBean.getMoveToLocation());
					}
					//updating location of users only if its successful in locations list
					if(updateLocationList>0){
						
						//putting location data id in an arraylist because our method accepts an arraylist.
						ArrayList locList = new ArrayList();
						locList.add(locDetailsDTO);
						
						//list of business users that belong to that location 
						bizUserList= regDAO.getBizUserListInLocList(locList);
						
						if(!bizUserList.isEmpty()){
							
							//updating LOC_ID's
							updatebizuserList=regDAO.updateLocIdOfBizUserList(bizUserList, editLocBean.getMoveToLocation());
						}
					}
				}else{
					errors.add("DuplicateName",new ActionError("ErrorKey",
					"Sub-Locations cannot be moved because duplicate names for sub-locations exists, but Details of "));
				}
			}
			/** Upadate location details only when the move operation is successfull or not required **/
			if(updatebizuserList>0 || Constants.ACTIVE.equalsIgnoreCase(locDetailsDTO.getStatus()) )
			{
				chkStatus = locDAO.updateLocationDetails(locDetailsDTO);
			}
			
			//giving the appropriate mesaage
			if(chkStatus>0)
			{
				errors.add(Constants.SUCCESSUPDATELOCATION, new ActionError(
						Constants.SUCCESSUPDATELOCATION));
				saveErrors(request, errors);
				return mapping.findForward(Constants.SUCCESS);
			}else{
				errors.add(Constants.ERRORUPDATELOCATION, new ActionError(
						Constants.ERRORUPDATELOCATION));
				saveErrors(request, errors);
				return mapping.findForward("initViewLoc");
			}
			
			
//			if (chkStatus > 0) {
//				locDataList =	locDAO.getHierarchyLocDataId(editLocBean.getLocDataId(),editLocBean.getStatus());
//				
//				//if status of location status is changed to inactive
//				// and the status value should be changed.
//				// so this condition will be called only when status value is changed from Active to Inactive
//				if( !Constants.ACTIVE.equalsIgnoreCase(locDetailsDTO.getStatus()) && (locDetailsDTO.getStatus().equals(locDetailsDTO.getStatus())) )
//				{
//					//list of business users that belong to the location list
//					bizUserList= regDAO.getBizUserListInLocList(locDataList);
//					
//					// since the status of location is changed to InActive, all the business users related the location , will be deactivaed
//					updatebizuserList=regDAO.updateBizUserListToInactive(bizUserList,Constants.InActive);
//					if(updatebizuserList>0)
//					{
//						updateLocationList=locDAO.updateStatusHierarchy(locDataList , locDetailsDTO.getStatus());
//					}
//					if(updateLocationList==0 || updateLocationList<0 )
//					{
//						// since the locations were ot able to update, so roll backing the status of the users also
//					updatebizuserList=regDAO.updateBizUserListToInactive(bizUserList,Constants.ActiveState);
//					}
//					
//				}
//				else{
//					
//					updateLocationList=locDAO.updateStatusHierarchy(locDataList , locDetailsDTO.getStatus());
//				}
//				
//				ActionMessages msg = new ActionMessages();
//				ActionMessage message = new ActionMessage("Saved");
//				msg.add(ActionMessages.GLOBAL_MESSAGE, message);
//				saveMessages(request, msg);
//				logger.info(new StringBuffer(100)
//				.append(userLoginId)
//				.append(locDetailsDTO.getLocationName())
//				.append(" location name .MODIFIED_SUCCESSFULLY for Edit location ")
//				.toString());
//				return mapping.findForward(Constants.SUCCESS);
//
//			} else {
//				logger.debug("location is not updated");
//			}
			
		} catch (DAOException de) {

			if (ErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
						"Common.MSG_SQL_EXCEPTION"));
			} else if (ErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
					.getMessage())) {
				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
						"ServiceClass.DUPLICATE_RECORD"));
			} else if (ErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
						"Common.MSG_DATABASE_EXCEPTION"));
			} else if (ErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
						"Common.MSG_APPLICATION_EXCEPTION"));
			}
			logger.error(new StringBuffer(100).append(userLoginId).append(
					"saveEditLocation Error - ").append(de.getMessage()));
			saveErrors(request, errors);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception has occur in saveEditLocation.", e);
		} finally {
			logger.info(userLoginId + " Exiting saveEditLocation()... ");
		}
		
		saveErrors(request, errors);
		return mapping.findForward("initViewLoc");
	}

}
