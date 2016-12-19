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
package com.ibm.dp.actions;

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

import com.ibm.dp.beans.LocationDataBean;
import com.ibm.dp.beans.UserDetailsBean;
import com.ibm.dp.common.USSDConstants;
import com.ibm.dp.common.USSDErrorCodes;
import com.ibm.dp.dao.LocationDataDAO;
import com.ibm.dp.dao.RegistrationOfAllUsersDAO;
import com.ibm.dp.dto.DPCreateAccountDto;
import com.ibm.dp.dto.LocationDataDTO;
import com.ibm.dp.exception.DAOException;
import com.ibm.dp.service.DPCreateAccountService;
import com.ibm.dp.service.impl.DPCreateAccountServiceImpl;
import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOUser;
import com.ibm.utilities.Pagination;
import com.ibm.utilities.PaginationUtils;
import com.ibm.utilities.USSDCommonUtility;
import com.ibm.utilities.ValidatorUtility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;



/*********************************************************************************
* This class help in creating , viweing and edit location details
* 
* 
* @author Pragati
* @version 1.0
********************************************************************************************/
public class LocationDataAction extends DispatchAction {

	private static Logger logger = Logger.getLogger(LocationDataAction.class);

	

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
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
		
		HttpSession session = request.getSession();
		LocationDataDAO locDAO = new LocationDataDAO();
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		//UserDetailsBean userDetailsBean = null;//(UserDetailsBean) session
				//.getAttribute(USSDConstants.USER_INFO);
//		String userLoginId = (userDetailsBean != null) ? userDetailsBean
//				.getLoginId()
//				+ ": " : "";
		ArrayList circleList = new ArrayList();
		int noofpages = 0;
		String circleCode=null;
		ArrayList locType= new ArrayList();
		try {
			// Get Values.
			if (userSessionContext.getCircleId() == 0) {
				circleList = accountService.getAllCircles();
				viewLocBean.setUserRole(HBOConstants.ROLE_MOBILITYADMIN);
			} 
			else {
				DPCreateAccountDto accountDto = accountService.getAccount(hboUser.getId());
				LabelValueBean lvBean = new LabelValueBean(hboUser
						.getCircleId(), hboUser.getCircleId()+"-" + accountDto.getCircleName());
				circleList.add(lvBean);
				viewLocBean.setUserRole(HBOConstants.ROLE_CIRCLEADMIN);
			}
			viewLocBean.setCircleList(circleList);
			locType = locDAO.getLocationMstrList(USSDConstants.Admin);
			viewLocBean.setLocationList(locType);
			if(viewLocBean.getUserRole()==HBOConstants.ROLE_MOBILITYADMIN)
			{
				if(viewLocBean.getCircleCode() != null)
				{
					circleCode=viewLocBean.getCircleCode();
				}
			}
			else if(viewLocBean.getUserRole()==USSDConstants.CIRCLE_ADMIN)
			{
				circleCode=hboUser.getCircleId();
				
			}
			if (circleCode!= null) {
				
				if(viewLocBean.getLocMstrId()==USSDConstants.Zone){
				
					noofpages = locDAO.countLocationList(
						viewLocBean.getCircleCode(),USSDConstants.Zone);
					request.setAttribute("PAGES", new Integer(noofpages));
					Pagination pagination = PaginationUtils
						.setPaginationinRequest(request);
					ArrayList pageZoneList = locDAO
						.getLocationList(viewLocBean.getCircleCode(), USSDConstants.Zone,
						USSDConstants.PAGE_TRUE, pagination.getLowerBound(),
						pagination.getUpperBound());
					
					if(!pageZoneList.isEmpty()){
						viewLocBean.setShowLocationList(pageZoneList);
					}
					else {
						errors.add("Location_ERROR_MSG_Zone", new ActionError(
							"Location_ERROR_MSG_Zone"));									
					}
		
				}
				else if(viewLocBean.getLocMstrId()==USSDConstants.City){	
					/** Setting Values for zonal User only **/
					if(userSessionContext.getAccountLevelName().equalsIgnoreCase(HBOConstants.ACCOUNT_LEVEL_ZBM)
						|| userSessionContext.getAccountLevelName().equalsIgnoreCase(HBOConstants.ACCOUNT_LEVEL_ZSM)){
					ArrayList locationList= new ArrayList();
						locDTO.setLocationDataName(userSessionContext.getAccountZoneName());
						locDTO.setLocDataId(userSessionContext.getAccountZoneId());
						locationList.add(locDTO);
						viewLocBean.setZoneList(locationList);
					}
					else{
					/** getting city list for drop down display* */
					ArrayList zoneList = locDAO.getLocationList(circleCode,USSDConstants.Zone, USSDConstants.PAGE_FALSE,
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
						noofpages = locDAO.countLocationList(viewLocBean.getZoneName(), USSDConstants.City);
	
						request.setAttribute("PAGES", new Integer(noofpages));
						Pagination pagination = PaginationUtils
								.setPaginationinRequest(request);
	
						ArrayList pageCityList = locDAO.getLocationList(
								viewLocBean.getZoneName(), USSDConstants.City,USSDConstants.PAGE_TRUE,
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
			//logger.info(userLoginId + " View Location Data Class Inititalized");			
			
		} catch (Exception de) {
			de.printStackTrace();
			logger.error(USSDConstants.getLogMessage("init", getClass().getName()));
			logger.error("Exception cause :" + de.getMessage());
			errors.clear();
			errors.add(USSDConstants.GENERAL_ERROR, new ActionError(
					USSDConstants.GENERAL_ERROR));
			//logger.info(new StringBuffer(100).append(userLoginId).append(
					//"View Location Data Class Inititalization Error - ")
				//	.append(de.getMessage()));
			return mapping.findForward(USSDConstants.ERROR);
		}
		finally {
			//logger.info(userLoginId + "is exiting on change of View-Edit  Location page... ");
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
		
		ActionErrors errors = new ActionErrors();
		LocationDataBean viewLocBean = (LocationDataBean) form;
		HttpSession session = request.getSession(true);
		LocationDataDAO locDAO = new LocationDataDAO();
		UserDetailsBean userDetailsBean = (UserDetailsBean) session
				.getAttribute(USSDConstants.USER_INFO);
		String userLoginId = (userDetailsBean != null) ? userDetailsBean.getLoginId()
				+ ": " : "";
		ArrayList circleList = new ArrayList();
		
		try {
			if(viewLocBean.getCircleCode()!= null){
				viewLocBean.setCircleCode(null);
			}
			errors.clear();
			if (userDetailsBean.getCircleId() == null) {
				circleList = USSDCommonUtility.getCircleList();
				viewLocBean.setUserRole(USSDConstants.SUPER_ADMIN);
			} else {
				LabelValueBean lvBean = new LabelValueBean(userDetailsBean
						.getCircleId(), userDetailsBean.getCircleName());
				circleList.add(lvBean);
				viewLocBean.setUserRole(USSDConstants.CIRCLE_ADMIN);
			}
			
			viewLocBean.setCircleList(circleList);
			viewLocBean.setLocationList(locDAO.getLocationMstrList(USSDConstants.Admin));
			logger.debug("circle list and location list is set for view/Edit location");
		}catch(Exception e){
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("initViewLoc");
		}
		finally{
			logger.info("user -" + userLoginId + " is exiting viewLocBean of location page...");
		}
		return mapping.findForward("initViewLoc");
	}
	

	/**
	 * This method initEditLocation() takes location data id for editing the
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
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		//UserDetailsBean userDetailsBean = (UserDetailsBean) session
		//		.getAttribute(USSDConstants.USER_INFO);
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
		String userLoginId = (userSessionContext != null) ? userSessionContext
				.getLoginName()
				+ ": " : "";
		try {
			if (userSessionContext.getCircleId() == 0) {
				circleList = accountService.getAllCircles();
				editLocBean.setUserRole(HBOConstants.ROLE_MOBILITYADMIN);
			} else {
				DPCreateAccountDto accountDto = accountService.getAccount(hboUser.getId());
				LabelValueBean lvBean = new LabelValueBean(hboUser
						.getCircleId(), accountDto.getCircleName());
				circleList.add(lvBean);
				editLocBean.setUserRole(USSDConstants.CIRCLE_ADMIN);
			}

			if (editLocBean.getLocDataId() != 0) {
				locDetailsDTO = locDAO.getLocationDetails(editLocBean
						.getLocDataId(),editLocBean.getLocMstrId());

				editLocBean.setCircleList(circleList);
				editLocBean.setLocationList(locDAO.getLocationMstrList(USSDConstants.Admin));
				editLocBean.setCircleCode(editLocBean.getCircleCode());
				editLocBean.setLocationDataName(locDetailsDTO.getLocationName());
				editLocBean
						.setOldLocationDataName(locDetailsDTO.getLocationName());
				editLocBean.setStatus(locDetailsDTO.getStatus());
				editLocBean.setOldStatus(locDetailsDTO.getStatus());
				editLocBean.setLocMstrId(editLocBean.getLocMstrId());
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
			session.setAttribute("statusFlag", locDetailsDTO.getStatus());
			editLocBean.setMoveLocList(finalMoveLocList);
			editLocBean.setLocMasterIdHidden(editLocBean.getLocMstrId());
		}  catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("EditLocation");
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
		UserSessionContext userSessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser hboUser = new HBOUser(userSessionContext);
		DPCreateAccountService accountService = new DPCreateAccountServiceImpl();
		String userLoginId = (userSessionContext != null) ? userSessionContext
				.getLoginName()
				+ ": " : "";
		//UserDetailsBean userDetailsBean = (UserDetailsBean) session
		//		.getAttribute(USSDConstants.USER_INFO);
//		String userLoginId = (userDetailsBean != null) ? userDetailsBean
//				.getLoginId()
//				+ ": " : "";
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
			locDetailsDTO.setModifiedBy(userSessionContext.getId()+"");
			locDetailsDTO.setOldStatus(editLocBean.getOldStatus());
		}

		try {
			if (userSessionContext.getCircleId() == 0) {
				circleList = accountService.getAllCircles();
				editLocBean.setUserRole(HBOConstants.ROLE_MOBILITYADMIN);
			} else {DPCreateAccountDto accountDto = accountService.getAccount(hboUser.getId());
				LabelValueBean lvBean = new LabelValueBean(hboUser
					.getCircleId(), hboUser.getCircleId()+"-" + accountDto.getCircleName());
				circleList.add(lvBean);
				editLocBean.setUserRole(USSDConstants.CIRCLE_ADMIN);
			}

			editLocBean.setCircleList(circleList);
			editLocBean.setLocationList(locDAO.getLocationMstrList(USSDConstants.Admin));
			//editLocBean.setLocDataId(editLocBean.getLocDataId());
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
								.getLocDataId(),editLocBean.getLocMasterIdHidden());
	
						editLocBean.setCircleList(circleList);
						editLocBean.setLocationList(locDAO.getLocationMstrList(USSDConstants.Admin));
						editLocBean.setCircleCode(editLocBean.getCircleCode());
						editLocBean.setLocationDataName(locDetailsDTO
								.getLocationName());
						editLocBean.setStatus(locDetailsDTO.getStatus());
					}
					return mapping.findForward("EditLocation");	
				} 
			}
				
			
			/** moving sub locations and users to different location **/
			if(editLocBean.getMoveToLocation()!=-1 ){
			
				//list of sub-locations
				locDataList=locDAO.getHierarchyLocDataId(editLocBean.getLocDataId(),editLocBean.getStatus(),editLocBean.getLocMasterIdHidden());
				
				//list of sub-locations for new location (under which we have to move the sub-locations of our original location.)
				locDataList1=locDAO.getHierarchyLocDataId(editLocBean.getMoveToLocation(),editLocBean.getStatus(),editLocBean.getLocMasterIdHidden());
				
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
						updateLocationList=locDAO.updateParentIdOfChildHierarchy(editLocBean.getMoveToLocation(), editLocBean.getLocDataId(),editLocBean.getLocMasterIdHidden());
					}
					//updating location of users only if its successful in locations list
					if(updateLocationList>0){
						
						//putting location data id in an arraylist because our method accepts an arraylist.
						//ArrayList locList = new ArrayList();
						//locList.add(locDetailsDTO);
						
						//list of business users that belong to that location 
						//bizUserList= regDAO.getBizUserListInLocList(locList);
						
						//if(!bizUserList.isEmpty()){
							
							//updating LOC_ID's
							updatebizuserList= 0;//regDAO.updateLocIdOfBizUserList(editLocBean.getLocDataId(), editLocBean.getMoveToLocation(),editLocBean.getLocMasterIdHidden());
						//}
					}
				}else{
					errors.add("DuplicateName",new ActionError("ErrorKey",
					"Sub-Locations cannot be moved because duplicate names for sub-locations exists, but Details of "));
				}
			}
			/** Upadate location details only when the move operation is successfull or not required **/
			if(updatebizuserList>0 || USSDConstants.ACTIVE.equalsIgnoreCase(locDetailsDTO.getStatus())||USSDConstants.INACTIVE.equalsIgnoreCase(locDetailsDTO.getStatus()))
			{
				chkStatus = locDAO.updateLocationDetails(locDetailsDTO,editLocBean.getLocMasterIdHidden());
			}
			
			//giving the appropriate mesaage
			if(chkStatus>0)
			{
				errors.add(USSDConstants.SUCCESSUPDATELOCATION, new ActionError(
						USSDConstants.SUCCESSUPDATELOCATION));
				saveErrors(request, errors);
				return mapping.findForward(USSDConstants.SUCCESS);
			}else{
				errors.add(USSDConstants.ERRORUPDATELOCATION, new ActionError(
						USSDConstants.ERRORUPDATELOCATION));
				saveErrors(request, errors);
				return mapping.findForward("initViewLoc");
			}
			
		} 
//		catch (DAOException de) {
//
//			if (USSDErrorCodes.SQLEXCEPTION.equals(de.getMessage())) {
//				errors.add("Common.MSG_SQL_EXCEPTION", new ActionError(
//						"Common.MSG_SQL_EXCEPTION"));
//			} else if (USSDErrorCodes.ServiceClass.SQLDUPEXCEPTION.equals(de
//					.getMessage())) {
//				errors.add("ServiceClass.DUPLICATE_RECORD", new ActionError(
//						"ServiceClass.DUPLICATE_RECORD"));
//			} else if (USSDErrorCodes.DATABASEEXCEPTION.equals(de.getMessage())) {
//				errors.add("Common.MSG_DATABASE_EXCEPTION", new ActionError(
//						"Common.MSG_DATABASE_EXCEPTION"));
//			} else if (USSDErrorCodes.SYSTEMEXCEPTION.equals(de.getMessage())) {
//				errors.add("Common.MSG_APPLICATION_EXCEPTION", new ActionError(
//						"Common.MSG_APPLICATION_EXCEPTION"));
//			}
//			logger.error(new StringBuffer(100).append(userLoginId).append(
//					"saveEditLocation Error - ").append(de.getMessage()));
//			saveErrors(request, errors);
//		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.info("EXCEPTION OCCURED ::  "+e.getMessage());
			errors.add("errors",new ActionError(e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward("SUCCESS");
		} finally {
			logger.info(userLoginId + " Exiting saveEditLocation()... ");
		}
		
	}

}
