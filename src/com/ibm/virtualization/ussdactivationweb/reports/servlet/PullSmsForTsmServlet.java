package com.ibm.virtualization.ussdactivationweb.reports.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.ibm.dp.sms.SMSUtility;
import com.ibm.virtualization.ussdactivationweb.reports.service.SMSReportService;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
public class PullSmsForTsmServlet extends HttpServlet {
	String mobileno=null;
	String shortcode=null;
	String dist_id=null;
	String message=null;
	String responseMsg=null;
	private static final Logger logger = Logger.getLogger(PullSmsForTsmServlet.class);
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//** invoke doPost() method from doGet() **//* 
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		doPost(request, response);
	}  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");
		PrintWriter out=null;
		Boolean checkSMSFormat=true;
		mobileno=request.getParameter("MSISDN");
		message=request.getParameter("MESSAGE");
		out=response.getWriter();
		logger.info("====== in do post pullsmsfortsmservler ==========");
		try
		{
		if(message!=null && mobileno!=null)
		{
			/*checkSMSFormat=SMSUtility.checkSMSFormat(message);
			if(checkSMSFormat)
			{*/
				responseMsg=SMSUtility.generateSMS(message,mobileno);
			/*}
			else
			{
			}*/
		}
		else 
		{
		}
		out.print(responseMsg);
		out.flush();
		out.close();
		
		}
		catch(Exception ex)
		{
			out.flush();
			out.close();
			ex.printStackTrace();
		}
		
	}
}
/*public class PullSmsForTsmServlet extends Action
{
	private static final Logger logger = Logger.getLogger(PullSmsForTsmServlet.class);
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	        throws Exception {
		String mobileno=null;
		String shortcode=null;
		String dist_id=null;
		String message=null;
		String responseMsg=null;
		PrintWriter out=null;
			Boolean checkSMSFormat=true;
			mobileno=request.getParameter("MSISDN");
			message=request.getParameter("MESSAGE");
			out=response.getWriter();
			logger.info("========= inf pullsmsfor tsmsservler ==============");
			if(message!=null && mobileno!=null)
			{
				checkSMSFormat=SMSUtility.checkSMSFormat(message);
				if(checkSMSFormat)
				{
					responseMsg=SMSUtility.generateSMS(message,mobileno);
				}
				else
				{
				}
			}
			else 
			{
			}
			out.print(responseMsg);
		
		return null;
		
	}
}*/
