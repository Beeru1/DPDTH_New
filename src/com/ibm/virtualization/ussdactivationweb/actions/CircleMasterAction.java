/**************************************************************************
 * File     : CircleMasterAction.java
 * Author   : Banita
 * Created  : Sep 8, 2008
 * Modified : Sep 8, 2008
 * Version  : 0.1
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.util.ArrayList;
import java.util.Iterator;

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

import com.ibm.virtualization.ussdactivationweb.beans.CircleMasterBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
import com.ibm.virtualization.ussdactivationweb.dto.CircleMasterDTO;
import com.ibm.virtualization.ussdactivationweb.dto.LocationDataDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.ErrorCodes;

/*******************************************************************************
 * This class handles the creation,update and search for a circle.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/
public class CircleMasterAction extends DispatchAction {

	private static final Logger logger = Logger
	.getLogger(CircleMasterAction.class);

	/**
	 * This method initializes the create circle interface. Initi the use case
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
	 * @return Object of ActionForward class
	 * @throws Exception
	 * 
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.debug("Entering init()... method of CircleMasterAction");
		ActionForward forward = null;
		ActionErrors errors = new ActionErrors();
		CircleMasterBean beanObj = (CircleMasterBean) form;
		ViewEditCircleMasterDAOImpl dao = null;
		try {
			UserDetailsBean userBean = (UserDetailsBean) (req.getSession())
			.getAttribute(Constants.USER_INFO);
			/**
			 * If the Session expires the UserBean becomes null else the success
			 * is returned to open the CreateCircle.jsp
			 */
			if (userBean == null) {

				errors.add(Constants.NAME, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(req, errors);
				forward = mapping.findForward(Constants.FAILURE1);
			} else {
				dao = new ViewEditCircleMasterDAOImpl();
				ArrayList hubList = dao.getHubList();
				if (hubList != null) {
					beanObj.setHubList(hubList);
					beanObj.setApefCheck(true);
					beanObj.setSimReqCheck(true);
				}
				logger.info(userBean.getLoginId() + " init of CircleMasterAction Initialized");
				forward = mapping.findForward("Success");
			}
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

			saveErrors(req, errors);
		} catch (Exception e) {
			logger.error("init Inititalization Error - " + e.getMessage());
			forward = mapping.findForward(Constants.ERROR);
		} finally {
			logger.debug("Exiting init().method of CircleMasterAction");
		}

		return forward;

	}

	/**
	 * This method is to insert details for circle creation.
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
	 * @return Object of ActionForward class
	 * @throws Exception
	 * 
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) {
		logger.debug("Entering insert() method of CircleMasterAction");
		ActionErrors errors = new ActionErrors();
		ViewEditCircleMasterDAOImpl dao = null;
		CircleMasterDTO dtoObj = null;
		ActionForward forward = null;
		try {
			UserDetailsBean userBean = (UserDetailsBean) (req.getSession())
			.getAttribute(Constants.USER_INFO);

			if (userBean == null) {
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(req, errors);
				forward = mapping.findForward(Constants.FAILURE1);
			} else {
				CircleMasterBean cirMBean = (CircleMasterBean) form;
				
				dao = new ViewEditCircleMasterDAOImpl();
				dtoObj = new CircleMasterDTO();
				try {
					dtoObj.setCreatedBy(userBean.getUserId());
					dtoObj.setUpdatedBy(userBean.getUserId());
					dtoObj.setHubCode(cirMBean.getHubCode());
					dtoObj.setCircleName(cirMBean.getCircleName().trim());
					dtoObj.setCircleCode(cirMBean.getCircleCode().trim());
					dtoObj.setWelcomeMsg(cirMBean.getWelcomeMsg());
					dtoObj.setReleaseTime(cirMBean.getReleaseTime());
					if (cirMBean.isMinsatCheck()) {
						dtoObj.setMinsatCheck("Y");
					} else {
						dtoObj.setMinsatCheck("N");
					}
					
					if (cirMBean.isApefCheck()) {
						dtoObj.setApefCheck("Y");
					} else {
						dtoObj.setApefCheck("N");
					}
					
					if(cirMBean.isSimReqCheck()){
						dtoObj.setSimReq("Y");
					}else{
						dtoObj.setSimReq("N");
					}

				if(cirMBean.getCircleCode()!= null || cirMBean.getCircleCode().equalsIgnoreCase("")){
					CircleMasterDTO circleDTO = dao.findByPrimaryKey(cirMBean
							.getCircleCode());
					if (circleDTO != null) {
						errors.add("CircleCodeExists", new ActionError(
						"CircleExists"));
						saveErrors(req, errors);
						dao = new ViewEditCircleMasterDAOImpl();
						req
						.setAttribute("circleName", cirMBean
								.getCircleName());
						ArrayList hubList = dao.getHubList();
						if (hubList != null) {
							cirMBean.setHubList(hubList);
						}
						logger.info(userBean.getLoginId()
								+ " Circle code already exist");
						return mapping.findForward("CircleExists");
					}
					}
				ArrayList hubList = dao.getHubList();
				if (hubList != null) {
					cirMBean.setHubList(hubList);
				}
					/**Server Side validation**/
					String circleCode = cirMBean.getCircleCode().trim();
					
					if(circleCode.equalsIgnoreCase(null)|| circleCode.equalsIgnoreCase("")){
						errors.add("Circle.circleCode.blank", new ActionError(
						"Circle.circleCode.blank"));
						saveErrors(req, errors);
						return mapping.findForward("CircleExists");
					}
					
					String circleName = cirMBean.getCircleName().trim();
					if(circleName.equalsIgnoreCase(null)|| circleName.equalsIgnoreCase("")){
						errors.add("Circle.circleName.blank", new ActionError(
						"Circle.circleName.blank"));
						saveErrors(req, errors);
						return mapping.findForward("CircleExists");
					}
					
					if (dao.findByCircleName(cirMBean.getCircleName()) == null) {
						dao.insert(dtoObj);
						ActionMessages msg = new ActionMessages();
						ActionMessage message = new ActionMessage("Saved");
						msg.add(ActionMessages.GLOBAL_MESSAGE, message);
						saveMessages(req, msg);
						logger.info(userBean.getLoginId() + " circle created");
						forward = mapping.findForward(Constants.SUCCESS);
					} else {
						errors.clear();
						errors.add("Circle.circleName.Exist", new ActionError(
								"Circle.circleName.Exist"));
						saveErrors(req, errors);
						dao = new ViewEditCircleMasterDAOImpl();
						ArrayList hubList1 = dao.getHubList();
						if (hubList1 != null) {
							cirMBean.setHubList(hubList);
						}
						logger.info(userBean.getLoginId()
								+ " Circle name already exist");
						forward = mapping.findForward("CircleExists");
					}
				} catch (DAOException excep) {
					errors.add("CircleCodeExists", new ActionError(
					"CircleExists"));
					saveErrors(req, errors);
					dao = new ViewEditCircleMasterDAOImpl();
					ArrayList hubList = dao.getHubList();
					if (hubList != null) {
						cirMBean.setHubList(hubList);
					}
					logger.info(userBean.getLoginId()
							+ " Circle code already exist");
					forward = mapping.findForward("CircleExists");
				}

			}

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

			saveErrors(req, errors);
		} catch (Exception e) {
			logger.error("insert Inititalization Error - " + e.getMessage());
			forward = mapping.findForward(Constants.ERROR);
		} finally {
			logger.debug("Exiting insert() method of CircleMasterAction ");
		}
		return forward;
	}

	/**
	 * This method is called when circle details needs to be updated.
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
	 * @return Object of ActionForward class
	 * @throws Exception
	 * 
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.debug("Entering update() method of CircleMasterAction ");
		CircleMasterBean beanObj = (CircleMasterBean) form;
		ViewEditCircleMasterDAOImpl dao = null;
		ActionForward forward = new ActionForward();
		CircleMasterDTO dtoObj = null;
		UserDetailsBean userBean = (UserDetailsBean) (req.getSession())
		.getAttribute("USER_INFO");
		ArrayList childListold =  new ArrayList();
		ArrayList childListnew =  new ArrayList();
		// things related to businessUser Update
		ArrayList bizUserList = new ArrayList();
		RegistrationOfAllUsersDAO regDAO = new RegistrationOfAllUsersDAO();
		int updatebizuserList=1;
		int updateLocationList=1;
		try {
			if (userBean == null) {
				ActionErrors errors = new ActionErrors();
				errors.add("name", new ActionError("Session Expired"));
				saveErrors(req, errors);
				forward = mapping.findForward("Failure");
			} else {
				dtoObj = new CircleMasterDTO();
				dao = new ViewEditCircleMasterDAOImpl();
				String UserId = userBean.getUserId();
				dtoObj.setCircleCode(beanObj.getCircleCode());
				dtoObj.setCircleName(beanObj.getCircleName());
				dtoObj.setStatus(beanObj.getCircleStatus());
				dtoObj.setUpdatedBy(UserId);
				dtoObj.setWelcomeMsg(beanObj.getWelcomeMsg());
				dtoObj.setReleaseTime(beanObj.getReleaseTime());
				if (beanObj.isMinsatCheck()) {
					dtoObj.setMinsatCheck("Y");
				} else {
					dtoObj.setMinsatCheck("N");
				}
				if (beanObj.isApefCheck()) {
					dtoObj.setApefCheck("Y");
				} else {
					dtoObj.setApefCheck("N");
				}
				if(beanObj.isSimReqCheck()){
					dtoObj.setSimReq("Y");
				}else{
					dtoObj.setSimReq("N");
				}


				ActionErrors errors = new ActionErrors();
				/**Server Side validation***/
				String circleName = beanObj.getCircleName().trim();
				if(circleName.equalsIgnoreCase(null)|| circleName.equalsIgnoreCase("")){
					errors.add("Circle.circleName.blank", new ActionError(
					"Circle.circleName.blank"));
					saveErrors(req, errors);
					return mapping.findForward("Success");
				}
				int ReturnStatus = 1;
				if ((beanObj.getOldcircleStatus().equalsIgnoreCase(
						beanObj.getCircleStatus()) && beanObj
						.getOldcircleName().equalsIgnoreCase(
								beanObj.getCircleName()))
								|| (!(beanObj.getOldcircleStatus()
										.equalsIgnoreCase(beanObj.getCircleStatus()))
										&& beanObj.getOldcircleName().equalsIgnoreCase(
												beanObj.getCircleName())
												|| ((beanObj.getOldcircleStatus()
														.equalsIgnoreCase(beanObj
																.getCircleStatus())) && !(beanObj
																		.getOldcircleName()
																		.equalsIgnoreCase(beanObj
																				.getCircleName()))) || !((beanObj
																						.getOldcircleStatus().equalsIgnoreCase(beanObj
																								.getCircleStatus())) && beanObj
																								.getOldcircleName().equalsIgnoreCase(
																										beanObj.getCircleName())))) {

					if (dao.isFound(dtoObj)) {
						ReturnStatus = 0;

					} else {
						
						
						if(!("-1").equalsIgnoreCase(beanObj.getMoveToCircle())){
							// list of sub-locations for original circle(which is being updated here)
							childListold = dao.circleChildList(beanObj.getCircleCode());
							
							//list of sub-locations of the circle which is being selected, under which the sub-locations will move
							childListnew = dao.circleChildList(beanObj.getMoveToCircle());
							
							//compairing the two list for same name
							String sameName="false";
							
							if(!childListnew.isEmpty() && !childListnew.isEmpty()){
								Iterator itr = childListold.iterator();
								while(itr.hasNext())
								{
									LocationDataDTO DTO = (LocationDataDTO)itr.next();
									Iterator itr1 = childListnew.iterator();
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
								if(!childListold.isEmpty())
								{
									// updateing there parents with new location
									updateLocationList=dao.updateParentOfChildList(beanObj.getCircleCode(),beanObj.getMoveToCircle());
								}
								//updating location of users only if its successful in locations list
								if(updateLocationList>0 || updateLocationList==0){
									
									//list of business users that belong to that circle 
									bizUserList= regDAO.getBizUserListInCircle(beanObj.getCircleCode());
									
									if(!bizUserList.isEmpty()){
										
										//updating Circles
										updatebizuserList=regDAO.updateCirclefBizUserList(bizUserList, beanObj.getMoveToCircle());
									}
								}
							}else{
								errors.add("DuplicateName",new ActionError("ErrorKey","Sub-Locations cannot be moved because duplicate names for sub-locations exsists, but Details of "));
							}
													
						}							
					}
					/** Upadate location details only when the move operation is successfull or not required **/
					if(updatebizuserList>=0 || Constants.ACTIVE.equalsIgnoreCase(beanObj.getCircleStatus()) )
					{
						ReturnStatus = dao.update(dtoObj, false);
					}
				} else {
					errors.clear();
					errors.add("circleNameAready", new ActionError(
							Constants.ERROR_KEY,
					"Circle already in active state."));
					saveErrors(req, errors);
					logger.info(userBean.getLoginId()
							+ " circle is already in active state");
					return mapping.findForward(Constants.SUCCESS);
				}
				if (ReturnStatus == 1) {
					//String childStatus = dao.circleChildUpdates(beanObj.getCircleCode(),Integer.parseInt(beanObj.getCircleStatus()));
					//if("childUpdated".equals(childStatus)){
					errors.add("circleUpdated", new ActionError(
							Constants.ERROR_KEY,
						"Circle is Updated Successfully."));
						saveErrors(req, errors);
						beanObj.setFunctionType("showAll");
						logger.info(userBean.getLoginId()
								+ " circle updated successfully");
						forward = mapping.findForward("ShowSucess");
					//}
				} else {
					errors.clear();
					errors.add("Circle.circleName.Exist", new ActionError(
							"Circle.circleName.Exist"));
					saveErrors(req, errors);
					logger.info(userBean.getLoginId()
							+ " Circle name already exist");
					forward = mapping.findForward("ShowSucess");
				}
			}
		} catch (DAOException de) {
			ActionErrors errors = new ActionErrors();
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

			saveErrors(req, errors);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		} finally {
			logger.debug("Exiting update() method of CircleMasterAction ");
		}
		return forward;
	}

	/**
	 * This method is called to fetch circle details.
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
	 * @return Object of ActionForward class
	 * @throws Exception
	 * 
	 */
	public ActionForward showDetails(ActionMapping mapObj, ActionForm frmObj,
			HttpServletRequest reqObj, HttpServletResponse resObj) {
		logger.debug("Entering showDetails() method of CircleMasterAction ");
		CircleMasterBean beanObj = null;
		ViewEditCircleMasterDAOImpl dao = null;
		CircleMasterDTO dtoObj = null;
		UserDetailsBean userBean = (UserDetailsBean) (reqObj.getSession())
		.getAttribute(Constants.USER_INFO);
		ActionForward forward = null;
		ArrayList moveCircleList = new  ArrayList();
		ArrayList finalmoveCircleList = new  ArrayList();
		try {
			if (userBean == null) {
				ActionErrors errors = new ActionErrors();
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(reqObj, errors);
				forward = mapObj.findForward(Constants.FAILURE1);
			} else {
				beanObj = (CircleMasterBean) frmObj;
				dao = new ViewEditCircleMasterDAOImpl();
				dtoObj = new CircleMasterDTO();
				dtoObj = dao.findByPrimaryKey(reqObj
						.getParameter(Constants.CIRCLECODE));
				beanObj.setCircleName(dtoObj.getCircleName());
				beanObj.setCircleName(dtoObj.getCircleName());
				beanObj.setCircleCode(dtoObj.getCircleCode());
				beanObj.setOldcircleName(dtoObj.getCircleName());
				beanObj.setOldcircleStatus(dtoObj.getStatus());
				String tempDate[] = dtoObj.getCreatedDt().toString().trim()
				.split(" ");
				beanObj.setCircleCreateDate(tempDate[0]);
				beanObj.setCircleCreatedBy(dtoObj.getCreatedBy());
				beanObj.setCircleStatus(dtoObj.getStatus());
				beanObj.setHubCode(dtoObj.getHubCode());
				beanObj.setWelcomeMsg(dtoObj.getWelcomeMsg());
				beanObj.setReleaseTime(dtoObj.getReleaseTime());
				if (dtoObj.getMinsatCheck().equals("Y")) {
					beanObj.setMinsatCheck(true);
				} else {
					beanObj.setMinsatCheck(false);
				}

				if (dtoObj.getApefCheck().equals("Y")) {

					beanObj.setApefCheck(true);
				} else {
					beanObj.setApefCheck(false);
				}
				if (dtoObj.getSimReq().equals("Y")) {

					beanObj.setSimReqCheck(true);
				} else {
					beanObj.setSimReqCheck(false);
				}
				String hubCode = dao.findHubCodeForCircle(dtoObj.getCircleCode());
				moveCircleList = dao.getCircleListByHub(hubCode);
				Iterator  itr = moveCircleList.iterator();
				while(itr.hasNext())
				{
					LabelValueBean lvBean = (LabelValueBean)itr.next();
					if(!(lvBean.getValue()).equalsIgnoreCase(dtoObj.getCircleCode()))
					{
						finalmoveCircleList.add(lvBean);
					}
				}
				if(!finalmoveCircleList.isEmpty()){
					beanObj.setMoveCircleList(finalmoveCircleList);
				}
				
				logger.info(userBean.getLoginId()
						+ " showDetails() of circle initialized");
				forward = mapObj.findForward(Constants.SUCCESS);
			}
		} catch (DAOException de) {
			ActionErrors errors = new ActionErrors();
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

			saveErrors(reqObj, errors);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		} finally {
			logger.debug("Exiting showDetails() method of CircleMasterAction ");
		}
		return forward;
	}

	/**
	 * This method is called to show all the details for circle.
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
	 * @return Object of ActionForward class
	 * @throws Exception
	 * 
	 */

	public ActionForward showAll(ActionMapping mapObj, ActionForm frmObj,
			HttpServletRequest reqObj, HttpServletResponse resObj) {
		logger.debug("Entering showAll() method of CircleMasterAction");
		ViewEditCircleMasterDAOImpl dao = new ViewEditCircleMasterDAOImpl();
		UserDetailsBean userBean = (UserDetailsBean) (reqObj.getSession())
		.getAttribute(Constants.USER_INFO);
		ActionForward forward = null;
		try {
			int noofpages = 0;
			noofpages = dao.countCircle();
			reqObj.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
			.setPaginationinRequest(reqObj);

			if (userBean == null) {
				ActionErrors errors = new ActionErrors();
				errors.add(Constants.NAME1, new ActionError(
						Constants.SESSION_EXPIRED));
				saveErrors(reqObj, errors);
				logger.info(userBean.getLoginId() + " session expired");
				forward = mapObj.findForward(Constants.FAILURE1);
			} else {
				ArrayList ArrList = new ArrayList();
				CircleMasterBean createObj = (CircleMasterBean) frmObj;
				ArrList = dao.findAllSelectedRows(pagination.getLowerBound(),
						pagination.getUpperBound());
				createObj.setDisplayDetails(ArrList);
				reqObj.setAttribute("ArrList", ArrList);
				logger.info(userBean.getLoginId() + " showAll() of CircleMasterAction initialized");
				forward = mapObj.findForward(Constants.SUCCESS);
			}
		} catch (DAOException de) {
			ActionErrors errors = new ActionErrors();
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
			saveErrors(reqObj, errors);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION, e);
		} finally {
			logger.debug("Exiting showAll() method of CircleMasterAction ");
		}
		return forward;
	}

}
