package com.ibm.dp.schedular;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.Constants;
import com.ibm.virtualization.recharge.common.ResourceReader;


public class ScheduleProcessServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
	}

	private static Logger logger = Logger.getLogger(ScheduleProcessServlet.class.getName());

	public void init() throws ServletException
	{
		super.init();
		try{ 

			Timer timer = new Timer();
	        Date startTime = getStartTime();
	        int websertime = Integer.parseInt(ResourceReader.getValueFromBundle(Constants.SCHEDULAR_WEBSERVICE_TIME,Constants.WEBSERVICE_RESOURCE_BUNDLE));
	        
	        //int dpcpetime = Integer.parseInt(ResourceReader.getValueFromBundle(Constants.SCHEDULAR_CPE_TIME,Constants.WEBSERVICE_RESOURCE_BUNDLE));
	        // time in milliseconds between successive task executions,

//	      long period = 1000 * 60 * websertime;
//	      timer.schedule(new ScheduleProcess(getServletContext()), startTime, period);	// Comment to Stop Scheduler

		}
		catch(Exception e)
		{
			logger.error("************** Error in ScheduleProcessServlet - "+e.getMessage()+" ********* ", e);
		}
	}


	private static Date getStartTime ()
	{
		Calendar calendar = Calendar.getInstance();
		Date startTime = null;
		try {
			if (Utility.getIPAddress().equalsIgnoreCase(
					ResourceReader.getValueFromBundle(
							Constants.SCHEDULAR_HOST_IP1,
							Constants.WEBSERVICE_RESOURCE_BUNDLE))) {
				startTime = calendar.getTime();
				logger.info("********* Schedulat start time for IP - "+ResourceReader.getValueFromBundle(
							Constants.SCHEDULAR_HOST_IP1,
							Constants.WEBSERVICE_RESOURCE_BUNDLE)+" is - "+ startTime.toString());
			}
			else if (Utility.getIPAddress().equalsIgnoreCase(
					ResourceReader.getValueFromBundle(
							Constants.SCHEDULAR_HOST_IP2,
							Constants.WEBSERVICE_RESOURCE_BUNDLE))) {
				calendar.add(Calendar.MINUTE, Integer.parseInt(ResourceReader.getValueFromBundle(
						Constants.SCHEDULAR_HOST_INTERVAL,
						Constants.WEBSERVICE_RESOURCE_BUNDLE)));
				startTime = calendar.getTime();
				logger.info("********* Schedulat start time for IP - "+ResourceReader.getValueFromBundle(
						Constants.SCHEDULAR_HOST_IP2,
						Constants.WEBSERVICE_RESOURCE_BUNDLE)+" is - "+ startTime.toString());

			}
			else {
				startTime = calendar.getTime();
				logger.info("********* Schedulat start time for IP - "+Utility.getIPAddress()+" is - "+ startTime.toString());
			}

		}
		catch (Exception e) {
			logger.error("************** Error in ScheduleProcessServlet getStartTime - "+e.getMessage()+" ********* ", e);
			startTime = calendar.getTime();
		}
		return startTime;

	}

}
