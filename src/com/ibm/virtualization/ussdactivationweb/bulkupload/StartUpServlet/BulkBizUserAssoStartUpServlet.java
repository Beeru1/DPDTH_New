/**************************************************************************
 * File     : BulkUploadStartUpServlet.java
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

package com.ibm.virtualization.ussdactivationweb.bulkupload.StartUpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler.BulkBizUserAssoScheduler;

/**
 * @author a_gupta
 * 
 */
public class BulkBizUserAssoStartUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(BulkBizUserAssoStartUpServlet.class.getName());

	public void init() {
		String[] arrDummy = null;
		try {
			logger
					.info("Inititalizialing Bulk File Upload of Business User Association");
			logger.debug("@@@ In startup servlet-init");

			BulkBizUserAssoScheduler.main(arrDummy);
			logger.debug("@@@ Bulk Biz Users started");

			logger
					.info("Initialized Bulk File Upload of Business User Association");
		} catch (Exception e) {
			logger.error("Exception in StartupServlet: " + e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] arrDummy = null;
		logger.debug("@@@ In startup servlet-init");
		try {
			BulkBizUserAssoScheduler.main(arrDummy);
			logger.debug("@@@ Bulk Biz Users started");
		} catch (Exception e) {
			logger.error("Exception in StartupServlet: " + e.getMessage());
		}

		super.doGet(request, response);
	}
}
