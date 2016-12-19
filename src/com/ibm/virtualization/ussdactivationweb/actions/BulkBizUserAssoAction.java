/**************************************************************************
 * File     : BulkBizUserAssoService.java
 * Author   : Abhipsa
 * Created  : Dec 8, 2008 
 * Modified : Dec 8, 2008 
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Dec 8, 2008 	Abhipsa	First Cut.
 * V0.2		Dec 8, 2008 	Abhipsa	First modification
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
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.ibm.virtualization.ussdactivationweb.beans.BulkBizUserAssoBean;
import com.ibm.virtualization.ussdactivationweb.beans.UserDetailsBean;
import com.ibm.virtualization.ussdactivationweb.dao.BulkBizUserAssoDAO;
import com.ibm.virtualization.ussdactivationweb.dto.BulkBizUserAssoDTO;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.USSDCommonUtility;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/**
 * @author a_gupta
 * 
 */
public class BulkBizUserAssoAction extends DispatchAction {

	/** getting logger refrence * */
	private static final Logger logger = Logger
	.getLogger(BulkBizUserAssoAction.class.toString());

	/**
	 * This method is for Bulk user mapping
	 * 
	 * @param mapping :
	 *            ActionMapping
	 * @param form :
	 *            ActionForm
	 * @param request :
	 *            HttpServletRequest
	 * @param response :
	 *            HttpServletResponse
	 * @return forward : ActionForward
	 * @throws Exception
	 * 
	 */

	public ActionForward bulkMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		logger.debug("Entering bulkMapping() : BulkBizUserAssoAction ");
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		BulkBizUserAssoDAO dao = new BulkBizUserAssoDAO();
		BulkBizUserAssoBean swap = (BulkBizUserAssoBean) form;
		BulkBizUserAssoDTO bulkBizUserAssoDTO = new BulkBizUserAssoDTO();
		UserDetailsBean userBean = (UserDetailsBean) request.getSession()
		.getAttribute(Constants.USER_INFO);
		BufferedWriter brWriter = null;
		String circleCode;
		FormFile file = swap.getUploadFile();
		File fileRef = null;
		try {
			if (userBean.getCircleId() == null) {
				circleCode = swap.getCircleCode();
			} else {
				circleCode = userBean.getCircleId();
			}

			/** validation for empty file * */
			if (file.getFileData().length == 0) {
				errors.add("error.empty.file", new ActionError(
				"error.empty.file"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is an empty file.");
				return mapping.findForward("Failure");
			}

			MessageResources messageResources = getResources(request);
			String fileStoragePath = messageResources
			.getMessage(Constants.FILE_STORAGE_PATH);
			/*******************************************************************
			 * validation for the file maximum file size maximum file size
			 * allowed to upload is 8 mb
			 ******************************************************************/
			if (file.getFileSize() > Integer.parseInt(messageResources
					.getMessage(Constants.MAX_FILE_SIZE_BU))) {
				errors.add("error.max.file.size.bu", new ActionError(
				"error.max.file.size.bu"));
				saveErrors(request, errors);
				logger.info("The file uploaded by "+userBean.getLoginId()+" is more then 3MB.");
				return mapping.findForward("Failure");
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

			bulkBizUserAssoDTO.setCircleCode(circleCode);
			bulkBizUserAssoDTO.setRelatedFileId(0);
			bulkBizUserAssoDTO.setMappingType(swap.getMappingTypes());
			bulkBizUserAssoDTO.setOriginalFileName(file.getFileName());
			bulkBizUserAssoDTO.setUploadedBy(userBean.getLoginId());
			bulkBizUserAssoDTO.setUserDataId(Integer.parseInt(swap
					.getBusinessUserId()));

			// Saving file name in the database
			long lonFileId = dao.fileInfoForBusinessUser(fileRef.getName(),
					bulkBizUserAssoDTO, Constants.FILE_STATUS_SAVED,
					Constants.FILE_FAILED_MSG_SAVED,
					Constants.FILE_TYPE_ORIGINAL_FILE);

			errors.add("file.uploaded", new ActionError("file.uploaded"));
			errors.add("file.uploaded1", new ActionError("file.uploaded1"));
			saveErrors(request, errors);
			forward = mapping.findForward("Success");
			logger.info("The file uploaded by "+userBean.getLoginId()+" is successfully saved.");
			
		} catch (Exception e) {
			logger.info(userBean.getLoginId()+" there was an exception in uploading the file.");
			logger.error("Exception in BulkBizuserAssoAction",e);
			errors.add("exception", new ActionError("exception"));
			saveErrors(request, errors);
			return mapping.findForward("Failure");
		} finally {
			closeResources(swap, file);
		}
		logger.debug("Exiting bulkMapping() : BulkBizUserAssoAction ");
		return forward;
	}

	private void closeResources(BulkBizUserAssoBean swap, FormFile file)
	throws IOException {
		if (swap != null) {
			swap.getUploadFile().destroy();
		}
		if (file != null) {
			file.destroy();
		}

	}

}
