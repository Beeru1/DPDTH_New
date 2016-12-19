 
/**************************************************************************
 * File     : SMSNewPullReportServlet.java
 * Author   : Banita
 * Created  : Feb 15, 2009
 * Modified : Feb 18, 2009
 * Version  : 1.0
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/
package com.ibm.virtualization.ussdactivationweb.reports.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.virtualization.recharge.sms.SMSConnectionBean;
//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportRequestDTO;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportConfigInitializer;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportService;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;



/**
 * Servlet implementation class for Servlet: SMReportServlet
 *
 */
public class SMSNewPullReportServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final Logger logger = Logger.getLogger(SMSNewPullReportServlet.class);

	SMSReportService smsReportService = null;

	String IS_SMS_FLAG_ON = "YES";

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */

	public void init(ServletConfig servletConfig) throws ServletException {

		super.init(servletConfig);

		/** Instantiate  SMSReportService on servlet initialization **/
		smsReportService =new SMSReportService();
		IS_SMS_FLAG_ON = Utility.getValueFromBundle(Constants.IS_SMS_REPORT_FLAG_ON, Constants.SMS_REPORT_RESOURCE_BUNDLE); // read from constants and set to true / false accordingly.
	}


	/*  (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** invoke doPost() method from doGet() **/ 
		doPost(request, response);
	}  	

	/* This method is entry point for any request comming for SMSPullReport
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/*
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.debug(" Entering in doPost(request, response) of SMSNewPullReportServlet");
		String mobileNo = request.getParameter("mobileno"); 
		String message = request.getParameter("msg"); ;		
		String reportCode = null;
		String smsMsg = null;
		PrintWriter out = response.getWriter();
		String requesterRegNo = mobileNo;
		SMSReportRequestDTO requestDTO = new SMSReportRequestDTO(mobileNo, message);
		try{ 
			/*** just to validate whether the requester has send the proper code and format of message ** 
			boolean  isValidRptCodeAndRegNo = smsReportService.validateLatestRptCodeAndRequesterNo(requestDTO);
			if(!isValidRptCodeAndRegNo){
				smsMsg = requestDTO.getErrMsg();
			} else {

				/*** Getting business user details by requestor registered no ** 

				BusinessUserDTO businessUserDTO = smsReportService.getNewBusinessUserDetails(requesterRegNo);
					if(businessUserDTO != null && Constants.ActiveState.equalsIgnoreCase(businessUserDTO.getStatus())){	

						/*** Validate SMS Short code and willc heck whether the short caode is authorised to user** 

						String isValidRequestor = smsReportService.validateLatestSMSReportRequest(requestDTO,businessUserDTO);
						reportCode = requestDTO.getReportCode();

						/*** verify whethet the user has permission to code being sent to servlet** 
						String smsKeyword = smsReportService.verifyNewRequestorPermission(businessUserDTO,reportCode);	

						if(Constants.NO_PERM == smsKeyword) {
							smsMsg = Constants.UNAUTHORIZED_ACCESS;

						} else  {
					if(isValidRequestor.equalsIgnoreCase(Constants.N)) {
							smsMsg = requestDTO.getErrMsg();
						} else {
							if(reportCode.equals(SMSReportConfigInitializer.FIRST_SC) ||
									reportCode.equals(SMSReportConfigInitializer.SECOND_SC) ||
									reportCode.equals(SMSReportConfigInitializer.THIRD_SC)){
								/** for service class reports * 
								smsMsg = smsReportService.getLatestActServiceClassWise(requestDTO,businessUserDTO,smsKeyword);
								logger.debug(" SMS Message : "+smsMsg);

							}else if(reportCode.equals(SMSReportConfigInitializer.PENDING)){
							
								/** for pending reports * 
								smsMsg = smsReportService.getPendingReports(requestDTO,businessUserDTO,smsKeyword);
								
							}else if(reportCode.equals(SMSReportConfigInitializer.SUB_STATUS)){
								
								/** for subscriber reports * 						
								smsMsg = smsReportService.getLatestActStatusForSubs(requestDTO,businessUserDTO,smsKeyword);
								logger.debug(" SMS Message : "+smsMsg);
								
							}else {
								/** for activation reports * 							
									smsMsg = smsReportService.getLatestPullReport(requestDTO,businessUserDTO,smsKeyword);
							
								logger.debug(" SMS Message : "+smsMsg);

								} 
							}	
						}
					
					} else {
						smsMsg = Constants.UNAUTHORIZED_ACCESS;
					}
				

		 
			}
		}catch (Exception e) {
			logger.error(" Exception Occured : "+e.getMessage(),e);
			smsMsg = e.getMessage();
		}

		try {
			if (smsMsg != null && "YES".equalsIgnoreCase(IS_SMS_FLAG_ON)) {
			   //SendSMS.sendSMS(requesterRegNo, smsMsg);
			}
		}  catch (Exception e) {
			logger.error(" Exception Occured while sending SMS to requestor : " + requesterRegNo,e);
		} finally {
			out.println(smsMsg);			
			logger.debug(" Exiting from doPost(request, response) of SMSPullReportServlet");
		}

	} 
*/
}