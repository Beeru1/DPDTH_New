/*
 * Created on jan 3,2008
 * @author Parul
 * Action class to intercept the request for 
 * generation of excel to view sucess and error
 * records after bulk file processing
 */
package com.ibm.hbo.actions;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.hbo.dao.impl.HBOUploadStatusDaoImpl;
import com.ibm.hbo.dto.HBOUserBean;
import com.ibm.hbo.forms.HBOFileUploadFormBean;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.dto.UserSessionContext;

/**
 * @author parul
 *
 */
public class HBOFileUploadStatus extends Action {
	private static final Logger logger;

	static {
		logger = Logger.getLogger(HBOFileUploadStatus.class);
		
	}
	public ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		HBOFileUploadFormBean fileUploadForm = (HBOFileUploadFormBean) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = new ActionForward(); // return value
		HttpSession session = request.getSession();
		int fileNo = Integer.parseInt((String)request.getParameter("fileId"));
		String fileType = (String)request.getParameter("fileType");
		HBOUploadStatusDaoImpl uploadStatusDao = new HBOUploadStatusDaoImpl();
		String status =null;
		String statusDetails =null;
		try {			
			if("/status".equalsIgnoreCase(mapping.getPath()))
			{
				status = uploadStatusDao.getStatus(fileNo,fileType);	
				fileUploadForm.setStatus(status);
				forward = mapping.findForward("success");
			}
			if("/statusDetails".equalsIgnoreCase(mapping.getPath()))
			{
				statusDetails = uploadStatusDao.getStatusDetails(fileNo,fileType);
				fileUploadForm.setStatusDetails(statusDetails);			
				forward = mapping.findForward("success");
			}			
		} catch (Exception e) {
			errors.add("name", new ActionError("id"));
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		} else {

		}
		return (forward);
	}
}