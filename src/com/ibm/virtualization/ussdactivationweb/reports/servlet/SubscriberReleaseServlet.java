/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.reports.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.ibm.virtualization.ussdactivationweb.reports.scheduler.SubscriberReleaseScheduler;

/**
 * @author a_gupta
 *
 */
public class SubscriberReleaseServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger
			.getLogger(SubscriberReleaseServlet.class.getName());

	public void init() {
		String[] arrDummy = null;
		try {
			logger
					.info("Inititalizialing Subscriber Release ");
			logger.debug("@@@ In startup servlet-init");

			SubscriberReleaseScheduler.main(arrDummy);
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
			SubscriberReleaseScheduler.main(arrDummy);
			logger.debug("@@@ Subscriber Release started");
		} catch (Exception e) {
			logger.error("Exception in StartupServlet: " + e.getMessage());
		}

		super.doGet(request, response);
	}
}
