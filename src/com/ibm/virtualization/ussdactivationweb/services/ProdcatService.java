/**************************************************************************
 * File     : ProdcatService.java
 * Author   : Banita
 * Created  : Oct 7, 2008
 * Modified : Oct 7, 2008
 * Version  : 0.1
 **************************************************************************
 *                               HISTORY
 **************************************************************************
 * V0.1		Oct 7, 2008 	Banita	First Cut.
 * V0.2		Oct 7, 2008 	Banita	First modification
 **************************************************************************
 *
 * Copyright @ 2002 This document has been prepared and written by 
 * IBM Global Services on behalf of Bharti, and is copyright of Bharti
 *
 **************************************************************************/

package com.ibm.virtualization.ussdactivationweb.services;

import org.apache.log4j.Logger;

import com.ibm.virtualization.ussdactivationweb.dao.ProdcatServicesDaoImpl;
import com.ibm.virtualization.ussdactivationweb.utils.DAOException;
import com.ibm.virtualization.ussdactivationweb.services.dto.CircleDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.LocationMstDTO;
import com.ibm.virtualization.ussdactivationweb.services.dto.ServiceDTO;
import com.ibm.virtualization.ussdactivationweb.utils.Constants;
import com.ibm.virtualization.ussdactivationweb.utils.Utility;

/*******************************************************************************
 * All the methods in this class call their respective methods in DAO Class It
 * contain all Prodcat related methods.
 * 
 * @author Banita
 * @version 1.0
 ******************************************************************************/

public class ProdcatService {

	private static Logger logger = Logger.getLogger(ProdcatService.class
			.getName());

	/**
	 * This method getServiceClassById() gets the service class details
	 * 
	 * @param serviceClassId
	 *            object for retreiving the details
	 * @return ServiceDTO
	 */

