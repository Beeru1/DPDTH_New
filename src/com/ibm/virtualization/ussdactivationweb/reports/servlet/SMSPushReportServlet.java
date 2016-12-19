package com.ibm.virtualization.ussdactivationweb.reports.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.reports.scheduler.SMSPushReportEngineScheduler;


/**
 * Servlet implementation class for Servlet: SMSPushReportServlet
 *
 */
 public class SMSPushReportServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	 
	 private static final Logger logger = Logger.getLogger(SMSPushReportServlet.class);

	 
	public void init(ServletConfig servletConfig) {
		
		try{
			super.init(servletConfig);	
			
			/** Call Report Engine Scheduler on Servlet initialization**/
			
			String[] args =null;
			
			//SMSPushReportEngineScheduler.main(args);
			
		} catch (Exception e) {
			
			logger.error("SMSPushReportServlet Initialization Failed",e);
		}
		
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** Invoke doPost() method from doGet() **/ 
		doPost(request, response);
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug(" Entering in doPost(equest, response) of SMSPushReportServlet");

			try{

			/** Call Report Engine Scheduler **/
			String[] args =null;
			
			//SMSPushReportEngineScheduler.main(args);

			
		} catch(Exception e){
			logger.error(" Exception Occured : "+e);
			
		}

		logger.debug(" Exiting from doPost(equest, response) of SMSPushReportServlet");
	}   	  	    
}