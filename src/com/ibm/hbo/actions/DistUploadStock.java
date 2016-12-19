package com.ibm.hbo.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

import com.ibm.hbo.common.HBOConstants;
import com.ibm.hbo.common.HBOLibrary;
import com.ibm.hbo.common.HBOUser;
import com.ibm.hbo.dao.HBOBulkUploadDAO;
import com.ibm.hbo.dao.impl.HBOBulkUploadDAOImpl;
import com.ibm.hbo.exception.HBOException;
import com.ibm.hbo.forms.HBOFileUploadFormBean;
import com.ibm.hbo.services.HBOMasterService;
import com.ibm.hbo.services.impl.HBOMasterServiceImpl;
import com.ibm.utilities.ErrorCodes;
import com.ibm.utilities.PropertyReader;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;


/**
 * @version 	1.0
 * @author
 */
public class DistUploadStock extends DispatchAction {

		
	private static final Logger logger;
	static {
		logger = Logger.getLogger(DistUploadStock.class);
	}
	
	public ActionForward fileUpload(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		HBOFileUploadFormBean fileUploadFormBean = (HBOFileUploadFormBean) form;
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		String userIdFrom =sessionContext.getId()+"";
		logger.info("userNameFrom---"+userIdFrom);
		
		//Setting Category List in Form
		ArrayList arrBussList = new ArrayList();
		HBOMasterService masterService = new HBOMasterServiceImpl();
		arrBussList = masterService.getMasterList(userIdFrom,HBOConstants.BUSINESS_CATEGORY_STOCK, "");
		fileUploadFormBean.setArrCategory(arrBussList);
		forward = mapping.findForward("success");
		return (forward);
	}

	private ActionErrors insertBulkData(
		HBOFileUploadFormBean fileUploadFormBean,
		HBOUser obj,
		HttpServletRequest request)
		throws HBOException, IOException {
	
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		
		HBOBulkUploadDAOImpl bulkDao = new HBOBulkUploadDAOImpl();
		HBOLibrary hboLibrary = new HBOLibrary();
		
		FormFile myFile = (FormFile) fileUploadFormBean.getThefile();		
		String FILEMAXSIZE=null;		
		FILEMAXSIZE = PropertyReader.getValue("PROD_FILE_MAX_SIZE");  // Max File Size define property File
		
		int FILESIZE = Integer.parseInt(FILEMAXSIZE);  
		String filePath = "";
		String fileFormat = "";
		String fileName = myFile.getFileName();
		byte[] fileData = myFile.getFileData();
	
		if (myFile != null && myFile.getFileSize() < FILESIZE) {
			String changedFileName =null;
			int fileId =0; 
			filePath =(String) getResources(request).getMessage("UPLOAD_DIST_BULK"); 
			
			fileId = bulkDao.insertBulkUploadData(Integer.parseInt(obj.getCircleId()),fileName,obj.getId().intValue(),filePath,"DistUpload");	
			
			changedFileName = hboLibrary.changedFileName(fileName,obj.getCircleId(),obj.getWarehouseID(),fileUploadFormBean.getContentOfFile(),fileId);

			fileFormat =(String) getResources(request).getMessage("DIST_UPLOAD_FORMAT1"); // Start Serialno,End Srial No,
			hboLibrary.writeToFile(fileFormat,fileData,filePath,changedFileName);
			fileUploadFormBean.setThefile(null);
		} 
		else {
			fileUploadFormBean.setMessage(null);
			if ("SIZE".equals(fileUploadFormBean.getContentOfFile())) {
				errors.add("BUS006", new ActionError("BUS006"));
			} else {
				errors.add("BUS005", new ActionError("BUS005"));
			}

		}
		
		request.setAttribute("ContentOfFile1",fileUploadFormBean.getContentOfFile());
		return errors;

	}

