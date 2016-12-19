
/**************************************************************************
 * File     : SMSPullReportServlet.java
 * Author   : Ashad
 * Created  : Oct 10, 2008
 * Modified : Oct 10, 2008
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
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

//import com.ibm.virtualization.ussdactivationweb.dto.SMSReportRequestDTO;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportConfigInitializer;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportService;
import com.ibm.virtualization.ussdactivationweb.services.dto.BusinessUserDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.SendSMS;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;



/**
 * Servlet implementation class for Servlet: SMReportServlet
 *
 */
public class SMSPullReportServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final Logger logger = Logger.getLogger(SMSPullReportServlet.class);

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

		logger.debug(" Entering in doPost(equest, response) of SMSPullReportServlet");

		String mobileNo = request.getParameter("mobileno"); 
		String message = request.getParameter("msg"); ;		
		String reportCode = null;
		String smsMsg = null;
		PrintWriter out = response.getWriter();
		String requesterRegNo = mobileNo;
		SMSReportRequestDTO requestDTO = new SMSReportRequestDTO(mobileNo, message);
		try{ 

			boolean  isValidRptCodeAndRegNo = smsReportService.validateRptCodeAndRequesterNo(requestDTO);
			if(!isValidRptCodeAndRegNo){
				smsMsg = requestDTO.getErrMsg();
			} else {

				/*** Getting business user details by requestor registered no ***

				//BusinessUserDTO businessUserDTO = smsReportService.getBusinessUserDetails(requesterRegNo);
				ArrayList businessUsers = new ArrayList();
				businessUsers = smsReportService.getBusinessUserDetails(requesterRegNo);
				if(businessUsers.isEmpty()){
					smsMsg = Constants.UNAUTHORIZED_ACCESS;
				}else{
				Iterator itr= businessUsers.iterator();
				while(itr.hasNext()){
					BusinessUserDTO businessUserDTO = (BusinessUserDTO)itr.next();
					if(businessUserDTO != null && Constants.ActiveState.equalsIgnoreCase(businessUserDTO.getStatus())){	

						/*** Validate SMS Short code and populate the SMSReportRequestDTO ***

						boolean isValidRequestor = smsReportService.validateSMSReportRequest(requestDTO);




					reportCode = requestDTO.getReportCode();

						/*** verify the requester permission ***


					int rptTypeFlag = smsReportService.verifyRequestorPermission(businessUserDTO,reportCode);	

						if(Constants.NO_PERMISSION == rptTypeFlag) {
							smsMsg = Constants.UNAUTHORIZED_ACCESS;

						} else  {
						if(!isValidRequestor) {
							smsMsg = requestDTO.getErrMsg();
						} else {
							if(reportCode.equals(SMSReportConfigInitializer.ACT_STATUS_FOR_SINGLE_SUB)){

									smsMsg = smsReportService.getActStatusForSubs(requestDTO);
									logger.debug(" SMS Message : "+smsMsg);

							}else if(reportCode.equals(SMSReportConfigInitializer.SERVICE_CLASS_WISE_ACT_COUNT_FOR_FTD)){



								smsMsg = smsReportService.getActServiceClassWise(requestDTO,rptTypeFlag);
								logger.debug(" SMS Message : "+smsMsg);

							} else {
								smsMsg = smsReportService.getPullReport(requestDTO,businessUserDTO,rptTypeFlag);
								logger.debug(" SMS Message : "+smsMsg);

								} 
							}	
						break;
						}
						
					} else {
						smsMsg = Constants.UNAUTHORIZED_ACCESS;
					}
				}

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
*/}