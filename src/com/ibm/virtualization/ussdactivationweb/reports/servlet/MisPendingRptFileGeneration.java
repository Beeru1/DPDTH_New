/**************************************************************************
 * File     : MisPendingRptFileGeneration.java
 * Author   : Ashad
 * Modified : 13 Feb ,1009
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		13 Feb ,1009 	Ashad First Cut.
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

import com.ibm.virtualization.ussdactivationweb.reports.scheduler.MisPendingReportScheduler;

/*******************************************************************************
 * This servlet MisPendingRptFileGeneration is used to generate the Misreport files 
 * 
 * @author  Ashad
 * @version 1.0
 ******************************************************************************/
public class MisPendingRptFileGeneration extends HttpServlet {

	private static final long serialVersionUID = 1497182296462436052L;

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	/**
	 * @see javax.servlet.http.HttpServlet#void
	 *      (javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] dummyStrings =null;
		MisPendingReportScheduler.main(dummyStrings);
	}
}