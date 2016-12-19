/*
 * Created on May 24, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.dp.schedular;

import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.ibm.utilities.Utility;

/**
 * @author Siddhartha Sharma
 * 
 * This scheduled fils is being called by the
 * com.servlet.ScheduleProcessServlet.java
 */
public class ScheduleProcess extends TimerTask {

	private static Logger logger = Logger.getLogger(ScheduleProcess.class.getName());
	
	//private static boolean isRunning = false;

	@SuppressWarnings("unused")
	private ServletContext context = null;

	public ScheduleProcess(ServletContext context) {
		this.context = context;
	}  

	public void run() 
	{
		
	}
	
	/*
	 * Commented by Nazim Hussian to Stop all webservices hits 
	 
	public void run() 
	{
			logger.info("******* Schedule Process of Webservice Invocation from IP - "+ Utility.getIPAddress()+ " begins *******");
			
			try
			{
				CreateRegionWebService createRegion = CreateRegionWebService.getCreateRegionWebService();
				createRegion.createRegion();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateRegionWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateRegionWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				EditRegionWebService editRegion = EditRegionWebService.getEditRegionWebService();
				editRegion.editRegion();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditRegionWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditRegionWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				CreateCircleWebService createCircle = CreateCircleWebService.getCreateCircleWebService();
				createCircle.createCircle();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateCircleWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateCircleWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				EditCircleWebService editCircle = EditCircleWebService.getEditCircleWebService();
				editCircle.editCircle();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditCircleWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditCircleWebServiceScheduleProcess - ",e);
			}
			
			
			
			try
			{
				CreateZoneWebServices createZone = CreateZoneWebServices.getCreateZoneWebServices();
				createZone.createZone();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateZoneWebServices ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateZoneWebServices ScheduleProcess - ",e);
			}
			
			
			try
			{
				EditZoneWebService editZone = EditZoneWebService.getEditZoneWebService();
				editZone.editZone();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditZoneWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditZoneWebService ScheduleProcess - ",e);
			}
			
			
			
			try
			{
				CreateTownWebService createTown = CreateTownWebService.getCreateTownWebService();
				createTown.createTown();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateTownWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateTownWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				EditTownWebService editTown = EditTownWebService.getEditTownWebService();
				editTown.editTown();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditTownWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditTownWebService ScheduleProcess - ",e);
			}
			
			
			
			try
			{
				CreateTSMWebService createTsm = CreateTSMWebService.getCreateTSMWebService();
				createTsm.createTsm();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateTSMWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateTSMWebService ScheduleProcess - ",e);
			}
			
			
			
			try
			{
				EditTSMWebService editTsm = EditTSMWebService.getEditTSMWebService();
				editTsm.editTsm();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditTSMWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditTSMWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				CreateDistributorWebService createDistributor = CreateDistributorWebService.getCreateDistributor();
				createDistributor.createDistributor();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateDistributorWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateDistributorWebService ScheduleProcess - ",e);
			}
			
			
			try
			{
				EditDistributorWebService editDistributor =  EditDistributorWebService.getEditDistributorWebService();
				editDistributor.editDistributor();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditDistributorWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditDistributorWebService ScheduleProcess - ",e);
			}
			
			try
			{
				CreateProductWebService createProduct = CreateProductWebService.getCreateProductWebService(); 
				createProduct.createProduct();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreateProductWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreateProductWebService ScheduleProcess - ",e);
			}
			
			try
			{
				EditProductWebService editProduct = EditProductWebService.getEditProductWebService();
				editProduct.editProduct();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in EditProductWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in EditProductWebService ScheduleProcess - ",e);
			}
			
			try
			{
				CreatePOWebService createPo = CreatePOWebService.getCreatePOWebService();
				createPo.createPo();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CreatePOWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CreatePOWebService ScheduleProcess - ",e);
			}
			
			try
			{
				CancelPOWebService cancelPo = CancelPOWebService.getCancelPOWebService();
				cancelPo.cancelPo();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in CancelPOWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in CancelPOWebService ScheduleProcess - ",e);
			}
			
			try
			{
				WrongShipmentWebService wrongshipment = WrongShipmentWebService.getWrongShipmentWebService();
				wrongshipment.sendWrongShipment();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in WrongShipmentWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in WrongShipmentWebService ScheduleProcess - ",e);
			}
			
			try
			{
				PushDcToBoTreeWebService puBoTreeWebService = PushDcToBoTreeWebService.getPushDcToBoTreeWebService();
				puBoTreeWebService.sendDeliveryChallanToBoTree();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in PushDcToBoTreeWebService ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in PushDcToBoTreeWebService ScheduleProcess - ",e);
			}
			
			//Added by Nazim Hussain to add new scheduler for Pushing Freah Stock DC into BoTree
			try
			{
				PushFreshStockToWarehouse puBoTreeWebService = PushFreshStockToWarehouse.getPushFreshStockToWarehouse();
				puBoTreeWebService.pushFreshStockToBoTree();
			} 
			catch (ServletException e) 
			{
				logger.error("Servlet Error in PushFreshStockToWarehouse ScheduleProcess - ",e);
			} 
			catch (Exception e) 
			{
				logger.error("Error in PushFreshStockToWarehouse ScheduleProcess - ",e);
			}
			
			logger.info("******* Schedule Process of Webservice Invocation from IP - "+ Utility.getIPAddress()+ " Ends *******");
	}
	*/
}