	public ActionForward bulkUpload(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		ActionForward forward = new ActionForward(); // return value
		HBOFileUploadFormBean fileUploadFormBean = (HBOFileUploadFormBean) form;
		HttpSession session = request.getSession();
		UserSessionContext sessionContext = (UserSessionContext) session.getAttribute(Constants.AUTHENTICATED_USER);
		HBOUser obj=new HBOUser(sessionContext);	
		ActionErrors errors = new ActionErrors();
		
		try {
			/*if (mapping.getPath().equalsIgnoreCase(HBOConstants.INSERT_BULK_DATA)) {
				getBulkData(mapping, fileUploadFormBean, obj);
				return mapping.findForward(HBOConstants.SUCCESS);
			  }
			if (mapping.getPath().equalsIgnoreCase(HBOConstants.UPLOAD_DIST_DATA)) {
				errors = insertBulkData(fileUploadFormBean, obj, request);
			}*/
			errors = insertBulkData(fileUploadFormBean, obj, request);
		} catch (Exception e) {

			if (e instanceof HBOException) {
				HBOException hboException = (HBOException) e;
				String errorCode = hboException.getErrCode();
				int errorCtr = hboException.getErrCtr();
				if (errorCode != null) {

					if (errorCode
						.equalsIgnoreCase(ErrorCodes.SYSTEMEXCEPTION)) {
						errors.add("SYS001", new ActionError("SYS001"));
					}
					if (errorCode.equalsIgnoreCase(ErrorCodes.SQLEXCEPTION)) {
						errors.add("SYS002", new ActionError("SYS002"));
					}
					if (errorCode
						.equalsIgnoreCase(ErrorCodes.UPLOADEXCEPTION)) {
						errors.add("BUS003", new ActionError("BUS003"));
					}
					if (errorCode
						.equalsIgnoreCase(ErrorCodes.FILEFORMATEXCEPTION)) {
						errors.add("BUS004", new ActionError("BUS004"));
						fileUploadFormBean.setMessage("Error at line Number: " + errorCtr);
						//	errors.add("BUS005", new ActionError("BUS00"));
					}
					e.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {

			saveErrors(request, errors);
			forward = mapping.findForward(HBOConstants.FAILURE);

		} else {
			forward = mapping.findForward(HBOConstants.SUCCESS);

		}

		return (forward);
	}
	private ActionForward getBulkData(
			ActionMapping mapping,
			HBOFileUploadFormBean fileUploadFormBean,
			HBOUser obj
			)
			throws HBOException {
			HBOBulkUploadDAO bulkDao = new HBOBulkUploadDAOImpl();	
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			java.util.Date nowDate = new java.util.Date();		
			int userId = obj.getId().intValue();
			String circleId = obj.getCircleId();		
			String sysDate1 = sdf.format(nowDate);
			String sysDate2 = sdf.format(nowDate);
			String fileType = fileUploadFormBean.getFileType();
			if (fileUploadFormBean.getRepStartDate() != null
				|| fileUploadFormBean.getRepEndDate() != null) {
				sysDate1 = fileUploadFormBean.getRepStartDate();
				sysDate2 = fileUploadFormBean.getRepEndDate();
			} else {
				sysDate1 = sdf.format(nowDate);
				sysDate2 = sdf.format(nowDate);
			}

			if (fileUploadFormBean.getToDate1() == null
				&& fileUploadFormBean.getToDate2() == null) {
				fileUploadFormBean.setToDate1(sysDate1.toUpperCase());
				fileUploadFormBean.setToDate2(sysDate2.toUpperCase());
				fileUploadFormBean.setRepStartDate(sysDate1.toUpperCase());
				fileUploadFormBean.setRepEndDate(sysDate2.toUpperCase());
			} else {
				fileUploadFormBean.setRepStartDate(fileUploadFormBean.getToDate1());
				fileUploadFormBean.setRepEndDate(fileUploadFormBean.getToDate2());
			}
			ArrayList uploadFileList=null; 
			if ("SIM".equals(fileUploadFormBean.getContentOfFile())) {
				uploadFileList =	bulkDao.getBulkUploadData(circleId, fileUploadFormBean, userId,fileType);
			}
			if ("PROD".equals(fileUploadFormBean.getContentOfFile())) {
				uploadFileList =	bulkDao.getBulkUploadData(circleId, fileUploadFormBean, userId,fileType);
			}
			fileUploadFormBean.setUploadFileList(uploadFileList);
			return mapping.findForward(HBOConstants.SUCCESS);
		}
}
