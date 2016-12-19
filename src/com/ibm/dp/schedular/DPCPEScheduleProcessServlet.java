package com.ibm.dp.schedular;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class DPCPEScheduleProcessServlet extends HttpServlet
{

  private java.util.Timer timer = null;


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {


	}

	public void init() throws ServletException { 
	super.init();
		try
		{
	        int delay = 1*60*1000;   
			int period = 2*60*1000;  
			Timer timer = new Timer();
			
			// No more need introduce, New scheduler with integration with DPCPE.  
			//timer.scheduleAtFixedRate(new DPCPEScheduleProcess(getServletContext()), delay, period);
        
			}catch(Exception e){ 
			e.printStackTrace();
		}
	}


}




