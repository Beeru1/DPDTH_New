package com.ibm.hbo.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.dto.HBOUserMstr;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOViewUserFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.UserService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.hbo.services.impl.UserServiceImpl;

/**
 * @version 	1.0
 * @author
 */
public class HBOViewUsersAction extends Action {
	/*
		* Logger for the class.
	*/
	private static final Logger logger;

	static {

		logger = Logger.getLogger(HBOViewUsersAction.class);
	}

	/* Local Variables */
	private static String VIEWUSER_SUCCESS = "viewAllUsers";

	private static String VIEWUSER_FAILURE = "viewUserFailure";

	private static String EDITUSER_SUCCESS = "editUser";

	private static String UPDATEZONE_SUCCESS = "updateZoneSucess";

	private static String VIEWUSERDETAILS_SUCCESS = "viewUserDetails";

	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		HBOViewUserFormBean viewUserFormBean = (HBOViewUserFormBean) form;
		HBOUserBean sessionUserBean = (HBOUserBean) session.getAttribute("USER_INFO");
		HBOMasterService masterService = new HBOMasterServiceImpl();
		try {

			if ("/viewAllUsers".equalsIgnoreCase(mapping.getPath())) {
				UserService user = new UserServiceImpl();
				viewUserFormBean.setLoginActorId(sessionUserBean.getActorId());
				viewUserFormBean.setUserList(
					(ArrayList) user.viewUserService(
						viewUserFormBean.getLoginActorId(),
						sessionUserBean.getUserLoginId()));
						
				forward = mapping.findForward(VIEWUSER_SUCCESS);
			}
			if ("/viewUserDetails".equalsIgnoreCase(mapping.getPath())) {
				viewUserFormBean.setLoginActorId(sessionUserBean.getActorId());
				String userId =
					request.getParameter("USER_ID") == null
						? ""
						: request.getParameter("USER_ID").toString();
				viewUserFormBean.setUserId(userId);
				UserService user = new UserServiceImpl();
				HBOUserMstr userDto = new HBOUserMstr();
				viewUserFormBean.setUserList(
					(ArrayList) user.viewUserService(
						viewUserFormBean.getLoginActorId(),
					sessionUserBean.getUserLoginId()));
					logger.info("pull all details");
				userDto = user.getEditUserDetails(viewUserFormBean.getUserId(),	viewUserFormBean.getUserList());

				/* BeanUtil to populate Form Bean with DTO data */

				BeanUtils.copyProperties(viewUserFormBean, userDto);
				forward = mapping.findForward(VIEWUSERDETAILS_SUCCESS);
			}
			if ("/initUpdateUser".equalsIgnoreCase(mapping.getPath())) {
				viewUserFormBean.setLoginActorId(sessionUserBean.getActorId());
				String userId =
					request.getParameter("USER_ID") == null
						? ""
						: request.getParameter("USER_ID").toString();
				viewUserFormBean.setUserId(userId);
				UserService user = new UserServiceImpl();
				HBOUserMstr userDto = new HBOUserMstr();
				//logger.info("login actor id"+viewUserFormBean.getLoginActorId());
				//logger.info(" login id"+viewUserFormBean.getUserId());
				
				//Get DropDown Values of State
				String condition="";
				ArrayList arrMasterList = masterService.getMasterList(userId,"state",condition);
				viewUserFormBean.setArrStateList(arrMasterList);
				
				viewUserFormBean.setUserList(
					(ArrayList) user.viewUserService(
						viewUserFormBean.getLoginActorId(),
						sessionUserBean.getUserLoginId()));
				userDto =
					user.getEditUserDetails(
						viewUserFormBean.getUserId(),
						viewUserFormBean.getUserList());
						
				int iSessionActorId=0;
				int iEditUserActorId=0;
				
				if(sessionUserBean.getActorId() != null){ 
					iSessionActorId = Integer.parseInt(sessionUserBean.getActorId());
				}
				logger.info("userDto.getActorId():"+userDto.getActorId());
				
				if(userDto.getActorId() != null){ 
					iEditUserActorId = Integer.parseInt(userDto.getActorId());
				}
				// If Logged in user role > User to be updated then issue.
				// Case when user changes userId from App URL
				logger.info("iSessionActorId:"+iSessionActorId);
				logger.info("iEditUserActorId:"+iEditUserActorId);
				
				if(iSessionActorId > iEditUserActorId) {
					forward = mapping.findForward(VIEWUSER_FAILURE);
				} else {
					/* BeanUtil to populate Form Bean with DTO data */
					BeanUtils.copyProperties(viewUserFormBean, userDto);
					forward = mapping.findForward(EDITUSER_SUCCESS);
				}
			}
			if ("/UpdateUser".equalsIgnoreCase(mapping.getPath())) {
				if (isTokenValid(request,true)){  
				HBOUserMstr userMstrDto = new HBOUserMstr();
				viewUserFormBean.setUpdatedBy(sessionUserBean.getUserLoginId());

				/* BeanUtil to populate DTO with Form Bean data */

				BeanUtils.copyProperties(userMstrDto, viewUserFormBean);

				/*Update zone service method called*/
				UserService user = new UserServiceImpl();
				int updateCnt = user.editUserService(userMstrDto);
				if (updateCnt > 0) {
					viewUserFormBean.setMessage("User Updated Sucessfully");
				}
				forward = mapping.findForward(UPDATEZONE_SUCCESS);
			}
			else
			{
				forward = mapping.findForward(UPDATEZONE_SUCCESS);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof IllegalAccessException) {
				logger.error(
					"execute() method : caught IllegalAccessException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof InvocationTargetException) {
				logger.error(
					"execute() method : caught InvocationTargetException Exception while using BeanUtils.copyProperties()");
			}
			if (e instanceof HBOException) {
				logger.error(
					"execute() method : caught HBOException Exception while using edit user");
			}
			logger.error("Exception From ArcViewUsersAction" + e);
			forward = mapping.findForward(VIEWUSER_FAILURE);

		}
		saveToken(request);
		// Finish with
		return (forward);

	}
}
