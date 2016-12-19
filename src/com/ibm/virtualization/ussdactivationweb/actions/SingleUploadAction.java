
/**************************************************************************
 * File     : SingleUploadAction.java
 * Author   : Abhipsa
 * Created  : Sept 23, 2008
 * Modified : Sept 23, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Sept 23, 2008 	Abhipsa	First Cut.
 * V0.2		Sept 23, 2008 	Abhipsa	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.util.ArrayList;
import java.util.Collections;

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
import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.SingleUploadBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.DistportalServicesDaoImpl;
import com.ibm.virtualization.ussdactivationweb.dao.RegistrationOfAllUsersDAO;
import com.ibm.virtualization.ussdactivationweb.dao.SingleUploadDAO;
import com.ibm.virtualization.ussdactivationweb.dto.RegistrationOfAllDTO;
import com.ibm.virtualization.ussdactivationweb.dto.SingleUploadDTO;
import com.ibm.virtualization.ussdactivationweb.pagination.Pagination;
import com.ibm.virtualization.ussdactivationweb.pagination.PaginationUtils;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.RequesterDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;


/**
 * @author abhipsa
 * 
 */
public class SingleUploadAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger
			.getLogger(SingleUploadAction.class.toString());

	/**
	 * 
	 * This method is called when you press "search mapped user" button on the
	 * jsp. This method search for the mapped users to a specific business
	 * user(selected from jsp using radio buttons).
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
	 */

	public ActionForward searchMappedUser(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger
				.debug("Entering searchMappedUser() method of SingleUploadAction.");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		SingleUploadBean bean = (SingleUploadBean) form;
		SingleUploadDAO singleDao = new SingleUploadDAO();
		SingleUploadDTO singleDto = new SingleUploadDTO();
		try {
			// *************Start of Pagination

			// Counting the number of users for pagination
			int noofpages = singleDao.countMappedUser(bean.getMappingType(),
					bean.getBusinessUserId());
			
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);

			// getting the list
			singleDto = singleDao.getMappingList(bean.getMappingType(), bean
					.getBusinessUserId(), pagination.getLowerBound(),
					pagination.getUpperBound());

			// ***************End of pagination

			// when the list from abouve is empty. First page has to be
			// initiated again.
			if (singleDto.getMappedBusinessUserList().isEmpty()) {
				errors.clear();
				errors.add(Constants.USER_ERROR, new ActionError(
						Constants.USER_ERROR));
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
				}
				forward = mapping.findForward(Constants.ERROR);

			}
			
			// when the list from above have some values
			else {
				bean.setMappedBusinessUserList(singleDto
						.getMappedBusinessUserList());
				bean.setBusinessUserId(bean.getBusinessUserId());
				bean.setMappingType(bean.getMappingType());
				bean.setBusinessUserName(singleDto.getBusinessUserName());
				bean.setBusinessUserCode(singleDto.getBusinessUserCode());
				session.setAttribute("mappedBusinessUserList", singleDto
						.getMappedBusinessUserList());
				
				
				/*** Code added by Ashad for user Hierarchy ****/
				getbizUserHierarchy(bean);
				
				forward = mapping.findForward("mappedUsers");

			}
			
			
		
			logger.info("searchMappedUser in SingleUploadAction is initaited  for business user "
					+ bean.getBusinessUserName());
		} catch (DAOException e) {
			logger.error("Exception cause :" + e.getMessage());
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}
		logger
				.debug("Exiting searchMappedUser() method of SingleUploadAction.");
		return forward;
	}

	private void getbizUserHierarchy(SingleUploadBean bean) throws com.ibm.virtualization.ussdactivationweb.utils.DAOException {
		RegistrationOfAllDTO dto = new RegistrationOfAllDTO();
		RegistrationOfAllUsersDAO dao = new RegistrationOfAllUsersDAO();
		dto = dao.getUserProfile(
				bean.getBusinessUserId());
		DistportalServicesDaoImpl distportalService = new DistportalServicesDaoImpl();
		RequesterDTO requesterDTO = distportalService.getCompleteRequesterDetails(dto.getRegNumber(), dto.getTypeOfUserValue());
		BusinessUserDTO[] userDtos = requesterDTO.getBusinessUserArray();
		
		int arrayLength = 0;
		if(requesterDTO != null){
		    arrayLength = userDtos.length;
		}
		BusinessUserDTO userDto = null;
		ArrayList userHierarchyList= new ArrayList();
		for(int index =0; index<arrayLength; index++){
			userDto = userDtos[index];
			if(userDto == null){

				continue;
			}
		
				userHierarchyList.add(userDto.getTypeOfUserValue()+" : "+userDto.getName()+"("+userDto.getRegNumber()+")");
			
		}
		Collections.reverse(userHierarchyList);
		StringBuffer bizUserName= new StringBuffer();
		for(int index =0; index < userHierarchyList.size(); index++){
			if(index == userHierarchyList.size() -1 ) {
				bizUserName.append(userHierarchyList.get(index));
			} else {
				bizUserName.append(userHierarchyList.get(index)).append(" -> ");
			}
		}
		logger.info(" User Hierarchy : "+bizUserName);
		bean.setBizUserHierarchy(bizUserName.toString());
	}

	/**
	 * 
	 * When we get the list of mapped users from the above method. We have edit
	 * button in every row of that list. This method is called when we click
	 * that "edit link". This method takes us to edit mapping page, where detail
	 * of the mapped user comes in text boxes. Along with thw list of users with
	 * which it coud be mapped.
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
	 */

	public ActionForward editUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse res)
			throws Exception {
		logger.debug("Entering editUser() method of SingleUploadAction.");
		HttpSession session = request.getSession();
		ActionForward forward = new ActionForward();
		SingleUploadBean bean = (SingleUploadBean) form;
		SingleUploadDAO singleDao = new SingleUploadDAO();
		SingleUploadDTO singedto = new SingleUploadDTO();
		ArrayList list = new ArrayList();
		UserDetailsBean userBean = (UserDetailsBean) session
		.getAttribute(Constants.USER_INFO);
		try {
			// *************Start of Pagination

			int mappingType = bean.getMappingType();

			String circleId = "";
			if (userBean.getCircleId()==null) {
				circleId = bean.getCircleCode(); 
			} else {
				circleId = userBean.getCircleId();
			}
			bean.setCircleCode(circleId);
					
			int noofpages = 0;
			int mappedUserCode = bean.getBusinessUserId();
			noofpages = singleDao.countParentList(circleId, mappingType,bean.getMappedBusinessUserId());
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);

			// ***************End of pagination

			
			singedto = singleDao.getmappedUserInfo(mappedUserCode, mappingType);
			bean
					.setMappedBusinessUserCode(singedto
							.getMappedBusinessUserCode());
			bean.setMappedBusinessUserId(singedto.getMappedBusinessUserId());
			bean
					.setMappedBusinessUserName(singedto
							.getMappedBusinessUserName());
			bean.setMsisdn(singedto.getMsisdn());
			bean.setBusinessUserId(singedto.getBusinessUserId());
			bean.setBusinessUserId1(singedto.getBusinessUserId());
			bean.setBusinessUserName(singedto.getBusinessUserName());
			bean.setMappingType(mappingType);
			list = (ArrayList) singleDao.getparentList(circleId,mappingType,mappedUserCode, pagination.getLowerBound(), pagination
							.getUpperBound());
			if(list.isEmpty())
			{
				ActionErrors errors = new ActionErrors();
				errors.add("No.Parent", new ActionError(
				"No.Parent"));
				saveErrors(request, errors);
			}
			bean.setBusinessUserList(list);
			request.setAttribute("businessUserList", list);
			forward = mapping.findForward("EditUserMapping");
			logger.info("editUser method in SingleUploadAction is initiated for user " 
					+ bean.getBusinessUserName());
		} catch (DAOException de) {
			logger.error("Exception Occured in editUser() method ", de);
			forward = mapping.findForward(Constants.ERROR);
		}
		logger.debug("Exiting editUser() method of SingleUploadAction.");
		return forward;
	}

	/**
	 * 
	 * This method is called when we click submit button on EditMappedUser.jsp
	 * This is used to update the user with the new mappings.
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
	 */
	public ActionForward updaterMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.debug("Entering updaterMapping() method of SingleUploadAction.");
		ActionErrors errors = new ActionErrors();
		SingleUploadBean bean = (SingleUploadBean) form;
		SingleUploadDAO singleDao = new SingleUploadDAO();
		HttpSession session = req.getSession();
		ActionForward forward = new ActionForward();
		try {

			// calling of dao method to update the user mapping.
			String update = singleDao.updateUserMapping(bean
					.getBusinessUserId(), bean.getMappedBusinessUserId(), bean
					.getMappingType());

			logger.info("updaterMapping method in SingleUploadAction is initiated " +
					"for business user "+ bean.getBusinessUserId());
			if (update.equals("success")) {
				errors.add("Information.updated", new ActionError(
						"Information.updated"));
				saveErrors(req, errors);
				forward = mapping.findForward("Success");
			} else {
				errors.add("Information.connot.updated", new ActionError(
						"Information.connot.updated"));
				saveErrors(req, errors);
				
				forward = mapping.findForward(Constants.ERROR);
			}

			

		} catch (DAOException de) {
			logger.error("Exception Occured in updaterMapping() method ", de);
			forward = mapping.findForward(Constants.ERROR);
		}
		logger.debug("Exiting updaterMapping() method of SingleUploadAction.");
		return forward;
	}

	/**
	 * 
	 * This method is called when we press "Add Mappping" button on
	 * SingleUploadMapping.jsp This method takes us to different page called
	 * AddUnMappedUsers.jsp. Through this method we will get the list of valid
	 * users which can be mapped with the selected user from
	 * SingleUploadMapping.jsp
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
	 */
	public ActionForward addNewMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Entering addNewMapping() method of SingleUploadAction.");
		HttpSession session = request.getSession();
		ActionErrors errors = new ActionErrors();
		SingleUploadBean bean = (SingleUploadBean) form;
		SingleUploadDAO singleDao = new SingleUploadDAO();
		SingleUploadDTO singleDto = new SingleUploadDTO();
		ActionForward forward = new ActionForward();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
				.getAttribute(Constants.USER_INFO);

		// In case of Circle Admin
		if (userBean.getCircleId() == null) {
			// do nothing
		} else {
			bean.setCircleCode(userBean.getCircleId());
		}

		// *************Start of Pagination

		try {
			int noofpages = 0;
			if (userBean.getCircleId() == null) {
				// do nothing
				noofpages = singleDao.countUnmappedUsers(request
						.getParameter(Constants.CIRCLECODE), bean.getMappingType(),bean.getBusinessUserId());
			}else{
				bean.setCircleCode(userBean.getCircleId());
				noofpages = singleDao.countUnmappedUsers(bean.getCircleCode(), bean.getMappingType(),bean.getBusinessUserId());
			}
			request.setAttribute("PAGES", new Integer(noofpages));
			Pagination pagination = PaginationUtils
					.setPaginationinRequest(request);
			singleDto = singleDao.getUnmappedUserList(bean.getBusinessUserId(),
					bean.getCircleCode(), pagination.getLowerBound(),
					pagination.getUpperBound(), bean.getMappingType());

			// ***************End of pagination

			// control enters if when the list of UnMappedUsers is empty
			if (singleDto.getMappedBusinessUserList().isEmpty()) {
				errors.add("error.Mapping", new ActionError("ErrorKey",
						"No user found for mapping."));
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);

			} else {
				bean.setBusinessUserId(bean.getBusinessUserId());
				bean.setMappedBusinessUserList(singleDto
						.getMappedBusinessUserList());
				bean.setBusinessUserName(singleDto.getBusinessUserName());
				bean.setBusinessUserCode(singleDto.getBusinessUserCode());
				session.setAttribute("mappedBusinessUserList", singleDto
						.getMappedBusinessUserList());
				session.setAttribute("businessUserId", String.valueOf(bean
						.getBusinessUserId()));
				
				/*** Code added by Ashad for user Hierarchy ****/
				getbizUserHierarchy(bean);
				
				logger.info("addNewMapping method in SingleUploadAction is initiated " +
						"for business user "+ bean.getBusinessUserName());
				return mapping.findForward("addUnmappeduser");
			}
		} catch (DAOException de) {
			logger.error("Exception Occured in addNewMapping() method ", de);
			forward = mapping.findForward(Constants.ERROR);
		}
		logger.debug("Exiting addNewMapping() method of SingleUploadAction.");
		return forward;
	}

	/**
	 * 
	 * This method is called when we press submit buttom from
	 * AddUnMappedUser.jsp This method updates the new mapping for the user in
	 * the database. And redirectd you to SingleUploadMapping.jsp
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
	 */
	public ActionForward insertNewMapping(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger
				.debug("Entering insertNewMapping() method of SingleUploadAction.");
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		SingleUploadBean bean = (SingleUploadBean) form;
		SingleUploadDAO singleDao = new SingleUploadDAO();
		ActionForward forward = new ActionForward();
		try {
			int updatedRow = singleDao.insertSelectedUserMapping(bean
					.getBusinessUserId(), bean.getMappedBusinessUserId(), bean
					.getMappingType());
			// if record already exists then show error msg on page
			logger.info("insertNewMapping method in SingleUploadAction is initaited" +
					" for business user Id "+bean.getBusinessUserId() +
					" Mapping Type: "+bean.getMappingType() );
						
			if (updatedRow == -1) {
				errors.add("error.addNewUser", new ActionError(
						"error.addNewUser"));
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			} else {
				errors.add("User.message", new ActionError("User.message"));
				saveErrors(request, errors);
				forward = mapping.findForward("Success");
			}
			
			session.removeAttribute("businessUserList");
		} catch (DAOException de) {
			logger.error("Exception Occured in insertNewMapping() method ", de);
			forward = mapping.findForward(Constants.ERROR);
		}
		logger
				.debug("Exiting insertNewMapping() method of SingleUploadAction.");
		return forward;
	}

}