	public ServiceDTO getServiceClassById(String serviceClassId,
			String circleCode) {
		logger
		.debug("Entering into getServiceClassById() of ProdcatService");
		ServiceDTO serviceClassDTO = null;
		if (serviceClassId.equals(null) || ("").equals(serviceClassId)
				|| circleCode.equals(null) || ("").equals(circleCode)) {
			serviceClassDTO = new ServiceDTO();
			serviceClassDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			serviceClassDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.ServiceClass_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
				serviceClassDTO = new ServiceDTO();
				serviceClassDTO = prodcatServices.getServiceClassById(Integer
						.parseInt(serviceClassId), circleCode);
				logger
				.info("ProdcatService.getServiceClassById(): serviceClassDTO = "
						+ serviceClassDTO
						+ " for service class - "
						+ serviceClassDTO);
				if (serviceClassDTO == null) {
					serviceClassDTO = new ServiceDTO();
					serviceClassDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					serviceClassDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_ServiceClass,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));

				}
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger
		.debug("Exiting from getServiceClassById() of ProdcatService");
		return serviceClassDTO;

	}

	/**
	 * This method getCircleByCode() gets the circle details
	 * 
	 * @param circleCode
	 *            object for retreiving the details
	 * @return CircleDTO
	 */
	public CircleDTO getCircleByCode(String circleCode) {
		logger
		 .debug("Entering into getCircleByCode() of ProdcatService");
		CircleDTO circleDTO = null;
		if (circleCode.equals(null) || ("").equals(circleCode)) {
			circleDTO = new CircleDTO();
			circleDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			circleDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.CircleCode_blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		} else {
			try {
				ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
				circleDTO = new CircleDTO();
				circleDTO = prodcatServices.getCircleByCode(circleCode);
				logger
				.info("ProdcatService.getCircleByCode(): circleDTO = "
						+ circleDTO
						+ " for circle - "
						+ circleDTO);
				if (circleDTO == null) {
					circleDTO = new CircleDTO();
					circleDTO.setErrorCode(Utility.getValueFromBundle(
							Constants.Error_Code2,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					circleDTO.setErrorMessage(Utility.getValueFromBundle(
							Constants.No_Circle,
							Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
				} 
			} catch (DAOException e) {
				logger.error(e.getMessage(), e);
			}
		}logger
		 .debug("Exiting from getCircleByCode() of ProdcatService");
		return circleDTO;
	}


	/**
	 * This method getLatestLocationCount() calls the impl to fetch the count.
	 * 
	 * @param modifiedfromDate
	 * 				  object for retreiving the details
	 * @param modifiedtoDate
	 * 				object for retreiving the details
	 * @return int
	 */

	public int getLatestLocationCount(String modifiedfromDate,
			String modifiedtoDate) {
		int locCount = 0;
		logger
		  .debug("Entering into getLatestLocationCount() of ProdcatService");
		try {
			ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
			locCount = prodcatServices.getLatestLocationCount(modifiedfromDate,
					modifiedtoDate);
			logger
			.info("ProdcatService.getLatestLocationCount()"+locCount);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}logger
		  .debug("xiting from getLatestLocationCount() of ProdcatService");
		return locCount;
	}
	
	/**
	 * This method getLatestLocationDetails() gets the location details
	 * 
	* @param modifiedfromDate
	* 			  object for retreiving the details
	 * @param modifiedtoDate
	 * 			  object for retreiving the details
	 * @return LocationMstDTO
	 */

	public LocationMstDTO getLatestLocationDetails(String modifiedfromDate,
			String modifiedtoDate, int intLb, int intUb) {
		logger
		  .debug("Entering into getLatestLocationDetails() of ProdcatService");
		LocationMstDTO locationMstrDTO = null;
		if (modifiedfromDate.equals(null) || ("").equals(modifiedfromDate)
				|| modifiedtoDate.equals(null) || ("").equals(modifiedtoDate)) {
			locationMstrDTO = new LocationMstDTO();
			locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Date_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		}else{
				try {
					ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
					locationMstrDTO = new LocationMstDTO();
					locationMstrDTO = prodcatServices.getLatestLocationDetails(
							modifiedfromDate, modifiedtoDate, intLb, intUb);
					logger
					.info("ProdcatService.getLatestLocationDetails(): locationMstrDTO = "
							+ locationMstrDTO
							+ " for location - "
							+ locationMstrDTO);
					if (locationMstrDTO == null) {
						locationMstrDTO = new LocationMstDTO();
						locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
								Constants.Error_Code2,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
						locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
								Constants.No_Location,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					}
				} catch (DAOException e) {
					logger.error(e.getMessage(), e);
				}
		}logger
		  .debug("Exiting from getLatestLocationDetails() of ProdcatService");
		return locationMstrDTO;
	}
	
	/**
	 * This method getAllCircles() gets the circle details
	 * 
	* @param modifiedfromDate
	* 			  object for retreiving the details
	 * @param modifiedtoDate
	 * 			  object for retreiving the details
	 * @return LocationMstDTO
	 */

	public LocationMstDTO getAllCircles(String modifiedfromDate,
			String modifiedtoDate) {
		logger
		  .debug("Entering into getAllCircles() of ProdcatService");
		LocationMstDTO locationMstrDTO = null;
		if (modifiedfromDate.equals(null) || modifiedfromDate.equals("")
				|| modifiedtoDate.equals(null) || modifiedtoDate.equals("")) {
			locationMstrDTO = new LocationMstDTO();
			locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Date_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		}else{
				try {
					ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
					locationMstrDTO = new LocationMstDTO();
					locationMstrDTO = prodcatServices.getAllCircles(modifiedfromDate,
							modifiedtoDate);
					logger
					.info("ProdcatService.getAllCircles(): locationMstrDTO = "
							+ locationMstrDTO
							+ " for circles - "
							+ locationMstrDTO);
					if (locationMstrDTO == null) {
						locationMstrDTO = new LocationMstDTO();
						locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
								Constants.Error_Code2,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
						locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
								Constants.No_Location,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					}
				} catch (DAOException e) {
					logger.error(e.getMessage(), e);
				}
		}logger
		  .debug("Exiting from getAllCircles() of ProdcatService");
		return locationMstrDTO;
	}
	
	
	/**
	 * This method getAllHubs() gets the hub details
	 * 
	* @param modifiedfromDate
	* 			  object for retreiving the details
	 * @param modifiedtoDate
	 * 			  object for retreiving the details
	 * @return LocationMstDTO
	 */
	public LocationMstDTO getAllHubs(String modifiedfromDate,
			String modifiedtoDate) {
		logger
		  .debug("Entering into getAllHubs() of ProdcatService");
		LocationMstDTO locationMstrDTO = null;
		if (modifiedfromDate.equals(null) || ("").equals(modifiedfromDate)
				|| modifiedtoDate.equals(null) || ("").equals(modifiedtoDate)) {
			locationMstrDTO = new LocationMstDTO();
			locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
					Constants.Error_Code1,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
			locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
					Constants.Date_Blank,
					Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
		}else{

				try {
					ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
					locationMstrDTO = new LocationMstDTO();
					locationMstrDTO = prodcatServices.getAllHubs(modifiedfromDate,
							modifiedtoDate);
					logger
					.info("ProdcatService.getAllHubs(): locationMstrDTO = "
							+ locationMstrDTO
							+ " for hubs - "
							+ locationMstrDTO);
					if (locationMstrDTO == null) {
						locationMstrDTO = new LocationMstDTO();
						locationMstrDTO.setErrorCode(Utility.getValueFromBundle(
								Constants.Error_Code2,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
						locationMstrDTO.setErrorMessage(Utility.getValueFromBundle(
								Constants.No_Location,
								Constants.WEB_APPLICATION_RESOURCE_BUNDLE));
					}
				} catch (DAOException e) {
					logger.error(e.getMessage(), e);
				}
		}
		logger
		  .debug("Exiting from getAllHubs() of ProdcatService");
		return locationMstrDTO;
	}
	
	
	/**
	 * This method getActiveCircle() gets the circle details
	 * 
	 * @param circleCode
	 *            object for retreiving the details
	 * @return String
	 */
	
	public String getActiveCircle(String circleCode) {
		logger
		  .debug("Entering into getActiveCircle() of ProdcatService");
		String activeCircle="";
		try {
			ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
			activeCircle = prodcatServices.getActiveCircle(circleCode);
			logger
			.info("ProdcatService.getActiveCircle()"+activeCircle);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}logger
		  .debug("Exiting from getActiveCircle() of ProdcatService");
		return activeCircle;
	}
	
	/**
	 * This method getAllActiveCircles() gets all the active circle details
	 * 
	 * @param circleCode
	 *            object for retreiving the details
	 * @return String
	 */
	public LocationMstDTO getAllActiveCircles(){
		logger
		  .debug("Entering into getAllActiveCircles() of ProdcatService");
		LocationMstDTO locationMstrDTO = null;
		
		try {
			ProdcatServicesDaoImpl prodcatServices = new ProdcatServicesDaoImpl();
			locationMstrDTO = new LocationMstDTO();
			locationMstrDTO = prodcatServices.getAllActiveCircles();
			logger
			.info("ProdcatService.getAllActiveCircles()");
		}catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		
		logger
		  .debug("Exiting from getAllActiveCircles() of ProdcatService");
		return locationMstrDTO;		
	}

}
