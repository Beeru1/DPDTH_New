/*
 * Created on May 24, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.dp.schedular;

import java.util.TimerTask;
import org.apache.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

//import com.ibm.dp.common.Constants;
import com.ibm.dp.cpedp.DPCPEDBInteraction;
import com.ibm.dp.cpedp.DPCPEDBInteractionImpl;
import com.ibm.utilities.Utility;
import com.ibm.virtualization.recharge.common.ResourceReader;


public class DPCPEScheduleProcess extends TimerTask 
{
	private static Logger logger = Logger.getLogger(DPCPEScheduleProcess.class.getName());
	@SuppressWarnings("unused")
	private ServletContext context = null;
	
	public DPCPEScheduleProcess(ServletContext context) 
	{
		this.context = context;
	}  

	public void run() 
	{
		try 
		{
			
			DPCPEDBInteraction interaction = new DPCPEDBInteractionImpl();
			//String paramFlag = interaction.getControlParameterFlag(Constants.PARAM_CPE_TO_DP);
			String paramFlag = ResourceReader.getValueFromBundle(com.ibm.virtualization.recharge.common.Constants.SCHEDUALR_CPEDP_INTFLAG,com.ibm.virtualization.recharge.common.Constants.WEBSERVICE_RESOURCE_BUNDLE);
			logger.info("****** Value of the DP CPE Integration touchpoint flag ->  " + paramFlag +" ******");
				  	
		  	if(paramFlag.equalsIgnoreCase("Y"))
		  	{
		  		logger.info("****** Schedule Process of CPE to DP updates from Host "+ Utility.getIPAddress()+" begins *******");
		  		interaction.updateAssignedStatus();
			  	interaction.updateRepairedStatus();
				interaction.updateUnAssignedStatus();
				logger.info("****** Schedule Process of CPE to DP updates from Host "+ Utility.getIPAddress()+" ends *******");
		  	}
		  	
		} 
		catch (ServletException e) 
		{
			logger.error("Servlet Error in ScheduleProcess - ",e);
		} catch (Exception e) 
		{
			logger.error("Error in ScheduleProcess - ",e);
		}
	}
}
