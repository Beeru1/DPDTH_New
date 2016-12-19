/**
 * 
 */
package com.ibm.virtualization.ussdactivationweb.bulkupload.StartUpServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.bulkupload.scheduler.BulkBizUserCreationScheduler;

/**
 * @author Nitesh
 *
 */
public class BulkBizUserCreationStratUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(BulkBizUserCreationStratUpServlet.class
			.getName());

	public void init() {
		String[] arrDummy = null;
		try {
			logger.debug("@@@ In startup servlet-init of BulkBizUserCreationStratUpServlet");
			
			
			//BulkBizUserCreationScheduler.main(arrDummy);
			logger.debug("@@@ Bulk Scheduler started BulkBizUserCreationStratUpServlet");
		} catch (Exception e) {
			logger.debug("Exception in StartupServlet BulkBizUserCreationStratUpServlet: " + e.getMessage());
		}
	}
	
	protected void doGet(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String[] arrDummy = null;
		logger.debug("@@@ In startup servlet-init BulkBizUserCreationStratUpServlet");
		
		//BulkBizUserCreationScheduler.main(arrDummy);
		logger.debug("@@@ Bulk Scheduler started BulkBizUserCreationStratUpServlet");
		super.doGet(arg0, arg1);
	}
}
