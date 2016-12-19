/**************************************************************************
 * File     : BulkSubscriberDeletionServlet.java
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
package com.ibm.virtualization.ussdactivationweb.reports.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.reports.scheduler.BulkSubscriberDeletionScheduler;

/**
 * @author a_gupta
 *
 */
public class BulkSubscriberDeletionServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(BulkSubscriberDeletionServlet.class.getName());

	public void init() {
		String[] arrDummy = null;
		try {
			logger
					.info("Inititalizialing Subscriber Release ");
			logger.debug("@@@ In startup servlet-init");
			
			BulkSubscriberDeletionScheduler.main(arrDummy);
			logger.debug("@@@ Subscriber Release started");

			logger
					.info("Initialized Subscriber Release ");
		} catch (Exception e) {
			logger.error("Exception in StartupServlet: " + e.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] arrDummy = null;
		logger.debug("@@@ In startup servlet-init");
		try {
			BulkSubscriberDeletionScheduler.main(arrDummy);
			logger.debug("@@@ Subscriber Release started");
		} catch (Exception e) {
			logger.error("Exception in StartupServlet: " + e.getMessage());
		}

		super.doGet(request, response);
	}
}
