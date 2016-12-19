/**************************************************************************
 * File     : BulkSubscriberAction.java
 * Author   : Banita
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Banita	First Cut.
 * V0.2		Dec 8, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

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
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.ibm.core.exception.DAOException;
import com.ibm.virtualization.ussdactivationweb.beans.BulkSubscriberBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.BulkSubscriberDAO;
import com.ibm.virtualization.ussdactivationweb.dao.ViewEditCircleMasterDAOImpl;
//import com.ibm.virtualization.ussdactivationweb.dto.BulkSubscriberDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkSubscriberAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger
	.getLogger(BulkSubscriberAction.class.toString());

	public ActionForward initial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Enter in to initial() method of class BulkUploadAction.");
		ActionForward forward = new ActionForward();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
		.getAttribute(Constants.USER_INFO);
		BulkSubscriberBean bulkBean = (BulkSubscriberBean) form;
		try {
			if (userBean.getCircleId() == null) {
				bulkBean.setUserRole(Constants.SUPER_ADMIN);
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl
						.getCircleList());
			} else {

				bulkBean.setUserRole(Constants.CIRCLE_ADMIN);
			}
			forward = mapping.findForward("viewBulk");
			logger.info(" Bulk Subscriber Upload page Initialized by "+userBean.getLoginId());
		} catch (Exception e) {
			logger.error("Exception Occured in initial() method ", e);
			logger.info(userBean.getLoginId()+" Exception occured durin the initialisation of Bulk Subscriber Upload page ");
			forward = mapping.findForward(Constants.ERROR);
		}
		
		logger.debug("Exit from initial() method of class BulkUploadAction.");
		return forward;
	}

	/**
	 * This method is responsible for bulk subscriber upload
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
	 * @exception IOException,Signals
	 *                that an I/O exception of some sort has occurred.
	 * @exception ServletException,This
	 *                exception is thrown to indicate a servlet problem.
	 * 
	 */
	
	/*
	
	public ActionForward bulkSubsUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		BulkSubscriberDAO dao = new BulkSubscriberDAO();
		HttpSession session = request.getSession();
		UserDetailsBean userBean = (UserDetailsBean) session
		.getAttribute(Constants.USER_INFO);
		BulkSubscriberBean bulkUploadBean = (BulkSubscriberBean) form;
		BulkSubscriberDTO bulkSubscriberDTO = new BulkSubscriberDTO();
		FormFile file = null;
		String circleCode = null;
		File fileRef = null;
		BufferedWriter brWriter = null;
		MessageResources messageResources = getResources(request);
		try {
			if (userBean.getCircleId() == null) {
				circleCode = bulkUploadBean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}
			file = bulkUploadBean.getUploadFile();
			/** validation for empty file * 
			if (file.getFileData().length == 0) {
				errors.add("error.empty.file", new ActionError(
				"error.empty.file"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
				return initial(mapping, form, request, response);
				
			}
			String fileStoragePath = messageResources
			.getMessage(Constants.FILE_STORAGE_PATH);
			/*******************************************************************
			 * validation for the maximum file size. maximum file size allowed
			 * to upload is 8 mb
			 *****************************************************************
			if (file.getFileSize() > Integer.parseInt(messageResources
					.getMessage(Constants.MAX_FILE_SIZE))) {
				errors.add("error.max.file.size", new ActionError(
				"error.max.file.size"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 8MB.");
				return initial(mapping, form, request, response);
			}
			int index = file.getFileName().lastIndexOf(".");
			String fileNameDataBase = file.getFileName().substring(0, index)
			+ "-"
			+ Utility
			.getDateAsString(new Date(), "dd-MM-yyyy-hh-mm-ss")
			+ file.getFileName().substring(index);

			String filePath = fileStoragePath + fileNameDataBase;

			String contents = new String(file.getFileData());

			fileRef = new File(filePath);
			brWriter = new BufferedWriter(new FileWriter(fileRef));
			brWriter.write(contents);
			brWriter.close();

			bulkSubscriberDTO.setCircleCode(circleCode);
			bulkSubscriberDTO.setRelatedFileId(0);
			bulkSubscriberDTO.setOriginalFileName(file.getFileName());
			bulkSubscriberDTO.setUploadedBy(userBean.getLoginId());
			bulkSubscriberDTO.setIntUploadedBy(Integer.parseInt(userBean
					.getUserId()));
			/** * Saving file name in the database **
			dao.fileInfoForSubscriber(fileRef.getName(),
					bulkSubscriberDTO, Constants.FILE_STATUS_SAVED,
					Constants.FILE_FAILED_MSG_SAVED,
					Constants.FILE_TYPE_ORIGINAL_FILE);

			errors.add("file.uploaded", new ActionError("file.uploaded"));
			errors.add("file.uploaded1", new ActionError("file.uploaded1"));
			saveErrors(request, errors);
			forward = mapping.findForward("Success");
			logger.info(userBean.getLoginId() + " Bulk Subscriber file uploaded successfully");
			
		} catch (DAOException e) {
			request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl
					.getCircleList());
			errors.add("name", new ActionError(Constants.ERROR_KEY, file
					.getFileName()
					+ "  "
					+ messageResources.getMessage(Constants.INVALID_FILE)));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId()+" there was an exception in uploading the file.");
			forward = mapping.findForward("Failure");
		} finally {
			closeResources(bulkUploadBean, file);
		}
		return forward;
	}*/
	/**
	 * This method responsible for close all IO resources
	 * 
	 * @param swap
	 *            :BulkUploadBean
	 * @param file
	 *            :FormFile
	 * @throws IOException
	 */

	private void closeResources(BulkSubscriberBean swap, FormFile file)
	throws IOException {
		if (swap != null) {
			swap.getUploadFile().destroy();
		}
		if (file != null) {
			file.destroy();
		}

	}
	
	public ActionForward bulkDeletionInitial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Enter in to bulkDeletionInitial() method of class BulkUploadAction.");
		ActionForward forward = new ActionForward();
		UserDetailsBean userBean = (UserDetailsBean) (request.getSession())
		.getAttribute(Constants.USER_INFO);
		BulkSubscriberBean bulkBean = (BulkSubscriberBean) form;
		try {
			if (userBean.getCircleId() == null) {
				bulkBean.setUserRole(Constants.SUPER_ADMIN);
				request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl
						.getCircleList());
			} else {

				bulkBean.setUserRole(Constants.CIRCLE_ADMIN);
			}
			forward = mapping.findForward("bulkdeletion");
			logger.info(" Bulk Subscriber Upload page Initialized by "+userBean.getLoginId());
		} catch (Exception e) {
			logger.error("Exception Occured in bulkDeletionInitial() method ", e);
			logger.info(userBean.getLoginId()+" Exception occured durin the initialisation of Bulk Subscriber deletion page ");
			forward = mapping.findForward(Constants.ERROR);
		}
		
		logger.debug("Exit from bulkDeletionInitial() method of class BulkUploadAction.");
		return forward;
	}
	
	public ActionForward bulkSubsdeletion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		BulkSubscriberDAO dao = new BulkSubscriberDAO();
		HttpSession session = request.getSession();
		UserDetailsBean userBean = (UserDetailsBean) session
		.getAttribute(Constants.USER_INFO);
		BulkSubscriberBean bulkUploadBean = (BulkSubscriberBean) form;
		FormFile file = null;
		String circleCode = null;
		File fileRef = null;
		BufferedWriter brWriter = null;
		MessageResources messageResources = getResources(request);
		try {
			if (userBean.getCircleId() == null) {
				circleCode = bulkUploadBean.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}
			file = bulkUploadBean.getUploadFile();
			/** validation for empty file * */
			if (file.getFileData().length == 0) {
				errors.add("error.empty.file", new ActionError(
				"error.empty.file"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
				return initial(mapping, form, request, response);
				
			}
			String fileStoragePath = messageResources
			.getMessage(Constants.FILE_STORAGE_PATH);
			/*******************************************************************
			 * validation for the maximum file size. maximum file size allowed
			 * to upload is 8 mb
			 ******************************************************************/
			if (file.getFileSize() > Integer.parseInt(messageResources
					.getMessage(Constants.MAX_FILE_SIZE))) {
				errors.add("error.max.file.size", new ActionError(
				"error.max.file.size"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 8MB.");
				return initial(mapping, form, request, response);
			}
			int index = file.getFileName().lastIndexOf(".");
			String fileNameDataBase = file.getFileName().substring(0, index)
			+ "-"
			+ Constants.SubDelFile
			+ file.getFileName().substring(index);

			String filePath = fileStoragePath + fileNameDataBase;

			String contents = new String(file.getFileData());

			fileRef = new File(filePath);
			brWriter = new BufferedWriter(new FileWriter(fileRef));
			brWriter.write(contents);
			brWriter.close();

		
			
			dao.deleteSubscribers(fileNameDataBase,circleCode);

			errors.add("file.deleted", new ActionError("file.deleted"));
			saveErrors(request, errors);
			forward = mapping.findForward("Success");
			logger.info(userBean.getLoginId() + " Bulk Subscriber deletion file uploaded successfully");
			
		} catch (DAOException e) {
			request.setAttribute(Constants.CIRCLE_LIST, ViewEditCircleMasterDAOImpl
					.getCircleList());
			errors.add("name", new ActionError(Constants.ERROR_KEY, file
					.getFileName()
					+ "  "
					+ messageResources.getMessage(Constants.INVALID_FILE)));
			saveErrors(request, errors);
			logger.info(userBean.getLoginId()+" there was an exception in uploading the file.");
			forward = mapping.findForward("Failure");
		} finally {
			closeResources(bulkUploadBean, file);
		}
		return forward;
	}
	
	public ActionForward initdeletedSubs(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("Entering method-init()");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		Calendar calendar = Calendar.getInstance();
		LinkedHashMap dateWiseMap = new LinkedHashMap();
		
		try {
	
//			 0-7 -- 7 days before report
			for (int i = 0; i < Integer.parseInt(Utility.getValueFromBundle(
					Constants.DAILY_REPORT_PREVIOUS_DAYS,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE)); i++) {
				if (i == 0) {
					if ("N".equalsIgnoreCase(Utility.getValueFromBundle(
							Constants.DAILY_REPORT_INCLUDE_CURRENT_DAY,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE))) {
						// This line added because current day is not required.
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						}
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					} else {
						calendar.add(Calendar.DAY_OF_MONTH, -1);
						dateWiseMap.put(Utility.getDateAsString(calendar.getTime(),
								"MM/dd/yyyy"), Utility.getDateAsString(calendar
								.getTime(), "MMM-dd-yyyy"));
					}
				}
			
			request.setAttribute("dateWiseMap", dateWiseMap);
		
		} catch (DAOException e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}catch (Exception e) {
			logger.error("Exception occured in init() method : " + e);
			errors.clear();
			errors.add(Constants.GENERAL_ERROR, new ActionError(
					Constants.GENERAL_ERROR));
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				forward = mapping.findForward(Constants.ERROR);
			}
		}
		forward = mapping.findForward("delSubReport");
		logger.info("Exiting method- init()");
		return forward;
	}
}